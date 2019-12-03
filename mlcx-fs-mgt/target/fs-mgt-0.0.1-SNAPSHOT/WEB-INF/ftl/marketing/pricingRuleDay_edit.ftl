<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>日租计费规则调整</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="pricingRuleDayEditForm">
					 <input type="hidden" name="ruleNo" value="${pricingRule.ruleNo}"/>
					 <input type="hidden" name="carBrandIdVo" value="${pricingRule.carBrandId}"/>
					 <input type="hidden" name="carModelIdVo" value="${pricingRule.carModelId}"/>
					 <input type="hidden" name="cityIdVo" value="${pricingRule.cityId}"/>
					 <input type="hidden" name="companyIdVo" value="${pricingRule.companyId}"/>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="ruleName" value="${pricingRule.ruleName}"/>
							</div>
							<div style="margin-top:7px;"><span name="ruleNameEdit"></span></div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-5">
							   <select name="cityId" class="form-control">
								 <#list cities as citie>
									 <option  <#if pricingRule.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车辆品牌：</label>
							<div class="col-sm-5">
				              <select name="carBrandId" class="form-control">
								 <#list carBrands as carBrand>
									 <option  value="${carBrand.carBrandId}" <#if pricingRule.carBrandId==carBrand.carBrandId>selected</#if>>
				                            ${carBrand.carBrandName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div style="margin-top:7px;"><span name="carBrandEdit"></span></div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车辆型号：</label>
							<div class="col-sm-5">
				              <select name="carModelId" class="form-control">
								 <#list carModels as carModel>
									 <option  value="${carModel.carModelId}" <#if pricingRule.carModelId==carModel.carModelId>selected</#if>>
				                            ${carModel.carModelName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div style="margin-top:7px;"><span name="carModelEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>工作日计费：</label>
							<div class="col-sm-5">
								 <input class="form-control" name="workingDay" value="${pricingRule.workingDay}"/>
							</div>
							<div style="margin-top:7px;">元/天<span name="workingDayEdit"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>周末计费：</label>
							<div class="col-sm-5">
								 <input class="form-control" name="weekend" value="${pricingRule.weekend}"/>
							</div>
							<div style="margin-top:7px;">元/天<span name="weekendEdit"></span></div>
						</div>
						 
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;集团名称：</label>
							<div class="col-sm-5">
							 <select name="companyId" class="form-control">
							  <option value="" <#if pricingRule.companyId==null||pricingRule.companyId==''>selected</#if>>请选择</option>
								 <#list companyList as company>
									 <option  value="${company.companyId}" <#if pricingRule.companyId==company.companyId>selected</#if>>
				                            ${company.companyName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div style="margin-top:7px;"><span name="companyIdEdit"></span></div>
						</div>
						<input type="hidden" name="discount" value="1"/>
						
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;是否标准计费：</label>
							<div class="col-sm-5">
							    <label class="radio-inline">
	                               <input type="radio" name="isStandardBilling" <#if  pricingRule.isStandardBilling==1>checked</#if> value="1"> 是
	                            </label>
	                            <label class="radio-inline">
	                               <input type="radio" name="isStandardBilling"  <#if  pricingRule.isStandardBilling==0>checked</#if> value="0"> 否
                                </label>
							</div>
						</div>
						 
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-5">
							<label class="control-label">${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}<label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-5">
							<label class="control-label">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}<label>
							</div>
						</div>
						</form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeEditPricingRule" class="btn btn-default pull-right sure btncolorb" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button>
                    <button type="button" id="EditPricingRule" class="btn btn-default pull-right sure btncolora" >
                        <i class="glyphicon glyphicon-check"></i>保存
                    </button> 										
                    </div>	
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleDayEdit":"res/js/marketing/pricingRuleDay_edit"}});
		require(["pricingRuleDayEdit"], function (pricingRuleDayEdit){
			pricingRuleDayEdit.init();
		});  
	});    
      $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
         $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
