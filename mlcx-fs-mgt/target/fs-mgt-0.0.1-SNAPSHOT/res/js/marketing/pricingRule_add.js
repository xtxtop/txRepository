define([],function() {
var pricingRuleAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addpricingRule").click(function(){
				pricingRuleAdd.savecheckPlan();
			});
			//新增页面的关闭
			$("#closeaddpricingRule").click(function(){
				closeTab("新增计费规则");
			});
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=pricingRuleAddFrom]");
			form.find("select[name='carBrand']").change(function(){
				var brandId = form.find("select[name='carBrand']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: pricingRuleAdd.appPath+"/pricingRule/getCarModleByBrand.do",
		             data: {brandId:brandId},
		             success: function(res){
		            	 var carSeries = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='carModel']").html("");
		            		 var option = "";
		            		 for(var i=0;i<carSeries.length;i++){
		            			 option+="<option  value='"+carSeries[i].carSeriesId+"'> "+carSeries[i].carSeriesName+" </option>";
		              		 }
		            		 form.find("select[name='carModel']").html(option);
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		                 }
		             }
				});
			});
		},
		addPricingRule:function(){
			var form = $("form[name=pricingRuleAddFrom]");
			var orderCaculateType = form.find("input[name='orderCaculateType']").val();
			form.ajaxSubmit({
				url : pricingRuleAdd.appPath + "/pricingRule/updatePricingRule.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("新增计费规则");
							$("#pricingRuleList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;增加失败，" + msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='ruleNameAdd']").empty();
					$("span[name='cityIdAdd']").empty();
					$("span[name='carBrandAdd']").empty();
					$("span[name='carModelAdd']").empty();
					$("span[name='priceOfMinuteAdd']").empty();
					$("span[name='priceOfKmAdd']").empty();
					$("span[name='regardlessFranchiseAdd']").empty();
					$("span[name='insuranceAmountAdd']").empty();
					if(orderCaculateType=="1"){
						$("span[name='priceOfMinuteOrdinaryAdd']").empty();
						$("span[name='priceOfKmOrdinaryAdd']").empty();
						$("span[name='billingCapPerDayOrdinaryAdd']").empty();
					}
					$("span[name='baseFeeAdd']").empty();
					$("span[name='companyIdAdd']").empty();
//					$("span[name='discountAdd']").empty();
//					$("span[name='priceOfKmAdd']").empty();
//					$("span[name='servicePriceOfMinuteAdd']").empty();
//					$("span[name='servicePriceOfKmAdd']").empty();
					$("span[name='billingCapPerDayAdd']").empty();
					//$("span[name='priorityAdd']").empty();
//					$("span[name='availableTime1Add']").empty();
//					$("span[name='availableTime2Add']").empty();
//					$("span[name='freeMinutesAdd']").empty();
					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameAdd']").append("<font color='red'>请输入名称！</font>");
						return false;
					}
					if (form.find("select[name='cityId']").val()=="") {
						$("span[name='cityIdAdd']").append("<font color='red'>请选择城市！</font>");
						return false;
					}
					if (form.find("select[name='carBrand']").val()=="") {
						$("span[name='carBrandAdd']").append("<font color='red'>请选择车辆品牌！</font>");
						return false;
					}
					if (form.find("select[name='carModel']").val()==""||form.find("select[name='carModel']").val()==null) {
						$("span[name='carModelAdd']").append("<font color='red'>请选择车辆型号！</font>");
						return false;
					}
					if (form.find("input[name='priceOfMinute']").val()=="") {
						$("span[name='priceOfMinuteAdd']").append("<font color='red'>请输入工作日按时间计费金额！</font>");
						return false;
					}
					if (form.find("input[name='priceOfKm']").val()=="") {
						$("span[name='priceOfKmAdd']").append("<font color='red'>请输入工作日按里程计费金额！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='priceOfMinuteOrdinary']").val()=="") {
							$("span[name='priceOfMinuteOrdinaryAdd']").append("<font color='red'>请输入周末按时间计费金额！</font>");
							return false;
						}
						if (form.find("input[name='priceOfKmOrdinary']").val()=="") {
							$("span[name='priceOfKmOrdinaryAdd']").append("<font color='red'>请输入周末按里程计费金额！</font>");
							return false;
						}
					}
					if (form.find("input[name='priceOfMinute']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfMinute']").val())) {
						$("span[name='priceOfMinuteAdd']").append("<font color='red'>工作日时间计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='priceOfKm']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfKm']").val())) {
						$("span[name='priceOfKmAdd']").append("<font color='red'>工作日里程计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='priceOfMinuteOrdinary']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfMinuteOrdinary']").val())) {
							$("span[name='priceOfMinuteOrdinaryAdd']").append("<font color='red'>周末时间计费金额输入有误(正数或0)！</font>");
							return false;
						}
						if (form.find("input[name='priceOfKmOrdinary']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfKmOrdinary']").val())) {
							$("span[name='priceOfKmOrdinaryAdd']").append("<font color='red'>周末里程计费金额输入有误(正数或0)！</font>");
							return false;
						}
					}
//					if (form.find("select[name='companyId']").val()!="") {
//						if (form.find("input[name='discount']").val()=="") {
//							$("span[name='discountAdd']").append("<font color='red'>请输入集团会员享受的折扣！</font>");
//							return false;
//						}else if(form.find("input[name='discount']").val()!=""){
//							if(!/^([01](\.0+)?|0\.[0-9]{0,2})$/.test(form.find("input[name='discount']").val())){
//								$("span[name='discountAdd']").append("<font color='red'>折扣需输入0~1之间且最多小数位为2的数！</font>");
//								return false;
//							}
//						}
//					}
					
					if (form.find("input[name='billingCapPerDay']").val()=="") {
						$("span[name='billingCapPerDayAdd']").append("<font color='red'>请输入工作日计费封顶金额！</font>");
						return false;
					}
					if (form.find("input[name='billingCapPerDay']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='billingCapPerDay']").val())) {
						$("span[name='billingCapPerDayAdd']").append("<font color='red'>工作日计费封顶金额输入有误(正数或0)！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='billingCapPerDayOrdinary']").val()=="") {
							$("span[name='billingCapPerDayOrdinaryAdd']").append("<font color='red'>请输入周末计费封顶金额！</font>");
							return false;
						}
						if (form.find("input[name='billingCapPerDayOrdinaryAdd']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='billingCapPerDayOrdinary']").val())) {
							$("span[name='billingCapPerDayOrdinaryAdd']").append("<font color='red'>周末计费封顶金额输入有误(正数或0)！</font>");
							return false;
						}
					}
					if (form.find("input[name='baseFee']").val()=="") {
						$("span[name='baseFeeAdd']").append("<font color='red'>请输入起步价金额！</font>");
						return false;
					}
					
					if (form.find("input[name='baseFee']").val()!=""&&!/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/.test(form.find("input[name='baseFee']").val())) {
						$("span[name='baseFeeAdd']").append("<font color='red'>起步价输入有误(正数或小数后两位)！</font>");
						return false;
					}
//					if (form.find("input[name='priority']").val()=="") {
//						$("span[name='priorityAdd']").append("<font color='red'>请输入优先级！</font>");
//						return false;
//					}
//					
//					if (form.find("input[name='priority']").val()!=""&&!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='priority']").val())) {
//						$("span[name='priorityAdd']").append("<font color='red'>优先级请输入正整数！</font>");
//						return false;
//					}
//					if (form.find("input[name='availableTime1']").val()=="") {
//						$("span[name='availableTime1Add']").append("<font color='red'>有效期至不为空！</font>");
//						return false;
//					}
//					if (form.find("input[name='availableTime2']").val()=="") {
//						$("span[name='availableTime2Add']").append("<font color='red'>有效期至不为空！</font>");
//						return false;
//					}
//					if (form.find("input[name='availableTime1']").val()!=""&&form.find("input[name='availableTime2']").val()!="") {
//						if(new Date(form.find("input[name='availableTime1']").val())>new Date(form.find("input[name='availableTime2']").val())){
//							$("span[name='availableTime1Add']").append("<font color='red'>有效期开始时间不能大于结束时间！</font>");
//							return false;
//						}
//					}
					if (form.find("input[name='regardlessFranchise']").val()=="") {
						$("span[name='regardlessFranchiseAdd']").append("<font color='red'>请输入不计免赔费！</font>");
						return false;
					}
					if (form.find("input[name='regardlessFranchise']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='regardlessFranchise']").val())) {
						$("span[name='regardlessFranchiseAdd']").append("<font color='red'>不计免赔费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='insuranceAmount']").val()=="") {
						$("span[name='insuranceAmountAdd']").append("<font color='red'>请输入保险费！</font>");
						return false;
					}
					if (form.find("input[name='insuranceAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='insuranceAmount']").val())) {
						$("span[name='insuranceAmountAdd']").append("<font color='red'>保险费金额输入有误(正数或0)！</font>");
						return false;
					}
				}
			});
		},
savecheckPlan:function() {
	var form = $("form[name=pricingRuleAddFrom]");
	var carBrand=form.find("select[name=carBrand]").val();
	var carModel=form.find("select[name=carModel]").val();
	var companyId=form.find("select[name=companyId]").val();
	if (form.find("input[name='ruleName']").val()=="") {
		$("span[name='ruleNameAdd']").html("<font color='red'>请输入名称！</font>");
		return false;
	}else{
		$("span[name='ruleNameAdd']").html("");
	}
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
	if (carModel=="") {
			$("span[name='carModelAdd']").html("<font color='red'>请选择车辆型号！</font>");
			return false;
	}else{
		$("span[name='carModelAdd']").html("");
	}
	pricingRuleAdd.addPricingRule();
//		 $.ajax({
//				url:pricingRuleAdd.appPath+"/pricingRule/uniquePricingRule.do",//验证计费规则的唯一性
//				type:"post",
//				data:{carBrand:carBrand,carModel:carModel,companyId:companyId},
//				dataType:"json",
//				success:function(res){ 
//					if(res == "1"){ 
//						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的计费规则已经存在，请重新添加！");
//					}else{
//						pricingRuleAdd.addPricingRule();
//					}
//				}
//		 });
}
}
return pricingRuleAdd;
})
