<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>自定义计费调整</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="pricingRuleDayCustomizedDateEditFrom">
					<input type="hidden" name="customizedId" value="${customizedDate.customizedId}">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label">名称：</label>
							<div class="col-sm-5">
							 <label class="control-label">${customizedDate.ruleName}</label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label">城市：</label>
							<div class="col-sm-5">
				              <label class="control-label">${customizedDate.cityName}</label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label">车辆品牌：</label>
							<div class="col-sm-5">
				              <label class="control-label">${customizedDate.carBrandName}</label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label">车辆型号：</label>
							<div class="col-sm-5">
				              <label class="control-label">${customizedDate.carModelName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label">自定义日期：</label>
							<div class="col-sm-5">
							<label class="control-label">${customizedDate.customizedDate?string('yyyy-MM-dd')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;自定义计费(元/天)：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="priceOfDayCustomized" value="${customizedDate.priceOfDayCustomized}"/>
							</div>
							<div>元/天</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="priceOfDayCustomizedEdit"></span></div>
						</div>
					   </form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeEditPricingRuleDayCustomizedDate" class="btn btn-default pull-right sure btncolorb" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button> 
                    <button type="button" id="EditPricingRuleDayCustomizedDate" class="btn btn-default pull-right sure btncolora" >
                            <i class="glyphicon glyphicon-check"></i>保存
                    </button> 																				
                    </div>	
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleDayCustomizedDateEdit":"res/js/marketing/pricingRuleDayCustomizedDate_edit"}});
		require(["pricingRuleDayCustomizedDateEdit"], function (pricingRuleDayCustomizedDateEdit){
			pricingRuleDayCustomizedDateEdit.init();
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
