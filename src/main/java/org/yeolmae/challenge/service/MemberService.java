package org.yeolmae.challenge.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.profile.MemberUpdateRequest;
import org.yeolmae.challenge.repository.MemberRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//    public MemberUpdateRequest updateForm(String email) {
//        Optional<Member> optionalMember = memberRepository.findMemberByEmail(email);
//        if (optionalMember.isPresent()){
//            return MemberUpdateRequest.toMemberUpdateRequest(optionalMember.get());
//        }else {
//            return null;
//        }
//    }

}
