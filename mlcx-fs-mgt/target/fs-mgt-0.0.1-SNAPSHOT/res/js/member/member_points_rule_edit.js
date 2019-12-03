define(
[],
function() {
var memberPointsRuleEdit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//编辑提交
		$("#editMemberPointsRule").click(function(){
			memberPointsRuleEdit.editMemberPointsRule();
		});
		//返回
		$("#closeMemberPointsRuleEdit").click(function(){
			closeTab("会员积分规则编辑");
			$("#memberPointsRuleList").DataTable().ajax.reload(function(){});
		});
	},
	editMemberPointsRule:function() {
		var form = $("form[name=memberPointsRuleEditForm]");
		form.ajaxSubmit({
			url : memberPointsRuleEdit.appPath + "/memberPointsRule/updateMemberPointsRule.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑会员积分规则成功", function() {
						closeTab("会员积分规则编辑");
						$("#memberPointsRuleList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "会员积分规则编辑失败！");
				}
			},
			beforeSubmit : function(formData,jqForm, options) {// 提交表单前数据验证
				$("span[name=businessTypeEdit]").empty();
				$("span[name=pointsTypeEdit]").empty();
				$("span[name=pointsValueEdit]").empty();
				$("span[name=isDefaultEdit]").empty();
				$("span[name=isAvailableEdit]").empty();
				if (form.find("select[name='businessType']").val()=="") {
					$("span[name=businessTypeEdit]").append("<font color='red' class='formtips onError emaill'>请选择积分业务类型！</font>");
					return false;
				}
				if (form.find("select[name='pointsType']").val()=="") {
					$("span[name=pointsTypeEdit]").append("<font color='red' class='formtips onError emaill'>请选择积分类型！</font>");
					return false;
				}
				if(form.find("input[name='pointsValue']").val()==""){
					$("span[name='pointsValueEdit']").append("<font color='red'>请输入规则所对应的积分值！</font>");
					return false;
				}
				if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='pointsValue']").val())){
					$("span[name='pointsValueEdit']").append("<font color='red'>请输入正整数！</font>");
					return false;
				}
				if (form.find("select[name='isDefault']").val()=="") {
					$("span[name=isDefaultEdit]").append("<font color='red' class='formtips onError emaill'>请选择是否是默认规则！</font>");
					return false;
				}
				if (form.find("select[name='isAvailable']").val()=="") {
					$("span[name='isAvailableEdit']").append("<font color='red' class='formtips onError emaill'>请选择是否可用！</font>");
					return false;
				}
			}
		});
	 }
	}
   return memberPointsRuleEdit;
});

