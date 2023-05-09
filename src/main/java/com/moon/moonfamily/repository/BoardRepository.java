package com.moon.moonfamily.repository;

import com.moon.moonfamily.dto.BoardListDto;
import com.moon.moonfamily.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BoardRepository extends PagingAndSortingRepository<BoardEntity, Integer> {

    public Page<BoardListDto> findByOrderByBoardWriteDateDesc(Pageable pageable);

    public Page<BoardEntity> findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(Date boardWriteDate, Pageable pageable);

    @Modifying
    @Query("UPDATE Board b SET b.boardClickCount = b.boardClickCount + 1 WHERE b.boardNumber = :boardNumber")
    public void increaseBoardClickCount(@Param("boardNumber") int boardNumber);
}