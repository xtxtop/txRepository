define([],function() {
var redeemCodeAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addRedeemCode").click(function(){
				redeemCodeAdd.saveRedeemCode();
			});
			//新增页面的关闭
			$("#closeAddRedeemCode").click(function(){
				closeTab("新增兑换码");
			});
			
			//方案列表
			$("#checkAddPlanBtnOfRedeemCodeAdd").click(function(){
				redeemCodeAdd.pageListPlan();
				$("#couponPlanModalAdd").modal({
					 show:true,
					 backdrop:'static'
				});
			});
		},
		saveRedeemCode:function() {
			var form = $("form[name=redeemCodeAddFrom]");
			var planNosModals = $(".planNoModal");
			if(planNosModals != null && planNosModals.length > 0){
				var data = [];
				for(var i = 0; i < planNosModals.length;i ++){
					var redQutity =  planNosModals.eq(i).find(".val").val();
					var	temp = new Object;
					var name = planNosModals.eq(i).find(".plan-name").html();
					temp.planNo = planNosModals.eq(i).find(".plan-no").html();
					temp.redQutity = redQutity;
					data[data.length] = temp
				}
				form.find('input[name=couponPlanString]').val(JSON.stringify(data))
			}
			
			form.ajaxSubmit({
				url : redeemCodeAdd.appPath + "/redeemCode/addRedeemCode.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码添加成功", function() {
							closeTab("新增兑换码");
							$("#redeemCodeList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码添加失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='redNameAdd']").empty();
					$("span[name='availableTime1Add']").empty();
					$("span[name='availableTime2Add']").empty();
					$("span[name='planNoAdd']").empty();
					if (form.find("input[name='redName']").val()=="") {
						$("span[name='redNameAdd']").append("<font color='red'>请输入兑换码名称</font>");
						return false;
					}
					/*if (form.find("input[name='availableTimes']").val()=="") {
						$("span[name='availableTimesAdd']").append("<font color='red'>请输入兑换次数！</font>");
						return false;
					}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='availableTimes']").val())){
		            	$("span[name='availableTimesAdd']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
						return false;
		            }*/
					
					
					if (form.find("input[name='availableTime1']").val()=="" || form.find("input[name='availableTime1']").val()==null) {
						$("span[name='availableTime1Add']").append("<font color='red'>有效开始日期不为空！</font>");
						return false;
					}
					if (form.find("input[name='availableTime2']").val()=="" || form.find("input[name='availableTime2']").val()==null) {
						$("span[name='availableTime2Add']").append("<font color='red'>有效结束日期不为空！</font>");
						return false;
					}
					if(new Date(form.find("input[name='availableTime1']").val())>new Date(form.find("input[name='availableTime2']").val())){
						$("span[name='availableTime1Add']").append("<font color='red'>有效期开始日期不能大于结束日期！</font>");
						return false;
					}
					
					var planNosModals = $(".planNoModal");
					if(planNosModals == null || planNosModals.length <= 0){
						$("span[name='planNoAdd']").append("<font color='red'>请选择一个优惠券方案！</font>");
						return false;
					}
					for(var i = 0; i < planNosModals.length;i ++){
						var redQutity =  planNosModals.eq(i).find(".val").val();
						var name = planNosModals.eq(i).find(".plan-name").html();
						if(redQutity == null ||redQutity == ""){
							$("span[name='planNoAdd']").append("<font color='red'>请设置["+name+"]方案的 兑换数量！</font>");
							return false;
						}
						if(!/^[0-9]*[1-9][0-9]*$/.test(redQutity)){
							$("span[name='planNoAdd']").append("<font color='red'>请设置["+name+"]方案的 兑换数量为正整数！</font>");
							return false;
						}
					}
				}
			});
		},pageListPlan:function () {
			var tpl = $("#couponPlanBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(tpl);
			
			$('#couPonPlanListsOfRedeemCodeaAdd').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": redeemCodeAdd.appPath+'/couponPlan/pageListCouponPlan.do?censorStatus=1&isAvailable=1',
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
				"columns": [
				               { "title":"","data": "planNo","width":"10%"},
				   			   { "title":"方案标题","data": "title"},
				   			   { "title":"优惠方式","data": "couponMethod" },
				   			   { "title":"折扣率/直减金额","data": "couponMethod" },
				   			   { "title":"有效起始日期","data": "vailableTime1" },
				   			   { "title":"有效结束日期","data": "vailableTime2" },
				   			   { "title":"有效天数（天）","data": "availableDays" },
				   			   { "title":"剩余可发放数量","data": "maxQuantity" }
				 ],
			   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
			   "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				   
				   	$(this).find("thead tr th:first-child").prepend('');
					$("#couponPlanToolsss").empty().append('<button type="button"  class="btn-new-type coupanPlan-batch-add">确定</button><button type="button"  class="btn-new-type btn-new-type-blue coupanPlan-batch-close">关闭</button>');
//					$("#couponPlanToolsss").append('');
					
					$(".coupanPlan-batch-add").on("click",function(){
						//couponPlanAdd.backupData(dataType,1);

						redeemCodeAdd.showSelect(JSON.parse($('input[name=tempinput]').val()));
						redeemCodeAdd.resetPlanNosModal(redeemCodeAdd.getSelectedData());
						$("#couponPlanModalAdd").modal("hide");
						$('#couPonPlanListsOfRedeemCodeaAdd tbody input[type="checkbox"]:checked').prop("checked",false);
					});
					$(".coupanPlan-batch-close").on("click",function(){
						//couponPlanAdd.backupData(dataType,2);
						$("#couponPlanModalAdd").modal("hide");
					});
				},
				"drawCallback": function( settings ) {
					redeemCodeAdd.couponPlanModalCallback()
			    },
				"columnDefs": [
					  {
						"targets" : [0],
						"orderable":false,
						"render" : function(data, type, full, meta) {
							var text = '<input type="checkbox"  name="dataDictItemId" value="' + full.planNo + '" nameValue="'+full.title+'" ';
							var data = redeemCodeAdd.getSelectedData();
							for(var i = 0; i < data.length;i ++){
								if(data[i].id == full.planNo){
									text += ' checked="checked" '
									break;
								}
							}
							return text +' >';
						}
					},{
					    "targets": [2],
					    "render": function(a,b, c, d) {
					    		if(a==1){
					    			return "折扣";
					    		}else if(a==2){
					    			return "直减";
					    		}else{
					    			return "";
					    		}
					    }
					},{
					    "targets": [3],
					    "render": function(a,b, c, d) {
					    		if(a==1){
					    			var discount = c.discount;
					    			if(discount == 0 || discount == 1){
					    				return c.discount+".00"
					    			}
					    			return ""+c.discount;
					    		}else if(a==2){
					    			return ""+c.discountAmount;
					    		}else{
					    			return "";
					    		}
					    }
					},{
					    "targets": [4,5],
					    "render": function(data, type, row, meta) {
					    	if(data){
					    		var now = moment(data); 
	        	            	return now.format('YYYY-MM-DD'); 
						    	}else{
						    		return "";
						    	}
					    }
					},{
					    "targets": [7],
					    "render": function(data, type, row, meta) {
					    	if(row.existingQuantity != null){
					    		return "" + (row.maxQuantity - row.existingQuantity);
					    	}
					    	return "" + row.maxQuantity;
					    }
					}
				]
			});
		},
		
		getSelectedData:function () {
			var data = [];
			var id = "";
			var name = "";
			id = $("input[name=planNo]").val();
			name = $("input[name=planName]").val();
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
		},showSelect:function (datas) {
			var idsString = "";
			var nameString = "";
			
			for(var i = 0; i < datas.length;i ++){
				idsString += datas[i].id + ",";
				nameString += datas[i].name + ",";
			}
			idsString = idsString.substring(0,idsString.length-1);
			nameString = nameString.substring(0,nameString.length-1);
			var form=$("form[name=redeemCodeAddFrom]");
			$("input[name=planNo]").val(idsString);
			$("input[name=planName]").val(nameString);
		},couponPlanModalCallback:function () {
			$("input[name=dataDictItemId]").click(function(){
				var selectIds=[];
				var unSelectIds=[];
				var selectData=[];
				var selectNames=[];
				var len=$('#couPonPlanListsOfRedeemCodeaAdd tbody input[type="checkbox"]:checked');
				$("#couPonPlanListsOfRedeemCodeaAdd tbody").find("input:checked").each(function(){
					var object = new Object;
					object.id = $(this).val();
					object.name = $(this).attr('nameValue');
					selectData.push(object);
				    });
				$("#couPonPlanListsOfRedeemCodeaAdd tbody").find("input[type=checkbox]").not("input:checked").each(function(){
					unSelectIds.push($(this).val());
				}); 
				var carModels = redeemCodeAdd.getSelectedData();
				//新增
				carModels = redeemCodeAdd.getAddedArray(selectData,carModels);
				//移除
				carModels = redeemCodeAdd.getRemovedArray(unSelectIds,carModels);
				//展示
				
				var tempinput = $('input[name=tempinput]')
				if(carModels.length > 0){
					tempinput.val(JSON.stringify(carModels))
				}else{
					tempinput.val("");
				}
				//redeemCodeAdd.showSelect(carModels);
				//bootbox.alert("绑定成功");
			});
		},getAddedArray(selectData,oldArray){////得到已新增选择的项的数组
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
		},getRemovedArray(unSelectIds,oldArray){//得到已排除未选择的项的数组
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
		},resetPlanNosModal(datas){
			var planNosModals = $(".planNoModal");
			var oldData = [];
			for(var i = 0; i < planNosModals.length;i ++){
				var	temp = new Object;
				temp.id = planNosModals.eq(i).find(".plan-no").html();
				temp.name = planNosModals.eq(i).find(".plan-name").html();
				temp.val = planNosModals.eq(i).find(".val").val();
				oldData[oldData.length] = temp
			}
			result = "";
			if(oldData == null || oldData.length == 0){
				for(var i = 0; i < datas.length;i ++){
					var text = '<div class="form-group"><label class="control-label reason key"></label>'
						+ '<div class="planNoModal">'
						+ '<span class = "plan-no" hidden="true">'+datas[i].id+'</span>'
						+ '<span class = "plan-name">'+datas[i].name+'</span>' 
						+ ' 兑换数量：<input class="form-control val"/>'
						+ '</div><div style="margin-top:7px;"></div></div>';
					result += text;
				}
			}else{
				for(var i = 0; i < datas.length;i ++){
					for(var j = 0; j < oldData.length;j ++){
						if(datas[i].id == oldData[j].id){
							var text = '<div class="form-group"><label class="control-label reason key"></label>'
								+ '<div class="planNoModal">'
								+ '<span class = "plan-no" hidden="true">'+oldData[j].id+'</span>'
								+ '<span class = "plan-name">'+oldData[j].name+'</span>' 
								+ ' 兑换数量：<input class="form-control val" value ="'+oldData[j].val +'"/>'
								+ '</div><div style="margin-top:7px;"></div></div>';
							result += text;
							break;
						}
						if(j == oldData.length - 1){
							var text = '<div class="form-group"><label class="control-label reason key"></label>'
								+ '<div class="planNoModal">'
								+ '<span class = "plan-no" hidden="true">'+datas[i].id+'</span>'
								+ '<span class = "plan-name">'+datas[i].name+'</span>' 
								+ ' 兑换数量：<input class="form-control val"/>'
								+ '</div><div style="margin-top:7px;"></div></div>';
							result += text;
						}
					}
				}
			}
			$('.planNosModal').html(result)
			//return result;
		}
	}
	return redeemCodeAdd;
})
