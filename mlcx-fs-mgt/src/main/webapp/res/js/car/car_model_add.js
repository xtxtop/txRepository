define([],function() {	
	var carModelAdd = {
			appPath : getPath("app"),
			imgPath:getPath("img"),
			init : function() {
				//增加提交
				$("#saveCarModel").click(function(){
					carModelAdd.saveCar();
				});
				$("#addCarPhotoUs").click(function(){
					$("#carAddModalUs").modal("show");
				});
				//新增图片回填
				$("#addCarPhotoBtnUs").click(function(){
					var form=$("form[name=carphotoFormUs]");
					var img=form.find("input[name=carPhotoUrl1]").val();
					if(img!=""){
						var form1=$("form[name=carModelAddForm]");
						form1.find("input[name=carPhotoUrl1]").val(img);
						form1.find("img[name=carPicUrlImg]").attr("src",carModelAdd.imgPath+"/"+img);
						$("#carAddModalUs").modal("hide");
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
					}
				});
				//关闭新增页
				$("#closeAddCarModel").click(function(){
					closeTabBT("车型新增");
	            });
				
			},
	 saveCar:function() {
		 $("#carBrandIdAdd").empty();
		 $("#carModelNameAdd").empty();
		 $("#seatNumberAdd").empty();
		 $("#boxTypeAdd").empty();
		 $("#displacementAdd").empty();
		 //$("#bondAdd").empty();
		 
		var reg =/^[1-9]\d*$/;
		var tel = /^\d{1,12}(\.\d{1,2})?$/; 
		var form = $("form[name=carModelAddForm]");
		var carBrandId=form.find("select[name=carBrandId]").val();
		var carModelName=form.find("input[name=carModelName]").val();
		var seatNumber=form.find("input[name=seatNumber]").val();
		var boxType=form.find("input[name=boxType]").val();
		var displacement=form.find("input[name=displacement]").val();
		var carPhotoUrl1=form.find("input[name=carPhotoUrl1]").val();
		if(carBrandId==""||carBrandId==null){
			$("#carBrandIdAdd").html("<font color='red'>暂无品牌，请先设置车辆品牌。</font>");
			return false;
		}
		
		if(carModelName==""){
			$("#carModelNameAdd").html("<font color='red'>请输入车辆型号。</font>");
			return false;
		}
		if(seatNumber==""){
			$("#seatNumberAdd").html("<font color='red'>请输入座位数。</font>");
			return false;
		}else if(!reg.test(seatNumber)){
			$("#seatNumberAdd").html("<font color='red'>座位数只能是正整数。</font>");
		}
		if(boxType==""){
			$("#boxTypeAdd").html("<font color='red'>请输入箱型。</font>");
			return false;
		}
		if(displacement==""){
			$("#displacementAdd").html("<font color='red'>请输入排量。</font>");
			return false;
		}
		if(displacement==""){
			$("#displacementAdd").html("<font color='red'>请输入排量。</font>");
			return false;
		}
		
		if(carPhotoUrl1==""||carPhotoUrl1==null){
			$("span[name=carPhotoUrl1]").html("<font color='red'>请上传车辆照片。</font>");
			return false;
		}else{
			$("span[name=carPhotoUrl1]").html("");
		}
		
//		if(bond==""){
//			$("#bondAdd").html("<font color='red'>请输入保证金额。</font>");
//			return false;
//		}else if(!tel.test(bond)){
//			$("#bondAdd").html("<font color='red'>只能是数字类型或小数位数最多2位。</font>");
//			return false;
//		}
		
		$.post("carModel/carModelNameCheck.do",{carBrandId:carBrandId,carModelName:carModelName},function(res)
				{
				
			if(res.code=="1"){
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前品牌车辆已设置过型号，不能重复设置。");
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


