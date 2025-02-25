package com.practice.QLTV.exception;

import com.practice.QLTV.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("Unexpected error: ", ex);
        return createErrorResponse(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
        log.error("Application error: {}", ex.getMessage());
        String message = formatMessage(ex.getErrorCode().getMessage(), ex.getArgs());
        return createErrorResponse(ex.getErrorCode(), message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: ", ex);
        return createErrorResponse(ErrorCode.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return createErrorResponse(ErrorCode.INVALID_REQUEST_FORMAT, message);
    }

    private ResponseEntity<ApiResponse<Void>> createErrorResponse(ErrorCode errorCode) {
        return createErrorResponse(errorCode, errorCode.getMessage());
    }

    private ResponseEntity<ApiResponse<Void>> createErrorResponse(ErrorCode errorCode, String message) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(errorCode.getCode())
                .message(message)
                .result(null)
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    private String formatMessage(String template, Object... args) {
        return messageSource.getMessage(
                template,
                args,
                template,
                LocaleContextHolder.getLocale()
        );
    }
}