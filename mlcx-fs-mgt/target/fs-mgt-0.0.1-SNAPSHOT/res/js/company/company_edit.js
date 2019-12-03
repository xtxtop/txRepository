define(
[],
function() {
var companyEdit = {
	appPath : getPath("app"),
	init : function() {
		//编辑提交
		$("#saveEditCompany").click(function(){
			companyEdit.editCompnay();
		});
		//返回
		$("#closeEditCompany").click(function(){
			closeTabBT("集团编辑");
			$("#companyList").DataTable().ajax.reload(function(){});
		});
		//详情页返回
		$("#closeCompanyView").click(function(){
			closeTab("集团详情");
//			$("#companyList").DataTable().ajax.reload(function(){});
		});
	},
	editCompnay:function() {
		var form = $("form[name=companyEditForm]");
		form.ajaxSubmit({
			url : companyEdit.appPath + "/company/updateCompany.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("集团编辑");
						$("#companyList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#companyNameEdit").empty();
				$("#cityIdEdit").empty();
				$("#contactPersonEdit").empty();
				$("#emailEdit").empty();
				var tel=/^0[0-9]{2,3}[0-9]{8}/;
			    var phone=/^1\d{10}$/;
			    var emailReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/; 
				if ($.trim(form.find("input[name='companyName']").val())=="") {
					$("#companyNameEdit").append("<font color='red' class='formtips onError emaill'>请输入公司名称！</font>");
					return false;
				}
				if (form.find("select[name='cityId']").val()=="") {
					$("#cityIdEdit").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='contactPerson']").val())=="") {
					$("#contactPersonEdit").append("<font color='red' class='formtips onError emaill'>请输入联系人！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='email']").val())=="") {
					$("#emailEdit").append("<font color='red' class='formtips onError emaill'>请输入联系人邮箱！</font>");
					return false;
				}else{
					if(!emailReg.test($.trim(form.find("input[name='email']").val()))){
						$("#emailEdit").append("<font color='red' class='formtips onError emaill'>联系人邮箱格式不正确！</font>");
						return false;
					}
				}
			}
		});
	 }
	}
	return companyEdit;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
