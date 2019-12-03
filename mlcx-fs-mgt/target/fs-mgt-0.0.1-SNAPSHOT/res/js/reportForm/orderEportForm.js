define([], function() {
	var orderEportForm = {
		appPath : getPath("app"),
		init : function() {
			// 按天搜索调用
			$("#orderSearchDayNs").click(function() {
				var form = $("form[name=orderSerachDayNsForm]");
				var createTimeStartDay =  form.find("input[name=createTimeStart]").val();
				var createTimeEndDay =  form.find("input[name=createTimeEnd]").val();
				if(createTimeStartDay==""&&createTimeEndDay==""){
					orderEportForm.myChartPie();
					orderEportForm.myChartPieNs();
					orderEportForm.myChartPieMs();
					orderEportForm.myChartPieNsMs();
				}else
				
				if(createTimeStartDay==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择开始时间。");
					return false;
				}else
				if(createTimeEndDay==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择结束时间。");
					return false;
				}
				
				if(createTimeStartDay!=""&&createTimeEndDay!=""){
					if(new Date(createTimeStartDay)>new Date(createTimeEndDay)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间。");
						return false;
					}
				}
				
				$.post("eportForm/getDay.do",{createTimeStartDay:createTimeStartDay+" 00:00:00",createTimeEndDay:createTimeEndDay+" 23:59:59"},function(res)
				{
					if(res.code==0){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "天数范围不能大于31天，请重新选择。");
					}else{
						alert("ok");
						orderEportForm.myChartPie();
						orderEportForm.myChartPieNs();
						orderEportForm.myChartPieMs();
						orderEportForm.myChartPieNsMs();
					}
					
				});
			});
			
			$("#orderSearchWeekNs").click(function() {
				var form = $("form[name=orderCountWeekNsForm]");
				var createTimeStartWeek =  form.find("input[name=createTimeStartWeek]").val();
				var createTimeEndWeek =  form.find("input[name=createTimeEndWeek]").val();
				
				if(createTimeStartWeek==""&&createTimeEndWeek==""){
					orderEportForm.myChartPieWeek();
					orderEportForm.myChartPieWeekMs();
				}else
				
				if(createTimeStartWeek==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择起始时间。");
					return false;
				}else
				
				if(createTimeEndWeek==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择结束时间。");
					return false;
				}
				
				if(createTimeStartWeek!=""&&createTimeEndWeek!=""){
					if(new Date(createTimeStartWeek)>new Date(createTimeEndWeek)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间。");
						return false;
					}
				}
				
				$.post("eportForm/getWeek.do",{createTimeStartWeek:createTimeStartWeek+" 00:00:00",createTimeEndWeek:createTimeEndWeek+" 00:00:00"},function(res)
						{
							if(res.code==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "时间范围不能大于十周，请重新选择。");
								return false;
							}else{
								orderEportForm.myChartPieWeek();
								orderEportForm.myChartPieWeekMs();
							}
							
						});
			});
			
			$("#orderSearchMonthNs").click(function() {
				var form = $("form[name=orderCountMonthNsForm]");
				var createTimeStartMonth =  form.find("input[name=createTimeStartMonth]").val();
				var createTimeEndMonth =  form.find("input[name=createTimeEndMonth]").val();
				if(createTimeStartMonth==""&&createTimeEndMonth==""){
					orderEportForm.myChartPie3();
					orderEportForm.myChartPieMonth();
				}else
				
				if(createTimeStartMonth==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择开始时间。");
					return false;
				}else
				if(createTimeEndMonth==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择结束时间。");
					return false;
				}
				
				if(createTimeStartMonth!=""&&createTimeEndMonth!=""){
					if(new Date(createTimeStartMonth)>new Date(createTimeEndMonth)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间。");
						return false;
					}
				}
					
				$.post("eportForm/getMonth.do",{createTimeStartMonth:createTimeStartMonth+"-01 00:00:00",createTimeEndMonth:createTimeEndMonth+"-01 00:00:00"},function(res)
				{
					if(res.code==0){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "月份范围不能大于12个月，请重新选择。");
					}else{
						orderEportForm.myChartPie3();
						orderEportForm.myChartPieMonth();
					}
				});
			});
			
			orderEportForm.myChartPie();
			orderEportForm.myChartPieNs();
			orderEportForm.myChartPieMs();
			orderEportForm.myChartPieNsMs();
			orderEportForm.myChartPieWeek();
			orderEportForm.myChartPieWeekMs();
			orderEportForm.myChartPie3();
			orderEportForm.myChartPieMonth();
		},
		
		//订单销售额按周累计
		myChartPieWeekNsMs : function(res) {
			var sum = [];//订单金额
			var paySum = [];//订单付款额
			var balanceSum = [];//余额抵扣额
			var time = [];//时间

			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					sum.push(res[i].orderAmount);
					paySum.push(res[i].payAmount);
					balanceSum.push(res[i].balanceAmount);
					time.push(res[i].time);
				}
			}

			var myChart = echarts.init(document.getElementById('orderEportWeekNsoMs'));
			option = {
				    title: {
				        text: '订单金额按周累计图'
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["订单金额","订单付款额","余额抵扣额"]
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    toolbox: {
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: time
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: [{
							name:"订单金额",
				            type:'line',
				            data:sum
			            },
			            {
							name:"订单付款额",
				            type:'line',
				            data:paySum
			            },
			            {
							name:"余额抵扣额",
				            type:'line',
				            data:balanceSum
				         }
				         ] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		
		//订单销售量按周统计
		myChartPieWeek : function() {
			var count = [];//新增订单数
			var fisCount = [] //完成订单数
			var countNs = [];//取消订单数
			var time = [];//时间

			var form = $("form[name=orderCountWeekNsForm]");
			var createTimeStartWeek =  form.find("input[name=createTimeStartWeek]").val();
			var createTimeEndWeek =  form.find("input[name=createTimeEndWeek]").val();
			
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/orderEportWeek.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartWeek:createTimeStartWeek,
					createTimeEndWeek:createTimeEndWeek
				},
				success : function(res) {
					if (res != "") {
						orderEportForm.myChartPieWeekNs(res);
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].orderNumber);
							fisCount.push(res[i].orderFhNumber);
							countNs.push(res[i].orderDeNumber);
							time.push(res[i].time);
						}
					}

					var myChart = echarts.init(document.getElementById('orderEportWeekNs'));
					option = {
						    title: {
						        text: '订单数按周统计图'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["新增订单数","完成订单数","取消订单数"]
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
									name:"新增订单数",
						            type:'line',
						            data:count
					            },
					            {
									name:"完成订单数",
						            type:'line',
						            data:fisCount
					            },
					            {
									name:"取消订单数",
						            type:'line',
						            data:countNs
					            }
					            ] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		//订单销售额按周统计图
		myChartPieWeekNs : function(res) {
			var orderAmountSum = [];//订单金额
			var orderPayAmountSum = [];//订单付款额
			var balanceAmountSum = [];//余额抵扣额
			var time = [];//时间
				
			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					orderAmountSum.push(res[i].orderAmount);
					orderPayAmountSum.push(res[i].payAmount);
					balanceAmountSum.push(res[i].balanceAmount);
					time.push(res[i].time);
				}
			}
			 
			var myChart = echarts.init(document.getElementById('orderEportWeekNso'));
			option = {
				    title: {
				        text: '订单金额按周统计图'
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["订单金额","订单付款额","余额抵扣额"]
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    toolbox: {
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: time
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: [{
								name:"订单金额",
					            type:'line',
					            data:orderAmountSum
				            },{
								name:"订单付款额",
					            type:'line',
					            data:orderPayAmountSum
				            },{
								name:"余额抵扣额",
					            type:'line',
					            data:balanceAmountSum
				            }] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		
		//订单销售量、订单销售额按周累计图
		myChartPieWeekMs : function() {
			var count = [];//新增订单数
			var fCount = [];//完成订单数
			var countNs = [];//取消订单数
			var time = [];//时间

			var form = $("form[name=orderCountWeekNsForm]");
			var createTimeStartWeek =  form.find("input[name=createTimeStartWeek]").val();
			var createTimeEndWeek =  form.find("input[name=createTimeEndWeek]").val();
			
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/orderEportWeekNs.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartWeek:createTimeStartWeek,
					createTimeEndWeek:createTimeEndWeek
				},
				success : function(res) {
					if (res != "") {
						orderEportForm.myChartPieWeekNsMs(res);
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].orderNumber);
							fCount.push(res[i].orderFhNumber);
							countNs.push(res[i].orderDeNumber);
							time.push(res[i].time);
						}
					}

					var myChart = echarts.init(document.getElementById('orderEportWeekNsMs'));
					option = {
						    title: {
						        text: '订单数按周累计图'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["新增订单数","完成订单数","取消订单数"]
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [{
									name:"新增订单数",
						            type:'line',
						            data:count
				            	},
				            	{
									name:"完成订单数",
						            type:'line',
						            data:fCount
				            	},
				            	{
									name:"取消订单数",
						            type:'line',
						            data:countNs
					            }] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		//订单销售额按月统计
		myChartPie6 : function(res) {
			var orderAmountSum = [];//订单金额
			var payAmountSum = [];//订单支付金额
			var balanceAmount = [];//余额抵扣额
			var time = [];//时间
			
			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					orderAmountSum.push(res[i].orderAmount);
					payAmountSum.push(res[i].payAmount);
					balanceAmount.push(res[i].balanceAmount);
					time.push(res[i].time);
				}
			}

			var myChart = echarts.init(document.getElementById('orderEportMonthNso'));
			option = {
				    title: {
				        text: '订单金额按月统计图'
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["订单金额","订单付款额","余额抵扣额"]
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    toolbox: {
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: time
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: [
			            {
						name:"订单金额",
			            type:'line',
			            data:orderAmountSum
			            },
			            {
						name:"订单付款额",
			            type:'line',
			            data:payAmountSum
			            },
			            {
						name:"余额抵扣额",
			            type:'line',
			            data:balanceAmount
			            }] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		//订单金额按月累计
		myChartPieMonthMs : function(res) {
			var orderSum = [];//订单额
			var payAmount = [];// 订单付款额
			var balanceAm = [];//余额抵扣额
			var time = [];//时间
			
			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					orderSum.push(res[i].orderAmount);
					payAmount.push(res[i].payAmount);
					balanceAm.push(res[i].balanceAmount);
					time.push(res[i].time);
				}
			}
					 
			var myChart = echarts.init(document.getElementById('orderEportMonthNsoMs'));
			option = {
				    title: {
				        text: '订单金额按月累计图'
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["订单金额","订单付款额","余额抵扣额"]
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    toolbox: {
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: time
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: [
				            {
								name:"订单金额",
					            type:'line',
					            data:orderSum
				            },
			            	{
								name:"订单付款额",
					            type:'line',
					            data:payAmount
				            },
				            {
								name:"余额抵扣额",
					            type:'line',
					            data:balanceAm
				            }
				            ] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		//订单销售量按月统计
		myChartPie3 : function() {
			var count = [];//新增订单数
			var fCount = [];//完成订单数
			var countNs = [];//取消订单数
			var time = [];//时间

			var form = $("form[name=orderCountMonthNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStartMonth]").val();
			var createTimeEnd =  form.find("input[name=createTimeEndMonth]").val();
			
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/getOrderEportForm.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStart:createTimeStart,
					createTimeEnd:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						orderEportForm.myChartPie6(res);
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].orderNumber);
							fCount.push(res[i].orderFhNumber);
							countNs.push(res[i].orderDeNumber);
							time.push(res[i].time);
						}
					}

					var myChart = echarts.init(document.getElementById('orderEportMonthNs'));
					option = {
						    title: {
						        text: '订单数按月统计图'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["新增订单数","完成订单数","取消订单数"]
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [{
								name:"新增订单数",
					            type:'line',
					            data:count
					            },
					            {
								name:"完成订单数",
					            type:'line',
					            data:fCount
					            },
					            {
								name:"取消订单数",
					            type:'line',
					            data:countNs
					            }] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
		//订单按月累计
		myChartPieMonth : function() {
			var count = [];//新增订单数
			//var sum = [];//订单额
			var fCount = []; //完成订单数
			var countNs = [];//取消订单数
			var time = [];//时间
			var form = $("form[name=orderCountMonthNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStartMonth]").val();
			var createTimeEnd =  form.find("input[name=createTimeEndMonth]").val();
			
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/getOrderEportFormNs.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStart:createTimeStart,
					createTimeEnd:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						orderEportForm.myChartPieMonthMs(res);
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].orderNumber);
							fCount.push(res[i].orderFhNumber);
							countNs.push(res[i].orderDeNumber);
							time.push(res[i].time);
						}
					}

					var myChart = echarts.init(document.getElementById('orderEportMonthNsMs'));
					option = {
						    title: {
						        text: '订单数按月累计图'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["新增订单数","完成订单数","取消订单数"]
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [{
									name:"新增订单数",
						            type:'line',
						            data:count
					            },
					            {
									name:"完成订单数",
						            type:'line',
						            data:fCount
					            },
					            {
									name:"取消订单数",
						            type:'line',
						            data:countNs
						         }] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
		//按日累计统计
		myChartPieMs : function() {
			var addCount = [];//新增订单数
			var finCount = [];//完成订单数
			var calCount = [];//取消订单数
			var time = [];//时间
			
			var form = $("form[name=orderSerachDayNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStart]").val();
			var createTimeEnd =  form.find("input[name=createTimeEnd]").val();
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/getOrderEportFormDayNs.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStart:createTimeStart,
					createTimeEnd:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						for (var i = 0; i < res.length; i++) {
							addCount.push(res[i].orderNumber);
							finCount.push(res[i].orderFhNumber);
							calCount.push(res[i].orderDeNumber);
							time.push(res[i].time);
						}
					}

					var myChart = echarts.init(document.getElementById('orderEportNsMs'));
					option = {
						    title: {
						        text: '订单数按日累计图'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["新增订单数","完成订单数","取消订单数"]
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [{
								name:"新增订单数",
					            type:'line',
					            data:addCount
					            },
					            {
								name:"完成订单数",
					            type:'line',
					            data:finCount
					            },
					            {
								name:"取消订单数",
					            type:'line',
					            data:calCount
					            }
					            ] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
		//订单数按日统计图
		myChartPie : function() {
			var count = [];//新增订单数
			var fCounts = [];//完成订单数
			var countNs = [];//取消订单数
			var time = [];//时间
			
			var form = $("form[name=orderSerachDayNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStart]").val();
			var createTimeEnd =  form.find("input[name=createTimeEnd]").val();
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/getOrderEportFormDay.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStart:createTimeStart,
					createTimeEnd:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						
						//orderEportForm.myChartPieNs(res);
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].orderNumber);
							countNs.push(res[i].orderDeNumber);
							fCounts.push(res[i].orderFhNumber);
							time.push(res[i].time);
						}
					}
					
					var myChart = echarts.init(document.getElementById('orderEportNs'));
					option = {
						    title: {
						        text: '订单数按日统计图',
						        padding: 5,//标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["新增订单数","完成订单数","取消订单数"]
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [{
								name:"新增订单数",
					            type:'line',
					            stack: '总量',
					            data:count
					            },
					            {
								name:"完成订单数",
					            type:'line',
					            stack: '总量',
					            data:fCounts
					            },
					            {
								name:"取消订单数",
					            type:'line',
					            stack: '总量',
					            data:countNs
					            }] 
					};

					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
		//订单金额按日统计图
		myChartPieNs : function() {
			var orderSum = [];//订单额
			var orderPayMentSum = [];//订单付款额
			var balanceDkSum = [];//余额抵扣额
			var time = [];//时间
			
			var form = $("form[name=orderSerachDayNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStart]").val();
			var createTimeEnd =  form.find("input[name=createTimeEnd]").val();
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/getOrderAmountForDayStat.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStart:createTimeStart,
					createTimeEnd:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						for (var i = 0; i < res.length; i++) {
							orderSum.push(res[i].orderAmount);//订单金额
							orderPayMentSum.push(res[i].payAmount);//订单付款额
							balanceDkSum.push(res[i].balanceAmount);//余额抵扣额
							time.push(res[i].time);
						}
					}
					var myChart = echarts.init(document.getElementById('orderEportNso'));
					option = {
						    title: {
						        text: '订单金额按日统计图',
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["订单金额","订单付款额","余额抵扣额"]
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [{
								name:"订单金额",
					            type:'line',
					            stack: '总量',
					            data:orderSum
					            },
					            {
								name:"订单付款额",
					            type:'line',
					            stack: '总量',
					            data:orderPayMentSum
					            },
					            {
								name:"余额抵扣额",
					            type:'line',
					            stack: '总量',
					            data:balanceDkSum
					            }] 
					};

					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
		myChartPieNsMs : function() {
			var orderSum = [];//订单额
			var orderPayMentSum = [];//订单付款额
			var balanceDkSum = [];//余额抵扣额
			var time = [];//时间
			
			var form = $("form[name=orderSerachDayNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStart]").val();
			var createTimeEnd =  form.find("input[name=createTimeEnd]").val();
			$.ajax({
				url : orderEportForm.appPath + "/eportForm/getOrderAmountDayCumulative.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStart:createTimeStart,
					createTimeEnd:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						for (var i = 0; i < res.length; i++) {
							orderSum.push(res[i].orderAmount);//订单金额
							orderPayMentSum.push(res[i].payAmount);//订单付款额
							balanceDkSum.push(res[i].balanceAmount);//余额抵扣额
							time.push(res[i].time);
						}
					}
					
					var myChart = echarts.init(document.getElementById('orderEportNsoMs'));
					option = {
						    title: {
						        text: '订单金额按日累计图'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["订单金额","订单付款额","余额抵扣额"],
						        top:'3%'
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: time
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [{
								name:"订单金额",
					            type:'line',
					            data:orderSum
					            },
					            {
								name:"订单付款额",
					            type:'line',
					            data:orderPayMentSum
					            },
					            {
								name:"余额抵扣额",
								type:'line',
					            data:balanceDkSum
					            }] 
					};

					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
			}
	};
	return orderEportForm;
});