define(
[],
function() {
var deviceAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveAddDevice").click(function(){
			deviceAdd.addDevice();
		});
		//返回
		$("#closeAddDevice").click(function(){
			closeTab("新增设备");
			$("#deviceList").DataTable().ajax.reload(function(){});
		});
		
	},
	addDevice:function() {
		var form = $("form[name=deviceAddForm]");
		form.ajaxSubmit({
			url : deviceAdd.appPath + "/device/addDevice.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增设备");
						$("#deviceList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#deviceSnAdd").empty();
				$("#brandIdAdd").empty();
				$("#deviceModelIdAdd").empty();
				$("#macAddrAdd").empty();
				$("#simCardNoAdd").empty();
				$("#cityIdAdd").empty();
				$("#deviceStatusAdd").empty();
				$("#deviceSnAdd").empty();
//				$("#versionNumberAdd").empty();
				if (form.find("input[name='deviceSn']").val()=="") {
					$("#deviceSnAdd").append("<font color='red' class='formtips onError emaill'>请输入设备序列号！</font>");
					return false;
				}
				if (form.find("input[name='deviceSn']").val()!=""&&(/[a-z]/.test(form.find("input[name='deviceSn']").val()))) {
					$("#deviceSnAdd").append("<font color='red' class='formtips onError emaill'>设备序列号格式不正确，字母要大写！</font>");
					return false;
				}
				if (form.find("select[name='brandId']").val()=="") {
					$("#brandIdAdd").append("<font color='red' class='formtips onError emaill'>请选择品牌！</font>");
					return false;
				}
				if (form.find("select[name='deviceModelId']").val()=="") {
					$("#deviceModelIdAdd").append("<font color='red' class='formtips onError emaill'>请选择设备型号！</font>");
					return false;
				}
				if (form.find("input[name='macAddr']").val()=="") {
					$("#macAddrAdd").append("<font color='red' class='formtips onError emaill'>请输入MAC地址！</font>");
					return false;
				}
				//^([0-9a-fA-F]{2})(([/\s:-][0-9a-fA-F]{2}){5})$
				var macVilade=/[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}/;
				if (form.find("input[name='macAddr']").val()!=""&&(!macVilade.test(form.find("input[name='macAddr']").val()))) {
					$("#macAddrAdd").append("<font color='red' class='formtips onError emaill'>请输入格式为xx:xx:xx:xx:xx:xx的MAC地址！</font>");
					return false;
				}
				if (form.find("input[name='simCardNo']").val()=="") {
					$("#simCardNoAdd").append("<font color='red' class='formtips onError emaill'>请输入SIM卡号！</font>");
					return false;
				}
				if (form.find("input[name='simCardNo']").val()!=""&&(!/^[0-9]*$/.test(form.find("input[name='simCardNo']").val()))) {
					$("#simCardNoAdd").append("<font color='red' class='formtips onError emaill'>SIM卡号格式不正确，只能是数字！</font>");
					return false;
				}
				/*if (form.find("select[name='cityId']").val()=="") {
					$("#cityIdAdd").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
					return false;
				}*/
//				if (form.find("select[name='deviceStatus']").val()=="") {
//					$("#deviceStatusAdd").append("<font color='red' class='formtips onError emaill'>请选择终端状态！</font>");
//					return false;
//				}
//				if (form.find("input[name='versionNumber']").val()=="") {
//					$("#versionNumberAdd").append("<font color='red' class='formtips onError emaill'>请输入版本号！</font>");
//					return false;
//				}
			}
		});
	 }
	}
	return deviceAdd;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
