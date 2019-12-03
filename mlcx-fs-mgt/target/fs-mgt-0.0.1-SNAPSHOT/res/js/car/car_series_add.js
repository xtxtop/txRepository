define([],function() {	
	var carSeriesAdd = {
	appPath : getPath("app"),
	imgPath : getPath("img"),
	init : function() {
		//车系增加提交
		$("#addCarSeries").click(function(){
			carSeriesAdd.saveCarSeries();
		});
		//关闭车系新增页
		$("#closeCarSeriesAdd").click(function(){
			closeTabBT("新增车系");
        });
		$("#addCarSeriesPhoto").click(function(){
			$("#carSeriesAddModal").modal("show");
		});
		//新增图片回填
		$("#addCarSeriesPhotoBtn").click(function(){
			var form=$("form[name=carSeriesPhotoForm]");
			var img=form.find("input[name=carSeriesPhotoUrl1]").val();
			if(img!=""){
				var formUpload=$("form[name=carSeriesAddForm]");
				formUpload.find("input[name=carSeriresPic]").val(img);
				formUpload.find("#carSeriesPicUrlImg").css("background-image","url("+carSeriesAdd.imgPath+'/'+img+")");
				$("#carSeriesAddModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	 saveCarSeries:function() {
		 var form = $("form[name=carSeriesAddForm]");
		 form.find("span[name=carSeriesNameAdd]").html("");
		 form.find("span[name=carBrandIdAdd]").html("");
		 form.find("span[name=seaTingAdd]").html("");
		 form.find("span[name=carSeriesPicAdd]").html("");
		 
		 var carSeriesName=form.find("input[name=carSeriesName]").val();
		 var carBrandId=form.find("select[name=carBrandId]").val();
		 var carSeriesPic=form.find("input[name=carSeriresPic]").val();
		 var seaTing=form.find("input[name=seaTing]").val();
		 if(carSeriesName==""){
			form.find("span[name=carSeriesNameAdd]").html("<font color='red'>请输入车系名称！</font>");
			return false;
		 }else if(carBrandId==""){
			form.find("span[name=carBrandIdAdd]").html("<font color='red'>请选择车辆品牌！</font>");
			return false;
		 }else if(seaTing==""){
			 form.find("span[name=seaTingAdd]").html("<font color='red'>请输入座位数！</font>");
			return false;
		 }else if(carSeriesPic==""){
			form.find("span[name=carSeriesPicAdd]").html("<font color='red'>请上传车系图标！</font>");
			return false;
		 }else{
			$.get("carSeries/carSeriesNameCheck.do",{carSeriesName:carSeriesName},function(res)
		 {
			if(res.code=="1"){
				form.find("span[name=carSeriesNameAdd]").html("<font color='red'>车系名称已存在，请重新输入。</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : carSeriesAdd.appPath + "/carSeries/editCarSeries.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增成功", function() {
								closeTab("新增车系");
//								addTab("车系列表",carSeriesAdd.appPath+ "/carSeries/toCarSeriesList.do");
								$("#carSeriesList").DataTable().ajax.reload(function(){});
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
 return	carSeriesAdd;
});


