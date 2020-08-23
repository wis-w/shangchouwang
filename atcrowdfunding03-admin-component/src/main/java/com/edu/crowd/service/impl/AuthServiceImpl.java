package com.edu.crowd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.crowd.entity.Auth;
import com.edu.crowd.entity.AuthExample;
import com.edu.crowd.entity.Role;
import com.edu.crowd.mapper.AuthMapper;
import com.edu.crowd.service.api.AuthService;

/**
 * @author wyg_edu
 * @date 2020年5月28日 下午8:47:09
 * @version v1.0
 */
@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthMapper authMapper;

	/**
	 * 进行数据的全表查询
	 */
	@Override
	public List<Auth> getAll() {
		return authMapper.selectByExample(new AuthExample());
	}

	/**
	 * 删除节点操作
	 */
	@Override
	public void removeNode(Integer id) {
		authMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 增加节点元素
	 */
	@Override
	public void addAuthNode(Auth auth) {
		authMapper.insert(auth);
		
	}

	/**
	 * 查询角色权限
	 */
	@Override
	public List<Integer> getAssignedAuthId(Integer roleId) {
		return authMapper.selectAssignedAuthById(roleId);
	}

	/**
	 * 进行权限修改
	 */
	@Override
	public void saveRoleAndAuth(Map<String, List<Integer>> map) {
		// 获取roleId
		Integer roleId = map.get("roleId").get(0);
		
		// 2.删除旧的关联关系
		authMapper.deleteOldRelationShip(roleId);
		
		// 3、获取authIdArray
		List<Integer> authList = map.get("authIdArray");
		if(authList != null && authList.size()>0) {
			authMapper.insertNewRelationShip(roleId,authList);
			
		}
	}

	/**
	 * 通过adminUd查找Role
	 */
	@Override
	public List<String> getRoleByAdminId(Integer adminId) {
		return authMapper.selectRoleByAdminId(adminId);
	}

}
