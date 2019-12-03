define([],function() {	
var carOwnerEdit = {
		appPath : getPath("app"),
		init : function() {
			//修改车主提交
			$("#editCarOwner").click(function(){
				carOwnerEdit.editCarOwner();
			});
			//车主修改页面关闭
			$("#closeCarOwnerEdit").click(function(){
				closeTab("车主信息编辑");
			});
			//类型选择（个人，公司）工商营业执照、组织机构代码证、税务登记证显示
        	var carOwnerEditForm=$("form[name=carOwnerEditForm]")
        	$("#szEdit,#szEditNext").attr('style','display: none');
        	carOwnerEditForm.find("input[type='radio']").click(function(){
        		if(carOwnerEditForm.find("input[name='ownerType']:checked").val()==2){
        			console.log('个人')
        			$("#szEdit,#szEditNext").attr('style','display: none');
        		}else if(carOwnerEditForm.find("input[name='ownerType']:checked").val()==1){
        			console.log('公司')
        			$("#szEdit,#szEditNext").attr('style','display: table-row');
        		}
        	});
		},
		validateFE:function(){
			var form = $("form[name=carOwnerEditForm]");
			form.ajaxSubmit({
				url : carOwnerEdit.appPath + "/carOwner/editCarOwner.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车主信息修改成功", function() {
							closeTab("车主信息编辑");
							$("#carOwnerList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车主信息修改失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("#ownerNameEdit").empty();
				   	$("#ownerFullNameEdit").empty();
				   	$("#contactPersonEdit").empty();
				   	$("#contactTelEdit").empty();
					$("#contactPhoneEdit").empty();
					$("#contactEmailEdit").empty();
				   	$("#idCardNoEdit").empty();
				   	$("#bizLicenseNoEdit").empty();
				   	$("#organizationCodeEdit").empty();
				   	$("#taxRegistrationEdit").empty();
				   	var form = $("form[name=carOwnerEditForm]");
				    var ownerName=form.find("input[name=ownerName]").val();
				    var ownerFullName=form.find("input[name=ownerFullName]").val();
				    var contactPerson=form.find("input[name=contactPerson]").val();
				    var contactTel=form.find("input[name=contactTel]").val();
				    var contactPhone=form.find("input[name=contactPhone]").val();
				    var contactEmail=form.find("input[name=contactEmail]").val();
				    var idCardNo=form.find("input[name=idCardNo]").val();
				    var bizLicenseNo=form.find("input[name=bizLicenseNo]").val();
				    var organizationCode=form.find("input[name=organizationCode]").val();
				    var taxRegistration=form.find("input[name=taxRegistration]").val();
				    var tel=/(^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$)|(^\+86(?:\-\d{2,3}\-|\(\d{2,3}\))\d{7,8}$)/;
				    var phone=/^1\d{10}$/;//^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
				    var email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
				    var shenNo=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
				    if (ownerName == "") {
						$("#ownerNameEdit").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
						return false;
		            }
				    if (ownerFullName == "") {
						$("#ownerFullNameEdit").append("<font color='red' class='formtips onError emaill'>请输入全称！</font>");
						return false;
		            }
				    if (contactPerson == "") {
						$("#contactPersonEdit").append("<font color='red' class='formtips onError emaill'>请输入联系人！</font>");
						return false;
		            }
				    if (contactPerson != ""&&($.trim(contactPerson)).length>20) {
						$("#contactPersonEdit").append("<font color='red' class='formtips onError emaill'>联系人输入不得超过20个字符！</font>");
						return false;
		            }
				    var type=form.find("input[name='ownerType']:checked").val();
/*				    if(type==1){
				    	if (contactTel == "") {
							$("#contactTelEdit").append("<font color='red' class='formtips onError emaill'>请输入联系电话！</font>");
							return false;
			            }	
				    }
				    
				    if (contactTel != ""&&!tel.test(contactTel)) {
						$("#contactTelEdit").append("<font color='red' class='formtips onError emaill'>格式不正确!<例:国际+86(756)3393686><国内029-5180000></font>");
						return false;
		            }*/
				    if (contactPhone == "") {
						$("#contactPhoneEdit").append("<font color='red' class='formtips onError emaill'>请输入联系人手机！</font>");
						return false;
		            }
				    if (contactPhone != ""&&!phone.test(contactPhone)) {
						$("#contactPhoneEdit").append("<font color='red' class='formtips onError emaill'>手机号格式不正确！</font>");
						return false;
		            }
				    if (contactEmail == "") {
						$("#contactEmailEdit").append("<font color='red' class='formtips onError emaill'>请输入邮箱！</font>");
						return false;
		            }
				    if (contactEmail != ""&&!email.test(contactEmail)) {
						$("#contactEmailEdit").append("<font color='red' class='formtips onError emaill'>邮箱格式不正确！</font>");
						return false;
		            }
/*				    if (idCardNo == "") {
						$("#idCardNoEdit").append("<font color='red' class='formtips onError emaill'>请输入身份证号！</font>");
						return false;
		            }
				    if (idCardNo != ""&&($.trim(idCardNo)).length>20) {
						$("#idCardNoEdit").append("<font color='red' class='formtips onError emaill'>身份证号输入不得超过20个字符！</font>");
						return false;
		            }
				    if (idCardNo != ""&&!shenNo.test(idCardNo)) {
						$("#idCardNoEdit").append("<font color='red' class='formtips onError emaill'>身份证号格式不正确！</font>");
						return false;
		            }
				    if(type==1){
				    	if (bizLicenseNo == "") {
							$("#bizLicenseNoEdit").append("<font color='red' class='formtips onError emaill'>请输入工商营业执照！</font>");
							return false;
			            }
				    	if (organizationCode == "") {
							$("#organizationCodeEdit").append("<font color='red' class='formtips onError emaill'>请输入组织机构代码证！</font>");
							return false;
			            }
				    	if (taxRegistration == "") {
							$("#taxRegistrationEdit").append("<font color='red' class='formtips onError emaill'>请输入税务登记证！</font>");
							return false;
			            }
				    }*/
				}
			});
		},
		editCarOwner:function() {
			var form = $("form[name=carOwnerEditForm]");
			var ownerId=form.find("input[name=ownerId]").val();
			var idCardNo=form.find("input[name=idCardNo]").val();
			 var bizLicenseNo=form.find("input[name=bizLicenseNo]").val();
			 var type=form.find("input[name='ownerType']:checked").val();
			 $("#ownerNameEdit").empty();
			   	$("#ownerFullNameEdit").empty();
			   	$("#contactPersonEdit").empty();
			   	$("#contactTelEdit").empty();
				$("#contactPhoneEdit").empty();
				$("#contactEmailEdit").empty();
			   	$("#idCardNoEdit").empty();
			   	$("#bizLicenseNoEdit").empty();
			   	$("#organizationCodeEdit").empty();
			   	$("#taxRegistrationEdit").empty();
			   	var form = $("form[name=carOwnerEditForm]");
			    var ownerName=form.find("input[name=ownerName]").val();
			    var ownerFullName=form.find("input[name=ownerFullName]").val();
			    var contactPerson=form.find("input[name=contactPerson]").val();
			    var contactTel=form.find("input[name=contactTel]").val();
			    var contactPhone=form.find("input[name=contactPhone]").val();
			    var contactEmail=form.find("input[name=contactEmail]").val();
			    var idCardNo=form.find("input[name=idCardNo]").val();
			    var bizLicenseNo=form.find("input[name=bizLicenseNo]").val();
			    var organizationCode=form.find("input[name=organizationCode]").val();
			    var taxRegistration=form.find("input[name=taxRegistration]").val();
			    var tel=/(^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$)|(^\+86(?:\-\d{2,3}\-|\(\d{2,3}\))\d{7,8}$)/;
			    var phone=/^1\d{10}$/;//^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
			    var email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
			    //var idCardZZ=/(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/;
			    var idCardZZ=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
			    if (ownerName == "") {
					$("#ownerNameEdit").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
					return false;
	            }
			    if (ownerFullName == "") {
					$("#ownerFullNameEdit").append("<font color='red' class='formtips onError emaill'>请输入全称！</font>");
					return false;
	            }
			    if (contactPerson == "") {
					$("#contactPersonEdit").append("<font color='red' class='formtips onError emaill'>请输入联系人！</font>");
					return false;
	            }
			    if (contactPerson != ""&&($.trim(contactPerson)).length>20) {
					$("#contactPersonEdit").append("<font color='red' class='formtips onError emaill'>联系人输入不得超过20个字符！</font>");
					return false;
	            }
			    var type=form.find("input[name='ownerType']:checked").val();
/*			    if(type==1){
			    	if (contactTel == "") {
						$("#contactTelEdit").append("<font color='red' class='formtips onError emaill'>请输入联系电话！</font>");
						return false;
		            }	
			    }
			    
			    if (contactTel != ""&&!tel.test(contactTel)) {
					$("#contactTelEdit").append("<font color='red' class='formtips onError emaill'>格式不正确!<例:国际+86(756)3393686><国内029-5180000></font>");
					return false;
	            }*/
			    if (contactPhone == "") {
					$("#contactPhoneEdit").append("<font color='red' class='formtips onError emaill'>请输入联系人手机号！</font>");
					return false;
	            }
			    if (contactPhone != ""&&!phone.test(contactPhone)) {
					$("#contactPhoneEdit").append("<font color='red' class='formtips onError emaill'>手机号格式不正确！</font>");
					return false;
	            }
			    if (contactEmail == "") {
					$("#contactEmailEdit").append("<font color='red' class='formtips onError emaill'>请输入邮箱！</font>");
					return false;
	            }
			    if (contactEmail != ""&&!email.test(contactEmail)) {
					$("#contactEmailEdit").append("<font color='red' class='formtips onError emaill'>邮箱格式不正确！</font>");
					return false;
	            }
/*			    if (idCardNo == "") {
					$("#idCardNoEdit").append("<font color='red' class='formtips onError emaill'>请输入身份证号！</font>");
					return false;
	            }
			    if (idCardNo != ""&&!idCardZZ.test($.trim(idCardNo))) {
					$("#idCardNoEdit").append("<font color='red' class='formtips onError emaill'>身份证号输入不正确！</font>");
					return false;
	            }
			    if(type==1){
			    	if (bizLicenseNo == "") {
						$("#bizLicenseNoEdit").append("<font color='red' class='formtips onError emaill'>请输入工商营业执照！</font>");
						return false;
		            }
			    	if (organizationCode == "") {
						$("#organizationCodeEdit").append("<font color='red' class='formtips onError emaill'>请输入组织机构代码证！</font>");
						return false;
		            }
			    	if (taxRegistration == "") {
						$("#taxRegistrationEdit").append("<font color='red' class='formtips onError emaill'>请输入税务登记证！</font>");
						return false;
		            }
			    }*/
			 if(idCardNo!=""){
				 $.ajax({
		    			url:carOwnerEdit.appPath+"/carOwner/onlyIdCardNo.do",//验证身份证号唯一性
		    			type:"post",
		    			data:{idCardNo:idCardNo,ownerId:ownerId},
		    			dataType:"json",
		    			success:function(res){ 
		    					if(res == "1"){ 
		    						$("#idCardNoEdit").empty();
		    						$("#idCardNoEdit").append("<font color='red' class='formtips onError emaill'>该身份证号已存在，请重新输入！</font>");
		    					}else{
		    						if(bizLicenseNo!=""&&type==1){
		    							 $.ajax({
			    				    			url:carOwnerEdit.appPath+"/carOwner/onlyBizLicenseNo.do",//验证工商营业执照唯一性
			    				    			type:"post",
			    				    			data:{bizLicenseNo:bizLicenseNo,ownerId:ownerId},
			    				    			dataType:"json",
			    				    			success:function(res){ 
			    				    				debugger;
			    				    					if(res == "1"){ 
			    				    						$("#bizLicenseNoEdit").empty();
			    				    						$("#bizLicenseNoEdit").append("<font color='red' class='formtips onError emaill'>该营业执照已存在，请重新输入！</font>");
			    				    					}else{
			    				    						carOwnerEdit.validateFE();
			    				    					}
			    				    					}
			    				    			});
		    						}else if(bizLicenseNo==""&&type==1){
		    							$("#bizLicenseNoEdit").empty();
		    							$("#bizLicenseNoEdit").append("<font color='red' class='formtips onError emaill'>请输入工商营业执照！</font>");
		    						}else if(type==2){
		    							carOwnerEdit.validateFE();
		    						}
		    					}
		    			}
		    		}); 
			 }else{
//				 $("#idCardNoEdit").empty();
//				 $("#idCardNoEdit").append("<font color='red' class='formtips onError emaill'>请输入身份证号！</font>");
				 carOwnerEdit.validateFE();
			 }
		}
}
return carOwnerEdit
})
