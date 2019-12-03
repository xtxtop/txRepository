define([],function() {	
	var carFaultEdit = {
			appPath : getPath("app"),
			init : function() {
				//详情关闭
				$("#closeCarFault").click(function(){
					closeTab("故障详情");
	            });
				//编辑页关闭
				$("#closeEditCarFault").click(function(){
					closeTabBT("故障编辑");
	            });
				//编辑提交
				$("#editCarFault").click(function(){
					carFaultEdit.editCar();
				});
				//选择日期后根据事故时间和车牌搜索订单和调度车辆及其他信息
				$("#recordFaultTime").change(function(){
					if($("#recordFaultTime").val()!=""){
						var form = $("form[name=carFaultEditForm]");
						if (form.find("input[name='carPlateNo']").val()=="") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入车牌号！");
							return false;
						}
						$.ajax({
							 type: "post",
				             url: carFaultEdit.appPath+"/carFault/toSearchCarFault.do",
				             data: {carPlateNo:form.find("input[name='carPlateNo']").val(),recordFaultTime:form.find("input[name='recordFaultTime']").val()},
				             success: function(res){
				            	 var carFault = res.data;
				            	 if(res.code=="1"){
				            		 form.find("input[name='carNo']").val(carFault.carNo);
				            		 form.find("select[name='carModelId'] option[value='"+carFault.carModelId+"']").attr("selected","selected");
				            		 form.find("input[name='carModelName']").val(carFault.carModelName);
				            		 form.find("select[name='cityId'] option[value='"+carFault.cityId+"']").attr("selected","selected");
				            		 form.find("input[name='cityName']").val(carFault.cityName);
				            		 form.find("select[name='useCarType'] option[value='"+carFault.useCarType+"']").attr("selected","selected");
				            		 form.find("input[name='documentNo']").val(carFault.documentNo);
				            		 form.find("input[name='driverId']").val(carFault.driverId);
				            		 form.find("input[name='driverName']").val(carFault.driverName);
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
				                 }
				             }
						});
					}
				});
			},
	 saveCar:function() {
		var form = $("form[name=carFaultAddForm]");
		form.ajaxSubmit({
			url : carFaultEdit.appPath + "/carFault/editCarFault.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "更新成功", function() {
						closeTab("故障编辑");
						$("#carFaultList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+ msg + "更新失败！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#carPlateNoAdd").empty();
				$("#recordFaultTimeAdd").empty();
				$("#faultLocationAdd").empty();
				$("#faultLevelAdd").empty();
				$("#processingStatusAdd").empty();
				$("#useCarTypeAdd").empty();
				$("#documentNoAdd").empty();
				$("#driverIdAdd").empty();
				$("#memoAdd").empty();
				
				var form=$("form[name=carFaultAddForm]");
	    		var carPlateNo=form.find("input[name=carPlateNo]").val();
				var recordFaultTime=form.find("input[name=recordFaultTime]").val();
				var faultLocation=form.find("input[name=faultLocation]").val();
				var faultLevel=form.find("select[name=faultLevel]").val();
				var processingStatus=form.find("select[name=processingStatus]").val();
				var useCarType=form.find("select[name=useCarType]").val();
				var documentNo=form.find("input[name=documentNo]").val();
				var driverId=form.find("select[name=driverId]").val();
				var memo=form.find("input[name=memo]").val();
				
				if(carPlateNo==""){
					$("#carPlateNoAdd").html("<font color='red'>车牌号不能为空！</font>");
					return false;
				}
				if(recordFaultTime==""){
					$("#recordFaultTimeAdd").html("<font color='red'>故障时间不能为空！</font>");
					return false;
				}
				if(faultLocation==""){
					$("#faultLocationAdd").html("<font color='red'>故障地点不能为空！</font>");
					return false;
				}
				if(faultLocation!=""&&faultLocation.length>50){
					$("#faultLocationAdd").html("<font color='red'>故障地点输入过长，不超过50！</font>");
					return false;
				}
				if(faultLevel==""){
					$("#faultLevelAdd").html("<font color='red'>请选择故障等级！</font>");
					return false;
				}
				if(processingStatus==""){
					$("#processingStatusAdd").html("<font color='red'>请选择处理状态！</font>");
					return false;
				}
				if(useCarType==""){
					$("#useCarTypeAdd").html("<font color='red'>请选择用车类型！</font>");
					return false;
				}
				if(documentNo==""){
					$("#documentNoAdd").html("<font color='red'>单据号不能为空！</font>");
					return false;
				}
				if(driverId==""){
					$("#driverIdAdd").html("<font color='red'>请选择用车人！</font>");
					return false;
				}
				/*if(memo==""){
					$("#memoAdd").html("<font color='red'>备注不能为空！</font>");
					return false;
				}*/
				
					
				
			}
		});
	},
	editCar:function() {
		debugger
		var form = $("form[name=carFaultEditForm]");
		form.ajaxSubmit({
			url : carFaultEdit.appPath + "/carFault/editCarFault.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("故障编辑");
						$("#carFaultList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#carPlateNoEdit").empty();
				$("#recordFaultTimeEdit").empty();
				$("#faultLocationEdit").empty();
				$("#faultLevelEdit").empty();
				$("#processingStatusEdit").empty();
				$("#useCarTypeEdit").empty();
				$("#documentNoEdit").empty();
				$("#driverIdEdit").empty();
				$("#memoEdit").empty();
				
				var form=$("form[name=carFaultEditForm]");
	    		var carPlateNo=form.find("input[name=carPlateNo]").val();
				var recordFaultTime=form.find("input[name=recordFaultTime]").val();
				var faultLocation=form.find("input[name=faultLocation]").val();
				var faultLevel=form.find("select[name=faultLevel]").val();
				var processingStatus=form.find("select[name=processingStatus]").val();
				var useCarType=form.find("select[name=useCarType]").val();
				var documentNo=form.find("input[name=documentNo]").val();
				var driverId=form.find("select[name=driverId]").val();
				var memo=form.find("input[name=memo]").val();
				
				if(carPlateNo==""){
					$("#carPlateNoEdit").html("<font color='red'>车牌号不能为空！</font>");
					return false;
				}
				if(recordFaultTime==""){
					$("#recordFaultTimeEdit").html("<font color='red'>故障时间不能为空！</font>");
					return false;
				}
				if(faultLocation==""){
					$("#faultLocationEdit").html("<font color='red'>故障地点不能为空！</font>");
					return false;
				}
				if(faultLocation!=""&&faultLocation.length>50){
					$("#faultLocationEdit").html("<font color='red'>故障地点输入过长，不超过50！</font>");
					return false;
				}
				if(faultLevel==""){
					$("#faultLevelEdit").html("<font color='red'>请选择故障等级！</font>");
					return false;
				}
				if(processingStatus==""){
					$("#processingStatusEdit").html("<font color='red'>请选择处理状态！</font>");
					return false;
				}
				if(useCarType==""){
					$("#useCarTypeEdit").html("<font color='red'>请选择用车类型！</font>");
					return false;
				}
				if(documentNo==""){
					$("#documentNoEdit").html("<font color='red'>单据号不能为空！</font>");
					return false;
				}
				if(driverId==""){
					$("#driverIdEdit").html("<font color='red'>请选择用车人！</font>");
					return false;
				}
				if(memo==""){
					$("#memoEdit").html("<font color='red'>备注不能为空！</font>");
					return false;
				}
			}
		});
	}
	}
 return	carFaultEdit;
});


