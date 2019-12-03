define(
[],
function() {
var lockBillingSchemeAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#savelockBillingSchemeAdd").click(function(){
			lockBillingSchemeAdd.addCharger();
		});
		//返回
		$("#closelockBillingSchemeAdd").click(function(){
			closeTab("新增地锁计费方案");
			$("#lockBillingSchemeList").DataTable().ajax.reload(function(){});
		});
				
	},
	addCharger:function() {
		var form = $("form[name=lockBillingSchemeAddForm]");
		form.ajaxSubmit({
			url : lockBillingSchemeAdd.appPath + "/lockBillingScheme/doaddlockBillingScheme.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增地锁计费方案");
						$("#lockBillingSchemeList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				
				$("#lockSchemeNameAdd").empty();
				$("#freeTimeAdd").empty();
				$("#unitTimeAdd").empty();
				$("#overtimePriceAdd").empty();
				$("#statusAdd").empty();

				if ($.trim(form.find("input[name='lockSchemeName']").val())=="") {
					$("#lockSchemeNameAdd").append("<font color='red' class='formtips onError emaill'>请输入地锁计费名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='freeTime']").val())=="") {
					$("#freeTimeAdd").append("<font color='red' class='formtips onError emaill'>请输入免费时长！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='unitTime']").val())=="") {
					$("#unitTimeAdd").append("<font color='red' class='formtips onError emaill'>请输入单位时间！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='overtimePrice']").val())=="") {
					$("#overtimePriceAdd").append("<font color='red' class='formtips onError emaill'>请输入超时费用！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='status']:checked").val())=="") {
					$("#statusAdd").append("<font color='red' class='formtips onError emaill'>请选择地锁计费方案状态！</font>");
					return false;
				}
			}
		});
	 }
	}
	return lockBillingSchemeAdd;
})
