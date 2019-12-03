define([],function() {	
	var carBrandEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//提交
			$("#carBrandEdit").click(function(){
				debugger
				carBrandEdit.saveEditCar();
			});
			//关闭新增故障页
			$("#closeCarBrandEdit").click(function(){
				closeTabBT("品牌编辑");
            });
			$("#editCarBrandPhoto").click(function(){
				$("#carBrandEditModal").modal("show");
			});
			//新增图片回填
			$("#editCarBrandPhotoBtn").click(function(){
				var form=$("form[name=carBrandPhotoEditForm]");
				var img=form.find("input[name=carBrandPhotoUrl1Edit]").val();
				if(img!=""){
					var formUpload=$("form[name=carBrandEditForm]");
					formUpload.find("input[name=logoPic]").val(img);
					formUpload.find("#carBrandPicUrlImgEdit").css("background-image","url("+carBrandEdit.imgPath+'/'+img+")");
					$("#carBrandEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
		},
		saveEditCar:function() {
			debugger
			 var form = $("form[name=carBrandEditForm]");
			 form.find("span[name=carBrandNameEdit]").html("");
			 form.find("span[name=logoPicEdit]").html("");
			 form.find("span[name=brandShortNameEdit]").html("");
			 form.find("span[name=engNameEdit]").html("");
			 form.find("span[name=webSiteEdit]").html("");
			 form.find("span[name=owerCompanyEdit]").html("");
			 form.find("span[name=companyAddrEdit]").html("");
			 form.find("span[name=brandInfoEdit]").html("");
			 
			var carBrandId=form.find("input[name=carBrandId]").val();
			
			var carBrandName=form.find("input[name=carBrandName]").val();
			var logoPic=form.find("input[name=logoPic]").val();
			var brandShortName=form.find("input[name=brandShortName]").val();
			var engName=form.find("input[name=engName]").val();
			var webSite=form.find("input[name=webSite]").val();
			var owerCompany=form.find("input[name=owerCompany]").val();
			var companyAddr=form.find("input[name=companyAddr]").val();
			var brandInfo=form.find("input[name=brandInfo]").val();
			if(carBrandName==""){
				form.find("span[name=carBrandNameEdit]").html("<font color='red'>请输入车辆品牌！</font>");
				return false;
			}else if(logoPic==""){
				form.find("span[name=logoPicEdit]").html("<font color='red'>请上传品牌logo！</font>");
				return false;
			}
//			else if(brandShortName==""){
//				form.find("span[name=brandShortNameEdit]").html("<font color='red'>请填写品牌简称！</font>");
//				return false;
//			}else if(engName==""){
//				form.find("span[name=engNameEdit]").html("<font color='red'>请填写品牌英文名称！</font>");
//				return false;
//			}else if(webSite==""){
//				form.find("span[name=webSiteEdit]").html("<font color='red'>请填写品牌官网地址！</font>");
//				return false;
//			}else if(owerCompany==""){
//				form.find("span[name=owerCompanyEdit]").html("<font color='red'>请填写所属公司！</font>");
//				return false;
//			}else if(companyAddr==""){
//				form.find("span[name=companyAddrEdit]").html("<font color='red'>请填写公司地址！</font>");
//				return false;
//			}else if(brandInfo==""){
//				form.find("span[name=brandInfoEdit]").html("<font color='red'>请填写品牌介绍！</font>");
//				return false;
//			}
			else{
				$.get("carBrand/carBrandNameCheck.do",{carBrandName:carBrandName,carBrandId:carBrandId},function(res)
			{
				if(res.code=="1"){
					form.find("span[name=carBrandNameEdit]").html("<font color='red'>车辆品牌已存在，请重新输入。</font>");
					return false;
				}else{
					form.ajaxSubmit({
						url : carBrandEdit.appPath + "/carBrand/editCarBrand.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
									closeTab("编辑品牌");
//									addTab("车辆品牌列表",carBrandEdit.appPath+ "/carBrand/toCarBrandList.do");
									$("#carBrandList").DataTable().ajax.reload(function(){});
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
 return	carBrandEdit;
});


