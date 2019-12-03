<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carRedPacketEditFrom">
		<input class="form-control val" name="carRedPacketId" type="hidden" value="${carRedPacket.carRedPacketId}" readonly="readonly" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">红包车编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车牌号：</label>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo" value="${carRedPacket.carPlateNo}" readonly="readonly" />
						</td>
						<td>
							<label class="control-label key">目标场站：</label>
						</td>
						<td>
							<input type="hidden" name="parkNo" value="${carRedPacket.parkNo}" />
							<input type="text" class="form-control val" value="${carRedPacket.parkName}" name="parkName" readonly="readonly" />
							<input type="button" class="btn btn-info" id="carRedPacketEditPark" value="选择">
							<span name="parkNoEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>红包金额</label>
						</td>
						<td>
							<input class="form-control val" name="redPacketAmount" value="${carRedPacket.redPacketAmount}" />
							<span name="redPacketAmountEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>是否需要充电：</label>
						</td>
						<td>
							<input name="isCharge" type="radio" <#if carRedPacket.isCharge==1>checked</#if> value="1" />是
							<input name="isCharge" type="radio" <#if carRedPacket.isCharge==0>checked</#if> value="0" />否
							<span name="isChargeEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否限制订单金额：</label>
						</td>
						<td>
							<input name="isOrderAmountLimit" type="radio" <#if carRedPacket.isOrderAmountLimit==1>checked</#if> value="1" />是
							<input name="isOrderAmountLimit" type="radio" <#if carRedPacket.isOrderAmountLimit==0>checked</#if> value="0" />否
							<span name="isOrderAmountLimitEdit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr  class="form-group" hidden="hidden" id="orderAmountLimitEditDiv">
						<td>
							<label class=" control-label key"><span>*</span>订单金额限额值：</label>
						</td>
						<td>
							<input class="form-control val" name="orderAmountLimit" carRedPacketId value="${carRedPacket.orderAmountLimit}" />
							<span name="orderAmountLimitEdit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editCarRedPacket" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditCarRedPacket" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 场站 -->
<div class="modal" id="carRedPacketEditParkModel">
	<div class="modal-dialog" style="width:750px;">
		<div class="modal-content">
			<form name="carRedPacketEditParkSerachForm">
				<div class="with-border">
					<div class="box-tools pull-right">
					</div>
					<!-- /.box-tools -->
					<div class="row pull-down-menu-car col-md-11" style="margin-left: 1px; background: #f1f5f8;width: 748px;">
						<div class="col-md-3">
							<div class="frombg">
								<span style="width: 45%">场站编号</span>
								<input class="form-control" name="parkNo" value="">
							</div>
						</div>
					</div>
				</div>
				<!-- /.box-header -->

				<div class="box-body">
				</div>
				<!-- /.box-body -->
				<!--修改-->
				<div class="col-md-12">
					<div class="box-footer">
						<!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
						<button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="carRedPacketEditParkSearch" style="background:#2b94fd;margin-right:330px !important;margin-top: -48px;">确定</button>
						<button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb" style="background:#fa6e30;float:right;margin-right:435px !important;margin-top: -48px;">清空</button>
					</div>
					<!-- /.box-footer -->
				</div>
			</form>
			<div class="col-xs-14">
				<!--定义操作列按钮模板-->
				<script id="carRedPacketEditParkBtnTpl" type="text/x-handlebars-template">
					{{#each func}}
					<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
				</script>
				<div class="box">

					<div class="box-header">
						<!-- <h3 class="box-title">数据列</h3> -->
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="carRedPacketEditParkList" class="table table-bordered table-striped table-hover" width="100%">
						</table>
					</div>
					<!-- /.box-body -->
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
				"carRedPacketEdit": "res/js/marketing/car_red_packet_edit"
			}
		});
		require(["carRedPacketEdit"], function(carRedPacketEdit) {
			carRedPacketEdit.init();
		});
	});
</script>