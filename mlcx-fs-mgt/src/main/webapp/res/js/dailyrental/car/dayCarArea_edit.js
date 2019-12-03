define([],function() {	
	var dayCarAreaEdit = {
	appPath : getPath("app"),
	init : function() {
		//增加提交
		$("#editDayCarArea").click(function(){
			dayCarAreaEdit.editDaycarArea();
		});
		//关闭
		$("#editCloseDayCarArea").click(function(){
			closeTabBT("服务范围编辑");
        });
	},
	editDaycarArea:function() {
		 var form = $("form[name=dayCarAreaEditForm]");
		 form.find("span[name=dayCarAreaNameEdit]").html("");
		 form.find("span[name=addrRegionEdit]").html("");
		
		 var areaName = form.find("input[name=areaName]").val();
		 var EditrRegion1Id = form.find("select[name=addrRegion1Id]").val();
		  var EditrRegion2Id = form.find("select[name=addrRegion1Id]").val();
		  var EditrRegion3Id = form.find("select[name=addrRegion1Id]").val();
		
		if(areaName==""){
			form.find("span[name=dayCarAreaNameEdit]").html("<font color='red'>请输入服务范围名称！</font>");
			return false;
		}
		if(EditrRegion1Id == ""){
			form.find("span[name=addrRegionEdit]").append("<font color='red' class='formtips onError emaill'>请选择省份！</font>");
			return false;
		}
		if(EditrRegion2Id == ""){
			form.find("span[name=addrRegionEdit]").append("<font color='red' class='formtips onError emaill'>请选择市！</font>");
			return false;
		}
		if(EditrRegion3Id == ""){
			form.find("span[name=addrRegionEdit]").append("<font color='red' class='formtips onError emaill'>请选择区！</font>");
			return false;
		}
		
				
		form.ajaxSubmit({
					url : dayCarAreaEdit.appPath + "/dayCarArea/addOrEditDayCarArea.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
								closeTab("服务范围编辑");
								$("#dayCarAreaList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功！");
						}
					},
					
				});		
					
	 	}
	}
 return	dayCarAreaEdit;
});
//获得市
function getResultCityEdit(d){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
			if(data){
				$("#dayCarCitiesEdit").html("");
                 var select="<select name='editrRegion2Id'  class='' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getDayResultDistrictsEdit(this.value)'>";
                 var pId;
                 for(var i=0;i<data.length;i++){
                	pId=data[0].regionId;
      				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
      			}
      			select+="</select>";
      			getDayResultDistrictsEdit(pId);
      			$("#dayCarCitiesEdit").append(select);
			} 
	 },"json");
}
//获得区
function getDayResultDistrictsEdit(a){
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#dayCarDistrictsEdit").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='' name='editrRegion3Id' style='height:34px;border: 1px solid #ccc;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#dayCarDistrictsEdit").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}




