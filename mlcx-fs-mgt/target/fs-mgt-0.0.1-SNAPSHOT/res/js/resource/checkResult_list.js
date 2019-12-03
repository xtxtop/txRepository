define([],function() {	
	var checkResult={
			appPath:getPath("app"),	
			init: function () {
	            //数据列表
				checkResult.pageList();
				//查询
				$("#checkResultSearchafter").click(function(){
					checkResult.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".checkResult-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("巡检结果详情",checkResult.appPath+ "/checkResult/toCheckResultView.do?checkResultId="+$(this).data("id"));
					});
				});
	        	$(".checkResult-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("巡检结果编辑",checkResult.appPath+ "/checkResult/toUpdateView.do?checkResultId="+$(this).data("id"));
					});
				});
	        	$(".checkResult-operate-del").each(function(id,obj){
	        		$(obj).on("click",function(){
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								checkResult.del($(obj).data("id"));
							}						
						}); 					
					});
				});
	        },
	        del:function(id){
	        	$.ajax({
	        		url: checkResult.appPath+ "/checkResult/delCheckResult.do?checkResultId="+id, 
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#checkResultList").DataTable().ajax.reload(function(){
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！");
	        			}
	        		}
	        	});      
	        },
			pageList:function () {	
				var checkResultTpl = $("#checkResultTpl").html();
				// 预编译模板
				var template = Handlebars.compile(checkResultTpl);
				$('#checkResultList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": checkResult.appPath+'/checkResult/pageListCheckResult.do',
						"data": function ( d ) {
							var form = $("form[name=checkResultSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"checkPlanNo":$.trim(form.find("input[name=checkPlanNo]").val()),
	        					"checkResult":form.find("select[name=checkResult]").val()
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
			            { "title":"巡检计划号","data": "checkPlanNo" },
						{ "title":"计划日期","data": "planDate" },
						{ "title":"巡检开始时间","data": "actualStartTime" },
						{ "title":"巡检完成时间","data": "actualEndTime" },
						{ "title":"巡检场站","data": "parkName" },	
						{ "title":"巡检项目","data": "checkItemId" },
						{ "title":"设备编号","data": "deviceNo" },
						{ "title":"结果","data": "checkResult" },
						{ "title":"异常简述","data": "abnormalBrief" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#checkResulttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#checkResultTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					    $("#checkResultTools").html("");
					   $("#checkResultTools").removeClass("col-xs-6");
					   $("#checkResultTools").append('<button type="button" class="btn-new-type checkResultTools-operate-addcheckResult">新增</button>');
		       			$(".checkResultTools-operate-addcheckResult").on("click",function(){
		       				addTab("巡检结果新增", checkResult.appPath+ "/checkResult/addCheckResult.do");
		       			});	     			
	        			
					},
					"drawCallback": function( settings ) {
						checkResult.operateColumEvent();
	        	    },
					"columnDefs": [
						 {
						    "targets": [1],
						    "render": function(data, type, row, meta) {
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD'); 
						    }
						},
						{
						    "targets": [2,3],
						    "render": function(data, type, row, meta) {
                              if(data){
                            	  var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    		
						    }
						},
						//巡检项目id（车辆 or 充电桩，具体id见数据字典表）
						 {
						    "targets": [5],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    	    if(data=="10009"){
							        	return "电桩";
							        }else if(data=="10010"){
							        	return "车辆 ";
							        }else{
							        	return "电桩,车辆 ";
							        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						//结果（1、正常，0、异常）
						{
						    "targets": [7],
						    "render": function(data, type, row, meta) {
							        if(data==0){
							        	return "异常";
							        }else{
							        	return "正常"
							        }
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon checkResult-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.checkResultId+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon checkResult-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.checkResultId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var del='<span class="glyphicon checkResult-operate-del" style="text-decoration: underline; cursor: pointer;"data-id="'+c.checkResultId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					return find+edit+del;
							}
						}
//						{
//	        	            "targets": [3,4],
//	        	            "render": function(data, type, row, meta) {
//	        	            	var now = moment(data); 
//	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
//	        	            }
//	        	        }
					]
				});
			}
	    };
	return checkResult;
});


