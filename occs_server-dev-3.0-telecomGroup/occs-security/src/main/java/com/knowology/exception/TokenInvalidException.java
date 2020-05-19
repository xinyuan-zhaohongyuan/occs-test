package com.knowology.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>token无效异常</p>
 *
 * @author : Conan
 * @date : 2019-07-30 15:44
 **/

public class TokenInvalidException extends AuthenticationException {
	private static final long serialVersionUID = -4238342664344215161L;
	
	public TokenInvalidException(String message) {
        super(message);
    }
    public TokenInvalidException(String message,Throwable t) {
        super(message,t);
    }
}
