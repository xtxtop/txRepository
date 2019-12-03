define([],function() {	
	var orderUserCount={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				orderUserCount.pageList();
				orderUserCount.pageWeekList();
				orderUserCount.pageMonthList();
				orderUserCount.pageYearList();
			 
				// 按天搜索调用
				$("#orderUserSearchDay").click(function() {
					var form = $("form[name=orderUserSerachDayForm]");
					var startTimeDay =  form.find("input[name=startTimeDay]").val();
					var endTimeDay = form.find("input[name=endTimeDay]").val();
					if(startTimeDay!=""&&endTimeDay!=""){
						if(new Date(startTimeDay)>new Date(endTimeDay)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderUserCount.pageList();
						}
					}else{
						orderUserCount.pageList();
					}
				});
				

				// 按周搜索调用
				$("#orderUserSearchWeek").click(function() {
					var form = $("form[name=orderUserSerachWeekForm]");
					var startTimeWeek =  form.find("input[name=startTimeWeek]").val();
					var endTimeWeek = form.find("input[name=endTimeWeek]").val();
					if(startTimeWeek!=""&&endTimeWeek!=""){
						if(new Date(startTimeWeek)>new Date(endTimeWeek)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderUserCount.pageWeekList();
						}
					}else{
						orderUserCount.pageWeekList();
					}
				});
				

				// 按月搜索调用
				$("#orderUserSearchMonth").click(function() {
					var form = $("form[name=orderUserSerachMonthForm]");
					var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
					var endTimeMonth = form.find("input[name=endTimeMonth]").val();
					if(startTimeMonth!=""&&endTimeMonth!=""){
						if(new Date(startTimeMonth)>new Date(endTimeMonth)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderUserCount.pageMonthList();
						}
					}else{
						orderUserCount.pageMonthList();
					}
				});
				

				// 按年搜索调用
				$("#orderUserSearchYear").click(function() {
					var form = $("form[name=orderUserSerachYearForm]");
					var startTimeYear =  form.find("input[name=startTimeYear]").val();
					var endTimeYear = form.find("input[name=endTimeYear]").val();
					if(startTimeYear!=""&&endTimeYear!=""){
						if(new Date(startTimeYear)>new Date(endTimeYear)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderUserCount.pageYearList();
						}
					}else{
						orderUserCount.pageYearList();
					}
				});
			},
			 
			pageList:function () {

				var operateDay=[];
				var arr1=[]; 
				var arr2=[]; 
				
				var form = $("form[name=orderUserSerachDayForm]");
				var startTimeDay =  form.find("input[name=startTimeDay]").val();
				var endTimeDay = form.find("input[name=endTimeDay]").val();
				
				$.ajax({
					url:orderUserCount.appPath+"/order/dayUserOrderOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeDay,endTime:endTimeDay},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].orderDay);
								 
								arr1.push(res.data[i].orderUserAmount);
								arr2.push(res.data[i].orderUserTime); 
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('orderUserOperateCountDay'));
				   var option = {
						    title: {
						        text: '每日订单平均价格'
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['订单平均价格（元）']
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
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'订单平均价格（元）',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('orderUserOperateDayCount'));
						   var option2 = {
								    title: {
								        text: '每日订单平均时长'
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['订单平均时长(分钟)']
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
								        data: operateDay
								    },
								    yAxis: {
								        type: 'value'
								    },
								    series: [
								        {
								            name:'订单平均时长(分钟)',
								            type:'line',
								            stack: '总量',
								            data:arr2
								        }
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
			pageWeekList:function () {

				var operateDay=[];
				var arr1=[]; 
				var arr2=[]; 
				
				var form = $("form[name=orderUserSerachWeekForm]");
				var startTimeWeek =  form.find("input[name=startTimeWeek]").val();
				var endTimeWeek = form.find("input[name=endTimeWeek]").val();
				
				$.ajax({
					url:orderUserCount.appPath+"/order/weekUserOrderOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeWeek,endTime:endTimeWeek},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].orderDay);

								arr1.push(res.data[i].orderUserAmount);
								arr2.push(res.data[i].orderUserTime); 
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('orderUserOperateCountWeek'));
				   var option = {
						    title: {
						        text: '每周订单平均价格'
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['订单平均价格（元）']
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
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'订单平均价格（元）',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('orderUserOperateWeekCount'));
						   var option2 = {
								    title: {
								        text: '每周订单平均时长'
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['订单平均时长(分钟)']
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
								        data: operateDay
								    },
								    yAxis: {
								        type: 'value'
								    },
								    series: [
								        {
								            name:'订单平均时长(分钟)',
								            type:'line',
								            stack: '总量',
								            data:arr2
								        }
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
			pageMonthList:function () {

				var operateDay=[];
				var arr1=[]; 
				var arr2=[]; 
				
				var form = $("form[name=orderUserSerachMonthForm]");
				var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
				var endTimeMonth = form.find("input[name=endTimeMonth]").val();
				
				$.ajax({
					url:orderUserCount.appPath+"/order/monthUserOrderOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeMonth,endTime:endTimeMonth},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].orderDay);

								arr1.push(res.data[i].orderUserAmount);
								arr2.push(res.data[i].orderUserTime); 
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('orderUserOperateCountMonth'));
				   var option = {
						    title: {
						        text: '每月订单平均价格'
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['订单平均价格（元）']
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
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'订单平均价格（元）',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('orderUserOperateMonthCount'));
						   var option2 = {
								    title: {
								        text: '每月订单平均时长'
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['订单平均时长(分钟)']
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
								        data: operateDay
								    },
								    yAxis: {
								        type: 'value'
								    },
								    series: [
								        {
								            name:'订单平均时长(分钟)',
								            type:'line',
								            stack: '总量',
								            data:arr2
								        }
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
			pageYearList:function () {

				var operateDay=[];
				var arr1=[]; 
				var arr2=[]; 
				
				var form = $("form[name=orderUserSerachYearForm]");
				var startTimeYear =  form.find("input[name=startTimeYear]").val();
				var endTimeYear = form.find("input[name=endTimeYear]").val();
				
				$.ajax({
					url:orderUserCount.appPath+"/order/yearUserOrderOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeYear,endTime:endTimeYear},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].orderDay);

								arr1.push(res.data[i].orderUserAmount);
								arr2.push(res.data[i].orderUserTime); 
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('orderUserOperateCountYear'));
				   var option = {
						    title: {
						        text: '每年订单平均价格'
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['订单平均价格（元）']
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
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'订单平均价格（元）',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('orderUserOperateYearCount'));
						   var option2 = {
								    title: {
								        text: '每年订单平均时长'
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['订单平均时长(分钟)']
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
								        data: operateDay
								    },
								    yAxis: {
								        type: 'value'
								    },
								    series: [
								        {
								            name:'订单平均时长(分钟)',
								            type:'line',
								            stack: '总量',
								            data:arr2
								        }
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
	        
	    };
	return orderUserCount;
});


