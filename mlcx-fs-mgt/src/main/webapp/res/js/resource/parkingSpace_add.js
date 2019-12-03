define([],function() {	
var parkingSpaceAdd = {
		appPath : getPath("app"),
		init : function() {
			$("input.butcheck").each(function(){//给所有的input绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#supportedServices").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			
			//城市改变
			$("#cityId").on("change",function(){
				var cityId = $(this).children('option:selected').val(); 
				$.ajax({
					 type: "post",
		             url: parkingSpaceAdd.appPath+"/park/getCityPark.do",
		             data: {cityId:cityId},
		             dataType: "json",
		             success: function(data){
		            	 if(data.code="1"){
		            		 var parks = data.data;
		            		 if(parks.length == 0){
		            			 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前城市下无场站、请添加场站！");
		            		 }
		            		 var parkNode = $("#parkNode");
		            		 parkNode.html("");
		            		 var item = "";
		            		 $.each(parks,function(i,val){
		            			 item += "<option value="+val.parkNo+">"+val.parkName+"</option>";
		            		 });
		            		 parkNode.html(item);
		                 }
		             }
				});
			});
			
			//新增提交
			$("#addParking").click(function(){
				parkingSpaceAdd.savepark();
			});
			//新增页面的关闭
			$("#addcloseParkingSpace").click(function(){
				closeTab("车位新增");
			});
			var form = $("form[name=parkingAddForm]");
			form.find("select[name='hasCharger']").change(function(){
        		if(form.find("select[name='hasCharger']").val()==1){
        			$("#chargerNoXS").attr('style','display: block');
        		}else if(form.find("select[name='hasCharger']").val()==0){
        			$("#chargerNoXS").attr('style','display: none');
        		}
        	});
		},
savepark:function() {
	var form = $("form[name=parkingAddForm]");
	form.ajaxSubmit({
		url : parkingSpaceAdd.appPath + "/parkingSpace/updateParkingSpace.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
					closeTab("新增车位");
					$("#parkingSpaceList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！"+msg);
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='chargerNo']").empty();
			if(form.find("select[name=hasCharger]").val()==1){
				if (form.find("input[name='chargerNo']").val()=="") {
					$("span[name='chargerNo']").append("<font color='red'>请输入电桩编号！</font>");
					return false;
				}
			}
			var temp = $("#parkNode").children('option:selected').val();
			if(null == temp){
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请核对场站信息！");
				return false;
			}
		}
	});
}
}
return parkingSpaceAdd;
})
