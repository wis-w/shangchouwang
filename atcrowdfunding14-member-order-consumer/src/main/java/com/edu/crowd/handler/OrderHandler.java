package com.edu.crowd.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.util.ResultEntity;
import com.edu.crowd.api.MySQLRemotrService;
import com.edu.crowd.entity.vo.AddressVO;
import com.edu.crowd.entity.vo.MemberLoginVO;
import com.edu.crowd.entity.vo.OrderProjectVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OrderHandler {

	@Autowired
	MySQLRemotrService mySQLRemotrService;
	
	/**
	 * 保存用户的收货地址
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping("/save/address")
	public String saveAddress(AddressVO vo, HttpSession session) {
		log.info(vo.toString());
		
		ResultEntity<String> entity = mySQLRemotrService.saveAddress(vo);
		
		log.info("保存结果："+entity.getResult());
		
		OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
		
		Integer returnCount = orderProjectVO.getReturnCount();
		
		return "redirect:http://localhost/order/confirm/order/"+returnCount;// 进行重定向，使页面进行刷新
	}
	
	/**
	 * 回报信息支持
	 * 
	 * @param projectId
	 * @param returnId
	 * @return
	 */
	@RequestMapping("/confirm/return/info/{returnId}")
	public String showReturnConfirmInfo(@PathVariable("returnId") Integer returnId, HttpSession session) {
		ResultEntity<OrderProjectVO> resultEntity = mySQLRemotrService.getOrderProjectVORemote(returnId);

		if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
			session.setAttribute("orderProjectVO", resultEntity.getData());// 存放在session域
		}

		return "confirm_return";
	}

	/**
	 * 确认订单
	 * 
	 * @param returnCount 回报数量
	 * @param session
	 * @return
	 */
	@RequestMapping("confirm/order/{returnCount}")
	public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount, HttpSession session) {
		// 把接收到的回报数量合并到Session域
		OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
		orderProjectVO.setReturnCount(returnCount);
		session.setAttribute("orderProjectVO", orderProjectVO);
		
		// 根据登录的用户，查询目前的收获地址
		MemberLoginVO loginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.LOGIN_MEMBER);
		ResultEntity<List<AddressVO>> resultEntity = mySQLRemotrService.getAddressVORemote(loginVO.getId());
		
		if(resultEntity.getResult().equals(ResultEntity.SUCCESS)) {
			session.setAttribute("addressVOList", resultEntity.getData());
		}
		
		
		return "confirm_order";

	}

}
