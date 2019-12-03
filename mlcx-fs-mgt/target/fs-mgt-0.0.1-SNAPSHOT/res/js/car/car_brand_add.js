define([],function() {	
	var carBrandAdd = {
	appPath : getPath("app"),
	imgPath : getPath("img"),
	init : function() {
		//品牌增加提交
		$("#addCarBrandAdd").click(function(){
			carBrandAdd.saveCarBrand();
		});
		//关闭品牌新增页
		$("#closeCarBrandAdd").click(function(){
			closeTabBT("新增品牌");
        });
		$("#addCarBrandPhoto").click(function(){
			$("#carBrandAddModal").modal("show");
		});
		//新增图片回填
		$("#addCarBrandPhotoBtn").click(function(){
			var form=$("form[name=carBrandPhotoForm]");
			var img=form.find("input[name=carBrandPhotoUrl1]").val();
			if(img!=""){
				var formUpload=$("form[name=carBrandAddForm]");
				formUpload.find("input[name=logoPic]").val(img);
//				formUpload.find("img[name=carBrandPicUrlImg]").attr("src",carBrandAdd.imgPath+"/"+img);
				formUpload.find("#carBrandPicUrlImg").css("background-image","url("+carBrandAdd.imgPath+'/'+img+")");
				$("#carBrandAddModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	 saveCarBrand:function() {
		 var form = $("form[name=carBrandAddForm]");
		 form.find("span[name=carBrandNameAdd]").html("");
		 form.find("span[name=logoPicAdd]").html("");
		 form.find("span[name=brandShortNameAdd]").html("");
		 form.find("span[name=engNameAdd]").html("");
		 form.find("span[name=webSiteAdd]").html("");
		 form.find("span[name=owerCompanyAdd]").html("");
		 form.find("span[name=companyAddrAdd]").html("");
		 form.find("span[name=brandInfoAdd]").html("");
		 
		var carBrandName = $.trim(form.find("input[name=carBrandName]").val());
		var logoPic = $.trim(form.find("input[name=logoPic]").val());
		var brandShortName = $.trim(form.find("input[name=brandShortName]").val());
		var engName = $.trim(form.find("input[name=engName]").val());
		var webSite = $.trim(form.find("input[name=webSite]").val());
		var owerCompany = $.trim(form.find("input[name=owerCompany]").val());
		var companyAddr = $.trim(form.find("input[name=companyAddr]").val());
		var brandInfo = $.trim(form.find("input[name=brandInfo]").val());
		if(carBrandName==""){
			form.find("span[name=carBrandNameAdd]").html("<font color='red'>请输入车辆品牌！</font>");
			return false;
		}else if(logoPic==""){
			form.find("span[name=logoPicAdd]").html("<font color='red'>请上传品牌logo！</font>");
			return false;
		}
//		else if(brandShortName==""){
//			form.find("span[name=brandShortNameAdd]").html("<font color='red'>请填写品牌简称！</font>");
//			return false;
//		}
//		else if(engName==""){
//			 form.find("span[name=engNameAdd]").html("<font color='red'>请填写品牌英文名称！</font>");
//			return false;
//		}else if(webSite==""){
//			form.find("span[name=webSiteAdd]").html("<font color='red'>请填写品牌官网地址！</font>");
//			return false;
//		}else if(owerCompany==""){
//			form.find("span[name=owerCompanyAdd]").html("<font color='red'>请填写所属公司！</font>");
//			return false;
//		}else if(companyAddr==""){
//			form.find("span[name=companyAddrAdd]").html("<font color='red'>请填写公司地址！</font>");
//			return false;
//		}else if(brandInfo==""){
//			form.find("span[name=brandInfoAdd]").html("<font color='red'>请填写品牌介绍！</font>");
//			return false;
//		}
		else{
			$.get("carBrand/carBrandNameCheck.do",{carBrandName:carBrandName},function(res)
		{
			if(res.code=="1"){
				form.find("span[name=carBrandNameAdd]").html("<font color='red'>车辆品牌已存在，请重新输入。</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : carBrandAdd.appPath + "/carBrand/editCarBrand.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增成功", function() {
								closeTab("新增品牌");
//								addTab("车辆品牌列表",carBrandAdd.appPath+ "/carBrand/toCarBrandList.do");
								$("#carBrandList").DataTable().ajax.reload(function(){});
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
	}
 return	carBrandAdd;
});


