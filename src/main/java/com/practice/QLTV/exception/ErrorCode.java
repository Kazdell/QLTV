package com.practice.QLTV.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1010, "User not existed", HttpStatus.NOT_FOUND),
    USER_EXISTED(1011, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1012, "User existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1013, "Email existed", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(1014, "Phone number existed", HttpStatus.BAD_REQUEST),
    IDENTITY_NUMBER_EXISTED(1015, "Identity number existed", HttpStatus.BAD_REQUEST),

    ROLE_NOT_EXISTED(1016, "Role not existed", HttpStatus.NOT_FOUND),

    USER_RETRIEVE_FAIL(1020, "Cannot retrieve user", HttpStatus.BAD_REQUEST)
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
