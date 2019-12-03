define([],function() {
var pricingPackageEdit = {
		appPath : getPath("app"),
		init : function() {
			
			//关闭计费规则详情编辑页
			$("#closeeditpricingPackage").click(function(){
				closeTab("套餐产品编辑");
            });
			//关闭计费规则详情页
			$("#closeViewpricingPackage").click(function(){
				closeTab("套餐产品详情");
            });
			//费规则详情编辑提交
			$("#editpricingPackage").click(function(){
				pricingPackageEdit.updatecheckPlan();
			});
			//审核页面的关闭
			$("#closeCencorpricingPackage").click(function(){
				closeTab("套餐产品审核");
			});
			//审核提交
			$("#addCencorpricingPackage").click(function(){
				pricingPackageEdit.updateCencor();
			});
			
			$("input[name='isLimitTimes']").change(function(){
				var isLimitTimes = $(this).val()
				var parent = $("#buyTimesDivEidt");
				var inputEle = parent.children('input');
				if(isLimitTimes==1){
					inputEle.css("cursor","inherit").removeAttr('readonly');
				}else{
					inputEle.css("cursor","not-allowed").attr('readonly','');
					
				}
			});
		},
 updatecheckPlan:function() {
	 debugger
	var form = $("form[name=pricingPackageEditFrom]");
	form.ajaxSubmit({
		url : pricingPackageEdit.appPath + "/pricingPackage/toPricingPackageCencor.do",
		type : "post",
		success : function(res) {
			debugger
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
					closeTab("套餐产品编辑");
					$("#pricingPackageList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			var packageKind = form.find("input[name='packageKind']").val();
			$("span[name='packageName']").empty();
			$("span[name='price']").empty();
			if(packageKind==1){
				$("span[name='minutes']").empty();
				$("span[name='deductionCeiling']").empty();
			}else{
				$("span[name='packAmount']").empty();
			}
			var isLimitTimes = form.find("input[name='isLimitTimes']:checked").val();
			if(isLimitTimes==1){
				$("span[name='buyTimesDivEidt']").empty();
			}
			$("span[name='isLimitTimesEdit']").empty();
			if (form.find("input[name='packageName']").val()=="") {
				$("span[name='packageName']").append("<font color='red'>请输入套餐名称！</font>");
				return false;
			}
			$("span[name='availableDays']").empty();
			if (form.find("input[name='packageName']").val()=="") {
				$("span[name='packageName']").append("<font color='red'>请输入套餐名称！</font>");
				return false;
			}
			if (form.find("input[name='price']").val()=="") {
				$("span[name='price']").append("<font color='red'>请输入销售价！</font>");
				return false;
			}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
            	$("span[name='price']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
				return false;
            }
			if(isLimitTimes==undefined){
				$("span[name='isLimitTimesEdit']").append("<font color='red'>请选择是否限制购买次数！</font>");
				return false;
			}else if(isLimitTimes==1){
				var reg = /^[1-9]\d*$/;
				if(!reg.test(form.find("input[name='buyTimes']").val())){
					$("span[name='buyTimesEdit']").append("<font color='red'>有效天数只能输入正整数！</font>");
					return false;
				}
			}
			
			if(packageKind==1){
				if (form.find("input[name='minutes']").val()=="") {
					$("span[name='minutes']").append("<font color='red'>请输入时长！</font>");
					return false;
				}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='minutes']").val())){
					$("span[name='minutes']").append("<font color='red'>时长只能输入正整数或是输入格式不对！</font>");
					return false;
				}
				if (form.find("input[name='deductionCeiling']").val()=="") {
					$("span[name='deductionCeiling']").append("<font color='red'>请输入套餐每日抵扣上限！</font>");
					return false;
				}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='deductionCeiling']").val())){
					$("span[name='deductionCeiling']").append("<font color='red'>只能输入正整数或是输入格式不对！</font>");
					return false;
				}
				if (form.find("input[name='availableDays']").val()=="") {
					$("span[name='availableDays']").append("<font color='red'>请输入有效天数！</font>");
					return false;
				}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='availableDays']").val())){
	            	$("span[name='availableDays']").append("<font color='red'>有效天数只能输入正整数或是输入格式不对！</font>");
					return false;
	            }
			}else{
				if (form.find("input[name='packAmount']").val()=="") {
					$("span[name='packAmount']").append("<font color='red'>请输入套餐金额！</font>");
					return false;
				}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='packAmount']").val())){
					$("span[name='packAmount']").append("<font color='red'>套餐金额只能输入正整数或是输入格式不对！</font>");
					return false;
				}
			}
			
		}
	});
},
updateCencor:function() {
	var form = $("form[name=pricingPackageCensorEditFrom]");
	form.ajaxSubmit({
		url : pricingPackageEdit.appPath + "/pricingPackage/toUpdatePricingPackage.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功", function() {
					closeTab("套餐产品审核");
					$("#pricingPackageList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			if (form.find("textarea[name='cencorMemo']").val()=="") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核备注！");
				return false;
			}
		}
	});
}
}
return pricingPackageEdit;
})
