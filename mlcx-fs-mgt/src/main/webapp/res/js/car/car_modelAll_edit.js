define([],function() {	
	var carModelAllEdit = {
	appPath : getPath("app"),
	imgPath : getPath("img"),
	init : function() {
		//车系增加提交
		$("#editCarModelAll").click(function(){
			carModelAllEdit.saveModelAll();
		});
		//关闭车系新增页
		$("#closeCarModelAllEdit").click(function(){
			closeTabBT("车型编辑");
        });
		$("#editCarModelAllPhoto").click(function(){
			$("#carModelAllEditModal").modal("show");
		});
		//新增图片回填
		$("#editCarModelAllPhotoBtn").click(function(){
			var form=$("form[name=carModelAllPhotoEditForm]");
			var img=form.find("input[name=carSeriesPhotoUrl1Edit]").val();
			if(img!=""){
				var formUpload=$("form[name=carModelAllEditForm]");
				formUpload.find("input[name=carSeriresPic]").val(img);
				formUpload.find("img[name=carSeriesPicUrlImg]").attr("src",carModelAllEdit.imgPath+"/"+img);
				$("#carModelAllEditModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	saveModelAll:function() {
		 var form = $("form[name=carModelAllEditForm]");
		 form.find("span[name=carSeriesNameEdit]").html("");
		 form.find("span[name=carBrandIdEdit]").html("");
		 form.find("span[name=carSeriesPicEdit]").html("");
		
		 var carSeriesId=form.find("input[name=carSeriesId]").val();
		
		 var carSeriesName=form.find("input[name=carSeriesName]").val();
		 var carBrandId=form.find("select[name=carBrandId]").val();
		 var carSeriesPic=form.find("input[name=carSeriesPic]").val();
		 if(carSeriesName==""){
			 form.find("span[name=carSeriesNameEdit]").html("<font color='red'>请输入车系名称！</font>");
			 return false;
		 }else if(carBrandId==""){
			 form.find("span[name=carBrandIdEdit]").html("<font color='red'>请选择车辆品牌！</font>");
			 return false;
		 }else if(carSeriesPic==""){
			 form.find("span[name=carSeriesPicEdit]").html("<font color='red'>请上传车系图标！</font>");
			 return false;
		 }else{
			 $.get("carModelAll/carModelAllNameCheck.do",{carSeriesName:carSeriesName,carSeriesId:carSeriesId},function(res)
		 {
			if(res.code=="1"){
				form.find("span[name=carSeriesNameEdit]").html("<font color='red'>车型名称已存在，请重新输入。</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : carModelAllEdit.appPath + "/carModelAll/editCarModelAll.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
								closeTabBT("车型编辑");
								addTab("车型列表",carModelAllEdit.appPath+ "/carModelAll/toCarModelList.do");
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
 return	carModelAllEdit;
});


