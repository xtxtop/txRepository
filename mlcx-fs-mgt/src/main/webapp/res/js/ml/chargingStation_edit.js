define(
[],
function() {
var chargingStationEdit = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		 var forms=$("form[name=chargingStationEditForm]");
		 var checkbox_supportedServices=forms.find("input[name='supportedServices']")
		 var checkbox_label=forms.find("input[name='label']")
		 var label_all=forms.find("input[name='label_all']").val().split(",");
		 var supportedServices_all=forms.find("input[name='supportedServices_all']").val().split(",");
		 for(var i=0;i<checkbox_supportedServices.length;i++){
		 	if($.inArray(checkbox_supportedServices[i].value, supportedServices_all)!=-1){
		 		checkbox_supportedServices[i].checked =true;
		 	}
		 }
		 for(var i=0;i<checkbox_label.length;i++){
			 if($.inArray(checkbox_label[i].value,label_all)!=-1){
				 checkbox_label[i].checked =true;
			 }
		 }
		 
		//添加提交
		$("#EditchargingStation").click(function(){
			chargingStationEdit.EditchargingStation();
		});
		//返回
		$("#EditclosechargingStation").click(function(){
			closeTab("编辑充电站");
			$("#chargingStationList").DataTable().ajax.reload(function(){});
		});
		//选择营业时间
		$("#clickBusiness_hours").click(function(){
			form.find("input[name=businessHours]").click();
		});
        //充电站上传图片
        $("#EditchargingStationPhotoButton").click(function(){
            $("#chargingStationPhotoEditModal").modal("show");
        });
        //充电站图片的回填
        $("#EditchargingStationPhotoBtn").click(function(){
            var form=$("form[name=chargingStationphotoFormEdit]");
            var img=form.find("input[name=chargingStationPicUrlEdit]").val();
            if(img!=""){
                var form1=$("form[name=chargingStationEditForm]");
                form1.find("input[name=stationUrl]").val(img);
                form1.find("#chargingStationPicUrlImg").css("background-image","url("+chargingStationEdit.imgPath+'/'+img+")");
                $("#chargingStationPhotoEditModal").modal("hide");
            }else{
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
            }

        });
        /*地图定位*/
        $("#chargingSearchGC_edit").click(function(){
            var cityId = $("#cityId_edit").val();
            if(cityId == ""||typeof(cityId) == 'undefined'){
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择城市！", function() {
                });
            } else {
                $("#storeEditMapModel").modal("show");
                chargingStationEdit.mapInit();
            }
        });
	},
	EditchargingStation:function() {
		var form = $("form[name=chargingStationEditForm]");
		form.ajaxSubmit({
			url : chargingStationEdit.appPath + "/chargingStation/editChargingStation.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑充电站");
						$("#chargingStationList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=chargingStationEditForm]");
				$("span[id$='edit_station_mlcx_verify']").html("");//清空所有span提示
				form.find("input[name='stationName']").val();//名称
				form.find("select[name='provinceId']").val();//省
				form.find("input[name='longitude']").val();//经纬度
				form.find("input[name='addrStreet']").val();//详细地址
				form.find("input[name='electricPrice']").val();//电费
				form.find("input[name='stationUrl']").val();//图片
				var rent=/^[0-9]*(\.[0-9]{0,5})?$/;//验证电费
				var rents=/^[0-9]{0,5}?$/;//验证数字
				
				if(form.find("input[name='stationName']").val()==''){
					spanWarning("stationName_edit_station","请输入充电站名称!")
				}
				if(form.find("select[name='provinceId']").val()==''){
					spanWarning("provinceId_edit_station","请选择所在地区!")
				}
				if(form.find("input[name='longitude']").val()==''||form.find("input[name='latitude']").val()==''){
					spanWarning("longitude_edit_station","请输入经纬度!")
				}
				if(form.find("input[name='electricPrice']").val()==''){
					spanWarning("electricPrice_edit_station","请输入电费!")
				}else if(!rent.test(form.find("input[name='electricPrice']").val())){
					spanWarning("electricPrice_edit_station","请输入正确的电费金额!")
				}
				if(form.find("input[name='addrStreet']").val()==''){
					spanWarning("addrStreet_edit_station","请输入详细地址!")
				}
				if(form.find("input[name='stationUrl']").val()==''){
					spanWarning("stationUrl_edit_station","请上传图片!")
				}
				if(form.find("select[name='stationType']").val()==''){
					spanWarning("stationType_edit_station","请选择类型!")
				}
				//复选框是否选择
				var checkbox=form.find("input[name='supportedServices']")
				var checkboxCount=0
				for(var i=0;i<checkbox.length;i++){
					//alert(checkbox[i].value)
					if(checkbox[i].checked){
						checkboxCount++;
					}
				}
				if(checkboxCount==0){
					spanWarning("supportedServices_edit_station","请选择充电站周边服务!")
				}
				var checkbox_label=form.find("input[name='label']")
				var checkbox_labelCount=0
				for(var i=0;i<checkbox_label.length;i++){
					if(checkbox_label[i].checked){
						checkbox_labelCount++;
					}
				}
				if(checkbox_labelCount==0){
					spanWarning("label_edit_station","请选择充电站标签!")
				}
				var edit_station_mlcx_verify=$("span[id$='edit_station_mlcx_verify']")
				for(var i=0;i<edit_station_mlcx_verify.length;i++){
					if(edit_station_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
 			}
		});
	 },
	 mapInit:function() {
	        var map = new BMap.Map("searchStoreMapEdit",{enableMapClick: false}); // 创建地图实例
	        var form = $("form[name=chargingStationEditForm]");
	        var cityId = $("#cityId_edit").val();
	        var provinceName = $("#provinceIdEdit").find("option:selected").text();
	        var cityName = $("#cityId_edit").find("option:selected").text();
	        var region = $("#districtIdEdit").val();
	           cityName =  cityName;
	        if(region!='') {
	            cityName +=$("#districtIdEdit").find("option:selected").text();
	        }

	                if(cityId != null && cityId!= ''){

	                    map.centerAndZoom(cityName,11);// 用城市名设置地图中心点
	                    map.enableScrollWheelZoom();
	                    map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
	                    var marker;
	                    //进行浏览器定位
	                    var geolocation = new BMap.Geolocation();
	                    geolocation.getCurrentPosition(function(r){
	                        // 定位成功事件
	                        if(this.getStatus() == BMAP_STATUS_SUCCESS){
	                            var point = new BMap.Point(r.point.lng,r.point.lat);
	                            marker = new BMap.Marker(point);  // 创建标注
	                            map.addOverlay(marker);
	                        }
	                    },{enableHighAccuracy: true})

	                    map.addEventListener("click",function(e){
	                        map.removeOverlay(marker);
	                        var point = new BMap.Point(e.point.lng,e.point.lat);
	                        form.find("input[name = 'longitude']").val(e.point.lng);
	                        form.find("input[name = 'latitude']").val(e.point.lat);
	                        form.find("input[name='lngLat']").val(e.point.lng+','+e.point.lat);
	                        map.centerAndZoom(point);
	                        marker = new BMap.Marker(point);  // 创建新的标注
	                        map.addOverlay(marker);    //将标注添加到地图上
	                        var gc = new BMap.Geocoder();
	                        gc.getLocation(point, function(rs){
	                            var addComp = rs.addressComponents;
	                            form.find("input[name='addrStreet']").val(addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber)
	                            $("#Editress").html(addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber);
	                            $("#coordinate_edit").html(e.point.lng+'  '+e.point.lat);
	                            
	                        });
	                    });

	                } else{
	                    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无城市信息！", function() {
	                    });
	                }
				}
			}
	return chargingStationEdit;
})
//获得县、区
function getResultCountryEditchargingStation(a){
	 var form = $("form[name=chargingStationEditForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#countrycityEdit").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='col-sm-4' name='districtId' id='districtIdEdit' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#countrycityEdit").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}
//获得市
function getResultCityEditchargingStation(d){
	 var form = $("form[name=chargingStationEditForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityEdit").html("");
                         var select="<select name='cityId' id='cityId_edit'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryEditchargingStation(this.value)'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			getResultCountryEditchargingStation(pId);
              			$("#itemcityEdit").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityEdit").html("");
		$("#itemcityEdit").html("");
	}
}

//复选框验证
function checkbox(x){
	if(x.checked){
		$(x).attr('checked',true);
	}else{
		$(x).attr('checked',false);
		
	}
}

//根据运营城市获取所在地区
function operatingCityNoChangeEdit(){
	var province=$("#operatingCityNo_edit_station option:selected").text();
	var operatingCityNo=province.split('-');
	$.post('sysRegion/getProvince.do', {provinceName:operatingCityNo[0].trim()}, 
			function(data) {
				if(data){
					var select="<select name='provinceId' id='provinceIdEdit' onclick='formVerify('provinceId_edit_station')'   class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCityEditchargingStation(this.value)'>";
					var p;
					for(var i=0;i<data.length;i++){
						if(data[i].text==operatingCityNo[0].trim()){
							select+="<option  value='"+data[i].regionId+"' selected> "+data[i].regionName+" </option>";
						}else{
							select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
						}
						p=data[i].regionId;
					}
					select+="</select>";
					$("#provinceIdEdits").html("")
					$("#provinceIdEdits").append(select);
					getResultCityEditchargingStation_operatingCity(p,operatingCityNo[1].trim());
				}
	},"json");
}
//根据运营城市 获得市
function getResultCityEditchargingStation_operatingCity(d,s){
	 var form = $("form[name=chargingStationEditForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityEdit").html("");
                         var select="<select name='cityId' id='cityId_edit'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryEditchargingStation(this.value)'>";
                        var x;
                         for(var i=0;i<data.length;i++){
                        	if(data[i].regionName==s){
                        		select+="<option  value='"+data[i].regionId+"' selected> "+data[i].regionName+" </option>";
                        		x=data[i].regionId;
                        	}else{
                        		select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                        	}
              			}
              			select+="</select>";
              			getResultCountryEditchargingStation(x);
              			$("#itemcityEdit").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityEdit").html("");
		$("#itemcityEdit").html("");
	}
}
