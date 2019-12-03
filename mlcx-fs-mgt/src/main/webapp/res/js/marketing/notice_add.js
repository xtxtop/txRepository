define([],function() {
var noticeAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addnotice").click(function(){
				noticeAdd.savenotice();
			});
			//新增页面的关闭
			$("#closeAddnotice").click(function(){
				closeTab("新增活动");
			});
			//上传图片
			$("#addnoticePicUrlButton").click(function(){
				$("#noticePicUrlAddModal").modal("show");
			});
			//新增图片回填
			$("#addnoticePicBtn").click(function(){
				var form=$("form[name=noticePicUrlAddForm]");
				var img=form.find("input[name=noticePicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=noticeAddFrom]");
					form1.find("input[name=noticePicUrl]").val(img);
					form1.find("#noticePicUrlImg").css("background-image", "url(" + noticeAdd.imgPath + '/' + img + ")");
					$("#noticePicUrlAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
		},
		savenotice:function() {
	var form = $("form[name=noticeAddFrom]");
	form.ajaxSubmit({
		url : noticeAdd.appPath + "/notice/addNotice.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动添加成功", function() {
					closeTab("新增活动");
					$("#noticeList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动添加失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='noticeTypeAdd']").empty();
			$("span[name='noticeNameAdd']").empty();
			$("span[name='rankingAdd']").empty();
			$("span[name='noticePicUrlAdd']").empty();
			$("span[name='externalLinkUrlAdd']").empty();
			$("span[name='text1Add']").empty();
			$("span[name='text2Add']").empty();
			$("span[name='text3Add']").empty();
			$("span[name='isAvailableAdd']").empty();
			if (form.find("select[name='noticeType']").val()=="") {
				$("span[name='noticeTypeAdd']").append("<font color='red'>请选择活动类型！</font>");
				return false;
			}
			if (form.find("input[name='noticeName']").val()=="") {
				$("span[name='noticeNameAdd']").append("<font color='red'>请输入活动名称！</font>");
				return false;
			}
			if (form.find("input[name='ranking']").val()=="") {
				$("span[name='rankingAdd']").append("<font color='red'>请输入排序！</font>");
				return false;
			}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
            	$("span[name='rankingAdd']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
				return false;
            }
			if (form.find("input[name='noticePicUrl']").val()=="") {
				$("span[name='noticePicUrlAdd']").append("<font color='red'>请上传活动图片！</font>");
				return false;
			}
//			if (form.find("input[name='externalLinkUrl']").val()=="") {
//				$("span[name='externalLinkUrlAdd']").append("<font color='red'>请输入外部链接！</font>");
//				return false;
//			}
			if (form.find("textarea[name='text1']").val()=="") {
				$("span[name='text1Add']").append("<font color='red'>请输入活动内容1！</font>");
				return false;
			}
			if (form.find("textarea[name='text2']").val()=="") {
				$("span[name='text2Add']").append("<font color='red'>请输入活动内容2！</font>");
				return false;
			}
			if (form.find("textarea[name='text3']").val()=="") {
				$("span[name='text3Add']").append("<font color='red'>请输入活动内容3！</font>");
				return false;
			}
			if (form.find("select[name='isAvailable']").val()=="") {
				$("span[name='isAvailableAdd']").append("<font color='red'>请选择可用状态！</font>");
				return false;
			}
		}
	});
}
}
return noticeAdd;
})
