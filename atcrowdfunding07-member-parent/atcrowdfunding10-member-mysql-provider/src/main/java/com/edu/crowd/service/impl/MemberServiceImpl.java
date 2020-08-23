package com.edu.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.crowd.entity.po.MemberPO;
import com.edu.crowd.entity.po.MemberPOExample;
import com.edu.crowd.entity.po.MemberPOExample.Criteria;
import com.edu.crowd.mapper.MemberPOMapper;
import com.edu.crowd.service.api.MemberService;

/**
 * @author wyg_edu
 * @date 2020年6月22日 下午8:47:30
 * @version v1.0
 */
@Service
@Transactional(readOnly = true)// 声明式事务 针对查询操作设置事务属性
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberPOMapper mapperPOMapper;

	/**
	 * 根据登录名查询用户
	 */
	@Override
	public MemberPO getMemberPOByLoginAcct(String loginacct) {
		// 创建Example对象
		MemberPOExample memberPOExample = new MemberPOExample();
		// 创建Criteria对象
		Criteria createCriteria = memberPOExample.createCriteria();
		// 封装查询对象
		createCriteria.andLoginacctEqualTo(loginacct);
		// 进行查询
		List<MemberPO> selectByExample = mapperPOMapper.selectByExample(memberPOExample);
		
		if(selectByExample == null || selectByExample.size()==0) {
			return null;
		}
		return selectByExample.get(0);
	}

	/**
	 * 保存对象
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)// 新启事务，设置非只读
	@Override
	public void saveMember(MemberPO memberPO) {
		mapperPOMapper.insertSelective(memberPO);
	}

}
