define(
[],
function() {
var companyAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveAddCompany").click(function(){
			companyAdd.addDevice();
		});
		//返回
		$("#closeAddCompany").click(function(){
			closeTab("新增集团");
			$("#companyList").DataTable().ajax.reload(function(){});
		});
	},
	addDevice:function() {
		var form = $("form[name=companyAddForm]");
		form.ajaxSubmit({
			url : companyAdd.appPath + "/company/addCompany.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增集团");
						$("#companyList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#companyNameAdd").empty();
				$("#cityIdAdd").empty();
				$("#contactPersonAdd").empty();
				$("#contactTelAdd").empty();
				$("#emailAdd").empty();
				var tel=/^0[0-9]{2,3}[0-9]{8}/;
			    var phone=/^1\d{10}$/;
			    var emailReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/; 
				if ($.trim(form.find("input[name='companyName']").val())=="") {
					$("#companyNameAdd").append("<font color='red' class='formtips onError emaill'>请输入公司名称！</font>");
					return false;
				}
				if (form.find("select[name='cityId']").val()=="") {
					$("#cityIdAdd").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='contactPerson']").val())=="") {
					$("#contactPersonAdd").append("<font color='red' class='formtips onError emaill'>请输入联系人！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='contactTel']").val())=="") {
					$("#contactTelAdd").append("<font color='red' class='formtips onError emaill'>请输入联系人电话！</font>");
					return false;
				}
				var contactTel=$.trim(form.find("input[name='contactTel']").val());
				if(tel.test(contactTel)||phone.test(contactTel)){
					
				}else{
					$("#contactTelAdd").append("<font color='red' class='formtips onError emaill'>联系人电话格式不正确！<如：029693598618或者13800001380></font>");
					return false;
				}
				if ($.trim(form.find("input[name='email']").val())=="") {
					$("#emailAdd").append("<font color='red' class='formtips onError emaill'>请输入联系人邮箱！</font>");
					return false;
				}else{
					if(!emailReg.test($.trim(form.find("input[name='email']").val()))){
						$("#emailAdd").append("<font color='red' class='formtips onError emaill'>联系人邮箱格式不正确！</font>");
						return false;
					}
				}
			}
		});
	 }
	}
	return companyAdd;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
