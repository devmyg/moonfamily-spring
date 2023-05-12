package com.moon.moonfamily.controller;

import com.moon.moonfamily.dto.BoardWriteDto;
import com.moon.moonfamily.dto.CommentWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/write")
    public ResponseDto<?> write(@RequestBody CommentWriteDto dto, @RequestHeader(value = "Authorization") String token) {
      return commentService.write(dto, token);
    }

    @DeleteMapping("/{commentId}")
    public ResponseDto<?> delete(@PathVariable int commentId, @RequestHeader(value = "Authorization") String token) {
        return commentService.delete(commentId, token);
    }

    @GetMapping("/{boardNumber}")
    public ResponseDto<?> getComment(@PathVariable int boardNumber) {
        return commentService.getComment(boardNumber);
    }

    @PatchMapping("/{commentId}")
    public ResponseDto<?> update(@PathVariable int commentId, @RequestBody CommentWriteDto dto, @RequestHeader(name = "Authorization") String token) {
        return commentService.updateComment(commentId, dto, token);
    }
}
