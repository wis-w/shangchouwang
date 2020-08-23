package com.edu.crowd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.crowd.entity.po.AddressPO;
import com.edu.crowd.entity.po.AddressPOExample;
import com.edu.crowd.entity.po.OrderPO;
import com.edu.crowd.entity.po.OrderProjectPO;
import com.edu.crowd.entity.vo.AddressVO;
import com.edu.crowd.entity.vo.OrderProjectVO;
import com.edu.crowd.entity.vo.OrderVO;
import com.edu.crowd.mapper.AddressPOMapper;
import com.edu.crowd.mapper.OrderPOMapper;
import com.edu.crowd.mapper.OrderProjectPOMapper;
import com.edu.crowd.service.api.OrderService;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderProjectPOMapper orderProjectPOMapper;
	
	@Autowired
	private OrderPOMapper orderPOMapper;

	@Autowired
	private AddressPOMapper addressPOMapper;

	@Override
	public OrderProjectVO getOrderProjectVO(Integer returnId) {
		
		return orderProjectPOMapper.selectOrderProjectVO(returnId);
	}

	@Override
	public List<AddressVO> getAddressVO(Integer id) {
		AddressPOExample addressPOExample = new AddressPOExample();
		
		addressPOExample.createCriteria().andMemberIdEqualTo(id);// 创建id=value实例
		
		List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);
		
		List<AddressVO> addressVOList = new ArrayList<AddressVO>();// 页面上要用VO显示，PO代表数据库显示逻辑的
		
		// 实现元素的复制
		for (AddressPO addressPO : addressPOList) {
			AddressVO addressVO = new AddressVO();
			BeanUtils.copyProperties(addressPO, addressVO);
			addressVOList.add(addressVO);
		}
		
		return addressVOList;
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveAddress(AddressVO vo) {
		AddressPO addressPO = new AddressPO();
		BeanUtils.copyProperties(vo, addressPO);
		addressPOMapper.insert(addressPO);
		
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveOrderVO(OrderVO orderVO) {
		OrderPO orderPO = new OrderPO();
		BeanUtils.copyProperties(orderVO, orderPO);// 进行对象的复制
		
		OrderProjectPO orderProjectPO = new OrderProjectPO();
		BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);// 进行对象的复制
		
		orderPOMapper.insert(orderPO);
		
		orderProjectPO.setOrderId(orderPO.getId());// 保存外键
		
		orderProjectPOMapper.insert(orderProjectPO);
		
	}
}












