define([],function() {	
	var carStatus={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				carStatus.pageList();
				//查询
				$("#carStatusSearchafter").click(function(){
					carStatus.pageList();
	            });
				carStatus.handleClickMore();
				
			},
			operateColumEvent:function(){
				
				//打开关门的弹出层
				$(".carStatus-operate-openDoor").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/openDoor.do")
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});
				//打开关门的弹出层
				$(".carStatus-operate-closeDoor").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/closeDoor.do")
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});
				//打开寻车的弹出层
				$(".carStatus-operate-findCar").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/findCar.do")
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});
				//打开启动动力的弹出层
				$(".carStatus-operate-turnOnPower").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/controlPower.do")
						form.find("input[name=cmdValue]").val(1)
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});
				//打开启动动力的弹出层
				$(".carStatus-operate-turnOffPower").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/controlPower.do")
						form.find("input[name=cmdValue]").val(0)
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});				
				//打开设为租赁的弹出层
				$(".carStatus-operate-setLeasingMode").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/setTerminalLeasingMode.do")
						form.find("input[name=cmdValue]").val(1)
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});
				//打开设为非租赁的弹出层
				$(".carStatus-operate-setNotLeasingMode").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/setTerminalLeasingMode.do")
						form.find("input[name=cmdValue]").val(0)
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});				
				//打开设置钥匙使能的弹出层
				$(".carStatus-operate-setKeyEnable").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/setKeyEnableMode.do")
						form.find("input[name=cmdValue]").val(1)
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});
				//打开设置钥匙禁用的弹出层
				$(".carStatus-operate-setKeyUnable").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carOperateForm]");
						form.find("input[name=subUrl]").val("/carStatus/setKeyEnableMode.do")
						form.find("input[name=cmdValue]").val(0)
						$(".operateModal").html($(this).html());
						form.find("input[name=deviceNo]").val($(this).data("id"))
						$("#operateModal").modal("show");
					});
				});								
				//车辆操作提交
				$("#carOperateBtn").click(function(){
					carStatus.carOperateFormSub();
	            });
				//车辆操作取消
				$("#carOperateCancel").click(function(){
					var form = $("form[name=carOperateForm]");
					$(".operateModal").html("");
					form.find("input[name=deviceNo]").val("")
					form.find("input[name=cmdValue]").val("")
					form.find("input[name=subUrl]").val("")
					form.find("[name=memo]").val("")
					var memoAdd = form.find("[name=memoAdd]").html("");
					$("#operateModal").modal("hide");
	            });
				$("#carOperateClose").click(function(){
					$("#carOperateCancel").click();
	            });
				//远程开门
				/*$(".carStatus-operate-openDoor").each(function(){
					$(this).on("click",function(){
						$.ajax({
							 url : carStatus.appPath+ "/carStatus/openDoor.do?deviceNo="+$(this).data("id"),
				             success: function(res){
				            	 if(res.code=="1"){
				            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开门指令发送成功！");
				                 }else if(res.code=="0"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开门指令发送失败！");
				                 }else if(res.code=="3"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
				                 }else{
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！");
				                 }
				             }
						});
					});
				});*/
				
				//远程关门
				/*$(".carStatus-operate-closeDoor").each(function(){
					$(this).on("click",function(){
						$.ajax({
							 url : carStatus.appPath+ "/carStatus/closeDoor.do?deviceNo="+$(this).data("id"),
				             success: function(res){
				            	 if(res.code=="1"){
				            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关门指令发送成功！");
				                 }else if(res.code=="0"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关门指令发送失败！");
				                 }else if(res.code=="3"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！"); 
				                 }
				             }
						});
					});
				});*/
				//远程寻车
				/*$(".carStatus-operate-findCar").each(function(){
					$(this).on("click",function(){
						$.ajax({
							 url : carStatus.appPath+ "/carStatus/findCar.do?deviceNo="+$(this).data("id"),
				             success: function(res){
				            	 if(res.code=="1"){
				            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "寻车指令发送成功！");
				                 }else if(res.code=="0"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "寻车指令发送失败！");
				                 }else if(res.code=="3"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！"); 
				                 }
				             }
						});
					});
				});*/
				//地图上显示车辆位置   
				$(".showCarLotion").each(function(){
					$(this).on("click",function(){
						addTab("车辆监控",carStatus.appPath+ "/carMonitor/toCarMonitorListReal.do?flag=1&carPlateNo="+$(this).data("id"));
					});
				});
				//地图上显示车辆轨迹
				$(".carStatus-operate-track").each(function(){
					$(this).on("click",function(){
						addTab("轨迹回放",carStatus.appPath+ "/carTrack/toCarTrackList.do?flag=1&carPlateNo="+$(this).data("id"));
					});
				});
				
				
				$(".car-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("车辆详情",carStatus.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
					});
				});
				
				
				
				//查询用车记录
				$(".carStatus-operate-record").each(function(){
					$(this).on("click",function(){
						addTab("用车记录",carStatus.appPath+ "/carRecord/toCarRecordList.do?carPlateNo="+$(this).data("id"));
					});
				});
				
				//查询用车指令记录
				$(".carStatus-operate-record2").each(function(){
					$(this).on("click",function(){
						addTab("用车指令记录",carStatus.appPath+ "/carOperateRecord/toCarOperateRecordList.do?carPlateNo="+$(this).data("id"));
					});
				});
				//查询报文日志记录
				$(".carStatus-operate-onlineLog").each(function(){
					$(this).on("click",function(){
						addTab("报文日志记录",carStatus.appPath+ "/deviceUploadpkgLog/getDeviceLogView.do?carPlateNo="+$(this).data("id"));
					});
				});
				//开启动力
				/*$(".carStatus-operate-turnOnPower").each(function(){
					$(this).on("click",function(){
						$.ajax({
							 url : carStatus.appPath+ "/carStatus/controlPower.do?deviceNo="+$(this).data("id")+"&cmdValue=1",
				             success: function(res){
				            	 if(res.code=="1"){
				            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开启动力指令发送成功！");
				                 }else if(res.code=="0"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开启动力指令发送失败！");
				                 }else if(res.code=="3"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！"); 
				                 }
				             }
						});
					});
				});*/
				//关闭动力
				/*$(".carStatus-operate-turnOffPower").each(function(){
					$(this).on("click",function(){
						$.ajax({
							 url : carStatus.appPath+ "/carStatus/controlPower.do?deviceNo="+$(this).data("id")+"&cmdValue=0",
				             success: function(res){
				            	 if(res.code=="1"){
				            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭动力指令发送成功！");
				                 }else if(res.code=="0"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭动力指令发送失败！");
				                 }else if(res.code=="3"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！"); 
				                 }
				             }
						});
					});
				});*/
			},carOperateFormSub: function () {
	        	var form = $("form[name=carOperateForm]");
				var memo = form.find("[name=memo]").val()
				if(memo == null || memo == ""){
					var memoAdd = form.find("[name=memoAdd]");
					memoAdd.html("<font color='red'>请输入备注！</font>")
					return
				}
				form.ajaxSubmit({
					url:carStatus.appPath+ form.find("input[name=subUrl]").val(),
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						if(res.code=="1"){
							$("#carOperateCancel").click();
		            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指令发送成功！");
		                 }else if(res.code=="0"){
		                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + (res.msg? res.msg : "指令发送失败！"));
		                 }else if(res.code=="3"){
		                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！"); 
		                 }
					},
			});},
			excel:function(){
				var form = $("form[name=carStatusSearchForm]");
				var carPlateNo = $.trim(form.find("input[name=carPlateNo]").val());
   				var deviceSn = $.trim(form.find("input[name=deviceSn]").val());
   				var userageStatus = form.find("select[name=userageStatus]").val();
   				var onlineStatus = form.find("select[name=onlineStatus]").val();
   				var powerSmall = $.trim(form.find("input[name=powerSmall]").val());
   				var powerBig = $.trim(form.find("input[name=powerBig]").val());
   				//var mileageSmall = form.find("input[name=mileageSmall]").val();
   				//var mileageBig = form.find("input[name=mileageBig]").val();
   				
   				
   				
				//window.location.href = carStatus.appPath+"/carStatus/toCarStatusExport.do?carPlateNo="+carPlateNo+"&deviceSn="+deviceSn+"&userageStatus="+userageStatus+"&onlineStatus="+onlineStatus+"&powerSmall="+powerSmall+"&powerBig="+powerBig+"&mileageSmall="+mileageSmall+"&mileageBig="+mileageBig;

				window.location.href = carStatus.appPath+"/carStatus/toCarStatusExport.do?carPlateNo="+carPlateNo+"&deviceSn="+deviceSn+"&userageStatus="+userageStatus+"&onlineStatus="+onlineStatus+"&powerSmall="+powerSmall+"&powerBig="+powerBig;
			},
			pageList:function () {	
				var carStatusTpl = $("#carStatusTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carStatusTpl);
				$('#carStatusList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carStatus.appPath+'/carStatus/pageListCarStatus.do',
						"data": function ( d ) {
							var form = $("form[name=carStatusSearchForm]");
							var power=form.find("input[name=power]").val();
							var powerBig=form.find("input[name=powerBig]").val();
							if((power!=null&&parseInt(power)<0)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "电量前框需大于等于0！");
								return false;
							}
							if(power!=null&&powerBig!=null&&parseInt(power)>parseInt(powerBig)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "电量前框不能大于电量后框！");
								return false;
							}
							if((powerBig!=null&&parseInt(powerBig)>100)||(powerBig!=null&&parseInt(powerBig)<0)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "电量后框需大于前框并小于等于100！");
								return false;
							}
							var mileage=form.find("input[name=mileage]").val();
							var mileageBig=form.find("input[name=mileageBig]").val();
							if((mileage!=null&&parseInt(mileage)<0)||(mileageBig!=null&&parseInt(mileageBig)<0)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "里程输入有误！");
								return false;
							}
							if(mileage!=null&&mileageBig!=null&&parseInt(mileage)>parseInt(mileageBig)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "里程前框不能大于里程后框！");
								return false;
							}
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":form.find("input[name=carPlateNo]").val(),
								"deviceSn":form.find("input[name=deviceSn]").val(),
	        					"userageStatus":form.find("select[name=userageStatus]").val(),
	        					"powerSmall":form.find("input[name=powerSmall]").val(),
	        					"rangeMileage":form.find("input[name=rangeMileage]").val(),
	        					"powerBig":form.find("input[name=powerBig]").val(),
	        					"mileageSmall":form.find("input[name=mileageSmall]").val(),
	        					"mileageBig":form.find("input[name=mileageBig]").val(),
	        					"onlineStatus":form.find("select[name=onlineStatus]").val(),
	        					"carStatus":form.find("select[name=carStatus]").val(),
	        					"isMinLowPower":form.find("select[name=isMinLowPower]").val(),
	        					"isLowPower":form.find("select[name=isLowPower]").val(),
	        					"isBrokenLine":form.find("select[name=isBrokenLine]").val()
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
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"租赁类型","data": "leaseType" },
//						{ "title":"车主","data": "carOwnerName" },
						{ "title":"车辆状态","data": "carStatus" },
						{ "title":"上线状态","data": "onlineStatus" },
						{ "title":"使用状态","data": "userageStatus" },
						{ "title":"电/油量","data": "power" },
						{ "title":"电瓶电压","data": "auxBatteryVoltage" },
						{ "title":"续航(Km)","data": "rangeMileage" },
						{ "title":"总里程(km)","data": "mileage" },
//						{ "title":"位置","data": "position" },
						/*{ "title":"终端序列号","data": "deviceSn" },*/
						{"title":"信号强度","data":"signalStrengthLevel"},
						{ "title":"终端在线","data": "isOnline" },
//						{ "title":"终端状态","data": "deviceStatus" },	
						{ "title":"最后上报时间","data": "lastReportingTime" },	
						{ "title":"订单最后结束时间","data": "orderFinishTime" },
						{ "title":"定位状态","data": "locationType" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carStatustool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carStatusTools.seacher-button-content'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carStatusTools").html("");
					   $("#carStatusTools").removeClass("col-xs-6");
		       			$("#carStatusTools").append('<button type="button" class="btn-new-type carStatusTools-operate-export">导出</button>');
		       			$(".carStatusTools-operate-export").on("click",function(){		   					
	       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
	       						if(result){
	       							carStatus.excel();
	       						}
	       					});	
	       			}); 	     			
					},
					"drawCallback": function( settings ) {
						carStatus.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets" : [ 0 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return '<span class="glyphicon car-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.carNo+ '" >'+a+'</span>';
								}else{
									return "";
								}
							}
						},
						
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						        if(c.leaseType==1){
						        	return "分时";
						        }else{
						        	return "长租";
						        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						        if(a==1){
						        	if(c.chargeState==1){
						        		return "已启动(正在充电)";
						        	}else{
						        		return "已启动(未充电)";
						        	}
						        
						        }else{
						        	if(c.chargeState==1){
						        		return "已熄火(正在充电)";
						        	}else{
						        		return "已熄火(未充电)";
						        	}
						        	
						        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(c.onlineStatus==0){
						    			return "下线";
						    		}else{
						    			return "上线";
						    		}
						        }else{
						        	return "";
						        }
						    }
						},
						
						{
						    "targets": [5],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						          if(c.userageStatus==0){
						        	return "空闲";
						        }else if(c.userageStatus==1){
						        	return "已预占";
						        }else if(c.userageStatus==2){
						        	return "订单中";
						        }else if(c.userageStatus==3){
						        	return "调度中";
						        }
						      }else{
							        return "";
							        }
						    }
						},
						{
						    "targets": [6],
						    "render": function(data, type, row, meta) {
						    	if(data !=null){
						    		return data+"%";
						    	}else{
						    		return 0+"%";
						    	}
						    }
						},{
						    "targets": [8,9],
						    "render": function(a,b, c, d) {
						    	if(a!=""){
						    		return a;
						        }else{
						        	return "";
						        }
						    }
						},
//						{
//						    "targets": [9],
//						    "render": function(data, type, row, meta) {
//						        	return data+'<img class="showCarLotion" data-id="'+row.carPlateNo+'" src="'+carStatus.appPath+'/res/img/car/position.png" width=20 height=20 style="cursor:pointer"/>';
//						    }
//						},
					
						{
						    "targets": [10],
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
								}
								return signal;
						    }
						},{
						    "targets": [11],
						    "render": function(a,b, c, d) {
						    	var text = "未绑定"
						    	if(c.deviceSn != null && c.deviceSn != ""){
						    		if(c.isOnline==1){
						    			text = "在线"
						    		}else if (c.isOnline==2){
						    			text = "不在线"
						    		}
						    		if(c.deviceModel != null && c.deviceModel != ""){
					    				text += "("+ c.deviceModel +")";
					    			}
						    	}
						    	return text;
						    }
						},
						//1、在线，2、节能，3、待机，4、离线，5、休眠，
//						{
//						    "targets": [10],
//						    "render": function(a,b, c, d) {
//						    	if(a!=null){
//						        if(c.deviceStatus==1){
//						        	return "在线";
//						        }else if(c.deviceStatus==2){
//						        	return "节能";
//						        }else if(c.deviceStatus==3){
//						        	return "待机";
//						        }else if(c.deviceStatus==4){
//						        	return "离线";
//						        }else if(c.deviceStatus==5){
//						        	return "休眠";
//						        }
//						    	}else{
//						    		return "";
//						    	}
//						    }
//						},
						{
						    "targets": [12],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    	var now = moment(a); 
	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [13],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    	var now = moment(a); 
	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    }
						},
						
						{
						    "targets": [14],
						    "render": function(a,b, c, d) {
						    	var locationType = "";
								if(a!=null){
									if(c.locationType==0){
										locationType="未定位";
									}else if(c.locationType==1){
										locationType="定位";
									}
								}
								return locationType;
						    }
						},
						
						{
							"targets": [15],
							"render": function (a, b, c, d) {
//var onlineLog ='<span class="span-style" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">报文日志</span>';
////								var lookLocation = data+'<img class="showCarLotion" data-id="'+row.carPlateNo+'" src="'+carStatus.appPath+'/res/img/car/position.png" width=20 height=20 style="cursor:pointer"/>';
//var location='<span class="span-style" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">查看位置</span>';
//var track='<span class="span-style" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">轨迹回放</span>';
//var record='<span class="span-style" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">用车记录</span>';
//var openDoor='<span class="span-style" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">开门</span>';
//var closeDoor='<span class="span-style" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">关门</span>';
//var findCar='<span class="span-style" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">寻车</span>';
//var turnOnPower='<span class="span-style" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">开启动力</span>';
//var turnOffPower='<span class="span-style" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">关闭动力</span>';
//var record2='<span class="span-style" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">指令记录</span>';

var onlineLog ='<span class="glyphicon carStatus-operate-onlineLog " data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">报文日志</span>';
//								var lookLocation = data+'<img class="showCarLotion" data-id="'+row.carPlateNo+'" src="'+carStatus.appPath+'/res/img/car/position.png" width=20 height=20 style="cursor:pointer"/>';
var location='<span class="span-style glyphicon carStatus-operate-location showCarLotion" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">查看位置</span>';
var track='<span class="span-style glyphicon  carStatus-operate-track" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">轨迹回放</span>';
var record='<span class="span-style glyphicon carStatus-operate-record" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">用车记录</span>';
var openDoor='<span class="span-style glyphicon carStatus-operate-openDoor" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">开门</span>';
var closeDoor='<span class="span-style glyphicon carStatus-operate-closeDoor" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">关门</span>';
var findCar='<span class="span-style glyphicon carStatus-operate-findCar" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">寻车</span>';
var turnOnPower='<span class="span-style glyphicon  carStatus-operate-turnOnPower" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">开启动力</span>';
var turnOffPower='<span class="span-style glyphicon  carStatus-operate-turnOffPower" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">关闭动力</span>';
var record2='<span class="span-style glyphicon  carStatus-operate-record2" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">指令记录</span>';
//var setLeasingMode='<span class="span-style glyphicon  carStatus-operate-setLeasingMode" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">设为租赁</span>';
//var setNotLeasingMode='<span class="span-style glyphicon  carStatus-operate-setNotLeasingMode" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">设为非租赁</span>';
//var setKeyEnable='<span class="span-style glyphicon  carStatus-operate-setKeyEnable" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">钥匙使能</span>';
//var setKeyUnable='<span class="span-style glyphicon  carStatus-operate-setKeyUnable" data-id="'+c.deviceNo+'" data-toggle="tooltip" data-placement="top">钥匙禁用</span>';

								
								/*if(c.roleAdminTag==0){
									return location+track+openDoor+closeDoor+findCar+record+onlineLog+record2;
								}else{*/
								return "<div class='table-button-content'>"+openDoor+onlineLog+findCar+record+closeDoor+track+location+turnOnPower+turnOffPower+record2+"</div>"
								/*}*/
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCarStatus").click(function(){
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
	return carStatus;
});


