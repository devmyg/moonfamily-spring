package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.CommentWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.CommentEntity;
import com.moon.moonfamily.entity.UserEntity;
import com.moon.moonfamily.repository.BoardRepository;
import com.moon.moonfamily.repository.CommentRepository;
import com.moon.moonfamily.repository.UserRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> write(CommentWriteDto dto, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        UserEntity userEntity = userRepository.findByUserId(userId);
        Optional<BoardEntity> boardEntityOptional = boardRepository.findByBoardNumber(dto.getBoardNumber());

        if (!boardEntityOptional.isPresent()) return ResponseDto.setFailed("게시물이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOptional.get();
        CommentEntity commentEntity = new CommentEntity(dto);
        commentEntity.setUser(userEntity);
        commentEntity.setBoard(boardEntity);

        boardEntity.setBoardCommentCount(boardEntity.getBoardCommentCount() + 1);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDate = format.format(date);
        commentEntity.setCommentWriteDate(writeDate);

        try {
            commentRepository.save(commentEntity);
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("성공", commentEntity);
    }

    @Transactional
    public ResponseDto<?> delete(int commentId, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        Optional<CommentEntity> commentEntityOptional = commentRepository.findById(commentId);
        if (!commentEntityOptional.isPresent()) return ResponseDto.setFailed("댓글이 존재하지 않습니다.");

        CommentEntity commentEntity = commentEntityOptional.get();
        if (!commentEntity.getUser().getUserId().equals(userId)) return ResponseDto.setFailed("작성자만 댓글을 삭제할 수 있습니다.");

        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("성공", null);
    }

    public ResponseDto<?> getComment(int boardNumber) {
        List<CommentEntity> commentList = commentRepository.findByBoard_boardNumberOrderByCommentWriteDateDesc(boardNumber);
        return ResponseDto.setSuccess("성공", commentList);
    }

    @Transactional
    public ResponseDto<?> updateComment(int commentId, CommentWriteDto dto, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        Optional<CommentEntity> commentEntityOptional = commentRepository.findById(commentId);
        if (commentEntityOptional == null) return ResponseDto.setFailed("댓글이 존재하지 않습니다.");

        CommentEntity commentEntity = commentEntityOptional.get();
        if (!commentEntity.getUser().getUserId().equals(userId)) return ResponseDto.setFailed("작성자만 수정이 가능합니다.");

        commentEntity.setCommentContent(dto.getCommentContent());

        try {
            commentRepository.save(commentEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        return ResponseDto.setSuccess("성공", null);
    }
}
