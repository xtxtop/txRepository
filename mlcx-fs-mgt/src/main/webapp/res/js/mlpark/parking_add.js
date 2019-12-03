define(
[],
function() {
var parkingAdd = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
        var form = $("form[name=parkingAddForm]");
        form.find("select[name=districtId]").html("");
        form.find("select[name=cityId]").html("");
        form.find("select[name=districtId]").hide();
        form.find("select[name=cityId]").hide();
        //初始隐藏车位分布
        $("#addParkingTwo").hide();
        $("#addParkingOne").removeClass('col-md-9');
        $("#addParkingOne").addClass('col-md-12');
        
		//添加提交
		$("#addparking").click(function(){
			parkingAdd.addparking();
		});
		//返回
		$("#addcloseparking").click(function(){
			closeTab("停车场增加");
			$("#parkingList").DataTable().ajax.reload(function(){});
		});
		//选择营业时间
		$("#clickBusiness_hours_add").click(function(){
			form.find("input[name=businessHours]").click();
		});
        //停车场上传图片
        $("#addparkingPhotoButton").click(function(){
            $("#parkingPhotoAddModal").modal("show");
        });
        //停车场图片的回填
        $("#addparkingPhotoBtn").click(function(){
            var form=$("form[name=parkingphotoForm]");
            var img=form.find("input[name=parkingPicUrl]").val();
            if(img!=""){
                var form1=$("form[name=parkingAddForm]");
                form1.find("input[name=parkingUrl]").val(img);
                form1.find("#parkingPicUrlImg").css("background-image","url("+parkingAdd.imgPath+'/'+img+")");
                $("#parkingPhotoAddModal").modal("hide");
            }else{
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
            }

        });
        /*地图定位*/
        $("#chargingSearchGC_parking").click(function(){
            var cityId_parking = $("#cityId_parking").val();
            if(cityId_parking == ""||typeof(cityId_parking) == 'undefined'||cityId_parking == null){
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择城市！", function() {
                });
            } else {
                $("#storeAddMapModel_parking").modal("show");
                parkingAdd.mapInit();
            }
        });
	},
	addparking:function() {
		var form = $("form[name=parkingAddForm]");
		form.ajaxSubmit({
			url : parkingAdd.appPath + "/parking/toAddParking.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("停车场增加");
						$("#parkingList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=parkingAddForm]");
				$("span[id$='add_parking_mlcx_verify']").html("");//清空所有span提示
				form.find("input[name='parkingName']").val();//名称
				form.find("select[name='parkingType']").val();//停车场类型
				form.find("select[name='operatingCityNo']").val();//运营城市
				form.find("select[name='provinceId_parking']").val();//省
				form.find("input[name='longitude']").val();//经纬度
				form.find("input[name='addrStreet']").val();//详细地址
				form.find("input[name='parkingSpaceNumber']").val();//总车位
				form.find("input[name='surplusSpaceNumber']").val();//剩余车位
				form.find("select[name='type']").val();//设备类型
				form.find("input[name='parkingUrl']").val();//图片
				form.find("input[name='pliesNumber']").val();//层数
				var rents=/^[0-9]{0,5}?$/;//验证数字
				verifySpaceNum();
				ground_verifySpaceNum();//验证地面分布总车位
				undergroundNumber_verifySpaceNum();
				
				if(form.find("input[name='parkingName']").val()==''){
					spanWarning("parkingName_add_parking","请输入停车场名称!")
				}
				if(form.find("select[name='operatingCityNo']").val()==''){
					spanWarning("operatingCityNo_add_parking","请选择运营城市!")
				}
				if(form.find("select[name='provinceId_parking']").val()==''){
					spanWarning("provinceId_add_parking","请选择所在地区!")
				}
				if(parseInt(form.find("input[name='undergroundSurplusSpaceNumber']").val())>parseInt(form.find("input[name='undergroundParkingSpaceNumber']").val())){
					spanWarning("undergroundSurplusSpaceNumber_add_parking","剩余车位不能大于总车位!")
				}
				if(parseInt(form.find("input[name='groundSurplusSpaceNumber']").val())>parseInt(form.find("input[name='groundParkingSpaceNumber']").val())){
					spanWarning("groundSurplusSpaceNumber_add_parking","剩余车位不能大于总车位!")
				}
				if(parseInt(form.find("input[name='surplusSpaceNumber']").val())>parseInt(form.find("input[name='parkingSpaceNumber']").val())){
					spanWarning("surplusSpaceNumber_add_parking","剩余车位不能大于总车位!")
				}
				if(form.find("input[name='longitude']").val()=='' ||form.find("input[name='latitude']").val()==''){
					spanWarning("longitude_add_parking","请输入经纬度!")
				}
				if(form.find("select[name='billingSchemeNo']").val()==''){
					spanWarning("billingSchemeNo_add_parking","请选择计费方案!")
				}
				if(form.find("input[name='addrStreet']").val()==''){
					spanWarning("addrStreet_add_parking","请输入详细地址!")
				}
			/*	if(form.find("input[name='parkingSpaceNumber']").val()==''){
					spanWarning("parkingSpaceNumber_add_parking","请输入总车位数!")
				}
				if(form.find("input[name='surplusSpaceNumber']").val()==''){
					spanWarning("parkingSpaceNumber_add_parking","请输入剩余车位数!")
				}*/
				if(form.find("input[name='parkingUrl']").val()==''){
					spanWarning("parkingUrl_add_parking","请上传图片!")
				}
				/*if(form.find("input[name='pliesNumber']").val()==''){
					spanWarning("pliesNumber_add_parking","请输入层数!")
				}*/
				if(form.find("select[name='parkingType']").val()==''){
					spanWarning("parkingType_add_parking","请选择停车场类型!")
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
					spanWarning("supportedServices_add_parking","请选择停车场周边服务!")
				}
				var checkbox_label=form.find("input[name='label']")
				var checkbox_labelCount_add=0
				for(var i=0;i<checkbox_label.length;i++){
					if(checkbox_label[i].checked){
						checkbox_labelCount_add++;
					}
				}
				if(checkbox_labelCount_add==0){
					spanWarning("label_add_parking","请选择停车场标签!")
				}
				//判断提交验证
				var add_parking_mlcx_verify=$("span[id$='add_parking_mlcx_verify']")
				for(var i=0;i<add_parking_mlcx_verify.length;i++){
					if(add_parking_mlcx_verify[i].innerHTML!=''){
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
	        var map = new BMap.Map("searchStoreMap_parking",{enableMapClick: false}); // 创建地图实例
	        var form = $("form[name=parkingAddForm]");
	        var cityId = $("#cityId_parking").val();
	        var provinceName = $("#provinceId_parking").find("option:selected").text();
	        var cityName = $("#cityId_parking").find("option:selected").text();
	        var region = $("#districtId_parking").val();
	           cityName =  cityName;
	        if(region!='') {
	            cityName +=$("#districtId_parking").find("option:selected").text();
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
	                            $("#address_parking").html(addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber);
	                            $("#coordinate_parking").html(e.point.lng+'  '+e.point.lat);
	                            
	                        });
	                    });

	                } else{
	                    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无城市信息！", function() {
	                    });
	                }
				}
			}
	return parkingAdd;
})
//获得县、区
function getResultCountryAddparking(a){
	 var form = $("form[name=parkingAddForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#countrycityAdd_park").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='col-sm-4' name='districtId' id='districtId_parking' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#countrycityAdd_park").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}
//获得市
function getResultCityAddparking(d){
	 var form = $("form[name=parkingAddForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityAdd_parking").html("");
                         var select="<select name='cityId' id='cityId_parking'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryAddparking(this.value)'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			getResultCountryAddparking(pId);
              			$("#itemcityAdd_parking").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityAdd_park").html("");
		$("#itemcityAdd_parking").html("");
	}
}
//根据运营城市获取所在地区
function operatingCityNoChangeAdd_parking(){
	var province=$("#operatingCityNo_add_parking option:selected").text();
	if(province!='请选择'){
	var operatingCityNo=province.split('-');
	$.post('sysRegion/getProvince.do', {provinceName:operatingCityNo[0].trim()}, 
			function(data) {
				if(data){
					var select="<select name='provinceId' id='provinceId_parking'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCityAddparking(this.value)'>";
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
					$("#provinceId_parkingAdd").html("")
					$("#provinceId_parkingAdd").append(select);
					getResultCityAddparking_operatingCity_parking(p,operatingCityNo[1].trim());
				}
	},"json");
	}else{
		$("#countrycityAdd_park").html("");
		$("#itemcityAdd_parking").html("");
		$.post('sysRegion/getProvince.do', {provinceName:'请选择'}, 
				function(data) {
					if(data){
						var select="<select name='provinceId' id='provinceId_parking'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCityAddparking(this.value)'>" +
								"<option value=''>请选择</option>";
						for(var i=0;i<data.length;i++){
							select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
						}
						select+="</select>";
						$("#provinceId_parkingAdd").html("")
						$("#provinceId_parkingAdd").append(select);
					}
		},"json");
	}
}
//根据运营城市 获得市
function getResultCityAddparking_operatingCity_parking(d,s){
	 var form = $("form[name=parkingAddForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityAdd_parking").html("");
                         var select="<select name='cityId' id='cityId_parking'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryAddparking(this.value)'>";
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
              			getResultCountryAddparking(x);
              			$("#itemcityAdd_parking").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityAdd_park").html("");
		$("#itemcityAdd_parking").html("");
	}
}
//楼层停车场触发事件
function typeChangeAdd(){
	var form = $("form[name=parkingAddForm]");
	var pliesNumber=form.find("input[name=pliesNumber]").val();//层数
	var parkingSpaceNumber=form.find("input[name=parkingSpaceNumber]").val();//总车位数
	var surplusSpaceNumber=form.find("input[name=surplusSpaceNumber]").val();//剩余车位数
	var muchPliesNumber=form.find("input[name=muchPliesNumber]").val();//多入口层数
	var much;
	if(muchPliesNumber!=null&&muchPliesNumber!=''){
		much=muchPliesNumber.split(',');
	}
	if(surplusSpaceNumber-parkingSpaceNumber>0){
		spanWarning("surplusSpaceNumber_add_parking","剩余车位数不能大于总车位数!")
	}else if(pliesNumber>0&&parkingSpaceNumber>0&&surplusSpaceNumber>0){
		formVerify('surplusSpaceNumber_add_parking')
		//初始隐藏车位分布
        $("#addParkingTwo").show();
        $("#addParkingOne").removeClass('col-md-12');
        $("#addParkingOne").addClass('col-md-9');
		$("#spaceNumAdd").html("");
		var numberTd;//层数+车位数
		var spaceNumLast=surplusSpaceNumber%pliesNumber;//取余
		var num=0;//每层车位数
		var lastNum=0;//最后一层车位数
		if(spaceNumLast==0){
			num=surplusSpaceNumber/pliesNumber;
			lastNum=num;
		}else{
			num=(surplusSpaceNumber-spaceNumLast)/pliesNumber;
			lastNum=num+spaceNumLast;
		}
		//每层车位数
		if(much!=null&&much.length>0){
			for(var j=0;j<much.length;j++){
				numberTd+='<tr> <input type="hidden" name="spaceNumber_add" id="add_parking_surplus_'+(parseInt(pliesNumber)+j+1)+'" value="'+much[j]+'层东,'+0+'">'
				+'<td style="width:24%;text-align: center;"><label><input type="text" class="form-control val" onblur="addBlurSpace(this)" id="space_'+(parseInt(pliesNumber)+j+1)+'" name="space_'+(parseInt(pliesNumber)+j+1)+'" value="'+much[j]+'层东"></label></td>';

				numberTd+='<td><input type="number" min="1" id="addSpace_'+(parseInt(pliesNumber)+j+1)+'" onblur="addBlurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val"'+ 
				'name="spaceNumber" placeholder="请输入车位数" value="0"></td></tr>';
			}
		}
		for(var i=0;i<pliesNumber;i++){
			if(i==pliesNumber-1){
				numberTd+='<tr> <input type="hidden" name="spaceNumber_add" id="add_parking_surplus_'+(i+1)+'" value="'+(i+1)+'层,'+lastNum+'">'
				+'<td style="width:24%;text-align: center;"><label><input type="text" class="form-control val" onblur="addBlurSpace(this)" id="space_'+(i+1)+'" name="space_'+(i+1)+'" value="'+(i+1)+'层"></label></td>';

				numberTd+='<td><input type="number" min="1" id="addSpace_'+(i+1)+'" onblur="addBlurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val"'+ 
				'name="spaceNumber" placeholder="请输入车位数" value='+lastNum+'></td></tr>';
			}else{
				numberTd+='<tr> <input type="hidden" name="spaceNumber_add" id="add_parking_surplus_'+(i+1)+'" value="'+(i+1)+'层,'+num+'">'
				+'<td style="width:24%;text-align: center;"><label><input type="text" class="form-control val" onblur="addBlurSpace(this)" id="space_'+(i+1)+'" name="space_'+(i+1)+'" value="'+(i+1)+'层"></label></td>';

				numberTd+='<td><input type="number" min="1" id="addSpace_'+(i+1)+'" onblur="addBlurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val"'+ 
				'name="spaceNumber" placeholder="请输入车位数" value='+num+'></td></tr>';
			}
		}
		$("#spaceNumAdd").append(numberTd);
	}else{
		formVerify('surplusSpaceNumber_add_parking')
		/*//初始隐藏车位分布
        $("#addParkingTwo").hide();
        $("#addParkingOne").removeClass('col-md-9');
        $("#addParkingOne").addClass('col-md-12');*/
		$("#spaceNumAdd").html("");
	}
}
//车位数统计
function verifySpaceNum(){
	var num=$("input[name='spaceNumber']");
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
		var form = $("form[name=parkingAddForm]");
		var surplusSpaceNumbers=form.find("input[name=surplusSpaceNumber]").val();//剩余车位数
	 if(parseInt(spaceNum)-parseInt(surplusSpaceNumbers)>0){
		 spanWarning("plies_add_parking","车辆分布超过剩余总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"车辆分布已超过剩余是车辆总数!");
		 typeChangeAdd()
		return false;
	 }
}
//当修改分层时触发事件
function addBlurSpace(x){
	var str=x.id.split("_");
	if(str[0]=='addSpace'){
		var space=$("#space_"+str[1]).val();
		$("#add_parking_surplus_"+str[1]).val(space+","+x.value);
	}else{
		var add_space=$("#addSpace_"+str[1]).val();
		$("#add_parking_surplus_"+str[1]).val(x.value+","+add_space);	
	}
}
//地面停车场触发事件
function groundNumberChangeAdd(){
	var form = $("form[name=parkingAddForm]");
	var groundParkingSpaceNumber=form.find("input[name=groundParkingSpaceNumber]").val();//总车位数
	var groundSurplusSpaceNumber=form.find("input[name=groundSurplusSpaceNumber]").val();//剩余车位数
	if(groundSurplusSpaceNumber-groundParkingSpaceNumber>0){
		spanWarning("groundSurplusSpaceNumber_add_parking","剩余车位数不能大于总车位数!")
	}else if(groundParkingSpaceNumber>0&&groundSurplusSpaceNumber>0){
		formVerify('groundSurplusSpaceNumber_add_parking')
		//初始隐藏车位分布
        $("#addParkingTwo").show();
        $("#addParkingOne").removeClass('col-md-12');
        $("#addParkingOne").addClass('col-md-9');
		$("#groundNumberAdd").html("");
		var numberTd;//层数+车位数
		var num=0;//每层车位数
		num=groundSurplusSpaceNumber;
		//每层车位数
		numberTd+='<tr> <input type="hidden" name="ground_spaceNumber_add" id="ground_add_parking_surplus_1" value="地面,'+num+'">'
				+'<td style="width:24%;text-align: center;"><label><input type="text" class="form-control val" onblur="ground_addBlurSpace(this)" id="ground_space_1" name="ground_space_1" value="地面"></label></td>';

		numberTd+='<td><input type="number" min="1" id="ground_addSpace_1" onblur="ground_addBlurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val"'+ 
				'name="ground_spaceNumber" placeholder="请输入车位数" value='+num+'></td></tr>';
		$("#groundNumberAdd").append(numberTd);
	}else{
		formVerify('groundSurplusSpaceNumber_add_parking')
	/*	//初始隐藏车位分布
        $("#addParkingTwo").hide();
        $("#addParkingOne").removeClass('col-md-9');
        $("#addParkingOne").addClass('col-md-12');*/
		$("#groundNumberAdd").html("");
	}
}
//车位数统计
function ground_verifySpaceNum(){
	var num=$("input[name='ground_spaceNumber']");
	var ground_spaceNum=0;
	 $.each(num, function(i,val){      
		 ground_spaceNum+=parseInt(val.value);
	  });   
		var form = $("form[name=parkingAddForm]");
		var surplusSpaceNumbers=form.find("input[name=groundSurplusSpaceNumber]").val();//剩余车位数
	 if(parseInt(ground_spaceNum)-parseInt(surplusSpaceNumbers)>0){
		 spanWarning("plies_add_parking","车辆分布超过剩余总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"车辆分布已超过剩余是车辆总数!");
		 groundNumberChangeAdd()
		return false;
	 }
}
//当修改分层时触发事件
function ground_addBlurSpace(x){
	var str=x.id.split("_");
	if(str[0]=='ground'&&str[1]=='addSpace'){
		var ground_space=$("#ground_space_"+str[2]).val();
		$("#ground_add_parking_surplus_"+str[2]).val(ground_space+","+x.value);
	}else{
		var ground_add_space=$("#ground_addSpace_"+str[1]).val();
		$("#ground_add_parking_surplus_"+str[2]).val(x.value+","+ground_add_space);	
	}
}
//地下停车场触发事件
function undergroundNumberChangeAdd(){
	var form = $("form[name=parkingAddForm]");
	var undergroundNumber=form.find("input[name=undergroundNumber]").val();//层数
	var undergroundParkingSpaceNumber=form.find("input[name=undergroundParkingSpaceNumber]").val();//总车位数
	var undergroundSurplusSpaceNumber=form.find("input[name=undergroundSurplusSpaceNumber]").val();//剩余车位数
	if(undergroundSurplusSpaceNumber-undergroundParkingSpaceNumber>0){
		spanWarning("undergroundSurplusSpaceNumber_add_parking","剩余车位数不能大于总车位数!")
	}else if(undergroundNumber>0&&undergroundParkingSpaceNumber>0&&undergroundSurplusSpaceNumber>0){
		formVerify('undergroundSurplusSpaceNumberr_add_parking')
		//初始隐藏车位分布
        $("#addParkingTwo").show();
        $("#addParkingOne").removeClass('col-md-12');
        $("#addParkingOne").addClass('col-md-9');
		$("#undergroundNumberAdd").html("");
		var numberTd;//层数+车位数
		var spaceNumLast=undergroundSurplusSpaceNumber%undergroundNumber;//取余
		var num=0;//每层车位数
		var lastNum=0;//最后一层车位数
		if(spaceNumLast==0){
			num=undergroundSurplusSpaceNumber/undergroundNumber;
			lastNum=num;
		}else{
			num=(undergroundSurplusSpaceNumber-spaceNumLast)/undergroundNumber;
			lastNum=num+spaceNumLast;
		}
		//每层车位数
		for(var i=0;i<undergroundNumber;i++){
			if(i==undergroundNumber-1){
				numberTd+='<tr> <input type="hidden" name="under_spaceNumber_add" id="under_add_parking_surplus_'+(i+1)+'" value="负'+(i+1)+'层,'+lastNum+'">'
				+'<td style="width:24%;text-align: center;"><label><input type="text" class="form-control val" onblur="under_addBlurSpace(this)" id="under_space_'+(i+1)+'" name="under_space_'+(i+1)+'" value="负'+(i+1)+'层"></label></td>';

				numberTd+='<td><input type="number" min="1" id="under_addSpace_'+(i+1)+'" onblur="under_addBlurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val"'+ 
				'name="under_spaceNumber" placeholder="请输入车位数" value='+lastNum+'></td></tr>';
			}else{
				numberTd+='<tr> <input type="hidden" name="under_spaceNumber_add" id="under_add_parking_surplus_'+(i+1)+'" value="负'+(i+1)+'层,'+num+'">'
				+'<td style="width:24%;text-align: center;"><label><input type="text" class="form-control val" onblur="under_addBlurSpace(this)" id="space_'+(i+1)+'" name="under_space_'+(i+1)+'" value="负'+(i+1)+'层"></label></td>';

				numberTd+='<td><input type="number" min="1" id="under_addSpace_'+(i+1)+'" onblur="under_addBlurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val"'+ 
				'name="under_spaceNumber" placeholder="请输入车位数" value='+num+'></td></tr>';
			}
		}
		$("#undergroundNumberAdd").append(numberTd);
	}else{
		formVerify('undergroundSurplusSpaceNumber_add_parking')
	/*	//初始隐藏车位分布
        $("#addParkingTwo").hide();
        $("#addParkingOne").removeClass('col-md-9');
        $("#addParkingOne").addClass('col-md-12');*/
		$("#undergroundNumberAdd").html("");
	}
}
//车位数统计
function undergroundNumber_verifySpaceNum(){
	var num=$("input[name='under_spaceNumber']");
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
		var form = $("form[name=parkingAddForm]");
		var surplusSpaceNumbers=form.find("input[name=undergroundSurplusSpaceNumber]").val();//剩余车位数
	 if(parseInt(spaceNum)-parseInt(surplusSpaceNumbers)>0){
		 spanWarning("plies_add_parking","车辆分布超过剩余总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"车辆分布已超过剩余是车辆总数!");
		 undergroundNumberChangeAdd()
		return false;
	 }
}
//当修改分层时触发事件
function under_addBlurSpace(x){
	var str=x.id.split("_");
	if(str[0]=='under'&&str[1]=='addSpace'){
		var under_space=$("#under_space_"+str[2]).val();
		$("#under_add_parking_surplus_"+str[2]).val(under_space+","+x.value);
	}else{
		var under_add_space=$("#under_addSpace_"+str[2]).val();
		$("#under_add_parking_surplus_"+str[2]).val(x.value+","+under_add_space);	
	}
}