define([],function() {	
var chargerEdit = {
		appPath : getPath("app"),
		beforePath:getPath("before"),
		init : function() {
			//编辑提交
			$("#editCharger").click(function(){
				chargerEdit.editCharger();
			});
			//关闭车辆详情编辑页
			$("#closeChargerEdit").click(function(){
				closeTab("充电桩编辑");
            });
		},
editCharger:function() {
	var form = $("form[name=chargerEditForm]");
	form.ajaxSubmit({
		url : chargerEdit.appPath + "/charger/updateCharger.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
					closeTab("充电桩编辑");
					$("#chargerList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("#chargerPowerYzEd").empty();
			$("#chargerSnYzEd").empty();
			if ($.trim(form.find("input[name='chargerPower']").val())=="") {
				$("#chargerPowerYzEd").append("<font color='red' class='formtips onError emaill'>请输入功率！</font>");
				return false;
			}
			if ($.trim(form.find("input[name='chargerPower']").val())!=""&&!/^[0-9]*$/.test(form.find("input[name='chargerPower']").val())) {
				$("#chargerPowerYzEd").append("<font color='red' class='formtips onError emaill'>功率格式不正确,只能是数字！</font>");
				return false;
			}
			if ($.trim(form.find("input[name='chargerSn']").val())=="") {
				$("#chargerSnYzEd").append("<font color='red' class='formtips onError emaill'>请输入设备串号！</font>");
				return false;
			}
			if ($.trim(form.find("input[name='chargerSn']").val())!=""&&!/^[A-Z0-9]*$/.test(form.find("input[name='chargerSn']").val())) {
				$("#chargerSnYzEd").append("<font color='red' class='formtips onError emaill'>设备串号，只能是数字或是大写字母！</font>");
				return false;
			}
		}
	});
}
}
 return chargerEdit;
})
