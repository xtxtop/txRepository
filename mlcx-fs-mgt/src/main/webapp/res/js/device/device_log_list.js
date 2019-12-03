define([],function() {	
	var deviceLog={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				deviceLog.pageList();
				//查询
				$("#deviceUploadpkgLogSearchForm").click(function(){
					deviceLog.pageList();
	            });
			},
				  
			//方法加载
	        operateColumEvent: function(){
	        	$(".devicelog-operate-find").each(function() {
				$(this).on("click", function() {
					var form = $("form[name=deviceUploadpkgLogSearchForm]");
	   				var createTimeStart = $.trim(form.find("input[name=createTimeStart]").val());
	   				var createTimeEnd = $.trim(form.find("input[name=createTimeEnd]").val());
					addTab("终端设备日志详情",deviceLog.appPath+ "/deviceUploadpkgLog/getDeviceLogView.do?deviceSn="+$(this).data("id")+"&createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd);
				});
			});
				
	        },
			pageList:function () {	
				var deviceOnlineTpl = $("#deviceOnlineTpl").html();
				// 预编译模板
				var template = Handlebars.compile(deviceOnlineTpl);
				$('#deviceOneLineList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": deviceLog.appPath+'/deviceUploadpkgLog/pageDeviceLogList.do',
						"data": function ( d ) {
							var form = $("form[name=deviceUploadpkgLogSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":form.find("input[name=carPlateNo]").val(),
								"deviceSn":form.find("input[name=deviceSn]").val(),
								"cmdType":form.find("input[name=cmdType]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val(),
								"createTimeEnd":form.find("input[name=createTimeEnd]").val(),
								
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
					//车牌号 设备序列号 指令类型 日志原文 中文日志 上报时间 操作列（查看）
					"columns": [
			           
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"设备序列号","data": "deviceSn" },
						{ "title":"操作指令","data": "cmdType" },
						{ "title":"日志原文","data": "logContent" },
						{ "title":"中文日志","data": "chiniseContent" },
						{ "title":"上报时间","data": "createTime" },
						{ "title":"操作","data": "" },
						
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#devicetool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#deviceTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
//					   
					},
					"drawCallback": function( settings ) {
						deviceLog.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               {
				        	            "targets": [0,1],
				        	            "render": function(data, type, row, meta) {
				        	            	if(data){
					        	            	return data;
				        	            	}else{
				        	            		return "";
				        	            	}
				        	            	 
				        	            }
				        	        },
				        	        
				        	        {
									    "targets": [2],
									    "render": function(data, type, row, meta) {
									        if(data=="00"){
									        	return "同步时间请求";
									        }else if(data=="02"){
									        	return "车辆启动提醒";
									        }else if(data=="03"){
									        	return "车辆熄火提醒";
									        }else if(data=="04"){
									        	return "终端准备进入休眠提醒";
									        }else if(data=="13"){
									        	return "节能 模式消息包";
									        }else if(data=="14"){
									        	return "待机 模式消息包";
									        }else if(data=="17"){
									        	return "休眠心跳";
									        }else if(data=="24"){
									        	return "设备实时状态数据包";
									        }else if(data=="36"){
									        	return "上报操作执行返回结果";
									        }else if(data=="33"){
									        	return "上报车辆控制指令的执行结果";
										    }else if(data=="3E"){
									        	return "开关动力控制";
										    }else if(data=="B1"){
										    	return "远程升级指令响应";
										    }else{
										    	return "";
										    }
									    }
									},
				        	        
						{
	        	            "targets": [5],
	        	            "render": function(data, type, row, meta) {
	        	            	if(data){
	        	            		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss');
	        	            	}else{
	        	            		return "";
	        	            	}
	        	            	 
	        	            }
	        	        },
	        	        {
							"targets": [6],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  devicelog-operate-find" data-id="'+c.deviceSn+'" data-toggle="tooltip" data-placement="top">查看</span>';
	        					return find;
	        					
							}
						}
						
					]
				});
			},
			
	    };
	return deviceLog;
});


