define([],function() {	
	var car={
			appPath:getPath("app"),	
			init: function () {	
				//关闭车辆详情页
				$("#closeCar").click(function(){
					closeTab("车辆详情");
	            });
				//上线状态提交
				$("#carOnFormBtn").click(function(){
					car.onFormSub();
	            });
				//上线取消
				$("#carOnCancelBtn").click(function(){
					$("#onModal").modal("hide");
	            });
				//下线状态提交
				$("#carOffBtn").click(function(){
					car.offFormSub();
	            });
				//下线取消
				$("#carOffCancel").click(function(){
					$("#OffModal").modal("hide");
	            });
	            //数据列表
				car.pageList();
				//查询
				$("#carSearchafter").click(function(){
					car.pageList();
	            });
				$("#onModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#onMemo").text("");
	            	form.find("input[name=carNo]").val("");
	            });
				$("#OffModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#offMemo").text("");
	            	form.find("input[name=carNo]").val("");
	            });

				// gps链接
				$("#gpsAddress").click(function(){
	            	var gpsAddress = $(this).text();
	            	var carPlateNo = $("#viewCarPlateNo").val();
	            	if(gpsAddress != "" && carPlateNo != ""){
						addTab('车辆监控',car.appPath + '/carMonitor/toCarMonitorListReal.do?carPlateNo='+carPlateNo);
	            	}
				});
				
				//调用点击更多
				car.handleClickMore();
			},
				   //上线操作
		        onFormSub: function () {
		        	var form = $("form[name=onForm]");
					form.ajaxSubmit({
		    			url:car.appPath+"/car/editCarTest.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var code = res.code;
							if(code == "1"){ 
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									$(".bootbox").modal("hide");
									$("#onModal").modal("hide");
									$("#carList").DataTable().ajax.reload(function(){
		    						}); 
								});
							}else{
								 $("#onModal").modal("hide");
								 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
							}
						}
						});
					},
					   //下线操作
					offFormSub: function () {
			        	var form = $("form[name=offForm]");
						form.ajaxSubmit({
							url:car.appPath+"/car/editCarTest.do",
							type : "post",
							dataType:"json", //数据类型  
							success:function(res){
								var msg = res.msg;
								var code = res.code;
								if(code == "1"){ 
								  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									  $(".bootbox").modal("hide");
									  $("#OffModal").modal("hide");
									  $("#carList").DataTable().ajax.reload(function(){
			    						}); 
								  });
								}else{
									 $("#OffModal").modal("hide");
									 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
							},
/*						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							$("span[name='onOffLineReasonNo']").empty();
		
							if (form.find("textarea[name='onOffLineReason']").val()=="") {
								$("span[name='onOffLineReasonNo']").append("<font color='red'>请输入下线备注！</font>");
								return false;
							}
						}*/
							});
						},
			//方法加载
	        operateColumEvent: function(){
	        	$(".car-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("车辆详情",car.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
					});
				});
				$(".car-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("车辆编辑",car.appPath+ "/car/toCarEdit.do?carNo="+$(this).data("id"));			
					});
				});	
				$(".car-operate-Log").each(function(id,obj){
					$(this).on("click",function(){
						addTab("车辆上下线日志",car.appPath+ "/car/toCarOneLineList.do?carNo="+$(this).data("id"));			
					});
				});	
				//下载二维码
				$(".car-operate-qrcode").each(function(id,obj){
					$(this).on("click",function(){
						var carNo=$(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要下载二维码吗？", function(result) {
							if(result){
								window.location.href = car.appPath+ "/car/downloadCarQrcode.do?carNo="+carNo;
							}						
	       				});
					});
				});
				//上线弹出层
				$(".car-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						//上线操作之前，先对车型进行判断，如果该车型拥有计费规则，则允许上线。否则提示，先去添加该车型的计费规则
						var carNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: car.appPath+"/car/carRuleExsit.do",
				             data: {carNo:carNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code=="1"){
				            		 $.ajax({
										 type: "post",
							             url: car.appPath+"/car/toCar.do",
							             data: {carNo:carNo},
							             dataType: "json",
							             success: function(data){
							            	 if(data.code="1"){
							            	    $("#onModal").modal("show");
//							            	    $("body")[0].css('padding-right','0');
							            	    $("body")[0].style.paddingRight=0;
												$("#carNo1").val(data.carNo);
//												debugger
												$("#onMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将车辆"+"“"+data.carPlateNo+"”"+" 上线吗？");
											 }
							             }
									});
				                 }else{
//				                	 debugger;
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
				                	 	console.log(data.msg);
					            	    $("body")[0].style.paddingRight=0;
				                 }	
				             }
						});
						
					});
				});
				//下线弹出层
				$(".car-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var carNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: car.appPath+"/car/toCar.do",
				             data: {carNo:carNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            	    $("#OffModal").modal("show");
									$("#carNo2").val(data.carNo);
									$("#offMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将车辆"+"“"+data.carPlateNo+"”"+" 下线吗？");
				                      }
				             }
						});
					});
				});
	        },
			pageList:function () {	
				var carTpl = $("#carTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carTpl);
				$('#carList').dataTable({
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": car.appPath+'/car/pageListCar.do',
						"data": function ( d ) {
							var form = $("form[name=carSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"moduleName":$.trim(form.find("input[name=moduleName]").val()),
								"deviceSn":$.trim(form.find("input[name=deviceSn]").val()),
	        					"leaseType":form.find("select[name=leaseType]").val(),
	        					"userageStatus":form.find("select[name=userageStatus]").val(),
	        					"onlineStatus":form.find("select[name=onlineStatus]").val(),
	        					"isIdle":form.find("select[name=isIdle]").val()
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
					"columns": [
			            { "title":"车辆编号","data": "carNo" },
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"城市","data": "cityName" },
//						{ "title":"租赁类型","data": "leaseType" },
						//{ "title":"车主","data": "carOwnerName" },	
						{ "title":"上线状态","data": "onlineStatus" },
						{ "title":"使用状态","data": "userageStatus" },
						{ "title":"电/油量","data": "power" },
						{ "title":"里程(km)","data": "mileage" },
						{"title":"信号强度","data":"signalStrengthLevel"},
						//{ "title":"终端序列号","data": "deviceSn" },	
						//{ "title":"终端状态","data": "deviceStatus" },
						{ "title":"终端在线","data": "isOnline" },
						{ "title":"上/下线操作人","data": "workerName" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#cartool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carTools").html("");
//					   $("#carTools").css("padding-left:", "48px");
					   $("#carTools").removeClass("col-xs-6");
		       			$("#carTools").append('<button type="button" class="btn-new-type carTools-operate-add">新增</button>');
		       			$("#carTools").append('<button type="button" class="btn-new-type carTools-operate-import">导入</button>');
		       			$(".carTools-operate-add").on("click",function(){
		       				
		       				addTab("新增车辆", car.appPath+ "/car/toCarAdd.do");
		       				/*$.ajax({
								 type: "post",
					             url: car.appPath+"/car/getCarNum.do",
					             success: function(data){
					            	 if(data.code="1"){
					            	    if(data.data < 32){
					            	    	addTab("新增车辆", car.appPath+ "/car/toCarAdd.do");
					            	    }else{
					            	    	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "您当前的车辆已达上限，请升级后进行操作！");
					            	    }
					                 }
					             }
							});*/
		       				
		       			});	 
		       			$("#carTools").append('<button type="button" class="btn-new-type carTools-operate-export">导出模板</button>');
		       			$("#carTools").append('<button type="button" class="btn-new-type carTools-operate-export-qrcode">批量下载二维码</button>');
		       			/**
		       			 * 导出
		       			 */
		       			$(".carTools-operate-export").on("click",function(){
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出模板吗？", function(result) {
								if(result){
									window.location.href = car.appPath+ "/car/downloadExcelFile.do?filepath=car&newFileName=car.xls";;
								}						
		       				});
		       			});
		       			/**
		       			 * 下载二维码
		       			 */
		       			$(".carTools-operate-export-qrcode").on("click",function(){
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要下载二维码吗？", function(result) {
								if(result){
									var form = $("form[name=carSearchForm]");
									var carPlateNo = $.trim(form.find("input[name=carPlateNo]").val());
//									var deviceSn = $.trim(form.find("input[name=deviceSn]").val());
//		        					var userageStatus = form.find("select[name=userageStatus]").val();
		        					var onlineStatus = form.find("select[name=onlineStatus]").val();
									window.location.href = car.appPath+ "/car/downloadCarQrcodes.do?carPlateNo="+carPlateNo+"&onlineStatus="+onlineStatus;
								}						
		       				});
		       			});
		       			$(".carTools-operate-import").on("click",function(){
		       				bootbox.dialog({
		       			        title: "导入",
		       			        dataType: "json",
		       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='exportForm' name='exportForms' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择要导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
		       			        buttons: {
		       			            "success": {
		       			                "label": "<i class='icon-ok'></i> 保存",
		       			                "className": "	btn-sm btn-success",
		       			                "callback": function() {
		       			                    var exportForm = $("form[name='exportForms']");
		       			                    exportForm.ajaxSubmit({
		       			                        url: car.appPath+ "/car/expotCarAdd.do",
		       			                        type: "post",
		       			                        success: function(res) {
		       			                        	if (res.code != 1) {
		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		       			                        	} else {
		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！"); 
		       			                        	}
		       			                            $("#carList").DataTable().ajax.reload(function(){
		       			                            	
		       			            				});
		       			                        }, 
		       			                        error: function(res) {
		       			                        	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据异常，请检查数据完整性！"); 
		       			                        }
		       			                    });
		       			                }
		       			            },
		       			            "cancel": {
		       			                "label": "<i class='icon-info'></i> 取消",
		       			                "className": "btn-sm btn-danger"
		       			            }
		       			            
		       			        }
		       			    })
		       			});
					},
					"drawCallback": function( settings ) {
						car.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
//						{
//						    "targets": [3],
//						    "render": function(data, type, row, meta) {
//						    	if(data){
//						        if(data==1){
//						        	return "分时";
//						        }else{
//						        	return "长租";
//						        }
//						    	}else{
//						    		return "";
//						    	}
//						    }
//						},
						{
						    "targets": [3],
						    "render": function(data, type, row, meta) {
						        if(data==0){
						        	return "未上线";
						        }else{
						        	return "已上线";
						        }
						    }
						},
						{
						    "targets": [4],
						    "render": function(data, type, row, meta) {
						        if(data==0){
						        	return "空闲";
						        }else if(data==1){
						        	return "已预占";
						        }else if(data==2){
						        	return "订单中";
						        }else if(data==3){
						        	return "调度中";
						        }else if(data==4){
						        	return "维护中";
						        }else{
						        	return "";
						        }
						    	
						    }
						},
						{
						    "targets": [5],
						    "render": function(data, type, row, meta) {
						    	if(data!=null){
						    		return data+"%";
						    	}else{
						    		return "未知";
						    	}
						    }
						},
						{
						    "targets": [9],
						    "render": function(a,b, c, d) {
						    	if(a!=""){
						    		return a;
						        }else{
						        	return "";
						        }
						    }
						},
						//终端状态（1、在线，2、节能，3、待机，4、离线，默认离线）
						/*{
						    "targets": [10],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
							        if(c.deviceStatus==1){
							        	return "在线";
							        }else if(c.deviceStatus==2){
							        	return "节能";
							        }else if(c.deviceStatus==3){
							        	return "待机";
							        }else if(c.deviceStatus==4){
							        	return "离线";
							        }else if(c.deviceStatus==5){
							        	return "休眠";
							        }
						    	}else{
						    		return "";
						    	}
						    }
						},*/
						{
						    "targets": [7],
						    "render": function(a,b, c, d) {
						    	var signal = "";//信号强度（1、非常差 ，2、差，3、一般，4、好  5、非常好）
								if(a!=null){
									if(c.signalStrengthLevel==1){
										signal="非常差";
									}else if(c.signalStrengthLevel==2){
										signal="差";
									}else if(c.signalStrengthLevel==3){
										signal="一般";
									}else if(c.signalStrengthLevel==4){
										signal="好";
									}else if(c.signalStrengthLevel==5){
										signal="非常好";
									}
								}else{
									signal="未知";
								}
								return signal;
						    }
						},
						{
						    "targets": [8],
						    "render": function(a,b, c, d) {
						    	if(c.deviceSn != null && c.deviceSn != ""){
						    		if(c.isOnline==1){
						    			return "在线";
						    		}
						    		return "不在线";
						    	}
						    	return "未绑定";
						    }
						},
						{
							"targets": [10],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon car-operate-find" data-id="'+c.carNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var edit='<span class="glyphicon car-operate-edit" data-id="'+c.carNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
	        					var onList='<span class="glyphicon car-operate-onList" data-id="'+c.carNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">上线</span>';
	        					var offList='<span class="glyphicon car-operate-offList" data-id="'+c.carNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">下线</span>';
	        					var onlineLog='<span class="glyphicon car-operate-Log" data-id="'+c.carNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">上/下线日志</span>';
	        					var op='<span class="glyphicon car-operate-edit" data-id="'+c.carNo+'" data-toggle="tooltip" data-placement="top" title="位置" style="text-decoration: underline; cursor: pointer;">位置</span>';
	        					var qrcode='<span class="glyphicon car-operate-qrcode" data-id="'+c.carNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">下载二维码</span>';
	        					if(c.onlineStatus==0){
	        					return "<div class='table-button-content'>"+find+onlineLog+onList+qrcode+edit+"</div>";
	        					}else{
	        					return "<div class='table-button-content'>"+find+onlineLog+offList+qrcode+"</div>";
	        					}
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCarList").click(function(){
					if($(".seach-input-details").hasClass("close-content")){
						$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
						$(".seach-input-details").removeClass("close-content");
					}else{
						$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
						$(".seach-input-details").addClass("close-content");
					}
				})
			}
	    };
	return car;
});


