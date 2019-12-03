define(
	[],
	function() {
		var merchantAdd = {
			appPath : getPath("app"),
			init : function() {
				//新增提交
				$("#addMerchant").click(function(){
					merchantAdd.saveMerchant();
				});
				//新增页面的关闭
				$("#closeAddMerchant").click(function(){
					closeTab("新增租赁商");
				});
			},
			saveMerchant:function() {
				// 提交表单前数据验证
				var form = $("form[name=merchantAddForm]");
				form.find("span[name=merchantNameAdd]").html("");
				form.find("span[name=cantactPersonAdd]").html("");
				form.find("span[name=eMailAdd]").html("");
				form.find("span[name=mobilePhoneAdd]").html("");
				
				form.find("span[name=addrRegionAdd]").html("");
				form.find("span[name=addrStreetAdd]").html("");
				form.find("span[name=isOffsiteReturncarAdd]").html("");
//				form.find("span[name=reconciliationCycleAdd]").html("");
//				form.find("span[name=profitRatioAdd]").html("");
				
			   	var merchantName = $.trim(form.find("input[name=merchantName]").val());
			    var cantactPerson = $.trim(form.find("input[name=cantactPerson]").val());
			    var eMail = $.trim(form.find("input[name=eMail]").val());
			    var mobilePhone = $.trim(form.find("input[name=mobilePhone]").val());
			    
			    var addrRegion1Id = form.find("select[name=addrRegion1Id]").val();
			    var addrRegion2Id = form.find("select[name=addrRegion2Id]").val();
			    var addrRegion3Id = form.find("select[name=addrRegion3Id]").val();
			    var addrStreet = $.trim(form.find("input[name=addrStreet]").val());
			    var isOffsiteReturncar = form.find("select[name=isOffsiteReturncar]").val();
//			    var reconciliationCycle = form.find("select[name=reconciliationCycle]").val();
//			    var profitRatio = form.find("input[name=profitRatio]").val();
			    
			    if (merchantName == "") {
			    	form.find("span[name=merchantNameAdd]").append("<font color='red' class='formtips onError emaill'>请填写商家名称！</font>");
					return false;
			    }
			    if (cantactPerson == "") {
			    	form.find("span[name=cantactPersonAdd]").append("<font color='red' class='formtips onError emaill'>请填写联系人！</font>");
					return false;
			    }
			    if (eMail == "") {
			    	form.find("span[name=eMailAdd]").append("<font color='red' class='formtips onError emaill'>请填写联系人邮箱！</font>");
					return false;
			    }
			    var emailReg = new RegExp(/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/);
			    if (!emailReg.test(eMail)) {
			    	form.find("span[name=eMailAdd]").append("<font color='red' class='formtips onError emaill'>联系人邮箱格式不正确！</font>");
					return false;
			    }
			    if (mobilePhone == "") {
			    	form.find("span[name=mobilePhoneAdd]").append("<font color='red' class='formtips onError emaill'>请填写联系人电话！</font>");
					return false;
			    }
			    var phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;  
			    if (!phoneReg.test(mobilePhone)) {  
			    	form.find("span[name=mobilePhoneAdd]").append("<font color='red' class='formtips onError emaill'>联系人电话格式不正确！</font>");
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
				if(addrStreet == ""){
					form.find("span[name=addrStreetAdd]").append("<font color='red' class='formtips onError emaill'>请填写街道！</font>");
					return false;
				}
				if(isOffsiteReturncar == ""){
					form.find("span[name=isOffsiteReturncarAdd]").append("<font color='red' class='formtips onError emaill'>请选择是否支持异地还车！</font>");
					return false;
				}
//				if(reconciliationCycle == ""){
//					form.find("span[name=reconciliationCycleAdd]").append("<font color='red' class='formtips onError emaill'>请选择对账周期！</font>");
//					return false;
//				}
//				if(profitRatio == ""){
//					form.find("span[name=profitRatioAdd]").append("<font color='red' class='formtips onError emaill'>请填写分润比！</font>");
//					return false;
//				}
//				var profitRatioReg = /^(0\.(0[1-9]|[1-9]{1,2}|[1-9]0)$)|^1$/;
//				if(!profitRatioReg.test(profitRatio)){
//					form.find("span[name=profitRatioAdd]").append("<font color='red' class='formtips onError emaill'>格式不正确，只能输入大于0且小于等于1的2位小数！</font>");
//					return false;
//				}
				if(merchantName !=""){
					 $.ajax({
		    			url:merchantAdd.appPath+"/merchant/checkMerchantName.do",//验证门店名称唯一性
		    			type:"post",
		    			data:{merchantName:merchantName},
		    			dataType:"json",
		    			success:function(res){ 
							if(res.code == "1"){ 
								var form1 = $("form[name=merchantAddForm]");
								form1.find("span[name=merchantNameAdd]").html("");
								form1.find("span[name=merchantNameAdd]").append("<font color='red' class='formtips onError emaill'>商家名称重复！</font>");
								return false;
							}else{
								var form = $("form[name=merchantAddForm]");
								form.ajaxSubmit({
									url : merchantAdd.appPath + "/merchant/editMerchants.do",
									type : "post",
									success : function(res) {
										var msg = res.msg;
										var code = res.code;
										if (code == "1") {
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
												closeTab("新增租赁商");
												$("#merchantList").DataTable().ajax.reload(function(){});
											});
										} else {
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
										}
									},
								});
							}
		    			}
		    		}); 
				 }else{
					 form.find("span[name=merchantNameAdd]").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
					 return false;
				 }
			}
		}
		return merchantAdd;
	}
);
//获得市
function getResultCity(d){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
			if(data){
				$("#merchantCities").html("");
                 var select="<select name='addrRegion2Id'  class='' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getResultDistricts(this.value)'>";
                 var pId;
                 for(var i=0;i<data.length;i++){
                	pId=data[0].regionId;
      				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
      			}
      			select+="</select>";
      			getResultDistricts(pId);
      			$("#merchantCities").append(select);
			} 
	 },"json");
}
//获得区
function getResultDistricts(a){
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#merchantDistricts").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='' name='addrRegion3Id' style='height:34px;border: 1px solid #ccc;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#merchantDistricts").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}
