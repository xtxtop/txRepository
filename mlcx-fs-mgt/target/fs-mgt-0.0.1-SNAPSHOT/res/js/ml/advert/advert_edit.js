define([],function() {
var advertMengLongEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			var form = $("form[name=advertMengLongEditFrom]");
			//编辑提交
			$("#editAdvertMengLong").click(function(){
				advertMengLongEdit.advertMengLongEdit();
			});
			//编辑页面的关闭
			$("#closeEditAdvertMengLong").click(function(){
				closeTab("广告编辑");
			});
			//上传图片
			$("#editAdvertMengLongPicUrlButton").click(function(){
				$("#advertMengLongPicUrlEditModal").modal("show");
			});
			//初始类型选择
			selectOptionType_edit();
			advertMengLongEdit.linkType();
			$("input[name='linkType']").click(function(){
				var form = $("form[name=advertMengLongEditFrom]");
				var linkType = form.find("input[name='linkType']:checked").val();
				if (linkType==0) {
					$("#linkUrlEditMengLong").show();
					$("#textEditMengLong").hide();
                    $("#linkUrlMengLong").val("");
				}else if(linkType==1){
					$("#linkUrlEditMengLong").hide();
					$("#textEditMengLong").show();
				}
	        });
			//判断广告类型
			advertMengLongEdit.advert();
			
			//编辑图片回填
			$("#editAdvertMengLongPicBtn").click(function(){
				var form=$("form[name=advertPicUrlEditForm]");
				var img=form.find("input[name=advertPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=advertMengLongEditFrom]");
					form1.find("input[name=advertPicUrl]").val(img);
					form1.find("#advertMengLongPicUrlImgEdit").css("background-image", "url(" + advertMengLongEdit.imgPath + '/' + img + ")");
					$("#advertMengLongPicUrlEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			
			
			//会员列表
			$("#selectCarModelEdit").click(function(){
				advertMengLongEdit.pageListModel();
				$("#carModelEditModal").modal({
					 show:true,
					 backdrop:'static'
				});
			});
			
		},
		advert:function(){//广告类型
			var form = $("form[name=advertMengLongEditFrom]");
			var type=form.find("select[name='type']").val();
			if(type==1){//总首页
				$("#advert_Edit_tr").hide();//隐藏区域广告
			    $("#advertEdit_AdvertPosition").html("");
			    var advertPosition_input=$("#advertPosition_input").val();
				initSelect(advertPosition_input,advertPosition_o);
			}else if(type==2||type==3){//充电桩 停车场
				 var advertType_input=$("#advertType_input").val();//区域类型
				 var advertPosition_input=$("#advertPosition_input").val();//区域位置
				 $("#advertEdit_AdvertPosition").html("");
				 $("#advertEdit_AdvertType").html("");
				 var select="<option  value=''> 请选择 </option>";
				 for(var i=0;i<advertPosition_t.length;i++){
						if(advertType_input==(i+1)){
							select+="<option  value='"+(i+1)+"' selected> "+advertPosition_t[i]+" </option>";
						}else{
							select+="<option  value='"+(i+1)+"'> "+advertPosition_t[i]+" </option>";
						}
					}
				select+="</select>";
				$("#advertEdit_AdvertType").append(select)
				if(advertType_input==1){
					initSelect(advertPosition_input,advertPosition_t_o);
				}else if(advertType_input==2){
					initSelect(advertPosition_input,advertPosition_t_t);
				}else if(advertType_input==3){
					initSelect(advertPosition_input,advertPosition_t_s);
				}else if(advertType_input==4){
					initSelect(advertPosition_input,advertPosition_t_f);
				}else if(advertType_input==5){
					$(".advertPicUrlEditMengLong").hide();
					initSelect(advertPosition_input,advertPosition_t_fv);
				}else if(advertType_input==6){
					initSelect(advertPosition_input,advertPosition_t_sx);
				}
				
			}else if(type==16){
				$("#advert_Edit_tr").hide();//隐藏区域广告
				$("#advertEdit_AdvertPosition").html("");
				var advertPosition_input=$("#advertPosition_input").val();
				initSelect(advertPosition_input,advertPosition_six);
			}else if(type==17){
				$("#advert_Edit_tr").hide();//隐藏区域广告
				$("#advertEdit_AdvertPosition").html("");
				var advertPosition_input=$("#advertPosition_input").val();
				initSelect(advertPosition_input,advertPosition_se);
			}else{
				$("#advert_Edit_tr").hide();//隐藏区域广告
				$("#advertEdit_AdvertPosition").html("");
				var advertPosition_input=$("#advertPosition_input").val();
				initSelect(advertPosition_input,advertPosition_f);
			}
			
		},
		linkType:function () {
			var form = $("form[name=advertMengLongEditFrom]");
			var linkType = form.find("input[name='linkType']:checked").val();
			if (linkType==0) {
				$("#linkUrlEditMengLong").show();
				$("#textEditMengLong").hide();
			}else if(linkType==1){
				$("#linkUrlEditMengLong").hide();
				$("#textEditMengLong").show();
			}
		},
		pageListModel:function () {
			var tpl = $("#carModelEditBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(tpl);
			$('#carModelEditLists').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": advertMengLongEdit.appPath + '/carModel/pageListCarModel.do',
					"data": function ( d ) {
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"listStatus":1
						} );
					},
					"dataSrc": function ( json ) {
						json.recordsTotal=json.rowCount;
						json.recordsFiltered=json.rowCount;
						json.data=json.data;
						return json.data;
					},
					error: function (xhr, error, thrown) {  
		            }
				},
			   "columns": [
					{ "title":"","data": "carModelId" },
					{ "title":"车型名称","data": "carModelName" },
					
				],
			   "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				   	$(this).find("thead tr th:first-child").prepend('');
					$("#carModelEditTools").empty().append('<button type="button"  class="btn-new-type carModel-batch-sel">确认</button><button type="button"  class="btn-new-type btn-new-type-blue carModel-batch-close">关闭</button>');
//					$("#memberPushEditTools").append('');
					$(".carModel-batch-sel").on("click",function(){
						advertMengLongEdit.setSelectedData();
						$("#carModelEditModal").modal("hide");
						$('#carModelEditLists tbody input[type="checkbox"]:checked').prop("checked",false);
					});
					$(".carModel-batch-close").on("click",function(){
						advertMengLongEdit.setDataTemp(advertEdit.getSelectedData());//如果取消时，则需要把缓存区的数据变为与已确认的一致
						$("#carModelEditModal").modal("hide");
					});
				},
				"drawCallback": function( settings ) {
					advertMengLongEdit.modalCallback()
			    },
				"columnDefs":[
						{
							"targets" : [0],
							"orderable":false,
							"render" : function(data, type, full, meta) {
								var nameValue = '';
								if(full.carModelName != null){
									nameValue = '" nameValue="' + full.carModelName +'" ';
								}
								var text = '<input type="checkbox"  name="idNo" value="' + full.carModelId + '" '+ nameValue;
								var data = advertMengLongEdit.getDataTemp();
								for(var i = 0; i < data.length;i ++){
									if(data[i].id == full.carModelId){
										text += ' checked="checked" '
										break;
									}
								}
								return text +' >';
							}
					}]
			});
		},getSelectedData:function () {//得到已经确认选择的数据
			var data = [];
			var form = $("form[name=advertMengLongEditFrom]");
			var id = form.find("input[name='carModelId']").val()
			var name =  form.find("input[name='carModelName']").val();
			
			if(id != null && id != "" && name != null && name != ""){
				var ids = id.split(",");
				var names = name.split(",");
				for(var i = 0; i < ids.length;i ++){
					var object = new Object;
					object.id = ids[i];
					object.name = names[i];
					data.push(object);
				}
			}
			
			return data;
		},setSelectedData:function () {
			var data = advertMengLongEdit.getDataTemp();//获取缓存区的数据
			var form = $("form[name=advertMengLongEditFrom]");
			if(data != null && data.length > 0){
				var idsString = "";
				var nameString = "";
				for(var i = 0; i < data.length;i ++){
					idsString += data[i].id + ",";
					nameString += data[i].name + ",";
				}
				idsString = idsString.substring(0,idsString.length-1);
				nameString = nameString.substring(0,nameString.length-1);
				
				form.find("input[name='carModelId']").val(idsString);
				form.find("input[name='carModelName']").val(nameString);
			}else{
				form.find("input[name='carModelId']").val("");
				form.find("input[name='carModelName']").val("");
			}
		},getDataTemp:function () {//得到缓存区的数据
			var jsonString = $("#carModelEditModal").find("input[name='dataTemp']").val();
			if(jsonString != null  && jsonString != ""){
				return JSON.parse(jsonString);
			}
			return [];
		},setDataTemp:function (data) {//设置缓存区的数据
			var input = $("#carModelEditModal").find("input[name='dataTemp']");
			if(input != null){
				if(data != null && data.length > 0){
					input.val(JSON.stringify(data));
				}else{
					input.val("");//传入的data为空时清除缓存区的数据
				}
			}
		},
		modalCallback:function () {
			$("input[name=idNo]").click(function(a,b,c,d){
				var data = advertMengLongEdit.getDataTemp();//获取缓存区的数据
				if(data == null){
					data = [];
				}
				
				var newData = [];
				if($(this).is(':checked')){
					newData = data;
					var thisData = new Object;
					thisData.id = $(this).val();
					thisData.name = $(this).attr('nameValue') == null ? "" : $(this).attr('nameValue');
					newData.push(thisData)
				}else{
					if(data.length > 0){
						for(var i = 0; i < data.length;i ++){
							if(data[i].id != $(this).val()){
								newData.push(data[i])
							}
						}
					}
				}
				advertMengLongEdit.setDataTemp(newData);
			});
		},
		
		
		
		advertMengLongEdit:function() {
	var form = $("form[name=advertMengLongEditFrom]");
	form.ajaxSubmit({
		url : advertMengLongEdit.appPath + "/advertMengLong/updateAdvert.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告编辑成功", function() {
					closeTab("广告编辑");
					$("#advertMengLongList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='advertTypeEdit']").empty();
			$("span[name='advertNameEdit']").empty();
			$("span[name='rankingEdit']").empty();
			$("span[name='advertPositionEdit']").empty();
			$("span[name='text1Edit']").empty();
			$("span[name='advertPicUrlEdit']").empty();
			$("span[name='carModelIdEdit']").empty();
			$("span[name='linkUrlEdit']").empty();
			var temp = $('input:radio[name="linkType"]:checked').val();
			var lin =  form.find("input[name='linkUrl']").val();
			var te =  form.find("textarea[name='text']").val()
			var ca =  form.find("input[name='carModelId']").val()
			
			var s=form.find("select[name='type']").val()
			if (form.find("select[name='advertType']").val()==""&&s==2) {
				$("span[name='advertTypeEdit']").append("<font color='red'>请选择区域类型！</font>");
				return false;
			}
			if (form.find("select[name='advertPosition']").val()=="") {
				$("span[name='advertPositionEdit']").append("<font color='red'>请输入区域位置！</font>");
				return false;
			}
			if (form.find("input[name='advertName']").val()=="") {
				$("span[name='advertNameEdit']").append("<font color='red'>请输入广告名称！</font>");
				return false;
			}
			if (form.find("input[name='ranking']").val()=="") {
				$("span[name='rankingEdit']").append("<font color='red'>请输入排序！</font>");
				return false;
			}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
            	$("span[name='rankingEdit']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
				return false;
			}
			
			/*if(temp==0 ){
				if(lin=="" || lin==undefined ||lin==null){
					$("span[name='linkUrlEdit']").append("<font color='red'>请输入外部链接！</font>");
					return false;
				}
			}
			if(temp==1){
				if(te=="" || te==undefined ||te==null){
					$("span[name='text1Edit']").append("<font color='red'>请输入内容！</font>");
					return false;
				}
			}
			*/
			var advertPosition_edit=$("#advertEdit_AdvertPosition").find("option:selected").text();
			if(form.find("select[name='advertType']").val()!=5&&advertPosition_edit.trim()!="滚动文字"){//滚动文字取消图片
				if (form.find("input[name='advertPicUrl']").val()=="") {
					$("span[name='advertPicUrlEdit']").append("<font color='red'>请上传广告图片！</font>");
					return false;
				}
			}
		}
	});
}
}
return advertMengLongEdit;
})
//广告类型选择
function advertEdit_Type_Change(x){
	$("#advertPosition_edit_change_span").show();
	if(x.value==1){
		$("#advert_Edit_tr").hide();//隐藏区域广告
		var advertEdit_AdvertType=document.getElementById("advertEdit_AdvertType").options;
		for(var i=0;i<advertEdit_AdvertType.length;i++){
			if(advertEdit_AdvertType[i].selected){
				advertEdit_AdvertType[i].selected=false;
			}
		}
		selectOptionEdit(advertPosition_o);
		$(".advertPicUrlEditMengLong").show();
		document.getElementById('linkTypeRadioEditMengLong').setAttribute('colSpan','1');
	}/*else if(x.value==3){
		$("#advert_Edit_tr").hide();//隐藏区域广告
		var advertEdit_AdvertType=document.getElementById("advertEdit_AdvertType").options;
		for(var i=0;i<advertEdit_AdvertType.length;i++){
			if(advertEdit_AdvertType[i].selected){
				advertEdit_AdvertType[i].selected=false;
			}
		}
		$(".advertPicUrlEditMengLong").show();
		document.getElementById('linkTypeRadioEditMengLong').setAttribute('colSpan','1');
		selectOptionEdit(advertPosition_s);
		
	}*/else if(x.value==2||x.value==3){
		$("#advert_Edit_tr").show();//显示区域广告
		$("#advertEdit_AdvertType").html("");
		$("#advertEdit_AdvertPosition").html("");
		var select="<option  value=''> 请选择 </option>";
	    for(var i=0;i<advertPosition_t.length;i++){
				select+="<option  value='"+(i+1)+"'> "+advertPosition_t[i]+" </option>";
			}
			select+="</select>";
			$("#advertEdit_AdvertType").append(select)
		$("#advertEdit_AdvertPosition").append("<option  value=''> 请选择 </option>");
	}else if(x.value==16){
		$("#advert_Edit_tr").hide();//隐藏区域广告
		var advertEdit_AdvertType=document.getElementById("advertEdit_AdvertType").options;
		for(var i=0;i<advertEdit_AdvertType.length;i++){
			if(advertEdit_AdvertType[i].selected){
				advertEdit_AdvertType[i].selected=false;
			}
		}
		$(".advertPicUrlEditMengLong").show();
		document.getElementById('linkTypeRadioEditMengLong').setAttribute('colSpan','1');
		selectOptionEdit(advertPosition_six);
	}else if(x.value==17){
		$("#advert_Edit_tr").hide();//隐藏区域广告
		var advertEdit_AdvertType=document.getElementById("advertEdit_AdvertType").options;
		for(var i=0;i<advertEdit_AdvertType.length;i++){
			if(advertEdit_AdvertType[i].selected){
				advertEdit_AdvertType[i].selected=false;
			}
		}
		$(".advertPicUrlEditMengLong").show();
		document.getElementById('linkTypeRadioEditMengLong').setAttribute('colSpan','1');
		selectOptionEdit(advertPosition_se);
	}else{
		$("#advert_Edit_tr").hide();//隐藏区域广告
		var advertEdit_AdvertType=document.getElementById("advertEdit_AdvertType").options;
		for(var i=0;i<advertEdit_AdvertType.length;i++){
			if(advertEdit_AdvertType[i].selected){
				advertEdit_AdvertType[i].selected=false;
			}
		}
		$(".advertPicUrlEditMengLong").show();
		document.getElementById('linkTypeRadioEditMengLong').setAttribute('colSpan','1');
		selectOptionEdit(advertPosition_f);
	}
	var form = $("form[name=advertMengLongEditFrom]");
	var linkType = form.find("input[name='linkType']:checked").val();
	if (linkType==0) {
		$("#linkUrlEditMengLong").show();
		$("#textEditMengLong").hide();
        $("#linkUrlMengLong").val("");
	}else if(linkType==1){
		$("#linkUrlEditMengLong").hide();
		$("#textEditMengLong").show();
	}
}
function advertEdit_advertType_Change(x){
	if(x.value==1){
		selectOptionEdit(advertPosition_t_o);
	}else if(x.value==2){
		selectOptionEdit(advertPosition_t_t);
	}else if (x.value==3){
		selectOptionEdit(advertPosition_t_s);
	}else if (x.value==4){
		selectOptionEdit(advertPosition_t_f);
	}else if (x.value==5){
		selectOptionEdit(advertPosition_t_fv);
	}else if (x.value==6){
		selectOptionEdit(advertPosition_t_sx);
	}
	if(x.value==5){
		$(".advertPicUrlEditMengLong").hide();
		document.getElementById('linkTypeRadioEditMengLong').setAttribute('colSpan','3');
	}else{
		$(".advertPicUrlEditMengLong").show();
		document.getElementById('linkTypeRadioEditMengLong').setAttribute('colSpan','1');
		var form = $("form[name=advertMengLongEditFrom]");
		var linkType = form.find("input[name='linkType']:checked").val();
		if (linkType==0) {
			$("#linkUrlEditMengLong").show();
			$("#textEditMengLong").hide();
            $("#linkUrlMengLong").val("");
		}else if(linkType==1){
			$("#linkUrlEditMengLong").hide();
			$("#textEditMengLong").show();
		}
	}
}
//充电桩 选择区域类型
function selectOptionEdit(x){
	$("#advertEdit_AdvertPosition").html("");
	var select="<option  value=''> 请选择 </option>";
    for(var i=0;i<x.length;i++){
			select+="<option  value='"+(i+1)+"'> "+x[i]+" </option>";
		}
		select+="</select>";
		$("#advertEdit_AdvertPosition").append(select)
}
//初始选择
function initSelect(s,x){
	var select="<option  value=''> 请选择 </option>";
	 for(var i=0;i<x.length;i++){
			if(s==(i+1)){
				select+="<option  value='"+(i+1)+"' selected> "+x[i]+" </option>";
			}else{
				select+="<option  value='"+(i+1)+"'> "+x[i]+" </option>";
			}
		}
		select+="</select>";
	 $("#advertEdit_AdvertPosition").append(select)
	 var advertPosition=$("#advertEdit_AdvertPosition").find("option:selected").text();
		if(advertPosition.trim()==="滚动文字"){
			$("#advertPosition_edit_change_span").hide();
		}else{
			$("#advertPosition_edit_change_span").show();
		}
}
function selectOptionType_edit(){
	var t=$("#advertEdit_Type_No").val();
	$("#advertEdit_Type").html("");
	var select="";
	for(var i=0;i<type_advert.length;i++){
		if((i+1)==t){
			select+="<option  value='"+(i+1)+"' selected> "+type_advert[i]+" </option>";
		}else{
			select+="<option  value='"+(i+1)+"'> "+type_advert[i]+" </option>";
		}
	}
	select+="</select>";
	$("#advertEdit_Type").append(select);
}
//选择区域位置为 '滚动文字'
function advertPosition_edit_change(x){
	var advertPosition_edit=$(x).find("option:selected").text();
	if(advertPosition_edit.trim()==="滚动文字"){
		$("#advertPosition_edit_change_span").hide();
	}else{
		$("#advertPosition_edit_change_span").show();
	}
}