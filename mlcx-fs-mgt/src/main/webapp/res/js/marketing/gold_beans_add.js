define([],function() {
var goldBeansAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addGoldBeans").click(function(){
				goldBeansAdd.saveGoldBeansSetting();
			});
		},
		saveGoldBeansSetting:function() {
		var form = $("form[name=goldBeansAddFrom]");
		form.ajaxSubmit({
			url : goldBeansAdd.appPath + "/goldbeans/addGoldbeansSetting.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "金豆设置成功", function() {
						closeTab("金豆设置");
						addTab("金豆设置详情",goldBeansAdd.appPath+ "/goldbeans/toAddGoldbeansSetting.do");
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='godlBeansNumAdd']").empty();
				$("span[name='daysAdd']").empty();
				$("span[name='beansMoneyRatioAdd']").empty();
				$("span[name='orderBeansRatioAdd']").empty();
				$("span[name='dedutionMaxAmountAdd']").empty();
				if (form.find("input[name='goldBeansNum']").val()=="") {
					$("span[name='godlBeansNumAdd']").append("<font color='red'>获得金豆个数不能为空！</font>");
					return false;
				}else if(!/^[1-9]\d*?$/.test(form.find("input[name='goldBeansNum']").val())){
	            	$("span[name='godlBeansNumAdd']").append("<font color='red'>格式不正确，只能输入大于0的整数！</font>");
					return false;
	            }
				if (form.find("input[name='days']").val()=="") {
					$("span[name='daysAdd']").append("<font color='red'>获得金豆所需天数不能为空！</font>");
					return false;
				}else if(!/^[1-9]\d*?$/.test(form.find("input[name='days']").val())){
	            	$("span[name='daysAdd']").append("<font color='red'>格式不正确，只能输入大于0的整数！</font>");
					return false;
	            }
				if (form.find("input[name='beansMoneyRatio']").val()=="") {
					$("span[name='beansMoneyRatioAdd']").append("<font color='red'>金豆和人民币的比率值不能为空！</font>");
					return false;
				}else if(!/^[0-9]+(.[0-9]{0,2})?$/.test(form.find("input[name='beansMoneyRatio']").val())){
	            	$("span[name='beansMoneyRatioAdd']").append("<font color='red'>格式不正确，只能输入大于0的整数或小数！</font>");
					return false;
	            }
				if (form.find("input[name='orderBeansRatio']").val()=="") {
					$("span[name='orderBeansRatioAdd']").append("<font color='red'>订单结算金豆抵扣比率值！</font>");
					return false;
				}else if(!/^(0\.(0[1-9]|[1-9]{1,2}|[1-9]0)$)|^1$/.test(form.find("input[name='orderBeansRatio']").val())){
	            	$("span[name='orderBeansRatioAdd']").append("<font color='red'>格式不正确，只能输入大于0且小于等于1的2位小数！</font>");
					return false;
	            }
				if (form.find("input[name='dedutionMaxAmount']").val()=="") {
					$("span[name='dedutionMaxAmountAdd']").append("<font color='red'>抵扣金额封顶值不能为空</font>");
					return false;
				}else if(!/^[0-9]+(.[0-9]{0,2})?$/.test(form.find("input[name='dedutionMaxAmount']").val())){
	            	$("span[name='dedutionMaxAmountAdd']").append("<font color='red'>格式不正确，只能输入大于0的整数或小数！</font>");
					return false;
	            }
			}
	});
}
}
return goldBeansAdd;
})
