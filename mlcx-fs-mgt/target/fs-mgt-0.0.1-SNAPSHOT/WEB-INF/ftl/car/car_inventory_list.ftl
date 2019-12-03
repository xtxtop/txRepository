<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
       <div class="box box-default">
        <div class="box-header with-border">
         <h3 class="box-title">查询</h3>
         <hr>
         <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="carInventorySearchForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-12">
          <div class="col-md-3">
                <div class="frombg">
                    <span>城市</span>
                   <select name="cityId" class="form-control">
				              <option value="">请选择</option>
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
				  </select>
                </div>
                
			</div>
			
			<div class="col-md-3">
               
                <div class="frombg">
                    <span>场站</span>
                   <select name="parkNo" class="form-control">
				               <option value="">请选择</option>
								 <#list pl as p>
									 <option  value="${p.parkNo}" >
				                            ${p.parkName}
				                     </option>
				                 </#list>  
					</select>
                </div>
			</div>
			
			<div class="col-md-3">
               
                <div class="frombg">
                    <span>车辆品牌</span>
                   <select name="carBrandId" class="form-control">
				               <option value="">请选择</option>
								 <#list carBrands as carBrand>
									 <option  value="${carBrand.carBrandId}" >
				                            ${carBrand.carBrandName}
				                     </option>
				                 </#list>  
				  </select>
                </div>
			</div>
			
			<div class="col-md-3">
               
                <div class="frombg">
                    <span>车辆型号</span>
                   <select name="carModelId" class="form-control">
				               <option value="">请选择</option>
								 <#list carModels as carModel>
									 <option  value="${carModel.carModelId}" >
				                            ${carModel.carModelName}
				                     </option>
				                 </#list>  
								</select>
                </div>
			</div>
			<!--修改-->
          <div class="col-md-12">
                <div class="box-footer">
                     <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
                     <!-- <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"><i class="hziconfont icons-qingchu iconbtn"></i>清空</button>-->

                     <!-- <button type="button" class="btn btn-default pull-right btn-flat btncolora" id="carInventorySearchafter"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>确认</button>-->
                     <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault">清空</button>
                   <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="carInventorySearchafter">确定</button>
                 </div>
          </div>
         </div><!-- /.box-body -->

         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="carInventoryTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="carInventoryList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
     
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"carInventory":"res/js/car/car_inventory_list"}});
		require(["carInventory"], function (carInventory){
			carInventory.init();
		});  
	   }); 
    </script>
