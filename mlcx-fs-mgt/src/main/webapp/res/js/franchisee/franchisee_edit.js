define([],function() {	
var franchiseeEdit = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			var form = $("form[name=franchiseeEditForm]");
			
			//上传图片
			$("#editFranchiseePhoto").click(function(){
				$("#franchiseeEditModal").modal("show");
			});
			
			

			$("#editFranchiseePhotoBtn").click(function(){
				debugger
				var form=$("form[name=franchiseephotoForm]");
				var img=form.find("input[name=franchiseePhotoUrl1]").val();
				if(img!=""){
					var form1=$("form[name=franchiseeEditForm]");
					form1.find("input[name=franchiseePhotoUrl1]").val(img);
					form1.find("#franchiseePicUrlImg").css("background-image","url("+franchiseeEdit.imgPath+'/'+img+")");
					$("#franchiseeEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
			$("#editFranchisee").click(function(){
				franchiseeEdit.saveFranchisee();
			});
			$("#saveCencorFranchisee").click(function(){
				franchiseeEdit.saveCencorFranchisee();
			});
			
			$("#closefranchiseeEdit").click(function(){
				closeTab("加盟商编辑");
			});
			$("#closeFranchiseeCencor").click(function(){
				closeTab("加盟商审核");
			});
			
			
			
			
		},
		
		saveCencorFranchisee:function() {
				var form = $("form[name=saveCencorFranchiseeForm]");
				form.ajaxSubmit({
					url : franchiseeEdit.appPath + "/franchisee/editFranchisee.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功", function() {
								closeTab("加盟商审核");
								$("#franchiseeList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编审核失败！"+msg);
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						$("span[name='cencorMemoNs']").empty();
						
						
						if (form.find("textarea[name='cencorMemo']").val()=="") {
							$("span[name='cencorMemoNs']").append("<font color='red'>请输入审核备注！</font>");
							return false;
						}
					}
				});
			},
		
		 saveFranchisee:function() {
			var form = $("form[name=franchiseeEditForm]");
			form.ajaxSubmit({
				url : franchiseeEdit.appPath + "/franchisee/editFranchisee.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
							closeTab("加盟商编辑");
							$("#franchiseeList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='franchiseeNameEdit']").empty();
					$("span[name='franchiseeFullNameEdit']").empty();
					$("span[name='carProportionEdit']").empty();
					$("span[name='parkProportionEdit']").empty();
					$("span[name='contactsEdit']").empty();
					$("span[name='contactsPhoneEdit']").empty();
					$("span[name='mailboxEdit']").empty();
					$("span[name='memoEdit']").empty();
					$("span[name='franchiseePhotoUrl1Edit']").empty();
					if (form.find("input[name='franchiseeName']").val()=="") {
						$("span[name='franchiseeNameEdit']").append("<font color='red'>请输入加盟商名称！</font>");
						return false;
					}
					if (form.find("input[name='franchiseeFullName']").val()=="") {
						$("span[name='franchiseeFullNameEdit']").append("<font color='red'>请输入加盟商全称！</font>");
						return false;
					}
					if(form.find("input[name='carProportion']").val() ==""){
						$("span[name='carProportionEdit']").append("<font color='red' class='formtips onError emaill'>请输入分润比例（按车）！</font>");
						return false;
					}else if (!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='carProportion']").val())) {
						$("span[name='carProportionEdit']").append("<font color='red' class='formtips onError emaill'>分润比例（按车）只能是数字！</font>");
						return false;
				    }
					if(form.find("input[name='parkProportion']").val() ==""){
						$("span[name='parkProportionEdit']").append("<font color='red' class='formtips onError emaill'>请输入分润比例（按场站）！</font>");
						return false;
					}else if (!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='parkProportion']").val())) {
						$("span[name='parkProportionEdit']").append("<font color='red' class='formtips onError emaill'>分润比例（按场站）只能是数字！</font>");
						return false;
				    }
					if (form.find("input[name='contacts']").val()=="") {
						$("span[name='contactsEdit']").append("<font color='red'>请输入联系人！</font>");
						return false;
					}
					
					if (form.find("input[name='contactsPhone']").val()=="") {
						$("span[name='contactsPhoneEdit']").append("<font color='red'>请输入联系人手机！</font>");
						return false;
					}else if(!/^1\d{10}$/.test(form.find("input[name='contactsPhone']").val())){
						$("span[name='contactsPhoneEdit']").append("<font color='red'>手机号格式不正确！</font>");
						return false;
					}
					
					if (form.find("input[name='mailbox']").val()=="") {
						$("span[name='mailboxEdit']").append("<font color='red'>请输入联系人邮箱！</font>");
						return false;
					}else if(!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(form.find("input[name='mailbox']").val())){
						$("span[name='mailboxEdit']").append("<font color='red'>联系人邮箱格式不正确！</font>");
						return false;
					}
					
					if (form.find("textarea[name='memo']").val()=="") {
						$("span[name='memoEdit']").append("<font color='red'>请输入备注！</font>");
						return false;
					}
					if (form.find("input[name='franchiseePhotoUrl1']").val()=="") {
						$("span[name='franchiseePhotoUrl1Edit']").append("<font color='red'>请上传相关证件！</font>");
						return false;
					}
					
				}
			});
		}
}
return franchiseeEdit
})
