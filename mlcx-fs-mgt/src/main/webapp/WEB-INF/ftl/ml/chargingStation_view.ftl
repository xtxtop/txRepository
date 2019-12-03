<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="ChargingStationVoAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">充电站详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span></span>充电站编号：</label>
						</td>
						<td><label class="control-label val">
								${ChargingStationVo.stationNo} </label></td>
						<td><label class=" control-label key"><span></span>充电站名称：</label>
						</td>
						<td><label class="control-label val">
								${ChargingStationVo.stationName} </label></td>
					</tr>
					<tr>
					   <td><label class=" control-label key"><span></span>运营城市：</label>
                        </td>
                        <td><label class="control-label val">
                                ${ChargingStationVo.operatingCityName} </label></td>
						<td><label class=" control-label key"><span></span>地址：</label>
						</td>
						<td><label class="control-label val">
						${ChargingStationVo.provinceName}${ChargingStationVo.cityName}${ChargingStationVo.districtName}${ChargingStationVo.addrStreet}
						</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>坐标：</label>
						</td>
						<td><label class="control-label val">
								经度：${ChargingStationVo.longitude}</br> 纬度：${ChargingStationVo.latitude} </label>
						</td>
						<td><label class=" control-label key">类型：</label></td>
						<td><label class="control-label val">
								<#if ChargingStationVo.stationType==1>
								    精品站
								<#elseif ChargingStationVo.stationType==2>
								    超级站
								 <#else>
								   普通站
								</#if> 
								
								</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key">充电桩数：</label></td>
						<td><label class="control-label val">
						         <#if ChargingStationVo.chargingPileNumber> 
								    ${ChargingStationVo.chargingPileNumber} 
								 <#else>
								    --
								 </#if>
								</label></td>
                        <td><label class=" control-label key">电费：</label></td>
                        <td><label class="control-label val">
                                ${ChargingStationVo.electricPrice}元/度 </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>状态：</label>
						</td>
						<td><label class="control-label val"> <#if
								ChargingStationVo.isAvailable==1> 启用 <#else> 停用 </#if> </label></td>
					<td><label class=" control-label key">标签：</label></td>
                        <td>
                        <#list clabel as cl>
                           <label class=""> ${cl.labelName}</label> 
                        </#list>
                            <div>
                            </div></td>
					</tr>
					<tr>
						<td><label class=" control-label key">服务：</label></td>
                        <td><#list cmatching as cm>
                           <label class=""> ${cm.matchingName}</label> 
                        </#list></td>
						<td><label class=" control-label key">充电站图片：</label></td>
                        <td><label class="control-label val">
                               <img src="${imagePath!''}/${ChargingStationVo.stationUrl}" width="280" height="180"/>
                               </label></td>
					</tr>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>营业时间：</label>
						</td>
						<td><label class="control-label val">
								${ChargingStationVo.businessHours} </label></td>
						<td><label class=" control-label key"><span></span>节假日营业：</label>
						</td>
						<td><label class="control-label val"> <#if
								ChargingStationVo.isBusinessFestival==1> 是 </#if> <#if
								ChargingStationVo.isBusinessFestival==0> 否 </#if> </label></td> 
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closechargingStationView" onclick="closeTab();" class="btn-new-type-edit">
								关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
