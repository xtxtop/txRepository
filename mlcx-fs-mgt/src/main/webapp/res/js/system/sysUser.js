//# sourceURL=sysUser.js
define([],function() {	
	var sysUser={
			appPath:getPath("app"),	
			init: function () {	
				$("#sysuser-update-password").click(function(){
					$("#sysuser-update-password").prop("disabled",true);
					sysUser.resetPassword();
		        });
				$("#sysUserEditBtn").click(function(){
					sysUser.addOrEdit();
	            });
				//搜索
				$("#sysUserSearchafter").click(function(){
					sysUser.pageList();
	            });
				//清除（重置）
				$("#sysUserResetBtn").click(function(){
					sysUser.reset();
		        });
				// 数据验证
				$("#formUserName").blur(function(){
					sysUser.userNameVerify();
				});
				$("#formPassword1").blur(function(){
					sysUser.password1Verify();
				});
				$("#formPassword2").blur(function(){
					sysUser.password2Verify();
				});
				$("#formEmail").blur(function(){
					sysUser.emailVerify();
				});
				$("#formMobilePhone").blur(function(){
					sysUser.phoneVerify();
				});
				$("#identification1").click(function() {
					$("#cityListDiv").hide();
					if($("#formUserId").val() == null || $("#formUserId").val() == ""){
						$("#cityListDiv").find("input[type=checkbox]").removeAttr("checked");
					}
				});
				$("#identification2").click(function() {
					$("#cityListDiv").hide();
					if($("#formUserId").val() == null || $("#formUserId").val() == ""){
						$("#cityListDiv").find("input[type=checkbox]").removeAttr("checked");
					}
				});
				$("#identification3").click(function() {
					$("#cityListDiv").show();
				});
				
				// 数据列表
				sysUser.pageList();
				// 加载角色列表
				sysUser.roleList();
				// 加载城市列表
				sysUser.cityList();
				// 初始化搜索样式
				sysUser.initSearchSysUserStyle();
				
				$("#mySysUserModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=sysUserForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	form.find("#formUserId").val("");
	            	form.find("#formPassword1").val("");
	            	form.find("#formPassword2").val("");
	            	form.find("input[type=hidden]").val("");
	            	// 初始化隐藏城市列表的div
					$("#cityListDiv").hide();
	            });
				$("#mySysUserModal").on("show.bs.modal", function() {
//					if($("#formUserId").val()!=""){
//						$("#divPassword1").hide();
//						$("#divPassword2").hide();
//					}else{
//						$("#divPassword1").show();
//						$("#divPassword2").show();
//					}
		        });
				
				
			},
			userNameVerify : function () {
				var $parent = $("#formUserName").parent();
		        $parent.find(".formtips").remove();
				
				if($("#formUserName").val()!=""&&!/^(?=.*[a-z])[a-z0-9]+/ig.test($("#formUserName").val())){
				   $parent.append("<font color='red' class='formtips'>用户名为字母,或是数字和字母的组合！</font>");
				}
			},
			password1Verify : function () {
				var $parent = $("#formPassword2").parent();
				$parent.find(".formtips").remove();
				
				if($("#formPassword1").val() != $("#formPassword2").val()){
					$parent.append("<font color='red' class='formtips'>两次密码不一致！</font>");
				}
			},
			password2Verify : function () {
				var $parent = $("#formPassword2").parent();
				$parent.find(".formtips").remove();
				
				if($("#formPassword1").val() != $("#formPassword2").val()){
					$parent.append("<font color='red' class='formtips'>两次密码不一致！</font>");
				}
			},
			emailVerify : function () {
				var $parent = $("#formEmail").parent();
				$parent.find(".formtips").remove();
				
				if(!/.+@.+\.[a-zA-Z]{2,4}$/.test($("#formEmail").val())){
					$parent.append("<font color='red' class='formtips'>请输入正确的E-Mail地址！</font>");
				}
			},
			phoneVerify : function () {
				var $parent = $("#formMobilePhone").parent();
				$parent.find(".formtips").remove();
				
				if(!/^1[34578]\d{9}$/.test($("#formMobilePhone").val())){
					$parent.append("<font color='red' class='formtips'>请输入正确的手机号码！</font>");
				}
			},
	        addOrEdit: function () {	        	
	        	var form = $("form[name=sysUserForm]");
				form.ajaxSubmit({
					url : sysUser.appPath+"/sysUser/addOrEditSysUser.do",
					type : "post",
					success : function(data) {
						if (data.code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function(){
								$("#mySysUserModal").modal("hide");
	    						$("#sysUserList").DataTable().ajax.reload(function(){
	    							
	    						});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
						}							
					},
					beforeSubmit: function(formData, jqForm, options) {
						
						var form = jqForm[0];
						if($('#identification3').is(':checked')){
							if($('[name=cityIds]:checked').length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当标识为城市运营时，请至少选择一个城市！");
					            return false;
							}
						}
						if($("#formUserName").val()!=""&&!/^(?=.*[a-z])[a-z0-9]+/ig.test($("#formUserName").val())){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请检查用户名称是否输入正确！");
					        return false;
						}
						if($("#formPassword1").val() != $("#formPassword2").val()){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "两次输入的密码不一致！");
							return false;
						}
						if(!/.+@.+\.[a-zA-Z]{2,4}$/.test($("#formEmail").val())){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "邮箱输入不正确！");
							return false;
						}
						if(!/^1[34578]\d{9}$/.test($("#formMobilePhone").val())){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "手机号输入不正确！");
							return false;
						}
						if(!form.formUserName.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入用户名称！");
					        return false;
					    }
						if(!form.formPassword1.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入密码！");
				            return false;
						}
						if(!form.formPassword2.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请再次输入密码！");
				            return false;
						}
						if(!form.formPassword2.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请再次输入密码！");
				            return false;
						}
						
						if($('[name=sysRole]:checked').length==0){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择角色！");
				            return false;
						}
						
						if($('[name=sysRole]:checked').length==0){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择角色！");
				            return false;
						}
                        if(!form.formRealName.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入真实姓名！");
				            return false;
						}
						if(!form.formEmail.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入邮箱！");
				            return false;
						}
						if(!form.formMobilePhone.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入手机号码！");
				            return false;
						}						
						if(!form.sysUserformIsAvailable1.checked && !form.sysUserformIsAvailable2.checked){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择账户是否可用！");
				            return false;
						}
						if(form.formPassword1.value != form.formPassword2.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "两次输入的密码不一致！");
				            return false;
						}else{
							formData[2].name = "password";
						}

					}
				})
	        }, 
	        resetPassword: function () {	        	
	        	var form = $("form[name=updatePassword]");
				form.ajaxSubmit({
					url : sysUser.appPath+"/sysUser/editPassword.do",
					type : "post",
					success : function(data) {
						$("#sysuser-update-password").removeProp("disabled");
						if (data.code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "密码修改成功！", function(){
								window.location.href=sysUser.appPath+"/toLogin.do";
							});F
						} else if(data.code=="2"){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg,function(){
							window.location.href=sysUser.appPath+"/toLogin.do";
							});
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
						}							
					},
					beforeSubmit: function(formData, jqForm, options) {
						var form = $("form[name=updatePassword]");
						if(form.find("input[name='oldPassword']").val()==''||form.find("input[name='oldPassword']").val()==null){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入原密码！");
							$("#sysuser-update-password").removeProp("disabled");
				            return false;
						}
						if(form.find("input[name='password']").val()==''||form.find("input[name='password']").val()==null){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入新密码！");
							$("#sysuser-update-password").removeProp("disabled");
				            return false;
						}
						if(form.find("input[name='password1']").val()==''||form.find("input[name='password1']").val()==null){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请再次输入新密码！");
							$("#sysuser-update-password").removeProp("disabled");
				            return false;
						}	
						if($("#newPassword").val() != $("#newPassword1").val()){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "两次输入的新密码不一致！");
							$("#sysuser-update-password").removeProp("disabled");
							return false;
						}
					}
				})
	        },
	        del: function (userId) {

	    		$.ajax({
	    			url:sysUser.appPath+"/sysUser/deleteSysUser.do",
	    			type:"post",
	    			data:{userId:userId},
	    			dataType:"json",
//	    			async:false,
	    			success:function(result){ 
	    					var msg = result.msg;
	    					var code = result.code;
	    					var data = result.data;
	    					if(code == "1"){
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function(){
									$("#mySysUserModal").modal("hide");
		    						$("#sysUserList").DataTable().ajax.reload(function(){
		    							
		    						});
								});
	    					}else{
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);	    						
	    					} 
	    			}
	    		});
	    		return false;
	        },
	        operateColumEvent: function(){
				$(".sysUser-operate-edit").each(function(){
					$(this).on("click",function(){
						$("#mySysUserModal").modal("show");
						sysUser.detail($(this).data("id"));
					});
				});
				$(".sysUser-operate-del").each(function(id,obj){
					$(obj).on("click",function(){
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统用户吗？", function(result) {
							if(result){
								sysUser.del($(obj).data("id"));
							}						
						}); 					
					});
				});	    
				$("#sysUserList").find('[data-toggle="tooltip"]').tooltip();
				$('#sysUserList tbody input[type="checkbox"]').on("click",function(e){
					sysUser.updateDataTableSelectAll();
				});
				sysUser.updateDataTableSelectAll();
	        },	
	        reset: function(){
	        	var form = $("form[name=sysUserForm]");
	        	//form.clearForm();
	        	
	        	if(form.find("input[name=userId]").val()){
	        		sysUser.detail(form.find("input[name=userId]").val());
	        	}else{
	        		form.resetForm();
	            	form.find("input[type=hidden]").val("");        		
	        	}
	        },
	        detail: function (id){ 
             	$.ajax({
             		url: sysUser.appPath+"/sysUser/detail.do?userId="+id, 
             		success: function(data){
             			if(data){
             				var form = $("form[name=sysUserForm]");
             				sysUser.loadData(data);
             			}
             		}
             	});
             },
	        loadData:function(jsonStr){
	            var obj = eval(jsonStr);
	            var key,value,tagName,type,arr;
	            var form = $("form[name=sysUserForm]");
	            for(x in obj){
	                key = x;
	                value = obj[x];   
	                form.find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
	                    tagName = $(this)[0].tagName;
	                    type = $(this).attr("type");
	                    if(tagName=="INPUT"){
	                        if(type=="radio"){
	                            $(this).prop("checked",$(this).val()==value);
	                        }else if(type=="checkbox"){
                				$(this).prop("checked",false);
	                        	if(key=="sysRole"){
	                        		for(var i=0;i<value.length;i++){
	                        			if($(this).val()==value[i].roleId){
	                        				$(this).prop("checked",true);
	                        			}
	                        		}
	                        	}
	                        }else{
	                            $(this).val(value);
	                        }
	                    }else if(tagName=="SELECT" || tagName=="TEXTAREA"){
	                        $(this).val(value);
	                    }
	                     
	                });
	                if(key=="password"){
	                	$("#formPassword1").val(value);
	    	        	$("#formPassword2").val(value);
	                }
	                if(key=="identification"){
	                	if(value == 1 || value == 2){
	                		$("#cityListDiv").hide();
							$("#cityListDiv").find("input[type=checkbox]").removeAttr("checked");
	                	}else if(value == 3){
							$("#cityListDiv").show();
							if(obj.sysUserCitys != null){
								var checkbox = $("#cityListDiv").find("input[type=checkbox]");
								for(var i = 0 ; i<obj.sysUserCitys.length ; i++){
									for(var j = 0 ; j<checkbox.length ; j++){
										if(checkbox.eq(j).val() == obj.sysUserCitys[i].cityId){
											checkbox.eq(j).attr("checked","checked");
											break;
										}
									}
								}
							}
							
	                	}
	                }
	            }
	        },
	        roleList: function () {
	    		$.ajax({
	    			url:sysUser.appPath+"/sysRole/queryAllSysRole.do",
	    			type:"post",
	    			data:{},
	    			dataType:"json",
//	    			async:false,
	    			success:function(result){ 
	    				$("#sysUserRoleList").empty();//先清空
	    				for(var i=0;i<result.length;i++) {
	    					var option = $("<input type='checkbox' name='sysRole'>").val(result[i].roleId);
	    					$("#sysUserRoleList").append(option).append(result[i].roleName);
	    				}
	    				
	    			}
	    		});
	    		return false;
	        },
			cityList : function() {
				$.ajax({
							url : sysUser.appPath
									+ "/dataDictItem/getModleByBrand.do?parentCatCode=CITY",
							type : "post",
							data : {},
							dataType : "json",
							// async:false,
							success : function(result) {
								$("#sysUserCityList").empty();// 先清空
								for (var i = 0; i < result.data.length; i++) {
									var option = $(
											"<input type='checkbox' name='cityIds'>")
											.val(result.data[i].dataDictItemId);
									$("#sysUserCityList").append(option)
											.append(" "+result.data[i].itemValue+" ");
								}

							}
						});
				return false;
			},
	        batchRemove: function (ids) {
	        	$.ajax({
	        		url: sysUser.appPath+"/sysUser/batchDelete.do?sysUserIds="+ids, 
	        		success: function(data){
	        			if(data.code=1){
	        				$("#sysUserList").DataTable().ajax.reload(function(){
	        					sysUser.updateDataTableSelectAll();
	        				});
	        			}
	        		}
	        	});        	
	        },
	        updateDataTableSelectAll:function(){
	        	   var $chkbox_all        = $('#sysUserList tbody input[type="checkbox"]');
	        	   var $chkbox_checked    = $('#sysUserList tbody input[type="checkbox"]:checked');
	        	   var chkbox_select_all  = $('#sysUserList thead input[type="checkbox"]');
	        	   if($chkbox_checked.length === 0){
				         $('#sysUserList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   } else if ($chkbox_checked.length === $chkbox_all.length){
				         $('#sysUserList thead input[type="checkbox"]:not(:checked)').prop("checked",true);   	     
	        	   } else {
				         $('#sysUserList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   }
	        },
			pageList:function () {	
				var tpl = $("#sysUserTpl").html();
				//预编译模板
				var template = Handlebars.compile(tpl);
				$('#sysUserList').dataTable( {	
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": sysUser.appPath+'/sysUser/getSysUserList.do',
						"data": function ( d ) {
							var form = $("form[name=sysUserSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize": d.length,
								"userName":form.find("input[name=userName]").val(),
								"realName":form.find("input[name=realName]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
	        					"isDeleted":0
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;							
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"order": [[ 1, "desc" ]],
					"columns": [
					    { "title":"","data": "paramId","width":"5px"},
			            { "title":"用户账号","data": "userName" },
						{ "title":"用户名称","data": "realName" },
						{ "title":"管理员角色","data": "sysRole" },
						{ "title":"最后登录时间","data": "lastLoginTime" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"更新时间","data": "updateTime" },
						{ "title":"是否可用","data": "isAvailable" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#sysUserTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					initComplete: function () {
						$("#sysUserTools").removeClass("col-xs-6");
						$(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
						
						$("#sysUserTools").append('<button type="button" class="btn-new-type" data-toggle="modal" data-target="#mySysUserModal">添加</button>');
//						$("#sysUserTools button").addClass('btnDefault');
//						$("#sysUserTools").append('<button type="button" class="btn btn-default btn-sm sysUser-batch-del">批量删除</button>'); 				
//						$(".sysUser-batch-del").on("click",function(){
//	    					var ids=[];
//	        				$("#sysUserList tbody").find("input:checked").each(function(){
//	        					ids.push($(this).val());
//	        				});
//	        				if(ids.length>0){
//	        					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统用户吗？", function(result) {
//	        						if(result){
//	        							sysUser.batchRemove(ids);
//	        						}
//	        					});	
//	        				}else{
//	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要删除的系统用户！");
//	        				}
//	        			});	        			
	        			$('#sysUserList thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#sysUserList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#sysUserList tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					},
					"drawCallback": function( settings ) {
						sysUser.operateColumEvent();
	        	    },
					"columnDefs": [
						{ "targets":[0],
						    //"visible": true,
						    "orderable":false,
						    "render":function (data, type, full, meta){
						             return '<input type="checkbox" name="userId[]" value="' + full.userId + '">';
						    }
						},
						{ "targets":[3],
						    "render":function (a, b, c, d){
						    	var roleName = "";
						    	for(var i=0;i<c.sysRole.length;i++){
						    		roleName+=c.sysRole[i].roleName+",";
						    	}
						    	roleName=roleName.substring(0,(roleName).lastIndexOf(","));
						        return roleName;
						    }
						},
						{
							targets: 8,
							render: function (a, b, c, d) {								
								var edit='<span class="glyphicon sysUser-operate-edit" data-id="'+c.userId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">修改</span>';
//	        					var del='<span class="glyphicon   btn btn-danger sysUser-operate-del" data-id="'+c.userId+'" data-toggle="tooltip" data-placement="top" title="删除">删除</span>';
	        					return edit;
							}
						},
						{
	        	            "targets": [4,5,6],
	        	            "render": function(data, type, row, meta) {
	        	            	if(data != null){
	        	            		var now = moment(data); 
	            	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
	        	            	}	
	        	            	return "";
	        	            }
	        	        },
	        	        {
	        	        	"targets": [7],
	        	            "render": function(data, type, row, meta) {
	        	                if(data==1){
	        	                	return "可用";
	        	                }else{
	        	                	return "不可用";
	        	                }
	        	            }
        	            }
					]
				});
				$("#myModal").on("shown.bs.modal", function () {
					//$('#myInput').focus()；
				});
			},
			initSearchSysUserStyle:function(){
				var itemLength = $(".seach-input-details>.seach-input-item").length;
				if(itemLength<=3){
					$(".seach-input-details").css({
						"margin-top":"24px",
						"height":"auto",
						"position":"relative",
						"z-index":"1",
						"margin-right":"4.5% !important",
						"margin-bottom":"10px"
							
					});
					$(".seach-input-details>.seach-input-item").css({
//						"margin-right":"4.5%",
						"width":"25%"
					});
					$(".seacher-button-content").css({
						"z-index":"0",
//					    "float": "right",
					    "margin-top": "-15px"
					})
				}
			}
	    };
	return sysUser;
});


