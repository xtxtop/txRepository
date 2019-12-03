define([],function() {	
	var orderMonthCar={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderMonthCar.pageList();
				//查询
				$("#orderMonthCarSearchafter").click(function(){
					orderMonthCar.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
	        	$(".orderMonth-operate-onList").each(function() {
					$(this).on("click", function() {
						addTab("车辆监控",orderMonth.appPath+ "/carMonitor/toCarMonitorListReal.do?flag=1&carPlateNo="+$(this).data("id"));
					});
				});
				$(".orderMonth-operate-carTrack").each(function(){
					$(this).on("click",function(){
						addTab("轨迹回放",orderMonth.appPath+ "/carTrack/toCarTrackList.do?carPlateNo="+$(this).data("id")+"&finishTime="+$(this).data("finishtime")+"&createTime="+$(this).data("id2"));
					});
				});
					
					
	        },
			pageList:function () {	
				var orderMonthCarTpl = $("#orderMonthCarTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderMonthCarTpl);
				$('#orderMonthCarList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderMonthCar.appPath+'/orderMonthCar/pageListOrderMonthCar.do',
						"data": function ( d ) {
							
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$("input[name='orderNoCarNs']").val()
//								
								
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
			            { "title":"品牌","data": "carBrandName" },
						{ "title":"型号","data": "carModelName" },
						
						//{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#orderMonthCartool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#orderMonthCarTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
		       			
					},
					"drawCallback": function( settings ) {
						orderMonthCar.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						
						
					]
				});
			},
	        
	        
	        formatCurrency :function (s,n) { 
	        	n = n > 0 && n <= 20 ? n : 2;  
	        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	        t = "";  
	        for (i = 0; i < l.length; i++) {  
	            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	        }  
	        return t.split("").reverse().join("") + "." + r;  }
	        
	    };
	
	return orderMonthCar;
});


