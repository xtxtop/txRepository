define([], function() {
	var packageDeductibleEportForm = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#packageDeductibleSearch").click(function() {
				debugger
				var form = $("form[name=packageDeductibleForm]");
				var radio =  form.find("input[name=inlineRadioOptions]:checked").val();
				if(radio==null){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "没有选择类型。");
					return false;
				}
				if(radio==0){
					packageDeductibleEportForm.myChartPie();
				}else if(radio==1){
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					
					if(createTimeStart==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择起始年。");
						return false;
					}
					
					if(createTimeEnd==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择结束年。");
						return false;
					}
					
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间。");
							return false;
						}
					}
					
					packageDeductibleEportForm.myChartPie2(createTimeStart,createTimeEnd);
				}else if(radio==2){
					var yearNs =  form.find("input[name=yearNs]").val();
					
					if(yearNs==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择年份。");
						return false;
					}
					packageDeductibleEportForm.myChartPie3(yearNs);
				}else if(radio==3){
					var monthNs =  form.find("input[name=monthNs]").val();
					
					if(monthNs==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择月份。");
						return false;
					}
					packageDeductibleEportForm.myChartPie4(monthNs);
				}
				
			});
			
			packageDeductibleEportForm.myChartPie();
			
		},
		
		myChartPie4 : function(createTime) {
			var count = [];//套餐总数
			var sum = [];//套餐数量
			var weeks = [];//时间

			$.ajax({
				url : packageDeductibleEportForm.appPath + "/eportForm/eportFormPackageDeductibleDay.do",
				type : "post",
				dataType : "json", // 数据类型
				data : {
					createTime:createTime
				},
				success : function(res) {
				
					if (res != "") {
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].count);
							if(res[i].sum==null){
								sum.push("0");
							}else{
								sum.push(res[i].sum);
							}
							weeks.push(res[i].weeks);
							
						}
					}
					 
					

					var myChart = echarts.init(document.getElementById('eportFormPackageDeductible'));
					var option = {
			                title: {
			                    text: '套餐抵扣统计'
			                },
			                tooltip: {
			                    trigger: 'axis'
			                },
			                color: [
			                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
			                ],
			                legend: {
			                    data:['套餐抵扣数量（个）','抵扣额统计（元）']
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
			                    data: weeks
			                },
			                yAxis: {
			                    type: 'value'
			                },
			                series: [
			                    
			                    {
			                        name:'套餐抵扣数量（个）',
			                        type:'line',
			                        stack: '总量',
			                        data:count
			                    },
			                    {
			                        name:'抵扣额统计（元）',
			                        type:'line',
			                        stack: '总量',
			                        data:sum
			                    },
			                ]
			            };

					// 为echarts对象加载数据
					myChart.setOption(option);

				}
			});
		},	
		
		 myChartPie3 : function(createTime) {
				var count = [];//套餐总数
				var sum = [];//套餐数量
				var weeks = [];//时间

				$.ajax({
					url : packageDeductibleEportForm.appPath + "/eportForm/eportFormPackageDeductibleMonth.do",
					type : "post",
					dataType : "json", // 数据类型
					data : {
						createTime:createTime
					},
					success : function(res) {
					
						if (res != "") {
							for (var i = 0; i < res.length; i++) {
								count.push(res[i].count);
								if(res[i].sum==null){
									sum.push("0");
								}else{
									sum.push(res[i].sum);
								}
								
								weeks.push(res[i].weeks);
								
							}
						}
						 
						

						var myChart = echarts.init(document.getElementById('eportFormPackageDeductible'));
						var option = {
				                title: {
				                    text: '套餐抵扣统计'
				                },
				                tooltip: {
				                    trigger: 'axis'
				                },
				                color: [
				                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
				                ],
				                legend: {
				                    data:['套餐抵扣数量（个）','抵扣额统计（元）']
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
				                    data: weeks
				                },
				                yAxis: {
				                    type: 'value'
				                },
				                series: [
				                    
				                    {
				                        name:'套餐抵扣数量（个）',
				                        type:'line',
				                        stack: '总量',
				                        data:count
				                    },
				                    {
				                        name:'抵扣额统计（元）',
				                        type:'line',
				                        stack: '总量',
				                        data:sum
				                    },
				                ]
				            };

						// 为echarts对象加载数据
						myChart.setOption(option);

					}
				});
			},	
		
		
  myChartPie2 : function(createTimeStart,createTimeEnd) {
			var count = [];//套餐总数
			var sum = [];//套餐数量
			var weeks = [];//时间

			$.ajax({
				url : packageDeductibleEportForm.appPath + "/eportForm/eportFormPackageDeductibleList.do",
				type : "post",
				dataType : "json", // 数据类型
				data : {
					createTimeStart:createTimeStart,createTimeEnd:createTimeEnd
				},
				success : function(res) {
				
					if (res != "") {
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].count);
							if(res[i].sum==null){
								sum.push("0");
							}else{
								sum.push(res[i].sum);
							}
							weeks.push(res[i].weeks);
							
						}
					}
					 
					

					var myChart = echarts.init(document.getElementById('eportFormPackageDeductible'));
					var option = {
							
							
			                title: {
			                    text: '套餐抵扣统计'
			                },
			                tooltip: {
			                    trigger: 'axis'
			                },
			                color: [
			                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
			                ],
			                legend: {
			                    data:['套餐抵扣数量（个）','抵扣额统计（元）']
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
			                    data: weeks
			                },
			                yAxis: {
			                    type: 'value'
			                },
			                series: [
			                    
			                    {
			                        name:'套餐抵扣数量（个）',
			                        type:'line',
			                        stack: '总量',
			                        data:count
			                    },
			                    {
			                        name:'抵扣额统计（元）',
			                        type:'line',
			                        stack: '总量',
			                        data:sum
			                    },
			                ]
			            };

					// 为echarts对象加载数据
					myChart.setOption(option);

				}
			});
		},
		
		myChartPie : function() {
			
			var count = [];//套餐总数
			var sum = [];//套餐数量
			var weeks = [];//时间

			$.ajax({
				url : packageDeductibleEportForm.appPath + "/eportForm/eportFormPackageDeductibleAll.do",
				type : "post",
				dataType : "json", // 数据类型
				/*data : {
					
				},*/
				success : function(res) {
				
					if (res != "") {
						for (var i = 0; i < res.length; i++) {
							count.push(res[i].count);
							if(res[i].sum==null){
								sum.push("0");
							}else{
								sum.push(res[i].sum);
							}
							//weeks.push(res[i].weeks);
							
						}
					}
					 
					

					var myChart = echarts.init(document.getElementById('eportFormPackageDeductible'));
					var option = {
			                title: {
			                    text: '套餐抵扣统计'
			                },
			                tooltip: {
			                    trigger: 'axis'
			                },
			                color: [
			                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
			                ],
			                legend: {
			                    data:['套餐抵扣数量（个）','抵扣额统计（元）']
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
			                    data: ["全部"]
			                },
			                yAxis: {
			                    type: 'value'
			                },
			                series: [
			                    
			                    {
			                        name:'套餐抵扣数量（个）',
			                        type:'line',
			                        stack: '总量',
			                        data:count
			                    },
			                    {
			                        name:'抵扣额统计（元）',
			                        type:'line',
			                        stack: '总量',
			                        data:sum
			                    },
			                ]
			            };

					// 为echarts对象加载数据
					myChart.setOption(option);

				}
			});
		}

	};
	return packageDeductibleEportForm;
});

function reportFormNs(v){
	if(v==0){
		$(".monthNs").hide();
		$(".dayNs").hide();
		$(".yearNs").hide();
	}else if(v==1){
		$(".monthNs").hide();
		$(".dayNs").hide();
		$(".yearNs").show();
	}else if(v==2){
		$(".dayNs").hide();
		$(".yearNs").hide();
		$(".monthNs").show();
	}else if(v==3){
		$(".dayNs").show();
		$(".yearNs").hide();
		$(".monthNs").hide();
	}
}
