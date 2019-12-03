define([],function() {	
	var orderDay={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderDay.pageList();
				//查询
				$("#orderDaySearchafter").click(function(){
					orderDay.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
				
					$(".orderDay-operate-find").on("click",function(){
						addTab("日租订单详情",orderDay.appPath+ "/orderDay/toOrderDayView.do?orderNo="+$(this).data("id"));
					});
			        $(".rent-operate-find").on("click",function(){
						addTab("每日租金详情",orderDay.appPath+ "/orderDayCharging/toOrderDayChargingList.do?orderNo="+$(this).data("id"));
					});
					
					$(".orderDay-operate-onList").each(function() {
						$(this).on("click", function() {
							addTab("车辆监控",orderDay.appPath+ "/carMonitor/toCarMonitorListReal.do?flag=1&carPlateNo="+$(this).data("id"));
						});
					});
					$(".orderDay-operate-carTrack").each(function(){
						$(this).on("click",function(){
							addTab("轨迹回放",orderDay.appPath+ "/carTrack/toCarTrackList.do?carPlateNo="+$(this).data("id")+"&finishTime="+$(this).data("finishtime")+"&createTime="+$(this).data("id2"));
						});
					});
					
						
					$(".orderDay-operate-over").on("click", function() {
						var id=$(this).data("id");
					addTab("订单信息",orderDay.appPath+ "/orderDay/toOrderDayOver.do?orderNo="+id);
					});
					
					$(".orderDay-operate-edit").each(function() {
						var orderNo=$(this).data("id");
						$(this).on("click", function() {
	 
							//判断，该订单的冲账记录有没有未审核的，如果有，则提示该订单还有未审核的冲账记录，先审核。如果没有，则可进行订单冲账的操作。
							 $.ajax({
					    			url:orderDay.appPath+"/order/orderStrikeBalanceCensorTag.do",//
					    			type:"post",
					    			data:{orderNo:orderNo},
					    			dataType:"json",
					    			success:function(res){
					    				var code=res.code;
					    				var data=res.data;
					    				if (code == "1") {
					    					if(data==1){
					    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该订单还有未审核的冲账记录，请先审核", function() {
													$("#orderDayList").DataTable().ajax.reload(function(){});
												});
					    					}else if(data==0){
					    						addTab("日租订单冲账",orderDay.appPath+ "/orderDay/toOrderDayStrikeBalanceAdd.do?orderNo="+orderNo);
					    					}
										}
					    			}
							 });
									  
						});
					});
					
				
	        },
			pageList:function () {	
				var orderDayTpl = $("#orderDayTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderDayTpl);
				$('#orderDayList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderDay.appPath+'/orderDay/pageListOrderDay.do',
						"data": function ( d ) {
							debugger
							var form = $("form[name=orderDaySearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=orderNo]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"startBillingTimeStart":form.find("input[name=startBillingTimeStart]").val()+" 00:00:00",
								"finishTimeEnd":form.find("input[name=finishTimeEnd]").val()+" 23:59:59",
	        					"orderStatus":form.find("select[name=orderStatus]").val(),
	        					"payStatus":form.find("select[name=payStatus]").val(),
	        					"actualTakeLoacton":form.find("input[name=actualTakeLoacton]").val(),
	        					"actualTerminalParkName":form.find("input[name=actualTerminalParkName]").val(),
								
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
			            { "title":"订单编号","data": "orderNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"客户姓名","data": "memberName" },
						{ "title":"取车","data": "actualTakeLoacton" },
						{ "title":"还车","data": "actualTerminalParkName" },
						{ "title":"取车时间","data": "actualPick" },
						{ "title":"还车时间","data": "actualStill" },
						{ "title":"订单状态","data": "orderStatus" },
						{ "title":"保险费用","data": "insurance" },
						{ "title":"押金","data": "deposit" },
						{ "title":"订单金额","data": "orderAmount" },
						{ "title":"应支付金额","data": "payableAmount" },
						{ "title":"支付状态","data": "payStatus" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#orderDaytool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#orderDayTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					    
		       			/**
		       			 * 导出
		       			 */
		       			/*$(".orderDayTools-operate-export").on("click",function(){
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出模板吗？", function(result) {
								if(result){
									window.location.href = orderDay.appPath+ "/orderDay/downloadExcelFile.do?filepath=orderDay&newFileName=orderDay.xls";;
								}						
		       				});
		       			});*/
		       			
					},
					"drawCallback": function( settings ) {
						orderDay.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [6,7],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						        	return now.format('YYYY-MM-DD HH:mm:ss');
						    	}else{
						    		return "";
						    	}
						    	 
						    }
						},
						// 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
						{
						    "targets": [8],
						    "render": function(a,b, c, d) {// 订单状态（0、已预约，1、进行中，2、已完成，3、已取消，默认0）
						    	if(a!=""){
						    		if(c.orderStatus==0){
			        					return "已预约";
			        				}else if(c.orderStatus==1){
			        					return "进行中";
			        				}else if(c.orderStatus==2){
			        					return "已完成";
			        				}else if(c.orderStatus==3){
			        					return "已取消";
			        				}
						        }else{
						        	return "";
						        }
						    }
						},
						 {
						    "targets": [9,10,11,12],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return orderDay.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
						// 支付状态（0、未支付，1、已支付，默认0）
						{
						    "targets": [13],
						    "render": function(a,b, c, d) {
						    		if(a==0){
			        					return "未支付";
			        				}else{
			        					return "已支付";
			        				}
						        
						    }
						},
						
						{
							"targets": [14],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  btn btn-default  orderDay-operate-find" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top">查看详情</span>';
								var rent='<span class="glyphicon  btn btn-default  rent-operate-find" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top">每日租金</span>';
								//var onList='<span class="glyphicon  btn btn-default  orderDay-operate-onList" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top" >车辆位置</span>';
								//var carTrack='<span class="glyphicon  btn btn-default  orderDay-operate-carTrack" data-id="'+c.carPlateNo+'" data-finishtime="'+c.finishTime+'" data-id2="'+c.createTime+'" data-toggle="tooltip" data-placement="top" >轨迹回放</span>';
								var over="";
								var edit="";
								if(c.orderStatus==0 || c.orderStatus==1){
	        					  over='<span class="glyphicon  btn btn-default  orderDay-operate-over" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" >结束订单</span>';
	        					}
								if(c.payStatus==0&&c.orderStatus==2){//已结束未支付
									edit='<span class="glyphicon  btn btn-default  orderDay-operate-edit" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" >订单冲账</span>';
								}
								return find+rent+over+edit;
	        					
							}
						}
					]
				});
			},
	        
	        
	        formatCurrency :function (s,n) { 
	        	n = n > 0 && n <= 20 ? n : 2;  
	        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	        t = "";  
	        for (i = 0; i < l.length; i++) {  
	            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	        }  
	        return t.split("").reverse().join("") + "." + r;  }
	        
	    };
	
	return orderDay;
});


