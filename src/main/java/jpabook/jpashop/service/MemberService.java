package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    //의존성 주입 (생성자인젝션)
    private final MemberRepository memberRepository;


    //회원가입
    @Transactional
    public Long join(Member member){
        //중복회원검사 메소드
        validationDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validationDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원이니다.");
        }
    }


    //회원 전체 조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //회원조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
