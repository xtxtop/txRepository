define(
[],
function() {
var carAccidentEdit = {
	appPath : getPath("app"),
	init : function() {
		//编辑提交
		$("#saveCarAccidentEdit").click(function(){
			carAccidentEdit.editAccidentEdit();
		});
		//返回
		$("#closeCarAccidentEdit").click(function(){
			closeTab("事故编辑");
			$("#carAccidentList").DataTable().ajax.reload(function(){});
		});
		//详情返回
		$("#closeCarAccidentVeiw").click(function(){
			closeTab("事故详情");
			$("#carAccidentList").DataTable().ajax.reload(function(){});
		});
		//选择日期后根据事故时间和车牌搜索订单和调度车辆及其他信息
		$("#recordAccidentTimeForEdit").change(function(){
			if($("#recordAccidentTimeForEdit").val()!=""){
				var form = $("form[name=carAccidentEditForm]");
				if (form.find("input[name='carPlateNo']").val()=="") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入车牌号！");
					return false;
				}else{
					$.ajax({
						type: "post",
						url: carAccidentEdit.appPath+"/carAccident/toSearchCarAccident.do",
						data: {carPlateNo:form.find("input[name='carPlateNo']").val(),recordAccidentTime:form.find("input[name='recordAccidentTime']").val()},
						success: function(res){
							var carIlligal = res.data;
							if(res.code=="1"){
								form.find("input[name='carNo']").val(carIlligal.carNo);
								form.find("select[name='carModelId'] option[value='"+carIlligal.carModelId+"']").attr("selected","selected");
								form.find("input[name='carModelName']").val(carIlligal.carModelName);
								form.find("select[name='cityId'] option[value='"+carIlligal.cityId+"']").attr("selected","selected");
								form.find("input[name='cityName']").val(carIlligal.cityName);
								form.find("select[name='useCarType'] option[value='"+carIlligal.useCarType+"']").attr("selected","selected");
								form.find("input[name='documentNo']").val(carIlligal.documentNo);
								form.find("input[name='driverId']").val(carIlligal.driverId);
								form.find("input[name='driverName']").val(carIlligal.driverName);
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
							}
						}
					});
				}
			}
		});
	},
	editAccidentEdit:function() {
		var form = $("form[name=carAccidentEditForm]");
		form.ajaxSubmit({
			url : carAccidentEdit.appPath + "/carAccident/updateCarAccident.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("事故编辑");
						$("#carAccidentList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "事故失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#carPlateNoEditErr").empty();
				$("#carModelIdEditErr").empty();
				$("#cityIdEditErr").empty();
				$("#recordAccidentTimeEditErr").empty();
				$("#accidentLevelEditErr").empty();
				$("#accidentLocationEditErr").empty();
				$("#insuranceCompanyEditErr").empty();
				$("#accidentStatusEditErr").empty();
				$("#useCarTypeEditErr").empty();
				$("#documentNoEditErr").empty();
				$("#driverNameEditErr").empty();
				if (form.find("input[name='carPlateNo']").val()=="") {
					$("#carPlateNoEditErr").append("<font color='red' class='formtips onError emaill'>请输入车牌号！</font>");
					return false;
				}
				if (form.find("input[name='carModelName']").val()=="") {
					$("#carModelIdEditErr").append("<font color='red' class='formtips onError emaill'>请选择车辆型号！</font>");
					return false;
				}
				if (form.find("input[name='cityName']").val()=="") {
					$("#cityIdEditErr").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
					return false;
				}
				if (form.find("input[name='recordAccidentTime']").val()=="") {
					$("#recordAccidentTimeEditErr").append("<font color='red' class='formtips onError emaill'>请选择事故时间！</font>");
					return false;
				}
				if (form.find("input[name='accidentLocation']").val()=="") {
					$("#accidentLocationEditErr").append("<font color='red' class='formtips onError emaill'>请输入事故地点！</font>");
					return false;
				}
				if (form.find("select[name='accidentLevel']").val()=="") {
					$("#accidentLevelEditErr").append("<font color='red' class='formtips onError emaill'>请选择事故等级！</font>");
					return false;
				}
				if (form.find("select[name='insuranceCompany']").val()=="") {
					$("#insuranceCompanyEditErr").append("<font color='red' class='formtips onError emaill'>请选择保险公司！</font>");
					return false;
				}
				if (form.find("select[name='accidentStatus']").val()=="") {
					$("#accidentStatusEditErr").append("<font color='red' class='formtips onError emaill'>请选择保险进度！</font>");
					return false;
				}
				if (form.find("select[name='useCarType']").val()=="") {
					$("#useCarTypeEditErr").append("<font color='red' class='formtips onError emaill'>请选择用车类型！</font>");
					return false;
				}
				if (form.find("input[name='documentNo']").val()=="") {
					$("#documentNoEditErr").append("<font color='red' class='formtips onError emaill'>请输入单据号！</font>");
					return false;
				}
				if (form.find("input[name='driverName']").val()=="") {
					$("#driverNameEditErr").append("<font color='red' class='formtips onError emaill'>请输入用车人！</font>");
					return false;
				}
			}
		});
	 }
	}
	return carAccidentEdit;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
