/**
 * 分配权限的数据加载函数
 * @returns
 */
function fillAuthTree(){
	// 发送ajax请求auth数据
	var ajaxReturn = $.ajax({
		"url":"assign/get/all/auth.json",
		"type":"post",
		"dataType":"json",
		"async":false
	});
	
	if(ajaxReturn.status!=200){
		layer.msg("请求处理出错！！响应状态码是"+ajaxReturn.status+" 说明"+ajaxReturn.statesText);
		return ;
	}
	
	// 获取返回的json数据
	var authList = ajaxReturn.responseJSON.data;
	
	// 准备给ztree设置的接送数据
	var setting={
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
			},
			"check":{
				"enable":true
			}
	};
	
	// 生成属性结构  <ul id = "authTreeDemo" class="ztree"></ul>
	$.fn.zTree.init($("#authTreeDemo"),setting,authList);
	

	// 调用zTreeObj对象，使节点展开
	var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
	zTreeObj.expandAll(true);
	
	// 查询已分配的auth的id组成的list
	ajaxReturn = $.ajax({
		"url":"assign/get/assigned/auth/id.json",
		"type":"post",
		"dataType":"json",
		"async":false,
		"data":{
			"roleId":window.id,
		}
	});
	
	if(ajaxReturn.status!=200){
		layer.msg("请求处理出错！！响应状态码是"+ajaxReturn.status+" 说明"+ajaxReturn.statesText);
		return ;
	}
	// 从响应结果中获取角色的权限列表
	var authIdList = ajaxReturn.responseJSON.data;
	
	// 根据authId的数组把树形结构中对应的节点进行勾选
	// 遍历数组
	for(var a = 0;a<authIdList.length;a++){
		var authId = authIdList[a];
		// 根据id查询书信钢结构对应的节点
		var treeNode = zTreeObj.getNodeByParam("id",authId);
		// 将treeNode设置为被勾选
		zTreeObj.checkNode(treeNode,true,false);// 当前节点，节点勾选，不启用联动
	}
	
}

/**
 * 显示删除确认的模态框
 * @param roleArray
 * @returns
 */
function showCondirModal(roleArray){
	// 显示模态框
	$("#confirmModal").modal("show");
	// 旧的数据清理
	$("#roleNameDiv").empty();
	
	// 在全局变量函数中创建数组存储id
	window.roleIdArray = [];
	
	// 遍历数组
	for(var i=0;i<roleArray.length;i++){
		var role = roleArray[i];
		var roleName=role.roleName;
		$("#roleNameDiv").append(roleName+"<br/>");
		// 调用数组的push()存储id
		window.roleIdArray.push(role.roleId);
	}
}

/**
 * 执行分业，生成页面效果
 * @returns
 */
function generatePage(){
	
	// 1、获取分页数据
	var pageInfo = getPageInfoRemote();
	
	// 2、填充表格
	fillTableBody(pageInfo);
	
	// 3、将复选框设置为未选中状态
	$("#summaryBtn").prop("checked",false);
}

/**
 * 获取pageInfo数据
 * @returns
 */
function getPageInfoRemote(){
	// 调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
	var ajaxResult = $.ajax({
		"url": "role/get/page/info.json",
		"type":"post",
		"data": {
			"pageNum": window.pageNum,
			"pageSize": window.pageSize,
			"keyWord": window.keyWord
		},
		"async":false,
		"dataType":"json"
	});
	
	// 判断当前响应状态码是否为200
	var statusCode = ajaxResult.status;
	
	// 如果当前响应状态码不是200，说明发生了错误或其他意外情况，显示提示消息，让当前函数停止执行
	if(statusCode != 200) {
		layer.msg("失败！响应状态码="+statusCode+" 说明信息="+ajaxResult.statusText);
		return null;
	}
	
	// 如果响应码是200则代表成功 获取pageInfo
	var resultEntity = ajaxResult.responseJSON;
	
	// 从resultEntity获取result属性
	var result = resultEntity.result;
	
	// 判断result是否成功
	if(result == "FILED"){
		layer.msg(resultEntity.message);
		return null;
	}
	
	// 确认result为成功后pageInfo，返回pageInfo
	return resultEntity.data;
}

/**
 * 填充表格
 * @param pageInfo
 * @returns
 */
function fillTableBody(pageInfo){
	
	
	// 清除tBody的旧数据
	$("#rolePageBody").empty();
	// 没有结果时清除导航条
	$("#Pagination").empty();
	
	// 判断pageinfo对象是否有效
	if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
		$("#rolePageBody").append("<tr><td colspan='4'>抱歉，没有查询到你需要的数据</td></tr>");
		return;
	}
	
	// 使用pageInfo填充tBody部分
	for(var i=0; i<pageInfo.list.length; i++){
		var role = pageInfo.list[i];
		var roleId = role.id;
		var roleName = role.name;
		
		var nunmberId="<td>"+(i+1)+"</td>";
		var checkboxId="<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
		var roleName = "<td>"+roleName+"</td>";
		
		// 通过button将id进行绑定，在单机响应函数中使用this.id
		var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
		// 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
		var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
		var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
		var buttonId = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
		
		var tr = "<tr>" + nunmberId + checkboxId + roleName + buttonId + "</tr>";
		$("#rolePageBody").append(tr);
	}
	
	generateNavigator(pageInfo);
	
}

/**
 * 生成导航条
 * @param pageInfo
 * @returns
 */
function generateNavigator(pageInfo){
	
	// 1、获取总记录数
	var tatalRecord = pageInfo.total;
	
	// 2、生命相关函数
	var properties = {
			"num_edge_entries": 3, //边缘页数
			"num_display_entries": 5, //主体页数
			"callback": pagintionCallBack,// 翻页的回调函数
			"items_per_page":pageInfo.pageSize, //每页显示1项
			"current_page":pageInfo.pageNum -1,// 当前页码
			"prev_text":"上一页",
			"next_text":"下一页"
	}
	
	// pagination函数
	$("#Pagination").pagination(tatalRecord,properties);
	
}

/**
 * 翻页的回调函数
 * @param pageIndex
 * @param jQuery
 * @returns
 */
function pagintionCallBack(pageIndex,jQuery){
	
	// 修改window全局变量
	window.pageNum = pageIndex+1;
	
	// 调用分页
	generatePage();
	
	// 取消页码的超链接行为
	return false;
	
}

