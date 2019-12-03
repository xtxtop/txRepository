define(
[],
function() {
var billingSchemeAdd = {
    appPath : getPath("app"),
    imgPath:getPath("img"),

	init : function() {
		 $('input').attr('autocomplete','off');//input框清楚缓存
		//添加提交
		$("#addbillingScheme").click(function(){
			billingSchemeAdd.addbillingScheme();
		});
		//返回
		$("#addclosebillingScheme").click(function(){
			closeTab("计费方案增加");
			$("#billingSchemeList").DataTable().ajax.reload(function(){});
		});
	},
	addbillingScheme:function() {
		var form = $("form[name=billingSchemeAddForm]");
		form.ajaxSubmit({
			url : billingSchemeAdd.appPath + "/billingScheme/addBillingScheme.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("计费方案增加");
						$("#billingSchemeList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var form = $("form[name=billingSchemeAddForm]");
				$("span[id$='add_billingScheme_mlcx_verify']").html("");//清空所有span提示
				form.find("input[name='schemeName']").val();//名称
				form.find("input[name='schemeVersions']").val();//版本
				form.find("input[name='effectiveDate']").val();//生效时间
				form.find("input[name='invalidDate']").val();//失效时间
				form.find("input[name='advanceFrozenMoney']").val();//预冻结金额
				form.find("input[name='minFrozenMoney']").val();//最小冻结姐
				//form.find("input[name='timeNum']").val();//时间段
				form.find("input[name='opintPrice']").val();//尖时间电价
				form.find("input[name='peakPrice']").val();//峰
				form.find("input[name='flatPrice']").val();//平
				form.find("input[name='valleyPrice']").val();//谷
				form.find("input[name='orderedRate']").val();//预约费率
				form.find("input[name='serviceCharge']").val();//服务费
				form.find("input[name='warmingPrice']").val();//告警金额
				var rents=/^[0-9]{0,5}?$/;//验证数字
				if(form.find("input[name='schemeName']").val()==''){
					spanWarning("schemeName_add_billingScheme","请输入计费方案名称!")
				}
				if(form.find("input[name='schemeVersions']").val()==''){
					spanWarning("schemeVersions_add_billingScheme","请输入版本号!")
				}
				if(form.find("input[name='effectiveDate']").val()==''){
					spanWarning("effectiveDate_add_billingScheme","请选择生效时间!")
				}
				if(form.find("input[name='invalidDate']").val()==''){
					spanWarning("invalidDate_add_billingScheme","请选择失效时间!")
				}
				if(form.find("input[name='advanceFrozenMoney']").val()==''){
					spanWarning("advanceFrozenMoney_add_billingScheme","请输入预冻结金额!")
				}else{
					verifyMoney_add("advanceFrozenMoney","请输入正确的预冻结金额(金额格式只能是大于0数字)!")
				}
				if(form.find("input[name='minFrozenMoney']").val()==''){
					spanWarning("minFrozenMoney_add_billingScheme","请输入最小冻结金额!")
				}else{
					verifyMoney_add("minFrozenMoney","请输入正确的最小冻结金额(金额格式只能是大于0数字)!")
				}
				/*if(form.find("input[name='timeNum']").val()==''){
					spanWarning("timeNum_add_billingScheme","请选择时间段!")
				}*/
				if(form.find("input[name='opintPrice']").val()==''){
					spanWarning("opintPrice_add_billingScheme","请输入尖时间电价!")
				}else{
					verifyMoney_add("opintPrice","请输入正确的尖时间电价(电价格式只能是大于0数字)!")
				}
				if(form.find("input[name='peakPrice']").val()==''){
					spanWarning("peakPrice_add_billingScheme","请输入峰时间电价!")
				}else{
					verifyMoney_add("peakPrice","请输入正确的峰时间电价(电价格式只能是大于0数字)!")
				}
				if(form.find("input[name='flatPrice']").val()==''){
					spanWarning("flatPrice_add_billingScheme","请输入平时间电价!")
				}else{
					verifyMoney_add("flatPrice","请输入正确的平时间电价(电价格式只能是大于0数字)!")
				}
				if(form.find("input[name='valleyPrice']").val()==''){
					spanWarning("valleyPrice_add_billingScheme","请输入谷时间电价!")
				}else{
					verifyMoney_add("valleyPrice","请输入正确的谷时间电价(电价格式只能是大于0数字)!")
				}
				if(form.find("input[name='orderedRate']").val()==''){
					spanWarning("orderedRate_add_billingScheme","请输入预约费率!")
				}else {
					verifyMoney_add("orderedRate","请输入正确的预约费率(费率格式只能是大于0数字)!")
				}
				if(form.find("input[name='orderedRate']").val()>100){
					spanWarning("orderedRate_add_billingScheme","预约费率只能取值于0到100之间!")
				}
				if(form.find("input[name='serviceCharge']").val()==''){
					spanWarning("serviceCharge_add_billingScheme","请输入服务费!")
				}else{
					verifyMoney_add("serviceCharge","请输入正确的服务费(金额格式只能是大于0数字)!")
				}
				if(form.find("input[name='warmingPrice']").val()==''){
					spanWarning("warmingPrice_add_billingScheme","请输入告警金额!")
				}else{
					verifyMoney_add("warmingPrice","请输入正确的告警金额(金额格式只能是大于0数字)!")
				}
				//判断提交验证
				var add_billingScheme_mlcx_verify=$("span[id$='add_billingScheme_mlcx_verify']")
				for(var i=0;i<add_billingScheme_mlcx_verify.length;i++){
					if(add_billingScheme_mlcx_verify[i].innerHTML!=''){
						return false;
					}
				}
				return true;
			}
		});
	 },
			}
	return billingSchemeAdd;
})
//验证金额
function verifyMoney_add(a,b){
	var form = $("form[name=billingSchemeAddForm]");
	var rent=/^[0-9]*(\.[0-9]{0,5})?$/;//验证电费
	if(!rent.test(form.find("input[name='"+a+"']").val())){
		spanWarning(a+"_add_billingScheme",b)
	}
}
