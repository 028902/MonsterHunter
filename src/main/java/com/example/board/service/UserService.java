package com.example.board.service;

import com.example.board.JwtTokenProvider;
import com.example.board.dto.UpdateInfoDto;
import com.example.board.entity.LoginRequest;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmailService emailService;

    public void register(User user) {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);
    }

    public Map<String, String> loginRequest(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByIdAndStatus(loginRequest.getId(), "A");
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    public void deleteUser(User deleteUser) {
        Optional<User> optionalUser = userRepository.findByIdAndStatus(deleteUser.getId(), "A");
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setStatus("D");
        userRepository.save(user);
    }

    public User getUserInfo(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void updateUserInfo(String userId, UpdateInfoDto updateInfoDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (updateInfoDto.getPassword() != null && !updateInfoDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateInfoDto.getPassword()));
        }
        if (updateInfoDto.getNickname() != null && !updateInfoDto.getNickname().isEmpty()) {
            user.setNickname(updateInfoDto.getNickname());
        }
        if (updateInfoDto.getEmail() != null && !updateInfoDto.getEmail().isEmpty()) {
            user.setEmail(updateInfoDto.getEmail());
        }
        if (updateInfoDto.getWeapon() != null && !updateInfoDto.getWeapon().isEmpty()) {
            user.setWeapon(updateInfoDto.getWeapon());
        }
        userRepository.save(user);
    }

    @Transactional
    public boolean passwordCheck(String userId, String currentPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }
        return true;
    }

    @Transactional
    public void updatePassword(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String temporaryPassword = generateTemporaryPassword();
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(temporaryPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), temporaryPassword);
    }

    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    public boolean isIdDuplicated(String id) {
        return userRepository.existsById(id);
    }

    public boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean isEmailDuplicated(String email) {
        return userRepository.existsByEmail(email);
    }
}
