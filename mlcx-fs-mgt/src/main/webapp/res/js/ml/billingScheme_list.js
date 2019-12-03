define([],function() {	
	var billingScheme={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				billingScheme.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#billingSchemeSearch").click(function(){
					billingScheme.pageList();
	            });
				$("#closebillingSchemeView").click(function(){
					closeTab("计费方案详情");
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".billingScheme-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("计费方案详情",billingScheme.appPath+ "/billingScheme/toBillingSchemeView.do?schemeNo="+$(this).data("id"));
					});
				});
                $(".billingScheme-operate-edit").each(function(){
                    $(this).on("click",function(){
                    	var schemeNo = $(this).data("id");
                    	$.post("billingScheme/checkEditBillingScheme.do",{schemeNo:schemeNo},function(res){	
							if(res.code==1){
								 addTab("计费方案编辑",billingScheme.appPath+ "/billingScheme/toBillingSchemeEdit.do?schemeNo="+schemeNo);
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg+"编辑!");
							}
						});
                    });
                });
                $(".billingScheme-operate-del").on("click",function(){
					var schemeNo = $(this).data("id");
					$.post("billingScheme/checkEditBillingScheme.do",{schemeNo:schemeNo},function(res){	
						if(res.code==1){
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
								if(result){
									$.post("billingScheme/delBillingScheme.do",{schemeNo:schemeNo},function(res){	
										if(res.code==1){
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
												  $(".bootbox").modal("hide");
												  $("#billingSchemeList").DataTable().ajax.reload(function(){
						    						}); 
											  });
										}else{
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
										}
									});
								}						
		       				});
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg+"删除!");
						}
					});
				});
	        },
			pageList:function () {	
				var billingSchemeBtnTpl = $("#billingSchemeTpl").html();
				// 预编译模板
				var template = Handlebars.compile(billingSchemeBtnTpl);
				$('#billingSchemeList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": billingScheme.appPath+'/billingScheme/pageListBillingScheme.do',
						"data": function ( d ) {
							var form = $("form[name=billingSchemeSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"schemeNo":$.trim(form.find("input[name=schemeNo]").val()),
								"status":$.trim(form.find("select[name=status]").val()),
								
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
			            { "title":"计费方案编号","data": "schemeNo" ,"defaultContent":"--"},
			            { "title":"计费方案名称","data": "schemeName" ,"defaultContent":"--"},
						{ "title":"计费方案版本","data": "schemeVersions" ,"defaultContent":"--"},
						{ "title":"状态","data": "status","defaultContent":"---"},
						{ "title":"生效时间","data": "effectiveDate","defaultContent":"--"},
						{ "title":"失效时间","data": "invalidDate","defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#billingSchemeTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#billingSchemeTool").html("");
						$("#billingSchemeTool").removeClass("col-xs-6");
					   $("#billingSchemeTool").append('<button type="button" class="btn-new-type billingSchemeTools-operate-addbillingScheme">新增</button>');
					   $(".billingSchemeTools-operate-addbillingScheme").on("click",function(){
		       				addTab("新增计费方案", billingScheme.appPath+ "/billingScheme/toAddBillingScheme.do");
		       			});
					},
					"drawCallback": function( settings ) {
						billingScheme.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
						{
							"targets" : [ 6],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD');
								}else{
									return "--";
								}
							}
						},  {
							"targets" : [ 4,5],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
										var now = moment(a);
										return now.format('YYYY-MM-DD');
									
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
						        		return "有效";
						        	}else if(a==0){
						        		return "无效";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [7],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon billingScheme-operate-view" data-id="'+c.schemeNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                                var edit='<span class="glyphicon billingScheme-operate-edit" data-id="'+c.schemeNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
                                var del='<span class="glyphicon  billingScheme-operate-del" data-id="'+c.schemeNo+'" data-toggle="tooltip" data-placement="top">删除</span>';
                                return view+edit+del;
							}
						}
					]
				});
			},
			
	    };
	return billingScheme;
});


