package com.example.board.config;

import com.example.board.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        log.error("Resource not found: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.NOT_FOUND.value()); // 404
        response.setMessage("Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 잘못된 입력값 (생성/수정 시)
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDto> handleBadRequest(Exception e) {
        log.error("Invalid request: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
        response.setMessage("Invalid request parameters");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 데이터 무결성 위반 (중복 키 등)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error("Data integrity violation: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.CONFLICT.value()); // 409
        response.setMessage("Data integrity violation");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // 권한 없음
    @ExceptionHandler({AccessDeniedException.class, SecurityException.class})
    public ResponseEntity<ResponseDto> handleAccessDenied(Exception e) {
        log.error("Access denied: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403 상태 코드
        response.setMessage("Access denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // 잘못된 요청 형식 (JSON 파싱 에러 등)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> handleMessageNotReadable(HttpMessageNotReadableException e) {
        log.error("Message not readable: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
        response.setMessage("Invalid request format");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 데이터베이스 관련 예외
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDto> handleDataAccessException(DataAccessException e) {
        log.error("Database error: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        response.setMessage("Database error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 트랜잭션 관련 예외
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ResponseDto> handleTransactionException(TransactionException e) {
        log.error("Transaction error: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        response.setMessage("Transaction error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("User not found: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
        response.setMessage("User not found");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 메일 전송 실패
    @ExceptionHandler(org.springframework.mail.MailSendException.class)
    public ResponseEntity<ResponseDto> handleMailSendException(org.springframework.mail.MailSendException e) {
        log.error("Failed to send email: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
        response.setMessage("Failed to send email: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 메일 전송 실패
    @ExceptionHandler(jakarta.mail.SendFailedException.class)
    public ResponseEntity<ResponseDto> handleSendFailedException(jakarta.mail.SendFailedException e) {
        log.error("Email sending failed: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
        response.setMessage("Failed to send email: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 기타 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGeneralError(Exception e) {
        log.error("Unexpected error: ", e);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        response.setMessage("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
