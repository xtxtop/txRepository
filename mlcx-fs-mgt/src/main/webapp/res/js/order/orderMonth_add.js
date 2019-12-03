define([],function() {	
var orderMonthAdd = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			var form = $("form[name=orderMonthAddForm]");
			
			
			$("#orderMonthCarAdd").on("hidden.bs.modal", function() {  
				
            });
			
			
			$(".addorderMonthCar").click(function(){
				
					$("#orderMonthCarAdd").modal({
						 show:true,
						 backdrop:'static'
					});
					orderMonthAdd.pageOrderMonthCar();
				
				
				
            });
		
			//增加提交
			$("#addOrderMonth").click(function(){
				orderMonthAdd.saveOrderMonth();
			});
			//增加页面关闭
			$("#closeOrderMonthAdd").click(function(){
				closeTab("长租订单新增");
			});
			
			
			
			
			
			
		},
		
		
		
		
		pageOrderMonthCar:function(){
			var btnTpl = $("#orderMonthBtnTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(btnTpl);
			var table = $('#orderMonthListAdd').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : orderMonthAdd.appPath+"/orderMonth/pageListOrderMonthCar.do",
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
				"dom" : "<'row'<'#orderMonthToolssssAdd.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#orderMonthToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm sysOrderMonth-batch-adddel">选择车辆</button>');
						$("#orderMonthToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm sysOrderMonth-batch-addclose">关闭</button>');
						$(".sysOrderMonth-batch-adddel").on("click",function(){
							var ids=[];
							var carInfo="";
							var n = 0;
							var len=$('#orderMonthListAdd tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择车辆！")
							}else{
								$("#orderMonthListAdd tbody").find("input:checked").each(function(){
									n += 1;
									
									carInfo += n+"、"+$(this).next('input').val()+","+$(this).next().next('input').val()+"<br/>";
									
									
		        					ids.push($(this).val());
		        					
		        				});
								$("#carsNs").val(ids);
								$("#orderMonthCarAdd").modal("hide");
								$("#carNm").empty();
								$("#carNm").html(carInfo);
								
							}
							
							$('#orderMonthListAdd thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#orderMonthListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#orderMonthListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysOrderMonth-batch-addclose").on("click",function(){
							$("#orderMonthCarAdd").modal("hide");
							$(".modal-backdrop").hide();
							 $('#orderMonthListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
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
			var form = $("form[name=orderMonthAddForm]");
			form.ajaxSubmit({
				url : orderMonthAdd.appPath + "/orderMonth/editOrderMonth.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("月租订单新增");
							$("#orderMonthList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='memberNameho']").empty();
					$("span[name='mobilePhoneho']").empty();
					$("span[name='orderAmountho']").empty();
					$("span[name='discountAmountho']").empty();
					$("span[name='depositho']").empty();
					$("span[name='insuranceho']").empty();
					$("span[name='payableAmountho']").empty();
					$("span[name='paymentTimeho']").empty();
					$("span[name='actualNumberho']").empty();
					$("span[name='actualStartTimeho']").empty();
					$("span[name='actualEndTimeho']").empty();
					$("span[name='actualTakeLoactonho']").empty();
					$("span[name='actualTerminalParkNameho']").empty();
					$("span[name='actualTerminalParkNameho']").empty();
					$("span[name='carNmadd']").empty();
					
					var cn = $("#carNm").text();
					
					
					
					if (form.find("input[name='memberName']").val()=="") {
						$("span[name='memberNameho']").append("<font color='red'>请输入客户名称！</font>");
						return false;
					}
					if (form.find("input[name='mobilePhone']").val()=="") {
						$("span[name='mobilePhoneho']").append("<font color='red'>请输入手机号码！</font>");
						return false;
					}
					if (form.find("input[name='orderAmount']").val()=="") {
						$("span[name='orderAmountho']").append("<font color='red'>请输入订单金额！</font>");
						return false;
					}
					
					if (form.find("input[name='orderAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='orderAmount']").val())) {
						$("span[name='orderAmountho']").append("<font color='red'>订单金额输入有误(正数或0)！</font>");
						return false;
					}
					
					if (form.find("input[name='discountAmount']").val()=="") {
						$("span[name='discountAmountho']").append("<font color='red'>请输入折扣金额！</font>");
						return false;
					}
					
					if (form.find("input[name='discountAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='discountAmount']").val())) {
						$("span[name='discountAmountho']").append("<font color='red'>折扣金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='deposit']").val()=="") {
						$("span[name='depositho']").append("<font color='red'>请输入押金金额！</font>");
						return false;
					}
					
					if (form.find("input[name='deposit']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='deposit']").val())) {
						$("span[name='depositho']").append("<font color='red'>押金金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='insurance']").val()=="") {
						$("span[name='insuranceho']").append("<font color='red'>请输入保险金额！</font>");
						return false;
					}
					
					if (form.find("input[name='insurance']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='insurance']").val())) {
						$("span[name='insuranceho']").append("<font color='red'>保险金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='payableAmount']").val()=="") {
						$("span[name='payableAmountho']").append("<font color='red'>请输入应付金额！</font>");
						return false;
					}
					
					if (form.find("input[name='payableAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='payableAmount']").val())) {
						$("span[name='payableAmountho']").append("<font color='red'>应付金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='paymentTime']").val()=="") {
						$("span[name='paymentTimeho']").append("<font color='red'>请输入支付时间！</font>");
						return false;
					}
					if (form.find("input[name='actualNumber']").val()=="") {
						$("span[name='actualNumberho']").append("<font color='red'>请输入租车数量！</font>");
						return false;
					}
					if(form.find("input[name='actualNumber']").val()!=""&&!/^\+?[1-9]\d*$/.test(form.find("input[name='actualNumber']").val())){
						$("span[name='actualNumberho']").append("<font color='red'>租车数量不正确 必须是大于0的正整数</font>");
						return false;
					}
					if (form.find("input[name='actualStartTime']").val()=="") {
						$("span[name='actualStartTimeho']").append("<font color='red'>请输入租车开始时间！</font>");
						return false;
					}
					if (form.find("input[name='actualEndTime']").val()=="") {
						$("span[name='actualEndTimeho']").append("<font color='red'>请输入租车结束时间！</font>");
						return false;
					}
					
					if (form.find("input[name='actualTakeLoacton']").val()=="") {
						$("span[name='actualTakeLoactonho']").append("<font color='red'>请输入取车地点！</font>");
						return false;
					}
					if (form.find("input[name='actualTerminalParkName']").val()=="") {
						$("span[name='actualTerminalParkNameho']").append("<font color='red'>请输入还车地点！</font>");
						return false;
					}
					if(cn==""){
						$("span[name='carNmadd']").append("<font color='red'>请选择车辆！</font>");
						return false;
					}
					
				}
			});
		}
}
return orderMonthAdd
})
