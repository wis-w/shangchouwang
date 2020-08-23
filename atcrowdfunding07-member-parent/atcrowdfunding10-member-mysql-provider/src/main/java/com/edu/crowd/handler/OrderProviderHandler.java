package com.edu.crowd.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.vo.AddressVO;
import com.edu.crowd.entity.vo.OrderProjectVO;
import com.edu.crowd.entity.vo.OrderVO;
import com.edu.crowd.service.api.OrderService;

@RestController
public class OrderProviderHandler {
	
	
	private static final Logger log = LoggerFactory.getLogger(OrderProviderHandler.class);
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 支付后的保存信息
	 * @param orderVO
	 * @return
	 */
	@RequestMapping("/save/order/remote")
	ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO){
		try {
			orderService.saveOrderVO(orderVO);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	/**
	 * 收货地址保存
	 * @param vo
	 * @return
	 */
	@RequestMapping("/save/address")
	public ResultEntity<String> saveAddress(@RequestBody AddressVO vo){
		
		try {
			orderService.saveAddress(vo);
			log.info("保存成功");
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	/**
	 * 回报信息查询
	 * @param projectId
	 * @param returnId
	 * @return
	 */
	@RequestMapping("/get/order/project/vo/remote")
	ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId")Integer returnId){
		try {
			OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(returnId);
			return ResultEntity.successWithData(orderProjectVO);
		} catch (Exception e) {
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	/**
	 * 地址查询
	 * @param remoteId
	 * @return
	 */
	@RequestMapping("/get/address/vo/remote")
	ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("id") Integer id){
		try {
			List<AddressVO> list = orderService.getAddressVO(id);
			return ResultEntity.successWithData(list);
		} catch (Exception e) {
			return ResultEntity.failed(e.getMessage());
		}
	}
	
}
