package com.moon.moonfamily.repository;

import com.moon.moonfamily.entity.BoardEntity;
import com.moon.moonfamily.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByBoard_boardNumberOrderByCommentWriteDateDesc(int boardNumber);

    void deleteAllByBoard(BoardEntity boardEntity);
}