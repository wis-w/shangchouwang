package com.edu.crowd.service.api;

import java.util.List;

import com.edu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

/**
 * @author wyg_edu
 * @date 2020年5月18日 下午9:24:52
 * @version v1.0
 */

public interface RoleService {
	
	PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyWord);

	void save(Role role);

	void update(Role role);

	void remove(Role role);
	
	void removeRole(List<Integer> roleIdList) ;

	List<Role> getAssignedRile(Integer adminId);

	List<Role> getUnAssignedRile(Integer adminId);

}
