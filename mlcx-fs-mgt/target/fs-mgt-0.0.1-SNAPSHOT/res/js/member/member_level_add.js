define(
[],
function() {
var memberLevelAdd = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//编辑提交
		$("#saveMemberLevel").click(function(){
			memberLevelAdd.addMemberLevel();
		});
		//返回
		$("#closeMemberLevel").click(function(){
			closeTab("会员等级添加");
			$("#memberLevelList").DataTable().ajax.reload(function(){});
		});
	},
	addMemberLevel:function() {
		var form = $("form[name=memberLevelAddForm]");
		$("span[name=levelNameAdd]").empty();
		$("span[name=levelDiscountAdd]").empty();
		$("span[name=upgradePointAdd]").empty();
		$("span[name=memoAdd]").empty();
		$("span[name=isAvailableAdd]").empty();
		if (form.find("input[name='levelName']").val()=="") {
			$("span[name=levelNameAdd]").append("<font color='red' class='formtips onError emaill'>请输入等级名称！</font>");
			return false;
		}
		if (form.find("input[name='levelDiscount']").val()=="") {
			$("span[name=levelDiscountAdd]").append("<font color='red' class='formtips onError emaill'>请输入等级折扣率！</font>");
			return false;
		}
		if(form.find("input[name='levelDiscount']").val()!=""&&form.find("input[name='levelDiscount']").val()!=null&&!/^([01](\.0+)?|0\.[0-9]{0,2})$/.test(form.find("input[name='levelDiscount']").val())){
			$("span[name='levelDiscountAdd']").append("<font color='red'>折扣需输入0~1之间且最多小数位为2的数！</font>");
			return false;
		}
		if (form.find("input[name='upgradePoint']").val()=="") {
			$("span[name=upgradePointAdd]").append("<font color='red' class='formtips onError emaill'>请输入晋升所需消费额！</font>");
			return false;
		}
		if(!/^[0-9]*[0-9][0-9]*$/.test(form.find("input[name='upgradePoint']").val())){
			$("span[name='upgradePointAdd']").append("<font color='red'>请输入正整数！</font>");
			return false;
		}
		if (form.find("textarea[name='memo']").val()=="") {
			$("span[name='memoAdd']").append("<font color='red' class='formtips onError emaill'>请输入备注！</font>");
			return false;
		}
		if (form.find("select[name='isAvailable']").val()=="") {
			$("span[name='isAvailableAdd']").append("<font color='red' class='formtips onError emaill'>请选择是否可用！</font>");
			return false;
		}
		
		$.post("memberLevel/memberLevelUnique.do",{levelName:form.find("input[name='levelName']").val()},function(res){
			if(res.code==1){
				$("span[name='levelNameAdd']").append("<font color='red'>会员等级名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : memberLevelAdd.appPath + "/memberLevel/addMemberLevel.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加会员等级成功", function() {
								closeTab("会员等级添加");
								$("#memberLevelList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg,function(){
								//closeTab("会员等级添加");
								//$("#memberLevelList").DataTable().ajax.reload(function(){});
							});
						}
					},
				});
			}
		});
	 }
	}
   return memberLevelAdd;
});