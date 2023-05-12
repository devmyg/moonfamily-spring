package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.UserEntity;
import com.moon.moonfamily.repository.UserRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> uploadProfilePicture(String token, MultipartFile file) {
        String userId = tokenProvider.validate(token);
        if (userId == null) return ResponseDto.setFailed("유효하지 않은 토큰");
        try {
            byte[] fileBytes = file.getBytes();
            String extension = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1))
                    .orElse(null);

            if (!Arrays.asList("jpg", "jpeg", "png").contains(extension.toLowerCase())) {
                return ResponseDto.setFailed("지원하지 않는 확장자입니다.");
            }

            Path path = Paths.get("C:", "Study", "moonfamily", "profile-pictures", userId, "profile." + extension);
            Files.createDirectories(path.getParent());
            Files.write(path, fileBytes);

            UserEntity user = userRepository.findByUserId(userId);
            user.setUserProfile(String.valueOf(Paths.get("profile-pictures", userId, "profile." + extension)));
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseDto.setFailed("검증 실패");
        }

        return ResponseDto.setSuccess("성공", null);
    }
}
