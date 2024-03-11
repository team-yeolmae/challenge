package org.yeolmae.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.History;
import org.yeolmae.challenge.domain.Member;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    History findByMemberId(int memberId);

    // 특정 멤버가 특정 챌린지에 참여했는지 확인하는 메소드
    boolean existsByMemberAndChallenge(Member member, Challenge challenge);
}