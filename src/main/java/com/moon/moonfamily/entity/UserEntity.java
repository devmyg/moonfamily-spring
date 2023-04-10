package com.moon.moonfamily.entity;

import com.moon.moonfamily.dto.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "User")
public class UserEntity {
    @Id
    private String userId;
    private String userPassword;
    private String userName;
    private String userPhoneNumber;
    private String userProfile;

    public UserEntity(SignUpDto dto) {
        this.userId = dto.getUserId();
        this.userPassword = dto.getUserPassword();
        this.userName = dto.getUserName();
        this.userPhoneNumber = dto.getUserPhoneNumber();
    }
}
