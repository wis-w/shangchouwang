package com.edu.crowd.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.util.ResultEntity;
import com.edu.crowd.api.MySQLRemotrService;
import com.edu.crowd.entity.vo.ProtalTypeVO;

/**
 * @author wyg_edu
 * @date 2020年6月23日 下午9:16:21
 * @version v1.0
 */

@Controller
public class PortalHandler {

	@Autowired
	private MySQLRemotrService mySQLRemotrService;

	/**
	 * 显示首页
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String showPortalPage(ModelMap modelMap) {
		
		// 1、调用mysql模块，提供查询的所需要的数据
		ResultEntity<List<ProtalTypeVO>> protalTypeProjectDataRemote = mySQLRemotrService
				.getProtalTypeProjectDataRemote();

		// 2、检查查询结果
		if (ResultEntity.SUCCESS.equals(protalTypeProjectDataRemote.getResult())) {
			// 3、获取查询结果的数据
			List<ProtalTypeVO> data = protalTypeProjectDataRemote.getData();
			// 4、存入模型
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_PROTAL_DATA, data);
		}

		return "portal";
	}
}
