define([],function() {
var checkResultEdit = {
		appPath : getPath("app"),
		init : function() {
			//巡检计划显示场站，实际时间等
			$("#checkPlanNos").blur(function(){
				if($(this).val().length>0){
					checkResultEdit.getcheckPlan($(this).val());
				}
			});
			//关闭车辆详情编辑页
			$("#closeViewcheckResult").click(function(){
				closeTabBT("巡检结果详情");
            });
			//编辑提交
			$("#editcheckResult").click(function(){
				checkResultEdit.updatecheckPlan();
			});
			//关闭车辆详情编辑页
			$("#closeEditcheckResult").click(function(){
				closeTabBT("巡检结果编辑");
            });
		},
		getcheckPlan:function(id) {
			$.ajax({
				 type: "post",
	             url: checkResultEdit.appPath + "/checkPlan/toCheckPlanBean.do",
	             data: {checkPlanNo:$.trim(id)},
	             success: function(data){
	            	 var form = $("form[name=checkResultAddForm]");
	                      if(data!=""){
	                    	  form.find("input[name=planDate]").val(moment(data.planDate).format('YYYY-MM-DD'));
	                    	  form.find("input[name=actualStartTime]").val(moment(data.actualStartTime).format('YYYY-MM-DD HH:mm:ss'));
	                    	  form.find("input[name=actualEndTime]").val(moment(data.actualEndTime).format('YYYY-MM-DD HH:mm:ss'));
	                    	  form.find("input[name=parkNo]").val(data.parkNo);
	                    	  form.find("input[name=parkName]").val(data.parkName);
	                    	  form.find("input[name=checkItemId]").val(data.checkItemId);
	                    	  form.find("input[name=checkItem]").val(data.checkItem);
//	                    	  checkResultEdit.savecheckPlan();
	                      }else{
	                    	  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "巡检计划不存在，请重新核对巡检计划号后进行增加操作");
	                      }  
	             }
			});
		},
 updatecheckPlan:function() {
	var form = $("form[name=checkResultEditForm]");
	form.ajaxSubmit({
		url : checkResultEdit.appPath + "/checkResult/updateCheckResult.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
					closeTab("巡检结果编辑");
					$("#checkResultList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='deviceNo']").empty();
			$("span[name='abnormalBrief']").empty();
			$("span[name='abnormalDetail']").empty();
			if (form.find("input[name='deviceNo']").val()=="") {
				$("span[name='deviceNo']").append("<font color='red'>请输入设备编号！</font>");
				return false;
			}
			if (form.find("input[name='abnormalBrief']").val()=="") {
				$("span[name='abnormalBrief']").append("<font color='red'>请输入异常简述！</font>");
				return false;
			}
			if (form.find("textarea[name='abnormalDetail']").val()=="") {
				$("span[name='abnormalDetail']").append("<font color='red'>请输入异常详述！</font>");
				return false;
			}
		}
	});
},
savecheckPlan:function() {
	var form = $("form[name=checkResultAddForm]");
	form.ajaxSubmit({
		url : checkResultEdit.appPath + "/checkResult/updateCheckResult.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
					closeTab("新增巡检结果");
					$("#checkResultList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败，"+msg);
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='checkPlanNo']").empty();
			$("span[name='deviceNo']").empty();
			$("span[name='abnormalBrief']").empty();
			$("span[name='abnormalDetail']").empty();
			if (form.find("input[name='checkPlanNo']").val()=="") {
				$("span[name='checkPlanNo']").append("<font color='red'>请输入巡检计划号！</font>");
				return false;
			}
			if (form.find("input[name='deviceNo']").val()=="") {
				$("span[name='deviceNo']").append("<font color='red'>请输入设备编号！</font>");
				return false;
			}
			if (form.find("input[name='abnormalBrief']").val()=="") {
				$("span[name='abnormalBrief']").append("<font color='red'>请输入异常简述！</font>");
				return false;
			}
			if (form.find("textarea[name='abnormalDetail']").val()=="") {
				$("span[name='abnormalDetail']").append("<font color='red'>请输入异常详述！</font>");
				return false;
			}
		}
	});
}
}
return checkResultEdit;
})
