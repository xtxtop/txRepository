define(
	[],function() {
	var workerOrderEdit = {
		appPath : getPath("app"),
		init : function() {
			
		     $("#mapLocationBdEdit").click(function(){
	        	$("#lctNsEdit").empty();
	        	var form = $("form[name=editWorkerOrderForm]");
					form.find("input[name='terminalParkName']").empty();
	        	$("#mapLocationEdit").modal("show");
	        	setTimeout(function(){
	        		var map = new BMap.Map("mapLctNsEdit");
					var point = new BMap.Point(114.065537,22.549583);
					map.centerAndZoom(point,12);
				  map.enableScrollWheelZoom();
				  
				 var geolocation = new BMap.Geolocation();
					geolocation.getCurrentPosition(function(r){
						if(this.getStatus() == BMAP_STATUS_SUCCESS){
							var mk = new BMap.Marker(r.point);
							map.addOverlay(mk);
							map.panTo(r.point);
							
						}
						       
					},{enableHighAccuracy: true})
				  
					var geoc = new BMap.Geocoder();    

					map.addEventListener("click", function(e){        
						var pt = e.point;
						geoc.getLocation(pt, function(rs){
							var addComp = rs.addressComponents;
							$("#lctNsEdit").html(addComp.city+addComp.district+addComp.street+addComp.streetNumber);
							form.find("input[name='terminalParkName']").val(addComp.city+addComp.district+addComp.street+addComp.streetNumber);
						});        
					});
	        	},200);
					
				
					
			});
		
		
		
		//编辑提交
		$("#saveWorkerOrder").click(function(){
			workerOrderEdit.editWorkerOrder();
		});
		//返回
		$("#closeWorkerOrderEdit").click(function(){
			closeTab("调度工单编辑");
			$("#workerOrderList").DataTable().ajax.reload(function(){});
		});
		
			$("#workerMgEdit").click(function(){
			
			$("#workerMgEditModal").modal("show");
			workerOrderEdit.pageWorker();
		});
			
			$("#carPlateNoMgEdit").click(function(){
				
				$("#carModalMgEdit").modal("show");
				workerOrderEdit.pageCar();
			});	
			
			$("#parkZyEdit").click(function(){
				$("#parkMhEdit").show();
				$("#mapMhEdit").hide();
				var form = $("form[name=editWorkerOrderForm]");
				form.find("select[name='terminalParkNo']").attr("disabled", false);
				form.find("input[name='terminalParkName']").attr("disabled", true);
				
				
			});
			$("#mapZyEdit").click(function(){
				$("#parkMhEdit").hide();
				$("#mapMhEdit").show();
				var form = $("form[name=editWorkerOrderForm]");
				form.find("select[name='terminalParkNo']").attr("disabled", true);
				form.find("input[name='terminalParkName']").attr("disabled", false);
			});
			
	},
	
	getSelectedRow:function(ids){
		debugger
		var form = $("form[name=editWorkerOrderForm]");
		var deviceId=ids;
		$.ajax({
     		url: workerOrderEdit.appPath+"/carStatus/getCarStatus.do?carNo="+ids, 
     		success: function(res){
     			
     			if(res.locationParkNo!=null&&res.locationParkNo!=""){
     				
     				
     				$.ajax({
     		     		url: workerOrderEdit.appPath+"/park/toPark.do?parkNo="+res.locationParkNo, 
     		     		success: function(d){
     		     			form.find("input[name='carPlateNo']").val(res.carPlateNo);
     	     				form.find("input[name='carNo']").val(res.carNo);
     	     				form.find("input[name='startParkName']").val(d.addrStreet);
     	     				form.find("input[name='startParkNo']").val(d.parkNo);
     	     				$("#carModalMgEdit").modal("hide");
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
     				
     				$("#carModalMgEdit").modal("hide");
     				$(".modal-backdrop").hide();
     			}else{

     				form.find("input[name='carPlateNo']").val(res.carPlateNo);
     				form.find("input[name='carNo']").val(res.carNo);
     				$("#carModalMgEdit").modal("hide");
     				$(".modal-backdrop").hide();
     				bootbox.alert("未获取车辆位置信息，请手工填写任务起点。");
     			
     			}
     			
     				
     			
     		}
     	});
	},
	
	pageCar:function(){
		var carMgBtnTpl = $("#carMgBtnTplEdit").html();
		// 预编译模板
		var template = Handlebars.compile(carMgBtnTpl);
		var table = $('#carMgListEdit').dataTable({
			searching : false,
			destroy : true,  
			"ajax" : {
				"type" : "POST",
				"url" : workerOrderEdit.appPath+"/car/pageListCar.do",
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
					$("#carMgToolssssEdit").empty().append('<button type="button"  class="btn-new-type syscarMgCar-batch-Editdel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue syscarMgCar-batch-Editclose">关闭</button>');
//					$("#carMgToolssssEdit").append('');
					$(".syscarMgCar-batch-Editdel").on("click",function(){
						var ids=[];
						var len=$('#carMgListEdit tbody input[type="checkbox"]:checked');
						if(len.length==0){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择车辆！")
						}else{
							$("#carMgListEdit tbody").find("input:checked").each(function(){
								
	        					ids.push($(this).val());
	        					
	        				});
							if(ids.length>1){
        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "车辆只能选择一个！")
        					}else{
        						workerOrderEdit.getSelectedRow(ids);
        					}
						}
						
						$('#carMgCarListEdit thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#carMgCarListEdit tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#carMgCarListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					});
					$(".syscarMgCar-batch-Editclose").on("click",function(){
						$("#carModalMgEdit").modal("hide");
						$(".modal-backdrop").hide();
						 $('#carMgListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
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
	
	pageWorker:function(){
		var carMgBtnTpl = $("#workerMgBtnTplEdit").html();
		// 预编译模板
		var template = Handlebars.compile(carMgBtnTpl);
		var table = $('#workerMgListEdit').dataTable({
			searching : false,
			destroy : true,  
			"ajax" : {
				"type" : "POST",
				"url" : workerOrderEdit.appPath+"/worker/pageListWorker.do",
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
					$("#workerMgToolssssEdit").empty().append('<button type="button"  class="btn-new-type sysworkerMgCar-batch-Editdel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue sysworkerMgCar-batch-Editclose">关闭</button>');
//					$("#workerMgToolssssEdit").append('');
					$(".sysworkerMgCar-batch-Editdel").on("click",function(){
						var ids=[];
						var wk="";
						var len=$('#workerMgListEdit tbody input[type="checkbox"]:checked');
						if(len.length==0){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择调度员！")
						}else{
							$("#workerMgListEdit tbody").find("input:checked").each(function(){
								
	        					ids.push($(this).val());
	        					
	        					wkNe = $(this).next('input').val();
	        					
	        				});
							if(ids.length>1){
        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "调度员只能选择一个！")
        					}else{
        						var form = $("form[name=editWorkerOrderForm]");
        	     				form.find("input[name='workerId']").val(ids);
        	     				form.find("input[name='workerName']").val(wkNe);
        	     				$("#workerMgEditModal").modal("hide");
        	     				$(".modal-backdrop").hide();
        					}
						}
						
						$('#workerMgListEdit thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#workerMgListEdit tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#workerMgListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					});
					$(".sysworkerMgCar-batch-Editclose").on("click",function(){
						$("#workerMgEditModal").modal("hide");
						$(".modal-backdrop").hide();
						 $('#workerMgListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
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
	editWorkerOrder:function() {
		var form = $("form[name=editWorkerOrderForm]");
		form.ajaxSubmit({
			url : workerOrderEdit.appPath + "/workerOrder/updateWorkerOrder.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("调度工单编辑");
						$("#workerOrderList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='typeEdit']").empty();
				$("span[name='planTimeEdit']").empty();
				$("span[name='startParkNoEdit']").empty();
				$("span[name='terminalParkNoEdit']").empty();
				$("span[name='carNoEdit']").empty();
				$("span[name='workerIdEdit']").empty();
				
				if (!form.find("input[name='type']").is(':checked')) {
					$("span[name='typeEdit']").html("<font color='red'>请选择类型！</font>");
					return false;
				}
				if (form.find("input[name='workerId']").val()=="") {
					$("span[name='workerIdEdit']").html("<font color='red'>请选择调度员！</font>");
					return false;
				}
				if (form.find("input[name='carNo']").val()=="") {
					$("span[name='carNoEdit']").html("<font color='red'>请选择车牌！</font>");
					return false;
				}
				
				if (form.find("input[name='startParkName']").val()=="") {
					$("span[name='startParkNoEdit']").html("<font color='red'>请选择起点！</font>");
					return false;
				}
				
				var ms = $("#pkNsEdit").prop("disabled");
				if(ms){
					if (form.find("input[name='terminalParkName']").val()=="") {
						$("span[name='terminalParkNoEdit']").html("<font color='red'>请选择终点！</font>");
						return false;
					}	
				}
				
				
				if (form.find("input[name='planTime']").val()=="") {
					$("span[name='planTimeEdit']").html("<font color='red'>请选择计划完成时间！</font>");
					return false;
				}
				
				var planTime = new Date(form.find("input[name='planTime']").val().replace(/-/g, "/"));
				if (new Date() > Date.parse(planTime)) {
					$("span[name='planTimeEdit']").html("<font color='red'>计划完成时间不能小于当前时间！</font>");
					return false;
				} 
				
				var memo = form.find("input[name='memo']").val();
				if (memo != null && form.find("input[name='memo']").val().length > 100) {
					$("span[name='memoEdit']").html("<font color='red'>备注请不要超过100个字符</font>");
					return false;
				}
			}
		});
	 }
	}
	return workerOrderEdit;
})
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
					 $("#carNos").html("");
					 for(var i=0;i<carStatusList.length;i++){
						 $("#carPlateNos").val(carStatusList[i].carPlateNo);
						 $("#carNos").append("<option  value='"+carStatusList[i].carNo+"'> "+carStatusList[i].carPlateNo+" </option>");
           			}
                 }else{
                	 $("#carPlateNos").val("");
                	 $("#carNos").html("");
                	 $("#carNos").append("<option  value=''>请选择</option>");
                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
                 }
            }
		});
	}else{
		$("#carPlateNos").val("");
		$("#carNos").html("");
   	 	$("#carNos").append("<option  value=''>请选择</option>");
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
