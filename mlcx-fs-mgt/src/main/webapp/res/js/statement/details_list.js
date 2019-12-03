define([],function() {	
	var details={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				details.pageList();
				//查询
				$("#detailsSearch").click(function(){
					var form = $("form[name=detailsSearchForm]");
					var finishTimeStart =  form.find("input[name=finishTimeStart]").val();
					var finishTimeEnd = form.find("input[name=finishTimeEnd]").val();
					if(finishTimeStart!=""&&finishTimeEnd!=""){
						if(new Date(finishTimeStart)>new Date(finishTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							details.pageList();
						}
					}else{
						details.pageList();
					}
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        },
			pageList:function () {
				var detailsBtnTpl = $("#detailsBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(detailsBtnTpl);
				$('#detailsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": details.appPath+'/financialStatement/pageListDetails.do',
						"data": function ( d ) {
							var form = $("form[name=detailsSearchForm]");
							var finishTimeStart=form.find("input[name=finishTimeStart]").val();
							var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"finishTimeStart":form.find("input[name=finishTimeStart]").val()+ " 00:00:00",
								"finishTimeEnd":form.find("input[name=finishTimeEnd]").val()+ " 23:59:59",
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
			            { "title":"订单号","data": "orderNo" },
						{ "title":"姓名","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"订单预定时间","data": "appointmentTime"},
						{ "title":"订单开始时间","data": "startBillingTime"},
						{ "title":"订单结束时间","data": "finishTime"},
						{ "title":"订单时长(分钟)","data": "orderDuration"},
						{ "title":"取车场站","data": "actualTakeLoacton"},
						{ "title":"还车场站","data": "actualTerminalLoacton" },
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"订单金额(元)","data": "orderAmount" },
						{ "title":"抵扣金额(元)","data": "packAmountDiscountAmount"},
						{ "title":"冲账金额(元)","data": "strikeBalanceAmount"},
						{ "title":"优惠金额(元)","data": "discountAmount"},
						{ "title":"应收金额(元)","data": "payableAmount"},
						{ "title":"实收金额(元)","data": "alreadyAmount"},
						{ "title":"支付方式","data": "paymentMethod"},
						{ "title":"支付流水号","data": "paymentFlowNo"}
					],
				   "dom": "<'row'<'#detailsTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#detailsTools").html("");
					   $("#detailsTools").removeClass("col-xs-6");
					   $("#detailsTools").append('<button type="button" class="btn-new-type detailsTools-operate-export">导出</button>');
		       			$(".detailsTools-operate-export").on("click",function(){
		       				var form = $("form[name=detailsSearchForm]");
		       				var finishTimeStart=form.find("input[name=finishTimeStart]").val();
							var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = details.appPath+ "/financialStatement/toDetailsExport.do?finishTimeStart="+finishTimeStart+" 00:00:00"+"&finishTimeEnd="+finishTimeEnd+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						details.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [3,4,5],
						    "render": function(a,b,c,d) {
						    	if(a){
						    		var date = moment(a);
						        	return date.format('YYYY-MM-DD HH:mm:ss');
						    	}else{
						    		return "";
						    	}
						    	
						    	}
						},{
						    "targets": [10,11,12,13,14,15],
						    "render": function(a,b,c,d) {//
						    	if(a){
		    	            		return "<span style='color:#2b94fd'>" + details.formatCurrency(a) + "</span>";
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},{
						    "targets": [16],
						    "render": function(a,b,c,d) {//
						    	if(a){
						    		if(a==1){
						    			return "支付宝";
						    		}else if(a==2){
						    			return "微信";
						    		}else{
						    			return "其他";
						    		}
		    	            	}else{
		    	            		return "";
		    	            	}
						    }
						}
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
	return details;
});


