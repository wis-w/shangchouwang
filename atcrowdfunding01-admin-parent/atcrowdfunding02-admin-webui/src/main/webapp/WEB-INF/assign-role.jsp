<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<script type="text/javascript">
	$(function(){
		// 右箭头的单击函数
		$("#toRightBtn").click(function(){
			// select是标签选择器
			// eq(0)是第一个元素
			// >表示选择子元素
			// :selected表示被选中的元素
			// appendTo可以将被选中的元素追加到
			$("select:eq(0)>option:selected").appendTo("select:eq(1)");
		});
		
		// 左箭头的单击函数
		$("#toLeftBtn").click(function(){
			$("select:eq(1)>option:selected").appendTo("select:eq(0)");
		});
		

		$("#submitBtn").click(function() {
			// 在提交表单前把“已分配”部分的 option 全部选中
			$("select:eq(1)>option").prop("selected", "selected");
			// 为了看到上面代码的效果，暂时不让表单提交
			// return false;
		});

	});
</script>

<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="admin/to/admin/page.html">首页</a></li>
					<li><a href="admin/get/page.html?pageNum=${requestScope.pageNum }&keyWord=${requestScope.keyWord }">数据列表</a></li>
					<li class="active">分配角色</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-body">
						<form action="assign/do/role/assign.html" method="post" role="form" class="form-inline">
						<input type="hidden" name = "adminId" value="${requestScope.adminId }"></input>
						<input type="hidden" name = "pageNum" value="${requestScope.pageNum }"></input>
						<input type="hidden" name = "keyWord" value="${requestScope.keyWord }"></input>
							<div class="form-group">
								<label for="exampleInputPassword1">未分配角色列表</label><br> <select
									class="form-control" multiple="" size="10"
									style="width: 100px; overflow-y: auto;">
									<c:forEach items="${requestScope.unAssignedList }" var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<ul>
									<li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
									<li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
										style="margin-top: 20px;"></li>
								</ul>
							</div>
							<div class="form-group" style="margin-left: 40px;">
								<label for="exampleInputPassword1">已分配角色列表</label><br> 
								<select name="roleIdList" class="form-control" multiple="multiple" size="10"
									style="width: 100px; overflow-y: auto;">
									<c:forEach items="${requestScope.assignedList }" var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</div>
							<button id="submitBtn" type="submit" style="width:150px;" class="btn btn-lg btn-success btn-block" >保存</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>