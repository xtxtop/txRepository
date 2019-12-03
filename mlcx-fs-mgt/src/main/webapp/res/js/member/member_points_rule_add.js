define(
[],
function() {
var memberPointsRuleAdd = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//编辑提交
		$("#saveMemberPointsRule").click(function(){
			memberPointsRuleAdd.addMemberPointsRule();
		});
		//返回
		$("#closeMemberPointsRule").click(function(){
			closeTab("会员积分规则添加");
			$("#memberPointsRuleList").DataTable().ajax.reload(function(){});
		});
	},
	addMemberPointsRule:function() {
		var form = $("form[name=memberPointsRuleAddForm]");
		form.ajaxSubmit({
			url : memberPointsRuleAdd.appPath + "/memberPointsRule/addMemberPointsRule.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加会员积分规则成功", function() {
						closeTab("会员积分规则添加");
						$("#memberPointsRuleList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
					closeTab("会员积分规则添加");
					$("#memberPointsRuleList").DataTable().ajax.reload(function(){});
				}
			},
			beforeSubmit : function(formData,jqForm, options) {// 提交表单前数据验证
				$("span[name=businessTypeAdd]").empty();
				$("span[name=pointsTypeAdd]").empty();
				$("span[name=pointsValueAdd]").empty();
				$("span[name=isDefaultAdd]").empty();
				$("span[name=isAvailableAdd]").empty();
				if (form.find("select[name='businessType']").val()=="") {
					$("span[name=businessTypeAdd]").append("<font color='red' class='formtips onError emaill'>请选择积分业务类型！</font>");
					return false;
				}
				if (form.find("select[name='pointsType']").val()=="") {
					$("span[name=pointsTypeAdd]").append("<font color='red' class='formtips onError emaill'>请选择积分类型！</font>");
					return false;
				}
				if(form.find("input[name='pointsValue']").val()==""){
					$("span[name='pointsValueAdd']").append("<font color='red'>请输入规则所对应的积分值！</font>");
					return false;
				}
				if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='pointsValue']").val())){
					$("span[name='pointsValueAdd']").append("<font color='red'>请输入正整数！</font>");
					return false;
				}
				if (form.find("select[name='isDefault']").val()=="") {
					$("span[name=isDefaultAdd]").append("<font color='red' class='formtips onError emaill'>请选择是否是默认规则！</font>");
					return false;
				}
				if (form.find("select[name='isAvailable']").val()=="") {
					$("span[name='isAvailableAdd']").append("<font color='red' class='formtips onError emaill'>请选择是否可用！</font>");
					return false;
				}
			}
		});
	 }
	}
   return memberPointsRuleAdd;
});

