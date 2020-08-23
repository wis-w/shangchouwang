<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
	$(function(){
		// 1、为分页操作准备初始化数据
		window.pageNum = 1;
		window.pageSize = 5;
		window.keyWord = "";
		
		// 2、调用分页函数
		generatePage();
		
		// 3、给查询按钮提供单机相映函数
		$("#searchBtn").click(function(){
			// 给关键词赋全局变量
			window.keyWord = $("#keyWordInput").val();
			
			// 刷新页面
			generatePage();
		});
		
		// 4、点击新增按钮打开模态框
		$("#showAddModal").click(function(){
			 $("#addModal").modal("show");
		});
		
		// 5、给新增模态框增加保存函数
		$("#saveRoleBtn").click(function(){
			// 1)获取用户在文本框的输入信息
			var roleName = $.trim($("#addModal [name=roleName]").val());// id=addModal 空格表示后代元素 name=roleName的属性
			
			// 2)发起ajax请求
			$.ajax({
				"url":"role/save.json",
				"type":"post",
				"data":{
					"name":roleName
				},
				"dataType":"json",
				"success":function(response){
					
					var result = response.result;
					if(result == "SUCCESS"){
						layer.msg("操作成功");
						
						// 重新调用分页
						window.pageNum=999999;// 跳转到最后一页，使得新增的出现在最后一页
						generatePage();
					}
					if(result == "FAILED"){
						layer.msg("操作失败！！"+response.message);
					}
					
				},
				"error":function(response){
					layer.msg(response.status+" : "+response.statusText);
				}
				
			});
	
			// 关闭模态框
			$("#addModal").modal("hide");
			// 清理模态框
			$("#addModal [name=roleName]").val("");
			
		});
		
		// 6、给铅笔按钮增加单机响应函数，显示模态框
		/* $(".pencilBtn").click(function(){
			console.log("asas");
			 $("#editModal").modal("show");
		}); */
		// 首先找到所有动态生成的静态元素 on函数的第一个参数是事件类型，第二个是真正事件的选择器，第三个是事件的响应函数
		$("#rolePageBody").on("click",".pencilBtn",function(){
			$("#editModal").modal("show");
			 
			// 获取表格当前行的角色名称 为了执行函数可以取到值，这里定义全局变量
			window.roleName = $(this).parent().prev().text();
			// 获取当前角色的id，依据是当前标签的id已经获取
			window.roleId = this.id;
			
			// 使用roleName进行回显
			$("#editModal [name=roleName]").val(window.roleName);
		});
		
		// 7.给更新模态框中的更新按钮绑定单击响应函数
		$("#updateRoleBtn").click(function(){
			
			// ①从文本框中获取新的角色名称
			var roleName = $("#editModal [name=roleName]").val();
			
			// ②发送Ajax请求执行更新
			$.ajax({
				"url":"role/update.json",
				"type":"post",
				"data":{
					"id":window.roleId,
					"name":roleName
				},
				"dataType":"JSON",
				"success":function(response){
					
					var result = response.result;
					
					if(result == "SUCCESS") {
						layer.msg("操作成功！");
						
						// 重新加载分页数据
						generatePage();
					}
					
					if(result == "FAILED") {
						layer.msg("操作失败！"+response.message);
					}
					
				},
				"error":function(response){
					layer.msg(response.status+" "+response.statusText);
				}
			});
			
			// ③关闭模态框
			$("#editModal").modal("hide");
		});
		
		
		// 8、数据删除操作
		$("#rolePageBody_move").on("click",".removeBtn",function(){
			// 获取当前角色的id，依据是当前标签的id已经获取
			window.roleId = this.id;
			$.ajax({
				"url":"role/remove.json",
				"type":"post",
				"data":{
					"id":window.roleId
				},
				"dataType":"json",
				"success":function(response){
					var result = response.result;
					if(result == "SUCCESS") {
						layer.msg("操作成功！");
						// 重新加载分页数据
						generatePage();
					}
					
					if(result == "FAILED") {
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.status+" "+response.statusText);
				}
			});
			
		});
		
		// 假数据
		// var roleArray = [{roleId:5,roleName:"aaa"},{roleId:5,roleName:"aaa"},{roleId:5,roleName:"aaa"},{roleId:5,roleName:"aaa"},{roleId:5,roleName:"aaa"}];
		// showCondirModal(roleArray);
		// 9、打开确认模态框的确认删除执行删除
		$("#removeRoleBtn").click(function(){
			
			// 数组转换成json串
			var requestBody = JSON.stringify(window.roleIdArray);
			
			$.ajax({
				"url":"role/remove/by/role/id/array.json",
				"type":"post",
				"data":requestBody,
				"contentType":"application/json;charset=UFT-8",
				"dataType":"json",
				"success":function(response){
					var result = response.result;
					if(result == "SUCCESS") {
						layer.msg("操作成功！");
						// 重新加载分页数据
						generatePage();
					}
					
					if(result == "FAILED") {
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.status+" "+response.statusText);
				}
			});
			// 关闭模态框
			$("#confirmModal").modal("hide");
		});
		
		// 10、给单挑删除的按钮绑定点击响应函数
		$("#rolePageBody").on("click",".removeBtn",function(){
			
			// 从当前对象出发获取角色名称
			var roleName = $(this).parent().prev().text();
			
			// 创建role对象存入到数组中
			var roleArray = [{
				roleId:this.id,
				roleName:roleName
			}];
			
			showCondirModal(roleArray);
		});
		
		// 11、给中的复选框绑定点击响应函数
		$("#summaryBtn").click(function(){
			// 1、获取当前自选框的行为
			var currentStates = this.checked;
			
			// 2、用当前多选框的状态定义其他多选框的状态
			$(".itemBox").prop("checked",currentStates);
			
		});
		
		// 12、全选与不选的点击函数
		$("#rolePageBody").on("click",".itemBox",function(){
			// 获取当前已经选中的.itemBox数量
			var checkBox = $(".itemBox:checked").length;
			
			// 在获取全部.itemBox的数量
			var totalBoxCount = $(".itemBox").length;
			
			// 使用二者的比较结果设置综合的checkBox
			$("#summaryBtn").prop("checked",checkBox==totalBoxCount);
			
		});
		
		// 13、给批量删除的按钮绑定单击响应函数
		$("#batchRemoveBtn").click(function(){
			// 创建数组对象，用来存放获取的角色对象
			var roleArray = [];
			
			// 遍历当前被勾选的对象
			$(".itemBox:checked").each(function(){
				// 使用this引用对当前遍历的复选框
				var roleId = this.id;
			
				// 通过DOM操作获取角色的名称
				var roleName = $(this).parent().next().text();
				
				roleArray.push({
					"roleId":roleId,
					"roleName":roleName
				});
			});
			
			// 检查roleArray的长度是否为0
			if(roleArray.length == 0){
				layer.msg("请选择要操作的对象");
				return;
			}
			// 调用删除的模态框
			showCondirModal(roleArray);
			
		});
		
		// 14、给分配按钮绑定单机响应函数
		$("#rolePageBody").on("click",".checkBtn",function(){
			
			// 将当前角色对象存入全局变量
			window.id=this.id;
			// 打开模态框
			$("#assignModal").modal("show");
			
			// 在模态框装在树形结构的数据
			fillAuthTree();
		});
		
		// 15、确认按钮单击函数
		$("#assignNtn").click(function(){
			// 收集一下树形结构的个个节点中被勾选的节点
			// 声明数组存放id
			var authIdArray = [];
			
			// 获取ztreeobj对象
			var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
			
			// 获取全部被勾选的节点
			var checkedNodes = zTreeObj.getCheckedNodes(true);
			
			// 遍历节点数组，取实际有意义的勾选内容
			for(var i =0;i<checkedNodes.length;i++){
				var checkedNode=checkedNodes[i];
				
				var authId = checkedNode.id;
				
				authIdArray.push(authId);
			}
			
			// 发送请求执行分配
			var requestBody={
					"authIdArray":authIdArray,
					"roleId":[window.id]// 为了服务端可以统一使用list去接受，这里使用数组进行包装
			};
			requestBody = JSON.stringify(requestBody);
			$.ajax({
				"url":"assign/do/role/assign/auth.json",
				"type":"post",
				"data":requestBody,
				"contentType":"application/json;charset:utf-8",
				"dataType":"json",
				"success":function(response){
					var result=response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功！！");
					}
					if(result=="FAILED"){
						layer.msg("操作失败哦"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.states+"—— "+response.statesText);
				}
			});
			$("#assignModal").modal("hide");
		});
	});
</script>

<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel-body">
					<form class="form-inline" role="form" style="float: left;">
						<div class="form-group has-feedback">
							<div class="input-group">
								<div class="input-group-addon">查询条件</div>
								<input id="keyWordInput" class="form-control has-success" type="text"
									placeholder="请输入查询条件">
							</div>
						</div>
						<button id="searchBtn" type="button" class="btn btn-warning">
							<i class="glyphicon glyphicon-search"></i> 查询
						</button>
					</form>
					<button id="batchRemoveBtn" type="button" class="btn btn-danger"
						style="float: right; margin-left: 10px;">
						<i class=" glyphicon glyphicon-remove"></i> 删除
					</button>
					<button type="button" id = "showAddModal" class="btn btn-primary" style="float: right;" >
						<i class="glyphicon glyphicon-plus"></i> 新增
					</button>
					<br>
					<hr style="clear: both;">
					<div class="table-responsive">
						<table class="table  table-bordered">
							<thead>
								<tr>
									<th width="30">#</th>
									<th width="30"><input id="summaryBtn" type="checkbox"></th>
									<th>名称</th>
									<th width="100">操作</th>
								</tr>
							</thead>
							<tbody id = "rolePageBody">
								<!-- 主体数据 -->
							</tbody>
							<tfoot>
								<tr>
									<td colspan="6" align="center">
										<div id = "Pagination" class = "pagination"></div>
									</td>
								</tr>

							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 引入模态框 -->
	<%@include file="/WEB-INF/modal-role-add.jsp" %>
	<%@include file="/WEB-INF/modal-role-edit.jsp" %>
	<%@include file="/WEB-INF/modal-role-confirm.jsp" %>
	<%@include file="/WEB-INF/modal-role-assign-auth.jsp" %>
	
</body>
</html>