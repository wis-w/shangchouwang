package com.edu.crowd.service.api;

import java.util.List;

import com.edu.crowd.entity.vo.AddressVO;
import com.edu.crowd.entity.vo.OrderProjectVO;
import com.edu.crowd.entity.vo.OrderVO;

public interface OrderService {

	OrderProjectVO getOrderProjectVO(Integer returnId);

	List<AddressVO> getAddressVO(Integer id);

	void saveAddress(AddressVO vo);

	void saveOrderVO(OrderVO orderVO);

}
