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
		</div>
		<div class="pull-right moreButtonNew" id="moreCustomerFeedbackSearch">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form name="customerFeedbackSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>客户</span>
							<input class="form-control pull-left" name="memberName" placeholder="请输入客户名称">
						</div>
						<div class="seach-input-item pull-left">
							<span>手机号</span>
							<input class="form-control" name="mobilePhone" placeholder="请输入手机号码">
						</div>
						<div class="seach-input-item pull-left">
							<span>回复状态</span>
							<select class="form-control" name="replyStatus">
								<option value="">全部</option>
								<option value="0" <#if replyStatus==0>
									selected="selected"
									</#if>>未回复</option>
								<option value="1">已回复</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>提交时间</span>
							<input class="datepicker form-control" name="createTimeStart" placeholder="请选择提交时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="createTimeEnd" placeholder="请选择结束时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>回复时间</span>
							<input class="datepicker form-control" name="replyTimeStart" placeholder="请选择回复时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="replyTimeEnd" placeholder="请选择结束时间" />
						</div>
					</div>
					<!-- /.row -->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="customerFeedbackSearch">确定</button>
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
			<script id="customerFeedbackBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="customerFeedbackList" class="table table-bettween-far table-hover" width="100%" border='1'>
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
				"customerFeedback": "res/js/customer/customerFeedback_list"
			}
		});
		require(["customerFeedback"], function(customerFeedback) {
			customerFeedback.init();
		});
	});
	$(function() {
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>