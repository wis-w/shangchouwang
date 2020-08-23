package com.edu.croed.exception;
/**
 * @author wyg_edu
 * @date 2020年5月10日 下午4:12:22
 * @version v1.0
 * 用户未登录就访问受保护资源
 */
public class AccessForbiddenException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AccessForbiddenException() {
		super();
	}

	public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessForbiddenException(String message) {
		super(message);
	}

	public AccessForbiddenException(Throwable cause) {
		super(cause);
	}
	
	

}
