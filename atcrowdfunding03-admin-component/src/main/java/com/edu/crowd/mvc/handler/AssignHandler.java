package com.edu.crowd.mvc.handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.Auth;
import com.edu.crowd.entity.Role;
import com.edu.crowd.service.api.AdminService;
import com.edu.crowd.service.api.AuthService;
import com.edu.crowd.service.api.RoleService;

/**
 * @author wyg_edu
 * @date 2020年5月27日 下午8:37:36
 * @version v1.0
 */
@Controller
public class AssignHandler {

	Logger log = LoggerFactory.getLogger(AssignHandler.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 进行权限修改
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assign/do/role/assign/auth.json")
	public ResultEntity<String> saveRoleAndAuth(@RequestBody Map<String,List<Integer>> map){
		authService.saveRoleAndAuth(map);
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 获取角色权限数据
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assign/get/assigned/auth/id.json")
	public ResultEntity<List<Integer>> getAssignedAuthId(@RequestParam("roleId") Integer roleId){
		
		List<Integer> list =authService.getAssignedAuthId(roleId);
		
		log.info("list:"+list);
		
		return ResultEntity.successWithData(list);
	}
	
	/**
	 * 权限的树形结构数据查询
	 */
	@ResponseBody
	@RequestMapping("/assign/get/all/auth.json")
	public ResultEntity<List<Auth>> getAllAuth(){
		List<Auth> list = authService.getAll();
		return ResultEntity.successWithData(list);
	}

	/**
	 * 进行角色分配
	 * 
	 * @param adminId
	 * @param pageNum
	 * @param keyWord
	 * @param roleIdList
	 * @return
	 */
	@RequestMapping("/assign/do/role/assign.html")
	public String saveRelationShip(@RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyWord") String keyWord,
			@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {// 允许list可以为空 required = false表示这个参数不是必须的
		
		adminService.saveRelationShip(adminId, roleIdList);

		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;

	}

	/**
	 * 角色权限查询
	 * 
	 * @param adminId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/assign/to/assign/role/page.html")
	public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
			@RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyWord") String keyWord,
			ModelMap modelMap) {
		// 1、查询已分配的角色
		List<Role> assignedList = roleService.getAssignedRile(adminId);

		// 2、查询未分配的角色
		List<Role> unAssignedList = roleService.getUnAssignedRile(adminId);

		// 3、存入模型（本质上是request.setAttribute("name",value)）
		modelMap.addAttribute("assignedList", assignedList);
		modelMap.addAttribute("unAssignedList", unAssignedList);
		modelMap.addAttribute("adminId", adminId);
		modelMap.addAttribute("pageNum", pageNum);
		modelMap.addAttribute("keyWord", keyWord);

		return "assign-role";

	}

}
