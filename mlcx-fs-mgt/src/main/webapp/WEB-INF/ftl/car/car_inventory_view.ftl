<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>车辆库存详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="carInventoryEditFrom">
					<input type="hidden" value="${carInventory.carInventoryId}" name="carInventoryId">
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;城市：</label>
							<div class="col-sm-5">
							<label class="control-label val">${carInventory.cityName}</label>
				             
							</div>
							<div><span name="cityIdEdit"></span></div>
						</div>
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label">&nbsp;&nbsp;场站：</label>
							<div class="col-sm-5">
							<label class="control-label"></label>
							</div>
							<div><span name="parkNoEdit"></span></div>
						</div> -->
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;车辆品牌：</label>
							<div class="col-sm-5">
				              <label class="control-label val">${carInventory.carBrandName}</label>
							</div>
							<div><span name="carBrandEdit"></span></div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;车辆型号：</label>
							<div class="col-sm-5">
				               <label class="control-label val">${carInventory.carModelName}</label>
							</div>
							<div><span name="carModelEdit"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;库存数：</label>
							<div class="col-sm-5">
							 <label class="control-label val">${carInventory.inventoryTotal}</label>
							</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="inventoryTotalEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;日期：</label>
							<div class="col-sm-5">
							 <label class="control-label val">${carInventory.inventoryDate?string('yyyy-MM-dd')}</label>
							</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="inventoryDateEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;已租借数量：</label>
							<div class="col-sm-5">
							<label class="control-label val">${carInventory.leasedQuantity}</label>
							</div>
							<div class="col-sm-7" style="pEditing-left:20px;"><span name="leasedQuantityEdit"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;实际可用库存：</label>
							<div class="col-sm-5">
							<label class="control-label val">${carInventory.available}</label>
							</div>
							<div class="col-sm-7" style="padding-left:20px;"><span name="availableEdit"></span></div>
						</div>
						
						
						
					   </form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeViewCarInventory" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                            关闭
                    </button> 
                    </div>	
                </div>
</div>
<script type="text/javascript">
$("#closeViewCarInventory").click(function(){
	closeTab("库存详情");
});
</script>



