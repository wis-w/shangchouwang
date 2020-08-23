package com.edu.crowd.entity.vo;

/**
 * @author wyg_edu
 * @date 2020年7月26日 下午2:08:23
 * @version v1.0
 */

import java.io.Serializable;

public class MemberConfirmInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 易付宝账号
	private String paynum;
	// 法人身份证号
	private String cardnum;

	public String getPaynum() {
		return paynum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardnum == null) ? 0 : cardnum.hashCode());
		result = prime * result + ((paynum == null) ? 0 : paynum.hashCode());
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
		MemberConfirmInfoVO other = (MemberConfirmInfoVO) obj;
		if (cardnum == null) {
			if (other.cardnum != null)
				return false;
		} else if (!cardnum.equals(other.cardnum))
			return false;
		if (paynum == null) {
			if (other.paynum != null)
				return false;
		} else if (!paynum.equals(other.paynum))
			return false;
		return true;
	}

	public MemberConfirmInfoVO() {
	}

	public MemberConfirmInfoVO(String paynum, String cardnum) {
		this.paynum = paynum;
		this.cardnum = cardnum;
	}

	@Override
	public String toString() {
		return "MemberConfirmInfoVO [paynum=" + paynum + ", cardnum=" + cardnum + "]";
	}

	public void setPaynum(String paynum) {
		this.paynum = paynum;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
