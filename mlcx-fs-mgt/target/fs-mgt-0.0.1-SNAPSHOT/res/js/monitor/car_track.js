var points = [];
var label; // 信息标签
var car; // 汽车图标
var is_processed = 1;// 0：关闭轨迹纠偏，返回原始轨迹1：打开轨迹纠偏，返回纠偏后轨迹。打开纠偏时，请求时间段内轨迹点数量不能超过2万，否则将返回错误。轨迹纠偏包括去噪、抽稀、绑路三个选项，可根据业务需求在process_option参数中自由组合。
var url = "http://api.map.baidu.com/trace/v2/track/gethistory";// 查询轨迹的url
var page_size = 1000;
var page_index = 1;
var entity_name = "";
var carMark;
var carTrackPoints;
var carTrackMap;
define(
		[],
		function() {
			var globalLuShu;
			var carTrack = {
				appPath : getPath("app"),
				init : function() {
					var mapHeight = $('#mainContentHeight').val();
					$('#trackMap').css({"width":"100%","height":mapHeight-4+'px'});
					$(window).resize(function () {
						var mapHeight = $('#mainContentHeight').val();
						$('#trackMap').css({"width":"100%","height":mapHeight-4+'px'});
					})
					$('.carTrack-info').css("height","0");
					// 列表页面搜索调用
					$("#carTrackSearch").click(
						function() {
							var form = $("form[name=carTrackSerachForm]");
							var createTimeStart = form.find("input[name=createTimeStart]").val();
							var createTimeEnd = form.find("input[name=createTimeEnd]").val();
							var carPlateNo = encodeURI($.trim(form.find("input[name=carPlateNo]").val()));							
							if (createTimeStart != ""&& createTimeEnd != "") {
								if (new Date(createTimeStart) > new Date(createTimeEnd)) {
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
								} else if (new Date(createTimeEnd) > new Date()) {
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "结束时间不能大于当前时间！");
								} /*else if (new Date(createTimeEnd).getTime()- new Date(createTimeStart).getTime()>24*60*60*1000) {
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "结束时间与开始时间的时间差不能大于24小时！");
								} */else {
									if (carPlateNo != "") {
										carTrack.getCarInfo(createTimeStart,createTimeEnd);
									} else {
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入车牌号！");
									}
								}
							} else if (createTimeStart == ""
									|| createTimeEnd == "") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间或者结束时间不能为空！");
							} else if (carPlateNo != "") {
								carTrack.getCarInfo(createTimeStart,createTimeEnd);
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入车牌号！");
							}
						});
					carTrack.noCarTrack();
					var form = $("form[name=carTrackSerachForm]");
					var flag = $.trim(form.find("input[name=flag]").val());
					if(flag=="1"){
						var carPlateNo = encodeURI($.trim(form.find("input[name=carPlateNo]").val()));
						var createTimeStart = form.find("input[name=createTimeStart]").val();
						var createTimeEnd = form.find("input[name=createTimeEnd]").val();
						if(carPlateNo!=""&&createTimeStart!=""&&createTimeEnd!=""){
							carTrack.getCarInfo(createTimeStart, createTimeEnd);
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "没有数据");
						}
					}
				},
				getCarInfo : function(createTimeStart,createTimeEnd) {
					var form = $("form[name=carTrackSerachForm]");
					var carPlateNo = encodeURI($.trim(form.find("input[name=carPlateNo]").val()));
					$.ajax({
						url : carTrack.appPath+ "/carTrack/getCarByPlateNo.do?carPlateNo="+ carPlateNo,
						success : function(res) {
							debugger;
							if (res.code == "1") {
								var car = res.data;// 车辆编号 carNo;
								var entity_name = car.carNo;
								carTrack.getHistory(entity_name,createTimeStart, createTimeEnd);
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "您输入的车牌号不存在！");
							}
						}
					});
				},
				
				noCarTrack : function() {
					var map = new BMap.Map("trackMap"); // 创建地图实例
					carTrackMap = map;
					map.centerAndZoom(new BMap.Point(103.388611, 35.563611), 5);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
//					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
					// 创建全屏地图控件实例  
					var zoomControlPlay = new ZoomControlPlay();  
					// 添加到地图当中，播放
					map.addControl(zoomControlPlay);
					// 创建全屏地图控件实例  
					var zoomControlPause = new ZoomControlPause();  
					// 添加到地图当中，暂停
					map.addControl(zoomControlPause);
					// 创建全屏地图控件实例  
					var zoomControlStop = new ZoomControlStop();  
					// 添加到地图当中，重置
					map.addControl(zoomControlStop);
//					// 创建全屏地图控件实例  
//					var zoomControlhide = new ZoomControlHide();  
//					// 添加到地图当中，隐藏信息窗口
//					map.addControl(zoomControlhide);
//					var zoomControlshow = new ZoomControlShow();  
//					// 添加到地图当中，展示信息窗口
//					map.addControl(zoomControlshow);
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
						var zoom = carTrack.getZoom(maxLng, minLng, maxLat,
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
							return 18 - i + 4;// 之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
						}
					}
					;
				},

				mapInit : function(points,flag) {
					// 初始化地图,选取第一个点为起始点
					var map = carTrackMap;
					var zoom = carTrack.setZoom(points, map);
					map.centerAndZoom(new BMap.Point(zoom.cenLng, zoom.cenLat),zoom.zoom);// 设置中心点坐标和地图级别
					map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
					var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM,offset: new BMap.Size(30, 90)};
					map.addControl(new BMap.NavigationControl(opts));
//					map.addControl(new BMap.OverviewMapControl({
//						isOpen : true
//					}));
					var centerPoint = new BMap.Point(
							(points[0].lng + points[points.length - 1].lng) / 2,
							(points[0].lat + points[points.length - 1].lat) / 2);
					map.panTo(centerPoint);
					var polylinePointsArray = new Array();
					var landmarkPois = new Array();
					
					var form = $("form[name=carTrackSerachForm]");
					var carPlateNo =$.trim(form.find("input[name=carPlateNo]").val());
					var pointInfo=null;
					for (var i=0; i < points.length; i++) {
						var x = points[i].lng;
						var y = points[i].lat;
						polylinePointsArray.push(new BMap.Point(x,y));
					    var courseAngle = points[i].direction;
					    var direct = "未知";
					    if(courseAngle){
					    	//direct = parseInt(courseAngle);
					    	direct = courseAngle+"°";
//					    	if(courseAngle==0||courseAngle==360){
//					    		var direct = "北";  
//					    	}else if(0<courseAngle && courseAngle<90){
//					    		var direct = "东北";  
//					    	}else if(courseAngle=90){
//					    		var direct = "东";  
//					    	}else if(90<courseAngle && courseAngle<180){
//					    		var direct = "东南";  
//					    	}else if(courseAngle=180){
//					    		var direct = "南";  
//					    	}else if(180<courseAngle && courseAngle<270){
//					    		var direct = "西南";  
//					    	}else if(courseAngle=270){
//					    		var direct = "西";  
//					    	}else if(270<courseAngle && courseAngle<360){
//					    		var direct = "西北";  
//					    	}
					    }
					    var status = "未知";
					    if(points[i].carStatus==1){
					    	status = "已启动";
					    }else if(points[i].carStatus==2){
					    	status = "已熄火";
					    }
					    var useStatus = "";
					    if(points[i].carUserageStatus==0){
					    	useStatus = "空闲";
					    }else if(points[i].carUserageStatus==1){
					    	useStatus = "已预占";
					    }else if(points[i].carUserageStatus==2){
					    	useStatus = "订单中";
					    }else if(points[i].carUserageStatus==3){
					    	useStatus = "调度中";
					    }else{
					    	useStatus = "未知";
					    }
					    var lat = points[i].lat.toFixed(6);
					    var lng = points[i].lng.toFixed(6);
					    var power = "未知";
					    if (points[i].power!=null) {
					    	power = points[i].power;
						}
					    var memberName = "未知";
					    if(points[i].memberName!=null){
					    	memberName = points[i].memberName;
					    }
					    var documentNo = "未知";
					    if(points[i].documentNo!=null){
					    	documentNo = points[i].documentNo;
					    }
					    var mileage = "未知";
					    if(points[i].mileage!=null){
					    	mileage = points[i].mileage;
					    }
//					    var item = "<ul class='clearfix carInfro1'><li class='pull-left'>车牌号：</li><li class='pull-left'>"+carPlateNo+"</li></ul>",
//					    row0 = "<ul class='clearfix carInfro1'><li class='pull-left'>监控时间：</li><li class='pull-left'>"+new Date(points[i].time*1000).Format("yyyy-MM-dd HH:mm:ss")+"</li></ul>",
//					    row1 = "<ul class='clearfix carInfro1'><li class='pull-left'>行驶速度：</li><li class='pull-left'>"+points[i].speed+"km/h</li></ul>",
//					    row2 = "<ul class='clearfix carInfro1'><li class='pull-left'>行驶里程：</li><li class='pull-left'>"+mileage+"</li></ul>",
//					    row3 = "<ul class='clearfix carInfro1'><li class='pull-left'>航向角：</li><li class='pull-left'>"+direct+"</li></ul>",
//					    row4 = "<ul class='clearfix carInfro1'><li class='pull-left'>车辆打火状态：</li><li class='pull-left'>"+status+"</li></ul>",
//					    row5 = "<ul class='clearfix carInfro1'><li class='pull-left'>车辆状态：</li><li class='pull-left'>"+useStatus+"</li></ul>",
//					    row6 = "<ul class='clearfix carInfro1'><li class='pull-left'>车辆使用人：</li><li class='pull-left'>"+memberName+"</li></ul>",
//					    row7 = "<ul class='clearfix carInfro1'><li class='pull-left'>单据号：</li><li class='pull-left'>"+documentNo+"</li></ul>"
//					    row8 = "<ul class='clearfix carInfro1'><li class='pull-left'>车辆纬度：</li><li class='pull-left'>"+lat+"</li></ul>",
//					    row9 = "<ul class='clearfix carInfro1'><li class='pull-left'>车辆经度：</li><li class='pull-left'>"+lng+"</li></ul>",
//					    row10 = "<ul class='clearfix carInfro1'><li class='pull-left'>剩余电/油量：</li><li class='pull-left'>"+power+"</li></ul>"
//					    var html = item+row0+row1+row2+row3+row4+row5+row6+row7+row8+row9+row10;
						var item = "<div class='head-infro'><div class='left'>"+carPlateNo+"</div>";
						var row0 = "<div class='right'>"+new Date(points[i].time*1000).Format("yyyy-MM-dd HH:mm:ss")+"</div></div>";
						var row1 = "<div class='echart-content' id='carTrackSpeedCharts'><div></div><h1></h1></div>";
						var row2 = "<div class='text-infro-content'><h2 class='title'>航向角</h2><div class='details'>"+direct+"</div></div>";
						var row3 = "<div class='text-infro-content'><h2 class='title'>车辆打火状态</h2><div class='details'>"+status+"</div></div>";
						var row4 = "<div class='text-infro-content'><h2 class='title'>车辆状态</h2><div class='details'>"+useStatus+"</div></div>";
						var row5 = "<div class='text-infro-content'><h2 class='title'>车辆使用人</h2><div class='details'>"+memberName+"</div></div>";
						var row6 = "<div class='text-infro-content'><h2 class='title'>单据号</h2><div class='details'>"+documentNo+"</div></div>";
						var row7 = "<div class='text-infro-content'><h2 class='title'>车辆经纬度</h2><div class='details'>"+lng+","+lat+"</div></div>"
						var row8 = "<div class='text-infro-content'><h2 class='title'>剩余电/油量</h2><div class='details'>"+power+"</div></div>"
						var row9 = "<div class='text-infro-content'><h2 class='title'>位置</h2><div class='details'>"+points[i].address+"</div></div>"
						var html = item+row0+row1+row2+row3+row4+row5+row6+row7+row8+row9; 
						//轨迹上每个点信息展示对象
					    pointInfo = {lng:points[i].lng,lat:points[i].lat,speed:points[i].speed,html:html,pauseTime:1};
					    landmarkPois.push(pointInfo);
					}
					$('.carTrack-info').css("width","18%");
					$('.carTrack-info').html(pointInfo.html);
					$('#trackMap').css("width","82%");
					car = new BMap.Marker(points[0], {
						icon : new BMap.Icon(carTrack.appPath
								+ '/res/img/car/car_red.png', new BMap.Size(32, 48), {
							imageOffset : new BMap.Size(0, 0)
						})
					});
					carTrackPoints=points;
					var lushuIcon = new BMap.Icon(carTrack.appPath+ '/res/img/car/car_red.png', new BMap.Size(32, 48), { anchor: new BMap.Size(30, 20) });
					carMark=car;
					//小车移动
					var lushu = new BMapLib.LuShu(map, points, {
						defaultContent: carPlateNo,
						speed: 60,//路书速度
						autoView: true, //是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
						icon: lushuIcon,
						enableRotation: true, //是否设置marker随着道路的走向进行旋转
						landmarkPois:landmarkPois//显示的特殊点，似乎是必选参数，可以留空，据说要和距原线路10米内才会暂停，这里就用原线的点
					});    
					globalLuShu = lushu;

					if(flag){
						lushu.start();
					}else{
						map.addOverlay(car);
					}
					//起点设置
					var startIcon = new BMap.Marker(points[0], {
						icon : new BMap.Icon(carTrack.appPath+ '/res/img/car/start_icon.png', 
								new BMap.Size(48,48), {imageOffset : new BMap.Size(0, 0)}
						)
					});
					startIcon.setZIndex(999);
					map.addOverlay(startIcon);
					//终点设置
					var endIcon = new BMap.Marker(points[points.length-1], {
						icon : new BMap.Icon(carTrack.appPath+ '/res/img/car/end_icon.png', 
								new BMap.Size(48,48), {imageOffset : new BMap.Size(0, 0)}
						)
					});
					endIcon.setZIndex(999);
					map.addOverlay(endIcon);
					
					var symbol = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_CLOSED_ARROW, {
						scale: 0.6,//图标缩放大小
						rotation: 0,
						strokeOpacity:1,
						strokeColor:'#fff',//设置矢量图标的线填充颜色
						strokeWeight: '1.5',//设置线宽
					});
					var iconSequence = new BMap.IconSequence(
							symbol,
							'100%', //offset为符号相对于线起点的位置，取值可以是百分比也可以是像素位置，默认为"100%"
							'3%' //repeat为符号在线上重复显示的距离，可以是百分比也可以是距离值，同时设置repeat与offset时，以repeat为准
					);
					var polyline = new BMap.Polyline(polylinePointsArray, { 
						enableEditing: false,//是否启用线编辑，默认为false
						enableClicking: false,//是否响应点击事件，默认为true
						icons:[iconSequence],
						strokeWeight:'5',//折线的宽度，以像素为单位
						strokeOpacity: 0.9,//折线的透明度，取值范围0 - 1
						strokeColor:"#4e4ae0" //折线颜色
					});
					map.addOverlay(polyline);
					
				},
				getHistory : function(entity_name,start_time,end_time) {
					$.ajax({
						url : carTrack.appPath
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
									var queryPoints = result.data.points;
									var bpoints = new Array();
									for(var i=queryPoints.length-1;i>=0;i--){
										var point = new Object();//lng,lat
										point.lng = queryPoints[i].location[0];
										point.lat = queryPoints[i].location[1];
										point.time = queryPoints[i].loc_time;//时间
										point.speed = queryPoints[i].speed;//速度
										point.direction = queryPoints[i].direction;//航向角
										point.carStatus = queryPoints[i].carStatus;//车辆打火状态;
										point.carUserageStatus = queryPoints[i].carUserageStatus;//车辆使用状态
										point.memberName = queryPoints[i].memberName;//车辆使用人
										point.phone = queryPoints[i].phone;//使用人手机号
										point.documentNo = queryPoints[i].documentNo;//单据号（订单或者调度单号）
										point.power = queryPoints[i].power;//剩余电油量
										point.address = queryPoints[i].address;//地址
										if (i>=1) {
											if (queryPoints[i].mileage==null) {
												point.mileage = queryPoints[i-1].mileage;//行驶里程
											}else{
												point.mileage = queryPoints[i].mileage;
											}
										}else{
											point.mileage = queryPoints[i].mileage;
										}
										bpoints.push(point);
									}
									carTrackMap.clearOverlays();
									carTrack.mapInit(bpoints,true);
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "没有数据");
								}
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + result.message);
							}
						}
					});
				},
				
				/**
		         * 日期转时间戳
		         * @param   {[type]}   str_time [字符串日期 格式2014-08-29 00:00:00]
		         * @return  {[type]}            [时间戳]
		         */
				 timeFormaToUnixtime: function (str_time) {
		            var new_str = str_time.replace(/:/g, '-');
		            new_str = new_str.replace(/ /g, '-');
		            var arr = new_str.split("-");
		            var strtotime = 0;
		            var datum = new Date(Date.UTC(arr[0], arr[1] - 1, arr[2], arr[3] - 8, arr[4], arr[5]));
		            if (datum != null && typeof datum != 'undefined') {
		                strtotime = datum.getTime() / 1000;
		            }
		            return strtotime;
		        },
		        /**
		         * 时间戳转日期
		         * @param   {[type]}   unixtime [时间戳]
		         * @return  {[type]}            [时间戳对应的日期]
		         */
		        unixtimeToDateTime: function (unixtime) {
		            var timestr = new Date(parseInt(unixtime) * 1000);
		            var datetime = this.date_format(timestr, 'yyyy-MM-dd hh:mm:ss');
		            return datetime;
		        },
			};
			// 定义一个控件类，即function
			function ZoomControlPlay() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(134, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControlPlay.prototype = new BMap.Control();
			ZoomControlPlay.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("div");
				var span = document.createElement("span");
				// 添加文字说明
				span.appendChild(document.createTextNode("播放      |"));
				button.className = "carC";
				span.className = "glyphicon glyphicon-play";
				button.style.padding = "3px 10px 3px 0px";
				span.style.color = "black";
				// 设置样式
				button.style.cursor = "pointer";
				button.style.backgroundColor = "white";
				button.style.color = "black";
				button.appendChild(span);
				
			   
				
				// 绑定事件
				button.onclick = function(e) {
					trackControl.paly();//播放
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				// 将DOM元素返回
				return button;
			};
			// 定义一个控件类，即function
			function ZoomControlPause() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(82, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControlPause.prototype = new BMap.Control();
			ZoomControlPause.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("div");
				var span = document.createElement("span");
				// 添加文字说明
				span.appendChild(document.createTextNode("暂停      |"));
				button.className = "carC";
				span.className = "glyphicon glyphicon-pause";
				button.style.padding = "3px 10px 3px 0px";
				span.style.color = "black";
				// 设置样式
				button.style.cursor = "pointer";
				button.style.backgroundColor = "white";
				button.style.color = "black";
				button.appendChild(span);
				// 绑定事件
				button.onclick = function(e) {
					trackControl.pause();//暂停
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				// 将DOM元素返回
				return button;
			};
			// 定义一个控件类，即function
			function ZoomControlStop() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(30, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControlStop.prototype = new BMap.Control();
			ZoomControlStop.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("div");
				var span = document.createElement("span");
				// 添加文字说明
				span.appendChild(document.createTextNode("重置"));
				button.className = "carC";
				span.className = "glyphicon glyphicon-repeat";
				button.style.padding = "3px 10px 3px 0px";
				span.style.color = "black";
				// 设置样式
				button.style.cursor = "pointer";
				button.style.backgroundColor = "white";
				button.style.color = "black";
				button.appendChild(span);
				// 绑定事件
				button.onclick = function(e) {
					trackControl.stop();//重置
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				// 将DOM元素返回
				return button;
			};
			// 定义一个控件类，即function
			function ZoomControlHide() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(120, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControlHide.prototype = new BMap.Control();
			ZoomControlHide.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("div");
				var span = document.createElement("span");
				// 添加文字说明
				span.appendChild(document.createTextNode("隐藏信息窗口      |"));
				button.className = "carC";
				span.className = "	glyphicon glyphicon-eye-close";
				button.style.padding = "3px 20px 3px 0px";
				span.style.color = "black";
				// 设置样式
				button.style.cursor = "pointer";
				button.style.backgroundColor = "white";
				button.style.color = "black";
				button.appendChild(span);
				// 绑定事件
				button.onclick = function(e) {
					trackControl.hide();//隐藏信息窗口
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				// 将DOM元素返回
				return button;
			};
			// 定义一个控件类，即function
			function ZoomControlShow() {
				// 设置默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(20, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			ZoomControlShow.prototype = new BMap.Control();
			ZoomControlShow.prototype.initialize = function(map) {
				// 创建一个DOM元素
				var button = document.createElement("div");
				var span = document.createElement("span");
				// 添加文字说明
				span.appendChild(document.createTextNode("展示信息窗口"));
				button.className = "carC";
				span.className = "glyphicon glyphicon-eye-open";
				button.style.padding = "3px 15px 3px 0";
				span.style.color = "black";
				// 设置样式
				button.style.cursor = "pointer";
				button.style.backgroundColor = "white";
				button.style.color = "black";
				button.appendChild(span);
				// 绑定事件
				button.onclick = function(e) {
					trackControl.show();//展示信息窗口
				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(button);
				// 将DOM元素返回
				return button;
			};
			// 按钮操作
			var trackControl = {
				paly : function() {
					if(globalLuShu){
						carTrackMap.removeOverlay(carMark);
						globalLuShu.start();
					}
				},
				pause : function() {
					if(globalLuShu){
						globalLuShu.pause();
					}
				},
				stop : function() {
					if(globalLuShu){
			    		globalLuShu.stop();
			    		carTrackMap.clearOverlays();
						carTrack.mapInit(carTrackPoints,false);
			    	}
				},
				hide : function() {
					if(globalLuShu){
						globalLuShu.hideInfoWindow();
			    	}
				},
				show : function() {
					if(globalLuShu){
						globalLuShu.showInfoWindow();
			    	}
				}
			}
			return carTrack;
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
