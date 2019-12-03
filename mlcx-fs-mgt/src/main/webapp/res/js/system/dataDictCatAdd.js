define(
[],
function() {
var dataDictCatAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveAddDataDictCat").click(function(){
			dataDictCatAdd.addDataDictCat();
		});
		//返回
		$("#closeAddDataDictCat").click(function(){
			closeTab("数据字典分类新增");
			$("#dataDictList").DataTable().ajax.reload(function(){});
		});
	},
	addDataDictCat:function() {
		var form = $("form[name=dataDictCatAddForm]");
		form.ajaxSubmit({
			url : dataDictCatAdd.appPath + "/dataDictCat/addDataDictCat.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("数据字典分类新增");
						$("#dataDictList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#dataDictCatCodeAdd").empty();
				$("#dataDictCatNameAdd").empty();
				if (form.find("input[name='dataDictCatCode']").val()=="") {
					$("#dataDictCatCodeAdd").append("<font color='red' class='formtips onError emaill'>请输入数据字典分类代码！</font>");
					return false;
				}
				if (form.find("input[name='dataDictCatName']").val()=="") {
					$("#dataDictCatNameAdd").append("<font color='red' class='formtips onError emaill'>请输入数据字典分类名称！</font>");
					return false;
				}
			}
		});
	 }
	}
	return dataDictCatAdd;
})
