define(
[],
function() {
var matchingAdd = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//添加提交
		$("#addmatching").click(function(){
			matchingAdd.addmatching();
		});
		//返回
		$("#addclosematching").click(function(){
			closeTab("配套服务增加");
			$("#matchingList").DataTable().ajax.reload(function(){});
		});
		//服务上传图片
        $("#addmatchingPicUrlPhotoButton").click(function(){
            $("#matchingPicUrlPhotoAddModal").modal("show");
        });
        //服务充电站图片的回填
        $("#addmatchingPicUrlPhotoBtn").click(function(){
            var form=$("form[name='matchingPicUrlphotoForm']");
            var img=form.find("input[name=matchingPicUrl]").val();
            if(img!=""){
                var form1=$("form[name=matchingAddForm]");
                form1.find("input[name=matchingPicUrl]").val(img);
                form1.find("#matchingPicUrlImg").css("background-image","url("+matchingAdd.imgPath+'/'+img+")");
                $("#matchingPicUrlPhotoAddModal").modal("hide");
            }else{
                bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
            }

        });
	},
	addmatching:function() {
		var form = $("form[name=matchingAddForm]");
		form.ajaxSubmit({
			url : matchingAdd.appPath + "/matching/addMatching.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("配套服务增加");
						$("#matchingList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=matchingAddForm]");
				form.find("input[name='matchingName']").val();//名称
				form.find("input[name='matchingPicUrl']").val();//图片
				
				if(form.find("input[name='matchingName']").val()==''){
					spanWarning("matchingName_add_matching","请输入配套服务名称!")
				}
				if(form.find("input[name='matchingPicUrl']").val()==''){
					spanWarning("matchingPicUrl_add_matching","请选择图片!")
				}
				//判断提交验证
				var add_matching_mlcx_verify=$("span[id$='add_matching_mlcx_verify']")
				for(var i=0;i<add_matching_mlcx_verify.length;i++){
					if(add_matching_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
			}
		});
	 },
			}
	return matchingAdd;
})
