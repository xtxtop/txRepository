define([],function() {
var carExtraCostsAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addcarExtraCosts").click(function(){
				carExtraCostsAdd.savecheckPlan();
			});
			//新增页面的关闭
			$("#closeaddcarExtraCosts").click(function(){
				closeTab("车辆附加费新增");
			});
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=carExtraCostsAddFrom]");
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: carExtraCostsAdd.appPath+"/pricingRuleDay/getCarModleByBrand.do",
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
		addExtraCosts:function(){
			var form = $("form[name=carExtraCostsAddFrom]");
			form.ajaxSubmit({
				url : carExtraCostsAdd.appPath + "/carExtraCosts/editCarExtraCosts.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("车辆附加费新增");
							$("#carExtraCostsList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='insuranceAdd']").empty();
					$("span[name='preLicensingAdd']").empty();
					$("span[name='remoteCostAdd']").empty();
					$("span[name='overtimeChargeAdd']").empty();
					$("span[name='coverChargeAdd']").empty();
					
					
					
					if (form.find("input[name='insurance']").val()=="") {
						$("span[name='insuranceAdd']").append("<font color='red'>请输入保险费！</font>");
						return false;
					}
					if (form.find("input[name='insurance']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='insurance']").val())) {
						$("span[name='insuranceAdd']").append("<font color='red'>保险费输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='preLicensing']").val()=="") {
						$("span[name='preLicensingAdd']").append("<font color='red'>请输入预授权（押金）！</font>");
						return false;
					}
					if (form.find("input[name='preLicensing']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='preLicensing']").val())) {
						$("span[name='preLicensingAdd']").append("<font color='red'>预授权（押金）输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='overtimeCharge']").val()=="") {
						$("span[name='overtimeChargeAdd']").append("<font color='red'>请输入超时服务费！</font>");
						return false;
					}
					if (form.find("input[name='overtimeCharge']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='overtimeCharge']").val())) {
						$("span[name='overtimeChargeAdd']").append("<font color='red'>超时服务费输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='coverCharge']").val()=="") {
						$("span[name='coverChargeAdd']").append("<font color='red'>请输入服务费！</font>");
						return false;
					}
					if (form.find("input[name='coverCharge']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='coverCharge']").val())) {
						$("span[name='coverChargeAdd']").append("<font color='red'>服务费输入有误(正数或0)！</font>");
						return false;
					}
					
					
					
					
				}
			});
		},
savecheckPlan:function() {
	var form = $("form[name=carExtraCostsAddFrom]");
	var carBrandId=form.find("select[name=carBrandId]").val();
	var carModelId=form.find("select[name=carModelId]").val();
	var cityId=form.find("select[name=cityId]").val();
	
	if (carBrandId=="") {
		$("span[name='carBrandAdd']").html("<font color='red'>请选择车辆品牌！</font>");
		return false;
		}else{
			$("span[name='carBrandAdd']").html("");
		}
		if (carModelId==""||carModelId==null) {
				$("span[name='carModelAdd']").html("<font color='red'>没有选择车辆型号或当前品牌未设置型号！</font>");
				return false;
		}else{
			$("span[name='carModelAdd']").html("");
		}
	
	if (form.find("select[name='cityId']").val()=="") {
		$("span[name='cityIdAdd']").html("<font color='red'>请选择城市！</font>");
		return false;
	}else{
		$("span[name='cityIdAdd']").html("");
	}
	
		 $.ajax({
				url:carExtraCostsAdd.appPath+"/carExtraCosts/carExtraCostsCheck.do",//验证计费规则的唯一性
				type:"post",
				data:{carBrandId:carBrandId,carModelId:carModelId,cityId:cityId},
				dataType:"json",
				success:function(res){ 
					if(res.code == "0"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的附加费用已经存在，请重新添加！");
					}else{
						carExtraCostsAdd.addExtraCosts();
					}
				}
		 });
}
}
return carExtraCostsAdd;
})
