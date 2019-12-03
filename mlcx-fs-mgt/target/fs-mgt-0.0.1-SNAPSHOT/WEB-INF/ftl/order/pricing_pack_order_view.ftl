<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricePackOrderViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">套餐订单详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>交易号：</label>
						</td>
						<td>
							<label class="control-label val"> ${pricingPackOrder.packOrderNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>客户姓名：</label>
						</td>
						<td>
							<label class="control-label val"> ${pricingPackOrder.memberName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>手机号：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackOrder.mobilePhone}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>套餐编号：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackOrder.packageId}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>套餐名称：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackOrder.packageName}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>金额：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackOrder.packAmount}元</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>购买时间：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackOrder.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>应付金额：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackOrder.payableAmount}元</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>支付状态：</label>
						</td>
						<td>
							<label class="control-label val">
	                				<#if pricingPackOrder.payStatus==0>
									未支付
									<#elseif pricingPackOrder.payStatus==1>
	                				已支付
	                				<#elseif pricingPackOrder.payStatus==2>
	                				部分支付
	                				</#if>
	                			</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>支付方式：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if pricingPackOrder.paymentMethod==1>
									支付宝
								<#elseif pricingPackOrder.paymentMethod==2>
	                				微信
	                			<#elseif pricingPackOrder.paymentMethod==3>
	                				银行卡转账
	                			<#else>
	                				其他
	                			</#if>
                				</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>有效期截止时间：</label>
						</td>
						<td>
						<label class="control-label val">${pricingPackOrder.vailableTime2?string('yyyy-MM-dd HH:mm:ss')}</label>	
						</td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closePricingPackOrderView" class="btn-new-type-edit">
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
    require.config({paths: {"pricePackOrder":"res/js/order/price_pack_order_list"}});
		require(["pricePackOrder"], function (pricePackOrder){
			pricePackOrder.init();
		});
	   }); 
    </script>