<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="couponPlanViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">优惠券方案详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>标题：</label>
						</td>
						<td>
							 <label class="control-label val">${couponPlan.title}</label>
						</td>	
							<td>
								<label class=" control-label key"><span></span>子标题：</label>
							</td>
							<td>
								 <label class="control-label val">${couponPlan.subtitle}</label>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>优惠类型：</label>
						</td>
						<td>
							<label class="control-label val">
							   <#if couponPlan.couponType==1>
							 	  优惠券
							   </#if>
							    <#if couponPlan.couponType==2>
							 	订单分享类优惠券
							   </#if>
							</label>
						</td>

						<td>
							<label class=" control-label key"><span></span>优惠方式：</label>
						</td>
						<td>
							 <label class="control-label val">
							   <#if couponPlan.couponMethod==1>
							 	  折扣
							   </#if>
							   <#if couponPlan.couponMethod==2>
							 	  直减
							   </#if>
							</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>
							<#if couponPlan.couponMethod==1>
							 	折扣率
							</#if>
							<#if couponPlan.couponMethod==2>
							 	直减金额
							</#if>
							</label>
						</td>
						<td>
							 <label class="control-label val">
							   <#if couponPlan.couponMethod==1>
							   		${couponPlan.discount}
								</#if>
								<#if couponPlan.couponMethod==2>
								 	 ${couponPlan.discountAmount}
								</#if>
							   </label>
						</td>
						<#if couponPlan.discountMaxAmount ??>
						<td>
                          <label class=" control-label key"><span></span>封顶金额</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.discountMaxAmount}</label>
						</td>
						</#if>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>满减金额</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.consumeAmount}</label>
						</td>
						<#if couponPlan.vailableTime1 ??>
						<td>
							<label class=" control-label key"><span></span>有效日期：</label>
						</td>
						<td>
							 <label class="control-label">${couponPlan.vailableTime1?string('yyyy-MM-dd ')}  至  ${couponPlan.vailableTime2?string('yyyy-MM-dd ')} </label>
						</td>
						</#if>
					</tr>
					
					<tr>
						<#if couponPlan.availableDays!=null&&couponPlan.availableDays!=''>
						<td>
							<label class=" control-label key"><span></span>有效天数：</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.availableDays} 天</label>
						</td>
						</#if>
						<td>
							<label class=" control-label key"><span></span>车型：</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.carModelName}</label>
						</td>

					</tr>
						<tr>
							<td>
								<label class=" control-label key"><span></span>城市：</label>
							</td>
							<td>
								<label class="control-label val">${couponPlan.cityName}</label>
							</td>
							<#if couponPlan.remark!=null&&couponPlan.remark!=''>
							<td>
								<label class=" control-label reason key"><span></span>备注：</label>
							</td>
							<td>
								<label class="control-label val">${couponPlan.remark}</label>
							</td>
							</#if>
						</tr>
							<tr>
								<td>
									<label class=" control-label key"><span></span>发放数量：</label>
								</td>
								<td>
										<label class="control-label val">${couponPlan.maxQuantity}</label>
								</td>
								<td>
									<label class=" control-label key"><span></span>已发放数量：</label>
								</td>
								<td>
										<label class="control-label val">${couponPlan.existingQuantity}</label>
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span></span>审核状态：</label>
								</td>
								<td>
									<label class="control-label val">
                                <#if couponPlan.censorStatus==0>未审核
                                <#elseif couponPlan.censorStatus==1>已审核
                                <#elseif couponPlan.censorStatus==2>未通过
                                </#if>
							 </label>
								</td>
								<#if couponPlan.censorStatus!=0>
								<td>
									<label class=" control-label key"><span></span>审核人员：</label>
								</td>
								<td>
									<label class="control-label val">${couponPlan.censorName}</label>
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span></span>审核日期：</label>
								</td>
								<td>
									<label class="control-label val">${couponPlan.censorTime?string('yyyy-MM-dd ')}</label>
								</td>
								<td>
									<label class=" control-label reason key"><span></span>审核备注：</label>
								</td>
								<td>
									<textarea class="form-control val" rows="6"  name="censorMemo" disabled>${couponPlan.censorMemo}</textarea>
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span></span>启用状态：</label>
								</td>
								<td>
									<label class="control-label val">
                                <#if couponPlan.isAvailable==1>
                                	启用
                                <#elseif couponPlan.isAvailable==0>
                                	停用
                                </#if>
                                </label>
								</td>
								<td>
									<label class=" control-label key"><span></span><#if couponPlan.isAvailable==1>启用时间<#elseif couponPlan.isAvailable==0>停用时间</#if></label>
							</label>
								</td>
								<td>
									<label class="control-label val">${couponPlan.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
								 </#if>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span></span>创建时间：</label>
								</td>
								<td>
									<label class="control-label val">${couponPlan.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
								<td>
									<label class=" control-label key"><span></span>更新时间：</label>
								</td>
								<td>
									<label class="control-label val">${couponPlan.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
							</tr>
							<tr>
								<#if couponPlan.photoUrl!=null&&couponPlan.photoUrl!=''>
								<td>
									<label class=" control-label key"><span></span>图片：</label>
								</td>
								<td colspan="3">
									<label class="control-label val">
                				<img src="${imagePath!''}/${couponPlan.photoUrl}" width="200" height="180"/>
                				</label>
								</td>
								</#if>
							</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeCouponPlanView" class="btn-new-type-edit">
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
	  require.config({paths: {"couponPlan":"res/js/marketing/couponPlan_list"}});
		require(["couponPlan"], function (couponPlan){
			couponPlan.init();
		});  
	});
</script>
