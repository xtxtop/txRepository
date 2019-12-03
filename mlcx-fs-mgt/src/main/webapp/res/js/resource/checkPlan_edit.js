define([],function() {
var checkPlanEdit = {
		appPath : getPath("app"),
		beforePath:getPath("before"),
		init : function() {
			//时间完成自动显示状态已完成
			$("#actualEndTime").each(function(){
				$(this).blur(function (){
					if($(this).val().length>0){
						 $("#planStatus option[value='1']").attr("selected", true);
					}
					
				});
			});
			//解绑弹出层
			$("#checkParkModal").on("hidden.bs.modal", function() {  
				
            });
			//解绑弹出层
			$("#checkUserModal").on("hidden.bs.modal", function() {  
				
            });
			$("input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
				     var ll=[];
				     //遍历value
					 $("input.butcheck:checked").each(function(i){
						//遍历value
						 l[i]=this.value
						 //遍历文本值
						 ll[i]=$(this).next().text();
					 });
					 //将所有的选中的值存放
					 $("#checkItemId").val(l.join(","));//将数据值联合字符串给显示对象附值
					 $("#checkItem").val(ll.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
			//编辑提交
			$("#editCheckPlan").click(function(){
				checkPlanEdit.updatecheckPlan();
			});
			//关闭车辆详情编辑页
			$("#closeEditCheckPlan").click(function(){
				closeTab("巡检计划编辑");
            });
			//场站列表
			$("#checkAddParkBtn").click(function(){
				$("#checkParkModal").modal({
					 show:true,
					 backdrop:'static'
				});
			});
			//人员列表
			$("#checkAddUserBtn").click(function(){
				$("#checkUserModal").modal({
					 show:true,
					 backdrop:'static'
				});
			});
			//详情页跳转巡检结果
			$(".checkPlan-result-detail").click(function(){
				addTab("巡检结果列表",checkPlanEdit.appPath+$(this).data("url"));
            });
			checkPlanEdit.pageListPark();
			checkPlanEdit.pageListUser();
			//修改页面是否选中
			checkPlanEdit.getCheckbox();
		},
getCheckbox:function(){
	 var checkItemId = $('#checkItemId').val().split(','); 
	 var butcheck = $('.butcheck').val().split(',');
	 $(".butcheck").each(function(){
		 for(var i=0;i<checkItemId.length;i++){
			   if(checkItemId[i]==$(this).val()){
				   $(this).prop("checked",true);
			   }
		   }
		 });
		},
 updatecheckPlan:function() {
	var form = $("form[name=checkPlanEditForm]");
	form.ajaxSubmit({
		url : checkPlanEdit.appPath + "/checkPlan/updateCheckPlan.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
					closeTab("巡检计划编辑");
					$("#checkPlanList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！");
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='planDate']").empty();
			$("span[name='additionFees']").empty();
			$("span[name='actualEndTime']").empty();
			if (form.find("input[name='planDate']").val()=="") {
				$("span[name='planDate']").append("<font color='red'>请选择计划日期！</font>");
				return false;
			}
			/*if (form.find("input[name='actualStartTime']").val()=="") {
				$("span[name='actualStartTime']").append("<font color='red'>请选择实际开始时间！</font>");
				return false;
			}
			if (form.find("input[name='actualEndTime']").val()=="") {
				$("span[name='actualEndTime']").append("<font color='red'>请选择实际完成时间！</font>");
				return false;
			}*/
		}
	});
},
savecheckPlan:function() {
	var form = $("form[name=checkPlanAddForm]");
	$("#saveCheckPlan").prop("disabled",true);
	form.ajaxSubmit({
		url : checkPlanEdit.appPath + "/checkPlan/updateCheckPlan.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
					closeTab("新增巡检计划");
					$("#checkPlanList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
				$("#saveCheckPlan").prop("disabled",false);
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			$("span[name='planDate']").empty();
			$("span[name='parkName']").empty();
			$("span[name='checkItemId']").empty();
			$("span[name='workerName']").empty();
			if (form.find("input[name='planDate']").val()=="") {
				$("span[name='planDate']").append("<font color='red'>请选择计划日期！</font>");
				$("#saveCheckPlan").prop("disabled",false);
				return false;
			}
			if (form.find("input[name='parkName']").val()=="") {
				$("span[name='parkName']").append("<font color='red'>请选择计划巡检场站！</font>");
				$("#saveCheckPlan").prop("disabled",false);
				return false;
			}
			if (form.find("input[name='checkItemId']").val()=="") {
				$("span[name='checkItemId']").append("<font color='red'>请选择计划巡检项！</font>");
				$("#saveCheckPlan").prop("disabled",false);
				return false;
			}
			if (form.find("input[name='workerName']").val()=="") {
				$("span[name='workerName']").append("<font color='red'>请选择计划巡检人员！</font>");
				$("#saveCheckPlan").prop("disabled",false);
				return false;
			}
		}
	});
},
pageListPark:function () {	
	var parkTpl = $("#checkParkBtnTpl").html();
	// 预编译模板
	var template = Handlebars.compile(parkTpl);
	$('#parkLists').dataTable( {
		searching:false,
		destroy: true,
		"ajax": {
			"type": "POST",
			"url": checkPlanEdit.appPath+'/park/pageListPark.do',
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
            { "title":"","data": "parkNo","width":"5px"},
            { "title":"编号","data": "parkNo" },
			{ "title":"城市","data": "cityName" },
			{ "title":"名称","data": "parkName" }
		],
	   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
	   "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
	   initComplete: function () {
		   $(this).find("thead tr th:first-child").prepend('');
			$("#parkToolss").empty().append('<button type="button"  class="btn-new-type checkPlanPark-batch-del">绑定场站</button><button type="button"  class="btn-new-type btn-new-type-blue checkPlanPark-batch-close">关闭</button>');
//			$("#parkToolss").append('');
			$(".checkPlanPark-batch-del").on("click",function(){
				var ids=[];
				var len=$('#parkLists tbody input[type="checkbox"]:checked');
				if(len.length==0){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择场站！")
				}else{
					$("#parkLists tbody").find("input:checked").each(function(){
   					ids.push($(this).val());
   				    });
					if(ids.length>1){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "场站只能选择一个！")
					}else{
						checkPlanEdit.getSelectedRowPark(ids);
					}
				}
				
				$('#parkLists thead input[type="checkbox"]').on("click",function(e){
   				if(this.checked){
   			         $('#parkLists tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
   			      } else {
   			         $('#parkLists tbody input[type="checkbox"]:checked').prop("checked",false);
   			      }
   			});
			});
			$(".checkPlanPark-batch-close").on("click",function(){
				$("#checkParkModal").modal("hide");
				 $('#parkLists tbody input[type="checkbox"]:checked').prop("checked",false);
			});
		},
		"drawCallback": function( settings ) {
	    },
		"columnDefs": [
			  {
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="parkNo" value="' + full.parkNo + '">';
						}
			}
		]
	});
},
pageListUser:function () {	
	var tpl = $("#checkWorkerBtnTpl").html();
	//预编译模板
	var template = Handlebars.compile(tpl);
	$('#checkWorkerLists').dataTable( {	
		searching:false,
		destroy: true,
		"ajax": {
			"type": "POST",
			"url": checkPlanEdit.appPath+'/worker/pageListWorker.do',
			"data": function ( d ) {
				return $.extend( {}, d, {
					"pageNo": (d.start/d.length)+1,
					"pageSize": d.length
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
		    { "title":"","data": "workerNo","width":"5px"},
		    { "title":"工号","data": "empNo" },
			{ "title":"巡检员","data": "workerName" }
		],
		  "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
		initComplete: function () {
			$(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
			$("#checkWorkerTools").empty().append('<button type="button" class="btn-new-type sysUserCheck-batch-del">绑定人员</button><button type="button" class="btn-new-type btn-new-type-blue sysUserCheck-batch-close">关闭</button>');
//			$("#checkWorkerTools").append('');
			$('#checkWorkerLists thead input[type="checkbox"]').on("click",function(e){
				if(this.checked){
			         $('#checkWorkerLists tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
			      } else {
			         $('#checkWorkerLists tbody input[type="checkbox"]:checked').prop("checked",false);
			      }
			});
			$(".sysUserCheck-batch-del").on("click",function(){
				var ids=[];
				$("#checkWorkerLists tbody").find("input:checked").each(function(){
					ids.push($(this).val());
				});
				if(ids.length>0){
					 checkPlanEdit.getSelectedRowUser(ids);
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要绑定的人员！");
				}
			});	      
			$(".sysUserCheck-batch-close").on("click",function(){
				$("#checkUserModal").modal("hide");
				 $('#checkWorkerLists tbody input[type="checkbox"]:checked').prop("checked",false);
			});
		},
		"columnDefs": [
			{ "targets":[0],
			    //"visible": true,
			    "orderable":false,
			    "render":function (data, type, full, meta){
			             return '<input type="checkbox" name="workerNo[]" value="' + full.workerNo + '">';
			    }
			}
		]
	});
},
getSelectedRowPark:function(ids){
	$("#parkNos").val(ids);
	$.ajax({
 		url: checkPlanEdit.appPath+"/checkPlan/tocheckPark.do?parkNo="+ids, 
 		success: function(data){
 			if(data!=null){
 				$("#checkParkModal").modal("hide");
 				$("#parkNames").val(data.parkName);
 			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "绑定失败！");
			}
 		}
 	});
},
getSelectedRowUser:function(ids){
	$("#workerId").val(ids);
    $.ajax({
		url: checkPlanEdit.appPath+"/checkPlan/tocheckUser.do?ids="+ids, 
		success: function(data){
			if(data!=null){
				$("#checkUserModal").modal("hide");
				$("#workerName").val(data);
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "绑定失败！");
			}
		}
	});
}
}
return checkPlanEdit;
})
