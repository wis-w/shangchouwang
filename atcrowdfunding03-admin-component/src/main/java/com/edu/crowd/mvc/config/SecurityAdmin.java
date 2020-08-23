package com.edu.crowd.mvc.config;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.edu.crowd.entity.Admin;

/**
 * @author wyg_edu
 * @date 2020年6月13日 下午4:49:36
 * @version v1.0
 * 考虑到USer对象中仅包含账号与密码，为了获取到原始的admin对象，串门创建这个类对User进行扩展
 */
public class SecurityAdmin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 原始的Admin对象，包含Admin的全部属性
	private Admin originalAdmin;

	public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
		// 调用父类的构造器
		super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
		// 给原始的成员变量进行赋值
		this.originalAdmin = originalAdmin;
		
		// 将原始Admin对象的密码擦除
		this.originalAdmin.setUserPswd(null);
	}
	
	/**
	 * 获取原始的Admin对象的get方法
	 * @return
	 */
	public Admin getOriginalAdmin() {
		return originalAdmin;
	}
	
	

}
