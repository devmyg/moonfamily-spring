package com.moon.moonfamily.controller;

import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.dto.SignInDto;
import com.moon.moonfamily.dto.SignInResponseDto;
import com.moon.moonfamily.dto.SignUpDto;
import com.moon.moonfamily.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody) {
        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

    @GetMapping("/")
    public String getBoard(@AuthenticationPrincipal String userId) {
        return "로그인된 사용자는 " + userId + "입니다.";
    }
}
