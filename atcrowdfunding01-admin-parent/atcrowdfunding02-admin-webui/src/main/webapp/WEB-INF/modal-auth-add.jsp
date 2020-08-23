<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="addAuthModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">尚筹网系统弹窗</h4>
			</div>
			<div class="modal-body">
				<form id="authFrom" class="form-signin" role="form">
					<div class="form-group has-success has-feedback">
						<input type="text" name="authName" class="form-control"
							placeholder="请输入角色名称" autofocus>
						<input type="text" name="authTitle" class="form-control"
							placeholder="请输入标题名称" autofocus>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="saveAuthBtn" type="button" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>
