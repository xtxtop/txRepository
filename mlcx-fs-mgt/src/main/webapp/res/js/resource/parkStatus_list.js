define([],function() {	
	var parkStatus={
			appPath:getPath("app"),	
			init: function () {
	            //数据列表
				parkStatus.pageList();
				//查询
				$("#parkStatusSearchafter").click(function(){
					parkStatus.pageList();
	            });
			},
			pageList:function () {	
				var parkStatusTpl = $("#parkStatusTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkStatusTpl);
				$('#parkStatusList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkStatus.appPath+'/parkStatus/pageListParkStatus.do',
						"data": function ( d ) {
							var form = $("form[name=parkStatusSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkNo":$.trim(form.find("input[name=parkNo]").val()),
								"parkName":$.trim(form.find("input[name=parkName]").val())
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
			            { "title":"编号","data": "parkNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"名称","data": "parkName" },
						{ "title":"类型","data": "parkType" },
						{ "title":"车位数","data": "parkingSpaces" },
						{ "title":"空闲车位","data": "freeParking" },	
						{ "title":"车辆数","data": "carNumber" },
						{ "title":"电桩数","data": "chargerNumber" },
						{ "title":"预约数","data": "reservedCarNumber" },
						{ "title":"驶出","data": "carOut" },
//						{ "title":"驶入","data": "carIn" },	
						{ "title":"当前预警","data": "warningSubType" }	
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#parkStatustool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#parkStatusTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
	        	            "targets": [3],
	        	            "render": function(data, type, row, meta) {
	        	            	if(data){
							        if(data==1){
							        	return "管理类";
							        }else{
							        	return "使用类";
							        }
						    	}else{
						    		return "";
						    	}
	        	            }
	        	        }
					]
				});
			}
	    };
	return parkStatus;
});


