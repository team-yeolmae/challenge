package org.yeolmae.challenge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Builder
@Getter
@Setter
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

    @OneToMany(mappedBy = "reply", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private Set<ReplyImage> imageSet = new HashSet<>();

    public void addReplyImage(String uuid, String fileName) {

        ReplyImage replyImage = ReplyImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .reply(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(replyImage);
    }

    public void clearReplyImages() {
        imageSet.forEach(replyImage -> replyImage.changeReply(null));
        this.imageSet.clear();
    }


}