define([],function() {	
var smsTemplateEdit = {
		appPath : getPath("app"),
		init : function() {
			//修改短信模板提交
			$("#editSmsTemplate").click(function(){
				smsTemplateEdit.editSmsTemplate();
			});
			//短息模板修改页面关闭
			$("#closeSmsTemplateEdit").click(function(){
				closeTab("编辑短信模板");
			});
			
		},
		editSmsTemplate:function() {
			var form = $("form[name=smsTemplateEditForm]");
			form.ajaxSubmit({
				url : smsTemplateEdit.appPath + "/smsTemplate/editSmsTemplate.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "短信模板信息修改成功", function() {
							closeTab("编辑短信模板");
							$("#smsTemplate").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "短信模板信息修改失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#templateContentEdit").empty();
				   	var form = $("form[name=smsTemplateEditForm]");
				    var templateContent=form.find("textarea[name=templateContent]").val();
				    if (templateContent == "") {
						$("#templateContentEdit").append("<font color='red' class='formtips onError emaill'>请输入短信模板内容！</font>");
						return false;
		            }
				}
			});
		}
}
return smsTemplateEdit
})
