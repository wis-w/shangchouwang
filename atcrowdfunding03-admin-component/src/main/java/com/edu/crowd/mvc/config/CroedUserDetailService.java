package com.edu.crowd.mvc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.edu.crowd.entity.Admin;
import com.edu.crowd.entity.Role;
import com.edu.crowd.service.api.AdminService;
import com.edu.crowd.service.api.AuthService;
import com.edu.crowd.service.api.RoleService;

/**
 * @author wyg_edu
 * @date 2020年6月13日 下午5:03:39
 * @version v1.0
 */
@Component
public class CroedUserDetailService implements UserDetailsService{
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 根据账号名称查找Admin对象
		Admin admin = adminService.getAdminByLoginAcct(username);
		
		// 根据adminId查询角色与权限
		List<Role> assignedRile = roleService.getAssignedRile(admin.getId());
		
		// 根据adminId查询权限
		List<String> authorityList = authService.getRoleByAdminId(admin.getId());
		
		// 创建集合对象存储GrantedAuthority
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		// 遍历assignedRile存入角色的信息
		for(Role role :assignedRile) {
			// 根据springSecurioty的要求必须加前缀
			String roleName = "ROLE_"+role.getName();
			
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
			authorities.add(authority);
		}
		
		// 存入权限的信息
		for(String authName : authorityList) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authName);
			authorities.add(authority);
			
		}
		
		// 封装SecuriutyAdmin对象
		SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
		
		// 有什么意义？？？ TODO  测试是这样也是可以通过的
//		User user = new User(username,admin.getUserPswd(),authorities);
		
		return securityAdmin;
	}

}
