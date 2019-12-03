<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="areaDepositViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">地区应缴押金详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>地区名称：</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.addrRegion}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>应缴押金：</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.depositAmount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>启用状态：</label>
						</td>
						<td>
							<label class="control-label val"><#if areaDeposit.isAvailable==1>启用<#elseif areaDeposit.isAvailable==0>停用</#if></label>
						</td>
						<td>
							 <label class=" control-label key"><span></span><#if areaDeposit.isAvailable==1>启用日期：<#elseif areaDeposit.isAvailable==0>停用时间：</#if>
							</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.availableUpdateTime?string('yyyy-MM-dd ')}</label>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					
					<tr>
							<td>
							<label class=" control-label key"><span></span>审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
                                <#if areaDeposit.censorStatus==0>未审核
                                <#elseif areaDeposit.censorStatus==1>已审核
                                <#elseif areaDeposit.censorStatus==2>待审核
                                <#elseif areaDeposit.censorStatus==3>未通过
                                </#if></label>
						</td>
						<#if areaDeposit.censorMemo!=null&&areaDeposit.censorMemo!=''>
						<td>
							<label class=" control-label key"><span></span>审核备注：</label>
						</td>
						<td colspan="3">
							<label class="control-label val">
									<textarea class="form-control val" rows="1"  name="censorMemo" disabled>${areaDeposit.censorMemo}</textarea>
								</label>
						</td>
					</#if>
					<td colspan="2"></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeAreaDepositView" class="btn-new-type-edit">
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
	  require.config({paths: {"areaDeposit":"res/js/marketing/area_deposit_list"}});
		require(["areaDeposit"], function (areaDeposit){
			areaDeposit.init();
		});  
	});
</script>