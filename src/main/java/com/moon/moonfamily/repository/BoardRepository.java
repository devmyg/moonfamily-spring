package com.moon.moonfamily.repository;

import com.moon.moonfamily.dto.BoardListDto;
import com.moon.moonfamily.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends PagingAndSortingRepository<BoardEntity, Integer> {

    @Query("SELECT b FROM Board b WHERE b.boardWriteDate > :currentDate ORDER BY b.boardClickCount DESC")
    List<BoardListDto> findTop3ByBoardWriteDateAfterOrderByBoardClickCountDesc(@Param("currentDate") @Temporal(TemporalType.TIMESTAMP) Date currentDate);

    List<BoardListDto> findTop3ByOrderByBoardClickCountDesc();

    Page<BoardListDto> findByOrderByBoardWriteDateDesc(Pageable pageable);

    Optional<BoardEntity> findByBoardNumber(int boardNumber);

    Page<BoardListDto> findByBoardTitleContainingOrBoardContentContainingOrderByBoardWriteDateDesc(String value, String value1, Pageable pageable);
}