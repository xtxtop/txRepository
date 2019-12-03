define([],function() {
var pricingRuleDayAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addpricingRuleDay").click(function(){
				pricingRuleDayAdd.savecheckPlan();
			});
			//新增页面的关闭
			$("#closeaddpricingRuleDay").click(function(){
				closeTab("新增日租计费规则");
			});
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=pricingRuleDayAddFrom]");
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: pricingRuleDayAdd.appPath+"/pricingRuleDay/getCarModleByBrand.do",
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
		addPricingRule:function(){
			var form = $("form[name=pricingRuleDayAddFrom]");
			form.ajaxSubmit({
				url : pricingRuleDayAdd.appPath + "/pricingRuleDay/updatePricingRuleDay.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("新增日租计费规则");
							$("#pricingRuleDayList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='ruleNameAdd']").empty();
					$("span[name='cityIdAdd']").empty();
					$("span[name='carBrandAdd']").empty();
					$("span[name='carModelAdd']").empty();
					$("span[name='workingDayAdd']").empty();
					$("span[name='weekendAdd']").empty();
					$("span[name='holidayAdd']").empty();
					$("span[name='priorityAdd']").empty();
					$("span[name='availableTime1Add']").empty();
					$("span[name='availableTime2Add']").empty();
					
					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameAdd']").append("<font color='red'>请输入名称！</font>");
						return false;
					}
					if (form.find("select[name='cityId']").val()=="") {
						$("span[name='cityIdAdd']").append("<font color='red'>请选择城市！</font>");
						return false;
					}
					if (form.find("select[name='carBrandId']").val()=="") {
						$("span[name='carBrandAdd']").append("<font color='red'>请选择车辆品牌！</font>");
						return false;
					}
					if (form.find("select[name='carModelId']").val()==""||form.find("select[name='carModelId']").val()==null) {
						$("span[name='carModelAdd']").append("<font color='red'>没有选择车辆型号或当前品牌未设置型号！</font>");
						return false;
					}
					if (form.find("input[name='workingDay']").val()=="") {
						$("span[name='workingDayAdd']").append("<font color='red'>请输入工作日计费金额！</font>");
						return false;
					}
					
					if (form.find("input[name='weekend']").val()=="") {
						$("span[name='weekendAdd']").append("<font color='red'>请输入周末计费金额！</font>");
						return false;
					}
					/*if (form.find("input[name='holiday']").val()=="") {
						$("span[name='holidayAdd']").append("<font color='red'>请输入节假日计费金额！</font>");
						return false;
					}*/
					if (form.find("input[name='workingDay']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='workingDay']").val())) {
						$("span[name='workingDayAdd']").append("<font color='red'>工作日计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='weekend']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='weekend']").val())) {
						$("span[name='weekendAdd']").append("<font color='red'>周末计费金额输入有误(正数或0)！</font>");
						return false;
					}
					/*if (form.find("input[name='holiday']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='holiday']").val())) {
						$("span[name='holidayAdd']").append("<font color='red'>节假日计费金额输入有误(正数或0)！</font>");
						return false;
					}*/
					
					
					/*if (form.find("input[name='priority']").val()=="") {
						$("span[name='priorityAdd']").append("<font color='red'>请输入优先级！</font>");
						return false;
					}
					
					if (form.find("input[name='priority']").val()!=""&&!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='priority']").val())) {
						$("span[name='priorityAdd']").append("<font color='red'>优先级请输入正整数！</font>");
						return false;
					}
					if (form.find("input[name='availableTime1']").val()=="") {
						$("span[name='availableTime1Add']").append("<font color='red'>有效期至不为空！</font>");
						return false;
					}
					if (form.find("input[name='availableTime2']").val()=="") {
						$("span[name='availableTime2Add']").append("<font color='red'>有效期至不为空！</font>");
						return false;
					}
					if (form.find("input[name='availableTime1']").val()!=""&&form.find("input[name='availableTime2']").val()!="") {
						if(new Date(form.find("input[name='availableTime1']").val())>new Date(form.find("input[name='availableTime2']").val())){
							$("span[name='availableTime1Add']").append("<font color='red'>有效期开始时间不能大于结束时间！</font>");
							return false;
						}
					}*/
				}
			});
		},
savecheckPlan:function() {
	var form = $("form[name=pricingRuleDayAddFrom]");
	var carBrand=form.find("select[name=carBrandId]").val();
	var carModel=form.find("select[name=carModelId]").val();
	var companyId=form.find("select[name=companyId]").val();
	var cityId=form.find("select[name=cityId]").val();
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
	if (carModel==""||carModel==null) {
			$("span[name='carModelAdd']").html("<font color='red'>没有选择车辆型号或当前品牌未设置型号！</font>");
			return false;
	}else{
		$("span[name='carModelAdd']").html("");
	}
		 $.ajax({
				url:pricingRuleDayAdd.appPath+"/pricingRuleDay/uniquePricingRuleDay.do",//验证计费规则的唯一性
				type:"post",
				data:{carBrand:carBrand,carModel:carModel,cityId:cityId,companyId:companyId},
				dataType:"json",
				success:function(res){ 
					if(res == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的计费规则已经存在，请重新添加！");
					}else{
						pricingRuleDayAdd.addPricingRule();
					}
				}
		 });
}
}
return pricingRuleDayAdd;
})
