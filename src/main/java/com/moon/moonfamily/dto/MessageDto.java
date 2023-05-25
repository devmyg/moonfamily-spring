package com.moon.moonfamily.dto;

import com.moon.moonfamily.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String userId;
    private String userName;
    private String userProfile;
    private String content;

    public MessageDto(UserEntity userEntity, String content) {
        this.userId = userEntity.getUserId();
        this.userName = userEntity.getUserName();
        this.userProfile = userEntity.getUserProfile();
        this.content = content;
    }
}