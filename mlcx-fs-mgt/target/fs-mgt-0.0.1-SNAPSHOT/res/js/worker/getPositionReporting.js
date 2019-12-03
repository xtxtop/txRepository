var reportingGlobalMap;
var globalreportingList = [];
var reportingGlobalMarkerPoint = [];
var globalreportingMark=new Object();
define(
		[],
		function() {
			var getPositionReporting = {
				appPath : getPath("app"),
				init : function() {
					var mapHeight = $('#mainContentHeight').val();
					$('#reporting-map').css({"width":"100%","height":mapHeight-4+'px'});
					$(window).resize(function () {
						var mapHeight = $('#mainContentHeight').val();
						$('#reporting-map').css({"width":"100%","height":mapHeight-4+'px'});
					})
					// 列表页面搜索调用
					/*$("#getPositionReportingSearch").click(function() {
						getPositionReporting.pageList();
					});*/
					getPositionReporting.mapInit();
					//getPositionReporting.pageList();
					getPositionReporting.refreshPage(22);
					$("#closeModelBtn").click(function(){
						$('#showWorkerModel').modal('hide')
					})
					
					
					
				},
				//定时刷新页面
				refreshPage: function (seconds) {
					var sec = parseInt(seconds);
					
				},
				//调度员相关数据列表
				pageList : function() {
					var form = $("form[name=queryWorkerSearchForm]");
					var workerNo = form.find("input[name=workerId]").val();
					
					var queryWorkerTpl = $("#queryWorkerTpl").html();
					// 预编译模板
					var template = Handlebars.compile(queryWorkerTpl);
					$('#queryWorkerList').dataTable( {
						searching:false,
						destroy: true,
						"ajax": {
							"type": "POST",
							"url": getPositionReporting.appPath+'/worker/pageListWorkerJob.do',
							"data": function ( d ) {
								
								return $.extend( {}, d, {
									"pageNo": (d.start/d.length)+1,
									"pageSize":d.length,
									"workerId":workerNo
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
				            { "title":"调度员","data": "workerName" },
							{ "title":"任务类型","data": "type" },
							{ "title":"状态","data": "workOrderStatus" }
						],
					   "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   		initComplete: function () {
					   		},
						"drawCallback": function( settings ) {
		        	    },
						"columnDefs": [
							{
							    "targets": [1],
							    "render": function(a,b,c,d) {
							    	var workerOrderType = "";//0-调度 1-救援 2-清洁 3-加油 4-维保
									if(c.type.indexOf("0")!=-1){
										workerOrderType +="调度，"
									}
									if(c.type.indexOf("1")!=-1){
										workerOrderType +="救援，"
									}
									if(c.type.indexOf("2")!=-1){
										workerOrderType +="清洁，"
									}
									if(c.type.indexOf("3")!=-1){
										workerOrderType +="加油，"
									}
									if(c.type.indexOf("4")!=-1){
										workerOrderType +="维保，"
									}
									
									var ty = workerOrderType.substring(0,workerOrderType.length-1);
									return ty;
							    }
							},
							{
							    "targets": [2],
							    "render": function(a,b,c,d) {
							    	//0-未下发 1-已下发 2-调度中 3-已结束4-已取消
							    	if(a!=null){
							        	if(a==0){
							        		return "未下发";
							        	}else if(a==1){
							        		return "已下发";
							        	}else if(a==2){
							        		return "调度中";
							        	}else if(a==3){
							        		return "已结束";
							        	}else if(a==4){
							        		return "已取消";
							        	}
							        }else{
							        	return "";
							        }
							    }
							}
						]
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
						var zoom = getPositionReporting.getZoom(maxLng, minLng, maxLat,
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
					var map = new BMap.Map("reporting-map",{enableMapClick: false}); // 创建地图实例
					reportingGlobalMap = map;
					map.centerAndZoom(new BMap.Point(114.102332,22.580554),5);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
					//初始化获取调度员经纬度数据
					$.ajax({
						 type: "post",
			             url: getPositionReporting.appPath+"/worker/getWorkerData.do",
			             data: {appIsOnline:'1'},
			             dataType: "json",
			             success: function(res){
			            	 if(res != null){
			            		 $.each(res.data, function(index,item){ 
			            		        var point = new BMap.Point(item.longitude,item.latitude);
			            				var marker = new BMap.Marker(point, {
			        						icon : new BMap.Icon(getPositionReporting.appPath
			        								+ '/res/img/marker.png', new BMap.Size(28,32), {
			        							imageOffset : new BMap.Size(0, 0)
			        						})
			        					});  // 创建标注
			            				map.addOverlay(marker);              // 将标注添加到地图中
			            				map.centerAndZoom(point, 15);
			            				var geoc = new BMap.Geocoder();  
			            				var adress = "";
			            				geoc.getLocation(point, function(rs){
			            					var addComp = rs.addressComponents;
			            					adress = addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber;
	            							var workerNo = item.workerNo;
				            				marker.addEventListener("click", function(){
				            					var hasMapInfo = $('.mapInfo').length;
				            					hasMapInfo?$('.mapInfo').remove():'';
				            					var myCompOverlay = new ComplexCustomOverlay(point, item.workerName,adress,item.reportTime);
				            					map.addOverlay(myCompOverlay);
				            					setTimeout(function(){
				            						$(".queryWorker").click(function(){
				            							
				            							addTab("调度工单列表",getPositionReporting.appPath+ "/workerOrder/toWorkerOrderList.do?workerNo="+workerNo);
				            							
//				            							$('#showWorkerModel').modal('show');
//				            							var form = $("form[name=queryWorkerSearchForm]");
//				            							form.find("input[id=workerNo]").val(workerNo);
//				            							getPositionReporting.pageList();
				            						})
				            						$(".removeInfoDiv").click(function(){
				            							$('.mapInfo').remove();
				            						})
				            					},500);
				            				});
			            				});  
			            				
			            		 });  
			                 }
			             }
					});
				},
			};
			// 复杂的自定义覆盖物
		    function ComplexCustomOverlay(point, name, addr,date){
			      this._point = point;
			      this._name = name;
			      this._addr = addr;
			      this._date = date;
		    }
		    ComplexCustomOverlay.prototype = new BMap.Overlay();
		    ComplexCustomOverlay.prototype.initialize = function(map){
		     this._map = map;
		     var peopleInfo = {
					name:this._name,
					addr:this._addr,
					date:this._date
				}
				
				//弹框盒子里面
				var mapInfro = this._div = document.createElement('div');
		        mapInfro.style.position = "absolute";
		        mapInfro.style.MozUserSelect = "none";
				mapInfro.className='mapInfo';
				//箭头
				var arrowDown = document.createElement('div');
				arrowDown.className = 'arrow-down';
				//标题
				var devicePerson = document.createElement("h1");
				devicePerson.className="devicePerson"
				var iconfont = document.createElement('i');
				devicePerson.innerHTML = "调度员："+peopleInfo.name+"<i class='iconfont removeInfoDiv' >&#xe600;</i>"
				//主要信息
				var mainInfro = document.createElement('div');
				mainInfro.className = "infro-content";
				mainInfro.innerHTML = "<ul><li class='m2map_t2'>最新上报位置</li><li class='info'>"+peopleInfo.addr+"</li></ul><ul><li class='m2map_t2'>最新上报时间</li><li class='info'>"+peopleInfo.date+"</li></ul>"
				//按钮
				var buttonContent = document.createElement('div');
				buttonContent.className = "task-info queryWorker"
				buttonContent.innerText = "任务详情"
				//添加到弹框盒子里面
				mapInfro.appendChild(arrowDown)
				mapInfro.appendChild(devicePerson);
				mapInfro.appendChild(mainInfro);
				mapInfro.appendChild(buttonContent);
		        map.getPanes().labelPane.appendChild(mapInfro);
		       return mapInfro;
		    }
		    ComplexCustomOverlay.prototype.draw = function(){
		        var map = this._map;
		        var pixel = map.pointToOverlayPixel(this._point);
		        this._div.style.left = pixel.x - 170+"px";
		        this._div.style.top  = pixel.y -256-28 + "px";
		       
		    }
		    ComplexCustomOverlay.prototype.move = function(point) {
	              var map = this._map;
	              var pixel = map.pointToOverlayPixel(new BMap.Point(point.lng, point.lat));
	              this._div.style.left = pixel.x - 170+"px";
			      this._div.style.top  = pixel.y -256-28 + "px";
		    }
			return getPositionReporting;
		});


