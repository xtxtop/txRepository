define([],function() {	
var customerFeedbackEdit = {
		appPath : getPath("app"),
		init : function() {
			//客服回复信息提交
			$("#editCustomerFeedback").click(function(){
				customerFeedbackEdit.editCustomerFeedback();
			});
			//客服回复信息页面关闭
			$("#closeCustomerFeedbackEdit").click(function(){
				closeTab("客服回复信息编辑");
			});
		},
		editCustomerFeedback:function(){
			var form = $("form[name=customerFeedbackEditForm]");
			form.ajaxSubmit({
				url : customerFeedbackEdit.appPath + "/customerFeedback/editCustomerFeedback.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "客服回复信息成功", function() {
							closeTab("客服回复信息编辑");
							$("#customerFeedbackList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "客服回复信息失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#replyContentEdit").empty();
				   	var form = $("form[name=customerFeedbackEditForm]");
				    var replyContent=form.find("textarea[name=replyContent]").val();
				    if (replyContent == "") {
						form.find("span[name=replyContentEdit]").append("<font color='red' class='formtips onError emaill'>请输入回复内容！</font>");
						return false;
		            }
				}
			});
		}
}
return customerFeedbackEdit
})
