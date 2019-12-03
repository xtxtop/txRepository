var parkGlobalMap;
var globalParkList = [];
var parkGlobalMarkerPoint = [];
var globalParkMark=new Object();
define(
		[],
		function() {
			var parkMonitor = {
				appPath : getPath("app"),
				init : function() {
					var mapHeight = $('#mainContentHeight').val();
					$('#park-map').css({"width":"100%","height":mapHeight-43+'px'});
					$(window).resize(function () {
						var mapHeight = $('#mainContentHeight').val();
						$('#park-map').css({"width":"100%","height":mapHeight-43+'px'});
					})
					// 列表页面搜索调用
					$("#parkMonitorSearch").click(function() {
						parkMonitor.pageList();
					});
					parkMonitor.mapInit();
					parkMonitor.pageList();
					parkMonitor.refreshPage(22);
				},
				//定时刷新页面
				refreshPage: function (seconds) {
					var sec = parseInt(seconds);
					
				},
				pageList : function() {
					var form = $("form[name=parkMonitorSerachForm]");
					var parkName = form.find("input[name='parkName']").val();	//场站名称
					var userageStatus = form.find("select[name='isAvailable']").val();	//是否上线
					form.ajaxSubmit({
						url : parkMonitor.appPath
								+ "/parkMonitor/pageListPark.do",
						type : "post",
						success : function(res) {
							if (res != "") {
								var parkListArr = eval(res);
								var parkList = new Array();
								var park = new Object();
								var points = new Array();
								var point;
								for (var i = 0; i < parkListArr.length; i++) {
									park = parkListArr[i];
									parkList.push(park);
									point = new Object();
									point.lng = parkListArr[i].longitude;
									point.lat = parkListArr[i].latitude;
									points.push(point);
								}
								
								parkMonitor.showMap(parkList,points);
								
								globalParkList = parkList;
								parkGlobalMarkerPoint = points;
								
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无满足查询条件的场站！");
							}
						}
					});
				},
				refreshPageList : function() {
					var form = $("form[name=parkMonitorSerachForm]");
					var parkName = form.find("input[name='parkName']").val();	//场站名称
					var userageStatus = form.find("select[name='isAvailable']").val();	//是否上线
					var cityId = form.find("input[name='cityId']").val();
					form.ajaxSubmit({
						url : parkMonitor.appPath
								+ "/parkMonitor/pageListPark.do",
						type : "post",
						success : function(res) {
							if (res != "") {
								var parkListArr = eval(res);
								var parkList = new Array();
								var park = new Object();
								var points = new Array();
								var point;
								for (var i = 0; i < parkListArr.length; i++) {
									park = parkListArr[i];
									parkList.push(park);
									point = new Object();
									point.lng = parkListArr[i].longitude;
									point.lat = parkListArr[i].latitude;
									points.push(point);
								}
								
								parkMonitor.showMap(parkList,points);
								
								globalParkList = parkList;
								parkGlobalMarkerPoint = points;
								
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无满足查询条件的场站！");
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
						var zoom = parkMonitor.getZoom(maxLng, minLng, maxLat,
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
					var map = new BMap.Map("park-map"); // 创建地图实例
					parkGlobalMap = map;
					map.centerAndZoom(new BMap.Point(114.102332,22.580554),5);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
				},
				showMap : function(parkList, points) {
					var map = parkGlobalMap; // 创建地图实例
					var zoom = parkMonitor.setZoom(points, map);
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
					for(var key in globalParkMark){
						map.removeOverlay(globalParkMark[key]);
					}
					parkMonitor.addMarker(parkList, map);
				},
				addMarker : function(parkList, map) {
					var point = new Array(); // 存放标注点经纬信息的数组
					var marker;
					var myIcon;
					for (var i = 0; i < parkList.length; i++) {
						var p0 = parkList[i].longitude; // 经度
						var p1 = parkList[i].latitude; // 纬度
						var monitor = parkList[i];	//场站对象
						var spaceNum = monitor.parkingSpaceNumber;	//车位数
						var carNum = monitor.carNumber;	//车辆数
						if((spaceNum-carNum)>10){	//剩余车位为10的时候即代表车位紧张
							myIcon = new BMap.Icon(parkMonitor.appPath+ '/res/img/car/parkstation_icon.png', new BMap.Size(32, 32));
						}else{														
							myIcon = new BMap.Icon(parkMonitor.appPath+ '/res/img/car/redpark.png', new BMap.Size(32, 32));
						}
						point[i] = new BMap.Point(p0, p1); // 循环生成新的地图点
						marker = new BMap.Marker(point[i], {
							icon : myIcon
						}); // 按照地图点坐标生成标记
						map.addOverlay(marker);
						globalParkMark[monitor.parkNo]=marker;
//						var opts = {
//								position : point[i],    // 指定文本标注所在的地理位置
//								offset   : new BMap.Size(32, -32)    //设置文本偏移量
//						}
//						var label = new BMap.Label(monitor.parkName, opts);  // 创建文本标注对象
//						label.setStyle({
//							 color : "red",
//							 fontSize : "12px",
//							 height : "20px",
//							 lineHeight : "20px",
//							 fontFamily:"微软雅黑"
//						 });
//						map.addOverlay(label);
						var chargerNum = monitor.chargerNumber == null?0:monitor.chargerNumber;
						var item = "<h4 style='text-align:center;'>"+monitor.parkName+"</h4>",
						row0 = "<ul class='clearfix carInfro'><li class='pull-left'>车位：</li><li class='pull-left'>"+spaceNum+"</li></ul>"
						row1 = "<ul class='clearfix carInfro'><li class='pull-left'>电桩：</li><li class='pull-left'>"+chargerNum+"</li></ul>",
						row2 = "<ul class='clearfix carInfro'><li class='pull-left'>车辆：</li><li class='pull-left'>"+carNum+"</li></ul>",
						row3 = "<ul class='clearfix carInfro'><li class='pull-left'>租金：</li><li class='pull-left'>"+monitor.parkRent+"元/月</li></ul>",
						row4 = "<ul class='clearfix carInfro'><li class='pull-left'>服务：</li><li class='pull-left'>"+monitor.supportedServices+"</li></ul>"
						content =item+row0+row1+row2+row3+row4;
						parkMonitor.addClickHandler(content,marker,map,200);
					}
				},
				addClickHandler : function(content, marker,map,height) {
					marker.addEventListener("click", function(e) {
						parkMonitor.openInfo(content,e,map,height);
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
						$("#park-map").addClass("fillScree");
						$("#park-map").css({
							"height":$(window).height(),
							"width":$(window).width(),
						});
						$(window).resize(function(){
							$("#park-map").css({
								"height":$(window).height(),
								"width":$(window).width(),
							});
						})
						parkMonitor.showMap(globalParkList, parkGlobalMarkerPoint);
					},
					
					
					//缩小
					scoTofill:function(){
						$("body,html").scrollTop(0);
						/*高为屏幕的高*/
						$("body").css({
							"overflow-y":"auto"
						})
						$(".textZln").show();
						$("#park-map").removeClass("fillScree");
						$("#park-map").css({
							"height":"550px",
							"width":"100%",
						});
						$(window).resize(function(){
							$("#park-map").css({
								"height":"550px",
								"width":"100%",
							});
						})
						parkMonitor.showMap( globalParkList, parkGlobalMarkerPoint);
						$(".btn").removeClass("buttonFull");
					}
			}
			return parkMonitor;
		});
		
