<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />

<body>
	<div class="container-fluid">
		<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
			<!-- Nav tabs -->
			<ul id="orderUserCountTabs" class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active">
					<a href="#orderUserCountDay" aria-controls="orderUserCountDay" role="tab" data-toggle="tab">按日统计</a>
				</li>
				<li role="presentation">
					<a href="#orderUserCountWeek" aria-controls="orderUserCountWeek" role="tab" data-toggle="tab">按周统计</a>
				</li>
				<li role="presentation">
					<a href="#orderUserCountMonth" aria-controls="orderUserCountMonth" role="tab" data-toggle="tab">按月统计</a>
				</li>
				<li role="presentation">
					<a href="#orderUserCountYear" aria-controls="orderUserCountYear" role="tab" data-toggle="tab">按年统计</a>
				</li>

			</ul>
			<!-- Tab panes -->
			<div class="tab-content backgroundColor">
				<div role="tabpanel" class="tab-pane active" id="orderUserCountDay">

					<form class="form-horizontal" name="orderUserSerachDayForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始：</span>
									<input  class="datetimepicker form-control" name="startTimeDay" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束：</span> 
									<input  class="datetimepicker form-control" name="endTimeDay" style="background:#FFFFFF" readonly/>
								</div>
							<!--修改-->
								<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="orderUserSearchDay">确定</button>
									<button type="reset" class="btn-new-type">清空</button>
								</div>
						</div>
					</form>

					<div class="row ">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateCountDay" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateDayCount" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					</br>
				</div>
				<div role="tabpanel" class="tab-pane" id="orderUserCountWeek">

					<form class="form-horizontal" name="orderUserSerachWeekForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始：</span>
									<input  class="datetimepicker form-control" name="startTimeWeek" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束：</span> 
									<input  class="datetimepicker form-control" name="endTimeWeek" style="background:#FFFFFF" readonly/>
								</div>
							<!--修改-->
								<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="orderUserSearchWeek">确定</button>
									<button type="reset" class="btn-new-type">清空</button>
								</div>
						</div>
					</form>

					<div class="row">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateCountWeek" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateWeekCount" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					</br>
				</div>
				<div role="tabpanel" class="tab-pane" id="orderUserCountMonth">

					<form class="form-horizontal" name="orderUserSerachMonthForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始：</span>
									<input  class="datetimepicker form-control" name="startTimeMonth" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束：</span> 
									<input  class="datetimepicker form-control" name="endTimeMonth" style="background:#FFFFFF" readonly/>
								</div>
							<!--修改-->
								<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="orderUserSearchMonth">确定</button>
									<button type="reset" class="btn-new-type">清空</button>
								</div>
						</div>
					</form>

					<div class="row">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateCountMonth" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateMonthCount" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					</br>
				</div>

				<div role="tabpanel" class="tab-pane" id="orderUserCountYear">

					<form class="form-horizontal" name="orderUserSerachYearForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始：</span>
									<input  class="datetimepicker form-control" name="startTimeYear" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束：</span> 
									<input  class="datetimepicker form-control" name="endTimeYear" style="background:#FFFFFF" readonly/>
								</div>
							<!--修改-->
								<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="orderUserSearchYear">确定</button>
									<button type="reset" class="btn-new-type">清空</button>
								</div>
						</div>
					</form>

					<div class="row">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateCountYear" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="col-md-12">
							<div class="">

								<div id="orderUserOperateYearCount" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					</br>
				</div>

				<script type="text/javascript">
					$(function() {
						require.config({
							paths: {
								"orderUserCount": "res/js/operateCount/order_user_count"
							}
						});
						require(["orderUserCount"], function(orderUserCount) {
							orderUserCount.init();
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