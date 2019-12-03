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
			<form class="form-horizontal" name="carModelSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>车型</span>
							<input type="text" class="form-control" name="carModelName" placeholder="请输入车型">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>品牌</span>
							<select class="form-control" name="carBrandId">
								<option value="" selected="selected">全部</option>
								<#list lb as l>
									<option value="${l.carBrandId}">
										${l.carBrandName}
									</option>
								</#list>
							</select>
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>类别</span>
							<select class="form-control" name="carType">
								<option value="" selected="selected">全部</option>
								<option value="1">经济型</option>
								<option value="2">商务型</option>
								<option value="3">豪华型</option>
								<option value="4">6至15座商务</option>
								<option value="5">SUV</option>
							</select>
						</div>
					</div>
					<!-- /.box-body -->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carModelSearchafter">确定</button>
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
			<script id="carModelTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="carModelList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->
<div class="modal fade" id="carModelOnLineModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定上架</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="carModelOnLineForm">
					<input type="hidden" name="carModelId">
					<input type="hidden" name="onOffLineStatus" value="1">
					<label for="inputEmail3" class=" control-label wen" id="carModelOnLineMemo"></label>
				</form>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-right sure" id="carModelOnLineFormBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="carModelOnLineCancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="carModelOffLineModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定停用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="carModelOffLineForm">
					<input type="hidden" name="carModelId">
					<input type="hidden" name="onOffLineStatus" value="0">
					<label for="inputEmail3 wen" class=" control-label" id="carModelOffLineMemo"></label>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="carModelOffLineBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="carModelOffLineCancel">取消</button>
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
				"carModel": "res/js/dailyrental/car/car_model_list"
			}
		});
		require(["carModel"], function(carModel) {
			carModel.init();
		});
	});
</script>