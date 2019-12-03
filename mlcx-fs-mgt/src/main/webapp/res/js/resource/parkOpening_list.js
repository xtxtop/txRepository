define([],function() {	
	var parkOpening={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				parkOpening.pageList();
				//查询
				$("#parkOpeningSearch").click(function(){
					parkOpening.pageList();
	            });
				$("#closeparkOpeningView").click(function(){
					closeTab("开设网店详情");
					parkOpening.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".parkOpening-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("开设网店信息详情",parkOpening.appPath+ "/parkOpening/toParkOpeningView.do?parkOpeningNo="+$(this).data("id"));
					});
				});
	        	
	        	$(".member-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("会员详情",parkOpening.appPath+ "/member/toMemberView.do?memberNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        	
	        },
	
			pageList:function () {	
				var parkOpeningBtnTpl = $("#parkOpeningBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkOpeningBtnTpl);
				$('#parkOpeningList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkOpening.appPath+'/parkOpening/pageListPark.do',
						"data": function ( d ) {
							var form = $("form[name=parkOpeningSearchForm]");
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
			            { "title":"客户","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"内容","data": "memo" },
						{ "title":"提交时间","data": "createTime"},
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#parkOpeningTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						parkOpening.operateColumEvent();
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
							"render": function (a, b, c, d) {
								var view="";
								var reply="";
								var view='<span class="glyphicon parkOpening-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'+c.parkOpeningNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								return view+reply;
							}
						}
					]
				});
			}
	    };
	return parkOpening;
});


