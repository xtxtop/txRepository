<meta charset="utf-8">
<style>
	.btn-new {
		display: inline-block;
		padding: 6px 12px;
		margin-bottom: 0;
		font-size: 14px;
		font-weight: normal;
		line-height: 1.42857143;
		text-align: center;
		white-space: nowrap;
		vertical-align: middle;
		-ms-touch-action: manipulation;
		touch-action: manipulation;
		cursor: pointer;
		-webkit-user-select: none;
		-moz-user-select: none;
		-ms-user-select: none;
		user-select: none;
		background-image: none;
		border: 1px solid transparent;
		border-radius: 4px;
	}
	
	.btn-sm-new {
		height: 30px;
		padding: 5px 10px;
		font-size: 12px;
		line-height: 1.5;
		border-radius: 3px;
	}
	
	.btn-default-new {
		color: #333;
		background-color: #fff;
		border-color: #ccc;
	}
</style>
<div class="container-fluid backgroundColor">
	<form name="carRedPacketAddFrom" class="form-horizontal">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">红包车添加</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;车牌号：</label>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo" value="${carPlateNo}" readonly="readonly" />
						</td>
						<td>
							<label class="col-sm-4 col-xs-4 control-label key">&nbsp;&nbsp;目标场站：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="hidden" name="parkNo" />
							<input type="text" class="form-control val" name="parkName" readonly="readonly" />
							<input type="button" class="btn-btnA btn btn-info" id="carRedPacketAddPark" value="选择">
							<span name="parkNoAdd">
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;红包金额</label>
						</td>
						<td>
							<input class="form-control val" name="redPacketAmount" />
							<span name="redPacketAmountAdd"></span>
						</td>

						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;是否需要充电：</label>
						</td>
						<td>
							<input type="radio" name="isCharge" value="0" />否
							<input type="radio" name="isCharge" value="1" />是
							<span name="isChargeAdd"></span>
						</td>

					</tr>
					<tr>
						<td>
						<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;是否限制订单金额：</label>
					</td>
					<td>
						<input type="radio" name="isOrderAmountLimit" value="0" />否
						<input type="radio" name="isOrderAmountLimit" value="1" />是
						<span name="isOrderAmountLimitAdd">
					</td>
						<td colspan="2"></td>
					</tr>
					<tr id="orderAmountLimitAddDiv" style="display:none">
						
					<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;订单金额限额值：</label>
					</td>
					<td>
						<input class="form-control val" name="orderAmountLimit" />
						<span name="orderAmountLimitAdd"></span>
					</td>
                    <td colspan="2"></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="addCarRedPacket" class="btn-new-type-edit">
                保存
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 场站 -->
<div class="modal" id="carRedPacketAddParkModel">
	<div class="modal-dialog" style="width:750px;">
		<div class="modal-content">
			<form name="carRedPacketAddParkSerachForm">
				<div class="with-border">
					<div class="title-new-details">选择目标场站</div>
					<!-- /.box-tools -->
					<div class="pull-down-menu-car">
							<div class="parkNo-frombg">
								<span>场站编号：</span>
								<input class="form-control-input" name="parkNo" value="" placeholder="请输入场站编号">
							</div>
					</div>
				</div>
				<!-- /.box-header -->

				<div class="box-body">
				</div>
				<!-- /.box-body -->
				<!--修改-->
				<div class="box-bullet">
					<div class="box-footer">
						<button type="button" class="btn-new-type" id="carRedPacketAddParkSearch">确定</button>
						<button type="reset" class="btn-new-type" >清空</button>
					</div>
					<!-- /.box-footer -->
				</div>
			</form>
			<div class="border-bullet">
				
				<div class="box">
					<div class="box-header">
						<!-- <h3 class="box-title">数据列</h3> -->
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="carRedPacketAddParkList" class="table table-bordered table-hover" width="100%">
						</table>
					</div>
					<!-- /.box-body -->
					<!--定义操作列按钮模板-->
					<div class="carRedPacketAddParkTool-bullet" id="carRedPacketAddParkTool">
					</div>
				</div>
				<!-- /.box -->
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"carRedPacketAdd": "res/js/marketing/car_red_packet_add"
			}
		});
		require(["carRedPacketAdd"], function(carRedPacketAdd) {
			carRedPacketAdd.init();
		});
	});
</script>