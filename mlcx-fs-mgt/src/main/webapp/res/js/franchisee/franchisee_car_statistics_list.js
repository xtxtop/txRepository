define(
		[],
		function() {
			var franchiseeCarStatistics = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#franchiseeCarStatisticsSearch").click(function() {
						franchiseeCarStatistics.pageList();
					});
					// 列表页面分页方法调用
					franchiseeCarStatistics.pageList();
				},
				
				operateColumEvent : function() {
					$(".franchiseeProfitOrder-operate-view").each(function() {
						$(this).on("click", function() {
							var carPalteNo = $(this).data("id");
							$("#earningsOrderModal").modal("show");
							var form = $("form[name=earningsOrderForm]");
							form.find("input[name='carOrParkNo']").val(carPalteNo);
							franchiseeCarStatistics.pageListFranchiseeCarForOrders();
						});
					});
					
					$(".earningsDetail-operate-view").each(function() {
						$(this).on("click", function() {
							var carPalteNo = $(this).data("id");
							$("#carRelateOrderReturnsDetailedModal").modal("show");
							var form = $("form[name=carRelateOrderReturnsDetailedModalForm]");
							form.find("input[name='carOrParkNo']").val(carPalteNo);
							franchiseeCarStatistics.pageListCarRelateOrderReturnsDetailed();
						});
					});
					
				},
				pageList : function() {
					var form = $("form[name=franchiseeCarStatisticsSerachForm]");
					var franchiseeCarStatisticsBtnTpl = $("#franchiseeCarStatisticsBtnTpl").html();
					
					// 预编译模板
					var template = Handlebars.compile(franchiseeCarStatisticsBtnTpl);
					var table = $('#franchiseeCarStatisticsList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : franchiseeCarStatistics.appPath
													+ "/franchisee/pageListFranchiseeCarStatistics.do",
											"data" : function(d) {
												return $.extend({},d,
													{"pageNo" : (d.start / d.length) + 1,
													 "pageSize" : d.length,
						        					 "franchiseeName":form.find("input[name=franchiseeName]").val(),
						        					 "carOrParkNo":form.find("input[name=carPlateNo]").val(),
						        					 /*"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
						        					 "createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59"*/
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
											"title" : "加盟商名称",
											"data" : "franchiseeName"
										}, {
											"title" : "车牌号",
											"data" : "carPlateNo"
										}, {
											"title" : "订单数",
											"data" : "orderNumber"
										}, {
											"title" : "总订单时长(分钟)",
											"data" : "orderDuration"
										}, {
											"title" : "平均时长(分钟)",
											"data" : "avgOrderDuration"
										},
										{
											"title" : "总订单里程(Km)",
											"data" : "orderMileage"
										},
										{
											"title" : "平均里程(Km)",
											"data" : "avgOrderMileage"
										},
										{
											"title" : "订单金额(元)",
											"data" : "orderAmount"
										},
										{
											"title" : "分润金额(元)",
											"data" : "profitAmount"
										},
										{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#franchiseetool.col-xs-12'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
								       
										},
										"drawCallback" : function(settings) {
											franchiseeCarStatistics.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
													targets : 9,
													render : function(a, b, c,
															d) {
														var viewOrder = '<span class="glyphicon franchiseeProfitOrder-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.carPlateNo
																+ '" >相关订单</span>';
														var viewEarningsDetail = '<span class="glyphicon earningsDetail-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
															+ c.carPlateNo
															+ '" >收益明细</span>';

															return viewOrder+viewEarningsDetail;
													}
												}]
									});
				},
				
				//车辆关联订单的收益明细
				pageListCarRelateOrderReturnsDetailed:function(){
					var carRelateOrderReturnsDetailedModalTpl = $("#carRelateOrderReturnsDetailedModalTpl").html();
					// 预编译模板
					var template = Handlebars.compile(carRelateOrderReturnsDetailedModalTpl);
					var form = $("form[name=carRelateOrderReturnsDetailedModalForm]");
					var table = $('#carRelateOrderReturnsDetailedList')
						.dataTable(
							{
								searching : false,
								destroy : true,
								"ajax" : {
									"type" : "POST",
									"url" : franchiseeCarStatistics.appPath
											+ "/franchisee/pageListFranchiseeProfit.do",
									"data" : function(d) {
										return $.extend({},d,
											{"pageNo" : (d.start / d.length) + 1,
											 "pageSize" : d.length,
											 "profitType":'0',
				        					 "carOrParkNo":form.find("input[name=carOrParkNo]").val()
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
									"title" : "加盟商名称",
									"data" : "franchiseeName"
								}, {
									"title" : "订单号",
									"data" : "orderNo"
								}, {
									"title" : "分润类型",
									"data" : "profitType"
								}, {
									"title" : "车辆编号",
									"data" : "carOrParkNo"
								}, {
									"title" : "订单金额(元)",
									"data" : "orderAmount"
								},
								{
									"title" : "按车辆分润(%)",
									"data" : "carProportion"
								},
								{
									"title" : "车辆分润(元)",
									"data" : "carProfit"
								} ],
								"dom" : "<'row'<''><'col-xs-6'f>r>"
										+ "t"
										+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
								initComplete : function() {
									 $("#franchiseetools").empty().append('<button type="button"  class="btn-new-type franchiseetools-batch-close">关闭</button>');
										
										$(".franchiseetools-batch-close").on("click",function(){
											$("#carRelateOrderReturnsDetailedModal").modal("hide");
										});
								},
								"drawCallback" : function(settings) {
									
								},
								"order" : [ [ 1, "desc" ] ],
								"columnDefs" : [
										{
										    "targets": [2],
										    "render": function(a,b,c,d) {//0、车辆，1、场站，2、车辆和场站
										    	if(a!=null){
										        	if(a==0){
										        		return "车辆";
										        	}else if(a==1){
										        		return "场站";
										        	}else if(a==2){
										        		return "车辆和场站";
										        	}
										        }else{
										        	return "";
										        }
										    }
										}
										]
							});
				},
				//相关订单
				pageListFranchiseeCarForOrders:function () {	
					var earningsOrderModalTpl = $("#earningsOrderModalTpl").html();
					// 预编译模板
					var template = Handlebars.compile(earningsOrderModalTpl);
					$('#earningsOrderList').dataTable( {
						searching:false,
						destroy: true,
						"ajax": {
							"type": "POST",
							"url": franchiseeCarStatistics.appPath+'/franchisee/pageListFranchiseeCarOrParkOrders.do',
							"data": function ( d ) {
								var form = $("form[name=earningsOrderForm]");
								return $.extend( {}, d, {
									"pageNo": (d.start/d.length)+1,
									"pageSize":d.length,
									"profitType":'0',
									"carOrParkNo":form.find("input[name=carOrParkNo]").val()
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
				            { "title":"订单号","data": "orderNo" },
							{ "title":"城市","data": "cityName" },
							{ "title":"车牌号","data": "carPlateNo" },
							{ "title":"客户","data": "memberName" },
							{ "title":"手机","data": "mobilePhone" },
							{ "title":"开始","data": "startBillingTime" },
							{ "title":"结束","data": "finishTime" },
							{ "title":"取车点","data": "isTakeInPark" },
							{ "title":"还车点","data": "isReturnInPark" },
							{ "title":"状态","data": "orderStatus" },
							{ "title":"总金额","data": "orderAmount" },
							{ "title":"应付","data": "payableAmount" },
							{ "title":"支付状态","data": "payStatus" }
						],
	                    // "dom": "<'row'<'col-xs-2'l><'#ordertool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
					   "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   $("#franchiseeOrder").empty().append('<button type="button"  class="btn-new-type franchOrder-batch-close">关闭</button>');
							
							$(".franchOrder-batch-close").on("click",function(){
								$("#earningsOrderModal").modal("hide");
							});
						},
						"drawCallback": function( settings ) {
		        	    },
		        	    "order": [[ 1, "desc" ]],
						"columnDefs": [
							{
		        	            "targets": [5,6],
		        	            "render": function(data, type, row, meta) {
		        	            	if(data){
		        	            		var now = moment(data); 
			        	            	return now.format('YYYY-MM-DD HH:mm:ss');
		        	            	}else{
		        	            		return "";
		        	            	}
		        	            	 
		        	            }
		        	        },{
		        	            "targets": [7],
		        	            "render": function(data, type, row, meta) {
		    	            			 return row.startAddress;
		    	            		
			        	        }
		        	        },{
		        	            "targets": [8],
		        	            "render": function(data, type, row, meta) {
	        	            			 return row.terminalAddress;
	        	            		
		        	            }
		        	            	 
		        	        },
		        	     // 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
							{
							    "targets": [9],
							    "render": function(a,b, c, d) {
							    	if(a!=""){
							    		if(c.orderStatus==0){
				        					return "已提交";
				        				}else if(c.orderStatus==1){
				        					return "<span style='color:#2b94fd'>已预约</span>";
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
		        	        {
							    "targets": [10,11],
							    "render": function(a,b,c,d) {
							    	if(a){
			    	            		return "<span style='color:#2b94fd'>" + franchiseeCarStatistics.formatCurrency(a) + "</span>";
			    	            	}else{
			    	            		return "0.00";
			    	            	}
							    }
							},
							// 支付状态（0、未支付，1、已支付，默认0）
							{
							    "targets": [12],
							    "render": function(a,b, c, d) {
							    		if(a==0){
				        					return "未支付";
				        				}else{
				        					return "已支付";
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
		        return t.split("").reverse().join("") + "." + r;  }
			};
			return franchiseeCarStatistics;
		});
