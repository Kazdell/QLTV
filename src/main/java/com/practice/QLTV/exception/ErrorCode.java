package com.practice.QLTV.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // General Errors
    UNCATEGORIZED_EXCEPTION(999, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_MAINTENANCE(998, "System is under maintenance", HttpStatus.SERVICE_UNAVAILABLE),
    DATABASE_ERROR(997, "Database operation failed", HttpStatus.INTERNAL_SERVER_ERROR),

    // Authentication & Authorization (1000-1099)
    INVALID_KEY(1001, "Invalid authentication key", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(1002, "Authentication token has expired", HttpStatus.UNAUTHORIZED),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1005, "Invalid username or password", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1006, "Authentication required", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Insufficient permissions", HttpStatus.FORBIDDEN),
    SESSION_EXPIRED(1008, "Session has expired", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED(1009, "Account is locked", HttpStatus.FORBIDDEN),

    // User Management (1010-1099)
    USER_NOT_EXISTED(1010, "User does not exist", HttpStatus.NOT_FOUND),
    USER_EXISTED(1011, "User already exists", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1012, "Username is already taken", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1013, "Email is already registered", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(1014, "Phone number is already registered", HttpStatus.BAD_REQUEST),
    IDENTITY_NUMBER_EXISTED(1015, "Identity number is already registered", HttpStatus.BAD_REQUEST),
    USER_INACTIVE(1016, "User account is inactive", HttpStatus.FORBIDDEN),
    USER_BANNED(1017, "User account is banned", HttpStatus.FORBIDDEN),
    USER_DELETED(1018, "User account has been deleted", HttpStatus.NOT_FOUND),
    USER_UPDATE_FAILED(1019, "Failed to update user information", HttpStatus.BAD_REQUEST),
    USER_RETRIEVE_FAIL(1020, "Failed to retrieve user information", HttpStatus.BAD_REQUEST),

    // Success Codes (1000)
    OPERATION_SUCCESSFUL(1000, "Operation completed successfully", HttpStatus.OK), // Generic success
    USER_RETRIEVED_SUCCESSFULLY(1000, "Retrieve successfully", HttpStatus.OK),
    USER_CREATED_SUCCESSFULLY(1000, "User created successfully", HttpStatus.OK),
    USER_UPDATED_SUCCESSFULLY(1000, "User updated successfully", HttpStatus.OK),
    UPDATE_STATUS_SUCCESSFUL(1000, "User status updated successfully", HttpStatus.OK),

    // Validation Errors (1100-1199)
    INVALID_DOB(1100, "Age must be at least {min} years", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(1101, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_FORMAT(1102, "Invalid phone number format", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT(1103, "Password does not meet security requirements", HttpStatus.BAD_REQUEST),
    INVALID_DATE_FORMAT(1104, "Invalid date format", HttpStatus.BAD_REQUEST),

    // Role Management (1200-1299)
    ROLE_NOT_EXISTED(1200, "Role does not exist", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXISTS(1201, "Role already exists", HttpStatus.BAD_REQUEST),
    INVALID_ROLE_ASSIGNMENT(1202, "Invalid role assignment", HttpStatus.BAD_REQUEST),
    ROLE_UPDATE_FAILED(1203, "Failed to update role", HttpStatus.BAD_REQUEST),

    // Request Processing (1300-1399)
    INVALID_REQUEST_FORMAT(1300, "Invalid request format", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD(1301, "Required field is missing", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER(1302, "Invalid parameter value", HttpStatus.BAD_REQUEST),
    REQUEST_TOO_LARGE(1303, "Request size exceeds limit", HttpStatus.PAYLOAD_TOO_LARGE),

    // Resource Management (1400-1499)
    RESOURCE_NOT_FOUND(1400, "Requested resource not found", HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS(1401, "Resource already exists", HttpStatus.CONFLICT),
    RESOURCE_ACCESS_DENIED(1402, "Access to resource denied", HttpStatus.FORBIDDEN);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatusCode() { return statusCode; }
}