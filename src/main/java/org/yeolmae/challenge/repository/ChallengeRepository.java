package org.yeolmae.challenge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

//    @Query
//    Page<Challenge> searchAll(String[] types, String keyword, Pageable pageable);


}
