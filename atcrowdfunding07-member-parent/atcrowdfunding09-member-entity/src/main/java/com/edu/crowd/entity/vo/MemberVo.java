package com.edu.crowd.entity.vo;
/**
 * @author wyg_edu
 * @date 2020年7月11日 上午11:32:46
 * @version v1.0
 * 表单数据
 */
public class MemberVo {
	
    private String loginacct;

    private String userpswd;

    private String username;

    private String email;
    
    private String phoneNum;
    
    private String code;

	public String getLoginacct() {
		return loginacct;
	}

	public MemberVo(String loginacct, String userpswd, String username, String email, String phoneNum, String code) {
		super();
		this.loginacct = loginacct;
		this.userpswd = userpswd;
		this.username = username;
		this.email = email;
		this.phoneNum = phoneNum;
		this.code = code;
	}

	public void setLoginacct(String loginacct) {
		this.loginacct = loginacct;
	}

	public String getUserpswd() {
		return userpswd;
	}

	public void setUserpswd(String userpswd) {
		this.userpswd = userpswd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MemberVo() {
		super();
	}

	@Override
	public String toString() {
		return "MemberVo [loginacct=" + loginacct + ", userpswd=" + userpswd + ", username=" + username + ", email="
				+ email + ", phoneNum=" + phoneNum + ", code=" + code + "]";
	}
}
