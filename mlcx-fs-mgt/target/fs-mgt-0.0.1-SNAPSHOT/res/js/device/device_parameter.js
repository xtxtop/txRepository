define([],function() {
var deviceParameter = {
	appPath : getPath("app"),
	init : function() {
		//重置提交
		$("#saveDeviceParameter").click(function(){
			deviceParameter.editdeviceParameter();
		});
		//重置提交
		$("#closeDeviceParameter").click(function(){
			closeTab("终端设备参数查询");
		});
		
		
		//请求终端获取参数
		deviceParameter.getDeviceParam();
		
		
	},
	
	getDeviceParam:function() {
		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "正在获取车载终端信息", function() {
			var form = $("form[name=deviceParameterForm]");
			form.ajaxSubmit({
				url : deviceParameter.appPath + "/device/getDeviceParam.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "查询成功，"+msg+"！");
							 var data = res.data;
							 form.find($("input[name=deviceSn]")).val(data.devcieSn);
							 form.find($("input[name=brandName]")).val(data.brandName);
							 form.find($("input[name=deviceModel]")).val(data.deviceModel);
							 form.find($("input[name=iccid]")).val(data.iccid);
							 form.find($("input[name=msisdn]")).val(data.msisdn);
							 form.find($("input[name=softwareVersion]")).val(data.softwareVersion);
							 form.find($("input[name=hardwareVersion]")).val(data.hardwareVersion);
							 form.find($("input[name=vin]")).val(data.vin);
							 form.find($("select[name=leaseMode]")).val(data.leaseMode);
							 form.find($("input[name=domainName]")).val(data.domainName);
							 form.find($("input[name=word]")).val(data.word);
							 form.find($("input[name=frequency]")).val(data.frequency);
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "查询失败，"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {
					
				}
			});
		});
	
	 },
	
	 editdeviceParameter:function() {
		var form = $("form[name=deviceParameterForm]");
		form.ajaxSubmit({
			url : deviceParameter.appPath + "/device/restartDeviceParameter.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "重置成功", function() {
						closeTab("终端设备参数查询");
						$("#deviceList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "重置失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				
			}
		});
	 }
	
	
	}
	return deviceParameter;
})

