define([],function() {	
	var chargingRecord={
			appPath:getPath("app"),	
			init: function () {
	            //数据列表
                chargingRecord.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#chargingRecordSearch").click(function(){
                    chargingRecord.pageList();
	            });
				$("#closeChargingRecordView").click(function(){
					closeTab("充电记录详情");
	            });
	        },

        	operateColumEvent: function() {
            	$(".chargingRecord-operate-view").each(function () {
                	$(this).on("click", function () {
                        addTab("充电记录详情",chargingRecord.appPath+ "/chargingRecord/toCharingRecordView.do?recordNo="+$(this).data("id"));
                	});
            	});
            },
			pageList:function () {	
				var chargingRecordBtnTpl = $("#chargingRecordTpl").html();
				// 预编译模板
				var template = Handlebars.compile(chargingRecordBtnTpl);
				$('#chargingRecordList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": chargingRecord.appPath+'/chargingRecord/pageListchargingRecord.do',
						"data": function ( d ) {
							var form = $("form[name=chargingRecordSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"recordNo":$.trim(form.find("input[name=recordNo]").val()),
								"chargingPileName":$.trim(form.find("input[name=chargingPileName]").val()),
                                "chargingGunNo":$.trim(form.find("input[name=chargingGunNo]").val()),

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
			            { "title":"充电记录编号","data": "recordNo" ,"defaultContent":"--"},
						{ "title":"充电桩名称","data": "chargingPileName" ,"defaultContent":"--"},
						{ "title":"充电枪编号","data": "chargingGunNo" ,"defaultContent":"--"},
						{ "title":"记录时间","data": "recordTime","defaultContent":"--"},
						{ "title":"交易流水号","data": "serialNumber","defaultContent":"--"},
						{ "title":"应用卡号","data": "cardNumber","defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作人","data": "operatorId","defaultContent":"--"},
                        { "title":"开始时间","data": "startTime","defaultContent":"--"},
                        { "title":"结束时间","data": "finishTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#chargingStationTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#chargingStationTool").html("");
						$("#chargingStationTool").removeClass("col-xs-6");
					},
					"drawCallback": function( settings ) {
                        chargingRecord.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
						{
							"targets" : [3],
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
							"targets": [10],
							"render": function (a, b, c, d) {
								var reply="";
								var view='<span class="glyphicon chargingRecord-operate-view" data-id="'+c.recordNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								return view;
							}
						},{
                            "targets" : [8],
                            "render" : function(a,
                                                b, c, d) {
                                if(a!=null){
                                    var now = moment(a);
                                    return now.format('YYYY-MM-DD');
                                }else{
                                    return "--";
                                }
                            }
                        },{
                            "targets" : [9],
                            "render" : function(a,
                                                b, c, d) {
                                if(a!=null){
                                    var now = moment(a);
                                    return now.format('YYYY-MM-DD');
                                }else{
                                    return "--";
                                }
                            }
                        },{
                            "targets" : [6],
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
					]
				});
			},
			
	    };
	return chargingRecord;
});


