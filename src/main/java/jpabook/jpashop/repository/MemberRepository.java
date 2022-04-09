package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    //JPA가 제공하는 표준어노테이션
    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id) {
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        //jpql은 엔티티를 대상으로 한다.
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;
    }

    public List<Member> findByName(String name){
        List<Member> result = em.createQuery("select m from Member m where m.name = name", Member.class)
                .setParameter("name",name)
                .getResultList();
        return result;
    }
}
