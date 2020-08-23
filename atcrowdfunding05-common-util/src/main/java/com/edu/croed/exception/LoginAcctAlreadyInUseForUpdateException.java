package com.edu.croed.exception;
/**
 * @author wyg_edu
 * @date 2020年5月13日 下午8:25:15
 * @version v1.0
 * 用户名已被占用
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public LoginAcctAlreadyInUseForUpdateException() {
		super();
	}

	public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginAcctAlreadyInUseForUpdateException(String message) {
		super(message);
	}

	public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
		super(cause);
	}
	

}
