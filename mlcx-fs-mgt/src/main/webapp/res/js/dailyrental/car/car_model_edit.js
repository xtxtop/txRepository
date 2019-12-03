define([],function() {	
	var carModelEdit = {
			appPath:getPath("app"),
			imgPath:getPath("img"),
			init : function() {
				//增加提交
				$("#saveCarModelEdit").click(function(){
					carModelEdit.saveCar();
				});
				$("#editCarModelPhoto").click(function(){
					$("#carModeleditModal").modal("show");
				});
				//编辑图片回填
				$("#editCarPhotoBtn").click(function(){
					var form=$("form[name=carModelPhotoFormEdit]");
					var img=form.find("input[name=carPhotoUrl]").val();
					if(img!=""){
						var form1=$("form[name=carModelEditForm]");
						form1.find("input[name=carModelUrl]").val(img);
						form1.find("#carPicUrlImg").css("background-image", "url(" + carModelEdit.imgPath + '/' + img + ")");
						$("#carModeleditModal").modal("hide");
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
					}
				});
				//关闭新增故障页
				$("#closeEditCarModel").click(function(){
					closeTabBT("车型编辑");
	            });
				
			},
	 saveCar:function() {
		 var form = $("form[name=carModelEditForm]");
		 form.find("span[name=carModelNameEdit]").html("");
		 form.find("span[name=carBrandIdEdit]").html("");
		 form.find("span[name=carSeriesIdEdit]").html("");
		 form.find("span[name=carPeriodIdEdit]").html("");
		 form.find("span[name=configModelEdit]").html("");
		 form.find("span[name=carTypeEdit]").html("");
		 form.find("span[name=boxTypeEdit]").html("");
		 form.find("span[name=seatNumberEdit]").html("");
		 form.find("span[name=gearBoxEdit]").html("");
		 form.find("span[name=displacementEdit]").html("");
		 form.find("span[name=tonsEdit]").html("");
		 form.find("span[name=carModelInfoEdit]").html("");
		 form.find("span[name=carModelUrlEdit]").html("");
		 
		 var carSeriesName=form.find("select[name=carSeriesId]").find("option:selected").text();
		 form.find("input[name=carSeriesName]").val(carSeriesName);
		
		 var carPeriodName=form.find("select[name=carPeriodId]").find("option:selected").text();
		 form.find("input[name=carPeriodName]").val(carPeriodName);
		 var carModelId = form.find("input[name=carModelId]").val();
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
			 form.find("span[name=carModelNameEdit]").html("<font color='red'>请先设置车型名称！</font>");
			 return false;
		 }
		 if(carBrandId==""){
			 form.find("span[name=carBrandIdEdit]").html("<font color='red'>请先设置车辆品牌！</font>");
			 return false;
		 }
		 if(carSeriesId==""){
			 form.find("span[name=carSeriesIdEdit]").html("<font color='red'>请先设置车型！</font>");
			 return false;
		 }
		 if(carPeriodId==""){
			 form.find("span[name=carPeriodIdEdit]").html("<font color='red'>请先设置车辆年代！</font>");
			 return false;
		 }
		 if(configModel==""){
			 form.find("span[name=configModelEdit]").html("<font color='red'>请输入车辆配置！</font>");
			 return false;
		 }
		 if(carType==""){
			 form.find("span[name=carTypeEdit]").html("<font color='red'>请先配置车辆适用类型！</font>");
			 return false;
		 }
		 if(boxType==""){
			 form.find("span[name=boxTypeEdit]").html("<font color='red'>请先配置车辆箱型！</font>");
			 return false;
		 }
		 if(seatNumber==""){
			 form.find("span[name=seatNumberEdit]").html("<font color='red'>请输入座位数！</font>");
			 return false;
		 }
		 if(gearBox==""){
			 form.find("span[name=gearBoxEdit]").html("<font color='red'>请选择变速箱！</font>");
			 return false;
		 }
		 if(displacement==""){
			 form.find("span[name=displacementEdit]").html("<font color='red'>请输入排量！</font>");
			 return false;
		 }
		 if(tons==""){
			 form.find("span[name=tonsEdit]").html("<font color='red'>请输入吨位！</font>");
			 return false;
		 }
		 if(carModelInfo==""){
			 form.find("span[name=carModelInfoEdit]").html("<font color='red'>请输入车型描述！</font>");
			 return false;
		 }
		 if(carModelUrl==""){
			 form.find("span[name=carModelUrlEdit]").html("<font color='red'>请上传车辆照片！</font>");
			 return false;
		 }else{
			 form.find("span[name=carModelUrlEdit]").html("");
		 }
		 
		 $.post("carModel/carModelNameCheck.do",{carModelId:carModelId,carModelName:carModelName,carBrandId:carBrandId,carSeriesId:carSeriesId,carPeriodId:carPeriodId,carType:carType},
			function(res){
				if(res.code=="1"){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前品牌车辆已设置过型号，不能重复设置。");
					return false;
				}else if(res.code=="2"){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车辆型号名称重复");
					return false;
				}else{
					form.ajaxSubmit({
						url : carModelEdit.appPath + "/carModel/editCarModel.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
									closeTab("车型编辑");
									addTab("车辆型号列表",carModelEdit.appPath+ "/carModel/toCarModelList.do");
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
							}
						},
						
					});		
				}
			});
		
	 }
   }
 return	carModelEdit;
});
//获取指定的select选中的标签的内部值赋给指定的input
function selectBrandValueEdit (selectId){
	var carBrandId = $("#"+selectId+"").find("option:selected").val();
	var carBrandName = $("#"+selectId+"").find("option:selected").text();
	var form = $("form[name=carModelEditForm]");
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
                    selectSeriesValueEdit('carModelSeriesIdEdit');
				}else{
					form.find("select[name=carSeriesId]").html("");
					form.find("select[name=carPeriodId]").html("");
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该品牌下暂无车系！");
				}
		},"json")
	}
}
//获取指定的select选中的标签的内部值赋给指定的input
function selectSeriesValueEdit (selectId){
	var carSeriesId = $("#"+selectId+"").find("option:selected").val();
	var carSeriesName = $("#"+selectId+"").find("option:selected").text();
	var form = $("form[name=carModelEditForm]");
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

