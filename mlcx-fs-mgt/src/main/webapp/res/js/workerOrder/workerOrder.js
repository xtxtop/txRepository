define(
		[],
		function() {
			var workerOrder = {
				appPath : getPath("app"),
				init : function() {
					//关闭详情页
					$("#closeWorkerOrderView").click(function(){
						closeTab("调度工单详情");
		            });
					//审核提交
					$("#workerOrderOnFormBtn").click(function(){
						workerOrder.onFormSub();
		            });
					//审核取消
					$("#workerOrderOnCancelBtn").click(function(){
						$("#cencorWorkerModal").modal("hide");
		            });
					// 列表页面搜索调用
					$("#workerOrderSearch").click(function() {
						var form = $("form[name=workerOrderSerachForm]");
						var sendTimeStart =  form.find("input[name=sendTimeStart]").val();
						var sendTimeEnd = form.find("input[name=sendTimeEnd]").val();
						var finishTimeStart =  form.find("input[name=finishTimeStart]").val();
						var finishTimeEnd = form.find("input[name=finishTimeEnd]").val();
						if(sendTimeStart!=""&&sendTimeEnd!=""){
							if(new Date(sendTimeStart)>new Date(sendTimeEnd)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "下发开始时间不能大于结束时间！");
							}else if(finishTimeStart!=""&&finishTimeEnd!=""){
								if(new Date(finishTimeStart)>new Date(finishTimeEnd)){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "完成开始时间不能大于结束时间！");
								}else{
									workerOrder.pageList();
								}
							}else{
								workerOrder.pageList();
							}
						}else{
							 if(finishTimeStart!=""&&finishTimeEnd!=""){
									if(new Date(finishTimeStart)>new Date(finishTimeEnd)){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "完成开始时间不能大于结束时间！");
									}else{
										workerOrder.pageList();
									}
							 }else{
								 workerOrder.pageList();
							 }
						}
					});
					// 列表页面分页方法调用
					workerOrder.pageList();
					workerOrder.handleClickMore();
					
					$("#workerOrderModal").on("hidden.bs.modal", function() {
		            	var form = $("form[name=workerOrderForm]");
		            	form.clearForm();
		            	form.resetForm();
		            	form.find("input[type=hidden]").val("");
		            });
		            
		            
		            $("#workerOrderModal").on("show.bs.modal", function() {
		            });

				},
				
				cencor: function (workerOrderNo) {
			    	var form = $("form[name=workerOrderForm]"); 
					form.ajaxSubmit({
						url:workerOrder.appPath+"/workerOrder/cencorWorkerOrder.do",
						type : "post",
						data : {workerOrderNo:workerOrderNo},
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							var data = res.data;
							if(code == "1"){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核成功！", function() {
//									$(".bootbox").modal("hide");	
//									$("#workerOrderEditModal").modal("hide");
//									form.find(".btn").prop("disabled",false);
									$("#workerOrder").DataTable().ajax.reload(function(){
								});
							 });
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核失败！"); 
//								$(".bootbox").modal("hide");
//								$("#workerOrderEditModal").modal("hide");
//								form.find(".btn").prop("disabled",false);
								$("#workerOrder").DataTable().ajax.reload(function(){
								});
							}
					    },
					});
				},
				operateColumEvent : function() {
					$(".workerOrder-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("调度工单详情",workerOrder.appPath+ "/workerOrder/toWorkerOrderView.do?workerOrderNo="+$(this).data("id"));
						});
					});
					$(".workerOrder-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("调度工单编辑",workerOrder.appPath+ "/workerOrder/toUpdateWorkerOrder.do?workerOrderNo="+$(this).data("id"));
						});
					});
					//地图上显示车辆轨迹
					$(".workerOrder-operate-track").each(function(){
						$(this).on("click",function(){
							addTab("轨迹回放",workerOrder.appPath+ "/carTrack/toCarTrackList.do?flag=1&carPlateNo="+$(this).data("id"));
						});
					});
					//下发调度单
					$(".workerOrder-operate-issued").each(function(id,obj){
						$(this).on("click",function(){
							var workerOrderNo=$(this).data("id");
							$.ajax({
								 type: "post",
					             url: workerOrder.appPath+"/workerOrder/toIssuedWorkerOrder.do",
					             data: {workerOrderNo:workerOrderNo},
					             dataType: "json",
					             success: function(data){ 
									 var code = data.code; 
									 if(code == "1"){  
										$("#cencorWorkerModal").modal("hide"); 
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！");
										$("#workerOrderList").DataTable().ajax.reload(function(){
			    						}); 
									 }else{
										 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
									 }
					             }
							});
						});
					});
					
					//审核弹出层
					$(".workerOrder-operate-cencor").each(function(id,obj){
						$(this).on("click",function(){
							var workerOrderNo=$(this).data("id");
							$.ajax({
								 type: "post",
					             url: workerOrder.appPath+"/workerOrder/toWorkerOrder.do",
					             data: {workerOrderNo:workerOrderNo},
					             dataType: "json",
					             success: function(data){
					            	 if(data.code="1"){
					            	    $("#cencorWorkerModal").modal("show");
					            	    $("form[name=onWorkerOrderForm]").find("textarea[name=cencorMemo]").val("");
										$("#workerOrderNo1").val(workerOrderNo);
										$("#onWorkerOrderMemo").html("<img src='res/img/tan.png' />&nbsp;&nbsp;您将对工单编号为"+"“"+workerOrderNo+"”"+"进行审核，请选择审核结果！");
					                 }
					             }
							});
						});
					});
					//取消
					$(".workerOrder-operate-concel").each(function(id,obj){
						$(this).on("click",function(){
							var workerOrderNo=$(this).data("id");
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要取消吗？", function(result) {
								if(result){
									$.ajax({
										type: "post",
										url: workerOrder.appPath+"/workerOrder/concelWorkerOrder.do",
										data: {workerOrderNo:workerOrderNo},
										dataType: "json",
										success: function(data){
											if(data.code="1"){
												bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "取消操作成功！",function(){
													$("#workerOrderList").DataTable().ajax.reload(function(){
						    						});	
												});
											}else{
												bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "取消操作失败！",function(){
													
												});
											}
										}
									});
								}						
	       				});
						});
					});
					
					//强制结束
					$(".workerOrder-operate-overWk").each(function(id,obj){
						$(this).on("click",function(){
							var workerOrderNo=$(this).data("id");
							var workerNo=$(this).data("id2");
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要取消吗？", function(result) {
								if(result){
									$.ajax({
										type: "post",
										url: workerOrder.appPath+"/workerOrder/checkBeforeOrderForceOver.do",
										data: {workerOrderNo:workerOrderNo},
										dataType: "json",
										success: function(data){
											if(data.code="1"){
												
												$.ajax({
													type: "post",
													url: workerOrder.appPath+"/workerOrder/workerOrderOver.do",
													data: {workerOrderNo:workerOrderNo,workerNo:workerNo},
													dataType: "json",
													success: function(data){
														if(data.code="1"){
															bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "强制结束调度单操作成功！",function(){
																$("#workerOrderList").DataTable().ajax.reload(function(){
									    						});	
															});
														}else{
															bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "强制结束调度单操作失败！",function(){
																
															});
														}
													}
												});
												
											}else{ bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg+"，确定要强制结束此订单吗？", function(result) {
						 						if(result){
						 							$.ajax({
														type: "post",
														url: workerOrder.appPath+"/workerOrder/workerOrderOver.do",
														data: {workerOrderNo:workerOrderNo,workerNo:workerNo},
														dataType: "json",
														success: function(data){
															if(data.code="1"){
																bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "强制结束调度单操作成功！",function(){
																	$("#workerOrderList").DataTable().ajax.reload(function(){
										    						});	
																});
															}else{
																bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "强制结束调度单操作失败！",function(){
																	
																});
															}
														}
													});
						 						}						
						 					});
											}
										}
									});
									
								}						
	       				});
						});
					});
					
				},
				//审核操作
		        onFormSub: function () {
		        	var form = $("form[name=onWorkerOrderForm]");
					form.ajaxSubmit({
		    			url:workerOrder.appPath+"/workerOrder/updateWorkerOrder.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									$(".bootbox").modal("hide");
									$("#cencorWorkerModal").modal("hide");
									$("#workerOrderList").DataTable().ajax.reload(function(){
		    						});
								});
								 
							}
						},beforeSubmit:function(formData, jqForm, options){
							if (form.find("textarea[name=cencorMemo]").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核备注！");
								return false;
							}
						}
						});
					},
				pageList : function() {
					var form = $("form[name=workerOrderSerachForm]");
					var workerOrderNo = form.find("input[name='workerOrderNo']").val();
					var type = form.find("select[name='type']").val();
					var finishTimeStart = form.find("input[name='finishTimeStart']").val();
					var finishTimeEnd = form.find("input[name='finishTimeEnd']").val();
					var sendTimeStart = form.find("input[name='sendTimeStart']").val();
					var sendTimeEnd = form.find("input[name='sendTimeEnd']").val();
					var cencorStatus = form.find("select[name='cencorStatus']").val();
					var workOrderStatus = form.find("select[name='workOrderStatus']").val();
					var workerId = form.find("input[name='workerId']").val();
					var workerName = form.find("input[name='workerName']").val();
					var carPlateNo = form.find("input[name='carPlateNo']").val();
						
					var workerOrderBtnTpl = $("#workerOrderBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(workerOrderBtnTpl);
					var table = $('#workerOrderList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : workerOrder.appPath
													+ "/workerOrder/pageListWorkerOrder.do",
											"data" : function(d) {
												return $
														.extend(
																{},
																d,
																{
																	"pageNo" : (d.start / d.length) + 1,
																	"pageSize" : d.length,
																	"workerOrderNo" : workerOrderNo,
																	"type" : type,
																	"finishTimeStart" :finishTimeStart+" 00:00:00",
																	"finishTimeEnd" : finishTimeEnd+" 23:59:59",
																	"sendTimeStart" :sendTimeStart+" 00:00:00",
																	"sendTimeEnd" :sendTimeEnd+" 23:59:59",
																	"cencorStatus" :cencorStatus,
																	"workOrderStatus" :workOrderStatus,
																	"workerId":workerId,
																	"workerName":workerName,
																	"carPlateNo":carPlateNo
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
										{
											"title" : "工单编号",
											"data" : "workerOrderNo"
										}, {
											"title" : "类型",
											"data" : "type"
										}, {
											"title" : "车牌号",
											"data" : "carPlateNo"
										}, {
											"title" : "调度员",
											"data" : "workerName"
										}, {
											"title" : "下发时间",
											"data" : "sendTime","defaultContent":""
										}, {
											"title" : "起点",
											"data" : "startParkName"
										}, {
											"title" : "终点",
											"data" : "terminalParkName"
										}, {
											"title" : "开始时间",
											"data" : "startTime","defaultContent":""
										}, {
											"title" : "完成时间",
											"data" : "finishTime","defaultContent":""
										}, {
											"title" : "审核状态",
											"data" : "cencorStatus"
										}, {
											"title" : "工单状态",
											"data" : "workerOrderStatus"
										}, {
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#workerOrdertool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											 $("#workerOrdertool").html("");
											$("#workerOrdertool").removeClass("col-xs-6");
											$("#workerOrdertool").append('<button type="button" class="btn-new-type workerOrderTools-operate-addWorkerOrder">新增</button>');
											$("#workerOrdertool").append('<button type="button" class="btn-new-type workerOrderTools-operate-export">导出模板</button>');
											$("#workerOrdertool").append('<button type="button" class="btn-new-type workerOrderTools-operate-import">导入</button>');
								       			$(".workerOrderTools-operate-addWorkerOrder").on("click",function(){
								       				addTab("调度工单添加", workerOrder.appPath+ "/workerOrder/toAddWorkerOrder.do");
								       			});
								       			
								       			/**
								       			 * 导出
								       			 */
								       			$(".workerOrderTools-operate-export").on("click",function(){
								       				bootbox.confirm("确定要导出模板吗？", function(result) {
														if(result){
															window.location.href = workerOrder.appPath+ "/workerOrder/downloadExcelFile.do?filepath=workerOrder&newFileName=workerOrder.xls";
														}						
								       				});
								       			});
								       			
								       			/**
								       			 * 导入
								       			 */
								       			$(".workerOrderTools-operate-import").on("click",function(){
								       				bootbox.dialog({
								       			        title: "批量导入",
								       			        dataType: "json",
								       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='importForm' name='importForm' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
								       			        buttons: {
								       			            "success": {
								       			                "label": "<i class='icon-ok'></i> 保存",
								       			                "className": "	btn-sm btn-success",
								       			                "callback": function() {
								       			                    var importForm = $("form[name='importForm']");
								       			                    importForm.ajaxSubmit({
								       			                        url: "workerOrder/importWorkerOrder.do",
								       			                        type: "post",
								       			                        success: function(res) {
								       			                        	if (res.code != 1) {
								       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
								       			                        	} else {
								       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！"); 
								       			                        	}
								       			                            $("#workerOrderList").DataTable().ajax.reload(function(){
								       			                            	
								       			            				});
								       			                        }, 
								       			                        error: function(res) {
								       			                        	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据异常，请检查数据完整性！"); 
								       			                        }
								       			                    });
								       			                }
								       			            },
								       			            "cancel": {
								       			                "label": "<i class='icon-info'></i> 取消",
								       			                "className": "btn-sm btn-danger"
								       			            }
								       			            
								       			        }
								       			    })
								       			});
										},
										"drawCallback" : function(settings) {
											workerOrder.operateColumEvent();
										},
										"order" : [ [ 0, "desc" ] ],
										"columnDefs" : [
												{
													"targets" : [ 1 ],
													"orderable" : false,
													"render" : function(a,
															b, c, d) {
														var workerOrderType = "";//0-调度 1-救援 2-清洁 3-加油 4-维保
														if(c.type.indexOf("0")!=-1){
															workerOrderType +="调度，"
														}
														if(c.type.indexOf("1")!=-1){
															workerOrderType +="救援，"
														}
														if(c.type.indexOf("2")!=-1){
															workerOrderType +="清洁，"
														}
														if(c.type.indexOf("3")!=-1){
															workerOrderType +="加油，"
														}
														if(c.type.indexOf("4")!=-1){
															workerOrderType +="维保，"
														}
														
														var ty = workerOrderType.substring(0,workerOrderType.length-1);
														return ty;
													}
												},
												{
													"targets" : [ 4,7,8 ],
													"orderable" : false,
													"render" : function(a,
															b, c, d) {
														var str = a;
														if(a!=null){
															var now = moment(a);
															return now.format('YYYY-MM-DD HH:mm:ss');
														}else{
															return "";
														}
													}
												},
												{
													"targets" : [ 9 ],
													"orderable" : false,
													"render" : function(a,
															b, c, d) {
														var authenticate;
														//（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
														if(a!=""||a!=null){
															var cencorStatus = c.cencorStatus;
															if(cencorStatus==0){
																authenticate="未审核"
															}else if(cencorStatus==1){
																authenticate="已审核"
															}else if(cencorStatus==2){
																authenticate="未通过"
															}
															return authenticate;
														}else{
															return "";
														}
													}
												},
												{
													"targets" : [ 10 ],
													"orderable" : false,
													"render" : function(a,
															b, c, d) {
														var authenticate;
														var workerOrderStatus = c.workOrderStatus;
														//（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
														if(workerOrderStatus==0){
															authenticate="未下发"
														}else if(workerOrderStatus==1){
															authenticate="已下发"
														}else if(workerOrderStatus==2){
															authenticate="调度中"
														}else if(workerOrderStatus==3){
															authenticate="已结束"
														}else if(workerOrderStatus==4){
														    authenticate="已取消"
													    }
														return authenticate;
													}
												},
												{
													targets : 11,
													render : function(a, b, c,
															d) {
														var view = '<span class="glyphicon workerOrder-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.workerOrderNo
																+ '" >查看</span>';
														var edit = '<span class="glyphicon workerOrder-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.workerOrderNo
																+ '" >编辑</span>';
															
														var issued = '<span class="glyphicon workerOrder-operate-issued" style="text-decoration: underline; cursor: pointer;" data-id="'
															+ c.workerOrderNo
															+ '" >下发</span>';
														
														var cencor='<span class="glyphicon workerOrder-operate-cencor" style="text-decoration: underline; cursor: pointer;" data-id="'+c.workerOrderNo+'" data-toggle="tooltip" data-placement="top">审核</span>';
														var track='<span class="glyphicon  workerOrder-operate-track" style="text-decoration: underline; cursor: pointer;" data-id="'+c.carPlateNo+'" data-toggle="tooltip" data-placement="top">轨迹回放</span>';
														var concel='<span class="glyphicon workerOrder-operate-concel" style="text-decoration: underline; cursor: pointer;" data-id="'+c.workerOrderNo+'" data-toggle="tooltip" data-placement="top">取消</span>';
														var overWk='<span class="glyphicon workerOrder-operate-overWk" style="text-decoration: underline; cursor: pointer;" data-id="'+c.workerOrderNo+'" data-id2="'+c.workerId+'" data-toggle="tooltip" data-placement="top">强制结束</span>';
														if(c.cencorStatus!=""||c.cencorStatus!=null){
															if(c.cencorStatus==0&&c.workOrderStatus==0){//未审核,未下发
																return view + edit + cencor + concel;
															}
//															else if(c.cencorStatus==0&&c.workOrderStatus!=4){//未审核
//																return view + edit + cencor;
//															}
															else if(c.cencorStatus==1&&c.workOrderStatus==0){//已审核,未下发
																return view + issued;
															}
															else if(c.cencorStatus==1&&c.workOrderStatus==2){//已审核
																return view+overWk;
															}
															else if(c.cencorStatus==1&&c.workOrderStatus==3){//已结束
																return view + track;
															}
															else if(c.cencorStatus==1&&c.workOrderStatus!=0&&c.workOrderStatus!=2){//已审核
																return view;
															}
															else if(c.cencorStatus==2){//审核未通过
																return view;
															}else{
																return view;
															}
														}else{
															return "";
														}
														
													}
												}]
									});
				},
				//点击更多
				handleClickMore:function(){
					$("#moreWokerOrder").click(function(){
						if($(".seach-input-details").hasClass("close-content")){
							$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
							$(".seach-input-details").removeClass("close-content");
						}else{
							$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
							$(".seach-input-details").addClass("close-content");
						}
					})
				}
			};
			return workerOrder;
		});
