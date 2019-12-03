define([],function() {	
	var orderPowerLog={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				orderPowerLog.pageList();
				//查询
				$("#orderPowerLogSearchafter").click(function(){
					var form = $("form[name=orderPowerLogSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "提交开始时间不能大于结束时间！");
						}else{
							orderPowerLog.pageList();
						}
					}else{
						orderPowerLog.pageList();
					}
					orderPowerLog.pageList();
	            });
			},
				  
					 
			//方法加载
	        operateColumEvent: function(){
//	        	$(".car-operate-find").each(function(){
//					$(this).on("click",function(){
//						addTab("车辆详情",car.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
//					});
//				});
				
	        },
			pageList:function () {	
				var orderPowerLogTpl = $("#orderPowerLogTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderPowerLogTpl);
				$('#orderPowerLogList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderPowerLog.appPath+'/order/pageListOrderLogs.do',
						"data": function ( d ) {
							var form = $("form[name=orderPowerLogSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"createTimeStart":form.find("input[name=createTimeStart]").val(),
	        					"createTimeEnd":form.find("input[name=createTimeEnd]").val() ,
								"orderNo":form.find("input[name=orderNo]").val(),
	        					
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
			            //{ "title":"车辆编号","data": "carNo" },
						{ "title":"订单号","data": "orderNo" },
						{ "title":"操作时间","data": "createTime" },
						{ "title":"终端编号","data": "deviceNo" },
						{ "title":"开启状态","data": "statusType" },
						{ "title":"会员","data": "memberName" },
						{ "title":"操作类型","data": "cantrolType" },
						//{ "title":"理由","data": "memo" },
						
						
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#cartool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
//					  
					},
					"drawCallback": function( settings ) {
						orderPowerLog.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               {
				        	            "targets": [0,2,4],
				        	            "render": function(data, type, row, meta) {
				        	            	if(data){
					        	            	return data;
				        	            	}else{
				        	            		return "";
				        	            	}
				        	            	 
				        	            }
				        	        },
				        	        {
				        	            "targets": [1],
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
									    "targets": [3],
									    "render": function(data, type, row, meta) {
									        if(data==1){
									        	return "成功";
									        }else{
									        	return "失败";
									        }
									    }
									},
	        	        
						{
						    "targets": [5],
						    "render": function(data, type, row, meta) {
						        if(data==0){
						        	return "关闭";
						        }else{
						        	return "开启";
						        }
						    }
						},
						
					]
				});
			}
	    };
	return orderPowerLog;
});


