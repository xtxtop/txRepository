var service_id = 125226;//生产
var ak = "XWlUgpVK4cGkZjyBSoyNTM8wEV1T0qgN";//生产
var is_processed = 1;// 0：关闭轨迹纠偏，返回原始轨迹1：打开轨迹纠偏，返回纠偏后轨迹。打开纠偏时，请求时间段内轨迹点数量不能超过2万，否则将返回错误。轨迹纠偏包括去噪、抽稀、绑路三个选项，可根据业务需求在process_option参数中自由组合。
var url = "http://api.map.baidu.com/trace/v2/track/gethistory";// 查询轨迹的url
var page_size = 1000;
var page_index = 1;
var entity_name = "";
var polylines = [];
var allCarNos = [];
var carGlobalMap;
var allMarks = new Object();;
var lushuIcon = new Object();
var globalCarNos = [];
var globalCarList = [];
var globalMarkerPoint = [];
var carGlobalPoints =new Object();
define(
		[],
		function() {
			var carMonitor = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#carMonitorSearch").click(function() {
						carMonitor.pageList();
					});
					carMonitor.mapInit();
					carMonitor.pageList();
					carMonitor.refreshPage(22);
				},
				//定时刷新页面
				refreshPage: function (seconds) {
					var sec = parseInt(seconds);
					setInterval(function(){
						carMonitor.refreshShowMap(globalCarList, globalCarNos);
					},sec*1000);
					setInterval(function(){
						carMonitor.refreshPageList();
					},sec*3000);
				},
				pageList : function() {
					var form = $("form[name=carMonitorSerachForm]");
					var carPlateNo = form.find("input[name='carPlateNo']").val();
					var flag = form.find("input[name='flag']").val();
					var userageStatus = form.find("input[name='userageStatus']").val();
					var cityId = form.find("input[name='cityId']").val();
					form.ajaxSubmit({
						url : carMonitor.appPath
								+ "/carMonitor/pageListCarMonitor.do",
						type : "post",
						success : function(res) {
							if (res != "") {
								var carStatusListArr = eval(res);
								var carStatusList = new Array();
								var carStatus = new Object();
								var points = new Array();
								var carNos = new Array();
								var point;
								for (var i = 0; i < carStatusListArr.length; i++) {
									carStatus = carStatusListArr[i];
									carStatusList.push(carStatus);
									carNos.push(carStatus.carNo);
									point = new Object();
									point.lng = carStatusListArr[i].longitude;
									point.lat = carStatusListArr[i].latitude;
									points.push(point);
								}
								globalCarList = carStatusList;
								globalCarNos = carNos;
								globalMarkerPoint = points;
								carMonitor.showMap(carStatusList,points);
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无满足查询条件的车辆！");
							}
						}
					});
				},
				refreshPageList : function() {
					var form = $("form[name=carMonitorSerachForm]");
					var carPlateNo = form.find("input[name='carPlateNo']").val();
					var flag = form.find("input[name='flag']").val();
					var userageStatus = form.find("input[name='userageStatus']").val();
					var cityId = form.find("input[name='cityId']").val();
					form.ajaxSubmit({
						url : carMonitor.appPath
								+ "/carMonitor/pageListCarMonitor.do",
						type : "post",
						success : function(res) {
							if (res != "") {
								var carStatusListArr = eval(res);
								var carStatusList = new Array();
								var carStatus = new Object();
								var points = new Array();
								var carNos = new Array();
								var point;
								for (var i = 0; i < carStatusListArr.length; i++) {
									carStatus = carStatusListArr[i];
									carStatusList.push(carStatus);
									carNos.push(carStatus.carNo);
									point = new Object();
									point.lng = carStatusListArr[i].longitude;
									point.lat = carStatusListArr[i].latitude;
									points.push(point);
								}
								globalCarList = carStatusList;
								globalCarNos = carNos;
								globalMarkerPoint = points;
							} 
						}
					});
				},
				refreshShowMap : function(globalCarList, globalCarNos) {
//					//移除覆盖物
//					for(var i=0;i<allMarks.length;i++){
//						carGlobalMap.removeOverlay(allMarks[i]);
//					}
					var mapPoints = new Object();
					var carNos = new Array();
					carNos.splice(0,carNos.length);
					carNos = globalCarNos;
						
					for(var i=0;i<globalCarList.length;i++){
						var monitor = globalCarList[i];
						var type = 0;
						if(monitor.order!=null){
							type = 1;
						}else if(monitor.workerOrder!=null){
							type = 0;
						}
						var nowTime = $("#nowTime").val();
						if(nowTime!=null||nowTime!=""){
							var start_time = new Date(nowTime).Format("yyyy-MM-dd HH:mm:ss");
							var end_time = new Date().Format("yyyy-MM-dd HH:mm:ss");
							carMonitor.getHistory(carGlobalMap, monitor.carNo, start_time, end_time, type);
						}
					}
				},
				getHistory : function(map,entity_name,start_time,end_time,type) {
					$.ajax({
						url : carMonitor.appPath
								+ "/carTrack/getHistoryByEntityName.do",
						data : {
							entityName : entity_name,
							startTime:start_time,
							endTime:end_time
						},
						success : function(result) {
							if (result.code == 1) {// 0为成功
								var total = result.data.total// 本次检索总结果条数
								if(total>0){
									var bpoints = new Array();
									for(var i=0;i<result.data.points.length;i++){
										var point = new Object();//lng,lat
										point.lng=result.data.points[i].location[0];
										point.lat=result.data.points[i].location[1];
										bpoints.push(point);
									}
									var carIcon = "/res/img/car/";
									if(type==1){
										carIcon += "car_red";
									}else if(type==2){
										carIcon += "car_blue";
									}else{
										carIcon += "car_green";
									}
//									//删除lushu点
//									carGlobalMap.removeOverlay(lushuIcon[entity_name]);
									if(lushuIcon[entity_name]){
										lushu = lushuIcon[entity_name];
										lushu.pause();
										var pointsArr = [];
										pointsArr.push(carGlobalPoints[entity_name]);
										for(var i=0;i<bpoints.length;i++){
											if(bpoints[i]!=null){
												pointsArr.push(bpoints[i]);
											}
										}
										lushu.setPoints(pointsArr);
										lushu.start(allMarks[entity_name]);
									}else{
										//小车移动
										var lushu = new BMapLib.LuShu(carGlobalMap, bpoints, {
											defaultContent: "",
											speed: 60,//路书速度
											autoView: true, //是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
											icon: new BMap.Icon(carMonitor.appPath+ carIcon+'.png', new BMap.Size(52, 23), { anchor: new BMap.Size(30, 20) }),
											enableRotation: true, //是否设置marker随着道路的走向进行旋转
											landmarkPois:[],//显示的特殊点，似乎是必选参数，可以留空，据说要和距原线路10米内才会暂停，这里就用原线的点
										});  
										lushuIcon[entity_name]=lushu;
										carGlobalPoints[entity_name]=bpoints[bpoints.length-1];
										lushu.start(allMarks[entity_name]);
									}
								}
							} 
						}
					});
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
						}
						;
						var cenLng = (parseFloat(maxLng) + parseFloat(minLng)) / 2;
						var cenLat = (parseFloat(maxLat) + parseFloat(minLat)) / 2;
						var zoom = carMonitor.getZoom(maxLng, minLng, maxLat,
								minLat, map);
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
				mapInit:function() {
					var map = new BMap.Map("car-map"); // 创建地图实例
					carGlobalMap = map;
					map.centerAndZoom(new BMap.Point(103.388611,35.563611),5);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
				},
				showMap : function(carStatusList, points) {
					var map = carGlobalMap; // 创建地图实例
					var zoom = carMonitor.setZoom(points, map);
					map.centerAndZoom(new BMap.Point(zoom.cenLng, zoom.cenLat),
							zoom.zoom);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
					//设置地图全屏按钮和退出全屏按钮
					// 创建控件实例  
					var myZoomCtrl = new ZoomControl();  
					// 添加到地图当中  
					map.addControl(myZoomCtrl);
					var escFullScreenCtrl = new ZoomControl1();  
					// 添加到地图当中  
					map.addControl(escFullScreenCtrl);
					carMonitor.addMarker(carStatusList, map);
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
						if(monitor.carStatus==1){
							var status = "已启动";
						}else{
							var status = "已熄火";
						}
						var content = "";
						if(monitor.order!=null){
							var item = "<h3 style='text-align:center;'>订单车辆信息</h3>",
							row0 = "<ul class='clearfix carInfro'><li class='pull-left'>车牌号：</li><li class='pull-left'>"+monitor.carPlateNo+"</li></ul>",
							row1 = "<ul class='clearfix carInfro'><li class='pull-left'>速度：</li><li class='pull-left'>"+monitor.speed+"km/h</li></ul>",
							row2 = "<ul class='clearfix carInfro'><li class='pull-left'>车辆状态：</li><li class='pull-left'>"+status+"</li></ul>",
							row3 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余电量：</li><li class='pull-left'>"+monitor.power+"%</li></ul>",
							row4 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余里程：</li><li class='pull-left'>"+monitor.rangeMileage+"km</li></ul>",
							row5 = "<ul class='clearfix carInfro'><li class='pull-left'>客户姓名：</li><li class='pull-left'>"+monitor.order.memberName+"</li></ul>",
							row6 = "<ul class='clearfix carInfro'><li class='pull-left'>手机：</li><li class='pull-left'>"+monitor.order.mobilePhone+"</li></ul>",
							row7 = "<ul class='clearfix carInfro'><li class='pull-left'>订单号：</li><li class='pull-left'>"+monitor.order.orderNo+"</li></ul>",
							row8 = "<ul class='clearfix carInfro'><li class='pull-left'>始发地：</li><li class='pull-left'>"+monitor.order.actualTakeLoacton+"</li></ul>",
							row9 = "<ul class='clearfix carInfro'><li class='pull-left'>目的地：</li><li class='pull-left'>未知</li></ul>",
							row10 = "<ul class='clearfix carInfro'><li class='pull-left'>开始时间：</li><li class='pull-left'>"+new Date(monitor.order.startBillingTime).Format("yyyy-MM-dd HH:mm:ss")+"</li></ul>"
							content = item+row0+row1+row2+row3+row4+row5+row6+row7+row8+row9+row10;
							myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32, 32));
							marker = new BMap.Marker(point[i], {
								icon : myIcon
							}); // 按照地图点坐标生成标记
//							map.addOverlay(marker);
							allMarks[monitor.carNo]=marker;
							carMonitor.addClickHandler(content,marker,map,350);
						}else if(monitor.workerOrder!=null){
							if(monitor.workerOrder.startTime!=null&&monitor.workerOrder.startTime!=''){
								var item = "<h3 style='text-align:center;'>调度车辆信息</h3>",
								row0 = "<ul class='clearfix carInfro'><li class='pull-left'>车牌号：</li><li class='pull-left'>"+monitor.carPlateNo+"</li></ul>",
								row1 = "<ul class='clearfix carInfro'><li class='pull-left'>速度：</li><li class='pull-left'>"+monitor.speed+"km/h</li></ul>",
								row2 = "<ul class='clearfix carInfro'><li class='pull-left'>车辆状态：</li><li class='pull-left'>"+status+"</li></ul>",
								row3 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余电量：</li><li class='pull-left'>"+monitor.power+"%</li></ul>",
								row4 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余里程：</li><li class='pull-left'>"+monitor.rangeMileage+"km</li></ul>",
								row5 = "<ul class='clearfix carInfro'><li class='pull-left'>调度员姓名：</li><li class='pull-left'>"+monitor.workerOrder.workerName+"</li></ul>",
								row6 = "<ul class='clearfix carInfro'><li class='pull-left'>手机：</li><li class='pull-left'>"+monitor.workerOrder.mobilePhone+"</li></ul>",
								row7 = "<ul class='clearfix carInfro'><li class='pull-left'>工单号：</li><li class='pull-left'>"+monitor.workerOrder.workerOrderNo+"</li></ul>",
								row8 = "<ul class='clearfix carInfro'><li class='pull-left'>始发地：</li><li class='pull-left'>"+monitor.workerOrder.startParkName+"</li></ul>",
								row9 = "<ul class='clearfix carInfro'><li class='pull-left'>目的地：</li><li class='pull-left'>"+monitor.workerOrder.terminalParkName+"</li></ul>",
								row10 = "<ul class='clearfix carInfro'><li class='pull-left'>开始时间：</li><li class='pull-left'>"+new Date(monitor.workerOrder.startTime).Format("yyyy-MM-dd HH:mm:ss")+"</li></ul>"	
							}else{
								var item = "<h3 style='text-align:center;'>调度车辆信息</h3>",
								row0 = "<ul class='clearfix carInfro'><li class='pull-left'>车牌号：</li><li class='pull-left'>"+monitor.carPlateNo+"</li></ul>",
								row1 = "<ul class='clearfix carInfro'><li class='pull-left'>速度：</li><li class='pull-left'>"+monitor.speed+"km/h</li></ul>",
								row2 = "<ul class='clearfix carInfro'><li class='pull-left'>车辆状态：</li><li class='pull-left'>"+status+"</li></ul>",
								row3 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余电量：</li><li class='pull-left'>"+monitor.power+"%</li></ul>",
								row4 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余里程：</li><li class='pull-left'>"+monitor.rangeMileage+"km</li></ul>",
								row5 = "<ul class='clearfix carInfro'><li class='pull-left'>调度员姓名：</li><li class='pull-left'>"+monitor.workerOrder.workerName+"</li></ul>",
								row6 = "<ul class='clearfix carInfro'><li class='pull-left'>手机：</li><li class='pull-left'>"+monitor.workerOrder.mobilePhone+"</li></ul>",
								row7 = "<ul class='clearfix carInfro'><li class='pull-left'>工单号：</li><li class='pull-left'>"+monitor.workerOrder.workerOrderNo+"</li></ul>",
								row8 = "<ul class='clearfix carInfro'><li class='pull-left'>始发地：</li><li class='pull-left'>"+monitor.workerOrder.startParkName+"</li></ul>",
								row9 = "<ul class='clearfix carInfro'><li class='pull-left'>目的地：</li><li class='pull-left'>"+monitor.workerOrder.terminalParkName+"</li></ul>",
								row10="";
							}
								myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32, 32));
								marker = new BMap.Marker(point[i], {
									icon : myIcon
								}); // 按照地图点坐标生成标记
//								map.addOverlay(marker);
								allMarks[monitor.carNo]=marker;
							content = item+row0+row1+row2+row3+row4+row5+row6+row7+row8+row9+row10;
							carMonitor.addClickHandler(content,marker,map,330);
						}else{
							var item = "<h3 style='text-align:center;'>空闲车辆信息</h3>",
							row0 = "<ul class='clearfix carInfro'><li class='pull-left'>车牌号：</li><li class='pull-left'>"+monitor.carPlateNo+"</li></ul>",
							row1 = "<ul class='clearfix carInfro'><li class='pull-left'>速度：</li><li class='pull-left'>"+monitor.speed+"km/h</li></ul>",
							row2 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余电量：</li><li class='pull-left'>"+monitor.power+"%</li></ul>",
							row3 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余里程：</li><li class='pull-left'>"+monitor.rangeMileage+"km</li></ul>",
							row4 = "<ul class='clearfix carInfro'><li class='pull-left'>车辆状态：</li><li class='pull-left'>"+status+"</li></ul>"
							myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
							marker = new BMap.Marker(point[i], {
								icon : myIcon
							}); // 按照地图点坐标生成标记
//							map.addOverlay(marker);
							allMarks[monitor.carNo]=marker;
						content = item+row0+row1+row2+row3+row4;
						carMonitor.addClickHandler(content,marker,map,200);
						}
					}
				    
				},
				addClickHandler : function(content, marker,map,height) {
					marker.addEventListener("click", function(e) {
						carMonitor.openInfo(content,e,map,height);
					});
				},
				openInfo : function(content,e,map,height) {
					var opts = {
						width : 240,     // 信息窗口宽度
						height: height,     // 信息窗口高度
					};
					var p = e.target;
					var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
					var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
					map.openInfoWindow(infoWindow, point); // 开启信息窗口
				}
			};
			// 定义一个控件类，即function  
			function ZoomControl(){  
				// 设置默认停靠位置和偏移量  
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;  
				this.defaultOffset = new BMap.Size(280, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control  
			ZoomControl.prototype = new BMap.Control();
			ZoomControl.prototype.initialize = function(map){  
				// 创建一个DOM元素  
				var button = document.createElement("button");
				button.className="btn";
				// 添加文字说明  
				button.appendChild(document.createTextNode("全屏"));  
				// 设置样式  
				button.style.cursor = "pointer";  
				button.style.border = "1px solid gray";  
				button.style.backgroundColor = "white";
				// 绑定事件，点击一次放大两级  
				button.onclick = function(e){  
					/*屏幕滚动条置顶*/
					fillObject.fillToSco();//放大
				};  
				// 添加DOM元素到地图中  
				map.getContainer().appendChild(button);  
				// 将DOM元素返回  
				return button;  
			} 
			// 定义一个控件类，即function  
			function ZoomControl1(){  
				// 设置默认停靠位置和偏移量  
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;  
				this.defaultOffset = new BMap.Size(180, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control  
			ZoomControl1.prototype = new BMap.Control();
			ZoomControl1.prototype.initialize = function(map){  
				// 创建一个DOM元素  
				var button = document.createElement("button");  
				// 添加文字说明  
				button.appendChild(document.createTextNode("退出全屏"));
				button.className="btn";
				// 设置样式  
				button.style.cursor = "pointer";  
				button.style.border = "1px solid gray";  
				button.style.backgroundColor = "white"; 
				// 绑定事件，点击一次放大两级  
				button.onclick = function(e){
					fillObject.scoTofill();//缩小
				};
				// 添加DOM元素到地图中  
				map.getContainer().appendChild(button);  
				// 将DOM元素返回  
				return button;  
			} 
			
			//放大object
			var fillObject = {
					fillToSco:function(){
						
						$("body,html").scrollTop(0);
						/*高为屏幕的高*/
						$("body").css({
							"overflow-y":"hidden"
						})
						$(".textZln").hide();
						$("#car-map").addClass("fillScree");
						$("#car-map").css({
							"height":$(window).height(),
							"width":$(window).width(),
						});
						$(window).resize(function(){
							$("#car-map").css({
								"height":$(window).height(),
								"width":$(window).width(),
							});
						})
//						carMonitor.mapInit();
						carMonitor.showMap(globalCarList, globalMarkerPoint);
						carMonitor.refreshShowMap(globalCarList, globalCarNos);
					},
					
					
					//缩小
					scoTofill:function(){
						$("body,html").scrollTop(0);
						/*高为屏幕的高*/
						$("body").css({
							"overflow-y":"auto"
						})
						$(".textZln").show();
						$("#car-map").removeClass("fillScree");
						$("#car-map").css({
							"height":"550px",
							"width":"100%",
						});
						$(window).resize(function(){
							$("#ln-map").css({
								"height":"550px",
								"width":"100%",
							});
						})
//						carMonitor.mapInit();
						carMonitor.showMap(globalCarList, globalMarkerPoint);
						carMonitor.refreshShowMap(globalCarList, globalCarNos);
						$(".btn").removeClass("buttonFull");
					}
			}
			return carMonitor;
		});
		/**
		 * 日期格式化  yyyy-MM-dd hh:mm:ss
		 * @param   {[type]}   date [description]
		 * @return  {[type]}        [description]
		 */
		Date.prototype.Format = function(fmt) {
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "H+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
