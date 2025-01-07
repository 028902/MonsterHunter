package com.example.board.service;

import com.example.board.JwtTokenProvider;
import com.example.board.entity.Admin;
import com.example.board.entity.LoginRequest;
import com.example.board.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void register(Admin admin) {
        String hashPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashPassword);
        adminRepository.save(admin);
    }

    public Map<String, String> loginRequest(LoginRequest loginRequest) {
        Optional<Admin> optionalAdmin = adminRepository.findById(loginRequest.getId());
        Admin admin = optionalAdmin.orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
        if(passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            String accessToken = jwtTokenProvider.generateAccessToken(admin.getId());
            String refreshToken = jwtTokenProvider.generateRefreshToken(admin.getId());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }
}
