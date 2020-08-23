package com.edu.crowd.service.api;
import java.util.List;

import com.edu.crowd.entity.Menu;


public interface MenuService {
	
	List<Menu> getAll();

	void addMenu(Menu menu);

	void removeNode(Integer id);

	void editMenu(Menu menu);

}
