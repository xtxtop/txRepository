$(function () {
	debugger
	
	$.post("sysStatistics/sysStatisticsCtAs.do",function(data)
			{
		var arr1 = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
		var mtimeAll = ['00','01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11','12','13','14','15','16','17','18','19','20','21','22','23'];
		for (var i = 0; i < data.length; i++) {
		for (var k = 0; k < mtimeAll.length; k++) {
			if(data[i].time==mtimeAll[k]){
				arr1[k]=(data[i].count);
			}
			
		}	
			
		}
		
		 $('#sysStatistics').highcharts({
		        chart: {
		            type: 'line'
		        },
		        title: {
		            text: '平台用户统计曲线图'
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		            categories: ['00时','01时','02时','03时','04时','05时','06时', '07时', '08时', '09时', '10时', '11时','12时','13时','14时','15时','16时','17时','18时','19时','20时','21时','22时','23时']
		        },
		        yAxis: {
		            title: {
		                text: ''
		            }
		        },
		        tooltip: {
		            enabled: false,
		            formatter: function() {
		                return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C';
		            }
		        },
		        plotOptions: {
		            line: {
		                dataLabels: {
		                    enabled: true
		                },
		                enableMouseTracking: false
		            }
		        },
		        series: [{
		            name: 'Tokyo',
		            data: arr1
		        },/* {
		            name: 'London',
		            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
		        }*/]
		    });
		
			});
	
   
});
