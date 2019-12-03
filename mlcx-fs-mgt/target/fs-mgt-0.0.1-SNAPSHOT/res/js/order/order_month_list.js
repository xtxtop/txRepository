define([],function() {	
	var orderMonth={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderMonth.pageList();
				//查询
				$("#orderMonthSearchafter").click(function(){
					orderMonth.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
				
					$(".orderMonth-operate-find").on("click",function(){
						addTab("月租订单详情",orderMonth.appPath+ "/orderMonth/toOrderMonthView.do?orderNo="+$(this).data("id"));
					});
			       
					
					$(".orderMonth-operate-getCar").on("click",function(){
						addTab("月租订单车辆",orderMonth.appPath+ "/orderMonthCar/toOrderMonthCarList.do?orderNo="+$(this).data("id"));
					});
					$(".orderMonth-operate-edit").on("click",function(){
						addTab("月租订单编辑",orderMonth.appPath+ "/orderMonth/toOrderMonthEdit.do?orderNo="+$(this).data("id"));
					});
					
	        },
			pageList:function () {	
				var orderMonthTpl = $("#orderMonthTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderMonthTpl);
				$('#orderMonthList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderMonth.appPath+'/orderMonth/pageListOrderMonth.do',
						"data": function ( d ) {
							debugger
							var form = $("form[name=orderMonthSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=orderNo]").val()),
	        					"orderStatus":form.find("select[name=orderStatus]").val(),
	        					"payStatus":form.find("select[name=payStatus]").val()
								
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
						{ "title":"客户姓名","data": "memberName" },
						{ "title":"取车","data": "actualTakeLoacton" },
						{ "title":"还车","data": "actualTerminalParkName" },
						{ "title":"取车时间","data": "actualStartTime" },
						{ "title":"还车时间","data": "actualEndTime" },
						{ "title":"订单状态","data": "orderStatus" },
						{ "title":"保险费用","data": "insurance" },
						{ "title":"押金","data": "deposit" },
						{ "title":"订单金额","data": "orderAmount" },
						{ "title":"应支付金额","data": "payableAmount" },
						{ "title":"支付状态","data": "payStatus" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#orderMonthtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#orderMonthTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					    
					   $("#orderMonthTools").html("");
		       			$("#orderMonthTools").append('<button type="button" class="btn btn-default btn-sm orderMonthTools-operate-add">新增</button>');
		       			
		       			$(".orderMonthTools-operate-add").on("click",function(){
		       				addTab("月租订单新增", orderMonth.appPath+ "/orderMonth/toOrderMonthAdd.do");
		       			});	 
		       			
					},
					"drawCallback": function( settings ) {
						orderMonth.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [4,5],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						        	return now.format('YYYY-MM-DD HH:mm:ss');
						    	}else{
						    		return "";
						    	}
						    	 
						    }
						},
						// 订单状态（1、已提车，2、已还车，3、已结束，4、已取消，默认1）
						{
						    "targets": [6],
						    "render": function(a,b, c, d) {
						    	if(a!=""){
						    		if(c.orderStatus==1){
			        					return "已提车";
			        				}else if(c.orderStatus==2){
			        					return "已还车";
			        				}else if(c.orderStatus==3){
			        					return "已计费";
			        				}else if(c.orderStatus==4){
			        					return "已结束";
			        				}
						        }else{
						        	return "";
						        }
						    }
						},
						 {
						    "targets": [7,8,9,10],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return orderMonth.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
						// 支付状态（0、未支付，1、已支付，默认0）
						{
						    "targets": [11],
						    "render": function(a,b, c, d) {
						    		if(a==0){
			        					return "未支付";
			        				}else{
			        					return "已支付";
			        				}
						        
						    }
						},
						
						{
							"targets": [12],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  btn btn-default  orderMonth-operate-find" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top">查看详情</span>';
								var getCar='<span class="glyphicon  btn btn-default  orderMonth-operate-getCar" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" >订单车辆</span>';
								var efit='<span class="glyphicon  btn btn-default  orderMonth-operate-edit" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top" >编辑</span>';
								return find+getCar+efit;
	        					
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
	
	return orderMonth;
});


