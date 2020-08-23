package com.edu.croed.util;

/**
 * @author wyg_edu
 * @date 2020年5月7日 下午8:16:38
 * @version v1.0 统一项目的返回结果，也可以在分布式系统中使用
 */
public class ResultEntity<T> {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";

	private String result;// 封装当前请求处理的结果成功或失败
	private String message;// 处理失败的信息
	private T data;// 返回的数据

	/**
	 * 请求处理成功且不需要返回数据的方法
	 */
	public static <Type> ResultEntity<Type> successWithoutData() {
		return new ResultEntity<Type>(SUCCESS, null, null);
	}

	/**
	 * 请求成功且需要返回值的方法
	 * 
	 * @param <Type>
	 * @param data
	 * @return
	 */
	public static <Type> ResultEntity<Type> successWithData(Type data) {
		return new ResultEntity<Type>(SUCCESS, null, data);
	}

	/**
	 * 请求处理失败使用的工具方法，参数失败的错误消息
	 * 
	 * @param <Type>
	 * @param message
	 * @return
	 */
	public static <Type> ResultEntity<Type> failed(String message) {
		return new ResultEntity<Type>(FAILED, message, null);
	}

	public ResultEntity(String result, String message, T data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public ResultEntity() {

	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultEntity [result=" + result + ", message=" + message + ", data=" + data + "]";
	}

}
