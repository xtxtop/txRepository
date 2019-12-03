define([],function() {	
var franchiseeAdd = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			var form = $("form[name=franchiseeAddForm]");
			
			//上传图片
			$("#addfranchiseePhoto").click(function(){
				$("#franchiseeAddModal").modal("show");
			});
			
			
			//新增图片回填
			$("#addfranchiseePhotoBtn").click(function(){
				debugger
				var form=$("form[name=franchiseephotoForm]");
				var img=form.find("input[name=franchiseePhotoUrl1]").val();
				if(img!=""){
					var form1=$("form[name=franchiseeAddForm]");
					form1.find("input[name=franchiseePhotoUrl1]").val(img);
					form1.find("#franchiseePicUrlImg").css("background-image","url("+franchiseeAdd.imgPath+'/'+img+")");
					$("#franchiseeAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			//增加提交
			$("#addfranchisee").click(function(){
				franchiseeAdd.saveFranchisee();
			});
			//增加页面关闭
			$("#closefranchiseeadd").click(function(){
				closeTab("加盟商新增");
			});
			
			
			
			
		},
		
		 saveFranchisee:function() {
			var form = $("form[name=franchiseeAddForm]");
			form.ajaxSubmit({
				url : franchiseeAdd.appPath + "/franchisee/editFranchisee.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("加盟商新增");
							$("#franchiseeList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='franchiseeNameAdd']").empty();
					$("span[name='franchiseeFullNameAdd']").empty();
					$("span[name='carProportionAdd']").empty();
					$("span[name='parkProportionAdd']").empty();
					$("span[name='contactsAdd']").empty();
					$("span[name='contactsPhoneAdd']").empty();
					$("span[name='mailboxAdd']").empty();
					$("span[name='memoAdd']").empty();
					$("span[name='franchiseePhotoUrl1Add']").empty();
					if (form.find("input[name='franchiseeName']").val()=="") {
						$("span[name='franchiseeNameAdd']").append("<font color='red'>请输入加盟商名称！</font>");
						return false;
					}
					if (form.find("input[name='franchiseeFullName']").val()=="") {
						$("span[name='franchiseeFullNameAdd']").append("<font color='red'>请输入加盟商全称！</font>");
						return false;
					}
					if(form.find("input[name='carProportion']").val() ==""){
						$("span[name='carProportionAdd']").append("<font color='red' class='formtips onError emaill'>请输入分润比例（按车）！</font>");
						return false;
					}else if (!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='carProportion']").val())) {
						$("span[name='carProportionAdd']").append("<font color='red' class='formtips onError emaill'>分润比例（按车）只能是数字！</font>");
						return false;
				    }
					if(form.find("input[name='parkProportion']").val() ==""){
						$("span[name='parkProportionAdd']").append("<font color='red' class='formtips onError emaill'>请输入分润比例（按场站）！</font>");
						return false;
					}else if (!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='parkProportion']").val())) {
						$("span[name='parkProportionAdd']").append("<font color='red' class='formtips onError emaill'>分润比例（按场站）只能是数字！</font>");
						return false;
				    }
					if (form.find("input[name='contacts']").val()=="") {
						$("span[name='contactsAdd']").append("<font color='red'>请输入联系人！</font>");
						return false;
					}
					
					if (form.find("input[name='contactsPhone']").val()=="") {
						$("span[name='contactsPhoneAdd']").append("<font color='red'>请输入联系人手机！</font>");
						return false;
					}else if(!/^1\d{10}$/.test(form.find("input[name='contactsPhone']").val())){
						$("span[name='contactsPhoneAdd']").append("<font color='red'>手机号格式不正确！</font>");
						return false;
					}
					
					if (form.find("input[name='mailbox']").val()=="") {
						$("span[name='mailboxAdd']").append("<font color='red'>请输入联系人邮箱！</font>");
						return false;
					}else if(!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(form.find("input[name='mailbox']").val())){
						$("span[name='mailboxAdd']").append("<font color='red'>联系人邮箱格式不正确！</font>");
						return false;
					}
					
					if (form.find("textarea[name='memo']").val()=="") {
						$("span[name='memoAdd']").append("<font color='red'>请输入备注！</font>");
						return false;
					}
					if (form.find("input[name='franchiseePhotoUrl1']").val()=="") {
						$("span[name='franchiseePhotoUrl1Add']").append("<font color='red'>请上传相关证件！</font>");
						return false;
					}
					
				}
			});
		}
}
return franchiseeAdd
})
