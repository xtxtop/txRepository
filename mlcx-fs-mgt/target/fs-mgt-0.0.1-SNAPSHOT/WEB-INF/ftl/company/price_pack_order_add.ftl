<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricePackOrderAddCompanyForm" class="form-horizontal">
		<input type="hidden" name="companyIds" value="${companyIds}" />
		<div class="row hzlist">
			
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">集团套餐订单添加</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
						<label class=" control-label key">套餐名称：</label>
							<input type="hidden" name="packageName" id="packageNameCompany"/>	
						</td>
						<td>
						<select name="packageId" class="form-control val" onchange="selectSetValue('packageIdCompany','packageNameCompany')" id="packageIdCompany">
							    	<option value="">请选择</option>
							    	<#if pricePackageList?exists>
							    		<#list pricePackageList as package>
							    			<option value="${package.packageNo}">${package.packageName}</option>
							    		</#list>
							    	</#if>
							    </select>
						<span id="packageIdAddCompany"></span>
						</td>
						<td>
							<label class=" control-label key">套餐金额：</label>
						</td>
						<td>
							<input class="form-control val" name="packAmount" readonly placeholder="请输入套餐金额"/>
						</td>
					</tr>
					<tr>
					<td>
					<label class=" control-label key">套餐时长：</label>
					</td>
					<td>
					<input class="form-control val" name="packMinute" readonly  placeholder="请输入套餐时长"/>
					</td>
					<td colspan="2"></td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="addPricingPackOrderCompany" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closePricingPackOrderCompany" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
    	require.config({paths: {"pricePackOrderAddCompany":"res/js/company/price_pack_order_add"}});
		require(["pricePackOrderAddCompany"], function (pricePackOrderAddCompany){
			pricePackOrderAddCompany.init();
		});
	   }); 
    </script>