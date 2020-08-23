/**
 * 刷新树形结构
 * @returns
 */
function generateTree(){
	$.ajax({
		"url":"get/auth/list.json",
		"data":"post",
		"dataType":"json",
		"success":function(response){
			var result = response.result;
			if(result=="SUCCESS"){
				// 获取json对象
				window.window.authList = response.data;
				
				// 将原有的根节点定义为虚拟顶级节点的节点
				for(var i=0;i<window.authList.length;i++){
					if(window.authList[i].categoryId==undefined && window.authList[i].id !=0){
						window.authList[i].categoryId=0;
					}
				}
				var rootNode={
						"id":0,
						"title":"根节点"
				}
				window.authList.push(rootNode);
				
				// 准备给ztree设置的接送数据
				var setting={
						"view":{
							"addHoverDom":myHoverDom,// 鼠标停留属性
							"removeHoverDom":removeHoverDom// 鼠标移开属性
						},
						"data":{
							"simpleData":{
								// 开启简单json功能
								"enable":true,
								// 使用categoryId属性关联父节点
								"pIdKey":"categoryId"
							},
							"key":{
								// 使用title作为节点属性
								"name":"title"
							}
						}
				};
				// 使用zTree显示json数据
				$.fn.zTree.init($("#authTreeDemo"),setting,window.authList);
				
				// 获取ZtreeObj对象
				var zTreeObj=$.fn.zTree.getZTreeObj("authTreeDemo");
				zTreeObj.expandAll(true);
				
			}
			if(result=="FAILED"){
				layer.msg("操作失败！"+response.message);
			}
		},
		"error":function(response){
			layer.msg(response.states+" "+response.statesText);
		}
	});
}

/**
 * 鼠标停留属性
 * treeNode : 节点的属性值
 * @returns
 */
function myHoverDom(treeId,treeNode){	
	
	var nodeId = treeNode.id+1;
	
	// 为了在需要移出按钮组精确定定位到span，应该设置id属性
	var btnGroupId = nodeId;
	
	// 判断之前是否已经添加了按钮组
	if($("#"+btnGroupId).length > 0){
		return ;
	}
 	
	// 准备个个按钮的html
	var editBtn = "<a id='"+nodeId+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
	var removeBtn="<a id='"+nodeId+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点' >&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
	var addBtn=   "<a id='"+nodeId+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='增加节点' >&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
	
	// 声明变量，存储拼接好的按钮
	var btnHTML = "";
	
	
	// 二级的菜单只允许被增加与修改
	if(treeNode.categoryId != 0){
		btnHTML = btnHTML+editBtn+removeBtn;
	}else{
		window.fatherId=treeNode.id;
		if(havaChildren()){
			// 一级节点有子元素不允许直接删除，可以增加、修改
			btnHTML=btnHTML+editBtn+addBtn;
		}else{
			// 一级节点没有子元素可以允许删除、增加、修改
			btnHTML=btnHTML+editBtn+addBtn+removeBtn;
		}
	}
	
	// 虚拟根节点不被允许修改删除,只允许被增加节点
	if(treeNode.id==0){
		btnHTML=""+addBtn;
	}
	
	// 在超链接后面追加按钮     authTreeDemo_7_span
	var anchorId = treeNode.tId+"_a";
	
	// 在超链接后后面添加span属性
	$("#"+anchorId).after("<span id = '"+btnGroupId+"'>"+btnHTML+"</span>")
	
}

/**
 * 鼠标移开属性
 * @returns
 */
function removeHoverDom(treeId,treeNode){
	// 拼接按钮组的ID
	var btnGroupId = treeNode.id+1;
	
	// 移出对应的按钮组 
	$("#"+btnGroupId).remove();
}

/**
 * 查询节点是否有子节点
 * @returns
 */
function havaChildren(){
	
	for(var a = 0;a<window.authList.length;a++){
		// 判断节点中的categoryId属性有与id属性相同，代表该节点是选中元素的直接点
		if(window.fatherId==window.authList[a].categoryId){
			return true;
		}
	}
	return false;	
}