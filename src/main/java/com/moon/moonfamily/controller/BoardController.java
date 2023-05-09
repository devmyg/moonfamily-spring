package com.moon.moonfamily.controller;

import com.moon.moonfamily.dto.BoardListDto;
import com.moon.moonfamily.dto.BoardListResponseDto;
import com.moon.moonfamily.dto.BoardWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/top3")
    public ResponseDto<List<BoardEntity>> getTop3() {
        return boardService.getTop3();
    }

    @GetMapping("/list/{page}")
    public ResponseDto<BoardListResponseDto> getBoardList(@PathVariable int page, @RequestParam(defaultValue = "10") int size) {
        return boardService.getBoardList(page, size);
    }

    @GetMapping("/popularsearchList")
    public ResponseDto<List<PopularSearchEntity>> getPopularsearchList() {
        return boardService.getPopularsearchList();
    }

    @PostMapping("/write")
    public ResponseDto<?> write(@RequestBody BoardWriteDto boardWriteDto, @RequestHeader(name = "Authorization") String token) {
        ResponseDto<?> result = boardService.write(boardWriteDto, token);
        return result;
    }

    @GetMapping("/{boardNumber}")
    public ResponseDto<?> getBoard(@PathVariable int boardNumber) {
        return boardService.getBoard(boardNumber);
    }

    @GetMapping("/{boardNumber}/click")
    public ResponseDto<?> increaseBoardClickCount(@PathVariable int boardNumber) {
        return boardService.increaseBoardClickCount(boardNumber);
    }

    @PatchMapping("/{boardNumber}")
    public ResponseDto<?> updateBoard(@PathVariable int boardNumber, @RequestBody BoardWriteDto dto, @RequestHeader(name = "Authorization") String token) {
        return boardService.updateBoard(boardNumber, dto, token);
    }
}
