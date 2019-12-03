define([],function() {	
	var orderComments={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderComments.pageList();
				//查询
				$("#orderCommentsSearch").click(function(){
					orderComments.pageList();
	            });
			},
			pageList:function () {	
				var orderCommentsBtnTpl = $("#orderCommentsBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderCommentsBtnTpl);
				$('#orderCommentsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderComments.appPath+'/orderComments/pageListOrderComments.do',
						"data": function ( d ) {
							var form = $("form[name=orderCommentsSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=orderNo]").val()),
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"carModelName":$.trim(form.find("input[name=carModelName]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val())
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
					    { "title":"订单号","data": "orderNo" },
			            { "title":"车牌","data": "carPlateNo" },
			            { "title":"车型","data": "carModelName" },
			            { "title":"用户姓名","data": "memberName" },
						{ "title":"手机","data": "mobilePhone" },
						{ "title":"评价星级","data": "score" },	
						{ "title":"评价内容","data": "content" }
					],
				   "dom": "<'row'<'#orderCommentsTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
	        	    }
				});
			}
	    };
	return orderComments;
});


