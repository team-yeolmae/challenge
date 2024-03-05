package org.yeolmae.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yeolmae.challenge.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
