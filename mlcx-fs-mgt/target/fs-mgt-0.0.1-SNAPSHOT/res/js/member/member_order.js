define([],function() {	
	var order={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				order.pageList();
				 
				//查询
				$("#orderSearchafter").click(function(){
					var form=$("form[name=orderSearchForm]");
					var startBillingTimeStart=form.find("input[name=startBillingTimeStart]").val();
					var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
					if(startBillingTimeStart!=null&&startBillingTimeStart!=""){
						if(finishTimeEnd!=null&&finishTimeEnd!=""){
							if(startBillingTimeStart>finishTimeEnd){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{ 
								order.pageList();
							}
						}
					}else{
						order.pageList();
					}
					
	            });
				
				
			},
			operateColumEvent : function() {
				$(".order-operate-find").each(function() {
					$(this).on("click", function() {
						addTab("订单详情",order.appPath+ "/order/toOrderView.do?orderNo="+$(this).data("id"));
					});
				});
				$(".order-operate-edit").each(function() {
					var orderNo=$(this).data("id");
					$(this).on("click", function() {
 
						//判断，该订单的冲账记录有没有未审核的，如果有，则提示该订单还有未审核的冲账记录，先审核。如果没有，则可进行订单冲账的操作。
						 $.ajax({
				    			url:order.appPath+"/order/orderStrikeBalanceCensorTag.do",//
				    			type:"post",
				    			data:{orderNo:orderNo},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var data=res.data;
				    				if (code == "1") {
				    					if(data==1){
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该订单还有未审核的冲账记录，请先审核", function() {
												$("#orderList").DataTable().ajax.reload(function(){});
											});
				    					}else if(data==0){
				    						addTab("订单冲账",order.appPath+ "/order/toOrderStrikeBalanceAdd.do?orderNo="+orderNo);
				    					}
									}
				    			}
						 });
								  
					});
				});
				//地图上显示车辆轨迹
				$(".order-operate-carTrack").each(function(){
					$(this).on("click",function(){
						addTab("轨迹回放",order.appPath+ "/carTrack/toCarTrackList.do?carPlateNo="+$(this).data("id")+"&finishTime="+$(this).data("finishtime")+"&createTime="+$(this).data("id2"));
					});
				});
				$(".order-operate-onList").each(function() {
					$(this).on("click", function() {
						addTab("车辆监控",order.appPath+ "/carMonitor/toCarMonitorListReal.do?flag=1&carPlateNo="+$(this).data("id"));
					});
				});
				$(".order-operate-over").each(function() {
					var id=$(this).data("id");
					$(this).on("click", function() {
					addTab("订单信息",order.appPath+ "/order/toOrderOver.do?orderNo="+id);
					});
//					var id=$(this).data("id");
//					$(this).on("click", function() {
//						 $.ajax({
//				    			url:order.appPath+"/order/orderOverNormal.do",//订单正常结束（暂时）
//				    			type:"post",
//				    			data:{orderNo:id},
//				    			dataType:"json",
//				    			success:function(res){
//				    				var code=res.code;
//				    				if (code == "1") {
//										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "结束订单操作成功！", function() {
//											$("#orderList").DataTable().ajax.reload(function(){});
//										});
//									} else {
//										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "结束订单操作失败！");
//									}
//				    			}
//					});
//				});
			});
			},
			pageList:function () {	
				var orderTpl = $("#orderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderTpl);
				$('#orderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": order.appPath+'/order/pageListOrder.do',
						"data": function ( d ) {
							var form = $("form[name=orderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=orderNo]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"startBillingTimeStart":form.find("input[name=startBillingTimeStart]").val()+" 00:00:00",
								"finishTimeEnd":form.find("input[name=finishTimeEnd]").val()+" 23:59:59",
	        					"orderStatus":form.find("select[name=orderStatus]").val(),
	        					"payStatus":form.find("select[name=payStatus]").val(),
	        					"companyName":form.find("input[name=companyName]").val(),
	        					"actualTakeLoacton":form.find("input[name=actualTakeLoacton]").val(),
	        					"actualTerminalParkName":form.find("input[name=actualTerminalParkName]").val()
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
					/*"columns": [
			            { "title":"订单编号","data": "orderNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"车型","data": "carModelName" },
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"客户姓名","data": "memberName" },
						{ "title":"手机号码","data": "mobilePhone" },
						{ "title":"集团名称","data": "companyName" },
						{ "title":"开始时间","data": "startBillingTime" },
						{ "title":"结束时间","data": "finishTime" },
						{ "title":"取车场站/地点","data": "isTakeInPark" },
						{ "title":"还车场站/地点","data": "isReturnInPark" },
						{ "title":"订单状态","data": "orderStatus" },
						{ "title":"订单金额(元)","data": "orderAmount" },
						{ "title":"应支付金额(元)","data": "payableAmount" },
						{ "title":"已支付金额(元)","data": "payableAmount" },
						{ "title":"已抵扣金额(元)","data": "packMinutesDiscountAmount" },
						{ "title":"支付状态","data": "payStatus" }
					],*/
					"columns": [
						{ "title":"订单号","data": "orderNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"车型","data": "carModelName" },
						{ "title":"车牌","data": "carPlateNo" },
						{ "title":"客户","data": "memberName" },
						{ "title":"手机","data": "mobilePhone" },
						{ "title":"集团","data": "companyName" },
						{ "title":"开始","data": "startBillingTime" },
						{ "title":"结束","data": "finishTime" },
						{ "title":"取车","data": "isTakeInPark" },
						{ "title":"还车","data": "isReturnInPark" },
						{ "title":"订单状态","data": "orderStatus" },
						{ "title":"金额","data": "orderAmount" },
						{ "title":"应支付","data": "payableAmount" },
						{ "title":"已支付","data": "payableAmount" },
						{ "title":"已抵扣","data": "packMinutesDiscountAmount" },
						{ "title":"支付状态","data": "payStatus" },
					],
                    // "dom": "<'row'<'col-xs-2'l><'#ordertool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#orderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#orderTools").html("");
		       			$("#orderTools").append('<button type="button" class="btn btn-default btn-sm orderTools-operate-export">导出Excel</button>');
		       			$(".orderTools-operate-export").on("click",function(){
		       				var form = $("form[name=orderSearchForm]");
		       				
		       				var orderNo = $.trim(form.find("input[name=orderNo]").val());
		       				var memberName = $.trim(form.find("input[name=memberName]").val());
		       				var mobilePhone =form.find("input[name=mobilePhone]").val();
		       				var carPlateNo = form.find("input[name=carPlateNo]").val();
		       				var startBillingTimeStart = form.find("input[name=startBillingTimeStart]").val()+" 00:00:00";
		       				var finishTimeEnd = form.find("input[name=finishTimeEnd]").val()+" 23:59:59";
		       				var orderStatus = form.find("select[name=orderStatus]").val();
		       				var payStatus = form.find("select[name=payStatus]").val();
		       				var companyName = form.find("input[name=companyName]").val();
		       				var actualTakeLoacton = form.find("input[name=actualTakeLoacton]").val();
		       				var actualTerminalParkName = form.find("input[name=actualTerminalParkName]").val();
		       				
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = order.appPath+ "/order/toOrderExport.do?orderNo="+orderNo+"&&memberName="+memberName+"&&carPlateNo="+carPlateNo+"&&startBillingTimeStart="+startBillingTimeStart+"&&finishTimeEnd="+finishTimeEnd+"&&orderStatus="+orderStatus+"&&payStatus="+payStatus+"&&mobilePhone="+mobilePhone+"&&companyName="+companyName+"&&actualTakeLoacton="+actualTakeLoacton+"&&actualTerminalParkName="+actualTerminalParkName;
								}
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						order.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
	        	            "targets": [7,8],
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
	        	            "render": function(data, type, row, meta) {
	        	            	if(data){
		        	            	return data;
	        	            	}else{
	        	            		return "";
	        	            	}
	        	            	 
	        	            }
	        	        },{
	        	            "targets": [9],
	        	            "render": function(data, type, row, meta) {
	    	            		if(data==1){
	    	            			 return row.startAddress;
	    	            		}
	    	            		return row.actualTakeLoacton;
		        	        }
	        	        },{
	        	            "targets": [10],
	        	            "render": function(data, type, row, meta) {
        	            		if(data==1){
        	            			 return row.terminalAddress;
        	            		}
        	            		return row.actualTerminalParkName;
	        	            }
	        	            	 
	        	        },
//	        	        {
//        	            "targets": [9],
//        	            "render": function(data, type, row, meta) {
//        	            	if(data){
//        	            		if(data.indexOf("区")==8){
//        	            			 return data.substring(9);
//        	            		}else{
//        	            			return data;
//        	            		}
//	        	            	
//        	            	}else{
//        	            		return "";
//        	            	}
//        	            	 
//        	            }
//        	        },
	        	        {
						    "targets": [12,13],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return order.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
						{
						    "targets": [14],
						    "render": function(a,b,c,d) {
						    	if(c.payStatus==1){
		    	            		return order.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
	        	     // 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
						{
						    "targets": [11],
						    "render": function(a,b, c, d) {
						    	if(a!=""){
						    		if(c.orderStatus==0){
			        					return "已提交";
			        				}else if(c.orderStatus==1){
			        					return "已预约";
			        				}else if(c.orderStatus==2){
			        					return "已计费";
			        				}else if(c.orderStatus==3){
			        					return "已结束";
			        				}else if(c.orderStatus==4){
			        					return "已取消";
			        				}
						        }else{
						        	return "";
						        }
						    }
						},
						// 支付状态（0、未支付，1、已支付，默认0）
						{
						    "targets": [16],
						    "render": function(a,b, c, d) {
						    		if(a==0){
			        					return "未支付";
			        				}else{
			        					return "已支付";
			        				}
						        
						    }
						} 
//						{
//	        	            "targets": [3,4],
//	        	            "render": function(data, type, row, meta) {
//	        	            	var now = moment(data); 
//	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
//	        	            }
//	        	        }
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
	return order;
});


