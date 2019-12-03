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
		<div class="pull-right moreButtonNew" id="moreWarningList">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="warningSearchForm">
				<input type="hidden" name="type" value="${type}">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>告警级别</span>
							<select class="form-control" name="warningLevel">
								<option value="">全部</option>
								<option value="1">一级</option>
								<option value="2">二级</option>
								<option value="3">三级</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>告警对象</span>
							<select class="form-control" name="warningType">
								<option value="">全部</option>
								<#list warningTypes as warningType>
									<option value="${warningType.itemValue}">
										${warningType.itemValue}
									</option>
								</#list>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>告警时间</span>
							<input class="datepicker form-control" name="warningTimeStart" placeholder="请选择告警时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>至</span>
							<input class="datepicker form-control" name="warningTimeEnd" placeholder="请选择结束时间" />
						</div>
						<div class="seach-input-item pull-left">
							<span>需人工关闭</span>
							<select class="form-control" name="isNeedManualClosed">
								<option value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>是否关闭</span>
							<select class="form-control" name="isClosed">
								<option value="">全部</option>
								<option value="1">已关闭</option>
								<option value="0">未关闭</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>城市</span>
							<select class="form-control" name="cityId">
								<option value="">全部</option>
								<#if cities?exists>
									<#list cities as city>
										<option value="${city.dataDictItemId}">${city.itemValue}</option>
									</#list>
								</#if>
							</select>
						</div>
					</div>
										<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="warningSearchafter">确定</button>
					</div>
				</div>
				<!-- /.box-header -->
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="warningTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="warningList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>
<!-- /.container-fluid -->
<div class="modal fade" id="OffWarningModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定关闭</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="offForm">
					<input type="hidden" name="warningNo" id="warningNo">
					<input type="hidden" name="carPlateNo" id="carPlateNo">
					<input type="hidden" name="isClosed" value="1">
					<label for="inputEmail3" class=" control-label wen"><img src="res/img/wen.png" style="width:29px;hegiht:29px;margin-top:-4px">&nbsp;&nbsp;您确定关闭该告警吗？</label>
					<div>
						<label for="inputEmail3" class=" control-label reason">*备注:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control textareas" name="closedMemo"></textarea>
						</div>
					</div>

				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="warningOffBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="warningOffCancel">取消</button>
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
				"warning": "res/js/monitor/warning_list"
			}
		});
		require(["warning"], function(warning) {
			warning.init();
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