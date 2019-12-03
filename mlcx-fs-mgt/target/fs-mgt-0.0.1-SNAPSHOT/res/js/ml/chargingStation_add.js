define(
[],
function() {
var chargingStationAdd = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
        var form = $("form[name=chargingStationAddForm]");
        form.find("select[name=districtId]").html("");
        form.find("select[name=cityId]").html("");
        form.find("select[name=districtId]").hide();
        form.find("select[name=cityId]").hide();
        
		//添加提交
		$("#addchargingStation").click(function(){
			chargingStationAdd.addchargingStation();
		});
		//返回
		$("#addclosechargingStation").click(function(){
			closeTab("充电站增加");
			$("#chargingStationList").DataTable().ajax.reload(function(){});
		});
		//选择营业时间
		$("#clickBusiness_hours_add").click(function(){
			form.find("input[name=businessHours]").click();
		});
        //充电站上传图片
        $("#addchargingStationPhotoButton").click(function(){
            $("#chargingStationPhotoAddModal").modal("show");
        });
        //充电站图片的回填
        $("#addchargingStationPhotoBtn").click(function(){
            var form=$("form[name=chargingStationphotoForm]");
            var img=form.find("input[name=chargingStationPicUrl]").val();
            if(img!=""){
                var form1=$("form[name=chargingStationAddForm]");
                form1.find("input[name=stationUrl]").val(img);
                form1.find("#chargingStationPicUrlImg").css("background-image","url("+chargingStationAdd.imgPath+'/'+img+")");
                $("#chargingStationPhotoAddModal").modal("hide");
            }else{
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
            }

        });
        /*地图定位*/
        $("#chargingSearchGC").click(function(){
            var cityId = $("#cityId").val();
            if(cityId == ""||typeof(cityId) == 'undefined'||cityId == null){
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择城市！", function() {
                });
            } else {
                $("#storeAddMapModel").modal("show");
                chargingStationAdd.mapInit();
            }
        });
	},
	addchargingStation:function() {
		var form = $("form[name=chargingStationAddForm]");
		form.ajaxSubmit({
			url : chargingStationAdd.appPath + "/chargingStation/addchargingStation.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("充电站增加");
						$("#chargingStationList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=chargingStationAddForm]");
				$("span[id$='add_station_mlcx_verify']").html("");//清空所有span提示
				form.find("input[name='stationName']").val();//名称
				form.find("select[name='operatingCityNo']").val();//运营城市
				form.find("select[name='provinceId']").val();//省
				form.find("input[name='longitude']").val();//经纬度
				form.find("input[name='addrStreet']").val();//详细地址
				form.find("input[name='electricPrice']").val();//电费
				form.find("input[name='stationUrl']").val();//图片
				var rent=/^[0-9]*(\.[0-9]{0,5})?$/;//验证电费
				var rents=/^[0-9]{0,5}?$/;//验证数字
				
				if(form.find("input[name='stationName']").val()==''){
					spanWarning("stationName_add_station","请输入充电站名称!")
				}
				if(form.find("select[name='operatingCityNo']").val()==''){
					spanWarning("operatingCityNo_add_station","请选择运营城市!")
				}
				if(form.find("select[name='provinceId']").val()==''){
					spanWarning("provinceId_add_station","请选择所在地区!")
				}
				if(form.find("input[name='longitude']").val()==''||form.find("input[name='latitude']").val()==''){
					spanWarning("longitude_add_station","请输入经纬度!")
				}
				if(form.find("input[name='electricPrice']").val()==''){
					spanWarning("electricPrice_add_station","请输入电费!")
				}else if(!rent.test(form.find("input[name='electricPrice']").val())){
					spanWarning("electricPrice_add_station","请输入正确的电费金额!")
				}
				if(form.find("input[name='addrStreet']").val()==''){
					spanWarning("addrStreet_add_station","请输入详细地址!")
				}
				if(form.find("input[name='stationUrl']").val()==''){
					spanWarning("stationUrl_add_station","请上传图片!")
				}
				if(form.find("select[name='stationType']").val()==''){
					spanWarning("stationType_add_station","请选择类型!")
				}
				//复选框是否选择
				var checkbox=form.find("input[name='supportedServices']")
				var checkboxCount_add=0
				for(var i=0;i<checkbox.length;i++){
					if(checkbox[i].checked){
						checkboxCount_add++;
					}
				}
				if(checkboxCount_add==0){
					spanWarning("supportedServices_add_station","请选择充电站周边服务!")
				}
				var checkbox_label=form.find("input[name='label']")
				var checkbox_labelCount_add=0
				for(var i=0;i<checkbox_label.length;i++){
					if(checkbox_label[i].checked){
						checkbox_labelCount_add++;
					}
				}
				if(checkbox_labelCount_add==0){
					spanWarning("label_add_station","请选择充电站标签!")
				}
				//判断提交验证
				var add_station_mlcx_verify=$("span[id$='add_station_mlcx_verify']")
				for(var i=0;i<add_station_mlcx_verify.length;i++){
					if(add_station_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
				/*$("span[id$='_mlcx_verify']").each(function(j,item){
				      // 你要实现的业务逻辑
				    alert(item.innerHTML!=''); 
				     if(item.innerHTML!=''){
				    	 return false;
				     }else{
				    	 return true;
				     }
				})*/
			}
		});
	 },
	 mapInit:function() {
	        var map = new BMap.Map("searchStoreMap",{enableMapClick: false}); // 创建地图实例
	        var form = $("form[name=chargingStationAddForm]");
	        var cityId = $("#cityId").val();
	        var provinceName = $("#provinceId").find("option:selected").text();
	        var cityName = $("#cityId").find("option:selected").text();
	        var region = $("#districtId").val();
	           cityName =  cityName;
	        if(region!='') {
	            cityName +=$("#districtId").find("option:selected").text();
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
	                            var point = new BMap.Point(r.point.lng, +r.point.lat);
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
	                            $("#address").html(addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber);
	                            $("#coordinate").html(e.point.lng+'  '+e.point.lat);
	                            
	                        });
	                    });

	                } else{
	                    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无城市信息！", function() {
	                    });
	                }
				}
			}
	return chargingStationAdd;
})
//获得县、区
function getResultCountryAddchargingStation(a){
	 var form = $("form[name=chargingStationAddForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#countrycityAdd").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='col-sm-4' name='districtId' id='districtId' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;'>";
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
function getResultCityAddchargingStation(d){
	 var form = $("form[name=chargingStationAddForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityAdd").html("");
                         var select="<select name='cityId' id='cityId'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryAddchargingStation(this.value)'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			getResultCountryAddchargingStation(pId);
              			$("#itemcityAdd").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityAdd").html("");
		$("#itemcityAdd").html("");
	}
}
//根据运营城市获取所在地区
function operatingCityNoChangeAdd(){
	var province=$("#operatingCityNo_add_station option:selected").text();
	if(province!='请选择'){
	var operatingCityNo=province.split('-');
	$.post('sysRegion/getProvince.do', {provinceName:operatingCityNo[0].trim()}, 
			function(data) {
				if(data){
					var select="<select name='provinceId' id='provinceId'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCityAddchargingStation(this.value)'>";
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
					$("#provinceIdAdd").html("")
					$("#provinceIdAdd").append(select);
					getResultCityAddchargingStation_operatingCity(p,operatingCityNo[1].trim());
				}
	},"json");
	}else{
		$("#countrycityAdd").html("");
		$("#itemcityAdd").html("");
		$.post('sysRegion/getProvince.do', {provinceName:'请选择'}, 
				function(data) {
			if(data){
				var select="<select name='provinceId' id='provinceId'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCityAddchargingStation(this.value)'>" +
						"<option value=''>请选择</option>";
				for(var i=0;i<data.length;i++){
					select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
				}
				select+="</select>";
				$("#provinceIdAdd").html("")
				$("#provinceIdAdd").append(select);
			}
		},"json");
	}
}
//根据运营城市 获得市
function getResultCityAddchargingStation_operatingCity(d,s){
	 var form = $("form[name=chargingStationAddForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityAdd").html("");
                         var select="<select name='cityId' id='cityId'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryAddchargingStation(this.value)'>";
                        var x;
                         for(var i=0;i<data.length;i++){
                        	if(data[i].regionName==s){
                        		select+="<option  value='"+data[i].regionId+"' selected='selected'> "+data[i].regionName+" </option>";
                        		x=data[i].regionId;
                        	}else{
                        		select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                        	}
              			}
              			select+="</select>";
              			getResultCountryAddchargingStation(x);
              			$("#itemcityAdd").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityAdd").html("");
		$("#itemcityAdd").html("");
	}
}
