define([], function() {
	var earningsTrendChart = {
		appPath : getPath("app"),
		init : function() {
			//页面查询
			$("#earningsSearch").click(function() {
				var form = $("form[name=earningsSerachForm]");
				var dataType = form.find("select[name=dataType]").val()
				if(dataType == "0"){
					earningsTrendChart.showEarningsTrendMonthChart();//查询月统计
				}else{
					earningsTrendChart.showEarningsTrendQuarterChart();//查询季度统计
				}
			});
			
			earningsTrendChart.showEarningsTrendMonthChart();   //加盟商收益趋势统计图表
			
		},
		//月统计
		showEarningsTrendMonthChart : function() {
			var form = $("form[name=earningsSerachForm]");
			var franchiseeName = form.find("input[name=franchiseeName]").val();
			$.ajax({
				url : earningsTrendChart.appPath + "/franchisee/franchProfitMonthStatistics.do",
				type : "post",
				data: {"franchiseeName":franchiseeName},  
				success : function(res) {
					if (res.code == "1") {
						var dateArray = res.data.dateArray;
						var orderAmountArray = res.data.orderAmountArray;
						var profitAmountArray = res.data.profitAmountArray;
						var carProfitArray = res.data.carProfitArray;
						var parkProfitArray = res.data.parkProfitArray;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('earningsTrend')); 
		                option = {
		                	    title : {
		                	        text: '加盟商收益趋势统计'
		                	    },
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	    	x:200,
		                	        data:['订单金额','分润额','车辆分润','场站分润']
		                	    },
		                	    toolbox: {
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : false,
		                	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
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
		                   	            name:'订单金额',
		                   	            type:'bar',
		                   	            data:orderAmountArray
		                   	        },
		                	        {
		                	            name:'分润额',
		                	            type:'bar',
		                	            data:profitAmountArray
		                	        },
		                	        {
		                	            name:'车辆分润',
		                	            type:'bar',
		                	            data:carProfitArray
		                	        },
		                	        {
		                	            name:'场站分润',
		                	            type:'bar',
		                	            data:parkProfitArray
		                	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},	
		
		//季度统计
		showEarningsTrendQuarterChart : function() {
			var form = $("form[name=earningsSerachForm]");
			var franchiseeName = form.find("input[name=franchiseeName]").val();
			$.ajax({
				url : earningsTrendChart.appPath + "/franchisee/franchProfitQuarterStatistics.do",
				type : "post",
				data : {"franchiseeName":franchiseeName}, 
				success : function(res) {
					if (res.code == "1") {
						var dateArray = res.data.dateArray;
						var orderAmountArray = res.data.orderAmountArray;
						var profitAmountArray = res.data.profitAmountArray;
						var carProfitArray = res.data.carProfitArray;
						var parkProfitArray = res.data.parkProfitArray;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('earningsTrend')); 
		                option = {
		                	    title : {
		                	        text: '加盟商收益趋势统计'
		                	    },
		                	    tooltip : {
		                	        trigger: 'axis'
		                	    },
		                	    legend: {
		                	    	x:200,
		                	        data:['订单金额','分润额','车辆分润','场站分润']
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
		                   	            name:'订单金额',
		                   	            type:'bar',
		                   	            data:orderAmountArray
		                   	        },
		                	        {
		                	            name:'分润额',
		                	            type:'bar',
		                	            data:profitAmountArray
		                	        },
		                	        {
		                	            name:'车辆分润',
		                	            type:'bar',
		                	            data:carProfitArray
		                	        },
		                	        {
		                	            name:'场站分润',
		                	            type:'bar',
		                	            data:parkProfitArray
		                	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},	
		
	}
	return earningsTrendChart;
});
