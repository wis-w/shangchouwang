<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
	$(function(){
		
		// 生成树形结构
		generateTree();
		
		// 给修改节点绑定单击响应函数
		$("#treeDemo").on("click",".editBtn",function(){
			// 将id绑定为全局变量
			window.eId=this.id;
			// 显示模态框
			$("#menuEditModal").modal("show");
			// 获取当前节点的信息
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
			// 通过id查询节点对象
			var key = "id";
			// 获取节点属性值
			var value = window.eId;
			
			// 获取选中的节点
			var currentNode = zTreeObj.getNodeByParam(key,value);
			// 回显表单数据
			$("#menuEditModal [name=name]").val(currentNode.name);
			$("#menuEditModal [name=url]").val(currentNode.url);
			
			// 回显radio可以这样理解：被选中的radio的value属性可以组成一个数组，
			// 然后再用这个数组设置回radio，就能够把对应的值选中
			$("#menuEditModal [name=icon]").val([currentNode.icon]);
			return false;
		});
		
		// 点击修改确认按钮
		$("#menuEditBtn").click(function(){
			var id = window.eId;
			// 收集表单项中用户输入的数据
			// 收集表单数据
			var name = $("#menuEditModal [name=name]").val();
			var url = $("#menuEditModal [name=url]").val();
			var icon = $("#menuEditModal [name=icon]:checked").val();
			if(name == "" || url == ""){
				layer.msg("参数不得为空！！");
				return;
			}
			$.ajax({
				"url":"menu/edit.json",
				"type":"post",
				"dataType":"json",
				"data":{
					"id":id,
					"name":name,
					"url":url,
					"icon":icon
				},
				"success":function(response){
					result=response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功！");
						generateTree();
					}
					if(result=="FAILED"){
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.states+" "+resopnse.statesText);
				}
			});
			// 显示模态框
			$("#menuEditModal").modal("hide");
		});
		
		// 给确认模态框的确定按钮绑定单机响应函数
		$("#confirmBtn").click(function(){
			$.ajax({
				"url":"menu/remove.json",
				"type":"post",
				"dataType":"json",
				"data":{
					"id":window.id
				},
				"success":function(response){
					var result = response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功");
						generateTree();
					}
					if(result=="FAILED"){
						layer.msg("操作失败！！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.states+" "+response.statesText);
				}
			});
			$("#menuConfirmModal").modal("hide");
		});
		
		// 给删除节点绑定单击响应函数
		$("#treeDemo").on("click",".removeBtn",function(){
			// 查询当前节点的id作为全局id
			window.id=this.id;
			// 打开模态框
			$("#menuConfirmModal").modal("show");
			// 获取zTreeObj对象
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
			// 根据id属性查询节点对象
			// 用来搜索节点的属性名
			var key = "id";
			
			// 用来搜索节点的属性值
			var value = window.id;
			
			var currentNode = zTreeObj.getNodeByParam(key, value);
			
			$("#removeNodeSpan").html("【<i class='"+currentNode.icon+"'></i>"+currentNode.name+"】");
			return false;
		});
		
		// 给添加节点按钮绑定单击响应函数
		$("#treeDemo").on("click",".addBtn",function(){
			// 查询当前节点的id作为全局id
			window.pid=this.id;
			// 打开模态框
			$("#menuAddModal").modal("show");
			return false;// 不理解，如果不加return false 模态框会先打开后关闭
		})
		
		// 给添加页面的添加按钮绑定单击响应函数
		$("#menuSaveBtn").click(function(){
			// 收集表单项中用户输入的数据
			var name = $.trim($("#menuAddModal [name=name]").val());
			var url = $.trim($("#menuAddModal [name=url]").val());
			
			// 单选按钮要定位到“被选中”的那一个
			var icon = $("#menuAddModal [name=icon]:checked").val();
			if(name == "" || url == ""){
				layer.msg("参数不得为空！！");
				return;
			}
			// 发送ajax请求
			$.ajax({
				"url":"menu/save.json",
				"type":"post",
				"async":false,
				"data":{
					"pid":window.pid,
					"name":name,
					"url":url,
					"icon":icon
				},
				"dataType":"json",
				"success":function(response){
					var result = response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功!");
					}
					if(result=="FAILED"){
						layer.msg("操作失败!"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.states+" "+response.statesText);
				}
			});
			// 关闭模态框 
			$("#menuAddModal").modal("hide");
			
			// 清空表单
			$("#menuResetBtn").click();// 重置按钮
			
			// 刷新树形结构
			generateTree();
		});
	});
</script>

<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<ul id="treeDemo" class="ztree">
						<!-- 数据主体，动态生成的节点依附的静态节点 -->
						</ul>
					</div>
				</div>

			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/modal-menu-add.jsp" %>
	<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>
	<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
</body>
</html>