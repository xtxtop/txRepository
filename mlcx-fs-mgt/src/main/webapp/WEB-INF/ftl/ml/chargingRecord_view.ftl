<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="ChargingRecordAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">充电记录详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span></span>充电记录编号：</label>
						</td>
						<td><label class="control-label val">
								${ChargingRecord.recordNo} </label></td>
						<td><label class=" control-label key"><span></span>充电桩编号：</label>
						</td>
						<td><label class="control-label val">
								${ChargingRecord.chargingPileNo} </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>充电枪编号：</label>
						</td>
						<td><label class="control-label val">
								${ChargingRecord.chargingGunNo}
						</label></td>
						<td><label class=" control-label key"><span></span>交易流水号：</label>
						</td>
						<td><label class="control-label val">
								${ChargingRecord.serialNumber}</label>
						</td>
					</tr>

                    <tr>
                        <td><label class=" control-label key"><span></span>应用卡号：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.cardNumber}
                        </label></td>
                        <td><label class=" control-label key"><span></span>尖电量：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.opintStart}</label>
                        </td>
                    </tr>

                    <tr>
                        <td><label class=" control-label key"><span></span>尖金额：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.opintFinish}
                        </label></td>
                        <td><label class=" control-label key"><span></span>峰电量：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.peakStart}</label>
                        </td>
                    </tr>

                    <tr>
                        <td><label class=" control-label key"><span></span>平电量：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.flatStart}
                        </label></td>
                        <td><label class=" control-label key"><span></span>平金额：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.flatFinish}</label>
                        </td>
                    </tr>


                    <tr>
                        <td><label class=" control-label key"><span></span>谷电量：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.alleyStart}
                        </label></td>
                        <td><label class=" control-label key"><span></span>谷金额：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.valleyFinish}</label>
                        </td>
                    </tr>

                    <tr>
                        <td><label class=" control-label key"><span></span>总起电量：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.totalStart}
                        </label></td>
                        <td><label class=" control-label key"><span></span>总止电量：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.totalFinish}</label>
                        </td>
                    </tr>

                    <tr>
                        <td><label class=" control-label key"><span></span>总金额：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.totalMoney}
                        </label></td>
                        <td><label class=" control-label key"><span></span>扣款前钱包余额：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.moneyBeforePay}</label>
                        </td>
                    </tr>


                    <tr>
                        <td><label class=" control-label key"><span></span>扣款后钱包余额：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.moneyAfterPay}
                        </label></td>
                        <td><label class=" control-label key"><span></span>计费模型版本号：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.schemeNo}</label>
                        </td>
                    </tr>

                    <tr>
                        <td><label class=" control-label key"><span></span>充电结束原因：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.overReason}
                        </label></td>
                        <td><label class=" control-label key"><span></span>车VIN号：</label>
                        </td>
                        <td><label class="control-label val">
						${ChargingRecord.vin}</label>
                        </td>
                    </tr>
					<tr>
						<td><label class=" control-label key"><span></span>充电方式：</label>
						</td>
						<td><label class="control-label val">
						<#if
						ChargingRecord.rechargeMode==1> 电子钱包 </#if>
						<#if
						ChargingRecord.rechargeMode==2> 有卡在线 </#if>
						<#if
						ChargingRecord.rechargeMode==3> 二维码 </#if>
						<#if
						ChargingRecord.rechargeMode==4> 门禁卡 </#if>
						<#if
						ChargingRecord.rechargeMode==5> 连接电缆线充电 </#if>
						</label>
						</td>

                        <td><label class=" control-label key"><span></span>扣款标示：</label>
                        </td>
                        <td><label class="control-label val"> <#if
						ChargingRecord.payFlag==0> M1卡失败 </#if> <#if
						ChargingRecord.payFlag==2> M1卡成功 </#if> </label></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closeChargingRecordView" onclick="closeTab();" class="btn-new-type-edit">
								关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
