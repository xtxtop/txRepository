define([],function() {	
var returnAreaEdit = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		beforePath:getPath("before"),
		init : function() {
			$("#supportedServicesreturnAreaEdit input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#supportedServicesreturnAreaEdit input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#supportedServicesEdit").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			$("#returnAreaCompanyRelIdsEdit input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#returnAreaCompanyRelIdsEdit input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#returnAreaCompanyIdsEdit").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			//上传图片
			$("#editreturnAreaPhotoBtn").click(function(){
				$("#editreturnAreaPhotoModal").modal("show");
			});
			//编辑图片回填
			$("#editreturnAreaPhotoButton").click(function(){
				var form=$("form[name=returnAreaPhotoEditForm]");
				var img=form.find("input[name=returnAreaPicEditUrl]").val();
				if(img!=""){
					var form1=$("form[name=returnAreaEditForm]");
					form1.find("input[name=returnAreaEditUrlHidden]").val(img);
					form1.find("img[name=imgEditUrl]").attr("src",returnAreaEdit.imgPath+"/"+img);
					$("#editreturnAreaPhotoModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			//编辑提交
			$("#savereturnArea").click(function(){
				returnAreaEdit.updatereturnArea();
			});
			//关闭车辆详情编辑页
			$("#closereturnArea").click(function(){
				closeTab("场站编辑");
            });
			//跳转到车位列表页面
			$("#carreturnAreaingSpaceOnFormBtn").each(function(){
				$(this).on("click",function(){
					EditTab("车位列表", returnAreaEdit.appPath+ "/returnAreaingSpace/toreturnAreaingSpace.do?returnAreaNo="+$(this).data("id"), null,function(){
					});
				});
            });
			//跳转到充电桩列表页面
			$("#carChargerOnFormBtn").each(function(){
				$(this).on("click",function(){
					EditTab("充电桩列表", returnAreaEdit.appPath+ "/charger/toChargerList.do?returnAreaNo="+$(this).data("id"), null,function(){
					});
				});
            });
			
			//获取坐标
			$("#searchGC").click(function(){
				$("#returnAreaEditSearchGCModal").modal("show");
				returnAreaEdit.mapInit("北京市");//地图初始化（先画出地图）
				debugger;
				//获取已经输入的地址信息
				var form=$("form[name=returnAreaEditForm]");
				var EditrRegion1Id=form.find("select[name=EditrRegion1Id]").val();
				var EditrRegion2Id=form.find("select[name=EditrRegion2Id]").val();
				var EditrRegion3Id=form.find("select[name=EditrRegion3Id]").val();
				var EditrStreet=form.find("input[name=EditrStreet]").val();
				$.ajax({
		    			url:returnAreaEdit.appPath+"/returnArea/searchEditress.do",//获取输入地址信息
		    			type:"post",
		    			data:{EditrRegion1Id:EditrRegion1Id,
		    				EditrRegion2Id:EditrRegion2Id,
		    				EditrRegion3Id:EditrRegion3Id,
		    				EditrStreet:EditrStreet},
		    			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		    			success:function(res){ 
		    				if(res.code=="1"){
		    					var EditressDetail=res.data;
			    				$("#searchGCMap").css({
			    					"width":"100%",
			    					"height":"250px",
			    					"overflow":"hidden"
			    				});
			    				returnAreaEdit.mapInit(EditressDetail);//地图初始化
			    				//输入地址查询经纬度并显示
			    				
		    				}
		    				}
		    			});
			});
			$("#returnAreaEditSearchGCBtn").click(function(){
				var form1=$("form[name=returnAreaEditSearchGCForm]");
				var gcPoint=form1.find("input[name=gcPoint]").val();
				if(gcPoint){
    				var temp=gcPoint.split(",");
    				longitude=temp[0];
    				latitude=temp[1];
    			}	
				var form=$("form[name=returnAreaEditForm]");
				form.find("input[name=longitude]").val(longitude);
				form.find("input[name=latitude]").val(latitude);
				$("#returnAreaEditSearchGCModal").modal("hide");
				
			});
			$("#returnAreaEditSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=returnAreaEditSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
			
			//修改页面的
			//获取坐标
			$("#searchGCEditArea").click(function(){
				//获取已经输入的地址信息
				var form=$("form[name=returnAreaEditForm]");
				var returnAreaType = form.find("input[name=isPloygon]").val();
				if(returnAreaType==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先配置场站类型系统参数！");
				}else if(returnAreaType=="0"){
					$("#returnAreaEditSearchGCModal").modal("show");
					returnAreaEdit.mapInit1("北京市");//地图初始化（先画出地图）
					var EditrRegion1Id=form.find("select[name=EditrRegion1Id]").val();
					var EditrRegion2Id=form.find("select[name=EditrRegion2Id]").val();
					var EditrRegion3Id=form.find("select[name=EditrRegion3Id]").val();
					var EditrStreet=form.find("input[name=EditrStreet]").val();
					$.ajax({
			    			url:returnAreaEdit.appPath+"/returnArea/searchEditress.do",//获取输入地址信息
			    			type:"post",
			    			data:{EditrRegion1Id:EditrRegion1Id,
			    				EditrRegion2Id:EditrRegion2Id,
			    				EditrRegion3Id:EditrRegion3Id,
			    				EditrStreet:EditrStreet},
			    			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			    			success:function(res){ 
			    				if(res.code=="1"){
			    					var EditressDetail=res.data;
				    				$("#searchGCMapEdit").css({
				    					"width":"100%",
				    					"height":"250px",
				    					"overflow":"hidden"
				    				});
				    				returnAreaEdit.mapInit1(EditressDetail);//地图初始化
				    				//输入地址查询经纬度并显示
				    				
			    				}
			    				}
			    			});
					}else if(returnAreaType=="1"){
						$("#returnAreaEditPloygonPointsModal").modal("show");
						$("#ploygonPointsMapEditArea").html("");
						$("#ploygonPointsMapEditArea").css({
							"width":"100%",
							"height":"450px",
							"overflow":"hidden"
						});
						setTimeout(function (){
							returnAreaEdit.ploygonPointsMapEditInitArea();//地图初始化（先画出地图）
						},"500")
					}
			});
		},
		ploygonPointsMapEditInitArea:function() {
			var map = new BMap.Map("ploygonPointsMapEditArea"); // 创建地图实例
			var form=$("form[name=returnAreaEditForm]");
			var lng = form.find("input[name=longitude]").val();
			var lat = form.find("input[name=latitude]").val();
			if(lng!=""&&lat!=""){
				var point = new BMap.Point(lng,lat);
				var EditrStreet = form.find("input[name=EditrStreet]").val();
				var formSearch=$("form[name=ploygonPointsSearchEditForm]");
				formSearch.find("input[name=gcAddress]").val(EditrStreet);
			}else{
				var point = new BMap.Point(116.331398,39.897445);
				var myCity = new BMap.LocalCity();
				myCity.get(
						function myFun(result){
							var cityName = result.name;
							map.setCenter(cityName);
						}
				);
			}
			map.centerAndZoom(point,17);
			map.enableScrollWheelZoom(); //启用鼠标滚动对地图放大缩小
			var ploygonPointsHidden = form.find("input[name=ploygonPoints]").val();
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
			//根据地址搜索
			$("#ploygonPointsSearchEditressEdit").click(function(){
				var form=$("form[name=ploygonPointsSearchEditForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 15);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
		   //鼠标绘制完成回调方法   获取各个点的经纬度
		    var overlays = [];
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
			$("#deleteEditPloygonPointsBtn").click(function(){
				 for(var i = 0; i < overlays.length; i++){
			         map.removeOverlay(overlays[i]);
			         points.splice(0,points.length);
			     }
				 map.removeOverlay(polygon);
			     overlays.length = 0   
			});
			//点击保存时的校验计算
			$("#returnAreaEditPloygonPointsBtn").click(function(){
				if(points==null||points.length==0){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先画多变形");
					return false;
				}else if(overlays.length>8){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "多边形不能大于8个边");
					return false;
				}else{
					$.ajax({
						url:returnAreaEdit.appPath+"/returnArea/checkPloygonPoints.do",
						type:"post",
						data:{points:JSON.stringify(points)},
						success:function(res){ 
							if(res.code=="1"){
								var longitudeAndLatitude=res.data;
								var form=$("form[name=returnAreaEditForm]");
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
								$("#returnAreaEditPloygonPointsModal").modal("hide");
								//根据所选坐标查询地址
							    // 创建地理编码服务实例 
							    var myGeo = new BMap.Geocoder(); 
							    // 根据坐标得到地址描述 
							    myGeo.getLocation(new BMap.Point(longitudeAndLatitude.longitude,longitudeAndLatitude.latitude), function(result){ 
							    var fullEditr = "";
							    if (result){ 
							    		fullEditr = result.Editress;
							    		form.find("input[name=EditrStreet]").val(result.Editress);
							    		var Editr1 = form.find("select[name=EditrRegion1Id]").find("option");
							    		var Editr2;
							    		var Editr3;
							    		$(Editr1).each(function(){  //遍历所有option  
							    			var txt = $(this).text();   //获取option值   
							    			if(fullEditr!=''){  
							    				var flag = fullEditr.indexOf($.trim(txt));
							    				if(flag>-1){
							    					$(this).attr("selected",true);
							    					getResultCity($(this).val());
							    					setTimeout(function (){
							    						Editr2 = form.find("select[name=EditrRegion2Id]").find("option");
							    						$(Editr2).each(function(){  //遍历所有option  
							    							var txt = $(this).text();   //获取option值   
							    							if(fullEditr!=''){  
							    								var flag = fullEditr.indexOf($.trim(txt));
							    								if(flag>-1){
							    									$(this).attr("selected",true);
							    									getResultCountry($(this).val());
							    									setTimeout(function (){ 
							    										Editr3 = form.find("select[name=EditrRegion3Id]").find("option");
							    										$(Editr3).each(function(){  //遍历所有option  
							    											var txt = $(this).text();   //获取option值   
							    											if(fullEditr!=''){  
							    												var flag = fullEditr.indexOf($.trim(txt));
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
		mapInit:function(EditressDetail) {
			debugger;
			var map = new BMap.Map("searchGCMap"); // 创建地图实例
			map.centerAndZoom(EditressDetail, 15);
			var localSearch = new BMap.LocalSearch(map);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var form=$("form[name=returnAreaEditSearchGCForm]");
				form.find("input[name=gcAddress]").val(EditressDetail);
				form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
			});
			localSearch.search(EditressDetail);
			//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
			//map.enableScrollWheelZoom(false);
			//map.EditControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			map.addEventListener('click', function(e){
			    var form=$("form[name=returnAreaEditSearchGCForm]");
			    form.find("input[name=gcPoint]").val(e.point.lng +','+ e.point.lat);
			    
			    //根据所选坐标查询地址
			    // 创建地理编码服务实例 
			    var myGeo = new BMap.Geocoder(); 
			    // 根据坐标得到地址描述 
			    myGeo.getLocation(new BMap.Point(e.point.lng, e.point.lat), function(result){ 
			    if (result){ 
			    	form.find("input[name=gcAddress]").val(result.Editress);	
			    } 
			    });
			});
			$("#searchPoint").click(function(){
				debugger;
				var form=$("form[name=returnAreaEditSearchGCForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 12);
					form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
		},
		mapInit1:function(EditressDetail) {
			debugger;
			var map = new BMap.Map("searchGCMapEdit"); // 创建地图实例
			map.centerAndZoom(EditressDetail, 15);
			var localSearch = new BMap.LocalSearch(map);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var form=$("form[name=returnAreaEditSearchGCForm]");
				form.find("input[name=gcAddress]").val(EditressDetail);
				form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
			});
			localSearch.search(EditressDetail);
			//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
			//map.enableScrollWheelZoom(false);
			//map.EditControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			map.addEventListener('click', function(e){
			    var form=$("form[name=returnAreaEditSearchGCForm]");
			    form.find("input[name=gcPoint]").val(e.point.lng +','+ e.point.lat);
			    
			    //根据所选坐标查询地址
			    // 创建地理编码服务实例 
			    var myGeo = new BMap.Geocoder(); 
			    // 根据坐标得到地址描述 
			    myGeo.getLocation(new BMap.Point(e.point.lng, e.point.lat), function(result){ 
			    if (result){ 
			    	form.find("input[name=gcAddress]").val(result.Editress);	
			    } 
			    });
			});
			$("#searchPointEdit").click(function(){
				debugger;
				var form=$("form[name=returnAreaEditSearchGCForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 12);
					form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
			$("#returnAreaEditSearchGCBtn").click(function(){
				var form1=$("form[name=returnAreaEditSearchGCForm]");
				var gcPoint=form1.find("input[name=gcPoint]").val();
				if(gcPoint){
    				var temp=gcPoint.split(",");
    				longitude=temp[0];
    				latitude=temp[1];
    			}	
				var form=$("form[name=returnAreaEditForm]");
				form.find("input[name=longitude]").val(longitude);
				form.find("input[name=latitude]").val(latitude);
				var fullEditr = form1.find("input[name=gcAddress]").val();
				form.find("input[name=EditrStreet]").val(fullEditr);
				var Editr1 = form.find("select[name=EditrRegion1Id]").find("option");
				var Editr2;
				var Editr3;
			     $(Editr1).each(function(){  //遍历所有option  
			          var txt = $(this).text();   //获取option值   
			          if(fullEditr!=''){  
			        	  var flag = fullEditr.indexOf($.trim(txt));
			        	  if(flag>-1){
			        		  $(this).attr("selected",true);
			        		  getResultCity($(this).val());
			        		  setTimeout(function (){
			        			  Editr2 = form.find("select[name=EditrRegion2Id]").find("option");
			        			  $(Editr2).each(function(){  //遍历所有option  
			    			          var txt = $(this).text();   //获取option值   
			    			          if(fullEditr!=''){  
			    			        	  var flag = fullEditr.indexOf($.trim(txt));
			    			        	  if(flag>-1){
			    			        		  $(this).attr("selected",true);
			    			        		  getResultCountry($(this).val());
			    			        		  setTimeout(function (){ 
			    			        			  Editr3 = form.find("select[name=EditrRegion3Id]").find("option");
			    			        			  $(Editr3).each(function(){  //遍历所有option  
			    			    			          var txt = $(this).text();   //获取option值   
			    			    			          if(fullEditr!=''){  
			    			    			        	  var flag = fullEditr.indexOf($.trim(txt));
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
				$("#returnAreaEditSearchGCModal").modal("hide");
				
			});
			$("#returnAreaEditSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=returnAreaEditSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
		},
 updatereturnArea:function() {
	 var form=$("form[name=returnAreaEditForm]");
	 $("#returnAreaNameEdit").empty();
	 	$("#longitudeEdit").empty();
	   	$("#latitudeEdit").empty();
		 var areaName=form.find("input[name=areaName]").val();
		var longitude=form.find("input[name=longitude]").val();
	    var latitude=form.find("input[name=latitude]").val();
	    if (areaName == "") {
			$("#returnAreaNameEdit").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
			return false;
	    }
 	 if(areaName !=""){
		    	var returnAreaId=form.find("input[name='returnAreaId']").val();
				 $.ajax({
		    			url:returnAreaEdit.appPath+"/returnArea/onlyAreaNameEdit.do",//验证场站名称唯一性
		    			type:"post",
		    			data:{areaName:areaName,returnAreaId:returnAreaId},
		    			dataType:"json",
		    			success:function(res){ 
		    					if(res.code == "0"){ 
		    						$("#returnAreaNameEdit").empty();
		    						$("#returnAreaNameEdit").append("<font color='red' class='formtips onError emaill'>场站名称重复！</font>");
		    						return false;
		    					}else{
		    						var form = $("form[name=returnAreaEditForm]");
		    						form.ajaxSubmit({
		    							url : returnAreaEdit.appPath + "/returnArea/updateReturnArea.do",
		    							type : "post",
		    							success : function(res) {
		    								var msg = res.msg;
		    								var code = res.code;
		    								if (code == "1") {
		    									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
		    										closeTab("还车区域编辑");
		    										$("#returnAreaList").DataTable().ajax.reload(function(){});
		    									});
		    								} else {
		    									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！" + msg);
		    								}
		    							},
		    							//beforeSubmit : function(formData, jqForm, options) {}
		    						});
		    					}
		    			}
		    		}); 
			 }else{
				 $("#returnAreaNameEdit").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
				return false;
			 }
		    
		    if (longitude == "") {
				$("#longitudeEdit").append("<font color='red' class='formtips onError emaill'>请输入经度！</font>");
				return false;
		    }
		    if (longitude != ""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(longitude)) {
				$("#longitudeEdit").append("<font color='red' class='formtips onError emaill'>经度格式不正确，不包含空格，只能是数字！</font>");
				return false;
		    }
		    if (latitude == "") {
				$("#latitudeEdit").append("<font color='red' class='formtips onError emaill'>请输入纬度！</font>");
				return false;
		    }
		    if (latitude != ""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(latitude)) {
				$("#latitudeEdit").append("<font color='red' class='formtips onError emaill'>纬度格式不正确，不包含空格，只能是数字！</font>");
				return false;
		    }

 		}
}
return returnAreaEdit;
})


