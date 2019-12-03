define([],function() {
var areaDepositEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//编辑提交
			$("#editAreaDeposit").click(function(){
				areaDepositEdit.editAreaDeposit();
			});
			//编辑页面的关闭
			$("#closeEditAreaDeposit").click(function(){
				closeTab("地区应缴押金编辑");
			});
			
		},
		editAreaDeposit:function() {
	var form = $("form[name=areaDepositEditFrom]");
	form.ajaxSubmit({
		url : areaDepositEdit.appPath + "/areaDeposit/updateAreaDeposit.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动编辑成功", function() {
					closeTab("地区应缴押金编辑");
					$("#areaDepositList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='depositAmountEdit']").empty();
			if (form.find("input[name='depositAmount']").val()=="") {
				$("span[name='depositAmountAdd']").append("<font color='red'>请输入应缴押金！</font>");
				return false;
			}else if(!/^[0-9]+(.[0-9]{2})?$/.test(form.find("input[name='depositAmount']").val())){
            	$("span[name='depositAmountEdit']").append("<font color='red'>格式不正确，只能输入最多有两位小数的正数！</font>");
				return false;
            }
		}
	});
}
}
return areaDepositEdit;
})
//获得县、区
function getResultCountry(a){
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#itemarea").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='col-sm-3' name='addrRegion3Id' style='height:34px;border: 1px solid #ccc;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#itemarea").append(select);
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
                         var select="<select name='addrRegion2Id'  class='col-sm-3' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getResultCountry(this.value)'>";
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
