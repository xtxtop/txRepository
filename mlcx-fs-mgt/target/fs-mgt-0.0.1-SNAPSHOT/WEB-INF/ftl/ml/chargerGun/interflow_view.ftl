<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargingGunViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">交流监测数据</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>监测编号：</label>
						</td>
						<td>
							<label class="control-label val">
									${interflow.interflowNo} 
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>工作状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if interflow.workState == 1>告警
							<#elseif interflow.workState == 2>待机
							<#elseif interflow.workState == 3>工作 
							<#elseif interflow.workState == 4>离线
							<#elseif interflow.workState == 5>完成
							<#elseif interflow.workState == 6>正在操作充电桩
							<#elseif interflow.workState == 7>预约中
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
							<label class=" control-label key"><span></span>连接确认开关状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if interflow.switchState==0>
							关
							<#elseif interflow.switchState==1>
							 开
							 </#if>
							 </label>
						</td>
                        <td>
                            <label class=" control-label key"><span></span>过压告警：</label>
                        </td>
                        <td>
                            <label class="control-label val">  
                             <#if interflow.overvoltage==0>
                                                                        正常
                            <#elseif interflow.overvoltage==1>
                                                                        告警
                             </#if></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>欠压告警：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                            <#if interflow.undervoltage==0>
                                                                        正常
                            <#elseif interflow.undervoltage==1>
                                                                        告警
                             </#if>
                            </label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>过负荷告警：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                            <#if interflow.overload==0>
                                                                        正常
                            <#elseif interflow.overload==1>
                                                                        告警
                             </#if>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>过流告警：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                            <#if interflow.overcurrent==0>
                                                                       正常
                            <#elseif interflow.overcurrent==1>
                                                                      告警
                             </#if>
                            </label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>急停告警：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                            <#if interflow.abruptStop==0>
                                                                       正常
                            <#elseif interflow.abruptStop==1>
                                                                        告警
                             </#if>
                            </label>
                        </td>
                        </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>防雷告警：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                            <#if interflow.lightningProtection==0>
                                                                       正常
                            <#elseif interflow.lightningProtection==1>
                                                                        告警
                             </#if>
                            </label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>电表异常：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                            <#if interflow.unusual==0>
                                                                       正常
                            <#elseif interflow.unusual==1>
                                                                        异常
                             </#if>
                            </label>
                        </td>
                       </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>充电输出电压(V)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${interflow.outputVoltage}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>充电输出电流(A)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${interflow.outputCurrent}</label>
                        </td>
                       </tr>
                    <tr>
                         <td>
                            <label class=" control-label key"><span></span>预留：</label>
                        </td>
                        <td>
                            <label class="control-label val">${interflow.reserved}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>输出继电器状态：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                            <#if interflow.relayState==0>
                                                                       关
                            <#elseif interflow.relayState==1>
                                                                        开
                             </#if>
                            </label>
                        </td>
                         </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>有功总电度(度)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${interflow.electricity}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>累计充电时间(分钟)：</label>
                        </td>
                        <td>
                            <label class="control-label val">${interflow.time}</label>
                        </td>
                        </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span></span>保存时间：</label>
                        </td>
                        <td>
                            <label class="control-label val">${interflow.recordTime?string('yyyy-MM-dd HH:mm:SS')}</label>
                        </td>
                        <td>
                            <label class=" control-label key"><span></span>创建日期：</label>
                        </td>
                        <td>
                            <label class="control-label val">${interflow.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
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
		$(function() {
	        $("#closeViewChargerGun").click(function(){
	            closeTab("交流检测数据");
	        });
	    });
	});
</script>