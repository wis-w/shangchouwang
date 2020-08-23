package com.edu.crowd.mvc.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.Auth;
import com.edu.crowd.service.api.AuthService;

/**
 * @author wyg_edu
 * @date 2020年5月28日 下午8:48:03
 * @version v1.0
 */
@Controller
public class AuthHandler {
	
	@Autowired
	private AuthService authService;
	
	
	/**
	 * 增加权限节点元素
	 * @param auth
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add/auth/node.json")
	public ResultEntity<String> addAuthNode(Auth auth){
		
		authService.addAuthNode(auth);
		
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 返回权限列表
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get/auth/list.json")
	public ResultEntity<List<Auth>> toAuthPage(ModelMap map) {
		List<Auth> list = authService.getAll();
		
		// 将查询到的数组返回给页面
		return ResultEntity.successWithData(list);
	}
	
	/**
	 * 权限节点删除计划
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/remove/auth/node.json")
	public ResultEntity<String> removeNode(@RequestParam("id") Integer id){
		authService.removeNode(id);
		return ResultEntity.successWithoutData();
		
	}

}
