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
		<div class="pull-right moreButtonNew" >
			<!-- <div class="up-box">
				<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
			</div> -->
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="accountBalanceSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details accountBalancelist close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>编号</span>
							<input class="form-control" name="memberNo" placeholder="请输入会员编号" value="">
						</div>
						<div class="seach-input-item pull-left">
							<span>姓名</span>
							<input class="form-control" name="memberName" placeholder="请输入姓名" value="">
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="accountBalanceSearch">确定</button>
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
			<script id="accountBalanceTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body" style="background:#fff;">
					<table id="accountBalanceList" class="table table-hover table-bettween-far" width="100%" border="1">
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
				"accountBalance": "res/js/ml/accountBalance_list"
			}
		});
		require(["accountBalance"], function(accountBalance) {
			accountBalance.init();
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