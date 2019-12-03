define([],function() {	
	var customerFeedbackWorker={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				customerFeedbackWorker.pageList();
				//查询
				$("#customerFeedbackWorkerSearch").click(function(){
					var form = $("form[name=customerFeedbackWorkerSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "提交开始时间不能大于结束时间！");
						}else{
							customerFeedbackWorker.pageList();
						}
					}else{
						customerFeedbackWorker.pageList();
					}
	            });
				$("#closeCustomerFeedbackWorkerView").click(function(){
					closeTab("问题反馈信息详情");
					customerFeedbackWorker.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".customerFeedbackWorker-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("问题反馈信息详情",customerFeedbackWorker.appPath+ "/worker/toCustomerFeedbackWorkerView.do?feedbackNo="+$(this).data("id"));
					});
				});
	        },
			pageList:function () {	
				var customerFeedbackBtnTpl = $("#customerFeedbackBtnTplWorker").html();
				// 预编译模板
				var template = Handlebars.compile(customerFeedbackBtnTpl);
				$('#customerFeedbackListWorker').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": customerFeedbackWorker.appPath+'/worker/pageListCustomerFeedback.do',
						"data": function ( d ) {
							var form = $("form[name=customerFeedbackWorkerSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
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
			            { "title":"调度员","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"内容","data": "content" },
						{ "title":"提交时间","data": "createTime"},
						{ "title":"类型","data": "feedbackType"},
//						{ "title":"回复状态","data": "replyStatus"},	
//						{ "title":"客服回复时间","data": "replyTime"},
//						{ "title":"回复人","data": "operatorName"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#customerFeedbackWorkerTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						customerFeedbackWorker.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
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
								var view='<span class="glyphicon customerFeedbackWorker-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'+c.feedbackNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								return view;
							}
						}
					]
				});
			}
	    };
	return customerFeedbackWorker;
});


