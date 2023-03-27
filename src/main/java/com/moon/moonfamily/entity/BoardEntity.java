package com.moon.moonfamily.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Board")
@Table(name="Board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardImage;
    private String boardVideo;
    private String boardFile;
    private String boardWriterId;
    private String boardWriterProfile;
    private String boardWriterName;
    private String boardWriteDate;
    private int boardClickCount;
    private int boardLikesCount;
    private int boardCommentCount;
}
