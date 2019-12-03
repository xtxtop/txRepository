define([],function() {	
	var depositOrder={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				depositOrder.pageList();
				depositOrder.handleClickMore();
				//查询
				$("#depositOrderSearch").click(function(){
					depositOrder.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){},
			pageList:function () {
				var depositOrderBtnTpl = $("#depositOrderBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(depositOrderBtnTpl);
				$('#depositOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": depositOrder.appPath+'/depositPay/pageListDepositOrder.do',
						"data": function ( d ) {
							var form = $("form[name=depositOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"mobilePhone":form.find("input[name=mobilePhone]").val(),
								"memberName":form.find("input[name=memberName]").val(),
								"paymentMethod":form.find("select[name=paymentMethod]").val(),
								"refundStatus":form.find("select[name=refundStatus]").val(),
								"refundMethod":form.find("select[name=refundMethod]").val(),
								"partTradeFlowNo":form.find("input[name=partTradeFlowNo]").val()
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
			            { "title":"客户名称","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"缴纳方式","data": "paymentMethod"},
						{ "title":"押金金额","data": "depositAmount"},
						{ "title":"缴纳时间","data": "paymentTime"},
						{ "title":"退款状态","data": "refundStatus"},
						{ "title":"退款时间","data": "refundTime"},	
						{ "title":"退款金额(元)","data": "refundAmount" },
						{ "title":"退款方式","data": "refundMethod"},
					],
				   "dom": "<'row'<'#depositOrderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						depositOrder.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b,c,d) {//支付方式（1、支付宝、2、微信 3、微信H5）
						    	if(a!=null){
						        	if(a==1){
						        		return "支付宝";
						        	}else if(a==2){
						        		return "微信";
						        	}else if(a==3){
						        		return "微信公众号";
						        	}else{
						        		return "";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [3,7],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return depositOrder.formatCurrency(a); 
		    	            	}else{
		    	            		return "";
		    	            	}
						    }
						},{
						    "targets": [4,6],
						    "render": function(a,b,c,d) {
						    	 if(a!=null){
							        	var now = moment(a);
										return now.format('YYYY-MM-DD HH:mm:ss');
							        }else{
							        	return "";
							        }
						    }
						},{
						    "targets": [5],
						    "render": function(a,b,c,d) {//0、未退款，1、已退款，2、退款失败
						    	if(a!=null){
						        	if(a==0){
						        		return "未退款";
						        	}else if(a==1){
						        		return "已退款";
						        	}else if(a==2){
						        		return "退款失败";
						        	}else{
							        	return "";
						        	}
						        }else{
						        	return "未退款";
						        }
						    }
						},{
						    "targets": [8],
						    "render": function(a,b,c,d) {//1、支付宝，2、微信，3、银行转账
						    	if(a!=null){
						        	if(a==1){
						        		return "支付宝";
						        	}else if(a==2){
						        		return "微信";
						        	}else if(a==3){
						        		return "微信公众号";
						        	}else if(a==4){
						        		return "线下退款";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
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
				$("#moreDepositOrderSearch").click(function(){
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
	return depositOrder;
});


