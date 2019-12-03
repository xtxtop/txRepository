define([],function() {
var pricingRuleDayEdit = {
		appPath : getPath("app"),
		init : function() {
			
			//关闭计费规则详情编辑页
			$("#closeEditPricingRule").click(function(){
				closeTab("计费规则调整");
            });
			//费规则详情编辑提交
			$("#EditPricingRule").click(function(){
				pricingRuleDayEdit.updateCheckPricingRuleDay();
			});
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=pricingRuleDayEditForm]");
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: pricingRuleDayEdit.appPath+"/pricingRuleDay/getCarModleByBrand.do",
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
		updatePricingRule:function(){
			var form = $("form[name=pricingRuleDayEditForm]");
			form.ajaxSubmit({
				url : pricingRuleDayEdit.appPath + "/pricingRuleDay/updatePricingRuleDay.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调整成功", function() {
							closeTab("日租计费规则调整");
							$("#pricingRuleDayList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调整失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='ruleNameEdit']").empty();
					$("span[name='cityIdEdit']").empty();
					$("span[name='carModelIdEdit']").empty();
//					$("span[name='carTypeEdit']").empty();
					$("span[name='priceOfDayEdit']").empty();
					$("span[name='priceOfDayOrdinaryEdit']").empty();
					$("span[name='regardlessFranchiseEdit']").empty();
					$("span[name='insuranceAmountEdit']").empty();
					
					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameEdit']").append("<font color='red'>请输入名称！</font>");
						return false;
					}
					if (form.find("select[name='cityId']").val()=="") {
						$("span[name='cityIdEdit']").append("<font color='red'>请选择城市！</font>");
						return false;
					}
					if (form.find("select[name='carModelId']").val()==""||form.find("select[name='carModelId']").val()==null) {
						$("span[name='carModelIdEdit']").append("<font color='red'>请选择车辆型号！</font>");
						return false;
					}
//					if (form.find("select[name='carType']").val()==""||form.find("select[name='carType']").val()==null) {
//						$("span[name='carTypeEdit']").append("<font color='red'>请选择适用类型！</font>");
//						return false;
//					}
					if (form.find("input[name='priceOfDay']").val()=="") {
						$("span[name='priceOfDayEdit']").append("<font color='red'>请输入工作日计费金额！</font>");
						return false;
					}
					if (form.find("input[name='priceOfDay']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfDay']").val())) {
						$("span[name='priceOfDayEdit']").append("<font color='red'>工作日计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='priceOfDayOrdinary']").val()=="") {
						$("span[name='priceOfDayOrdinaryEdit']").append("<font color='red'>请输入周末计费金额！</font>");
						return false;
					}
					if (form.find("input[name='priceOfDayOrdinary']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfDayOrdinary']").val())) {
						$("span[name='priceOfDayOrdinaryEdit']").append("<font color='red'>周末计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='regardlessFranchise']").val()=="") {
						$("span[name='regardlessFranchiseEdit']").append("<font color='red'>请输入不计免赔费！</font>");
						return false;
					}
					if (form.find("input[name='regardlessFranchise']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='regardlessFranchise']").val())) {
						$("span[name='regardlessFranchiseEdit']").append("<font color='red'>不计免赔费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='insuranceAmount']").val()=="") {
						$("span[name='insuranceAmountEdit']").append("<font color='red'>请输入保险费！</font>");
						return false;
					}
					if (form.find("input[name='insuranceAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='insuranceAmount']").val())) {
						$("span[name='insuranceAmountEdit']").append("<font color='red'>保险费金额输入有误(正数或0)！</font>");
						return false;
					}
					
				}
			});
		},
		updateCheckPricingRuleDay:function() {
			var form = $("form[name=pricingRuleDayEditForm]");
			var carModelId=form.find("select[name=carModelId]").val();
//			var carType=form.find("select[name=carType]").val();
			var ruleId = form.find("input[name=ruleId]").val(); 
			var cityId=form.find("select[name=cityId]").val();
			if (form.find("input[name='ruleName']").val()=="") {
				$("span[name='ruleNameEdit']").html("<font color='red'>请输入名称！</font>");
				return false;
			}else{
				$("span[name='ruleNameEdit']").html("");
			}
			if (form.find("select[name='cityId']").val()=="") {
				$("span[name='cityIdEdit']").html("<font color='red'>请选择城市！</font>");
				return false;
			}else{
				$("span[name='cityIdEdit']").html("");
			}
			if (carModelId==""||carModelId==null) {
				$("span[name='carModelIdEdit']").html("<font color='red'>请选择车辆型号！</font>");
				return false;
			}else{
				$("span[name='carModelIdEdit']").html("");
			}
//			if (carType==""||carType==null) {
//				$("span[name='carTypeEdit']").html("<font color='red'>请选择使用车型！</font>");
//				return false;
//			}else{
//				$("span[name='carTypeEdit']").html("");
//			}
			 $.ajax({
					url:pricingRuleDayEdit.appPath+"/pricingRuleDay/uniquePricingRuleDay.do",//验证计费规则的唯一性
					type:"post",
					//data:{carModelId:carModelId,carType:carType,ruleId:ruleId,cityId:cityId},
					data:{carModelId:carModelId,ruleId:ruleId,cityId:cityId},
					dataType:"json",
					success:function(res){ 
						if(res == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的计费规则已经存在，请重新添加！");
						}else{
							pricingRuleDayEdit.updatePricingRule();
						}
					}
			 });
 	}
}
return pricingRuleDayEdit;
})
