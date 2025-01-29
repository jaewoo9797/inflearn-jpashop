package jpabook.jpashop.member.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.member.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
