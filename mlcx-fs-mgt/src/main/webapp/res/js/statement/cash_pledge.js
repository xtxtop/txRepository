define([],function() {	
	var cashPledge={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				cashPledge.pageList();
				//查询
				$("#cashPledgeSearch").click(function(){
					var form = $("form[name=cashPledgeSearchForm]");
					var createTime =  form.find("input[name=createTime]").val();
					var finishTime = form.find("input[name=finishTime]").val();
					if(createTime!=""&&finishTime!=""){
						if(new Date(createTime)>new Date(finishTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							cashPledge.pageList();
						}
					}else{
						cashPledge.pageList();
					}
	            });
				$("#closecashPledgeView").click(function(){
					closeTab("发票开票详情");
					cashPledge.pageList();
	            });
				$("#cashPledgeBrowseModalClose").click(function(){
					$("#cashPledgeBrowseModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        },
			pageList:function () {
				$('#cashPledge').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": cashPledge.appPath+'/cashPledge/pageListcashPledge.do',
						"data": function ( d ) {
							var form = $("form[name=cashPledgeSearchForm]");
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
					    { "title":"支付方式","data": "type" },
					    { "title":"缴纳人次","data": "cashPerCount" },
			            { "title":"收入","data": "income" },
						{ "title":"支付手续费","data": "cashAgentFee" },
						{ "title":"退款人次","data": "cashRefundCount" },
						{ "title":"退款","data": "refund"},
						{ "title":"退还手续费","data": "refundAgentFee"}
					],
				   "dom": "<'row'<'#cashPledgeTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#cashPledgeTools").html("");
					   $("#cashPledgeTools").removeClass("col-xs-6");
					   $("#cashPledgeTools").append('<button type="button" class="btn-new-type cashPledgeTools-operate-export">导出</button>');
		       			$(".cashPledgeTools-operate-export").on("click",function(){
		       				var form = $("form[name=cashPledgeSearchForm]");
		       				var startTime=form.find("input[name=startTime]").val();
							var endTime=form.find("input[name=endTime]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = cashPledge.appPath+ "/cashPledge/tocashPledgeExport.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						cashPledge.operateColumEvent();
	        	    },
					"columnDefs": [
								{
								    "targets": [1,4],
								    "render": function(a,b,c,d) {
								    	if(a!="null"){
								    		return a;
								    	}else{
								    		return 0;
								    	}
								    }
								},
								{
								    "targets": [2,3,5,6],
								    "render": function(a,b,c,d) {
								    	if(a!="null"){
								    		return "<span style='color:#2b94fd'>" + a + "</span>";
								    	}else{
								    		return 0.00;
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
	return cashPledge;
});


