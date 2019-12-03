define([],function() {
var advertEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			var form = $("form[name=advertEditFrom]");
			//编辑提交
			$("#editAdvert").click(function(){
				advertEdit.editAdvert();
			});
			var jumpType=$("#jumpTypeEditId").val();
			if(jumpType=="1"){
				$(".nativeUrlTypeEdit").show();
				$(".LinkUrlEdit").hide();
				$(".memoEdit").hide();
			}else if(jumpType=="2"){
				$(".nativeUrlTypeEdit").hide();
				$(".LinkUrlEdit").show();
				$(".memoEdit").hide();
			}else if(jumpType=="3"){
				$(".nativeUrlTypeEdit").hide();
				$(".LinkUrlEdit").hide();
				$(".memoEdit").show();
			}
			//编辑页面的关闭
			$("#closeEditAdvert").click(function(){
				closeTab("活动编辑");
			});
			//上传图片
			$("#editAdvertPicUrlButton").click(function(){
				$("#advertPicUrlEditModal").modal("show");
			});
			//编辑图片回填
			$("#editAdvertPicBtn").click(function(){
				var form=$("form[name=advertPicUrlEditForm]");
				var img=form.find("input[name=advertPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=advertEditFrom]");
					form1.find("input[name=advertPicUrl]").val(img);
					form1.find("#advertPicUrlImg").css("background-image", "url(" + advertEdit.imgPath + '/' + img + ")");
					$("#advertPicUrlEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
			//点击活动广告区分跳转类型 弹出对应的跳转链接地址
			form.find("select[name=jumpType]").change(function(){
			var jumpType=$("#jumpTypeEditId").val();
				if(jumpType=="1"){
					$(".nativeUrlTypeEdit").show();
					$(".LinkUrlEdit").hide();
					$(".memoEdit").hide();
				}else if(jumpType=="2"){
					$(".nativeUrlTypeEdit").hide();
					$(".LinkUrlEdit").show();
					$(".memoEdit").hide();
				}else if(jumpType=="3"){
					$(".nativeUrlTypeEdit").hide();
					$(".LinkUrlEdit").hide();
					$(".memoEdit").show();
				}
				
			});
			
			
			
		},
		editAdvert:function() {
	var form = $("form[name=advertEditFrom]");
	form.ajaxSubmit({
		url : advertEdit.appPath + "/advert/updateAdvert.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动编辑成功", function() {
					closeTab("活动编辑");
					$("#advertList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='advertTypeEdit']").empty();
			$("span[name='advertNameEdit']").empty();
			$("span[name='rankingEdit']").empty();
			$("span[name='advertPicUrlEdit']").empty();
			$("span[name='externalLinkUrl']").empty();
			$("span[name='text1Edit']").empty();
			$("span[name='jumpTypeEdit']").empty();
			$("span[name='nativeUrlTypeEdit']").empty();
			$("span[name='isAvailableEdit']").empty();
			$("span[name='advertMemberTypeEdit']").empty();
			
			if (form.find("select[name='advertType']").val()=="") {
				$("span[name='advertTypeEdit']").append("<font color='red'>请选择活动类型！</font>");
				return false;
			}
			if (form.find("input[name='advertName']").val()=="") {
				$("span[name='advertNameEdit']").append("<font color='red'>请输入活动名称！</font>");
				return false;
			}
			
			if (form.find("select[name='jumpType']").val()=="") {
				$("span[name='jumpTypeEdit']").append("<font color='red'>请选择跳转类型！</font>");
				return false;
			}
			
			if(form.find("select[name='jumpType']").val()=="1"){
				if (form.find("select[name='nativeUrlType']").val()=="") {
					$("span[name='nativeUrlTypeEdit']").append("<font color='red'>请选择内部链接跳转路径！</font>");
					return false;
				}
			}
			if(form.find("select[name='jumpType']").val()=="2"){
				if (form.find("input[name='externalLinkUrl']").val()=="") {
					$("span[name='externalLinkUrlEdit']").append("<font color='red'>请输入链接跳转路径！</font>");
					return false;
				}
			}
			
			
			
			if (form.find("select[name='advertMemberType']").val()=="") {
				$("span[name='advertMemberTypeEdit']").append("<font color='red'>请选择展示类型！</font>");
				return false;
			}
			if (form.find("input[name='ranking']").val()=="") {
				$("span[name='rankingEdit']").append("<font color='red'>请输入排序！</font>");
				return false;
			}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
            	$("span[name='rankingEdit']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
				return false;
            }
			if (form.find("input[name='advertPicUrl']").val()=="") {
				$("span[name='advertPicUrlEdit']").append("<font color='red'>请上传活动图片！</font>");
				return false;
			}
//			if (form.find("input[name='externalLinkUrl']").val()=="") {
//				$("span[name='externalLinkUrlEdit']").append("<font color='red'>请输入外部链接！</font>");
//				return false;
//			}
//			if (form.find("textarea[name='text1']").val()=="") {
//				$("span[name='text1Edit']").append("<font color='red'>请输入活动内容1！</font>");
//				return false;
//			}
			if (form.find("textarea[name='text2']").val()=="") {
				$("span[name='text2Edit']").append("<font color='red'>请输入活动内容2！</font>");
				return false;
			}
			if (form.find("textarea[name='text3']").val()=="") {
				$("span[name='text3Edit']").append("<font color='red'>请输入活动内容3！</font>");
				return false;
			}
			if (form.find("select[name='isAvailable']").val()=="") {
				$("span[name='isAvailableEdit']").append("<font color='red'>请选择可用状态！</font>");
				return false;
			}
		}
	});
}
}
return advertEdit;
})
