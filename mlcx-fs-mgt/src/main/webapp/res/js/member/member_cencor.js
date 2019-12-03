define(
[],
function() {
var memberCencor = {
	appPath : getPath("app"),
	init : function() {
		var form = $("form[name=memberCencorForm]");
		$(".imgEnlarge").click(function(){
			var id=$(this).attr("src");//获取图片;
            window.open(memberCencor.appPath+"/member/getImg.do?imgSrc="+id,"","");
         });
		//审核按钮赋值
		$("#cencorStatus1").click(function(){
			memberCencor.setValue();
		});
		//审核按钮赋值
		$("#cencorStatus2").click(function(){
			memberCencor.setValue();
		});
		//编辑提交
		$("#saveCencorMember").click(function(){
			memberCencor.cencorMember();
		});
		//上一个
		$("#upCencorMember").click(function(){
			closeTab("会员审核");
			var memberLowNo=form.find("#memberLowNo").val();
			addTab("会员审核",memberCencor.appPath+ "/member/toCencorMember.do?memberNo="+memberLowNo);
		});
		//下一个
		$("#downCencorMember").click(function(){
			closeTab("会员审核");
			var memberNextNo=form.find("#memberNextNo").val();
			addTab("会员审核",memberCencor.appPath+ "/member/toCencorMember.do?memberNo="+memberNextNo);
		});
		//返回
		$("#closeCencorMember").click(function(){
			closeTab("会员审核");
		});
		
		if($("#typeUp").val()==0){
			$("#upCencorMember").hide();
		}else{
			$("#upCencorMember").show();
		}
		if($("#typeDown").val()==0){
			$("#downCencorMember").hide();
		}else{
			$("#downCencorMember").show();
		}
			
		
	},
	setValue:function(){
		var form = $("form[name=memberCencorForm]");
		form.find("input[name='censorStatus']").val(form.find("input[name='cencorStatus1']:checked").val());
		form.find("#cencorMemo").val();
	},
	cencorMember:function() {
		var form = $("form[name=memberCencorForm]");
		form.ajaxSubmit({
			url : memberCencor.appPath + "/member/cencorMember.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					var memberNextNo=form.find("#memberNextNo").val();
					if(memberNextNo==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功。", function() {
							closeTab("会员审核");
							$("#memberList").DataTable().ajax.reload(function(){});
						});
					}else{
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功，是否审核下一个会员？", function(result) {
							if(result){
								closeTab("会员审核");
								addTab("会员审核",memberCencor.appPath+ "/member/toCencorMember.do?memberNo="+memberNextNo);
							}else{
								closeTab("会员审核");
								$("#memberList").DataTable().ajax.reload(function(){});
							}						
						});
					}
					
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核失败！"+msg);
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#memberNameCencor").empty();
				$("#sexCencor").empty();
				$("#idCardCencor").empty();
				$("#expirationDateCencor").empty();
				$("#cencorMemoCencor").empty();
				
				if (form.find("input[name='memberName']").val().trim()=="") {
					$("#memberNameCencor").append("<font color='red' class='formtips onError emaill'>请输入会员名称！</font>");
					return false;
				}
				if (form.find("select[name='sex']").val()=="") {
					$("#sexCencor").append("<font color='red' class='formtips onError emaill'>请选择性别！</font>");
					return false;
				}
				if (form.find("input[name='idCard']").val().trim()=="") {
					$("#idCardCencor").append("<font color='red' class='formtips onError emaill'>请输入身份证号！</font>");
					return false;
				}else{
					var idCardReg = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
					if(!idCardReg.test(form.find("input[name='idCard']").val())){
						$("#idCardCencor").append("<font color='red' class='formtips onError emaill'>身份证号输入有误！</font>");
						return false;
					}
				}
				if (form.find("input[name='expirationDate']").val().trim()=="") {
					$("#expirationDateCencor").append("<font color='red' class='formtips onError emaill'>请输入驾驶证有效期！</font>");
					return false;
				}
				
				if (form.find("input[name='censorStatus']:checked").val()=="3" && form.find("textarea[name='cencorMemo']").val().trim()=="") {
					$("#cencoeMemoLabel").text("*审核备注：");
					$("#cencorMemoCencor").append("<font color='red' class='formtips onError emaill'>请添加审核备注！</font>");
					return false;
				}
			}
		});
	 }
	}
return memberCencor;
})
