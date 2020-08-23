package com.edu.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.crowd.entity.Role;
import com.edu.crowd.entity.RoleExample;
import com.edu.crowd.entity.RoleExample.Criteria;
import com.edu.crowd.mapper.RoleMapper;
import com.edu.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author wyg_edu
 * @date 2020年5月18日 下午9:25:03
 * @version v1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleMapper mapper;

	/**
	 * 分页查询
	 */
	@Override
	public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyWord) {
		// 1、开启分页
		PageHelper.startPage(pageNum, pageSize);
		
		// 2、执行查询
		List<Role> role = mapper.selectRoleByKeyWord(keyWord);
		
		// 3、封装pageInfo
		return new PageInfo<Role>(role);
	}
	
	/**
	 * 新增函数
	 */
	@Override
	public void save(Role role) {
		mapper.insert(role);
	}

	/**
	 * 更新操作
	 */
	@Override
	public void update(Role role) {
		mapper.updateByPrimaryKey(role);
		
	}

	/**
	 * 删除操作
	 */
	@Override
	public void remove(Role role) {
		mapper.deleteByPrimaryKey(role.getId());
	}

	/**
	 * 批量删除操作
	 */
	@Override
	public void removeRole(List<Integer> roleIdList) {
		RoleExample roleExample = new RoleExample();
		Criteria createCriteria = roleExample.createCriteria();
		// where id in ( , , , );
		createCriteria.andIdIn(roleIdList);
		mapper.deleteByExample(roleExample);
	}

	/**
	 * 查找已分配权限的角色
	 */
	@Override
	public List<Role> getAssignedRile(Integer adminId) {
		
		return mapper.selectAssignRole(adminId);
	}

	/**
	 * 查询未分配权限的角色
	 */
	@Override
	public List<Role> getUnAssignedRile(Integer adminId) {
		return mapper.selectUnAssignRole(adminId);
	}

}
