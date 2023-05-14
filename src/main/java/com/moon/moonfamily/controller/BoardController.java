package com.moon.moonfamily.controller;

import com.moon.moonfamily.dto.BoardListDto;
import com.moon.moonfamily.dto.BoardListResponseDto;
import com.moon.moonfamily.dto.BoardWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping("/write")
    public ResponseDto<?> write(@RequestBody BoardWriteDto boardWriteDto, @RequestHeader(name = "Authorization") String token) {
        ResponseDto<?> result = boardService.write(boardWriteDto, token);
        return result;
    }

    @PatchMapping("/{boardNumber}")
    public ResponseDto<?> updateBoard(@PathVariable int boardNumber, @RequestBody BoardWriteDto dto, @RequestHeader(name = "Authorization") String token) {
        return boardService.updateBoard(boardNumber, dto, token);
    }

    @GetMapping("/{boardNumber}")
    public ResponseDto<?> getBoard(@PathVariable int boardNumber) {
        return boardService.getBoard(boardNumber);
    }

    @DeleteMapping("/{boardNumber}")
    public ResponseDto<?> deleteBoard(@PathVariable int boardNumber, @RequestHeader(name = "Authorization") String token) {
        return boardService.deleteBoard(boardNumber, token);
    }

    @GetMapping("/list")
    public ResponseDto<BoardListResponseDto> getBoardList(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestHeader(name = "Authorization") String token) {
        return boardService.getBoardList(page, size, token);
    }

    @PutMapping("/{boardNumber}/click")
    public ResponseDto<?> increaseBoardLikeCount(@PathVariable int boardNumber) {
        return boardService.increaseBoardLikeCount(boardNumber);
    }

    @PutMapping("/{boardNumber}/like")
    public ResponseDto<?> increaseBoardClickCount(@PathVariable int boardNumber) {
        return boardService.increaseBoardClickCount(boardNumber);
    }

    @GetMapping("/search")
    public ResponseDto<BoardListResponseDto> searchBoard(@RequestParam(value = "value") String value,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestHeader(value = "Authorization") String token) {
        return boardService.search(value, page, size, token);
    }

    @GetMapping("/top3")
    public ResponseDto<List<BoardListDto>> getTop3ByViewsInLast7Days() {
        return boardService.getTop3();
    }
}
