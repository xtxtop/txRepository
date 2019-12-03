define([], function() {
	var salePackageEportForm = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#packageSearchDayNs").click(function() {
				debugger;
				var form = $("form[name=packageCountDayNsForm]");
				var createTimeStartDay =  form.find("input[name=createTimeStart]").val();
				var createTimeEndDay =  form.find("input[name=createTimeEnd]").val();
				if(createTimeStartDay==""&&createTimeEndDay==""){
					salePackageEportForm.myChartPie();
					salePackageEportForm.myChartPieMs();
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
						
						salePackageEportForm.myChartPie();
						salePackageEportForm.myChartPieMs();
					}
					
				});
			});
			
			$("#packageSearchMonthNs").click(function() {
				var form = $("form[name=packageCountMonthNsForm]");
				var createTimeStartMonth =  form.find("input[name=createTimeStart]").val();
				var createTimeEndMonth =  form.find("input[name=createTimeEnd]").val();
				
				if(createTimeStartMonth==""&&createTimeEndMonth==""){
					salePackageEportForm.myChartPie3();
					salePackageEportForm.myChartPieMonth();
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
						salePackageEportForm.myChartPie3();
						salePackageEportForm.myChartPieMonth();
					}
					
				});
				
			});
			
			$("#packageSearchWeekNs").click(function() {
				var form = $("form[name=packageCountWeekNsForm]");
				var createTimeStartWeek =  form.find("input[name=createTimeStart]").val();
				var createTimeEndWeek = form.find("input[name=createTimeEnd]").val();
				
				if(createTimeStartWeek==""&&createTimeEndWeek==""){
					salePackageEportForm.myChartPieWeek();
					salePackageEportForm.myChartPieWeekMs();
				}else
				
				if(createTimeStartWeek==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择开始时间。");
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
								salePackageEportForm.myChartPieWeek();
								salePackageEportForm.myChartPieWeekMs();
							}
							
						});
			});
			
			
			
			salePackageEportForm.myChartPie();
			salePackageEportForm.myChartPieMs();
			salePackageEportForm.myChartPieWeek();
			salePackageEportForm.myChartPieWeekMs();
			salePackageEportForm.myChartPie3();
			salePackageEportForm.myChartPieMonth();
			
		},
		//充值套餐销售额、充值额按周统计
		myChartPieWeekNs : function(res) {
			var salesAmount = [];//订单数
			var topUpAmount = [];//订单额
			var time = [];//时间
				
			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					salesAmount.push(res[i].salesAmount);
					topUpAmount.push(res[i].topUpAmount);
					time.push(res[i].time);
				}
			}
			var myChart = echarts.init(document.getElementById('salePackageWeekNso'));
			option = {
				    title: {
				        text: '充值套餐销售额、充值额按周统计',
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["销售额","充值额"]
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
							name:"销售额",
				            type:'line',
				            data:salesAmount
			            },
			            {
							name:"充值额",
				            type:'line',
				            data:topUpAmount
				        }
			            ] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		//充值套餐销售额和充值额按周累计
		myChartPieWeekNsMs : function(res) {
			var salesAmount = [];//销售额
			var topUpAmount = [];//充值额
			var time = [];//时间

			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					salesAmount.push(res[i].salesAmount);
					topUpAmount.push(res[i].topUpAmount);
					time.push(res[i].time);
				}
			}
			var myChart = echarts.init(document.getElementById('salePackageWeekNsoMs'));
			option = {
				    title: {
				        text: '充值套餐销售额、充值额按周累计',
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["销售额","充值额"]
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
							name:"销售额",
				            type:'line',
				            data:salesAmount
			            },
			            {
							name:"充值额",
				            type:'line',
				            data:topUpAmount
				        }
			            ] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		
		//充值套餐按周销售量统计
		myChartPieWeek : function() {
			var salesCount = [];//销售量
			var time = [];//时间
			
			var form = $("form[name=packageCountWeekNsForm]");
			var createTimeStartWeek =  form.find("input[name=createTimeStart]").val();
			var createTimeEndWeek = form.find("input[name=createTimeEnd]").val();

			$.ajax({
				url : salePackageEportForm.appPath + "/eportForm/salePackageEportWeek.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartWeek:createTimeStartWeek,
					createTimeEndWeek:createTimeEndWeek
				},
				success : function(res) {
					if (res != "") {
						salePackageEportForm.myChartPieWeekNs(res);
						for (var i = 0; i < res.length; i++) {
							salesCount.push(res[i].salesNumber);
							time.push(res[i].time);
						}
					}
					var myChart = echarts.init(document.getElementById('salePackageWeekNs'));
					option = {
					    title: {
					        text: '充值套餐销售数按周统计',
					    },
					    tooltip: {
					        trigger: 'axis'
					    },
					    legend: {
					        data:["销售量"]
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
							name:"销售量",
				            type:'line',
				            data:salesCount
				            }] 
				};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		//充值套餐按周累计销售量
		myChartPieWeekMs : function() {
			var salesCount = [];//订单数
			var time = [];//时间
			
			var form = $("form[name=packageCountWeekNsForm]");
			var createTimeStartWeek =  form.find("input[name=createTimeStart]").val();
			var createTimeEndWeek = form.find("input[name=createTimeEnd]").val();

			$.ajax({
				url : salePackageEportForm.appPath + "/eportForm/salePackageEportWeekNs.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartWeek:createTimeStartWeek,
					createTimeEndWeek:createTimeEndWeek
				},
				success : function(res) {
					if (res != "") {
						salePackageEportForm.myChartPieWeekNsMs(res);
						for (var i = 0; i < res.length; i++) {
							salesCount.push(res[i].salesNumber);
							time.push(res[i].time);
						}
					}
					var myChart = echarts.init(document.getElementById('salePackageWeekNsMs'));
					option =  {
						    title: {
						        text: '充值套餐销售数按周累计',
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["销售量"]
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
								name:"销售量",
					            type:'line',
					            data:salesCount
					            }] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
/*		myChartPieDay : function(createTimeStartDay,createTimeEndDay) {
			var count = [];//订单数
			var sum = [];//订单额
			var name = [];//场站名称
			var time = [];//时间
			var series=[];

			$.ajax({
				url : salePackageEportForm.appPath + "/eportForm/salePackageEportDay.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartDay:createTimeStartDay,
					createTimeEndDay:createTimeEndDay,
				},
				success : function(res) {
					if (res != "") {
						salePackageEportForm.myChartPieDayNs(res);
						for (var i = 0; i < res.length; i++) {
							name.push(res[i].packageName);
							time=res[i].times;
							series.push(
							
									{
										name:res[i].packageName,
							            type:'line',
							            stack: '总量',
							            data:res[i].counts
							            }		
							
							);
							
						}
					}

					var myChart = echarts.init(document.getElementById('salePackageNs'));
					option = {
						    title: {
						        text: '套餐销售数量（按天）'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:name
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
						    series: series
};


					// 为echarts对象加载数据
					myChart.setOption(option);

				}
			});
		},*/
		myChartPieDayNs : function(res) {
			var count = [];//订单数
			var sum = [];//订单额
			var name = [];//场站名称
			var time = [];//时间
			var series=[];

			
				
				
					if (res != "") {
						for (var i = 0; i < res.length; i++) {
							//count.push(res[i].count);
							name.push(res[i].packageName);
							time=res[i].times;
							series.push(
							
									{
										name:res[i].packageName,
							            type:'line',
							            stack: '总量',
							            data:res[i].sums
							            }		
							
							);
							
						}
					}
					 
					

					var myChart = echarts.init(document.getElementById('salePackageNso'));
					option = {
						    title: {
						        text: '套餐销售额（按天）'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:name
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
						    series: series
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
		},
		
		//充值套餐销售额、充值额按月统计
		myChartPieMonthNs : function(res) {
			var salesAmount = [];//销售额
			var topUpAmount = [];//充值额
			var time = [];//时间
			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					salesAmount.push(res[i].salesAmount);
					topUpAmount.push(res[i].topUpAmount);
					time.push(res[i].time);
				}
			}

			var myChart = echarts.init(document.getElementById('salePackageMonthNso'));
			option = {
				    title: {
				        text: '充值套餐销售额、充值额按月统计',
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["销售额","充值额"]
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
							name:"销售额",
				            type:'line',
				            data:salesAmount
			            },
			            {
							name:"充值额",
				            type:'line',
				            data:topUpAmount
				         }] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		//充值套餐按月累计销售额和充值额
		myChartPie6 : function(res) {
			var salesAmount = [];//订单数
			var topUpAmount = [];//订单额
			var time = [];//时间
				
			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					salesAmount.push(res[i].salesAmount);
					topUpAmount.push(res[i].topUpAmount);
					time.push(res[i].time);
				}
			}
			var myChart = echarts.init(document.getElementById('salePackageMonthNsoMs'));
			option = {
				    title: {
				        text: '充值套餐销售额、充值额按月累计',
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["销售额","充值额"]
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
							name:"销售额",
				            type:'line',
				            data:salesAmount
			            },
			            {
							name:"充值额",
				            type:'line',
				            data:topUpAmount
				         }] 
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
		},
		//充值套餐按月统计销售量
		myChartPieMonth : function() {
			var salesCount = [];//订单数
			var time = [];//时间

			var form = $("form[name=packageCountMonthNsForm]");
			var createTimeStartMonth =  form.find("input[name=createTimeStart]").val();
			var createTimeEndMonth =  form.find("input[name=createTimeEnd]").val();
			
			$.ajax({
				url : salePackageEportForm.appPath + "/eportForm/salePackageEportMonthNs.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartMonth:createTimeStartMonth,
					createTimeEndMonth:createTimeEndMonth
				},
				success : function(res) {
					if (res != "") {
						salePackageEportForm.myChartPieMonthNs(res);
						for (var i = 0; i < res.length; i++) {
							salesCount.push(res[i].salesNumber);
							time.push(res[i].time);
						}
					}
					var myChart = echarts.init(document.getElementById('salePackageMonthNs'));
					option = {
						    title: {
						        text: '充值套餐销售量按月统计',
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["销售量"]
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
								name:"销售量",
					            type:'line',
					            data:salesCount
					            }] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
		//充值套餐按月累计销售量
		myChartPie3 : function() {
			var salesCount = [];//订单数
			var time = [];//时间

			var form = $("form[name=packageCountMonthNsForm]");
			var createTimeStartMonth =  form.find("input[name=createTimeStart]").val();
			var createTimeEndMonth =  form.find("input[name=createTimeEnd]").val();
			
			$.ajax({
				url : salePackageEportForm.appPath + "/eportForm/salePackageEportMonth.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartMonth:createTimeStartMonth,
					createTimeEndMonth:createTimeEndMonth
				},
				success : function(res) {
					if (res != "") {
						salePackageEportForm.myChartPie6(res);
						for (var i = 0; i < res.length; i++) {
							salesCount.push(res[i].salesNumber);
							time.push(res[i].time);
						}
					}
					 
					var myChart = echarts.init(document.getElementById('salePackageMonthNsMs'));
					option = {
						    title: {
						        text: '充值套餐销售量按月累计',
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["销售量"]
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
								name:"销售量",
					            type:'line',
					            data:salesCount
					            }] 
					};

					// 为echarts对象加载数据
					myChart.setOption(option);

				}
			});
		},
		
		//充值套餐销售数按日累计
		myChartPieMs : function() {
			debugger;
			var salesCount = [];//销售数
			var time = [];//时间
			
			var form = $("form[name=packageCountDayNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStart]").val();
			var createTimeEnd =  form.find("input[name=createTimeEnd]").val();
			
			$.ajax({
				url : salePackageEportForm.appPath + "/eportForm/salePackageEportDayNs.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartDay:createTimeStart,
					createTimeEndDay:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						salePackageEportForm.myChartPieNsMs(res);
						for (var i = 0; i < res.length; i++) {
							salesCount.push(res[i].salesNumber);
							time.push(res[i].time);
						}
					}
					var myChart = echarts.init(document.getElementById('salePackageNsMs'));
					option = {
						    title: {
						        text: '充值套餐销售数按日累计',
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["销售量"]
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
								name:"销售量",
					            type:'line',
					            data:salesCount
					            }] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
		//充值套餐销售数按日统计
		myChartPie : function() {
			var salesCount = [];//销售数
			var time = [];//时间
			
			var form = $("form[name=packageCountDayNsForm]");
			var createTimeStart =  form.find("input[name=createTimeStart]").val();
			var createTimeEnd =  form.find("input[name=createTimeEnd]").val();
			$.ajax({
				url : salePackageEportForm.appPath + "/eportForm/salePackageEportDay.do",
				type : "post",
				dataType : "json", // 数据类型
				data:{
					createTimeStartDay:createTimeStart,
					createTimeEndDay:createTimeEnd,
				},
				success : function(res) {
					if (res != "") {
						salePackageEportForm.myChartPieNs(res);
						for (var i = 0; i < res.length; i++) {
							salesCount.push(res[i].salesNumber);
							time.push(res[i].time);
						}
					}
					var myChart = echarts.init(document.getElementById('salePackageNs'));
					option = {
						    title: {
						        text: '充值套餐销售数按日统计',
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:["销售量"]
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
								name:"销售量",
					            type:'line',
					            data:salesCount
					            }] 
					};
					// 为echarts对象加载数据
					myChart.setOption(option);
				}
			});
		},
		
	
		//充值套餐销售额、充值额按日统计
		myChartPieNs : function(res) {
			var salesAmount = [];//销售额
			var topUpAmount = [];//充值额
			var time = [];//时间
				
			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					salesAmount.push(res[i].salesAmount);
					topUpAmount.push(res[i].topUpAmount);
					time.push(res[i].time);
				}
			}

			var myChart = echarts.init(document.getElementById('salePackageNso'));
			option = {
				    title: {
				        text: '充值套餐销售额、充值额按日统计'
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["销售额","充值额"]
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
						name:"销售额",
			            type:'line',
			            data:salesAmount
		            },
		            {
						name:"充值额",
			            type:'line',
			            data:topUpAmount
		            }
			         ]
				};
				// 为echarts对象加载数据
				myChart.setOption(option);
		},
		//充值套餐销售额、充值额按日统计
		myChartPieNsMs : function(res) {
			var salesAmount = [];//销售额
			var topUpAmount = [];//充值额
			var time = [];//时间

			if (res != "") {
				for (var i = 0; i < res.length; i++) {
					salesAmount.push(res[i].salesAmount);
					topUpAmount.push(res[i].topUpAmount);
					time.push(res[i].time);
					
				}
			}
			var myChart = echarts.init(document.getElementById('salePackageNsoMs'));
			option = {
				    title: {
				        text: '充值套餐销售额、充值额按日累计',
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:["销售额","充值额"]
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
							name:"销售额",
				            type:'line',
				            data:salesAmount
			            },
			            {
							name:"充值额",
				            type:'line',
				            data:topUpAmount
			            }
				      ] 
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		}
	};
	return salePackageEportForm;
});

function reportFormNs(v){
	if(v==0){
		$(".monthNs").hide();
		$(".dayNs").hide();
		$(".weekNs").hide();
	}else if(v==1){
		$(".monthNs").hide();
		$(".dayNs").hide();
		$(".weekNs").show();
	}else if(v==2){
		$(".dayNs").hide();
		$(".weekNs").hide();
		$(".monthNs").show();
	}else if(v==3){
		$(".dayNs").show();
		$(".weekNs").hide();
		$(".monthNs").hide();
	}
}
