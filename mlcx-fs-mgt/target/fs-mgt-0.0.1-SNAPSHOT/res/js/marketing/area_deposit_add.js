define([],function() {
var areaDepositAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addAreaDeposit").click(function(){
				areaDepositAdd.saveAreaDeposit();
			});
			//新增页面的关闭
			$("#closeAddAreaDeposit").click(function(){
				closeTab("新增地区应缴押金");
			});
		},
		saveAreaDeposit:function() {
		var form = $("form[name=areaDepositAddFrom]");
		form.ajaxSubmit({
			url : areaDepositAdd.appPath + "/areaDeposit/addAreaDeposit.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "地区应缴押金添加成功", function() {
						closeTab("新增地区应缴押金");
						$("#areaDepositList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "地区应缴押金添加失败！");
				}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='depositAmountAdd']").empty();
			if (form.find("input[name='depositAmount']").val()=="") {
				$("span[name='depositAmountAdd']").append("<font color='red'>请输入应缴押金！</font>");
				return false;
			}else if(!/^[0-9]+(.[0-9]{2})?$/.test(form.find("input[name='depositAmount']").val())){
            	$("span[name='depositAmountAdd']").append("<font color='red'>格式不正确，只能输入最多有两位小数的正数！</font>");
				return false;
            }
		}
	});
}
}
return areaDepositAdd;
});
//获得县、区
function getResultCountry(a){
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#countrycity").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='' name='addrRegion3Id' style='height:34px;border: 1px solid #ccc;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#countrycity").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}
//获得市
function getResultCity(d){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcity").html("");
                         var select="<select name='addrRegion2Id'  class='' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getResultCountry(this.value)'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			getResultCountry(pId);
              			$("#itemcity").append(select);
					} 
	 },"json");
}
