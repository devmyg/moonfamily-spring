package com.moon.moonfamily.controller;

import com.moon.moonfamily.dto.BoardWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/list")
    public ResponseDto<List<BoardEntity>> getList() {
        return boardService.getList();
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
}
