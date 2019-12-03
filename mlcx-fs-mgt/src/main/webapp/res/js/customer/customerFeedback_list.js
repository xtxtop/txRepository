define([],function() {	
	var customerFeedback={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				customerFeedback.pageList();
				customerFeedback.handleClickMore();
				//查询
				$("#customerFeedbackSearch").click(function(){
					customerFeedback.pageList();
	            });
				$("#closeCustomerFeedbackView").click(function(){
					closeTab("客服回复信息详情");
					customerFeedback.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".customerFeedback-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("客服回复信息详情",customerFeedback.appPath+ "/customerFeedback/toCustomerFeedbackView.do?feedbackNo="+$(this).data("id"));
					});
				});
	        	$(".customerFeedback-operate-reply").each(function(){
					$(this).on("click",function(){
						addTab("客服回复信息编辑",customerFeedback.appPath+ "/customerFeedback/toCustomerFeedbackEdit.do?feedbackNo="+$(this).data("id"));
					});
				});
	        	
	        	$(".member-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("会员详情",customerFeedback.appPath+ "/member/toMemberView.do?memberNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        },
	        del: function (ownerId) {
	    		$.ajax({
	    			url:customerFeedback.appPath+"/customerFeedback/delcustomerFeedback.do",
	    			type:"post",
	    			data:{ownerId:ownerId},
	    			dataType:"json",
	    			success:function(res){ 
	    					var code = res.code;
	    					var data = res.data;
	    					if(code == "1"){ 
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
		    						$("#customerFeedbackList").DataTable().ajax.reload(function(){

		    						}); 
	    						});
	    					}else{
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！"); 
	    					} 
	    			}
	    		});
	    		return false;
	        },
			pageList:function () {	
				var customerFeedbackBtnTpl = $("#customerFeedbackBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(customerFeedbackBtnTpl);
				$('#customerFeedbackList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": customerFeedback.appPath+'/customerFeedback/pageListCustomerFeedback.do',
						"data": function ( d ) {
							var form = $("form[name=customerFeedbackSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"replyStatus":form.find("select[name=replyStatus]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
								"replyTimeStart":form.find("input[name=replyTimeStart]").val()+" 00:00:00",
								"replyTimeEnd":form.find("input[name=replyTimeEnd]").val()+" 23:59:59",
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
			            { "title":"客户","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"内容","data": "content" },
						{ "title":"提交时间","data": "createTime"},
						{ "title":"类型","data": "feedbackType"},
						{ "title":"回复状态","data": "replyStatus"},	
						{ "title":"客服回复时间","data": "replyTime"},
						{ "title":"回复人","data": "operatorName"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#customerFeedbackTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						customerFeedback.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
					               
								{
									"targets" : [ 0 ],
									"render" : function(a,
											b, c, d) {
										if(a){
											return '<span class="glyphicon member-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.memberNo+ '" >'+a+'</span>';
										}else{
											return "";
										}
									}
								},  
					               
					               
					               
					               {
					            	   "targets": [2],
					            	   "render": function(a,b,c,d) {
					            		   if(a!=null){
					            			   if(a.length>20){
					            				   return a.substr(0,10)+"..."; 
					            			   }else{
					            				   return a;
					            			   }
					            			   
					            		   }else{
					            			   return "";
					            		   }
					            	   }
					               },{
					            	   "targets": [3,6],
					            	   "render": function(a,b,c,d) {
					            		   if(a!=null){
					            			   var now = moment(a);
					            			   return now.format('YYYY-MM-DD HH:mm:ss');
					            		   }else{
					            			   return "";
					            		   }
					            	   }
					               },           
						{
						    "targets": [4],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "咨询";
						        	}else if(a==2){
						        		return "投诉";
						        	}else if(a==3){
						        		return "建议";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [5],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "未回复";
						        	}else if(a==1){
						        		return "已回复";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						{
							"targets": [8],
							"render": function (a, b, c, d) {
								var view="";
								var reply="";
								var view='<span class="glyphicon customerFeedback-operate-view" data-id="'+c.feedbackNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								if(c.replyStatus==0){
									reply='<span class="glyphicon customerFeedback-operate-reply" data-id="'+c.feedbackNo+'" data-toggle="tooltip" data-placement="top">回复</span>';
								}
								return view+reply;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCustomerFeedbackSearch").click(function(){
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
	return customerFeedback;
});


