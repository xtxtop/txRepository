define([],function() {	
	var carRpts={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				carRpts.pageList();
				//查询
				$("#carRptsSearch").click(function(){
					var form = $("form[name=carRptsSearchForm]");
					var finishTimeStart =  form.find("input[name=finishTimeStart]").val();
					var finishTimeEnd = form.find("input[name=finishTimeEnd]").val();
					if(finishTimeStart!=""&&finishTimeEnd!=""){
						if(new Date(finishTimeStart)>new Date(finishTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							carRpts.pageList();
						}
					}else{
						carRpts.pageList();
					}
	            });
			},
			//方法加载
			
	        operateColumEvent: function(){
	        },
			pageList:function () {
				var carRptsBtnTpl = $("#carRptsBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carRptsBtnTpl);
				$('#carRptsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carRpts.appPath+'/financialStatement/pageListCarRpts.do',
						"data": function ( d ) {
							var form = $("form[name=carRptsSearchForm]");
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
			            { "title":"车牌号","data": "carPlateNo" },
						{ "title":"订单数","data": "orderNum" },
						{ "title":"时长(分钟)","data": "orderDuration" },
						{ "title":"订单金额(元)","data": "orderAmount"},
						{ "title":"实收金额","data": "alreadyAmount"}/*,
						{ "title":"实收金额对应时长","data": "alreadyDuration"},
						{ "title":"套餐抵扣订单数","data": "packOrderNum"},
						{ "title":"套餐抵扣金额","data": "packAmount"},
						{ "title":"套餐抵扣时长","data": "packDuration" },
						{ "title":"冲账订单数","data": "strikeBalanceOrderNum" },
						{ "title":"冲账金额(元)","data": "strikeBalanceAmount" },
						{ "title":"微信订单数","data": "wxOrderNum"},
						{ "title":"微信订单金额(元)","data": "wxAmount"},
						{ "title":"微信实收金额","data": "wxRealAmount"},
						{ "title":"微信手续费","data": "wxCharge"},
						{ "title":"支付宝订单数","data": "alipayOrderNum"},
						{ "title":"支付宝订单金额","data": "alilpayAmount"},
						{ "title":"支付宝实收金额","data": "alilpayRealAmount"},
						{ "title":"支付宝手续费","data": "alipayCharge"}*/
					],
				   "dom": "<'row'<'#carRptsTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carRptsTools").html("");
					   $("#carRptsTools").removeClass("col-xs-6");
					   $("#carRptsTools").append('<button type="button" class="btn-new-type carRptsTools-operate-export">导出</button>');
		       			$(".carRptsTools-operate-export").on("click",function(){
		       				var form = $("form[name=carRptsSearchForm]");
		       				var finishTimeStart=form.find("input[name=finishTimeStart]").val();
							var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = carRpts.appPath+ "/financialStatement/tocCarRptsExport.do?startTime="+finishTimeStart+" 00:00:00"+"&endTime="+finishTimeEnd+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						carRpts.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						   /* "targets": [3,4,7,10,12,13,14,16,17,18],*/
							"targets": [3,4],
						    "render": function(a,b,c,d) {//
						    	if(a){
		    	            		return "<span style='color:#2b94fd'>" + carRpts.formatCurrency(a) + "</span>";
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
//						{
//						    "targets": [15],
//						    "render": function(a,b,c,d) {//
//						    	if(a){
//						    		if(a==1){
//						    			return "支付宝";
//						    		}else if(a==2){
//						    			return "微信";
//						    		}else{
//						    			return "其他";
//						    		}
//		    	            	}else{
//		    	            		return "";
//		    	            	}
//						    }
//						}
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
	return carRpts;
});


