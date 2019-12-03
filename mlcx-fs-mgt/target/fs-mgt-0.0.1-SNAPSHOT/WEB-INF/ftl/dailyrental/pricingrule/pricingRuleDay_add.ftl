<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingRuleDayAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增日租计费规则</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control" name="ruleName" placeholder="请输入名称"/>
							<span name="ruleNameAdd"></span>
						</td>
					<td>
						<label class=" control-label"><span>*</span>车辆型号：</label>
					</td>
					<td>
						<select name="carModelId" class="form-control">
				               <option value="">请选择</option>
								 <#list carModels as carModel>
									 <option  value="${carModel.carModelId}" >
				                            ${carModel.carModelName}
				                     </option>
				                 </#list>  
								</select>
						<span name="carModelIdAdd"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>工作日计费(元/天)：</label>
						</td>
						<td>
							<input class="form-control" name="priceOfDay"  placeholder="请输入工作日计费"/>
							<span name="priceOfDayAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>周末计费(元/天)：</label>
						</td>
						<td>
							<input class="form-control" name="priceOfDayOrdinary"  placeholder="请输入周末计费"/>
							<span name="priceOfDayOrdinaryAdd"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>不计免赔费（元/天）：</label>
						</td>
						<td>
							<input class="form-control" name="regardlessFranchise"  placeholder="请输入不计免赔费"/>
							<span name="regardlessFranchiseAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>保险费（元/天）：</label>
						</td>
						<td>
							<input class="form-control" name="insuranceAmount"  placeholder="请输入保险费"/>
							<span name="insuranceAmountAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control">
				               <option value="">请选择</option>
								 <#list cities as city>
									 <option  value="${city.dataDictItemId}" >
				                            ${city.itemValue}
				                     </option>
				                 </#list>  
								</select>
							<span name="cityIdAdd"></span>
						</td>
						
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addpricingRuleDay" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeaddpricingRuleDay" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingRuleDayAdd":"res/js/dailyrental/pricingrule/pricingRuleDay_add"}});
		require(["pricingRuleDayAdd"], function (pricingRuleDayAdd){
			pricingRuleDayAdd.init();
		});  
	});    
</script>