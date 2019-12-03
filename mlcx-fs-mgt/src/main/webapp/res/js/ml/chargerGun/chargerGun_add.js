define(
[],
function() {
var chargerGunAdd = {
	appPath : getPath("app"),
	init : function() {
		//新增提交按钮
		$("#saveChargerGun").click(function(){
			chargerGunAdd.addCharger();
		});
		//新增关闭按钮
		$("#closeAddChargerGun").click(function(){
			closeTab("新增充电枪");
			chargerGunAdd.addCharger();
		});
		//时间插件样式
        $(".datetimepickerGunAdd").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
		
		
		//场站改变
		$("#stationNoId").on("change",function(){
			var chargingPileId = $("#chargingPileId");
			chargingPileId.html("");
			var form = $("form[name=chargingGunAddForm]");
			var stationId = form.find("select[name='stationName']").val();
			if(stationId==''){
    			 chargingPileId.html("<option value=''>请选择</option>");
    			 return false;
			}else{
				$.ajax({
				 type: "post",
	             url: chargerGunAdd.appPath+"/chargerGun/getStation.do",
	             data: {stationId:stationId},
	             dataType: "json",
	             success: function(data){
	            	 if(data.code="1"){
	            		 var parks = data.data;
	            		 if(parks.length == 0){
	            			 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前场站下无充电桩、请重新选择场站！");
	            			 chargingPileId.html("<option value=''>请选择</option>");
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
	            			 chargingPileId.html("<option value=''>请选择</option>");
	            			 return false;
	            		 }else{
	            			 chargingPileId.html(item);
	            		 }
	                 }
	             }
			});
			}
		});
		
	},
	addCharger:function() {
		var form = $("form[name=chargingGunAddForm]");
		form.ajaxSubmit({
			url : chargerGunAdd.appPath + "/chargerGun/addChargingGun.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增充电枪");
						$("#chargingGunList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg);
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#stationNameAdd").empty();
				$("#chargingPileAdd").empty();
				$("#workStatusAdd").empty();
				$("#chargingGunCodeAdd").empty();
				$("#registrantAdd").empty();
				$("#registrantTimeAdd").empty();
				if ($.trim(form.find("select[name='stationName']").val())=="") {
					//$("#stationNameAdd").append("<font color='red' class='formtips onError emaill'>请选择场站！</font>");
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"请选择场站！");
					return false;
				}
				if ($.trim(form.find("select[name='chargingPileNo']").val())=="") {
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"请选择充电桩！");
					return false;
				}
				if ($.trim(form.find("select[name='workStatus']").val())=="") {
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"请选择工作状态！");
					return false;
				}
				
				if ($.trim(form.find("input[name='chargingGunCode']").val())=="") {
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"请选择充电枪编码！");
					return false;
				}else if(form.find("input[name='chargingGunCode']").val().length>2){
					bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"充电枪编码长度只不能超过两位!");
					return false;
				}
			}
		});
	 }
	}
	return chargerGunAdd;
})
