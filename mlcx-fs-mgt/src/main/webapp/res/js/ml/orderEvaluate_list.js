define([],function() {
	var orderEvaluate={
			appPath:getPath("app"),
			init: function () {
                //数据列表
                orderEvaluate.pageList();
				//查询
				$("#orderEvaluateButton").click(function(){
                    orderEvaluate.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        },
	  
			pageList:function () {	
				var orderEvaluateTpl = $("#orderEvaluateTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderEvaluateTpl);
				$('#orderEvaluateList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderEvaluate.appPath+'/orderEvaluate/pageListOrderEvaluate.do',
						"data": function ( d ) {
							var form = $("form[name=orderEvaluateSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=orderNo]").val()),
								"memberNo":$.trim(form.find("input[name=memberNo]").val()),
								"orderType":$.trim(form.find("select[name=orderType]").val()),
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
			            { "title":"评价编号","data": "evaluateNo" ,"defaultContent":"--"},
						{ "title":"会员编号","data": "memberNo" ,"defaultContent":"--"},
                        { "title":"订单编号","data": "orderNo" ,"defaultContent":"--"},
                        { "title":"订单类型","data": "orderType" ,"defaultContent":"--"},
						{ "title":"评价等级","data": "evaluateGrade","defaultContent":"--"},
						{ "title":"评价备注","data": "evaluateMemo","defaultContent":"--"},
                        { "title":"创建时间","data": "createTime","defaultContent":"--"},
                        { "title":"更新时间","data": "updateTime","defaultContent":"--"},
                        { "title":"操作人类型","data": "operatorType","defaultContent":"--"},
                        { "title":"操作人ID","data": "operatorId","defaultContent":"--"},

					],
					/*"dom" : "<'row'<'#chargingPileTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#chargingPileTool").html("");
						$("#chargingPileTool").removeClass("col-xs-6");
					},*/
					"drawCallback": function( settings ) {
                        orderEvaluate.operateColumEvent();
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
						},
                        {
                            "targets": [3],
                            "render": function(a,b,c,d) {
                                if(a!=null){
                                    if(a==0){
                                        return "充电订单";
                                    }else if(a==1){
                                        return "地锁订单";
                                    }
                                }else{
                                    return "--";
                                }
                            }
                        },{
                            "targets" : [7],
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
                            "targets" : [8],
                            "render" : function(a,
                                                b, c, d) {
                                if(a!=null){
                                    if(a==0){
                                        return "系统自动操作";
                                    }else if(a==1){
                                        return "平台人员操作";
                                    }else if(a==2){
                                        return "商家人员操作";
									}else if(a==3){
                                    	return "会员操作"
									}
                                }else{
                                    return "--";
                                }
                            }
                        },
					]
				});
			},
	    };
	return orderEvaluate;
});


