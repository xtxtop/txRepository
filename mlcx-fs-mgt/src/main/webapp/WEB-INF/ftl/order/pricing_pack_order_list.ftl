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
		<div class="pull-right moreButtonNew" id="morePricingParkOrderList">
			<div class="up-triangle">
			</div>
			<div class="up-box">
				<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
			</div>
		</div>
		<!-- /.box-tools -->
		<div class="search-input-content">
			<form class="form-horizontal" name="pricePackOrderSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>交易号</span>
							<input type="text" class="form-control" name="packOrderNo" placeholder="请输入交易号">
						</div>
						<div class="seach-input-item pull-left">
							<span>套餐名称</span>
							<select class="form-control" name="packageName">
								<option value="">请选择</option>
								<#if pricingPackageList??>
									<#list pricingPackageList as package>
										<option value="${package.packageName}">${package.packageName}</option>
									</#list>
								</#if>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>套餐类型</span>
							<select class="form-control" name="packageType">
								<option value="">全部</option>
								<option value="1">赠送类套餐</option>
								<option value="2" <#if packageType==2> selected="selected"</#if>>销售套餐</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>手机号</span>
							<input class=" form-control" name="mobilePhone" placeholder="请输入手机号" />
						</div>
						<div class="seach-input-item pull-left">
							<span>客户姓名</span>
							<input type="text" class="form-control" name="memberName" placeholder="请输入客户姓名">
						</div>
						<div class="seach-input-item pull-left">
							<span>是否充值</span>
							<select class="form-control" name="isRecharge">
								<option value="">全部</option>
								<option value="1" <#if isRecharge!=null && isRecharge==1>selected</#if>>是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>时间范围</span>
							<input class="datepicker form-control" name="startTime" placeholder="请选择开始时间" value="${today}" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="endTime" placeholder="请选择结束时间" value="${today}" />
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="pricePackOrderSearch">确定</button>
					</div>
				</div>
				<!-- /.box-header -->
				<input type="hidden" name="memberNo" value="${memberNo}">
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="pricePackOrderTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="pricePackOrderList" class="table table-hover table-bettween-far" width="100%" border="1">
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
				"pricePackOrder": "res/js/order/price_pack_order_list"
			}
		});
		require(["pricePackOrder"], function(pricePackOrder) {
			pricePackOrder.init();
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