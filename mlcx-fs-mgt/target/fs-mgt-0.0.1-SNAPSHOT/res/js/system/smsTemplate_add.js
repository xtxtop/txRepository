define([],function() {	
var smsTemplateAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增短信模板提交
			$("#addSmsTemplate").click(function(){
				smsTemplateAdd.saveSmsTemplate();
			});
			//新增页面关闭
			$("#closeSmsTemplateAdd").click(function(){
				closeTab("新增短信模板");
			});
		},
		saveSmsTemplate:function() {
			var form = $("form[name=smsTemplateAddForm]");
			form.ajaxSubmit({
				url : smsTemplateAdd.appPath + "/smsTemplate/addSmsTemplate.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增成功", function() {
							closeTab("新增短信模板");
							$("#smsTemplate").DataTable().ajax.reload(function(){});
						});
					}else if(code == "-2"){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "同一类型的模板只能添加一条记录！");
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#templateContentAdd").empty();
				   	var form = $("form[name=smsTemplateAddForm]");
				    var templateContent=form.find("textarea[name=templateContent]").val();
				    if (templateContent == "") {
						$("#templateContentAdd").append("<font color='red' class='formtips onError emaill'>请输入短信模板内容！</font>");
						return false;
		            }
				}
			});
		}
}
return smsTemplateAdd
})
