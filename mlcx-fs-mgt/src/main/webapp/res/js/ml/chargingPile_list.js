define([],function() {	
	var chargingPile={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				chargingPile.pageList();				
				//查询
				$("#chargingPileSearch").click(function(){
					chargingPile.pageList();
	            });
				$("#closechargingPileView").click(function(){
					closeTab("充电桩详情");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
//	        	$(".chargingPile-operate-view").each(function(){
//					$(this).on("click",function(){
//						addTab("充电站详情",chargingPile.appPath+ "/chargingPile/tochargingPileView.do?stationNo="+$(this).data("id"));
//					});
//				});
                $(".chargingPile-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("编辑充电站",chargingPile.appPath+ "/chargingPile/toEditchargingPile.do?chargingPileNo="+$(this).data("id"));
                    });
                });
            	$(".chargingPile-operate-data").each(function(){
					$(this).on("click",function(){
						addTab("监测数据",chargingPile.appPath+ "/chargingPile/toMonitoringData.do?chargingPileNo="+$(this).data("id"));
					});
				});
            	//删除充电枪
	        	$(".chargingPile-operate-del").each(function () {
	                var chargingPileNo = $(this).data("id");
	                $(this).on("click", function () {
	                    bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function (result) {
	                        if (result) {
	                        	chargingPile.deleteChargingPile(chargingPileNo);
	                        }
	                    });
	                });
	            });
	        },
	      //删除
	        deleteChargingPile: function (id) {
	            $.ajax({
	                url: chargingPile.appPath + "/chargingPile/delChargingPile.do",
	                data: {chargingPileNo: id},
	                dataType: "json",
	                success: function (res) {
	                    var code = res.code;
	                    var data = res.data;
	                    if (code == "1") {
	                        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function () {
	                            $("#chargingPileList").DataTable().ajax.reload(function () {});
	                        });
	                    } else {
	                        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败,+"+msg+"！");
	                    }
	                }
	            });
	            return false;
	        },
			pageList:function () {	
				var chargingPileBtnTpl = $("#chargingPileTpl").html();
				// 预编译模板
				var template = Handlebars.compile(chargingPileBtnTpl);
				$('#chargingPileList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": chargingPile.appPath+'/chargingPile/pageListchargingPile.do',
						"data": function ( d ) {
							var form = $("form[name=chargingPileSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"terminalNo":$.trim(form.find("input[name=terminalNo]").val()),
								"electricityType":$.trim(form.find("select[name=electricityType]").val()),
								"stationNo":$.trim(form.find("select[name=stationNo]").val()),
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
			            { "title":"编号","data": "chargingPileNo" ,"defaultContent":"--"},
						{ "title":"充电桩名称","data": "chargingPileName" ,"defaultContent":"--"},
						{ "title":"类型","data": "electricityType" ,"defaultContent":"--"},
						{ "title":"终端编码","data": "terminalNo","defaultContent":"--"},
						{ "title":"充电桩状态","data": "status","defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#chargingPileTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#chargingPileTool").html("");
						$("#chargingPileTool").removeClass("col-xs-6");
					   $("#chargingPileTool").append('<button type="button" class="btn-new-type chargingPileTools-operate-addchargingPile">新增</button>');
					   $(".chargingPileTools-operate-addchargingPile").on("click",function(){
		       				addTab("新增充电桩", chargingPile.appPath+ "/chargingPile/toAddchargingPile.do");
		       			});
					},
					"drawCallback": function( settings ) {
						chargingPile.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets" : [5],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD');
								}else{
									return "";
								}
							}
						},  
						{
						    "targets": [2],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "慢充";
						        	}else if(a==1){
						        		return "快充";
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
						        	if(a==0){
						        		return "无效";
						        	}else if(a==1){
						        		return "有效";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [6],
							"render": function (a, b, c, d) {
								//var view='<span class="glyphicon chargingPile-operate-view" data-id="'+c.stationNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                                var edit='<span class="glyphicon chargingPile-operate-edit" data-id="'+c.chargingPileNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
                                var data='<span class="glyphicon  chargingPile-operate-data" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargingPileNo+'" data-toggle="tooltip" data-placement="top">监测数据</span>';
                            	var del='<span class="glyphicon  chargingPile-operate-del" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargingPileNo+'" data-toggle="tooltip" data-placement="top">删除</span>';
                                return edit+data+del;

							}
						}
					]
				});
			},
	    };
	return chargingPile;
});


