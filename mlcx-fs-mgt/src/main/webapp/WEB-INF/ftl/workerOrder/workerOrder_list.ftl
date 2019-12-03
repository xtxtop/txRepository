<meta charset="utf-8">
<style>
	.seach-input-item pull-left span {
		white-space: nowrap !important;
	}
	
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
			white-space: nowrap !important;
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
		<div class="pull-right moreButtonNew" id="moreWokerOrder">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form name="workerOrderSerachForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details workerOrder close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>工单编号</span>
							<input class="form-control" name="workerOrderNo" value="${workerOrderNo}"  placeholder="请输入工单编号">
						</div>
						<div class="seach-input-item pull-left">
							<span>类型</span>
							<select class="form-control" name="type">
								<option value="">全部</option>
								<option value="0">调度</option>
								<option value="1">救援</option>
								<option value="2">清洁</option>
								<option value="3">加油</option>
								<option value="4">维保</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>审核状态</span>
							<select class="form-control" name="cencorStatus">
								<option value="">全部</option>
								<option value="0" <#if censorStatus!=null && censorStatus==0>selected</#if> >未审核</option>
								<option value="1">已审核</option>
								<option value="2">未通过</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>工单状态</span>
							<select class="form-control" name="workOrderStatus">
								<option value="">全部</option>
								<option value="0">未下发</option>
								<option value="1" <#if workOrderStatus!=null && workOrderStatus==1>selected</#if> >已下发</option>
								<option value="2" <#if workOrderStatus!=null && workOrderStatus==2>selected</#if> >调度中</option>
								<option value="3">已结束</option>
								<option value="4">已取消</option>
							</select>
						</div>
						<input name="workerId" type="hidden" value="${workerId}" />
						<div class="seach-input-item pull-left">
							<span>调度员</span>
							<input class="form-control" name="workerName"  placeholder="请输入调度员名称">
						</div>
						<div class="seach-input-item pull-left">
							<span>车牌号</span>
							<input class="form-control" name="carPlateNo"  placeholder="请输入车牌号">
						</div>
						<div class="seach-input-item pull-left">
							<span>下发时间开始</span>
							<input class="datepicker form-control" name="sendTimeStart" style="background:#FFFFFF" readonly  placeholder="请选择下发开始时间"/>
						</div>
						<div class="seach-input-item pull-left">
							<span>下发时间结束</span>
							<input class="datepicker form-control" name="sendTimeEnd" style="background:#FFFFFF" readonly  placeholder="请选择下发结束时间"/>
						</div>
						<div class="seach-input-item pull-left">
							<span>完成时间开始</span>
							<input class="datepicker form-control" name="finishTimeStart" style="background:#FFFFFF" readonly placeholder="请选择完成开始时间"/>
						</div>
						<div class="seach-input-item pull-left">
							<span>完成时间结束</span>
							<input class="datepicker form-control" name="finishTimeEnd" style="background:#FFFFFF" readonly placeholder="请选择完成结束时间"/>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="workerOrderSearch">确定</button>
					</div>
				</div>
			</form>
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="workerOrderBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="workerOrderList" class="table table-hover table-bettween-far" width="100%" border="1">
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

<div class="modal fade" id="cencorWorkerModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">

			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="onWorkerOrderForm">
					<input type="hidden" name="workerOrderNo" id="workerOrderNo1">
					<label for="inputEmail3" class=" control-label wen" id="onWorkerOrderMemo"></label>
					<div style='margin-left:33px;margin-top:10px;'>
						<label style='font-weight:100 !important;color: #5a6777;'><input name="cencorStatus" type="radio" value="1" checked="checked" style='font-weight:100 !important'/>通过 </label>
						<label style='font-weight:100 !important;color: #5a6777;'><input name="cencorStatus" type="radio" value="2" />不通过</label>
					</div>
					<label for="inputEmail3" class=" control-label reason">审核备注:</label>
					<div class="form-group">
						<div class="col-sm-8">
							<textarea class="form-control textareas" name="cencorMemo"></textarea>
						</div>
					</div>
			</div>

			</form>
			<div class="modal-footer">

				<input type="button" class="btn btn-default pull-right sure" id="workerOrderOnFormBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="workerOrderOnCancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript">
	require.config({
		paths: {
			"workerOrder": "res/js/workerOrder/workerOrder"
		}
	});
	require(["workerOrder"], function(workerOrder) {
		workerOrder.init();
	});
	$(function() {
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: "linked", //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>