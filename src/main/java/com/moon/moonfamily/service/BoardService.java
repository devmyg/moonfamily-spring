package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.BoardListDto;
import com.moon.moonfamily.dto.BoardListResponseDto;
import com.moon.moonfamily.dto.BoardWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.repository.BoardRepository;
import com.moon.moonfamily.repository.PopularSearchRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            //boardList = boardRepository.findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc();
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<BoardListResponseDto> getBoardList(int page, int size) {
        BoardListResponseDto boardListResponseDto;
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "boardWriteDate");
            Page<BoardListDto> boardPage = boardRepository.findByOrderByBoardWriteDateDesc(pageable);
            List<BoardListDto> boardList = boardPage.getContent();
            int totalPages = boardPage.getTotalPages();
            boardListResponseDto = new BoardListResponseDto(boardList, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", boardListResponseDto);
    }

    public ResponseDto<List<PopularSearchEntity>> getPopularsearchList() {
        List<PopularSearchEntity> popularSearchList = new ArrayList<PopularSearchEntity>();
        try {
            popularSearchList = popularSearchRepository.findTop10ByOrderByPopularSearchCountDesc();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", popularSearchList);
    }

    public ResponseDto<?> write(BoardWriteDto dto, String token) {
        // 토큰 검증
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        BoardEntity boardEntity = new BoardEntity(dto);
        boardEntity.setBoardWriterId(userId);

        // 작성 날짜 설정
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDate = format.format(date);
        boardEntity.setBoardWriteDate(writeDate);

        try {
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", boardEntity);
    }

    public ResponseDto<BoardEntity> getBoard(int boardNumber) {
        Optional<BoardEntity> boardEntityOpt = boardRepository.findById(boardNumber);
        if (boardEntityOpt.isEmpty()) return ResponseDto.setFailed("게시글이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOpt.get();
        return ResponseDto.setSuccess("Success", boardEntity);
    }

    @Transactional
    public ResponseDto<?> increaseBoardClickCount(int boardNumber) {
        try {
            boardRepository.increaseBoardClickCount(boardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("Success", null);
    }

    @Transactional
    public ResponseDto<?> updateBoard(int boardNumber, BoardWriteDto dto, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardNumber);
        if (boardEntityOptional == null) return ResponseDto.setFailed("게시글이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOptional.get();
        if (!boardEntity.getBoardWriterId().equals(userId)) return ResponseDto.setFailed("작성자만 수정이 가능합니다.");

        boardEntity.setBoardTitle(dto.getBoardTitle());
        boardEntity.setBoardContent(dto.getBoardContent());

        try {
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        return ResponseDto.setSuccess("Success", boardEntity);
    }
}
