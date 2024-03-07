package org.yeolmae.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yeolmae.challenge.domain.History;
import org.yeolmae.challenge.domain.Member;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    History findByMemberId(int memberId);
}
