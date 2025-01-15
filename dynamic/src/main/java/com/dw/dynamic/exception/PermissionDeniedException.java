package com.dw.dynamic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN) // 권한 없음
public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException() {
        super();
    }
    public PermissionDeniedException(String message) {
        super(message);
    }
}
