define([],function() {	
	var carSeriesEdit = {
	appPath : getPath("app"),
	imgPath : getPath("img"),
	init : function() {
		//车系增加提交
		$("#editCarSeries").click(function(){
			carSeriesEdit.saveCarSeries();
		});
		//关闭车系新增页
		$("#closeCarSeriesEdit").click(function(){
			closeTabBT("车系编辑");
        });
		$("#editCarSeriesPhoto").click(function(){
			$("#carSeriesEditModal").modal("show");
		});
		//新增图片回填
		$("#editCarSeriesPhotoBtn").click(function(){
			var form=$("form[name=carSeriesPhotoEditForm]");
			var img=form.find("input[name=carSeriesPhotoUrl1Edit]").val();
			if(img!=""){
				var formUpload=$("form[name=carSeriesEditForm]");
				formUpload.find("input[name=carSeriresPic]").val(img);
				formUpload.find("#carSeriesPicUrlImg").css("background-image","url("+carSeriesEdit.imgPath+'/'+img+")");
				$("#carSeriesEditModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	 saveCarSeries:function() {
		 var form = $("form[name=carSeriesEditForm]");
		 form.find("span[name=carSeriesNameEdit]").html("");
		 form.find("span[name=carBrandIdEdit]").html("");
		 form.find("span[name=carSeriesPicEdit]").html("");
		 form.find("span[name=seaTingEdit]").html("");
		
		 var carSeriesId=form.find("input[name=carSeriesId]").val();
		
		 var carSeriesName=form.find("input[name=carSeriesName]").val();
		 var carBrandId=form.find("select[name=carBrandId]").val();
		 var carSeriesPic=form.find("input[name=carSeriesPic]").val();
		 var seaTing=form.find("input[name=seaTing]").val();
		 if(carSeriesName==""){
			 form.find("span[name=carSeriesNameEdit]").html("<font color='red'>请输入车系名称！</font>");
			 return false;
		 }else if(carBrandId==""){
			 form.find("span[name=carBrandIdEdit]").html("<font color='red'>请选择车辆品牌！</font>");
			 return false;
		 }else if(seaTing==""){
			 form.find("span[name=seaTingEdit]").html("<font color='red'>请输入座位数！</font>");
			return false;
		 }else if(carSeriesPic==""){
			 form.find("span[name=carSeriesPicEdit]").html("<font color='red'>请上传车系图标！</font>");
			 return false;
		 }else{
			 $.get("carSeries/carSeriesNameCheck.do",{carSeriesName:carSeriesName,carSeriesId:carSeriesId},function(res)
		 {
			if(res.code=="1"){
				form.find("span[name=carSeriesNameEdit]").html("<font color='red'>车系名称已存在，请重新输入。</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : carSeriesEdit.appPath + "/carSeries/editCarSeries.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
								closeTab("新增车系");
//								addTab("车系列表",carSeriesEdit.appPath+ "/carSeries/toCarSeriesList.do");
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
 return	carSeriesEdit;
});


