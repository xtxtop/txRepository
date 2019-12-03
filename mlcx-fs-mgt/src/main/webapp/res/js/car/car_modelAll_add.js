define([],function() {	
	var carModelAllAdd = {
	appPath : getPath("app"),
	imgPath : getPath("img"),
	init : function() {
		//车系增加提交
		$("#addCarModelAll").click(function(){
			carModelAllAdd.saveCarModelAll();
		});
		//关闭车系新增页
		$("#closeCarModelAllAdd").click(function(){
			closeTabBT("新增车型");
        });
		$("#addCarModelAllPhoto").click(function(){
			$("#carModelAllAdd").modal("show");
		});
		//新增图片回填
		$("#addCarModelAllPhotoBtn").click(function(){
			var form=$("form[name=carModelAllPhotoForm]");
			var img=form.find("input[name=carSeriesPhotoUrl1]").val();
			if(img!=""){
				var formUpload=$("form[name=carModelAllAddForm]");
				formUpload.find("input[name=carSeriresPic]").val(img);
				formUpload.find("img[name=carSeriesPicUrlImg]").attr("src",carModelAllAdd.imgPath+"/"+img);
				$("#carModelAllAdd").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	saveCarModelAll:function() {
		 var form = $("form[name=carModelAllAddForm]");
		 form.find("span[name=carSeriesNameAdd]").html("");
		 form.find("span[name=carBrandIdAdd]").html("");
		 form.find("span[name=carSeriesPicAdd]").html("");
		 
		 var carSeriesName=form.find("input[name=carSeriesName]").val();
		 var carBrandId=form.find("select[name=carBrandId]").val();
		 var carSeriesPic=form.find("input[name=carSeriresPic]").val();
		 if(carSeriesName==""){
			form.find("span[name=carSeriesNameAdd]").html("<font color='red'>请输入车系名称！</font>");
			return false;
		 }else if(carBrandId==""){
			form.find("span[name=carBrandIdAdd]").html("<font color='red'>请选择车辆品牌！</font>");
			return false;
		 }else if(carSeriesPic==""){
			form.find("span[name=carSeriesPicAdd]").html("<font color='red'>请上传车系图标！</font>");
			return false;
		 }else{
			$.get("carModelAll/carModelAllNameCheck.do",{carSeriesName:carSeriesName},function(res)
		 {
			if(res.code=="1"){
				form.find("span[name=carSeriesNameAdd]").html("<font color='red'>车型名称已存在，请重新输入。</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : carModelAllAdd.appPath + "/carModelAll/editCarModelAll.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增成功", function() {
								closeTab("新增车型");
								addTab("车型列表",carModelAllAdd.appPath+ "/carModelAll/toCarModelList.do");
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
 return	carModelAllAdd;
});


