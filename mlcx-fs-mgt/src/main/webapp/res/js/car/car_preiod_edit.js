define([],function() {	
	var carPreoidEdit = {
	appPath : getPath("app"),
	init : function() {
		//车辆年代编辑提交
		$("#editCarPreiod").click(function(){
			carPreoidEdit.saveCarPreiod();
		});
		//关闭车辆年代编辑页
		$("#closeCarPreiodEdit").click(function(){
			closeTabBT("车辆年代编辑");
        });
	},
	 saveCarPreiod:function() {
		 var form = $("form[name=carPreiodEditForm]");
		 form.find("span[name=carPeriodNameEdit]").html("");
		 form.find("span[name=carBrandIdEdit]").html("");
		 form.find("span[name=carSeriesIdEdit]").html("");
		
		var carPeriodId=form.find("input[name=carPeriodId]").val();
		
		var carPeriodName=form.find("input[name=carPeriodName]").val();
		var carBrandId=form.find("select[name=carBrandId]").val();
		var carSeriesId=form.find("input[name=carSeriesId]").val();
		if(carPeriodName==""){
			form.find("span[name=carPeriodNameEdit]").html("<font color='red'>请输入年代名称！</font>");
			return false;
		}else if(carBrandId==""){
			form.find("span[name=carBrandIdEdit]").html("<font color='red'>请选择车辆品牌！</font>");
			return false;
		}else if(carSeriesId==""){
			form.find("span[name=carSeriesIdEdit]").html("<font color='red'>请上传车辆年代图标！</font>");
			return false;
		}else{
			$.get("carPreiod/carPeriodNameCheck.do",{carBrandId:carBrandId,carSeriesId:carSeriesId,carPeriodId:carPeriodId,carPeriodName:carPeriodName},function(res)
		{
			if(res.code=="1"){
				form.find("span[name=carPeriodNameEdit]").html("<font color='red'>年代名称已存在，请重新输入。</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : carPreoidEdit.appPath + "/carPreiod/editCarPreiod.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
								closeTab("车辆年代编辑");
								addTab("车辆年代列表",carPreoidEdit.appPath+ "/carPreiod/toCarPreiodList.do");
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
 return	carPreoidEdit;
});
//获取指定的select选中的标签的内部值赋给指定的input
function selectBrandValueEdit(selectId){
	 var form = $("form[name=carPreiodEditForm]");
	var carBrandId=form.find("select[name=carBrandId]").val();
	if(carBrandId!=""){
		$.post("carPreiod/getCarSeriesByBrandId.do", {carBrandId:carBrandId}, 
			function(res){ 
				var msg = res.msg;
				var code = res.code;
				var carSeriesList = res.data;
				var form = $("form[name=carPreiodEditForm]");
				if(code == "1"){ 
					form.find("select[name=carSeriesId]").html("");
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
				}else{
					form.find("select[name=carSeriesId]").html("");
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该品牌下暂无车系！");
				}
		},"json")
	}
}

