package com.edu.croed.exception;
/**
 * @author wyg_edu
 * @date 2020年5月8日 下午9:52:25
 * @version v1.0
 * 登录失败的异常
 */
public class LoginFailedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public LoginFailedException() {
		super();
	}

	public LoginFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LoginFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginFailedException(String message) {
		super(message);
	}

	public LoginFailedException(Throwable cause) {
		super(cause);
	}
	
	

}
