define([],function() {	
var loveCarCensor = {
		appPath : getPath("app"),
		init : function() {
			//审核活动提交
			$("#loveCarCensor").click(function(){
                loveCarCensor.censorAdvert();
			});
			//活动审核页面关闭
			$("#closeloveCarCensor").click(function(){
				closeTab("活动审核");
			});
		},
		censorAdvert:function() {
			var form = $("form[name=loveCarCensorForm]");
			form.ajaxSubmit({
				url : loveCarCensor.appPath + "/cLoveCar/censorLoveCar.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核操作成功", function() {
							closeTab("活动审核");
							$("#loveCarMengLongList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#censorMemoCensor").empty();
					var form = $("form[name=loveCarCensorForm]");
					if(form.find("input[name=censorStatus]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择审核状态！");
						return false;
					}

				}
			});
		}
}
return loveCarCensor
})
