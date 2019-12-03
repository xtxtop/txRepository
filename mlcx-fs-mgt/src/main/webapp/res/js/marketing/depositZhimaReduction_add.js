define([],function() {
var depositZhimaReductionAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addDepositZhimaReduction").click(function(){
				depositZhimaReductionAdd.saveDepositZhimaReduction();
			});
			//新增页面的关闭
			$("#closeAddDepositZhimaReduction").click(function(){
				closeTab("芝麻分减免押金新增");
			});
		},
		saveDepositZhimaReduction:function() {
			var form = $("form[name=depositZhimaReductionAddFrom]");
   		 	form.ajaxSubmit({
				url : depositZhimaReductionAdd.appPath + "/depositZhimaReduction/addDepositZhimaReduction.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("芝麻分减免押金新增");
							$("#depositZhimaReductionList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败,"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='reductionAmountAdd']").empty();
					$("span[name='zhimaScoreAdd']").empty();
					if (form.find("input[name='reductionAmount']").val()=="") {
						$("span[name='reductionAmountAdd']").append("<font color='red'>请输入减免金额！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='reductionAmount']").val())){
		            	$("span[name='reductionAmountAdd']").append("<font color='red'>减免金额只能输入正数！</font>");
						return false;
		            }else if(form.find("input[name='reductionAmount']").val() <= 0){
		            	$("span[name='reductionAmountAdd']").append("<font color='red'>减免金额必须大于0！</font>");
						return false;
		            }
					
					if (form.find("input[name='zhimaScore']").val()=="") {
						$("span[name='zhimaScoreAdd']").append("<font color='red'>请输入芝麻分！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='zhimaScore']").val())){
						$("span[name='zhimaScoreAdd']").append("<font color='red'>芝麻分只能输入正数！</font>");
						return false;
					}else if(form.find("input[name='zhimaScore']").val() <= 0){
						$("span[name='zhimaScoreAdd']").append("<font color='red'>芝麻分必须大于0！</font>");
						return false;
					}
				}
			});
		}
}
return depositZhimaReductionAdd;
})
