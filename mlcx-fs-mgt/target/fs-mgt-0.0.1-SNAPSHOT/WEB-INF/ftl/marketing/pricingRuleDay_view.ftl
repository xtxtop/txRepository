<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>日租计费规则详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-11 form-horizontal">
					<form  class="form-horizontal">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-7">
							   <label class="control-label">${pricingRule.ruleName}</lable>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-7">
				               <label class="control-label">${pricingRule.cityName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>工作日计费：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.workingDay}元/天</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>周末计费：</label>
							<div class="col-sm-5">
							 <label class="control-label">${pricingRule.weekend}元/天</lable>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>车辆品牌：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.carBrandName}</lable>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车型：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.carModelName}</lable>
							</div>
						</div>
						<#if pricingRule.companyId!=null&&pricingRule.companyId!=''>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;集团名称：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.companyName}</lable>
							</div>
						</div>
						<!--
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;折扣：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.discount}</lable>
							</div>
						</div>-->
						<!--<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;免费时长：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.freeMinutes}分钟</lable>
							</div>
						</div> -->
						</#if>
						<!--<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;按里程计费：</label>
							<div class="col-sm-3">
							<label class="control-label">${pricingRule.priceOfKm}元/公里</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;人员服务按时计费：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.servicePriceOfMinute}元/分钟</lable>
							</div>
						</div>	
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;人员服务按里程计费：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.servicePriceOfKm}元/公里</lable>
							</div>
						</div>-->
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;是否标准计费：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if pricingRule.isStandardBilling==1>
							是
							<#else>
							否
							</#if>
							</lable>
							</div>
						</div>
						
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;编辑人：</label>
							<div class="col-sm-7">
							<label class="control-label">${sysUser.userName}</lable>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if pricingRule.isAvailable==1>
							启用
							<#else>
							停用
							</#if>
							</lable>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;审核状态：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if pricingRule.cencorStatus==0>
							未审核
							</#if>
							<#if pricingRule.cencorStatus==1>
							已审核
							</#if>
							<#if pricingRule.cencorStatus==2>
							待审核
							</#if>
							<#if pricingRule.cencorStatus==3>
							未通过
							</#if>
							</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;审核日期：</label>
							<div class="col-sm-7">
							<label class="control-label">
								${pricingRule.cencorTime?string('yyyy-MM-dd HH:mm:ss')} 
							</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;审核人：</label>
							<div class="col-sm-7">
							 <label class="control-label"> ${sysUser1.userName}</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-7">
							<label class="control-label">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}</lable>
							</div>
						</div>
						</form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-5" style="margin-bottom:20px;">
                    <button type="button" id="closeViewpricingRuleDay" class="btn btn-default pull-right sure btncolora" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button> 										
                    </div>	
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleDay":"res/js/marketing/pricingRuleDay_list"}});
		require(["pricingRuleDay"], function (pricingRuleDay){
			pricingRuleDay.init();
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
