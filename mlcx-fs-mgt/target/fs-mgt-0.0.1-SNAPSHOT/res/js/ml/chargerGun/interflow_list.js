define([],function() {	
	var interflow={
			appPath:getPath("app"),	
			init: function () {
				//数据列表
				interflow.pageList();
				
				//关闭监测数据详情页
				$("#closeViewinterflow").click(function(){
					closeTab("查看监测数据");
	            });
				//模糊查询
				$("#interflowSearchBt").click(function(){
					interflow.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".interflow-operate-find").each(function(){
					$(this).on("click",function(){
						var interflowNo = $(this).data("id");
						addTab("查看监测数据",interflow.appPath+ "/chargerGun/toMonitoringData.do?id="+interflowNo+"&type=1");
					});
				});
	        	
	        },
			
			pageList:function () {	
				var chargerTpl = $("#chargerTpl").html();
				// 预编译模板
				var template = Handlebars.compile(chargerTpl);
				$('#interflowList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": interflow.appPath+'/interflow/pageListMonitorDataInterflow.do',
						"data": function ( d ) {
							var form = $("form[name=interflowSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"interflowNo":form.find("input[name=interflowNo]").val(),
								"chargingPileNo":form.find("input[name=chargingPileNo]").val()
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
						{ "title":"编号","data": "interflowNo" ,"defaultContent":"--"},
						{ "title":"充电桩名称","data": "chargingPileName" ,"defaultContent":"--"},
						{ "title":"充电枪编码","data": "chargingGunCode" ,"defaultContent":"--"},
						{ "title":"工作状态","data": "workState" ,"defaultContent":"--"},
						{ "title":"连接开关状态","data": "switchState" ,"defaultContent":"--"},	
						{ "title":"累计充电时间(分钟)","data":"time","defaultContent":"--" },
						{ "title":"保存时间","data":"recordTime","defaultContent":"--" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#chargerTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						interflow.operateColumEvent();
	        	    },
	        	    "order": [[ 0, "desc" ]],
					"columnDefs": [
						{
						   "targets": [6],
						   "render": function(data, type, row, meta) {
							   if (data) {
	                                return moment(data).format("YYYY-MM-DD HH:mm:ss");
	                            } else {
	                                return "--";
	                            }
						    }
						},{
							   "targets": [4],
							   "render": function(data, type, row, meta) {
								   if (data==1) {
		                                return "开";
		                            } else {
		                                return "关";
		                            }
							    }
							},{
							"targets": [3],
							"render": function(data, type, row, meta) {
								var aa = "";
						    	if(data== 1){
						    		aa="告警"
						    	}
						    	if(data== 2){
						    		aa="待机"
						    	}
						    	if(data== 3){
						    		aa="工作"
						    	}
						    	if(data== 4){
						    		aa="离线"
						    	}
						    	if(data== 5){
						    		aa="完成"
						    	}
						    	if(data== 6){
						    		aa="正在操作充电桩"
						    	}
						    	if(data== 7){
						    		aa="预约中"
						    	}
						    	
						    	return aa;
							}
						},{
							"targets": [7],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon interflow-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.interflowNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
	        					return view ;
							}
						}
					]
				});
			}
	    };
	return interflow;
});
