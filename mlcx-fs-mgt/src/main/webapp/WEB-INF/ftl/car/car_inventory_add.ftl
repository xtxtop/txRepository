<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>新增车辆库存</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="carInventoryAddFrom">
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
				              <select name="carModelId" class="form-control" >
				               <option value="">请选择</option>
								 
								</select>
							</div>
							<div><br/><span name="carModelAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;日期：</label>
							<div class="col-sm-5">
							<input class="form-control" name="inventoryDate" readonly="readonly"/>
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="inventoryDateAdd"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;库存数：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="inventoryTotal" />
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="inventoryTotalAdd"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;已租借数量：</label>
							<div class="col-sm-5">
							 <input class="form-control" name="leasedQuantity" />
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="leasedQuantityAdd"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;实际可用库存：</label>
							<div class="col-sm-5">
							<input class="form-control" name="available" />
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="availableAdd"></span></div>
						</div>
					
						
					   </form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeaddCarInventoryDay" class="btn btn-default pull-right sure btncolorb" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button> 
                    <button type="button" id="addCarInventoryDay" class="btn btn-default pull-right sure btncolora" >
                            <i class="glyphicon glyphicon-check"></i>保存
                    </button> 																				
                    </div>	
                </div>
</div>

    
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carInventoryAdd":"res/js/car/car_inventory_add"}});
		require(["carInventoryAdd"], function (carInventoryAdd){
			carInventoryAdd.init();
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
