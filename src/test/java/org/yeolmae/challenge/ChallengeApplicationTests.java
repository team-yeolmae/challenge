package org.yeolmae.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.ChallengeImage;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.ReplyRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class ChallengeApplicationTests {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsertWithImages() {

        Challenge challenge = Challenge.builder()
                .title("image test")
                .content("첨부파일 테스트")
                .writer("writer")
                .registerDate(LocalDate.now())
                .startDate(LocalDate.of(2024, 4, 1))
                .endDate(LocalDate.of(2024,4, 30))
                .build();

        for (int i =0; i<3 ; i++){
            challenge.addChallengeImage(UUID.randomUUID().toString(), "file"+i+".jpg");
        }
        challengeRepository.save(challenge);

    }

    @Test
    public void testReadWithImages(){
        Optional<Challenge> result = challengeRepository.findByIdWithImages(1);

        Challenge challenge = result.orElseThrow();

        for (ChallengeImage challengeImage : challenge.getImageSet()){

        }
    }

    @Test
    @Transactional
    @Commit
    public void testModifyImages() {

        Optional<Challenge> result = challengeRepository.findByIdWithImages(1);

        Challenge challenge = result.orElseThrow();

        challenge.clearChallengeImage();

        for (int i =0; i<2 ; i++){
            challenge.addChallengeImage(UUID.randomUUID().toString(), "update_file01"+i+".jpg");
        }
        challengeRepository.save(challenge);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){

        int id = 1;

        replyRepository.deleteByChallenge_Id(id);

        challengeRepository.deleteById(id);

    }

}
