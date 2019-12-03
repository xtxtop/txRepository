define(
[],
function() {
var deviceEdit = {
	appPath : getPath("app"),
	init : function() {
		//编辑提交
		$("#saveDevice").click(function(){
			deviceEdit.editDevice();
		});
		//返回
		$("#closeDevice").click(function(){
			closeTab("终端设备编辑");
			$("#deviceList").DataTable().ajax.reload(function(){});
		});
		
		/*$("#sss").click(function(){
		
			//$("#ss").removeProp("readonly");
		});*/
		
			
	
	},
	
	editDevice:function() {
		var form = $("form[name=deviceEditForm]");
		form.ajaxSubmit({
			url : deviceEdit.appPath + "/device/updateDevice.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("会员编辑");
						$("#deviceList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#deviceSnEdit").empty();
				$("#brandIdEdit").empty();
				$("#deviceModelIdEdit").empty();
				$("#macAddrEdit").empty();
				$("#simCardNoEdit").empty();
				$("#deviceSnEdit").empty();
				$("#cityIdEdit").empty();
				if (form.find("input[name='deviceSn']").val()=="") {
					$("#deviceSnEdit").append("<font color='red' class='formtips onError emaill'>请输入设备序列号！</font>");
					return false;
				}
				if (form.find("input[name='deviceSn']").val()!=""&&(/[a-z]/.test(form.find("input[name='deviceSn']").val()))) {
					$("#deviceSnEdit").append("<font color='red' class='formtips onError emaill'>设备序列号格式不正确，字母要大写！</font>");
					return false;
				}
				if (form.find("select[name='brandId']").val()=="") {
					$("#brandIdEdit").append("<font color='red' class='formtips onError emaill'>请选择品牌！</font>");
					return false;
				}
				if (form.find("select[name='deviceModelId']").val()=="") {
					$("#deviceModelIdEdit").append("<font color='red' class='formtips onError emaill'>请选择设备型号！</font>");
					return false;
				}
				if (form.find("input[name='macAddr']").val()=="") {
					$("#macAddrEdit").append("<font color='red' class='formtips onError emaill'>请输入MAC地址！</font>");
					return false;
				}
				var macVilade=/[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}:[A-Za-z0-9]{2}/;
				if (form.find("input[name='macAddr']").val()!=""&&(!macVilade.test(form.find("input[name='macAddr']").val()))) {
					$("#macAddrEdit").append("<font color='red' class='formtips onError emaill'>请输入格式为xx:xx:xx:xx:xx:xx的MAC地址！</font>");
					return false;
				}
				if (form.find("input[name='simCardNo']").val()=="") {
					$("#simCardNoEdit").append("<font color='red' class='formtips onError emaill'>请输入SIM卡号！</font>");
					return false;
				}
				if (form.find("input[name='simCardNo']").val()!=""&&(!/^[0-9]*$/.test(form.find("input[name='simCardNo']").val()))) {
					$("#simCardNoEdit").append("<font color='red' class='formtips onError emaill'>SIM卡号格式不正确，只能是数字！</font>");
					return false;
				}
//				if (form.find("select[name='cityId']").val()=="") {
//					$("#cityIdEdit").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
//					return false;
//				}
			}
		});
	 }
	}
	return deviceEdit;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
function test(){
	 $("input[name='deviceSn']").removeProp("readonly");
}
