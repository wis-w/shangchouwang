package com.edu.crowd.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyg_edu
 * @date 2020年7月26日 下午2:05:09
 * @version v1.0
 */
public class ProjectVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 分类 id 集合
	private List<Integer> typeIdList;
	// 标签 id 集合
	private List<Integer> tagIdList;
	// 项目名称
	private String projectName;
	// 项目描述
	private String projectDescription;
	// 计划筹集的金额
	private Integer money;
	// 筹集资金的天数
	private Integer day;
	// 创建项目的日期
	private String createdate;
	// 头图的路径
	private String headerPicturePath;
	// 详情图片的路径
	private List<String> detailPicturePathList;
	// 发起人信息
	private MemberLauchInfoVO memberLauchInfoVO;

	public ProjectVO() {
	}

	public ProjectVO(List<Integer> typeIdList, List<Integer> tagIdList, String projectName, String projectDescription,
			Integer money, Integer day, String createdate, String headerPicturePath, List<String> detailPicturePathList,
			MemberLauchInfoVO memberLauchInfoVO, List<ReturnVO> returnVOList, MemberConfirmInfoVO memberConfirmInfoVO) {
		this.typeIdList = typeIdList;
		this.tagIdList = tagIdList;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.money = money;
		this.day = day;
		this.createdate = createdate;
		this.headerPicturePath = headerPicturePath;
		this.detailPicturePathList = detailPicturePathList;
		this.memberLauchInfoVO = memberLauchInfoVO;
		this.returnVOList = returnVOList;
		this.memberConfirmInfoVO = memberConfirmInfoVO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdate == null) ? 0 : createdate.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((detailPicturePathList == null) ? 0 : detailPicturePathList.hashCode());
		result = prime * result + ((headerPicturePath == null) ? 0 : headerPicturePath.hashCode());
		result = prime * result + ((memberConfirmInfoVO == null) ? 0 : memberConfirmInfoVO.hashCode());
		result = prime * result + ((memberLauchInfoVO == null) ? 0 : memberLauchInfoVO.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((projectDescription == null) ? 0 : projectDescription.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((returnVOList == null) ? 0 : returnVOList.hashCode());
		result = prime * result + ((tagIdList == null) ? 0 : tagIdList.hashCode());
		result = prime * result + ((typeIdList == null) ? 0 : typeIdList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectVO other = (ProjectVO) obj;
		if (createdate == null) {
			if (other.createdate != null)
				return false;
		} else if (!createdate.equals(other.createdate))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (detailPicturePathList == null) {
			if (other.detailPicturePathList != null)
				return false;
		} else if (!detailPicturePathList.equals(other.detailPicturePathList))
			return false;
		if (headerPicturePath == null) {
			if (other.headerPicturePath != null)
				return false;
		} else if (!headerPicturePath.equals(other.headerPicturePath))
			return false;
		if (memberConfirmInfoVO == null) {
			if (other.memberConfirmInfoVO != null)
				return false;
		} else if (!memberConfirmInfoVO.equals(other.memberConfirmInfoVO))
			return false;
		if (memberLauchInfoVO == null) {
			if (other.memberLauchInfoVO != null)
				return false;
		} else if (!memberLauchInfoVO.equals(other.memberLauchInfoVO))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (projectDescription == null) {
			if (other.projectDescription != null)
				return false;
		} else if (!projectDescription.equals(other.projectDescription))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (returnVOList == null) {
			if (other.returnVOList != null)
				return false;
		} else if (!returnVOList.equals(other.returnVOList))
			return false;
		if (tagIdList == null) {
			if (other.tagIdList != null)
				return false;
		} else if (!tagIdList.equals(other.tagIdList))
			return false;
		if (typeIdList == null) {
			if (other.typeIdList != null)
				return false;
		} else if (!typeIdList.equals(other.typeIdList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectVO [typeIdList=" + typeIdList + ", tagIdList=" + tagIdList + ", projectName=" + projectName
				+ ", projectDescription=" + projectDescription + ", money=" + money + ", day=" + day + ", createdate="
				+ createdate + ", headerPicturePath=" + headerPicturePath + ", detailPicturePathList="
				+ detailPicturePathList + ", memberLauchInfoVO=" + memberLauchInfoVO + ", returnVOList=" + returnVOList
				+ ", memberConfirmInfoVO=" + memberConfirmInfoVO + "]";
	}

	public List<Integer> getTypeIdList() {
		return typeIdList;
	}

	public void setTypeIdList(List<Integer> typeIdList) {
		this.typeIdList = typeIdList;
	}

	public List<Integer> getTagIdList() {
		return tagIdList;
	}

	public void setTagIdList(List<Integer> tagIdList) {
		this.tagIdList = tagIdList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getHeaderPicturePath() {
		return headerPicturePath;
	}

	public void setHeaderPicturePath(String headerPicturePath) {
		this.headerPicturePath = headerPicturePath;
	}

	public List<String> getDetailPicturePathList() {
		return detailPicturePathList;
	}

	public void setDetailPicturePathList(List<String> detailPicturePathList) {
		this.detailPicturePathList = detailPicturePathList;
	}

	public MemberLauchInfoVO getMemberLauchInfoVO() {
		return memberLauchInfoVO;
	}

	public void setMemberLauchInfoVO(MemberLauchInfoVO memberLauchInfoVO) {
		this.memberLauchInfoVO = memberLauchInfoVO;
	}

	public List<ReturnVO> getReturnVOList() {
		return returnVOList;
	}

	public void setReturnVOList(List<ReturnVO> returnVOList) {
		this.returnVOList = returnVOList;
	}

	public MemberConfirmInfoVO getMemberConfirmInfoVO() {
		return memberConfirmInfoVO;
	}

	public void setMemberConfirmInfoVO(MemberConfirmInfoVO memberConfirmInfoVO) {
		this.memberConfirmInfoVO = memberConfirmInfoVO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 回报信息集合
	private List<ReturnVO> returnVOList;
	// 发起人确认信息
	private MemberConfirmInfoVO memberConfirmInfoVO;

}
