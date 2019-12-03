define([],function() {
var couponPlanEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//编辑提交
			$("#editCouponPlan").click(function(){
				couponPlanEdit.editCouponPlan();
			});
			//编辑页面的关闭
			$("#closeEditCouponPlan").click(function(){
				closeTab("优惠券方案编辑");
			});
			//上传图片
			$("#editCouponPlanPicUrlButton").click(function(){
				$("#couponPlanPicUrlEditModal").modal("show");
			});
			//编辑图片回填
			$("#editCouponPlanPicBtn").click(function(){
				var form=$("form[name=couponPlanPicUrlEditForm]");
				var img=form.find("input[name=couponPlanPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=couponPlanEditFrom]");
					form1.find("input[name=photoUrl]").val(img);
					form1.find("#couponPlanPicUrlImg").css("background-image", "url(" + couponPlanEdit.imgPath + '/' + img + ")");
					$("#couponPlanPicUrlEditModal").modal("hide");
				}else{
					bootbox.alert("请上传图片！");
				}
			});
			
			//车型列表
			$("#checkEditCarBtn").click(function(){
				couponPlanEdit.pageListPark('carModel');
				$("#couponPlanModal").modal({
					 show:true,
					 backdrop:'static'
				});
			});
			
			//城市列表
			$("#checkEditCityBtn").click(function(){
				couponPlanEdit.pageListPark('city');
				$("#couponPlanModal").modal({
					 show:true,
					 backdrop:'static'
				});
			});
			
			var form = $("form[name=couponPlanEditFrom]");
			form.find("select[name='couponMethod']").change(function(){
				if(form.find("select[name='couponMethod']").val() == 1){
					 form.find(".couponMethod-1").show(); 
					 form.find(".couponMethod-2").hide(); 
					 form.find("input[name=discountAmount]").val("");
					 form.find("input[name=discountMaxAmount]").val("")
				}else{
					 form.find(".couponMethod-1").hide(); 
					 form.find(".couponMethod-2").show(); 
					 form.find("input[name=discount]").val("")
					 form.find("input[name=consumeAmount]").val("")

				}
		    });
			
			form.find("input[name='availableTime']").click(function(){
				
				if(form.find("input[name='availableTime']:checked").val() == 1){
					 form.find(".availableTime-1").show(); 
					 form.find(".availableTime-2").hide(); 
					 form.find("input[name=vailableTime1]").val("")
					 form.find("input[name=vailableTime2]").val("")
				}else{
					 form.find(".availableTime-1").hide(); 
					 form.find(".availableTime-2").show(); 
					 form.find("input[name=availableDays]").val("")
				}
		    });
			form.find("select[name='couponUseType']").change(function(){
				couponPlanEdit.changeData("carModel");
				couponPlanEdit.changeData("city");
			});
		},
		editCouponPlan:function() {
	var form = $("form[name=couponPlanEditFrom]");
	form.ajaxSubmit({
		url : couponPlanEdit.appPath + "/couponPlan/updateOrDelCouponPlan.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("优惠券编辑成功", function() {
					closeTab("优惠券编辑");
					$("#couponPlanList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("优惠券编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='titleEdit']").empty();
			$("span[name='discountAmountEdit']").empty();
			$("span[name='discountEdit']").empty();
			$("span[name='discountMaxAmountEdit']").empty();
			$("span[name='consumeAmountEdit']").empty();
			$("span[name='availableDaysEdit']").empty();
			$("span[name='vailableTime1Edit']").empty();
			$("span[name='vailableTime2Edit']").empty();
			$("span[name='cityNameEdit']").empty();
			$("span[name='carModelNameEdit']").empty();
			
			if ($("#titleId").val()=="") {
				$("span[name='titleEdit']").append("<font color='red'>请输入标题！</font>");
				return false;
			}
			
			if (form.find("select[name='couponMethod']").val() == 2) {
				if(form.find("input[name='discountAmount']").val() == null || form.find("input[name='discountAmount']").val() == ""){
	            	$("span[name='discountAmountEdit']").append("<font color='red'>直减额不能为空！</font>");
					return false;
				}
				if(!/^[0-9]+(.[0-9]{1,2})?$/.test(form.find("input[name='discountAmount']").val())){
	            	$("span[name='discountAmountEdit']").append("<font color='red'>直减额只能输入大于0且小数位最多为两位的正数！</font>");
					return false;
				}
				if (form.find("input[name='consumeAmount']").val()==""){
					$("span[name='consumeAmountEdit']").append("<font color='red'>请输入满减金额！</font>");
					return false;
				}
				if (!/^[0-9]+(.[0-9]{1,2})?$/.test(form.find("input[name='consumeAmount']").val())) {
					$("span[name='consumeAmountEdit']").append("<font color='red'>满减金额只能输入小数位最多为两位的正数！</font>");
					return false;
				}
			}else if(form.find("select[name='couponMethod']").val() == 1){
				if(form.find("input[name='discount']").val() == null || form.find("input[name='discount']").val() == ""){
	            	$("span[name='discountEdit']").append("<font color='red'>折扣率不能为空！</font>");
					return false;
				}
				if(!/^([01](\.0+)?|0\.[0-9]{0,2})$/.test(form.find("input[name='discount']").val())){
					$("span[name='discountEdit']").append("<font color='red'>折扣率只能输入0~1之间且小数位最多为两位的数！</font>");
					return false;
				}
				
				if (form.find("input[name='discountMaxAmount']").val()==""){
					$("span[name='discountMaxAmountEdit']").append("<font color='red'>请输入封顶金额！</font>");
					return false;
				}
				if (form.find("input[name='discountMaxAmount']").val()!=""&&!/^[0-9]+(.[0-9]{1,2})?$/.test(form.find("input[name='discountMaxAmount']").val())) {
					$("span[name='discountMaxAmountEdit']").append("<font color='red'>封顶金额输入有误(正数或小数后两位)！</font>");
					return false;
				}
			}
			
			/*var availableDaysInput = form.find("input[name='availableDays']").val()=="";
			var vailableTime1Input = form.find("input[name='vailableTime1']").val()=="";
			var vailableTime2Input = form.find("input[name='vailableTime2']").val()=="";
			
			if(availableDaysInput && vailableTime1Input && vailableTime2Input){
				$("span[name='availableDaysEdit']").append("<font color='red'>请从有效日期或有效天数选择一项输入</font>");
				return false;
			}else if(!availableDaysInput && (!vailableTime1Input || !vailableTime2Input)){
				$("span[name='availableDaysEdit']").append("<font color='red'>不能同时输入有效日期和有效天数</font>");
				return false;
			}if(availableDaysInput){
				if (vailableTime1Input) {
					$("span[name='vailableTime1Edit']").append("<font color='red'>选择输入有效日期时，开始日期不为空！</font>");
					return false;
				}
				if (vailableTime2Input) {
					$("span[name='vailableTime2Edit']").append("<font color='red'>选择输入有效日期时，结束日期不为空！</font>");
					return false;
				}
				if(new Date(form.find("input[name='vailableTime1']").val())>new Date(form.find("input[name='vailableTime2']").val())){
					$("span[name='vailableTime1Edit']").append("<font color='red'>有效期开始时间不能大于结束时间！</font>");
					return false;
				}
			}else{
				if (!/^\+?[1-9][0-9]*$/.test(form.find("input[name='availableDays']").val())) {
					$("span[name='availableDaysEdit']").append("<font color='red'>选择输入有效天数时，请输入正整数！</font>");
					return false;
				}
			}*/
			
			if (form.find("input[name='carModelId']").val()==""){
				$("span[name='carModelNameEdit']").append("<font color='red'>请选择车型！</font>");
				return false;
			}
			if (form.find("input[name='cityId']").val()==""){
				$("span[name='cityNameEdit']").append("<font color='red'>请选择城市！</font>");
				return false;
			}
			if (form.find("input[name='maxQuantity']").val()==""){
				$("span[name='maxQuantityEdit']").append("<font color='red'>请输入优惠卷方案发放数量！</font>");
				return false;
			}
			if(form.find("input[name='maxQuantity']").val() != null && !/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='maxQuantity']").val())){
				$("span[name='maxQuantityEdit']").append("<font color='red'>优惠券限制数量只能为正整数！</font>");
				return false;
			}
			if(form.find("input[name='availableTime']:checked").val() == 1){
				if (form.find("input[name='availableDays']").val()==""){
					$("span[name='availableDaysEdit']").append("<font color='red'>请输入有效天数！</font>");
					return false;
				}
				if (!/^\+?[1-9][0-9]*$/.test(form.find("input[name='availableDays']").val())) {
					$("span[name='availableDaysEdit']").append("<font color='red'>有效天数必须为正整数！</font>");
					return false;
				}
			}else{
				if (form.find("input[name='vailableTime1']").val()=="" || form.find("input[name='vailableTime1']").val()==null) {
					$("span[name='vailableTime1Edit']").append("<font color='red'>有效开始日期不为空！</font>");
					return false;
				}
				if (form.find("input[name='vailableTime2']").val()=="" || form.find("input[name='vailableTime2']").val()==null) {
					$("span[name='vailableTime2Edit']").append("<font color='red'>有效结束日期不为空！</font>");
					return false;
				}
				if(new Date(form.find("input[name='vailableTime1']").val())>new Date(form.find("input[name='vailableTime2']").val())){
					$("span[name='vailableTime1Edit']").append("<font color='red'>有效期开始日期不能大于结束日期！</font>");
					return false;
				}
			}
		}
	});
},pageListPark:function (dataType) {
	var tpl = $("#couponPlanBtnTpl").html();
	// 预编译模板
	var template = Handlebars.compile(tpl);
	
	//获取配置
	var config = couponPlanEdit.getTypeConfig(dataType);
	var form = $("form[name=couponPlanEditFrom]");
	//优惠使用类型
	var couponUseType = form.find("select[name='couponUseType']").val();
	
	if(config.catCode == "CITY"){
		var url = couponPlanEdit.appPath+'/dataDictItem/dataDictItemPageList.do?dataDictCatCode='+config.catCode;
	}else{
		var url = couponPlanEdit.appPath+"/carModelAll/pageListCarModelAll.do";
	}
	if(couponUseType=="2"&&config.catCode!="CITY"){
		url = couponPlanEdit.appPath+'/carModel/pageListCarModel.do?onOffLineStatus=1';
	}
	$('#couPonPlanLists').dataTable( {
		searching:false,
		destroy: true,
		"ajax": {
			"type": "POST",
			"url": url,
			"data": function ( d ) {
				return $.extend( {}, d, {
					"pageNo": (d.start/d.length)+1,
					"pageSize":d.length
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
		"columns": config.columns,
	   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
	   "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
	   initComplete: function () {
		   	$(this).find("thead tr th:first-child").prepend('');
			//$("#parkToolss").append('<button type="button"  class="btn btn-default btn-sm coupanPlan-batch-sel">绑定优惠券</button>');
			/*$(".coupanPlan-batch-sel").on("click",function(){
				$('#couPonPlanLists thead input[type="checkbox"]').on("click",function(e){
	   				if(this.checked){
				         $('#couPonPlanLists tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
				    } else {
				         $('#couPonPlanLists tbody input[type="checkbox"]:checked').prop("checked",false);
				    }
				});
			});*/
			$("#parkToolss").empty().append('<button type="button"  class="btn-new-type coupanPlan-batch-sel">确定</button><button type="button"  class="btn-new-type btn-new-type-blue coupanPlan-batch-close">关闭</button>');
//			$("#parkToolss").append('');
			$(".coupanPlan-batch-sel").on("click",function(){
				couponPlanEdit.backupData(dataType,1);
				$("#couponPlanModal").modal("hide");
				 $('#couPonPlanLists tbody input[type="checkbox"]:checked').prop("checked",false);
			});
			$(".coupanPlan-batch-close").on("click",function(){
				couponPlanEdit.backupData(dataType,2);
				$("#couponPlanModal").modal("hide");
			});
		},
		"drawCallback": function( settings ) {
			couponPlanEdit.couponPlanModalCallback(dataType)
	    },
		"columnDefs": [
			  {
				"targets" : [0],
				"orderable":false,
				"render" : function(data, type, full, meta) {
					if(couponUseType=="2"&&config.catCode!="CITY"){
						var text = '<input type="checkbox"  name="carModelId" value="' + full.carModelId + '" nameValue="'+full.carModelName+'" ';
						var data = couponPlanEdit.getSelectedData(dataType);
						for(var i = 0; i < data.length;i ++){
							if(data[i].id == full.carModelId){
								text += ' checked="checked" '
									break;
							}
						}
						return text +' >';
					}else if(config.catCode=="CITY"){//城市
						var text = '<input type="checkbox"  name="dataDictItemId" value="' + full.dataDictItemId + '" nameValue="'+full.itemValue+'" ';
						var data = couponPlanEdit.getSelectedData(dataType);
						for(var i = 0; i < data.length;i ++){
							if(data[i].id == full.dataDictItemId){
								text += ' checked="checked" '
									break;
							}
						}
						return text +' >';
					}else{
						var text = '<input type="checkbox"  name="carSeriesId" value="' + full.carSeriesId + '" nameValue="'+full.carSeriesName+'" ';
						var data = couponPlanEdit.getSelectedData(dataType);
						for(var i = 0; i < data.length;i ++){
							if(data[i].id == full.carSeriesId){
								text += ' checked="checked" '
									break;
							}
						}
						return text +' >';
					}
				}
			}
		]
	});
},getTypeConfig:function (type) {
	if(type == 'carModel'){//车型
		var form = $("form[name=couponPlanEditFrom]");
		//优惠使用类型
		var couponUseType = form.find("select[name='couponUseType']").val();
		if(couponUseType=="1"){
			return {
				catCode: "",
				columns:[
				               { "title":"","data": "carSeriesId","width":"20%"},
				   			   { "title":"车型","data": "carSeriesName","width":"80%"}
				 ]
			};
		}else{
			return {
				catCode: "",
				columns:[
				               { "title":"","data": "carModelId","width":"20%"},
				   			   { "title":"车型","data": "carModelName","width":"80%"}
				 ]
			};
		}
	}else if(type == 'city'){//城市
		return {
			catCode: "CITY",
			columns:[
			               { "title":"","data": "dataDictItemId","width":"20%"},
			   			   { "title":"城市","data": "itemValue","width":"80%"}
			 ]
		};
	}
},backupData :function(type,opreateType){//操作备份数据(opreateType:1备份，2还原)
	var form=$("form[name=couponPlanEditFrom]");
	var idInput,nameInput,backIdInput,backNameInput;
	if(type == 'carModel'){//车型
		idInput = "input[name=carModelId]";
		nameInput = "input[name=carModelName]";
		backIdInput = "input[name=backUpCarModelId]";
		backNameInput = "input[name=backUpCarModelName]";
	}else if(type == 'city'){//城市
		idInput = "input[name=cityId]";
		nameInput = "input[name=cityName]";
		backIdInput = "input[name=backUpCityId]";
		backNameInput = "input[name=backUpCityName]";
	}
	if(opreateType == '1'){//临时备份
		form.find(backIdInput).val(form.find(idInput).val());
		form.find(backNameInput).val(form.find(nameInput).val());
	}else if(opreateType == '2'){//根据备份还原
		form.find(idInput).val(form.find(backIdInput).val());
		form.find(nameInput).val(form.find(backNameInput).val());
	}
	
},getSelectedData:function (type) {
	var data = [];
	var id = "";
	var name = "";
	var form=$("form[name=couponPlanEditFrom]");

	if(type == 'carModel'){//车型
		var couponUseType = form.find("select[name='couponUseType']").val();
			id = form.find("input[name=carModelId]").val();
			name = form.find("input[name=carModelName]").val();
	}else if(type == 'city'){//城市
		id = form.find("input[name=cityId]").val();
		name = form.find("input[name=cityName]").val();
	}
	
	if(id != null && id != "" && name != null && name != ""){
		var ids = id.split(",");
		var names = name.split(",");
		if(ids.length != names.length){
			bootbox.alert("选择的数据错误");
		}else{
			for(var i = 0; i < ids.length;i ++){
				var object = new Object;
				object.id = ids[i];
				object.name = names[i];
				data.push(object);
			}
		}
	}
	return data;
},showSelect:function (type,datas) {//根据类型（如：city,carModel）放入指定的input框中展示
	var idsString = "";
	var nameString = "";
	
	for(var i = 0; i < datas.length;i ++){
		idsString += datas[i].id + ",";
		nameString += datas[i].name + ",";
	}
	idsString = idsString.substring(0,idsString.length-1);
	nameString = nameString.substring(0,nameString.length-1);
	var form=$("form[name=couponPlanEditFrom]");

	if(type == 'carModel'){//车型
		form.find("input[name=carModelId]").val(idsString)
		form.find("input[name=carModelName]").val(nameString)
	}else if(type == 'city'){//城市
		form.find("input[name=cityId]").val(idsString)
		form.find("input[name=cityName]").val(nameString)
	}
	
},getRemovedArray  :function(unSelectIds,oldArray){//得到已排除未选择的项的数组
	var delIndexs = [];
	for(var i = 0; i < unSelectIds.length;i ++){
		for(var j = 0; j < oldArray.length;j ++){
			if(oldArray[j].id == unSelectIds[i]){
				delIndexs.push(j);
				break;
			}
		}
		
	}
	
	if(delIndexs.length > 0){
		var newArray = [];
		for(var i = 0; i < oldArray.length;i ++){
			var isAdd = true;
			for(var j = 0; j < delIndexs.length;j ++){
				if(i == delIndexs[j]){
					isAdd = false;
					break;
				}
			}
			if(isAdd){
				newArray.push(oldArray[i]);
			}
		}
		return newArray;
	}
	return oldArray;
},getAddedArray :function(selectData,oldArray){////得到已新增选择的项的数组
	for(var i = 0; i < selectData.length;i ++){
		var isPush = true;
		for(var j = 0; j < oldArray.length;j ++){
			if(oldArray[j].id == selectData[i].id){
				isPush = false;
			}
		}
		if(isPush){
			oldArray.push(selectData[i]);
		}
	}
	return oldArray;
},couponPlanModalCallback:function (type) {
	var form = $("form[name=couponPlanEditFrom]");
	//优惠使用类型
	var couponUseType = form.find("select[name='couponUseType']").val();
	//获取配置
	var config = couponPlanEdit.getTypeConfig(type);
	
	if(config.catCode=="CITY"){
		var obj = $("input[name=dataDictItemId]");
	}else if(couponUseType=="1"&&config.catCode!="CITY"){
		var obj = $("input[name=carSeriesId]");
	}
	
	if(couponUseType=="2"&&config.catCode!="CITY"){
		obj = $("input[name=carModelId]");
	}
	obj.click(function(){
		var selectIds=[];
		var unSelectIds=[];
		var selectData=[];
		var selectNames=[];
		var len=$('#couPonPlanLists tbody input[type="checkbox"]:checked');
		$("#couPonPlanLists tbody").find("input:checked").each(function(){
			var object = new Object;
			object.id = $(this).val();
			object.name = $(this).attr('nameValue');
			selectData.push(object);
		    });
		$("#couPonPlanLists tbody").find("input[type=checkbox]").not("input:checked").each(function(){
			unSelectIds.push($(this).val());
		}); 
		var carModels = couponPlanEdit.getSelectedData(type);
		//新增
		carModels = couponPlanEdit.getAddedArray(selectData,carModels);
		//移除
		carModels = couponPlanEdit.getRemovedArray(unSelectIds,carModels);
		//展示
		couponPlanEdit.showSelect(type,carModels);
		//重新赋值couponPlanEdit.carModels = carModels;
		//bootbox.alert("绑定成功");
	});
},changeData :function (type){////得到已新增选择的项的数组
	var selectIds=[];
	var unSelectIds=[];
	var selectData=[];
	var selectNames=[];
	var len=$('#couPonPlanLists tbody input[type="checkbox"]:checked');
	$("#couPonPlanLists tbody").find("input:checked").each(function(){
		var object = new Object;
		object.id = $(this).val();
		object.name = $(this).attr('nameValue');
		selectData.push(object);
		unSelectIds.push($(this).val());
	    });
	var carModels = couponPlanEdit.getSelectedData(type);
	//移除
	carModels = couponPlanEdit.getRemovedArray(unSelectIds,selectData);
	//展示
	couponPlanEdit.showSelect(type,carModels);
	
}}
return couponPlanEdit;
})