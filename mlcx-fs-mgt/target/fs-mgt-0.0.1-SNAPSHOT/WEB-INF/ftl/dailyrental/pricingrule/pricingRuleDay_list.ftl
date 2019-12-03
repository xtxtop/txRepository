<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		. .form-control {
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
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="pricingRuleDaySearchForm">
				<div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>名称</span>
							<input type="text" class="form-control" id="ruleName" name="ruleName" placeholder="请输入名称">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>车型名称</span>
							<select class="form-control" name="carModelId">
								<option value="">全部</option>
								<#list carModels as carModel>
									<option value="${carModel.carModelId}">
										${carModel.carModelName}
									</option>
								</#list>
							</select>
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>状态</span>
							<select class="form-control" name="isAvailable">
								<option value="">全部</option>
								<option value="1">启用</option>
								<option value="0">停用</option>
							</select>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="pricingRuleDaySearchafter">确定</button>
					</div>
				</div>
				<!-- /.box-body -->
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="pricingRuleDayTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="pricingRuleDayList" class="table table-hover table-bettween-far" width="100%" border='1'>
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->

<div class="modal fade" id="pricingRuleDayOnModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定启用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="pricingRuleDayOnForm">
					<input type="hidden" name="ruleId">
					<input type="hidden" name="isAvailable" value="1">
					<label for="inputEmail3" class=" control-label wen" id="onPricingDayRuleMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">*原因:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-8">
							<textarea class="form-control" name="availableMemo"></textarea>
						</div>
					</div>
			</div>

			</form>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-right sure" id="pricingRuleDayOnFormBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="pricingRuleDayOnCancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="pricingRuleDayOffModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定停用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="pricingRuleDayOffForm">
					<input type="hidden" name="ruleId">
					<input type="hidden" name="isAvailable" value="0">
					<label for="inputEmail3 wen" class=" control-label" id="offPricingDayRuleMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">*原因:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control textareas" name="availableMemo"></textarea>
						</div>
					</div>

				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="pricingRuleDayOffBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="pricingRuleDayOffCancel">取消</button>
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"pricingRuleDay": "res/js/dailyrental/pricingrule/pricingRuleDay_list"
			}
		});
		require(["pricingRuleDay"], function(pricingRuleDay) {
			pricingRuleDay.init();
		});
		$("input[name='customizedDateStr']").datepicker({
			language: "zh-CN",
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			multidate: true,
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>