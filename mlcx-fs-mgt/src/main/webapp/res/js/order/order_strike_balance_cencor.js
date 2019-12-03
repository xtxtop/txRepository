define([],function() {	
var orderStrikeBalance = {
		appPath : getPath("app"),
		init : function() {
			//审核订单冲账信息提交
			$("#cencorOrderStrikeBalance").click(function(){
				orderStrikeBalance.cencorOrderStrikeBalance();
			});
			//订单冲账审核页面关闭
			$("#closeOrderStrikeBalanceCencor").click(function(){
				closeTab("订单冲账审核");
			});
		},
		cencorOrderStrikeBalance:function() {
			var form = $("form[name=orderStrikeBalanceCencorForm]");
			form.ajaxSubmit({
				url : orderStrikeBalance.appPath + "/orderStrikeBalance/cencorOrderStrikeBalance.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "订单冲账审核操作成功", function() {
							closeTab("订单冲账审核");
							$("#orderStrikeBalanceList").DataTable().ajax.reload(function(){});
						});
					}else if(code=="2"){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var form = $("form[name=orderStrikeBalanceCencorForm]");
					if(form.find("input[name=censorStatus]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择审核状态！");
						return false;
					}
					if(form.find("textarea[name=censorMemo]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核备注！");
						return false;
					}
				}
			});
		}
}
return orderStrikeBalance
})
