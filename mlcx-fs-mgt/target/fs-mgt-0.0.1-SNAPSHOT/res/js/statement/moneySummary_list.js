define([], function() {
	var moneySummary = {
		appPath : getPath("app"),
		init : function() {
			//页面查询
			//默认显示最近一月的资金统计
			$("#moneySummarySearchDefault").click(function() {
				var form = $("form[name=defaultMoneySummarySerachForm]");
				var startTimeDay =  form.find("input[name=finishTimeStart]").val();
				var endTimeDay = form.find("input[name=finishTimeEnd]").val();
				if(startTimeDay != "" && endTimeDay != ""){
					if(new Date(startTimeDay) > new Date(endTimeDay)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						moneySummary.showListMoneySummaryDefault();
					}
				}else{
					moneySummary.showListMoneySummaryDefault();
				}
			});
			
			//按月
			$("#moneySummarySearchMonths").click(function() {
				var form = $("form[name=monthMoneySummarySerachForm]");
				var startTimeDay =  form.find("input[name=startTime]").val();
				var endTimeDay = form.find("input[name=endTime]").val();
				if(startTimeDay != "" && endTimeDay != ""){
					if(new Date(startTimeDay) > new Date(endTimeDay)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						moneySummary.showListMoneySummaryMonth();
					}
				}else{
					moneySummary.showListMoneySummaryMonth();
				}
			});
			
			//按天
			$("#moneySummarySearchDay").click(function() {
				var form = $("form[name=dayMoneySummarySerachForm]");
				var startTimeDay =  form.find("input[name=finishTimeStart]").val();
				var endTimeDay = form.find("input[name=finishTimeEnd]").val();
				if(startTimeDay != "" && endTimeDay != ""){
					if(new Date(startTimeDay) > new Date(endTimeDay)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						moneySummary.showListMoneySummaryDay();
					}
				}else{
					moneySummary.showListMoneySummaryDay();
				}
			});
			
			moneySummary.showListMoneySummaryDefault();
			moneySummary.showListMoneySummaryMonth();
			moneySummary.showListMoneySummaryDay();
		},
		
		showListMoneySummaryDefault:function () {
			var moneySummaryBtnTpl = $("#moneySummaryBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(moneySummaryBtnTpl);
			$('#moneySummaryList').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": moneySummary.appPath+'/financialStatement/pageListMoneySummary.do',
					"data": function ( d ) {
						var form = $("form[name=defaultMoneySummarySerachForm]");
						var finishTimeStart=form.find("input[name=finishTimeStart]").val();
						var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"startTime":form.find("input[name=finishTimeStart]").val(),
							"endTime":form.find("input[name=finishTimeEnd]").val(),
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
					{ "title":"订单实收金额(元)","data": "alreadyAmount" },
					{ "title":"充值笔数","data": "packageRechargeNum"},
					{ "title":"充值实收金额(元)","data": "packageAlreadyAmount"},
					{ "title":"交押金笔数","data": "payDepositNum"},
					{ "title":"交押金金额(元)）","data": "payDepositAmount"},
					{ "title":"退押金笔数","data": "refundDepositNum"},
					{ "title":"退押金金额(元)","data": "refundDepositAmount" }
				],
			   "dom": "<'row'<'#moneySummaryTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				   $("#moneySummaryTools").html("");
				   $("#moneySummaryTools").removeClass("col-xs-6");
				   $("#moneySummaryTools").append('<button type="button" class="btn-new-type moneySummaryTools-operate-export">导出</button>');
	       			$(".moneySummaryTools-operate-export").on("click",function(){
	       				var form = $("form[name=defaultMoneySummarySerachForm]");
	       				var finishTimeStart=form.find("input[name=finishTimeStart]").val();
						var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
	       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
							if(result){
								window.location.href = moneySummary.appPath+ "/financialStatement/moneySummaryDefaultExport.do?startTime="+finishTimeStart+"&"+"endTime="+finishTimeEnd;
							}						
	       				});
	       			});
				},
				"drawCallback": function( settings ) {
        	    },
				"columnDefs": [
					{
					    "targets": [1,3,5],
					    "render": function(a,b,c,d) {//
					    	if(a){
	    	            		return moneySummary.formatCurrency(a); 
	    	            	}else{
	    	            		return "0.00";
	    	            	}
					    }
					},{
						 "targets": [2,4,6],
						  "render": function(a,b,c,d) {//
				    		if(a == null ){
				    			return "0"; 
				    		}else{
				    			return a; 
				    		}
						}
					},{
					    "targets": [7],
					    "render": function(a,b,c,d) {//
					    	if(a){
					    		if(a > 0 ){
					    			return "-"+moneySummary.formatCurrency(a); 
					    		}else{
					    			return moneySummary.formatCurrency(a); 
					    		}
	    	            		
	    	            	}else{
	    	            		return "0.00";
	    	            	}
					    }
					},
				]
			});
		},
		//按月
		showListMoneySummaryMonth:function () {
			var moneySummaryMonthBtnTpl = $("#moneySummaryMonthBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(moneySummaryMonthBtnTpl);
			$('#moneySummaryMonthList').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": moneySummary.appPath+'/financialStatement/pageListMoneySummaryMonth.do',
					"data": function ( d ) {
						var form = $("form[name=monthMoneySummarySerachForm]");
						var startTime=form.find("input[name=startTime]").val();
						var endTime=form.find("input[name=endTime]").val();
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"startMonth":startTime,
							"endMonth":endTime,
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
				    { "title":"月份","data": "months" },
		            { "title":"订单数","data": "orderNum" },
					{ "title":"订单实收金额(元)","data": "alreadyAmount" },
					{ "title":"充值笔数","data": "packageRechargeNum"},
					{ "title":"充值实收金额(元)","data": "packageAlreadyAmount"},
					{ "title":"交押金笔数","data": "payDepositNum"},
					{ "title":"交押金金额(元)）","data": "payDepositAmount"},
					{ "title":"退押金笔数","data": "refundDepositNum"},
					{ "title":"退押金金额(元)","data": "refundDepositAmount" }
				],
			   "dom": "<'row'<'#moneySummaryTools1'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				   $("#moneySummaryTools1").html("");
				   $("#moneySummaryTools1").removeClass("col-xs-6");
				   $("#moneySummaryTools1").append('<button type="button" class="btn-new-type moneySummaryTools1-operate-export">导出</button>');
	       			$(".moneySummaryTools1-operate-export").on("click",function(){
	       				var form = $("form[name=monthMoneySummarySerachForm]");
	       				var finishTimeStart=form.find("input[name=startTime]").val();
						var finishTimeEnd=form.find("input[name=endTime]").val();
	       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
							if(result){
								window.location.href = moneySummary.appPath+ "/financialStatement/moneySummaryMonthExport.do?startMonth="+finishTimeStart+""+" &endMonth="+finishTimeEnd;
							}						
	       				});
	       			});
				},
				"drawCallback": function( settings ) {
        	    },
				"columnDefs": [
					{
					    "targets": [2,4,6],
					    "render": function(a,b,c,d) {//
					    	if(a){
	    	            		return moneySummary.formatCurrency(a); 
	    	            	}else if(a == null){
	    	            		return "0.00";
	    	            	}else {
	    	            		return "0.00";
	    	            	}
					    }
					},{
					    "targets": [8],
					    "render": function(a,b,c,d) {//
					    	if(a){
					    		if(a > 0 ){
					    			return "-"+moneySummary.formatCurrency(a); 
					    		}else{
					    			return moneySummary.formatCurrency(a); 
					    		}
	    	            		
	    	            	}else{
	    	            		return "0.00";
	    	            	}
					    }
					},{
					    "targets": [1,3,5,7],
					    "render": function(a,b,c,d) {//
					    		if(a == null ){
					    			return "0"; 
					    		}else{
					    			return a; 
					    		}
	    	            	
					    }
					}
				]
			});
		},
		//按天汇总
		showListMoneySummaryDay:function () {
			var moneySummaryDayBtnTpl = $("#moneySummaryDayBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(moneySummaryDayBtnTpl);
			$('#moneySummaryDayList').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": moneySummary.appPath+'/financialStatement/pageListMoneySummaryDay.do',
					"data": function ( d ) {
						var form = $("form[name=dayMoneySummarySerachForm]");
						var finishTimeStart=form.find("input[name=finishTimeStart]").val();
						var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"startTime":form.find("input[name=finishTimeStart]").val(),
							"endTime":form.find("input[name=finishTimeEnd]").val(),
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
				    { "title":"日期","data": "days" },
		            { "title":"订单数","data": "orderNum" },
					{ "title":"订单实收金额(元)","data": "alreadyAmount" },
					{ "title":"充值笔数","data": "packageRechargeNum"},
					{ "title":"充值实收金额(元)","data": "packageAlreadyAmount"},
					{ "title":"交押金笔数","data": "payDepositNum"},
					{ "title":"交押金金额(元)）","data": "payDepositAmount"},
					{ "title":"退押金笔数","data": "refundDepositNum"},
					{ "title":"退押金金额(元)","data": "refundDepositAmount" }
				],
			   "dom": "<'row'<'#moneySummaryTools2'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				   $("#moneySummaryTools2").html("");
				   $("#moneySummaryTools2").removeClass("col-xs-6");
				   $("#moneySummaryTools2").append('<button type="button" class="btn-new-type moneySummaryTools2-operate-export">导出</button>');
	       			$(".moneySummaryTools2-operate-export").on("click",function(){
	       				var form = $("form[name=dayMoneySummarySerachForm]");
	       				var finishTimeStart=form.find("input[name=finishTimeStart]").val();
						var finishTimeEnd=form.find("input[name=finishTimeEnd]").val();
	       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
							if(result){
								window.location.href = moneySummary.appPath+ "/financialStatement/moneySummaryDayExport.do?startTime="+finishTimeStart+""+"&endTime="+finishTimeEnd;
							}						
	       				});
	       			});
				},
				"drawCallback": function( settings ) {
        	    },
				"columnDefs": [
					{
					    "targets": [2,4,6],
					    "render": function(a,b,c,d) {//
					    	if(a){
	    	            		return moneySummary.formatCurrency(a); 
	    	            	}else if(a == null){
	    	            		return "0.00";
	    	            	}else {
	    	            		return "0.00";
	    	            	}
					    }
					},{
					    "targets": [8],
					    "render": function(a,b,c,d) {//
					    	if(a){
					    		if(a > 0 ){
					    			return "-"+moneySummary.formatCurrency(a); 
					    		}else{
					    			return moneySummary.formatCurrency(a); 
					    		}
	    	            		
	    	            	}else{
	    	            		return "0.00";
	    	            	}
					    }
					},{
					    "targets": [1,3,5,7],
					    "render": function(a,b,c,d) {//
					    		if(a == null ){
					    			return "0"; 
					    		}else{
					    			return a; 
					    		}
	    	            	
					    }
					}
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
	        return t.split("").reverse().join("") + "." + r;
	        
	        }
	}
	return moneySummary;
});