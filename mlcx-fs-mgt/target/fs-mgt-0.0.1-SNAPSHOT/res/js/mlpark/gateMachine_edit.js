define(
[],
function() {
var gateMachineEdit = {
	appPath : getPath("app"),
	init : function() {
		//编辑提交
		$("#savegateMachineEdit").click(function(){
			gateMachineEdit.EditgateMachine();
		});
		//返回
		$("#closegateMachineEdit").click(function(){
			closeTab("编辑闸机");
			$("#gateMachineList").DataTable().ajax.reload(function(){});
		});
	},
	EditgateMachine:function() {
		var form = $("form[name=gateMachineEditForm]");
		form.ajaxSubmit({
			url : gateMachineEdit.appPath + "/gateMachine/upASaddgateMachine.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑闸机");
						$("#gateMachineList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#gateMachineNameEdit").empty();
				$("#gateMachineCodeEdit").empty();
				$("#stationNoEdit").empty();
				$("#activeConditionEdit").empty();
				
				if ($.trim(form.find("input[name='gateName']").val())=="") {
					$("#gateMachineNameEdit").append("<font color='red' class='formtips onError emaill'>请输入闸机名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='gateCode']").val())=="") {
					$("#gateMachineCodeEdit").append("<font color='red' class='formtips onError emaill'>请输入闸机编号！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='parkingNo']").val())=="") {
					$("#stationNoEdit").append("<font color='red' class='formtips onError emaill'>请选择停车场！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='activeCondition']").val())=="") {
					$("#activeConditionEdit").append("<font color='red' class='formtips onError emaill'>请选择可用状态！</font>");
					return false;
				}
			}
		});
	 }
	}
	return gateMachineEdit;
})
