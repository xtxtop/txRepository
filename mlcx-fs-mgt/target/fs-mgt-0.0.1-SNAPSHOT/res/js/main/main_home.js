define([], function() {

	var mycars=new Array("Saab","Volvo","BMW");
	var indexChart = {
		appPath : getPath("app"),
		isGoWatch:true,//是不是继续执行监听代码
		watchToPage:{},//存储去哪个页面
		addClick:function(obj){
			//注入跳转集合
			indexChart.watchToPage[obj.id] = function(){
				addTab(obj.title,indexChart.appPath + obj.url);
			}
			$("#"+obj.ele).text(obj.text);
			$("#"+obj.ele).data("itemId",obj.id);
			$("#"+obj.ele).css({'cursor':'pointer'});
			//监听点击事件
			$("#"+obj.ele).click(function(){
				addTab(obj.title,indexChart.appPath + obj.url);
			});
		},
		watchFunc:function(id){
			//监控点击事件动态分配
			var indexItemClickId = sessionStorage.getItem("indexItemClickId");
			var funcObj = indexChart.watchToPage;
			for(var props in funcObj){
				if(props==indexItemClickId){
					funcObj[props]();
					indexChart.isGoWatch = false;
				}
			}
		},
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
			if(searchType == "order"){
				addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?orderNo='+searchInput);
			}else if(searchType == "member"){
				debugger
				addTab('会员管理',indexChart.appPath + '/member/toMemberList.do?memberName='+searchInput);
			}else if(searchType == "car"){
				addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?carPlateNo='+searchInput);
			}else if(searchType == "park"){
				addTab('场站列表',indexChart.appPath + '/park/mainPage.do?parkNo='+searchInput);
			}else if(searchType == "worker"){
				addTab('调度工单列表',indexChart.appPath + '/workerOrder/toWorkerOrderList.do?workerOrderNo='+searchInput);
			}
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
		                	            data : dateArray
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}'
		                	            }
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
		                	            data : dateArray
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}'
		                	            }
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
		                	        x : 20
		                	    },
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	        data:['上线','下线']
		                	    },
		                	    toolbox: {
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : [0,0],
		                	            axisLabel : {
		                	                interval:0
		                	            },
		                	            data : dateArray
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}'
		                	            }
		                	        }
		                	    ],
		                	    series : [
		                     	    {
		                   	            name:'上线',
		                   	            type:'bar',
		                   	            data:onlineArray
		                   	        },
		                	        {
		                	            name:'下线',
		                	            type:'bar',
		                	            data:offlineArray
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
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	        data:['告警数']
		                	    },
		                	    toolbox: {
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : [0,0],
		                	            axisLabel : {
		                	                interval:0
		                	            },
		                	            data : dateArray
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}'
		                	            }
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
		showHomeChart:function(){
			$.ajax({
				url : indexChart.appPath + "/index/headerData.do",
				type : "post",
				success : function(res) {
					if (res.code == "1") {
						indexChart.addClick({title:"订单列表",ele:"orderSum",id:res.data.orderNumId,text:res.data.orderNum,url:"/order/toOrderList.do"});
						indexChart.addClick({title:"会员列表",ele:"memberSum",id:res.data.memberNumId,text:res.data.memberNum,url:"/member/toMemberList.do"});
						indexChart.addClick({title:"会员列表",ele:"AcMemSum",id:res.data.censoredMemberNumId,text:res.data.censoredMemberNum,url:"/member/toMemberList.do?cencorStatus=1"});
						indexChart.addClick({title:"车辆列表",ele:"carSum",id:res.data.carNumId,text:res.data.carNum,url:"/car/toCarList.do"});
						indexChart.addClick({title:"场站列表",ele:"airForSum",id:res.data.parkNumId,text:res.data.parkNum,url:"/park/mainPage.do"});
						if(indexChart.isGoWatch){
							indexChart.watchFunc();
						}
						// $("#orderSum").text(res.data.orderNum);     //总订单数
						// $("#memberSum").text(res.data.memberNum);  //总会员数
						// $("#AcMemSum").text(res.data.censoredMemberNum);  //认证会员
						// $("#carSum").text(res.data.carNum);  //总车辆
						// $("#airForSum").text(res.data.parkNum);  //总场站数
						// $("#orderSum").css({'cursor':'pointer'});
						// $("#orderSum").click(function(){							
						// 	addTab('订单列表',indexChart.appPath + '/order/toOrderList.do');
						// });
						// $("#memberSum").css({'cursor':'pointer'});
						// $("#memberSum").click(function(){
						// 	addTab('会员列表',indexChart.appPath + '/member/toMemberList.do');
						// });
						// $("#AcMemSum").css({'cursor':'pointer'});
						// $("#AcMemSum").click(function(){
						// 	addTab('会员列表',indexChart.appPath + '/member/toMemberList.do?cencorStatus=1');
						// });
						// $("#carSum").css({'cursor':'pointer'});
						// $("#carSum").click(function(){
						// 	addTab('车辆列表',indexChart.appPath + '/car/toCarList.do');
						// });
						// $("#airForSum").css({'cursor':'pointer'});
						// $("#airForSum").click(function(){
						// 	addTab('场站列表',indexChart.appPath + '/park/mainPage.do');
						// });
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
						indexChart.addClick({title:"押金退款列表",ele:"waitRefund",id:res.data.waitRefundNumId,text:res.data.waitRefundNum,url:"/depositRefund/toDepositRefundList.do?cencorStatus=0"});
						indexChart.addClick({title:"会员列表",ele:"waitCensorMember",id:res.data.waitCensorMemberNumId,text:res.data.waitCensorMemberNum,url:"/member/toMemberList.do?cencorStatus=2"});
						indexChart.addClick({title:"非订单列表",ele:"onOrder",id:res.data.onOrderNumId,text:res.data.onOrderNum,url:"/noOrder/mainPageReal.do"});
						indexChart.addClick({title:"发票开票列表",ele:"invoice",id:res.data.invoiceNumId,text:res.data.invoiceNum,url:"/invoice/toInvoiceList.do?invoiceStatus=0"});
						indexChart.addClick({title:"客服回复列表",ele:"customerFeedback",id:res.data.customerFeedbackNumId,text:res.data.customerFeedbackNum,url:"/customerFeedback/toCustomerFeedbackList.do?noReply=yes"});
						indexChart.addClick({title:"网店开通建议",ele:"newParkOpening",id:res.data.newParkOpeningNumId,text:res.data.newParkOpeningNum,url:"/parkOpening/mainPage.do?today=yes"});
						indexChart.addClick({title:"订单评价信息",ele:"orderComments",id:res.data.orderCommentsNumId,text:res.data.orderCommentsNum,url:"/orderComments/toOrderCommentsList.do"});
						if(indexChart.isGoWatch){
							indexChart.watchFunc();
						}
						// $("#waitRefund").text(res.data.waitRefundNum);     //待审核
						// $("#waitCensorMember").text(res.data.waitCensorMemberNum);  //待认证会员
						// $("#onOrder").text(res.data.onOrderNum);  //非订单用车
						// $("#invoice").text(res.data.invoiceNum);  //待开发票
						// $("#lowPower").text(res.data.lowPowerNum);  //小电瓶低电
						// $("#customerFeedback").text(res.data.customerFeedbackNum);//客服反馈列表未回复数量
						// $("#newParkOpening").text(res.data.newParkOpeningNum);//今天新增网点反馈数量
						// $("#orderComments").text(res.data.orderCommentsNum);//当天新增订单评价数量
						
						// $("#orderComments").css({'cursor':'pointer'});
						
						// $("#customerFeedback").css({'cursor':'pointer'});
						// $("#customerFeedback").click(function(){
						// 	addTab('客服回复列表',indexChart.appPath + '/customerFeedback/toCustomerFeedbackList.do?noReply=yes');
						// });
						// $("#newParkOpening").css({'cursor':'pointer'});
						// $("#newParkOpening").click(function(){
						// 	addTab('网点开通建议',indexChart.appPath + '/parkOpening/mainPage.do?today=yes');
						// });
						// $("#waitRefund").css({'cursor':'pointer'});
						// $("#waitRefund").click(function(){
						// 	addTab('押金退款列表',indexChart.appPath + '/depositRefund/toDepositRefundList.do?cencorStatus=0');
						// });
						// $("#waitCensorMember").css({'cursor':'pointer'});
						// $("#waitCensorMember").click(function(){
						// 	addTab('会员列表',indexChart.appPath + '/member/toMemberList.do?cencorStatus=2');
						// });
						// $("#onOrder").css({'cursor':'pointer'});
						// $("#onOrder").click(function(){
						// 	addTab('非订单列表',indexChart.appPath + '/noOrder/mainPageReal.do');
						// });
						// $("#invoice").css({'cursor':'pointer'});
						// $("#invoice").click(function(){
						// 	addTab('发票开票列表',indexChart.appPath + '/invoice/toInvoiceList.do?invoiceStatus=0');
						// });
						// $("#lowPower").css({'cursor':'pointer'});
						// $("#lowPower").click(function(){
						// 	addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isMinLowPower=1');
						// });
						
						// $("#orderComments").css({'cursor':'pointer'});
						// $("#orderComments").click(function(){
						// 	addTab('订单评价信息',indexChart.appPath + '/orderComments/toOrderCommentsList.do');
						// });
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
						indexChart.addClick({title:"订单列表",ele:"notPayOrder",id:res.data.notPayOrderId,text:res.data.notPayOrderNum,url:"/order/toOrderList.do?payStatus=0&orderStatus=3"});
						indexChart.addClick({title:"订单列表",ele:"todayOrder",id:res.data.todayOrderId,text:res.data.todayOrderNum,url:"/order/toOrderList.do?today=yes"});
						indexChart.addClick({title:"订单列表",ele:"doingOrder",id:res.data.doingOrderId,text:res.data.doingOrderNum,url:"/order/toOrderList.do?orderStatus=2"});
						indexChart.addClick({title:"套餐订单列表",ele:"recharge",id:res.data.rechargeNumId,text:res.data.rechargeNum,url:"/pricingPackOrder/toPricingPackOrderList.do?today=yes&isRecharge=1&packageType=2"});//
						if(indexChart.isGoWatch){
							indexChart.watchFunc();
						}
						// $("#notPayOrder").text(res.data.notPayOrderNum);  //未支付订单
						// $("#todayOrder").text(res.data.todayOrderNum);  //今日新增订单
						// $("#doingOrder").text(res.data.doingOrderNum);  //进行中的订单
						// $("#recharge").text(res.data.rechargeNum);  //今日充值笔数
						// $("#notPayOrder").css({'cursor':'pointer'});
						// $("#notPayOrder").click(function(){
						// 	addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?payStatus=0&orderStatus=3');
						// });
						// $("#todayOrder").css({'cursor':'pointer'});
						// $("#todayOrder").click(function(){
						// 	addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?today=yes');
						// });
						// $("#doingOrder").css({'cursor':'pointer'});
						// $("#doingOrder").click(function(){
						// 	addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?orderStatus=2');
						// });
						// $("#recharge").css({'cursor':'pointer'});
						// $("#recharge").click(function(){
						// 	addTab('套餐订单列表',indexChart.appPath + '/pricingPackOrder/toPricingPackOrderList.do?today=yes&isRecharge=1&packageType=2');
						// });
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
						indexChart.addClick({title:"场站列表",ele:"lotParkingSpace",id:res.data.lotParkingSpaceId,text:res.data.lotParkingSpaceNum,url:"/park/mainPage.do?isLotParkingSpace=1"});
						indexChart.addClick({title:"调度工单列表",ele:"waitWorkerOrder",id:res.data.waitWorkerOrderId,text:res.data.waitWorkerOrderNum,url:"/workerOrder/toWorkerOrderList.do?workOrderStatus=1"});
						indexChart.addClick({title:"车辆状态列表",ele:"carLowPower",id:res.data.carLowPowerId,text:res.data.carLowPowerNum,url:"/carStatus/toCarStatusList.do?isLowPower=1"});
						indexChart.addClick({title:"车辆列表",ele:"online",id:res.data.onlineId,text:res.data.onlineNum,url:"/car/toCarList.do?onlineStatus=1"});
						indexChart.addClick({title:"车辆列表",ele:"offline",id:res.data.offlineId,text:res.data.offlineNum,url:"/car/toCarList.do?onlineStatus=0"});
						indexChart.addClick({title:"调度工单列表",ele:"doingWorkerOrder",id:res.data.doingWorkerOrderId,text:res.data.doingWorkerOrderNum,url:"/workerOrder/toWorkerOrderList.do?workOrderStatus=2"});
						if(indexChart.isGoWatch){
							indexChart.watchFunc();
						}
						// $("#lotParkingSpace").text(res.data.lotParkingSpaceNum);  //车位不足场站
						// $("#waitWorkerOrder").text(res.data.waitWorkerOrderNum);  //待办任务
						// $("#carLowPower").text(res.data.carLowPowerNum);  //电量不足车辆
						// $("#online").text(res.data.onlineNum);  //上线车辆
						// $("#offline").text(res.data.offlineNum);  //下线车辆
						// $("#doingWorkerOrder").text(res.data.doingWorkerOrderNum);  //进行中的任务
						// $("#lotParkingSpace").css({'cursor':'pointer'});
						// $("#lotParkingSpace").click(function(){
						// 	addTab('场站列表',indexChart.appPath + '/park/mainPage.do?isLotParkingSpace=1');
						// });
						// $("#waitWorkerOrder").css({'cursor':'pointer'});
						// $("#waitWorkerOrder").click(function(){
						// 	addTab('调度工单列表',indexChart.appPath + '/workerOrder/toWorkerOrderList.do?workOrderStatus=1');
						// });
						// $("#carLowPower").css({'cursor':'pointer'});
						// $("#carLowPower").click(function(){
						// 	addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isLowPower=1');
						// });
						// $("#online").css({'cursor':'pointer'});
						// $("#online").click(function(){
						// 	addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?onlineStatus=1');
						// });
						// $("#offline").css({'cursor':'pointer'});
						// $("#offline").click(function(){
						// 	addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?onlineStatus=0');
						// });
						// $("#doingWorkerOrder").css({'cursor':'pointer'});
						// $("#doingWorkerOrder").click(function(){
						// 	addTab('调度工单列表',indexChart.appPath + '/workerOrder/toWorkerOrderList.do?workOrderStatus=2');
						// });
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
						indexChart.addClick({title:"订单列表",ele:"excessOrder",id:res.data.excessOrderNumId,text:res.data.excessOrderNum,url:"/order/toOrderList.do?warningOrder=1"});
						indexChart.addClick({title:"车辆状态列表",ele:"carFreeTime",id:res.data.carIdleNumId,text:res.data.carIdleNum,url:"/car/toCarList.do?isIdle=1"});
						indexChart.addClick({title:"车辆状态列表",ele:"carNotLine",id:res.data.carBrokenLineNumId,text:res.data.carBrokenLineNum,url:"/carStatus/toCarStatusList.do?isBrokenLine=1"});
						indexChart.addClick({title:"非订单列表",ele:"notOrder",id:res.data.onOrderNumId,text:res.data.onOrderNum,url:"/noOrder/mainPageReal.do"});
						indexChart.addClick({title:"车辆状态列表",ele:"lowPower1",id:res.data.lowPowerNumId,text:res.data.lowPowerNum,url:"/carStatus/toCarStatusList.do?isMinLowPower=1"});
						if(indexChart.isGoWatch){
							indexChart.watchFunc();
						}
						// $("#excessOrder").text(res.data.excessOrderNum);  //超额订单
						// $("#carFreeTime").text(res.data.carIdleNum);  //闲置车辆
						// $("#carNotLine").text(res.data.carBrokenLineNum);  //断线车辆
						// $("#notOrder").text(res.data.onOrderNum);  //非订单用车
						// $("#lowPower1").text(res.data.lowPowerNum);  //小电瓶低电
						// $("#excessOrder").css({'cursor':'pointer'});
						// $("#excessOrder").click(function(){
						// 	addTab('订单列表',indexChart.appPath + '/order/toOrderList.do?warningOrder=1');
						// });
						// $("#carFreeTime").css({'cursor':'pointer'});
						// $("#carFreeTime").click(function(){
						// 	addTab('车辆列表',indexChart.appPath + '/car/toCarList.do?isIdle=1');
						// });
						// $("#carNotLine").css({'cursor':'pointer'});
						// $("#carNotLine").click(function(){
						// 	addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isBrokenLine=1');
						// });
						// $("#notOrder").css({'cursor':'pointer'});
						// $("#notOrder").click(function(){
						// 	addTab('非订单列表',indexChart.appPath + '/noOrder/mainPageReal.do');
						// });
						// $("#lowPower1").css({'cursor':'pointer'});
						// $("#lowPower1").click(function(){
						// 	addTab('车辆状态列表',indexChart.appPath + '/carStatus/toCarStatusList.do?isMinLowPower=1');
						// });
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
