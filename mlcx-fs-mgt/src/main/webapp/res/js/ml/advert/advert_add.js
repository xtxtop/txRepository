define([],function() {
var advertAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			var form = $("form[name=advertMengLongAddFrom]");
			//新增提交
			$("#saveAddAdvertMengLong").click(function(){
				advertAdd.saveAdvert();
			});
			$("#cml").hide();
			$("#textMengLong").hide();
			//新增页面的关闭
			$("#closeAddAdvertMengLong").click(function(){
				closeTab("新增广告");
			});
			//初始隐藏广告区域类型
			$("#advert_add_tr").hide();
			//初始类型选择
			selectOptionType();
			//初始区域位置
			selectOption(advertPosition_o);
			
			//上传图片
			$("#addAdvertMengLongPicUrlButton").click(function(){
				$("#advertMengLongPicUrlAddModal").modal("show");
			});
			$("input[name='linkType']").click(function(){
				var form = $("form[name=advertMengLongAddFrom]");
				var linkType = form.find("input[name='linkType']:checked").val();
				if (linkType==0) {
					$("#linkUrlAddMengLong").show();
					$("#textMengLong").hide();
				}else if(linkType==1){
					$("#linkUrlAddMengLong").hide();
					$("#textMengLong").show();
				}
	        });
			//新增图片回填
			$("#addAdvertMengLongPicBtn").click(function(){
				var form=$("form[name=advertMengLongPicUrlAddForm]");
				var img=form.find("input[name=advertPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=advertMengLongAddFrom]");
					form1.find("input[name=advertPicUrl]").val(img);
					form1.find("#advertMengLongPicUrlImg").css("background-image", "url(" + advertAdd.imgPath + '/' + img + ")");
					$("#advertMengLongPicUrlAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
			
		},

		
		saveAdvert:function() {
				var form = $("form[name=advertMengLongAddFrom]");
				form.ajaxSubmit({
					url : advertAdd.appPath + "/advertMengLong/addAdvert.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告添加成功", function() {
								closeTab("新增广告");
								$("#advertMengLongList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告添加失败！");
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						$("span[name='typeAdd']").empty();
						$("span[name='advertTypeAdd']").empty();
						$("span[name='advertNameAdd']").empty();
						$("span[name='rankingAdd']").empty();
						$("span[name='advertPositionAdd']").empty();
						$("span[name='advertPicUrlAdd']").empty();
						$("span[name='linkUrlAdd']").empty();
						$("span[name='textAdd']").empty();
						if (form.find("select[name='type']").val()=="") {
							$("span[name='typeAdd']").append("<font color='red'>请选择广告类型！</font>");
							return false;
						}
						var s=form.find("select[name='type']").val()
						if (form.find("select[name='advertType']").val()==""&&s==2) {
							$("span[name='advertTypeAdd']").append("<font color='red'>请选择区域类型！</font>");
							return false;
						}
						if (form.find("select[name='advertPosition']").val()=="") {
							$("span[name='advertPositionAdd']").append("<font color='red'>请输入区域位置！</font>");
							return false;
						}
						if (form.find("input[name='advertName']").val()=="") {
							$("span[name='advertNameAdd']").append("<font color='red'>请输入活动名称！</font>");
							return false;
						}
						
						if (form.find("input[name='ranking']").val()=="") {
							$("span[name='rankingAdd']").append("<font color='red'>请输入排序！</font>");
							return false;
						}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
			            	$("span[name='rankingAdd']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
							return false;
						}
						var advertPosition=$("#advertAdd_AdvertPosition").find("option:selected").text();
						if(form.find("select[name='advertType']").val()!=5&&advertPosition.trim()!="滚动文字"){//滚动文字取消图片
							if (form.find("input[name='advertPicUrl']").val()=="") {
								$("span[name='advertPicUrlAdd']").append("<font color='red'>请上传广告图片！</font>");
								return false;
							}
						}
					/*	
						var linkUrl=form.find("input[name='linkUrl']").val();
						var text=$("#addTextMengLong").val();
						var carModelId=form.find("input[name='carModelId']").val();
						if(form.find("input[name='linkType']:checked").val()==0){
							if(linkUrl == null || linkUrl == undefined || linkUrl == ""){
								$("span[name='linkUrlAdd']").append("<font color='red'>请输入外部链接！</font>");
								return false;
							}
						}else if(form.find("input[name='linkType']:checked").val()==1){
							if(text === null || text == undefined || text == ""){
								$("span[name='textAdd']").append("<font color='red'>请输入内容！</font>");
								return false;
							}
							
						}*/
					}
				});
			}
			}
			return advertAdd;
})
//广告类型选择
function advertAdd_Type_Change(x){
	$("#advertPosition_add_change_span").show();
	if(x.value==1){
		$("#advert_add_tr").hide();//隐藏区域广告
		var advertAdd_AdvertType=document.getElementById("advertAdd_AdvertType").options;
		for(var i=0;i<advertAdd_AdvertType.length;i++){
			if(advertAdd_AdvertType[i].selected){
				advertAdd_AdvertType[i].selected=false;
			}
		}
		$(".advertPicUrlAddMengLong").show();
		document.getElementById('linkTypeRadioAddMengLong').setAttribute('colSpan','1');
		selectOption(advertPosition_o);
	}/*else if(x.value==3){
		$("#advert_add_tr").hide();//隐藏区域广告
		var advertAdd_AdvertType=document.getElementById("advertAdd_AdvertType").options;
		for(var i=0;i<advertAdd_AdvertType.length;i++){
			if(advertAdd_AdvertType[i].selected){
				advertAdd_AdvertType[i].selected=false;
			}
		}
		selectOption(advertPosition_s);
		$(".advertPicUrlAddMengLong").show();
		document.getElementById('linkTypeRadioAddMengLong').setAttribute('colSpan','1');
	}*/else if(x.value==2||x.value==3){
		$("#advert_add_tr").show();//显示区域广告
		$("#advertAdd_AdvertType").html("");
		$("#advertAdd_AdvertPosition").html("");
		var select="<option  value=''> 请选择 </option>";
	    for(var i=0;i<advertPosition_t.length;i++){
				select+="<option  value='"+(i+1)+"'> "+advertPosition_t[i]+" </option>";
			}
			select+="</select>";
			$("#advertAdd_AdvertType").append(select)
		$("#advertAdd_AdvertPosition").append("<option  value=''> 请选择 </option>");
	}else if (x.value==16){
		$("#advert_add_tr").hide();//隐藏区域广告
		var advertAdd_AdvertType=document.getElementById("advertAdd_AdvertType").options;
		for(var i=0;i<advertAdd_AdvertType.length;i++){
			if(advertAdd_AdvertType[i].selected){
				advertAdd_AdvertType[i].selected=false;
			}
		}
		selectOption(advertPosition_six);
		$(".advertPicUrlAddMengLong").show();
		document.getElementById('linkTypeRadioAddMengLong').setAttribute('colSpan','1');
	}else if (x.value==17){
		$("#advert_add_tr").hide();//隐藏区域广告
		var advertAdd_AdvertType=document.getElementById("advertAdd_AdvertType").options;
		for(var i=0;i<advertAdd_AdvertType.length;i++){
			if(advertAdd_AdvertType[i].selected){
				advertAdd_AdvertType[i].selected=false;
			}
		}
		selectOption(advertPosition_se);
		$(".advertPicUrlAddMengLong").show();
		document.getElementById('linkTypeRadioAddMengLong').setAttribute('colSpan','1');
	}else{
		$("#advert_add_tr").hide();//隐藏区域广告
		var advertAdd_AdvertType=document.getElementById("advertAdd_AdvertType").options;
		for(var i=0;i<advertAdd_AdvertType.length;i++){
			if(advertAdd_AdvertType[i].selected){
				advertAdd_AdvertType[i].selected=false;
			}
		}
		selectOption(advertPosition_f);
		$(".advertPicUrlAddMengLong").show();
		document.getElementById('linkTypeRadioAddMengLong').setAttribute('colSpan','1');
	}
	var form = $("form[name=advertMengLongAddFrom]");
	var linkType = form.find("input[name='linkType']:checked").val();
	if (linkType==0) {
		$("#linkUrlAddMengLong").show();
		$("#textMengLong").hide();
	}else if(linkType==1){
		$("#linkUrlAddMengLong").hide();
		$("#textMengLong").show();
	}
}//充电桩 选择区域类型
function advertAdd_advertType_Change(x){
	if(x.value==1){//顶部
		selectOption(advertPosition_t_o);
	}else if(x.value==2){//中间
		selectOption(advertPosition_t_t);
	}else if (x.value==3){//广告
		selectOption(advertPosition_t_s);
	}else if (x.value==4){//图片
		selectOption(advertPosition_t_f);
	}else if (x.value==5){//滚动文字
		selectOption(advertPosition_t_fv);
	}else if (x.value==6){//最新动态
		selectOption(advertPosition_t_sx);
	}
	if(x.value==5){
		$(".advertPicUrlAddMengLong").hide();
		document.getElementById('linkTypeRadioAddMengLong').setAttribute('colSpan','3');
	}else{
		$(".advertPicUrlAddMengLong").show();
		document.getElementById('linkTypeRadioAddMengLong').setAttribute('colSpan','1');
		var form = $("form[name=advertMengLongAddFrom]");
		var linkType = form.find("input[name='linkType']:checked").val();
		if (linkType==0) {
			$("#linkUrlAddMengLong").show();
			$("#textMengLong").hide();
		}else if(linkType==1){
			$("#linkUrlAddMengLong").hide();
			$("#textMengLong").show();
		}
	}
}
//添加区域位置
function selectOption(x){
	$("#advertAdd_AdvertPosition").html("");
	var select="<option  value=''> 请选择 </option>";
    for(var i=0;i<x.length;i++){
			select+="<option  value='"+(i+1)+"'> "+x[i]+" </option>";
		}
		select+="</select>";
		$("#advertAdd_AdvertPosition").append(select)
}
function selectOptionType(){
	$("#advertAdd_Type").html("");
	var select="";
	for(var i=0;i<type_advert.length;i++){
		select+="<option  value='"+(i+1)+"'> "+type_advert[i]+" </option>";
	}
	select+="</select>";
	$("#advertAdd_Type").append(select)
}
//选择区域位置为 '滚动文字'
function advertPosition_add_change(x){
	var advertPosition=$(x).find("option:selected").text();
	if(advertPosition.trim()==="滚动文字"){
		$("#advertPosition_add_change_span").hide();
	}else{
		$("#advertPosition_add_change_span").show();
	}
}
