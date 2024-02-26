package org.yeolmae.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yeolmae.challenge.domain.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
}
