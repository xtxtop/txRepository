<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="col-sm-2 control-label"><h4>日租计费规则审核</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8 form-horizontal">
					<form  class="form-horizontal" name="pricingRuleDayCencorForm">
					 <input type="hidden" name="ruleNo" value="${pricingRule.ruleNo}"/>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;名称：</label>
							<div class="col-sm-4">
							   <label class="control-label">${pricingRule.ruleName}</lable>
							</div>
						</div>
						 <div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;城市：</label>
							<div class="col-sm-4">
				               <label class="control-label">${pricingRule.cityName}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;车型：</label>
							<div class="col-sm-4">
							<label class="control-label">${pricingRule.carModelName}</lable>
							</div>
						</div>
						
						
						<#if pricingRule.companyId!=null&&pricingRule.companyId!=''>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;集团名称：</label>
							<div class="col-sm-4">
							<label class="control-label">${pricingRule.companyName}</lable>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;折扣：</label>
							<div class="col-sm-4">
							<label class="control-label">${pricingRule.discount}</lable>
							</div>
						</div> 
						</#if>
						
						 <div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;是否标准计费：</label>
							<div class="col-sm-4">
							<label class="control-label">
							<#if pricingRule.isStandardBilling==1>
							是
							<#else>
							否
							</#if>
							</lable>
							</div>
						</div>
						 
						
						 <div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;编辑人：</label>
							<div class="col-sm-4">
							<label class="control-label">${sysUser.userName}</lable>
							</div>
						</div>
						 <div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;状态：</label>
							<div class="col-sm-4">
							<label class="control-label">
							<#if pricingRule.isAvailable==1>
							启用
							<#else>
							停用
							</#if>
							</lable>
							</div>
						</div>
						 <div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;审核状态：</label>
							<div class="col-sm-4" id="cencorStatusId">
							    <label class="radio-inline">
	                               <input type="radio" name="cencorStatus" <#if  pricingRule.cencorStatus==1>checked</#if> value="1"> 已审核
	                            </label>
	                            <label class="radio-inline">
	                               <input type="radio" name="cencorStatus"  <#if  pricingRule.cencorStatus==3>checked</#if> value="3">不通过
                                </label>
							</div>
						</div>
						<div class="form-group">
						<label class="col-sm-3 control-label reason">*&nbsp;&nbsp;审核备注：</label>
	                            <div class="col-sm-4">
	                                 <textarea class="form-control"   name="cencorMemo"></textarea>
	                            </div>
	                      </div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-4">
							<label class="control-label">${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}<label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-4">
							<label class="control-label">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}<label>
							</div>
						</div>
+						</form>	
						<div class="form-group">
							<div class="col-sm-9">
							<button type="button" id="closeCencorPricingRuleDay" class="btn btn-default pull-right sure " >关闭</button>
							<button type="button" id="EditCencorPricingRuleDay" class="btn btn-default pull-right sure " >保存</button> 										
							</div>	
						</div>
					</div>	
        		</div><!-- /.row -->
    
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleDayCencor":"res/js/marketing/pricingRuleDay_cencor"}});
		require(["pricingRuleDayCencor"], function (pricingRuleDayCencor){
			pricingRuleDayCencor.init();
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
