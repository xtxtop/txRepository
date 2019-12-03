define(
[],
function() {
var parkingEdit = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		 var forms=$("form[name=parkingEditForm]");
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
		 /*//根据层数显示车位分布
		 var pliesNumber=forms.find("input[name='pliesNumber']").val();
		 if(pliesNumber<=1){
			 $("#editParkingTwo").hide();
		     $("#editParkingOne").removeClass('col-md-9');
		     $("#editParkingOne").addClass('col-md-12');
		 }else{
			 
		 }*/
		//添加提交
		$("#Editparking").click(function(){
			parkingEdit.Editparking();
		});
		//返回
		$("#Editcloseparking").click(function(){
			closeTab("编辑停车场");
			$("#parkingList").DataTable().ajax.reload(function(){});
		});
		//选择营业时间
		$("#editBusinessHours_parking").click(function(){
			form.find("input[name=businessHours]").click();
		});
        //停车场上传图片
        $("#EditparkingPhotoButton_parking").click(function(){
            $("#parkingPhotoEditModal").modal("show");
        });
        //停车场图片的回填
        $("#EditparkingPhotoBtn").click(function(){
            var form=$("form[name=parkingphotoFormEdit]");
            var img=form.find("input[name=parkingPicUrlEdit]").val();
            if(img!=""){
                var form1=$("form[name=parkingEditForm]");
                form1.find("input[name=parkingUrl]").val(img);
                form1.find("#parkingPicUrlImg_parking").css("background-image","url("+parkingEdit.imgPath+'/'+img+")");
                $("#parkingPhotoEditModal").modal("hide");
            }else{
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
            }

        });
        /*地图定位*/
        $("#chargingSearchGC_edit_parking").click(function(){
            var cityId = $("#cityId_edit_parking").val();
            if(cityId == ""||typeof(cityId) == 'undefined'){
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择城市！", function() {
                });
            } else {
                $("#storeEditMapModel_parking").modal("show");
                parkingEdit.mapInit();
            }
        });
	},
	Editparking:function() {
		var form = $("form[name=parkingEditForm]");
		form.ajaxSubmit({
			url : parkingEdit.appPath + "/parking/toEditparking.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑停车场");
						$("#parkingList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=parkingEditForm]");
				$("span[id$='edit_parking_mlcx_verify']").html("");//清空所有span提示
				form.find("input[name='parkingName']").val();//名称
				form.find("select[name='provinceId']").val();//省
				form.find("input[name='longitude']").val();//经纬度
				form.find("input[name='addrStreet']").val();//详细地址
				form.find("input[name='parkingUrl']").val();//图片
				form.find("input[name='pliesNumber']").val();//层数
				form.find("input[name='parkingSpaceNumber']").val();//车位数
				form.find("input[name='surplusSpaceNumber']").val();//剩余车位
				var rents=/^[0-9]{0,5}?$/;//验证数字
					verifySpaceNumEdit();
					ground_verifySpaceNumEdit();//验证地面车位
					under_verifySpaceNumEdit();
					if(parseInt(form.find("input[name='undergroundSurplusSpaceNumber']").val())>parseInt(form.find("input[name='undergroundParkingSpaceNumber']").val())){
						spanWarning("undergroundSurplusSpaceNumber_edit_parking","剩余车位不能大于总车位!")
					}
					if(parseInt(form.find("input[name='groundSurplusSpaceNumber']").val())>parseInt(form.find("input[name='groundParkingSpaceNumber']").val())){
						spanWarning("groundSurplusSpaceNumber_edit_parking","剩余车位不能大于总车位!")
					}
					if(parseInt(form.find("input[name='surplusSpaceNumber']").val())>parseInt(form.find("input[name='parkingSpaceNumber']").val())){
						spanWarning("surplusSpaceNumber_edit_parking","剩余车位不能大于总车位!")
					}
				if(form.find("input[name='parkingName']").val()==''){
					spanWarning("parkingName_edit_parking","请输入停车场名称!")
				}
				if(form.find("select[name='provinceId']").val()==''){
					spanWarning("provinceId_edit_parking","请选择所在地区!")
				}
				if(form.find("input[name='longitude']").val()==''||form.find("input[name='latitude']").val()==''){
					spanWarning("longitude_edit_parking","请输入经纬度!")
				}
				if(form.find("input[name='addrStreet']").val()==''){
					spanWarning("addrStreet_edit_parking","请输入详细地址!")
				}
				if(form.find("input[name='parkingUrl']").val()==''){
					spanWarning("parkingUrl_edit_parking","请上传图片!")
				}
			/*	if(form.find("input[name='parkingSpaceNumber']").val()==''){
					spanWarning("parkingSpaceNumber_edit_parking","请输入车位数!")
				}
				if(form.find("input[name='surplusSpaceNumber']").val()==''){
					spanWarning("surplusSpaceNumber_edit_parking","请输入车位数!")
				}
				if(form.find("input[name='pliesNumber']").val()==''){
					spanWarning("pliesNumber_edit_parking","请输入层数!")
				}*/
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
					spanWarning("supportedServices_edit_parking","请选择停车场周边服务!")
				}
				var checkbox_label=form.find("input[name='label']")
				var checkbox_labelCount=0
				for(var i=0;i<checkbox_label.length;i++){
					if(checkbox_label[i].checked){
						checkbox_labelCount++;
					}
				}
				if(checkbox_labelCount==0){
					spanWarning("label_edit_parking","请选择停车场标签!")
				}
				var edit_parking_mlcx_verify=$("span[id$='edit_parking_mlcx_verify']")
				for(var i=0;i<edit_parking_mlcx_verify.length;i++){
					if(edit_parking_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
 			}
		});
	 },
	 mapInit:function() {
	        var map = new BMap.Map("searchStoreMapEdit_parking",{enableMapClick: false}); // 创建地图实例
	        var form = $("form[name=parkingEditForm]");
	        var cityId = $("#cityId_edit_parking").val();
	        var provinceName = $("#provinceIdEdit_parking").find("option:selected").text();
	        var cityName = $("#cityId_edit_parking").find("option:selected").text();
	        var region = $("#districtIdEdit_parking").val();
	           cityName =  cityName;
	        if(region!='') {
	            cityName +=$("#districtIdEdit_parking").find("option:selected").text();
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
	                            $("#Editress_parking").html(addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber);
	                            $("#coordinate_edit_parking").html(e.point.lng+'  '+e.point.lat);
	                            
	                        });
	                    });

	                } else{
	                    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无城市信息！", function() {
	                    });
	                }
				}
			}
	return parkingEdit;
})
//获得县、区
function getResultCountryEditparking_parking(a){
	 var form = $("form[name=parkingEditForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	$.post('sysRegion/getCountrys.do', {id:a},
		 function(data) {
				if(data){
					$("#countrycityEdit_parking").html("");
                     if(data.length!=0){ 
                    	 var select="<select class='col-sm-4' name='districtId' id='districtIdEdit_parking' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;'>";
                    	 for(var i=0;i<data.length;i++){
                				select+="<option value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
                		 } 
                    	 select+="</select>";
                    	 $("#countrycityEdit_parking").append(select);
                     }else{
                     }
              		
				} 
		},"json");
}
//获得市
function getResultCityEditparking_parking(d){
	 var form = $("form[name=parkingEditForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityEdit_parking").html("");
                         var select="<select name='cityId' id='cityId_edit_parking'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryEditparking_parking(this.value)'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			getResultCountryEditparking_parking(pId);
              			$("#itemcityEdit_parking").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityEdit_parking").html("");
		$("#itemcityEdit_parking").html("");
	}
}

//复选框验证
function checkbox_parking(x){
	if(x.checked){
		$(x).attr('checked',true);
	}else{
		$(x).attr('checked',false);
		
	}
}

//根据运营城市获取所在地区
function operatingCityNoChangeEdit_parking(){
	var province=$("#operatingCityNo_edit_parking option:selected").text();
	var operatingCityNo=province.split('-');
	$.post('sysRegion/getProvince.do', {provinceName:operatingCityNo[0].trim()}, 
			function(data) {
				if(data){
					var select="<select name='provinceId' id='provinceIdEdit_parking' onclick='formVerify('provinceId_edit_parking')'   class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCityEditparking_parking(this.value)'>";
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
					$("#provinceIdEdits_parking").html("")
					$("#provinceIdEdits_parking").append(select);
					getResultCityEditparking_operatingCity_parking(p,operatingCityNo[1].trim());
				}
	},"json");
}
//根据运营城市 获得市
function getResultCityEditparking_operatingCity_parking(d,s){
	 var form = $("form[name=parkingEditForm]");
	 //清空地址 经纬度
	 form.find("input[name=addrStreet]").val("");
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityEdit_parking").html("");
                         var select="<select name='cityId' id='cityId_edit_parking'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='getResultCountryEditparking_parking(this.value)'>";
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
              			getResultCountryEditparking_parking(x);
              			$("#itemcityEdit_parking").append(select);
					} 
	 },"json");
	}else{
		$("#countrycityEdit_parking").html("");
		$("#itemcityEdit_parking").html("");
	}
}
//楼层停车场触发事件
function typeChangeEdit(){
	var form = $("form[name=parkingEditForm]");
	var pliesNumber=form.find("input[name=pliesNumber]").val();//层数
	var parkingSpaceNumber=form.find("input[name=parkingSpaceNumber]").val();//总车位数
	var surplusSpaceNumber=form.find("input[name=surplusSpaceNumber]").val();//剩余车位数
	var muchPliesNumber=form.find("input[name=muchPliesNumber]").val();//多入口层数
	var much;
	if(muchPliesNumber!=null&&muchPliesNumber!=''){
		much=muchPliesNumber.split(',');
	}
	if(surplusSpaceNumber-parkingSpaceNumber>0){
		spanWarning("surplusSpaceNumber_edit_parking","剩余车位数不能大于总车位数!")
	}else if(pliesNumber>0&&parkingSpaceNumber>0&&surplusSpaceNumber>0){
		formVerify('surplusSpaceNumber_edit_parking')
		//初始隐藏车位分布
        $("#editParkingTwo").show();
        $("#editParkingOne").removeClass('col-md-12');
        $("#editParkingOne").addClass('col-md-9');
		$("#spaceNumEdit").html("");
		//剩余车位
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
		//总车位
		var total_spaceNumLast=parkingSpaceNumber%pliesNumber;//取余
		var total_num=0;//每层车位数
		var total_lastNum=0;//最后一层车位数
		if(total_spaceNumLast==0){
			total_num=parkingSpaceNumber/pliesNumber;
			total_lastNum=total_num;
		}else{
			total_num=(parkingSpaceNumber-total_spaceNumLast)/pliesNumber;
			total_lastNum=total_num+total_spaceNumLast;
		}
		//每层车位数
		if(much!=null&&much.length>0){
			for(var j=0;j<much.length;j++){
				numberTd+='<tr><td style="width:24%;text-align: center;"><label><input type="text" class="form-control val " id="editSpace_'+(parseInt(pliesNumber)+j+1)+'" onblur="blurSpace(this)" value="'+(much[j])+'层东"></label>'+
				'<input type="hidden" name="spaceNumber" id="parking_surplus_'+(parseInt(pliesNumber)+j+1)+'" value="'+(much[j])+'层东,0,0"></td>'+
				'<td><input type="number" min="1" id="parking_'+(parseInt(pliesNumber)+j+1)+'"  onblur="blurSpace(this)" onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val pliesNumbers"'+ 
				'name="parkingSpaceNumbers" placeholder="请输入车位数" value="0"></td>'+
				'<td><input type="number" min="1" max="0" id="surplus_'+(parseInt(pliesNumber)+j+1)+'" onblur="blurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val pliesNumber"'+ 
					'name="surplusSpaceNumbers" placeholder="请输入车位数" value="0"></td></tr>';
				'</tr>';
			}
		}
		//每层车位数
		for(var i=0;i<pliesNumber;i++){
			if(i==pliesNumber-1){
				numberTd+='<tr><td style="width:24%;text-align: center;"><label><input type="text" class="form-control val " id="editSpace_'+(i+1)+'" onblur="blurSpace(this)" value="'+(i+1)+'层"></label>'+
				'<input type="hidden" name="spaceNumber" id="parking_surplus_'+(i+1)+'" value="'+(i+1)+'层,'+total_lastNum+','+lastNum+'"></td>'+
				'<td><input type="number" min="1" id="parking_'+(i+1)+'"  onblur="blurSpace(this)" onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val pliesNumbers"'+ 
				'name="parkingSpaceNumbers" placeholder="请输入车位数" value='+total_lastNum+'></td>'+
				'<td><input type="number" min="1" max="'+total_lastNum+'" id="surplus_'+(i+1)+'" onblur="blurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val pliesNumber"'+ 
					'name="surplusSpaceNumbers" placeholder="请输入车位数" value='+lastNum+'></td></tr>';
				'</tr>';
			}else{
				numberTd+='<tr><td style="width:24%;text-align: center;"><label><input type="text" class="form-control val " id="editSpace_'+(i+1)+'" onblur="blurSpace(this)" value="'+(i+1)+'层"></label>'+
				'<input type="hidden" name="spaceNumber" id="parking_surplus_'+(i+1)+'" value="'+(i+1)+'层,'+total_num+','+num+'"></td>'
				+'<td><input type="number" min="1"  id="parking_'+(i+1)+'"  onblur="blurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val pliesNumbers"'+ 
				'name="parkingSpaceNumbers" placeholder="请输入车位数" value='+total_num+'></td>'+
				'<td><input type="number" min="1" max="'+total_num+'" id="surplus_'+(i+1)+'"  onblur="blurSpace(this)" onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val pliesNumber"'+ 
				'name="surplusSpaceNumbers" placeholder="请输入车位数" value='+num+'></td></tr>';
			}
		}
		$("#spaceNumEdit").append(numberTd);
	}else{
		formVerify('surplusSpaceNumber_edit_parking')
		/*//初始隐藏车位分布
        $("#editParkingTwo").hide();
        $("#editParkingOne").removeClass('col-md-9');
        $("#editParkingOne").addClass('col-md-12');*/
		$("#spaceNumEdit").html("");
	}
}
//车位数统计
function verifySpaceNumEdit(){
	var nums=$(".pliesNumbers");
	var num=$(".pliesNumber");
	var spaceNums=0;
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
	 $.each(nums, function(i,val){      
		 spaceNums+=parseInt(val.value);
	 });   
	var form = $("form[name=parkingEditForm]");
	var surplusSpaceNumbers=form.find("input[name=surplusSpaceNumber]").val();//剩余车位数
	var parkingSpaceNumber=form.find("input[name=parkingSpaceNumber]").val();//总车位数
	 if(parseInt(spaceNum)-parseInt(surplusSpaceNumbers)>0){
		 spanWarning("plies_edit_parking","车辆分布超过剩余总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"楼层停车场车辆分布已超过剩余车辆总数!");
		 //typeChangeEdit()
		return false;
	 }
	 if(parseInt(spaceNums)-parseInt(parkingSpaceNumber)>0){
		 spanWarning("plies_edit_parking","车辆分布超过总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"楼层停车场车辆分布已超过车辆总数!");
		 //typeChangeEdit()
		 return false;
	 }
}
//当修改分布车位时触发事件
function blurSpace(x){
	var form = $("form[name=parkingEditForm]");
	var str=x.id.split("_");
	var park=$("#parking_surplus_"+str[1]).val().split(",");
	if(str[0]=='editSpace'){
		var space=$("#editSpace_"+str[1]).val();
		var sur=$("#surplus_"+str[1]).val();
		if(park.length==4){
			$("#parking_surplus_"+str[1]).val(park[0]+','+x.value+','+$("#parking_"+str[1]).val()+','+$("#surplus_"+str[1]).val());
		}else{
			$("#parking_surplus_"+str[1]).val(x.value+','+$("#parking_"+str[1]).val()+','+$("#surplus_"+str[1]).val());
		}
	}else if(str[0]=='parking'){
		var space=$("#editSpace_"+str[1]).val();
		var sur=$("#surplus_"+str[1]).val();
		if(parseInt(x.value)<parseInt(sur)){
			$("#surplus_"+str[1]).val(x.value)
		}
		$("#surplus_"+str[1]).attr('max',x.value);
		if(park.length==4){
			$("#parking_surplus_"+str[1]).val(park[0]+','+space+','+x.value+','+$("#surplus_"+str[1]).val());
		}else{
			$("#parking_surplus_"+str[1]).val(space+','+x.value+','+$("#surplus_"+str[1]).val());
		}
	}else{
		if(park.length==4){
			$("#parking_surplus_"+str[1]).val(park[0]+','+$("#editSpace_"+str[1]).val()+','+$("#parking_"+str[1]).val()+','+x.value);
		}else{
			$("#parking_surplus_"+str[1]).val($("#editSpace_"+str[1]).val()+','+$("#parking_"+str[1]).val()+','+x.value);
		}
	}
	var nums=$(".pliesNumbers");
	var num=$(".pliesNumber");
	var spaceNums=0;
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
	 $.each(nums, function(i,val){      
		 spaceNums+=parseInt(val.value);
	 });   
	 form.find("input[name=parkingSpaceNumber]").val(spaceNums);
	 form.find("input[name=surplusSpaceNumber]").val(spaceNum);
}
//地下停车场触发事件
function undergroundNumberChangeEdit(){
	var form = $("form[name=parkingEditForm]");
	var undergroundNumber=form.find("input[name=undergroundNumber]").val();//层数
	var undergroundParkingSpaceNumber=form.find("input[name=undergroundParkingSpaceNumber]").val();//总车位数
	var undergroundSurplusSpaceNumber=form.find("input[name=undergroundSurplusSpaceNumber]").val();//剩余车位数
	if(undergroundSurplusSpaceNumber-undergroundParkingSpaceNumber>0){
		spanWarning("undergroundSurplusSpaceNumber_edit_parking","剩余车位数不能大于总车位数!")
	}else if(undergroundNumber>0&&undergroundParkingSpaceNumber>0&&undergroundSurplusSpaceNumber>0){
		formVerify('undergroundSurplusSpaceNumber_edit_parking')
		//初始隐藏车位分布
        $("#editParkingTwo").show();
        $("#editParkingOne").removeClass('col-md-12');
        $("#editParkingOne").addClass('col-md-9');
		$("#under_spaceNumEdit").html("");
		var numberTd;//层数+车位数
		//剩余车位
		var under_spaceNumLast=undergroundParkingSpaceNumber%undergroundNumber;//取余
		var under_num=0;//每层车位数
		var under_lastNum=0;//最后一层车位数
		if(under_spaceNumLast==0){
			under_num=undergroundParkingSpaceNumber/undergroundNumber;
			under_lastNum=under_num;
		}else{
			under_num=(undergroundParkingSpaceNumber-under_spaceNumLast)/undergroundNumber;
			under_lastNum=under_num+under_spaceNumLast;
		}
		//总车位
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
				numberTd+='<tr><td style="width:24%;text-align: center;"><label><input type="text" class="form-control val " id="under_editSpace_'+(i+1)+'" onblur="under_blurSpace(this)" value="负'+(i+1)+'层"></label>'+
				'<input type="hidden" name="under_spaceNumber" id="under_parking_surplus_'+(i+1)+'" value="负'+(i+1)+'层,'+under_lastNum+','+lastNum+'"></td>'+
				'<td><input type="number" min="1" id="under_parking_'+(i+1)+'"  onblur="under_blurSpace(this)" onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val under_pliesNumbers"'+ 
				'name="under_parkingSpaceNumbers" placeholder="请输入车位数" value='+under_lastNum+'></td>'+
				'<td><input type="number" min="1" max="'+under_lastNum+'" id="under_surplus_'+(i+1)+'" onblur="under_blurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val under_pliesNumber"'+ 
					'name="under_surplusSpaceNumbers" placeholder="请输入车位数" value='+lastNum+'></td></tr>';
				'</tr>';
			}else{
				numberTd+='<tr><td style="width:24%;text-align: center;"><label><input type="text" class="form-control val " id="under_editSpace_'+(i+1)+'" onblur="under_blurSpace(this)" value="负'+(i+1)+'层"></label>'+
				'<input type="hidden" name="under_spaceNumber" id="under_parking_surplus_'+(i+1)+'" value="负'+(i+1)+'层,'+under_num+','+num+'"></td>'
				+'<td><input type="number" min="1"  id="under_parking_'+(i+1)+'"  onblur="under_blurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val under_pliesNumbers"'+ 
				'name="under_parkingSpaceNumbers" placeholder="请输入车位数" value='+under_num+'></td>'+
				'<td><input type="number" min="1" max="'+under_num+'" id="under_surplus_'+(i+1)+'"  onblur="under_blurSpace(this)" onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val under_pliesNumber"'+ 
				'name="under_surplusSpaceNumbers" placeholder="请输入车位数" value='+num+'></td></tr>';
			}
		}
		$("#under_spaceNumEdit").append(numberTd);
	}else{
		formVerify('undergroundSurplusSpaceNumber_edit_parking')
		/*//初始隐藏车位分布
        $("#editParkingTwo").hide();
        $("#editParkingOne").removeClass('col-md-9');
        $("#editParkingOne").addClass('col-md-12');*/
		$("#under_spaceNumEdit").html("");
	}
}
//车位数统计
function under_verifySpaceNumEdit(){
	var nums=$(".under_pliesNumbers");
	var num=$(".under_pliesNumber");
	var spaceNums=0;
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
	 $.each(nums, function(i,val){      
		 spaceNums+=parseInt(val.value);
	 });   
	var form = $("form[name=parkingEditForm]");
	var surplusSpaceNumbers=form.find("input[name=undergroundSurplusSpaceNumber]").val();//剩余车位数
	var undergroundParkingSpaceNumber=form.find("input[name=undergroundParkingSpaceNumber]").val();//总车位数
	 if(parseInt(spaceNum)-parseInt(surplusSpaceNumbers)>0){
		 spanWarning("plies_edit_parking","车辆分布超过剩余总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"地下停车场车辆分布已超过剩余车辆总数!");
		 //undergroundNumberChangeEdit()
		return false;
	 }
	 if(parseInt(spaceNums)-parseInt(undergroundParkingSpaceNumber)>0){
		 spanWarning("plies_edit_parking","车辆分布超过总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"地下停车场车辆分布已超过车辆总数!");
		 //undergroundNumberChangeEdit()
		 return false;
	 }
}
//当修改分布车位时触发事件
function under_blurSpace(x){
	var form = $("form[name=parkingEditForm]");
	var str=x.id.split("_");
	var under=$("#under_parking_surplus_"+str[2]).val().split(",");
	if(str[0]=='under'&&str[1]=='editSpace'){
		var space=$("#under_editSpace_"+str[2]).val();
		var sur=$("#under_surplus_"+str[2]).val();
		if(under.length==4){
			$("#under_parking_surplus_"+str[2]).val(under[0]+','+x.value+','+$("#under_parking_"+str[2]).val()+','+$("#under_surplus_"+str[2]).val());
		}else{
			$("#under_parking_surplus_"+str[2]).val(x.value+','+$("#under_parking_"+str[2]).val()+','+$("#under_surplus_"+str[2]).val());
		}
	}else if(str[0]=='under'&&str[1]=='parking'){
		var space=$("#under_editSpace_"+str[2]).val();
		var sur=$("#under_surplus_"+str[2]).val();
		if(parseInt(x.value)<parseInt(sur)){
			$("#under_surplus_"+str[2]).val(x.value)
		}
		$("#under_surplus_"+str[2]).attr('max',x.value);
		if(under.length==4){
			$("#under_parking_surplus_"+str[2]).val(under[0]+','+space+','+x.value+','+$("#under_surplus_"+str[2]).val());
		}else{
			$("#under_parking_surplus_"+str[2]).val(space+','+x.value+','+$("#under_surplus_"+str[2]).val());
		}
	}else{
		if(under.length==4){
			$("#under_parking_surplus_"+str[2]).val(under[0]+','+$("#under_editSpace_"+str[2]).val()+','+$("#under_parking_"+str[2]).val()+','+x.value);
		}else{
			$("#under_parking_surplus_"+str[2]).val($("#under_editSpace_"+str[2]).val()+','+$("#under_parking_"+str[2]).val()+','+x.value);
		}
	}
	var nums=$(".under_pliesNumbers");
	var num=$(".under_pliesNumber");
	var spaceNums=0;
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
	 $.each(nums, function(i,val){      
		 spaceNums+=parseInt(val.value);
	 }); 
	form.find("input[name=undergroundParkingSpaceNumber]").val(spaceNums);
	form.find("input[name=undergroundSurplusSpaceNumber]").val(spaceNum);
	
}
//地面停车场触发事件
function groundNumberChangeEdit(){
	var form = $("form[name=parkingEditForm]");
	var groundParkingSpaceNumber=form.find("input[name=groundParkingSpaceNumber]").val();//总车位数
	var groundSurplusSpaceNumber=form.find("input[name=groundSurplusSpaceNumber]").val();//剩余车位数
	if(groundSurplusSpaceNumber-groundParkingSpaceNumber>0){
		spanWarning("groundSurplusSpaceNumber_edit_parking","剩余车位数不能大于总车位数!")
	}else if(groundParkingSpaceNumber>0&&groundSurplusSpaceNumber>0){
		formVerify('groundSurplusSpaceNumber_edit_parking')
		//初始隐藏车位分布
        $("#editParkingTwo").show();
        $("#editParkingOne").removeClass('col-md-12');
        $("#editParkingOne").addClass('col-md-9');
		$("#ground_spaceNumEdit").html("");
		var numberTd;//层数+车位数
		var num=groundSurplusSpaceNumber;//每层车位数剩余
		var nums=groundParkingSpaceNumber;//每层车位数总数
		//每层车位数
		numberTd+='<tr><td style="width:24%;text-align: center;"><label><input type="text" class="form-control val " id="ground_editSpace_1" onblur="ground_blurSpace(this)" value="地面"></label>'+
		'<input type="hidden" name="ground_spaceNumber" id="ground_parking_surplus_1" value="地面,'+nums+','+num+'"></td>'
		+'<td><input type="number" min="1"  id="ground_parking_1"  onblur="ground_blurSpace(this)"  onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val ground_pliesNumbers"'+ 
		'name="ground_parkingSpaceNumbers" placeholder="请输入车位数" value='+nums+'></td>'+
		'<td><input type="number" min="1" max="'+nums+'" id="ground_surplus_1"  onblur="ground_blurSpace(this)" onkeyup="this.value=this.value.replace(/\D/g,"")"  class="form-control val ground_pliesNumber"'+ 
		'name="ground_surplusSpaceNumbers" placeholder="请输入车位数" value='+num+'></td></tr>';
	
		$("#ground_spaceNumEdit").append(numberTd);
	}else{
		formVerify('groundSurplusSpaceNumber_edit_parking')
		/*//初始隐藏车位分布
        $("#editParkingTwo").hide();
        $("#editParkingOne").removeClass('col-md-9');
        $("#editParkingOne").addClass('col-md-12');*/
		$("#ground_spaceNumEdit").html("");
	}
}
//车位数统计
function ground_verifySpaceNumEdit(){
	var nums=$(".ground_pliesNumbers");
	var num=$(".ground_pliesNumber");
	var spaceNums=0;
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
	 $.each(nums, function(i,val){      
		 spaceNums+=parseInt(val.value);
	 });   
	var form = $("form[name=parkingEditForm]");
	var groundSurplusSpaceNumber=form.find("input[name=groundSurplusSpaceNumber]").val();//剩余车位数
	var groundParkingSpaceNumber=form.find("input[name=groundParkingSpaceNumber]").val();//总车位数
	 if(parseInt(spaceNum)-parseInt(groundSurplusSpaceNumber)>0){
		 spanWarning("plies_edit_parking","车辆分布超过剩余总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"地面停车场车辆分布已超过剩余车辆总数!");
		 //groundNumberChangeEdit()
		return false;
	 }
	 if(parseInt(spaceNums)-parseInt(groundParkingSpaceNumber)>0){
		 spanWarning("plies_edit_parking","车辆分布超过总车位!")
		 bootbox.alert("<img src='res/img/tan.png' style='width:29px;height:29px;margin-top:-4px;'>&nbsp;&nbsp;"+"车面辆分布已超过车辆总数!");
		 //groundNumberChangeEdit()
		 return false;
	 }
}
//当修改分布车位时触发事件
function ground_blurSpace(x){
	var form = $("form[name=parkingEditForm]");
	var str=x.id.split("_");
	var ground=$("#ground_parking_surplus_"+str[2]).val().split(",");
	if(str[0]=='ground'&&str[1]=='editSpace'){
		var space=$("#ground_editSpace_"+str[2]).val();
		var sur=$("#ground_surplus_"+str[2]).val();
		if(ground.length==4){
			$("#ground_parking_surplus_"+str[2]).val(ground[0]+','+x.value+','+$("#ground_parking_"+str[2]).val()+','+$("#ground_surplus_"+str[2]).val());
		}else{
			$("#ground_parking_surplus_"+str[2]).val(x.value+','+$("#ground_parking_"+str[2]).val()+','+$("#ground_surplus_"+str[2]).val());
		}
	}else if(str[0]=='ground'&&str[1]=='parking'){
		var space=$("#ground_editSpace_"+str[2]).val();
		var sur=$("#ground_surplus_"+str[2]).val();
		if(parseInt(x.value)<parseInt(sur)){
			$("#ground_surplus_"+str[2]).val(x.value)
		}
		$("#ground_surplus_"+str[2]).attr('max',x.value);
		if(ground.length==4){
			$("#ground_parking_surplus_"+str[2]).val(ground[0]+','+space+','+x.value+','+$("#ground_surplus_"+str[2]).val());
		}else{
			$("#ground_parking_surplus_"+str[2]).val(space+','+x.value+','+$("#ground_surplus_"+str[2]).val());
		}
	}else{
		if(ground.length==4){
			$("#ground_parking_surplus_"+str[2]).val(ground[0]+','+$("#ground_editSpace_"+str[2]).val()+','+$("#ground_parking_"+str[2]).val()+','+x.value);
		}else{
			$("#ground_parking_surplus_"+str[2]).val($("#ground_editSpace_"+str[2]).val()+','+$("#ground_parking_"+str[2]).val()+','+x.value);
		}
	}
	var nums=$(".ground_pliesNumbers");
	var num=$(".ground_pliesNumber");
	var spaceNums=0;
	var spaceNum=0;
	 $.each(num, function(i,val){      
		 spaceNum+=parseInt(val.value);
	  });   
	 $.each(nums, function(i,val){      
		 spaceNums+=parseInt(val.value);
	 });   
	 form.find("input[name=groundParkingSpaceNumber]").val(spaceNums);
	 form.find("input[name=groundSurplusSpaceNumber]").val(spaceNum);
}
//编辑车位分布
function clickButton(){
	bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "" +
			"是否进行编辑停车场车位分布,编辑后会导致停车场车位分布重新生成,绑定的地锁需要重新绑定才能生效,是否继续!", function(result) {
		if(result){
            $(".clickButton").attr("readonly",false);
            $("#button").addClass("disabled");
		}else{
		}						
	}); 
}