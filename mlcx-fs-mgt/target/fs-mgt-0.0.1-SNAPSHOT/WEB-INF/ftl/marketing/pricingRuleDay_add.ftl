<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>新增日租计费规则</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="pricingRuleDayAddFrom">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="ruleName" />
							</div>
							<div><span name="ruleNameAdd"></span></div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-5">
				              <select name="cityId" class="form-control">
				              <option value="">请选择</option>
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div><span name="cityIdAdd"></span></div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车辆品牌：</label>
							<div class="col-sm-5">
				              <select name="carBrandId" class="form-control">
				               <option value="">请选择</option>
								 <#list carBrands as carBrand>
									 <option  value="${carBrand.carBrandId}" >
				                            ${carBrand.carBrandName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div><span name="carBrandAdd"></span></div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车辆型号：</label>
							<div class="col-sm-5">
				              <select name="carModelId" class="form-control">
				               <option value="">请选择</option>
								 <#list carModels as carModel>
									 <option  value="${carModel.carModelId}" >
				                            ${carModel.carModelName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div><br/><span name="carModelAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>工作日计费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="workingDay" />
							</div>
							<div>元/天</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="workingDayAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>周末计费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="weekend" />
							</div>
							<div>元/天</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="weekendAdd"></span></div>
						</div>
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>节假日计费：</label>
							<div class="col-sm-5">
							<input class="form-control" name="holiday" />
							</div>
							<div>元/天</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="holidayAdd"></span></div>
						</div> -->
						
						
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label">&nbsp;&nbsp;集团名称：</label>
							<div class="col-sm-5">
							 <select name="companyId" class="form-control">
							  <option value="">请选择</option>
								 <#list companyList as company>
									 <option  value="${company.companyId}" >
				                            ${company.companyName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div><span name="companyIdAdd"></span></div>
						</div>
						<input type="hidden" name="discount" value="1"/>
						
						 
						 <!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;优先级：</label>
							<div class="col-sm-5">
							<input class="form-control" name="priority" />
							</div>
							<div class="col-sm-5"><span name="priorityAdd"></span></div>
						</div> -->
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;有效日期：</label>
							<div class="col-sm-5">
							<input class="datetimepicker form-control" name="availableTime1" />
							 至<input class="datetimepicker form-control" name="availableTime2" />
							</div>
							<div><span name="availableTime1Add"></span></div>
							<div><span name="availableTime2Add"></span></div>
						</div> -->
					   </form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeaddpricingRuleDay" class="btn btn-default pull-right sure btncolorb" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button> 
                    <button type="button" id="addpricingRuleDay" class="btn btn-default pull-right sure btncolora" >
                            <i class="glyphicon glyphicon-check"></i>保存
                    </button> 																				
                    </div>	
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleDayAdd":"res/js/marketing/pricingRuleDay_add"}});
		require(["pricingRuleDayAdd"], function (pricingRuleDayAdd){
			pricingRuleDayAdd.init();
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
