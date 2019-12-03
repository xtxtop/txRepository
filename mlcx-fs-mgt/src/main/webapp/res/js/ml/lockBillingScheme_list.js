define([],function() {	
	var lockBillingScheme={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				lockBillingScheme.pageList();				
				//查询
				$("#lockBillingSchemeSearch").click(function(){
					lockBillingScheme.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
//	        	$(".lockBillingScheme-operate-view").each(function(){
//					$(this).on("click",function(){
//						addTab("充电站详情",lockBillingScheme.appPath+ "/lockBillingScheme/tolockBillingSchemeView.do?stationNo="+$(this).data("id"));
//					});
//				});
                $(".lockBillingScheme-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("编辑地锁计费方案",lockBillingScheme.appPath+ "/lockBillingScheme/toEditlockBillingScheme.do?lockBillingSchemeNo="+$(this).data("id"));
                    });
                });
	        },
	  
			pageList:function () {	
				var lockBillingSchemeBtnTpl = $("#lockBillingSchemeTpl").html();
				// 预编译模板
				var template = Handlebars.compile(lockBillingSchemeBtnTpl);
				$('#lockBillingSchemeList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": lockBillingScheme.appPath+'/lockBillingScheme/pageListlockBillingScheme.do',
						"data": function ( d ) {
							var form = $("form[name=lockBillingSchemeSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"lockSchemeName":$.trim(form.find("input[name=lockSchemeName]").val()),
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
			            { "title":"编号","data": "lockSchemeNo" ,"defaultContent":"---"},
						{ "title":"计费方案名称","data": "lockSchemeName" ,"defaultContent":"---"},
						{ "title":"免费时长","data": "freeTime" ,"defaultContent":"---"},
						{ "title":"单位时间","data": "unitTime","defaultContent":"---"},
						{ "title":"超时金额(元/单位时间)","data": "overtimePrice","defaultContent":"---"},
						{ "title":"状态","data": "status","defaultContent":"---"},
						{ "title":"创建时间","data": "createTime","defaultContent":"---"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#lockBillingSchemeTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#lockBillingSchemeTool").html("");
						$("#lockBillingSchemeTool").removeClass("col-xs-6");
					   $("#lockBillingSchemeTool").append('<button type="button" class="btn-new-type lockBillingSchemeTools-operate-addlockBillingScheme">新增</button>');
					   $(".lockBillingSchemeTools-operate-addlockBillingScheme").on("click",function(){
		       				addTab("新增地锁计费方案", lockBillingScheme.appPath+ "/lockBillingScheme/toAddlockBillingScheme.do");
		       			});
					},
					"drawCallback": function( settings ) {
						lockBillingScheme.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets" : [6],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD');
								}else{
									return "";
								}
							}
						},{
						    "targets": [5],
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
						},{
							"targets": [7],
							"render": function (a, b, c, d) {
								//var view='<span class="glyphicon lockBillingScheme-operate-view" data-id="'+c.stationNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                                var edit='<span class="glyphicon lockBillingScheme-operate-edit" data-id="'+c.lockSchemeNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								
								return edit;
							}
						}
					]
				});
			},
	    };
	return lockBillingScheme;
});


