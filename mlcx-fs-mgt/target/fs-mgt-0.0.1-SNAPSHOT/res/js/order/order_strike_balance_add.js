define([],function() {	
var orderStrikeBalanceAdd = {
		appPath : getPath("app"),
		init : function() {
			//订单冲账信息提交
			$("#addOrderStrikeBalance").click(function(){
				orderStrikeBalanceAdd.orderStrikeBalanceAdd();
			});
			//订单冲账提交页面关闭
			$("#closeOrderStrikeBalanceAdd").click(function(){
				closeTab("订单冲账");
			});
		},
		orderStrikeBalanceAdd:function() {
			var form = $("form[name=orderStrikeBalanceAddForm]");
			form.ajaxSubmit({
				url : orderStrikeBalanceAdd.appPath + "/orderStrikeBalance/orderStrikeBalanceAdd.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "订单冲账操作成功", function() {
							closeTab("订单冲账");
							$("#orderList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "订单冲账操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var form = $("form[name=orderStrikeBalanceAddForm]");
					if(form.find("input[name=strikeBalanceAmount]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入冲账金额！");
						return false;
					}
//					var num=/^[0-9]\d*(\.\d+)?$/;
					var num=/^[0-9]+(.[0-9]{1,2})?$/; //大于0，且小数点为两位的正则
					var tempValue = form.find("input[name=strikeBalanceAmount]").val();	//冲账金额
					if(!num.test(parseFloat($.trim(tempValue)))){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "冲账金额需是大于0的两位小数的有效金额！");
						return false;
					}else{
						if(parseFloat(tempValue)==0){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "冲账金额需不能为0");
							return false;
						}
						if(parseFloat(tempValue)>parseFloat(form.find("input[name=payAmount]").val())){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "冲账金额需不能大于应付金额！");
							return false;
						}
					}
					if(form.find("textarea[name=strikeBalanceMemo]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入冲账备注！");
						return false;
					}
				}
			});
		}
}
return orderStrikeBalanceAdd
})
