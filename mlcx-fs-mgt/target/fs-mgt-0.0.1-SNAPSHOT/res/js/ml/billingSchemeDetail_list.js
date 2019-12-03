define([],function() {	
	var billingSchemeDetail={
			appPath:getPath("app"),	
			init: function () {	
				 $('input').attr('autocomplete','off');//input框清楚缓存
	            //数据列表
				billingSchemeDetail.pageList();
				
				//查询
				$("#billingSchemeDetailSearchafter").click(function(){
					billingSchemeDetail.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
                $(".billingSchemeDetail-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("明细编辑",billingSchemeDetail.appPath+ "/detail/toBillingSchemeDetailEdit.do?detailNo="+$(this).data("id"));
                    });
                });
	        },
			pageList:function () {	
				var billingSchemeDetailBtnTpl = $("#billingSchemeDetailTpl").html();
				// 预编译模板
				var template = Handlebars.compile(billingSchemeDetailBtnTpl);
				$('#billingSchemeDetailList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": billingSchemeDetail.appPath+'/detail/pageListBillingSchemeDetail.do',
						"data": function ( d ) {
							var form = $("form[name=billingSchemeDetailSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"detailNo":$.trim(form.find("input[name=detailNo]").val()),
								
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
			            { "title":"明细编号","data": "detailNo" ,"defaultContent":"--"},
						{ "title":"计费方案名称","data": "schemeName" ,"defaultContent":"--"},
						{ "title":"序号","data": "serialNumber" ,"defaultContent":"--"},
						{ "title":"开始时间","data": "startTime" ,"defaultContent":"--"},
						{ "title":"结束时间","data": "finishTime","defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"更新时间","data": "updateTime","defaultContent":"--"},
						{ "title":"备注","data": "memo","defaultContent":"--"},
						{ "title":"状态","data": "status","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#billingSchemeDetailTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#billingSchemeDetailTool").html("");
						$("#billingSchemeDetailTool").removeClass("col-xs-6");
					   $("#billingSchemeDetailTool").append('<button type="button" class="btn-new-type billingSchemeDetailTools-operate-addbillingSchemeDetail">新增</button>');
					   $(".billingSchemeDetailTools-operate-addbillingSchemeDetail").on("click",function(){
		       				addTab("新增明细", billingSchemeDetail.appPath+ "/detail/toAddBillingSchemeDetail.do");
		       			});
					},
					"drawCallback": function( settings ) {
						billingSchemeDetail.operateColumEvent();
	        	    },
					"columnDefs": [
						{
							"targets" : [ 3,4],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('HH:mm');
								}else{
									return "--";
								}
							}
						}, {
							"targets" : [ 5,6],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD HH-mm-ss');
								}else{
									return "--";
								}
							}
						},   {
						    "targets": [7],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "尖时段";
						        	}else if(a==2){
						        		return "峰时段";
						        	}else if(a==3){
						        		return "平时段";
						        	}else if(a==4){
						        		return "谷时段";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
								  
						{
						    "targets": [8],
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
							"targets": [9],
							"render": function (a, b, c, d) {
                                var edit='<span class="glyphicon billingSchemeDetail-operate-edit" data-id="'+c.detailNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								
								return edit;
							}
						}
					]
				});
			},
			
	    };
	return billingSchemeDetail;
});


