package com.example.board;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Generated Key: " + base64Key);
    }

}