define([],function() {	
	var join={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				join.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#joinSearchafter").click(function(){
					join.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
                $(".join-operate-reply").each(function(){
                    $(this).on("click",function(){
                        addTab("加盟详情",join.appPath+ "/join/getJoinNo.do?joinNo="+$(this).data("id"));
                    });
                });
	        },
			pageList:function () {	
				var joinBtnTpl = $("#joinTpl").html();
				// 预编译模板
				var template = Handlebars.compile(joinBtnTpl);
				$('#joinList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": join.appPath+'/join/joinList.do',
						"data": function ( d ) {
							var form = $("form[name=joinSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"joinType":$.trim(form.find("select[name=joinType]").val()),
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
			            { "title":"编号","data": "joinNo" ,"defaultContent":"--"},
						{ "title":"会员名称","data": "memberName" ,"defaultContent":"--"},
						{ "title":"电话","data": "mobilePhone" ,"defaultContent":"--"},
						{ "title":"加盟类型","data": "joinType" ,"defaultContent":"--"},
						{ "title":"加盟时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#joinTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					},
					"drawCallback": function( settings ) {
						join.operateColumEvent();
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
							"targets": [3],
							"render": function(a,b,c,d) {
								if(a!=null){
									if(a==1){
										return "充电桩";
									}else if(a==2){
										return "停车场";
									}
								}else{
									return "--";
								}
							}
						},
						{
							"targets": [5],
							"render": function (a, b, c, d) {
                                var reply='<span class="glyphicon join-operate-reply" data-id="'+c.joinNo+'" data-toggle="tooltip" data-placement="top">详情</span>';
								return reply;
							}
						}
					]
				});
			},
			
	    };
	return join;
});


