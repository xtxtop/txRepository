define([],function() {	
	var carPreoidAdd = {
	appPath : getPath("app"),
	init : function() {
		//车辆年代增加提交
		$("#addCarPreiod").click(function(){
			carPreoidAdd.saveCarPreiod();
		});
		//关闭车辆年代新增页
		$("#closeCarPreiodAdd").click(function(){
			closeTabBT("新增车辆年代");
        });
	},
	 saveCarPreiod:function() {
		 var form = $("form[name=carPreiodAddForm]");
		 form.find("span[name=carPeriodNameAdd]").html("");
		 form.find("span[name=carBrandIdAdd]").html("");
		 form.find("span[name=carSeriesIdAdd]").html("");
		 
		var carPeriodName=form.find("input[name=carPeriodName]").val();
		var carBrandId=form.find("select[name=carBrandId]").val();
		var carSeriesId=form.find("select[name=carSeriesId]").val();
		if(carPeriodName==""){
			form.find("span[name=carPeriodNameAdd]").html("<font color='red'>请输入年代名称！</font>");
			return false;
		}else if(carBrandId==""){
			form.find("span[name=carBrandIdAdd]").html("<font color='red'>请选择车辆品牌！</font>");
			return false;
		}else if(carSeriesId==""){
			form.find("span[name=carSeriesIdAdd]").html("<font color='red'>请选择车辆年代！</font>");
			return false;
		}else{
			$.get("carPreiod/carPeriodNameCheck.do",{carBrandId:carBrandId,carSeriesId:carSeriesId,carPeriodName:carPeriodName},function(res)
		{
			if(res.code=="1"){
				form.find("span[name=carPeriodNameAdd]").html("<font color='red'>年代名称已存在，请重新输入。</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : carPreoidAdd.appPath + "/carPreiod/editCarPreiod.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增成功", function() {
								closeTab("新增车辆年代");
								addTab("车辆年代列表",carPreoidAdd.appPath+ "/carPreiod/toCarPreiodList.do");
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
 return	carPreoidAdd;
});
//获取指定的select选中的标签的内部值赋给指定的input
function selectBrandValueAdd(selectId){
	var carBrandId = $("#"+selectId+"").find("option:selected").val();
	if(carBrandId!=""){
		$.post("carPreiod/getCarSeriesByBrandId.do", {carBrandId:carBrandId}, 
			function(res){ 
				var msg = res.msg;
				var code = res.code;
				var carSeriesList = res.data;
				var form = $("form[name=carPreiodAddForm]");
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
					var option="<option value=''>全部</option>";
					form.find("select[name=carSeriesId]").html(option);
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该品牌下暂无车系！");
				}
		},"json")
	}
}

