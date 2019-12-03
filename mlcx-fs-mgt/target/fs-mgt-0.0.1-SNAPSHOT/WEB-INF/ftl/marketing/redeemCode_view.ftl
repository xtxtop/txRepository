<meta charset="utf-8">
<div class="container-fluid backgroundColor">	
    <form name="redeemCodeViewForm">
    	<input type="hidden" name="redCode" value="${redeemCode.redCode}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">兑换码详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>名称：</label>
						</td>
						<td>
							 <label class="control-label val">${redeemCode.redName}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>兑换码：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.redCode}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>有效日期：</label>
						</td>
						<td>
							  <label class="control-label">${redeemCode.availableTime1?string('yyyy-MM-dd ')}  至  ${redeemCode.availableTime2?string('yyyy-MM-dd ')} </label>
						</td>
                          <#if redeemCode.remark!=null&&redeemCode.remark!=''>
						<td>
							<label class=" control-label reason key"><span></span>备注：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.remark}</label>
						</td>
						</#if>
						<td colspan="2"></td>
					</tr>
					
					 <#if redeemCode.censorStatus!=0>
					<tr>
						<td>
							<label class=" control-label key"><span></span>审核人员：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.censorName}</label>
						</td>
					<td>
						<label class=" control-label key"><span></span>审核日期：</label>
					<td>
						<label class="control-label val">${redeemCode.censorTime?string('yyyy-MM-dd ')}</label>
					</td>
				  
					</tr>
					<tr>
						<td>
				  	<label class=" control-label reason key"><span></span>审核备注：</label>
				  </td>
				  <td>
				  	<label class="control-label val">${redeemCode.censorMemo}</label>
				  </td>
					<td>
						<label class=" control-label key"><span></span>启用状态：</label>
					</td>
					<td>
						<label class="control-label val">
                                <#if redeemCode.isAvailable==1>
                                	启用
                                <#elseif redeemCode.isAvailable==0>
                                	停用
                                </#if>
                                </label>
					</td>
						
					</tr>
					<tr>
					<td>
							<label class=" control-label key"><span></span><#if redeemCode.isAvailable==1>启用时间<#elseif redeemCode.isAvailable==0>停用时间</#if></label>
							</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span><#if redeemCode.isAvailable==1>启用时间<#elseif redeemCode.isAvailable==0>停用时间</#if></label>
							</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						</tr>
						 </#if>
					<tr>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
                                <#if redeemCode.censorStatus==0>未审核
                                <#elseif redeemCode.censorStatus==1>已审核
                                <#elseif redeemCode.censorStatus==2>未通过
                                </#if>
							 </label>
						</td>
						<td colspan="2"></td>
						
					</tr>
					<tr>
						<td>
						<label class=" control-label reason key">优惠方案：</label>
						<td colspan="3">
							<table id="couPonPlanLists_censor" class="table table-bordered table-striped table-hover"></table>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeRedeemCodeView" class="btn-new-type-edit">
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
	  require.config({paths: {"redeemCodeCensor":"res/js/marketing/redeemCode_censor"}});
		require(["redeemCodeCensor"], function (redeemCodeCensor){
			redeemCodeCensor.init();
		});  
	});
</script>