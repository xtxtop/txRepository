<meta charset="utf-8">
<style>
.seach-input-item pull-left .form-control {
	width: 50% !important;
}

@media only screen and (max-width:992px) {
	.pull-down-menu input, .pull-down-menu select {
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
		white-space: nowrap;
	}
}

@media only screen and (min-width:768px) and (max-width:1024px) {
	.row .sorting_disabled {
		font-size: 1.2rem !important;
		white-space: nowrap;
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
<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
	<ul id="moneySummaryTabs" class="nav nav-tabs">
		<li class="active"><a href="#defaultSummaryTab"
			aria-controls="defaultSummaryTab" role="tab" data-toggle="tab">默认最近一月</a></li>
		<li><a href="#monthSummaryTab" aria-controls="monthSummaryTab"
			role="tab" data-toggle="tab">按月统计</a></li>
		<li><a href="#daySummaryTab" aria-controls="daySummaryTab"
			role="tab" data-toggle="tab">按天统计</a></li>
	</ul>
	<div class="tab-content">
	<!-- 默认显示最近一月的 -->
	<div class="tab-pane container-fluid active" id="defaultSummaryTab">
		<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
			<div class="search-input-content">
				<form name="defaultMoneySummarySerachForm">
					<div class="seach-input-border-box">
						
						<!-- /.box-tools -->
						<div
							class="seach-input-details close-content clearfix">
							<div class="seach-input-item seach-input-item-change pull-left">
								<span>资金汇总时间</span><input class="datepicker form-control"
									name="finishTimeStart" value="${finishTimeStart}"
									placeholder="" />
							</div>
							<div class="seach-input-item seach-input-item-change pull-left">
								<span>至</span><input class="datepicker form-control"
									name="finishTimeEnd" value="${finishTimeEnd}" placeholder="" />
							</div>

						</div>
						<div class="seacher-button-content">
							<button type="reset" class="btn-new-type">清空</button>
							<button type="button" class="btn-new-type"
								id="moneySummarySearchDefault">确定</button>
						</div>
					</div>
					<!-- /.box-body -->
				</form>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
		<div class="row show-table-content">
			<div class="col-xs-12">
				<!--定义操作列按钮模板-->
				<script id="moneySummaryBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
				<div class="box">
					<div class="box-body">
						<table id="moneySummaryList"
							class="table table-hover" width="100%"
							border="1">
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
	<!-- 月统计 -->
	<div class="tab-pane container-fluid" id="monthSummaryTab">
		<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
			<div class="search-input-content">
				<form name="monthMoneySummarySerachForm">
					<div class="seach-input-border-box">
						<div
							class="seach-input-details close-content clearfix">
							<div class="seach-input-item seach-input-item-change pull-left">
								<span>资金汇总时间</span><input class="monthDatepicker form-control" name="startTime" value="" placeholder="" />
							</div>
							<div class="seach-input-item seach-input-item-change pull-left">
								<span>至</span><input class="monthDatepicker form-control" name="endTime" value="" placeholder="" />
							</div>

						</div>
						<div class="seacher-button-content">
							<button type="reset" class="btn-new-type">清空</button>
							<button type="button" class="btn-new-type"
								id="moneySummarySearchMonths">确定</button>
						</div>
					</div>
					<!-- /.box-body -->
				</form>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
		<div class="row show-table-content">
			<div class="col-xs-12">
				<!--定义操作列按钮模板-->
				<script id="moneySummaryMonthBtnTpl"
					type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
				<div class="box">
					<div class="box-body">
						<table id="moneySummaryMonthList"
							class="table table-hover" width="100%"
							border="1">
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
	<!-- 日统计 -->
	<div class="tab-pane container-fluid" id="daySummaryTab" >
		<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
			<div class="search-input-content">
				<form name="dayMoneySummarySerachForm">
					<div class="seach-input-border-box">
						
						<!-- /.box-tools -->
						<div
							class="seach-input-details seach-input-details-change close-content clearfix">
							<div class="seach-input-item seach-input-item-change pull-left">
								<span>资金汇总时间</span><input class="daysDatepicker form-control"
									name="finishTimeStart" value=""
									placeholder="" />
							</div>
							<div class="seach-input-item seach-input-item-change pull-left">
								<span>至</span><input class="daysDatepicker form-control"
									name="finishTimeEnd" value="" placeholder="" />
							</div>

						</div>
						<div class="seacher-button-content">
							<button type="reset" class="btn-new-type">清空</button>
							<button type="button" class="btn-new-type"
								id="moneySummarySearchDay">确定</button>
						</div>
					</div>
					<!-- /.box-body -->
				</form>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
		<div class="row show-table-content">
			<div class="col-xs-12">
				<!--定义操作列按钮模板-->
				<script id="moneySummaryDayBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
				<div class="box">
					<div class="box-body">
						<table id="moneySummaryDayList"
							class="table table-hover" width="100%"
							border="1">
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
</div>


</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"moneySummary" : "res/js/statement/moneySummary_list"
			}
		});
		require([ "moneySummary" ], function(moneySummary) {
			moneySummary.init();
		});
	});
	$(function() {
		$(".datepicker").datepicker({
			language : "zh-CN",
			autoclose : true,//选中之后自动隐藏日期选择框
			clearBtn : true,//清除按钮
			todayBtn : "linked",//今日按钮
			format : "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});

		$('.monthDatepicker').datetimepicker({
			format : 'yyyy-mm',
			weekStart : 1,
			autoclose : true,
			startView : 3,
			minView : 3,
			forceParse : false,
			language : 'zh-CN'
		});

		$(".daysDatepicker").datepicker({
			language : "zh-CN",
			autoclose : true,//选中之后自动隐藏日期选择框
			clearBtn : true,//清除按钮
			todayBtn : 'linked',//今日按钮
			format : "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});

	});
</script>
