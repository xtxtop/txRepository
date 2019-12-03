<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />

<body>
	<div class="container-fluid">
		<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
			<!-- Nav tabs -->
			<ul id="memberCountTabs" class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active">
					<a href="#memberCountDay" aria-controls="memberCountDay" role="tab" data-toggle="tab">按日统计</a>
				</li>
				<li role="presentation">
					<a href="#memberCountWeek" aria-controls="memberCountWeek" role="tab" data-toggle="tab">按周统计</a>
				</li>
				<li role="presentation">
					<a href="#memberCountMonth" aria-controls="memberCountMonth" role="tab" data-toggle="tab">按月统计</a>
				</li>
				<li role="presentation">
					<a href="#memberCountYear" aria-controls="memberCountYear" role="tab" data-toggle="tab">按年统计</a>
				</li>

			</ul>
			<!-- Tab panes -->
			<div class="tab-content backgroundColor">
				<div role="tabpanel" class="tab-pane active" id="memberCountDay">

					<form class="form-horizontal" name="memberSerachDayForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始</span><input  class="datetimepicker form-control" name="startTimeDay" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束</span> <input  class="datetimepicker form-control" name="endTimeDay" style="background:#FFFFFF" readonly/>
								</div>
							<!--修改-->
							<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="memberSearchDay"> 确定</button>
									<button type="reset" class="btn-new-type">清空</button>
							</div>
						</div>
					</form>

					<div class="row ">
						<div class="col-md-12">
							<!--<div class="box box-default">
								<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员每天注册、认证、缴押金、退款的数量</span>
							</div>-->
							<div class="">

								<div id="memberOperateCountDay" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<br/>
					<div class="row">
						<div class="col-md-12">
							<!--<div class="box box-default">
								<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员注册、认证、缴押金、退款按天合计的数量</span>
							</div>-->
							<div class="">

								<div id="memberOperateDayCount" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					</br>
				</div>
				<div role="tabpanel" class="tab-pane" id="memberCountWeek">

					<form class="form-horizontal" name="memberSerachWeekForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始</span><input  class="datetimepicker form-control" name="startTimeWeek" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束</span> <input  class="datetimepicker form-control" name="endTimeWeek" style="background:#FFFFFF" readonly/>
								</div>
							<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="memberSearchWeek">确定</button>
									<button type="reset" class="btn-new-type">清空</button>
							</div>
						</div>
					</form>

					<div class="row">
						<div class="col-md-12">
							<!--<div class="box box-default">
								<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员每周注册、认证、缴押金、退款的数量</span>
							</div>-->
							<div class="">

								<div id="memberOperateCountWeek" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="col-md-12">
							<!--<div class="box box-default">
								<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员注册、认证、缴押金、退款按周合计的数量</span>
							</div>-->
							<div class="">

								<div id="memberOperateWeekCount" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					</br>
				</div>
				<div role="tabpanel" class="tab-pane" id="memberCountMonth">

					<form class="form-horizontal" name="memberSerachMonthForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始</span> <input  class="datetimepicker form-control" name="startTimeMonth" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束</span> <input  class="datetimepicker form-control" name="endTimeMonth" style="background:#FFFFFF" readonly/>
								</div>
							<!--修改-->
							<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="memberSearchMonth">确定</button>
									<button type="reset" class="btn-new-type">清空</button>
							</div>
						</div>
					</form>

					<div class="row">
						<div class="col-md-12">
							<div class="">
								<!--<div class="box box-default">
									<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员每月注册、认证、缴押金、退款的数量</span>
								</div>-->
								<div id="memberOperateCountMonth" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="col-md-12">
							<!--<div class="box box-default">
								<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员注册、认证、缴押金、退款按月合计的数量</span>
							</div>-->
							<div class="">

								<div id="memberOperateMonthCount" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					</br>
				</div>

				<div role="tabpanel" class="tab-pane" id="memberCountYear">

					<form class="form-horizontal" name="memberSerachYearForm">
						<div class="row pull-down-menu">
								<div class="seach-input-item pull-left">
									<span >时间开始</span><input  class="datetimepicker form-control" name="startTimeYear" style="background:#FFFFFF" readonly/>
								</div>
								<div class="seach-input-item pull-left">
									<span >时间结束</span> <input  class="datetimepicker form-control" name="endTimeYear" style="background:#FFFFFF" readonly/>
								</div>
							<!--修改-->
							<div class="box-footer-btn">
									<button type="button" class="btn-new-type" id="memberSearchYear">确定</button>
									<button type="reset" class="btn-new-type">清空</button>
							</div>
						</div>
					</form>

					<div class="row">
						<div class="col-md-12">
							<!--<div class="box box-default">
								<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员每年注册、认证、缴押金、退款的数量</span>
							</div>-->
							<div class="">

								<div id="memberOperateCountYear" style="width: 1300px;height:320px;"></div>

							</div>
							<!-- / box-->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="col-md-12">
							<!--<div class="box box-default">
								<span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：会员注册、认证、缴押金、退款按年合计的数量</span>
							</div>-->
							<div class="">

								<div id="memberOperateYearCount" style="width: 1300px;height:320px;"></div>

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
								"memberOperateCount": "res/js/operateCount/member_operate_count"
							}
						});
						require(["memberOperateCount"], function(memberOperateCount) {
							memberOperateCount.init();
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

						$(".monthTime").datepicker({
							language: "zh-CN",
							autoclose: true, //选中之后自动隐藏日期选择框
							clearBtn: true, //清除按钮
							todayBtn: 'linked', //今日按钮
							format: "yyyy-mm" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
						});

						$(".yearTime").datepicker({
							language: "zh-CN",
							autoclose: true, //选中之后自动隐藏日期选择框
							clearBtn: true, //清除按钮
							todayBtn: 'linked', //今日按钮
							format: "yyyy" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
						});

					});
				</script>