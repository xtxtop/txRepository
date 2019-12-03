define([],function() {
			var tempCar;
			var noOrder = {
				appPath : getPath("app"),
				init : function() {
					noOrder.mapInit();
//					noOrder.pageList(null);
					noOrder.lushu1();
					noOrder.lushu2();
					noOrder.lushu3();
					//关闭提交
					$("#warningOffBtnclo").click(function(){
						$("#warningOffBtnclo").prop("disabled",true);
						noOrder.offFormSubclo();
		            });
					
					$("#warningOffCancelclo").click(function(){
						 $("#OffWarningModalclo").modal("hide");
		            });
					
					$("#OffWarningModalclo").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=offForm]");
		            	//form.clearForm();
		            	form.resetForm();
		            	form.find("input[name=warningNo]").val("");
		            });
				},

				//下线操作
				offFormSubclo: function () {
		        	var form = $("form[name=offForm]");
					form.ajaxSubmit({
						url:noOrder.appPath+"/warning/updateWarning.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							 
							  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭成功！",function(){
								  $("#OffWarningModalclo").modal("hide");
								  $(".bootbox").modal("hide");
								  form.find("textarea[name='closedMemo']").html("");
								  $("#warningOffBtnclo").prop("disabled",false);
								  noOrder.pageList(null);
							  });
							   
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭失败！",function(){
									 $("#OffWarningModalclo").modal("hide");
									 $(".bootbox").modal("hide");
									 $("#warningOffBtnclo").prop("disabled",false);
								});
								
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='closedMemo']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入备注！");
								$("#warningOffBtnclo").prop("disabled",false);
								return false;
							}
						}
						});
					},
				pageList : function(type) {
					$.ajax({
						url : noOrder.appPath
								+ "/noOrder/pageListnoOrder.do",
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
									noOrder.showCarStatusMap(carStatusList,points,type);
								}else{
									noOrder.showMap(carStatusList,points,type);
								}
								tempCar = carStatusList;
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无满足查询条件的车辆！",function(){
									noOrder.mapInit();
									noOrder.pageList(null);
								});
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
						var zoom = noOrder.getZoom(maxLng, minLng, maxLat,
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
					var map = new BMap.Map("ln-map"); // 创建地图实例
					globalMap = map;
					map.centerAndZoom(new BMap.Point(114.093277,22.55212),20);// 初始化地图，设置中心点坐标和地图级别
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
					map.enableScrollWheelZoom();
					map.centerAndZoom(new BMap.Point(114.127053,22.607782), 20);
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
				                icon  : new BMap.Icon(noOrder.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 70,
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
				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00001",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(noOrder.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 80,
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
				                map.setViewport(arrPois);
				                
				                lushu = new BMapLib.LuShu(map,arrPois,{
				                defaultContent:"粤B00002",
				                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				                icon  : new BMap.Icon(noOrder.appPath+ '/res/img/car/car_red.png', new BMap.Size(32,32)),
				                speed: 90,
				                enableRotation:true,//是否设置marker随着道路的走向进行旋转
				                landmarkPois: []});          
				                lushu.start();
				              }
				        }
				    });
				    drv.search('龙岗汽车总站', '深圳平湖汽车站');
				},
				showCarStatusMap: function(carStatusList, points,type) {
					var map = new BMap.Map("ln-map"); // 创建地图实例
					var zoom = noOrder.setZoom(points, map);
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
					if($("input[name=carDetailDivOrMarker]").val()==1){
						$(".btn").addClass("buttonFull");
					}
					noOrder.addMarker(carStatusList, map);
				},
				showMap : function(carStatusList, points,type) {
					var map = new BMap.Map("ln-map"); // 创建地图实例
					var zoom = noOrder.setZoom(points, map);
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
//					var freeHourCtrl = new ZoomControl2();  
					// 添加到地图当中  
					//map.addControl(freeHourCtrl);
					if(type!=null){
						$('select[name=selectFreeHour] option[value='+type+']').attr("selected", true); 
						//$("select[name=selectFreeHour] option[value=4]").attr("selected", true); 
						//$('select[name=selectFreeHour][value=4]').attr("selected", 'selected');
					}
					if($("input[name=carDetailDivOrMarker]").val()==1){
						$(".btn").addClass("buttonFull");
					}
					noOrder.addMarker(carStatusList, map);
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
							var noOrderType = "非订单中车辆异常点火";
						}
//						else if(monitor.noOrderType==2){
//							var noOrderType = "车辆闲置时间超过24小时";
//						}else if(monitor.noOrderType==3){
//							var noOrderType = "车辆闲置时间超过48小时";
//						}else if(monitor.noOrderType==4){
//							var noOrderType = "车辆闲置时间超过72小时";
//						}
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
							row5 = "<ul class='clearfix carInfro'><li class='pull-left'>报警类型：</li><li class='pull-left'>"+noOrderType+"</li></ul>"
							row6 = "<ul class='clearfix carInfro'><li class='pull-center'><button class='closeCar' id="+monitor.warningNo+" onclick=closeWarning('"+monitor.warningNo+"');>关闭警告</button> </li><li class='pull-left'></li></li></ul>"
							content = item+row0+row1+row2+row3+row4+row5+row6;
							if(monitor.realTimeAlarmType==1){
								myIcon = new BMap.Icon(noOrder.appPath+ '/res/img/car/car_red.png', new BMap.Size(32, 32));
								marker = new BMap.Marker(point[i], {
									icon : myIcon
								}); // 按照地图点坐标生成标记
								//marker.setOffset(offset:30);
								map.addOverlay(marker);
								noOrder.addClickHandler(content,marker,map,240);
							}
							
//							else if(monitor.noOrderType==2){
//								myIcon = new BMap.Icon(noOrder.appPath+ '/res/img/car/car_blue.png', new BMap.Size(32, 32));
//							}else if(monitor.noOrderType==3){
//								myIcon = new BMap.Icon(noOrder.appPath+ '/res/img/car/car_green.png', new BMap.Size(32, 32));
//							}else if(monitor.noOrderType==4){
//								myIcon = new BMap.Icon(noOrder.appPath+ '/res/img/car/car_yellow.png', new BMap.Size(32, 32));
//							}
							
					}
					
				},
				
				addClickHandler : function(content, marker,map,height) {
					marker.addEventListener("click", function(e) {
						if($("input[name=carDetailDivOrMarker]").val()==1){
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
							noOrder.openInfo(content,e,map,height);
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
					
					var carListBox = $("#carListBox");
					if(carListBox){
						$("#carListBox").remove();
					}
					
					$("body,html").scrollTop(0);
				       /*高为屏幕的高*/
			    	 $("body").css({
			    		 "overflow-y":"hidden"
			    	 })
			    	 $(".textZln").hide();
			    	 $("#ln-map").addClass("fillScree");
			    	  $("#ln-map").css({
				           "height":$(window).height(),
				           "width":$(window).width(),
				        });
				     $(window).resize(function(){
				    	 	$("#ln-map").css({
					           "height":$(window).height(),
					           "width":$(window).width(),
					        });
				       })
//				       var points = new Array();
//				       noOrder.showMap(null,points);
				       noOrder.mapInit();
				     noOrder.lushu1();
						noOrder.lushu2();
						noOrder.lushu3();
						//设置carDetailDivOrMarker的值为1
//						$("input[name=carDetailDivOrMarker]").val(1);
//						var nodes = $("#mainConsoleTabs");
//						var node2 = $(".wrapper");
//						var docHeight = $(window).height(); //获取窗口高度  
//						nodes.append("<div id='carListBox' style='border:1px solid gray;position:absolute;width:200px;height:auto;max-height:"+docHeight+"px;overflow:auto;background-color:white;top:30%;left:2%;z-index:2147483647;text-align:center;'><p style='font-size:18px;font-weight:bold;'>警告列表</p></div>");
//						var carListBox = $("#carListBox");
//						$.each(tempCar,function(i,rowData){
//							if(rowData.realTimeAlarmType == 1){
//								var result = "<div style='display:block;text-align:center;border-top:1px solid #000000;font-size:18px;line-height:20px;height:20px;'>"+rowData.carPlateNo+"</div>";
//								carListBox.append(result);
//							}
//						});
					//	
				},
				
			
				
				scoTofill:function(){
					$("body,html").scrollTop(0);
				       /*高为屏幕的高*/
			    	 $("body").css({
			    		 "overflow-y":"auto"
			    	 })
			    	 $(".textZln").show();
			    	 $("#ln-map").removeClass("fillScree");
			    	  $("#ln-map").css({
				           "height":"550px",
				           "width":"100%",
				        });
				     $(window).resize(function(){
				    	 	$("#ln-map").css({
						           "height":"550px",
						           "width":"100%",
						        });
				       })
//				       var points = new Array();
//				       noOrder.showMap(null,points);
//				       noOrder.pageList();
				       noOrder.mapInit();
				     noOrder.lushu1();
						noOrder.lushu2();
						noOrder.lushu3();
						$(".btn").removeClass("buttonFull");
						$("input[name=carDetailDivOrMarker]").val(0);
						
					$("#carListBox").remove();	
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
			
			
			/**
			 * 自定义覆盖层升级版
			 */
			// 复杂的自定义覆盖物
		    function ComplexCustomOverlay(point, text, mouseoverText,temp){
		      this._point = point;
		      this._text = text;
		      this._overText = mouseoverText;
		      mapNode = temp;
		    }
		    ComplexCustomOverlay.prototype = new BMap.Overlay();
		    ComplexCustomOverlay.prototype.initialize = function(map){
		      this._map = map;
		      var div = this._div = document.createElement("div");
		      div.style.position = "absolute";
		      div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
		      div.style.backgroundColor = "#EE5D5B";
		      div.style.border = "1px solid #BC3B3A";
		      div.style.color = "white";
		      div.style.height = "18px";
		      div.style.padding = "2px";
		      div.style.lineHeight = "18px";
		      div.style.whiteSpace = "nowrap";
		      div.style.MozUserSelect = "none";
		      div.style.fontSize = "12px"
		      var span = this._span = document.createElement("span");
		      div.appendChild(span);
		      span.appendChild(document.createTextNode(this._text));      
		      var that = this;

		      var arrow = this._arrow = document.createElement("div");
		      arrow.style.background = "url(http://map.baidu.com/fwmap/upload/r/map/fwmap/static/house/images/label.png) no-repeat";
		      arrow.style.position = "absolute";
		      arrow.style.width = "11px";
		      arrow.style.height = "10px";
		      arrow.style.top = "22px";
		      arrow.style.left = "10px";
		      arrow.style.overflow = "hidden";
		      div.appendChild(arrow);
		     
		      div.onmouseover = function(){
		        this.style.backgroundColor = "#6BADCA";
		        this.style.borderColor = "#0000ff";
		        this.getElementsByTagName("span")[0].innerHTML = that._overText;
		        arrow.style.backgroundPosition = "0px -20px";
		      }

		      div.onmouseout = function(){
		        this.style.backgroundColor = "#EE5D5B";
		        this.style.borderColor = "#BC3B3A";
		        this.getElementsByTagName("span")[0].innerHTML = that._text;
		        arrow.style.backgroundPosition = "0px 0px";
		      }

		      mapNode.getPanes().labelPane.appendChild(div);
		      
		      return div;
		    }
		    
		    ComplexCustomOverlay.prototype.draw = function(){
		        var map = this._map;
		        var pixel = map.pointToOverlayPixel(this._point);
		        this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
		        this._div.style.top  = pixel.y - 30 + "px";
		      }
			// 定义一个控件类，即function  
//			function ZoomControl2(){  
//			    // 设置默认停靠位置和偏移量  
//			    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;  
//			    this.defaultOffset = new BMap.Size(20, 10);
//			}
//			// 通过JavaScript的prototype属性继承于BMap.Control  
//			ZoomControl2.prototype = new BMap.Control();
//			ZoomControl2.prototype.initialize = function(map){  
//			    // 创建一个DOM元素  
//			    var selectbtn = document.createElement("select");
//			    selectbtn.name="selectFreeHour";
//			    selectbtn.className="btn";
////			    // 添加文字说明  
//			  //  type
//			    selectbtn.innerHTML = "<option value=''>选择闲置时间</option><option value='2'>闲置24小时</option><option value='3'>闲置48小时</option><option value='4'>闲置72小时</option>"
////			    // 设置样式  
//			    selectbtn.style.cursor = "pointer";  
//			    selectbtn.style.border = "1px solid gray";  
//			    selectbtn.style.backgroundColor = "white";  
//			    // 绑定事件， 
////			    selectbtn.addClass("buttonFull");
//			    //selectbtn.className="buttonFull";
//			    selectbtn.onchange = function(){
//			    	var type=$("select[name=selectFreeHour]").val();
//			    	 noOrder.mapInit();
//					noOrder.pageList(type);
//			    };
//			    // 添加DOM元素到地图中  
//			    map.getContainer().appendChild(selectbtn);  
//			    // 将DOM元素返回  
//			    return selectbtn;  
//			} 
			
			
			
			return noOrder;
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
		function closeWarning(warningNo1){
			$(".BMap_pop").hide();
			$(".BMap_shadow").hide();
			var warningNo=warningNo1;
			$.ajax({
				 type: "post",
	             url: getPath("app")+"/warning/toWarning.do",
	             data: {warningNo:warningNo},
	             dataType: "json",
	             success: function(data){
	            	 if(data!=null){
	            	    $("#OffWarningModalclo").modal("show");
						$("#warningNo").val(data.warningNo);
						$("#carPlateNo").val(data.carPlateNo);
	                 }else{
	                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "不可关闭！");
	                 }
	             }
			});
			
		}
