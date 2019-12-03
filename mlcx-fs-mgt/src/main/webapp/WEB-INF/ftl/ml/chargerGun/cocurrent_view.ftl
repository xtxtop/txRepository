<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargingGunViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">直流监测数据</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>监测编号：</label>
						</td>
						<td>
							<label class="control-label val">
									${cocurrent.cocurrentNo} 
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>工作状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if cocurrent.batteryChargerStatus == 1>告警
							<#elseif cocurrent.batteryChargerStatus == 2>待机
							<#elseif cocurrent.batteryChargerStatus == 3>工作 
							<#elseif cocurrent.batteryChargerStatus == 4>离线
							<#elseif cocurrent.batteryChargerStatus == 5>完成
							<#elseif cocurrent.batteryChargerStatus == 6>正在操作充电桩
							<#elseif cocurrent.batteryChargerStatus == 7>预约中
							<#else>
							</#if>
							</label>
						</td>
					</tr>
                    <tr>
						<td>
                            <label class=" control-label key"><span></span>充电桩名称：</label>
                        </td>
                        <td>
                            <label class="control-label val">${chargingPileName}</label>
                        </td>
						<td>
							<label class=" control-label key"><span></span>充电枪编码：</label>
						</td>
						<td>
							<label class="control-label val">${chargingGunInfoCode}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>是否连接车辆：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if cocurrent.connectCar==0>
							未连接
							<#elseif cocurrent.connectCar==1>
							 连接
							 </#if>
							 </label>
						</td>
                        <td>
                            <label class=" control-label key"><span></span>累计充电时间：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.chargeTime}</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>有功总电度(度)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.totalActivePower}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>充电输出电压(V)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.outputVoltage}</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>充电输出电流(A)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.outputCurrent}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>SOC：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.soc}</label>
                        </td>
                        </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>电池组最低温(℃)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.batteryTemperatureMin}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>电池组最高温度(℃)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.batteryTemperatureMax}</label>
                        </td>
                       </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>单体电池最高电压(V)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.voltageMax}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>单体电池最低电压(V)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.voltageMin}</label>
                        </td>
                        </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>全局故障代码：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.globalError}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>模块故障代码：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.modularError}</label>
                        </td>
                         </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>温度监控(DC+)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.dcTemperatureP}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>温度监控(DC-)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.dcTemperatureN}</label>
                        </td>
                    </tr>
                    <tr>
                         <td>
                            <label class=" control-label key"><span></span>记录时间：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.cordTime?string('yyyy-MM-dd HH:mm:ss')}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>汽车VIN号：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cocurrent.vin}</label>
                        </td>
                    </tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="8"><button type="button"	id="closeViewChargerGun" class="btn-new-type-edit">
						关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		$("#closeViewChargerGun").click(function(){
			closeTab("直流检测数据");
		});
	});
</script>