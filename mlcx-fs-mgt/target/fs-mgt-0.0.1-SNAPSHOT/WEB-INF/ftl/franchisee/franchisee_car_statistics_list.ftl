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
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="franchiseeCarStatisticsSerachForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>加盟商</span>
							<input class="form-control" name="franchiseeName" placeholder="请输入加盟商">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>车牌号</span>
							<input class="form-control" name="carPlateNo" placeholder="请输入车牌号">
						</div>

					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="franchiseeCarStatisticsSearch">确定</button>
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
			<script id="franchiseeCarStatisticsBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="franchiseeCarStatisticsList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->

<!-- 相关订单 -->
<div class="modal" id="earningsOrderModal">
	<div class="modal-dialog" style="width:900px;">
		<form class="form-horizontal" name="earningsOrderForm">
			<input type="hidden" id="carOrParkNo" name="carOrParkNo" value="">
			<div class="modal-content">
				<div class="with-border">
					<div class="title-new-details">相关订单</div>
				</div>
				<div class="col-xs-14">
					<!--定义操作列按钮模板-->
					<script id="earningsOrderModalTpl" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
					<div class="box">

						<div class="box-header">
							<!-- <h3 class="box-title">数据列</h3> -->
						</div>
						<!-- /.box-header -->
						<div class="box-body box-body-change-padding">
							<table id="earningsOrderList" class="table table-bordered table-hover" width="100%">
							</table>
						</div>
						<!-- /.box-body -->
						<div class="carRedPacketAddParkTool-bullet" id="franchiseeOrder">
					</div>
					</div>
					<!-- /.box -->
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</form>
	</div>
	<!-- /.modal -->
</div>
<!-- 收益明细 -->
<div class="modal" id="carRelateOrderReturnsDetailedModal">
	<div class="modal-dialog" style="width:700px;">
		<form class="form-horizontal" name="carRelateOrderReturnsDetailedModalForm">
			<input type="hidden" id="carOrParkNo" name="carOrParkNo" value="">
			<div class="modal-content">
				<div class="with-border">
					<div class="title-new-details">收益明细</div>
				</div>
				<div class="">
					<!--定义操作列按钮模板-->
					<script id="carRelateOrderReturnsDetailedModalTpl" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
					<div class="box">

						<div class="box-header">
							<!-- <h3 class="box-title">数据列</h3> -->
						</div>
						<!-- /.box-header -->
						<div class="box-body box-body-change-padding">
							<table id="carRelateOrderReturnsDetailedList" class="table table-bordered table-hover" width="100%">
							</table>
						</div>
						<!-- /.box-body -->
						<div class="carRedPacketAddParkTool-bullet" id="franchiseetools">
					</div>
					</div>
					<!-- /.box -->
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</form>
	</div>
	<!-- /.modal -->
</div>
<!--
    <script type="text/javascript" src="${basePath!'' }/res/js/franchisee/main.js"></script>
    -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"franchiseeCarStatistics": "res/js/franchisee/franchisee_car_statistics_list"
			}
		});
		require(["franchiseeCarStatistics"], function(franchiseeCarStatistics) {
			franchiseeCarStatistics.init();
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