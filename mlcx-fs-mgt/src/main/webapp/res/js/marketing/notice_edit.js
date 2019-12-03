define([],function() {
var noticeEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//编辑提交
			$("#editNotice").click(function(){
				noticeEdit.editnotice();
			});
			//编辑页面的关闭
			$("#closeEditNotice").click(function(){
				closeTab("公告编辑");
			});
			//上传图片
			$("#editNoticePicUrlButton").click(function(){
				$("#noticePicUrlEditModal").modal("show");
			});
			//编辑图片回填
			$("#editNoticePicBtn").click(function(){
				var form=$("form[name=noticePicUrlEditForm]");
				var img=form.find("input[name=noticePicUrl]").val();
				if(img!=""){
					var form1=$("form[name=noticeEditFrom]");
					form1.find("input[name=noticePicUrl]").val(img);
					form1.find("#noticePicUrlImg").css("background-image", "url(" + noticeEdit.imgPath + '/' + img + ")");
					$("#noticePicUrlEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
		},
		editnotice:function() {
	var form = $("form[name=noticeEditFrom]");
	form.ajaxSubmit({
		url : noticeEdit.appPath + "/notice/updateNotice.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "公告编辑成功", function() {
					closeTab("公告编辑");
					$("#noticeList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "公告编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='noticeTypeEdit']").empty();
			$("span[name='noticeNameEdit']").empty();
			$("span[name='rankingEdit']").empty();
			$("span[name='noticePicUrlEdit']").empty();
			$("span[name='externalLinkUrl']").empty();
			$("span[name='text1Edit']").empty();
			$("span[name='text2Edit']").empty();
			$("span[name='text3Edit']").empty();
			$("span[name='isAvailableEdit']").empty();
			if (form.find("select[name='noticeType']").val()=="") {
				$("span[name='noticeTypeEdit']").append("<font color='red'>请选择公告类型！</font>");
				return false;
			}
			if (form.find("input[name='noticeName']").val()=="") {
				$("span[name='noticeNameEdit']").append("<font color='red'>请输入公告名称！</font>");
				return false;
			}
			if (form.find("input[name='ranking']").val()=="") {
				$("span[name='rankingEdit']").append("<font color='red'>请输入排序！</font>");
				return false;
			}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
            	$("span[name='rankingEdit']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
				return false;
            }
			if (form.find("input[name='noticePicUrl']").val()=="") {
				$("span[name='noticePicUrlEdit']").append("<font color='red'>请上传公告图片！</font>");
				return false;
			}
//			if (form.find("input[name='externalLinkUrl']").val()=="") {
//				$("span[name='externalLinkUrlEdit']").append("<font color='red'>请输入外部链接！</font>");
//				return false;
//			}
			if (form.find("textarea[name='text1']").val()=="") {
				$("span[name='text1Edit']").append("<font color='red'>请输入公告内容1！</font>");
				return false;
			}
			if (form.find("textarea[name='text2']").val()=="") {
				$("span[name='text2Edit']").append("<font color='red'>请输入公告内容2！</font>");
				return false;
			}
			if (form.find("textarea[name='text3']").val()=="") {
				$("span[name='text3Edit']").append("<font color='red'>请输入公告内容3！</font>");
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
return noticeEdit;
})
