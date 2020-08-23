/**
 * 修改默认的图标
 * @param treeId
 * @param treeNode
 * @returns
 */
function myAddDiyDom(treeId,treeNode){
	// console.log(treeId);// 树形结构附着的静态结构的id属性
	// console.log(treeNode);// 当前树形的节点全部数据，包括后端查询的全部menu属性
	
	// zTree生成id的规则是 treeId_+当前节点的序号+功能(_ico)：treeDome_7_ico
	// 当前节点的序号:当前节点的pId
	// 根据id的生成规则拼接成span的id
	// 根据控制突变的span标签的id找到span标签
	var spanId = treeNode.tId+"_ico"
	// 删除旧的class属性
	// 添加新的class属性
	$("#"+spanId).removeClass().addClass(treeNode.icon);

}

/**
 * 在鼠标移入节点范围时，添加按钮组
 * @param treeId
 * @param treeNode
 * @returns
 */
function myHoverDom (treeId,treeNode){
	// 按钮组结构<span><a><i><a/></i></a></span>
	// 按钮组出现的位置是在节点treeDemo_n_a超链接的后面
	
	// 为了在需要移出按钮组精确定定位到span，应该设置id属性
	var btnGroupId = treeNode.tId + "_btnGrp";
	
	// 判断之前是否已经添加了按钮组
	if($("#"+btnGroupId).length > 0){
		return ;
	}
	
	// 准备个个按钮的html
	var editBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
	var removeBtn="<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除子接点' >&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
	var addBtn=   "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='增加子节点' >&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
	
	// 获取当前节点的级别
	var level = treeNode.level;
	
	
	// 声明变量，存储拼接好的按钮
	var btnHTML = "";
	
	// 判断当前节点的级别
	// 级别为0是根节点只能进行添加
	if(level == 0){
		btnHTML = addBtn;
	}
	
	// 级别是1是分子节点，可以进行增加修改，如果没有子节点可以删除
	if(level == 1){
		btnHTML = addBtn + " " + editBtn;
		// 判断当前节点是否有子节点
		var length = treeNode.children.length;
		if(length == 0){
			btnHTML = btnHTML + "  " + removeBtn;
		}
	}
	
	// 级别为2是子节点，可以删除、修改
	if(level == 2){
		btnHTML = editBtn + " " + removeBtn;
	}
	
	// 在超链接后面追加按钮
	var anchorId = treeNode.tId+"_a";
	// 在超链接后后面添加span属性
	$("#"+anchorId).after("<span id = '"+btnGroupId+"'>"+btnHTML+"</span>")
			
}

/**
 * 在鼠标离开节点范围时，删除按钮组
 * @param treeId
 * @param treeNode
 * @returns
 */
function removeHoverDom (treeId,treeNode){
	
	// 拼接按钮组的ID
	var btnGroupId = treeNode.tId + "_btnGrp";
	
	// 移出对应的按钮组 
	$("#"+btnGroupId).remove();
}

/**
 * 生成属性结构函数
 * @returns
 */
function generateTree(){
	// 1、设置节点数据
	$.ajax({
		"url":"menu/get/whole/tree.json",
		"type":"post",
		"dataType":"json",
		"success":function(response){
			var result = response.result;
			if(result == "SUCCESS"){
				// 2、创建json对象，用于存储zTree所做的设置
				var setting = {
						"view":{
							"addDiyDom":myAddDiyDom,// 自定义属性
							"addHoverDom":myHoverDom,// 鼠标停留属性
							"removeHoverDom":removeHoverDom// 鼠标移开属性
						},
						"data":{
							"key":{
								"url":"no_url"// 定义不存在的属性，使点击节点不进行跳转
							}
						}
				};
				var zNodes = response.data;
				
				// 3、初始化节点数据
				$.fn.zTree.init($("#treeDemo"),setting,zNodes);
			}
			if(result == "FAILED"){
				layer.msg(response.message);
			}
		},
		"error":function(response){
			layer.msg(response.message);
		}
	});
}