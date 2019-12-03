define([],function() {	
	var summary={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				summary.pageList();
				//查询
				$("#summarySearch").click(function(){
					var form = $("form[name=summarySearchForm]");
					var createTime =  form.find("input[name=createTime]").val();
					var finishTime = form.find("input[name=finishTime]").val();
					if(createTime!=""&&finishTime!=""){
						if(new Date(createTime)>new Date(finishTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							summary.pageList();
						}
					}else{
						summary.pageList();
					}
	            });
				$("#closesummaryView").click(function(){
					closeTab("发票开票详情");
					summary.pageList();
	            });
				$("#summaryBrowseModalClose").click(function(){
					$("#summaryBrowseModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".summary-operate-view").each(function(){
					$(this).on("click",function(){
						var memberNo=$(this).data("id");
						var form = $("form[name=summarySearchForm]");
	       				var checkDateRange1=form.find("input[name=createTime]").val()+" 00:00:00";
						var checkDateRange2=form.find("input[name=finishTime]").val()+" 23:59:59";
						addTab("财务对账明细",summary.appPath+ "/summary/tosummaryView.do?memberNo="+memberNo+"&checkDateRange1="+checkDateRange1+"&checkDateRange2="+checkDateRange2);
					});
				});
	        },
			pageList:function () {
				var summaryBtnTpl = $("#summaryBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(summaryBtnTpl);
				$('#summaryList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": summary.appPath+'/financialStatement/pageListSummary.do',
						"data": function ( d ) {
							var form = $("form[name=summarySearchForm]");
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
			            { "title":"订单数","data": "orderNum" },
						{ "title":"时长(分钟)","data": "orderDuration" },
						{ "title":"订单金额(元)","data": "orderAmount" },
						{ "title":"实收金额(元)","data": "alreadyAmount"},
						{ "title":"实收金额对应时长(分钟)","data": "alreadyDuration"},
						{ "title":"套餐抵扣订单数","data": "packOrderNum"},
						{ "title":"套餐抵扣金额(元)","data": "packAmount"},
						{ "title":"优惠订单数","data": "discountAmountNum"},
						{ "title":"优惠金额金额(元)","data": "discountAmount"},
						//{ "title":"套餐抵扣时长(分钟)","data": "packDuration"},
						{ "title":"冲账订单数","data": "strikeBalanceOrderNum" },
						{ "title":"冲账金额(元)","data": "strikeBalanceAmount" },
						{ "title":"微信订单数","data": "wxOrderNum" },
						{ "title":"微信订单金额(元)","data": "wxAmount"},
						{ "title":"微信实收金额(元)","data": "wxRealAmount"},
						{ "title":"微信手续费(元)","data": "wxCharge"},
						{ "title":"支付宝订单数","data": "alipayOrderNum"},
						{ "title":"支付宝订单金额(元)","data": "alilpayAmount"},
						{ "title":"支付宝实收金额(元)","data": "alilpayRealAmount"},
						{ "title":"支付宝手续费(元)","data": "alipayCharge"}
					],
				   "dom": "<'row'<'#summaryTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#summaryTools").html("");
					   $("#summaryTools").removeClass("col-xs-6");
					   $("#summaryTools").append('<button type="button" class="btn-new-type summaryTools-operate-export">导出</button>');
		       			$(".summaryTools-operate-export").on("click",function(){
		       				var form = $("form[name=summarySearchForm]");
		       				var startTime=form.find("input[name=startTime]").val();
							var endTime=form.find("input[name=endTime]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = summary.appPath+ "/financialStatement/toSummaryExport.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						summary.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
//						{
//						    "targets": [0],
//						    "render": function(a,b,c,d) {//1、增值税普通发票，2、增值税专用发票
//						    	if(c.checkDateRange1!=null&&
//						    			c.checkDateRange2!=null){
//						    		var checkDateRange1 = moment(c.checkDateRange1);
//						    		var checkDateRange2 = moment(c.checkDateRange2); 
//						        	return checkDateRange1.format('YYYY-MM-DD')+"——"+checkDateRange2.format('YYYY-MM-DD');
//						        }else{
//						        	return "";
//						        }
//						    }
//						},{
//						    "targets": [4,5,6,7],
//						    "render": function(a,b,c,d) {//0、未开票，1、已开票
//						    	if(a){
//		    	            		return summary.formatCurrency(a); 
//		    	            	}else{
//		    	            		return "0.00";
//		    	            	}
//						    }
//						},
{
						    "targets": [7],
						    "render": function(a,b,c,d) {//
						    	if(a){
						    		return summary.formatCurrency(a);
		    	            	}else{
		    	            		return "0";
		    	            	}
						    }
						},
						{
						    "targets": [8],
						    "render": function(a,b,c,d) {//
						    	if(a){
						    		return summary.formatCurrency(a);
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
						  
//						{
//							"targets": [8],
//							"render": function (a, b, c, d) {
//								var view='<span class="glyphicon  btn btn-default summary-operate-view" data-id="'+c.memberNo+'"  data-toggle="tooltip" data-placement="top" title="查看">查看</span>';
//								return view;
//							}
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
	return summary;
});


