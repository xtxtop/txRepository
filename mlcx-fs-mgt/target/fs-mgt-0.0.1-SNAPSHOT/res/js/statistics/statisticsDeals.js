define([], function() {
	var statisticsDeals = {
		appPath : getPath("app"),
		init : function() {
			 //搜索
			 $("#statisticsSearch_btn_sub_order").click(function(){
				 statisticsDeals.statistics();
				 statisticsDeals.queryDealsYear();
			 });
			statisticsDeals.statistics();
			statisticsDeals.queryDealsYear();
		},
		queryDealsYear : function() {
			$.ajax({
				url : statisticsDeals.appPath+ "/statisticsDeals/queryDealsYear.do",
				success : function(data) {
					var years = eval(data);
					$("#statYear").html("");//先清空
					var date = new Date();
					var option = "";
    				for(var i=0;i<years.length;i++) {
    					if(date.getFullYear()==years[i]){
    						option += "<option value='"+years[i]+"' selected='selected'>"+years[i]+"</option>";
    					}
    				}
    				$("#statYear").html(option);
				}
			})
		},
		statistics : function() {
			var form =  $("form[name=statisticsSearchForm]");
			var statYear=  form.find("select[name='statYear']").val();
			var customerName =  $.trim(form.find("input[name='customerName']").val());
			$.ajax({
				url : statisticsDeals.appPath
						+ "/statisticsDeals/queryStatisticsPayReceivable.do",
				data:{statYear:statYear,customerName:customerName},
				success : function(data) {
					var statisticsList = eval(data);
					var arr1 = [0,0,0,0,0,0,0,0,0,0,0,0];// 应收金额
					var arr2 = [0,0,0,0,0,0,0,0,0,0,0,0];// 已收金额
					var arr3 = [0,0,0,0,0,0,0,0,0,0,0,0];// 应付金额
					var arr4 = [0,0,0,0,0,0,0,0,0,0,0,0];// 已付金额
					var arr5 = [0,0,0,0,0,0,0,0,0,0,0,0];// 月交易量
					var months = new Array();//月份
					var monthAll = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11','12'];
					if (statisticsList.length > 0) {
						for(var i=0;i<statisticsList.length;i++){
							for(var k=0;k<monthAll.length;k++){
								if(statisticsList[i].month1==monthAll[k]){
									arr1[k]=statisticsList[i].receivableAmount;
									arr2[k]=statisticsList[i].completeReceiv;
									arr3[k]=statisticsList[i].payableAmount;
									arr4[k]=statisticsList[i].realPaid;
									arr5[k]=statisticsList[i].monthDealNum;
								}
							}
						}
					}
					Highcharts.setOptions({
						global : {
							useUTC : false
						}
					});
					var chart;
					var options = {
						chart : {
							renderTo : 'container',
							type : 'column',
						},
						title : {
							text : '年度营业额'
						},
//						subtitle : {
//							text : '预测分析'
//						},
						xAxis : {
							categories : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May',
									'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov',
									'Dec' ]
						},
						yAxis : {
							min: 0,
							title : {
								text : '数值'
							}
						},
						legend : {
							layout : 'vertical',
							backgroundColor : '#FFFFFF',
							align : 'left',
							verticalAlign : 'top',
							x : 50,
							y : 20,
							floating : true,
							shadow : true
						},
						tooltip : {
							formatter : function() {
								return this.x + '</br>营业额：' + this.y+ ' ';
							}
						},
						credits : {
							enabled : false
						},
						plotOptions : {
							column : {
								pointPadding : 0.2,
								borderWidth : 0
							},
							line: {  
								dataGrouping: {  
					            enabled: false  
					        }  
					    }  
						},
						series : [ {
							name : '应收金额'
						}, {
							name : '已收金额'
						}, {
							name : '应付金额'
						}, {
							name : '已付金额'
						}, {
							name : '月交易量'
						} ]
					};
					options.series[0].data = arr1;
	                options.series[1].data = arr2;
	                options.series[2].data = arr3;
	                options.series[3].data = arr4;
	                options.series[4].data = arr5;
	                chart = new Highcharts.Chart(options);
				}
			})
		}
	};
	return statisticsDeals;
});
