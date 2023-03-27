package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.repository.BoardRepository;
import com.moon.moonfamily.repository.PopularSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
