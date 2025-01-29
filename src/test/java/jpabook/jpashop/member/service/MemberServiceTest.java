package jpabook.jpashop.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import jpabook.jpashop.member.db.MemberRepositoryImpl;
import jpabook.jpashop.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepositoryImpl memberRepository;

    @Test
    void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("kim");
        // when
        Long savedId = memberService.join(member);
        Member foundMember = memberRepository.findOne(savedId);
        // then
        assertThat(foundMember).isEqualTo(member);
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");
        // when
        memberService.join(member1);
        Throwable throwable = catchThrowable(() -> memberService.join(member2));
        // then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already exists member : %s", member2.getUsername());
    }
}