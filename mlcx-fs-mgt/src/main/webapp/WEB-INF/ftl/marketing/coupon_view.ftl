<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="couponViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">优惠券详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>优惠券编号：</label>
						</td>
						<td>
							 <label class="control-label val">${coupon.couponNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>方案标题：</label>
						</td>
						<td>
							<label class="control-label val">${coupon.title}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>会员编号：</label>
						</td>
						<td>
							 <label class="control-label val">${coupon.memberNo}</label>
						</td>

						<td>
							<label class=" control-label key"><span></span>会员名称：</label>
						</td>
						<td>
							 <label class="control-label val">${coupon.memberName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>优惠类型：</label>
						</td>
						<td>
							<label class="control-label val">
							   <#if coupon.couponType==1>
							 	  优惠券
							   </#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>优惠方式：</label>
						</td>
						<td>
							<label class="control-label val">
							   <#if coupon.couponMethod==1>
							 	  折扣
							   </#if>
							   <#if coupon.couponMethod==2>
							 	  直减
							   </#if>
							</label>
						</td>
					</tr>
					<tr>
					<td>
						<label class=" control-label key"><span></span>
							<#if coupon.couponMethod==1>
							 	折扣率
							</#if>
							<#if coupon.couponMethod==2>
							 	直减金额
							</#if>
							</label>
					<td>
						 <label class="control-label val">
							   <#if coupon.couponMethod==1>
							   		${coupon.discount}
								</#if>
								<#if coupon.couponMethod==2>
								 	 ${coupon.discountAmount}
								</#if>
							   </label>
					</td>
					<#if coupon.vailableTime1??>
				  <td>
				  	<label class=" control-label key"><span></span>有效日期：</label>
				  </td>
				  <td>
				  	<label class="control-label val">${coupon.vailableTime1?string('yyyy-MM-dd ')}  至   ${coupon.vailableTime2?string('yyyy-MM-dd ')}</label>
				  </td>
				  </#if>
					</tr>
					<tr>
							<#if advert.linkUrl!=null&&advert.linkUrl!=''&& advert.jumpType==2 || advert.jumpType==3>
					<td>
						<label class="col-sm-4 control-label key"><span></span>跳转链接：</label>
					</td>
					<td colspan="3">
						<label class="control-label val">${advert.linkUrl}</label>
					</td>
					<#else>
						</#if>
					</tr>
					<tr>
						<td>
							<label class="control-label key"><span></span>发放时间：</label>
						</td>
						<td>
							<label class="control-label val">
									  <#if coupon.issueMethod==1>
									 	  系统注册
									   </#if>
									   <#if coupon.issueMethod==2>
									 	  活动奖励
									   </#if>
									    <#if coupon.issueMethod==3>
									 	 手动发放
									   </#if>
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>发放人：</label>
						</td>
						<td>
							<label class="control-label val">${coupon.issueorName}</label>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>发放渠道：</label>
						</td>
						<td>
							<label class="control-label val">
									 <#if coupon.issueChannel==1>
									 	  官网
									</#if>
                               	</label>
						</td>
						<td>
						<label class=" control-label key"><span></span>发放方式：</label>
						</td>
						<td>
							<label class="control-label val">${coupon.issueTime?string('yyyy-MM-dd ')}</label>
						</td>
						
					</tr>
					<tr>
						<td>
							  <label class=" control-label key"><span></span>启用状态：</label>
						</td>
						<td>
							<label class="control-label val"><#if coupon.isAvailable==1>启用<#elseif coupon.isAvailable==0>停用</#if></label>
						</td>
						<td>
							<label class=" control-label key"><span></span><#if coupon.isAvailable==1>启用日期<#elseif coupon.isAvailable==0>停用时间</#if></label>
						</td>
						<td>
							 <label class="control-label val">${coupon.availableUpdateTime?string('yyyy-MM-dd ')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>使用状态：</label>
						</td>
						<td>
							<label class="control-label val">
									  <#if coupon.usedStatus==0>
									 	  未使用
									   </#if>
									   <#if coupon.usedStatus==1>
									 	  已使用
									   </#if>
								</label>
						</td>
						 <#if coupon.usedStatus==1>
						<td>
							<label class=" control-label key"><span></span>使用时间：</label>
						</td>
						<td>
							<label class="control-label val">${coupon.usedTime?string('yyyy-MM-dd')}</label>
						</td>
						
					</tr>
					
					<tr>
						<td>
							<label class=" control-label key"><span></span>订单类型：</label>
						</td>
						<td>
							 <label class="control-label val">
							   <#if coupon.bizObjType==2>
							 	  分时订单
							   </#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>订单编号：</label>
						</td>
						<td>
							<label class="control-label val">${coupon.bizObjNo}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>实际抵扣金额</label>
						</td>
						<td>
							<label class="control-label val">${coupon.deductibleAmount}</label>
						</td>
					</#if>
						<#if coupon.availableDays??>
						<td>
							<label class=" control-label key"><span></span>有效天数：</label>
						</td>
						<td>
							<label class="control-label val">${coupon.availableDays}</label>
						</td>
						</#if>
						<td colspan="2"></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeCouponView" class="btn-new-type-edit">
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
	  require.config({paths: {"coupon":"res/js/marketing/coupon_list"}});
		require(["coupon"], function (coupon){
			coupon.init();
		});  
	});
</script>