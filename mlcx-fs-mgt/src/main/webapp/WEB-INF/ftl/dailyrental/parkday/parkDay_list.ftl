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
		<div class="search-input-content">
			<form class="form-horizontal" name="parkDaySearchForm">
				<div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>门店名称</span>
							<input type="text" class="form-control" id="parkName" name="parkName" placeholder="请输入门店名称">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>状态</span>
							<select class="form-control" name="isVailable">
								<option value="">全部</option>
								<option value="0">停用</option>
								<option value="1">启用</option>
							</select>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="parkDaySearchafter">确定</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="parkDayTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="parkDayList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>
<!-- /.container-fluid -->

<div class="modal fade" id="onParkDayModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定启用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="onParkDayForm">
					<input type="hidden" name="parkId">
					<input type="hidden" name="isVailable" value="1">
					<label for="inputEmail3" class=" control-label wen" id="onParkDayMemo"></label>
			</div>

			</form>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-right sure" id="parkDayOnFormBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="parkDayOnCancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="offParkDayModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定停用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="offParkDayForm">
					<input type="hidden" name="parkId">
					<input type="hidden" name="isVailable" value="0">
					<label for="inputEmail3" class=" control-label wen" id="offParkDayMemo"></label>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="parkDayOffBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="parkDayOffCancel">取消</button>
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
				"parkDay": "res/js/dailyrental/parkday/parkDay_list"
			}
		});
		require(["parkDay"], function(parkDay) {
			parkDay.init();
		});
	});
</script>