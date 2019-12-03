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
			<form name="companySearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>集团名称</span>
							<input class="form-control" name="companyName" placeholder="请输入集团名称">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>审核状态</span>
							<select class="form-control" name="cencorStatus">
								<option value="">全部</option>
								<option value="1">未审核</option>
								<option value="3">已审核</option>
								<option value="4">不通过</option>
							</select>
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>启用状态</span>
							<select class="form-control" name="companyStatus">
								<option value="">全部</option>
								<option value="1">启用</option>
								<option value="2">停用</option>
							</select>
						</div>

					</div>
					<!-- /.row -->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="companySearch">确定</button>
					</div>
				</div>
				<!-- /.box-body -->
			</form>
		</div>
	</div>
	<!-- /.box -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="companyBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="companyList" class="table  table-hover table-bettween-far" width="100%" border='1'>
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
<div class="modal fade" id="enableCompanyModel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">集团启用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="enableCompanyForm">
					<input type="hidden" name="companyId" id="enableCompanyNo">
					<input type="hidden" name="companyStatus" value="1">
					<label for="inputEmail3" class=" control-label wen" id="enableCompanyMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">理由:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-8">
							<textarea class="form-control textareas" name="companyMemo"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="enableCompanyBtn" value="确定">
						<button type="button" class="btn btn-default pull-right cancels" id="enableCompanyCancel">取消</button>
					</div>
				</form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div class="modal fade" id="disableCompanyModel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">集团停用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="disableCompanyForm">
					<input type="hidden" name="companyId" id="disableCompanyNo">
					<input type="hidden" name="companyStatus" value="2">
					<label for="inputEmail3" class=" control-label wen" id="disableCompanyMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">理由:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-8">
							<textarea class="form-control textareas" name="availableMemo"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="disableCompanyBtn" value="确定">
						<button type="button" class="btn btn-default pull-right cancels" id="disableCompanyCancel">取消</button>
					</div>
				</form>
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
				"company": "res/js/company/company_list"
			}
		});
		require(["company"], function(company) {
			company.init();
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