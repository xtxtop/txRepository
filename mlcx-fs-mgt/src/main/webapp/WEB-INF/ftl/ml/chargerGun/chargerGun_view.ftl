<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargingGunViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">查看充电枪</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>场站：</label>
						</td>
						<td>
							<label class="control-label val">
									${chargingGunInfo.chargingStationName} 
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>充电桩：</label>
						</td>
						<td>
							<label class="control-label val">${chargingGunInfo.chargingPileName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>工作状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if chargingGunInfo.workStatus == 1>告警
							<#elseif chargingGunInfo.workStatus == 2>待机
							<#elseif chargingGunInfo.workStatus == 3>工作 
							<#elseif chargingGunInfo.workStatus == 4>离线
							<#elseif chargingGunInfo.workStatus == 5>完成
							<#elseif chargingGunInfo.workStatus == 6>正在操作充电桩
							<#elseif chargingGunInfo.workStatus == 7>预约中
							<#else>
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>充电枪编码：</label>
						</td>
						<td>
							<label class="control-label val">${chargingGunInfo.chargingGunCode}</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="8"><button type="button" onclick="closeTab();"	id="closeViewChargerGun" class="btn-new-type-edit">
						关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

