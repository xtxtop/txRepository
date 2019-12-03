define([],function() {
var pricingRuleCustomDateAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addPricingRuleCustomDate").click(function(){
				pricingRuleCustomDateAdd.savecheckPlan();
			});
			//新增页面的关闭
			$("#closeAddPricingRuleCustomDate").click(function(){
				closeTab("新增自定义计费");
			});
			
		},
		addPricingRule:function(){
			var form = $("form[name=pricingRuleCustomDateAddForm]");
			form.ajaxSubmit({
				url : pricingRuleCustomDateAdd.appPath + "/pricingRuleDay/updatePricingRuleCustomDate.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("新增自定义计费");
							$("#pricingRuleCustomDateList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='priceOfDayCustomizedAdd']").empty();
					
					if (form.find("input[name='priceOfDayCustomized']").val()=="") {
						$("span[name='priceOfDayCustomizedAdd']").append("<font color='red'>请输入自定义计费！</font>");
						return false;
					}
					
					if (form.find("input[name='priceOfDayCustomized']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfDayCustomized']").val())) {
						$("span[name='priceOfDayCustomizedAdd']").append("<font color='red'>自定义计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
				}
			});
		},
		savecheckPlan:function() {
			var form = $("form[name=pricingRuleCustomDateAddForm]");
			var ruleId = form.find("input[name=ruleId]").val();
			var customizedDate=form.find("input[name=customizedDate]").val();
			
			if (customizedDate=="") {
				$("span[name='customizedDateAdd']").html("<font color='red'>请输入自定义日期！</font>");
				return false;
			}else{
				$("span[name='customizedDateAdd']").html("");
			}
			 $.ajax({
					url:pricingRuleCustomDateAdd.appPath+"/pricingRuleDay/uniquePricingRuleCustomDate.do",
					type:"post",
					data:{ruleId:ruleId,customizedDate:customizedDate},
					dataType:"json",
					success:function(res){ 
						if(res == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前日期的自定义计费已经存在，请重新添加！");
						}else{
							pricingRuleCustomDateAdd.addPricingRule();
						}
					}
			 });
		}
	}
return pricingRuleCustomDateAdd;
})
