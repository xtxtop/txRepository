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
				$(".orderDay-operate-assign").each(function(){
					$(this).on("click",function(){
						addTab("指派订单",orderDay.appPath+ "/orderDay/toAssignOrderDay.do?orderNo="+$(this).data("id"));
					});
				});
				
				$(".orderDay-operate-cancel").on("click",function(){
					var takeCarDoorAmount = $("#takeCarDoorAmount").val();
					var orderNo = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要取消吗？取消之后收取"+takeCarDoorAmount+"元违约金", function(result) {
						if(result){
							$.post("orderDay/cancelOrderDay.do",{orderNo:orderNo},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "取消成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#orderDayList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
								
							});
						}						
       				});
					
				});
				
				
				$(".orderDay-operate-menCancel").on("click",function(){
					var cancelAmount = $("#cancelAmount").val();
					var orderNo = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要取消吗？取消之后收取"+cancelAmount+"元违约金", function(result) {
						if(result){
							$.post("orderDay/menCancelOrderDay.do",{orderNo:orderNo},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "取消成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#orderDayList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
								
							});
						}						
       				});
					
				});
				
				
				
				
				
				$(".orderDay-operate-over").on("click", function() {
					var id=$(this).data("id");
					addTab("订单信息",orderDay.appPath+ "/orderDay/toOrderDayOver.do?orderNo="+id);
				});
				
				$(".orderDay-operate-change").each(function() {
					var orderNo=$(this).data("id");
					$(this).on("click", function() {
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定此订单要换车吗？", function(result) {
							if(result){
								 $.ajax({
					    			url:orderDay.appPath+"/merchantOrder/confirmChangeCar.do",
					    			type:"post",
					    			data:{orderNo:orderNo},
					    			dataType:"json",
					    			success:function(res){
					    				var code = res.code;
					    				var msg = res.msg
					    				if (code == "1") {
					    					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
												$("#orderDayList").DataTable().ajax.reload(function(){});
											});
										}else {
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
										}
					    			}
								 });
							}
						})
								  
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
	        					"orderStatus":form.find("select[name=orderStatus]").val(),
	        					"payStatus":form.find("select[name=payStatus]").val(),
	        					"merchantId":form.find("select[name=merchantId]").val()
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
						{ "title":"接单状态","data": "merchantOrderStatus" },
						{ "title":"租赁商","data": "merchantName" },
						{ "title":"客户姓名","data": "memberName" },
						{ "title":"联系电话","data": "mobilePhone" },
						{ "title":"开始时间","data": "startBillingTime" },
						{ "title":"结束时间","data": "finishTime" },
						{ "title":"取车点","data": "actualTakePakeName" },
						{ "title":"还车点","data": "actualTerminalParkName" },
						{ "title":"订单状态","data": "orderStatus" },
						{ "title":"押金预授权","data": "deposit" },
						{ "title":"订单费用","data": "orderAmount" },
						{ "title":"已付费用","data": "amountPaid" },
						{ "title":"应付","data": "payableAmount" },
						{ "title":"支付状态","data": "payStatus" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#orderDayTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
		       			
					},
					"drawCallback": function( settings ) {
						orderDay.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						// 商家订单状态（0:待签订单，1：接受订单，2：拒绝订单，3：交车，4：换车，5：验车）
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(a==0){
			        					return "待处理";
			        				}else if(a==1){
			        					return "接受订单";
			        				}else if(a==2){
			        					return "拒绝订单";
			        				}else if(a==3){
			        					return "交车";
			        				}else if(a==4){
			        					return "换车";
			        				}else if(a==5){
			        					return "验车";
			        				}else if(a==6){
			        					return "取消";
			        				}else if(a==7){
			        					return "待换车";
			        				}else{
			        					return "";
			        				}
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [6],
						    "render": function(a,b, c, d) {
					    		if(c.orderStatus==1||c.orderStatus==4){
					    			var now = moment(c.appointmentTakeTime); 
					    			return now.format('YYYY-MM-DD HH:mm:ss');
		        				}else{
		        					if(c.actualTakeTime){
		        						var now = moment(c.actualTakeTime); 
		        						return now.format('YYYY-MM-DD HH:mm:ss');
		        					}else{
		        						var now = moment(c.appointmentTakeTime); 
						    			return now.format('YYYY-MM-DD HH:mm:ss');
		        					}
		        				}
						    }
						},
						{
						    "targets": [7],
						    "render": function(a,b, c, d) {
					    		if(c.orderStatus==1||c.orderStatus==4){
					    			var now = moment(c.appointmentReturnTime); 
					    			return now.format('YYYY-MM-DD HH:mm:ss');
		        				}else if(c.orderStatus==2){
		        					if(c.actualTakeTime!=null){
		        						var now = moment(c.actualTakeTime); 
		        						return now.format('YYYY-MM-DD HH:mm:ss');
		        					}else{
		        						var now = moment(c.appointmentReturnTime); 
		        						return now.format('YYYY-MM-DD HH:mm:ss');
		        					}
		        				}else{
		        					if(c.actualReturnTime!=null){
		        						var now = moment(c.actualReturnTime); 
		        						return now.format('YYYY-MM-DD HH:mm:ss');
		        					}else{
		        						var now = moment(c.appointmentReturnTime); 
			        					return now.format('YYYY-MM-DD HH:mm:ss');
		        					}
		        				}
						    }
						},
						{
						    "targets": [8],
						    "render": function(a,b, c, d) {
						    	if(c.actualTakePakeName!=null){
	        						var parkName = c.actualTakePakeName;
	        						return parkName;
	        					}else{
	        						return c.startParkName;
	        					}
						    }
						},
						{
						    "targets": [9],
						    "render": function(a,b, c, d) {
					    		if(c.orderStatus==1||c.orderStatus==4){
					    			var parkName = c.returnParkName;
					    			return parkName;
		        				}else{
		        					var parkName = c.actualTerminalParkName;
		        					if(parkName!=null){
						    			return parkName;
		        					}else{
						    			return c.returnParkName;
		        					}
		        				}
						    }
						},
						// 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
						{
						    "targets": [10],
						    "render": function(a,b, c, d) {// 订单状态（0、已预约，1、进行中，2、已完成，3、已取消，默认0）
						    	if(a!=""){
						    		if(c.orderStatus==1){
			        					return "已预约";
			        				}else if(c.orderStatus==2){
			        					return "进行中";
			        				}else if(c.orderStatus==3){
			        					return "已完成";
			        				}else if(c.orderStatus==4){
			        					return "已取消";
			        				}
						        }else{
						        	return "";
						        }
						    }
						},
						 {
						    "targets": [11,12,13],
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
						    "targets": [15],
						    "render": function(a,b, c, d) {
						    		if(a==0){
			        					return "未支付";
			        				}else{
			        					return "已支付";
			        				}
						        
						    }
						},
						
						{
							"targets": [16],
							"render": function (a, b, c, d) {
								var show = "";
								var change = '<span class="glyphicon orderDay-operate-change" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top">换车</span>';
								var find='<span class="glyphicon orderDay-operate-find" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top">查看详情</span>';
								var over="";
								var assign = '<span class="glyphicon orderDay-operate-assign" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top">指派</span>';
								var cancel = ""; 
								if(c.orderStatus==2){
									over='<span class="glyphicon orderDay-operate-over" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" >结束订单</span>';
									show=find+over;
	        					}else{
	        						show=find;
	        					}
								if(c.orderStatus==1&&c.merchantOrderStatus==2){
									show=find+over+assign;
								}
								if((c.orderStatus==1&&c.merchantOrderStatus==1)||(c.orderStatus==2&&c.merchantOrderStatus==4)){
									show=find+over+change;
								}
								if(c.payStatus==1&&c.merchantOrderStatus==1){
									cancel='<span class="glyphicon  btn btn-default  orderDay-operate-cancel" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" >取消订单</span>';
									show=find+cancel;
								}
								if(c.payStatus==1 && c.orderStatus==1 && c.startParkNo != null){
									mcancel='<span class="glyphicon  btn btn-default  orderDay-operate-menCancel" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" >取消订单</span>';
									show=find+mcancel;
								}
								return show;
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


