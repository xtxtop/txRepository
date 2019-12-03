<meta charset="utf-8">
<div class="container-fluid backgroundColor" id="scollOo">
	<form name="pricingRuleCustomDateEditFrom">
		<input type="hidden" name="customizedId" value="${customizedDate.customizedId}">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">自定义计费调整</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label">名称：</label>
						</td>
						<td> 
						<label class="control-label">${customizedDate.ruleName}</label>
						</td>
						<td>
							<label class=" control-label">车辆型号：</label>
						</td>
						<td>
							 <label class="control-label">${customizedDate.carModelName}</label>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label"><span>*</span>自定义日期：</label>
						</td>
						<td>
							<label class="control-label">${customizedDate.customizedDate?string('yyyy-MM-dd')}</label>
						</td>
						
						<td>
							<label class=" control-label"><span>*</span>自定义计费(元/天)：</label>
						</td>
						<td>
							 <input class="form-control" name="priceOfDayCustomized" value="${customizedDate.priceOfDayCustomized}"/>
							 <span name="priceOfDayCustomizedEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editPricingRuleCustomDate" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditPricingRuleCustomDate" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingRuleCustomDateEdit":"res/js/dailyrental/pricingrule/pricingRuleCustomDate_edit"}});
		require(["pricingRuleCustomDateEdit"], function (pricingRuleCustomDateEdit){
			pricingRuleCustomDateEdit.init();
		});  
	});    
</script>