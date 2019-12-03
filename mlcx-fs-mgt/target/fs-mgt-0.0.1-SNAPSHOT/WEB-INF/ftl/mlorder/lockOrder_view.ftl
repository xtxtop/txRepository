<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<div class="row hzlist">
		<table class="tab-table table table-border table-responsive">
			<thead class="tab-thead">
				<tr>
					<th colspan="4">地锁订单详情</th>
				</tr>
			</thead>
			<tbody class="tab-tbody">
				<tr>
					<td><label class=" control-lockOrder key">
						<span></span>订单编号：</label></td>
					<td><label class="control-lockOrder val">
						${lockOrder.orderNo} </label></td>
					<td><label class=" control-lockOrder key">
                        <span></span>订单来源：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.orderSource==0>
                             ios
                        <#elseif lockOrder.orderSource==1>
                                                                     安卓
                        <#else>
                            --
                        </#if>
                        </label></td>
                 </tr>
                 <tr>       
                    <td><label class=" control-lockOrder key">
                        <span></span>会员名称：</label></td>
                    <td><label class="control-lockOrder val">
                        ${lockOrder.memberName} </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>手机号：</label></td>
                    <td><label class="control-lockOrder val">
                        ${lockOrder.mobilePhone} </label></td>
				</tr>
				<tr>
					<td><label class=" control-lockOrder key">
						<span></span>充电站名称：</label></td>
					<td><label class="control-lockOrder val">
						${lockOrder.stationName} </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>地锁名称：</label></td>
                    <td><label class="control-lockOrder val">
                        ${lockOrder.parkingLockName} </label></td>
                </tr>
				<tr>
					<td><label class=" control-lockOrder key">
						<span></span>地锁计费类型：</label></td>
					<td><label class="control-lockOrder val">
						<#if lockOrder.lockType==0>
                                                            充电
                        <#elseif lockOrder.lockType==1>
                                                            停车
                        <#else>
                            --
                        </#if>
						</label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>充电订单编号：</label></td>
                        <td><label class="control-lockOrder val">
                        <#if lockOrder.chargeOrderNo>
                             ${lockOrder.chargeOrderNo}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>开始时间：</label></td>
                    <td><label class="control-lockOrder val">
                        ${lockOrder.orderStartTime?string('yyyy-MM-dd HH:mm:ss')} </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>结束时间：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.orderEndTime>
                             ${lockOrder.orderEndTime?string('yyyy-MM-dd HH:mm:ss')}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>订单总时长(分钟)：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.orderTimeLength>
                            ${lockOrder.orderTimeLength}
                        <#else>
                            --
                        </#if>
                   </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>免费时长(分钟)：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.orderFreeTime>
                            ${lockOrder.orderFreeTime}
                        <#else>
                           --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>超时时长(分钟)：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.orderOverTime>
                            ${lockOrder.orderOverTime}
                        <#else>
                            --
                        </#if>
                         </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>订单总金额(元)：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.orderAmount>
                            ${lockOrder.orderAmount}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>超时总金额(元)：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.overtimeAmount>
                            ${lockOrder.overtimeAmount}
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>余额抵扣金额(元)：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.discountAmount>
                            ${lockOrder.discountAmount}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>未付金额(元)：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.nopayAmount>
                            ${lockOrder.nopayAmount}
                        <#else>
                            --
                        </#if>
                         </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>订单状态：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.orderStatus==0>
                                                                    进行中
                        <#elseif lockOrder.orderStatus==1>
                                                                    待支付
                        <#elseif lockOrder.orderStatus==2>
                                                                待评价
                        <#elseif lockOrder.orderStatus==3>
                                                                  已完成
                        <#elseif lockOrder.orderStatus==4>
                                                                  待结算
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>支付状态：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.payStatus==0>
                                                                    未支付
                        <#elseif lockOrder.payStatus==1>
                                                                    已支付
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>支付方式：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.payType==0>
                                                                    支付宝
                        <#elseif lockOrder.payType==1>
                                                                    微信
                        <#else>
                            --
                        </#if>
                         </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>支付时间：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.paymentTime>
                             ${lockOrder.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
                        <#else>
                            --
                        </#if>
                         </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>支付流水号：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.paymentFlowNo>
                             ${lockOrder.paymentFlowNo}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>结束类型：</label></td>
                    <td><label class="control-lockOrder val">
                        <#if lockOrder.finishType==0>
                                                                    正常结束
                        <#elseif lockOrder.finishType==1>
                                                                     后台结束
                        <#else>
                            --
                        </#if>
                        </label></td>
                     <td><label class=" control-lockOrder key">
                        <span></span>结束理由：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.finishReason>
                             ${lockOrder.finishReason}
                        <#else>
                            --
                        </#if>
                         </label></td>
                </tr>
                <tr>
                    <td><label class=" control-lockOrder key">
                        <span></span>订单备注：</label></td>
                    <td><label class="control-lockOrder val">
                         <#if lockOrder.orderMemo>
                             ${lockOrder.orderMemo}
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-lockOrder key">
                        <span></span>创建时间：</label></td>
                    <td><label class="control-lockOrder val">
                        ${lockOrder.createTime?string('yyyy-MM-dd HH:mm:ss')} </label></td>
                </tr>
			</tbody>
			<tfoot class="tab-tfoot">
				<tr style="text-align: center;">
					<td colspan="4"><button type="button"
							id="closelockOrderView" class="btn-new-type-edit">关闭</button></td>

				</tr>
			</tfoot>
		</table>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("#closelockOrderView").click(function(){
            closeTab("充电订单详情");
        });
	});
</script>