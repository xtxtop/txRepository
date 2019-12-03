define([],function() {	
var carOwnerCencor = {
		appPath : getPath("app"),
		init : function() {
			//审核车主信息提交
			$("#cencorCarOwner").click(function(){
				carOwnerCencor.cencorCarOwner();
			});
			//车主审核页面关闭
			$("#closeCarOwnerCencor").click(function(){
				closeTab("车主信息审核");
			});
		},
		cencorCarOwner:function() {
			var form = $("form[name=carOwnerCencorForm]");
			form.ajaxSubmit({
				url : carOwnerCencor.appPath + "/carOwner/cencorCarOwner.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车主审核操作成功", function() {
							closeTab("车主信息审核");
							$("#carOwnerList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车主审核操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#cencorMemoCencor").empty();
					var form = $("form[name=carOwnerCencorForm]");
					if(form.find("input[name=cencorStatus]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择审核状态！");
						return false;
					}
					if(form.find("textarea[name=cencorMemo]").val()==""){
						$("#cencorMemoCencor").append("<font color='red' class='formtips onError emaill'>请输入审核备注！</font>");
						return false;
					}
				}
			});
		}
}
return carOwnerCencor
})
