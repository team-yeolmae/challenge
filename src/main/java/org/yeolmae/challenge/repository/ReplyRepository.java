package org.yeolmae.challenge.repository;

import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yeolmae.challenge.domain.Reply;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Query("select r from Reply r where r.challenge.id = :challengeId")
    Page<Reply> listOfReplies(@Param("challengeId") Integer challengeId, Pageable pageable);

    void deleteByChallenge_Id(int id);

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select r from Reply r where r.rno = :rno")
    Optional<Reply> findByIdWithImages();
}