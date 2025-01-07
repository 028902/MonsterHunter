package com.example.board.controller;

import com.example.board.JwtTokenProvider;
import com.example.board.entity.Admin;
import com.example.board.entity.LoginRequest;
import com.example.board.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /*회원가입*/
    @PostMapping("/admin/register")
    public ResponseEntity<String> register(@RequestBody Admin admin) {
        adminService.register(admin);
        return ResponseEntity.ok("Registration Success");
    }

    @PostMapping("/admin/loginRequest")
    public ResponseEntity<?> loginRequest(@RequestBody LoginRequest loginRequest) {
        // 로그인 요청을 서비스에 전달하고, 성공하면 액세스 토큰과 리프레시 토큰 반환
        Map<String, String> tokens = adminService.loginRequest(loginRequest); // 액세스 토큰과 리프레시 토큰을 반환

        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok().body(Map.of(
                "message", "Login successful",
                "accessToken", tokens.get("accessToken"),
                "refreshToken", tokens.get("refreshToken")
        ));
    }

    @PostMapping("/admin/refresh-token")
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
