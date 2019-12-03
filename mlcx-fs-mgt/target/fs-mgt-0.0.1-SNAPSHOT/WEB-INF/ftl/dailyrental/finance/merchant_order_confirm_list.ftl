<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		. .form-control {
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
			<form class="form-horizontal" name="merchantOrderConfirmSearchForm">
				<div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details merchantOrderList close-content clearfix">
							<div class="seach-input-item pull-left">
								<span>商家名称</span>
								<select class="form-control" name="merchantId" placeholder="">
									<option value="">请选择</option>
									<#list merchantList as merchant>
										<option value="${merchant.merchantId}">
											${merchant.merchantName}
										</option>
									</#list>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>对账周期</span>
								<select class="form-control" name="date" placeholder="">
									<option value="">请选择</option>
									<#list dateList as date>
										<option value="date">
											${date}
										</option>
									</#list>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>账单确认状态</span>
								<select class="form-control" name="confirmStatus" placeholder="">
									<option value="">请选择</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>账单结算状态</span>
								<select class="form-control" name="isSettled" placeholder="">
									<option value="">请选择</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
						</div>
						<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="merchantOrderConfirmSearchBtn">确定</button>
					</div>
				</div>
			</form>
		</div>
	</div>
<div class="row show-table-content">
	<div class="col-xs-12">
		<!--定义操作列按钮模板-->
		<script id="merchantOrderConfirmTpl" type="text/x-handlebars-template">
			{{#each func}}
			<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
		</script>
		<div class="box">
			<div class="box-body">
				<table id="merchantOrderConfirmList" class="table table-hover table-bettween-far" width="100%" border="1">
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
				"merchantOrderConfirm": "res/js/dailyrental/finance/merchant_order_confirm_list"
			}
		});
		require(["merchantOrderConfirm"], function(merchantOrderConfirm) {
			merchantOrderConfirm.init();
		});
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>