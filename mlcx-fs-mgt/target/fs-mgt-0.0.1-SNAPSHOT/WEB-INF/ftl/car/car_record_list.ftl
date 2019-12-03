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
		.seach-input-item pull-left .form-control {
			margin-right: 10%；
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
			<!--<span class="pull-right moreButton" id="moreCarRecord">更多</span>-->
		</div>
		<div class="search-input-content">
			<form name="carRecordSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details close-content clearfix">
							<div class="seach-input-item pull-left">
								<span>车牌号</span>
								 <input class="form-control" name="carPlateNo" placeholder="请输入车牌号" value="${carPlateNo}">
							</div>
							<div class="seach-input-item pull-left">
								<span>单据号</span>
								<input class="form-control" name="documentNo" placeholder="请输入单据号">
							</div>
							<div class="seach-input-item pull-left">
								<span>用车时间起</span>
								<input class="datetimepicker form-control" name="startTimeStart" placeholder="请选择用车时间" readonly/>
							</div>
							<div class="seach-input-item pull-left">
								<span>还车时间起</span>
								<input class="datetimepicker form-control" name="finishTimeEnd" placeholder="请选择还车时间" readonly/>
							</div>
							<div class="seach-input-item pull-left">
								<span>用车类型</span>
								<select class="form-control" name="useCarType">
									<option value="">全部</option>
									<option value="1">订单用车</option>
									<option value="2">调度用车</option>
								</select>
							</div>

					</div>
										<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carRecordSearch">确定</button>
					</div>
				</div>
			</form>
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
<div class="row show-table-content">
	<div class="col-xs-12">
		<!--定义操作列按钮模板-->
		<script id="carRecordBtnTpl" type="text/x-handlebars-template">
			{{#each func}}
			<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
		</script>
		<div class="box">
			<div class="box-body">
				<table id="carRecordList" class="table table-hover table-bettween-far" width="100%" border="1">
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
<!-- /.container-fluid -->

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"carRecord": "res/js/car/car_record_list"
			}
		});
		require(["carRecord"], function(carRecord) {
			carRecord.init();
		});
	});
	$(function() {
		$(".datetimepicker").datetimepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>