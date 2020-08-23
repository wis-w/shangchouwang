<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		// 1、设置节点数据
		$.ajax({
			"url":"menu/get/whole/tree.json",
			"type":"post",
			"dataType":"json",
			"success":function(response){
				var result = response.result;
				if(result == "SUCCESS"){
					// 2、创建json对象，用于存储zTree所做的设置
					var setting = {	};
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
</body>
</html>