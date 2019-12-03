define([],function() {	
	var carIdleStatus={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				carIdleStatus.pageList();
				//查询
				$("#carIdleStatusSearchafter").click(function(){
					carIdleStatus.pageList();
	            });
				carIdleStatus.handleClickMore();
				
			},
			operateColumEvent:function(){
				//创建红包车
				$(".carIdleStatus-operate-add-red-packet").each(function(){
					$(this).on("click",function(){
						addTab("红包车添加",carIdleStatus.appPath+ "/carRedPacket/toCarRedPacketAdd.do?carPlateNo="+$(this).data("id"));
					});
				});
				
		       	$(".car-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("车辆详情",carIdleStatus.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
					});
				});
	        	
				
				
			},
			pageList:function () {	
				var carIdleStatusTpl = $("#carIdleStatusTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carIdleStatusTpl);
				$('#carIdleStatusList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carIdleStatus.appPath+'/carIdleStatus/pageListCarIdleStatus.do',
						"data": function ( d ) {
							var form = $("form[name=carIdleStatusSearchForm]");
							
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":form.find("input[name=carPlateNo]").val(),
	        					"carStatus":form.find("select[name=carStatus]").val()
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
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"车辆状态","data": "carStatus" },
						{ "title":"空闲时间","data": "idleTime" },	
						{ "title":"剩余电量","data": "power" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#carIdleStatusTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						carIdleStatus.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
					               
						{
							"targets" : [ 0 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return '<span class="glyphicon car-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.carNo+ '" >'+a+'</span>';
								}else{
									return "";
								}
							}
						},        
					               
					               
						{
						    "targets": [1],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
							        if(a==1){
							        	if(c.chargeState==1){
							        		return "已启动(正在充电)";
							        	}else{
							        		return "已启动(未充电)";
							        	}
							        
							        }else{
							        	if(c.chargeState==1){
							        		return "已熄火(正在充电)";
							        	}else{
							        		return "已熄火(未充电)";
							        	}
							        	
							        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [3],
						    "render": function(a, b, c, d) {
						    	if(a !=null){
						    		return a+"%";
						    	}else{
						    		return 0+"%";
						    	}
						    }
						},
						{
							"targets": [4],
							"render": function (a, b, c, d) {
								var addRedPacket ='<span class="glyphicon carIdleStatus-operate-add-red-packet" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">新增红包车辆</span>';
								return addRedPacket
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#morecarIdleStatus").click(function(){
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
	return carIdleStatus;
});


