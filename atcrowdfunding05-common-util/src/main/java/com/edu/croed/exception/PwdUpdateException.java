package com.edu.croed.exception;
/**
 * @author wyg_edu
 * @date 2020年5月19日 下午4:47:56
 * @version v1.0
 * 用户密码修改异常
 */
public class PwdUpdateException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PwdUpdateException() {
		super();
	}

	public PwdUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PwdUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public PwdUpdateException(String message) {
		super(message);
	}

	public PwdUpdateException(Throwable cause) {
		super(cause);
	}

}
