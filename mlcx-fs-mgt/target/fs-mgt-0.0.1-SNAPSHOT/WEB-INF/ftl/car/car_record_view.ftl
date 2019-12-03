<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carRecordViewForm" class="form-horizontal">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">用车记录详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;车牌号：</label>
						</td>
						<td>
							<label class="control-label val">${carRecord.carPlateNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;型号：</label>
						</td>
						<td>
							<label class="control-label val">${carRecord.carModel}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<label class="control-label val">${carRecord.city}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;用车类型：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if carRecord.useCarType==1>
                					订单用车
                				<#elseif carRecord.useCarType==2>
                					调度用车
                				</#if>
                				</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;单据号：</label>
						</td>
						<td>
							<label class="control-label val">${carRecord.documentNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;用车人：</label>
						</td>
						<td>
							<label class="control-label val">${carRecord.driverName}</label>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>&nbsp;&nbsp;起点：</label></td>
						<td><label class="control-label val">${carRecord.startParkName}</label></td>
						<td><label class=" control-label key"><span></span>&nbsp;&nbsp;终点：</label></td>
						<td><label class="control-label val">${carRecord.terminalParkName}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>&nbsp;&nbsp;用车时间：</label></td>
						<td><label class="control-label val">${carRecord.startTime?string('yyyy-MM-dd HH:mm:ss')}</label></td>
						<td><label class=" control-label key"><span></span>&nbsp;&nbsp;还车时间：</label></td>
						<td><label class="control-label val">${carRecord.finishTime?string('yyyy-MM-dd HH:mm:ss')}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>&nbsp;&nbsp;开始电量：</label></td>
						<td><label class="control-label val">${carRecord.startPower}%</label></td>
						<td><label class=" control-label key"><span></span>&nbsp;&nbsp;结束电量：</label></td>
						<td><label class="control-label val">${carRecord.finishPower}%</label></td>
					</tr>
					<tr>
						<td><label class=" control-label"><span></span>&nbsp;&nbsp;创建时间：</label></td>
						<td><label class="control-label">${carRecord.createTime?string('yyyy-MM-dd HH:mm:ss')}</label></td>
						<td><label class=" control-label"><span></span>&nbsp;&nbsp;更新时间：</label></td>
						<td><label class="control-label">${carRecord.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
				<td colspan="4"><button type="button" id="closeCarRecordVeiw" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carRecord":"res/js/car/car_record_list"}});
		require(["carRecord"], function (carRecord){
			carRecord.init();
		});  
	});
</script>