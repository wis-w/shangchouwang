<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
	$(function(){
		
		initPagination();// 函数调用，对页面的导航条进行初始化
		
		
		$("#summaryBtn").click(function(){// 定义全选择
			// 1、获取当前自选框的行为
			var currentStates = this.checked;
			
			// 2、用当前多选框的状态定义其他多选框的状态
			$(".itemBox").prop("checked",currentStates);
		});
		
		$(".itemBox").click(function(){// 全选与全不选的函数
			// 获取已经选中了的个数
			var useBox = $(".itemBox:checked").length;
			// 获取全部的复选框个数
			var sumBox = $(".itemBox").length;
			// 设置全选的属性值
			$("#summaryBtn").prop("checked",useBox==sumBox);
			
		});
		
		// 显示删除确认模态框
		$("#adminRemoveBtn").click(function(){
			if($(".itemBox:checked").length==0){
				layer.msg("请选择需要操作的用户");
				return;
			}
			// 显示模态框
			$("#adminConfirmModal").modal("show");	
			// 清理旧数据
			$("#adminNameDiv").empty();
			
			// 创建角色的list
			window.adminArray = [];
			// 创建idList
			window.adminIdArray=[];
			$(".itemBox:checked").each(function(){
				var id = this.id.split('_')[1];
				// 通过DOM操作获取角色的用户名
				var name = $(this).parent().next().text();
				adminArray.push({
					"id":id,
					"name":name
				});
			});
			for(var i=0;i<adminArray.length;i++){
				var name=adminArray[i].name;
				var id=adminArray[i].id;
				window.adminIdArray.push(id);
				$("#adminNameDiv").append("id&nbsp;:"+id+"&nbsp;&nbsp;账号:"+name+"<br/>")
			}
		});
		
		// 点击确认删除按钮进行删除操作
		$("#removeAdminBtn").click(function(){
			// 数组转换成json串
			var requestBody = JSON.stringify(window.adminIdArray);
			$.ajax({
				"url":"admin/remove/by/array.json",
				"type":"post",
				"data":requestBody,
				"contentType":"application/json;charset=UFT-8",
				"dataType":"json",
				"success":function(response){
					var result = response.result;
					if(result == "SUCCESS") {
						layer.msg("操作成功！");
					}
					
					if(result == "FAILED") {
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.states+" "+response.statesText);
				}
				
			});
				$("#adminConfirmModal").modal("hide");	
				// 取消选中复选框
				$("#summaryBtn").prop("checked",false);
				$(".itemBox").prop("checked",false);
				
				// 对导航栏初始化
				initPagination();
				// 强制刷新页面
				window.location.reload();
		});
		
	});
	
	function initPagination(){
		
		// 获取总记录数
		var totalRecord = ${requestScope.pageInfo.total};
		
		// 声明json对象存储设置属性
		var properties = {
				num_edge_entries: 3, //边缘页数
				num_display_entries: 5, //主体页数
				callback: pageSelectCallback,// 翻页的回调函数
				items_per_page:${requestScope.pageInfo.pageSize}, //每页显示1项
				current_page:${requestScope.pageInfo.pageNum - 1},// 当前页码
				prev_text:"上一页",
				next_text:"下一页"
		};
		
		// 生成页码导航条
		$("#Pagination").pagination(totalRecord,properties);
	}
	
	// 用户点击1，2，3 调用该函数跳转
	// 回调函数的含义，声明出来交给系统或者框架调用
	function pageSelectCallback(pageIndex,jQuery){
		// 根据pageIndex计算得到pageNum
		var pageNum = pageIndex + 1;
		// 跳转到pageNum页面
		window.location.href = "admin/get/page.html?pageNum="+pageNum+"&keyWord=${param.keyWord}";
		// 由于每一个页码都是超链接，所以取消超链接的默认行为
		return false;
	}
	
</script>
<body>
	<%@include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form action="admin/get/page.html" method="post" class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input name="keyWord" class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button type="submit" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button id="adminRemoveBtn" type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<a class="btn btn-primary" style="float: right;" href="admin/to/admin/add.html">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</a>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryBtn" type="checkbox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th>创建时间</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty requestScope.pageInfo.list }">
										<tr>
											<td colspan="6" align="center">抱歉没有查询到您要的数据</td>
										</tr>
									</c:if>

									<c:if test="${!empty requestScope.pageInfo.list }">
										<c:forEach items="${requestScope.pageInfo.list }" var="admin" varStatus="myStatus">
											<tr>
												<td>${myStatus.count }</td>
												<td><input id="check_${admin.id }" class='itemBox' type="checkbox"></td>
												<td>${admin.loginAcct }</td>
												<td>${admin.userName }</td>
												<td>${admin.email }</td>
												<td>${admin.createTime }</td>
												<td>
													<!-- 权限分配 -->
													<a href="assign/to/assign/role/page.html?adminId=${admin.id }&pageNum=${requestScope.pageInfo.pageNum }&keyWord=${param.keyWord }" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
													<!-- 修改 -->
													<a href="admin/to/edit/page.html?adminId=${admin.id }&pageNum=${requestScope.pageInfo.pageNum }&keyWord=${param.keyWord }" class="btn btn-primary btn-xs">
														<i class=" glyphicon glyphicon-pencil"></i>
													</a>
													<!-- 刪除 -->
													<a href="admin/remove/${admin.id }/${requestScope.pageInfo.pageNum }/${param.keyWord }.html" class="btn btn-danger btn-xs">
														<i class=" glyphicon glyphicon-remove"></i>
													</a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/modal-admin-confirm.jsp" %>
</body>
</html>
