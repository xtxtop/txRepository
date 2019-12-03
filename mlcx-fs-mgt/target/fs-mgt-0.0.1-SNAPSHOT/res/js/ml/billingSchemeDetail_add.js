define(
[],
function() {
var billingSchemeDetailAdd = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//添加提交
		$("#addbillingSchemeDetail").click(function(){
			billingSchemeDetailAdd.addbillingSchemeDetail();
		});
		//返回
		$("#addclosebillingSchemeDetail").click(function(){
			closeTab("明细增加");
			$("#billingSchemeDetailList").DataTable().ajax.reload(function(){});
		});
	},
	addbillingSchemeDetail:function() {
		var form = $("form[name=billingSchemeDetailAddForm]");
		form.ajaxSubmit({
			url : billingSchemeDetailAdd.appPath + "/detail/addBillingSchemeDetail.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("明细增加");
						$("#billingSchemeDetailList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=billingSchemeDetailAddForm]");
				form.find("select[name='schemeNo']").val();//计费方案
				form.find("input[name='serialNumber']").val();//排序
				form.find("input[name='timeQuantum']").val();//开始时间
				
				if(form.find("select[name='schemeNo']").val()==''){
					spanWarning("schemeNo_add_billingSchemeDetail","请选择计费方案!")
				}
				if(form.find("input[name='serialNumber']").val()==''){
					spanWarning("serialNumber_add_billingSchemeDetail","请输入排序!")
				}
				if(form.find("input[name='timeQuantum']").val()==''){
					spanWarning("timeQuantum_add_billingSchemeDetail","请选择时间段!")
				}
				//判断提交验证
				var add_billingSchemeDetail_mlcx_verify=$("span[id$='add_billingSchemeDetail_mlcx_verify']")
				for(var i=0;i<add_billingSchemeDetail_mlcx_verify.length;i++){
					if(add_billingSchemeDetail_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
			}
		});
	 },
			}
	return billingSchemeDetailAdd;
})
