define([],function() {	
	var parkingLock={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				parkingLock.pageList();				
				//查询
				$("#parkingLockSearch").click(function(){
					parkingLock.pageList();
	            });
				$("#closeparkingLockView").click(function(){
					closeTab("地锁详情");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
//	        	$(".parkingLock-operate-view").each(function(){
//					$(this).on("click",function(){
//						addTab("充电站详情",parkingLock.appPath+ "/parkingLock/toparkingLockView.do?stationNo="+$(this).data("id"));
//					});
//				});
                $(".parkingLock-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("编辑地锁",parkingLock.appPath+ "/parkingLock/toEditparkingLock.do?parkingLockNo="+$(this).data("id"));
                    });
                });
	        },
	  
			pageList:function () {	
				var parkingLockBtnTpl = $("#parkingLockTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkingLockBtnTpl);
				$('#parkingLockList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkingLock.appPath+'/parkingLock/pageListparkingLock.do',
						"data": function ( d ) {
							var form = $("form[name=parkingLockSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkingLockName":$.trim(form.find("input[name=parkingLockName]").val()),
								"parkingLockType":$.trim(form.find("select[name=parkingLockType]").val()),
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
			            { "title":"编号","data": "parkingLockNo" ,"defaultContent":"---"},
						{ "title":"地锁名称","data": "parkingLockName" ,"defaultContent":"---"},
						{ "title":"类型","data": "parkingLockType" ,"defaultContent":"---"},//产品类型
						{ "title":"序列号","data": "parkingLockSerialNumber","defaultContent":"---"},
						{ "title":"状态","data": "parkingLockStatus","defaultContent":"---"},//升降
						{ "title":"创建时间","data": "createTime","defaultContent":"---"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#parkingLockTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#parkingLockTool").html("");
						$("#parkingLockTool").removeClass("col-xs-6");
					   $("#parkingLockTool").append('<button type="button" class="btn-new-type parkingLockTools-operate-addparkingLock">新增</button>');
					   $(".parkingLockTools-operate-addparkingLock").on("click",function(){
		       				addTab("新增地锁", parkingLock.appPath+ "/parkingLock/toAddparkingLock.do");
		       			});
					},
					"drawCallback": function( settings ) {
						parkingLock.operateColumEvent();
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
						        		return "网关版";
						        	}else if(a==1){
						        		return "UDP版";
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
						        		return "升起";
						        	}else if(a==1){
						        		return "落下";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [6],
							"render": function (a, b, c, d) {
								//var view='<span class="glyphicon parkingLock-operate-view" data-id="'+c.stationNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                                var edit='<span class="glyphicon parkingLock-operate-edit" data-id="'+c.parkingLockNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								
								return edit;
							}
						}
					]
				});
			},
	    };
	return parkingLock;
});


