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
		</div>
		<div class="pull-right moreButtonNew" id="moreCarAccident">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form name="carAccidentSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details car-accident close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>车牌号</span>
							<input class="form-control " name="carPlateNo" placeholder="请输入车牌号">
						</div>
						<div class="seach-input-item pull-left">
							<span>单据号</span>
							<input class="form-control" name="documentNo" placeholder="请输入单据号" value="${documentNo}">
						</div>
						<div class="seach-input-item pull-left">
							<span>用车类型</span>
							<select class="form-control" name="useCarType">
								<option value="">全部</option>
								<option value="1">订单用车</option>
								<option value="2">调度用车</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>事故等级</span>
							<select class="form-control" name="accidentLevel">
								<option value="">全部</option>
								<option value="1">一般事故</option>
								<option value="2">轻微事故</option>
								<option value="3">重大事故</option>
								<option value="4">特大事故</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>用车人</span>
							<input class="form-control" name="driverName" placeholder="请输入用车人">
						</div>
						<div class="seach-input-item pull-left">
							<span>事故开始时间</span>
							<input class="datetimepicker form-control" name="recordAccidentTimeStart" placeholder="请选择开始时间" readonly/>
						</div>
						<div class="seach-input-item pull-left">
							<span>事故结束时间</span>
							<input class="datetimepicker form-control" name="recordAccidentTimeEnd" placeholder="请选择结束时间" readonly/>
						</div>
					</div>
					<!-- /.box-tools -->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carAccidentSearch">确定</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- /.box -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="carAccidentBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="carAccidentList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
			<!-- /.row -->
		</div>
		<!-- /.container-fluid -->
	</div>
</div>
<!-- /.col -->
<!-- /.row -->

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"carAccident": "res/js/car/car_accident_list"
			}
		});
		require(["carAccident"], function(carAccident) {
			carAccident.init();
		});
	});
	$(function() {
		$(".datetimepicker").datetimepicker({
			language: "zh-CN",
			minView: "month",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>