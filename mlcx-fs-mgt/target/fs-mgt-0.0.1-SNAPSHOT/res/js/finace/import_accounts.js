define([],function() {	
	var importAccounts={
			appPath : getPath("app"),
			init : function() {
				//页面提交
				$("#importAccount").click(function(){
					importAccounts.importAccounts();
				});
				//页面关闭
				$("#closeImportAccount").click(function(){
					closeTab("手动导入账单");
				});
			},
			importAccounts:function() {
				var form = $("form[name=carAddForm]");
				var accountsType = form.find("input[name='accountsType']:checked").val();
				var sendUrl = "/importAccounts/alipay.do";
				if(accountsType == 2){
					sendUrl = "/importAccounts/weChart.do";
				}
				form.ajaxSubmit({
					url : importAccounts.appPath + sendUrl,
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "导入成功", function() {
								$("#importAccountModal").modal('hide')
							});
						} else {
							msg = msg == null ? "" : msg;
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "导入失败！"+msg, function() {
								$("#importAccountModal").modal('hide')
							});
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						$("span[name='timeError']").empty();
						if (form.find("input[name='time']").val()=="") {
							$("span[name='timeError']").append("<font color='red'>请输入有效的日期！</font>");
							return false;
						}
						$("#importAccountModal").modal('show')
					}
				});
			}
			
	}
	return importAccounts;
});


