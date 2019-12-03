define(
		[],
		function() {
			var regionMonitor = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#regionMonitorSearch").click(function() {
						regionMonitor.pageList();
					});
					regionMonitor.mapInit();
					regionMonitor.pageList();
					regionMonitor.refreshPage(22);
				},
				//定时刷新页面
				refreshPage: function (seconds) {
					var sec = parseInt(seconds);
					
				},
				pageList : function() {
				},
				refreshPageList : function() {
				},
				mapInit:function() {
					var map = new BMap.Map("ln-map"); // 创建地图实例
					Map = map;
					map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
					map.enableScrollWheelZoom();
					var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT}); //右上角
					map.addControl(top_right_navigation); // 添加默认缩放平移控件
					map.enableScrollWheelZoom();
					map.enableInertialDragging();
					map.enableContinuousZoom();
					var size = new BMap.Size(10, 10);
					map.addControl(new BMap.CityListControl({
					    anchor: BMAP_ANCHOR_TOP_LEFT,
					    offset: size,
					    // 切换城市之间事件
					    // onChangeBefore: function(){
					    //    alert('before');
					    // },
					    // 切换城市之后事件
					    // onChangeAfter:function(){
					    //   alert('after');
					    // }
					}));
				}
			};
			return regionMonitor;
		});
	
			
		
