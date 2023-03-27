package com.moon.moonfamily.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String userId;
    private String userPassword;
    private String userPasswordCheck;
    private String userName;
    private String userPhoneNumber;
}
