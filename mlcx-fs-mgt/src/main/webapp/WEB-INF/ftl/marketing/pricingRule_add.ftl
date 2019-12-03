<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingRuleAddFrom">
		<input type="hidden" name="orderCaculateType" value="${orderCaculateType}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增计费规则</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control val" name="ruleName" placeholder="请输入名称"/>
							<span name="ruleNameAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
				              <option value="">请选择</option>
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							<span name="cityIdAdd"></span>	
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车辆品牌：</label>
						</td>
						<td>
						<select name="carBrand" class="form-control val">
				               <option value="">请选择</option>
								 <#list carBrands as carBrand>
									 <option  value="${carBrand.carBrandId}" >
				                            ${carBrand.carBrandName}
				                     </option>
				                 </#list>  
								</select>
								<span name="carBrandAdd"></span>
						<td>
							<label class=" control-label key"><span>*</span>车辆型号：</label>
						</td>
						<td>
							 <select name="carModel" class="form-control val">
				               <option value="">请选择</option>
								 <#list carModels as carModel>
									 <option  value="${carModel.carSeriesId}" >
				                            ${carModel.carSeriesName}
				                     </option>
				                 </#list>  
								</select>
								<span name="carModelAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>工作日按时间计费：</label>
						</td>
						<td class="btn-btnA-con">
							  <input class="form-control val" name="priceOfMinute" placeholder="请知晓工作日按时间计费"/>
							  <div class="btn-btnA">元/分钟</div>
							  <span name="priceOfMinuteAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>工作日按里程计费：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="priceOfKm"  placeholder="请知晓工作日按里程计费"/>
							<div class="btn-btnA">元/公里</div>
							<span name="priceOfKmAdd"></span>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>集团名称：</label>
						</td>
						<td>
							 <select name="companyId" class="form-control val">
							  <option value="">平台</option>
								 <#list companyList as company>
									 <option  value="${company.companyId}" >
				                            ${company.companyName}
				                     </option>
				                 </#list>  
								</select>
							<span name="companyIdAdd"></span>
						</td>
						<!--<input type="hidden" name="discount" value="1"/>-->
						<td>
							<label class=" control-label key"><span>*</span>起步价：</label>
						</td>
						<td>
							<input class="form-control val" name="baseFee" placeholder="请输入起步价"/>
							<span name="baseFeeAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>不计免赔费（元/次）：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control" name="regardlessFranchise" placeholder="请输入不计免赔费"/>
							<div class="btn-btnA">元/次</div>
							<span name="regardlessFranchiseAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>保险费（元/天）：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control" name="insuranceAmount" placeholder="请输入保险费"/>
							<div class="btn-btnA">元/天</div>
							<span name="insuranceAmountAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label key"><span>*</span>标准：</label>
						</td>
						<td>
							<label><input name="isStandardBilling" type="radio" value="0" />否 </label>
                                    <label><input name="isStandardBilling" type="radio" value="1" checked/>是 </label>
                              	   <div style="color:#F00">备注：选择标准不能修改计费规则</div>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>工作日计费封顶：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="billingCapPerDay" placeholder="请填写工作日计费封顶金额"/>
							<div class="btn-btnA">元/天</div>
							<span name="billingCapPerDayAdd"></span>
						</td>
					</tr>
					
						<#if orderCaculateType==1>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>周末按时间计费：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="priceOfMinuteOrdinary" />
							<div class="btn-btnA">元/分钟</div>
							<span name="priceOfMinuteOrdinaryAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>周末按里程计费：</label>
						</td>
						<td class="btn-btnA-con">
							 <input class="form-control val" name="priceOfKmOrdinary" placeholder="请知晓周末按里程计费"/>
							<div class="btn-btnA">元/公里</div>
							<span name="priceOfKmOrdinaryAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>周末计费封顶：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="billingCapPerDayOrdinary" placeholder="请知晓周末计费封顶"/>
							<div class="btn-btnA">元/天</div>
							<span name="billingCapPerDayOrdinaryAdd"></span>
						</td>
						
					</tr>
					</#if>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="2"><button type="button" id="addpricingRule" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeaddpricingRule" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingRuleAdd":"res/js/marketing/pricingRule_add"}});
		require(["pricingRuleAdd"], function (pricingRuleAdd){
			pricingRuleAdd.init();
		});  
	});    
      $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
         $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            minView: "month",
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>