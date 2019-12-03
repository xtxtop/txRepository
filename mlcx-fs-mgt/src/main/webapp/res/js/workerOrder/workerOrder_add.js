$(function() {
var workerOrderAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#addWorkerOrder").click(function(){
			workerOrderAdd.addWorkerOrder();
		});
		//返回
		$("#closeWorkerOrderAdd").click(function(){
			closeTab("调度工单添加");
			$("#workerOrderList").DataTable().ajax.reload(function(){});
		});
		
		$("#carPlateNoMgAdd").click(function(){
			
			$("#carModalMgAdd").modal("show");
			workerOrderAdd.pageCar();
		});
		
		$("#workerMgAdd").click(function(){
			
			$("#workerMgAddModal").modal("show");
			workerOrderAdd.pageWorker();
		});
		
		$("#parkZyAdd").click(function(){
			$("#parkMhAdd").show();
			$("#mapMhAdd").hide();
			var form = $("form[name=addWorkerOrderForm]");
			form.find("select[name='terminalParkNo']").attr("disabled", false);
			form.find("input[name='terminalParkName']").attr("disabled", true);
			
			
		});
		$("#mapZyAdd").click(function(){
			$("#parkMhAdd").hide();
			$("#mapMhAdd").show();
			var form = $("form[name=addWorkerOrderForm]");
			form.find("select[name='terminalParkNo']").attr("disabled", true);
			form.find("input[name='terminalParkName']").attr("disabled", false);
		});
		
		
	},
	
	pageWorker:function(){
		var carMgBtnTpl = $("#workerMgBtnTplAdd").html();
		// 预编译模板
		var template = Handlebars.compile(carMgBtnTpl);
		var table = $('#workerMgListAdd').dataTable({
			searching : false,
			destroy : true,  
			"ajax" : {
				"type" : "POST",
				"url" : workerOrderAdd.appPath+"/worker/pageListWorker.do",
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
			{ "title":"","data": "workerNo","width":"5px"},
			{
				"title" : "工号",
				"data" : "empNo"
			},{
				"title" : "姓名",
				"data" : "workerName"
			}],
			"dom" : "<'row'<''><'col-xs-6'f>r>"
					+ "t"
					+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			  initComplete: function () {
					$(this).find("thead tr th:first-child").prepend('');
					$("#workerMgToolssssAdd").empty().append('<button type="button"  class="btn-new-type sysworkerMgCar-batch-adddel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue sysworkerMgCar-batch-addclose">关闭</button>');
//					$("#workerMgToolssssAdd").append('');
					$(".sysworkerMgCar-batch-adddel").on("click",function(){
						var ids=[];
						var wk="";
						var len=$('#workerMgListAdd tbody input[type="checkbox"]:checked');
						if(len.length==0){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择调度员！")
						}else{
							$("#workerMgListAdd tbody").find("input:checked").each(function(){
								
	        					ids.push($(this).val());
	        					
	        					wkNe = $(this).next('input').val();
	        					
	        				});
							if(ids.length>1){
        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调度员只能选择一个！")
        					}else{
        						var form = $("form[name=addWorkerOrderForm]");
        	     				form.find("input[name='workerId']").val(ids);
        	     				form.find("input[name='workerName']").val(wkNe);
        	     				$("#workerMgAddModal").modal("hide");
        	     				$(".modal-backdrop").hide();
        					}
						}
						
						$('#workerMgListAdd thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#workerMgListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#workerMgListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					});
					$(".sysworkerMgCar-batch-addclose").on("click",function(){
						$("#workerMgAddModal").modal("hide");
						$(".modal-backdrop").hide();
						 $('#workerMgListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
					});
			  },
			"columnDefs" : [
				   {
					"targets" : [0],
					 "orderable":false,
					"render" : function(data, type, full, meta) {
						  return '<input type="checkbox" name="workerNo" value="' + full.workerNo + '"><input type="hidden" value="' + full.workerName + '">';
					}
				   }
				   ]
		});
	},
	
	getSelectedRow:function(ids){
		debugger
		var form = $("form[name=addWorkerOrderForm]");
		var deviceId=ids;
		$.ajax({
     		url: workerOrderAdd.appPath+"/carStatus/getCarStatus.do?carNo="+ids, 
     		success: function(res){
     			if(res.locationParkNo!=null&&res.locationParkNo!=""){
     				
     				
     				$.ajax({
     		     		url: workerOrderAdd.appPath+"/park/toPark.do?parkNo="+res.locationParkNo, 
     		     		success: function(d){
     		     			form.find("input[name='carPlateNo']").val(res.carPlateNo);
     	     				form.find("input[name='carNo']").val(res.carNo);
     	     				form.find("input[name='startParkName']").val(d.addrStreet);
     	     				form.find("input[name='startParkNo']").val(d.parkNo);
     	     				$("#carModalMgAdd").modal("hide");
     	     				$(".modal-backdrop").hide();
     		     			
     		     		}
     		     	});
     				
     				
     			}
     			
     			else if(res.longitude!=null&&res.longitude!=""&&res.latitude!=null&&res.latitude!=""){
     				form.find("input[name='carPlateNo']").val(res.carPlateNo);
     				form.find("input[name='carNo']").val(res.carNo);
     				//var map = new BMap.Map("allmap");
     				var point = new BMap.Point(res.longitude,res.latitude);
     				var gc = new BMap.Geocoder(); 
     				gc.getLocation(point, function(rs) {
     				
     				var addComp = rs.addressComponents;
     				var mapAddress = addComp.city + addComp.district
     				+ addComp.street + addComp.streetNumber;
     				form.find("input[name='startParkName']").val(mapAddress);
     				}); 
     				
     				$("#carModalMgAdd").modal("hide");
     				$(".modal-backdrop").hide();
     			}else{
     				form.find("input[name='carPlateNo']").val(res.carPlateNo);
     				form.find("input[name='carNo']").val(res.carNo);
     				$("#carModalMgAdd").modal("hide");
     				$(".modal-backdrop").hide();
     				bootbox.alert("未获取车辆位置信息，请手工填写任务起点。");
     			}
     			
     				
     			
     		}
     	});
	},
	
	pageCar:function(){
		var carMgBtnTpl = $("#carMgBtnTplAdd").html();
		// 预编译模板
		var template = Handlebars.compile(carMgBtnTpl);
		var table = $('#carMgListAdd').dataTable({
			searching : false,
			destroy : true,  
			"ajax" : {
				"type" : "POST",
				"url" : workerOrderAdd.appPath+"/car/pageListCar.do",
				"data" : function(d) {
					return $.extend({},d,
									{"pageNo" : (d.start / d.length) + 1,
									 "pageSize" : d.length,
									 "userageStatus" : 0,
									 "onlineStatus" : 1
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
				"title" : "车牌号",
				"data" : "carPlateNo","width":"12px"
			},{
				"title" : "品牌",
				"data" : "carBrandName","width":"12px"
			},{
				"title" : "车型",
				"data" : "carModelName","width":"12px"
			} ],
			"dom" : "<'row'<''><'col-xs-6'f>r>"
					+ "t"
					+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			  initComplete: function () {
					$(this).find("thead tr th:first-child").prepend('');
					$("#carMgToolssssAdd").empty().append('<button type="button"  class="btn-new-type syscarMgCar-batch-adddel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue syscarMgCar-batch-addclose">关闭</button>');
//					$("#carMgToolssssAdd").append('');
					$(".syscarMgCar-batch-adddel").on("click",function(){
						var ids=[];
						var len=$('#carMgListAdd tbody input[type="checkbox"]:checked');
						if(len.length==0){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择车辆！")
						}else{
							$("#carMgListAdd tbody").find("input:checked").each(function(){
								
	        					ids.push($(this).val());
	        					
	        				});
							if(ids.length>1){
        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车辆只能选择一个！")
        					}else{
        						workerOrderAdd.getSelectedRow(ids);
        					}
						}
						
						$('#carMgCarListAdd thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#carMgCarListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#carMgCarListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					});
					$(".syscarMgCar-batch-addclose").on("click",function(){
						$("#carModalMgAdd").modal("hide");
						$(".modal-backdrop").hide();
						 $('#carMgListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
					});
			  },
			"columnDefs" : [
				   {
					"targets" : [0],
					 "orderable":false,
					"render" : function(data, type, full, meta) {
						  return '<input type="checkbox" name="carNo" value="' + full.carNo + '">';
					}
				   }
				   ]
		});
	},
	addWorkerOrder:function() {
		debugger;
		var form = $("form[name=addWorkerOrderForm]");
		var sd=form.find("input[name='type']").val();
		form.ajaxSubmit({
			url : workerOrderAdd.appPath + "/workerOrder/addWorkerOrder.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("调度工单添加");
						$("#workerOrderList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='typeAdd']").empty();
				$("span[name='planTimeAdd']").empty();
				$("span[name='startParkNoAdd']").empty();
				$("span[name='terminalParkNoAdd']").empty();
				$("span[name='carNoAdd']").empty();
				$("span[name='workerIdAdd']").empty();
				debugger
				if (!form.find("input[name='type']").is(':checked')) {
					$("span[name='typeAdd']").html("<font color='red'>请选择类型！</font>");
					return false;
				}
				if (form.find("input[name='workerId']").val()=="") {
					$("span[name='workerIdAdd']").html("<font color='red'>请选择调度员！</font>");
					return false;
				}
				if (form.find("input[name='carNo']").val()=="") {
					$("span[name='carNoAdd']").html("<font color='red'>请选择车牌！</font>");
					return false;
				}
				
				if (form.find("input[name='startParkName']").val()=="") {
					$("span[name='startParkNoAdd']").html("<font color='red'>请选择起点！</font>");
					return false;
				}
				var ms = $("#pkNsAdd").prop("disabled");
				if(ms){
					if (form.find("input[name='terminalParkName']").val()=="") {
						$("span[name='terminalParkNoAdd']").html("<font color='red'>请选择终点！</font>");
						return false;
					}
				}
				
				
				if (form.find("input[name='planTime']").val()=="") {
					$("span[name='planTimeAdd']").html("<font color='red'>请选择计划完成时间！</font>");
					return false;
				}
				
				var planTime = new Date(form.find("input[name='planTime']").val().replace(/-/g, "/"));
				if (new Date() > Date.parse(planTime)) {
					$("span[name='planTimeAdd']").html("<font color='red'>计划完成时间不能小于当前时间！</font>");
					return false;
				} 
				
				var memo = form.find("input[name='memo']").val();
				if (memo != null && form.find("input[name='memo']").val().length > 100) {
					$("span[name='memoAdd']").html("<font color='red'>备注请不要超过100个字符</font>");
					return false;
				}
			}
		});
	 }
	}
	workerOrderAdd.init();
});
function selectSetValue1(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
		var appPath = $("#appPath").val();
		$.ajax({
			 type: "post",
			 url : appPath+ "/workerOrder/getCarByParkNo.do?parkNo="+selectValue,
			 success: function(res){
				 var carStatusList = res.data;
				 if(res.code=="1"){
					 $("#carNo").html("");
					 for(var i=0;i<carStatusList.length;i++){
						 $("#carPlateNo").val(carStatusList[i].carPlateNo);
						 $("#carNo").append("<option  value='"+carStatusList[i].carNo+"'> "+carStatusList[i].carPlateNo+" </option>");
           			}
                 }else{
                	 $("#carPlateNo").val("");
                	 $("#carNo").html("");
                	 $("#carNo").append("<option  value=''>请选择</option>");
                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
                 }
            }
		});
	}else{
		$("#carPlateNo").val("");
		$("#carNo").html("");
   	 	$("#carNo").append("<option  value=''>请选择</option>");
	}
}
//获取指定的select选中的标签的内部值赋给指定的input
function selectSetValue(selectId,inputId){
	var selectText = $("#"+selectId+"").find("option:selected").text();
	var selectValue = $("#"+selectId+"").find("option:selected").val();
	if(selectValue!=""){
		$("#"+inputId).val(selectText);
	}
}
