var allMarks = new Object();
var lastCarObject = new Object();
var globalCarNos = [];
var globalCarList = [];
var globalMarkerPoint = new Object();
var polygonBMaps = new Object();//场站覆盖物
var globalPoints = [];
var globalResult;
define(
		[],
		function() {
			var carMonitor = {
				appPath : getPath("app"),
				init : function() {
					var mapHeight = $('#mainContentHeight').val();
					$('#car-map').css({"width":"100%","height":mapHeight-4+'px'});
					$(window).resize(function () {
						var mapHeight = $('#mainContentHeight').val();
						$('#car-map').css({"width":"100%","height":mapHeight-4+'px'});
					})
					// 列表页面搜索调用
					$("#carMonitorSearch").click(function() {
						carMonitor.pageList("clickFlag");
						var form = $("form[name=carMonitorSerachForm]");
						var carPlateNo = encodeURI($.trim(form.find("input[name=carPlateNo]").val()));
						$(".baidu-map-button").removeClass('active');
						$("#btnAll").removeClass('resize-btnAll');
						$(".car-info").removeClass("show-car-info");
						$("#car-map-parent-div").css("width","100%");
						
					});
					carMonitor.inputChange();
					//carMonitor.removeMarkerLabel(allMarks);
					//carMonitor.showMarkLabel(globalCarList, allMarks);
					carMonitor.mapInit();
//					carMonitor.parkList();
					carMonitor.pageList();
					carMonitor.connect();
					
					$(".baidu-map-button.bg1").click(function(){
						var $this = $(this);
						carMonitor.carClassify(0,globalResult,function(globalCarList,globalPoints){	
							carMonitor.showMap(globalCarList,globalPoints);
							$this.addClass('active').siblings('.baidu-map-button').removeClass('active');
							$(".car-info").removeClass("show-car-info");
							$("#car-map-parent-div").css("width","100%");
							$("#btnAll").removeClass('resize-btnAll');
						})
		            });
					$(".baidu-map-button.bg2").click(function(){
						var $this = $(this);
						carMonitor.carClassify(3,globalResult,function(globalCarList,globalPoints){
							carMonitor.showMap(globalCarList,globalPoints);
							$this.addClass('active').siblings('.baidu-map-button').removeClass('active');
							$(".car-info").removeClass("show-car-info");
							$("#car-map-parent-div").css("width","100%");
							$("#btnAll").removeClass('resize-btnAll');
						})
		            });
				
					$(".baidu-map-button.bg3").click(function(){
						var $this = $(this);
						carMonitor.carClassify(2,globalResult,function(globalCarList,globalPoints){
							carMonitor.showMap(globalCarList,globalPoints);
							$this.addClass('active').siblings('.baidu-map-button').removeClass('active');
							$(".car-info").removeClass("show-car-info");
							$("#car-map-parent-div").css("width","100%");
							$("#btnAll").removeClass('resize-btnAll');
						})
		            });
					$(".baidu-map-button.bg4").click(function(){
						var $this = $(this);
						carMonitor.carClassify(1,globalResult,function(globalCarList,globalPoints){
							carMonitor.showMap(globalCarList,globalPoints);
							$this.addClass('active').siblings('.baidu-map-button').removeClass('active');
							$(".car-info").removeClass("show-car-info");
							$("#car-map-parent-div").css("width","100%");
							$("#btnAll").removeClass('resize-btnAll');
						})
		            });
				},
				inputChange:function(){
//					inputFun('inputCarPlateNo',function(carpateNo){
//						//第一步 需要正则校验车牌号  
//						//第一步通过后，走代码，否则 返回
//						//第三部  根据异步效应的数据去修改select值即可
////						
//					})
					
				
					$('#inputCarPlateNo').on('change',function(){
						var plateNoData = $(this).val();
						var rep = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
						var isTrue = rep.test(plateNoData);
						if(isTrue){
							//动态修改select
							var selectEle = $('#select');
							var childEles = selectEle.children();
							//类数组对象 转换 为数组
							var optionArr = Array.prototype.slice.call(childEles,0);
//							for(var index in optionArr){
//								var optionHtml = optionArr[index].innerHTML;
//								if(optionHtml==cityName){
//									//去改变select
//								}
//							}
							
						}else{
							
						}

					});

					
				},
				pageList : function(str) {
					var form = $("form[name=carMonitorSerachForm]");
					var carPlateNo = form.find("input[name='carPlateNo']").val();
					var flag = form.find("input[name='flag']").val();
					var userageStatus = form.find("input[name='userageStatus']").val();
					var cityId = form.find("select[name='cityId']").val();
					
					form.ajaxSubmit({
						url : carMonitor.appPath + "/carMonitor/getCarMonitorList.do",
						type : "post",
						success : function(res) {
							if (res != "") {
								if(cityId){
									resizeCarData(cityId,res,function(data){
										var carStatusList = [],points=[];
										for(var index in data){
												carStatusList.push(data[index]);
												points.push({lat:data[index].latitude,lng:data[index].longitude});
										}
										globalPoints = points;
										globalCarList=carStatusList;
										carMonitor.showMap(carStatusList,points);
									})
								}else{

									var carStatusList = [],points=[];
									for(var index in res){
										carStatusList.push(res[index]);
										points.push({lat:res[index].latitude,lng:res[index].longitude});
									}
									globalPoints = points;
									carMonitor.showMap(carStatusList,points);
									
									globalCarList=carStatusList;
										
								}
								globalResult = res;
								var data = carMonitor.handleCarStatus(res);	
								$("#kxCarNum").text(data.kxCar.length);
								$("#ddzCarNum").text(data.ddzCar.length);
								$("#dingdzCarNum").text(data.dingdzCar.length);
								$("#yjzCarNum").text(data.yjzCar.length);
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无满足查询条件的车辆！");
							}
					
							
						}
					});
					
					
					//选择城市过滤对应数据
					function resizeCarData(cityId,data,callback){
						
						var arr = [];
						for(var index in data){
							if(data[index].cityId == cityId){
								arr.push(data[index])
							}
						}
						if(callback){
							callback(arr);
						}
						
					}
				},
				//处理车辆分类信息
				
				carClassify:function(status,dataList,callback){
					
					var carStatusList = [],points=[];
					for(var index in dataList){
						var statusNum = dataList[index].userageStatus;
						if(statusNum==status){
							carStatusList.push(dataList[index]);
							points.push({lat:dataList[index].latitude,lng:dataList[index].longitude})
						}else{}
						
					}
					
					if(callback){
						callback(carStatusList,points);
					}
					
				},
				
				
				carInfo : function(carPlateNo) {
					
					var form = $("form[name=carMonitorSerachForm]");
					var carPlateNo = form.find("input[name='carPlateNo']").val(carPlateNo);
					
					//动态改变select
//					form.find("input[name='carPlateNo']").data("cityname",'');
					
					form.ajaxSubmit({
						url : carMonitor.appPath + "/carMonitor/getCarMonitorInfo.do",
						type : "post",
						success : function(res) {
							if (res != "") {
								var memberName = res.memberName==null?"":res.memberName;
								var documentNo = res.documentNo==null?"":res.documentNo;
								var lng = "未知";
								if(res.longitude!=null){
									lng = res.longitude.toFixed(6);
								}	
								var lat = "未知";
								if(res.latitude!=null){
									lat = res.latitude.toFixed(6);
								}
								var useStatus = "空闲";
								if (res.userageStatus==1) {
									useStatus = "已预占";
								}else if(res.userageStatus==2){
									useStatus = "订单中";
								}else if(res.userageStatus==3){
									useStatus = "调度中";
								}
								var power = "未知";
							    if (res.power!=null) {
							    	power = res.power;
								}
							    if(res.userageStatus!=0){
							    	var row3 = "<div class='text-infro-content'><h2 class='title'>车辆使用人</h2><div class='details'>"+memberName+"</div></div>";
							    	var row4 = "<div class='text-infro-content'><h2 class='title'>单据号</h2><div class='details'>"+documentNo+"</div></div>";
							    }
								var item = "<div class='head-infro'><div class='left'>"+res.carPlateNo+"</div>";
								var row0 = "<div class='right'>"+res.loc_time+"</div></div>";
								var row1 = "<div class='echart-content' id='carMonotorSpeedCharts'><div></div><h1></h1></div>";
								var row2 = "<div class='text-infro-content'><h2 class='title'>车辆状态</h2><div class='details'>"+useStatus+"</div></div>";
								var row5 = "<div class='text-infro-content'><h2 class='title'>车辆经纬度</h2><div class='details'>"+lng+","+lat+"</div></div>"
								var row6 = "<div class='text-infro-content'><h2 class='title'>剩余电/油量</h2><div class='details'>"+power+"</div></div>"
								var row7 = "<div class='text-infro-content'><h2 class='title'>位置</h2><div class='details'>"+res.address+"</div></div>"
								var html = item+row0+row1+row5+row6+row7;
								if(res.userageStatus!=0){
									html = item+row0+row1+row3+row4+row5+row6+row7;
								}
								$(".car-info").empty();
								$(".car-info").append(html);
								$("#car-map-parent-div").css("width","84%");
								$(".car-info").addClass("show-car-info");
								$("#btnAll").addClass('resize-btnAll');
								
								carMonitor.renderSpeedChartsForCarMonitor({
									eleId:'carMonotorSpeedCharts',
									chartsTitle:'行驶速度',
									height:200,
									maxValue:240,
									initStr:'km/h',
									showValue:res.speed
								});
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无满足查询条件的车辆！");
							}
						}
					});
					
				},
				renderSpeedChartsForCarMonitor:function (configObj){
					var chartsContent = document.getElementById(configObj.eleId),
						chartsEle = chartsContent.children[0],
						chartsTitle = chartsContent.children[1];
						chartsContent.style.paddingBottom = '1px';
						chartsEle.style.height = configObj.height+'px';
						chartsEle.style.paddingTop = '10px';
						chartsTitle.innerHTML = configObj.chartsTitle;
						chartsTitle.style.color = '#fff';
						chartsTitle.style.textAlign = 'center';
						chartsTitle.style.fontWeight = 'normal';
						chartsTitle.style.fontSize = '14px';
						chartsTitle.style.marginTop = '-20px';
						chartsTitle.style.marginBottom = '10px';
					// 基于准备好的dom，初始化echarts实例
			        var myChart = echarts.init(chartsEle);
			        // 指定图表的配置项和数据
			        var option = {
			            tooltip : {
					        formatter: "{a} <br/>{b} : {c}%"
						    },
//						    toolbox: {
//						        feature: {
//						            restore: {},
//						            saveAsImage: {}
//						        }
//						    },
						    series: [
								{
						            name:'速度',
						            type:'gauge',
						            min:0,
						            max:configObj.maxValue,
						            splitNumber:configObj.maxValue/40,
						            radius: '90%',
						            //圆环的大小
						            axisLine: {            // 坐标轴线
						                lineStyle: {       // 属性lineStyle控制线条样式
						                    color: [[0.09, 'lime'],[0.82, '#1e90ff'],[1, '#ff4500']],
						                    width: 2,
						                    shadowColor : '#fff', //默认透明
//						                    shadowBlur: 10
						                }
						            },
						            //数字的大小
						            axisLabel: {            // 坐标轴小标记
						                textStyle: {
						                	fontSize: 12,
//						                    fontWeight: 'bolder',
						                    color: '#fff',
						                    shadowColor : '#fff',//默认透明
//						                    shadowBlur: 10
						                }
						            },
						            //坐标轴小标记
						            axisTick: {            // 坐标轴小标记
						                length :8,        // 属性length控制线长
						                lineStyle: {       // 属性lineStyle控制线条样式
						                    color: 'auto',
						                    shadowColor : '#fff', //默认透明
//						                    shadowBlur: 10
						                }
						            },
						            //坐标轴大标记
						            splitLine: {           // 分隔线
						                length :12,         // 属性length控制线长
						                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
						                    width:2,
						                    color: '#fff',
						                    shadowColor : '#fff', //默认透明
//						                    shadowBlur: 10
						                }
						            },
						            //标题
						            title : {
						                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						                    fontWeight: 'bolder',
						                    fontSize: 16,
//						                    fontStyle: 'italic',
						                    color: '#fff',
						                    shadowColor : '#fff', //默认透明
//						                    shadowBlur: 10
						                }
						            },
						            //底部数字
						            detail : {
//						                backgroundColor: 'rgba(30,144,255,0.8)',
//						                borderWidth: 1,
//						                borderColor: '#fff',
//						                shadowColor : '#fff', //默认透明
////						                shadowBlur: 5,
						                offsetCenter: [0, '50%'],     // x, y，单位px
						                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
//						                    fontWeight: 'bolder',
						                    color: '#fff',
						                    fontSize: 12,
						                }
						            },
						            data:[{value: configObj.showValue?configObj.showValue.toFixed(2):0.00, name: configObj.initStr}]
						        }
						    ]
				        };

			        // 使用刚指定的配置项和数据显示图表。
			        myChart.setOption(option);
				},
			
				//处理车辆对应状态
				handleCarStatus:function(arr){
					
					var carStatusObj = {
							kxCar:{
								data:[],
								length:0
							},
							yjzCar:{
								data:[],
								length:0
							},
							ddzCar:{
								data:[],
								length:0
							},
							dingdzCar:{
								data:[],
								length:0
							}
					};
					
					for(var index in arr){
						if(arr[index].userageStatus==0){
							carStatusObj.kxCar.data.push(arr[index]);
							carStatusObj.kxCar.length++;
						}else if(arr[index].userageStatus==1){
							carStatusObj.yjzCar.data.push(arr[index]);
							carStatusObj.yjzCar.length++;
						}else if(arr[index].userageStatus==2){
							carStatusObj.dingdzCar.data.push(arr[index]);
							carStatusObj.dingdzCar.length++;
						}else{
							carStatusObj.ddzCar.data.push(arr[index]);
							carStatusObj.ddzCar.length++;
						}
					}
					
					return carStatusObj;
				},
				parkList:function(){
					$.ajax({
						 type: "post",
			             url: carMonitor.appPath+"/carMonitor/pageListPark.do",
			             data: {parkName:''},
			             success: function(res){
			            	if(''!=res){
			            		var parkArr = eval(res);
			            		for (var i = 0; i < parkArr.length; i++) {
			            			var park = parkArr[i];
			            			var parkNo=park.parkNo;
			            			var parkName=park.parkName;
			            			var parkpoints = park.ploygonPoints;
			            			if(parkpoints ==''){
			            				continue;			            				
			            			}
			            			
			            			var parkointStrArry = parkpoints.split(",");
			            			var pointArray = new Array();
			            			var txtPoint=null;
			            			for (var j = 0; j < parkointStrArry.length; j++) {
			            				var pointStr = parkointStrArry[j];
			            				var pointLA = pointStr.split(" ");
			            				var Lstr = $.trim(pointLA[0]);
			            				var Astr = $.trim(pointLA[1]);
			            				var pointLBMap = new BMap.Point(eval(Lstr),eval(Astr));
			            				txtPoint = pointLBMap;
			            				pointArray.push(pointLBMap);
			            			}
			            			//场站
			            			var polygon = new BMap.Polygon(pointArray,{strokeColor:"blue",strokeWeight:1.5,strokeOpacity:0.5,fillOpacity:0.05});
			            			var park_label_opts = {position : txtPoint,offset:new BMap.Size(30, -30)}
			            			var park_label = new BMap.Label(park.parkName,park_label_opts);
									park_label.setStyle({color : "red",fontSize : "14px",height : "20px",border:"none",background:"none",lineHeight : "20px",fontFamily:"微软雅黑"});
			            			
			            			var polygonBMap=new Object();
			            			polygonBMap["polygon"]=polygon;
			            			polygonBMap["park_label"]=park_label;
			            			polygonBMaps[park.parkNo]=polygonBMap;
			            		}
			            	}
			             }
					});
				},
				showMap : function(carStatusList, points) {
					
					var map = new BMap.Map("car-map"); // 创建地图实例
					map.clearOverlays();
					var point = new BMap.Point(116.400244,39.92556);
					globalMap = map;
					var zoom = carMonitor.setZoom(points, map);// 初始化地图，设置中心点坐标和地图级别
					map.centerAndZoom(new BMap.Point(zoom.cenLng, zoom.cenLat),zoom.zoom);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM,offset: new BMap.Size(30, 90)};
					map.addControl(new BMap.NavigationControl(opts)); // 添加默认缩放平移控件
//					设置控件  全屏 退出全屏 关闭和开启车牌号  隐藏和显示信息栏     ----wz
					//设置地图全屏按钮和退出全屏按钮
					// 创建全屏地图控件实例  
//					var myZoomCtrl = new ZoomControl();  
//					// 添加到地图当中  
//					map.addControl(myZoomCtrl);
//					// 创建退出全屏控件实例 
//					var escFullScreenCtrl = new ZoomControl1();  
//					// 添加到地图当中  
//					map.addControl(escFullScreenCtrl);
					
					carMonitor.addMarker(carStatusList,map);
//					
//					var showparklabel = new ZoomControl4();  
//					map.addControl(showparklabel);
					
					//画场站和场站名称
					for(var key in polygonBMaps){
						var my_polygon =polygonBMaps[key]["polygon"];
						var my_park_label=polygonBMaps[key]["park_label"];
						polygonBMaps[key]["is_show"]=1;
						map.addOverlay(my_polygon);
						map.addOverlay(my_park_label);
					}
					//画车辆
					map.setViewport(points);
				},
				connect:function (){
					var fsMgtHost = $("#appPath").val();
					var host = fsMgtHost.substring(fsMgtHost.indexOf("//")+2);
				    var webSocket = new WebSocket("ws://"+host+"/webSocketServer.do");
				    webSocket.onopen = function(event) {  
				        console.log("WebSocket:已连接");  
				        console.log(event);  
				        sendMessage();
				    };  
				    webSocket.onmessage = function(event) {  
				        var msgObject = event.data;  
				        console.log("WebSocket:收到一条消息-norm", msgObject);  
//				        alert("WebSocket:收到一条消息"+msgObject);
				        var carMonitorObj = JSON.parse(msgObject);
				        var carMark = allMarks[carMonitorObj.carNo];
				        carMonitor.setCarWindowInfo(globalMap, carMonitorObj, carMark);
				        //更新全局point
				        var point = new Object();
				        point.lng = carMonitorObj.longitude;
						point.lat = carMonitorObj.latitude;
						globalMarkerPoint[carMonitorObj.carNo] = point;
				        //熄火状态 1、已启动，2、已熄火
				        var carStatus = carMonitorObj.carStatus;
				        //坐标上报时间间隔
				        var timeDiff = carMonitorObj.timeDiff;
				        var lastCarObj = lastCarObject[carMonitorObj.carNo];
				        if(carStatus==1){
				        	var pts = [];
				        	var p2 = new BMap.Point(carMonitorObj.longitude,carMonitorObj.latitude);
				        	if(timeDiff<=60){
				        		pts.push(p2);
				        		carMonitor.run(pts,carMonitorObj.carPlateNo,carMark,carMonitorObj.courseAngle);
				        	}else if(timeDiff>60&&timeDiff<120){
				        		var p1 = new BMap.Point(lastCarObj.longitude,lastCarObj.latitude);
				        		carMonitor.routeCompensation(carMonitorObj.carPlateNo, carMark, carMonitorObj.courseAngle, p1, p2,timeDiff);
				        	}else if(timeDiff>=900){//15分钟没有上传新坐标，直接移动过去
				        		pts.push(p2);
				        		carMonitor.run(pts,carMonitorObj.carPlateNo,carMark,carMonitorObj.courseAngle);
				        	}
				        }else if(carStatus==2){
				        	var pts = [];
				        	var p2 = new BMap.Point(carMonitorObj.longitude,carMonitorObj.latitude);
			        		pts.push(p2);
			        		carMonitor.run(pts,carMonitorObj.carPlateNo,carMark,carMonitorObj.courseAngle);
				        }
				        
				    };  
				    webSocket.onerror = function(event) {  
				        console.log("WebSocket:发生错误 ");  
				        console.log(event);  
				    };  
				    webSocket.onclose = function(event) {  
				        console.log("WebSocket:已关闭");  
				        console.log(event);  
				    }  
				    function sendMessage() {
				    	webSocket.send("carMonitor");
				    }
				    
				},
				// 根据原始数据计算中心坐标和缩放级别，并为地图设置中心坐标和缩放级别。
				setZoom : function(points, map) {
					var obj = new Object();
					if (points.length > 0) {
						var maxLng = points[0].lng;
						var minLng = points[0].lng;
						var maxLat = points[0].lat;
						var minLat = points[0].lat;
						var res;
						for (var i = points.length - 1; i >= 0; i--) {
							res = points[i];
							if (res.lng > maxLng)
								maxLng = res.lng;
							if (res.lng < minLng)
								minLng = res.lng;
							if (res.lat > maxLat)
								maxLat = res.lat;
							if (res.lat < minLat)
								minLat = res.lat;
						};
						var cenLng = (parseFloat(maxLng) + parseFloat(minLng)) / 2;
						var cenLat = (parseFloat(maxLat) + parseFloat(minLat)) / 2;
						var zoom = carMonitor.getZoom(maxLng, minLng, maxLat,minLat, map);
						obj.cenLng = cenLng;
						obj.cenLat = cenLat;
						obj.zoom = zoom;
						return obj;
					} else {
						obj.cenLng = "103.388611";
						obj.cenLat = "35.563611";
						obj.zoom = "5";
						// 没有坐标，显示全中国
						return obj;
					}
				},
				// 根据经纬极值计算绽放级别。
				getZoom : function(maxLng, minLng, maxLat, minLat, map) {
					var zoom = [ "50", "100", "200", "500", "1000", "2000",
							"5000", "10000", "20000", "25000", "50000",
							"100000", "200000", "500000", "1000000", "2000000" ]// 级别18到3。
					var pointA = new BMap.Point(maxLng, maxLat); // 创建点坐标A
					var pointB = new BMap.Point(minLng, minLat); // 创建点坐标B
					var distance = map.getDistance(pointA, pointB).toFixed(1); // 获取两点距离,保留小数点后两位
					for (var i = 0, zoomLen = zoom.length; i < zoomLen; i++) {
						if (zoom[i] - distance > 0) {
							return 18 - i + 3;// 之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
						}
					}
					;
				},
				mapInit : function() {
					var map = new BMap.Map("car-map"); // 创建地图实例
					globalMap = map;
					map.centerAndZoom(new BMap.Point(114.093277, 22.55212), 6);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
				},
				
			
				//小车的运动过程
				 carRun:function(pts,carPlateNo,carMark,rotation,steps) {
					 
						var map = globalMap; // 创建地图实例
						var paths = pts.length; // 获得有几个点
						var label = new BMap.Label(carPlateNo, {
							offset : new BMap.Size(20, -10)
						});
						// 给label设置样式，任意的CSS都是可以的
						label.setStyle({
							color : "red", // 颜色
							fontSize : "14px", // 字号
							border : "0", // 边
							// height:"120px", //高度
							// width:"125px", //宽
							textAlign : "center", // 文字水平居中显示
							// lineHeight:"120px", //行高，文字垂直居中显示
							// background:"url()", //背景图片
							cursor : "pointer"
						});
						carMark.setLabel(label);
						i = 0;
						function resetMkPoint(i) {
							if (i < paths - 1) {
								//console.log(carMonitor.findRotation(pts[i],pts[i + 1]));
								// var rotationed=carMk.getRotation();//该点已经旋转的角度
								carMark.setRotation(carMonitor.findRotation(pts[i],pts[i + 1]));// 设置旋转角度
							}
							carMark.setPosition(pts[i]);
							if (i < paths) {
								setTimeout(function() {
									i++;
									resetMkPoint(i);
								}, 1000*steps);// 修改时间间隔1000，能控制小车跑完全程所用的时间。
							}
						}
						setTimeout(function() {
							carMark.setRotation(rotation);
							resetMkPoint(0);
						}, 1000) // setTimeout；延时
					},
				// 小车的运动过程
				run : function(pts,carPlateNo,carMark,rotation) {
					var map = globalMap; // 创建地图实例
					var paths = pts.length; // 获得有几个点
					var label = new BMap.Label(carPlateNo, {
						offset : new BMap.Size(20, -10)
					});
					// 给label设置样式，任意的CSS都是可以的
					label.setStyle({
						color : "red", // 颜色
						fontSize : "14px", // 字号
						border : "0", // 边
						textAlign : "center", // 文字水平居中显示
						cursor : "pointer"
					});
					label.setTitle(carPlateNo);
					carMark.setLabel(label);
					i = 0;
					function resetMkPoint(i) {
						if (i < paths - 1) {
							carMark.setRotation(rotation);
						}
						carMark.setPosition(pts[i]);
						if (i < paths) {
							setTimeout(function() {
								i++;
								resetMkPoint(i);
							}, 10000);// 修改时间间隔1000，能控制小车跑完全程所用的时间。
						}
					}
					setTimeout(function() {
						carMark.setRotation(rotation-90);
						resetMkPoint(0);
					}, 100) // setTimeout；延时
				},
				findRotation : function(curPos, targetPos) {
					var map = globalMap; // 创建地图实例
					var deg = 0;
					curPos = map.pointToPixel(curPos);
					targetPos = map.pointToPixel(targetPos);
					if (targetPos.x != curPos.x) {
						var tan = (targetPos.y - curPos.y)
								/ (targetPos.x - curPos.x), atan = Math
								.atan(tan);
						deg = atan * 360 / (2 * Math.PI);
						if (targetPos.x < curPos.x) {
							deg = -deg + 90 + 90;
						} else {
							deg = -deg;
						}
						return -deg;

					} else {
						var disy = targetPos.y - curPos.y;
						var bias = 0;
						if (disy > 0)
							bias = -1
						else
							bias = 1
						return -bias * 90;
					}

				},
				setMarkLable:function(carMark,markInfo){
					var label = new BMap.Label(markInfo,{offset:new BMap.Size(20,-10)});
					// 给label设置样式，任意的CSS都是可以的
					label.setStyle({
						color : "red", // 颜色
						fontSize : "14px", // 字号
						border : "0", // 边
						textAlign : "center", // 文字水平居中显示
						cursor : "pointer"
					});
					carMark.setLabel(label);
				},
				showMarkLabel:function(carMonitorList,allMarker){
					for(var i=0;i<carMonitorList.length;i++){
						var label = new BMap.Label(carMonitorList[i].carPlateNo,{offset:new BMap.Size(20,-10)});
						// 给label设置样式，任意的CSS都是可以的
						label.setStyle({
							color : "red", // 颜色
							fontSize : "14px", // 字号
							border : "0", // 边
							textAlign : "center", // 文字水平居中显示
							cursor : "pointer"
						});
						var carNo = carMonitorList[i].carNo;
						allMarker[carNo].setLabel(label);
					}
				},
				removeMarkerLabel:function(marker){
					for(var key in marker){
						globalMap.removeOverlay(marker[key].getLabel());
					}
				},
				setCarWindowInfo:function (map,monitor,marker){
					var p0 = monitor.longitude; // 经度
					var p1 = monitor.latitude; // 纬度
					var point = new BMap.Point(p0, p1); // 循环生成新的地图点
					if (monitor.carStatus == 1) {
						var status = "已启动";
					} else {
						var status = "已熄火";
					}
					var chargeState = "未充电"
					if(monitor.chargeState == 1){
						chargeState = "正在充电"
					}
					var content = "";
					var rangeMileage = monitor.rangeMileage==null?"未知":monitor.rangeMileage+"km";
					if(monitor.userageStatus==2||monitor.userageStatus==1){
					} else if(monitor.userageStatus==0){
					}
				},
				addMarker : function(carStatusList, map) {
					// cenLng cenLat; 存放标注点经纬信息的数组
					var point = new Array(); // 存放标注点经纬信息的数组
					var marker;
					var myIcon;
					for (var i = 0; i < carStatusList.length; i++) {
						
						var p0 = carStatusList[i].longitude; // 经度
						var p1 = carStatusList[i].latitude; // 纬度
						point[i] = new BMap.Point(p0, p1); // 循环生成新的地图点
						var monitor = carStatusList[i];
						if (monitor.carStatus == 1) {
							var status = "已启动";
						} else {
							var status = "已熄火";
						}
						var chargeState = "未充电"
						if(monitor.chargeState == 1){
							chargeState = "正在充电"
						}
						var content = "";
						var rangeMileage = monitor.rangeMileage==null?"未知":monitor.rangeMileage+"km";
						if(monitor.userageStatus==2){
							myIcon = new BMap.Icon(carMonitor.appPath
									+ '/res/img/car/car_red.png',
									new BMap.Size(32, 48));
							marker = new BMap.Marker(point[i], {
								icon : myIcon
							}); 
							map.addOverlay(marker);
							carMonitor.addClickHandler(content, marker, map,350,monitor.carPlateNo);
						}else if(monitor.userageStatus==3){
							myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png',new BMap.Size(32, 48));
							marker = new BMap.Marker(point[i], {
								icon : myIcon
							});
							//carMonitor.setMarkLable(marker, monitor.carPlateNo);
							// 按照地图点坐标生成标记
							map.addOverlay(marker);
							carMonitor.addClickHandler(content, marker, map,400,monitor.carPlateNo);
						} else if(monitor.userageStatus==1){
							myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_yellow.png',new BMap.Size(32, 48));
							marker = new BMap.Marker(point[i], {
								icon : myIcon
							});
							//carMonitor.setMarkLable(marker, monitor.carPlateNo);
							// 按照地图点坐标生成标记
							map.addOverlay(marker);
							carMonitor.addClickHandler(content, marker, map,400,monitor.carPlateNo);
						} else if(monitor.userageStatus==0){
							
							myIcon = new BMap.Icon(carMonitor.appPath + '/res/img/car/car_green.png',new BMap.Size(32, 48));
							marker = new BMap.Marker(point[i], {
								icon : myIcon
							});
							//carMonitor.setMarkLable(marker, monitor.carPlateNo);
							// 按照地图点坐标生成标记
							map.addOverlay(marker);
							carMonitor.addClickHandler(content, marker, map,230,monitor.carPlateNo);
              			}
					}
				},
				
				
				
				addClickHandler : function(content, marker, map, height,carPlateNo) {
					marker.addEventListener("click", function(e) {
						//删除已有的markerLabel
						map.removeOverlay(allMarks);
						
						$(".car-info").empty();
						$(".car-info").append(content);
						//请求数据
						carMonitor.carInfo(carPlateNo);
						var label = new BMap.Label("<div>"+carPlateNo+"<span style='background:#607BF1;display:block;width:10px;height:10px;position:absolute;transform: rotate(45deg);left:24px;top:20px;z-index:9999'></span></div>",{offset:new BMap.Size(20,-10)});
						// 给label设置样式，任意的CSS都是可以的
						label.setStyle({
							backgroundColor:"#607BF1",
							display:"inline-block",
							border :"none", 
							fontWeight :"bold", 
							color : "#fff", // 颜色
							padding:"4px 12px",
							textAlign : "center", // 文字水平居中显示
							cursor : "pointer",
							maxWidth:"1000px",
							borderRadius:"15px",
							marginLeft: "-35px",
							marginTop:"-22px"
						});
						marker.setLabel(label);
						allMarks = marker.getLabel();
					});
				},
				openInfo : function(content, e, map, height) {
					var opts = {
						width : 260, // 信息窗口宽度
						height : height, // 信息窗口高度
					};
					var p = e.target;
					var point = new BMap.Point(p.getPosition().lng, p
							.getPosition().lat);
					var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
					map.openInfoWindow(infoWindow, point); // 开启信息窗口
				}
			};
			
			// 定义一个控件类，即function
			function ZoomControl() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(320, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControl.prototype = new BMap.Control();
			ZoomControl.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("sapn");
				button.className = "btn";
				// 添加文字说明
				button.appendChild(document.createTextNode("全屏"));
				// 设置样式
				button.style.cursor = "pointer";
				button.style.border = "1px solid gray";
				button.style.backgroundColor = "white";
				button.style.marginRight = "5%";
//				button.style.right = "";
				// 绑定事件，点击一次放大两级
				button.onclick = function(e) {
					/* 屏幕滚动条置顶 */
					fillObject.fillToSco();// 放大
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				
				// 将DOM元素返回
				return button;
			}
			// 定义一个控件类，即function
			function ZoomControl1() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(220, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControl1.prototype = new BMap.Control();
			ZoomControl1.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("span");
				// 添加文字说明
				button.appendChild(document.createTextNode("退出全屏"));
				button.className = "btn";
				// 设置样式
				button.style.cursor = "pointer";
				button.style.border = "1px solid gray";
				button.style.backgroundColor = "white";
				button.style.marginRight = "6%";
				// 绑定事件，点击一次放大两级
				button.onclick = function(e) {
					fillObject.scoTofill();// 缩小
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				// 将DOM元素返回
				return button;
			}
		
			function ZoomControl4() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(220, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControl4.prototype = new BMap.Control();
			ZoomControl4.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("span");
				button.className = "btn";
				button.id = "closeCarMarkBtn";
				// 添加文字说明
				button.appendChild(document.createTextNode("显示/隐藏场站名"));
				// 设置样式
				button.style.cursor = "pointer";
				button.style.border = "1px solid gray";
				button.style.backgroundColor = "white";
				button.style.marginRight = "4%";
				// 绑定事件，
				button.onclick = function(e) {
					fillObject.removeParkLabel();
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				// 将DOM元素返回
				return button;
			}
			
			
			// 放大object
			var fillObject = {
				fillToSco : function() {

					$("body,html").scrollTop(0);
					/* 高为屏幕的高 */
					$("body").css({
						"overflow-y" : "hidden"
					})
					$(".textZln").hide();
					$("#car-map").addClass("fillScree");
					$("#car-map").css({
						"height" : $(window).height(),
						"width" : $(window).width(),
					});
					$(window).resize(function() {
						$("#car-map").css({
							"height" : $(window).height(),
							"width" : $(window).width(),
						});
					})
//					globalMap.clearOverlays();
					var allPoints = [];
					for(var key in globalMarkerPoint){
						allPoints.push(globalMarkerPoint[key]);
					}
					carMonitor.showMap(globalCarList, allPoints);
				},

				// 缩小
				scoTofill : function() {
					$("body,html").scrollTop(0);
					/* 高为屏幕的高 */
					$("body").css({
						"overflow-y" : "auto"
					})
					$(".textZln").show();
					$("#car-map").removeClass("fillScree");
					$("#car-map").css({
						"height" : "750px",
						"width" : "100%",
					});
					$(window).resize(function() {
						$("#ln-map").css({
							"height" : "750px",
							"width" : "100%",
						});
					})
//					globalMap.clearOverlays();
					var allPoints = [];
					for(var key in globalMarkerPoint){
						allPoints.push(globalMarkerPoint[key]);
					}
					carMonitor.showMap(globalCarList, allPoints);
					$(".btn").removeClass("buttonFull");
				},
				showMarkLabel:function(carMonitorList,allMarks){
					for(var i=0;i<carMonitorList.length;i++){
						var label = new BMap.Label(carMonitorList[i].carPlateNo,{offset:new BMap.Size(20,-10)});
						// 给label设置样式，任意的CSS都是可以的
						label.setStyle({
							color : "red", // 颜色
							fontSize : "14px", // 字号
							border : "0", // 边
							textAlign : "center", // 文字水平居中显示
							cursor : "pointer"
						});
						var carNoTemp = carMonitorList[i].carNo;
						var marker = allMarks[carNoTemp];
						if(marker){
							marker.setLabel(label);
						}
					}
				},
				removeMarkerLabel:function(marker){
					for(var key in marker){
						globalMap.removeOverlay(marker[key].getLabel());
					}
				},
				removeParkLabel:function(){
					//画场站和场站名称
					for(var key in polygonBMaps){
						var is_show = polygonBMaps[key]["is_show"];
						var my_park_label=polygonBMaps[key]["park_label"];
						if(is_show ==0){
							polygonBMaps[key]["is_show"]=1;
							globalMap.addOverlay(my_park_label);
						}else{
							polygonBMaps[key]["is_show"]=0;
							globalMap.removeOverlay(my_park_label);
						}
					}
				}
			}
			return carMonitor;
		});
/**
 * 日期格式化 yyyy-MM-dd hh:mm:ss
 * 
 * @param {[type]}
 *            date [description]
 * @return {[type]} [description]
 */
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
