define([],function() {
var advertAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			var form = $("form[name=advertAddFrom]");
			$(".nativeUrlType").hide();
			$(".LinkUrl").hide();
			$(".memo").hide();
			//新增提交
			$("#addAdvert").click(function(){
				advertAdd.saveAdvert();
			});
			//新增页面的关闭
			$("#closeAddAdvert").click(function(){
				closeTab("新增活动");
			});
			//上传图片
			$("#addAdvertPicUrlButton").click(function(){
				$("#advertPicUrlAddModal").modal("show");
			});
			//新增图片回填
			$("#addAdvertPicBtn").click(function(){
				var form=$("form[name=advertPicUrlAddForm]");
				var img=form.find("input[name=advertPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=advertAddFrom]");
					form1.find("input[name=advertPicUrl]").val(img);
					form1.find("#advertPicUrlImg").css("background-image", "url(" + advertAdd.imgPath + '/' + img + ")");
					$("#advertPicUrlAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
			
			//点击活动广告区分跳转类型 弹出对应的跳转链接地址
			form.find("select[name=jumpType]").change(function(){
			var jumpType=$("#jumpTypeId").val();
				if(jumpType=="1"){
					$(".nativeUrlType").show();
					$(".LinkUrl").hide();
					$(".memo").hide();
				}else if(jumpType=="2"){
					$(".nativeUrlType").hide();
					$(".LinkUrl").show();
					$(".memo").hide();
				}else if(jumpType=="3"){
					$(".nativeUrlType").hide();
					$(".LinkUrl").hide();
					$(".memo").show();
				}
				
			});
			
			
			
		},
		saveAdvert:function() {
				var form = $("form[name=advertAddFrom]");
				form.ajaxSubmit({
					url : advertAdd.appPath + "/advert/addAdvert.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动添加成功", function() {
								closeTab("新增活动");
								$("#advertList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动添加失败！");
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						$("span[name='advertTypeAdd']").empty();
						$("span[name='advertNameAdd']").empty();
						$("span[name='rankingAdd']").empty();
						$("span[name='advertPicUrlAdd']").empty();
						$("span[name='externalLinkUrlAdd']").empty();
						$("span[name='text1Add']").empty();
						$("span[name='jumpTypeAdd']").empty();
						$("span[name='nativeUrlTypeAdd']").empty();
						$("span[name='isAvailableAdd']").empty();
						$("span[name='jumpTypeAdd']").empty();
						$("span[name='advertMemberTypeAdd']").empty();
						
						if (form.find("select[name='advertType']").val()=="") {
							$("span[name='advertTypeAdd']").append("<font color='red'>请选择活动类型！</font>");
							return false;
						}
						if (form.find("input[name='advertName']").val()=="") {
							$("span[name='advertNameAdd']").append("<font color='red'>请输入活动名称！</font>");
							return false;
						}
						
						if (form.find("select[name='jumpType']").val()=="") {
							$("span[name='jumpTypeAdd']").append("<font color='red'>请选择跳转类型！</font>");
							return false;
						}
						
						if(form.find("select[name='jumpType']").val()=="1"){
							if (form.find("select[name='nativeUrlType']").val()=="") {
								$("span[name='nativeUrlTypeAdd']").append("<font color='red'>请选择内部链接跳转路径！</font>");
								return false;
							}
						}
						if(form.find("select[name='jumpType']").val()=="2"){
							if (form.find("input[name='externalLinkUrl']").val()=="") {
								$("span[name='externalLinkUrlAdd']").append("<font color='red'>请输入链接跳转路径！</font>");
								return false;
							}
						}
						
						if (form.find("select[name='advertMemberType']").val()=="") {
							$("span[name='advertMemberTypeAdd']").append("<font color='red'>请选择展示类型！</font>");
							return false;
						}
						if (form.find("input[name='ranking']").val()=="") {
							$("span[name='rankingAdd']").append("<font color='red'>请输入排序！</font>");
							return false;
						}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
			            	$("span[name='rankingAdd']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
							return false;
			            }
						if (form.find("input[name='advertPicUrl']").val()=="") {
							$("span[name='advertPicUrlAdd']").append("<font color='red'>请上传活动图片！</font>");
							return false;
						}
			//			if (form.find("input[name='externalLinkUrl']").val()=="") {
			//				$("span[name='externalLinkUrlAdd']").append("<font color='red'>请输入外部链接！</font>");
			//				return false;
			//			}
//						if (form.find("textarea[name='text1']").val()=="") {
//							$("span[name='text1Add']").append("<font color='red'>请输入活动内容1！</font>");
//							return false;
//						}
						
						if (form.find("select[name='isAvailable']").val()=="") {
							$("span[name='isAvailableAdd']").append("<font color='red'>请选择可用状态！</font>");
							return false;
						}
					}
				});
			}
			}
			return advertAdd;
})
