package com.edu.crowd.entity.vo;

public class PraralProjectVo {

	private Integer id;
	private String projectName;
	private String headerPicturePath;
	private Integer money;
	private String deployDate;
	private Integer percentage;
	private Integer supporter;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getHeaderPicturePath() {
		return headerPicturePath;
	}

	public void setHeaderPicturePath(String headerPicturePath) {
		this.headerPicturePath = headerPicturePath;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getDeployDate() {
		return deployDate;
	}

	public void setDeployDate(String deployDate) {
		this.deployDate = deployDate;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public Integer getSupporter() {
		return supporter;
	}

	public void setSupporter(Integer supporter) {
		this.supporter = supporter;
	}

	public PraralProjectVo(Integer id, String projectName, String headerPicturePath, Integer money, String deployDate,
			Integer percentage, Integer supporter) {
		this.id = id;
		this.projectName = projectName;
		this.headerPicturePath = headerPicturePath;
		this.money = money;
		this.deployDate = deployDate;
		this.percentage = percentage;
		this.supporter = supporter;
	}

	public PraralProjectVo() {
	}

	@Override
	public String toString() {
		return "PraralProjectVo [id=" + id + ", projectName=" + projectName + ", headerPicturePath=" + headerPicturePath
				+ ", money=" + money + ", deployDate=" + deployDate + ", percentage=" + percentage + ", supporter="
				+ supporter + "]";
	}

}
