define([],function() {
var pricingRuleDayEdit = {
		appPath : getPath("app"),
		init : function() {
			
			//关闭计费规则详情编辑页
			$("#closeEditPricingRule").click(function(){
				closeTab("计费规则调整");
            });
			//费规则详情编辑提交
			$("#EditPricingRule").click(function(){
				pricingRuleDayEdit.updatecheckPlan();
			});
			//选择车辆品牌后匹配相应的车型
			var form = $("form[name=pricingRuleDayEditForm]");
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: pricingRuleDayEdit.appPath+"/pricingRuleDay/getCarModleByBrand.do",
		             data: {brandId:brandId},
		             success: function(res){
		            	 var dataItems = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='carModelId']").html("");
		            		 var option = "";
		            		 for(var i=0;i<dataItems.length;i++){
		            			 option+="<option  value='"+dataItems[i].carModelId+"'> "+dataItems[i].carModelName+" </option>";
		              		 }
		            		 form.find("select[name='carModelId']").html(option);
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		                 }
		             }
				});
			});
		},
		updatePricingRule:function(){
			var form = $("form[name=pricingRuleDayEditForm]");
			form.ajaxSubmit({
				url : pricingRuleDayEdit.appPath + "/pricingRuleDay/updatePricingRuleDay.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调整成功", function() {
							closeTab("日租计费规则调整");
							$("#pricingRuleDayList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调整失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='ruleNameEdit']").empty();
					$("span[name='cityIdEdit']").empty();
					$("span[name='carBrandEdit']").empty();
					$("span[name='carModelEdit']").empty();
					$("span[name='workingDayEdit']").empty();
					$("span[name='weekendEdit']").empty();
					$("span[name='holidayEdit']").empty();
					$("span[name='priorityEdit']").empty();
					$("span[name='availableTime1Edit']").empty();
					$("span[name='availableTime2Edit']").empty();
					
					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameEdit']").append("<font color='red'>请输入名称！</font>");
						return false;
					}
					if (form.find("select[name='cityId']").val()=="") {
						$("span[name='cityIdEdit']").append("<font color='red'>请选择城市！</font>");
						return false;
					}
					if (form.find("select[name='carBrandId']").val()=="") {
						$("span[name='carBrandEdit']").append("<font color='red'>请选择车辆品牌！</font>");
						return false;
					}
					if (form.find("select[name='carModelId']").val()==""||form.find("select[name='carModelId']").val()==null) {
						$("span[name='carModelEdit']").append("<font color='red'>没有选择车辆型号或当前品牌未设置型号！</font>");
						return false;
					}
					/*if (form.find("input[name='workingDay']").val()=="") {
						$("span[name='workingDayEdit']").append("<font color='red'>请输入工作日计费金额！</font>");
						return false;
					}*/
					
					if (form.find("input[name='weekend']").val()=="") {
						$("span[name='weekendEdit']").append("<font color='red'>请输入周末计费金额！</font>");
						return false;
					}
					/*if (form.find("input[name='holiday']").val()=="") {
						$("span[name='holidayEdit']").append("<font color='red'>请输入节假日计费金额！</font>");
						return false;
					}
					if (form.find("input[name='workingDay']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='workingDay']").val())) {
						$("span[name='workingDayEdit']").append("<font color='red'>工作日计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='weekend']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='weekend']").val())) {
						$("span[name='weekendEdit']").append("<font color='red'>周末计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='holiday']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='holiday']").val())) {
						$("span[name='holidayEdit']").append("<font color='red'>节假日计费金额输入有误(正数或0)！</font>");
						return false;
					}
					
					
					if (form.find("input[name='priority']").val()=="") {
						$("span[name='priorityEdit']").append("<font color='red'>请输入优先级！</font>");
						return false;
					}
					
					if (form.find("input[name='priority']").val()!=""&&!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='priority']").val())) {
						$("span[name='priorityEdit']").append("<font color='red'>优先级请输入正整数！</font>");
						return false;
					}
					if (form.find("input[name='availableTime1']").val()=="") {
						$("span[name='availableTime1Edit']").append("<font color='red'>有效期至不为空！</font>");
						return false;
					}
					if (form.find("input[name='availableTime2']").val()=="") {
						$("span[name='availableTime2Edit']").append("<font color='red'>有效期至不为空！</font>");
						return false;
					}
					if (form.find("input[name='availableTime1']").val()!=""&&form.find("input[name='availableTime2']").val()!="") {
						if(new Date(form.find("input[name='availableTime1']").val())>new Date(form.find("input[name='availableTime2']").val())){
							$("span[name='availableTime1Edit']").append("<font color='red'>有效期开始时间不能大于结束时间！</font>");
							return false;
						}
					}*/
				}
			});
		},
 updatecheckPlan:function() {
		var form = $("form[name=pricingRuleDayEditForm]");
		var carBrand=form.find("select[name=carBrandId]").val();
		var carModel=form.find("select[name=carModelId]").val();
		var cityId=form.find("select[name=cityId]").val();
		var companyId=form.find("select[name=companyId]").val();
		if (form.find("input[name='ruleName']").val()=="") {
			$("span[name='ruleNameEdit']").html("<font color='red'>请输入名称！</font>");
			return false;
		}else{
			$("span[name='ruleNameEdit']").html("");
		}
		if (form.find("select[name='cityId']").val()=="") {
			$("span[name='cityIdEdit']").html("<font color='red'>请选择城市！</font>");
			return false;
		}else{
			$("span[name='cityIdEdit']").html("");
		}
		if (carBrand=="") {
				$("span[name='carBrandEdit']").html("<font color='red'>请选择车辆品牌！</font>");
				return false;
		}else{
			$("span[name='carBrandEdit']").html("");
		}
		if (carModel==""||carModel==null) {
				$("span[name='carModelEdit']").html("<font color='red'>没有选择车辆型号或当前品牌未设置型号！</font>");
				return false;
		}else{
			$("span[name='carModelEdit']").html("");
		}
		var companyIdVo = form.find("input[name=companyIdVo]").val()
		var carBrandIdVo = form.find("input[name=carBrandIdVo]").val()
		var carModelIdVo = form.find("input[name=carModelIdVo]").val()
		var cityIdVo = form.find("input[name=cityIdVo]").val()
		if(companyId==companyIdVo&&carBrand==carBrandIdVo&&carModel==carModelIdVo&&cityId==cityIdVo){
			pricingRuleDayEdit.updatePricingRule();
		}else{
			 $.ajax({
					url:pricingRuleDayEdit.appPath+"/pricingRuleDay/uniquePricingRuleDay.do",//验证计费规则的唯一性
					type:"post",
					data:{carBrand:carBrand,carModel:carModel,companyId:companyId,cityId:cityId},
					dataType:"json",
					success:function(res){ 
						if(res == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的计费规则已经存在，请重新添加！");
						}else{
							pricingRuleDayEdit.updatePricingRule();
						}
					}
			 });
 }
}
}
return pricingRuleDayEdit;
})
