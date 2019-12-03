<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		.frombg .form-control {
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
		.frombg .form-control {
			margin-right: 10%；
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
	
	.float {
		float: right;
	}
	
	.floatLeft {
		float: left;
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
		<div class="pull-right moreButtonNew" id="moreCarStatus">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="carStatusSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details car-status close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>车牌号</span>
							<input type="text" placeholder="请输入车牌号" class="form-control" id="carPlateNo" name="carPlateNo" placeholder="">
						</div>
						<div class="seach-input-item pull-left">
							<span>终端序列号</span>
							<input type="text" placeholder="请输入终端序列" class="form-control" id="deviceSn" name="deviceSn" placeholder="">
						</div>
						<div class="seach-input-item pull-left">
							<span>终端在线</span>
							<select class="form-control" name="isOnline">
								<option value="">全部</option>
								<option value="1">在线</option>
								<option value="2">不在线</option>
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
								<#-- <option value="4">维保中</option>-->
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>上线状态</span>
							<select class="form-control" name="onlineStatus">
								<option value="">全部</option>
								<option value="1">上线</option>
								<option value="0">下线</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>是否小电瓶低电</span>
							<select class="form-control" name="isMinLowPower">
								<option value="">全部</option>
								<option value="1" <#if carStatus.isMinLowPower!=null && carStatus.isMinLowPower==1>selected</#if> >是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>是否电/油量不足</span>
							<select class="form-control" name="isLowPower">
								<option value="">全部</option>
								<option value="1" <#if carStatus.isLowPower!=null && carStatus.isLowPower==1>selected</#if> >是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
							<span>是否断线</span>
							<select class="form-control" name="isBrokenLine">
								<option value="">全部</option>
								<option value="1" <#if carStatus.isBrokenLine!=null && carStatus.isBrokenLine==1>selected</#if> >是</option>
								<option value="0">否</option>
							</select>
						</div>

						<div class="seach-input-item pull-left">
							<span>可续航</span>
							<input type="text" class="form-control" id="rangeMileage" name="rangeMileage" placeholder="请输入关键字">
						</div>
						<div class="seach-input-item pull-left">
							<span>车辆状态</span>
							<select class="form-control" name="carStatus">
								<option value="">全部</option>
								<option value="1">已点火</option>
								<option value="2">已熄火</option>
							</select>
						</div>
						<div class="seach-input-item car-status-dian-you pull-left">
							<span>电/油量</span>
							<div class="pull-left">
								<input type="text" class="form-control pull-left" id="powerSmall" name="powerSmall" placeholder="电量">
								<label class="pull-left">-</label>
								<input type="text" class="form-control pull-left" id="powerBig" name="powerBig" placeholder="油量">
								<label class="pull-left">%</label>
							</div>
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carStatusSearchafter">确定</button>
					</div>
				</div>
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<!-- /.row -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="carStatusTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
				{{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="carStatusList" class="table  table-hover" width="100%" border='1'>
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>
<!-- /.container-fluid -->

<div class="modal fade" id="operateModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close" id="carOperateClose"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title operateModal"></h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="carOperateForm">
					<input type="hidden" name="deviceNo">
					<input type="hidden" name="cmdValue">
					<input type="hidden" name="subUrl">
					<label for="inputEmail3" class=" control-label wen"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">备注:</label>
					</div>
					<div class="form-group">

						<div class="">
							<textarea class="form-control textareas" name="memo"></textarea>
						</div>
						<div style="margin-left:30px;"><span name="memoAdd"></span></div>
					</div>

				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="carOperateBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="carOperateCancel">取消</button>
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
				"carStatus": "res/js/carStatus/car_status_list"
			}
		});
		require(["carStatus"], function(carStatus) {
			carStatus.init();
		});
	});
</script>