package com.practice.QLTV.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = new Object[]{};
    }

    public AppException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }

    public AppException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.args = new Object[]{};
    }
}
