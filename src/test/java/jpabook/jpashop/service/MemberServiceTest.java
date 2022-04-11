package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class) //JUnit에서 스프링 올려서 테스트하려면 이거 필요
@SpringBootTest//테스트임을 알려주는거
@Transactional //디비에 트랜잭션후 롤백(테스트클래스라)
public class MemberServiceTest {

    //Flush 사용하기위해!
    @Autowired
    EntityManager em;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    //@Rollback(false) //DB애 반영사항 직접 보고싶을 떄 ON
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("ddd");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); //영속성 컨텍스트에서 DB로 반영한다! 다시 롤백한다.
        Assert.assertEquals(member, memberRepository.findOne(saveId));
    }


    @Test(expected = IllegalStateException.class)  //예상하는 오류종류
    public void 중복회원예워() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("ddd1");
        Member member2 = new Member();
        member2.setName("ddd1");
        //when
        memberService.join(member1);
        memberService.join(member2);//예외가 튀어나와!
        //try{
        //    memberService.join(member2);
        //} catch(IllegalStateException e){//오류를 잡는다!
        //    return;
        //}


        //then
        Assert.fail("예외가 발생해야함"); //이곳에 도달하면 뭔가 잘못된 것임
    }

}