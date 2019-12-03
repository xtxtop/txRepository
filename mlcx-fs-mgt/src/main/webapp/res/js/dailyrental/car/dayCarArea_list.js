define([],function() {	
	var dayCarArea={
			appPath:getPath("app"),	
			init: function () {
				
	            //数据列表
				dayCarArea.pageList();
				//查询
				$("#dayCarAreaSearchafter").click(function(){
					dayCarArea.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".dayCarArea-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("服务范围详情",dayCarArea.appPath+ "/dayCarArea/toDayCarAreaView.do?carAreaId="+$(this).data("id"));
					});
				});
	        	$(".dayCarArea-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("服务范围编辑",dayCarArea.appPath+ "/dayCarArea/toEditAddDayCarArea.do?carAreaId="+$(this).data("id"));
					});
				});
				
	        	//启用
				$(".dayCarArea-operate-onList").on("click",function(){
					var carAreaId = $(this).data("id");
					var isAvailable=1;
					$.post("dayCarArea/toEditDayCarArea.do?carAreaId="+carAreaId+"&isAvailable="+isAvailable,
						function(res){	
						if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#dayCarAreaList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
								
							});
												
       				
					
				});
	        	
				//停用
				$(".dayCarArea-operate-offList").on("click",function(){
					var carAreaId = $(this).data("id");
					var isAvailable=0;
					$.post("dayCarArea/toEditDayCarArea.do?carAreaId="+carAreaId+"&isAvailable="+isAvailable,
						function(res){	
						if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#dayCarAreaList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
								
							});
												
       				
					
				});
	        	
	        	
	        },
	        

	
			pageList:function () {	
				var dayCarAreaTpl = $("#dayCarAreaTpl").html();
				// 预编译模板
				var template = Handlebars.compile(dayCarAreaTpl);
				$('#dayCarAreaList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": dayCarArea.appPath+'/dayCarArea/pageListDayCarArea.do',
						"data": function ( d ) {
							var form = $("form[name=dayCarAreaSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
//								"dayCarAreaNo":$.trim(form.find("input[name=dayCarAreaNo]").val()),
								"areaName":$.trim(form.find("input[name=areaName]").val()),
//	        					"supportedServices":form.find("select[name=supportedServices]").val(),
        					    "isAvailable":form.find("select[name=isAvailable]").val(),
//	        					"dayCarAreaType":form.find("select[name=dayCarAreaType]").val()
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
						{ "title":"名称","data": "areaName" },
						{ "title":"状态","data": "isAvailable" },	
						{ "title":"创建时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#dayCarAreatool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#dayCarAreaTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					    $("#dayCarAreaTools").html("");
					   $("#dayCarAreaTools").removeClass("col-xs-6");
					   $("#dayCarAreaTools").append('<button type="button" class="btn-new-type dayCarAreaTools-operate-adddayCarArea">新增</button>');
		       			$(".dayCarAreaTools-operate-adddayCarArea").on("click",function(){
		       				addTab("服务区域新增", dayCarArea.appPath+ "/dayCarArea/toAddDayCarArea.do");
		       			});	     			
	        			
					},
					"drawCallback": function( settings ) {
						debugger
						dayCarArea.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
		              						 {
						    "targets": [0],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						    		
						    		
						    		
						    		
						    		return a;
						        }else{
						        	return "";
						        }
						    }
						},
						
						
						//场站状态（0、停用，1、启用，默认0）
						{
						    "targets": [1],
						    "render": function(data, type, row, meta) {
						    		if(data==0){
						    			return "停用";
							        }else{
							        	return "启用";
							        }	
						    }
						},
						{
						    "targets": [2],
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
							"targets": [3],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  dayCarArea-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.carAreaId+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon  dayCarArea-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.carAreaId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var onList='<span class="glyphicon  dayCarArea-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.carAreaId+'" data-toggle="tooltip" data-placement="top">启用</span>';
	        					var offList='<span class="glyphicon  dayCarArea-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.carAreaId+'" data-toggle="tooltip" data-placement="top">停用</span>';
	        					if(c.isAvailable==0){
	        						return find+edit+onList;
	        					}else{
	        						if(c.isView==0){
	        							return find+edit+offList;
	        						}else{
	        							return find+edit+offList;
	        						}
	        						
	        					}
							}
						}
//						{
//	        	            "targets": [3,4],
//	        	            "render": function(data, type, row, meta) {
//	        	            	var now = moment(data); 
//	        	            	dayCar now.format('YYYY-MM-DD HH:mm:ss'); 
//	        	            }
//	        	        }
					]
				});
			}
	    };
	return dayCarArea;
});


