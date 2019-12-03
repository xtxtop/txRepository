<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		.seach-input-item pull-left .form-control {
			width: 45% !important;
			margin-right: 20%;
		}
		.pull-down-menu span {
			width: 20%;
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
	
	.other {
		margin: 0 !important;
		padding: 0 !important;
	}
	
	.box-footer button {
		margin: 0 1%;
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
	
	.table-bettween-far tr td {
		height: 72px;
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="moreMemberList">
			<div class="up-triangle">
			</div>
			<div class="up-box">
				<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
			</div>
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="memberSerachForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details memberlist close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>手机</span>
							<input class="form-control" name="mobilePhone" placeholder="请输入手机号码">
						</div>
						<div class="seach-input-item pull-left">
							<span>姓名</span>
							<input class="form-control" name="memberName" placeholder="请输入姓名" value="${memberName}">
						</div>
						<div class="seach-input-item pull-left">
							<span>身份证</span>
							<input class="form-control" name="idCard" placeholder="请输入身份证">
						</div>
						<div class="seach-input-item pull-left">
							<span>认证</span>
							<select class="form-control" name="censorStatus">
								<option value="">全部</option>
								<option value="1" <#if cencorStatus!=null && cencorStatus==1>selected</#if> >已认证</option>
								<option value="0">未认证</option>
								<option value="2" <#if cencorStatus!=null && cencorStatus==2>selected</#if> >待认证</option>
								<option value="3">未通过</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>类型</span>
							<select class="form-control" name="memberType">
								<option value="">全部</option>
								<option value="1">普通</option>
								<option value="2">集团</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>所属集团</span>
							<input class="form-control" name="companyName" placeholder="请输入所属集团">
						</div>
						<div class="seach-input-item pull-left">
							<span>会员等级</span>
							<select class="form-control" name="memberLevelId">
								<option value="">全部</option>
								<#list memberLevelList as memberLevel>
									<option value="${memberLevel.memberLevelId}">${memberLevel.levelName}</option>
								</#list>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>状态</span>
							<select class="form-control" name="isBlacklist">
								<option value="">全部</option>
								<option value="0">正常</option>
								<option value="1">黑名单</option>
							</select>
						</div>
						<!--   <div class="col-md-3">
                	<div class="seach-input-item pull-left">
                        <span>押金</span>
                        <select class="form-control" name="deposit">
                        <option value="">全部</option>
                        <option value="1">有</option>
                        <option value="0">无</option>
                    </select>
                    </div>
                </div> -->

						<div class="seach-input-item pull-left">
							<span>邀请人</span>
							<input class="form-control" name="invitationCode" placeholder="请输入邀请人">
						</div>
						<div class="seach-input-item pull-left timeBegin">
							<span>注册时间起</span>
							<input class="datetimepicker form-control" name="registerTimeStart" placeholder="请选择注册开始时间" readonly/>
						</div>
						<div class="seach-input-item pull-left timeBegin timeEnd">
							<span>至</span>
							<input class="datetimepicker form-control" name="registerTimeEnd" placeholder="请选择注册结束时间" readonly/>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="memberSearch">确定</button>
					</div>
				</div>
				<!-- /.box-header -->
				<input type="hidden" name="refereeId" value="${refereeId}" />

			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12" style="background:#fff;">
			<!--定义操作列按钮模板-->
			<script id="memberBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body" style="background:#fff;">
					<table id="memberList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->

<div class="modal fade" id="inBlacklistModel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">移入黑名单</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="inBlacklistForm">
					<input type="hidden" name="memberNo" id="inMemberNo">
					<input type="hidden" name="isBlacklist" value="1">
					<label for="inputEmail3" class=" control-label wen" id="inBlacklistMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">理由:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-8">
							<textarea class="form-control textareas" name="blacklistMemo"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="inBlacklistBtn" value="确定">
						<button type="button" class="btn btn-default pull-right cancels" id="inBlacklistCancel">取消</button>
					</div>
				</form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div class="modal fade" id="outBlacklistModel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">移出黑名单</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="outBlacklistForm">
					<input type="hidden" name="memberNo" id="outMemberNo">
					<input type="hidden" name="isBlacklist" value="0">
					<label for="inputEmail3" class=" control-label wen" id="outBlacklistMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">理由:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-8">
							<textarea class="form-control textareas" name="blacklistMemo"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="outBlacklistBtn" value="确定">
						<button type="button" class="btn btn-default pull-right cancels" id="outBlacklistCancel">取消</button>
					</div>
				</form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<input type="hidden" id="isAdmin" value="${roleAdminTag}">
<!-- <#if roleAdminTag!=null && roleAdminTag==1>132</#if> -->
<!--
    <script type="text/javascript" src="${basePath!'' }/res/js/member/main.js"></script>
    -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"member": "res/js/member/member_list"
			}
		});
		require(["member"], function(member) {
			member.init();
		});
	});
	$(function() {
		$(".datetimepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: 'linked', //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>