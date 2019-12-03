define([],function() {
var orderShareSettingAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addOrderShareSetting").click(function(){
				orderShareSettingAdd.saveOrderShareSetting();
			});
			//新增页面的关闭
			$("#closeOrderShareSettingAdd").click(function(){
				closeTab("新增订单分享页配置");
			});
			//上传图片
			$("#orderSharePicUrlAddButton").click(function(){
				$("#orderShareSettingAddModal").modal("show");
			});
			//新增图片回填
			$("#orderShareSettingAddPicBtn").click(function(){
				var form=$("form[name=orderShareSettingAddPicForm]");
				var img=form.find("input[name=orderShareSettingAddUrl1]").val();
				if(img!=""){
					var form1=$("form[name=orderShareSettingAddFrom]");
					form1.find("input[name=orderSharePicUrl]").val(img);
					form1.find("#orderSharePicUrlImg").css("background-image", "url(" + orderShareSettingAdd.imgPath + '/' + img + ")");
					$("#orderShareSettingAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			//上传icon
			$("#orderShareIconUrlAddButton").click(function(){
				$("#orderShareSettingAddIconModal").modal("show");
			});
			//新增icon回填
			$("#orderShareSettingAddIconBtn").click(function(){
				var form=$("form[name=orderShareSettingAddIconForm]");
				var img=form.find("input[name=orderShareSettingAddIconUrl1]").val();
				if(img!=""){
					var form1=$("form[name=orderShareSettingAddFrom]");
					form1.find("input[name=orderShareIconUrl]").val(img);
					form1.find("#orderShareIconUrlImg").css("background-image", "url(" + orderShareSettingAdd.imgPath + '/' + img + ")");
					$("#orderShareSettingAddIconModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传icon！");
				}
			});
		},
		saveOrderShareSetting:function() {
			var form = $("form[name=orderShareSettingAddFrom]");
			form.ajaxSubmit({
				url : orderShareSettingAdd.appPath + "/orderShareSetting/addOrderShareSetting.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
							closeTab("新增订单分享页配置");
							$("#orderShareSettingList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败,"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='orderShareNameAdd']").empty();
					$("span[name='orderShareMemoAdd']").empty();
					$("span[name='linkUrlAdd']").empty();
					$("span[name='orderSharePicUrlAdd']").empty();
					$("span[name='orderShareIconUrlAdd']").empty();
					$("span[name='orderShareContentAdd']").empty();
					if (form.find("input[name='orderShareName']").val()=="") {
						$("span[name='orderShareNameAdd']").append("<font color='red'>请输入订单分享名称！</font>");
						return false;
					}
					if (form.find("input[name='orderShareMemo']").val()=="") {
						$("span[name='orderShareMemoAdd']").append("<font color='red'>请输入分享链接的内容！</font>");
						return false;
					}
					if (form.find("input[name='linkUrl']").val()=="") {
						$("span[name='linkUrlAdd']").append("<font color='red'>请输入订单分享页链接地址！</font>");
						return false;
					}
					if (form.find("input[name='orderSharePicUrl']").val()=="") {
						$("span[name='orderSharePicUrlAdd']").append("<font color='red'>请上传订单分享页图片！</font>");
						return false;
					}
					if (form.find("input[name='orderShareIconUrl']").val()=="") {
						$("span[name='orderShareIconUrlAdd']").append("<font color='red'>请上传订单分享icon图片！</font>");
						return false;
					}
					if (form.find("textarea[name='orderShareContent']").val()=="") {
						$("span[name='orderShareContentAdd']").append("<font color='red'>请输入订单分享页内容！</font>");
						return false;
					}
				}
			});
		}
}
return orderShareSettingAdd;
})
