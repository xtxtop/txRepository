define([],function() {	
	var chargerGun={
			appPath:getPath("app"),	
			init: function () {
				//数据列表
				chargerGun.pageList();
				
				//关闭充电枪详情页
				$("#closeViewChargerGun").click(function(){
					closeTab("查看充电枪");
	            });
	            
				//模糊查询
				$("#chargerGunSearchBt").click(function(){
					chargerGun.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".chargerGun-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("查看充电枪",chargerGun.appPath+ "/chargerGun/toViewChargingGun.do?chargingGunNo="+$(this).data("id"));
					});
				});
	        	$(".chargerGun-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("编辑充电枪",chargerGun.appPath+ "/chargerGun/toEditChargingGun.do?chargingGunNo="+$(this).data("id"));
					});
				});
	        	//删除充电枪
	        	$(".chargerGun-operate-del").each(function () {
	                var chargingGunNo = $(this).data("id");
	                $(this).on("click", function () {
	                    bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function (result) {
	                        if (result) {
	                        	chargerGun.deleteChargingGun(chargingGunNo);
	                        }
	                    });
	                });
	            });
	        	
	        },

			//删除
			deleteChargingGun: function (id) {
	            $.ajax({
	                url: chargerGun.appPath + "/chargerGun/delChargingGun.do",
	                data: {chargingGunNo: id},
	                dataType: "json",
	                success: function (res) {
	                    var code = res.code;
	                    var data = res.data;
	                    if (code == "1") {
	                        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function () {
	                            $("#chargingGunList").DataTable().ajax.reload(function () {});
	                        });
	                    } else {
	                        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败,"+msg);
	                    }
	                }
	            });
	            return false;
	        },
			
			pageList:function () {	
				var chargerTpl = $("#chargerTpl").html();
				// 预编译模板
				var template = Handlebars.compile(chargerTpl);
				$('#chargingGunList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": chargerGun.appPath+'/chargerGun/pageListChargerGun.do',
						"data": function ( d ) {
							var form = $("form[name=chargerGunSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"workStatus":form.find("select[name=workStatus]").val(),
								"chargingGunCode":$.trim(form.find("input[name=chargingGunCode]").val()),
								"chargingStationNo":form.find("select[name=chargingStationNo]").val()
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
						{ "title":"充电枪编码","data": "chargingGunCode" ,"defaultContent":"--"},
						{ "title":"充电站名称","data": "chargingStationName" ,"defaultContent":"--"},
						{ "title":"充电桩名称","data": "chargingPileName" ,"defaultContent":"--"},
						{ "title":"工作状态","data": "workStatus" ,"defaultContent":"--"},
						{ "title":"创建时间","data": "createTime" ,"defaultContent":"--"},	
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#chargerTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#chargerTools").html("");
					   $("#chargerTools").removeClass("col-xs-6");
					   $("#chargerTools").append('<button type="button" class="btn-new-type chargerTools-operate-addCharger">新增</button>');
		       			$(".chargerTools-operate-addCharger").on("click",function(){
		       				addTab("新增充电枪", chargerGun.appPath+ "/chargerGun/toAddChargerGun.do");
		       			});	 	     			
	        			
					},
					"drawCallback": function( settings ) {
						chargerGun.operateColumEvent();
	        	    },
	        	    "order": [[ 0, "desc" ]],
					"columnDefs": [
						{
						   "targets": [4],
						   "render": function(data, type, row, meta) {
							   if (data) {
	                                return moment(data).format("YYYY-MM-DD HH:mm:ss");
	                            } else {
	                                return "";
	                            }
						    }
						},{
							"targets": [3],
							"render": function(data, type, row, meta) {
								var aa = "";
						    	if(row.workStatus== 1){
						    		aa="告警"
						    	}
						    	if(row.workStatus== 2){
						    		aa="待机"
						    	}
						    	if(row.workStatus== 3){
						    		aa="工作"
						    	}
						    	if(row.workStatus== 4){
						    		aa="离线"
						    	}
						    	if(row.workStatus== 5){
						    		aa="完成"
						    	}
						    	if(row.workStatus== 6){
						    		aa="正在操作充电桩"
						    	}
						    	if(row.workStatus== 7){
						    		aa="预约中"
						    	}
						    	
						    	return aa;
							}
						},{
							"targets": [5],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon chargerGun-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargingGunNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon  chargerGun-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargingGunNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  chargerGun-operate-del" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargingGunNo+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					return view + edit + del;
							}
						}
					]
				});
			}
	    };
	return chargerGun;
});
