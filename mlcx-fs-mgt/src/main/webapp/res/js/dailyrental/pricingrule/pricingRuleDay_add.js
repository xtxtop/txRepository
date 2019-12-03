define([],function() {
var pricingRuleDayAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addpricingRuleDay").click(function(){
				pricingRuleDayAdd.saveCheckPricingRuleDay();
			});
			//新增页面的关闭
			$("#closeaddpricingRuleDay").click(function(){
				closeTab("新增日租计费规则");
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
					$("span[name='carModelIdAdd']").empty();
					//$("span[name='carTypeAdd']").empty();
					$("span[name='priceOfDayAdd']").empty();
					$("span[name='priceOfDayOrdinaryAdd']").empty();
					$("span[name='regardlessFranchiseAdd']").empty();
					$("span[name='insuranceAmountAdd']").empty();
					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameAdd']").append("<font color='red'>请输入名称！</font>");
						return false;
					}
					if (form.find("select[name='cityId']").val()=="") {
						$("span[name='cityIdAdd']").append("<font color='red'>请选择城市！</font>");
						return false;
					}
					if (form.find("select[name='carModelId']").val()==""||form.find("select[name='carModelId']").val()==null) {
						$("span[name='carModelIdAdd']").append("<font color='red'>请选择车辆型号</font>");
						return false;
					}
//					if (form.find("select[name='carType']").val()==""||form.find("select[name='carType']").val()==null) {
//						$("span[name='carTypeAdd']").append("<font color='red'>请选择适用类型</font>");
//						return false;
//					}
					if (form.find("input[name='priceOfDay']").val()=="") {
						$("span[name='priceOfDayAdd']").append("<font color='red'>请输入工作日计费金额！</font>");
						return false;
					}
					if (form.find("input[name='priceOfDay']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfDay']").val())) {
						$("span[name='priceOfDayAdd']").append("<font color='red'>周末计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='priceOfDayOrdinary']").val()=="") {
						$("span[name='priceOfDayOrdinaryAdd']").append("<font color='red'>请输入周末计费金额！</font>");
						return false;
					}
					if (form.find("input[name='priceOfDayOrdinary']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfDayOrdinary']").val())) {
						$("span[name='priceOfDayOrdinaryAdd']").append("<font color='red'>工作日计费金额输入有误(正数或0)！</font>");
						return false;
					}
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
		saveCheckPricingRuleDay:function() {
			var form = $("form[name=pricingRuleDayAddFrom]");
			var carModelId=form.find("select[name=carModelId]").val();
			var cityId=form.find("select[name=cityId]").val();
			//var carType=form.find("select[name=carType]").val();
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
			if (carModelId==""||carModelId==null) {
					$("span[name='carModelIdAdd']").html("<font color='red'>请选择车辆型号！</font>");
					return false;
			}else{
				$("span[name='carModelIdAdd']").html("");
			}
//			if (form.find("select[name='carType']").val()==""||form.find("select[name='carType']").val()==null) {
//				$("span[name='carTypeAdd']").append("<font color='red'>请选择适用类型</font>");
//				return false;
//			}else{
//				$("span[name='carTypeAdd']").html("");
//			}
			$.ajax({
				url:pricingRuleDayAdd.appPath+"/pricingRuleDay/uniquePricingRuleDay.do",//验证计费规则的唯一性
				type:"post",
				//data:{carModelId:carModelId,cityId:cityId,carType:carType},
				data:{carModelId:carModelId,cityId:cityId},
				dataType:"json",
				success:function(res){ 
					if(res == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的计费规则已经存在！");
					}else{
						pricingRuleDayAdd.addPricingRule();
					}
				}
			});
		}
	}
return pricingRuleDayAdd;
})
