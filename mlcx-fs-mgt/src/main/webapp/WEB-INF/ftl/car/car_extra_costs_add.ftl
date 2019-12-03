<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>新增车辆附件费用</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="carExtraCostsAddFrom">
					
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
							<div><span name="carModelAdd"></span></div>
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
							<label class="col-sm-4 control-label"><span>*</span>保险费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="insurance" />
							</div>
							<div>元/天</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="insuranceAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>预授权（押金）：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="preLicensing" />
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="preLicensingAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>超时服务费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="overtimeCharge" />
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="overtimeChargeAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>服务费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="coverCharge" />
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="coverChargeAdd"></span></div>
						</div>
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>异地还车费：</label>
							<div class="col-sm-5">
							<input class="form-control" name="remoteCost" />
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="remoteCostAdd"></span></div>
						</div> -->
						
					   </form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeaddcarExtraCosts" class="btn btn-default pull-right sure btncolorb" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button> 
                    <button type="button" id="addcarExtraCosts" class="btn btn-default pull-right sure btncolora" >
                            <i class="glyphicon glyphicon-check"></i>保存
                    </button> 																				
                    </div>	
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carExtraCostsAdd":"res/js/car/car_extra_costs_add"}});
		require(["carExtraCostsAdd"], function (carExtraCostsAdd){
			carExtraCostsAdd.init();
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
