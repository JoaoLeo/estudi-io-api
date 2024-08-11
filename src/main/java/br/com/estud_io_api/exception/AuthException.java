package br.com.estud_io_api.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

    public AuthException(String msg) {
        super(msg);
    }
    public AuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
