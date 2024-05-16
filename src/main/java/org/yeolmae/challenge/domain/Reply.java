package org.yeolmae.challenge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "challenge")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challengeId")
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(nullable = false)
    private String replyText;

    @Column(nullable = false)
    private String replyer;

    @CreationTimestamp
    private LocalDateTime registerDateTime;

    @UpdateTimestamp
    private LocalDateTime modifyDateTime;

    public void changeReply(String replyText) {
        this.replyText = replyText;
        this.modifyDateTime = LocalDateTime.now();
    }

}