define([],function() {	
	var orderDayCharging={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderDayCharging.pageList();
				//查询
				$("#orderDayChargingSearchafter").click(function(){
					orderDayCharging.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
	        	
	        },
			pageList:function () {	
				var orderDayChargingTpl = $("#orderDayChargingTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderDayChargingTpl);
				$('#orderDayChargingList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderDayCharging.appPath+'/orderDayCharging/pageListOrderDayCharging.do',
						"data": function ( d ) {
							var form = $("form[name=orderDayChargingSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":form.find("input[name='orderNocg']").val(),
								"startBillingTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"finishTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59"
								
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
			            { "title":"订单日期","data": "orderDayTime" },
						{ "title":"每日租金","data": "orderDayAmount" },
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#orderDayChargingtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#orderDayChargingTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   
		       			
		       			/**
		       			 * 导出
		       			 */
		       			/*$(".orderDayChargingTools-operate-export").on("click",function(){
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出模板吗？", function(result) {
								if(result){
									window.location.href = orderDayCharging.appPath+ "/orderDayCharging/downloadExcelFile.do?filepath=orderDayCharging&newFileName=orderDayCharging.xls";;
								}						
		       				});
		       			});*/
		       			
					},
					"drawCallback": function( settings ) {
						orderDayCharging.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [0],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						        	return now.format('YYYY-MM-DD HH:mm:ss');
						    	}else{
						    		return "";
						    	}
						    	 
						    }
						},
						
						 {
						    "targets": [1],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return orderDayCharging.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
						
						
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
	
	return orderDayCharging;
});


