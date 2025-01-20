package com.example.board.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 리소스를 찾을 수 없는 경우 (조회/수정/삭제 시)
    @ExceptionHandler({NoSuchElementException.class, EntityNotFoundException.class})
    public void handleNotFoundException(Exception e, HttpServletResponse response) {
        log.error("Resource not found: ", e);
        response.setStatus(HttpStatus.NOT_FOUND.value()); // 404
    }

    // 잘못된 입력값 (생성/수정 시)
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public void handleBadRequest(Exception e, HttpServletResponse response) {
        log.error("Invalid request: ", e);
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
    }

    // 데이터 무결성 위반 (중복 키 등)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleDataIntegrityViolation(DataIntegrityViolationException e, HttpServletResponse response) {
        log.error("Data integrity violation: ", e);
        response.setStatus(HttpStatus.CONFLICT.value()); // 409
    }

    // 권한 없음
    @ExceptionHandler({AccessDeniedException.class, SecurityException.class})
    public void handleAccessDenied(Exception e, HttpServletResponse response) {
        log.error("Access denied: ", e);
        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403 상태 코드
    }

    // 잘못된 요청 형식 (JSON 파싱 에러 등)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleMessageNotReadable(HttpMessageNotReadableException e, HttpServletResponse response) {
        log.error("Message not readable: ", e);
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
    }

    // 데이터베이스 관련 예외
    @ExceptionHandler(DataAccessException.class)
    public void handleDataAccessException(DataAccessException e, HttpServletResponse response) {
        log.error("Database error: ", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
    }

    // 트랜잭션 관련 예외
    @ExceptionHandler(TransactionException.class)
    public void handleTransactionException(TransactionException e, HttpServletResponse response) {
        log.error("Transaction error: ", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public void handleUsernameNotFoundException(UsernameNotFoundException e, HttpServletResponse response) {
        log.error("User not found: ", e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
    }

    // 메일 전송 실패
    @ExceptionHandler(org.springframework.mail.MailSendException.class)
    public void handleMailSendException(org.springframework.mail.MailSendException e, HttpServletResponse response) {
        log.error("Failed to send email: ", e);
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
    }

    // 메일 전송 실패
    @ExceptionHandler(jakarta.mail.SendFailedException.class)
    public void handleSendFailedException(jakarta.mail.SendFailedException e, HttpServletResponse response) {
        log.error("Email sending failed: ", e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    // 비밀번호 다름
    @ExceptionHandler(BadCredentialsException.class)
    public void badCredentialsException(BadCredentialsException e, HttpServletResponse response) {
        log.error("Current password is incorrect: ", e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
    }

    // 토큰 만료
    @ExceptionHandler(ExpiredJwtException.class)
    public void expiredJwtException(ExpiredJwtException e, HttpServletResponse response) {
        log.error("Expired JWT token: ", e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
    }

    // 기타 모든 예외
    @ExceptionHandler(Exception.class)
    public void handleGeneralError(Exception e, HttpServletResponse response) {
        log.error("Unexpected error: ", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
    }
}
