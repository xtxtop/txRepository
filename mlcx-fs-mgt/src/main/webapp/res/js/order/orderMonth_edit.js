define([],function() {	
var orderMonthEdit = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			var form = $("form[name=orderMonthEditForm]");
		
			$("#orderMonthCarEdit").on("hidden.bs.modal", function() {  
				
            });
			
			
			$(".EditorderMonthCar").click(function(){
				
					$("#orderMonthCarEdit").modal({
						 show:true,
						 backdrop:'static'
					});
					orderMonthEdit.pageOrderMonthCar();
				
				
				
            });
		
			//增加提交
			$("#editOrderMonth").click(function(){
				orderMonthEdit.saveOrderMonth();
			});
			//增加页面关闭
			$("#closeOrderMonthEdit").click(function(){
				closeTab("月租订单编辑");
			});
			
			
			
			
			
			
		},
		
		
		
		
		pageOrderMonthCar:function(){
			var btnTpl = $("#orderMonthBtnTplEdit").html();
			// 预编译模板
			var template = Handlebars.compile(btnTpl);
			var table = $('#orderMonthListEdit').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : orderMonthEdit.appPath+"/orderMonth/pageListOrderMonthCar.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length
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
				{ "title":"","data": "carNo","width":"5px"},
				{
					"title" : "车辆编号",
					"data" : "carNo"
				},{
					"title" : "车牌号",
					"data" : "carPlateNo"
				},{
					"title" : "品牌名称",
					"data" : "carBrandName"
				}, {
					"title" : "车型名称",
					"data" : "carModelName"
				} ],
				"dom" : "<'row'<'#orderMonthToolssssEdit.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#orderMonthToolssssEdit").append('<button type="button"  class="btn btn-default btn-sm sysOrderMonth-batch-Editdel">选择车辆</button>');
						$("#orderMonthToolssssEdit").append('<button type="button"  class="btn btn-default btn-sm sysOrderMonth-batch-Editclose">关闭</button>');
						$(".sysOrderMonth-batch-Editdel").on("click",function(){
							var ids=[];
							var carInfo="";
							var n = 0;
							var len=$('#orderMonthListEdit tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择车辆！")
							}else{
								$("#orderMonthListEdit tbody").find("input:checked").each(function(){
									n += 1;
									
									carInfo += n+"、"+$(this).next('input').val()+","+$(this).next().next('input').val()+"<br/>";
									
									
		        					ids.push($(this).val());
		        					
		        				});
								$("#carsNs").val(ids);
								$("#orderMonthCarEdit").modal("hide");
								$("#carNmEd").empty();
								$("#carNmEd").html(carInfo);
								
							}
							
							$('#orderMonthListEdit thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#orderMonthListEdit tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#orderMonthListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysOrderMonth-batch-Editclose").on("click",function(){
							$("#orderMonthCarEdit").modal("hide");
							$(".modal-backdrop").hide();
							 $('#orderMonthListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
					   {
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="carNo" value="' + full.carNo + '"><input type="hidden" value="' + full.carBrandName + '"><input type="hidden"  value="' + full.carModelName + '">';
						}
					   }
					   ]
			});
		},
		saveOrderMonth:function() {
			debugger
			var form = $("form[name=orderMonthEditForm]");
			form.ajaxSubmit({
				url : orderMonthEdit.appPath + "/orderMonth/editOrderMonth.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
							closeTab("月租订单编辑");
							$("#orderMonthList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='memberNameed']").empty();
					$("span[name='mobilePhoneed']").empty();
					$("span[name='orderAmounted']").empty();
					$("span[name='discountAmounted']").empty();
					$("span[name='deposited']").empty();
					$("span[name='insuranceed']").empty();
					$("span[name='payableAmounted']").empty();
					$("span[name='paymentTimeed']").empty();
					$("span[name='actualNumbered']").empty();
					$("span[name='actualStartTimeed']").empty();
					$("span[name='actualEndTimeed']").empty();
					$("span[name='actualTakeLoactoned']").empty();
					$("span[name='actualTerminalParkNameed']").empty();
					$("span[name='actualTerminalParkNameed']").empty();
//					$("span[name='carNmEdit']").empty();
					
					/*var cn = $("#carNm").text();*/
					
					
					
					if (form.find("input[name='memberName']").val()=="") {
						$("span[name='memberNameed']").append("<font color='red'>请输入客户名称！</font>");
						return false;
					}
					if (form.find("input[name='mobilePhone']").val()=="") {
						$("span[name='mobilePhoneed']").append("<font color='red'>请输入手机号码！</font>");
						return false;
					}
					if (form.find("input[name='orderAmount']").val()=="") {
						$("span[name='orderAmounted']").append("<font color='red'>请输入订单金额！</font>");
						return false;
					}
					
					if (form.find("input[name='orderAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='orderAmount']").val())) {
						$("span[name='orderAmounted']").append("<font color='red'>订单金额输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='discountAmount']").val()=="") {
						$("span[name='discountAmounted']").append("<font color='red'>请输入折扣金额！</font>");
						return false;
					}
					
					if (form.find("input[name='discountAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='discountAmount']").val())) {
						$("span[name='discountAmounted']").append("<font color='red'>折扣金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='deposit']").val()=="") {
						$("span[name='deposited']").append("<font color='red'>请输入押金金额！</font>");
						return false;
					}
					
					if (form.find("input[name='deposit']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='deposit']").val())) {
						$("span[name='deposited']").append("<font color='red'>押金金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='insurance']").val()=="") {
						$("span[name='insuranceed']").append("<font color='red'>请输入保险金额！</font>");
						return false;
					}
					
					if (form.find("input[name='insurance']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='insurance']").val())) {
						$("span[name='insuranceed']").append("<font color='red'>保险金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='payableAmount']").val()=="") {
						$("span[name='payableAmounted']").append("<font color='red'>请输入应付金额！</font>");
						return false;
					}
					
					if (form.find("input[name='payableAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='payableAmount']").val())) {
						$("span[name='payableAmounted']").append("<font color='red'>应付金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='paymentTime']").val()=="") {
						$("span[name='paymentTimeed']").append("<font color='red'>请输入支付时间！</font>");
						return false;
					}
					if (form.find("input[name='actualNumber']").val()=="") {
						$("span[name='actualNumbered']").append("<font color='red'>请输入租车数量！</font>");
						return false;
					}
					if(form.find("input[name='actualNumber']").val()!=""&&!/^\+?[1-9]\d*$/.test(form.find("input[name='actualNumber']").val())){
						$("span[name='actualNumbered']").append("<font color='red'>租车数量不正确 必须是大于0的正整数</font>");
						return false;
					}
					if (form.find("input[name='actualStartTime']").val()=="") {
						$("span[name='actualStartTimeed']").append("<font color='red'>请输入租车开始时间！</font>");
						return false;
					}
					if (form.find("input[name='actualEndTime']").val()=="") {
						$("span[name='actualEndTimeed']").append("<font color='red'>请输入租车结束时间！</font>");
						return false;
					}
					
					if (form.find("input[name='actualTakeLoacton']").val()=="") {
						$("span[name='actualTakeLoactoned']").append("<font color='red'>请输入取车地点！</font>");
						return false;
					}
					if (form.find("input[name='actualTerminalParkName']").val()=="") {
						$("span[name='actualTerminalParkNameed']").append("<font color='red'>请输入还车地点！</font>");
						return false;
					}
					/*if(cn==""){
						$("span[name='carNmEdit']").append("<font color='red'>请选择车辆！</font>");
						return false;
					}*/
					
				}
			});
		}
}
return orderMonthEdit
})
