package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.MessageDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.UserEntity;
import com.moon.moonfamily.repository.UserRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    public ResponseDto<MessageDto> send(String content, String token) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");

        UserEntity userEntity = userRepository.findByUserId(userId);
        MessageDto messageDto = new MessageDto(userEntity, content);

        return ResponseDto.setSuccess("성공", messageDto);
    }
}
