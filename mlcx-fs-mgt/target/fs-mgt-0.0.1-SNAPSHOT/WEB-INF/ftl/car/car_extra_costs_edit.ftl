<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>车辆附加费编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="carExtraCostsEditFrom">
					<input class="form-control" type="hidden" name="extraCostsNo" value="${carExtraCosts.extraCostsNo}"/>
					<input class="form-control" type="hidden" name="carModelIdGd" value="${carExtraCosts.carModelId}"/>
					<input class="form-control" type="hidden" name="carBrandIdGd" value="${carExtraCosts.carBrandId}"/>
					<input class="form-control" type="hidden" name="cityIdGd" value="${carExtraCosts.cityId}"/>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车辆品牌：</label>
							<div class="col-sm-5">
				              <select name="carBrandId" class="form-control">
				               <option value="">请选择</option>
								 <#list carBrands as carBrand>
									 <option  value="${carBrand.carBrandId}" <#if carExtraCosts.carBrandId == carBrand.carBrandId> selected </#if>  >
				                            ${carBrand.carBrandName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div><span name="carBrandEdit"></span></div>
						</div>
					
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车辆型号：</label>
							<div class="col-sm-5">
				              <select name="carModelId" class="form-control">
				               <option value="">请选择</option>
								 <#list carModels as carModel>
									 <option  value="${carModel.carModelId}" <#if carExtraCosts.carModelId == carModel.carModelId> selected </#if> >
				                            ${carModel.carModelName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div><span name="carModelEdit"></span></div>
						</div>
						 
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-5">
				              <select name="cityId" class="form-control">
				              <option value="">请选择</option>
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}"  <#if carExtraCosts.cityId == citie.dataDictItemId>selected</#if> >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div><span name="cityIdEdit"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>保险费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="insurance" value="${carExtraCosts.insurance}"/>
							</div>
							<div>元/天</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="insuranceEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>预授权（押金）：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="preLicensing" value="${carExtraCosts.preLicensing}"/>
							</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="preLicensingEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>超时服务费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="overtimeCharge" value="${carExtraCosts.overtimeCharge}"/>
							</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="overtimeChargeEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>服务费：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="coverCharge" value="${carExtraCosts.coverCharge}"/>
							</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="coverChargeEdit"></span></div>
						</div>
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>异地还车费：</label>
							<div class="col-sm-5">
							<input class="form-control" name="remoteCost" value=""/>
							</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="remoteCostEdit"></span></div>
						</div> -->
						
					   </form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeEditcarExtraCosts" class="btn btn-default pull-right sure btncolorb" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button> 
                    <button type="button" id="EditcarExtraCosts" class="btn btn-default pull-right sure btncolora" >
                            <i class="glyphicon glyphicon-check"></i>保存
                    </button> 																				
                    </div>	
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carExtraCostsEdit":"res/js/car/car_extra_costs_edit"}});
		require(["carExtraCostsEdit"], function (carExtraCostsEdit){
			carExtraCostsEdit.init();
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
