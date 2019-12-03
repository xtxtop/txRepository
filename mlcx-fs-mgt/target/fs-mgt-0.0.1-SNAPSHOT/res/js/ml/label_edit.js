define(
[],
function() {
var labelEdit = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//添加提交
		$("#Editlabel").click(function(){
			labelEdit.Editlabel();
		});
		//返回
		$("#Editcloselabel").click(function(){
			closeTab("编辑标签");
			$("#labelList").DataTable().ajax.reload(function(){});
		});
	},
	Editlabel:function() {
		var form = $("form[name=labelEditForm]");
		form.ajaxSubmit({
			url : labelEdit.appPath + "/label/editlabel.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑标签");
						$("#labelList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				  var form = $("form[name=labelEditForm]");
				form.find("input[name='labelName']").val();//名称
				if(form.find("input[name='labelName']").val()==''){
					spanWarning("labelName_edit","请输入标签名称!")
					return false;
				}
			}
		});
	 },
	}
	return labelEdit;
})
