define(
[],
function() {
var pricePackOrderAddCompany= {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#addPricingPackOrderCompany").click(function(){
			pricePackOrderAddCompany.addPricePackOrder();
		});
		//返回
		$("#closePricingPackOrderCompany").click(function(){
			closeTab("集团新增套餐");
			$("#pricePackOrderList").DataTable().ajax.reload(function(){});
		});
	},
	addPricePackOrder:function() {
		var form = $("form[name=pricePackOrderAddCompanyForm]");
		form.ajaxSubmit({
			url : pricePackOrderAddCompany.appPath + "/company/addPricingPackOrder.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "集团套餐添加成功", function() {
						closeTab("集团新增套餐");
						addTab("套餐订单列表",pricePackOrderAddCompany.appPath+ "/pricingPackOrder/toPricingPackOrderList.do");
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#packageIdAddCompany").empty();
				if (form.find("select[name='packageId']").val()=="") {
					$("#packageIdAddCompany").append("<font color='red' class='formtips onError emaill'>请选择套餐！</font>");
					return false;
				}
				
			}
		});
	 }
	}
	return pricePackOrderAddCompany;
})
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
		var appPath = $("#appPath").val();
		$.ajax({
			type: "post",
			url : appPath+ "/pricingPackage/toPricingPackage.do?packageNo="+selectValue,
			success: function(res){
				var form = $("form[name=pricePackOrderAddCompanyForm]");
				form.find("input[name='packMinute']").val(res.minutes);
				form.find("input[name='packAmount']").val(res.price);
			}
		});
	}else{
		var form = $("form[name=pricePackOrderAddCompanyForm]");
		form.find("input[name='packAmount']").val("");
	}
}
