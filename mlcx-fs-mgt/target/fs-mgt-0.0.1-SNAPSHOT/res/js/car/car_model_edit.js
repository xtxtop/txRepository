define([],function() {	
	var carModelEdit = {
			appPath : getPath("app"),
			imgPath:getPath("img"),
			init : function() {
				//增加提交
				$("#saveCarModelEdit").click(function(){
					carModelEdit.saveCar();
				});
				$("#editCarPhotoUs").click(function(){
					$("#careditModalUs").modal("show");
				});
				//编辑图片回填
				$("#editCarPhotoBtnUs").click(function(){
					var form=$("form[name=carphotoFormEdUs]");
					var img=form.find("input[name=carPhotoUrl1]").val();
					if(img!=""){
						var form1=$("form[name=carModelEditForm]");
						form1.find("input[name=carPhotoUrl1]").val(img);
						form1.find("img[name=carPicUrlImg]").attr("src",carModelEdit.imgPath+"/"+img);
						$("#careditModalUs").modal("hide");
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
		 $("#carBrandIdEd").empty();
		 $("#carModelNameEd").empty();
		 $("#seatNumberEd").empty();
		 $("#boxTypeEd").empty();
		 $("#displacementEd").empty();
		// $("#bondEd").empty();
		 
		var reg =/^[1-9]\d*$/;
		var tel = /^\d{1,12}(\.\d{1,2})?$/; 
		var form = $("form[name=carModelEditForm]");
		var carBrandId=form.find("select[name=carBrandId]").val();
		var carModelName=$.trim(form.find("input[name=carModelName]").val());
		var seatNumber=form.find("input[name=seatNumber]").val();
		var boxType=form.find("input[name=boxType]").val();
		var displacement=form.find("input[name=displacement]").val();
		//var bond=form.find("input[name=bond]").val();
		if(carBrandId==""||carBrandId==null){
			$("#carBrandIdAdd").html("<font color='red'>暂无品牌，请先设置车辆品牌。</font>");
			return false;
		}
		
		if(carModelName==""){
			$("#carModelNameEd").html("<font color='red'>请输入车辆型号。</font>");
			return false;
		}
		if(seatNumber==""){
			$("#seatNumberEd").html("<font color='red'>请输入座位数。</font>");
			return false;
		}else if(!reg.test(seatNumber)){
			$("#seatNumberEd").html("<font color='red'>座位数只能是正整数。</font>");
		}
		if(boxType==""){
			$("#boxTypeEd").html("<font color='red'>请输入箱型。</font>");
			return false;
		}
		if(displacement==""){
			$("#displacementEd").html("<font color='red'>请输入排量。</font>");
			return false;
		}
		if(displacement==""){
			$("#displacementEd").html("<font color='red'>请输入排量。</font>");
			return false;
		}
		
		
		
//		if(bond==""){
//			$("#bondEd").html("<font color='red'>请输入保证金额。</font>");
//			return false;
//		}else if(!tel.test(bond)){
//			$("#bondEd").html("<font color='red'>只能是数字类型或小数位数最多2位。</font>");
//			return false;
//		}
		
		
		var carModelNameGd=form.find("input[name=carModelNameGd]").val();
		var carBrandIdGd=form.find("input[name=carBrandIdGd]").val();
		if(carBrandId==carBrandIdGd&&carModelName==carModelNameGd){
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
			
		}else{
			$.post("carModel/carModelNameCheck.do",{carBrandId:carBrandId,carModelName:carModelName},function(res)
					{
					
				if(res.code=="1"){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前品牌车辆已设置过型号，不能重复设置。");
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
	}
 return	carModelEdit;
});


