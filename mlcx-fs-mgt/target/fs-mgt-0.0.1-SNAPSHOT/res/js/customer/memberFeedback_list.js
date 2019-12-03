define([],function() {	
	var memberFeedback={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				memberFeedback.pageList();
				//查询
				$("#memberFeedbackSearch").click(function(){
					memberFeedback.pageList();
	            });
				$("#closememberFeedbackView").click(function(){
					closeTab("客服回复信息详情");
					memberFeedback.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".memberFeedback-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("客服回复信息详情",memberFeedback.appPath+ "/customerFeedback/toMemberFeedbackView.do?feedbackNo="+$(this).data("id"));
					});
				});
	        	
	        	$(".member-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("会员详情",memberFeedback.appPath+ "/member/toMemberView.do?memberNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        },
	
			pageList:function () {	
				var memberFeedbackBtnTpl = $("#memberFeedbackBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(memberFeedbackBtnTpl);
				$('#memberFeedbackList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": memberFeedback.appPath+'/customerFeedback/pageListMemberFeedback.do',
						"data": function ( d ) {
							var form = $("form[name=memberFeedbackSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"replyStatus":form.find("select[name=replyStatus]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
								
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
						//{ "title":"回复状态","data": "replyStatus"},	
						//{ "title":"客服回复时间","data": "replyTime"},
						//{ "title":"回复人","data": "operatorName"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#memberFeedbackTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						memberFeedback.operateColumEvent();
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
					            	   "targets": [3],
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
						},
						{
							"targets": [5],
							"render": function (a, b, c, d) {
								var view="";
								var reply="";
								var view='<span class="glyphicon memberFeedback-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'+c.feedbackNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								return view+reply;
							}
						}
					]
				});
			}
	    };
	return memberFeedback;
});


