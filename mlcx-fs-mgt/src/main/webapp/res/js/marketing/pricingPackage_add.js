define([],function() {
var pricingPackageAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addpricingPackage").click(function(){
				pricingPackageAdd.savecheckPlan();
			});
			//新增页面的关闭
			$("#closeaddpricingPackage").click(function(){
				closeTab("套餐产品新增");
			});
//			$("input[name='packageKind']").click(function(){
//				var form = $("form[name=pricingPackageAddFrom]");
//				var packageKind = form.find("input[name='packageKind']:checked").val();
//				if(packageKind==1){
//					$("#timeDiv").show();
//					$("#moneyDiv").hide();
//					$("#availableDaysDiv").show();
//					form.find("input[name='availableDaysDiv']").val("");
//				}else{
//					$("#availableDaysDiv").hide();
//					$("#timeDiv").hide();
//					$("#moneyDiv").show();
//				}
//			});
			$("input[name='isLimitTimes']").click(function(){
				var form = $("form[name=pricingPackageAddFrom]");
				var isLimitTimes = form.find("input[name='isLimitTimes']:checked").val();
				if(isLimitTimes==1){
					$("#buyTimesDiv").show();
				}else{
					$("#buyTimesDiv").hide();
					form.find("input[name='buyTimes']").val("");
				}
			});
			
			
			$("select[name='packageType']").click(function(){
					var form = $("form[name=pricingPackageAddFrom]");
					var packageType = form.find("select[name='packageType']").val();
					 var price ="";
					 var packAmount = "";
					if(packageType=="5"){
						$(".car_red").html("");
				         price+="<input class='form-control val' name='price'  value='0' readonly='readonly'/> "; 
				         $(".car_packAmount").html("");
				         packAmount+="<input class='form-control val' name='packAmount'  value='0' readonly='readonly'/> "; 
					}else{
						 price+="<input class='form-control val'  value='' name='price'/> ";
						 packAmount+="<input class='form-control val'  value='' name='packAmount' /> "; 
					}
						form.find(".car_red").html(price);
						form.find(".car_packAmount").html(packAmount);
				});
			
			
		},
		savecheckPlan:function() {
			var form = $("form[name=pricingPackageAddFrom]");
   		 	form.ajaxSubmit({
				url : pricingPackageAdd.appPath + "/pricingPackage/addPricingPackage.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("套餐产品新增");
							$("#pricingPackageList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败,"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var packageKind = form.find("input[name='packageKind']:checked").val();
					$("span[name='packageName']").empty();
					$("span[name='price']").empty();
					if(packageKind==1){
						$("span[name='minutes']").empty();
						$("span[name='deductionCeiling']").empty();
						$("span[name='availableDays']").empty();
					}else{
						$("span[name='packAmount']").empty();
					}
					var isLimitTimes = form.find("input[name='isLimitTimes']:checked").val();
					if(isLimitTimes==1){
						$("span[name='buyTimes']").empty();
					}
					$("span[name='isLimitTimes']").empty();
					if (form.find("input[name='packageName']").val()=="") {
						$("span[name='packageName']").append("<font color='red'>请输入套餐名称！</font>");
						return false;
					}
					if(form.find("select[name='packageType']").val()!="5"){
						if (form.find("input[name='price']").val()=="") {
							$("span[name='price']").append("<font color='red'>请输入销售价！</font>");
							return false;
						}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
			            	$("span[name='price']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
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
						if(form.find("select[name='packageType']").val()!="5"){
							if (form.find("input[name='packAmount']").val()=="") {
								$("span[name='packAmount']").append("<font color='red'>请输入套餐金额！</font>");
								return false;
							}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='packAmount']").val())){
								$("span[name='packAmount']").append("<font color='red'>套餐金额只能输入正整数或是输入格式不对！</font>");
								return false;
							}
						}
						
					}
					
					if(isLimitTimes==undefined){
						$("span[name='isLimitTimes']").append("<font color='red'>请选择是否限制购买次数！</font>");
						return false;
					}else if(isLimitTimes==1){
						var reg = /^[1-9]\d*$/;
						if(!reg.test(form.find("input[name='buyTimes']").val())){
							$("span[name='buyTimes']").append("<font color='red'>有效天数只能输入正整数！</font>");
							return false;
						}
					}
				}
			});
		}
}
return pricingPackageAdd;
})
