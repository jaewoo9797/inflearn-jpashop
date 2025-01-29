package jpabook.jpashop.member.db;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import jpabook.jpashop.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryImplTest {

    @Autowired MemberRepositoryImpl memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {
        // given
        Member member = new Member();
        member.setUsername("memberA");
        Long savedId = memberRepository.save(member);

        // when
        Member foundMember = memberRepository.find(savedId);

        // then
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(foundMember).isEqualTo(member);
    }
}