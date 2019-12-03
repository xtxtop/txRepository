var overlaysEdit = [];
var pointsEdit = [];
var ploygonMapEdit;
var drawingManagerEdit
define([],function() {	
var parkEdit = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		resImgPath:getPath("resImg"),
		beforePath:getPath("before"),
		init : function() {
			
			var form = $("form[name=parkEditForm]");
			form.find("select[name='cityId']").change(function(){
				var cityId = form.find("select[name='cityId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: parkEdit.appPath+"/park/getDotByRegionId.do",
		             data: {cityId:cityId},
		             success: function(res){
		            	 var dataItems = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='regionId']").html("");
		            		 var option = "";
		            		 for(var i=0;i<dataItems.length;i++){
		            			 option+="<option  value='"+dataItems[i].dataDictItemId+"'> "+dataItems[i].itemValue+" </option>";
		              		 }
		            		 form.find("select[name='regionId']").html(option);
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
		                	 form.find("select[name='regionId']").html("<option value=''>请选择</option selected>");
		                 }
		             }
				});
			});
			
			$("#supportedServicesParkEdit input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#supportedServicesParkEdit input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#supportedServicesEdit").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			$("#parkCompanyRelIdsEdit input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#parkCompanyRelIdsEdit input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#parkCompanyIdsEdit").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			//上传图片
			$("#editParkPhotoBtn").click(function(){
				$("#editParkPhotoModal").modal("show");

			});
			parkEdit.uploadFile();
			var form1=$("form[name=parkEditForm]");
			var parkNo = form1.find("input[name=parkNo]").val();
			parkEdit.getItemPics(parkNo);
			
		   	var form = $("form[name=parkEditForm]");
	    	var parkNo = form.find("input[name='parkNo']").val();
//			parkEdit.getItemPics(parkNo);
			//编辑图片回填
			$("#editParkPhotoButton").click(function(){
				$("#editParkPhotoModal").modal("hide");
				var form=$("form[name=parkPhotoEditForm]");
				var img=form.find("input[name=parkPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=parkEditForm]");
					form1.find("input[name=parkPicUrl1]").val(img);
					form1.find("#imgEditUrl").css("background-image", "url(" + parkEdit.imgPath + '/' + img + ")");
					$("#editParkPhotoModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			//编辑提交
			$("#savePark").click(function(){
				parkEdit.updatepark();
			});
			//关闭车辆详情编辑页
			$("#closePark").click(function(){
				closeTab("场站编辑");
            });
			//跳转到车位列表页面
			$("#carParkingSpaceOnFormBtn").each(function(){
				$(this).on("click",function(){
					addTab("车位列表", parkEdit.appPath+ "/parkingSpace/toParkingSpace.do?parkNo="+$(this).data("id"), null,function(){
					});
				});
            });
			//跳转到充电桩列表页面
			$("#carChargerOnFormBtn").each(function(){
				$(this).on("click",function(){
					addTab("充电桩列表", parkEdit.appPath+ "/charger/toChargerList.do?parkNo="+$(this).data("id"), null,function(){
					});
				});
            });
			
			//获取坐标
			$("#parkAddSearchGCBtn").click(function(){
				var form1=$("form[name=parkAddSearchGCForm]");
				var gcPoint=form1.find("input[name=gcPoint]").val();
				if(gcPoint){
    				var temp=gcPoint.split(",");
    				longitude=temp[0];
    				latitude=temp[1];
    			}	
				var form=$("form[name=parkAddForm]");
				form.find("input[name=longitude]").val(longitude);
				form.find("input[name=latitude]").val(latitude);
				$("#parkAddSearchGCModal").modal("hide");
				
			});
			$("#parkAddSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=parkAddSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
			
			//修改页面的
			//获取坐标
			$("#searchGCEdit").click(function(){
				//获取已经输入的地址信息
				var form=$("form[name=parkEditForm]");
				var parkType = form.find("input[name=isPloygon]").val();
				if(parkType==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先配置场站类型系统参数！");
				}else if(parkType=="0"){
					$("#parkEditSearchGCModal").modal("show");
					parkEdit.mapInit1("北京市");//地图初始化（先画出地图）
					var addrRegion1Id=form.find("select[name=addrRegion1Id]").val();
					var addrRegion2Id=form.find("select[name=addrRegion2Id]").val();
					var addrRegion3Id=form.find("select[name=addrRegion3Id]").val();
					var addrStreet=form.find("input[name=addrStreet]").val();
					$.ajax({
			    			url:parkEdit.appPath+"/park/searchAddress.do",//获取输入地址信息
			    			type:"post",
			    			data:{addrRegion1Id:addrRegion1Id,
			    				addrRegion2Id:addrRegion2Id,
			    				addrRegion3Id:addrRegion3Id,
			    				addrStreet:addrStreet},
			    			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			    			success:function(res){ 
			    				if(res.code=="1"){
			    					var addressDetail=res.data;
				    				$("#searchGCMapEdit").css({
				    					"width":"100%",
				    					"height":"250px",
				    					"overflow":"hidden"
				    				});
				    				parkEdit.mapInit1(addressDetail);//地图初始化
				    				//输入地址查询经纬度并显示
				    				
			    				}
			    				}
			    			});
					}else if(parkType=="1"){
						$("#parkEditPloygonPointsModal").modal("show");
						$("#ploygonPointsMapEdit").html("");
						$("#ploygonPointsMapEdit").css({
							"width":"100%",
							"height":"450px",
							"overflow":"hidden"
						});
						setTimeout(function (){
							parkEdit.ploygonPointsMapEditInit();//地图初始化（先画出地图）
							ploygonMapEdit.addEventListener("click",function(e){
								if(pointsEdit.length>0){
									drawingManagerEdit.close();
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先删除多边形");
								};
							});
						},400)
					}
			});
			 //删除覆盖物
			$("#deleteEditPloygonPointsBtn").click(function(){
				ploygonMapEdit.clearOverlays();
				pointsEdit.splice(0,pointsEdit.length);
				var form = $("form[name=parkEditForm]");
				form.find("input[name='ploygonPoints']").val("");
				form.find("input[name='longitude']").val("");
				form.find("input[name='latitude']").val("");
			});
			//点击保存时的校验计算
			$("#parkEditPloygonPointsBtn").click(function(){
				if(pointsEdit==null||pointsEdit.length==0){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先画多变形");
					return false;
				}else if(overlaysEdit.length>8){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "多边形不能大于8个边");
					return false;
				}else{
					$.ajax({
						url:parkEdit.appPath+"/park/checkPloygonPoints.do",
						type:"post",
						data:{points:JSON.stringify(pointsEdit)},
						success:function(res){ 
							if(res.code=="1"){
								var longitudeAndLatitude=res.data;
								var form=$("form[name=parkEditForm]");
								var ploygonPoints = "";
								for(var i=0;i<pointsEdit.length;i++){
						        	var longitude = pointsEdit[i].longitude;
						        	var latitude = pointsEdit[i].latitude;
						        	ploygonPoints+=longitude+" "+latitude+",";
						        }
								ploygonPoints+=pointsEdit[0].longitude+" "+pointsEdit[0].latitude;
								form.find("input[name=ploygonPoints]").val(ploygonPoints);
								form.find("input[name=longitude]").val(longitudeAndLatitude.longitude);
								form.find("input[name=latitude]").val(longitudeAndLatitude.latitude);
								$("#parkEditPloygonPointsModal").modal("hide");
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
		ploygonPointsMapEditInit:function() {
			var map = new BMap.Map("ploygonPointsMapEdit",{enableMapClick:false}); // 创建地图实例
			ploygonMapEdit = map;
			var form=$("form[name=parkEditForm]");
			var lng = form.find("input[name=longitude]").val();
			var lat = form.find("input[name=latitude]").val();
			var point = new BMap.Point(116.331398,39.897445);
			if(lng!=""&&lat!=""){
				point = new BMap.Point(lng,lat);
				var addrStreet = form.find("input[name=addrStreet]").val();
				var formSearch=$("form[name=ploygonPointsSearchEditForm]");
				formSearch.find("input[name=gcAddress]").val(addrStreet);
			}else{
				var myCity = new BMap.LocalCity();
				myCity.get(
					function myFun(result){
						var cityName = result.name;
						map.setCenter(cityName);
					}
				);
			}
			map.centerAndZoom(point,14);
			map.panTo(point);
			map.enableScrollWheelZoom(); //启用鼠标滚动对地图放大缩小
			setTimeout(function(){
				var ploygonPointsHidden = form.find("input[name=ploygonPoints]").val();
				if(ploygonPointsHidden!=""){
					var ploygonPointsArr = ploygonPointsHidden.split(",");
					var ploygonArr = [];
					for(var i=0;i<ploygonPointsArr.length;i++){
						var pointTemp = ploygonPointsArr[i].split(" ");
						var p = new BMap.Point(pointTemp[0],pointTemp[1]);
						ploygonArr.push(p);
						var obj = new Object();
						obj.longitude = pointTemp[0];
						obj.latitude = pointTemp[1];
						pointsEdit.push(obj);
					}
					var polygon = new BMap.Polygon(ploygonArr, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5});  //创建多边形
					map.addOverlay(polygon);   //增加多边形
					map.setViewport(ploygonArr);//让多边形显示在最佳视野内
				}
				//根据地址搜索
				$("#ploygonPointsSearchAddressEdit").click(function(){
					var form=$("form[name=ploygonPointsSearchEditForm]");
					// 创建地址解析器实例
					var myGeo = new BMap.Geocoder();
					// 将地址解析结果显示在地图上,并调整地图视野
					myGeo.getPoint(form.find("input[name=gcAddress]").val(), function(reslutPoint){
						if (reslutPoint) {
							map.centerAndZoom(reslutPoint, 14);
							map.addOverlay(new BMap.Marker(reslutPoint));
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "您选择地址没有解析到结果!");
						}
					}, "中国");
				});
				//鼠标绘制完成回调方法   获取各个点的经纬度
				var overlaycomplete = function(e){
					overlaysEdit.push(e.overlay);
					var path = e.overlay.getPath();//Array<Point> 返回多边型的点数组
					for(var i=0;i<path.length;i++){
						var obj = new Object();
						obj.longitude = path[i].lng;
						obj.latitude = path[i].lat;
						pointsEdit.push(obj);
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
				drawingManagerEdit = new BMapLib.DrawingManager(map, {
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
				drawingManagerEdit.addEventListener('overlaycomplete', overlaycomplete);
				
			},1000)
		   
		},
		mapInit1:function(addressDetail) {
			var map = new BMap.Map("searchGCMapEdit",{enableMapClick:false}); // 创建地图实例
			map.centerAndZoom(addressDetail, 15);
			var localSearch = new BMap.LocalSearch(map);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var form=$("form[name=parkEditSearchGCForm]");
				form.find("input[name=gcAddress]").val(addressDetail);
				form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
			});
			localSearch.search(addressDetail);
			//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
			//map.enableScrollWheelZoom(false);
			//map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			map.addEventListener('click', function(e){
			    var form=$("form[name=parkEditSearchGCForm]");
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
				var form=$("form[name=parkEditSearchGCForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 12);
					form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
			$("#parkEditSearchGCBtn").click(function(){
				var form1=$("form[name=parkEditSearchGCForm]");
				var gcPoint=form1.find("input[name=gcPoint]").val();
				if(gcPoint){
    				var temp=gcPoint.split(",");
    				longitude=temp[0];
    				latitude=temp[1];
    			}	
				var form=$("form[name=parkEditForm]");
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
				$("#parkEditSearchGCModal").modal("hide");
				
			});
			$("#parkEditSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=parkEditSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
		},
 updatepark:function() {
	 
	// 提交表单前数据验证
		$("#parkNameEdit").empty();
	   	$("#addrStreetEdit").empty();
	   	$("#longitudeEdit").empty();
	   	$("#latitudeEdit").empty();
		$("#electronicFenceRadiusEdit").empty();
		$("#parkTypeEdit").empty();
		$("#isPublicEdit").empty();
	   	$("#styleIdEdit").empty();
	   	$("#ownerTypeEdit").empty();
	   	$("#serviceFeeGetAdde").empty();
	   	$("#serviceFeeBackAdde").empty();
		$("#businessHoursAdde").empty()
	   	$("#electricPriceEdit").empty();
	   	$("#parkRentEdit").empty();
	   	$("#payRentDayOfMonthEdit").empty();
	 	$("span[name='supportedServices']").empty();
		$("#serviceFeeGetAdd").empty();
	   	$("#serviceFeeBackAdd").empty();
	   	$("#parkPicUrl1Edit").empty();
	   	var form = $("form[name=parkEditForm]");
	   	var serviceFeeGet=form.find("input[name=serviceFeeGet]").val();
	   	var serviceFeeBack=form.find("input[name=serviceFeeBack]").val();
	    var parkName=form.find("input[name=parkName]").val();
	    var addrStreet=form.find("input[name=addrStreet]").val();
	    var longitude=form.find("input[name=longitude]").val();
	    var latitude=form.find("input[name=latitude]").val();
	    var electronicFenceRadius=form.find("input[name=electronicFenceRadius]").val();
	    var parkType=form.find("select[name=parkType]").val();
	    var isPublic=form.find("select[name=isPublic]").val();
	    var styleId=form.find("select[name=styleId]").val();
	    var ownerType=form.find("select[name=ownerType]").val();
	    var businessHours=form.find("select[name=businessHours]").val();
	    var electricPrice=form.find("input[name=electricPrice]").val();
	    var parkRent=form.find("input[name=parkRent]").val();
	    var payRentDayOfMonth=form.find("input[name=payRentDayOfMonth]").val();
	    var parkingSpaceNumber=form.find("input[name=parkingSpaceNumber]").val();
	    var parkPicUrl1=$("#parkPicUrl1").src;
	    if (parkName == "") {
			$("#parkNameEdit").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
			return false;
     }
	   
	    if (addrStreet == "") {
			$("#addrStreetEdit").append("<font color='red' class='formtips onError emaill'>请输入详细地址！</font>");
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
/*		    if (form.find("input[name='supportedServices']").val()=="") {
			$("span[name='supportedServices']").append("<font color='red'>请选择服务！</font>");
			return false;
		}*/
	    var isPloygon = form.find("input[name=isPloygon]").val();
	    if(isPloygon=="0"){
		    if (electronicFenceRadius == "") {
				$("#electronicFenceRadiusEdit").append("<font color='red' class='formtips onError emaill'>请输入电子围栏半径！</font>");
				return false;
         }else if(!/^[0-9]*[1-9][0-9]*$/.test(electronicFenceRadius)){
         	$("#electronicFenceRadiusEdit").append("<font color='red' class='formtips onError emaill'>电子围栏半径只能输入正整数,不能有空格！</font>");
				return false;
         }
	    }
	    if (parkType == "") {
			$("#parkTypeEdit").append("<font color='red' class='formtips onError emaill'>请选择类别！</font>");
			return false;
     }
	    if (isPublic == "") {
			$("#isPublicEdit").append("<font color='red' class='formtips onError emaill'>请选择是否开放！</font>");
			return false;
     }
/*		    if (styleId == "") {
			$("#styleIdEdit").append("<font color='red' class='formtips onError emaill'>请选择样式！</font>");
			return false;
     }*/
/*	    	if (ownerType == "") {
			$("#ownerTypeEdit").append("<font color='red' class='formtips onError emaill'>请选择所属！</font>");
			return false;
     }
 	if (electricPrice == "") {
			$("#electricPriceEdit").append("<font color='red' class='formtips onError emaill'>请输入场站电费！</font>");
			return false;
     }*/
	    if(parkingSpaceNumber != "" && !/^[0-9]*$/.test(parkingSpaceNumber)){
     	$("#parkingSpaceNumberEdit").append("<font color='red' class='formtips onError emaill'>车位数只能输入正整数,不能有空格！</font>");
			return false;
     }
 	if(electricPrice != ""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(electricPrice)){
     	$("#electricPriceEdit").append("<font color='red' class='formtips onError emaill'>格式不正确，不能有空格，场站电费只能输入整数或小数！</font>");
			return false;
     }
/*	    	if (parkRent == "") {
			$("#parkRentEdit").append("<font color='red' class='formtips onError emaill'>请输入场站租金！</font>");
			return false;
     }else */
     if(parkRent != "" && !/^[0-9]*[1-9][0-9]*$/.test(parkRent)){
     	$("#parkRentEdit").append("<font color='red' class='formtips onError emaill'>场站租金只能输入正整数或是输入格式不对！</font>");
			return false;
     }
/*            if (payRentDayOfMonth == "") {
			$("#payRentDayOfMonthEdit").append("<font color='red' class='formtips onError emaill'>请输入交租日期！</font>");
			return false;
     }
 	if (payRentDayOfMonth != "") {
			if(!/^[0-9]*[1-9][0-9]*$/.test(payRentDayOfMonth)){
				$("#payRentDayOfMonthEdit").append("<font color='red' class='formtips onError emaill'>交租日期只能输入正整数或是输入格式不对！</font>");
				return false;
         }else if(31<payRentDayOfMonth.replace(/\s+/g,"")){
         	$("#payRentDayOfMonthEdit").append("<font color='red' class='formtips onError emaill'>交租日期天数不能大于31，正确格式比如（1~31）！</font>");
				return false;
         }else if(payRentDayOfMonth.replace(/\s+/g,"")<=0){
         	$("#payRentDayOfMonthEdit").append("<font color='red' class='formtips onError emaill'>交租日期天数不能为0，正确格式比如（1~31）！</font>");
				return false;
         }
 	}*/
 	if(businessHours == ""){
 		$("#businessHoursAdde").append("<font color='red' class='formtips onError emaill'>请输入营业时间！</font>");
 	}
 	if (form.find("input[name='serviceFeeGet']").val()=="") {
 		$("#serviceFeeGetAdde").append("<font color='red' class='formtips onError emaill'>请输入取车附加费金额！</font>");
 		return false;
 	}
	
 	if (form.find("input[name='serviceFeeGet']").val()!=""&&!/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/.test(form.find("input[name='serviceFeeGet']").val())) {
 		$("#serviceFeeGetAdde").append("<font color='red' class='formtips onError emaill'>取车附加费输入有误(正数或小数后两位)！</font>");
 		return false;
 	}
 	
 	if (form.find("input[name='serviceFeeBack']").val()=="") {
 		$("#serviceFeeBackAdde").append("<font color='red' class='formtips onError emaill'>请输入还车附加费！</font>");
 		return false;
 	}
	
 	if (form.find("input[name='serviceFeeBack']").val()!=""&&!/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/.test(form.find("input[name='serviceFeeBack']").val())) {
 		$("#serviceFeeBackAdde").append("<font color='red' class='formtips onError emaill'>还车附加费输入有误(正数或小数后两位)！</font>");
		return false;
 	}
 	if (parkPicUrl1 == "") {
		$("#parkPicUrl1Edit").append("<font color='red' class='formtips onError emaill'>请上传图片！</font>");
		return false;
    }
 	 if(parkName !=""){
		    	var parkNo=form.find("input[name='parkNo']").val();
				 $.ajax({
		    			url:parkEdit.appPath+"/park/onlyParkNameEdit.do",//验证场站名称唯一性
		    			type:"post",
		    			data:{parkName:parkName,parkNo:parkNo},
		    			dataType:"json",
		    			success:function(res){ 
		    					if(res.code == "0"){ 
		    						$("#parkNameEdit").empty();
		    						$("#parkNameEdit").append("<font color='red' class='formtips onError emaill'>场站名称重复！</font>");
		    						return false;
		    					}else{
		    						var form = $("form[name=parkEditForm]");
		    						form.ajaxSubmit({
		    							url : parkEdit.appPath + "/park/updatePark.do",
		    							type : "post",
		    							success : function(res) {
		    								var msg = res.msg;
		    								var code = res.code;
		    								if (code == "1") {
		    									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
		    										closeTab("场站编辑");
		    										$("#parkList").DataTable().ajax.reload(function(){});
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
				 $("#parkNameEdit").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
				return false;
			 }
	
	 
	
 		},uploadFile:function(){
 			var relativePath="park_photo";
 			var resImgPath=parkEdit.resImgPath;
 			var imgPath=parkEdit.imgPath;
 			var storePath=resImgPath+"/" + relativePath;
 			//relativePath="";测试用
 			//storePath="";
 			$("#appUpload").load("res/tpl/uploadFile.html",function(){
 		        var manualUploader = new qq.FineUploader({
 		            element: document.getElementById("parkFineEditUploader"),
 		            template: "qq-template-manual-trigger-m",
 		            request: {
 		                endpoint: parkEdit.appPath+"/upload/uploadFileNew.do",
 		                method: "post",
 		                params:{
 		                	storePath:storePath,
 		                	resPath:relativePath
 		                }
 		            },
 		            validation: {
 		            	//itemLimit:1,
 		                allowedExtensions: ["jpeg", "jpg", "gif", "png"]//,//"xls","doc","xlsx","docx","pdf',"txt"],
 		                //sizeLimit: 40960000, // 50 kB = 50 * 1024 bytes16.   
 		                //minSizeLimit:33333
 		            },
 		            thumbnails: {
 		                placeholders: {
 		                    waitingPath: "res/dep/fine-uploader/placeholders/waiting-generic.png",
 		                    notAvailablePath: "res/dep/fine-uploader/placeholders/not_available-generic.png"
 		                }
 		            },
 		            autoUpload: false,
 		            //debug: true,
 		           /* deleteFile: {
 		                enabled: true,
 		                method: "post",
 		                endpoint: brand.appPath+"/upload/deleteFile.do",
 		                params: {
 		                    filePath: "bar"
 		                }
 		            },*/
 		            callbacks: {
 		                onUpload: function (id, fileName) {
 		                },
 		                onSubmitted: function (id, fileName) {
 		                	var li=$("#parkFineEditUploader .qq-uploader .qq-upload-list").find("li");
                        	if(li != null && li.length < 6){
        						li=$("#parkFineEditUploader .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
        						parkEdit.itemPicsBindEvent(li);
	                        	li.find(".qq-upload-remove").hide();
        					}else{
	                        	$("#parkFineEditUploader .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]")[0].remove();
        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "上传图片不能超过5张！");
        					}
 		                },
 		                onComplete: function(id, fileName, responseJSON) {
                        	var form = $("form[name='parkPhotoEditForm']");
                        	var form1=$("form[name=parkEditForm]");
 		                	if(form.find("input[name='parkPicUrl1']").val() != ""){
 		                		var path = form.find("input[name='parkPicUrl1']").val() + "," + responseJSON.data[0]
 		                    	form.find("input[name='parkPicUrl1']").val(path);                        	
 		                		form1.find("input[name=parkPicUrl1]").val(path);
 		                	}else{
 		                		form.find("input[name='parkPicUrl1']").val(responseJSON.data[0]);
 		                		form1.find("input[name=parkPicUrl1]").val(responseJSON.data[0]);

 		                	}
 		                	var li=$("#parkFineEditUploader .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
 		                	li.attr("data-filepath",responseJSON.data[0]);
 		                	if(responseJSON.success){
 		                		li.find(".qq-upload-remove").show();                  		
 		                	}
 		                },
 		                onProgress: function(id,  fileName,  loaded,  total) {
 		                },
 		                onCancel: function(id,  fileName) {                       	
 		                }, 
 		                /*onDelete: function(id) {
 		                },
 		                onDeleteComplete: function(id, xhr, isError) {
 		                }*/
 		            }/*,
 		            params:{
 		            	
 		            }*/
 		        });
 		        $("#parkFineEditUploader .trigger-upload").on("click", function() {
 		            manualUploader.uploadStoredFiles();
 		        }); 
 		    });	        	
 		},
 		deleteFile:function(filePaths,fn){
 			var resPath="";
 			var regexp = /res\/img/g;	    		
 			if(filePaths.match(regexp)){
 				resPath="";
 			}else{
 				resPath=parkEdit.resImgPath
 			}
 			$.ajax({
 				url: parkEdit.appPath+"/upload/deleteFile.do?filePaths="+filePaths,
 				data:{resPath:resPath},
 				success: function(data){
 					if(data.code=1){
 						var form = $("form[name='parkPhotoEditForm']");
                    	var input = form.find("input[name='parkPicUrl1']");
        				if(input != ""){
        					var aa = input.val();
                    		var urls = input.val().split(",");
                    		var newUrls = [];
                    		if(urls != null){
                    			for(var i = 0 ; i < urls.length; i++){
                        			if(urls[i] != filePaths){
                        				newUrls[newUrls.length] = urls[i];
                        			}
                        		}
                    			input.val(newUrls);  
                    			var form1=$("form[name=parkEditForm]");
            					form1.find("input[name=parkPicUrl1]").val(newUrls);
                    		}
                    	}
 						if(fn){
 							fn();
 						}
 					}
 				}
 			});        	
 		},
 		getUploadFilePath:function(){
 			var filePath=[];	        	
 			$("#parkFineEditUploader .qq-upload-success").each(function(){
 				filePath.push($(this).data("filepath"));                		
 			});
 			return filePath;
 		},
 		getItemPics:function(parkNo){
 			var uploadTpl = $("#upload-item-template").html();
 			$.ajax({
 				url: parkEdit.appPath+"/park/getPsrkPitureUrl.do?parkNo="+parkNo, 
 				success: function(data){
 					//$("#parkFineEditUploader .qq-uploader .qq-upload-list").html();
 					if(data){
 						for(x in data){
 							var to=$("#parkFineEditUploader .qq-uploader .qq-upload-list");
 							var li=$(uploadTpl).appendTo(to);
 							var regexp = /res\/img/g;
 							var imgsrc="";
 							if(data[x].match(regexp)){
 								imgsrc=parkEdit.appPath+"/"+data[x];
 							}else{
 								imgsrc=parkEdit.imgPath+"/"+data[x]
 							}
 							li.attr("data-filepath",data[x]).find("img").attr("src",imgsrc);//.addClass("qq-file-id-"+x).attr("qq-file-id",x);
 							var length=data[x].split("/").length;
 							li.find(".qq-upload-file").text(data[x].split("/")[length-1]);
 							//li.find(".qq-upload-size").text(imgSize+"kb");
 							parkEdit.itemPicsBindEvent(li);
 							/*if(x==0){
 								li.find(".qq-upload-up").hide();
 							}
 							if(x==data.length-1){
 								li.find(".qq-upload-down").hide();
 							}*/
 						}
 					}
 				}
 			});
 		},
 		itemPicsBindEvent:function(obj){
 			obj.find(".qq-upload-up").on("click",function(){
 				if(obj.prev().length){
 		  		obj.insertBefore(obj.prev());
 					//obj.find(".qq-upload-up").show();
 				}/*else{
 					obj.find(".qq-upload-up").hide();
 				}*/
 			});
 			obj.find(".qq-upload-down").on("click",function(){
 				if(obj.next().length){
 		  		obj.insertAfter(obj.next());
 		  		//obj.find(".qq-upload-down").show();
 				}/*else{
 					obj.find(".qq-upload-down").hide();
 				}*/
 			});	
 			obj.find(".qq-upload-remove").on("click",function(){
 				var filePath=obj.data("filepath");
 				var temp=$(this);
 				parkEdit.deleteFile(filePath,function(){
 					obj.remove();
 				});
 			});
 		}	
}
return parkEdit;
})
//获得县、区
function getResultCountry(a){
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#itemarea").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='col-sm-4' name='addrRegion3Id' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#itemarea").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}
//获得市
function getResultCity(d){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcity").html("");
                         var select="<select name='addrRegion2Id'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountry(this.value)'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			getResultCountry(pId);
              			$("#itemcity").append(select);
					} 
	 },"json");
}
