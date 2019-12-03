var ploygonMap;
var overlays = [];
var points = [];
var drawingManagerAdd;
define([],function() {	
var parkAdd = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		resImgPath:getPath("resImg"),
		beforePath:getPath("before"),
		init : function() {
			//加盟商
			$("#relateFranchiseeAdd").click(function(){
				$("#relateFranchiseeModel").modal("show");
				parkAdd.pageRelateFranchisee();
			});
			//加盟商列表查询
			$("#relateFranchiseeSearch").click(function(){
				parkAdd.pageRelateFranchisee();
	        });
		
			var form = $("form[name=parkAddForm]");
			form.find("select[name='cityId']").change(function(){
				var cityId = form.find("select[name='cityId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: parkAdd.appPath+"/park/getDotByRegionId.do",
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
			
			
			$("#supportedServicesPark input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#supportedServicesPark input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#supportedServices").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			$("#parkCompanyRelIds input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("#parkCompanyRelIds input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#parkCompanyIds").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			//上传图片
			$("#addParkPhotoButton").click(function(){
				$("#parkPhotoAddModal").modal("show");
			});
			//新增图片回填
			$("#addParkPhotoBtn").click(function(){
				$("#parkPhotoAddModal").modal("hide");

				var form=$("form[name=parkphotoForm]");
				var img=form.find("input[name=parkPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=parkAddForm]");
					form1.find("input[name=parkPicUrl1]").val(img);
					form1.find("parkPicUrlImg").css("background-image","url("+parkAdd.imgPath+'/'+img+")");
					$("#parkPhotoAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			//新增提交
			$("#addPark").click(function(){
				parkAdd.savepark();
			});
			//新增页面的关闭
			$("#addclosePark").click(function(){
				closeTab("新增场站");
			});
			//跳转到车位列表页面
			$("#carParkingSpaceOnFormBtn").each(function(){
				$(this).on("click",function(){
					addTab("车位列表", parkAdd.appPath+ "/parkingSpace/toParkingSpace.do?parkNo="+$(this).data("id"), null,function(){
					});
				});
            });
			//跳转到充电桩列表页面
			$("#carChargerOnFormBtn").each(function(){
				$(this).on("click",function(){
					addTab("充电桩列表", parkAdd.appPath+ "/charger/toChargerList.do?parkNo="+$(this).data("id"), null,function(){
					});
				});
            });
			
			//获取坐标
			$("#searchGC").click(function(){
				var form=$("form[name=parkAddForm]");
				var parkType = form.find("input[name=isPloygon]").val();
				if(parkType==""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先配置场站类型系统参数！");
				}else if(parkType=="0"){
					$("#parkAddSearchGCModal").modal("show");
					$("#searchGCMap").css({
						"width":"100%",
						"height":"250px",
						"overflow":"hidden"
					});
					parkAdd.mapInit("广东省深圳市龙岗区");//地图初始化（先画出地图）
					//获取已经输入的地址信息
					var addrRegion1Id=form.find("select[name=addrRegion1Id]").val();
					var addrRegion2Id=form.find("select[name=addrRegion2Id]").val();
					var addrRegion3Id=form.find("select[name=addrRegion3Id]").val();
					var addrStreet=form.find("input[name=addrStreet]").val();
					$.ajax({
						url:parkAdd.appPath+"/park/searchAddress.do",//获取输入地址信息
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
										parkAdd.mapInit(addressDetail);//地图初始化
									}
									//输入地址查询经纬度并显示
								}
							}
					});
				}else if(parkType=="1"){
					$("#parkAddPloygonPointsModal").modal("show");
					$("#ploygonPointsMap").html("");
					$("#ploygonPointsMap").css({
						"width":"100%",
						"height":"450px",
						"overflow":"hidden"
					});
					setTimeout(function (){
						parkAdd.ploygonPointsMapInit();//地图初始化（先画出地图）
					},400)
					setTimeout(function (){
						parkAdd.ploygonPointsMapInit();//地图初始化（先画出地图）
						ploygonMap.addEventListener("click",function(e){
							if(points.length>0){
								drawingManagerAdd.close();
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先删除多边形");
							};
						});
					},400)
				}
			});
			$("#parkAddSearchGCBtn").click(function(){
				var form1=$("form[name=parkAddSearchGCForm]");
				var gcPoint=form1.find("input[name=gcPoint]").val();
				var longitude;
				var latitude;
				if(gcPoint){
    				var temp=gcPoint.split(",");
    				longitude=temp[0];
    				latitude=temp[1];
    			}	
				var form=$("form[name=parkAddForm]");
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
			        		  getResultCityAddPark($(this).val());
			        		  setTimeout(function (){
			        			  addr2 = form.find("select[name=addrRegion2Id]").find("option");
			        			  $(addr2).each(function(){  //遍历所有option  
			    			          var txt = $(this).text();   //获取option值   
			    			          if(fullAddr!=''){  
			    			        	  var flag = fullAddr.indexOf($.trim(txt));
			    			        	  if(flag>-1){
			    			        		  $(this).attr("selected",true);
			    			        		  getResultCountryAddPark($(this).val());
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
				$("#parkAddSearchGCModal").modal("hide");
				
			});
			$("#parkAddSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=parkAddSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
			
			parkAdd.uploadFile();
			
			 //删除覆盖物
			$("#deletePloygonPointsBtn").click(function(){
				ploygonMap.clearOverlays();
				points.splice(0,points.length);
				var form = $("form[name=parkAddForm]");
				form.find("input[name='ploygonPoints']").val("");
				form.find("input[name='longitude']").val("");
				form.find("input[name='latitude']").val("");
			});
			//点击保存时的校验计算
			$("#parkAddPloygonPointsBtn").click(function(){
				if(points==null||points.length==0){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先画多变形");
					return ;
				}else if(overlays.length>8){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "多边形不能大于8个边");
					return ;
				}else{
					$.ajax({
						url:parkAdd.appPath+"/park/checkPloygonPoints.do",
						type:"post",
						data:{points:JSON.stringify(points)},
						success:function(res){ 
							if(res.code=="1"){
								var longitudeAndLatitude=res.data;
								var form=$("form[name=parkAddForm]");
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
								$("#parkAddPloygonPointsModal").modal("hide");
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
							    					getResultCityAddPark($(this).val());
							    					setTimeout(function (){
							    						addr2 = form.find("select[name=addrRegion2Id]").find("option");
							    						$(addr2).each(function(){  //遍历所有option  
							    							var txt = $(this).text();   //获取option值   
							    							if(fullAddr!=''){  
							    								var flag = fullAddr.indexOf($.trim(txt));
							    								if(flag>-1){
							    									$(this).attr("selected",true);
							    									getResultCountryAddPark($(this).val());
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
		
		//加盟商列表
		pageRelateFranchisee:function(){
			var form = $("form[name=relateFranchiseeSerachForm]");
			var franchiseeNo = form.find("input[name='franchiseeNo']").val();
			var franchiseeBtnTpl = $("#relateFranchiseeBtnTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(franchiseeBtnTpl);
			var table = $('#relateFranchiseeListAdd').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : parkAdd.appPath+"/franchisee/pageListAuditfranchisee.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "franchiseeNo" : franchiseeNo
										});
					},
					"dataSrc" : function(json) {
						json.recordsTotal = json.rowCount;
						json.recordsFiltered = json.rowCount;
						json.data = json.data;
						return json.data;
					},
					error : function(xhr, error, thrown) {
					}
				},
				"columns" : [ 
				{ "title":"","data": "franchiseeNo","width":"5px"},
				{
					"title" : "加盟商编号",
					"data" : "franchiseeNo"
				},{
					"title" : "加盟商名称",
					"data" : "franchiseeName"
				}],
				"dom" : "<'row'<''><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#franchiseeToolssssAdd").empty().append('<button type="button"  class="btn-new-type sysworkerMgCar-batch-adddel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue sysworkerMgCar-batch-addclose">关闭</button>');
//						$("#franchiseeToolssssAdd").append('');
						$(".sysworkerMgCar-batch-adddel").on("click",function(){
							var ids=[];
							var franchiseeName="";
							var len=$('#relateFranchiseeListAdd tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择加盟商！")
							}else{
								$("#relateFranchiseeListAdd tbody").find("input:checked").each(function(){
									
		        					ids.push($(this).val());
		        					
		        					franchiseeName = $(this).next('input').val();
		        					
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "加盟商只能选择一个！")
	        					}else{
	        						var form = $("form[name=parkAddForm]");
	        	     				form.find("input[name='franchiseeId']").val(ids);
	        	     				form.find("input[name='franchiseeName']").val(franchiseeName);
	        	     				$("#relateFranchiseeModel").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#relateFranchiseeListAdd thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#relateFranchiseeListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#relateFranchiseeListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgCar-batch-addclose").on("click",function(){
							$("#relateFranchiseeModel").modal("hide");
							$(".modal-backdrop").hide();
							$('#relateFranchiseeListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
					   {
						 "targets" : [0],
						 "orderable":false,
						 "render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="franchiseeNo" value="' + full.franchiseeNo + '"><input type="hidden" value="' + full.franchiseeName + '">';
						}
					   }
					   ]
			});
		},
		
		ploygonPointsMapInit:function() {
			var formAdd=$("form[name=parkAddForm]");
			var map = new BMap.Map("ploygonPointsMap",{enableMapClick:false}); // 创建地图实例
			ploygonMap = map;
			var point = new BMap.Point(116.331398,39.897445);
			map.centerAndZoom(point,14);
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
				var form=$("form[name=ploygonPointsSearchForm]");
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
				var ploygonPointsHidden = formAdd.find("input[name=ploygonPoints]").val();
				if(ploygonPointsHidden!=""){
					var ploygonPointsArr = ploygonPointsHidden.split(",");
					var ploygonArr = [];
					ploygonArr.slice(0,ploygonArr.length);
					for(var i=0;i<ploygonPointsArr.length;i++){
						var pointTemp = ploygonPointsArr[i].split(" ");
						var point =  new BMap.Point(pointTemp[0],pointTemp[1]);
						ploygonArr.push(point);
					}
					var polygon = new BMap.Polygon(ploygonArr, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5});  //创建多边形
					map.addOverlay(polygon);   //增加多边形
					map.setViewport(ploygonArr);//让多边形显示在最佳视野内
				}
			}
			//根据地址搜索
			$("#ploygonPointsSearchAddress").click(function(){
				var form=$("form[name=ploygonPointsSearchForm]");
				// 创建地址解析器实例
				var myGeo = new BMap.Geocoder();
				// 将地址解析结果显示在地图上,并调整地图视野
				myGeo.getPoint(form.find("input[name=gcAddress]").val(), function(reslutPoint){
					if (reslutPoint) {
						map.centerAndZoom(reslutPoint, 16);
						map.addOverlay(new BMap.Marker(reslutPoint));
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "您选择地址没有解析到结果!");
					}
				}, "中国");
			});
			//鼠标绘制完成回调方法   获取各个点的经纬度
			var overlaycomplete = function(e){
				overlays.push(e.overlay);
				var path = e.overlay.getPath();//Array<Point> 返回多边型的点数组
				points.slice(0,points.length);
				for(var i=0;i<path.length;i++){
					var obj = new Object();
					obj.longitude = path[i].lng;
					obj.latitude = path[i].lat;
					points.push(obj);
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
			drawingManagerAdd = new BMapLib.DrawingManager(map, {
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
			drawingManagerAdd.addEventListener('overlaycomplete', overlaycomplete);
			
		},
		renderReverse:function(response) {
            if (response&&response.status&&response.status=="OK") {
            	var location = response.result;
            } else {
                alert(0);
            }
        },
		mapInit:function(addressDetail) {
			$("#searchGCMap").html("");
			var map = new BMap.Map("searchGCMap"); // 创建地图实例
			map.centerAndZoom(addressDetail, 15);
			var localSearch = new BMap.LocalSearch(map);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var form=$("form[name=parkAddSearchGCForm]");
				form.find("input[name=gcAddress]").val(addressDetail);
				form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
			});
			localSearch.search(addressDetail);
			//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
			//map.enableScrollWheelZoom(false);
//			map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			map.addEventListener('click', function(e){
			    var form=$("form[name=parkAddSearchGCForm]");
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
				var form=$("form[name=parkAddSearchGCForm]");
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
			var map = new BMap.Map("searchGCMap",{enableMapClick:false}); // 创建地图实例
			map.centerAndZoom(addressDetail, 15);
			var localSearch = new BMap.LocalSearch(map);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var form=$("form[name=parkAddSearchGCForm]");
				form.find("input[name=gcAddress]").val(addressDetail);
				form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
			});
			localSearch.search(addressDetail);
			//map.centerAndZoom(new BMap.Point(103.388611,35.563611),12);// 初始化地图，设置中心点坐标和地图级别
			//map.enableScrollWheelZoom(false);
			//map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			map.addEventListener('click', function(e){
			    var form=$("form[name=parkAddSearchGCForm]");
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
				var form=$("form[name=parkAddSearchGCForm]");
				var localSearch = new BMap.LocalSearch(map);
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(form.find("input[name=gcAddress]").val(), 12);
					form.find("input[name=gcPoint]").val(poi.point.lng + "," + poi.point.lat);
				});
				localSearch.search(form.find("input[name=gcAddress]").val());
			});
//			$("#parkAddSearchGCBtn").click(function(){
//				var form1=$("form[name=parkAddSearchGCForm]");
//				var gcPoint=form1.find("input[name=gcPoint]").val();
//				if(gcPoint){
//    				var temp=gcPoint.split(",");
//    				longitude=temp[0];
//    				latitude=temp[1];
//    			}	
//				var gcAddress=form1.find("input[name=gcAddress]").val();
//				var form=$("form[name=parkAddForm]");
//				form.find("input[name=longitude]").val(longitude);
//				form.find("input[name=latitude]").val(latitude);
//				$("#parkAddSearchGCModal").modal("hide");
//				
//			});
			$("#parkAddSearchGCModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=parkAddSearchGCForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
		},
savepark:function() {
	// 提交表单前数据验证
	$("#parkNameAdd").empty();
	$("#addrRegion1IdAdd").empty();
   	$("#addrStreetAdd").empty();
   	$("#longitudeAdd").empty();
   	$("#latitudeAdd").empty();
	$("#electronicFenceRadiusAdd").empty();
	$("#parkTypeAdd").empty();
	$("#isPublicAdd").empty();
   	$("#styleIdAdd").empty();
   	$("#parkingSpaceNumberAdd").empty();
//   	$("#carNumberAdd").empty();
//   	$("#chargerNumberAdd").empty();
   	$("span[name='supportedServices']").empty();
   	$("#ownerTypeAdd").empty();
   	$("#businessHoursAdd").empty();
   	$("#parkPicUrl1Add").empty();
   	$("#electricPriceAdd").empty();
   	$("#parkRentAdd").empty();
   	$("#payRentDayOfMonthAdd").empty();
   	$("#serviceFeeGetAdd").empty();
   	$("#serviceFeeBackAdd").empty();
   	$("#cityIdAdd").empty();
   	var form = $("form[name=parkAddForm]");
   	var serviceFeeGet=form.find("input[name=serviceFeeGet]").val();
   	var serviceFeeBack=form.find("input[name=serviceFeeBack]").val();
    var parkName=form.find("input[name=parkName]").val();
    var addrRegion1Id=form.find("select[name=addrRegion1Id]").val();
    var addrStreet=form.find("input[name=addrStreet]").val();
    var longitude=form.find("input[name=longitude]").val();
    var latitude=form.find("input[name=latitude]").val();
    var electronicFenceRadius=form.find("input[name=electronicFenceRadius]").val();
    var parkType=form.find("select[name=parkType]").val();
    var isPublic=form.find("select[name=isPublic]").val();
    var styleId=form.find("select[name=styleId]").val();
    var cityId=form.find("select[name=cityId]").val();
    var parkingSpaceNumber=form.find("input[name=parkingSpaceNumber]").val();
    var carNumber=form.find("input[name=carNumber]").val();
    var chargerNumber=form.find("input[name=chargerNumber]").val();
    var supportedServices=form.find("input[name='supportedServices']").val();
    var ownerType=form.find("select[name=ownerType]").val();
    var businessHours=form.find("input[name=businessHours]").val();
    var parkPicUrl1=form.find("input[name=parkPicUrl1]").val();
    var electricPrice=form.find("input[name=electricPrice]").val();
    var parkRent=form.find("input[name=parkRent]").val();
    var payRentDayOfMonth=form.find("input[name=payRentDayOfMonth]").val();
    if (parkName == "") {
		$("#parkNameAdd").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
		return false;
    }
    if (addrRegion1Id == "-1") {
		$("#addrRegion1IdAdd").append("<font color='red' class='formtips onError emaill'>请选择地址！</font>");
		return false;
    }
    if (addrStreet == "") {
		$("#addrStreetAdd").append("<font color='red' class='formtips onError emaill'>请输入详细地址！</font>");
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
    
    if (cityId == "") {
		$("#cityIdAdd").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
		return false;
    }
    
//    var type=form.find("input[name='ownerType']:checked").val();
//    if(type==1){
//    	if (contactTel == "") {
//			$("#contactTelEdit").append("<font color='red' class='formtips onError emaill'>请输入联系电话！</font>");
//			return false;
//        }	
//    }
    var isPloygon = form.find("input[name=isPloygon]").val();
    if(isPloygon=="0"){
    	if (electronicFenceRadius == "") {
    		$("#electronicFenceRadiusAdd").append("<font color='red' class='formtips onError emaill'>请输入电子围栏半径！</font>");
    		return false;
    	}else if(!/^[0-9]*[1-9][0-9]*$/.test(electronicFenceRadius)){
    		$("#electronicFenceRadiusAdd").append("<font color='red' class='formtips onError emaill'>电子围栏半径只能输入正整数,不能有空格！</font>");
    		return false;
    	}
    }
    if (parkType == "") {
		$("#parkTypeAdd").append("<font color='red' class='formtips onError emaill'>请选择类别！</font>");
		return false;
    }
    if (isPublic == "") {
		$("#isPublicAdd").append("<font color='red' class='formtips onError emaill'>请选择是否开放！</font>");
		return false;
    }
/*		    if (styleId == "") {
		$("#styleIdAdd").append("<font color='red' class='formtips onError emaill'>请选择样式！</font>");
		return false;
    } */
   /* if (parkingSpaceNumber == "") {
		$("#parkingSpaceNumberAdd").append("<font color='red' class='formtips onError emaill'>请输入车位数！</font>");
		return false;
    }else*/ 
    if(parkingSpaceNumber != "" && !/^[0-9]*[1-9][0-9]*$/.test(parkingSpaceNumber)){
    	$("#parkingSpaceNumberAdd").append("<font color='red' class='formtips onError emaill'>车位数只能输入正整数,不能有空格！</font>");
		return false;
    }
    
//    if (carNumber == "") {
//		$("#carNumberAdd").append("<font color='red' class='formtips onError emaill'>请输入车辆数！</font>");
//		return false;
//    }else if(!/^[0-9]*[1-9][0-9]*$/.test(carNumber)){
//    	$("#carNumberAdd").append("<font color='red' class='formtips onError emaill'>车辆数只能输入正整数,不能有空格！</font>");
//		return false;
//    }
//    if (chargerNumber == "") {
//		$("#chargerNumberAdd").append("<font color='red' class='formtips onError emaill'>请输入电桩数！</font>");
//		return false;
//    }else if(!/^[0-9]*[1-9][0-9]*$/.test(chargerNumber)){
//    	$("#chargerNumberAdd").append("<font color='red' class='formtips onError emaill'>电桩数只能输入正整数,不能有空格！</font>");
//		return false;
//    }
/*		    if (supportedServices=="") {
		$("span[name='supportedServices']").append("<font color='red'>请选择服务！</font>");
		return false;
	}
	if (ownerType == "") {
		$("#ownerTypeAdd").append("<font color='red' class='formtips onError emaill'>请选择所属！</font>");
		return false;
    }*/
	
/*	    	if (electricPrice == "") {
		$("#electricPriceAdd").append("<font color='red' class='formtips onError emaill'>请输入场站电费！</font>");
		return false;
    } */
	if(electricPrice != ""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(electricPrice)){
    	$("#electricPriceAdd").append("<font color='red' class='formtips onError emaill'>格式不正确，不能有空格，场站电费只能输入整数或小数！</font>");
		return false;
    }
/*	    	if (parkRent == "") {
		$("#parkRentAdd").append("<font color='red' class='formtips onError emaill'>请输入场站租金！</font>");
		return false;
    }else */
    if(parkRent != "" && !/^[0-9]*[1-9][0-9]*$/.test(parkRent)){
    	$("#parkRentAdd").append("<font color='red' class='formtips onError emaill'>场站租金只能输入正整数或是输入格式不对！</font>");
		return false;
    }
/*	    	if (payRentDayOfMonth == "") {
		$("#payRentDayOfMonthAdd").append("<font color='red' class='formtips onError emaill'>请输入交租日期！</font>");
		return false;
    }
    if (payRentDayOfMonth != "") {
    	 if(!/^[0-9]*[1-9][0-9]*$/.test(payRentDayOfMonth)){
         	$("#payRentDayOfMonthAdd").append("<font color='red' class='formtips onError emaill'>交租日期只能输入正整数或是输入格式不对！</font>");
				return false;
         }else if(31<payRentDayOfMonth.replace(/\s+/g,"")){
         	$("#payRentDayOfMonthAdd").append("<font color='red' class='formtips onError emaill'>交租日期天数不能大于31，正确格式比如（1~31）！</font>");
				return false;
         }else if(payRentDayOfMonth.replace(/\s+/g,"")<=0){
         	$("#payRentDayOfMonthAdd").append("<font color='red' class='formtips onError emaill'>交租日期天数不能为0，正确格式比如（1~31）！</font>");
				return false;
         }
    }*/
	if(businessHours == ""){
		$("#businessHoursAdd").append("<font color='red' class='formtips onError emaill'>请输入营业时间！</font>");
		return false;
	}
	
	/*if (form.find("input[name='serviceFeeGet']").val()=="") {
		$("#serviceFeeGetAdd").append("<font color='red' class='formtips onError emaill'>请输入取车附加费！</font>");
		return false;
	}*/
	
	if (form.find("input[name='serviceFeeGet']").val()!=""&&!/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/.test(form.find("input[name='serviceFeeGet']").val())) {
		$("#serviceFeeGetAdd").append("<font color='red' class='formtips onError emaill'>输入取车附加费有误！(正数或小数2位)</font>");
		return false;
	}
	/*if (form.find("input[name='serviceFeeBack']").val()=="") {
		$("#serviceFeeBackAdd").append("<font color='red' class='formtips onError emaill'>请输入还车附加费！</font>");
		return false;
	}*/
	
	if (form.find("input[name='serviceFeeBack']").val()!=""&&!/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/.test(form.find("input[name='serviceFeeBack']").val())) {
		$("#serviceFeeBackAdd").append("<font color='red' class='formtips onError emaill'>输入还车附加费有误！(正数或小数2位)</font>");
		return false;
	}
	
	/*if (parkPicUrl1 == "") {
		$("#parkPicUrl1Add").append("<font color='red' class='formtips onError emaill'>请上传图片！</font>");
		return false;
    }*/
	
	 if(parkName !=""){
		 $.ajax({
    			url:parkAdd.appPath+"/park/onlyParkName.do",//验证场站名称唯一性
    			type:"post",
    			data:{parkName:parkName},
    			dataType:"json",
    			success:function(res){ 
    					if(res.code == "1"){ 
    						$("#parkNameAdd").empty();
    						$("#parkNameAdd").append("<font color='red' class='formtips onError emaill'>场站名称重复！</font>");
    						return false;
    					}else{
    						var form = $("form[name=parkAddForm]");
    						var franchiseeId = form.find("input[name='franchiseeId']").val();
    						form.ajaxSubmit({
    							url : parkAdd.appPath + "/park/updatePark.do",
    							type : "post",
    							data :{"franchiseeNo": franchiseeId},
    							success : function(res) {
    								var msg = res.msg;
    								var code = res.code;
    								if (code == "1") {
    									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
    										closeTab("新增场站");
    										$("#parkList").DataTable().ajax.reload(function(){});
    									});
    								} else {
    									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" +msg);
    								}
    							},
    							//beforeSubmit : function(formData, jqForm, options) {}
    						});
    					}
    			}
    		}); 
	 }else{
		 $("#parkNameAdd").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
		return false;
	 }

	},
		
	      uploadFile:function(){
	    	  
	        	var relativePath="park_photo";
	        	var resImgPath=parkAdd.resImgPath;
	        	var imgPath=parkAdd.imgPath;
	        	var storePath=resImgPath+"/" + relativePath;
	        	//relativePath="";测试用
	        	//storePath="";
	        	$("#appUpload").load("res/tpl/uploadFile.html",function(){
	                var manualUploader = new qq.FineUploader({
	                    element: document.getElementById("parkFineUploader"),
	                    template: "qq-template-manual-trigger-m",
	                    request: {
	                        endpoint: parkAdd.appPath+"/upload/uploadFileNew.do",
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
	                        	var li=$("#parkFineUploader .qq-uploader .qq-upload-list").find("li");
	                        	if(li != null && li.length < 6){
            						li=$("#parkFineUploader .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
    	                        	parkAdd.itemPicsBindEvent(li);
    	                        	li.find(".qq-upload-remove").hide();
            					}else{
    	                        	$("#parkFineUploader .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]")[0].remove();
            						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "上传图片不能超过5张！");
            					}
	                        },
	                        onComplete: function(id, fileName, responseJSON) {
	                        	var form = $("form[name='parkphotoForm']");
	                        	
	                        	/*for (var i = 1; i < 6; i++){
	                        		var input = form.find("input[name='parkPicUrl"+ i +"']");
	                        		if(input != null){
	                        			if(input.val() == null || input.val() == ""){
		                        			input.val(responseJSON.data[0]); 
		                        			break;
			                        	}
	                        			if(i == 5){
	                    					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "上传图片不能超过5张！");
	                        			}
	                        		}
	                        	}*/
	                        	var form1=$("form[name=parkAddForm]");
	                        	
	                        	if(form.find("input[name='parkPicUrl1']").val() != ""){
	                        		var path = form.find("input[name='parkPicUrl1']").val() + "," + responseJSON.data[0]
		                        	form.find("input[name='parkPicUrl1']").val(path);   
	            					form1.find("input[name=parkPicUrl1]").val(path);
	                        	}else{
	                        		form.find("input[name='parkPicUrl1']").val(responseJSON.data[0]);            
	            					form1.find("input[name=parkPicUrl1]").val(responseJSON.data[0]);
	                        	}
	                        	
	                        	var li=$("#parkFineUploader .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
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
	                $("#parkFineUploader .trigger-upload").on("click", function() {
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
					resPath=parkAdd.resImgPath
				}
	        	$.ajax({
	        		url: parkAdd.appPath+"/upload/deleteFile.do?filePaths="+filePaths,
	        		data:{resPath:resPath},
	        		success: function(data){
	        			if(data.code=1){
	        				var form = $("form[name='parkphotoForm']");
                        	var input = form.find("input[name='parkPicUrl1']");
	        				if(input != ""){
                        		var urls = input.val().split(",");
                        		var newUrls = [];
                        		if(urls != null){
                        			for(var i = 0 ; i < urls.length; i++){
                            			if(urls[i] != filePaths){
                            				newUrls[newUrls.length] = urls[i];
                            			}
                            		}
                        			input.val(newUrls);            
                        			var form1=$("form[name=parkAddForm]");
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
	        	$("#parkFineUploader .qq-upload-success").each(function(){
	        		filePath.push($(this).data("filepath"));                		
          	});
	        	return filePath;
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
      			parkAdd.deleteFile(filePath,function(){
      				obj.remove();
      			});
          	});
	        }	
}
return parkAdd;
})
//获得县、区
function getResultCountryAddPark(a){
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#countrycityAdd").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='col-sm-4' name='addrRegion3Id' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#countrycityAdd").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}
//获得市
function getResultCityAddPark(d){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityAdd").html("");
                         var select="<select name='addrRegion2Id'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryAddPark(this.value)'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			getResultCountryAddPark(pId);
              			$("#itemcityAdd").append(select);
					} 
	 },"json");
}
