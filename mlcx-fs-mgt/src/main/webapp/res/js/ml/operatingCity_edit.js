define(
[],
function() {
var operatingCityEity = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//添加提交
		$("#EditoperatingCity").click(function(){
			operatingCityEity.EditoperatingCity();
		});
		//返回
		$("#EditcloseoperatingCity").click(function(){
			closeTab("编辑运营城市");
			$("#operatingCityList").DataTable().ajax.reload(function(){});
		});
	},
	EditoperatingCity:function() {
		var form = $("form[name=operatingCityEditForm]");
		form.ajaxSubmit({
			url : operatingCityEity.appPath + "/operatingCity/editoperatingCity.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑运营城市");
						$("#operatingCityList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				  var form = $("form[name=operatingCityEditForm]");
					form.find("select[name='provinceId']").val();//名称
					form.find("input[name='longitude']").val();//经度
					form.find("input[name='latitude']").val();//纬度
					
					if(form.find("select[name='provinceId']").val()==''){
						spanWarning("provinceId_edit_peratingCity","请选择运营城市!")
						return false;
					}
					if(form.find("input[name='longitude']").val()==''||form.find("input[name='latitude']").val()==''){
						spanWarning("longitude_edit_operatingCity","请选择坐标!")
						return false;
					}
			}
		});
	 },
	}
	return operatingCityEity;
})
//获得市
function getResultCityEditoperatingCity(d){
	 $("#itemcityEdit_operatingCity").show();
	 var form = $("form[name=operatingCityEditForm]");
	 //清空地址 经纬度
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
	 form.find("input[name=operatingCityName]").val("");
	if(d!=''){
	$.post('sysRegion/getCitys.do', {id:d}, 
		function(data) {
					if(data){
						$("#itemcityEdit_operatingCity").html("");
                         var select="<select name='cityId_operatingCity' id='cityId_operatingCity_edit'  class='col-sm-4' style='width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;' onchange='cityId_operatingCity_change_edit()'>";
                         var pId;
                         for(var i=0;i<data.length;i++){
                        	pId=data[0].regionId;
              				select+="<option  value='"+data[i].regionId+"'> "+data[i].regionName+" </option>";
              			}
              			select+="</select>";
              			$("#itemcityEdit_operatingCity").append(select);
              			var province=$("#provinceId_operatingCity_edit option:selected").text();
              			var city=$("#cityId_operatingCity_edit option:selected").text();
              			 form.find("input[name=operatingCityName]").val(province.trim()+'-'+city.trim());
              			 if(province.trim()=='北京市'||province.trim()=='上海市'||province.trim()=='重庆市'||province.trim()=='天津市'){
              				 form.find("#edit_operatingCity_city_name").val(province.trim());
              			 }else{
              				 form.find("#edit_operatingCity_city_name").val(city.trim());
              			 }
					} 
	 },"json");
	}else{
		$("#itemcityEdit_operatingCity").html("");
	}
}
//获取经纬度
function getLonAndLat_edit(){
	$("#longitude_edit_operatingCity_mlcx_verify").html("");
	if($("#provinceId_operatingCity_edit").val()!=""&&$("#provinceId_operatingCity_edit").val()!=null){
		var province=$("#provinceId_operatingCity_edit option:selected").text();
		var city=$("#cityId_operatingCity_edit option:selected").text();
		$.ajax({
    		url:"operatingCity/getLonAndLat.do?province="+province+"&city="+city, 
    		success: function(data){
    			if(data.code="1"){
    				 var form = $("form[name=operatingCityEditForm]");
    				 //经纬度
    				 form.find("input[name=longitude]").val(data.data[0]);
    				 form.find("input[name=latitude]").val(data.data[1]);
    			}else{
    				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;获取经纬度失败!");
    			}
    		}
    	});      
	}else{
		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "获取失败，"+msg+"！");
	}
}

//赋值城市名称
function cityId_operatingCity_change_edit(){
	var province=$("#provinceId_operatingCity_edit option:selected").text();
	var city=$("#cityId_operatingCity_edit option:selected").text();
	 var form = $("form[name=operatingCityEditForm]");
	 form.find("input[name=operatingCityName]").val(province.trim()+'-'+city.trim());
	 if(province.trim()=='北京市'||province.trim()=='上海市'||province.trim()=='重庆市'||province.trim()=='天津市'){
		 form.find("#edit_operatingCity_city_name").val(province.trim());
	 }else{
		 form.find("#edit_operatingCity_city_name").val(city.trim());
	 }
	 form.find("input[name=longitude]").val("");
	 form.find("input[name=latitude]").val("");
}
