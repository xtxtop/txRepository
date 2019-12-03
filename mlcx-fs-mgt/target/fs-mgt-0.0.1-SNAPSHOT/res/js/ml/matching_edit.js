define(
[],
function() {
var matchingEdit = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//添加提交
		$("#Editmatching").click(function(){
			matchingEdit.Editmatching();
		});
		//返回
		$("#Editclosematching").click(function(){
			closeTab("编辑配套服务");
			$("#matchingList").DataTable().ajax.reload(function(){});
		});
		//服务上传图片
        $("#editmatchingPicUrlPhotoButton").click(function(){
            $("#matchingPicUrlPhotoEditModal").modal("show");
        });
        //服务充电站图片的回填
        $("#editmatchingPicUrlPhotoBtn").click(function(){
            var form=$("form[name='matchingPicUrlEditphotoForm']");
            var img=form.find("input[name=matchingPicUrlEdit]").val();
            if(img!=""){
                var form1=$("form[name=matchingEditForm]");
                form1.find("input[name=matchingPicUrl]").val(img);
                form1.find("#matchingPicUrlEditImg").css("background-image","url("+matchingEdit.imgPath+'/'+img+")");
                $("#matchingPicUrlPhotoEditModal").modal("hide");
            }else{
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
            }

        });
	},
	Editmatching:function() {
		var form = $("form[name=matchingEditForm]");
		form.ajaxSubmit({
			url : matchingEdit.appPath + "/matching/editMatching.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑配套服务");
						$("#matchingList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				  var form = $("form[name=matchingEditForm]");
				form.find("input[name='matchingName']").val();//名称
				if(form.find("input[name='matchingName']").val()==''){
					spanWarning("matchingName_edit_matching","请输入配套服务名称!")
				}
				if(form.find("input[name='matchingPicUrl']").val()==''){
					spanWarning("matchingPicUrl_edit_matching","请选择图片!")
				}
				//判断提交验证
				var edit_matching_mlcx_verify=$("span[id$='edit_matching_mlcx_verify']")
				for(var i=0;i<edit_matching_mlcx_verify.length;i++){
					if(edit_matching_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
			}
		});
	 },
	}
	return matchingEdit;
})
