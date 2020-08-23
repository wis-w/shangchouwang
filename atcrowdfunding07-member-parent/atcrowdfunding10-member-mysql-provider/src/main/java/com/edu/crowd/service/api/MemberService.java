package com.edu.crowd.service.api;

import com.edu.crowd.entity.po.MemberPO;

/**
 * @author wyg_edu
 * @date 2020年6月22日 下午8:46:11
 * @version v1.0
 */
public interface MemberService {

	MemberPO getMemberPOByLoginAcct(String loginacct);

	void saveMember(MemberPO memberPO);

}
