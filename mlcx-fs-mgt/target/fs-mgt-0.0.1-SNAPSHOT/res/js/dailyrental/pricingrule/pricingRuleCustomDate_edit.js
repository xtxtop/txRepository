define([],function() {
var pricingRuleCustomDate = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#editPricingRuleCustomDate").click(function(){
				pricingRuleCustomDate.addPricingRuleCustomDate();
			});
			//新增页面的关闭
			$("#closeEditPricingRuleCustomDate").click(function(){
				closeTab("自定义计费调整");
			});
			
		},
		addPricingRuleCustomDate:function(){
			var form = $("form[name=pricingRuleCustomDateEditFrom]");
			form.ajaxSubmit({
				url : pricingRuleCustomDate.appPath + "/pricingRuleDay/updatePricingRuleCustomDate.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调整成功", function() {
							closeTab("自定义计费调整");
							$("#pricingRuleCustomDateList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调整失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='priceOfDayCustomizedEdit']").empty();
					
					if (form.find("input[name='priceOfDayCustomized']").val()=="") {
						$("span[name='priceOfDayCustomizedEdit']").append("<font color='red'>请输入自定义计费！</font>");
						return false;
					}
					
					if (form.find("input[name='priceOfDayCustomized']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfDayCustomized']").val())) {
						$("span[name='priceOfDayCustomizedEdit']").append("<font color='red'>自定义计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
				}
			});
		},

}
return pricingRuleCustomDate;
})
