package com.example.board.controller;

import com.example.board.JwtTokenProvider;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.Admin;
import com.example.board.entity.LoginRequest;
import com.example.board.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> register(@RequestBody Admin admin) {
        adminService.register(admin);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Insert admin successfully");
        response.setData(admin);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/loginRequest")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> loginRequest(@RequestBody LoginRequest loginRequest) {
        // 로그인 요청을 서비스에 전달하고, 성공하면 액세스 토큰과 리프레시 토큰 반환
        Map<String, String> tokens = adminService.loginRequest(loginRequest); // 액세스 토큰과 리프레시 토큰을 반환

        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok().body(Map.of(
                "accessToken", tokens.get("accessToken"),
                "refreshToken", tokens.get("refreshToken")
        ));
    }

    @PostMapping("/admin/refresh-token")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> refreshToken(@RequestBody Map<String, String> tokens) {
        String refreshToken = tokens.get("refreshToken");

        // 리프레시 토큰 유효성 검사
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            // 리프레시 토큰에서 사용자 ID 추출
            String userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);
            // 새로운 액세스 토큰 생성
            String newAccessToken = jwtTokenProvider.generateAccessToken(userId);
            ResponseDto response = new ResponseDto();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Refresh token valid, new access token generated");
            response.setData(Map.of("accessToken", newAccessToken));
            return ResponseEntity.ok(response);
        } else {
            // 리프레시 토큰이 유효하지 않거나 만료된 경우
            ResponseDto response = new ResponseDto();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid or expired refresh token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
