package com.edu.crowd.entity.vo;

import java.util.List;

public class ProtalTypeVO {

	private Integer id;
	private String name;
	private String remark;
	private List<PraralProjectVo> praralProjectVo;
	public ProtalTypeVO() {
	}
	public ProtalTypeVO(Integer id, String name, String remark, List<PraralProjectVo> praralProjectVo) {
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.praralProjectVo = praralProjectVo;
	}
	@Override
	public String toString() {
		return "ProtalTypeVO [id=" + id + ", name=" + name + ", remark=" + remark + ", praralProjectVo="
				+ praralProjectVo + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<PraralProjectVo> getPraralProjectVo() {
		return praralProjectVo;
	}
	public void setPraralProjectVo(List<PraralProjectVo> praralProjectVo) {
		this.praralProjectVo = praralProjectVo;
	}
}
