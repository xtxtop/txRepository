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
		<!--<div class="pull-right moreButtonNew" id="moreCarList">
			<div class="up-triangle"></div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>-->
		<div class="search-input-content">
			<form class="form-horizontal" name="carSearchForm">
				<!--修改 -->
				<div class="seach-input-border-box">

					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>车牌号</span>
							<input type="text" placeholder="请输入车牌号" class="form-control" id="carPlateNo" name="carPlateNo" placeholder="" value="${carPlateNo}">
						</div>
						<div class="seach-input-item pull-left">
							<span>终端序列</span>
							<input type="text" placeholder="请输入终端序列" class="form-control" id="deviceSn" name="deviceSn" placeholder="">
						</div>
						<div class="seach-input-item pull-left">
							<span>上线状态</span>
							<select class="form-control" name="onlineStatus">
								<option value="">全部</option>
								<option value="0" <#if onlineStatus!=null && onlineStatus==0>selected</#if> >未上线</option>
								<option value="1" <#if onlineStatus!=null && onlineStatus==1>selected</#if> >已上线</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>使用状态</span>
							<select class="form-control" name="userageStatus">
								<option value="">全部</option>
								<option value="0">空闲</option>
								<option value="1">预占</option>
								<option value="2">订单中</option>
								<option value="3">调度中</option>
								<option value="4">维保中</option>
							</select>
						</div>
						<!--<div class="seach-input-item pull-left">
							<span>租赁类型</span>
							<select class="form-control" name="leaseType">
								<option value="">全部</option>
								<option value="1">分时</option>
								<option value="2">长租</option>
							</select>
						</div>-->
						<div class="seach-input-item pull-left">
							<span>是否闲置</span>
							<select class="form-control" name="isIdle">
								<option value="">全部</option>
								<option value="1" <#if isIdle!=null && isIdle==1>selected</#if> >闲置</option>
								<option value="0">不闲置</option>
							</select>
						</div>
					</div>
										<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carSearchafter">确定</button>
					</div>
				</div>
				<!-- /.box-header -->
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->

		<!-- /.col -->
	</div>
	<!-- /.row -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="carTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="carList" class="table table-hover" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>
<!-- /.container-fluid -->

<div class="modal fade" id="onModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定上线</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="onForm">
					<input type="hidden" name="carNo" id="carNo1">
					<input type="hidden" name="onlineStatus" value="1">
					<div>

						<label for="inputEmail3" class=" control-label wen" id="onMemo"></label>

						<div>
							<label for="inputEmail3" class=" control-label reason">上线理由:</label>
						</div>
						<div class="form-group">

							<div class="col-sm-8">
								<select name="updownWhy" class="form-control" id="updownWhy" style="margin-left:30px;margin-top:10px;color:#5a6777;">
									<#list carUpWhy as updownWhy>
										<option <#if updownWhy.itemValue==updownWhy.itemValue>selected</#if> value="${updownWhy.itemValue}"> ${updownWhy.itemValue}
										</option>
									</#list>
								</select>
							</div>
						</div>

						<div>
							<label for="inputEmail3" class=" control-label reason">备注:</label>
						</div>
						<div class="form-group">

							<div class="col-sm-8">
								<textarea class="form-control textareas" name="onOffLineReason"></textarea>
							</div>
						</div>
					</div>

				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="carOnFormBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="carOnCancelBtn">取消</button>
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="OffModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定下线</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="offForm">
					<input type="hidden" name="carNo" id="carNo2">
					<input type="hidden" name="onlineStatus" value="0">
					<label for="inputEmail3" class=" control-label wen" id="offMemo"></label>

					<div>
						<label for="inputEmail3" class=" control-label reason">下线理由:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<select name="updownWhy" class="form-control" id="updownWhy" style="margin-left:30px;margin-top:10px;color:#5a6777;">
								<#list carDownWhy as updownWhy>
									<option <#if updownWhy.itemValue==updownWhy.itemValue>selected</#if> value="${updownWhy.itemValue}"> ${updownWhy.itemValue}
									</option>
								</#list>
							</select>
						</div>
					</div>
					<div>
						<label for="inputEmail3" class=" control-label reason">备注:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control textareas" name="onOffLineReason"></textarea>
						</div>
						<div><span name="onOffLineReasonNo"></span></div>
					</div>

				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="carOffBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="carOffCancel">取消</button>
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
				"car": "res/js/car/car_list"
			}
		});
		require(["car"], function(car) {
			car.init();
		});
	});
</script>