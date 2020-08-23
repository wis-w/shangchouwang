package com.edu.crowd.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.po.MemberPO;
import com.edu.crowd.service.api.MemberService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wyg_edu
 * @date 2020年6月22日 下午8:43:48
 * @version v1.0
 */
@RestController
@Slf4j
public class MemberProviderHandler {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 进行用户保存
	 * @param memberPO
	 * @return
	 */
	@RequestMapping("/save/member/remote")
	public ResultEntity<String> saveRemote(@RequestBody MemberPO memberPO){
		try {
			memberService.saveMember(memberPO);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			if(e instanceof DuplicateKeyException) {
				// 唯一约束冲突的异常
				return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	/**
	 * 查询是否出存在该用户
	 * @param loginacct
	 * @return
	 */
	@RequestMapping("/get/getmemberpo/by/login/acct/remote")
	public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct")String loginacct){
		log.info("进入mysql模块进行用户登录查询");
		try {
			// 调用本地查询
			MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
			// 返回成功结果
			return ResultEntity.successWithData(memberPO);
		} catch (Exception e) {
			// 返回失败的结果
			return ResultEntity.failed(e.getMessage());
		}
		
	}

}
