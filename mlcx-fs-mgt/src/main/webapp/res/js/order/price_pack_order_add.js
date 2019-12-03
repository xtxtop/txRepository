define(
[],
function() {
var pricePackOrderAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#addPricingPackOrder").click(function(){
			pricePackOrderAdd.addPricePackOrder();
		});
		//返回
		$("#closePricingPackOrder").click(function(){
			closeTab("新增套餐");
			$("#pricePackOrderList").DataTable().ajax.reload(function(){});
		});
	},
	addPricePackOrder:function() {
		var form = $("form[name=pricePackOrderAddForm]");
		form.ajaxSubmit({
			url : pricePackOrderAdd.appPath + "/pricingPackOrder/addPricingPackOrder.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增套餐");
						addTab("套餐订单列表",pricePackOrderAdd.appPath+ "/pricingPackOrder/toPricingPackOrderList.do");
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#packageIdAdd").empty();
				if (form.find("select[name='packageId']").val()=="") {
					$("#packageIdAdd").append("<font color='red' class='formtips onError emaill'>请选择套餐！</font>");
					return false;
				}
				if (form.find("input[name='vailableTime2']").val()=="") {
					$("#vailableTime2Add").append("<font color='red' class='formtips onError emaill'>请设置套餐有效期！</font>");
					return false;
				}
				
			}
		});
	 }
	}
	return pricePackOrderAdd;
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
				var form = $("form[name=pricePackOrderAddForm]");
				form.find("input[name='packMinute']").val(res.minutes);
				form.find("input[name='packAmount']").val(res.packAmount);
				form.find("input[name='packageName']").val(res.packageName);
			}
		});
	}else{
		var form = $("form[name=pricePackOrderAddForm]");
		form.find("input[name='packAmount']").val("");
	}
}
