define([],function() {	
	var orderStrikeBalance={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				orderStrikeBalance.pageList();
				orderStrikeBalance.handleClickMore();
				//查询
				$("#orderStrikeBalanceSearchafter").click(function(){
					orderStrikeBalance.pageList();
	            });
				
				$("#closeOrderStrikeBalanceView").click(function(){
					closeTab("订单冲账详情");
					orderStrikeBalance.pageList();
	            });
			},
			operateColumEvent : function() {
				$(".orderStrikeBalance-operate-view").each(function() {
					$(this).on("click", function() {
						addTab("订单冲账详情",orderStrikeBalance.appPath+ "/orderStrikeBalance/toOrderStrikeBalanceView.do?strikeBalanceNo="+$(this).data("id"));
					});
				});
				$(".orderStrikeBalance-operate-cencor").each(function() {
					$(this).on("click", function() {
						addTab("订单冲账审核",orderStrikeBalance.appPath+ "/orderStrikeBalance/toOrderStrikeBalanceCencor.do?strikeBalanceNo="+$(this).data("id"));
					});
				});
				
				$(".order-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("订单详情",orderStrikeBalance.appPath+ "/order/toOrderView.do?orderNo="+$(this).data("id"));
					});
				});
				
				
				$(".member-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("会员详情",orderStrikeBalance.appPath+ "/member/toMemberView.do?memberNo="+$(this).data("id"));
					});
				});
				
				
			},
			pageList:function () {	
				var orderStrikeBalanceTpl = $("#orderStrikeBalanceTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderStrikeBalanceTpl);
				$('#orderStrikeBalanceList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderStrikeBalance.appPath+'/orderStrikeBalance/pageListOrderStrikeBalance.do',
						"data": function ( d ) {
							var form = $("form[name=orderStrikeBalanceSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"strikeBalanceNo":form.find("input[name=strikeBalanceNo]").val(),
								"orderNo":form.find("input[name=orderNo]").val(),
								"memberName":form.find("input[name=memberName]").val(),
								"censorStatus":form.find("select[name=censorStatus]").val(),
								"submitTtimeStart":form.find("input[name=submitTtimeStart]").val()+" 00:00:00",
								"submitTtimeEnd":form.find("input[name=submitTtimeEnd]").val()+" 23:59:59",
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
			            { "title":"冲账单号","data": "strikeBalanceNo" },
						{ "title":"订单号","data": "orderNo" },
						{ "title":"客户","data": "memberName" },
						{ "title":"订单金额(元)","data": "orderAmount" },
						{ "title":"应付金额(元)","data": "payAmount" },	
						{ "title":"冲账金额(元)","data": "strikeBalanceAmount" },
						{ "title":"附加服务费(元)","data": "serviceFee" },
						{ "title":"冲账原因","data": "strikeBalanceReason" },
						{ "title":"提交时间","data": "submitTtime" },
						{ "title":"审核状态","data": "censorStatus" },
						{ "title":"审核时间","data": "censorTime" },	
						{ "title":"操作","orderStrikeBalanceable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#orderStrikeBalancetool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#orderStrikeBalanceTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						orderStrikeBalance.operateColumEvent();
	        	    },
					"columnDefs": [
					               
					               
							{
								"targets" : [ 1 ],
								"render" : function(a,
										b, c, d) {
									if(a){
										return '<span class="glyphicon order-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.orderNo+ '" >'+a+'</span>';
									}else{
										return "";
									}
								}
							}, 
							
							{
								"targets" : [ 2 ],
								"render" : function(a,
										b, c, d) {
									if(a){
										return '<span class="glyphicon member-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.memberId+ '" >'+a+'</span>';
									}else{
										return "";
									}
								}
							}, 
					               
					               
					               {
					            	   "targets": [3,4,5,6],
					            	   "render": function(data, type, row, meta) {
					            		   if(data){
					    	            		return "<span style='color:#2b94fd'>" + orderStrikeBalance.formatCurrency(data) + "</span>";
					    	            	}else{
					    	            		return "0.0";
					    	            	}
					            	   }
					               },
					               {
									    "targets": [7],
									    "render": function(a,b, c, d) {//冲账原因
									    	if(a != null){
									    		if(c.strikeBalanceReason==1){
						        					return "故障";
						        				}else if(c.strikeBalanceReason==2){
						        					return "没电";
						        				}
									        }
									        return "";
									    }
									},
						{
	        	            "targets": [8,10],
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
						    "targets": [9],
						    "render": function(a,b, c, d) {//审核状态（0：未审核，1：已审核2：未通过）
						    		if(a==0){
			        					return "未审核";
			        				}else if(a==1){
			        					return "已审核";
			        				}else if(a==2){
			        					return "未通过";
			        				}
						    }
						},
						{
							"targets": [11],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon  orderStrikeBalance-operate-view" data-id="'+c.strikeBalanceNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var cencor='<span class="glyphicon  orderStrikeBalance-operate-cencor" data-id="'+c.strikeBalanceNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">审核</span>';
	        					if(c.censorStatus==0 && c.roleAdminTag==1){
	        						return view+cencor;
	        					}else{
	        						return view;
	        					}
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
		        return t.split("").reverse().join("") + "." + r;  
		        },
		    	//点击更多
				handleClickMore:function(){
					$("#moreOrderStrike").click(function(){
						debugger;
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
	return orderStrikeBalance;
});


