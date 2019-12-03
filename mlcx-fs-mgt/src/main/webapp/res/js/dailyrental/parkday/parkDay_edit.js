define([],function() {	
var parkDayEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		globalMap:null,
		globalMarker:undefined,
		init : function() {
			
			//上传图片
			$("#editParkPhotoButton").click(function(){
				$("#parkDayPhotoEditModal").modal("show");
			});
			//新增图片回填
			$("#editParkDayPhotoBtn").click(function(){
				$("#parkDayPhotoEditModal").modal("hide");

				var form=$("form[name=parkDayPhotoEditForm]");
				var img=form.find("input[name=parkDayPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=parkDayEditForm]");
					form1.find("input[name=parkDayPhoto]").val(img);
					form1.find("#parkPicUrlImg").css("background-image", "url(" + parkDayEdit.imgPath + '/' + img + ")");
					$("#parkDayPhotoEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
			//编辑提交
			$("#saveParkDayEdit").click(function(){
				parkDayEdit.saveParkDay();
			});
			//编辑页面的关闭
			$("#closeParkDayEdit").click(function(){
				closeTab("编辑门店");
			});
			//初始化地图
			parkDayEdit.creatMaker();
			parkDayEdit.clickAndMarker();
			//搜索
//			$("#searchParkDayEditBtn").click(function(){
//				var searchAddr = $("#searchAddressEdit").val();
//				parkDayEdit.searchMapByAddr(searchAddr);
//			});
			$("#searchParkDayEditBtn").click(function(){
				debugger
				var searchAddr = $("#searchAddressEdit").val();
				if(!!searchAddr){
					parkDayEdit.searchMapByAddr(searchAddr);
					$("#loadMapContentEdit").modal("show");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入查询地点！");
				}
				
			});
			//触发地图model
//			$("#searchAddressEdit").focus(function(){
//				var searchAddr = $("#searchAddressEdit").val();
//				if(searchAddr!==""){
//					$("#loadMapContentEdit").modal("show");
//				}
//				
//			})
		},
		creatMaker:function() {
			var map = new BMap.Map("parkDayEditMap"); // 创建地图实例
			globalMap = map;
			map.enableScrollWheelZoom(false);
			map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
			
			// 添加控件到地图当中  
			var myZoomCtrl = new ZoomControl();  
			map.addControl(myZoomCtrl);
			
			var form = $("form[name=parkDayEditForm]");
			var longitude = form.find("input[name=longitude]").val();
			var latitude = form.find("input[name=latitude]").val();
			var point = new BMap.Point(longitude,latitude);
			map.centerAndZoom(point,18);
			var marker = new BMap.Marker(point); // 创建标注
			map.addOverlay(marker);
			globalMarker = marker;
			var fullAddr = form.find("input[name=fullAddr]").val();;
			var label = new BMap.Label(fullAddr,{offset:new BMap.Size(-30,-20)});
	    	label.setStyle({
	    		color : "red",
	    		fontSize : "13px",
	    		height : "20px",
	    		lineHeight : "20px",
	    		fontFamily:"微软雅黑",
	    		backgroundColor :"0.05",
	    		border :"0"
	    	});
	    	marker.setLabel(label);
		},
		clickAndMarker:function(){
			var map = globalMap;
			map.addEventListener("click", showInfo);
			function showInfo(e){
				if(typeof(globalMarker)!="undefined"){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请先删除标记！");
					return false;
				}
				var point = new BMap.Point(e.point.lng, e.point.lat); 
				globalMap.centerAndZoom(point, 18);
				var marker = new BMap.Marker(point); // 创建标注
				map.addOverlay(marker);
				globalMarker = marker;
				 //根据所选坐标查询地址
			    // 创建地理编码服务实例 
			    var myGeo = new BMap.Geocoder(); 
			    // 根据坐标得到地址描述 
			    myGeo.getLocation(point, function(result){ 
				    if (result){ 
				    	var addComp = result.addressComponents;
				    	var form = $("form[name=parkDayEditForm]");
				    	var fullAddr = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
					    form.find("input[name=fullAddr]").val(fullAddr);
					    form.find("input[name=longitude]").val(e.point.lng);
					    form.find("input[name=latitude]").val(e.point.lat);
				    	var detailAddress = addComp.province +  addComp.city +  addComp.district + addComp.street + addComp.streetNumber
				    	$("#searchAddressEdit").val(detailAddress);
				    	var label = new BMap.Label(detailAddress,{offset:new BMap.Size(-30,-20)});
				    	label.setStyle({
				    		color : "red",
				    		fontSize : "13px",
				    		height : "20px",
				    		lineHeight : "20px",
				    		fontFamily:"微软雅黑",
				    		backgroundColor :"0.05",
				    		border :"0"
				    	});
				    	marker.setLabel(label);
				    } 
			    })
			    $("#loadMapContentEdit").modal("hide");
			}
		},
		searchMapByAddr:function(addressDetail){
			globalMap.centerAndZoom(addressDetail, 18);
			var localSearch = new BMap.LocalSearch(globalMap);
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var poi = searchResult.getPoi(0);
				var addressDetail = poi.address;
				var district = addressDetail.substring(addressDetail.indexOf("市")+1,addressDetail.indexOf("区")+1);
				var addrStreet = addressDetail.substring(addressDetail.indexOf("区")+1);
				var fullAddr = poi.province +","+ poi.city +","+district+","+addrStreet;
				var form = $("form[name=parkDayEditForm]");
			    form.find("input[name=fullAddr]").val(fullAddr);
				form.find("input[name=longitude]").val(poi.point.lng);
				form.find("input[name=latitude]").val(poi.point.lat);
				if(typeof(globalMarker)!="undefined"){
					globalMap.removeOverlay(globalMarker);
				}
				var point = new BMap.Point(poi.point.lng, poi.point.lat); 
				globalMap.centerAndZoom(point, 18);
				var marker = new BMap.Marker(point); // 创建标注
				globalMap.addOverlay(marker);
				globalMarker = marker;
			});
			localSearch.search(addressDetail);
		},
		saveParkDay:function() {
			// 提交表单前数据验证
			var form = $("form[name=parkDayEditForm]");
			form.find("span[name=merchantIdEdit]").html("");
			form.find("span[name=parkNameEdit]").html("");
			form.find("span[name=cantactPersonEdit]").html("");
			form.find("span[name=mobilePhoneEdit]").html("");
			form.find("span[name=parkDayPhotoEdit]").html("");
			$("#pardDayFullAddrEdit").html("");
			
		   	var merchantId = $.trim(form.find("select[name=merchantId]").val());
		   	var parkId = $.trim(form.find("input[name=parkId]").val());
		    var parkName = $.trim(form.find("input[name=parkName]").val());
		    var cantactPerson = $.trim(form.find("input[name=cantactPerson]").val());
		    var mobilePhone = $.trim(form.find("input[name=mobilePhone]").val());
		    //var businessHours=form.find("input[name=businessHours]").val();
		    var parkDayPhoto = $.trim(form.find("input[name=parkDayPhoto]").val());
		    var fullAddr = $.trim(form.find("input[name=fullAddr]").val());
		    var longitude = $.trim(form.find("input[name=longitude]").val());
		    
		    if (merchantId == "") {
		    	form.find("span[name=merchantIdEdit]").append("<font color='red' class='formtips onError emaill'>请选择商家！</font>");
				return false;
		    }
		    if (parkName == "") {
		    	form.find("span[name=parkNameEdit]").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
				return false;
		    }
		    if (cantactPerson == "") {
		    	form.find("span[name=cantactPersonEdit]").append("<font color='red' class='formtips onError emaill'>请填写联系人名称！</font>");
				return false;
		    }
		    if (mobilePhone == "") {
		    	form.find("span[name=mobilePhoneEdit]").append("<font color='red' class='formtips onError emaill'>请填写联系人电话！</font>");
				return false;
		    }
		    var phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;  
		    if (!phoneReg.test(mobilePhone)) {  
		    	form.find("span[name=mobilePhoneEdit]").append("<font color='red' class='formtips onError emaill'>联系人电话格式不正确！</font>");
		    	return false;
		    } 
//			if(businessHours == ""){
//				$("#businessHoursEdit").append("<font color='red' class='formtips onError emaill'>请输入营业时间！</font>");
//				return false;
//			}
			if(parkDayPhoto == ""){
				form.find("span[name=parkDayPhotoEdit]").append("<font color='red' class='formtips onError emaill'>请上传门店照片！</font>");
				return false;
			}
			if(fullAddr == "" || longitude==""){
				$("#pardDayFullAddrEdit").append("<font color='red' class='formtips onError emaill'>请在地图上标记门店位置！</font>");
				return false;
			}
			if(fullAddr.indexOf("undefined")>-1){
				$("#pardDayFullAddrEdit").append("<font color='red' class='formtips onError emaill'>地址无效，请重新标记门店位置！</font>");
				return false;
			}
			if(parkName !=""){
				 $.ajax({
	    			url:parkDayEdit.appPath+"/parkDay/checkParkName.do",//验证场站名称唯一性
	    			type:"post",
	    			data:{parkName:parkName,parkId:parkId},
	    			dataType:"json",
	    			success:function(res){ 
						if(res.code == "1"){
							var form1 = $("form[name=parkDayEditForm]");
							form1.find("span[name=parkNameEdit]").empty();
							form1.find("span[name=parkNameEdit]").append("<font color='red' class='formtips onError emaill'>门店名称重复！</font>");
							return false;
						}else{
							var form = $("form[name=parkDayEditForm]");
							form.ajaxSubmit({
								url : parkDayEdit.appPath + "/parkDay/updatePark.do",
								type : "post",
								success : function(res) {
									var msg = res.msg;
									var code = res.code;
									if (code == "1") {
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
											globalMarker = undefined;
											closeTab("编辑门店");
											$("#parkDayList").DataTable().ajax.reload(function(){});
										});
									} else {
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
									}
								},
							});
						}
	    			}
	    		}); 
			 }else{
				 form.find("span[name=parkNameEdit]").append("<font color='red' class='formtips onError emaill'>请输入名称！</font>");
				return false;
			 }
		}
	}
	//定义一个控件类，即function
	function ZoomControl() {
		// 设置默认停靠位置和偏移量
		this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
		this.defaultOffset = new BMap.Size(10, 10);
	}
	// 通过JavaScript的prototype属性继承于BMap.Control
	ZoomControl.prototype = new BMap.Control();
	ZoomControl.prototype.initialize = function(map) {
		// 创建一个DOM元素
		var button = document.createElement("button");
		// 添加文字说明
		button.appendChild(document.createTextNode("删除标记"));
		// 设置样式
		button.style.cursor = "pointer";
		button.style.border = "1px solid gray";
		button.style.backgroundColor = "white";
		// 绑定事件
		button.onclick = function(e) {
			fillObject.removeMarker();//移除标记
			$("#searchAddressEdit").val("");//清除搜索框
			var form = $("form[name=parkDayEditForm]");
		    form.find("input[name=fullAddr]").val("");
			form.find("input[name=longitude]").val("");
			form.find("input[name=latitude]").val("");
		};
		// 添加DOM元素到地图中
		map.getContainer().appendChild(button);
		// 将DOM元素返回
		return button;
	}
	var fillObject = {
		removeMarker : function() {
			if(globalMarker!=null){
				var map = globalMap;
				map.removeOverlay(globalMarker);
				globalMarker = undefined;
			}
		}
	}
return parkDayEdit;
})
