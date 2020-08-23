package com.edu.crowd.entity.vo;

public class DetailReturnVO {

	// 回报信息主键
	private Integer returnId;
	// 当前挡位支持的金额
	private Integer supportMoney;
	// 单笔限购0：不限购，1限额
	private Integer singnalPurchase;
	// 限额数量
	private Integer purchase;
	// 当前挡位支持者数量
	private Integer supporterCount;
	// 运费 0：包邮
	private Integer freight;
	// 众筹成功后的发货时间
	private Integer returnDate;
	// 回报的内容
	private String content;

	public Integer getReturnId() {
		return returnId;
	}

	public DetailReturnVO(Integer returnId, Integer supportMoney, Integer singnalPurchase, Integer purchase,
			Integer supporterCount, Integer freight, Integer returnDate, String content) {
		super();
		this.returnId = returnId;
		this.supportMoney = supportMoney;
		this.singnalPurchase = singnalPurchase;
		this.purchase = purchase;
		this.supporterCount = supporterCount;
		this.freight = freight;
		this.returnDate = returnDate;
		this.content = content;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}

	public Integer getSupportMoney() {
		return supportMoney;
	}

	public void setSupportMoney(Integer supportMoney) {
		this.supportMoney = supportMoney;
	}

	public Integer getSingnalPurchase() {
		return singnalPurchase;
	}

	public void setSingnalPurchase(Integer singnalPurchase) {
		this.singnalPurchase = singnalPurchase;
	}

	public Integer getSupporterCount() {
		return supporterCount;
	}

	public void setSupporterCount(Integer supporterCount) {
		this.supporterCount = supporterCount;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public Integer getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Integer returnDate) {
		this.returnDate = returnDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DetailReturnVO(Integer returnId, Integer supportMoney, Integer singnalPurchase, Integer supporterCount,
			Integer freight, Integer returnDate, String content) {
		super();
		this.returnId = returnId;
		this.supportMoney = supportMoney;
		this.singnalPurchase = singnalPurchase;
		this.supporterCount = supporterCount;
		this.freight = freight;
		this.returnDate = returnDate;
		this.content = content;
	}

	public DetailReturnVO() {
		super();
	}

	@Override
	public String toString() {
		return "DetailReturnVO [returnId=" + returnId + ", supportMoney=" + supportMoney + ", singnalPurchase="
				+ singnalPurchase + ", supporterCount=" + supporterCount + ", freight=" + freight + ", returnDate="
				+ returnDate + ", content=" + content + "]";
	}

}
