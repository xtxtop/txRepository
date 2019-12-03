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
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="deviceUploadpkgLogSearchForm">
				<input type="hidden" name="carNo" value="${carNo}" />
				<div class="seach-input-border-box">
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>车牌号</span>
							<input type="text" class="form-control" id="carPlateNo" name="carPlateNo"   value="${carPlateNo}"  placeholder="请输入车牌号" />
						</div>
						<div class="seach-input-item pull-left">
							<span>设备序列号</span>
							<input type="text" class="form-control" id="deviceSn" name="deviceSn" placeholder="请输入设备序列号" />
						</div>
						<div class="seach-input-item pull-left">
							<span>操作指令</span>
							<input type="text" class="form-control" id="cmdType" name="cmdType" placeholder="请输入操作指令">
						</div>
						<div class="seach-input-item pull-left">
							<span>上报时间起</span>
							<input class="datepicker form-control" name="createTimeStart" placeholder="请选择上报时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>上报时间止</span>
							<input class="datepicker form-control" name="createTimeEnd" placeholder="请选择上报时间结束点" />
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="deviceUploadpkgLogSearchForm">确定</button>
					</div>
				</div>
				<!-- /.box-body -->
			</form>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="deviceOnlineTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-header">
					<!-- <h3 class="box-title">数据列</h3> -->
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="deviceOneLineList" class="table table-hover table-bettween-far" width="100%" border="1">
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
				"deviceLog": "res/js/device/device_log_list"
			}
		});
		require(["deviceLog"], function(deviceLog) {
			deviceLog.init();
		});
	});

	$(".datepicker").datetimepicker({
		language: "zh-CN",
		autoclose: true,
		clearBtn: true, //清除按钮
		todayBtn: true,
		minuteStep: 5,
		startDate: moment(new Date()).format("YYYY-MM HH:mm:ss"),
		format: "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
</script>