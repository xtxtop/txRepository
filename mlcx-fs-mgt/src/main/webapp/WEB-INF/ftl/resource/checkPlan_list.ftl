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
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
		<div class="search-input-content">
			<!-- /.box-header -->
			<form class="form-horizontal" name="checkPlanSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details checkPlanList close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>场站</span>
							<input type="text" class="form-control" id="parkName" name="parkName" placeholder="请输入场站">
						</div>
						<div class="seach-input-item pull-left">
							<span>计划巡检时间</span>
							<input class="datepicker form-control" name="planDateStart" placeholder="请选择巡检时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="planDateEnd" placeholder="请选择巡检结束时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>状态</span>
							<select class="form-control" name="planStatus">
								<option value="">全部</option>
								<option value="0">未完成</option>
								<option value="1">已完成</option>
								<option value="2">已取消</option>
							</select>
						</div>

					</div>
					<!-- /.row-->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="checkPlanSearchafter">确定</button>
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
			<script id="checkPlanTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="checkPlanList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>
<!-- /.container-fluid -->

<div class="modal fade" id="offcheckPlanModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="offcheckPlanForm">
					<input type="hidden" name="checkPlanNo" id="checkPlanNo">
					<input type="hidden" name="planStatus" value="2">
					<label for="inputEmail3" class=" control-label wen" id="offcheckPlanMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">*&nbsp;&nbsp;理由:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control textareas" name="memo"></textarea>
						</div>
					</div>

				</form>
				<div class="modal-footer">

					<button type="button" id="checkPlanOffBtn" class="btn btn-default pull-right btncolora sure">
                        确定
                     </button>
					<button type="button" class="btn btn-default pull-right cancels" id="checkPlanOffCancel">
                                             取消
                      </button>
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
				"checkPlan": "res/js/resource/checkPlan_list"
			}
		});
		require(["checkPlan"], function(checkPlan) {
			checkPlan.init();
		});
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: "linked", //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>