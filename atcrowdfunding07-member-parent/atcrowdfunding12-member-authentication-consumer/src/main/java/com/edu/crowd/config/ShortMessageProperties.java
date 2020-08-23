package com.edu.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wyg_edu
 * @date 2020年7月2日 下午9:46:11
 * @version v1.0
 */
@Component// 进入IOC
@ConfigurationProperties(prefix = "short.message")
public class ShortMessageProperties {
	
	private String host;
	private String path;
	private String method;
	private String appcode;
	private String sign;
	private String skin;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getAppcode() {
		return appcode;
	}
	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public ShortMessageProperties(String host, String path, String method, String appcode, String sign, String skin) {
		super();
		this.host = host;
		this.path = path;
		this.method = method;
		this.appcode = appcode;
		this.sign = sign;
		this.skin = skin;
	}
	public ShortMessageProperties() {
		super();
	}
	@Override
	public String toString() {
		return "ShortMessageProperties [host=" + host + ", path=" + path + ", method=" + method + ", appcode=" + appcode
				+ ", sign=" + sign + ", skin=" + skin + "]";
	}
	

}
