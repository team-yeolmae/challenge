package org.yeolmae.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yeolmae.challenge.domain.Member;

public interface MemberJoinRepository extends JpaRepository<Member, Integer> {
}
