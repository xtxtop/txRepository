<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carInventoryEditFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车辆库存详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;城市：</label>
						<td>
							<label class="control-label val">${carInventory.cityName}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;车辆品牌：</label>
						</td>
						<td>
							<label class="control-label val">${carInventory.carBrandName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;车辆型号：</label>
						</td>
						<td>
							<label class="control-label val">${carInventory.carModelName}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;总库存数：</label>
						</td>
						<td>
							<label class="control-label val">${carInventory.inventoryTotal}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;已租借数量：</label>
						</td>
						<td>
							<label class="control-label val">${carInventory.leasedQuantity}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;已预定数量：</label>
						</td>
						<td>
						<label class="control-label val">${carInventory.reserveQuantity}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;实际可用库存：</label>
						</td>
						<td>
							<label class="control-label val">${carInventory.availableInventory}</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeViewCarInventory" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
$("#closeViewCarInventory").click(function(){
	closeTab("库存详情");
});
</script>