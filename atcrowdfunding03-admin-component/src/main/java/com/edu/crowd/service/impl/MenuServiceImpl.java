package com.edu.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.crowd.entity.Menu;
import com.edu.crowd.entity.MenuExample;
import com.edu.crowd.mapper.MenuMapper;
import com.edu.crowd.service.api.MenuService;

/**
 * @author wyg_edu
 * @date 2020年5月24日 上午11:13:44
 * @version v1.0
 */
@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	MenuMapper menuMapper;

	/**
	 * 获取节点树
	 */
	@Override
	public List<Menu> getAll() {
		return menuMapper.selectByExample(new MenuExample());
	}

	/**
	 * 增加节点
	 */
	@Override
	public void addMenu(Menu menu) {
		menuMapper.insert(menu);
	}

	/**
	 * 删除节点
	 */
	@Override
	public void removeNode(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改节点
	 */
	@Override
	public void editMenu(Menu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
	}

}
