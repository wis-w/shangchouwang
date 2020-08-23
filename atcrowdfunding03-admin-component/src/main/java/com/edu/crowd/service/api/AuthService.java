package com.edu.crowd.service.api;

import java.util.List;
import java.util.Map;

import com.edu.crowd.entity.Auth;
import com.edu.crowd.entity.Role;

/**
 * @author wyg_edu
 * @date 2020年5月28日 下午8:46:49
 * @version v1.0
 */
public interface AuthService {

	List<Auth> getAll();

	void removeNode(Integer id);

	void addAuthNode(Auth auth);

	List<Integer> getAssignedAuthId(Integer roleId);

	void saveRoleAndAuth(Map<String, List<Integer>> map);
	
	List<String> getRoleByAdminId(Integer adminId);

}
