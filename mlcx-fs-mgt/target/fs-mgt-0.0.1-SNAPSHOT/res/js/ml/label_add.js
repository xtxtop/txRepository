define(
[],
function() {
var labelAdd = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//添加提交
		$("#addlabel").click(function(){
			labelAdd.addlabel();
		});
		//返回
		$("#addcloselabel").click(function(){
			closeTab("标签增加");
			$("#labelList").DataTable().ajax.reload(function(){});
		});
	},
	addlabel:function() {
		var form = $("form[name=labelAddForm]");
		form.ajaxSubmit({
			url : labelAdd.appPath + "/label/addlabel.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("标签增加");
						$("#labelList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=labelAddForm]");
				form.find("input[name='labelName']").val();//名称
				
				if(form.find("input[name='labelName']").val()==''){
					spanWarning("labelName_add","请输入标签名称!")
					return false;
				}
			}
		});
	 },
			}
	return labelAdd;
})
