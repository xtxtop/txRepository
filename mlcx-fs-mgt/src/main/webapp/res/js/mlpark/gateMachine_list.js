define([],function() {	
	var gateMachine={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				gateMachine.pageList();
				//查询
				$("#gateMachineSearchafter").click(function(){
					gateMachine.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
					$(".gateMachine-operate-edit").on("click",function(){		
						var a=$(this).parent().parent().children().eq(1).text();
						addTab("闸机编辑",gateMachine.appPath+ "/gateMachine/togateMachineEdit.do?gateNo="+$(this).data("id")+"&parkingName="+a);			
					});
					$(".gateMachine-operate-del").on("click",function(){
						var gateNo = $(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.post("gateMachine/delgateMachine.do",{gateNo:gateNo},function(res){	
									if(res.code==1){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
											  $(".bootbox").modal("hide");
											  $("#gateMachineList").DataTable().ajax.reload(function(){
					    						}); 
										  });
										
									}else{
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
									}
									
								});
							}						
	       				});
						
					});
	        },
			pageList:function () {	
				var gateMachineTpl = $("#gateMachineTpl").html();
				// 预编译模板
				var template = Handlebars.compile(gateMachineTpl);
				$('#gateMachineList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": gateMachine.appPath+'/gateMachine/gateMachineList.do',
						"data": function ( d ) {
							var form = $("form[name=gateMachineSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"gateName":$.trim(form.find("input[name=gateName]").val()),
								"parkingNo":$.trim(form.find("select[name=parkingNo]").val()),
								"gateStatus":$.trim(form.find("select[name=gateStatus]").val()),
								"activeCondition":$.trim(form.find("select[name=activeCondition]").val()),
	        					"onlineStatus":form.find("select[name=onlineStatus]").val(),
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
						{ "title":"闸机编号","data": "gateCode" },
						{ "title":"停车场名称","data": "parkingName" },
						{ "title":"闸机名称","data": "gateName"},
						{ "title":"升降状态","data": "gateStatus"},
						{ "title":"闸机状态","data": "activeCondition"},
						{ "title":"在线状态","data": "onlineStatus"},
						{ "title":"创建时间","data": "createTime"},
						{ "title":"修改时间","data": "updateTime"},
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#gateMachinetool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#gateMachineTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#gateMachineTools").html("");
		       			$("#gateMachineTools").append('<button type="button" class="btn-new-type gateMachineTools-operate-add">新增</button>');
		       			
		       			$(".gateMachineTools-operate-add").on("click",function(){
		       				addTab("新增闸机", gateMachine.appPath+ "/gateMachine/togateMachineAdd.do");
		       			});	 
		       			
					},
					"drawCallback": function( settings ) {
						gateMachine.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(a==1){
						        		return "降";
						        	}else if(a==0){
						        		return "升";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(a==1){
						        		return "停用";
						        	}else if(a==0){
						        		return "启用";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [5],
							"render": function(a,b, c, d) {
								if(a!=null){
									if(a==1){
										return "离线";
									}else if(a==0){
										return "在线";
									}
								}else{
									return "--";
								}
							}
						},
						{
							"targets" : [7,6],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD HH:mm:ss');
								}else{
									return "--";
								}
							}
						},  
						{
							"targets": [8],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon  gateMachine-operate-edit" data-id="'+c.gateNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  gateMachine-operate-del" data-id="'+c.gateNo+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+del;
	        					
							}
						}
					]
				});
			},
	    };
	return gateMachine;
});


