<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30% !important;
		}
		.seach-input-item pull-left .form-control {
			width: 45% !important;
			margin-right: 20%;
		}
		.pull-down-menu span {
			width: 20%;
		}
	}
	
	@media only screen and (min-width:992px) and (max-width:1190px) {
		.seach-input-item pull-left .form-control {
			width: 120px !important;
		}
	}
	
	@media only screen and (max-width:768px) {
		.row .sorting_disabled {
			font-size: 1.1rem !important;
		}
	}
	
	@media only screen and (min-width:768px) and (max-width:1024px) {
		.row .sorting_disabled {
			font-size: 1.2rem !important;
		}
	}
	
	@media only screen and (min-width:1024px) and (max-width:1366px) {
		.row .sorting_disabled {
			font-size: 1.3rem !important;
		}
	}
	
	.row .seach-input-item pull-left {
		width: 100% !important;
	}
	
	#sysParamTools {
		margin-right: 8px;
	}
	
	.pull-down-menu input,
	.pull-down-menu select {
		margin-right: 10%;
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">

		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>

		<div class="search-input-content">
			<form class="form-horizontal" name="sysUserSearchForm">
				<div class="seach-input-border-box">
					
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>用户账号</span>
							<input type="text" class="form-control" name="userName" placeholder="请输入用户账号">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>用户名称</span>
							<input type="text" class="form-control" name="realName" placeholder="请输入用户名称">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>是否可用</span>
							<select class="form-control" name="isAvailable">
								<option value="">全部</option>
								<option value="1">可用</option>
								<option value="0">不可用</option>
							</select>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="sysUserSearchafter">确定</button>
					</div>
				</div>
				<!-- /.box-body -->
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="sysUserTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="sysUserList" class="table table-hover" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /. container-fluid-->

<!-- 弹出框-->
<style>
	.key {
		font-size: 1.5rem;
		font-weight: 500;
		color: #414550;
		line-height: 15px;
	}
	
	.val {
		font-size: 1.5rem;
		font-weight: 500;
		color: #a0a7af;
		line-height: 15px;
	}
</style>
<div class="modal fade" id="mySysUserModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加/编辑用户管理</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="sysUserForm">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label key">用户账号：<span style="color: red;">*</span></label>
						<div class="col-sm-8">
							<input type="hidden" name="userId" id="formUserId">
							<input type="text" placeholder="请输入账号" maxlength="30" class="form-control val" id="formUserName" name="userName" placeholder="" required="required">
						</div>
					</div>
					<div class="form-group" id="divPassword1">
						<label for="inputEmail3" class="col-sm-3 control-label key">密码：<span style="color: red;">*</span></label>
						<div class="col-sm-8">
							<input type="password" placeholder="请输入密码" maxlength="20" class="form-control val" id="formPassword1" name="password1" placeholder="">
						</div>
					</div>
					<div class="form-group" id="divPassword2">
						<label for="inputEmail3" class="col-sm-3 control-label key">确认密码：<span style="color: red;">*</span></label>
						<div class="col-sm-8">
							<input type="password" placeholder="确认密码" maxlength="20" class="form-control val" id="formPassword2" name="password2" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-3 control-label key">所属角色：<span style="color: red;">*</span></label>
						<div class="col-sm-8 val" id="sysUserRoleList">
							<input type="checkbox" name="sysRole" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label key">用户标识：<span style="color: red;">*</span></label>
						<div class="col-sm-8 val">
							<label class="radio-inline">
                                    <input type="radio" class="identification" name="identification" id="identification1" value="1" checked="checked"> 平台
                                </label>
							<label class="radio-inline">
                                    <input type="radio" class="identification" name="identification" id="identification2" value="2"> 商家
                                </label>
							<label class="radio-inline">
                                    <input type="radio" class="identification" name="identification" id="identification3" value="3"> 城市运营
                                </label>
						</div>
					</div>
					<div class="form-group" id="cityListDiv">
						<label for="inputPassword3" class="col-sm-3 control-label key">运营城市：<span style="color: red;">*</span></label>
						<div class="col-sm-8 val" id="sysUserCityList">
							<input type="checkbox" name="cityIds" />
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label key">用户名称：<span style="color: red;">*</span></label>
						<div class="col-sm-8">
							<input type="text" placeholder="请输入用户名称" maxlength="16" class="form-control val" id="formRealName" name="realName" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label key">email：<span style="color: red;">*</span></label>
						<div class="col-sm-8">
							<input type="email" placeholder="请输入邮箱" class="form-control val" id="formEmail" name="email" placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label key">手机：<span style="color: red;">*</span></label>
						<div class="col-sm-8">
							<input type="tel" placeholder="请输入手机号" class="form-control val" id="formMobilePhone" name="mobilePhone" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label key">电话：</label>
						<div class="col-sm-8">
							<input type="tel" placeholder="请输入电话号码" class="form-control val" id="formTelPhone" name="telPhone" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-3 control-label key">备注：</label>
						<div class="col-sm-8">
							<input type="text" placeholder="请填写备注信息" class="form-control val" id="sysUserformMemo" name="memo" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-3 control-label key">账户是否可用：<span style="color: red;">*</span></label>
						<div class="col-sm-8 val">
							<label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isAvailable" id="sysUserformIsAvailable1" value="1"> 是
                                </label>
							<label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isAvailable" id="sysUserformIsAvailable2" value="0"> 否
                                </label>
						</div>
					</div>

					<div class="modal-footer">
						<button type="button" style="width: 95px; height: 32px; margin-right: 50px !important;color: #3f9fff;background: #fff;border-color: #3f9fff;" class="btn btn-default pull-right" id="sysUserResetBtn">重置</button>
						<button type="button" style="width: 95px; height: 32px; background:#2b94fd;" class="btn btn-default pull-right btncolora" id="sysUserEditBtn">保存</button>
						<!-- <input type="button" class="btn btn-default pull-right" id="sysUserEditBtn" value="保存" >-->
					</div>
				</form>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>