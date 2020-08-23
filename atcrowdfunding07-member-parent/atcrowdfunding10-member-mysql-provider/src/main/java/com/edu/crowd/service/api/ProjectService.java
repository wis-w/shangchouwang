package com.edu.crowd.service.api;

import java.util.List;

import com.edu.crowd.entity.vo.DetailProjectVO;
import com.edu.crowd.entity.vo.ProjectVO;
import com.edu.crowd.entity.vo.ProtalTypeVO;

/**
 * @author wyg_edu
 * @date 2020年7月25日 下午9:39:15
 * @version v1.0
 */
public interface ProjectService {

	void saveProject(ProjectVO projectVo, Integer id);
	 
	List<ProtalTypeVO> getProtalTyprVO();

	DetailProjectVO getDetailProjectVO(Integer projectId);
}
