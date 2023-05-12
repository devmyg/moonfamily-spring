package com.moon.moonfamily.entity;

import com.moon.moonfamily.dto.BoardWriteDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_writer_id", nullable = false)
    private UserEntity user;

    private String boardWriteDate;
    private int boardClickCount;
    private int boardLikeCount;
    private int boardCommentCount;

    public BoardEntity(BoardWriteDto dto) {
        this.boardTitle = dto.getBoardTitle();
        this.boardContent = dto.getBoardContent();
        this.boardImage = dto.getBoardImage();
    }
}
