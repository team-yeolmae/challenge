package org.yeolmae.challenge.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "reply")
public class ReplyImage implements Comparable<ReplyImage> {

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Reply reply;

    @Override
    public int compareTo(ReplyImage other) {
        return this.ord - other.ord;
    }

    public void changeReply(Reply reply) {
        this.reply = reply;
    }
}
