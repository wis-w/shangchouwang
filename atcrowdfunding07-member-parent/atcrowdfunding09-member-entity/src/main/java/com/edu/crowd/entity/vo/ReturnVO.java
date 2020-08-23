package com.edu.crowd.entity.vo;

import java.io.Serializable;

/**
 * @author wyg_edu
 * @date 2020年7月26日 下午2:07:42
 * @version v1.0
 */
public class ReturnVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 回报类型：0 - 实物回报， 1 虚拟物品回报
	private Integer type;
	// 支持金额
	private Integer supportmoney;
	// 回报内容介绍
	private String content;
	// 总回报数量，0 为不限制
	private Integer count;
	// 是否限制单笔购买数量，0 表示不限购，1 表示限购
	private Integer signalpurchase;
	// 如果单笔限购，那么具体的限购数量
	private Integer purchase;
	// 运费，“0”为包邮
	private Integer freight;
	// 是否开发票，0 - 不开发票， 1 - 开发票
	private Integer invoice;
	// 众筹结束后返还回报物品天数
	private Integer returndate;
	// 说明图片路径
	private String describPicPath;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((describPicPath == null) ? 0 : describPicPath.hashCode());
		result = prime * result + ((freight == null) ? 0 : freight.hashCode());
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((purchase == null) ? 0 : purchase.hashCode());
		result = prime * result + ((returndate == null) ? 0 : returndate.hashCode());
		result = prime * result + ((signalpurchase == null) ? 0 : signalpurchase.hashCode());
		result = prime * result + ((supportmoney == null) ? 0 : supportmoney.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ReturnVO other = (ReturnVO) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (describPicPath == null) {
			if (other.describPicPath != null)
				return false;
		} else if (!describPicPath.equals(other.describPicPath))
			return false;
		if (freight == null) {
			if (other.freight != null)
				return false;
		} else if (!freight.equals(other.freight))
			return false;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (purchase == null) {
			if (other.purchase != null)
				return false;
		} else if (!purchase.equals(other.purchase))
			return false;
		if (returndate == null) {
			if (other.returndate != null)
				return false;
		} else if (!returndate.equals(other.returndate))
			return false;
		if (signalpurchase == null) {
			if (other.signalpurchase != null)
				return false;
		} else if (!signalpurchase.equals(other.signalpurchase))
			return false;
		if (supportmoney == null) {
			if (other.supportmoney != null)
				return false;
		} else if (!supportmoney.equals(other.supportmoney))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public ReturnVO() {
	}

	public ReturnVO(Integer type, Integer supportmoney, String content, Integer count, Integer signalpurchase,
			Integer purchase, Integer freight, Integer invoice, Integer returndate, String describPicPath) {
		this.type = type;
		this.supportmoney = supportmoney;
		this.content = content;
		this.count = count;
		this.signalpurchase = signalpurchase;
		this.purchase = purchase;
		this.freight = freight;
		this.invoice = invoice;
		this.returndate = returndate;
		this.describPicPath = describPicPath;
	}

	@Override
	public String toString() {
		return "ReturnVO [type=" + type + ", supportmoney=" + supportmoney + ", content=" + content + ", count=" + count
				+ ", signalpurchase=" + signalpurchase + ", purchase=" + purchase + ", freight=" + freight
				+ ", invoice=" + invoice + ", returndate=" + returndate + ", describPicPath=" + describPicPath + "]";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSupportmoney() {
		return supportmoney;
	}

	public void setSupportmoney(Integer supportmoney) {
		this.supportmoney = supportmoney;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSignalpurchase() {
		return signalpurchase;
	}

	public void setSignalpurchase(Integer signalpurchase) {
		this.signalpurchase = signalpurchase;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public Integer getInvoice() {
		return invoice;
	}

	public void setInvoice(Integer invoice) {
		this.invoice = invoice;
	}

	public Integer getReturndate() {
		return returndate;
	}

	public void setReturndate(Integer returndate) {
		this.returndate = returndate;
	}

	public String getDescribPicPath() {
		return describPicPath;
	}

	public void setDescribPicPath(String describPicPath) {
		this.describPicPath = describPicPath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
