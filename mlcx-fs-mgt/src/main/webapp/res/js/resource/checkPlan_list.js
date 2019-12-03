define([],function() {	
	var checkPlan={
			appPath:getPath("app"),	
			init: function () {
				//关闭车辆详情页
				$("#closecheckPlan").click(function(){
					closeTab("场站详情");
	            });
				//取消状态提交
				$("#checkPlanOffBtn").click(function(){
					checkPlan.offFormSub();
	            });
				//取消
				$("#checkPlanOffCancel").click(function(){
					$("#offcheckPlanModal").modal("hide");
	            });
	            //数据列表
				checkPlan.pageList();
				//查询
				$("#checkPlanSearchafter").click(function(){
					checkPlan.pageList();
	            });
				$("#offcheckPlanModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offcheckPlanForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#offcheckPlanMemo").text("");
	            	form.find("input[name=checkPlanNo]").val("");
	            });
				//详情页跳转巡检结果
				$(".checkPlan-result-detail").click(function(){
					addTab("巡检结果列表",checkPlan.appPath+$(this).data("url"));
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".checkPlan-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("巡检计划详情",checkPlan.appPath+ "/checkPlan/toCheckPlanView.do?checkPlanNo="+$(this).data("id"));
					});
				});
	        	$(".checkPlan-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("巡检计划编辑",checkPlan.appPath+ "/checkPlan/toUpdateView.do?checkPlanNo="+$(this).data("id"));
					});
				});
	        	$(".checkPlan-operate-offList").each(function(){
					$(this).on("click",function(){
						addTab("巡检结果列表",checkPlan.appPath+ "/checkResult/toCheckResult.do?checkPlanNo="+$(this).data("id"));
					});
				});
				//停用弹出层
				$(".checkPlan-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var checkPlanNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: checkPlan.appPath+"/checkPlan/toCheckPlanBean.do",
				             data: {checkPlanNo:checkPlanNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data!=""){
				            	    $("#offcheckPlanModal").modal("show");
									$("#checkPlanNo").val(data.checkPlanNo);
									$("#offcheckPlanMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将编号为"+"“"+data.checkPlanNo+"”"+"的巡检计划取消吗？");
				                  }else{
				                	  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "无法取消！"); 
				                  }
				             }
						});
					});
				});
	        },
				   //停用操作
				offFormSub: function () {
		        	var form = $("form[name=offcheckPlanForm]");
		        	$("#checkPlanOffBtn").prop("disabled",true);
					form.ajaxSubmit({
						url:checkPlan.appPath+"/checkPlan/updateCheckPlan.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							 $("#offcheckPlanModal").modal("hide");
							  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！");
							  $("#checkPlanOffBtn").prop("disabled",false);
							  $("#checkPlanList").DataTable().ajax.reload(function(){
	    						}); 
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='memo']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入理由！");
								 $("#checkPlanOffBtn").prop("disabled",false);
								return false;
							}
						}
						});
					},
			pageList:function () {	
				var checkPlanTpl = $("#checkPlanTpl").html();
				// 预编译模板
				var template = Handlebars.compile(checkPlanTpl);
				$('#checkPlanList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": checkPlan.appPath+'/checkPlan/pageListCheckPlan.do',
						"data": function ( d ) {
							var form = $("form[name=checkPlanSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkName":$.trim(form.find("input[name=parkName]").val()),
								"planDateStart":form.find("input[name=planDateStart]").val()+" 00:00:00",
	        					"planDateEnd":form.find("input[name=planDateEnd]").val()+" 23:59:59",
	        					"planStatus":form.find("select[name=planStatus]").val()
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
			            { "title":"编号","data": "checkPlanNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"计划巡检时间","data": "planDate" },
						{ "title":"计划巡检场站","data": "parkName" },
						{ "title":"计划巡检项","data": "checkItem" },	
						{ "title":"计划巡检人员","data": "workerName" },
						{ "title":"实际开始时间","data": "actualStartTime" },
						{ "title":"实际完成时间","data": "actualEndTime" },
						{ "title":"状态","data": "planStatus" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#checkPlantool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#checkPlanTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					    $("#checkPlanTools").html("");
					   $("#checkPlanTools").removeClass("col-xs-6");
		       			$("#checkPlanTools").append('<button type="button" class="btn-new-type checkPlanTools-operate-addcheckPlan">新增</button>');
		       			$(".checkPlanTools-operate-addcheckPlan").on("click",function(){
		       				addTab("新增巡检计划 ", checkPlan.appPath+ "/checkPlan/toAddcheckPlan.do");
		       			});	     			
	        			
					},
					"drawCallback": function( settings ) {
						checkPlan.operateColumEvent();
	        	    },
					"columnDefs": [
						 {
						    "targets": [2],
						    "render": function(data, type, row, meta) {
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD'); 
						    }
						},
						{
						    "targets": [6,7],
						    "render": function(data, type, row, meta) {
                              if(data){
                            	  var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    		
						    }
						},
						//计划状态，1、待处理，2、处理中，3、已完成，4、已取消
						{
						    "targets": [8],
						    "render": function(data, type, row, meta) {
							        if(data==1){
							        	return "待处理";
							        }else if(data==2){
							        	return "处理中";
							        }else if(data==3){
							        	return "已完成"
							        }else if(data==4){
							        	return "已取消"
							        }else{
							        	return ""
							        }
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon checkPlan-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.checkPlanNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon checkPlan-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.checkPlanNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var onList='<span class="glyphicon checkPlan-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.checkPlanNo+'" data-toggle="tooltip" data-placement="top">取消</span>';
	        					var offList='<span class="glyphicon checkPlan-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.checkPlanNo+'" data-toggle="tooltip" data-placement="top">巡检结果</span>';
	        					if(c.planStatus==0){
	        					return find+edit+onList+offList;
	        					}else{
	        					return find+edit+offList;
	        					}
							}
						}
//						{
//	        	            "targets": [3,4],
//	        	            "render": function(data, type, row, meta) {
//	        	            	var now = moment(data); 
//	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
//	        	            }
//	        	        }
					]
				});
			}
	    };
	return checkPlan;
});


