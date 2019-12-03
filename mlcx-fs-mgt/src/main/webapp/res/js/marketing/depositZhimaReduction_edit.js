define([],function() {
var depositZhimaReductionEdit = {
		appPath : getPath("app"),
		init : function() {
			//编辑提交
			$("#editDepositZhimaReduction").click(function(){
				depositZhimaReductionEdit.saveDepositZhimaReduction();
			});
			//编辑页面的关闭
			$("#closeEditDepositZhimaReduction").click(function(){
				closeTab("芝麻分减免押金编辑");
			});
		},
		saveDepositZhimaReduction:function() {
			var form = $("form[name=depositZhimaReductionEditFrom]");
   		 	form.ajaxSubmit({
				url : depositZhimaReductionEdit.appPath + "/depositZhimaReduction/updateDepositZhimaReduction.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改成功", function() {
							closeTab("芝麻分减免押金编辑");
							$("#depositZhimaReductionList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改失败,"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='reductionAmountEdit']").empty();
					$("span[name='zhimaScoreEdit']").empty();
					if (form.find("input[name='reductionAmount']").val()=="") {
						$("span[name='reductionAmountEdit']").append("<font color='red'>请输入减免金额！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='reductionAmount']").val())){
		            	$("span[name='reductionAmountEdit']").append("<font color='red'>格式不正确，不能有空格，减免金额只能输入整数或小数！</font>");
						return false;
		            }
					
					if (form.find("input[name='zhimaScore']").val()=="") {
						$("span[name='zhimaScoreEdit']").append("<font color='red'>请输入芝麻分！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='zhimaScore']").val())){
						$("span[name='zhimaScoreEdit']").append("<font color='red'>芝麻分只能输入正整数！</font>");
						return false;
					}
				}
			});
		}
}
return depositZhimaReductionEdit;
})
