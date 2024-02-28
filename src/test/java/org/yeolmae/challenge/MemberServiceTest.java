package org.yeolmae.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.MemberJoinDTO;
import org.yeolmae.challenge.repository.MemberRepository;
import org.yeolmae.challenge.service.MemberService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, bCryptPasswordEncoder);
    }

    @Test
    @DisplayName("실제 저장 로직이 기대하는대로 저장되는지 확인")
    void whenSaveMember_thenReturnsMemberId() {
        // Given
        MemberJoinDTO request = new MemberJoinDTO("user@example.com", "password", "nickname");
        Member member = Member.builder()
                .email(request.getEmail())
                .pw(bCryptPasswordEncoder.encode(request.getPw()))
                .nickname(request.getNickname())
                .build();
        member.setId(1); // 가정한 멤버 ID 설정

        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // When
        int savedMemberId = memberService.save(request);

        // Then
        assertThat(savedMemberId).isEqualTo(1);
        // Mockito의 verify 메서드는 특정 메서드(여기서는 save) 호출이 일어났는지를 검증할 때 사용
        verify(memberRepository).save(any(Member.class)); // 저장 메소드 호출 검증
//        verify(bCryptPasswordEncoder).encode(anyString()); // 패스워드 인코딩 호출 검증
    }

//    @Test
//    @DisplayName("회원정보 입력 테스트")
//    void saveMemberTest() {
//
//        MemberJoinRequest request = new MemberJoinRequest("test@example.com", "password", "nickname");
//
//        Member member = Member.builder()
//                .email(request.getEmail())
//                .pw(bCryptPasswordEncoder.encode(request.getPw()))
//                .nickname(request.getNickname())
//                .build();
//
//        // save 메서드가 호출될 때 실행할 동작
//        // InvocationOnMock 객체를 매개변수로 받아 특정 로직을 수행한 후 결과를 반환
//        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
//            // getArgument(0) 메서드를 통해 이 인자를 Member 타입으로 캐스팅하고 savedMember 변수에 저장
//            // Member 객체의 첫 번째 인자 = id = savedMember = setId(1) = 1
//            Member savedMember = invocation.getArgument(0);
//            savedMember.setId(1); // ID 설정
//            return savedMember;
//        });
//
//        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
//
//        // 실행
//        int savedMemberId = memberService.save(request);
//
//        // 검증
//        assertThat(savedMemberId).isEqualTo(1);
//
//        verify(memberRepository).save(any(Member.class));
////        verify(bCryptPasswordEncoder).encode("password");
//
//    }


}
