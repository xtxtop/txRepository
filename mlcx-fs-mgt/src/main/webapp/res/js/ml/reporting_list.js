define([],function() {	
	var reporting={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				reporting.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#reportingSearchafter").click(function(){
					reporting.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
                $(".reporting-operate-reply").each(function(){
                    $(this).on("click",function(){
                        addTab("违停详情",reporting.appPath+ "/reporting/getReportingNo.do?reportingNo="+$(this).data("id"));
                    });
                });
	        },
			pageList:function () {	
				var reportingBtnTpl = $("#reportingTpl").html();
				// 预编译模板
				var template = Handlebars.compile(reportingBtnTpl);
				$('#reportingList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": reporting.appPath+'/reporting/reportingList.do',
						"data": function ( d ) {
							var form = $("form[name=reportingSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"lockName":$.trim(form.find("input[name=lockName]").val()),
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
			            { "title":"编号","data": "reportingNo" ,"defaultContent":"--"},
						{ "title":"会员名称","data": "memberName" ,"defaultContent":"--"},
						{ "title":"电话","data": "mobilePhone" ,"defaultContent":"--"},
						{ "title":"地锁名称","data": "lockName" ,"defaultContent":"--"},
						{ "title":"上报时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#reportingTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					},
					"drawCallback": function( settings ) {
						reporting.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
						{
							"targets" : [ 4],
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
							"targets": [5],
							"render": function (a, b, c, d) {
                                var reply='<span class="glyphicon reporting-operate-reply" data-id="'+c.reportingNo+'" data-toggle="tooltip" data-placement="top">详情</span>';
								return reply;
							}
						}
					]
				});
			},
			
	    };
	return reporting;
});


