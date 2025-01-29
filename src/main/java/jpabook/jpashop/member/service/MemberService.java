package jpabook.jpashop.member.service;

import java.util.List;
import jpabook.jpashop.member.db.MemberRepositoryImpl;
import jpabook.jpashop.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    // TODO 구현체 의존 -> 이후 인터페이스로 리팩터링 실시하기
    private final MemberRepositoryImpl memberRepository;

    @Transactional
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> foundMember =
                memberRepository.findByName(member.getUsername());
        if (!foundMember.isEmpty()) {
            throw new IllegalStateException(String.format("already exists member : %s", member.getUsername()));
        }
    }
}
