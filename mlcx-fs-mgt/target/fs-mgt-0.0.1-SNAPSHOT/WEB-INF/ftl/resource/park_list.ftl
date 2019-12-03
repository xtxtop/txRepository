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
		<div class="pull-right moreButtonNew" id="moreParkList">
		<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="parkSearchForm">
				<!-- /.box-tools -->
				<div class="seach-input-details parkList close-content clearfix">
					<div class="seach-input-item pull-left">
						<span>编号</span>
						<input type="text" class="form-control" id="parkNo" name="parkNo" placeholder="请输入编号" value="${parkNo}">
					</div>
					<div class="seach-input-item pull-left">
						<span>名称</span>
						<input type="text" class="form-control" id="parkName" name="parkName" placeholder="请输入场站名称">
					</div>
					<div class="seach-input-item pull-left">
						<span>状态</span>
						<select class="form-control" name="isAvailable">
							<option value="">全部</option>
							<option value="0">停用</option>
							<option value="1">启用</option>
						</select>
					</div>
					<div class="seach-input-item pull-left">
						<span>服务</span>
						<select class="form-control" name="supportedServices">
							<option value="">全部</option>
							<option value="快充">快充</option>
							<option value="慢充">慢充</option>
							<option value="清洗">清洗</option>
							<option value="维修">维修</option>
						</select>
					</div>
					<div class="seach-input-item pull-left">
						<span>类别</span>
						<select class="form-control" name="parkType">
							<option value="">全部</option>
							<option value="1">管理类</option>
							<option value="2">使用类</option>
						</select>
					</div>
					<div class="seach-input-item pull-left">
						<span>是否车位不足</span>
						<select class="form-control" name="isLotParkingSpace">
							<option value="">全部</option>
							<option value="1" <#if isLotParkingSpace!=null && isLotParkingSpace==1>selected</#if> >是</option>
							<option value="0">否</option>
						</select>
					</div>
				</div>
				<div class="seach-input-border-box">
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="parkSearchafter">确定</button>
					</div>
				</div>
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.row -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="parkTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="parkList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>
<!-- /.container-fluid -->

<div class="modal fade" id="onParkModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定启用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="onParkForm">
					<input type="hidden" name="parkNo" id="parkNo1">
					<input type="hidden" name="isAvailable" value="1">
					<label for="inputEmail3" class=" control-label wen" id="onParkMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">理由:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control textareas" name="memo"></textarea>
						</div>
					</div>
			</div>

			</form>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-right sure" id="parkOnFormBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="parkOnCancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="offParkModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定停用</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="offParkForm">
					<input type="hidden" name="parkNo" id="parkNo2">
					<input type="hidden" name="isAvailable" value="0">
					<label for="inputEmail3" class=" control-label wen" id="offParkMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">理由:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control textareas" name="memo"></textarea>
						</div>
					</div>

				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="parkOffBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="parkOffCancel">取消</button>
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="onViewModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定可见</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="onViewForm">
					<input type="hidden" name="parkNo" id="parkOnView">
					<input type="hidden" name="isView" value="1">
					<label for="inputEmail3" class=" control-label wen" id="viewMemo"></label>
			</div>

			</form>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-right sure" id="onViewBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="onViewCancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="noViewModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定隐藏</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="noViewForm">
					<input type="hidden" name="parkNo" id="parkNoView">
					<input type="hidden" name="isView" value="0">
					<label for="inputEmail3" class=" control-label wen" id="noViewMemo"></label>
			</div>

			</form>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-right sure" id="noViewBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="noViewCancelBtn">取消</button>
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
				"park": "res/js/resource/park_list"
			}
		});
		require(["park"], function(park) {
			park.init();
		});
	});
</script>