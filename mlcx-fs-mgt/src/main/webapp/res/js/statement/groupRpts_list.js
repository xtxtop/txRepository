define([],function() {	
	var groupRpts={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				groupRpts.pageList();
				//查询
				$("#groupRptsSearch").click(function(){
					var form = $("form[name=groupRptsSearchForm]");
					var finishTimeStart =  form.find("input[name=finishTimeStart]").val();
					var finishTimeEnd = form.find("input[name=finishTimeEnd]").val();
					if(finishTimeStart!=""&&finishTimeEnd!=""){
						if(new Date(finishTimeStart)>new Date(finishTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							groupRpts.pageList();
						}
					}else{
						groupRpts.pageList();
					}
	            });
			},
			//方法加载
			
	        operateColumEvent: function(){
	        },
			pageList:function () {
				var groupRptsBtnTpl = $("#groupRptsBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(groupRptsBtnTpl);
				$('#groupRptsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": groupRpts.appPath+'/financialStatement/pageListGroupRpts.do',
						"data": function ( d ) {
							var form = $("form[name=groupRptsSearchForm]");
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
			            { "title":"集团名称","data": "companyName" },
						{ "title":"订单数","data": "orderNum" },
						{ "title":"时长(分钟)","data": "orderDuration" },
						{ "title":"订单金额(元)","data": "orderAmount"},
						{ "title":"实收金额","data": "alreadyAmount"},
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
						{ "title":"支付宝手续费(元)","data": "alipayCharge"},
						{ "title":"时长平均值","data": "orderDurationAvg"},
						{ "title":"金额平均值","data": "orderAmountAvg"}
						
						
					],
				   "dom": "<'row'<'#groupRptsTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#groupRptsTools").html("");
					   $("#groupRptsTools").removeClass("col-xs-6");
					   $("#groupRptsTools").append('<button type="button" class="btn-new-type groupRptsTools-operate-export">导出</button>');
		       			$(".groupRptsTools-operate-export").on("click",function(){
		       				var form = $("form[name=groupRptsSearchForm]");
		       				var finishTimeStart=form.find("input[name=finishTimeStart]").val();
							var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = groupRpts.appPath+ "/financialStatement/toGroupRptsExport.do?startTime="+finishTimeStart+" 00:00:00"+"&endTime="+finishTimeEnd+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						groupRpts.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [3,4,7,10,12,13,14,15,17,18,20],
						    "render": function(a,b,c,d) {//
						    	if(a){
		    	            		return "<span style='color:#2b94fd'>" + groupRpts.formatCurrency(a) + "</span>";
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
	return groupRpts;
});


