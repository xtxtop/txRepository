define(
[],
function() {
var memberRechargeAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#addMemberRecharge").click(function(){
			memberRechargeAdd.addPricePackOrder();
		});
		//返回
		$("#closeMemberRecharge").click(function(){
			closeTab("手工充值");
			$("#memberList").DataTable().ajax.reload(function(){});
		});
	},
	addPricePackOrder:function() {
		var form = $("form[name=memberRechargeAddForm]");
		form.ajaxSubmit({
			url : memberRechargeAdd.appPath + "/memberAccountAmount/rechargeMemberAccount.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
						closeTab("手工充值");
						$("#memberList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#packAmountAdd").empty();
				if (form.find("input[name='packAmount']").val() == null || form.find("input[name='packAmount']").val() == "") {
					$("#packAmountAdd").append("<font color='red' class='formtips onError emaill'>请输入充值金额！</font>");
					return false;
				}
				if (!/^(\-)?\d+(\.\d{1,2})?$/.test(form.find("input[name='packAmount']").val())) {
					$("#packAmountAdd").append("<font color='red' class='formtips onError emaill'>充值金额输入有误(实数或小数后两位)！</font>");
					return false;
				}
				if (form.find("input[name='packAmount']").val() == 0 || form.find("input[name='packAmount']").val() == "0") {
					$("#packAmountAdd").append("<font color='red' class='formtips onError emaill'>充值金额不能为0！</font>");
					return false;
				}
			}
		});
	 }
	}
	return memberRechargeAdd;
})
