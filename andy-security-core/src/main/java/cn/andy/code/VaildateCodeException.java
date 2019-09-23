package cn.andy.code;

import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;


public class VaildateCodeException extends AuthenticationException {


    public VaildateCodeException(String message) {
        super(message);
    }
}
