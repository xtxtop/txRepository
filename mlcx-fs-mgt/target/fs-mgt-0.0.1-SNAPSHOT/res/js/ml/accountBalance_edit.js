define(
[],
function() {
var accountBalanceEdit = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//编辑提交
		$("#editaccountBalance").click(function(){
			accountBalanceEdit.addaccountBalance();
		});
		//返回
		$("#editcloseaccountBalance").click(function(){
			closeTab("编辑余额");
			$("#accountBalanceList").DataTable().ajax.reload(function(){});
		});
	},
	addaccountBalance:function() {
		var form = $("form[name=accountBalanceEditForm]");
		form.ajaxSubmit({
			url : accountBalanceEdit.appPath + "/accountBalance/editAccountBalance.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("明细编辑");
						$("#accountBalanceList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=accountBalanceEditForm]");
				form.find("input[name='stopBalance']").val();//停车余额
				form.find("input[name='chargingBalance']").val();//充电余额
				
				if(form.find("input[name='stopBalance']").val()==''){
					spanWarning("stopBalance_edit_accountBalance","请输入停车余额!")
				}
				if(form.find("input[name='chargingBalance']").val()==''){
					spanWarning("chargingBalance_edit_accountBalance","请输入充电余额!")
				}
				//判断提交验证
				var edit_accountBalance_mlcx_verify=$("span[id$='edit_accountBalance_mlcx_verify']")
				for(var i=0;i<edit_accountBalance_mlcx_verify.length;i++){
					if(edit_accountBalance_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
			}
		});
	 },
			}
	return accountBalanceEdit;
})
