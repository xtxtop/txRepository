define(
[],
function() {
var chargingGunEdit = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		//时间插件样式
        $(".datetimepickerGunEdit").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
      //场站改变
        $("#stationNoIdEdit").on("change",function(){
        	 var chargingPileIdEdit = $("#chargingPileIdEdit");
    		 chargingPileIdEdit.html("");
        	var form = $("form[name=chargingGunEditForm]");
        	var stationId = form.find("select[name='stationNo']").val();
        	if(stationId==''){
        		 chargingPileIdEdit.html("<option value=''>请选择</option>");
    			 return false;
			}else{
				$.ajax({
        		 type: "post",
                 url: chargingGunEdit.appPath+"/chargerGun/getStation.do",
                 data: {stationId:stationId},
                 dataType: "json",
                 success: function(data){
                	 if(data.code="1"){
                		 var parks = data.data;
                		 if(parks.length == 0){
                			 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前场站下无充电桩、请重新选择场站！");
                			 chargingPileIdEdit.html("<option value=''>请选择</option>");
                			 return false;
                		 }
                		 var item = "";
	                	$.each(parks,function(i,val){
	                		if(val.status==1){
	                				 item += "<option value="+val.chargingPileNo+">"+val.chargingPileName+"</option>";
	                			}
	                		});
	                		 if(item==''){
	                			 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前场站下无充电桩、请重新选择场站！"); 
	                			 chargingPileIdEdit.html("<option value=''>请选择</option>");
	                			 return false;
	                		 }else{
	                			 chargingPileIdEdit.html(item);
	                		 }
                     }
                 }
        	});
			}
        });
		//编辑提交
		$("#saveEditChargerGun").click(function(){
			chargingGunEdit.EditchargingGun();
		});
		//编辑关闭按钮
		$("#closeEditChargerGun").click(function(){
			closeTab("编辑充电枪");
			$("#chargingGunList").DataTable().ajax.reload(function(){});
		});
		
	},
	//编辑充电枪
	EditchargingGun:function() {
		var form = $("form[name=chargingGunEditForm]");
		form.ajaxSubmit({
			url : chargingGunEdit.appPath + "/chargerGun/editChargingGun.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑充电枪");
						$("#chargingGunList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=chargingGunEditForm]");
				
				if(form.find("select[name='stationNo']").val() ==''){
					//spanWarning("stationNameEdit","请选择场站名称!")
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp"+"请选择场站名称!");
					return false;
				}
				if(form.find("select[name='chargingPileNo']").val() ==''){
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"请选择充电桩!");
					return false;
				}
				if(	form.find("select[name='workStatus']").val() ==''){
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"请选择工作状态!");
					return false;
				}
				if(form.find("input[name='chargingGunCode']").val() ==''){
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"请输入充电枪编码!");
					return false;
				}else if(form.find("input[name='chargingGunCode']").val().length>2){
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"充电枪编码长度只不能超过两位!");
					return false;
				}
				
 			}
		});
	 }
	 
	}
	return chargingGunEdit;
});
