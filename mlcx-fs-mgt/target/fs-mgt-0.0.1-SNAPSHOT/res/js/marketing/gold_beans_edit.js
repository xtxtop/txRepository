define([],function() {
var goldBeansEdit = {
		appPath : getPath("app"),
		init : function() {
			//编辑提交
			$("#editGoldBeans").click(function(){
				goldBeansEdit.editGoldBeansSetting();
			});
			//返回金豆设置详情页面
			$("#returnGoldBeansView").click(function(){
				closeTab("金豆设置详情");
				addTab("金豆设置",goldBeansEdit.appPath+ "/goldbeans/toAddGoldbeansSetting.do");
			});
			//删除提交
			$("#deleteGoldBeans").click(function(){
				goldBeansEdit.deleteGoldBeansSetting();
			});
			//编辑页面
			$("#toEditGoldBeans").click(function(){
				var form = $("form[name=goldBeansViewFrom]");
				var goldBeansId = form.find("input[name='goldBeansId']").val();
				closeTab("金豆设置");
				addTab("金豆设置详情",goldBeansEdit.appPath+ "/goldbeans/toUpdateGoldbeansSetting.do?goldBeansId="+goldBeansId);
			});
			
		},
		editGoldBeansSetting:function() {
			var form = $("form[name=goldBeansEditFrom]");
			form.ajaxSubmit({
				url : goldBeansEdit.appPath + "/goldbeans/updateGoldbeansSetting.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "金豆设置编辑成功", function() {
							closeTab("金豆设置详情");
							addTab("金豆设置",goldBeansEdit.appPath+ "/goldbeans/toAddGoldbeansSetting.do");
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "金豆设置编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='goldBeansNumEdit']").empty();
					$("span[name='daysEdit']").empty();
					$("span[name='beansMoneyRatioEdit']").empty();
					$("span[name='orderBeansRatioEdit']").empty();
					$("span[name='dedutionMaxAmountEdit']").empty();
					if (form.find("input[name='goldBeansNum']").val()=="") {
						$("span[name='goldBeansNumEdit']").append("<font color='red'>获得金豆个数不能为空！</font>");
						return false;
					}else if(!/^[1-9]\d*?$/.test(form.find("input[name='goldBeansNum']").val())){
		            	$("span[name='goldBeansNumEdit']").append("<font color='red'>格式不正确，只能输入大于0的整数！</font>");
						return false;
		            }
					if (form.find("input[name='days']").val()=="") {
						$("span[name='daysEdit']").append("<font color='red'>获得金豆所需天数不能为空！</font>");
						return false;
					}else if(!/^[1-9]\d*?$/.test(form.find("input[name='days']").val())){
		            	$("span[name='daysEdit']").append("<font color='red'>格式不正确，只能输入大于0的整数！</font>");
						return false;
		            }
					if (form.find("input[name='beansMoneyRatio']").val()=="") {
						$("span[name='beansMoneyRatioEdit']").append("<font color='red'>金豆和人民币的比率值不能为空！</font>");
						return false;
					}else if(!/^[0-9]+(.[0-9]{0,2})?$/.test(form.find("input[name='beansMoneyRatio']").val())){
		            	$("span[name='beansMoneyRatioEdit']").append("<font color='red'>格式不正确，只能输入大于0的整数或小数！</font>");
						return false;
		            }
					if (form.find("input[name='orderBeansRatio']").val()=="") {
						$("span[name='orderBeansRatioEdit']").append("<font color='red'>订单结算金豆抵扣比率值！</font>");
						return false;
					}else if(!/^(0\.(0[1-9]|[1-9]{1,2}|[1-9]0)$)|^1$/.test(form.find("input[name='orderBeansRatio']").val())){
		            	$("span[name='orderBeansRatioEdit']").append("<font color='red'>格式不正确，只能输入大于0且小于等于1的2位小数！</font>");
						return false;
		            }
					if (form.find("input[name='dedutionMaxAmount']").val()=="") {
						$("span[name='dedutionMaxAmountEdit']").append("<font color='red'>抵扣金额封顶值不能为空</font>");
						return false;
					}else if(!/^[0-9]+(.[0-9]{0,2})?$/.test(form.find("input[name='dedutionMaxAmount']").val())){
		            	$("span[name='dedutionMaxAmountEdit']").append("<font color='red'>格式不正确，只能输入大于0的整数或小数！</font>");
						return false;
		            }
				}
			})
		},
		deleteGoldBeansSetting:function(){
			bootbox.confirm("你确定要删除金豆设置吗？",function(result) {
				if(result){
					var form = $("form[name=goldBeansViewFrom]");
					var goldBeansId = form.find("input[name='goldBeansId']").val();
					$.ajax({
						url : goldBeansEdit.appPath+ "/goldbeans/deleteGoldbeansSetting.do?goldBeansId="+goldBeansId,
						success: function(res){
							if(res.code=="1"){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "金豆设置删除成功", function() {
									addTab("金豆设置",goldBeansEdit.appPath+ "/goldbeans/toAddGoldbeansSetting.do");
								});
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "金豆设置删除失败！");
							}
						}
					});
				}
			})
		}
}
return goldBeansEdit;
})
