package com.dw.dynamic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED) //인증 실패(즉, 로그인을 안했을때)
public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super();
    }
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
