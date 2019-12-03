<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="couponPlanCencorForm">
		<input type="hidden" name="planNo" value="${couponPlan.planNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">优惠券方案审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>标题：</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.title}</label>
						</td>
						<td>
							<label class=" control-label key">子标题：</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.subtitle}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">优惠类型：</label>
						</td>
						<td>
							<label class="control-label val">
							   <#if couponPlan.couponType==1>
							 	  优惠券
							   </#if>
							</label>
					    </td>
					    <td>
							<label class=" control-label key">优惠方式：</label>
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
							<label class=" control-label key">
							<#if couponPlan.couponMethod==1>
							 	折扣率：
							</#if>
							<#if couponPlan.couponMethod==2>
							 	直减金额：
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
							<label class=" control-label key">封顶金额：</label>
						</td>
						<td>
							 <label class="control-label val">${couponPlan.discountMaxAmount}</label>
						</td>
						</#if>
					</tr>
					<tr>
							<td>
							<label class=" control-label key">满减金额：</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.consumeAmount}</label>
						</td>
						<td>
							<label class=" control-label key">车型：</label>
						</td>
						<td>
							<in<label class="control-label val">${couponPlan.carModelName}</label>
						</td>
					</tr>
					<tr>
					<td>
					<label class=" control-label key">城市：</label>
					</td>
					<td>
					<label class="control-label val">${couponPlan.cityName}</label>
					</td>
					<#if couponPlan.vailableTime1 ??>
					<td>
					<label class=" control-label key">有效日期：</label>
					</td>
					<td>
					<label class="control-label val">${couponPlan.vailableTime1?string('yyyy-MM-dd ')}  至  ${couponPlan.vailableTime2?string('yyyy-MM-dd ')} </label>
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
							<label class=" control-label key"><span>*</span>审核状态：</label>
						</td>
						<td>
							<#if couponPlan.censorStatus?? && couponPlan.censorStatus != null>
							 	<label class="">
	                               <input type="radio" name="censorStatus"value="1" <#if couponPlan.censorStatus == 1>checked</#if>"> 已审核
	                            </label>
	                            <label class="">
	                               <input type="radio" name="censorStatus" value="2" <#if couponPlan.censorStatus == 2>checked</#if>>不通过
                                </label>
								<#else>
								 <label class="">
	                               <input type="radio" name="censorStatus"value="1" checked="checked"> 已审核
	                            </label>
	                            <label class="">
	                               <input type="radio" name="censorStatus" value="2">不通过
                                </label>
							</#if>
							   
						</td>
					</tr>
					<tr>
					<td>
						<label class=" control-label key reason">审核备注：</label>
						</td>
						<td>
							<textarea class="form-control val"   name="censorMemo"></textarea>
						</td>
						<#if couponPlan.availableDays!=null&&couponPlan.availableDays!=''>
						<td>
							<label class=" control-label key">有效天数：</label>
						</td>
						<td>
							<label class="control-label val">${couponPlan.availableDays} 天</label>
						</td>
						</#if>
					</tr>
					<tr>
						<#if couponPlan.photoUrl!=null&&couponPlan.photoUrl!=''>
						<td>
							<label class="col-sm-2 control-label val">图片：</label>
						</td>
						<td colspan="3">
							<label class="control-label val">
                				<img src="${imagePath!''}/${couponPlan.photoUrl}" width="280" height="180"/>
                				</label>
						</td>
						</#if>
						
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="EditCouponPlanRule" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCouponPlanRule" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"couponPlanCencor":"res/js/marketing/couponPlan_cencor"}});
		require(["couponPlanCencor"], function (couponPlanCencor){
			couponPlanCencor.init();
		});  
	}); 
</script>