package com.edu.crowd.entity.vo;

import java.io.Serializable;

/**
 * @author wyg_edu
 * @date 2020年7月11日 下午5:31:06
 * @version v1.0 用户登录的VO对象
 */
public class MemberLoginVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String username;
	
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberLoginVO other = (MemberLoginVO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public MemberLoginVO() {
		super();
	}

	@Override
	public String toString() {
		return "MemberLoginVO [id=" + id + ", username=" + username + ", email=" + email + "]";
	}

	public MemberLoginVO(Integer id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}

}
