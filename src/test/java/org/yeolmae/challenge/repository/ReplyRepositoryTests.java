package org.yeolmae.challenge.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.ReplyImage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;


    @Test
    public void testInsertWithImage() {

        Challenge challenge = new Challenge(1, "title", "writer", "content",
                LocalDate.now(),
                LocalDate.of(2024, Month.APRIL, 1),
                LocalDate.of(2024, Month.APRIL, 30),
                null,
                null
                );

        Reply reply = Reply.builder()
                .rno(1)
                .replyText("첨부파일 테스트")
                .replyer("test01")
                .registerDate(LocalDate.now())
                .challenge(challenge)
                .build();

        for(int i=0; i<3; i++) {
            reply.addImage(UUID.randomUUID().toString(), "File" + i + ".jpg");
        }

        replyRepository.save(reply);

    }


    @Test
    public void testReadWithImage() {

        Optional<Reply> result = replyRepository.findById(1);
        Reply reply = result.orElseThrow();

        for(ReplyImage replyImage : reply.getImageSet()) {
            System.out.println(replyImage);
        }
    }

    @Test
    @Transactional
    @Commit
    public void testModifyImage() {

        Optional<Reply> result = replyRepository.findByIdWithImage(1);
        Reply reply = result.orElseThrow();

        reply.clearImage();

        for(int i=0; i<1; i++) {
            reply.addImage(UUID.randomUUID().toString(),"updateFile" + i + ".jpg");
        }

        replyRepository.save(reply);
    }




}
