define([], function() {
	var indexChart = {
		appPath : getPath("app"),
		init : function() {
			//快速查询
			$("#searchType").change(function(){
				debugger
				indexChart.searchTypeChange();
            });
			//快速查询
			$("#quickSearchBut").click(function(){
				indexChart.quickSearch();
            });
			indexChart.showOrderChart();	//订单图表
			indexChart.showMemberChart();   //注册会员图表
			indexChart.showCordonChart();   //车辆上下线统计
			indexChart.showAlarmChart();    //告警数统计
			indexChart.showCarOrderChart();     //监控接口
			indexChart.showHomeChart();     //首页头部
			indexChart.showBackChart();    //待办接口
			indexChart.showDealChart();    //交易接口
			indexChart.showWagonChart();   //车务接口
			indexChart.showMonChart();     //监控接口
			indexChart.getMemberValue();	//会员数据
		},
		searchTypeChange : function() {
			debugger
			var searchType = $("#searchType").val();
			var searchInput = $("#searchInput").val();
			$("#searchInput").val('');
			if(searchType == "order"){
				$("#searchInput").attr("placeholder","请输入订单号");
			}else if(searchType == "member"){
				$("#searchInput").attr("placeholder","请输入会员姓名");
			}else if(searchType == "car"){
				$("#searchInput").attr("placeholder","请输入车牌号");
			}else if(searchType == "park"){
				$("#searchInput").attr("placeholder","请输入场站编号");
			}else if(searchType == "worker"){
				$("#searchInput").attr("placeholder","请输入任务单号");
			}
		},
		quickSearch : function() {
			var searchType = $("#searchType").val();
			var searchInput = $("#searchInput").val();
			if(searchType&&searchInput){
				if(searchType == "order"){
					addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?orderNo='+searchInput);
				}else if(searchType == "member"){
					addTab('会员管理',indexChart.appPath + '/member/toMemberList.do?memberName='+searchInput);
				}else if(searchType == "car"){
					addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?carPlateNo='+searchInput);
				}else if(searchType == "park"){
					addTab('场站列表',indexChart.appPath + '/park/mainPage.do?parkNo='+searchInput);
				}else if(searchType == "worker"){
					addTab('调度工单列表',indexChart.appPath + '/workerOrder/toWorkerOrderList.do?workerOrderNo='+searchInput);
				}
			}else{
				indexChart.seacherAddCss()
			}
			
		},
		//首页搜索添加样式方法
		seacherAddCss:function(){
			$("#searchType,#searchInput").css({'border-color':'#ff0000'});
			$(".seach-tip").css({'left':'70px','opacity':'1'});
			setTimeout(function(){
				$("#searchType,#searchInput").css({'border-color':'#2A63FB'});
				$(".seach-tip").css({'left':'460px','opacity':'0'})
			},3000)
		},
		showOrderChart : function() {
			$.ajax({
				url : indexChart.appPath + "/index/getOrderDay10Count.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						var dateArray = res.data.dateArray;
						var numArray = res.data.numArray;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('orderTrend')); 
		                
		                option = {
		                	    title : {
		                	        text: '订单数统计图（近10天）',
			                	    x : 20
		                	    },
		                	    color:['#0872EA'],
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	        data:['成交量']
		                	    },
		                	    toolbox: {
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : false,
		                	            data : dateArray,
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                }
				                	    },
				                	    splitLine:{
		                                    show:false,
		                                },
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}'
		                	            },
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                }
				                	    },
				                	    splitLine:{
		                                    lineStyle:{
		                                        type:'dashed',
		                                        color:'#aaa'
		                                    }
		                                },
		                	        }
		                	    ],
		                	    series : [
		                	        {
		                	            name:'成交量',
		                	            type:'line',
		                	            data:numArray
		                	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},
		showMemberChart : function() {
			$.ajax({
				url : indexChart.appPath + "/index/getMember10Count.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						var dateArray = res.data.dateArray;
						var registerArray = res.data.registerArray;
						var cencorArray = res.data.cencorArray;
						var depositArray = res.data.depositArray;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('memberTrend')); 
		                
		                option = {
		                	    title : {
		                	        text: '会员数统计（近10天）'
		                	    },
		                	    color:[ "#3F9FFF","#FF1B1B","#33BE67"],
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	    	x:200,
		                	        data:['注册会员','认证会员','缴押金会员']
		                	    },
		                	    toolbox: {
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : false,
		                	            data : dateArray,
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                }
				                	    },
				                	    splitLine:{
		                                    show:false,
		                                },
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}'
		                	            },
		                	        axisLine:{
	                                    show:true,
	                                    lineStyle:{
		                                	color:'#E5E5E5',
	                                        fontWeight:'200',
	                                        
		                                }
			                	    },
			                	    splitLine:{
	                                    lineStyle:{
	                                        type:'dashed',
	                                        color:'#aaa'
	                                    }
	                                },
		                	        }
		                	    ],
		                	    series : [
		                     	    {
		                   	            name:'注册会员',
		                   	            type:'line',
		                   	            data:registerArray
		                   	        },
		                	        {
		                	            name:'认证会员',
		                	            type:'line',
		                	            data:cencorArray
		                	        },
		                	        {
		                	            name:'缴押金会员',
		                	            type:'line',
		                	            data:depositArray
		                	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},	
		showCordonChart : function() {
			$.ajax({
				url : indexChart.appPath + "/index/getCarLine10Count.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						var dateArray = res.data.dateArray;
						var onlineArray = res.data.onlineArray;
						var offlineArray = res.data.offlineArray;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('carTrend')); 
		                
		                option = {
		                		title : {
			                	   text: '车辆上下线统计（近10天）',
		                	    },
		                	    color:[ "#3F9FFF","#C7E3FF"],
		                	    tooltip : {
		                	        trigger: 'axis',
		                	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		                	            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
		                	        },
		                	        formatter:function(data){
		                	        	var time = data[0][1];
		                	        	var farmatterVar0 = data[0][2];
		                	        	var upLine = data[0][0]+":"+(farmatterVar0>0?farmatterVar0:Math.abs(farmatterVar0));
		                	        	var farmatterVar1 = data[1][2];
		                	        	var downLine = data[1][0]+":"+(farmatterVar1>0?farmatterVar1:Math.abs(farmatterVar1));
		                	    		return time+"<br/>"+upLine+"<br/>"+downLine;
		                	    	}
		                	    },
		                	    legend: {
		                	        data:[ '上线', '下线']
		                	    },
		                	    grid: {
		                	        left: '3%',
		                	        right: '4%',
		                	        bottom: '3%',
		                	        containLabel: true
		                	    },
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            axisTick : {show: false},
		                	            splitLine:{
		                                    show:false
		                                },
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                },
		                                },
		                                splitLine:{
		                                    show:false,
		                                },
		                                
		                	            data : dateArray
		                	        }
		                	    ],
		                	    yAxis : [
		                	    	{
		                	            type : 'value',
		                	            splitLine:{
		                                    lineStyle:{
		                                        type:'dashed',
		                                        color:'#aaa'
		                                    }
		                                },
		                	            axisLabel : {
			                	                formatter: function(value){
			                	                	return value<0?Math.abs(value):value
			                	                }
			                	            },
			                	            axisLine:{
			                                    show:true,
			                                    lineStyle:{
				                                	color:'#E5E5E5',
			                                        fontWeight:'200',
			                                        
				                                }
					                	    },
			                	    },
			                	   
		                	    ],
		                	    series : [
		                	        
		                	        {
		                	            name:'上线',
		                	            type:'bar',
		                	            stack: '总量',
		                	            label: {
		                	                normal: {
		                	                    show: true
		                	                }
		                	            },
		                	            data:onlineArray
		                	        },
		                	        {
		                	            name:'下线',
		                	            type:'bar',
		                	            stack: '总量',
		                	            
		                	            label: {
		                	                normal: {
		                	                    show: true,
		                	                    position: 'onmousedown'
		                	                }
		                	            },
		                	            data:offlineArray,
		                	            
		                	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},
		showAlarmChart : function() {
			$.ajax({
				url : indexChart.appPath + "/index/getWarning10Count.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						var dateArray = res.data.dateArray;
						var numArray = res.data.numArray;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('alarmTrend')); 
		                
		                
		                option = {
		                	    title : {
		                	        text: '告警数统计（近10天）'
		                	    },
		                	    color:['#FF1B1B'],
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	        data:['告警数'],
		                	        borderRadius: [5, 5, 0, 0] //（顺时针左上，右上，右下，左下）
		                	    },
		                	    toolbox: {
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : [0,0],
		                	            axisLabel : {
		                	                interval:0,
		                	            },
		                	            data : dateArray,
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                }
				                	    },
				                	    splitLine:{
		                                    show:false,
		                                },
		                	        },
		                	        
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}',
		                	            },
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                }
				                	    },
				                	    splitLine:{
		                                    lineStyle:{
		                                        type:'dashed',
		                                        color:'#aaa'
		                                    }
		                                },
		                	        }
		                	    ],
		                	    series : [
		                     	    {
		                   	            name:'告警数',
		                   	            type:'bar',
		                   	            data:numArray
		                   	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},
		showCarOrderChart: function() {
			$.ajax({
				url : indexChart.appPath + "/index/getCarOrderCount.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						var dateArray = res.data.dateArray;
						var numArray = res.data.numArray;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('carOrderTrend')); 
		                
		                option = {
		                	    title : {
		                	        text: '车型订单使用率',
			                	    x : 20
		                	    },
		                	    color:['#0872EA'],
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	        data:['使用量']
		                	    },
		                	    toolbox: {
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : false,
		                	            data : numArray,
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                }
				                	    },
				                	    splitLine:{
		                                    show:false,
		                                },
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}'
		                	            },
		                	            axisLine:{
		                                    show:true,
		                                    lineStyle:{
			                                	color:'#E5E5E5',
		                                        fontWeight:'200',
		                                        
			                                }
				                	    },
				                	    splitLine:{
		                                    lineStyle:{
		                                        type:'dashed',
		                                        color:'#aaa'
		                                    }
		                                },
		                	        }
		                	    ],
		                	    series : [
		                	        {
		                	            name:'使用量 ',
		                	            type:'line',
		                	            data:dateArray
		                	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},
		showHomeChart:function(){
			$.ajax({
				url : indexChart.appPath + "/index/headerData.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						console.log(res.data);
						$("#orderSum").text(res.data.orderNum);     //总订单数
						$("#memberSum").text(res.data.memberNum);  //总会员数
						$("#AcMemSum").text(res.data.censoredMemberNum);  //认证会员
						$("#carSum").text(res.data.carNum);  //总车辆
						$("#airForSum").text(res.data.parkNum);  //总场站数
						$("#orderSum").css({'cursor':'pointer'});
						$("#orderSum").click(function(){
							addTab('订单列表',indexChart.appPath + '/order/toOrderList.do');
						});
						$("#memberSum").css({'cursor':'pointer'});
						$("#memberSum").click(function(){
							addTab('会员列表',indexChart.appPath + '/member/toMemberList.do');
						});
						$("#AcMemSum").css({'cursor':'pointer'});
						$("#AcMemSum").click(function(){
							addTab('会员列表',indexChart.appPath + '/member/toMemberList.do?cencorStatus=1');
						});
						$("#carSum").css({'cursor':'pointer'});
						$("#carSum").click(function(){
							addTab('车辆列表',indexChart.appPath + '/car/toCarList.do');
						});
						$("#airForSum").css({'cursor':'pointer'});
						$("#airForSum").click(function(){
							addTab('场站列表',indexChart.appPath + '/park/mainPage.do');
						});
					}
				}
			})
		},
		//待办
		showBackChart:function(){
			$.ajax({
				url : indexChart.appPath + "/index/toDoData.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						console.log(res.data);
						$("#waitRefund").text(res.data.waitRefundNum);     //待审核
						$("#waitCensorMember").text(res.data.waitCensorMemberNum);  //待认证会员
						$("#onOrder").text(res.data.onOrderNum);  //非订单用车
						$("#invoice").text(res.data.invoiceNum);  //待开发票
						$("#lowPower").text(res.data.lowPowerNum);  //小电瓶低电
						$("#customerFeedback").text(res.data.customerFeedbackNum);//客服反馈列表未回复数量
						$("#newParkOpening").text(res.data.newParkOpeningNum);//今天新增网点反馈数量
						$("#orderComments").text(res.data.orderCommentsNum);//当天新增订单评价数量
						
						$("#orderComments").css({'cursor':'pointer'});
						
						$("#customerFeedback").css({'cursor':'pointer'});
						$("#customerFeedback").click(function(){
							addTab('客服回复列表',indexChart.appPath + '/customerFeedback/toCustomerFeedbackList.do?noReply=yes');
						});
						$("#newParkOpening").css({'cursor':'pointer'});
						$("#newParkOpening").click(function(){
							addTab('网点开通建议',indexChart.appPath + '/parkOpening/mainPage.do?today=yes');
						});
						$("#waitRefund").css({'cursor':'pointer'});
						$("#waitRefund").click(function(){
							addTab('押金退款列表',indexChart.appPath + '/depositRefund/toDepositRefundList.do?cencorStatus=0');
						});
						$("#waitCensorMember").css({'cursor':'pointer'});
						$("#waitCensorMember").click(function(){
							addTab('会员列表',indexChart.appPath + '/member/toMemberList.do?cencorStatus=2');
						});
						$("#onOrder").css({'cursor':'pointer'});
						$("#onOrder").click(function(){
							addTab('非订单列表',indexChart.appPath + '/noOrder/mainPageReal.do');
						});
						$("#invoice").css({'cursor':'pointer'});
						$("#invoice").click(function(){
							addTab('发票开票列表',indexChart.appPath + '/invoice/toInvoiceList.do?invoiceStatus=0');
						});
						$("#lowPower").css({'cursor':'pointer'});
						$("#lowPower").click(function(){
							addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isMinLowPower=1');
						});
						
						$("#orderComments").css({'cursor':'pointer'});
						$("#orderComments").click(function(){
							addTab('订单评价信息',indexChart.appPath + '/orderComments/toOrderCommentsList.do');
						});
					}
				}
			})
		},
		//交易
		showDealChart:function(){
			$.ajax({
				url : indexChart.appPath + "/index/transactionData.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						console.log(res.data);
						$("#notPayOrder").text(res.data.notPayOrderNum);  //未支付订单
						$("#todayOrder").text(res.data.todayOrderNum);  //今日新增订单
						$("#doingOrder").text(res.data.doingOrderNum);  //进行中的订单
						$("#recharge").text(res.data.rechargeNum);  //今日充值笔数
						$("#notPayOrder").css({'cursor':'pointer'});
						$("#notPayOrder").click(function(){
							addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?payStatus=0&orderStatus=3');
						});
						$("#todayOrder").css({'cursor':'pointer'});
						$("#todayOrder").click(function(){
							addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?today=yes');
						});
						$("#doingOrder").css({'cursor':'pointer'});
						$("#doingOrder").click(function(){
							addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?orderStatus=2');
						});
						$("#recharge").css({'cursor':'pointer'});
						$("#recharge").click(function(){
							addTab('套餐订单列表',indexChart.appPath + '/pricingPackOrder/toPricingPackOrderList.do?today=yes&isRecharge=1&packageType=2');
						});
					}
				}
			})
		},
		//车务
		showWagonChart:function(){
			$.ajax({
				url : indexChart.appPath + "/index/carServiceData.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						console.log(res.data);
						$("#lotParkingSpace").text(res.data.lotParkingSpaceNum);  //车位不足场站
						$("#waitWorkerOrder").text(res.data.waitWorkerOrderNum);  //待办任务
						$("#carLowPower").text(res.data.carLowPowerNum);  //电量不足车辆
						$("#online").text(res.data.onlineNum);  //上线车辆
						$("#offline").text(res.data.offlineNum);  //下线车辆
						$("#doingWorkerOrder").text(res.data.doingWorkerOrderNum);  //进行中的任务
						$("#lotParkingSpace").css({'cursor':'pointer'});
						$("#lotParkingSpace").click(function(){
							addTab('场站列表',indexChart.appPath + '/park/mainPage.do?isLotParkingSpace=1');
						});
						$("#waitWorkerOrder").css({'cursor':'pointer'});
						$("#waitWorkerOrder").click(function(){
							addTab('调度工单列表',indexChart.appPath + '/workerOrder/toWorkerOrderList.do?workOrderStatus=1');
						});
						$("#carLowPower").css({'cursor':'pointer'});
						$("#carLowPower").click(function(){
							addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isLowPower=1');
						});
						$("#online").css({'cursor':'pointer'});
						$("#online").click(function(){
							addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?onlineStatus=1');
						});
						$("#offline").css({'cursor':'pointer'});
						$("#offline").click(function(){
							addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?onlineStatus=0');
						});
						$("#doingWorkerOrder").css({'cursor':'pointer'});
						$("#doingWorkerOrder").click(function(){
							addTab('调度工单列表',indexChart.appPath + '/workerOrder/toWorkerOrderList.do?workOrderStatus=2');
						});
					}
				}
			})
		},
		//监控
		showMonChart:function(){
			$.ajax({
				url : indexChart.appPath + "/index/warningData.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						console.log(res.data);
						$("#excessOrder").text(res.data.excessOrderNum);  //超额订单
						$("#carFreeTime").text(res.data.carIdleNum);  //闲置车辆
						$("#carNotLine").text(res.data.carBrokenLineNum);  //断线车辆
						$("#notOrder").text(res.data.onOrderNum);  //非订单用车
						$("#lowPower1").text(res.data.lowPowerNum);  //小电瓶低电
						$("#excessOrder").css({'cursor':'pointer'});
						$("#excessOrder").click(function(){
							addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?warningOrder=1');
						});
						$("#carFreeTime").css({'cursor':'pointer'});
						$("#carFreeTime").click(function(){
							addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?isIdle=1');
						});
						$("#carNotLine").css({'cursor':'pointer'});
						$("#carNotLine").click(function(){
							addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isBrokenLine=1');
						});
						$("#notOrder").css({'cursor':'pointer'});
						$("#notOrder").click(function(){
							addTab('非订单列表',indexChart.appPath + '/noOrder/mainPageReal.do');
						});
						$("#lowPower1").css({'cursor':'pointer'});
						$("#lowPower1").click(function(){
							addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isMinLowPower=1');
						});
					}
				}
			})
		},
		getMemberValue : function() {
			$.ajax({
				url : indexChart.appPath + "/getRealTimeMeberNum.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						var result = res.data;
						$("#newRegisterNum").html(result.newRegisterNum);
						$("#newRealNum").html(result.newRealViewNum);
					}
				}
			});
		}
	}
	return indexChart;
});
