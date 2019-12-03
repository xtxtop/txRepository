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
		<div class="pull-right moreButtonNew" id="moreoverOrder">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="overOrderSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>订单编号</span>
							<input type="text" class="form-control" id="overOrderNo" name="overOrderNo" placeholder="请输入订单编号">
						</div>
						<div class="seach-input-item pull-left">
							<span>订单状态</span>
							<select class="form-control" name="overOrderStatus">
								<option value="">全部</option>
								<!--<option value="0">已完成</option>-->
								<option value="1">已预约</option>
								<option value="2">已计费</option>
								<option value="3">已结束</option>
								<option value="4">已取消</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>客户姓名</span>
							<input type="text" class="form-control" id="memberName" name="memberName" placeholder="请输入客户姓名">
						</div>
						<div class="seach-input-item pull-left">
							<span>手机号码</span>
							<input type="text" class="form-control" id="mobilePhone" name="mobilePhone" placeholder="请输入手机号码">
						</div>
						<div class="seach-input-item pull-left">
							<span>取车场站</span>
							<input type="text" class="form-control" name="actualTakeLoacton" placeholder="请输入取车场站">
						</div>
						<div class="seach-input-item pull-left">
							<span>时间范围</span>
							<input class="datepicker form-control" name="startBillingTimeStart" placeholder="请选择开始时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="finishTimeEnd" placeholder="请选择结束时间" />
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="overOrderSearchafter">确定</button>
					</div>
				</div>
			</form>
		</div>
		<!-- /.box-footer -->
	</div>
	<!-- /.box -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="overOrderTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="overOrderList" class="table table-hover table-bettween-far" width="100%" border="1">
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
				"overOrder": "res/js/order/overOrder_list"
			}
		});
		require(["overOrder"], function(overOrder) {
			overOrder.init();
		});
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: "linked", //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>