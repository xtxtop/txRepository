define([],function() {	
	var orderDayRefund={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderDayRefund.pageList();
				//查询
				$("#orderDayRefundSearchafter").click(function(){
					orderDayRefund.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
				$(".orderDayRefund-operate-refund").each(function() {
					var orderNo=$(this).data("id");
					var refundMethod = $(this).data("type");
					var refundAmount = $(this).data("refundamount");
					$(this).on("click", function() {
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要退款吗？", function(result) {
							if (result) {
								if(refundMethod==1){//支付宝
	    	        				$.ajax({
	    		        				url: orderDayRefund.appPath+"/refund/toOrderDayAlipayRefund.do?orderNo="+orderNo+"&refundAmount="+refundAmount, 
	    		        				type: 'POST',
	    		        				success: function(res){
	    		        					var code=res.code;
	    		        					if(code=="1"){
	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该退款操作成功！",function(){
	    		        							orderDayRefund.pageList();	
		        								});
	    		        					}else{
	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg,function(){
	    		        							orderDayRefund.pageList();	
		        								});
	    		        					}
	    		        				}
	    		        			});	
	    	        			}else if(refundMethod==2){//微信
	    	        				$.ajax({
	    		        				url: orderDayRefund.appPath+"/refund/toOrderDayWxRefund.do?orderNo="+orderNo+"&refundAmount="+refundAmount, 
	    		        				type: 'POST',
	    		        				success: function(res){
	    		        					var code=res.code;
	    		        					if(code=="1"){
	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该退款操作成功！",function(){
	    		        							orderDayRefund.pageList();	
		        								});
	    		        					}else{
	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg,function(){
	    		        							orderDayRefund.pageList();	
		        								});
	    		        					}
	    		        				}
	    		        			});	
	    	        			}
							}
						})
								  
					});
					
				});
					
				
	        },
			pageList:function () {	
				var orderDayRefundTpl = $("#orderDayRefundTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderDayRefundTpl);
				$('#orderDayRefundList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderDayRefund.appPath+'/refund/pageListOrderDayRefund.do',
						"data": function ( d ) {
							debugger
							var form = $("form[name=orderDayRefundSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=orderNo]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"paymentMethod":form.find("select[name=paymentMethod]").val(),
								"refundStatus":$.trim(form.find("select[name=refundStatus]").val()),
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
						{ "title":"联系电话","data": "mobilePhone" },
						{ "title":"支付金额","data": "payableAmount" },
						{ "title":"退还租车金额","data": "returnAmount" },
						{ "title":"超时还车罚金","data": "overtimeCharge" },
						{ "title":"提前还车违约金","data": "serviceFeeAmount" },
						{ "title":"未到店取车违约金","data": "cancelAmount" },
						{ "title":"送车上门未取车违约金","data": "takeCarDoorAmount" },
						{ "title":"退款方式","data": "paymentMethod" },
						{ "title":"退款金额","data": "refundAmount" },
						{ "title":"退款状态","data": "refundStatus" },
						{ "title":"退款时间","data": "refundTime" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#orderDayRefundTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
		       			
					},
					"drawCallback": function( settings ) {
						orderDayRefund.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [3,4,5,6,7,8,10],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return orderDayRefund.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
						{
						    "targets": [9],
						    "render": function(a,b, c, d) {
						    		if(a==1){
			        					return "支付宝";
			        				}else if(a==2){
			        					return "微信";
			        				}else{
			        					return "";
			        				}
						        
						    }
						},
						{
						    "targets": [11],
						    "render": function(a,b, c, d) {
						    		if(a!=null){
						    			if(a==0){
						    				return "未退款";
						    			}else if(a==1){
						    				return "已退款";
						    			}else if(a==2){
						    				return "退款失败";
						    			}
			        				}else{
			        					return "未退款";
			        				}
						        
						    }
						},
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
							"render": function (a, b, c, d) {
								if (c.refundStatus!=1) {
									var refund = '<span class="glyphicon btn btn-default orderDayRefund-operate-refund" data-id="'+c.orderNo+'" data-type="'+c.paymentMethod+'" data-refundamount="'+c.refundAmount+'" data-toggle="tooltip" data-placement="top">退款</span>';
									return refund;
								}else{
									return "";
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
	
	return orderDayRefund;
});


