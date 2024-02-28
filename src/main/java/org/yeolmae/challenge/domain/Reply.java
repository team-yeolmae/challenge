package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private Challenge challenge;

    @Column(nullable = false)
    private String replyText;

    @Column(nullable = false)
    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }

}