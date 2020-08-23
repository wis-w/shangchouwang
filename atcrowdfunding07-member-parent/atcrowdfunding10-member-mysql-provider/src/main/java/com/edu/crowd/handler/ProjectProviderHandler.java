package com.edu.crowd.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.vo.DetailProjectVO;
import com.edu.crowd.entity.vo.ProjectVO;
import com.edu.crowd.entity.vo.ProtalTypeVO;
import com.edu.crowd.service.api.ProjectService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wyg_edu
 * @date 2020年7月25日 下午9:38:23
 * @version v1.0
 */
@Slf4j
@RestController
public class ProjectProviderHandler {
	
	@Autowired
	private ProjectService projectService;
	

	/**
	 * 详情页查询
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/get/project/datail/remote/{projectId}")
	public ResultEntity<DetailProjectVO> getDetailProjectVO(@PathVariable("projectId") Integer projectId){
		
		try {
			DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
			return ResultEntity.successWithData(detailProjectVO);
		} catch (Exception e) {
			return ResultEntity.failed(e.getMessage());
		}
		
		
	}
	
	
	/**
	 * 进行首页数据查询分析
	 * @return
	 */
	@RequestMapping("/get/protal/type/project/data/remote")
	ResultEntity<List<ProtalTypeVO>> getProtalTypeProjectDataRemote(){
		try {
			List<ProtalTypeVO> protalTyprVO = projectService.getProtalTyprVO();
			return ResultEntity.successWithData(protalTyprVO);
		} catch (Exception e) {
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	@RequestMapping("/save/project/vo/remote")
	ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVo, @RequestParam("remoteId") Integer id){
		try {
			// 调用本地进行数据保存
			projectService.saveProject(projectVo,id);
			
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			log.info("【{}】数据保存失败！！", new Object[] {e.getMessage()});
			return ResultEntity.failed(e.getMessage());
		}
	}
	
}
