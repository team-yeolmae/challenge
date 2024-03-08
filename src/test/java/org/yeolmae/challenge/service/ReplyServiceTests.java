package org.yeolmae.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.ReplyRepository;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReplyService replyService;

//    @Test
//    public void testRegisterWithImages() {
//
//
//        Optional<Reply> reply = replyRepository.findByIdWithImage(1);
//
//        if(reply != null) {
//            for(int i=0; i<; i++) {
//
//            }
//        }
//
//
//
//    }
//
//
//    @Test
//    public void testListWithAll() {
//
//        Page<ReadReplyResponse> replyPage =
//    }

}
