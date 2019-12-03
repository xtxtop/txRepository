define([], function() {
	var packageEportForm = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#eportFormPackageSearch").click(function() {
				debugger
				var form = $("form[name=eportFormPackageForm]");
				var radio =  form.find("input[name=inlineRadioOptions]:checked").val();
				if(radio==null){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "没有选择类型。");
					return false;
				}
				if(radio==0){
					packageEportForm.myChartPie();
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
					
					packageEportForm.myChartPie2(createTimeStart,createTimeEnd);
				}else if(radio==2){
					var yearNs =  form.find("input[name=yearNs]").val();
					
					if(yearNs==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择年份。");
						return false;
					}
					packageEportForm.myChartPie3(yearNs);
				}else if(radio==3){
					var monthNs =  form.find("input[name=monthNs]").val();
					
					if(monthNs==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择月份。");
						return false;
					}
					packageEportForm.myChartPie4(monthNs);
				}
				
			});
			
			packageEportForm.myChartPie();
			
		},
		
		myChartPie4 : function(createTime) {
			var count = [];//套餐总数
			var sum = [];//套餐数量
			var weeks = [];//时间

			$.ajax({
				url : packageEportForm.appPath + "/eportForm/eportFormPackageListDay.do",
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
					 
					

					var myChart = echarts.init(document.getElementById('packageEportFormNs'));
					var option = {
			                title: {
			                    text: '套餐赠送统计'
			                },
			                tooltip: {
			                    trigger: 'axis'
			                },
			                color: [
			                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
			                ],
			                legend: {
			                    data:['套餐赠送数量（个）','赠送额统计（元）']
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
			                        name:'套餐赠送数量（个）',
			                        type:'line',
			                        stack: '总量',
			                        data:count
			                    },
			                    {
			                        name:'赠送额统计（元）',
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
					url : packageEportForm.appPath + "/eportForm/eportFormPackageListMonth.do",
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
						 
						

						var myChart = echarts.init(document.getElementById('packageEportFormNs'));
						var option = {
				                title: {
				                    text: '套餐赠送统计'
				                },
				                tooltip: {
				                    trigger: 'axis'
				                },
				                color: [
				                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
				                ],
				                legend: {
				                    data:['套餐赠送数量（个）','赠送额统计（元）']
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
				                        name:'套餐赠送数量（个）',
				                        type:'line',
				                        stack: '总量',
				                        data:count
				                    },
				                    {
				                        name:'赠送额统计（元）',
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
				url : packageEportForm.appPath + "/eportForm/reportFormPricingPack.do",
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
					 
					

					var myChart = echarts.init(document.getElementById('packageEportFormNs'));
					var option = {
			                title: {
			                    text: '套餐赠送统计'
			                },
			                tooltip: {
			                    trigger: 'axis'
			                },
			                color: [
			                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
			                ],
			                legend: {
			                    data:['套餐赠送数量（个）','赠送额统计（元）']
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
			                        name:'套餐赠送数量（个）',
			                        type:'line',
			                        stack: '总量',
			                        data:count
			                    },
			                    {
			                        name:'赠送额统计（元）',
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
				url : packageEportForm.appPath + "/eportForm/eportFormPackageListAll.do",
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
					 
					

					var myChart = echarts.init(document.getElementById('packageEportFormNs'));
					var option = {
			                title: {
			                    text: '套餐赠送统计'
			                },
			                tooltip: {
			                    trigger: 'axis'
			                },
			                color: [
			                '#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8','#c23531','#2f4554', '#61a0a8'
			                ],
			                legend: {
			                    data:['套餐赠送数量（个）','赠送额统计（元）']
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
			                        name:'套餐赠送数量（个）',
			                        type:'line',
			                        stack: '总量',
			                        data:count
			                    },
			                    {
			                        name:'赠送额统计（元）',
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
	return packageEportForm;
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
