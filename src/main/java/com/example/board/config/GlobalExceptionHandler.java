package com.example.board.config;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        log.error("Resource not found: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Resource not found");
    }

    // 잘못된 입력값 (생성/수정 시)
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleBadRequest(Exception e) {
        log.error("Invalid request: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid request parameters");
    }

    // 데이터 무결성 위반 (중복 키 등)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error("Data integrity violation: ", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Data integrity violation");
    }

    // 권한 없음
    @ExceptionHandler({AccessDeniedException.class, SecurityException.class})
    public ResponseEntity<?> handleAccessDenied(Exception e) {
        log.error("Access denied: ", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Access denied");
    }

    // 잘못된 요청 형식 (JSON 파싱 에러 등)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleMessageNotReadable(HttpMessageNotReadableException e) {
        log.error("Message not readable: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid request format");
    }

    // 데이터베이스 관련 예외
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        log.error("Database error: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database error occurred");
    }

    // 트랜잭션 관련 예외
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<?> handleTransactionException(TransactionException e) {
        log.error("Transaction error: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Transaction error occurred");
    }

    // 기타 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralError(Exception e) {
        log.error("Unexpected error: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }
}
