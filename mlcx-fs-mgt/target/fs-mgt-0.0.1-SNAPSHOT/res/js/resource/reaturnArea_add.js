var ploygonMap;
define([],function() {	
var returnAreaAdd = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		beforePath:getPath("before"),
		init : function() {
			$("#supportedServicesreturnArea input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#supportedServicesreturnArea input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#supportedServices").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			$("#returnAreaCompanyRelIds input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#returnAreaCompanyRelIds input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#returnAreaCompanyIds").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			//上传图片
			$("#addreturnAreaPhotoButton").click(function(){
				$("#returnAreaPhotoAddModal").modal("show");
			});
			//新增图片回填
			$("#addreturnAreaPhotoBtn").click(function(){
				var form=$("form[name=returnAreaphotoForm]");
				var img=form.find("input[name=returnAreaPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=returnAreaAddForm]");
					form1.find("input[name=returnAreaPicUrl1]").val(img);
					form1.find("img[name=returnAreaPicUrlImg]").attr("src",returnAreaAdd.imgPath+"/"+img);
					$("#returnAreaPhotoAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			//新增提交
			$("#addreturnArea").click(function(){
				returnAreaAdd.savereturnArea();
			});
			//新增页面的关闭
			$("#addclosereturnArea").click(function(){
				closeTab("");
			});
			
			
			
			
			//获取坐标
			$("#searchAreaGC").click(function(){
				var form=$("form[name=returnAreaAddForm]");
				var returnAreaType = form.find("input[name=isPloygon]").val();
				if(returnAreaType==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先配置场站类型系统参数！");
				}else if(returnAreaType=="0"){
					$("#returnAreaAddSearchGCModal").modal("show");
					$("#searchGCMapArea").css({
						"width":"100%",
						"height":"250px",
						"overflow":"hidden"
					});
					returnAreaAdd.mapInit("广东省深圳市龙岗区");//地图初始化（先画出地图）
					//获取已经输入的地址信息
					var addrRegion1Id=form.find("select[name=addrRegion1Id]").val();
					var addrRegion2Id=form.find("select[name=addrRegion2Id]").val();
					var addrRegion3Id=form.find("select[name=addrRegion3Id]").val();
					var addrStreet=form.find("input[name=addrStreet]").val();
					$.ajax({
						url:returnAreaAdd.appPath+"/returnArea/searchAddress.do",//获取输入地址信息
						type:"post",
						data:{addrRegion1Id:addrRegion1Id,
							addrRegion2Id:addrRegion2Id,
							addrRegion3Id:addrRegion3Id,
							addrStreet:addrStreet},
							contentType: "application/x-www-form-urlencoded; charset=utf-8", 
							success:function(res){ 
								if(res.code=="1"){
									var addressDetail=res.data;
									if(addressDetail!=""){
										returnAreaAdd.mapInit(addressDetail);//地图初始化
									}
									//输入地址查询经纬度并显示
								}
							}
					});
				}else if(returnAreaType=="1"){
					$("#returnAreaAddPloygonPointsModal").modal("show");
					$("#AreaploygonPointsMap").html("");
					$("#AreaploygonPointsMap").css({
						"width":"100%",
						"height":"450px",
						"overflow":"hidden"
					});
					setTimeout(function (){
						returnAreaAdd.AreaploygonPointsMapInit();//地图初始化（先画出地图）
					},"500")
				}
			});
			$("#returnAreaAddSearchGCBtn").click(function(){
				var form1=$("form[name=returnAreaAddSearchGCForm]");
				var gcPoint=form1.find("input[name=gcPoint]").val();
				if(gcPoint){
    				var temp=gcPoint.split(",");
    				longitude=temp[0];
    				latitude=temp[1];
    			}	
				var form=$("form[name=returnAreaAddForm]");
				form.find("input[name=longitude]").val(longitude);
				form.find("input[name=latitude]").val(latitude);
				var fullAddr = form1.find("input[name=gcAddress]").val();
				form.find("input[name=addrStreet]").val(fullAddr);
				var addr1 = form.find("select[name=addrRegion1Id]").find("option");
				var addr2;
				var addr3;
			     $(addr1).each(function(){  //遍历所有option  
			          var txt = $(this).text();   //获取option值   
			          if(fullAddr!=''){  
			        	  var flag = fullAddr.indexOf($.trim(txt));
			        	  if(flag>-1){
			        		  $(this).attr("selected",true);
			        		  getResultCity($(this).val());
			        		  setTimeout(function (){
			        			  addr2 = form.find("select[name=addrRegion2Id]").find("option");
			        			  $(addr2).each(function(){  //遍历所有option  
			    			          var txt = $(this).text();   //获取option值   
			    			          if(fullAddr!=''){  
			    			        	  var flag = fullAddr.indexOf($.trim(txt));
			    			        	  if(flag>-1){
			    			        		  $(this).attr("selected",true);
			    			        		  getResultCountry($(this).val());
			    			        		  setTimeout(function (){ 
			    			        			  addr3 = form.find("select[name=addrRegion3Id]").find("option");
			    			        			  $(addr3).each(function(){  //遍历所有option  
			    			    			          var txt = $(this).text();   //获取option值   
			    			    			          if(fullAddr!=''){  
			    			    			        	  var flag = fullAddr.indexOf($.trim(txt));
			    			    			        	  if(flag>-1){
			    			    			        		  $(this).attr("selected",true);
			    			    			        	  }
			    			    			          }  
			    			    			     })  
			    			        		  },600);
			    			        	  }
			    			          }  
			    			     }) 
			        		  },600);
			        	  }
			          }  
			     }) 
				$("#returnAreaAddSearchGCModal").modal("hide");
				
			});
			$("#returnAreaAddSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=returnAreaAddSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
			
			//修改页面的
			//获取坐标
			$("#searchGCEdit").click(function(){
				$("#returnAreaAddSearchGCModal").modal("show");
				returnAreaAdd.mapInit1("广东省深圳市龙岗区");//地图初始化（先画出地图）
				debugger;
				//获取已经输入的地址信息
				var form=$("form[name=returnAreaAddForm]");
				var addrRegion1Id=form.find("select[name=addrRegion1Id]").val();
				var addrRegion2Id=form.find("select[name=addrRegion2Id]").val();
				var addrRegion3Id=form.find("select[name=addrRegion3Id]").val();
				var addrStreet=form.find("input[name=addrStreet]").val();
				$.ajax({
		    			url:returnAreaAdd.appPath+"/returnArea/searchAddress.do",//获取输入地址信息
		    			type:"post",
		    			data:{addrRegion1Id:addrRegion1Id,
		    				addrRegion2Id:addrRegion2Id,
		    				addrRegion3Id:addrRegion3Id,
		    				addrStreet:addrStreet},
		    			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		    			success:function(res){ 
		    				if(res.code=="1"){
		    					var addressDetail=res.data;
			    				$("#searchGCMapAreaEdit").css({
			    					"width":"100%",
			    					"height":"250px",
			    					"overflow":"hidden"
			    				});
			    				returnAreaAdd.mapInit1(addressDetail);//地图初始化
			    				//输入地址查询经纬度并显示
			    				
		    				}
		    				}
		    			});
			});
		},
		AreaploygonPointsMapInit:function() {
			var formAdd=$("form[name=returnAreaAddForm]");
			var map = new BMap.Map("AreaploygonPointsMap"); // 创建地图实例
			var point = new BMap.Point(116.331398,39.897445);
			map.centerAndZoom(point,15);
			map.enableScrollWheelZoom(); //启用鼠标滚动对地图放大缩小
			var addrStreet = formAdd.find("input[name=addrStreet]").val();
			if(addrStreet==""){
				var myCity = new BMap.LocalCity();
				myCity.get(
						function myFun(result){
							var cityName = result.name;
							map.setCenter(cityName);
						}
				);
			}else{
				var form=$("form[name=AreaploygonPointsSearchForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 15);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
				
				var ploygonPointsHidden = formAdd.find("input[name=ploygonPoints]").val();
				var polygon;
				if(ploygonPointsHidden!=""){
					 var ploygonPointsArr = ploygonPointsHidden.split(",");
					 var ploygonArr = [];
					 for(var i=0;i<ploygonPointsArr.length;i++){
						 var pointTemp = ploygonPointsArr[i].split(" ");
						 var point =  new BMap.Point(pointTemp[0],pointTemp[1]);
						 ploygonArr.push(point);
				     }
					 polygon = new BMap.Polygon(ploygonArr, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5});  //创建多边形
					 map.addOverlay(polygon);   //增加多边形
				}
			}
			//根据地址搜索
			$("#ploygonPointsSearchAddress").click(function(){
				var form=$("form[name=AreaploygonPointsSearchForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 15);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
		   //鼠标绘制完成回调方法   获取各个点的经纬度
		    var overlays = [];
			var polygon;
			var points = [];
		    var overlaycomplete = function(e){
		        overlays.push(e.overlay);
		        var path = e.overlay.getPath();//Array<Point> 返回多边型的点数组
		        for(var i=0;i<path.length;i++){
		        	var obj = new Object();
		        	obj.longitude = path[i].lng;
		        	obj.latitude = path[i].lat;
		        	points.push(obj);
//		            console.log("lng:"+path[i].lng+"\n lat:"+path[i].lat);
		        }
//				polygon = new BMap.Polygon(path, {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
//				map.addOverlay(polygon);   //增加多边形
		    };
		    var styleOptions = {
		        strokeColor:"blue",    //边线颜色。
		        fillColor:"white",      //填充颜色。当参数为空时，圆形将没有填充效果。
		        strokeWeight: 3,       //边线的宽度，以像素为单位。
		        strokeOpacity: 0.8,       //边线透明度，取值范围0 - 1。
		        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
		        strokeStyle: 'solid' //边线的样式，solid或dashed。
		    }
		    //实例化鼠标绘制工具
		    var drawingManager = new BMapLib.DrawingManager(map, {
		        isOpen: false, //是否开启绘制模式
		        enableDrawingTool: true, //是否显示工具栏
		        drawingMode:BMAP_DRAWING_POLYGON,//绘制模式  多边形
		        drawingToolOptions: {
		            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
		            offset: new BMap.Size(5, 5), //偏离值
		            drawingModes:[
		                BMAP_DRAWING_POLYGON
		            ]
		        },
		        polygonOptions: styleOptions //多边形的样式
		    });
		     //添加鼠标绘制工具监听事件，用于获取绘制结果
		    drawingManager.addEventListener('overlaycomplete', overlaycomplete);
		    //删除覆盖物
			$("#deletePloygonPointsBtn").click(function(){
				 for(var i = 0; i < overlays.length; i++){
			         map.removeOverlay(overlays[i]);
			         points.splice(0,points.length);
			     }
			     overlays.length = 0   
			});
			//点击保存时的校验计算
			$("#returnAreaAddPloygonPointsBtn").click(function(){
				if(points==null||points.length==0){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先画多变形");
					return ;
				}else if(overlays.length>8){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "多边形不能大于8个边");
					return ;
				}else{
					$.ajax({
						url:returnAreaAdd.appPath+"/returnArea/checkPloygonPoints.do",
						type:"post",
						data:{points:JSON.stringify(points)},
						success:function(res){ 
							if(res.code=="1"){
								var longitudeAndLatitude=res.data;
								var form=$("form[name=returnAreaAddForm]");
								var ploygonPoints = "";
								for(var i=0;i<points.length;i++){
						        	var longitude = points[i].longitude;
						        	var latitude = points[i].latitude;
						        	ploygonPoints+=longitude+" "+latitude+",";
						        }
								ploygonPoints+=points[0].longitude+" "+points[0].latitude;
								form.find("input[name=ploygonPoints]").val(ploygonPoints);
								form.find("input[name=longitude]").val(longitudeAndLatitude.longitude);
								form.find("input[name=latitude]").val(longitudeAndLatitude.latitude);
								$("#returnAreaAddPloygonPointsModal").modal("hide");
								//根据所选坐标查询地址
							    // 创建地理编码服务实例 
							    var myGeo = new BMap.Geocoder(); 
							    // 根据坐标得到地址描述 
							    myGeo.getLocation(new BMap.Point(longitudeAndLatitude.longitude,longitudeAndLatitude.latitude), function(result){ 
							    var fullAddr = "";
							    if (result){ 
							    		fullAddr = result.address;
							    		form.find("input[name=addrStreet]").val(result.address);
							    		var addr1 = form.find("select[name=addrRegion1Id]").find("option");
							    		var addr2;
							    		var addr3;
							    		$(addr1).each(function(){  //遍历所有option  
							    			var txt = $(this).text();   //获取option值   
							    			if(fullAddr!=''){  
							    				var flag = fullAddr.indexOf($.trim(txt));
							    				if(flag>-1){
							    					$(this).attr("selected",true);
							    					getResultCity($(this).val());
							    					setTimeout(function (){
							    						addr2 = form.find("select[name=addrRegion2Id]").find("option");
							    						$(addr2).each(function(){  //遍历所有option  
							    							var txt = $(this).text();   //获取option值   
							    							if(fullAddr!=''){  
							    								var flag = fullAddr.indexOf($.trim(txt));
							    								if(flag>-1){
							    									$(this).attr("selected",true);
							    									getResultCountry($(this).val());
							    									setTimeout(function (){ 
							    										addr3 = form.find("select[name=addrRegion3Id]").find("option");
							    										$(addr3).each(function(){  //遍历所有option  
							    											var txt = $(this).text();   //获取option值   
							    											if(fullAddr!=''){  
							    												var flag = fullAddr.indexOf($.trim(txt));
							    												if(flag>-1){
							    													$(this).attr("selected",true);
							    												}
							    											}  
							    										})  
							    									},600);
							    								}
							    							}  
							    						}) 
							    					},600);
							    				}
							    			}  
							    		}) 
							    	} 
							    });
							}else{
								bootboxt(res.msg);
							}
						}
					});
				}
			});
		},
		renderReverse:function(response) {
            if (response&&response.status&&response.status=="OK") {
            	var location = response.result;
            } else {
                alert(0);
            }
        },
		mapInit:function(addressDetail) {
			$("#searchGCMapArea").html("");
			var map = new BMap.Map("searchGCMapArea"); // 创建地图实例
			map.centerAndZoom(addressDetail, 15);
			var localSearch = new BMap.LocalSearch(map);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var form=$("form[name=returnAreaAddSearchGCForm]");
				form.find("input[name=gcAddress]").val(addressDetail);
				form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
			});
			localSearch.search(addressDetail);
			//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
			//map.enableScrollWheelZoom(false);
//			map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			map.addEventListener('click', function(e){
			    var form=$("form[name=returnAreaAddSearchGCForm]");
			    form.find("input[name=gcPoint]").val(e.point.lng +','+ e.point.lat);
			    
			    //根据所选坐标查询地址
			    // 创建地理编码服务实例 
			    var myGeo = new BMap.Geocoder(); 
			    // 根据坐标得到地址描述 
			    myGeo.getLocation(new BMap.Point(e.point.lng, e.point.lat), function(result){ 
			    if (result){ 
			    	form.find("input[name=gcAddress]").val(result.address);	
			    } 
			    });
			});
			$("#searchPoint").click(function(){
				debugger;
				var form=$("form[name=returnAreaAddSearchGCForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 12);
					form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
		},
		mapInit1:function(addressDetail) {
			debugger;
			var map = new BMap.Map("searchGCMapAreaEdit"); // 创建地图实例
			map.centerAndZoom(addressDetail, 15);
			var localSearch = new BMap.LocalSearch(map);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var form=$("form[name=returnAreaAddSearchGCForm]");
				form.find("input[name=gcAddress]").val(addressDetail);
				form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
			});
			localSearch.search(addressDetail);
			//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
			//map.enableScrollWheelZoom(false);
			//map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			map.addEventListener('click', function(e){
			    var form=$("form[name=returnAreaAddSearchGCForm]");
			    form.find("input[name=gcPoint]").val(e.point.lng +','+ e.point.lat);
			    
			    //根据所选坐标查询地址
			    // 创建地理编码服务实例 
			    var myGeo = new BMap.Geocoder(); 
			    // 根据坐标得到地址描述 
			    myGeo.getLocation(new BMap.Point(e.point.lng, e.point.lat), function(result){ 
			    if (result){ 
			    	form.find("input[name=gcAddress]").val(result.address);	
			    } 
			    });
			});
			$("#searchPointEdit").click(function(){
				debugger;
				var form=$("form[name=returnAreaAddSearchGCForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 12);
					form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
			$("#returnAreaAddSearchGCBtn").click(function(){
				var form1=$("form[name=returnAreaAddSearchGCForm]");
				var gcPoint=form1.find("input[name=gcPoint]").val();
				if(gcPoint){
    				var temp=gcPoint.split(",");
    				longitude=temp[0];
    				latitude=temp[1];
    			}	
				var gcAddress=form1.find("input[name=gcAddress]").val();
				var form=$("form[name=returnAreaAddForm]");
				form.find("input[name=longitude]").val(longitude);
				form.find("input[name=latitude]").val(latitude);
				$("#returnAreaAddSearchGCModal").modal("hide");
				
			});
			$("#returnAreaAddSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=returnAreaAddSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
		},
savereturnArea:function() {
	var form=$("form[name=returnAreaAddForm]");
 	$("#returnAreaNameAdd").empty();
 	$("#longitudeAdd").empty();
   	$("#latitudeAdd").empty();
	 var areaName=form.find("input[name=areaName]").val();
	var longitude=form.find("input[name=longitude]").val();
    var latitude=form.find("input[name=latitude]").val();
    if (areaName == "") {
		$("#returnAreaNameAdd").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
		return false;
    }
	 if(areaName !=""){
		 $.ajax({
    			url:returnAreaAdd.appPath+"/returnArea/onlyAreaName.do",//验证场站名称唯一性
    			type:"post",
    			data:{areaName:areaName},
    			dataType:"json",
    			success:function(res){ 
    					if(res.code == "1"){ 
    						$("#returnAreaNameAdd").empty();
    						$("#returnAreaNameAdd").append("<font color='red' class='formtips onError emaill'>场站名称重复！</font>");
    						return false;
    					}else{
    						var form = $("form[name=returnAreaAddForm]");
    						form.ajaxSubmit({
    							url : returnAreaAdd.appPath + "/returnArea/updateReturnArea.do",
    							type : "post",
    							success : function(res) {
    								var msg = res.msg;
    								var code = res.code;
    								if (code == "1") {
    									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
    										closeTab("新增场站");
    										$("#returnAreaList").DataTable().ajax.reload(function(){});
    									});
    								} else {
    									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
    								}
    							},
    							//beforeSubmit : function(formData, jqForm, options) {}
    						});
    					}
    			}
    		}); 
	 }else{
		 $("#returnAreaNameAdd").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
		return false;
	 }
    
    if (longitude == "") {
		$("#longitudeAdd").append("<font color='red' class='formtips onError emaill'>请输入经度！</font>");
		return false;
    }
    if (longitude != ""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(longitude)) {
		$("#longitudeAdd").append("<font color='red' class='formtips onError emaill'>经度格式不正确，不包含空格，只能是数字！</font>");
		return false;
    }
    if (latitude == "") {
		$("#latitudeAdd").append("<font color='red' class='formtips onError emaill'>请输入纬度！</font>");
		return false;
    }
    if (latitude != ""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(latitude)) {
		$("#latitudeAdd").append("<font color='red' class='formtips onError emaill'>纬度格式不正确，不包含空格，只能是数字！</font>");
		return false;
    }

    



	}
}
return returnAreaAdd;
})

