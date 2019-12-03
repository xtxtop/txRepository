define([],function() {	
	var orderDayOver = {
			appPath : getPath("app"),
			init : function() {
				//详情关闭
				$("#closeOrderDayEdit").click(function(){
					closeTab("日租订单信息");
	            });
				//结束订单提交
				$("#saveOrderDayEdit").click(function(){
					orderDayOver.detail();
				});
				//获取坐标
				$("#changeCarDayLocation").click(function(){
					$("#carLocationSearchGCModalDay").modal("show");
					var form=$("form[name=orderEditFormDay]");
					var longitude=form.find("input[name=longitude]").val();
					var latitude=form.find("input[name=latitude]").val();
					orderDayOver.mapInit(116.413554,39.911013);//地图初始化（先画出地图）
					$("#searchCarGCMapDay").css({
    					"width":"100%",
    					"height":"550px",
    					"overflow":"hidden"
    				});
					if(longitude!=''&&latitude!=''){
						orderDayOver.mapInit(longitude,latitude);//地图初始化
					}
					
    				//输入地址查询经纬度并显示
				});
				$("#carLocationSearchGCBtnDay").click(function(){
					var form1=$("form[name=carLocationSearchGCDayForm]");
					var gcAddress=form1.find("input[name=gcAddress]").val();
					var gcPoint=form1.find("input[name=gcPoint]").val();
					if(gcPoint){
	    				var temp=gcPoint.split(",");
	    				longitude=temp[0];
	    				latitude=temp[1];
	    			}	
					var form=$("form[name=orderEditForm]");
					form.find("input[name=longitude]").val(longitude);
					form.find("input[name=latitude]").val(latitude);
					form.find("input[name=currentCarLocation]").val(gcAddress);
					$("#carLocationSearchGCModalDay").modal("hide");
					
				});
				
			},
			mapInit:function(longitude,latitude) {
				var map = new BMap.Map("searchCarGCMapDay"); // 创建地图实例
				var marker = new BMap.Marker(new BMap.Point(longitude,latitude)); // 创建标注
				var markerB=marker;
				map.centerAndZoom(new BMap.Point(longitude,latitude), 15);
				map.addOverlay(marker);//创建标注
				var localSearch = new BMap.LocalSearch(map);
				var form=$("form[name=carLocationSearchGCDayForm]");
				localSearch.setSearchCompleteCallback(function (searchResult) {
					map.removeOverlay(markerB);//清除原来的标注    
					var poi = searchResult.getPoi(0);
					form.find("input[name=gcAddress]").val(addressDetail);
					form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
					var marker = new BMap.Marker(new BMap.Point(poi.point.lng,poi.point.lat)); // 创建标注
					markerB=marker;
					map.addOverlay(marker);//创建标注
				});
				//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
				//map.enableScrollWheelZoom(false);
				//map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
				 // 创建地理编码服务实例 
			    var myGeo = new BMap.Geocoder(); 
				map.addEventListener('click', function(e){
				    form.find("input[name=gcPoint]").val(e.point.lng +','+ e.point.lat);
				    
				    //根据所选坐标查询地址
				   
				    // 根据坐标得到地址描述 
				    myGeo.getLocation(new BMap.Point(e.point.lng, e.point.lat), function(result){ 
				    if (result){ 
				    	map.removeOverlay(markerB);    
				    	form.find("input[name=gcAddress]").val(result.address);
				    	var marker = new BMap.Marker(new BMap.Point(e.point.lng,e.point.lat)); // 创建标注
				    	markerB=marker;
						map.addOverlay(marker);//创建标注
				    } 
				    });
				});
				 myGeo.getLocation(new BMap.Point(longitude,latitude), function(result){ 
					    if (result){ 
					    	form.find("input[name=gcAddress]").val(result.address);
					    	form.find("input[name=gcPoint]").val(longitude +','+ latitude);
					    } 
					    });
				$("#searchCarPointDay").click(function(){
					var form=$("form[name=carLocationSearchGCDayForm]");
					var localSearch = new BMap.LocalSearch(map);
					localSearch.setSearchCompleteCallback(function (searchResult) {
						map.removeOverlay(markerB);    
						var poi = searchResult.getPoi(0);
						map.centerAndZoom(form.find("input[name=gcAddress]").val(), 12);
						form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
						var marker = new BMap.Marker(new BMap.Point(poi.point.lng,poi.point.lat)); // 创建标注
						markerB=marker;
						map.addOverlay(marker);//创建标注
					});
					localSearch.search(form.find("input[name=gcAddress]").val());
				});
			},
	detail:function(){
		var form = $("form[name=orderEditFormDay]");
		form.ajaxSubmit({
			url : orderDayOver.appPath + "/orderDay/orderDayOver.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "结束成功", function() {
						closeTab("日租订单信息");
						$("#orderDayList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "手动结束结束失败，"+res.msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='finishReason']").empty();
				$("span[name='currentCarLocation']").empty();
				$("span[name='longitude']").empty();
				$("span[name='latitude']").empty();
				$("span[name='additionFees']").empty();
				$("span[name='additionReason']").empty();
				if (form.find("textarea[name='finishReason']").val()=="") {
					$("span[name='finishReason']").append("<font color='red'>请输入结束原因！</font>");
					return false;
				}
				if (form.find("input[name='currentCarLocation']").val()=="") {
					$("span[name='currentCarLocation']").append("<font color='red'>请输入当前车辆位置！</font>");
					return false;
				}
				if (form.find("input[name='longitude']").val()=="") {
					$("span[name='longitude']").append("<font color='red'>请输入经度！</font>");
					return false;
				}
				if (form.find("input[name='latitude']").val()=="") {
					$("span[name='latitude']").append("<font color='red'>请输入纬度！</font>");
					return false;
				}
				if (form.find("input[name='additionFees']").val()=="") {
					$("span[name='additionFees']").append("<font color='red'>请输入附加费用！</font>");
					return false;
				}
				
				if (!/^[0-9]*$/.test(form.find("input[name='additionFees']").val())) {
					$("span[name='additionFees']").append("<font color='red'>附加费用只能是数字！</font>");
					return false;
				}
				if (form.find("input[name='additionReason']").val()=="") {
					$("span[name='additionReason']").append("<font color='red'>请输入附加费用原因！</font>");
					return false;
				}
			}
		});
	}
	}
 return	orderDayOver;
});


