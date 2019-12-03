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
		<div class="pull-right moreButtonNew" id="moreMemberPointsRecord">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="memberPointsRecordSerachForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details MemberPointsRecordList close-content clearfix">
							<div class="seach-input-item pull-left">
								<span>积分业务类型</span>
								<select class="form-control" name="businessType">
									<option value="">全部</option>
									<option value="1">订单支付</option>
									<option value="2">套餐支付</option>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>积分类型</span>
								<select class="form-control" name="pointsType">
									<option value="">全部</option>
									<option value="0">会员经验积分</option>
									<option value="1">可用于消费的积分</option>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>创建时间</span><input class="datetimepicker form-control" name="createTimeStart" style="background:#FFFFFF" readonly placeholder="请选择开始时间"/>
							</div>
							<div class="seach-input-item pull-left">
								<span>至</span><input class="datetimepicker form-control" name="createTimeEnd" style="background:#FFFFFF" readonly placeholder="请选择结束时间"/>
							</div>
							<div class="seach-input-item pull-left">
								<span>操作类型</span>
								<select class="form-control" name="opType">
									<option value="">全部</option>
									<option value="1">增加/获得积分</option>
									<option value="0">扣除/使用积分</option>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>会员姓名</span>
								<input class="form-control" name="memberName" placeholder="请输入会员姓名">
							</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="memberPointsRecordSearch">确定</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- /.col -->
<div class="row show-table-content">
	<div class="col-xs-12">
		<!--定义操作列按钮模板-->
		<script id="memberPointsRecordBtnTpl" type="text/x-handlebars-template">
			{{#each func}}
			<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
		</script>
		<div class="box">
			<div class="box-body">
				<table id="memberPointsRecordList" class="table table-hover table-bettween-far" width="100%" border="1">
				</table>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>
</div>
</div>
<!-- /.container-fluid -->
<!--
    <script type="text/javascript" src="${basePath!'' }/res/js/member/main.js"></script>
    -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"memberPointsRecord": "res/js/member/member_points_record_list"
			}
		});
		require(["memberPointsRecord"], function(memberPointsRecord) {
			memberPointsRecord.init();
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