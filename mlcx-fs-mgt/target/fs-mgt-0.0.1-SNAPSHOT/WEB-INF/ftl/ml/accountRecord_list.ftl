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
	
	.box-footer button {
		margin: 0 1%;
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
	
	.table-bettween-far tr td {
		height: 72px;
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="moreaccountRecordList">
			<!-- <div class="up-box">
				<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
			</div> -->
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="accountRecordSearchForm">
			<input type="hidden" name="memberNo" value="${memberNo }">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details accountRecordlist close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>编号</span>
							<input class="form-control" name="id" placeholder="请输入交易记录编号" value="">
						</div>
						<div class="seach-input-item pull-left">
							<span>交易类型</span>
						     <select name="dealType" class="form-control">
						      <option value="">--全部--</option>
						      <option value="1">充电</option>
						      <option value="2">停车</option>
						     </select>
						</div>
						<div class="seach-input-item pull-left">
							<span>类型</span>
						     <select name="type" class="form-control">
						      <option value="">--全部--</option>
						      <option value="1">充值</option>
						      <option value="2">赠送</option>
						      <option value="3">抵扣</option>
						      <option value="4">返还</option>
						     </select>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="accountRecordSearch">确定</button>
					</div>
				</div>
				<!-- /.box-header -->
				<input type="hidden" name="refereeId" value="${refereeId}" />

			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12" style="background:#fff;">
			<!--定义操作列按钮模板-->
			<script id="accountRecordTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body" style="background:#fff;">
					<table id="accountRecordList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"accountRecord": "res/js/ml/accountRecord_list"
			}
		});
		require(["accountRecord"], function(accountRecord) {
			accountRecord.init();
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