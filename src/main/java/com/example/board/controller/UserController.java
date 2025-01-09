package com.example.board.controller;

import com.example.board.JwtTokenProvider;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.LoginRequest;
import com.example.board.entity.User;
import com.example.board.service.EmailService;
import com.example.board.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /*회원가입*/
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> register(@RequestBody User user) {
        userService.register(user);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Insert user successfully");
        response.setData(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginRequest")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> loginRequest(@RequestBody LoginRequest loginRequest) {
        // 로그인 요청을 서비스에 전달하고, 성공하면 액세스 토큰과 리프레시 토큰 반환
        Map<String, String> tokens = userService.loginRequest(loginRequest); // 액세스 토큰과 리프레시 토큰을 반환

        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok().body(Map.of(
                "accessToken", tokens.get("accessToken"),
                "refreshToken", tokens.get("refreshToken")
        ));
    }

    @Operation(
            summary = "회원 탈퇴",
            description = "STATUS를 탈퇴상태(D)로 변경"
    )
    @PatchMapping("/withdraw/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        userService.deleteUser(user);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Delete user successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
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

    @Operation(
            summary = "비밀번호 찾기",
            description = "아이디를 입력하면 DB에서 해당 아이디와 연결된 이메일로 임시 비밀번호 발송."
    )
    @PostMapping("/password/reset/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> resetPassword(@PathVariable String id) {
        userService.updatePassword(id);

        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Temporary password has been sent to your email");
        return ResponseEntity.ok(response);
    }
}
