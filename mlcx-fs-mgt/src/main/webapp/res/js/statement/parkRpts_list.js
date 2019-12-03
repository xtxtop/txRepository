define([],function() {	
	var parkRpts={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				parkRpts.pageList();
				//查询
				$("#parkRptsSearch").click(function(){
					var form = $("form[name=parkRptsSearchForm]");
					var finishTimeStart =  form.find("input[name=finishTimeStart]").val();
					var finishTimeEnd = form.find("input[name=finishTimeEnd]").val();
					if(finishTimeStart!=""&&finishTimeEnd!=""){
						if(new Date(finishTimeStart)>new Date(finishTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							parkRpts.pageList();
						}
					}else{
						parkRpts.pageList();
					}
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        },
			pageList:function () {
				var parkRptsBtnTpl = $("#parkRptsBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkRptsBtnTpl);
				$('#parkRptsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkRpts.appPath+'/financialStatement/pageListParkRpts.do',
						"data": function ( d ) {
							var form = $("form[name=parkRptsSearchForm]");
							var finishTimeStart=form.find("input[name=finishTimeStart]").val();
							var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"startTime":form.find("input[name=finishTimeStart]").val()+ " 00:00:00",
								"endTime":form.find("input[name=finishTimeEnd]").val()+ " 23:59:59",
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
			            { "title":"场站编号","data": "startParkNo" },
						{ "title":"场站名称","data": "parkName" },
//						{ "title":"时长(分钟)","data": "orderDuration" },
						{ "title":"订单数","data": "orderNum"},
						{ "title":"总金额(元)","data": "orderAmount"},
						{ "title":"场站还车附加费(元)","data": "serviceFeeBack"},
						{ "title":"场站取车附加费(元)","data": "serviceFeeGet"},
						{ "title":"已实收金额(元)","data": "alreadyAmount"},
						{ "title":"余额抵扣单数","data": "balanceNum" },
						{ "title":"余额抵扣额(元)","data": "balanceDiscountAmount" },
						{ "title":"券抵扣单数","data": "discountAmountNum" },
						{ "title":"券抵扣额(元)","data": "discountAmount" },
						{ "title":"冲账单数","data": "strikeBalanceOrderNum" },
						{ "title":"冲账额(元)","data": "strikeBalanceAmount" },
/*						{ "title":"微信订单数","data": "wxOrderNum"},
						{ "title":"微信订单金额(元)","data": "wxAmount"},
						{ "title":"微信实收金额","data": "wxRealAmount"},
						{ "title":"微信手续费","data": "wxCharge"},
						{ "title":"支付宝订单数","data": "alipayOrderNum"},
						{ "title":"支付宝订单金额","data": "alilpayAmount"},
						{ "title":"支付宝实收金额","data": "alilpayRealAmount"},
						{ "title":"支付宝手续费","data": "alipayCharge"}*/
					],
				   "dom": "<'row'<'#parkRptsTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#parkRptsTools").html("");
					   $("#parkRptsTools").removeClass("col-xs-6");
					   $("#parkRptsTools").append('<button type="button" class="btn-new-type parkRptsTools-operate-export">导出</button>');
		       			$(".parkRptsTools-operate-export").on("click",function(){
		       				var form = $("form[name=parkRptsSearchForm]");
		       				var finishTimeStart=form.find("input[name=finishTimeStart]").val();
							var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = parkRpts.appPath+ "/financialStatement/toParkRptsExport.do?startTime="+finishTimeStart+" 00:00:00"+"&endTime="+finishTimeEnd+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						parkRpts.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [3,4,5,6,8,10,12],
						    "render": function(a,b,c,d) {//
						    	if(a){
		    	            		return parkRpts.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
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
	return parkRpts;
});