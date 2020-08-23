<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-auth.js"></script>
<script type="text/javascript">
	$(function(){
		generateTree();
		
		// 点击增加按钮
		$("#authTreeDemo").on("click",".addBtn",function(){
			$("#addAuthModal").modal("show");
			window.id=this.id-1;
			return false;
		});
		
		// 点击模态框保存按钮
		$("#saveAuthBtn").click(function(){
			// 收集表单数据
			var name = $("#addAuthModal [name=authName]").val();
			var title = $("#addAuthModal [name=authTitle]").val();
			
			if(window.id==0){
				window.id=null;
			}
			
			$.ajax({
				"url":"add/auth/node.json",
				"type":"post",
				"dataType":"json",
				"data":{
					"name":name,
					"title":title,
					"categoryId":window.id
				},
				"success":function(response){
					var result = response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功！！");
						// 刷新ztree
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
			$("#addAuthModal").modal("hide");
			document.getElementById('authFrom').reset()
		});
		
		// 点击删除函数
		$("#authTreeDemo").on("click",".removeBtn",function(){
			$.ajax({
				"url":"remove/auth/node.json",
				"type":"post",
				"dataType":"json",
				"data":{
					"id":this.id-1
				},
				"success":function(response){
					var result = response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功！！");
						// 刷新ztree
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
			
			// 防止页面跳转
			return false;
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
						<i class="glyphicon glyphicon-th-list"></i> 权限维护列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<ul id = "authTreeDemo" class="ztree"></ul>
					</div>
				</div>

			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/modal-auth-add.jsp" %>
</body>
</html>