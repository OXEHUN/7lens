package jpabasic.project_7lans.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Whisper {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member writer;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Relation relation;

    private String content;

    private LocalDateTime createDate = LocalDateTime.now();

    private boolean readStatus = false;

    public void changeReadStatusApprove(Member reader){
        if(!writer.equals(reader)) readStatus = true;
    }

    @Builder
    public static Whisper createWhisper(
            Member writer,
            String content
    ){
        return Whisper.builder()
                .writer(writer)
                .content(content)
                .build();
    }


}
