define(
[],
function() {
var gateMachineAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#savegateMachineAdd").click(function(){
			gateMachineAdd.addgateMachine();
		});
		//返回
		$("#closegateMachineAdd").click(function(){
			closeTab("新增闸机");
			$("#gateMachineList").DataTable().ajax.reload(function(){});
		});
	},
	addgateMachine:function() {
		var form = $("form[name=gateMachineAddForm]");
		form.ajaxSubmit({
			url : gateMachineAdd.appPath + "/gateMachine/upASaddgateMachine.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增闸机");
						$("#gateMachineList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#gateMachineNameAdd").empty();
				$("#gateMachineCodeAdd").empty();
				$("#stationNoAdd").empty();
				$("#activeConditionAdd").empty();
				
				if ($.trim(form.find("input[name='gateName']").val())=="") {
					$("#gateMachineNameAdd").append("<font color='red' class='formtips onError emaill'>请输入闸机名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='gateCode']").val())=="") {
					$("#gateMachineCodeAdd").append("<font color='red' class='formtips onError emaill'>请输入闸机编号！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='parkingNo']").val())=="") {
					$("#stationNoAdd").append("<font color='red' class='formtips onError emaill'>请选择停车场！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='activeCondition']").val())=="") {
					$("#activeConditionAdd").append("<font color='red' class='formtips onError emaill'>请选择可用状态！</font>");
					return false;
				}
			}
		});
	 }
	}
	return gateMachineAdd;
})
