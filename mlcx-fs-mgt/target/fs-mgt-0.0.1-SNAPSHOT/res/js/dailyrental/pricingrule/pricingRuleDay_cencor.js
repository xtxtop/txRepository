define([],function() {
var pricingRuleDayCencor = {
		appPath : getPath("app"),
		init : function() {
			
			//关闭计费规则审核编辑页
			$("#closeCencorPricingRuleDay").click(function(){
				closeTab("日租计费规则审核");
            });
			//计费规则审核提交
			$("#cencorPricingRuleDay").click(function(){
				pricingRuleDayCencor.cencorpricingRuleDay();
			});
		},
		cencorpricingRuleDay:function() {
			var form = $("form[name=pricingRuleDayCencorForm]");
			form.ajaxSubmit({
				url : pricingRuleDayCencor.appPath + "/pricingRuleDay/cencorPricingRuleDay.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功", function() {
							closeTab("计费规则审核");
							$("#pricingRuleDayList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					
					if ($("input[type='radio']:checked").val() !=1 && $("input[type='radio']:checked").val() != 3 ) {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择审核状态！");
						return false;
					}
					if (form.find("textarea[name='cencorMemo']").val()=="") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核备注！");
						return false;
					}
				}
	});
}
}
return pricingRuleDayCencor;
})
