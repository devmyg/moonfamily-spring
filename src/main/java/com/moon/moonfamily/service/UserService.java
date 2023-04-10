package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.UserEntity;
import com.moon.moonfamily.repository.UserRepository;
import com.moon.moonfamily.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    public ResponseDto<?> uploadProfilePicture(String token, MultipartFile file) {
        try {
            String userId = tokenProvider.validate(token.substring(7));
            byte[] fileBytes = file.getBytes();
            String extension = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1))
                    .orElse(null);

            Path path = Paths.get("profile-pictures", userId, "profile." + extension);
            Files.createDirectories(path.getParent());
            Files.write(path, fileBytes);

            UserEntity user = userRepository.findByUserId(userId);
            user.setUserProfile(path.toString());
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseDto.setFailed("검증 실패");
        }

        return ResponseDto.setSuccess("Profile Picture Upload Success", null);
    }
}
