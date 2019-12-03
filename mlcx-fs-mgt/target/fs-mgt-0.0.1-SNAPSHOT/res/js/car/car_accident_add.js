define(
[],
function() {
var carAccidentAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveAddCarAccidentAdd").click(function(){
			carAccidentAdd.addCarAccident();
		});
		//返回
		$("#closeAddCarAccidentAdd").click(function(){
			closeTab("新增事故");
			$("#carIllegalList").DataTable().ajax.reload(function(){});
		});
		//选择日期后根据事故时间和车牌搜索订单和调度车辆及其他信息
		$("#recordAccidentTime").change(function(){
			if($("#recordAccidentTime").val()!=""){
				var form = $("form[name=carAccidentAddForm]");
				if (form.find("input[name='carPlateNo']").val()=="") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入车牌号！");
					return false;
				}
				$.ajax({
					 type: "post",
		             url: carAccidentAdd.appPath+"/carAccident/toSearchCarAccident.do",
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
		});
	},
	addCarAccident:function() {
		var form = $("form[name=carAccidentAddForm]");
		form.ajaxSubmit({
			url : carAccidentAdd.appPath + "/carAccident/addCarAccident.do",
			type : "post",
			data:{recordAccidentTimes:$("#recordAccidentTime").val()},
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增事故");
						$("#carAccidentList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#carPlateNoErr").empty();
				$("#carModelIdErr").empty();
				$("#cityIdErr").empty();
				$("#cityIdErr").empty();
				$("#recordAccidentTimeErr").empty();
				$("#accidentLevelErr").empty();
				$("#insuranceCompanyErr").empty();
				$("#accidentLocationErr").empty();
				$("#accidentStatusErr").empty();
				$("#useCarTypeErr").empty();
				$("#documentNoErr").empty();
				$("#driverNameErr").empty();
				if (form.find("input[name='carPlateNo']").val()=="") {
					$("#carPlateNoErr").append("<font color='red' class='formtips onError emaill'>请输入车牌号！</font>");
					return false;
				}
				if (form.find("select[name='carModelId']").val()=="") {
					$("#carModelIdErr").append("<font color='red' class='formtips onError emaill'>车牌号没有匹配到车辆型号！</font>");
					return false;
				}
				if (form.find("select[name='cityId']").val()=="") {
					$("#cityIdErr").append("<font color='red' class='formtips onError emaill'>车牌号没有匹配到城市！</font>");
					return false;
				}
				if (form.find("input[name='recordAccidentTime']").val()=="") {
					$("#recordAccidentTimeErr").append("<font color='red' class='formtips onError emaill'>请选择事故时间！</font>");
					return false;
				}
				if (form.find("input[name='accidentLocation']").val()=="") {
					$("#accidentLocationErr").append("<font color='red' class='formtips onError emaill'>请输入事故地点！</font>");
					return false;
				}
				if (form.find("select[name='accidentLevel']").val()=="") {
					$("#accidentLevelErr").append("<font color='red' class='formtips onError emaill'>请选择事故等级！</font>");
					return false;
				}
				if (form.find("select[name='insuranceCompany']").val()=="") {
					$("#insuranceCompanyErr").append("<font color='red' class='formtips onError emaill'>请选择保险公司！</font>");
					return false;
				}
				if (form.find("select[name='accidentStatus']").val()=="") {
					$("#accidentStatusErr").append("<font color='red' class='formtips onError emaill'>请选择保险进度！</font>");
					return false;
				}
				if (form.find("select[name='useCarType']").val()=="") {
					$("#useCarTypeErr").append("<font color='red' class='formtips onError emaill'>请选择用车类型！</font>");
					return false;
				}
				if (form.find("input[name='documentNo']").val()=="") {
					$("#documentNoErr").append("<font color='red' class='formtips onError emaill'>请输入单据号！</font>");
					return false;
				}
				if (form.find("input[name='driverName']").val()=="") {
					$("#driverNameErr").append("<font color='red' class='formtips onError emaill'>请输入用车人！</font>");
					return false;
				}
			}
		});
	 }
	}
	return carAccidentAdd;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
