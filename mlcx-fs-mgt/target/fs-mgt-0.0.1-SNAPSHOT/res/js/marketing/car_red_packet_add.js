define([],function() {
var carRedPacketAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addCarRedPacket").click(function(){
				carRedPacketAdd.saveCarRedPacket();
			});
			//场站
			$("#carRedPacketAddPark").click(function(){
				$("#carRedPacketAddParkModel").modal("show");
				carRedPacketAdd.carRedPacketPageList();
			});
			//场站列表查询
			$("#carRedPacketAddParkSearch").click(function(){
				carRedPacketAdd.carRedPacketPageList();
	        });
			$("input[name='isOrderAmountLimit']").click(function(){
				var form = $("form[name=carRedPacketAddFrom]");
				var isOrderAmountLimit = form.find("input[name='isOrderAmountLimit']:checked").val();
				if (isOrderAmountLimit==1) {
					$("#orderAmountLimitAddDiv").show();
				}else{
					$("#orderAmountLimitAddDiv").hide();
					form.find("input[name='orderAmountLimit']").val("");
				}
	        });
		},
		//场站列表
		carRedPacketPageList:function(){
			var form = $("form[name=carRedPacketAddParkSerachForm]");
			var parkNo = form.find("input[name='parkNo']").val();
			var table = $('#carRedPacketAddParkList').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : carRedPacketAdd.appPath+"/park/pageListPark.do",
					"data" : function(d) {
						return $.extend({},d,
						{"pageNo" : (d.start / d.length) + 1,
						 "pageSize" : d.length,
						 "parkNo" : parkNo,
						 "isAvailable" : "1"
						 
						});
					},
					"dataSrc" : function(json) {
						json.recordsTotal = json.rowCount;
						json.recordsFiltered = json.rowCount;
						json.data = json.data;
						return json.data;
					},
					error : function(xhr, error, thrown) {
					}
				},
				"columns" : [ 
				{ "title":"","data": "parkNo","width":"5px"},
				{
					"title" : "场站编号",
					"data" : "parkNo"
				},{
					"title" : "场站名称",
					"data" : "parkName"
				}],
				"dom" : "<'row'<''><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$("#carRedPacketAddParkTool").empty().append('<button type="button"  class="btn-new-type car-red-packet-batch-chooseAddPark">选择</button><button type="button"  class="btn-new-type btn-new-type-blue car-red-packet-batch-chooseAddPark-close">关闭</button>');
						$(".car-red-packet-batch-chooseAddPark").on("click",function(){
							var parkNos=[];
							var parkNames=[];
							var len=$('#carRedPacketAddParkList tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择场站！")
							}else{
								$("#carRedPacketAddParkList tbody").find("input:checked").each(function(){
									
									parkNos.push($(this).val());
		        					
									parkNames.push($(this).next('input').val());
		        					
		        				});
								var form = $("form[name=carRedPacketAddFrom]");
								form.find("input[name='parkNo']").val(parkNos);
								form.find("input[name='parkName']").val(parkNames);
								$("#carRedPacketAddParkModel").modal("hide");
								$(".modal-backdrop").hide();
							}
							
							$('#carRedPacketAddParkList thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#carRedPacketAddParkList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#carRedPacketAddParkList tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".car-red-packet-batch-chooseAddPark-close").on("click",function(){
							$("#carRedPacketAddParkModel").modal("hide");
							$(".modal-backdrop").hide();
							$('#carRedPacketAddParkList tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
					   {
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="parkNo" value="' + full.parkNo + '"><input type="hidden" value="' + full.parkName + '">';
						}
					   }
					   ]
			});
		},
		saveCarRedPacket:function() {
			var form = $("form[name=carRedPacketAddFrom]");
			form.ajaxSubmit({
				url : carRedPacketAdd.appPath + "/carRedPacket/addCarRedPacket.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "红包车设置成功", function() {
						closeTab("红包车添加");
						addTab("红包车管理",carRedPacketAdd.appPath+ "/carRedPacket/toCarRedPacketList.do");
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
					closeTab("红包车添加");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var isOrderAmountLimit = form.find("input[name='isOrderAmountLimit']:checked").val();
				$("span[name='parkNoAdd']").empty();
				$("span[name='redPacketAmountAdd']").empty();
				$("span[name='isOrderAmountLimitAdd']").empty();
				if (isOrderAmountLimit==1) {
					$("span[name='orderAmountLimitAdd']").empty();
				}
				$("span[name='isChargeAdd']").empty();
				if (form.find("input[name='parkNo']").val()=="") {
					$("span[name='parkNoAdd']").append("<font color='red'>请先选择目标场站！</font>");
					return false;
				}
				if (form.find("input[name='redPacketAmount']").val()=="") {
					$("span[name='redPacketAmountAdd']").append("<font color='red'>请设置红包金额！</font>");
					return false;
				}else if(!/^[1-9]\d*?$/.test(form.find("input[name='redPacketAmount']").val())){
	            	$("span[name='redPacketAmountAdd']").append("<font color='red'>格式不正确，只能输入大于0的整数！</font>");
					return false;
	            }
				if (isOrderAmountLimit==null||isOrderAmountLimit=="") {
					$("span[name='isOrderAmountLimitAdd']").append("<font color='red'>请选择是否限制订单金额</font>");
					return false;
				}
				if (isOrderAmountLimit==1) {
					if (form.find("input[name='orderAmountLimit']").val()=="") {
						$("span[name='orderAmountLimitAdd']").append("<font color='red'>订单金额限额值不能为空！</font>");
						return false;
					}else if(!/^[0-9]+(.[0-9]{0,2})?$/.test(form.find("input[name='orderAmountLimit']").val())){
						$("span[name='orderAmountLimitAdd']").append("<font color='red'>格式不正确，只能输入大于0的整数或小数！</font>");
						return false;
					}
				}
				var isCharge = form.find("input[name='isCharge']:checked").val();
				if (isCharge==null||isCharge=="") {
					$("span[name='isChargeAdd']").append("<font color='red'>请选择是否需要充电</font>");
					return false;
				}
			}
	 });
	}
}
return carRedPacketAdd;
})
