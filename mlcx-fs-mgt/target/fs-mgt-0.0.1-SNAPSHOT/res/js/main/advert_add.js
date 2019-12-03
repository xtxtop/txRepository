define([],function() {
var advertAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addAdvert").click(function(){
				advertAdd.saveAdvert();
			});
			//新增页面的关闭
			$("#closeAddAdvert").click(function(){
				closeTab("新增活动");
			});
			//上传图片
			$("#addAdvertPicUrlButton").click(function(){
				$("#advertPicUrlAddModal").modal("show");
			});
			//新增图片回填
			$("#addAdvertPicBtn").click(function(){
				var form=$("form[name=advertPicUrlAddForm]");
				var img=form.find("input[name=advertPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=advertAddFrom]");
					form1.find("input[name=advertPicUrl]").val(img);
					form1.find("img[name=advertPicUrlImg]").attr("src",advertAdd.imgPath+"/"+img);
					$("#advertPicUrlAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
		},
		saveAdvert:function() {
	var form = $("form[name=advertAddFrom]");
	form.ajaxSubmit({
		url : advertAdd.appPath + "/advert/addAdvert.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动添加成功", function() {
					closeTab("新增活动");
					$("#advertList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动添加失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='advertTypeAdd']").empty();
			$("span[name='advertNameAdd']").empty();
			$("span[name='rankingAdd']").empty();
			$("span[name='advertPicUrlAdd']").empty();
			//$("span[name='linkUrlAdd']").empty();
			$("span[name='text1Add']").empty();
			$("span[name='text2Add']").empty();
			$("span[name='text3Add']").empty();
			$("span[name='isAvailableAdd']").empty();
			if (form.find("select[name='advertType']").val()=="") {
				$("span[name='advertTypeAdd']").append("<font color='red'>请选择活动类型！</font>");
				return false;
			}
			if (form.find("input[name='advertName']").val()=="") {
				$("span[name='advertNameAdd']").append("<font color='red'>请输入活动名称！</font>");
				return false;
			}
			if (form.find("input[name='ranking']").val()=="") {
				$("span[name='rankingAdd']").append("<font color='red'>请输入排序！</font>");
				return false;
			}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
            	$("span[name='rankingAdd']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
				return false;
            }
			if (form.find("input[name='advertPicUrl']").val()=="") {
				$("span[name='advertPicUrlAdd']").append("<font color='red'>请上传活动图片！</font>");
				return false;
			}
//			if (form.find("input[name='linkUrl']").val()=="") {
//				$("span[name='linkUrlAdd']").append("<font color='red'>请输入跳转链接！</font>");
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
return advertAdd;
})
