define(
[],
function() {
var carIllegalEdit = {
	appPath : getPath("app"),
	init : function() {
		//编辑提交
		$("#saveCarIllegalEdit").click(function(){
			carIllegalEdit.editCarIllegalEdit();
		});
		//返回
		$("#closeCarIllegalEdit").click(function(){
			closeTab("违章编辑");
			$("#carIllegalList").DataTable().ajax.reload(function(){});
		});
		//详情返回
		$("#closeCarIllegalVeiw").click(function(){
			closeTab("违章详情");
			$("#carIllegalList").DataTable().ajax.reload(function(){});
		});
		//选择日期后根据事故时间和车牌搜索订单或调度车辆及其他信息
		$("#recordAccidentTime").change(function(){
			if($("#recordAccidentTime").val()!=""){
				var form = $("form[name=carIllegalEditForm]");
				if (form.find("input[name='carPlateNo']").val()=="") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入车牌号！");
					return false;
				}
				$.ajax({
					 type: "post",
		             url: carIllegalEdit.appPath+"/carIllegal/toSearchCarIllegal.do",
		             data: {carPlateNo:form.find("input[name='carPlateNo']").val(),illegalTime:$("#recordAccidentTime").val()},
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
		                	 form[0].reset();
		                 }
		             }
				});
			}
		});
	},
	editCarIllegalEdit:function() {
		var form = $("form[name=carIllegalEditForm]");
		form.ajaxSubmit({
			url : carIllegalEdit.appPath + "/carIllegal/updateCarIllegal.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("违章编辑");
						$("#carIllegalList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=carIllegalEditForm]");
				form.find("span[name=carPlateNoEdit]").empty();
				//form.find("span[name=carModelNameEdit]").empty();
				form.find("span[name=cityName]").empty();
				form.find("span[name=illegalTimeEdit]").empty();
				form.find("span[name=illegalLocationEdit]").empty();
				form.find("span[name=illegalTypeEdit]").empty();
				form.find("span[name=illegalFinesEdit]").empty();
				form.find("span[name=processingStatusEdit]").empty();
				form.find("span[name=paymentStatusEdit]").empty();
				form.find("span[name=useCarTypeEdit]").empty();
				form.find("span[name=documentNoEdit]").empty();
				form.find("span[name=driverNameEdit]").empty();
				form.find("span[name=processingAgency]").empty();
				form.find("span[name=pointsDeduction]").empty();
				form.find("span[name=illegalDetailEdit]").empty();

				if (form.find("input[name='carPlateNo']").val()=="") {
					form.find("span[name=carPlateNoEdit]").html("<font color='red' class='formtips onError emaill'>输入车牌号没有匹配到城市！</font>");
					return false;
				}
//				if (form.find("input[name='carModelName']").val()=="") {
//					form.find("span[name=carModelNameEdit]").html("<font color='red' class='formtips onError emaill'>输入车牌号没有匹配到车型！</font>");
//					return false;
//				}
				if (form.find("input[name='cityName']").val()=="") {
					form.find("span[name=cityNameEdit]").html("<font color='red' class='formtips onError emaill'>请输入车牌号！</font>");
					return false;
				}
				if (form.find("input[name='illegalTime']").val()=="") {
					form.find("span[name=illegalTimeEdit]").html("<font color='red' class='formtips onError emaill'>请选择违章时间！</font>");
					return false;
				}
				if (form.find("input[name='illegalLocation']").val()=="") {
					form.find("span[name=illegalLocationEdit]").html("<font color='red' class='formtips onError emaill'>请驶入违章地点！</font>");
					return false;
				}
				if (form.find("select[name='illegalType']").val()=="") {
					form.find("span[name=illegalTypeEdit]").html("<font color='red' class='formtips onError emaill'>请选择违章类型！</font>");
					return false;
				}
				if (form.find("input[name='illegalFines']").val()=="") {
					form.find("span[name=illegalFinesEdit]").html("<font color='red' class='formtips onError emaill'>请输入罚款金额！</font>");
					return false;
				}else{
					var fines = form.find("input[name='illegalFines']").val();
					var reg = /^\d+(\.\d{1,2})?$/;
					if(!reg.test(fines)){
						form.find("span[name=illegalFinesEdit]").html("<font color='red' class='formtips onError emaill'>请输入数字，最多两位小数！</font>");
						return false;
					}
				}
				if (form.find("select[name='processingStatus']").val()=="") {
					form.find("span[name=processingStatusEdit]").html("<font color='red' class='formtips onError emaill'>请选择处理状态！</font>");
					return false;
				}
				if (form.find("select[name='paymentStatus']").val()=="") {
					form.find("span[name=paymentStatusEdit]").html("<font color='red' class='formtips onError emaill'>请选择缴费状态！</font>");
					return false;
				}
//				if (form.find("select[name='useCarType']").val()=="") {
//					form.find("span[name=useCarTypeEdit]").html("<font color='red' class='formtips onError emaill'>请选择用车类型！</font>");
//					return false;
//				}
//				if (form.find("input[name='documentNo']").val()=="") {
//					form.find("span[name=documentNoEdit]").html("<font color='red' class='formtips onError emaill'>请输入单据号！</font>");
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
					form.find("span[name=illegalDetailEdit]").html("<font color='red' class='formtips onError emaill'>请输入违章内容！</font>");
					return false;
				}
			}
		});
	 }
	}
	return carIllegalEdit;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
