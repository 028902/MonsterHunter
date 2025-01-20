package com.example.board.controller;

import com.example.board.JwtTokenProvider;
import com.example.board.dto.PassCheckDto;
import com.example.board.dto.TokenCheckDto;
import com.example.board.dto.UpdateInfoDto;
import com.example.board.entity.LoginRequest;
import com.example.board.entity.User;
import com.example.board.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /*회원가입*/
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/loginRequest")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<String, Object>> loginRequest(@RequestBody LoginRequest loginRequest) {
        // 로그인 요청을 서비스에 전달하고, 성공하면 액세스 토큰과 리프레시 토큰 반환
        Map<String, String> tokens = userService.loginRequest(loginRequest); // 액세스 토큰과 리프레시 토큰을 반환
        String userId = jwtTokenProvider.getUserIdFromToken(tokens.get("accessToken"));
        User user = userService.getUserInfo(userId);
        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok().body(Map.of(
                "id", user.getId(),
                "nickname", user.getNickname(),
                "accessToken", tokens.get("accessToken"),
                "refreshToken", tokens.get("refreshToken")
        ));
    }
    @Operation(
            summary = "회원정보 조회"
    )
    @GetMapping("/user/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<User> getUserInfo(@PathVariable String id) {
        User user = userService.getUserInfo(id);
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "회원정보 수정"
    )
    @PutMapping("/user/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void updateUserInfo(@PathVariable String id, @RequestBody UpdateInfoDto updateInfoDto) {
        userService.updateUserInfo(id, updateInfoDto);
    }

    @Operation(
            summary = "비밀번호 체크",
            description = "회원정보 수정 전 비밀번호 체크"
    )
    @PostMapping("/user/{id}/passCheck")
    @CrossOrigin(origins = "http://localhost:3000")
    public void passwordCheck(
            @PathVariable String id,
            @RequestBody PassCheckDto passCheckDto) {
        userService.passwordCheck(id, passCheckDto.getPassword());
    }


    @Operation(
            summary = "회원 탈퇴",
            description = "STATUS를 탈퇴상태(D)로 변경"
    )
    @PatchMapping("/withdraw/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteUser(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        userService.deleteUser(user);
    }

    @PostMapping("/validate-token")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody TokenCheckDto tokens) {
        String accessToken = tokens.getAccessToken();
        String refreshToken = tokens.getRefreshToken();

        // 액세스 토큰 유효성 검사
        if (jwtTokenProvider.validateToken(accessToken)) {
            // 액세스 토큰이 유효한 경우
            String userId = jwtTokenProvider.getUserIdFromToken(accessToken);
            User user = userService.getUserInfo(userId);

            return ResponseEntity.ok().body(Map.of(
                    "id", user.getId(),
                    "nickname", user.getNickname(),
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));
        } else {
            //리프레시 토큰이 유효한 경우
            String userId = jwtTokenProvider.getUserIdFromToken(refreshToken); // 새로운 액세스 토큰 생성
            User user = userService.getUserInfo(userId);

            String newAccessToken = jwtTokenProvider.generateAccessToken(userId);
            return ResponseEntity.ok().body(Map.of(
                    "id", user.getId(),
                    "nickname", user.getNickname(),
                    "accessToken", newAccessToken
            ));
        }
    }

    @Operation(
            summary = "비밀번호 찾기",
            description = "아이디를 입력하면 DB에서 해당 아이디와 연결된 이메일로 임시 비밀번호 발송."
    )
    @PostMapping("/password/reset/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void resetPassword(@PathVariable String id) {
        userService.updatePassword(id);
    }

    @GetMapping("/check-id")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> checkId(@RequestParam String id) {
        boolean isDuplicated = userService.isIdDuplicated(id);
        return ResponseEntity.ok(!isDuplicated);
    }

    @GetMapping("/check-nickname")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean isDuplicated = userService.isNicknameDuplicated(nickname);
        return ResponseEntity.ok(!isDuplicated);
    }

    @GetMapping("/check-email")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean isDuplicated = userService.isEmailDuplicated(email);
        return ResponseEntity.ok(!isDuplicated);
    }
}
