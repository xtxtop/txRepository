<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingRuleDayEditForm">
		<input type="hidden" name="ruleId" value="${pricingRule.ruleId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">日租计费规则调整</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control" name="ruleName" value="${pricingRule.ruleName}"/>
							<span name="ruleNameEdit"></span>
						</td>
					<td>
						<label class=" control-label"><span>*</span>车辆型号：</label>
					</td>
					<td>
						<select name="carModelId" class="form-control">
								 <#list carModels as carModel>
									 <option  value="${carModel.carModelId}" <#if pricingRule.carModelId==carModel.carModelId>selected</#if>>
				                            ${carModel.carModelName}
				                     </option>
				                 </#list>  
								</select>
						<span name="carModelIdEdit"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>工作日计费(元/天)：</label>
						</td>
						<td>
							<input class="form-control" name="priceOfDay" value="${pricingRule.priceOfDay}"/>
							<span name="priceOfDayEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>周末计费(元/天)：</label>
						</td>
						<td>
							<input class="form-control" name="priceOfDayOrdinary" value="${pricingRule.priceOfDayOrdinary}"/>
							<span name="priceOfDayOrdinaryEdit"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>不计免赔费（元/天）：</label>
						</td>
						<td>
							<input class="form-control" name="regardlessFranchise" value="${pricingRule.regardlessFranchise}"/>
							<span name="regardlessFranchiseEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>保险费费（元/天）：</label>
						</td>
						<td>
							<input class="form-control" name="insuranceAmount" value="${pricingRule.insuranceAmount}"/>
							<span name="insuranceAmountEdit"></span>
						</td>
					</tr>
					<tr>
						<tr>
						<td>
							<label class=" control-label"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control">
				               <option value="">请选择</option>
								 <#list cities as city>
									 <option  value="${city.dataDictItemId}" <#if pricingRule.cityId==city.dataDictItemId>selected</#if>>
				                            ${city.itemValue}
				                     </option>
				                 </#list>  
								</select>
							<span name="cityIdEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>更新时间：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}<label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="EditPricingRule" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditPricingRule" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingRuleDayEdit":"res/js/dailyrental/pricingrule/pricingRuleDay_edit"}});
		require(["pricingRuleDayEdit"], function (pricingRuleDayEdit){
			pricingRuleDayEdit.init();
		});  
	});    
</script>