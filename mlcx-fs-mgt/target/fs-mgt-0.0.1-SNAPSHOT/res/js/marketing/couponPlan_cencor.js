define([],function() {
var couponPlanCencor = {
		appPath : getPath("app"),
		init : function() {
			
			//关闭优惠券方案审核编辑页
			$("#closeCouponPlanRule").click(function(){
				closeTab("优惠券方案审核");
            });
			//优惠券方案审核提交
			$("#EditCouponPlanRule").click(function(){
				couponPlanCencor.updatecheckPlan();
			});
		},
 updatecheckPlan:function() {
	var form = $("form[name=couponPlanCencorForm]");
	form.ajaxSubmit({
		url : couponPlanCencor.appPath + "/couponPlan/updateCouponPlanCensor.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功", function() {
					closeTab("优惠券方案审核");
					$("#couponPlanList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			if (form.find("textarea[name='cencorMemo']").val()=="") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核备注！");
				return false;
			}
		}
	});
}
}
return couponPlanCencor;
})
