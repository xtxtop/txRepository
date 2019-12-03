define([],function() {	
	var orderStat={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderStat.pageList();
				//查询
				$("#orderStatSearch").click(function(){
					var form = $("form[name=orderStatSearchForm]");
					var startTime =  form.find("input[name=startTime]").val();
					var endTime = form.find("input[name=endTime]").val();
					if(startTime != "" && endTime != ""){
						if(new Date(startTime)>new Date(endTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "查询开始时间不能大于结束时间！");
						}else{
							orderStat.pageList();
						}
					}else{
						orderStat.pageList();
					}
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	
	        },
			pageList:function () {
				var orderStatBtnTpl = $("#orderStatBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderStatBtnTpl);
				$('#orderStatList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderStat.appPath+'/financialStatement/pageListOrderStat.do',
						"data": function ( d ) {
							var form = $("form[name=orderStatSearchForm]");
							var startTime=form.find("input[name=startTime]").val();
							var endTime=form.find("input[name=endTime]").val();
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"startTime":form.find("input[name=startTime]").val()+ " 00:00:00",
								"endTime":form.find("input[name=endTime]").val()+ " 23:59:59",
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
			            { "title":"订单数","data": "orderNo" },
						{ "title":"订单总金额（元）","data": "orderAmount" },
						{ "title":"实收金额(元)","data": "payableAmount"},
						{ "title":"余额抵扣单数","data": "balanceNum"},
						{ "title":"余额抵扣额（元）","data": "balanceDiscountAmount"},
						{ "title":"券抵扣单数","data": "discountAmountNum"},
						{ "title":"券抵扣总额（元）","data": "discountAmount"},
						{ "title":"冲账订单数","data": "strikeBalanceOrderNum"},
						{ "title":"冲账总金额(元)","data": "strikeBalanceAmount" },
						{ "title":"未支付单数","data": "noPayOrderNum" },
						{ "title":"未支付总金额(元)","data": "noPayAmount"}
					],
				   "dom": "<'row'<'#orderStatTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#orderStatTools").html("");
					   $("#orderStatTools").removeClass("col-xs-6");
					   $("#orderStatTools").append('<button type="button" class="btn-new-type orderStatTools-operate-export">导出</button>');
		       			$(".orderStatTools-operate-export").on("click",function(){
		       				var form = $("form[name=orderStatSearchForm]");
		       				var startTime=form.find("input[name=startTime]").val();
							var endTime=form.find("input[name=endTime]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = orderStat.appPath+ "/financialStatement/toOrderStatisticalExport.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						orderStat.operateColumEvent();
	        	    },
					"columnDefs": [
					    {
						    "targets": [1,2,4,6,8,10],
						    "render": function(a,b,c,d) {//
						    	if(a){
						    		return orderStat.formatCurrency(a);
		    	            	}else{
		    	            		return "0";
		    	            	}
						    }
						},
					]
				});
			},formatCurrency :function (s,n) { 
	        	n = n > 0 && n <= 20 ? n : 2;  
		        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
		        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
		        t = "";  
		        for (i = 0; i < l.length; i++) {  
		            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		        }  
		        return t.split("").reverse().join("") + "." + r;  }
	    };
	return orderStat;
});