define([],function() {	
	var orderOperateCount={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				orderOperateCount.pageList();
				orderOperateCount.pageWeekList();
				orderOperateCount.pageMonthList(); 
			 
				// 按天搜索调用
				$("#orderSearchDay").click(function() {
					var form = $("form[name=orderSerachDayForm]");
					var startTimeDay =  form.find("input[name=startTimeDay]").val();
					var endTimeDay = form.find("input[name=endTimeDay]").val();
					if(startTimeDay!=""&&endTimeDay!=""){
						if(new Date(startTimeDay)>new Date(endTimeDay)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderOperateCount.pageList();
						}
					}else{
						orderOperateCount.pageList();
					}
				});
				

				// 按周搜索调用
				$("#orderSearchWeek").click(function() {
					var form = $("form[name=orderSerachWeekForm]");
					var startTimeWeek =  form.find("input[name=startTimeWeek]").val();
					var endTimeWeek = form.find("input[name=endTimeWeek]").val();
					if(startTimeWeek!=""&&endTimeWeek!=""){
						if(new Date(startTimeWeek)>new Date(endTimeWeek)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderOperateCount.pageWeekList();
						}
					}else{
						orderOperateCount.pageWeekList();
					}
				});
				

				// 按月搜索调用
				$("#orderSearchMonth").click(function() {
					var form = $("form[name=orderSerachMonthForm]");
					var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
					var endTimeMonth = form.find("input[name=endTimeMonth]").val();
					if(startTimeMonth!=""&&endTimeMonth!=""){
						if(new Date(startTimeMonth)>new Date(endTimeMonth)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderOperateCount.pageMonthList();
						}
					}else{
						orderOperateCount.pageMonthList();
					}
				});
				

				// 按年搜索调用
				$("#orderSearchYear").click(function() {
					var form = $("form[name=orderSerachYearForm]");
					var startTimeYear =  form.find("input[name=startTimeYear]").val();
					var endTimeYear = form.find("input[name=endTimeYear]").val();
					if(startTimeYear!=""&&endTimeYear!=""){
						if(new Date(startTimeYear)>new Date(endTimeYear)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							orderOperateCount.pageYearList();
						}
					}else{
						orderOperateCount.pageYearList();
					}
				});
				

				//打开选择场站页面-日
				$("#orderOpenBMDay").click(function(){
					orderOperateCount.orderOpenBMDay();
	            });
				
				//选择场站提交-日
				$("#orderOperateCountDayBtn").click(function(){
					orderOperateCount.orderbmEdit();
	            });
				

				//打开选择场站页面-周
				$("#orderOpenBMWeek").click(function(){
					orderOperateCount.orderOpenBMWeek();
	            });
				
				//选择场站提交-周
				$("#orderOperateCountWeekBtn").click(function(){
					orderOperateCount.orderbmWeekEdit();
	            });
				

				//打开选择场站页面-月
				$("#orderOpenBMMonth").click(function(){
					orderOperateCount.orderOpenBMMonth();
	            });
				
				//选择场站提交-月
				$("#orderOperateCountMonthBtn").click(function(){
					orderOperateCount.orderbmMonthEdit();
	            });
				
				
			},

			orderbmMonthEdit:function () {

				 var bb = "";  
			     var temp = "";  
			     var a = document.getElementsByName("parkNameMonth");  
			     for ( var i = 0; i < a.length; i++) {  
				     if (a[i].checked) {  
					     temp = a[i].value;  
					     bb = bb + "," +temp;  
				     }  
			     } 
			     document.getElementById("parkNameMonthTempString").value = bb.substring(1, bb.length); 

				 orderOperateCount.pageMonthList();
					
				$("#orderOperateCountMonthModal").modal("hide");
				
			},
			orderbmWeekEdit:function () {

				 var bb = "";  
			     var temp = "";  
			     var a = document.getElementsByName("parkNameWeek");  
			     for ( var i = 0; i < a.length; i++) {  
				     if (a[i].checked) {  
					     temp = a[i].value;  
					     bb = bb + "," +temp;  
				     }  
			     } 
			     document.getElementById("parkNameWeekTempString").value = bb.substring(1, bb.length); 

				 orderOperateCount.pageWeekList();
					
				$("#orderOperateCountWeekModal").modal("hide");
				
			},
			orderbmEdit:function () {

				 var bb = "";  
			     var temp = "";  
			     var a = document.getElementsByName("parkName");  
			     for ( var i = 0; i < a.length; i++) {  
				     if (a[i].checked) {  
					     temp = a[i].value;  
					     bb = bb + "," +temp;  
				     }  
			     } 
			     document.getElementById("parkNameTempString").value = bb.substring(1, bb.length); 

				 orderOperateCount.pageList();
					
				$("#orderOperateCountDayModal").modal("hide");
				
			},
			orderOpenBMDay:function () {
				$("#orderOperateCountDayModal").modal("show");
			},
			orderOpenBMWeek:function () {
				$("#orderOperateCountWeekModal").modal("show");
			},
			orderOpenBMMonth:function () {
				$("#orderOperateCountMonthModal").modal("show");
			},
			pageList:function () {
 
				var form = $("form[name=orderSerachDayForm]");
				var startTimeDay =  form.find("input[name=startTimeDay]").val();
				var endTimeDay = form.find("input[name=endTimeDay]").val();
				var parkNameTempString = form.find("input[name=parkNameTempString]").val();
				
				$.ajax({
					url:orderOperateCount.appPath+"/order/dayOrderOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeDay,endTime:endTimeDay,parkNameTempString:parkNameTempString},
					success:function(res){
						var resData = JSON.parse(res.data.option);
						var resData2 = JSON.parse(res.data.option2);
						var resData3 = JSON.parse(res.data.optionMoney);
						var resData4 = JSON.parse(res.data.optionMoney2);
						 
						var myChart = echarts.init(document.getElementById('orderOperateCountDay'));
						var option = resData;
						myChart.setOption(option);

						var myChart2 = echarts.init(document.getElementById('orderOperateDayCount'));
						var option2 = resData2;
						myChart2.setOption(option2);

						var myChart3 = echarts.init(document.getElementById('orderOperateJECountDay'));
						var option3 = resData3;
						myChart3.setOption(option3);

						var myChart4 = echarts.init(document.getElementById('orderOperateJEDayCount'));
						var option4 = resData4;
						myChart4.setOption(option4);
							
					}
			});
				
			},
			pageWeekList:function () {
 
				var form = $("form[name=orderSerachWeekForm]");
				var startTimeWeek =  form.find("input[name=startTimeWeek]").val();
				var endTimeWeek = form.find("input[name=endTimeWeek]").val();
				var parkNameTempString = form.find("input[name=parkNameWeekTempString]").val();
				
				$.ajax({
					url:orderOperateCount.appPath+"/order/weekOrderOperateCount.do",
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeWeek,endTime:endTimeWeek,parkNameTempString:parkNameTempString},
					success:function(res){

						var resData = JSON.parse(res.data.option);
						var resData2 = JSON.parse(res.data.option2);
						var resData3 = JSON.parse(res.data.optionMoney);
						var resData4 = JSON.parse(res.data.optionMoney2);
						 
						var myChart = echarts.init(document.getElementById('orderOperateCountWeek'));
						var option = resData;
						myChart.setOption(option);

						var myChart2 = echarts.init(document.getElementById('orderOperateWeekCount'));
						var option2 = resData2;
						myChart2.setOption(option2);

						var myChart3 = echarts.init(document.getElementById('orderOperateJECountWeek'));
						var option3 = resData3;
						myChart3.setOption(option3);

						var myChart4 = echarts.init(document.getElementById('orderOperateJEWeekCount'));
						var option4 = resData4;
						myChart4.setOption(option4);
							
					}
			});
				
			},
			pageMonthList:function () {
 
				var form = $("form[name=orderSerachMonthForm]");
				var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
				var endTimeMonth = form.find("input[name=endTimeMonth]").val();
				var parkNameTempString = form.find("input[name=parkNameMonthTempString]").val();
				
				$.ajax({
					url:orderOperateCount.appPath+"/order/monthOrderOperateCount.do", 
					type : "post",
					dataType:"json", //数据类型  
					data:{startTime:startTimeMonth,endTime:endTimeMonth,parkNameTempString:parkNameTempString},
					success:function(res){

						var resData = JSON.parse(res.data.option);
						var resData2 = JSON.parse(res.data.option2);
						var resData3 = JSON.parse(res.data.optionMoney);
						var resData4 = JSON.parse(res.data.optionMoney2);
						 
						var myChart = echarts.init(document.getElementById('orderOperateCountMonth'));
						var option = resData;
						myChart.setOption(option);

						var myChart2 = echarts.init(document.getElementById('orderOperateMonthCount'));
						var option2 = resData2;
						myChart2.setOption(option2);

						var myChart3 = echarts.init(document.getElementById('orderOperateJECountMonth'));
						var option3 = resData3;
						myChart3.setOption(option3);

						var myChart4 = echarts.init(document.getElementById('orderOperateJEMonthCount'));
						var option4 = resData4;
						myChart4.setOption(option4);
							
					}
			});
				
			},
	        
	    };
	return orderOperateCount;
});


