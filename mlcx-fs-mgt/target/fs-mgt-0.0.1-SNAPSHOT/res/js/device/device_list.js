define(
		[],
		function() {
			var device = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#deviceSearch").click(function() {
						var form = $("form[name=deviceSearchForm]");
						var bindingTimeStart = form.find("input[name='bindingTimeStart']").val();
						var bindingTimeEnd = form.find("input[name='bindingTimeEnd']").val();
						if(bindingTimeStart!=""&&bindingTimeEnd!=""){
							if(new Date(bindingTimeStart)>new Date(bindingTimeEnd)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{
								device.pageList();
							}
						}else{
							device.pageList();
						}
					});
					//设备启用提交
					$("#enableDeviceBtn").click(function(){
						device.enableDeviceFormSub();
		            });
					//设备停用取消
					$("#enableDeviceCancel").click(function(){
						$("#enableDeviceModel").modal("hide");
		            });
					$("#closeDevice").click(function(){
						closeTab("终端设备详情");
					});
					//设备停用提交
					$("#disableDeviceBtn").click(function(){
						device.disableDeviceFormSub();
		            });
					//设备停用取消
					$("#disableDeviceCancel").click(function(){
						$("#disableDeviceModel").modal("hide");
		            });
					
					$("#enableDeviceModel").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=enableDeviceForm]");
		            	$("#enableDeviceMemo").text("");
		            });
					$("#disableDeviceModel").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=disableDeviceForm]");
		            	$("#disableDeviceMemo").text("");
		            });
					//重置蓝牙名称提交
					$("#resetBlueToothNameModel").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=resetBlueToothNameForm]");
		            	form.find("input[name='deviceNo']").val();
	            		form.find("input[name='bluetoothName']").val();
		            });
					//重置蓝牙名称提交
					$("#resetBlueToothNameBtn").click(function(){
						device.resetBlueToothName();
		            });
					//重置蓝牙名称取消
					$("#resetBlueToothNameBtnCancel").click(function(){
						$("#resetBlueToothNameModel").modal("hide");
		            });
					// 列表页面分页方法调用
					device.pageList();
				},
				//启用操作
				enableDeviceFormSub: function () {
		        	var form = $("form[name=enableDeviceForm]");
					form.ajaxSubmit({
		    			url:device.appPath+"/device/enableDevice.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								$("#enableDeviceModel").modal("hide");
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
									$("#deviceList").DataTable().ajax.reload(function(){});
								});
							}
						}
					});
				},
				//停用操作
				disableDeviceFormSub: function () {
		        	var form = $("form[name=disableDeviceForm]");
					form.ajaxSubmit({
		    			url:device.appPath+"/device/enableDevice.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								$("#disableDeviceModel").modal("hide");
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
									$("#deviceList").DataTable().ajax.reload(function(){});
								}); 
							}
						}
					});
				},
				//重置蓝牙名 
				resetBlueToothName: function () {
					var form = $("form[name=resetBlueToothNameForm]");
            		var deviceNo = form.find("input[name='deviceNo']").val();
            		var bluetoothName = form.find("input[name='bluetoothName']").val();
					$.ajax({
						 url : device.appPath+ "/device/resetBluetoothName.do?deviceNo="+deviceNo+"&bluetoothName="+bluetoothName,
			             success: function(res){
			            	 if(res.code=="1"){
			            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指令发送成功！");
			                 }else if(res.code=="0"){
			                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指令发送失败！");
			                 }else if(res.code=="3"){
			                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
			                 }else{
			                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！");
			                 }
			            	 $("#resetBlueToothNameModel").modal("hide");
			             }
					});
				},
				operateColumEvent : function() {
					//重启设备
					$(".device-operate-restartDevice").each(function(){
						$(this).on("click",function(){
							$.ajax({
								 url : device.appPath+ "/device/restartDevice.do?deviceNo="+$(this).data("id"),
					             success: function(res){
					            	 if(res.code=="1"){
					            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "重启设备指令发送成功！");
					                 }else if(res.code=="0"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "重启设备指令发送失败！");
					                 }else if(res.code=="3"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
					                 }else{
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！");
					                 }
					             }
							});
						});
					});
					
					//设备升级
					$(".device-operate-deviceUpgrade").each(function(){
						$(this).on("click",function(){
							$.ajax({
								 url : device.appPath+ "/device/deviceUpgrade.do?deviceNo="+$(this).data("id"),
					             success: function(res){
					            	 if(res.code=="1"){
					            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "设备升级指令发送成功！");
					                 }else if(res.code=="0"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "设备升级指令发送失败！");
					                 }else if(res.code=="3"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
					                 }else{
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！");
					                 }
					             }
							});
						});
					});
					
					//重启蓝牙
					$(".device-operate-bluetooth").each(function(){
						$(this).on("click",function(){
							$.ajax({
								 url : device.appPath+ "/device/restartDeviceBluetooth.do?deviceNo="+$(this).data("id"),
					             success: function(res){
					            	 if(res.code=="1"){
					            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "重启蓝牙指令发送成功！");
					                 }else if(res.code=="0"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "重启蓝牙指令发送失败！");
					                 }else if(res.code=="3"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
					                 }else{
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！");
					                 }
					             }
							});
						});
					});
					
					//激活动力开关
					$(".device-operate-enablePowerCtrl").each(function(){
						$(this).on("click",function(){
							$.ajax({
								 url : device.appPath+ "/device/enablePowerCtrl.do?deviceNo="+$(this).data("id")+"&enable=1",
					             success: function(res){
					            	 if(res.code=="1"){
					            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指令发送成功！");
					                 }else if(res.code=="0"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指令发送失败！");
					                 }else if(res.code=="3"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
					                 }else{
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！");
					                 }
					             }
							});
						});
					});
					
					//禁用动力开关
					$(".device-operate-disablePowerCtrl").each(function(){
						$(this).on("click",function(){
							$.ajax({
								 url : device.appPath+ "/device/enablePowerCtrl.do?deviceNo="+$(this).data("id")+"&enable=0",
					             success: function(res){
					            	 if(res.code=="1"){
					            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指令发送成功！");
					                 }else if(res.code=="0"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指令发送失败！");
					                 }else if(res.code=="3"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
					                 }else{
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！");
					                 }
					             }
							});
						});
					});
					$(".device-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("终端设备详情",device.appPath+ "/device/getDeviceView.do?terminalDeviceNo="+$(this).data("id"));
						});
					});
					
					
					$(".device-operate-deviceParameter").each(function() {
						$(this).on("click", function() {
							addTab("终端设备参数查询",device.appPath+ "/device/getDeviceParameter.do?terminalDeviceNo="+$(this).data("id"));
						});
					});
					
					//查询报文日志记录
					$(".carStatus-operate-onlineLog").each(function(){
						$(this).on("click",function(){
							addTab("报文日志记录",device.appPath+ "/deviceUploadpkgLog/getDeviceLogView.do?deviceSn="+$(this).data("id"));
						});
					});
					
					$(".device-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("终端设备编辑",device.appPath+ "/device/getDeviceDetail.do?terminalDeviceNo="+$(this).data("id"));
						});
					});
					
					$(".car-operate-view").each(function(){
						$(this).on("click",function(){
							addTab("车辆详情",device.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
						});
					});
					//重置蓝牙名称弹出层
					$(".device-operate-resetBluetoothName").each(function(id){
						$(this).on("click",function(){
							var terminalDeviceNo=$(this).data("id");
							var carPlateNo = $(this).data("id2");
							$.ajax({
								 url : device.appPath+ "/device/getBluetoothName.do?deviceNo="+terminalDeviceNo+"&carPlateNo="+carPlateNo,
					             success: function(res){
					            	 if(res.code=="1"){
					            		 var form = $("form[name=resetBlueToothNameForm]");
					            		$("#resetBlueToothNameModel").modal("show");
					            		form.find("input[name='deviceNo']").val(terminalDeviceNo);
					            		form.find("input[name='bluetoothName']").val(res.data.bluetoothName);
					                 }
					             }
							});
						});
					});
					
					
					
					//启用弹出层
					$(".device-operate-enable").each(function(id){
						$(this).on("click",function(){
							var terminalDeviceNo=$(this).data("id");
							$.ajax({
								 url : device.appPath+ "/device/ajaxText.do",
					             success: function(res){
					            	 if(res.code=="1"){
					            		 $("#enableDeviceModel").modal("show");
										 $("#enableDeviceNo").val(terminalDeviceNo);
										 $("#enableDeviceMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将终端编号为“"+terminalDeviceNo+"”启用吗？");
					                 }
					             }
							});
						});
					});
					//停用弹出层
					$(".device-operate-disable").each(function(id){
						$(this).on("click",function(){
							var terminalDeviceNo=$(this).data("id");
							$.ajax({
								 url : device.appPath+ "/device/ajaxText.do",
					             success: function(res){
					            	 if(res.code=="1"){
					            		$("#disableDeviceModel").modal("show");
										$("#disableDeviceNo").val(terminalDeviceNo);
										$("#disableDeviceMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将终端编号为“"+terminalDeviceNo+"”停用吗？");
					                 }
					             }
							});
						});
					});
				},
				pageList : function() {
					var form = $("form[name=deviceSearchForm]");
					var deviceSn = $.trim(form.find("input[name='deviceSn']").val());
					var simCardNo = $.trim(form.find("input[name='simCardNo']").val());
					var carPlateNo = $.trim(form.find("input[name='carPlateNo']").val());
					var deviceStatus = form.find("select[name='deviceStatus']").val();
					var isAvailable = form.find("select[name='isAvailable']").val();
					var bindingTimeStart = form.find("input[name='bindingTimeStart']").val();
					var bindingTimeEnd = form.find("input[name='bindingTimeEnd']").val();
					var deviceBtnTpl = $("#deviceBtnTpl").html();
					var isOnline= form.find("select[name='isOnline']").val();
					// 预编译模板
					var template = Handlebars.compile(deviceBtnTpl);
					var table = $('#deviceList').dataTable({
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : device.appPath
													+ "/device/pageListDevice.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "deviceSn" :deviceSn,
																 "simCardNo" : simCardNo,
																 "carPlateNo" :carPlateNo,
																 "deviceStatus":deviceStatus,
																 "isAvailable":isAvailable,
																 "isOnline":isOnline,
																 "cityId":form.find("select[name=cityId]").val(),
																 "bindingTimeStart":bindingTimeStart +" 00:00:00",
																 "bindingTimeEnd":bindingTimeEnd +" 23:59:59"
																
																});
											},
											"dataSrc" : function(json) {
												json.recordsTotal = json.rowCount;
												json.recordsFiltered = json.rowCount;
												json.data = json.data;
												return json.data;
											},
											error : function(xhr, error, thrown) {
											}
										},
										"columns" : [ 
										{
											"title" : "终端序列号",
											"data" : "deviceSn"
										},/* {
											"title" : "SIM卡号",
											"data" : "simCardNo"
										},*/
										{
											"title" : "汽车品牌",
											"data" : "carBrandName"
										},
										{
											"title" : "汽车型号",
											"data" : "carSeriesName"
										},
										
										
										{
											"title" : "城市",
											"data" : "cityName"
										}, {
											"title" : "绑定车辆",
											"data" : "carPlateNo"
										}, {
											"title" : "绑定时间",
											"data" : "bindingTime"
										}, {
											"title" : "信号强度",
											"data" : "signalStrengthLevel"
										}, {
											"title" : "最后上报时间",
											"data" : "lastReportingTime"
										},{
											"title" : "终端在线",
											"data" : "isOnline"
										}, {
											"title" : "启用状态",
											"data" : "isAvailable"
										}, {
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#deviceTool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											 $("#deviceTool").html("");
											$("#deviceTool").removeClass("col-xs-6");
											$("#deviceTool").append('<button type="button" class="btn-new-type deviceTools-operate-addDevice">新增设备</button>');
								       			$("#deviceTool").append('<button type="button" class="btn-new-type deviceTools-operate-import">导入</button>');
								       			$("#deviceTool").append('<button type="button" class="btn-new-type deviceTools-operate-export">导入模板下载</button>');
								       			$(".deviceTools-operate-addDevice").on("click",function(){
								       				addTab("新增设备", device.appPath+ "/device/toAddDevice.do");
								       			});
								       			/**
								       			 * 导出
								       			 */
								       			$(".deviceTools-operate-export").on("click",function(){
								       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出模板吗？", function(result) {
														if(result){
															window.location.href = device.appPath+ "/device/downloadExcelFile.do?filepath=device&newFileName=device.xls";;
														}						
								       				});
								       			});
								       			/**
								       			 * 导入
								       			 */
								       			$(".deviceTools-operate-import").on("click",function(){
								       				bootbox.dialog({
								       			        title: "导入",
								       			        dataType: "json",
								       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='importForm' name='importForm' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
								       			        buttons: {
								       			            "success": {
								       			                "label": "<i class='icon-ok'></i> 保存",
								       			                "className": "	btn-sm btn-success",
								       			                "callback": function() {
								       			                    var importForm = $("form[name='importForm']");
								       			                    importForm.ajaxSubmit({
								       			                        url: device.appPath+ "/device/importDevice.do",
								       			                        type: "post",
								       			                        success: function(res) {
								       			                        	if (res.code != 1) {
								       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
								       			                        	} else {
								       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！"); 
								       			                        	}
								       			                            $("#deviceList").DataTable().ajax.reload(function(){
								       			                            	
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
										"drawCallback" : function(settings) {
											device.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
//												{
//													"targets" : [ 5 ],
//													"render" : function(a,
//															b, c, d) {
//														var dStatus;//终端状态（1、在线，2、节能，3、待机，4、离线，5、休眠，默认离线）
//														if(a!=null){
//															if(a==1){
//																dStatus="在线";
//															}else if(a==2){
//																dStatus="节能";
//															}else if(a==3){
//																dStatus="待机";
//															}else if(a==4){
//																dStatus="离线";
//															}else if(a==5){
//																dStatus="休眠";
//															}
//															return dStatus;
//														}else{
//															return "";
//														}
//													}
//												},

													{
														"targets" : [ 2 ],
														"render" : function(a,b, c, d) {
															if(a){
																return '<span class="glyphicon car-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.carNo+ '" >'+a+'</span>';
															}else{
																return "";
															}
																}
													},  


												{
													"targets" : [ 6 ],
													"render" : function(a,
															b, c, d) {
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
														}
														return signal;
													}
												},
												{
													"targets" : [ 5,7 ],
													"render" : function(a,
															b, c, d) {
														if(a!=null){
															var now = moment(a);
															return now.format('YYYY-MM-DD HH:mm:ss');
														}else{
															return "";
														}
													}
												},
												{
													"targets" : 8,
													"render" : function(a,
															b, c, d) {
														if(c.isOnline==1){//终端设备是否在线（1、在线，2、不在线）
															return "在线";
														}else if(c.isOnline==2){
															return "不在线";
														}
														return "";
													}
												},
												{
													"targets" : 9,
													"render" : function(a,
															b, c, d) {
														var available;//是否启用（0、停用，1、启用）
														if(c.isAvailable==0){
															available="停用";
														}else if(c.isAvailable==1){
															available="启用";
														}
														return available;
													}
												},
												{
													targets : 10,
													render : function(a, b, c,
															d) {
														var start = "";
														var stop = "";
														var resetBluetoothName = "";
														var view = '<span class="glyphicon device-operate-view" data-id="'+ c.terminalDeviceNo+ '" >查看</span>';
														var edit = '<span class="glyphicon device-operate-edit" data-id="'+ c.terminalDeviceNo+ '" >编辑</span>';
														var restartDevice = '<span class="glyphicon device-operate-restartDevice"  data-id="'+ c.terminalDeviceNo+ '" >重启设备</span>';
														//var deviceUpgrade = '<span class="glyphicon device-operate-deviceUpgrade"  data-id="'+ c.terminalDeviceNo+ '" >设备升级</span>';
														//var deviceParameter = '<span class="glyphicon device-operate-deviceParameter"  data-id="'+ c.terminalDeviceNo+ '" >终端设备参数查询</span>';
														var onlineLog ='<span class="glyphicon carStatus-operate-onlineLog " data-id="'+c.deviceSn+'" data-toggle="tooltip" data-placement="top">报文日志</span>';
														//var bluetooth = '<span class="glyphicon device-operate-bluetooth" data-id="'+ c.terminalDeviceNo+ '" >重启蓝牙</span>';
//														var enablePowerCtrl = '<span class="glyphicon device-operate-enablePowerCtrl" data-id="'+ c.terminalDeviceNo+ '" >激活动力开关</span>';
//														var disablePowerCtrl = '<span class="glyphicon device-operate-disablePowerCtrl" data-id="'+ c.terminalDeviceNo + '" >禁用动力开关</span>';														
														if(c.isAvailable==0){
															start = '<span class="glyphicon device-operate-enable" data-id="'+ c.terminalDeviceNo+ '" >启用</span>';
														}
														if(c.isAvailable==1){
															stop = '<span class="glyphicon device-operate-disable" data-id="'+ c.terminalDeviceNo + '" >停用</span>';
														}
														if(c.carPlateNo!=null&&c.carPlateNo!=""){
															resetBluetoothName = '<span class="glyphicon device-operate-resetBluetoothName" data-id="'+ c.terminalDeviceNo+ '" data-id2="'+c.carPlateNo+ '" >重置蓝牙名</span>';	
														}
														//if(c.roleAdminTag==1){ 20170109，不再根据此标记做限制，只要进入此页面的，都可以看到
//														return "<div class='table-button-content'>"+view + edit + start + stop + restartDevice + bluetooth + enablePowerCtrl + disablePowerCtrl+resetBluetoothName+"</div>";
														return "<div class='table-button-content'>"+view + restartDevice + edit + start + stop + resetBluetoothName+ onlineLog+"</div>";
														//}else{
														//	return view + edit + start + stop + bluetooth;
														//}
													}
												}]
									});
				},
			};
			return device;
		});

