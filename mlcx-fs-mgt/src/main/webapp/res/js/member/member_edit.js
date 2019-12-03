define(
[],
function() {
var memberEdit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//图片
		$(".imgEnlarge").click(function(){
            var id=$(this).attr("src");//获取图片;
            window.open(memberEdit.appPath+"/member/getImg.do?imgSrc="+id,"","");
         });
		//编辑提交
		$("#saveMember").click(function(){
			memberEdit.editMember();
		});
		//返回
		$("#closeMember").click(function(){
			closeTab("会员编辑");
			$("#memberList").DataTable().ajax.reload(function(){});
		});
		//会员头像上传
		$("#addMemberPhotoButton").click(function(){
			$("#memberPhotoUrlEditModal").modal("show");
		});
		//会员头像的回填
		$("#addMemberPhotoBtn").click(function(){
			var form=$("form[name=memberPhotoUrlEditForm]");
			var img=form.find("input[name=memberPhotoUrl1]").val();
			if(img!=""){
				var form1=$("form[name=memberForm]");
				form1.find("input[name=memberPhotoUrl]").val(img);
				form1.find("#memberPhotoUrlImg").css("background-image","url("+memberEdit.imgPath+'/'+img+")");
				$("#memberPhotoUrlEditModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
			
		});
		//驾驶证图片1上传
		$("#addDriverLicensePhotoUrl1Button").click(function(){
			$("#DriverLicensePhotoUrl1EditModal").modal("show");
		});
		//驾驶证图片1的回填
		$("#addDriverLicensePhotoUrl1Btn").click(function(){
			debugger;
			var form=$("form[name=driverLicensePhotoUrl1EditForm]");
			var img=form.find("input[name=driverLicensePhotoUrl1]").val();
			if(img!=""){
				var form1=$("form[name=memberForm]");
				form1.find("input[name=driverLicensePhotoUrl1]").val(img);
				form1.find("#driverLicensePhotoUrl1Img").css("background-image","url("+memberEdit.imgPath+'/'+img+")");
				$("#DriverLicensePhotoUrl1EditModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
		
		//手持身份证上传
		$("#addIdCardPhotoUrlButton").click(function(){
			$("#idCardPhotoUrlEditModal").modal("show");
		});
		//手持身份证上传
		$("#addidCardPhotoUrlBtn").click(function(){
			debugger;
			var form=$("form[name=idCardPhotoUrlEditForm]");
			var img=form.find("input[name=idCardPhotoUrl]").val();
			if(img!=""){
				var form1=$("form[name=memberForm]");
				form1.find("input[name=idCardPhotoUrl]").val(img);
				form1.find("idCardPhotoUrlImg").css("background-image","url("+memberEdit.imgPath+'/'+img+")");
				$("#idCardPhotoUrlEditModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
		
		
		//驾驶证图片2上传
		$("#addDriverLicensePhotoUrl2Button").click(function(){
			$("#DriverLicensePhotoUrl2EditModal").modal("show");
		});
		
		//驾驶证图片2的回填
		$("#addDriverLicensePhotoUrl2Btn").click(function(){
			var form=$("form[name=driverLicensePhotoUrl2EditForm]");
			var img=form.find("input[name=driverLicensePhotoUrl2]").val();
			if(img!=""){
				var form1=$("form[name=memberForm]");
				form1.find("input[name=driverLicensePhotoUrl2]").val(img);
				form1.find("img[name=driverLicensePhotoUrl2Img]").attr("src",memberEdit.imgPath+"/"+img);
				$("#DriverLicensePhotoUrl2EditModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
		//其他证件上传
		$("#addpaperUrlImgButton").click(function(){
			$("#addpaperUrlImg").modal("show");
		});
		//其他证件上传回填
		$("#addpaperUrlBtn").click(function(){
			debugger;
			var form=$("form[name=paperUrlError]");
			var img=form.find("input[name=paperUrl]").val();
			if(img!=""){
				var form1=$("form[name=memberForm]");
				form1.find("input[name=paperUrl]").val(img);
				form1.find("img[name=paperUrlImg]").attr("src",memberEdit.imgPath+"/"+img);
				$("#addpaperUrlImg").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
		//会员编辑页跳转到会员订单页面
		$("#memberPayAmount").each(function(){
			var memberNo=$("#memberNoPayAmount").val();
			$(this).on("click",function(){
				addTab("订单列表", memberEdit.appPath+ "/member/getMemberPayAmountOrder.do?memberNo="+memberNo, "",function(){ 
				});
			});
        });
		//会员编辑页跳转到会员订单页面
		$("#memberPayAmountview").each(function(){
			var memberNo=$("#memberNoPayAmountview").val();
			$(this).on("click",function(){
				addTab("订单列表", memberEdit.appPath+ "/member/getMemberPayAmountOrder.do?memberNo="+memberNo, "",function(){ 
				});
			});
        });
		//会员详情页跳转到会员订单页面
		$("#memberOrder").each(function(){
			var form=$("form[name=memberListForm]");
			var memberNo=form.find("#memberNo").val();
			$(this).on("click",function(){
				addTab("订单列表", memberEdit.appPath+ "/member/getMemberOrder.do?memberNo="+memberNo, "",function(){ 
				});
			});
        });
		//会员编辑页面跳转到会员订单页面
		$("#EditmemberOrder").each(function(){
			var memberNo=$("#EditmemberNo").val();
			$(this).on("click",function(){
				addTab("订单列表", memberEdit.appPath+ "/member/getMemberOrder.do?memberNo="+memberNo, "",function(){ 
				});
			});
        });
		//会员详情页面页面跳转到违章列表页面
		$("#viewllegalNum").each(function(){
			var driverId=$("#memberillegalNumNoview").val();
			$(this).on("click",function(){
				addTab("违章列表", memberEdit.appPath+ "/carIllegal/getillegalNum.do?driverId="+driverId, "",function(){ 
				});
			});
        });
		//会员编辑页面跳转到违章列表页面
		$("#EditillegalNum").each(function(){
			var driverId=$("#memberillegalNumNo").val();
			$(this).on("click",function(){
				addTab("违章列表", memberEdit.appPath+ "/carIllegal/getillegalNum.do?driverId="+driverId, "",function(){ 
				});
			});
        });
		//会员详情页跳转到会员列表页面
		$("#mbs").each(function(){
			var form=$("form[name=memberListForm]");
			var refereeId=form.find("#memberNo").val();
			$(this).on("click",function(){
				addTab("会员管理", memberEdit.appPath+ "/member/toMemberListMbs.do?refereeId="+refereeId, "",function(){ 
				});
			});
        });
		
		//会员编辑页跳转到会员列表页面
		$("#Editmbs").each(function(){
			var form=$("form[name=memberForm]");
			var refereeId=form.find("#EditmemberNo").val();
			$(this).on("click",function(){
				addTab("会员管理", memberEdit.appPath+ "/member/toMemberListMbs.do?refereeId="+refereeId, "",function(){ 
				});
			});
        });
	},
	editMember:function() {
		var form = $("form[name=memberForm]");
		form.ajaxSubmit({
			url : memberEdit.appPath + "/member/updateMember.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("会员编辑");
						$("#memberList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
				}
			},
			beforeSubmit : function(formData,jqForm, options) {// 提交表单前数据验证
				$("#memberNameEdit").empty();
				$("#memberNickEdit").empty();
//				$("#mobilePhoneEdit").empty();
				$("#sexEdit").empty();
				$("#idCardEdit").empty();
				$("#expirationDateEdit").empty();
				var phone=/^1\d{10}$/;
				if (form.find("input[name='memberName']").val()=="") {
					$("#memberNameEdit").append("<font color='red' class='formtips onError emaill'>请输入会员名称！</font>");
					return false;
				}
				/*if (form.find("input[name='memberNick']").val()=="") {
					$("#memberNickEdit").append("<font color='red' class='formtips onError emaill'>请输入会员昵称！</font>");
					return false;
				}*/
			/*	if ($.trim(form.find("input[name='mobilePhone']").val())=="") {
					$("#mobilePhoneEdit").append("<font color='red' class='formtips onError emaill'>请输入手机号！</font>");
					return false;
				}else{
					if(!phone.test($.trim(form.find("input[name='mobilePhone']").val()))){
						$("#mobilePhoneEdit").append("<font color='red' class='formtips onError emaill'>请输入手机号！</font>");
						return false;
					}
				}*/
				if (form.find("select[name='sex']").val()=="") {
					$("#sexEdit").append("<font color='red' class='formtips onError emaill'>请选择性别！</font>");
					return false;
				}
				if (form.find("input[name='idCard']").val()=="") {
					$("#idCardEdit").append("<font color='red' class='formtips onError emaill'>请输入身份证号！</font>");
					return false;
				}else{
					var idCardReg = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/; 
					if(!idCardReg.test(form.find("input[name='idCard']").val())){
						$("#idCardEdit").append("<font color='red' class='formtips onError emaill'>身份证号输入有误！</font>");
						return false;
					}
				}
				if (form.find("input[name='expirationDate']").val()=="") {
					$("#expirationDateEdit").append("<font color='red' class='formtips onError emaill'>请输入驾驶证有效期！</font>");
					return false;
				}
			}
		});
	 }
	}
   return memberEdit;
});

