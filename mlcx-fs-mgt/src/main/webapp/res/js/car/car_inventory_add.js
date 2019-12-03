define([],function() {
var carInventoryAdd = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			//新增提交
			$("#addCarInventoryDay").click(function(){
				carInventoryAdd.addCarInventory();
			});
			//新增页面的关闭
			$("#closeaddCarInventoryDay").click(function(){
				closeTab("库存新增");
			});
			
			
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=carInventoryAddFrom]");
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: carInventoryAdd.appPath+"/pricingRuleDay/getCarModleByBrand.do",
		             data: {brandId:brandId},
		             success: function(res){
		            	 var dataItems = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='carModelId']").html("");
		            		 var option = "<option value=''>请选择</option selected>";
		            		 for(var i=0;i<dataItems.length;i++){
		            			 option+="<option  value='"+dataItems[i].carModelId+"'> "+dataItems[i].carModelName+" </option>";
		              		 }
		            		 form.find("select[name='carModelId']").html(option);
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		                 }
		             }
				});
			});
			
			form.find("select[name='carModelId'],select[name='cityId']").change(function(){
				var cityId = form.find("select[name='cityId']").val();
				var carBrandId = form.find("select[name='carBrandId']").val();
				var carModelId = form.find("select[name='carModelId']").val();
				if(cityId!=""&&carBrandId!=""&&carModelId!=""){
					$.ajax({
						 type: "post",
			             url: carInventoryAdd.appPath+"/carInventory/carInventoryCheck.do",
			             data: {
			            	 cityId:cityId,
			            	 carBrandId:carBrandId,
			            	 carModelId:carModelId
			             },
			             success: function(res){
			            	 
			            	 var data = res.data;
			            	 if(res.code=="1"){
			            		 var now = moment(data.inventoryDate);
			            		 form.find("input[name='inventoryDate']").val(now.format('YYYY-MM-DD'));
			            		 form.find("input[name='inventoryTotal']").val(data.inventoryTotal);
			            		 form.find("input[name='leasedQuantity']").val(data.leasedQuantity);
			            		 form.find("input[name='available']").val(data.available);
			            		// $("#addCarInventoryDay").attr("disabled",false); 
			            		 
			                 }else{
			                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "系统异常。"); 
			                	 //$("#addCarInventoryDay").attr("disabled",true); 
			                 }
			             }
					});
					
				}
			});
			
		},
		addCarInventory:function(){
			var form = $("form[name=carInventoryAddFrom]");
			form.ajaxSubmit({
				url : carInventoryAdd.appPath + "/carInventory/editCarInventory.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("库存新增");
							$("#carInventoryList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='cityIdAdd']").empty();
					//$("span[name='parkNoAdd']").empty();
					$("span[name='carBrandAdd']").empty();
					$("span[name='carModelAdd']").empty();
					$("span[name='inventoryTotalAdd']").empty();
					$("span[name='inventoryDateAdd']").empty();
					$("span[name='leasedQuantityAdd']").empty();
					//$("span[name='reserveQuantityAdd']").empty();
					
					
					
					if (form.find("select[name='cityId']").val()=="") {
						$("span[name='cityIdAdd']").append("<font color='red'>请选择城市！</font>");
						return false;
					}
//					if (form.find("select[name='parkNo']").val()=="") {
//						$("span[name='parkNoAdd']").append("<font color='red'>请选择场站！</font>");
//						return false;
//					}
					if (form.find("select[name='carBrandId']").val()=="") {
						$("span[name='carBrandAdd']").append("<font color='red'>请选择车辆品牌！</font>");
						return false;
					}
					if (form.find("select[name='carModelId']").val()==""||form.find("select[name='carModelId']").val()==null) {
						$("span[name='carModelAdd']").append("<font color='red'>请选择车辆型号！</font>");
						return false;
					}
					if (form.find("input[name='inventoryDate']").val()=="") {
						$("span[name='inventoryDateAdd']").append("<font color='red'>请输入日期！</font>");
						return false;
					}
					if (form.find("input[name='inventoryTotal']").val()=="") {
						$("span[name='inventoryTotalAdd']").append("<font color='red'>请输入库存数！</font>");
						return false;
					}
					if (form.find("input[name='inventoryTotal']").val()!=""&&!/^[0-9]*[0-9][0-9]*$/.test(form.find("input[name='inventoryTotal']").val())) {
						$("span[name='inventoryTotalAdd']").append("<font color='red'>库存数请输入整数！</font>");
						return false;
					}
					
					if (form.find("input[name='leasedQuantity']").val()=="") {
						$("span[name='leasedQuantityAdd']").append("<font color='red'>请输入已租借数量！</font>");
						return false;
					}
					if (form.find("input[name='leasedQuantity']").val()!=""&&!/^[0-9]*[0-9][0-9]*$/.test(form.find("input[name='leasedQuantity']").val())) {
						$("span[name='leasedQuantityAdd']").append("<font color='red'>已租借数量请输入整数！</font>");
						return false;
					}
					
					
					if (form.find("input[name='available']").val()=="") {
						$("span[name='availableAdd']").append("<font color='red'>请输入实际可用库存！</font>");
						return false;
					}
					if (form.find("input[name='available']").val()!=""&&!/^[0-9]*[0-9][0-9]*$/.test(form.find("input[name='available']").val())) {
						$("span[name='availableAdd']").append("<font color='red'>实际可用库存请输入整数！</font>");
						return false;
					}
					
					
					
				}
			});
		},
/*savecheckPlan:function() {
	debugger
	var form = $("form[name=carInventoryAddFrom]");
	var carBrand=form.find("select[name=carBrandId]").val();
	var carModel=form.find("select[name=carModelId]").val();
	var cityId=form.find("select[name=cityId]").val();
	var inventoryDate = form.find("input[name='inventoryDate']").val();
	
	$("span[name='cityIdAdd']").empty();
	$("span[name='carBrandAdd']").empty();
	$("span[name='carModelAdd']").empty();
	
	
	
	
	if (form.find("select[name='cityId']").val()=="") {
		$("span[name='cityIdAdd']").html("<font color='red'>请选择城市！</font>");
		return false;
	}else{
		$("span[name='cityIdAdd']").html("");
	}
	if (carBrand=="") {
			$("span[name='carBrandAdd']").html("<font color='red'>请选择车辆品牌！</font>");
			return false;
	}else{
		$("span[name='carBrandAdd']").html("");
	}
	if (carModel==""||carModel==null) {
			$("span[name='carModelAdd']").html("<font color='red'>没有选择车辆型号或当前品牌未设置型号！</font>");
			return false;
	}else{
		$("span[name='carModelAdd']").html("");
	}
	if (form.find("input[name='inventoryDate']").val()=="") {
		$("span[name='inventoryDateAdd']").append("<font color='red'>请输入日期！</font>");
		return false;
	}else{
		$("span[name='inventoryDateAdd']").html("");
	}
		 $.ajax({
				url:carInventoryAdd.appPath+"/carInventory/checkCarInventory.do",
				type:"post",
				data:{carBrandId:carBrand,carModelId:carModel,cityId:cityId,inventoryDate:inventoryDate},
				dataType:"json",
				success:function(res){ 
					if(res.code == "0"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
					}else{
						carInventoryAdd.addCarInventory();
					}
				}
		 });
		 
}*/
}
return carInventoryAdd;
})

