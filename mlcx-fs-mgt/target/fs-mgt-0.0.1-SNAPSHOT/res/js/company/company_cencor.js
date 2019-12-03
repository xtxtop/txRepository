define(
[],
function() {
var companyCencor = {
	appPath : getPath("app"),
	init : function() {
		//编辑提交
		$("#saveCencorCompany").click(function(){
			companyCencor.cencorCompnay();
		});
		//返回
		$("#closeCencorCompany").click(function(){
			closeTabBT("集团审核");
			$("#companyList").DataTable().ajax.reload(function(){});
		});
	},
	cencorCompnay:function() {
		var form = $("form[name=companyCencorForm]");
		form.ajaxSubmit({
			url : companyCencor.appPath + "/company/cencorCompany.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功", function() {
						closeTab("集团审核");
						$("#companyList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				
			}
		});
	 }
	}
	return	companyCencor;
})
