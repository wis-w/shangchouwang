<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="adminConfirmModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">系统弹窗</h4>
			</div>
			<div class="modal-body">
				<h4>请确认删除是否要删除下列的用户账号</h4>
				<div id="adminNameDiv"></div>
			</div>
			<div class="modal-footer">
				<button id="removeAdminBtn" type="button" class="btn btn-primary">确认删除</button>
			</div>
		</div>
	</div>
</div>
