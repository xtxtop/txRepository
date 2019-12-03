define([],function() {
var carExtraCostsEdit = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#EditcarExtraCosts").click(function(){
				carExtraCostsEdit.savecheckPlan();
			});
			//新增页面的关闭
			$("#closeEditcarExtraCosts").click(function(){
				closeTab("车辆附加费编辑");
			});
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=carExtraCostsEditFrom]");
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: carExtraCostsEdit.appPath+"/pricingRuleDay/getCarModleByBrand.do",
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
		EditExtraCosts:function(){
			var form = $("form[name=carExtraCostsEditFrom]");
			form.ajaxSubmit({
				url : carExtraCostsEdit.appPath + "/carExtraCosts/editCarExtraCosts.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
							closeTab("车辆附加费编辑");
							$("#carExtraCostsList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='insuranceEdit']").empty();
					$("span[name='preLicensingEdit']").empty();
					$("span[name='remoteCostEdit']").empty();
					$("span[name='overtimeChargeEdit']").empty();
					
					
					
					if (form.find("input[name='insurance']").val()=="") {
						$("span[name='insuranceEdit']").append("<font color='red'>请输入保险费！</font>");
						return false;
					}
					if (form.find("input[name='insurance']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='insurance']").val())) {
						$("span[name='insuranceEdit']").append("<font color='red'>保险费输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='preLicensing']").val()=="") {
						$("span[name='preLicensingEdit']").append("<font color='red'>请输入预授权（押金）！</font>");
						return false;
					}
					if (form.find("input[name='preLicensing']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='preLicensing']").val())) {
						$("span[name='preLicensingEdit']").append("<font color='red'>预授权（押金）输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='overtimeCharge']").val()=="") {
						$("span[name='overtimeChargeEdit']").append("<font color='red'>请输入超时服务费！</font>");
						return false;
					}
					if (form.find("input[name='overtimeCharge']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='overtimeCharge']").val())) {
						$("span[name='overtimeChargeEdit']").append("<font color='red'>超时服务费输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='coverCharge']").val()=="") {
						$("span[name='coverChargeEdit']").append("<font color='red'>请输入服务费！</font>");
						return false;
					}
					if (form.find("input[name='coverCharge']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='coverCharge']").val())) {
						$("span[name='coverChargeEdit']").append("<font color='red'>服务费输入有误(正数或0)！</font>");
						return false;
					}
					
					
					
					
				}
			});
		},
savecheckPlan:function() {
	var form = $("form[name=carExtraCostsEditFrom]");
	var carBrandId=form.find("select[name=carBrandId]").val();
	var carModelId=form.find("select[name=carModelId]").val();
	var cityId=form.find("select[name=cityId]").val();
	
	if (carBrandId=="") {
		$("span[name='carBrandEdit']").html("<font color='red'>请选择车辆品牌！</font>");
		return false;
		}else{
			$("span[name='carBrandEdit']").html("");
		}
		if (carModelId==""||carModelId==null) {
				$("span[name='carModelEdit']").html("<font color='red'>没有选择车辆型号或当前品牌未设置型号！</font>");
				return false;
		}else{
			$("span[name='carModelEdit']").html("");
		}
	
	if (form.find("select[name='cityId']").val()=="") {
		$("span[name='cityIdEdit']").html("<font color='red'>请选择城市！</font>");
		return false;
	}else{
		$("span[name='cityIdEdit']").html("");
	}
	
	
	var carModelIdGd=form.find("input[name=carModelIdGd]").val();
	var carBrandIdGd=form.find("input[name=carBrandIdGd]").val();
	var cityIdGd=form.find("input[name=cityIdGd]").val();
	
	if(carModelIdGd==carModelId&&carBrandIdGd==carBrandId&&cityIdGd==cityId){
		carExtraCostsEdit.EditExtraCosts();
	}else{
		
	
		 $.ajax({
				url:carExtraCostsEdit.appPath+"/carExtraCosts/carExtraCostsCheck.do",//验证计费规则的唯一性
				type:"post",
				data:{carBrandId:carBrandId,carModelId:carModelId},
				dataType:"json",
				success:function(res){ 
					if(res.code == "0"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的附加费用已经存在，请重新添加！");
					}else{
						carExtraCostsEdit.EditExtraCosts();
					}
				}
		 });
	}
}
}
return carExtraCostsEdit;
})
