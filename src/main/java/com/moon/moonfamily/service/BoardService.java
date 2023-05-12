package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.BoardListDto;
import com.moon.moonfamily.dto.BoardListResponseDto;
import com.moon.moonfamily.dto.BoardWriteDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.entity.UserEntity;
import com.moon.moonfamily.repository.BoardRepository;
import com.moon.moonfamily.repository.CommentRepository;
import com.moon.moonfamily.repository.PopularSearchRepository;
import com.moon.moonfamily.repository.UserRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PopularSearchRepository popularSearchRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TokenProvider tokenProvider;

    public ResponseDto<List<BoardListDto>> getTop3() {
        List<BoardListDto> boardList = new ArrayList<BoardListDto>();
        try {
            boardList = boardRepository.findTop3ByOrderByBoardClickCountDesc();
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("성공", boardList);
    }

    public ResponseDto<BoardListResponseDto> getBoardList(int page, int size, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

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
        return ResponseDto.setSuccess("성공", boardListResponseDto);
    }

    @Transactional
    public ResponseDto<?> write(BoardWriteDto dto, String token) {
        // 토큰 검증
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        BoardEntity boardEntity = new BoardEntity(dto);
        UserEntity user = userRepository.findByUserId(userId);
        boardEntity.setUser(user);

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
        return ResponseDto.setSuccess("성공", boardEntity);
    }

    public ResponseDto<BoardEntity> getBoard(int boardNumber) {
        Optional<BoardEntity> boardEntityOpt = boardRepository.findById(boardNumber);
        if (boardEntityOpt.isEmpty()) return ResponseDto.setFailed("게시글이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOpt.get();
        return ResponseDto.setSuccess("성공", boardEntity);
    }

    @Transactional
    public ResponseDto<?> increaseBoardLikeCount(int boardNumber) {
        Optional<BoardEntity> boardEntityOpt = boardRepository.findById(boardNumber);
        if(!boardEntityOpt.isPresent()) return ResponseDto.setFailed("게시물이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOpt.get();
        boardEntity.setBoardLikeCount(boardEntity.getBoardLikeCount() + 1);
        try {
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("성공", null);
    }

    @Transactional
    public ResponseDto<?> increaseBoardClickCount(int boardNumber) {
        Optional<BoardEntity> boardEntityOpt = boardRepository.findById(boardNumber);
        if(!boardEntityOpt.isPresent()) return ResponseDto.setFailed("게시물이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOpt.get();
        boardEntity.setBoardClickCount(boardEntity.getBoardClickCount() + 1);
        try {
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("성공", null);
    }

    @Transactional
    public ResponseDto<?> updateBoard(int boardNumber, BoardWriteDto dto, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardNumber);
        if (boardEntityOptional == null) return ResponseDto.setFailed("게시글이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOptional.get();
        if (!boardEntity.getUser().getUserId().equals(userId)) return ResponseDto.setFailed("작성자만 수정이 가능합니다.");

        boardEntity.setBoardTitle(dto.getBoardTitle());
        boardEntity.setBoardContent(dto.getBoardContent());

        try {
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        return ResponseDto.setSuccess("성공", boardEntity);
    }

    @Transactional
    public ResponseDto<?> deleteBoard(int boardNumber, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardNumber);
        if (boardEntityOptional == null) return ResponseDto.setFailed("게시글이 존재하지 않습니다.");

        BoardEntity boardEntity = boardEntityOptional.get();
        if (!boardEntity.getUser().getUserId().equals(userId)) return ResponseDto.setFailed("작성자만 삭제가 가능합니다.");

        try {
            commentRepository.deleteAllByBoard(boardEntity);
            boardRepository.deleteById(boardEntity.getBoardNumber());
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        return ResponseDto.setSuccess("성공", null);
    }

    @Transactional
    public ResponseDto<BoardListResponseDto> search(String value, int page, int size, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        BoardListResponseDto boardListResponseDto;
        PopularSearchEntity popularSearchEntity = popularSearchRepository.findByPopularTerm(value);
        if(popularSearchEntity == null) popularSearchEntity = new PopularSearchEntity(value);
        else popularSearchEntity.setPopularSearchCount(popularSearchEntity.getPopularSearchCount() + 1);
        try {
            popularSearchRepository.save(popularSearchEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("검색 데이터베이스 에러");
        }

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "boardWriteDate");
            Page<BoardListDto> boardPage = boardRepository.findByBoardTitleContainingOrBoardContentContainingOrderByBoardWriteDateDesc(value, value, pageable);
            List<BoardListDto> boardList = boardPage.getContent();
            int totalPages = boardPage.getTotalPages();
            boardListResponseDto = new BoardListResponseDto(boardList, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("성공", boardListResponseDto);
    }
}
