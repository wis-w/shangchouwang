package com.edu.crowd.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.po.MemberPO;
import com.edu.crowd.entity.vo.AddressVO;
import com.edu.crowd.entity.vo.DetailProjectVO;
import com.edu.crowd.entity.vo.OrderProjectVO;
import com.edu.crowd.entity.vo.OrderVO;
import com.edu.crowd.entity.vo.ProjectVO;
import com.edu.crowd.entity.vo.ProtalTypeVO;

/**
 * @author wyg_edu
 * @date 2020年6月22日 下午8:37:44
 * @version v1.0
 */
@FeignClient("com-crowd-mysql")
public interface MySQLRemotrService {
	
	
	@RequestMapping("/get/getmemberpo/by/login/acct/remote")
	ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct")String loginacct);
	
	@RequestMapping("/save/member/remote")
	public ResultEntity<String> saveRemote(@RequestBody MemberPO memberPO);

	@RequestMapping("/save/project/vo/remote")
	ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVo, @RequestParam("remoteId") Integer id);
	
	@RequestMapping("/get/protal/type/project/data/remote")
	ResultEntity<List<ProtalTypeVO>> getProtalTypeProjectDataRemote();
	
	@RequestMapping("/get/project/datail/remote/{projectId}")
	public ResultEntity<DetailProjectVO> getDetailProjectVO(@PathVariable("projectId") Integer projectId);

	@RequestMapping("/get/order/project/vo/remote")
	ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId")Integer returnId);

	@RequestMapping("/get/address/vo/remote")
	ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("id") Integer id);

	@RequestMapping("/save/address")
	ResultEntity<String> saveAddress(@RequestBody AddressVO vo);

	@RequestMapping("/save/order/remote")
	ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);
	
}
