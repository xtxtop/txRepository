define([],function() {	
var advertCensor = {
		appPath : getPath("app"),
		init : function() {
			//审核活动提交
			$("#censorAdvert").click(function(){
				advertCensor.censorAdvert();
			});
			//活动审核页面关闭
			$("#closeAdvertCensor").click(function(){
				closeTab("活动审核");
			});
		},
		censorAdvert:function() {
			var form = $("form[name=advertCensorForm]");
			form.ajaxSubmit({
				url : advertCensor.appPath + "/advert/censorAdvert.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动审核操作成功", function() {
							closeTab("活动审核");
							$("#advertList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动审核操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#censorMemoCensor").empty();
					var form = $("form[name=advertCensorForm]");
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
return advertCensor
})
