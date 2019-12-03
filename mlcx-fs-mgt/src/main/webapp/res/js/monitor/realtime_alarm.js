define([],function() {
			var tempCar;
			var realtimeAlarm = {
				appPath : getPath("app"),
				init : function() {
					realtimeAlarm.mapInit();
					realtimeAlarm.pageList(null);	//null代表查询全部的超市报警车辆信息，没有区分时段
				},
				pageList : function(type) {
					$.ajax({
						url : realtimeAlarm.appPath
								+ "/realTimeAlarm/pageListRealTimeAlarm.do",
						type : "post",
						data : {"type":type},
						success : function(res) {
							if (res != "") {
								var carStatusListArr = eval(res);
								var carStatusList = new Array();
								var carStatus = new Object();
								var points = new Array();
								var point;
								for (var i = 0; i < carStatusListArr.length; i++) {
									carStatus = carStatusListArr[i];
									carStatusList.push(carStatus);
									point = new Object();
									point.lng = carStatusListArr[i].longitude;
									point.lat = carStatusListArr[i].latitude;
									points.push(point);
								}
								if(carStatusListArr.length==1&&carStatusListArr[0].flag=="1"){
									realtimeAlarm.showCarStatusMap(carStatusList,points,type);
								}else{
									realtimeAlarm.showMap(carStatusList,points,type);
								}
								tempCar=carStatusList;
							} else {
								
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无满足查询条件的车辆！",function(){
									realtimeAlarm.mapInit();
								});
								$(".modal").css("z-index","10000");
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
						var zoom = realtimeAlarm.getZoom(maxLng, minLng, maxLat,
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
					var map = new BMap.Map("realTime-map"); // 创建地图实例
					map.centerAndZoom(new BMap.Point(103.388611,35.563611),5);// 初始化地图，设置中心点坐标和地图级别
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
					var freeHourCtrl = new ZoomControl2();  
					// 添加到地图当中  
					map.addControl(freeHourCtrl);
				},
				showCarStatusMap: function(carStatusList, points,type) {
					var map = new BMap.Map("realTime-map"); // 创建地图实例
					var zoom = realtimeAlarm.setZoom(points, map);
					map.centerAndZoom(new BMap.Point(zoom.cenLng, zoom.cenLat),
							17);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
					//设置地图全屏按钮和退出全屏按钮
					// 创建控件实例  
					var fullScreenCtrl = new ZoomControl();  
					// 添加到地图当中  
					map.addControl(fullScreenCtrl);
					var escFullScreenCtrl = new ZoomControl1();  
					// 添加到地图当中  
					map.addControl(escFullScreenCtrl);
					var freeHourCtrl = new ZoomControl2();  
					// 添加到地图当中  
					map.addControl(freeHourCtrl);
					if(type!=null){
						$('select[name=selectFreeHour] option[value='+type+']').attr("selected", true); 
						//$('select[name=selectFreeHour][value='+type+']').attr("selected", 'selected');
					}
					if($("input[name=carDetailDivRealTimeAlarm]").val()==1){
						$(".btn").addClass("buttonFull");
					}
					realtimeAlarm.addMarker(carStatusList, map);
				},
				showMap : function(carStatusList, points,type) {
					var map = new BMap.Map("realTime-map"); // 创建地图实例
					var zoom = realtimeAlarm.setZoom(points, map);
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
					var freeHourCtrl = new ZoomControl2();  
					// 添加到地图当中  
					map.addControl(freeHourCtrl);
					if(type!=null){
						$('select[name=selectFreeHour] option[value='+type+']').attr("selected", true); 
						//$("select[name=selectFreeHour] option[value=4]").attr("selected", true); 
						//$('select[name=selectFreeHour][value=4]').attr("selected", 'selected');
					}
					if($("input[name=carDetailDivRealTimeAlarm]").val()==1){
						$(".btn").addClass("buttonFull");
					}
					realtimeAlarm.addMarker(carStatusList, map);
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
						if(monitor.realTimeAlarmType==1){
							var realTimeAlarmType = "非订单中车辆异常点火";
						}else if(monitor.realTimeAlarmType==2){
							var realTimeAlarmType = "车辆闲置时间超过24小时";
						}else if(monitor.realTimeAlarmType==3){
							var realTimeAlarmType = "车辆闲置时间超过48小时";
						}else if(monitor.realTimeAlarmType==4){
							var realTimeAlarmType = "车辆闲置时间超过72小时";
						}
						if(monitor.carStatus==1){
							var status = "已启动";
						}else{
							var status = "已熄火";
						}
						if(monitor.rangeMileage==null){
							monitor.rangeMileage=0;
						}
						var content = "";
							var item = "<h3 style='text-align:center;'>车辆信息</h3>",
							row0 = "<ul class='clearfix carInfro'><li class='pull-left'>车牌号：</li><li class='pull-left'>"+monitor.carPlateNo+"</li></ul>",
							row1 = "<ul class='clearfix carInfro'><li class='pull-left'>速度：</li><li class='pull-left'>"+monitor.speed+"km/h</li></ul>",
							row2 = "<ul class='clearfix carInfro'><li class='pull-left'>车辆状态：</li><li class='pull-left'>"+status+"</li></ul>",
							row3 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余电量：</li><li class='pull-left'>"+monitor.power+"%</li></ul>",
							row4 = "<ul class='clearfix carInfro'><li class='pull-left'>剩余里程：</li><li class='pull-left'>"+monitor.rangeMileage+"km</li></ul>",
							row5 = "<ul class='clearfix carInfro'><li class='pull-left'>报警类型：</li><li class='pull-left'>"+realTimeAlarmType+"</li></ul>"
							content = item+row0+row1+row2+row3+row4+row5;
//							if(monitor.realTimeAlarmType==1){
//								myIcon = new BMap.Icon(realtimeAlarm.appPath+ '/res/img/car/car_red.png', new BMap.Size(32, 32));
//							}else
							if(monitor.realTimeAlarmType==2){
								myIcon = new BMap.Icon(realtimeAlarm.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32, 32));
								marker = new BMap.Marker(point[i], {
									icon : myIcon
								}); // 按照地图点坐标生成标记
								//marker.setOffset(offset:30);
								map.addOverlay(marker);
								realtimeAlarm.addClickHandler(content,marker,map,240);
							}else if(monitor.realTimeAlarmType==3){
								myIcon = new BMap.Icon(realtimeAlarm.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
								marker = new BMap.Marker(point[i], {
									icon : myIcon
								}); // 按照地图点坐标生成标记
								//marker.setOffset(offset:30);
								map.addOverlay(marker);
								realtimeAlarm.addClickHandler(content,marker,map,240);
							}else if(monitor.realTimeAlarmType==4){
								myIcon = new BMap.Icon(realtimeAlarm.appPath+ '/res/img/car/car_yellow.png', new BMap.Size(32, 32));
								marker = new BMap.Marker(point[i], {
									icon : myIcon
								}); // 按照地图点坐标生成标记
								//marker.setOffset(offset:30);
								map.addOverlay(marker);
								realtimeAlarm.addClickHandler(content,marker,map,240);
							}
							
					}
				},
				addClickHandler : function(content, marker,map,height) {
					marker.addEventListener("click", function(e) {
						if($("input[name=carDetailDivRealTimeAlarm]").val()==1){
							//判断有没有div,有的话先去掉、
							var div1=document.getElementById("carDetail");
							if(div1){
								div1.parentNode.removeChild(div1);
							}
							// 定义一个控件类，即function  
							function ZoomControl3(){  
							    // 设置默认停靠位置和偏移量  
							    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;  
							    this.defaultOffset = new BMap.Size(10, 100);
							}
							// 通过JavaScript的prototype属性继承于BMap.Control  
							ZoomControl3.prototype = new BMap.Control();
							ZoomControl3.prototype.initialize = function(map){  
							    // 创建一个DOM元素  
							    var div = document.createElement("div");
							    div.id="carDetail";
//							    // 添加文字说明  
							  //  type
							    div.innerHTML ='<img id="carDetailImg" style="position: absolute; top: 12px; width: 10px; height: 10px; cursor: pointer; z-index: 10000; margin-left: 90%;" src="http://api0.map.bdimg.com/images/iw_close1d3.gif" >'+content;
//							    // 设置样式  
//							    selectbtn.style.cursor = "pointer";  
//							    selectbtn.style.border = "1px solid gray";  
							    div.style.backgroundColor = "white"; 
							    // 添加DOM元素到地图中  
							    map.getContainer().appendChild(div);  
							    // 将DOM元素返回  
							    return div;  
							} 
							var carDetailCtrl = new ZoomControl3();  
							// 添加到地图当中  
							map.addControl(carDetailCtrl);
							$("#carDetail ul").removeClass("carInfro");
							$("#carDetail ul").addClass("carInfroFull");
							var img = document.getElementById("carDetailImg");
							img.style.width="30px";
							img.style.height="30px";
						    img.onclick=function img(){
						    	$("#carDetail").attr("style","display:none");
						    	map.removeControl(carDetailCtrl);
						    };
						}else{
							realtimeAlarm.openInfo(content,e,map,height);
						}
					});
				},
				openInfo : function(content,e,map,height) {
					var opts = {
						width : 240,     // 信息窗口宽度
						height: height,// 信息窗口高度
					};
					var p = e.target;
					var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
					var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
					map.openInfoWindow(infoWindow, point); // 开启信息窗口
				}
				
			};
			
			//放大object
			
			
			var fillObject = {
				fillToSco:function(){
					$("body,html").scrollTop(0);
				       /*高为屏幕的高*/
			    	 $("body").css({
			    		 "overflow-y":"hidden"
			    	 })
			    	 $(".textZln").hide();
			    	 $("#realTime-map").addClass("fillScree");
			    	  $("#realTime-map").css({
				           "height":$(window).height(),
				           "width":$(window).width(),
				        });
				     $(window).resize(function(){
				    	 	$("#realTime-map").css({
					           "height":$(window).height(),
					           "width":$(window).width(),
					        });
				       })
					    var points = new Array();
					    for (var i = 0; i < tempCar.length; i++) {
							point = new Object();
							point.lng = tempCar[i].longitude;
							point.lat = tempCar[i].latitude;
							points.push(point);
					    }
					    realtimeAlarm.showMap(tempCar,points);
//				        realtimeAlarm.mapInit();
//						realtimeAlarm.pageList();
						//设置carDetailDivRealTimeAlarm的值为1
						$("input[name=carDetailDivRealTimeAlarm]").val(1);
				},
				scoTofill:function(){
					$("body,html").scrollTop(0);
				       /*高为屏幕的高*/
			    	 $("body").css({
			    		 "overflow-y":"auto"
			    	 })
			    	 $(".textZln").show();
			    	 $("#realTime-map").removeClass("fillScree");
			    	  $("#realTime-map").css({
				           "height":"550px",
				           "width":"100%",
				        });
				     $(window).resize(function(){
				    	 	$("#realTime-map").css({
						           "height":"550px",
						           "width":"100%",
						        });
				       })
				       var points = new Array();
					   for (var i = 0; i < tempCar.length; i++) {
							point = new Object();
							point.lng = tempCar[i].longitude;
							point.lat = tempCar[i].latitude;
							points.push(point);
					    }
					    realtimeAlarm.showMap(tempCar,points);
						realtimeAlarm.pageList();
						$(".btn").removeClass("buttonFull");
						$("input[name=carDetailDivRealTimeAlarm]").val(0);
				}
			}
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
//			    button.addClass("buttonFull");
			//    button.className="buttonFull";
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
			   // button.className="buttonFull";
//			    button.addClass("buttonFull");
			    // 绑定事件，点击一次放大两级  
			    button.onclick = function(e){
			    	fillObject.scoTofill();//缩小
			    };
			    // 添加DOM元素到地图中  
			    map.getContainer().appendChild(button);  
			    // 将DOM元素返回  
			    return button;  
			} 
			// 定义一个控件类，即function  
			function ZoomControl2(){  
			    // 设置默认停靠位置和偏移量  
			    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;  
			    this.defaultOffset = new BMap.Size(20, 10);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control  
			ZoomControl2.prototype = new BMap.Control();
			ZoomControl2.prototype.initialize = function(map){  
			    // 创建一个DOM元素  
			    var selectbtn = document.createElement("select");
			    selectbtn.name="selectFreeHour";
			    selectbtn.className="btn";
//			    // 添加文字说明  
			  //  type
			    selectbtn.innerHTML = "<option value=''>选择闲置时间</option><option value='2'>闲置24小时</option><option value='3'>闲置48小时</option><option value='4'>闲置72小时</option>"
//			    // 设置样式  
			    selectbtn.style.cursor = "pointer";  
			    selectbtn.style.border = "1px solid gray";  
			    selectbtn.style.backgroundColor = "white";  
			    // 绑定事件， 
//			    selectbtn.addClass("buttonFull");
			    //selectbtn.className="buttonFull";
			    selectbtn.onchange = function(){
			    	var type=$("select[name=selectFreeHour]").val();
			    	 realtimeAlarm.mapInit();
					realtimeAlarm.pageList(type);
			    };
			    // 添加DOM元素到地图中  
			    map.getContainer().appendChild(selectbtn);  
			    // 将DOM元素返回  
			    return selectbtn;  
			} 
			return realtimeAlarm;
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
