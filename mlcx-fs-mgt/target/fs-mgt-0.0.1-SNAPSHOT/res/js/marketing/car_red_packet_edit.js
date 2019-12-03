define([],function() {
var carRedPacketEdit = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#editCarRedPacket").click(function(){
				carRedPacketEdit.editCarRedPacket();
			});
			
			//关闭
			$("#closeEditCarRedPacket").click(function(){
				closeTab("红包车编辑");
			});
			
			//场站
			$("#carRedPacketEditPark").click(function(){
				$("#carRedPacketEditParkModel").modal("show");
				carRedPacketEdit.carRedPacketPageList();
			});
			//场站列表查询
			$("#carRedPacketEditParkSearch").click(function(){
				carRedPacketEdit.carRedPacketPageList();
	        });
			
			var form = $("form[name=carRedPacketEditFrom]");
			var isOrderAmountLimit = form.find("input[name='isOrderAmountLimit']:checked").val();
			if (isOrderAmountLimit==1) {
				$("#orderAmountLimitEditDiv").show();
			}else{
				$("#orderAmountLimitEditDiv").hide();
				
			}
			
			
			$("input[name='isOrderAmountLimit']").click(function(){
				var form = $("form[name=carRedPacketEditFrom]");
				var isOrderAmountLimit = form.find("input[name='isOrderAmountLimit']:checked").val();
				if (isOrderAmountLimit==1) {
					$("#orderAmountLimitEditDiv").show();
				}else{
					$("#orderAmountLimitEditDiv").hide();
					
				}
	        });
		},
		//场站列表
		carRedPacketPageList:function(){
			var form = $("form[name=carRedPacketEditParkSerachForm]");
			var parkNo = form.find("input[name='parkNo']").val();
			var carRedPacketEditParkBtnTpl = $("#carRedPacketEditParkBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(carRedPacketEditParkBtnTpl);
			var table = $('#carRedPacketEditParkList').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : carRedPacketEdit.appPath+"/park/pageListPark.do",
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
				"dom" : "<'row'<'#carRedPacketEditParkTool.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#carRedPacketEditParkTool").append('<button type="button"  class="btn btn-default btn-sm car-red-packet-batch-chooseEditPark">选择</button>');
						$("#carRedPacketEditParkTool").append('<button type="button"  class="btn btn-default btn-sm car-red-packet-batch-chooseEditPark-close">关闭</button>');
						$(".car-red-packet-batch-chooseEditPark").on("click",function(){
							var parkNos=[];
							var parkNames=[];
							var len=$('#carRedPacketEditParkList tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择场站！")
							}else{
								$("#carRedPacketEditParkList tbody").find("input:checked").each(function(){
									
									parkNos.push($(this).val());
		        					
									parkNames.push($(this).next('input').val());
		        					
		        				});
								var form = $("form[name=carRedPacketEditFrom]");
								form.find("input[name='parkNo']").val(parkNos);
								form.find("input[name='parkName']").val(parkNames);
								$("#carRedPacketEditParkModel").modal("hide");
								$(".modal-backdrop").hide();
							}
							
							$('#carRedPacketEditParkList thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#carRedPacketEditParkList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#carRedPacketEditParkList tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".car-red-packet-batch-chooseEditPark-close").on("click",function(){
							$("#carRedPacketEditParkModel").modal("hide");
							$(".modal-backdrop").hide();
							$('#carRedPacketEditParkList tbody input[type="checkbox"]:checked').prop("checked",false);
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
		editCarRedPacket:function() {
			var form = $("form[name=carRedPacketEditFrom]");
			form.ajaxSubmit({
				url : carRedPacketEdit.appPath + "/carRedPacket/updateCarRedPacket.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "红包车编辑成功", function() {
						closeTab("红包车编辑");
						$("#carRedPacketList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				var isOrderAmountLimit = form.find("input[name='isOrderAmountLimit']:checked").val();
				$("span[name='parkNoEdit']").empty();
				$("span[name='redPacketAmountEdit']").empty();
				$("span[name='isOrderAmountLimitEdit']").empty();
				if (isOrderAmountLimit==1) {
					$("span[name='orderAmountLimitEdit']").empty();
				}
				$("span[name='isChargeEdit']").empty();
				if (form.find("input[name='parkNo']").val()=="") {
					$("span[name='parkNoEdit']").append("<font color='red'>请先选择目标场站！</font>");
					return false;
				}
				if (form.find("input[name='redPacketAmount']").val()=="") {
					$("span[name='redPacketAmountEdit']").append("<font color='red'>请设置红包金额！</font>");
					return false;
				}else if(!/^[1-9]\d*?$/.test(form.find("input[name='redPacketAmount']").val())){
	            	$("span[name='redPacketAmountEdit']").append("<font color='red'>格式不正确，只能输入大于0的整数！</font>");
					return false;
	            }
				if (isOrderAmountLimit==null||isOrderAmountLimit=="") {
					$("span[name='isOrderAmountLimitEdit']").append("<font color='red'>请选择是否限制订单金额</font>");
					return false;
				}
				if (isOrderAmountLimit==1) {
					if (form.find("input[name='orderAmountLimit']").val()=="") {
						$("span[name='orderAmountLimitEdit']").append("<font color='red'>订单金额限额值不能为空！</font>");
						return false;
					}else if(!/^[0-9]+(.[0-9]{0,2})?$/.test(form.find("input[name='orderAmountLimit']").val())){
						$("span[name='orderAmountLimitEdit']").append("<font color='red'>格式不正确，只能输入大于0的整数或小数！</font>");
						return false;
					}
				}
				var isCharge = form.find("input[name='isCharge']:checked").val();
				if (isCharge==null||isCharge=="") {
					$("span[name='isChargeEdit']").append("<font color='red'>请选择是否需要充电</font>");
					return false;
				}
			}
	 });
	}
}
return carRedPacketEdit;
})
