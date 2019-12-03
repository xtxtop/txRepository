<meta charset="utf-8">

<div class="container-fluid">
	<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
		<ul id="orderCountTabNs" class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active">
				<a href="#packageCountDayNs" aria-controls="packageCountDayNs" role="tab" data-toggle="tab">按日统计</a>
			</li>
			<li role="presentation">
				<a href="#packageCountWeekNs" aria-controls="packageCountWeekNs" role="tab" data-toggle="tab">按周统计</a>
			</li>
			<li role="presentation">
				<a href="#packageCountMonthNs" aria-controls="packageCountMonthNs" role="tab" data-toggle="tab">按月统计</a>
			</li>
		</ul>

		<div class="tab-content backgroundColor">

			<div role="tabpanel" class="tab-pane active" id="packageCountDayNs" style="width:100%;white-space:nowrap;">

				<form class="form-horizontal" name="packageCountDayNsForm">
					<div class="row pull-down-menu">
							<div class="seach-input-item pull-left">
								<span>开始时间：</span>
								<input class="dayTime form-control" name="createTimeStart" style="background:#FFFFFF" readonly/>
						</div>
							<div class="seach-input-item pull-left">
								<span>结束时间：</span> 
								<input class="dayTime form-control" name="createTimeEnd" style="background:#FFFFFF" readonly/>
							</div>
						<!--修改-->
						<div class="box-footer-btn">
								<button type="button" class="btn-new-type" id="packageSearchDayNs">确定</button>
								<button type="reset" class="btn-new-type">清空</button>
						</div>
					</div>
				</form>

				<div class="row ">
					<!-- <div><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：套餐每天销售的数量与金额统计</span></div> -->
					<br/><br/>
					<div class="col-sm-5 Jq_resize_box">
						<div id="salePackageNs" style="width: 500px;height:320px;"></div>
					</div>
					<div class="col-sm-7 Jq_resize_box">
						<div id="salePackageNso" style="width: 700px;height:320px;margin-left:-20px;"></div>
					</div>

				</div>

				<div class="row">
					<!-- <div><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：套餐按天合计销售的数量与金额统计</span></div> -->
					<div class="col-sm-5 Jq_resize_box">
						<div id="salePackageNsMs" style="width: 500px;height:320px;"></div>
					</div>
					<div class="col-sm-7 Jq_resize_box">
						<div id="salePackageNsoMs" style="width: 700px;height:320px;margin-left:-20px;"></div>
					</div>
				</div>

			</div>

			<div role="tabpanel" class="tab-pane" id="packageCountWeekNs" style="width:100%;white-space:nowrap;">

				<form class="form-horizontal" name="packageCountWeekNsForm">
					<div class="row pull-down-menu">
							<div class="seach-input-item pull-left">
								<span >开始时间：</span>
								<input  class="dayTime form-control" name="createTimeStart" style="background:#FFFFFF" readonly/>
							</div>
							<div class="seach-input-item pull-left">
								<span >结束时间：</span> 
								<input  class="dayTime form-control" name="createTimeEnd" style="background:#FFFFFF" readonly/>
							</div>
						<!--修改-->
						<div class="box-footer-btn">
								<button type="button" class="btn-new-type" id="packageSearchWeekNs">确定</button>
								<button type="reset" class="btn-new-type"">清空</button>
						</div>
					</div>
				</form>

				<div class="row">
					<!-- <div><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：套餐每周销售的数量与金额统计</span></div> -->
					<br/><br/>
					<div class="col-sm-5 Jq_resize_box">
						<div id="salePackageWeekNs" style="width: 500px;height:320px;"></div>
					</div>
					<div class="col-sm-7 Jq_resize_box">
						<div id="salePackageWeekNso" style="width: 700px;height:320px;margin-left:-20px;"></div>
					</div>

				</div>

				<div class="row">
					<!-- <div><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：套餐按周合计销售的数量与金额统计</span></div> -->
					<div class="col-sm-5 Jq_resize_box">
						<div id="salePackageWeekNsMs" style="width: 500px;height:320px;"></div>
					</div>
					<div class="col-sm-7 Jq_resize_box">
						<div id="salePackageWeekNsoMs" style="width: 700px;height:320px;margin-left:-20px;;"></div>
					</div>
				</div>

			</div>

			<div role="tabpanel" class="tab-pane" id="packageCountMonthNs" style="width:100%;white-space:nowrap;">

				<form class="form-horizontal" name="packageCountMonthNsForm">
					<div class="row pull-down-menu">
							<div class="seach-input-item pull-left">
								<span >开始时间：</span><input  class="monthTime form-control" name="createTimeStart" style="background:#FFFFFF" readonly/>
							</div>
							<div class="seach-input-item pull-left">
								<span >结束时间：</span> <input  class="monthTime form-control" name="createTimeEnd" style="background:#FFFFFF" readonly/>
							</div>
						<!--修改-->
						<div class="box-footer-btn">
								<button type="button" class="btn-new-type" id="packageSearchMonthNs">确定</button>
								<button type="reset" class="btn-new-type">清空</button>
						</div>
					</div>
				</form>

				<div class="row">
					<!-- <div><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：套餐每月销售的数量与金额统计</span></div> -->
					<br/><br/>
					<div class="col-sm-5 Jq_resize_box">
						<div id="salePackageMonthNs" style="width: 500px;height:320px;"></div>
					</div>
					<div class="col-sm-7 Jq_resize_box">
						<div id="salePackageMonthNso" style="width: 700px;height:320px;margin-left:-20px;"></div>
					</div>

				</div>

				<div class="row">
					<!-- <div><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：套餐按月合计销售的数量与金额统计</span></div> -->
					<div class="col-sm-5 Jq_resize_box">
						<div id="salePackageMonthNsMs" style="width: 500px;height:320px;"></div>
					</div>
					<div class="col-sm-7 Jq_resize_box">
						<div id="salePackageMonthNsoMs" style="width: 700px;height:320px;margin-left:-20px;"></div>
					</div>
				</div>

			</div>

		</div>
	</div>
	<!-- /.row -->
</div>
<!-- /.container-fluid -->

<script type="text/javascript" src="${basePath!'' }/res/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"salePackageEportForm": "res/js/reportForm/salePackageEportForm"
			}
		});
		require(["salePackageEportForm"], function(salePackageEportForm) {
			salePackageEportForm.init();
		});

	});
</script>
<script type="text/javascript">
	$(".yearTime").datetimepicker({
		format: 'yyyy',
		weekStart: 1,
		autoclose: true,
		startView: 4,
		minView: 4,
		forceParse: false,
		language: 'zh-CN'
	});

	$('.monthTime').datetimepicker({
		format: 'yyyy-mm',
		weekStart: 1,
		autoclose: true,
		startView: 3,
		minView: 3,
		forceParse: false,
		language: 'zh-CN'
	});

	$('.dayTime').datetimepicker({
		language: "zh-CN",
		minView: "month",
		autoclose: true, //选中之后自动隐藏日期选择框
		clearBtn: true, //清除按钮
		todayBtn: 'linked', //今日按钮
		format: "yyyy-mm-dd"
	});
</script>