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
					carMonitor.lushu1();
					carMonitor.lushu2();
					carMonitor.lushu3();
					carMonitor.lushu4();
					carMonitor.lushu5();
					carMonitor.lushu6();
					carMonitor.lushu7();
					carMonitor.lushu8();
					carMonitor.lushu9();
					carMonitor.lushu10();
					carMonitor.lushu11();
					carMonitor.lushu12();
					carMonitor.lushu13();
					carMonitor.lushu14();
					carMonitor.lushu15();
					carMonitor.lushu16();
					carMonitor.lushu17();
					carMonitor.addCarMarker();
//					carMonitor.pageList();
//					carMonitor.refreshPage(22);
				},
				//定时刷新页面
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
					var map = new BMap.Map("l-map"); // 创建地图实例
					globalMap = map;
					map.centerAndZoom(new BMap.Point(114.093277,22.55212),6);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
				},
				lushu1 : function() {
					var map = globalMap; // 创建地图实例
					
					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
					//设置地图全屏按钮和退出全屏按钮
					// 创建控件实例  
					var myZoomCtrl = new ZoomControl();  
					// 添加到地图当中  
					map.addControl(myZoomCtrl);
					var escFullScreenCtrl = new ZoomControl1();  
					// 添加到地图当中  
					map.addControl(escFullScreenCtrl);
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00000",//"从天安门到百度大厦"
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 60,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
					drv.search('莲花山公园东', '塘厦镇');
				},
				lushu2:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00001",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 55,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
					drv.search('深圳南山汽车站', '深圳市大运体育场');
					//绑定事件
				},
				lushu3:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00002",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 65,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('龙岗汽车总站', '深圳平湖汽车站');
				},
				lushu4:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B0003",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 70,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('光明城站', '塘厦镇');
				},
				lushu5:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00004",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 65,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('奥林匹克大厦', '腾讯大厦');
				},
				lushu6:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00005",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 55,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('奥林匹克大厦', '民治');

				},
				lushu7:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00006",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 50,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('奥林宾馆', '深圳高尔夫俱乐部');
				},
				lushu8:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00007",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 60,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('深圳湾', '宝安机场');
				},
				lushu9:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00008",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 48,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('宇阳大厦', '宝安机场');
				},
				lushu10:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00009",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 53,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('广东省深圳市福田区深圳会展中心', '深圳野生动物园');
				},
				lushu11:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00010",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32,32)),
				                speed: 58,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('世界之窗', '深圳大剧院');
				},
				lushu12:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00011",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32,32)),
				                speed: 62,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('龙顶山', '深圳大剧院');
				},
				lushu13:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B000012",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32,32)),
				                speed: 60,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('龙顶山', '南山科技园');
				},
				lushu14:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00013",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32,32)),
				                speed: 65,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('南山科技园', '朗山路');
				},
				lushu15:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00014",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32,32)),
				                speed: 65,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('大冲商务中心', '朗山酒店');
				},
				lushu16:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00015",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32,32)),
				                speed: 65,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('西丽', '保安机场');
				},
				lushu17:function(){
					var map = globalMap; // 创建地图实例
					var lushu;
					// 实例化一个驾车导航用来生成路线
				    var drv = new BMap.DrivingRoute('深圳', {
				        onSearchComplete: function(res) {
				            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
				                var plan = res.getPlan(0);
				                var arrPois =[];
				                for(var j=0;j<plan.getNumRoutes();j++){
				                    var route = plan.getRoute(j);
				                    arrPois= arrPois.concat(route.getPath());
				                }
//				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00016",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32,32)),
				                speed: 50,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('西丽', '蛇口');
				},
				addCarMarker:function(){
					var myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
					var marker = new BMap.Marker(new BMap.Point(114.127341,22.617791), {
						icon : myIcon
					}); // 按照地图点坐标生成标记
					globalMap.addOverlay(marker);
					myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
					marker = new BMap.Marker(new BMap.Point(114.098738,22.56547), {
						icon : myIcon
					}); // 按照地图点坐标生成标记
					globalMap.addOverlay(marker);
					myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
					marker = new BMap.Marker(new BMap.Point(114.079479,22.553722), {
						icon : myIcon
					}); // 按照地图点坐标生成标记
					globalMap.addOverlay(marker);
					globalMap.addOverlay(marker);
					myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
					marker = new BMap.Marker(new BMap.Point(114.070136,22.547981), {
						icon : myIcon
					}); // 按照地图点坐标生成标记
					globalMap.addOverlay(marker);
					globalMap.addOverlay(marker);
					myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
					marker = new BMap.Marker(new BMap.Point(114.077898,22.53997), {
						icon : myIcon
					}); // 按照地图点坐标生成标记
					globalMap.addOverlay(marker);
				}
				,
				addMarker : function(carStatusList,parkList, map) {
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
				    
					for (var i = 0; i < parkList.length; i++) {
						var p0 = parkList[i].longitude; // 经度
						var p1 = parkList[i].latitude; // 纬度
						point[i] = new BMap.Point(p0, p1); // 循环生成新的地图点
						myIcon = new BMap.Icon(carMonitor.appPath+ '/res/img/car/park_icon.png', new BMap.Size(32, 32));
						marker = new BMap.Marker(point[i], {
							icon : myIcon
						}); // 按照地图点坐标生成标记
						map.addOverlay(marker);
						var monitor = parkList[i];
						var item = "<h3 style='text-align:center;'>场站信息</h3>",
						row0 = "<ul class='clearfix carInfro'><li class='pull-left'>场站名称：</li><li class='pull-left'>"+monitor.parkName+"</li></ul>"
//						row1 = "<ul class='clearfix carInfro'><li class='pull-left'>速度：</li><li class='pull-left'>"+monitor.speed+"km/h</li></ul>",
//						row2 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余电量：</li><li class='pull-left'>"+monitor.power+"%</li></ul>",
//						row3 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余里程：</li><li class='pull-left'>"+monitor.rangeMileage+"km</li></ul>",
//						row4 = "<ul class='clearfix carInfro'><li class='pull-left'>车辆状态：</li><li class='pull-left'>"+status+"</li></ul>"
						content =row0;
						carMonitor.addClickHandler(content,marker,map,20);
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
						$("#l-map").addClass("fillScree");
						$("#l-map").css({
							"height":$(window).height(),
							"width":$(window).width(),
						});
						$(window).resize(function(){
							$("#l-map").css({
								"height":$(window).height(),
								"width":$(window).width(),
							});
						})
						globalMap.clearOverlays();
						carMonitor.mapInit();
						carMonitor.lushu1();
						carMonitor.lushu2();
						carMonitor.lushu3();
						carMonitor.lushu4();
						carMonitor.lushu5();
						carMonitor.lushu6();
						carMonitor.lushu7();
						carMonitor.lushu8();
						carMonitor.lushu9();
						carMonitor.lushu10();
						carMonitor.lushu11();
						carMonitor.lushu12();
						carMonitor.lushu13();
						carMonitor.lushu14();
						carMonitor.lushu15();
						carMonitor.lushu16();
						carMonitor.lushu17();
						carMonitor.addCarMarker();
					},
					
					
					//缩小
					scoTofill:function(){
						$("body,html").scrollTop(0);
						/*高为屏幕的高*/
						$("body").css({
							"overflow-y":"auto"
						})
						$(".textZln").show();
						$("#l-map").removeClass("fillScree");
						$("#l-map").css({
							"height":"550px",
							"width":"100%",
						});
						$(window).resize(function(){
							$("#ln-map").css({
								"height":"550px",
								"width":"100%",
							});
						})
						globalMap.clearOverlays();
						carMonitor.mapInit();
						carMonitor.lushu1();
						carMonitor.lushu2();
						carMonitor.lushu3();
						carMonitor.lushu4();
						carMonitor.lushu5();
						carMonitor.lushu6();
						carMonitor.lushu7();
						carMonitor.lushu8();
						carMonitor.lushu9();
						carMonitor.lushu10();
						carMonitor.lushu11();
						carMonitor.lushu12();
						carMonitor.lushu13();
						carMonitor.lushu14();
						carMonitor.lushu15();
						carMonitor.lushu16();
						carMonitor.lushu17();
						carMonitor.addCarMarker();
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
