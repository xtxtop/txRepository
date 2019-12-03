define([],function() {
var pricingRuleDayCustomizedDateEdit = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#EditPricingRuleDayCustomizedDate").click(function(){
				pricingRuleDayCustomizedDateEdit.addPricingRule();
			});
			//新增页面的关闭
			$("#closeEditPricingRuleDayCustomizedDate").click(function(){
				closeTab("自定义计费调整");
			});
			
		},
		addPricingRule:function(){
			var form = $("form[name=pricingRuleDayCustomizedDateEditFrom]");
			form.ajaxSubmit({
				url : pricingRuleDayCustomizedDateEdit.appPath + "/pricingRuleDay/updatePricingRuleDayCustomizedDate.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调整成功", function() {
							closeTab("自定义计费调整");
							$("#pricingRuleDayCustomizedDateList").DataTable().ajax.reload(function(){});
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
return pricingRuleDayCustomizedDateEdit;
})
