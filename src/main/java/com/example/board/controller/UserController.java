package com.example.board.controller;

import com.example.board.JwtTokenProvider;
import com.example.board.entity.LoginRequest;
import com.example.board.entity.User;
import com.example.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /*회원가입*/
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/loginRequest")
    public ResponseEntity<?> loginRequest(@RequestBody LoginRequest loginRequest) {
        try {
            // 로그인 요청을 서비스에 전달하고, 성공하면 액세스 토큰과 리프레시 토큰 반환
            Map<String, String> tokens = userService.loginRequest(loginRequest); // 액세스 토큰과 리프레시 토큰을 반환

            // JWT 토큰을 클라이언트에 반환
            return ResponseEntity.ok().body(Map.of(
                    "message", "Login successful",
                    "accessToken", tokens.get("accessToken"),
                    "refreshToken", tokens.get("refreshToken")
            ));
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 에러 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid credentials"
            ));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> tokens) {
        String refreshToken = tokens.get("refreshToken");

        // 리프레시 토큰 유효성 검사
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            // 리프레시 토큰에서 사용자 ID 추출
            String userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);

            // 새로운 액세스 토큰 생성
            String newAccessToken = jwtTokenProvider.generateAccessToken(userId);

            return ResponseEntity.ok().body(Map.of(
                    "accessToken", newAccessToken
            ));
        } else {
            // 리프레시 토큰이 유효하지 않거나 만료된 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid or expired refresh token"
            ));
        }
    }
}
