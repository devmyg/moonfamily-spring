package com.moon.moonfamily.controller;


import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.UserEntity;
import com.moon.moonfamily.repository.UserRepository;
import com.moon.moonfamily.security.TokenProvider;
import com.moon.moonfamily.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/uploadProfilePicture")
    public ResponseDto<?> uploadProfilePicture(@RequestHeader("Authorization") String token, @RequestPart("file") MultipartFile file) {
        ResponseDto<?> result = userService.uploadProfilePicture(token, file);
        return result;
    }
}
