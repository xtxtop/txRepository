define([],function() {
var memberBalanceRecord = {
		appPath : getPath("app"),
		init : function() {
			
			//关闭优惠券方案审核编辑页
			$("#closeMembeerBalance").click(function(){
				var form = $("form[name=memberBalanceFreezeForm]");
				form.find("input[name=memberNo]").val();
				var isFreeze = form.find("input[name=isFreeze]").val();
				var hint = isFreeze == 1 ? "冻结" : "解冻";
				closeTab(hint+"余额");
            });
			//优惠券方案审核提交
			$("#updateMembeerBalance").click(function(){
				memberBalanceRecord.updateMembeerBalance();
			});
		},
	updateMembeerBalance:function() {
		debugger
	var form = $("form[name=memberBalanceFreezeForm]");
	form.ajaxSubmit({
		url : memberBalanceRecord.appPath + "/member/updateMemberBalance.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
					closeTab("会员余额"+$('#tabTitleName').html());
					$("#memberBalanceList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			if (form.find("textarea[name='freezeReason']").val()=="") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入原因！");
				return false;
			}
		}
	});
}
}
return memberBalanceRecord;
})
