define([],function() {	
var parkingSpaceEdit = {
		appPath : getPath("app"),
		beforePath:getPath("before"),
		init : function() {
			$("input.butcheck").each(function(){//给所有的input绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#supportedServices").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			//编辑提交
			$("#saveParking").click(function(){
				parkingSpaceEdit.updatepark();
			});
			//关闭车辆详情编辑页
			$("#closeParking").click(function(){
				closeTab("车位详情");
            });
			//关闭新增页
			$("#closeParkingSpace").click(function(){
				closeTab("车位编辑");
            });
			var form = $("form[name=parkingEditForm]");
			form.find("select[name='hasCharger']").change(function(){
        		if(form.find("select[name='hasCharger']").val()==1){
        			$("#chargerNoXSEdit").attr('style','display: block');
        		}else if(form.find("select[name='hasCharger']").val()==0){
        			$("#chargerNoXSEdit").attr('style','display: none');
        		}
        	});
		},
 updatepark:function() {
	var form = $("form[name=parkingEditForm]");
	form.ajaxSubmit({
		url : parkingSpaceEdit.appPath + "/parkingSpace/updateParkingSpace.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
					closeTab("车位编辑");
					$("#parkingSpaceList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！"+msg);
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
		}
	});
},
savepark:function() {
	var form = $("form[name=parkingAddForm]");
	form.ajaxSubmit({
		url : parkingSpaceEdit.appPath + "/parkingSpace/updateParkingSpace.do",
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
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='chargerNo']").empty();
			if (form.find("input[name='chargerNo']").val()=="") {
				$("span[name='chargerNo']").append("<font color='red'>请输入电桩编号！</font>");
				return false;
			}
		}
	});
}
}
return parkingSpaceEdit;
})
