define([],function() {	
	var depositRefund={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				depositRefund.pageList();
				depositRefund.handleClickMore();
				//查询
				$("#depositRefundSearch").click(function(){
					depositRefund.pageList();
	            });
				$("#closeDepositRefundView").click(function(){
					closeTab("退款详情");
					depositRefund.pageList();
	            });
				$("#memoDepositRefund").click(function(){
					depositRefund.memoDepositRefund();
				});
				$("#closeDepositRefundMemo").click(function(){
					$("#depositRefundMemoModal").modal("hide");
					$("form[name=depositRefundMemoForm]").resetForm();
			    	$("form[name=depositRefundMemoForm]").find("textarea[name=refundMemo]").val("");
				});
				
			},
			memoDepositRefund:function() {
				var form = $("form[name=depositRefundMemoForm]");
				form.ajaxSubmit({
					url : depositRefund.appPath + "/depositRefund/depositRefundPayMemo.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "押金线下退款完成！", function() {
								$("#depositRefundMemoModal").modal("hide");
								$("form[name=depositRefundMemoForm]").resetForm();
						    	$("form[name=depositRefundMemoForm]").find("textarea[name=refundMemo]").val("");
								depositRefund.pageList();
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "押金线下退款失败！");
							$("#depositRefundMemoModal").modal("hide");
							$("form[name=depositRefundMemoForm]").resetForm();
					    	$("form[name=depositRefundMemoForm]").find("textarea[name=refundMemo]").val("");
							depositRefund.pageList();
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						var form = $("form[name=depositRefundMemoForm]");
						if(form.find("textarea[name=refundMemo]").val()==""){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入备注信息！");
							return false;
						}
					}
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".depositRefund-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("退款详情",depositRefund.appPath+ "/depositRefund/toDepositRefundView.do?refundNo="+$(this).data("id"));
					});
				});
	        	//审核
	        	$(".depositRefund-operate-cencor").each(function(){
	        		$(this).on("click",function(){
						addTab("退款审核",depositRefund.appPath+ "/depositRefund/toDepositRefundCencor.do?refundNo="+$(this).data("id"));
					});
				});
	        	//线上退款
	        	$(".depositRefund-operate-onlineRefund").each(function(){
	        		$(this).on("click",function(){
	        			debugger
	        			var id=$(this).data("id");
	        			var type=$(this).data("type");
	        			//先判断该退款记录是否已经支付
	        			$.ajax({
	        				url: depositRefund.appPath+"/depositRefund/getDepositRefundStatus.do?depositRefundNo="+id, 
	        				type: 'POST',
	        				success: function(res){
	        					var code=res.code;
	        					if(code == "1" || code == "2"){
	        						if(res.data){
	        							var status=res.data.refundStatus;
	        							if(status==0||status==2){
	        								if(type==1){//支付宝
	        	    	        				//window.open(depositRefund.appPath+"/depositRefund/toDepositRefundPay.do?depositRefundNo="+id);
	        	    	        				$.ajax({
	        	    		        				url: depositRefund.appPath+"/depositRefund/toDepositRefundPay.do?depositRefundNo="+id, 
	        	    		        				type: 'POST',
	        	    		        				success: function(res){
	        	    		        					var code=res.code;
	        	    		        					if(code=="1"){
	        	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该退款操作成功！",function(){
	        		        									depositRefund.pageList();	
	        		        								});
	        	    		        					}else{
	        	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg,function(){
	        		        									depositRefund.pageList();	
	        		        								});
	        	    		        					}
	        	    		        				}
	        	    		        			});	
	        	    	        			}else if(type==2||type==3){//微信
	        	    	        				$.ajax({
	        	    		        				url: depositRefund.appPath+"/depositRefund/toDepositRefundWXPay.do?depositRefundNo="+id, 
	        	    		        				type: 'POST',
	        	    		        				success: function(res){
	        	    		        					var code=res.code;
	        	    		        					if(code=="1"){
	        	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该退款操作成功！",function(){
	        		        									depositRefund.pageList();	
	        		        								});
	        	    		        					}else{
	        	    		        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg,function(){
	        		        									depositRefund.pageList();	
	        		        								});
	        	    		        					}
	        	    		        				}
	        	    		        			});	
	        	    	        			}else if(type==3){//线下退款
	        	    	        				var formMemo = $("form[name=depositRefundMemoForm]");
	        	    	        				formMemo.find("input[name=refundNo]").val(id);
	        	    	        				$("#depositRefundMemoModal").modal("show");
	        	    	        			}
	        							}else if (status==1){
	        								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该退款已经完成！",function(){
	        									depositRefund.pageList();	
	        								});
	        							}
	        						}
	        					}
	        					
	        				}
	        			});	
	        			
	        			
	        			//addTab("支付宝退款",depositRefund.appPath+ "/depositRefund/toDepositRefundPay.do?depositRefundNo="+id);
//	        			bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要退款吗？", function(result) {
//							if(result){
//								$.ajax({
//			        				url: depositRefund.appPath+"/depositRefund/toDepositRefundPay.do?depositRefundNo="+id, 
//			        				type: 'POST',
//			        				success: function(res){
//			        					
//			        				}
//			        			});	
//							}						
//	       				});
					});
				});

	        	//线下退款
	        	$(".depositRefund-operate-offlineRefund").each(function(){
	        		$(this).on("click",function(){
	        			var id=$(this).data("id");
        				var formMemo = $("form[name=depositRefundMemoForm]");
        				formMemo.find("input[name=refundNo]").val(id);
        				$("#depositRefundMemoModal").modal("show");
	        		});
        		});
	        },
			pageList:function () {
				var depositRefundBtnTpl = $("#depositRefundBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(depositRefundBtnTpl);
				$('#depositRefundList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": depositRefund.appPath+'/depositRefund/pageListDepositRefund.do',
						"data": function ( d ) {
							var form = $("form[name=depositRefundSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"mobilePhone":form.find("input[name=mobilePhone]").val(),
								"applyTimeStart":form.find("input[name=applyTime1]").val()+" 00:00:00",
								"applyTimeEnd":form.find("input[name=applyTime2]").val()+" 23:59:59",
								"cencorStatus":form.find("select[name=cencorStatus]").val(),
								"refundStatus":form.find("select[name=refundStatus]").val(),
								"refundTimeStart":form.find("input[name=refundTime1]").val()+" 00:00:00",
								"refundTimeEnd":form.find("input[name=refundTime2]").val()+" 23:59:59",
								"refundFlowNo":form.find("input[name=refundFlowNo]").val(),
								"refundMethod":form.find("select[name=refundMethod]").val(),
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
						{ "title":"退款金额(元)","data": "refundAmount" },
						{ "title":"申请时间","data": "applyTime"},
						//{ "title":"是否欠费","data": "memberName"},//暂无字段
						//{ "title":"欠费金额","data": "memberName"},//暂无字段
						{ "title":"审核状态","data": "cencorStatus"},
						{ "title":"退款状态","data": "refundStatus"},
						{ "title":"退款方式","data": "refundMethod"},
						{ "title":"退款流水号","data": "refundFlowNo"},
						{ "title":"退款时间","data": "refundTime"},
						{ "title":"客户端申请退款理由","data": "refundGrounds"},
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#depositRefundTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#depositRefundTools").html("");
					   $("#depositRefundTools").removeClass("col-xs-6");
					   $("#depositRefundTools").append('<button type="button" class="btn-new-type depositRefundTools-operate-export">导出</button>');
		       			/**
		       			 * 导出
		       			 */
		       			$(".depositRefundTools-operate-export").on("click",function(){
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									var form = $("form[name=depositRefundSearchForm]");
									form.attr("action",depositRefund.appPath+ "/depositRefund/exportDepositRefund.do").submit();
//									window.location.href = depositRefund.appPath+ "/depositRefund/exportDepositRefund.do?filepath=depositRefund&newFileName=depositRefund.xls";
								}						
		       				});
		       			}); 	     			
	        			
					},
					"drawCallback": function( settings ) {
						depositRefund.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return depositRefund.formatCurrency(a); 
		    	            	}else{
		    	            		return "";
		    	            	}
						    }
						},{
						    "targets": [3,8],
						    "render": function(a,b,c,d) {
						    	 if(a!=null){
							        	var now = moment(a);
										return now.format('YYYY-MM-DD HH:mm:ss');
							        }else{
							        	return "";
							        }
						    }
						},{
						    "targets": [4],
						    "render": function(a,b,c,d) {//0、未审核，1、已审核，2、待审核，3、审核不通过
						    	if(a!=null){
						        	if(a==0){
						        		return "未审核";
						        	}else if(a==1){
						        		return "已审核";
						        	}else if(a==2){
						        		return "待审核";
						        	}else if(a==3){
						        		return "审核不通过";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [5],
						    "render": function(a,b,c,d) {//0、未退款，1、已退款
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
						        	return "";
						        }
						    }
						},{
						    "targets": [6],
						    "render": function(a,b,c,d) {//1、支付宝，2、微信，3、银行转账
						    	if(a!=null){
						        	if(a==1){
						        		return "支付宝";
						        	}else if(a==2||a==3){
						        		return "微信";
						        	}else if(a==4){
						        		return "线下退款";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						
						{
							"targets" : [ 9 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return a;
								}else{
									return "";
								}
							}
						},
						{
							"targets": [10],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon depositRefund-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'+c.refundNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var cencor="";
								if(c.cencorStatus==0||c.cencorStatus==2){
									cencor='<span class="glyphicon depositRefund-operate-cencor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.refundNo+'" data-toggle="tooltip" data-placement="top">审核</span>';
								}
								var onlineRefund="";
								var offlineRefund="";
								if(c.cencorStatus==1&&c.refundStatus!=1){
									onlineRefund='<span class="glyphicon depositRefund-operate-onlineRefund" style="text-decoration: underline; cursor: pointer;"data-id="'+c.refundNo+'" data-type="'+c.refundMethod+'" data-toggle="tooltip" data-placement="top">线上退款</span>';
									offlineRefund='<span class="glyphicon depositRefund-operate-offlineRefund" style="text-decoration: underline; cursor: pointer;"data-id="'+c.refundNo+'" data-type="'+c.refundMethod+'" data-toggle="tooltip" data-placement="top">线下退款</span>';
								}
								return view+cencor+onlineRefund+offlineRefund;
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
				$("#moreDepositRefundSearch").click(function(){
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
	return depositRefund;
});


