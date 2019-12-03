define([],function() {	
	var matching={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
				 $('input').attr('autocomplete','off');//input框清楚缓存
	            //数据列表
				matching.pageList();
				
				//查询
				$("#matchingSearchafter").click(function(){
					matching.pageList();
	            });
				$("#closematchingView").click(function(){
					closeTab("配套服务详情");
					
	            });
			},
			//方法加载
	        operateColumEvent: function(){
                $(".matching-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("配套服务编辑",matching.appPath+ "/matching/toMatchingEdit.do?matchingId="+$(this).data("id"));
                    });
                });
                $(".matching-operate-del").on("click",function(){
					var matchingId = $(this).data("id");
					$.post("matching/verifyMatching.do",{matchingId:matchingId},function(res){	
						if(res.code==1){
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
								if(result){
									$.post("matching/delMatching.do",{matchingId:matchingId},function(res){	
										if(res.code==1){
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
												  $(".bootbox").modal("hide");
												  $("#matchingList").DataTable().ajax.reload(function(){
						    						}); 
											  });
										}else{
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
										}
									});
								}						
		       				});
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
						}
					});
				});
                $(".matching-operate-reply").each(function(id,obj){
	        		$(obj).on("click",function(){
	        			var x=[];
	        			x=$(obj).data("id").split(',')
	                	var status='';
	                	if(x[1]==1){
	                		status='启用';
	                	}else{
	                		status='停用';
	                	}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+status+"吗？", function(result) {
							if(result){
								matching.del(x[0],x[1],status);
							}						
						}); 					
					});
				});
	        },
	        del:function(id,enableStatus,status){
	        	$.ajax({
	        		url: matching.appPath+ "/matching/upMatching.do?matchingId="+id+"&enableStatus="+enableStatus, 
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#matchingList").DataTable().ajax.reload(function(){
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"成功！");
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"失败！");
	        			}
	        		}
	        	});      
	        },
			pageList:function () {	
				var matchingBtnTpl = $("#matchingTpl").html();
				// 预编译模板
				var template = Handlebars.compile(matchingBtnTpl);
				$('#matchingList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": matching.appPath+'/matching/pageListMatching.do',
						"data": function ( d ) {
							var form = $("form[name=matchingSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"enableStatus":$.trim(form.find("select[name=enableStatus]").val()),
								"matchingType":$.trim(form.find("select[name=matchingType]").val()),
								
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							//alert(JSON.stringify(json.data))
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			            { "title":"配套服务ID","data": "matchingId" ,"defaultContent":"--"},
						{ "title":"配套服务名称","data": "matchingName" ,"defaultContent":"--"},
						{ "title":"logo图片","data": "matchingPicUrl" ,"defaultContent":"--"},
						{ "title":"状态","data": "enableStatus" ,"defaultContent":"--"},
						{ "title":"类型","data": "matchingType","defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#matchingTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#matchingTool").html("");
						$("#matchingTool").removeClass("col-xs-6");
					   $("#matchingTool").append('<button type="button" class="btn-new-type matchingTools-operate-addmatching">新增</button>');
					   $(".matchingTools-operate-addmatching").on("click",function(){
		       				addTab("新增配套服务", matching.appPath+ "/matching/toAddMatching.do");
		       			});
					},
					"drawCallback": function( settings ) {
						matching.operateColumEvent();
	        	    },
					"columnDefs": [
					     {
								"targets": [2],
								"render": function (a, b, c, d) {
									return "<div class='matching-brand-img' style='background-image:url("+matching.imgPath+'/'+a+")'></div>";
								}
					     },
						{
							"targets" : [ 5],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD');
								}else{
									return "--";
								}
							}
						},  
								  
						{
						    "targets": [3],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "停用";
						        	}else if(a==1){
						        		return "启用";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [4],
							"render": function(a,b,c,d) {
								if(a!=null){
									if(a==1){
										return "充电桩配套";
									}else if(a==2){
										return "停车场配套";
									}
								}else{
									return "--";
								}
							}
						},
						{
							"targets": [6],
							"render": function (a, b, c, d) {
								var reply="";
                                var edit='<span class="glyphicon matching-operate-edit" data-id="'+c.matchingId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								if(c.enableStatus==1){
									reply='<span class="glyphicon matching-operate-reply" data-id="'+c.matchingId+',0" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									reply='<span class="glyphicon matching-operate-reply" data-id="'+c.matchingId+',1" data-toggle="tooltip" data-placement="top">启用</span>';
								}
								var del='<span class="glyphicon matching-operate-del" data-id="'+c.matchingId+'" data-toggle="tooltip" data-placement="top">删除</span>';
								return edit+reply+del;
							}
						}
					]
				});
			},
			
	    };
	return matching;
});


