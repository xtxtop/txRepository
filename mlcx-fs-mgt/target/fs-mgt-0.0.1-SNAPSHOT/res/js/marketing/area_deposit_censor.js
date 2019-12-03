define([],function() {	
var areaDeposit = {
		appPath : getPath("app"),
		init : function() {
			//审核地区应缴押金的提交
			$("#censorAreaDeposit").click(function(){
				areaDeposit.censorAreaDeposit();
			});
			//地区应缴押金审核页面关闭
			$("#closeAreaDeposit").click(function(){
				closeTab("地区应缴押金审核");
			});
		},
		censorAreaDeposit:function() {
			var form = $("form[name=areaDepositForm]");
			form.ajaxSubmit({
				url : areaDeposit.appPath + "/areaDeposit/censorAreaDeposit.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "地区应缴押金审核操作成功", function() {
							closeTab("地区应缴押金审核");
							$("#areaDepositList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "地区应缴押金审核操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#censorMemoCensor").empty();
					var form = $("form[name=areaDepositForm]");
					if(form.find("input[name=censorStatus]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择审核状态！");
						return false;
					}
					if (form.find("input[name='censorStatus']:checked").val()=="3" && form.find("textarea[name='censorMemo']").val().trim()=="") {
						$("#censorMemoCensor").append("<font color='red' class='formtips onError emaill'>请输入审核备注！</font>");
						return false;
					}
				}
			});
		}
}
return areaDeposit
})
