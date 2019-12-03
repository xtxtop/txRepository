<meta charset="utf-8">
<style>
	.seach-input-item pull-left .form-control {
		width: 50% !important;
	}
	
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
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="moreCheckAccount">
			<div class="up-triangle">
			</div>
			<div class="up-box">
				<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
			</div>
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="checkAccountsViewSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>对账周期</span>
							<input class="datepicker form-control" name="createTime" value="${checkAccountsView.checkDateRange1?string('YYYY-MM-dd')}" placeholder="" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="finishTime" value="${checkAccountsView.checkDateRange2?string('YYYY-MM-dd')}" placeholder="" />
						</div>
						<div class="seach-input-item pull-left">
							<span>客户姓名</span>
							<input class="form-control pull-left" name="memberName" value="${checkAccountsView.memberName}" placeholder="">
						</div>
						<div class="seach-input-item pull-left">
							<span>车牌号</span>
							<input class="form-control pull-left" name="carPlateNo" placeholder="请输入车牌号">
						</div>
						<div class="seach-input-item pull-left">
							<span>订单状态</span>
							<select class="form-control" name="orderStatus">
									<option value="">全部</option>
									<option value="1">已预约</option>
									<option value="2">已计费</option>
									<option value="3">已结束</option>
									<option value="4">已取消</option>
								</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>支付状态</span>
							<select class="form-control" name="payStatus">
									<option value="">全部</option>
									<option value="0">未支付</option>
									<option value="1">已支付</option>
									<!--<option value="2">未结算</option>-->
								</select>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="checkAccountsViewSearch">确定</button>
					</div>
				</div>
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="checkAccountsViewBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="checkAccountsViewList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"checkAccountsView": "res/js/finace/check_accounts_view"
			}
		});
		require(["checkAccountsView"], function(checkAccountsView) {
			checkAccountsView.init();
		});
	});
	$(function() {
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: "linked", //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>