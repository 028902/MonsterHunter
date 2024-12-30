package com.example.board.service;

import com.example.board.JwtTokenProvider;
import com.example.board.entity.LoginRequest;
import com.example.board.entity.User;
import com.example.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void register(User user) {
        String password = user.getPassword();
        String hashPassword = passwordEncoder.encode(password);
        user.setPassword(hashPassword);
        userMapper.registration(user);
    }

    public Map<String, String> loginRequest(LoginRequest loginRequest) {
        String password = userMapper.getPasswordById(loginRequest.getId());
        System.out.println(password);

        // 비밀번호 검증
        if (passwordEncoder.matches(loginRequest.getPassword(), password)) {
            // 검증 성공 시 액세스 토큰과 리프레시 토큰 생성
            String accessToken = jwtTokenProvider.generateAccessToken(loginRequest.getId());
            String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getId());

            // 두 토큰을 맵에 담아서 반환
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return tokens;
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
}
