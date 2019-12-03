define([],function() {	
	var memberCount={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				memberCount.pageList();
				//查询
				$("#memberCountSearch").click(function(){
					var form = $("form[name=memberCountSearchForm]");
					var createTime =  form.find("input[name=createTime]").val();
					var finishTime = form.find("input[name=finishTime]").val();
					if(createTime!=""&&finishTime!=""){
						if(new Date(createTime)>new Date(finishTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							memberCount.pageList();
						}
					}else{
						memberCount.pageList();
					}
	            });
				$("#closememberCountView").click(function(){
					closeTab("发票开票详情");
					memberCount.pageList();
	            });
				$("#memberCountBrowseModalClose").click(function(){
					$("#memberCountBrowseModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        },
			pageList:function () {
				$('#memberCount').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": memberCount.appPath+'/memberCount/pageListmemberCount.do',
						"data": function ( d ) {
							var form = $("form[name=memberCountSearchForm]");
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
					    { "title":"会员姓名","data": "memberName" },
					    { "title":"手机号","data": "mobilePhone" },
			            { "title":"订单数","data": "orderCount" },
						{ "title":"时长(分钟)","data": "orderDuration" },
						{ "title":"订单金额(元)","data": "orderAmount" },
						{ "title":"实收金额(元)","data": "paidAmount"},
						{ "title":"实收金额对应时长(分钟)","data": "paidDuration"},
						{ "title":"套餐抵扣订单数","data": "packDiscountAmount"},
						{ "title":"套餐抵扣金额(元)","data": "packMinuteAmount"},
						{ "title":"套餐抵扣时长(分钟)","data": "packMinuteDuration"},
						{ "title":"冲账订单数","data": "strikeDiscountAmount" },
						{ "title":"冲账金额(元)","data": "strikeBalanceAmount" },
						{ "title":"微信订单数","data": "wxOrderCount" },
						{ "title":"微信订单金额(元)","data": "wxOrderAmount"},
						{ "title":"微信实收金额(元)","data": "wxpayAmount"},
						{ "title":"微信手续费(元)","data": "wxproFee"},
						{ "title":"支付宝订单数","data": "zfbOrderCount"},
						{ "title":"支付宝订单金额(元)","data": "zfbOrderAmount"},
						{ "title":"支付宝实收金额(元)","data": "zfbpayAmount"},
						{ "title":"支付宝手续费(元)","data": "zfbproFee"}
					],
				   "dom": "<'row'<'#memberCountTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#memberCountTools").html("");
					   $("#memberCountTools").removeClass("col-xs-6");
					   $("#memberCountTools").append('<button type="button" class="btn-new-type memberCountTools-operate-export">导出</button>');
		       			$(".memberCountTools-operate-export").on("click",function(){
		       				var form = $("form[name=memberCountSearchForm]");
		       				var startTime=form.find("input[name=startTime]").val();
							var endTime=form.find("input[name=endTime]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = memberCount.appPath+ "/memberCount/tomemberCountExport.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						memberCount.operateColumEvent();
	        	    },
					"columnDefs": [
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
	return memberCount;
});


