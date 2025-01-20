package com.example.board.controller;

import com.example.board.JwtTokenProvider;
import com.example.board.dto.ResponseDto;
import com.example.board.dto.TokenCheckDto;
import com.example.board.entity.Admin;
import com.example.board.entity.LoginRequest;
import com.example.board.entity.User;
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
    public ResponseEntity<Admin> register(@RequestBody Admin admin) {
        adminService.register(admin);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/admin/loginRequest")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> loginRequest(@RequestBody LoginRequest loginRequest) {
        // 로그인 요청을 서비스에 전달하고, 성공하면 액세스 토큰과 리프레시 토큰 반환
        Map<String, String> tokens = adminService.loginRequest(loginRequest); // 액세스 토큰과 리프레시 토큰을 반환
        String userId = jwtTokenProvider.getUserIdFromToken(tokens.get("accessToken"));
        Admin admin = adminService.getAdminInfo(userId);

        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok().body(Map.of(
                "id", admin.getId(),
                "accessToken", tokens.get("accessToken"),
                "refreshToken", tokens.get("refreshToken")
        ));
    }

    @PostMapping("/admin/validate-token")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody TokenCheckDto tokens) {
        String accessToken = tokens.getAccessToken();
        String refreshToken = tokens.getRefreshToken();

        // 액세스 토큰 유효성 검사
        if (jwtTokenProvider.validateToken(accessToken)) {
            // 액세스 토큰이 유효한 경우
            String userId = jwtTokenProvider.getUserIdFromToken(accessToken);
            Admin admin = adminService.getAdminInfo(userId);

            return ResponseEntity.ok().body(Map.of(
                    "id", admin.getId(),
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));
        } else {
            //리프레시 토큰이 유효한 경우
            String userId = jwtTokenProvider.getUserIdFromToken(refreshToken); // 새로운 액세스 토큰 생성
            Admin admin = adminService.getAdminInfo(userId);

            String newAccessToken = jwtTokenProvider.generateAccessToken(userId);
            return ResponseEntity.ok().body(Map.of(
                    "id", admin.getId(),
                    "accessToken", newAccessToken
            ));
        }
    }

}
