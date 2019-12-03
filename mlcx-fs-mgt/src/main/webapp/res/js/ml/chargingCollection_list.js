define([],function() {
	var chargingCollection={
			appPath:getPath("app"),
			init: function () {
                //数据列表
                chargingCollection.pageList();
				//查询
				$("#chargingCollection").click(function(){
                    chargingCollection.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
//	        	$(".chargingPile-operate-view").each(function(){
//					$(this).on("click",function(){
//						addTab("充电站详情",chargingPile.appPath+ "/chargingPile/tochargingPileView.do?stationNo="+$(this).data("id"));
//					});
//				});
                $(".chargingCollection-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("编辑充电站",chargingCollection.appPath+ "/chargingPile/toEditchargingPile.do?chargingPileNo="+$(this).data("id"));
                    });
                });
	        },
	  
			pageList:function () {	
				var chargingPileBtnTpl = $("#chargingCollectionTpl").html();
				// 预编译模板
				var template = Handlebars.compile(chargingPileBtnTpl);
				$('#chargingCollectionList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": chargingCollection.appPath+'/chargingCollection/getChargingInfo.do',
						"data": function ( d ) {
							var form = $("form[name=chargingCollectionSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"stationName":$.trim(form.find("input[name=stationName]").val()),
								"collectionStatus":$.trim(form.find("select[name=collectionStatus]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
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
			            { "title":"编号","data": "collectionNo" ,"defaultContent":"--"},
						{ "title":"会员姓名","data": "memberName" ,"defaultContent":"--"},
						{ "title":"充电站名称","data": "stationName" ,"defaultContent":"--"},
						{ "title":"收藏状态","data": "collectionStatus","defaultContent":"--"},
						{ "title":"收藏时间","data": "updateTime","defaultContent":"--"},
					],
					"dom" : "<'row'<'#chargingPileTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#chargingPileTool").html("");
						$("#chargingPileTool").removeClass("col-xs-6");
					},
					"drawCallback": function( settings ) {
                        chargingCollection.operateColumEvent();
	        	    },
	        	    //"order": [[ 4, "desc" ]],
					"columnDefs": [
						{
							"targets" : [4],
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
                            "targets": [3],
                            "render": function(a,b,c,d) {
                                if(a!=null){
                                    if(a==1){
                                        return "收藏中";
                                    }else if(a==0){
                                        return "取消收藏";
                                    }
                                }else{
                                    return "--";
                                }
                            }
                        }
					]
				});
			},
	    };
	return chargingCollection;
});


