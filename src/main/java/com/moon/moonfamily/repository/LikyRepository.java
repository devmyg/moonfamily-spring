package com.moon.moonfamily.repository;

import com.moon.moonfamily.entity.LikyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikyRepository extends JpaRepository<LikyEntity, Integer> {
}
