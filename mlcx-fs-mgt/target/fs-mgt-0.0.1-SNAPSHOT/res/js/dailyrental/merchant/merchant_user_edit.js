define(
	[],
	function() {
		var merchantUserEdit = {
			appPath : getPath("app"),
			init : function() {
				//编辑提交
				$("#editMerchantUser").click(function(){
					merchantUserEdit.saveMerchantUser();
				});
				//编辑页面的关闭
				$("#closeEditMerchantUser").click(function(){
					closeTab("编辑商家用户");
				});
			},
			saveMerchantUser:function() {
				// 提交表单前数据验证
				var form = $("form[name=merchantUserEditForm]");
				form.find("span[name=merchantId]").html("");
				form.find("span[name=userName]").html("");
				form.find("span[name=password]").html("");
				form.find("span[name=mobilePhone]").html("");
				form.find("span[name=sex]").html("");
				form.find("span[name=cityId]").html("");
				
				var userNo = $.trim(form.find("input[name=userNo]").val());
			   	var merchantId = $.trim(form.find("select[name=merchantId]").val());
			    var userName = $.trim(form.find("input[name=userName]").val());
			    var password = $.trim(form.find("input[name=password]").val());
			    var mobilePhone = $.trim(form.find("input[name=mobilePhone]").val());
			    var sex = form.find("select[name=sex]").val();
			    var cityId = form.find("select[name=cityId]").val();
			    
			    if (merchantId == "") {
			    	form.find("span[name=merchantId]").append("<font color='red' class='formtips onError emaill'>请选择商家！</font>");
					return false;
			    }
			    if (userName == "") {
			    	form.find("span[name=userName]").append("<font color='red' class='formtips onError emaill'>请填写用户名！</font>");
					return false;
			    }
			    if (password == "") {
			    	form.find("span[name=password]").append("<font color='red' class='formtips onError emaill'>请填写密码！</font>");
					return false;
			    }
			    var passwordReg = /^[0-9a-zA-Z]+$/;
			    if (!passwordReg.test(password)) {
			    	form.find("span[name=password]").append("<font color='red' class='formtips onError emaill'>密码格式不正确！</font>");
					return false;
			    }
			    if(password.length<6||password.length>12){
			    	form.find("span[name=password]").append("<font color='red' class='formtips onError emaill'>密码长度不正确(6-12位数字字母)！</font>");
					return false;
			    }
			    if (mobilePhone == "") {
			    	form.find("span[name=mobilePhone]").append("<font color='red' class='formtips onError emaill'>请填写联系人电话！</font>");
					return false;
			    }
			    var phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;  
			    if (!phoneReg.test(mobilePhone)) {  
			    	form.find("span[name=mobilePhone]").append("<font color='red' class='formtips onError emaill'>联系人电话格式不正确！</font>");
			    	return false;
			    }  
				if(sex == ""){
					form.find("span[name=sex]").append("<font color='red' class='formtips onError emaill'>请选择性别！</font>");
					return false;
				}
				if(cityId == ""){
					form.find("span[name=cityId]").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
					return false;
				}
				if(mobilePhone !=""){
					 $.ajax({
		    			url:merchantUserEdit.appPath+"/merchantUser/checkMerchantUserPhone.do",//验证手机号码唯一性
		    			type:"post",
		    			data:{mobilePhone:mobilePhone,userNo:userNo},
		    			dataType:"json",
		    			success:function(res){ 
							if(res.code == "1"){ 
								var form1 = $("form[name=merchantUserEditForm]");
								form1.find("span[name=mobilePhone]").html("");
								form1.find("span[name=mobilePhone]").append("<font color='red' class='formtips onError emaill'>该手机号已注册！</font>");
								return false;
							}else{
								var form = $("form[name=merchantUserEditForm]");
								form.ajaxSubmit({
									url : merchantUserEdit.appPath + "/merchantUser/editMerchantUser.do",
									type : "post",
									success : function(res) {
										var msg = res.msg;
										var code = res.code;
										if (code == "1") {
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
												closeTab("编辑商家用户");
												$("#merchantUserList").DataTable().ajax.reload(function(){});
											});
										} else {
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
										}
									},
								});
							}
		    			}
		    		}); 
				 }else{
					 form.find("span[name=mobilePhone]").append("<font color='red' class='formtips onError emaill'>请输入电话！</font>");
					 return false;
				 }
			}
		}
		return merchantUserEdit;
	}
);