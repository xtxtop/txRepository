define(
[],
function() {
var chargingPileAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#savechargingPileAdd").click(function(){
			chargingPileAdd.addCharger();
		});
		//返回
		$("#closechargingPileAdd").click(function(){
			closeTab("新增充电桩");
			$("#chargingPileList").DataTable().ajax.reload(function(){});
		});
				
	},
	addCharger:function() {
		var form = $("form[name=chargingPileAddForm]");
		form.ajaxSubmit({
			url : chargingPileAdd.appPath + "/chargingPile/doaddchargingPile.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增充电桩");
						$("#chargingPileList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败,"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#chargingPileNoAdd").empty();
				$("#chargingPileNameAdd").empty();
				$("#terminalNoAdd").empty();
				$("#stationNoAdd").empty();
				$("#electricityTypeAdd").empty();
				$("#statusAdd").empty();
				$("#ischargingAdd").empty();
				$("#billingSchemeAdd").empty();
				$("#kwNoAdd").empty();
                $("#voltageInAdd").empty();
                $("#voltageOutAdd").empty();
                $("#isoAdd").empty();
				
				if ($.trim(form.find("input[name='chargingPileNo']").val())=="") {
					$("#chargingPileNoAdd").append("<font color='red' class='formtips onError emaill'>请输入充电桩编码！</font>");
					return false;
				}else if(form.find("input[name='chargingPileNo']").val().length>16){
					$("#chargingPileNoAdd").append("<font color='red' class='formtips onError emaill'>充电桩编码长度只不能超过16位！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='chargingPileName']").val())=="") {
					$("#chargingPileNameAdd").append("<font color='red' class='formtips onError emaill'>请输入充电桩名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='terminalNo']").val())=="") {
					$("#terminalNoAdd").append("<font color='red' class='formtips onError emaill'>请输入终端编号！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='stationNo']").val())=="") {
					$("#stationNoAdd").append("<font color='red' class='formtips onError emaill'>请选择场站！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='electricityType']:checked").val())=="") {
					$("#electricityTypeAdd").append("<font color='red' class='formtips onError emaill'>请选择充电桩类型！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='status']:checked").val())=="") {
					$("#statusAdd").append("<font color='red' class='formtips onError emaill'>请选择充电桩状态！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='ischarging']:checked").val())=="") {
					$("#ischargingAdd").append("<font color='red' class='formtips onError emaill'>请选择是否免费！</font>");
					return false;
				}
				if($.trim(form.find("input[name='ischarging']:checked").val())==1){
					if ($.trim(form.find("select[name='billingScheme']").val())=="") {
						$("#billingSchemeAdd").append("<font color='red' class='formtips onError emaill'>请选择计费方案！</font>");
						return false;
					}
				}
				var kwjs = $.trim(form.find("input[name='kw']").val());
                if (kwjs =="" || kwjs.indexOf("-") == -1) {
                    $("#kwNoAdd").append("<font color='red' class='formtips onError emaill'>请输入范围，并拿“-”隔开！</font>");
                    return false;
                }
                var voltageInjs = $.trim(form.find("input[name='voltageIn']").val());
                if (voltageInjs=="" || voltageInjs.indexOf("-") == -1) {
                    $("#voltageInAdd").append("<font color='red' class='formtips onError emaill'>请输入范围，并拿“-”隔开！</font>");
                    return false;
                }
                var voltageOutjs = $.trim(form.find("input[name='voltageOut']").val());
                if (voltageOutjs=="" || voltageOutjs.indexOf("-") == -1) {
                    $("#voltageOutAdd").append("<font color='red' class='formtips onError emaill'>请输入范围，并拿“-”隔开！</font>");
                    return false;
                }
                var iosjs = $.trim(form.find("input[name='iso']").val());
                if (iosjs=="" || iosjs.indexOf(",") == -1) {
                    $("#isoAdd").append("<font color='red' class='formtips onError emaill'>请输入国际标准，并拿“,”隔开！</font>");
                    return false;
                }
			}
		});
	 }
	}
	return chargingPileAdd;
})
