package com.edu.crowd.entity.vo;

import java.io.Serializable;

/**
 * @author wyg_edu
 * @date 2020年7月26日 下午2:07:00
 * @version v1.0
 */
public class MemberLauchInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 简单介绍
	private String descriptionSimple;
	// 详细介绍
	private String descriptionDetail;
	// 联系电话
	private String phoneNum;
	// 客服电话
	private String serviceNum;

	public MemberLauchInfoVO() {
	}

	public MemberLauchInfoVO(String descriptionSimple, String descriptionDetail, String phoneNum, String serviceNum) {
		this.descriptionSimple = descriptionSimple;
		this.descriptionDetail = descriptionDetail;
		this.phoneNum = phoneNum;
		this.serviceNum = serviceNum;
	}

	@Override
	public String toString() {
		return "MemberLauchInfoVO [descriptionSimple=" + descriptionSimple + ", descriptionDetail=" + descriptionDetail
				+ ", phoneNum=" + phoneNum + ", serviceNum=" + serviceNum + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descriptionDetail == null) ? 0 : descriptionDetail.hashCode());
		result = prime * result + ((descriptionSimple == null) ? 0 : descriptionSimple.hashCode());
		result = prime * result + ((phoneNum == null) ? 0 : phoneNum.hashCode());
		result = prime * result + ((serviceNum == null) ? 0 : serviceNum.hashCode());
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
		MemberLauchInfoVO other = (MemberLauchInfoVO) obj;
		if (descriptionDetail == null) {
			if (other.descriptionDetail != null)
				return false;
		} else if (!descriptionDetail.equals(other.descriptionDetail))
			return false;
		if (descriptionSimple == null) {
			if (other.descriptionSimple != null)
				return false;
		} else if (!descriptionSimple.equals(other.descriptionSimple))
			return false;
		if (phoneNum == null) {
			if (other.phoneNum != null)
				return false;
		} else if (!phoneNum.equals(other.phoneNum))
			return false;
		if (serviceNum == null) {
			if (other.serviceNum != null)
				return false;
		} else if (!serviceNum.equals(other.serviceNum))
			return false;
		return true;
	}

	public String getDescriptionSimple() {
		return descriptionSimple;
	}

	public void setDescriptionSimple(String descriptionSimple) {
		this.descriptionSimple = descriptionSimple;
	}

	public String getDescriptionDetail() {
		return descriptionDetail;
	}

	public void setDescriptionDetail(String descriptionDetail) {
		this.descriptionDetail = descriptionDetail;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
