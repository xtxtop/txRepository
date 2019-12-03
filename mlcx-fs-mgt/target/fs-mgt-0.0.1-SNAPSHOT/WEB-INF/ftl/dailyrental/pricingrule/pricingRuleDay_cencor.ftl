<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingRuleDayCencorForm">
		<input type="hidden" name="ruleId" value="${pricingRule.ruleId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">日租计费规则审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>名称：</label>
						</td>
						<td>
							 <label class="control-label">${pricingRule.ruleName}</lable>
						</td>
					<td>
						<label class=" control-label">*城市：</label>
					</td>
					<td>
						<label class="control-label">${pricingRule.cityName}</label>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*车型：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.carModelName}</lable>
						</td>
						<td>
							<label class=" control-label">*状态：</label>
						</td>
						<td>
							<label class="control-label">
							<#if pricingRule.isAvailable==1>
							启用
							<#else>
							停用
							</#if>
							</lable>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label">*审核状态：</label>
						</td>
						<td>
							<label class="">
	                               <input type="radio" checked name="cencorStatus" <#if  pricingRule.cencorStatus==1>checked</#if> value="1"> 已审核
	                            </label>
	                            <label class="">
	                               <input type="radio" name="cencorStatus"  <#if  pricingRule.cencorStatus==3>checked</#if> value="3">不通过
                                </label>
						</td>
						<td>
							<label class=" control-label reason">*审核备注：</label>
						</td>
						<td>
							<textarea class="form-control"   name="cencorMemo"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*创建时间：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}<label>
						</td>
						<td>
							<label class=" control-label">*更新时间：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}<label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="cencorPricingRuleDay" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCencorPricingRuleDay" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingRuleDayCencor":"res/js/dailyrental/pricingrule/pricingRuleDay_cencor"}});
		require(["pricingRuleDayCencor"], function (pricingRuleDayCencor){
			pricingRuleDayCencor.init();
		});  
	});    
</script>