package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("MEMBERa");
        //when
        Long savedID = memberRepository.save(member);
        Member findMember = memberRepository.find(savedID);
        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        //같은 영속성컨텍스트안에서는 두 객체ㄱ는 같다.
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}