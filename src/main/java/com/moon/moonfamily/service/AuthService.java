package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.dto.SignInDto;
import com.moon.moonfamily.dto.SignInResponseDto;
import com.moon.moonfamily.dto.SignUpDto;
import com.moon.moonfamily.entity.UserEntity;
import com.moon.moonfamily.repository.UserRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ResponseDto<?> signUp(SignUpDto dto) {
        String userId = dto.getUserId();
        String userPassword = dto.getUserPassword();

        // ID 중복 확인
        try {
            if (userRepository.existsById(userId)) return ResponseDto.setFailed("Existed ID!");
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error!");
        }

        // UserEntity 생성
        UserEntity userEntity = new UserEntity(dto);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userPassword);
        userEntity.setUserPassword(encodedPassword);

        // UserRepository를 이용해 데이터베이스에 Entity 저장
        try {
            userRepository.save(userEntity);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error!");
        }

        return ResponseDto.setSuccess("Sign Up Success", null);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
        String userId = dto.getUserId();
        String userPassword = dto.getUserPassword();

        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.setFailed("로그인 실패(잘못된 아이디)");
            if (!passwordEncoder.matches(userPassword, userEntity.getUserPassword()))
                return ResponseDto.setFailed("로그인 실패(잘못된 패스워드)");
        } catch (Exception error) {
            return ResponseDto.setFailed("데이터베이스 에러");
        }

        userEntity.setUserPassword("");

        String token = tokenProvider.create(userId);
        int exprTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
        return ResponseDto.setSuccess("로그인 성공", signInResponseDto);
    }
}
