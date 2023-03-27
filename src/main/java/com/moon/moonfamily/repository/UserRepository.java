package com.moon.moonfamily.repository;

import com.moon.moonfamily.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    public boolean existsByUserIdAndUserPassword(String userEmail, String userPassword);

    public UserEntity findByUserId(String userId);
}
