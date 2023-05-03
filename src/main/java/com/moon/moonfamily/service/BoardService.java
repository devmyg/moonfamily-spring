package com.moon.moonfamily.service;

import antlr.Token;
import com.moon.moonfamily.dto.BoardWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.repository.BoardRepository;
import com.moon.moonfamily.repository.PopularSearchRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    PopularSearchRepository popularSearchRepository;

    @Autowired
    TokenProvider tokenProvider;

    public ResponseDto<List<BoardEntity>> getTop3() {
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        Date date = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
        try {
            boardList = boardRepository.findTop3ByBoardWriteDateAfterOrderByBoardLikesCountDesc(date);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<BoardEntity>> getList() {
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        try {
            boardList = boardRepository.findByOrderByBoardWriteDateDesc();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<PopularSearchEntity>> getPopularsearchList() {
        List<PopularSearchEntity> popularSearchList = new ArrayList<PopularSearchEntity>();
        try {
            popularSearchList = popularSearchRepository.findTop10ByOrderByPopularSearchCountDesc();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", popularSearchList);
    }

    public ResponseDto<?> write(BoardWriteDto dto, String token) {
        String boardWriterId = dto.getBoardWriterId();

        // 토큰 검증
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        // 작성자 검증
        if (!boardWriterId.equals(userId)) return ResponseDto.setFailed("잘못된 접근");

        BoardEntity boardEntity = new BoardEntity(dto);

        // 작성 날짜 설정
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDate = format.format(date);
        boardEntity.setBoardWriteDate(writeDate);

        try {
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("Data Base Error!");
        }
        return ResponseDto.setSuccess("Success", boardEntity);
    }
}
