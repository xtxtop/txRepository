define(
[],
function() {
var dataDictCatEdit = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveEditDataDictCat").click(function(){
			dataDictCatEdit.editDataDictCat();
		});
		//返回
		$("#closeEditDataDictCat").click(function(){
			closeTab("新增数据字典分类");
			$("#dataDictList").DataTable().ajax.reload(function(){});
		});
	},
	editDataDictCat:function() {
		var form = $("form[name=dataDictCatEditForm]");
		form.ajaxSubmit({
			url : dataDictCatEdit.appPath + "/dataDictCat/editDataDictCat.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("修改数据字典分类");
						$("#dataDictList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#dataDictCatCodeEdit").empty();
				$("#dataDictCatNameEdit").empty();
				if (form.find("input[name='dataDictCatCode']").val()=="") {
					$("#dataDictCatCodeEdit").append("<font color='red' class='formtips onError emaill'>请输入数据字典分类代码！</font>");
					return false;
				}
				if (form.find("input[name='dataDictCatName']").val()=="") {
					$("#dataDictCatNameEdit").append("<font color='red' class='formtips onError emaill'>请输入数据字典分类名称！</font>");
					return false;
				}
			}
		});
	 }
	}
	return dataDictCatEdit;
})
