<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<div class="row hzlist">
		<table class="tab-table table table-border table-responsive">
			<thead class="tab-thead">
				<tr>
					<th colspan="4">停车订单详情</th>
				</tr>
			</thead>
			<tbody class="tab-tbody">
				<tr>
					<td><label class=" control-parkOrder key">
						<span></span>订单编号：</label></td>
					<td><label class="control-parkOrder val">
						${parkOrder.parkOrderNo} </label></td>
					<td><label class=" control-parkOrder key">
                        <span></span>订单来源：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.orderSource==0>
                             ios
                        <#elseif parkOrder.orderSource==1>
                                                                     安卓
                        <#else>
                            --
                        </#if>
                        </label></td>
                 </tr>
                 <tr>       
                    <td><label class=" control-parkOrder key">
                        <span></span>会员名称：</label></td>
                    <td><label class="control-parkOrder val">
                        ${parkOrder.memberName} </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>手机号：</label></td>
                    <td><label class="control-parkOrder val">
                        ${parkOrder.mobilePhone} </label></td>
				</tr>
				<tr>
					<td><label class=" control-parkOrder key">
						<span></span>停车场名称：</label></td>
					<td><label class="control-parkOrder val">
						${parkOrder.parkingName} </label></td>
						 <td><label class=" control-parkOrder key">
                        <span></span>预约时间：</label></td>
                    <td><label class="control-parkOrder val">
                          <#if parkOrder.appointmentTime>
                             ${parkOrder.appointmentTime?string('yyyy-MM-dd HH:mm:ss')}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-parkOrder key">
                        <span></span>停车开始时间：</label></td>
                    <td><label class="control-parkOrder val">
                    <#if parkOrder.entryTime>
                        ${parkOrder.entryTime?string('yyyy-MM-dd HH:mm:ss')} 
                     <#else>
                     --
                     </#if>
                        </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>停车结束时间：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.departureTime>
                             ${parkOrder.departureTime?string('yyyy-MM-dd HH:mm:ss')}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                    <td><label class=" control-parkOrder key">
                        <span></span>订单总时长(分钟)：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.totalTime>
                            ${parkOrder.totalTime}
                        <#else>
                            --
                        </#if>
                   </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>免费时长(分钟)：</label></td>
                    <td><label class="control-parkOrder val">
                         <#if parkOrder.appointmentFreeTotal>
                            ${parkOrder.appointmentFreeTotal}
                        <#else>
                           --
                        </#if>
                        </label></td> 
                </tr>
                <tr>
                    <td><label class=" control-parkOrder key">
                        <span></span>订单总金额(元)：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.totalMoney>
                            ${parkOrder.totalMoney}
                        <#else>
                            --
                        </#if>
                        </label></td>
                         <td><label class=" control-parkOrder key">
                        <span></span>停车金额(元)：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.parkMoney>
                            ${parkOrder.parkMoney}
                        <#else>
                            --
                        </#if>
                        </label></td>
                  </tr>
                <tr>
                    <td><label class=" control-parkOrder key">
                        <span></span>余额抵扣金额(元)：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.discountAmount>
                            ${parkOrder.discountAmount}
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>未付金额(元)：</label></td>
                    <td><label class="control-parkOrder val">
                         <#if parkOrder.nopayAmount>
                            ${parkOrder.nopayAmount}
                        <#else>
                            --
                        </#if>
                         </label></td>
                  </tr>
                <tr>
                    <td><label class=" control-parkOrder key">
                        <span></span>订单状态：</label></td>
                    <td><label class="control-parkOrder val">
                            <#if parkOrder.orderStatus==0>
                                                                    进行中
                        <#elseif parkOrder.orderStatus==1>
                                                                    待支付
                        <#elseif parkOrder.orderStatus==2>
                                                                待评价
                        <#elseif parkOrder.orderStatus==3>
                                                                  已完成
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>支付状态：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.payStatus==0>
                                                                    未支付
                        <#elseif parkOrder.payStatus==1>
                                                                    已支付
                        <#else>
                            --
                        </#if>
                        </label></td>
                  </tr>
                <tr>
                    <td><label class=" control-parkOrder key">
                        <span></span>支付方式：</label></td>
                    <td><label class="control-parkOrder val">
                         <#if parkOrder.payType==0>
                                                                    支付宝
                        <#elseif parkOrder.payType==1>
                                                                    微信
                        <#else>
                            --
                        </#if>
                         </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>支付时间：</label></td>
                    <td><label class="control-parkOrder val">
                         <#if parkOrder.paymentTime>
                             ${parkOrder.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
                        <#else>
                            --
                        </#if>
                         </label></td>
                   </tr>
                <tr>
                    <td><label class=" control-parkOrder key">
                        <span></span>支付流水号：</label></td>
                    <td><label class="control-parkOrder val">
                         <#if parkOrder.paymentFlowNo>
                             ${parkOrder.paymentFlowNo}
                        <#else>
                            --
                        </#if>
                        </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>结束类型：</label></td>
                    <td><label class="control-parkOrder val">
                        <#if parkOrder.finishType==0>
                                                                    正常结束
                        <#elseif parkOrder.finishType==1>
                                                                     后台结束
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
                <tr>
                     <td><label class=" control-parkOrder key">
                        <span></span>结束理由：</label></td>
                    <td><label class="control-parkOrder val">
                         <#if parkOrder.finishReason>
                             ${parkOrder.finishReason}
                        <#else>
                            --
                        </#if>
                         </label></td>
                    <td><label class=" control-parkOrder key">
                        <span></span>订单备注：</label></td>
                    <td><label class="control-parkOrder val">
                         <#if parkOrder.orderMemo>
                             ${parkOrder.orderMemo}
                        <#else>
                            --
                        </#if>
                        </label></td>
                </tr>
			</tbody>
			<tfoot class="tab-tfoot">
				<tr style="text-align: center;">
					<td colspan="4"><button type="button"
							id="closeparkOrderView" class="btn-new-type-edit">关闭</button></td>

				</tr>
			</tfoot>
		</table>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("#closeparkOrderView").click(function(){
            closeTab("充电订单详情");
        });
	});
</script>