package org.yeolmae.challenge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    @NotEmpty
    @Column(nullable = false)
    private String replyText;

    @NotEmpty
    @Column(nullable = false)
    private String replyer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;

    public void changeReply(String text) {
        this.replyText = text;
    }

}