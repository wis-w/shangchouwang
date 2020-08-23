package com.edu.crowd.mvc.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.Role;
import com.edu.crowd.service.api.RoleService;
import com.github.pagehelper.PageInfo;

/**
 * @author wyg_edu
 * @date 2020年5月18日 下午9:26:32
 * @version v1.0
 */
@Controller
public class RoleHandler {
	
	Logger log = LoggerFactory.getLogger(RoleHandler.class);
	
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 根据id数组进行批量删除操作
	 * @param roleList
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/role/remove/by/role/id/array.json")
	public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleList){
		// 进行批量删除操作
		roleService.removeRole(roleList);
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 通过id进行role数据删除
	 */
	@ResponseBody
	@RequestMapping("/role/remove.json")
	public ResultEntity<String> removeRole(Role role){
		log.info("开始删除操作");
		roleService.remove(role);
		return ResultEntity.successWithoutData();
		
	}
	
	/**
	 * 通过id信息更新数据库
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/role/update.json")
	public ResultEntity<String> updateRole(Role role){
		roleService.update(role);
		return ResultEntity.successWithoutData();
		
	}
	
	/**
	 * 进行操作的保存函数
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/role/save.json")
	public ResultEntity<Role> saveRole(Role role){
		
		roleService.save(role);
		return ResultEntity.successWithoutData();
		
	}
	
	/**
	 * 进行分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param keyWord
	 * @return
	 */
	@PreAuthorize("hasRole('部长')")
	@ResponseBody
	@RequestMapping("/role/get/page/info.json")
	public ResultEntity<PageInfo<Role>> getPageInfo(
			@RequestParam(value = "pageNum" , defaultValue = "1")Integer pageNum,
			@RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
			@RequestParam(value = "keyWord",defaultValue = "")String keyWord){
		// 获取分页数据
		PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyWord);
		// 封装并返回，如果上述抛出异常，则交给异常处理机制
		return ResultEntity.successWithData(pageInfo);
	}

}
