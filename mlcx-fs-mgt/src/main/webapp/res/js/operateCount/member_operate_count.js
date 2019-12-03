define([],function() {	
	var memberOperateCount={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				memberOperateCount.pageList();
				memberOperateCount.pageWeekList();
				memberOperateCount.pageMonthList();
				memberOperateCount.pageYearList();
			 
				// 按天搜索调用
				$("#memberSearchDay").click(function() {
					var form = $("form[name=memberSerachDayForm]");
					var startTimeDay =  form.find("input[name=startTimeDay]").val();
					var endTimeDay = form.find("input[name=endTimeDay]").val();
					if(startTimeDay!=""&&endTimeDay!=""){
						if(new Date(startTimeDay)>new Date(endTimeDay)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							memberOperateCount.pageList();
						}
					}else{
						memberOperateCount.pageList();
					}
				});
				

				// 按周搜索调用
				$("#memberSearchWeek").click(function() {
					var form = $("form[name=memberSerachWeekForm]");
					var startTimeWeek =  form.find("input[name=startTimeWeek]").val();
					var endTimeWeek = form.find("input[name=endTimeWeek]").val();
					if(startTimeWeek!=""&&endTimeWeek!=""){
						if(new Date(startTimeWeek)>new Date(endTimeWeek)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							memberOperateCount.pageWeekList();
						}
					}else{
						memberOperateCount.pageWeekList();
					}
				});
				

				// 按月搜索调用
				$("#memberSearchMonth").click(function() {
					var form = $("form[name=memberSerachMonthForm]");
					var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
					var endTimeMonth = form.find("input[name=endTimeMonth]").val();
					if(startTimeMonth!=""&&endTimeMonth!=""){
						if(new Date(startTimeMonth)>new Date(endTimeMonth)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							memberOperateCount.pageMonthList();
						}
					}else{
						memberOperateCount.pageMonthList();
					}
				});
				

				// 按年搜索调用
				$("#memberSearchYear").click(function() {
					var form = $("form[name=memberSerachYearForm]");
					var startTimeYear =  form.find("input[name=startTimeYear]").val();
					var endTimeYear = form.find("input[name=endTimeYear]").val();
					if(startTimeYear!=""&&endTimeYear!=""){
						if(new Date(startTimeYear)>new Date(endTimeYear)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							memberOperateCount.pageYearList();
						}
					}else{
						memberOperateCount.pageYearList();
					}
				});
			},
			 
			pageList:function () {

				var operateDay=[];
				var arr1=[];// 会员注册数
				var arr2=[];// 会员认证数
				var arr3=[];//押金缴费 
				var arr4=[];//退款金额 

				var arrCount1=[];// 会员注册数
				var arrCount2=[];// 会员认证数
				var arrCount3=[];//押金缴费 
				var arrCount4=[];//退款金额 
				
				var form = $("form[name=memberSerachDayForm]");
				var startTimeDay =  form.find("input[name=startTimeDay]").val();
				var endTimeDay = form.find("input[name=endTimeDay]").val();
				
				$.ajax({
					url:memberOperateCount.appPath+"/memberCount/pageListmemberOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeDay,endTime:endTimeDay},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].memberDay);
								 
								arr1.push(res.data[i].memberRegisterCount);
								arr2.push(res.data[i].memberAuthenticationCount);
								arr3.push(res.data[i].memberDepositPaymentCount);
								arr4.push(res.data[i].memberRefundAmountCount);
								  
								arrCount1.push(res.data[i].memberRegisterCountzj);
								arrCount2.push(res.data[i].memberAuthenticationCountzj);
								arrCount3.push(res.data[i].memberDepositPaymentCountzj);
								arrCount4.push(res.data[i].memberRefundAmountCountzj);
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('memberOperateCountDay'));
				   var option = {
						    title: {
						        text: '会员基础数据按日统计',
						        subtext: '说明：会员每天注册、认证、缴押金、退款的数量', //副标题
						        left: 'center', //位置
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
//						        data: ['周一','周二','周三','周四','周五','周六','周日']
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'会员注册数',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        },
						        {
						            name:'会员认证数',
						            type:'line',
						            stack: '总量',
						            data:arr2
						        },
						        {
						            name:'缴押金人数',
						            type:'line',
						            stack: '总量',
						            data:arr3
						        },
						        {
						            name:'退款人数',
						            type:'line',
						            stack: '总量',
						            data:arr4
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('memberOperateDayCount'));
						   var option2 = {
								    title: {
								        text: '会员基础数据按日累计',
								        subtext: '说明：会员注册、认证、缴押金、退款按天合计的数量', //副标题
								        left: 'center', //位置
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
								            name:'会员注册数',
								            type:'line',
								            stack: '总量',
								            data:arrCount1
								        },
								        {
								            name:'会员认证数',
								            type:'line',
								            stack: '总量',
								            data:arrCount2
								        },
								        {
								            name:'缴押金人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount3
								        },
								        {
								            name:'退款人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount4
								        } 
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
			pageWeekList:function () {

				var operateDay=[];
				var arr1=[];// 会员注册数
				var arr2=[];// 会员认证数
				var arr3=[];//押金缴费 
				var arr4=[];//退款金额 

				var arrCount1=[];// 会员注册数
				var arrCount2=[];// 会员认证数
				var arrCount3=[];//押金缴费 
				var arrCount4=[];//退款金额 
				
				var form = $("form[name=memberSerachWeekForm]");
				var startTimeWeek =  form.find("input[name=startTimeWeek]").val();
				var endTimeWeek = form.find("input[name=endTimeWeek]").val();
				
				$.ajax({
					url:memberOperateCount.appPath+"/memberCount/weekMemberOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeWeek,endTime:endTimeWeek},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].memberDay);
								 
								arr1.push(res.data[i].memberRegisterCount);
								arr2.push(res.data[i].memberAuthenticationCount);
								arr3.push(res.data[i].memberDepositPaymentCount);
								arr4.push(res.data[i].memberRefundAmountCount);
								  
								arrCount1.push(res.data[i].memberRegisterCountzj);
								arrCount2.push(res.data[i].memberAuthenticationCountzj);
								arrCount3.push(res.data[i].memberDepositPaymentCountzj);
								arrCount4.push(res.data[i].memberRefundAmountCountzj);
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('memberOperateCountWeek'));
				   var option = {
						    title: {
						        text: '会员基础数据按周统计',
						        subtext: '说明：会员每周注册、认证、缴押金、退款的数量', //副标题
						        left: 'center', //位置
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
//						        data: ['周一','周二','周三','周四','周五','周六','周日']
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'会员注册数',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        },
						        {
						            name:'会员认证数',
						            type:'line',
						            stack: '总量',
						            data:arr2
						        },
						        {
						            name:'缴押金人数',
						            type:'line',
						            stack: '总量',
						            data:arr3
						        },
						        {
						            name:'退款人数',
						            type:'line',
						            stack: '总量',
						            data:arr4
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('memberOperateWeekCount'));
						   var option2 = {
								    title: {
								        text: '会员基础数据按周累计',
								        subtext: '说明：会员注册、认证、缴押金、退款按周合计的数量', //副标题
								        left: 'center', //位置
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
								            name:'会员注册数',
								            type:'line',
								            stack: '总量',
								            data:arrCount1
								        },
								        {
								            name:'会员认证数',
								            type:'line',
								            stack: '总量',
								            data:arrCount2
								        },
								        {
								            name:'缴押金人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount3
								        },
								        {
								            name:'退款人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount4
								        } 
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
			pageMonthList:function () {

				var operateDay=[];
				var arr1=[];// 会员注册数
				var arr2=[];// 会员认证数
				var arr3=[];//押金缴费 
				var arr4=[];//退款金额 

				var arrCount1=[];// 会员注册数
				var arrCount2=[];// 会员认证数
				var arrCount3=[];//押金缴费 
				var arrCount4=[];//退款金额 
				
				var form = $("form[name=memberSerachMonthForm]");
				var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
				var endTimeMonth = form.find("input[name=endTimeMonth]").val();

				$.ajax({
					url:memberOperateCount.appPath+"/memberCount/monthMemberOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeMonth,endTime:endTimeMonth},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].memberDay);
								 
								arr1.push(res.data[i].memberRegisterCount);
								arr2.push(res.data[i].memberAuthenticationCount);
								arr3.push(res.data[i].memberDepositPaymentCount);
								arr4.push(res.data[i].memberRefundAmountCount);
								  
								arrCount1.push(res.data[i].memberRegisterCountzj);
								arrCount2.push(res.data[i].memberAuthenticationCountzj);
								arrCount3.push(res.data[i].memberDepositPaymentCountzj);
								arrCount4.push(res.data[i].memberRefundAmountCountzj);
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('memberOperateCountMonth'));
				   var option = {
						    title: {
						        text: '会员基础数据按月统计',
						        subtext: '说明：会员每月注册、认证、缴押金、退款的数量', //副标题
						        left: 'center', //位置
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
//						        data: ['周一','周二','周三','周四','周五','周六','周日']
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'会员注册数',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        },
						        {
						            name:'会员认证数',
						            type:'line',
						            stack: '总量',
						            data:arr2
						        },
						        {
						            name:'缴押金人数',
						            type:'line',
						            stack: '总量',
						            data:arr3
						        },
						        {
						            name:'退款人数',
						            type:'line',
						            stack: '总量',
						            data:arr4
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('memberOperateMonthCount'));
						   var option2 = {
								    title: {
								        text: '会员基础数据按月累计',
								        subtext: '说明：会员注册、认证、缴押金、退款按月合计的数量', //副标题
								        left: 'center', //位置
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
								            name:'会员注册数',
								            type:'line',
								            stack: '总量',
								            data:arrCount1
								        },
								        {
								            name:'会员认证数',
								            type:'line',
								            stack: '总量',
								            data:arrCount2
								        },
								        {
								            name:'缴押金人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount3
								        },
								        {
								            name:'退款人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount4
								        } 
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
			pageYearList:function () {

				var operateDay=[];
				var arr1=[];// 会员注册数
				var arr2=[];// 会员认证数
				var arr3=[];//押金缴费 
				var arr4=[];//退款金额 

				var arrCount1=[];// 会员注册数
				var arrCount2=[];// 会员认证数
				var arrCount3=[];//押金缴费 
				var arrCount4=[];//退款金额 
				
				var form = $("form[name=memberSerachYearForm]");
				var startTimeYear =  form.find("input[name=startTimeYear]").val();
				var endTimeYear = form.find("input[name=endTimeYear]").val();
				
				$.ajax({
					url:memberOperateCount.appPath+"/memberCount/yearMemberOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeYear,endTime:endTimeYear},
					success:function(res){
						if(res.data != ""){
							for(var i=0;i<res.data.length;i++){

								operateDay.push(res.data[i].memberDay);
								 
								arr1.push(res.data[i].memberRegisterCount);
								arr2.push(res.data[i].memberAuthenticationCount);
								arr3.push(res.data[i].memberDepositPaymentCount);
								arr4.push(res.data[i].memberRefundAmountCount);
								  
								arrCount1.push(res.data[i].memberRegisterCountzj);
								arrCount2.push(res.data[i].memberAuthenticationCountzj);
								arrCount3.push(res.data[i].memberDepositPaymentCountzj);
								arrCount4.push(res.data[i].memberRefundAmountCountzj);
								
							 }
						} 
						
				   var myChart = echarts.init(document.getElementById('memberOperateCountYear'));
				   var option = {
						    title: {
						        text: '会员基础数据按年统计',
						        subtext: '说明：会员每年注册、认证、缴押金、退款的数量', //副标题
						        left: 'center', //位置
						    },
						    tooltip: {
						        trigger: 'axis'
						    }, 
						    legend: {
						        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
//						        data: ['周一','周二','周三','周四','周五','周六','周日']
						        data: operateDay
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'会员注册数',
						            type:'line',
						            stack: '总量',
						            data:arr1
						        },
						        {
						            name:'会员认证数',
						            type:'line',
						            stack: '总量',
						            data:arr2
						        },
						        {
						            name:'缴押金人数',
						            type:'line',
						            stack: '总量',
						            data:arr3
						        },
						        {
						            name:'退款人数',
						            type:'line',
						            stack: '总量',
						            data:arr4
						        } 
						    ]
						};
 
						myChart.setOption(option);
						
						

						   var myChart2 = echarts.init(document.getElementById('memberOperateYearCount'));
						   var option2 = {
								    title: {
								        text: '会员基础数据按年累计',
								        subtext: '说明：会员注册、认证、缴押金、退款按年合计的数量', //副标题
								        left: 'center', //位置
								    },
								    tooltip: {
								        trigger: 'axis'
								    }, 
								    legend: {
								        data:['会员注册数','会员认证数','缴押金人数','退款人数']
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
								            name:'会员注册数',
								            type:'line',
								            stack: '总量',
								            data:arrCount1
								        },
								        {
								            name:'会员认证数',
								            type:'line',
								            stack: '总量',
								            data:arrCount2
								        },
								        {
								            name:'缴押金人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount3
								        },
								        {
								            name:'退款人数',
								            type:'line',
								            stack: '总量',
								            data:arrCount4
								        } 
								    ]
								};
		 
								myChart2.setOption(option2);
								
					}
			});
				
			},
	        
	    };
	return memberOperateCount;
});


