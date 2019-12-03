define([],function() {	
	var dayCarAreaAdd = {
	appPath : getPath("app"),
	init : function() {
		//增加提交
		$("#addDayCarArea").click(function(){
			dayCarAreaAdd.saveDaycarArea();
		});
		//关闭
		$("#addCloseDayCarArea").click(function(){
			debugger
			closeTabBT("服务区域新增");
        });
	},
	saveDaycarArea:function() {
		debugger
		 var form = $("form[name=dayCarAreaAddForm]");
		 form.find("span[name=dayCarAreaNameAdd]").html("");
		 form.find("span[name=addrRegionAdd]").html("");
		 
		  var areaName = form.find("input[name=areaName]").val();
		  var addrRegion1Id = form.find("select[name=addrRegion1Id]").val();
		  var addrRegion2Id = form.find("select[name=addrRegion2Id]").val();
		  var addrRegion3Id = form.find("select[name=addrRegion3Id]").val();
		  
		if(areaName == ""){
			debugger
			form.find("span[name=dayCarAreaNameAdd]").html("<font color='red'>请输入服务范围名称！</font>");
			return false;
		}
		if(addrRegion1Id == ""){
			form.find("span[name=addrRegionAdd]").append("<font color='red' class='formtips onError emaill'>请选择省份！</font>");
			return false;
		}
		if(addrRegion2Id == ""){
			form.find("span[name=addrRegionAdd]").append("<font color='red' class='formtips onError emaill'>请选择市！</font>");
			return false;
		}
		if(addrRegion3Id == ""){
			form.find("span[name=addrRegionAdd]").append("<font color='red' class='formtips onError emaill'>请选择区！</font>");
			return false;
		}
		
				
		form.ajaxSubmit({
					url : dayCarAreaAdd.appPath + "/dayCarArea/addOrEditDayCarArea.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增成功", function() {
								closeTab("服务区域新增");
								$("#dayCarAreaList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "新增失败！");
						}
					},
					
				});		
					
	 	}
	}
 return	dayCarAreaAdd;
});
//获得市
function getDayResultCity(d){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
			if(data){
				$("#dayCarCities").html("");
                 var select="<select name='addrRegion2Id'  class='' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getDayResultDistricts(this.value)'>";
                 var pId;
                 for(var i=0;i<data.length;i++){
                	pId=data[0].regionId;
      				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
      			}
      			select+="</select>";
      			getDayResultDistricts(pId);
      			$("#dayCarCities").append(select);
			} 
	 },"json");
}
//获得区
function getDayResultDistricts(a){
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#dayCarDistricts").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='' name='addrRegion3Id' style='height:34px;border: 1px solid #ccc;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#dayCarDistricts").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}




