define(
[],
function() {
var lockBillingSchemeEdit = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#savelockBillingSchemeEdit").click(function(){
			lockBillingSchemeEdit.editCharger();
		});
		//返回
		$("#closelockBillingSchemeEdit").click(function(){
			closeTab("编辑地锁计费方案");
			$("#lockBillingSchemeList").DataTable().ajax.reload(function(){});
		});
				
	},
	editCharger:function() {
		var form = $("form[name=lockBillingSchemeEditForm]");
		form.ajaxSubmit({
			url : lockBillingSchemeEdit.appPath + "/lockBillingScheme/doeditlockBillingScheme.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑地锁计费方案");
						$("#lockBillingSchemeList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				
				$("#lockSchemeNameEdit").empty();
				$("#freeTimeEdit").empty();
				$("#unitTimeEdit").empty();
				$("#overtimePriceEdit").empty();
				$("#statusEdit").empty();

				if ($.trim(form.find("input[name='lockSchemeName']").val())=="") {
					$("#lockSchemeNameEdit").append("<font color='red' class='formtips onError emaill'>请输入地锁计费名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='freeTime']").val())=="") {
					$("#freeTimeEdit").append("<font color='red' class='formtips onError emaill'>请输入免费时长！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='unitTime']").val())=="") {
					$("#unitTimeEdit").append("<font color='red' class='formtips onError emaill'>请输入单位时间！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='overtimePrice']").val())=="") {
					$("#overtimePriceEdit").append("<font color='red' class='formtips onError emaill'>请输入超时费用！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='status']:checked").val())=="") {
					$("#statusEdit").append("<font color='red' class='formtips onError emaill'>请选择地锁计费方案状态！</font>");
					return false;
				}
			}
		});
	 }
	}
	return lockBillingSchemeEdit;
})
