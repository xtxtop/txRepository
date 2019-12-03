define([],function() {	
	var pricingRuleDayView={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				pricingRuleDayView.customDatePageList();
				$("#closeViewpricingRuleDay").click(function(){
					closeTab("日租计费规则详情");
				});
			},
	        customDatePageList:function () {	
				var pricingRuleViewCustomDateTpl = $("#pricingRuleViewCustomDateTpl").html();
				// 预编译模板
				var template = Handlebars.compile(pricingRuleViewCustomDateTpl);
				$('#pricingRuleViewCustomDateList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": pricingRuleDayView.appPath+'/pricingRuleDay/pageListPricingRuleCustomDate.do',
						"data": function ( d ) {
							var form = $("form[name=pricingRuleViewCustomDateForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"ruleId":form.find("input[name=ruleId]").val(),
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
			            { "title":"计费规则名称","data": "ruleName","defaultContent":"" },
			            //{ "title":"城市","data": "cityName","defaultContent":"" },
						{ "title":"车辆型号","data": "carModelName","defaultContent":"" },
						{ "title":"自定义日期","data": "customizedDate","defaultContent":"" },
						{ "title":"自定义日期按天计费","data": "priceOfDayCustomized","defaultContent":"" },
					],
				   "dom": "<'row'<'#pricingRuleDayViewTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#pricingRuleDayViewTools").html("");
					},
					"drawCallback": function( settings ) {
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						
						{
						    "targets": [2],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						        	return now.format('YYYY-MM-DD'); 
							    }else{
							    	return "";
							    }
						    }
						},
						{
						    "targets": [3],
						    "render": function(data, type, row, meta) {
						    	if(data!=null){
						        	return data+"元/天"; 
							    }else{
							    	return "";
							    }
						    }
						},
					]
				});
	        }
	    };
	return pricingRuleDayView;
});


