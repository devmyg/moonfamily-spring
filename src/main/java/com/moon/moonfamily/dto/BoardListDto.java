package com.moon.moonfamily.dto;

import com.moon.moonfamily.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {
    private int boardNumber;
    private String boardTitle;
    private String boardWriterId;
    private String boardWriteDate;
    private int boardClickCount;
    private int boardLikeCount;
    private int boardCommentCount;

    public BoardListDto(BoardEntity board) {
        this.boardNumber = board.getBoardNumber();
        this.boardTitle = board.getBoardTitle();
        this.boardWriterId = board.getBoardWriterId();
        this.boardWriteDate = board.getBoardWriteDate().toString();
        this.boardClickCount = board.getBoardClickCount();
        this.boardLikeCount = board.getBoardLikeCount();
        this.boardCommentCount = board.getBoardCommentCount();
    }
}

