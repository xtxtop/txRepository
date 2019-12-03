define([],function() {	
	var carDetail={
			appPath:getPath("app"),	
			init: function () {	
				//关闭车辆详情页
				$("#closecarDetail").click(function(){
					closeTab("车辆详情");
	            });
				
	            //数据列表
				carDetail.pageList();
				//查询
				$("#carDetailSearchafter").click(function(){
					carDetail.pageList();
	            });
				
			},
	
			
			//方法加载
	        operateColumEvent: function(){
			
	        },
			pageList:function () {	
				var carDetailTpl = $("#carDetailTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carDetailTpl);
				$('#carDetailList').dataTable({
				

				    scrollX:true,
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carDetail.appPath+'/carDetail/pageListCarDetail.do',
						"data": function ( d ) {
							var form = $("form[name=carDetailSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"deviceSn":$.trim(form.find("input[name=deviceSn]").val())
								
	        					
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
						{ "title":"终端序列号","data": "deviceSn"},
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"上报时间","data": "lastReportingTime" },
						{ "title":"车辆状态","data": "carSta" },
						{ "title":"车速","data": "speed" },
						{ "title":"累计里程","data": "mileage" },
						{ "title":"总电压","data": "totalVol" },
						{ "title":"总电量","data": "current" },
						{ "title":"SOC(剩余容量)","data": "soc" },
						{ "title":"DC-DC状态","data": "dcSta" },
						{ "title":"档位","data": "gear" },
						{ "title":"绝缘电阻","data": "resistance" },
						{ "title":"加速踏板行程","data": "accPedal" },
						{ "title":"制动踏板位置","data": "brakePedalPos" },
						{ "title":"电源档位","data": "elecSwitch" },
						{ "title":"车辆租赁模式","data": "leasingMode" },
						{ "title":"动力使能","data": "powEnable" },
						{ "title":"物理钥匙","data": "phyKey" },
						{ "title":"车门状态","data": "carDoor" },
						{ "title":"车窗状态","data": "carWindow" },
						{ "title":"中控锁状态","data": "cenctrlLock" },
						{ "title":"灯光状态","data": "light" },
						{ "title":"手刹状态","data": "handbrake" },
						{ "title":"蓄电池电压","data": "auxBatteryVol" },
						{ "title":"充电枪状态","data": "chargingGun" },
						{ "title":"定位是否有效","data": "locStatus" },
						{ "title":"GPS 里程","data": "gpsMileage" },
						{ "title":"GPS 速度","data": "gpsSpeed" },
						{ "title":"GPS 方向角","data": "gpsAngle" },
						{ "title":"终端定位星数","data": "locSateNum" },
						{ "title":"通信信号强度","data": "signalStrength" },
						
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carDetailtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carDetailTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carDetailTools").html("");
//					   $("#carDetailTools").css("padding-left:", "48px");
					   $("#carDetailTools").removeClass("col-xs-6");
		       			
		       			
		       		
		
					},
					"drawCallback": function( settings ) {
						carDetail.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets": [2],
							"render": function (a, b, c, d) {
								var now = moment(a);
								return now.format('YYYY-MM-DD HH:mm:ss');
	        					
							}
						},
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#morecarDetailList").click(function(){
					if($(".seach-input-details").hasClass("close-content")){
						$(this).html("<div class='pull-right moreButtonNew' id='morecarDetailStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
						$(".seach-input-details").removeClass("close-content");
					}else{
						$(this).html("<div class='pull-right moreButtonNew' id='morecarDetailStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
						$(".seach-input-details").addClass("close-content");
					}
				})
			}
	    };
	return carDetail;
});


