define([],function() {
var orderShareSettingEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#editOrderShareSetting").click(function(){
				orderShareSettingEdit.saveOrderShareSetting();
			});
			//新增页面的关闭
			$("#closeOrderShareSettingEdit").click(function(){
				closeTab("编辑订单分享页配置");
			});
			//上传图片
			$("#orderSharePicUrlEditButton").click(function(){
				$("#orderShareSettingEditModal").modal("show");
			});
			//新增图片回填
			$("#orderShareSettingEditPicBtn").click(function(){
				var form=$("form[name=orderShareSettingEditPicForm]");
				var img=form.find("input[name=orderShareSettingEditUrl1]").val();
				if(img!=""){
					var form1=$("form[name=orderShareSettingEditFrom]");
					form1.find("input[name=orderSharePicUrl]").val(img);
					form1.find("#orderSharePicUrlImg").css("background-image", "url(" + orderShareSettingEdit.imgPath + '/' + img + ")");
					$("#orderShareSettingEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
			//上传icon
			$("#orderShareIconUrlEditButton").click(function(){
				$("#orderShareSettingEditIconModal").modal("show");
			});
			//新增icon回填
			$("#orderShareSettingEditIconBtn").click(function(){
				var form=$("form[name=orderShareSettingEditIconForm]");
				var img=form.find("input[name=orderShareSettingEditIconUrl1]").val();
				if(img!=""){
					var form1=$("form[name=orderShareSettingEditFrom]");
					form1.find("input[name=orderShareIconUrl]").val(img);
					form1.find("#orderShareIconUrlImg").css("background-image", "url(" + orderShareSettingEdit.imgPath + '/' + img + ")");
					$("#orderShareSettingEditIconModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
		},
		saveOrderShareSetting:function() {
			var form = $("form[name=orderShareSettingEditFrom]");
			form.ajaxSubmit({
				url : orderShareSettingEdit.appPath + "/orderShareSetting/updateOrderShareSetting.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
							closeTab("编辑订单分享页配置");
							$("#orderShareSettingList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='orderShareNameEdit']").empty();
					$("span[name='orderShareMemoEdit']").empty();
					$("span[name='linkUrlEdit']").empty();
					$("span[name='orderSharePicUrlEdit']").empty();
					$("span[name='orderShareIconUrlEdit']").empty();
					$("span[name='orderShareContentEdit']").empty();
					if (form.find("input[name='orderShareName']").val()=="") {
						$("span[name='orderShareNameEdit']").append("<font color='red'>请输入订单分享名称！</font>");
						return false;
					}
					if (form.find("input[name='orderShareMemo']").val()=="") {
						$("span[name='orderShareMemoEdit']").append("<font color='red'>请输入分享链接的内容！</font>");
						return false;
					}
					if (form.find("input[name='linkUrl']").val()=="") {
						$("span[name='linkUrlEdit']").append("<font color='red'>请输入订单分享页链接地址！</font>");
						return false;
					}
					if (form.find("input[name='orderSharePicUrl']").val()=="") {
						$("span[name='orderSharePicUrlEdit']").append("<font color='red'>请上传订单分享页图片！</font>");
						return false;
					}
					if (form.find("input[name='orderShareIconUrl']").val()=="") {
						$("span[name='orderShareIconUrlEdit']").append("<font color='red'>请上传订单分享icon图片！</font>");
						return false;
					}
					if (form.find("textarea[name='orderShareContent']").val()=="") {
						$("span[name='orderShareContentEdit']").append("<font color='red'>请输入订单分享页内容！</font>");
						return false;
					}
				}
			});
		}
}
return orderShareSettingEdit;
})
