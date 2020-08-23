package com.edu.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.crowd.entity.po.MemberConfirmInfoPO;
import com.edu.crowd.entity.po.MemberLaunchInfoPO;
import com.edu.crowd.entity.po.ProjectPO;
import com.edu.crowd.entity.po.ReturnPO;
import com.edu.crowd.entity.vo.DetailProjectVO;
import com.edu.crowd.entity.vo.MemberConfirmInfoVO;
import com.edu.crowd.entity.vo.MemberLauchInfoVO;
import com.edu.crowd.entity.vo.ProjectVO;
import com.edu.crowd.entity.vo.ProtalTypeVO;
import com.edu.crowd.entity.vo.ReturnVO;
import com.edu.crowd.mapper.MemberConfirmInfoPOMapper;
import com.edu.crowd.mapper.MemberLaunchInfoPOMapper;
import com.edu.crowd.mapper.ProjectItemPicPOMapper;
import com.edu.crowd.mapper.ProjectPOMapper;
import com.edu.crowd.mapper.ReturnPOMapper;
import com.edu.crowd.service.api.ProjectService;

/**
 * @author wyg_edu
 * @date 2020年7月25日 下午9:40:32
 * @version v1.0
 */
@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectPOMapper projectPOMapper;
	
	@Autowired
	private ProjectItemPicPOMapper projectItemPicPOMapper;
	
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
	
	@Autowired
	private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
	
	@Autowired
	private ReturnPOMapper returnPOMapper;

	/**
	 * 进行ProjectVO数据保存
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public void saveProject(ProjectVO projectVo, Integer memberId) {
		// 一、保存projectPO对象
		// 1、创建空ProjectPO对象
		ProjectPO projectPO = new ProjectPO();
		
		// 2、复制projectVO到projectPo
		BeanUtils.copyProperties(projectVo, projectPO);
		
		projectPO.setMemberid(memberId);
		
		String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		projectPO.setCreatedate(format);// 创建时间
		projectPO.setStatus(0);// 表示即将开始
		
		// 3、保存projectPO对象 做一下配置，可以将自增主键保存到对象中去
		// <insert id="insertSelective" useGeneratedKeys="true" keyProperty="id" parameterType="com.edu.crowd.entity.po.ProjectPO" >
		projectPOMapper.insertSelective(projectPO);
		
		// 4、从PO对象中获取自增逐渐
		Integer projectId = projectPO.getId();
		
		// 二、保存项目的分类信息
		// 1、从VO对象中获取typeIdList
		List<Integer> typeIdList = projectVo.getTypeIdList();
		// 2、执行对象保存
		projectPOMapper.insertRelationship(typeIdList, projectId);
		
		// 三、保存项目的标签的关联信息
		List<Integer> tagIdList = projectVo.getTagIdList();
		projectPOMapper.insertTagRelationship(tagIdList,projectId);
		
		// 四、保存项目详情图片的信息
		List<String> detailPicturePathList = projectVo.getDetailPicturePathList();
		projectItemPicPOMapper.insertPathList(detailPicturePathList,projectId);
		
		// 五、保存项目发起人的信息
		MemberLauchInfoVO memberLauchInfoVO = projectVo.getMemberLauchInfoVO();
		MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
		BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
		memberLaunchInfoPO.setMemberid(memberId);
		memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
		
		// 六、保存项目回报的信息
		List<ReturnVO> returnVOList = projectVo.getReturnVOList();
		
		List<ReturnPO> returnPOList = new ArrayList<>();
		
		for (ReturnVO returnVO : returnVOList) {
			ReturnPO returnPO = new ReturnPO();
			BeanUtils.copyProperties(returnVO, returnPO);
			returnPOList.add(returnPO);
		}
		
		returnPOMapper.insertReturnPOBatch(returnPOList,projectId);
		
		// 七、保存项目的确认信息
		MemberConfirmInfoVO memberConfirmInfoVO = projectVo.getMemberConfirmInfoVO();
		MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
		BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
		memberConfirmInfoPO.setMemberid(memberId);
		memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);
	}

	/**
	 * 主页数据查询
	 */
	@Override
	public List<ProtalTypeVO> getProtalTyprVO() {
		return projectPOMapper.selectProtalTypeList();
	}

	/**
	 * 详情页数据查询
	 */
	@Override
	public DetailProjectVO getDetailProjectVO(Integer projectId) {
		// 获取详情对象
		DetailProjectVO selectDetailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
		
		// 对审核状态进行判断赋值
		switch (selectDetailProjectVO.getStatus()) {
		case 0:
			selectDetailProjectVO.setStatusText("审核中");
			break;
		case 1:
			selectDetailProjectVO.setStatusText("众筹中");
			break;
		case 2:
			selectDetailProjectVO.setStatusText("众筹成功");
			break;
		case 3:
			selectDetailProjectVO.setStatusText("已关闭");
			break;

		default:
			break;
		}
		
		// 计算众筹结束日期  2020-11-12
		String deployDate = selectDetailProjectVO.getDeployDate();

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(deployDate, fmt);
        Period next = Period.between(date, LocalDate.now());// 计算众筹开始到现在的天数
        int bDay = selectDetailProjectVO.getDay() - next.getDays();// 总时间-开始时间=剩余天数
        selectDetailProjectVO.setLastDay(bDay);
		
		return selectDetailProjectVO;
	}
	

}
