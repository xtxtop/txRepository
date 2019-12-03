define([],function() {
var carInventoryEdit = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			//新增提交
			$("#editCarInventory").click(function(){
				carInventoryEdit.editCarInventory();
			});
			//新增页面的关闭
			$("#closeEditCarInventory").click(function(){
				closeTab("库存编辑");
			});
			
			
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=carInventoryEditFrom]");
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: carInventoryEdit.appPath+"/pricingRuleDay/getCarModleByBrand.do",
		             data: {brandId:brandId},
		             success: function(res){
		            	 var dataItems = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='carModelId']").html("");
		            		 var option = "";
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
		},
		editCarInventory:function(){
			var form = $("form[name=carInventoryEditFrom]");
			form.ajaxSubmit({
				url : carInventoryEdit.appPath + "/carInventory/editCarInventory.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
							closeTab("库存新增");
							$("#carInventoryList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='cityIdEdit']").empty();
					//$("span[name='parkNoEdit']").empty();
					$("span[name='carBrandEdit']").empty();
					$("span[name='carModelEdit']").empty();
					$("span[name='inventoryTotalEdit']").empty();
					$("span[name='inventoryDateEdit']").empty();
					$("span[name='leasedQuantityEdit']").empty();
					
					
					
//					if (form.find("select[name='cityId']").val()=="") {
//						$("span[name='cityIdEdit']").append("<font color='red'>请选择城市！</font>");
//						return false;
//					}
//					if (form.find("select[name='parkNo']").val()=="") {
//						$("span[name='parkNoEdit']").append("<font color='red'>请选择场站！</font>");
//						return false;
//					}
//					if (form.find("select[name='carBrandId']").val()=="") {
//						$("span[name='carBrandEdit']").append("<font color='red'>请选择车辆品牌！</font>");
//						return false;
//					}
//					if (form.find("select[name='carModelId']").val()==""||form.find("select[name='carModelId']").val()==null) {
//						$("span[name='carModelEdit']").append("<font color='red'>请选择车辆型号！</font>");
//						return false;
//					}
					if (form.find("input[name='inventoryTotal']").val()=="") {
						$("span[name='inventoryTotalEdit']").append("<font color='red'>请输入库存数！</font>");
						return false;
					}
					if (form.find("input[name='inventoryTotal']").val()!=""&&!/^[0-9]*[0-9][0-9]*$/.test(form.find("input[name='inventoryTotal']").val())) {
						$("span[name='inventoryTotalEdit']").append("<font color='red'>库存数请输入整数！</font>");
						return false;
					}
//					if (form.find("input[name='inventoryDate']").val()=="") {
//						$("span[name='inventoryDateEdit']").append("<font color='red'>请输入日期！</font>");
//						return false;
//					}
					
					if (form.find("input[name='leasedQuantity']").val()=="") {
						$("span[name='leasedQuantityEdit']").append("<font color='red'>请输入已租借数量！</font>");
						return false;
					}
					if (form.find("input[name='leasedQuantity']").val()!=""&&!/^[0-9]*[0-9][0-9]*$/.test(form.find("input[name='leasedQuantity']").val())) {
						$("span[name='leasedQuantityEdit']").append("<font color='red'>已租借数量请输入整数！</font>");
						return false;
					}
					
					
					if (form.find("input[name='available']").val()=="") {
						$("span[name='availableEdit']").append("<font color='red'>请输入实际可用库存！</font>");
						return false;
					}
					if (form.find("input[name='available']").val()!=""&&!/^[0-9]*[0-9][0-9]*$/.test(form.find("input[name='available']").val())) {
						$("span[name='availableEdit']").append("<font color='red'>实际可用库存请输入整数！</font>");
						return false;
					}
					
					
				}
			});
		},
//savecheckPlan:function() {
//	var form = $("form[name=carInventoryEditFrom]");
//	var carBrand=form.find("select[name=carBrandId]").val();
//	var carModel=form.find("select[name=carModelId]").val();
//	
//	
//	
//	
//	if (form.find("select[name='cityId']").val()=="") {
//		$("span[name='cityIdEdit']").html("<font color='red'>请选择城市！</font>");
//		return false;
//	}else{
//		$("span[name='cityIdEdit']").html("");
//	}
//	if (carBrand=="") {
//			$("span[name='carBrandEdit']").html("<font color='red'>请选择车辆品牌！</font>");
//			return false;
//	}else{
//		$("span[name='carBrandEdit']").html("");
//	}
//	if (carModel=="") {
//			$("span[name='carModelEdit']").html("<font color='red'>请选择车辆型号！</font>");
//			return false;
//	}else{
//		$("span[name='carModelEdit']").html("");
//	}
//		 $.ajax({
//				url:carInventoryEdit.appPath+"/CarInventoryDay/uniqueCarInventoryDay.do",//验证计费规则的唯一性
//				type:"post",
//				data:{carBrand:carBrand,carModel:carModel},
//				dataType:"json",
//				success:function(res){ 
//					if(res == "1"){ 
//						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的库存已经存在，请重新添加！");
//					}else{
//						carInventoryEdit.EditCarInventory();
//					}
//				}
//		 });
//}
}
return carInventoryEdit;
})
