define([],function() {	
	var overOrder={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				overOrder.pageList();
				overOrder.handleClickMore();
				//查询
				$("#overOrderSearchafter").click(function(){
					var form=$("form[name=overOrderSearchForm]");
					var startBillingTimeStart=form.find("input[name=startBillingTimeStart]").val();
					var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
					if(startBillingTimeStart!=null&&startBillingTimeStart!=""){
						if(finishTimeEnd!=null&&finishTimeEnd!=""){
							if(startBillingTimeStart>finishTimeEnd){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{ 
								overOrder.pageList();
							}
						}
					}else{
						overOrder.pageList();
					}
					
	            });
				
				
			},
			operateColumEvent : function() {
				$(".overOrder-operate-find").each(function() {
					$(this).on("click", function() {
						addTab("订单详情",overOrder.appPath+ "/order/toOrderView.do?orderNo="+$(this).data("id"));
					});
				});
		
				//地图上显示车辆轨迹
				$(".overOrder-operate-carTrack").each(function(){
					$(this).on("click",function(){
						addTab("轨迹回放",overOrder.appPath+ "/carTrack/toCarTrackList.do?carPlateNo="+$(this).data("id")+"&finishTime="+$(this).data("finishtime")+"&createTime="+$(this).data("id2"));
					});
				});
				$(".overOrder-operate-onList").each(function() {
					$(this).on("click", function() {
						addTab("车辆监控",overOrder.appPath+ "/carMonitor/toCarMonitorListReal.do?flag=1&carPlateNo="+$(this).data("id"));
					});
				});
				$(".overOrder-operate-onLists").each(function() {
					var orderNo=$(this).data("id");
					$(this).on("click", function() {
						 $.ajax({
				    			url:overOrder.appPath+"/order/updateOrderOverAmount.do",//
				    			type:"post",
				    			data:{orderNo:orderNo},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var data=res.data;
				    				if (code == "1") {
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
												$("#overOrderList").DataTable().ajax.reload(function(){});
											});
				    					}else if(code == "0"){
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败", function() {
											});
				    					}
									}
				    			
						 });
								  
					});
				});
				//开启动力
				$(".overOrder-operate-turnOnPower").each(function(){
					$(this).on("click",function(){
						$.ajax({
							 url : overOrder.appPath+ "/carStatus/overOrdercontrolPower.do?orderNo="+$(this).data("id")+"&cmdValue=1",
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
				});
				
				$(".overOrderDevice-operate-find").each(function() {
					$(this).on("click", function() {
						addTab("开关动力日志",overOrder.appPath+ "/order/orderDevice.do?orderNo="+$(this).data("id"));
					});
				});
					
				
				//关闭动力
				$(".overOrder-operate-turnOffPower").each(function(){
					$(this).on("click",function(){
						$.ajax({
							 url : overOrder.appPath+ "/carStatus/overOrdercontrolPower.do?orderNo="+$(this).data("id")+"&cmdValue=0",
				             success: function(res){
				            	 if(res.code=="1"){
				            	    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭动力指令发送成功！");
				                 }else if(res.code=="0"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭动力指令发送失败！");
				                 }else if(res.code=="3"){
				                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此设备不存在");
				                 }else if(res.code=="-10"){
					                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
					             }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请稍后再试！"); 
				                 }
				             }
						});
					});
				});
			
			
			},
			pageList:function () {	
				var overOrderTpl = $("#overOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(overOrderTpl);
				$('#overOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": overOrder.appPath+'/order/pageListoverOrder.do',
						"data": function ( d ) {
							var form = $("form[name=overOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=overOrderNo]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"startBillingTimeStart":form.find("input[name=startBillingTimeStart]").val()+" 00:00:00",
								"finishTimeEnd":form.find("input[name=finishTimeEnd]").val()+" 23:59:59",
	        					"orderStatus":form.find("select[name=overOrderStatus]").val(),
	        					"payStatus":form.find("select[name=payStatus]").val(),
	        					"actualTakeLoacton":form.find("input[name=actualTakeLoacton]").val(),
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
	//订单号、城市、车牌、客户、手机、开始时间、取车点、时长、会员余额、已关动力（显示是/否）操作列。				            
			            { "title":"订单编号","data": "orderNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"车型","data": "carModelName" },
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"客户姓名","data": "memberName" },	
						{ "title":"手机号码","data": "mobilePhone" },
						{ "title":"集团名称","data": "companyName" },
						{ "title":"开始时间","data": "startBillingTime" },
						{ "title":"取车场站/地点","data": "isTakeInPark" },
						{ "title":"时长","data": "orderDuration" },
						{ "title":"会员余额(元)","data": "memberBalance" },
						{ "title":"操作","overOrderable":false}
					],
                    // "dom": "<'row'<'col-xs-2'l><'#overOrdertool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#overOrderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#overOrderTools").html("");
		       		
					},
					"drawCallback": function( settings ) {
						overOrder.operateColumEvent();
	        	    },
	        	    "overOrder": [[ 1, "desc" ]],
					"columnDefs": [
						{
	        	            "targets": [7],
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
	        	            "targets": [6,9],
	        	            "render": function(data, type, row, meta) {
	        	            	if(data){
		        	            	return data;
	        	            	}else{
	        	            		return "";
	        	            	}
	        	            	 
	        	            }
	        	        },
//	        	        {
//	        	            "targets": [10],
//	        	            "render": function(data, type, row, meta) {
//        	            		if(data==1){
//        	            			 return row.terminalAddress;
//        	            		}
//        	            		return row.actualTerminalParkName;
//	        	            }
//	        	            	 
//	        	        },
//	        	       {
	        	        { "targets": [8],
        	            "render": function(data, type, row, meta) {
    	            		if(data==1){
    	            			 return row.startAddress;
    	            		}
    	            		return row.actualTakeLoacton;
	        	        }
        	        },
	        	      
						{
						    "targets": [10],
						    "render": function(a,b,c,d) {
						    	if(c.payStatus==1){
		    	            		return overOrder.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
	        	     // 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
						
						{
							"targets": [11],
							"render": function (a, b, c, d) {
								var find="";
								var edit="";
								var onList="";
								var over="";
								var turnOnPower="";
								var turnOffPower="";
								var find='<span class="glyphicon  overOrder-operate-find" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看详情</span>';
								var findDevice='<span class="glyphicon  overOrderDevice-operate-find" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看开关动力日志</span>';
								turnOnPower='<span class="glyphicon  overOrder-operate-turnOnPower" data-id="'+c.orderNo+'"   data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">开启动力</span>';
								turnOffPower='<span class="glyphicon  overOrder-operate-turnOffPower" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">关闭动力</span>';
	        					var onList='<span class="glyphicon  overOrder-operate-onList" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">车辆位置</span>';
	        					var onLists='<span class="glyphicon  overOrder-operate-onLists" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">弹框提示</span>';
	        					return find+findDevice+onList+over+turnOnPower+turnOffPower+onLists;
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
	        return t.split("").reverse().join("") + "." + r;  
	        },
	    	//点击更多
			handleClickMore:function(){
				$("#moreoverOrder").click(function(){
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
	return overOrder;
});


