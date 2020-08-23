package com.edu.crowd.mvc.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.Menu;
import com.edu.crowd.service.api.MenuService;

/**
 * @author wyg_edu
 * @date 2020年5月24日 上午11:14:21
 * @version v1.0
 */
//@Controller
@RestController // @RestController=@Controller+ @ResponseBody
public class MenuHandler {
	
	Logger log = LoggerFactory.getLogger(MenuHandler.class);
	
	@Autowired
	MenuService menuService;
	
	/**
	 * 修改节点
	 * @return
	 */
	// @ResponseBody
	@RequestMapping("/menu/edit.json")
	public ResultEntity<String> editMenu(Menu menu) {
		menuService.editMenu(menu);
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 删除节点
	 * @param menu
	 * @return
	 */
	// @ResponseBody
	@RequestMapping("/menu/remove.json")
	public ResultEntity<String> removeNode(Menu menu){
		menuService.removeNode(menu.getId());
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 增加节点操作
	 * @param menu
	 * @return
	 */
	// @ResponseBody
	@RequestMapping("/menu/save.json")
	public ResultEntity<String> addMenu(Menu menu) {
		menuService.addMenu(menu);
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 获取所有的节点信息
	 * @return
	 */
	// @ResponseBody
	@RequestMapping("/menu/get/whole/tree.json")
	public ResultEntity<Menu> getWholeTreeNew() {
		// 1、查询所有Menu对象
		List<Menu> menuList = menuService.getAll();

		// 2、声明一个变量作为存储的根节点
		Menu root = null;
		
		// 3、创建map来存储id与menu的对应关系，便于查找父节点
		Map<Integer, Menu> menuMap=new HashMap<Integer, Menu>();
		
		// 4、遍历menuList，填充MenuMap
		for(Menu menu:menuList) {
			menuMap.put(menu.getId(), menu);
		}
		
		// 5、再次遍历menuList:
		for(Menu menu:menuList) {
			// 6、获取当前对象的Pid
			Integer pId = menu.getPid();
			
			// 7、如果pid为null，则认定该节点为根节点，赋值为root
			if(pId == null) {
				root =menu;
				continue;// 根节点不存在父节点，因此跳过该循环
			}
			
			// 8、如果Pid不为bull说明该节点部位父节点，根据pid查找menu对象
			Menu father = menuMap.get(pId);
			// 9、把当前节点存入到父节点的集合
			father.getChildren().add(menu);
			
		}
		// 10、以上运算包含了整个属性结构以root进行返回
		return ResultEntity.successWithData(root);
		
	}
	
	// @ResponseBody
	@RequestMapping("/menu/get/whole/tree/old")
	public ResultEntity<Menu> getWholeTreeOld(){
		// 1、查询所有Menu对象
		List<Menu> menuList = menuService.getAll();
		
		// 2、声明一个变量作为存储的根节点
		Menu root = null;
		
		// 3、创建Map对象来存储id和Menu对象的对应关系便于查找父节点
//		Map<Integer,Menu> menuMap = new HashMap();
		
		// 4、遍历list填充menuMap
		for(Menu menu:menuList) {
			
			// 获取当前节点的Pid
			Integer pid = menu.getPid();
			
			// 检查pid是否为空
			if(pid == null) {
				// 把当前正在遍历的menu对象赋值给root
				root = menu;
				
				// 停止本次循环，继续下次循环
				continue;
			}
			
			// 如果pid不是null，则查询给节点的父节点，然后进行组装父子关系
			for(Menu mayBeFather: menuList) {
				// 获取疑似父节点的id
				Integer id = mayBeFather.getId();
				
				// 取子节点的pid与父节点的id比较
				if(Objects.equals(pid, id)) {
					// 将子节点添加到父节点的children集合中
					mayBeFather.getChildren().add(menu);
					
					// 找到子接点后推出循环(一对一关系)
					break;
				}
			}
		}
		// 将组装好的数据结构进行返回给浏览器
		return ResultEntity.successWithData(root);
		
	}

}
