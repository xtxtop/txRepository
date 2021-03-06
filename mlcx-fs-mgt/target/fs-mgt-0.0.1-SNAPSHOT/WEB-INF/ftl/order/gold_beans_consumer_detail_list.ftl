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
			<form class="form-horizontal" name="goldBeansConsumerDetailsSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>订单编号</span>
							<input type="text" class="form-control" name="orderNo" placeholder="请输入订单编号">
						</div>
						<div class="seach-input-item pull-left">
							<span>会员手机号</span>
							<input type="text" class="form-control" name="memberPhone" placeholder="请输入会员手机号">
						</div>
						<div class="seach-input-item pull-left">
							<span>时间范围</span>
							<input class="datepicker form-control" name="consumerTimeStart" placeholder="请选择开始时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="consumerTimeEnd" placeholder="请选择结束时间" />
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="goldBeansConsumerDetailsSearchafter">确定</button>
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
			<script id="goldBeansConsumerDetailsTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="goldBeansConsumerDetailsList" class="table table-hover table-bettween-far" width="100%" border="1">
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
				"goldBeansConsumerDetails": "res/js/order/gold_beans_consumer_detail_list"
			}
		});
		require(["goldBeansConsumerDetails"], function(goldBeansConsumerDetails) {
			goldBeansConsumerDetails.init();
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