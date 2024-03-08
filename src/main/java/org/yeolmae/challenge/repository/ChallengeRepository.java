package org.yeolmae.challenge.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yeolmae.challenge.domain.Challenge;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select c from Challenge c where c.id =:id")
    Optional<Challenge> findByIdWithImages(@Param("id") int id);
}