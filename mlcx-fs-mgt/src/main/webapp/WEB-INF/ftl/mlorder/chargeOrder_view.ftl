<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<div class="row hzlist">
		<table class="tab-table table table-border table-responsive">
			<thead class="tab-thead">
				<tr>
					<th colspan="4">充电订单详情</th>
				</tr>
			</thead>
			<tbody class="tab-tbody">
				<tr>
					<td><label class=" control-chargeOrder key">
						<span></span>订单编号：</label></td>
					<td><label class="control-chargeOrder val">
						${chargeOrder.orderNo} </label></td>
					<td><label class=" control-chargeOrder key">
                        <span></span>订单来源：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.orderSource==0>
                             ios
                        <#elseif chargeOrder.orderSource==1>
                                                                     安卓
                        <#else>
                            --
                        </#if>
                        </label></td>
                 </tr>
                 <tr>       
                    <td><label class=" control-chargeOrder key">
                        <span></span>会员名称：</label></td>
                    <td><label class="control-chargeOrder val">
                        ${chargeOrder.memberName} </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>手机号：</label></td>
                    <td><label class="control-chargeOrder val">
                        ${chargeOrder.mobilePhone} </label></td>
				</tr>
				<tr>
					<td><label class=" control-chargeOrder key">
						<span></span>充电站名称：</label></td>
					<td><label class="control-chargeOrder val">
						${chargeOrder.stationName} </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>充电桩名称：</label></td>
                    <td><label class="control-chargeOrder val">
                        ${chargeOrder.chargingPileName} </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>开始时间：</label></td>
                    <td><label class="control-chargeOrder val">
                        ${chargeOrder.orderStartTime?string('yyyy-MM-dd HH:mm:ss')} </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>结束时间：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.orderEndTime>
                             ${chargeOrder.orderEndTime?string('yyyy-MM-dd HH:mm:ss')}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>订单总时长(分钟)：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.orderTimeLength>
                            ${chargeOrder.orderTimeLength}
                        <#else>
                            --
                        </#if>
                   </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>充电度数：</label></td>
                    <td><label class="control-chargeOrder val">
                         <#if chargeOrder.chargeDegree>
                            ${chargeOrder.chargeDegree}度
                        <#else>
                            0度
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>充电金额(元)：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.chargeAmount>
                            ${chargeOrder.chargeAmount}
                        <#else>
                            0
                        </#if>
                         </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>服务费用金额(元)：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.serviceAmount>
                            ${chargeOrder.serviceAmount}
                        <#else>
                            0
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>订单总金额(元)：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.orderAmount>
                            ${chargeOrder.orderAmount}
                        <#else>
                            0
                        </#if>
                        </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>余额抵扣金额(元)：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.discountAmount>
                            ${chargeOrder.discountAmount}
                        <#else>
                            0
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>未付金额(元)：</label></td>
                    <td><label class="control-chargeOrder val">
                         <#if chargeOrder.nopayAmount>
                            ${chargeOrder.nopayAmount}
                        <#else>
                            0
                        </#if>
                         </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>订单状态：</label></td>
                    <td><label class="control-chargeOrder val">
                            <#if chargeOrder.orderStatus==0>
                                                                    进行中
                        <#elseif chargeOrder.orderStatus==1>
                                                                    待支付
                        <#elseif chargeOrder.orderStatus==2>
                                                                待评价
                        <#elseif chargeOrder.orderStatus==3>
                                                                  已完成
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>支付状态：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.payStatus==0>
                                                                    未支付
                        <#elseif chargeOrder.payStatus==1>
                                                                    已支付
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>支付方式：</label></td>
                    <td><label class="control-chargeOrder val">
                         <#if chargeOrder.payType==0>
                                                                    支付宝
                        <#elseif chargeOrder.payType==1>
                                                                    微信
                        <#else>
                            --
                        </#if>
                         </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>支付时间：</label></td>
                    <td><label class="control-chargeOrder val">
                         <#if chargeOrder.paymentTime>
                             ${chargeOrder.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
                        <#else>
                            --
                        </#if>
                         </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>支付流水号：</label></td>
                    <td><label class="control-chargeOrder val">
                         <#if chargeOrder.paymentFlowNo>
                             ${chargeOrder.paymentFlowNo}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>结束类型：</label></td>
                    <td><label class="control-chargeOrder val">
                        <#if chargeOrder.finishType==0>
                                                                    正常结束
                        <#elseif chargeOrder.finishType==1>
                                                                     后台结束
                        <#else>
                            --
                        </#if>
                        </label></td>
                     <td><label class=" control-chargeOrder key">
                        <span></span>结束理由：</label></td>
                    <td><label class="control-chargeOrder val">
                         <#if chargeOrder.finishReason>
                             ${chargeOrder.finishReason}
                        <#else>
                            --
                        </#if>
                         </label></td>
                </tr>
                <tr>
                    <td><label class=" control-chargeOrder key">
                        <span></span>订单备注：</label></td>
                    <td><label class="control-chargeOrder val">
                         <#if chargeOrder.orderMemo>
                             ${chargeOrder.orderMemo}
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-chargeOrder key">
                        <span></span>创建时间：</label></td>
                    <td><label class="control-chargeOrder val">
                        ${chargeOrder.createTime?string('yyyy-MM-dd HH:mm:ss')} </label></td>
                </tr>
			</tbody>
			<tfoot class="tab-tfoot">
				<tr style="text-align: center;">
					<td colspan="4"><button type="button"
							id="closechargeOrderView" class="btn-new-type-edit">关闭</button></td>

				</tr>
			</tfoot>
		</table>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("#closechargeOrderView").click(function(){
            closeTab("充电订单详情");
        });
	});
</script>