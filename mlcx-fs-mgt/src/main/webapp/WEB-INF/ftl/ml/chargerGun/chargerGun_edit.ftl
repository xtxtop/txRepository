<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargingGunEditForm">
		<input type="hidden" name="chargingGunNo" value="${chargingGunInfo.chargingGunNo }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑充电枪</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>场站：</label>
						</td>
						<td>
							<input type="hidden" name="stationNo" value="">
							<select name="stationNo" id="stationNoIdEdit" class="form-control val">
							<option value="">请选择</option>
						 		<#list stationList as pa>
						 		<#if pa.isAvailable==1>
									<option value="${pa.stationNo}"
									<#if chargingStation.stationNo == pa.stationNo>selected</#if>
									>${pa.stationName}</option> 
									</#if>
								</#list>
							</select>
							<span id="stationNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电桩：</label>
						</td>
						<td>
							<select name="chargingPileNo" id="chargingPileIdEdit" class="form-control val">
								<option value="">请选择</option>
								<#list pileList as br>
								    <#if br.status==1>
									<option value="${br.chargingPileNo}"
									<#if chargingPile.chargingPileNo == br.chargingPileNo>selected</#if>
									>${br.chargingPileName}</option>
									</#if>
								</#list>
						</select>
                        <span id="chargingPileEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>工作状态：</label>
						</td>
						<td>
							<select name="workStatus" class="form-control val">
								<option value="">请选择</option>
								<option value="1" <#if chargingGunInfo.workStatus == 1>selected</#if>>告警</option>
								<option value="2" <#if chargingGunInfo.workStatus == 2>selected</#if>>待机</option>
								<option value="3" <#if chargingGunInfo.workStatus == 3>selected</#if>>工作</option>
								<option value="4" <#if chargingGunInfo.workStatus == 4>selected</#if>>离线</option>
								<option value="5" <#if chargingGunInfo.workStatus == 5>selected</#if>>完成</option>
								<option value="6" <#if chargingGunInfo.workStatus == 6>selected</#if>>正在操作充电桩</option>
								<option value="7" <#if chargingGunInfo.workStatus == 7>selected</#if>>预约中</option>
						</select>
						<span id="workStatusEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电枪编码：</label>
						</td>
						<td>
							<input type="text" class="form-control val" maxlength="2" name="chargingGunCode" value="${chargingGunInfo.chargingGunCode}"/>
							<span id="chargingGunCodeEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveEditChargerGun" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditChargerGun" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"chargerGunEdit" : "res/js/ml/chargerGun/chargingGun_edit"
			}
		});
		require([ "chargerGunEdit" ], function(chargerGunEdit) {
			chargerGunEdit.init();
		});
	});
</script>