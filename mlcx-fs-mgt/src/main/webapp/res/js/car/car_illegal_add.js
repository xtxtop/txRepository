define(
[],
function() {
var carIllegalAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveAddCarIllegalAdd").click(function(){
			carIllegalAdd.addCarIllegal();
		});
		//返回
		$("#closeAddCarIllegalAdd").click(function(){
			closeTab("新增违章");
			$("#carIllegalList").DataTable().ajax.reload(function(){});
		});
		//选择日期后根据违章时间和车牌搜索订单和调度车辆及其他信息
		$("#illegalTime").change(function(){
			if($("#illegalTime").val()!=""){
				var form = $("form[name=carIllegalAddForm]");
				if (form.find("input[name='carPlateNo']").val()=="") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入车牌号！");
					return false;
				}
				$.ajax({
					 type: "post",
		             url: carIllegalAdd.appPath+"/carIllegal/toSearchCarIllegal.do",
		             data: {carPlateNo:form.find("input[name='carPlateNo']").val(),illegalTime:form.find("input[name='illegalTime']").val()},
		             success: function(res){
		            	 var carIlligal = res.data;
		            	 if(res.code=="1"){
		            		 form.find("input[name='carNo']").val(carIlligal.carNo);
//		            		 form.find("select[name='carModelId'] option[value='"+carIlligal.carModelId+"']").attr("selected","selected");
//		            		 form.find("input[name='carModelName']").val(carIlligal.carModelName);
		            		 form.find("select[name='cityId'] option[value='"+carIlligal.cityId+"']").attr("selected","selected");
		            		 form.find("input[name='cityName']").val(carIlligal.cityName);
		            		 form.find("select[name='useCarType'] option[value='"+carIlligal.useCarType+"']").attr("selected","selected");
		            		 form.find("input[name='documentNo']").val(carIlligal.documentNo);
		            		 form.find("input[name='driverId']").val(carIlligal.driverId);
		            		 form.find("input[name='driverName']").val(carIlligal.driverName);
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		                	 form[0].reset();
		                 }
		             }
				});
			}
		});
	},
	addCarIllegal:function() {
		var form = $("form[name=carIllegalAddForm]");
		form.ajaxSubmit({
			url : carIllegalAdd.appPath + "/carIllegal/addCarIllegal.do",
			type : "post",
			data:{timeIllegal:$("#illegalTime").val()},
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增违章");
						$("#carIllegalList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=carIllegalAddForm]");
				form.find("span[name=carPlateNoAdd]").empty();
				form.find("span[name=carModelIdAdd]").empty();
				form.find("span[name=cityIdAdd]").empty();
				form.find("span[name=illegalTimeAdd]").empty();
				form.find("span[name=illegalLocationAdd]").empty();
				form.find("span[name=illegalTypeAdd]").empty();
				form.find("span[name=illegalFinesAdd]").empty();
				form.find("span[name=processingStatusAdd]").empty();
				form.find("span[name=paymentStatusAdd]").empty();
				//form.find("span[name=useCarTypeAdd]").empty();
//				form.find("span[name=documentNoAdd]").empty();
//				form.find("span[name=driverNameAdd]").empty();
				form.find("span[name=processingAgency]").empty();
				form.find("span[name=pointsDeduction]").empty();
				form.find("span[name=illegalDetailAdd]").empty();
				
				if (form.find("input[name='carPlateNo']").val()=="") {
					form.find("span[name=carPlateNoAdd]").html("<font color='red' class='formtips onError emaill'>请输入车牌号！</font>");
					return false;
				}
//				if (form.find("select[name='carModelId']").val()=="") {
//					form.find("span[name=carModelIdAdd]").html("<font color='red' class='formtips onError emaill'>请选择车辆型号！</font>");
//					return false;
//				}
				if (form.find("select[name='cityId']").val()=="-1") {
					form.find("span[name=cityIdAdd]").html("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
					return false;
				}
				if (form.find("input[name='illegalTime']").val()=="") {
					form.find("span[name=illegalTimeAdd]").html("<font color='red' class='formtips onError emaill'>请选择违章时间！</font>");
					return false;
				}
				if (form.find("input[name='illegalLocation']").val()=="") {
					form.find("span[name=illegalLocationAdd]").html("<font color='red' class='formtips onError emaill'>请输入违章地点！</font>");
					return false;
				}
				if (form.find("select[name='illegalType']").val()=="") {
					form.find("span[name=illegalTypeAdd]").html("<font color='red' class='formtips onError emaill'>请选择违章类型！</font>");
					return false;
				}
				if (form.find("input[name='illegalFines']").val()=="") {
					form.find("span[name=illegalFinesAdd]").html("<font color='red' class='formtips onError emaill'>请输入罚款金额！</font>");
					return false;
				}else{
					var fines = form.find("input[name='illegalFines']").val();
					var reg = /^\d+(\.\d{1,2})?$/;
					if(!reg.test(fines)){
						form.find("span[name=illegalFinesAdd]").html("<font color='red' class='formtips onError emaill'>请输入数字，最多两位小数！</font>");
						return false;
					}
				}
				if (form.find("select[name='processingStatus']").val()=="") {
					form.find("span[name=processingStatusAdd]").html("<font color='red' class='formtips onError emaill'>请选择处理状态！</font>");
					return false;
				}
				if (form.find("select[name='paymentStatus']").val()=="") {
					form.find("span[name=paymentStatusAdd]").html("<font color='red' class='formtips onError emaill'>请选择缴费状态！</font>");
					return false;
				}
//				if (form.find("select[name='useCarType']").val()=="") {
//					form.find("span[name=useCarTypeAdd]").html("<font color='red' class='formtips onError emaill'>请选择用车类型！</font>");
//					return false;
//				}
//				if (form.find("input[name='documentNo']").val()=="") {
//					form.find("span[name=documentNoAdd]").html("<font color='red' class='formtips onError emaill'>请输入单据号！</font>");
//					return false;
//				}
//				if (form.find("input[name='driverName']").val()=="") {
//					form.find("span[name=driverNameAdd]").html("<font color='red' class='formtips onError emaill'>请输入用车人！</font>");
//					return false;
//				}
				if (form.find("input[name='pointsDeduction']").val()=="") {
					form.find("span[name=pointsDeductionAdd]").html("<font color='red' class='formtips onError emaill'>请输入扣除的分数！</font>");
					return false;
				}
				if (!/^(1[0-2]|[0-9])$/.test(form.find("input[name='pointsDeduction']").val())) {
					form.find("span[name=pointsDeductionAdd]").html("<font color='red' class='formtips onError emaill'>输入扣除的分数必须在0~12之间！</font>");
					return false;
				}else{
					form.find("span[name=pointsDeductionAdd]").html("");					
				}
				if (form.find("input[name='processingAgency']").val()=="") {
					form.find("span[name=processingAgency]").html("<font color='red' class='formtips onError emaill'>请输入处理机构！</font>");
					return false;
				}
				if (form.find("textarea[name='illegalDetail']").val()=="") {
					form.find("span[name=illegalDetailAdd]").html("<font color='red' class='formtips onError emaill'>请输入违章内容！</font>");
					return false;
				}
				
			}
		});
	 }
	}
	return carIllegalAdd;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
///** 
// * 设置select选中 
// * @param selectId select的id值 
// * @param checkValue 选中option的值 
//*/  
//function setSelectChecked(selectId, checkValue){  
//    var select = document.getElementById(selectId);  
//    for(var i=0; i<select.options.length; i++){  
//        if(select.options[i].innerHTML == checkValue){  
//            select.options[i].selected = true;  
//            break;  
//        }  
//    }  
//};  
