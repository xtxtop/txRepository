define([],function() {	
	var carModelAdd = {
			appPath : getPath("app"),
			imgPath:getPath("img"),
			init : function() {
				//增加提交
				$("#saveCarModel").click(function(){
					carModelAdd.saveCar();
				});
				$("#addCarModelUrl").click(function(){
					$("#carModelAddModel").modal("show");
				});
				//新增图片回填
				$("#addCarModelPhotoBtn").click(function(){
					var form=$("form[name=carModelPhotoAddForm]");
					var img=form.find("input[name=carModelPhotoUrl]").val();
					if(img!=""){
						var form1=$("form[name=carModelAddForm]");
						form1.find("input[name=carModelUrl]").val(img);
						form1.find("#carPicUrlImgAdd").css("background-image", "url(" + carModelAdd.imgPath + '/' + img + ")");
						$("#carModelAddModel").modal("hide");
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
					}
				});
				//关闭新增页
				$("#closeAddCarModel").click(function(){
					debugger
					closeTabBT("车型新增");
	            });
				
			},
	 saveCar:function() {
		 var form = $("form[name=carModelAddForm]");
		 form.find("span[name=carModelNameAdd]").html("");
		 form.find("span[name=carBrandIdAdd]").html("");
		 form.find("span[name=carSeriesIdAdd]").html("");
		 form.find("span[name=carPeriodIdAdd]").html("");
		 form.find("span[name=configModelAdd]").html("");
		 form.find("span[name=carTypeAdd]").html("");
		 form.find("span[name=boxTypeAdd]").html("");
		 form.find("span[name=seatNumberAdd]").html("");
		 form.find("span[name=gearBoxAdd]").html("");
		 form.find("span[name=displacementAdd]").html("");
		 form.find("span[name=tonsAdd]").html("");
		 form.find("span[name=carModelInfoAdd]").html("");
		 form.find("span[name=carModelUrlAdd]").html("");
		 
		 var carSeriesName=form.find("select[name=carSeriesId]").find("option:selected").text();
		 form.find("input[name=carSeriesName]").val(carSeriesName);
		
		 var carPeriodName=form.find("select[name=carPeriodId]").find("option:selected").text();
		 form.find("input[name=carPeriodName]").val(carPeriodName);
		
		 var carModelName = $.trim(form.find("input[name=carModelName]").val());
		 var carBrandId = $.trim(form.find("select[name=carBrandId]").val());
		 var carSeriesId = form.find("select[name=carSeriesId]").val();
		 var carPeriodId = form.find("select[name=carPeriodId]").val();
		 var configModel = $.trim(form.find("input[name=configModel]").val());
		 var carType = form.find("select[name=carType]").val();
		 var boxType = form.find("select[name=boxType]").val();
		 var seatNumber = form.find("select[name=seatNumber]").val();
		 var gearBox = form.find("select[name=gearBox]").val();
		 var displacement = $.trim(form.find("input[name=displacement]").val());
		 var tons = $.trim(form.find("input[name=tons]").val());
		 var carModelInfo = $.trim(form.find("input[name=carModelInfo]").val());
		 var carModelUrl = $.trim(form.find("input[name=carModelUrl]").val());
		
		 if(carModelName==""){
			 form.find("span[name=carModelNameAdd]").html("<font color='red'>请先设置车型名称！</font>");
			 return false;
		 }
		 if(carBrandId==""){
			 form.find("span[name=carBrandIdAdd]").html("<font color='red'>请先设置车辆品牌！</font>");
			 return false;
		 }
		 if(carSeriesId==""){
			 form.find("span[name=carSeriesIdAdd]").html("<font color='red'>请先设置车型！</font>");
			 return false;
		 }
		 if(carPeriodId==""){
			 form.find("span[name=carPeriodIdAdd]").html("<font color='red'>请先设置车辆年代！</font>");
			 return false;
		 }
		 if(configModel==""){
			 form.find("span[name=configModelAdd]").html("<font color='red'>请输入车辆配置！</font>");
			 return false;
		 }
		 if(carType==""){
			 form.find("span[name=carTypeAdd]").html("<font color='red'>请先配置车辆适用类型！</font>");
			 return false;
		 }
		 if(boxType==""){
			 form.find("span[name=boxTypeAdd]").html("<font color='red'>请先配置车辆箱型！</font>");
			 return false;
		 }
		 if(seatNumber==""){
			 form.find("span[name=seatNumberAdd]").html("<font color='red'>请输入座位数！</font>");
			 return false;
		 }
		 if(gearBox==""){
			 form.find("span[name=gearBoxAdd]").html("<font color='red'>请选择变速箱！</font>");
			 return false;
		 }
		 if(displacement==""){
			 form.find("span[name=displacementAdd]").html("<font color='red'>请输入排量！</font>");
			 return false;
		 }
		 if(tons==""){
			 form.find("span[name=tonsAdd]").html("<font color='red'>请输入吨位！</font>");
			 return false;
		 }
		 if(carModelInfo==""){
			 form.find("span[name=carModelInfoAdd]").html("<font color='red'>请输入车型描述！</font>");
			 return false;
		 }
		 if(carModelUrl==""){
			 form.find("span[name=carModelUrlAdd]").html("<font color='red'>请上传车辆照片！</font>");
			 return false;
		 }else{
			 form.find("span[name=carModelUrlAdd]").html("");
		 }
		
		 $.post("carModel/carModelNameCheck.do",{carBrandId:carBrandId,carSeriesId:carSeriesId,carPeriodId:carPeriodId,carType:carType,carModelName:carModelName},
			function(res){
				if(res.code=="1"){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前品牌车辆已设置过型号，不能重复设置！");
					return false;
				}else if(res.code=="2"){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车辆型号名称重复");
					return false;
				}else{
					form.ajaxSubmit({
						url : carModelAdd.appPath + "/carModel/editCarModel.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增成功", function() {
									closeTab("车型新增");
									addTab("车辆型号列表",carModelAdd.appPath+ "/carModel/toCarModelList.do");
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增失败！");
							}
						},
						
					});		
				}
			});
	 	}
	}
 return	carModelAdd;
});
//获取指定的select选中的标签的内部值赋给指定的input
function selectBrandValueAdd (selectId){
	var carBrandId = $("#"+selectId+"").find("option:selected").val();
	var carBrandName = $("#"+selectId+"").find("option:selected").text();
	var form = $("form[name=carModelAddForm]");
	form.find("input[name=carBrandName]").val($.trim(carBrandName));
	if(carBrandId!=""){
		$.post("carPreiod/getCarSeriesByBrandId.do", {carBrandId:carBrandId}, 
			function(res){ 
				var msg = res.msg;
				var code = res.code;
				var carSeriesList = res.data;
				if(code == "1"){ 
					form.find("select[name=carSeriesId]").html("");
					form.find("select[name=carPeriodId]").html("");
					var option="<option value=''>全部</option>";
                    for(var i=0;i<carSeriesList.length;i++){
                    	var carSeries = carSeriesList[i];
                    	if(i==0){
                    		option+="<option selected=selected value='"+carSeries.carSeriesId+"'> "+carSeries.carSeriesName+" </option>";
                    	}else{
                    		option+="<option value='"+carSeries.carSeriesId+"'> "+carSeries.carSeriesName+" </option>";
                    	}
          			}
                    form.find("select[name=carSeriesId]").html(option);
                    selectSeriesValueAdd('carModelSeriesIdAdd');
				}else{
					form.find("select[name=carSeriesId]").html("");
					form.find("select[name=carPeriodId]").html("");
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该品牌下暂无车系！");
				}
		},"json")
	}
}
//获取指定的select选中的标签的内部值赋给指定的input
function selectSeriesValueAdd (selectId){
	var carSeriesId = $("#"+selectId+"").find("option:selected").val();
	var carSeriesName = $("#"+selectId+"").find("option:selected").text();
	var form = $("form[name=carModelAddForm]");
	form.find("input[name=carSeriesName]").val(carSeriesName);
	if(carSeriesId!=""){
		$.post("carPreiod/getCarPeriodsBySeriesId.do", {carSeriesId:carSeriesId}, 
			function(res){ 
				var msg = res.msg;
				var code = res.code;
				var carPeriodList = res.data;
				if(code == "1"){ 
					form.find("select[name=carPeriodId]").html("");
					var option="<option value=''>全部</option>";
                    for(var i=0;i<carPeriodList.length;i++){
                    	var carPeriod = carPeriodList[i];
                    	if(i==0){
                    		option+="<option selected=selected value='"+carPeriod.carPeriodId+"'> "+carPeriod.carPeriodName+" </option>";
                    	}else{
                    		option+="<option value='"+carPeriod.carPeriodId+"'> "+carPeriod.carPeriodName+" </option>";
                    	}
          			}
                    form.find("select[name=carPeriodId]").html(option);
				}else{
					form.find("select[name=carPeriodId]").html("");
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车系下暂无车辆年代！");
				}
		},"json")
	}
}


