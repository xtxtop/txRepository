define([],function() {	
	var pricingRuleDay={
			appPath:getPath("app"),	
			init: function () {	
				//启用状态提交
				$("#pricingRuleDayOnFormBtn").click(function(){
					pricingRuleDay.onFormSub();
	            });
				//启用取消
				$("#pricingRuleDayOnCancelBtn").click(function(){
					$("#onpricingRuleDayModal").modal("hide");
	            });
				//停用状态提交
				$("#pricingRuleDayOffBtn").click(function(){
					pricingRuleDay.offFormSub();
	            });
				//停用取消
				$("#pricingRuleDayOffCancel").click(function(){
					$("#OffpricingRuleDayModal").modal("hide");
	            });
				
	            //数据列表
				pricingRuleDay.pageList();
				//查询
				$("#pricingRuleDaySearchafter").click(function(){
					pricingRuleDay.pageList();
	            });
				$("#onpricingRuleDayModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#onMemoPr").text("");
	            	form.find("input[name=ruleNo]").val("");
	            });
				$("#OffpricingRuleDayModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#offMemo").text("");
	            	form.find("input[name=ruleNo]").val("");
	            });
				$("#closeViewpricingRuleDay").click(function(){
					closeTab("日租计费规则详情");
				});
				
				$("#customizedDateModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=customizedDateForm]");
	            	form.resetForm();
	            });
				$("#customizedDateDayBtn").click(function(){
					pricingRuleDay.saveCustomizedDate();
				});
				$("#customizedDateDayCancel").click(function() {  
					$("#customizedDateDayModal").modal("hide");
	            });
				
				
			},
				saveCustomizedDate:function(){
					var form = $("form[name=customizedDateDayForm]");
					form.ajaxSubmit({
		    			url:pricingRuleDay.appPath+"/pricingRuleDay/saveDayCustomizedDate.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									$("#customizedDateDayModal").modal("hide");
									form.resetForm();
								});
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！");
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							$("span[name='priceOfDayCustomizedError']").empty();
							$("span[name='customizedDateError']").empty();
							
							if (form.find("input[name='priceOfDayCustomized']").val()=="") {
								$("span[name='priceOfDayCustomizedError']").append("<font color='red'>请输入时间计费！</font>");
								return false;
							}
							if (form.find("input[name='priceOfDayCustomized']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfDayCustomized']").val())) {
								$("span[name='priceOfDayCustomizedError']").append("<font color='red'>时间计费金额输入有误(正数或0)！</font>");
								return false;
							}
							
							if (form.find("input[name='customizedDateStr']").val()=="") {
								$("span[name='customizedDateError']").append("<font color='red'>请选择日期！</font>");
								return false;
							}
						}
						});
				},
				   //启用操作
		        onFormSub: function () {
		        	var form = $("form[name=onForm]");
					form.ajaxSubmit({
		    			url:pricingRuleDay.appPath+"/pricingRuleDay/enablePricingRuleDay.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									$("#onpricingRuleDayModal").modal("hide");
									$(".bootbox").modal("hide");
									$("#onMemoPrDay").text("");
									form.find("textarea[name='availableMemo']").html("");
									$("#pricingRuleDayList").DataTable().ajax.reload(function(){
		    						}); 
								});
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！",function(){
									$("#onMemoPrDay").text("");
								});
								
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='availableMemo']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入原因！");
								return false;
							}
						}
						});
					},
					   //下线操作
					offFormSub: function () {
			        	var form = $("form[name=offFormDay]");
						form.ajaxSubmit({
							url:pricingRuleDay.appPath+"/pricingRuleDay/disablePricingRuleDay.do",
							type : "post",
							dataType:"json", //数据类型  
							success:function(res){
								var msg = res.msg;
								var code = res.code;
								if(code == "1"){ 
								  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									  $("#OffpricingRuleDayModal").modal("hide");
									  $(".bootbox").modal("hide");
										 form.find("textarea[name='availableMemo']").html("")
										  $("#offMemoDay").text("");
										 $("#pricingRuleDayList").DataTable().ajax.reload(function(){
				    						}); 
								  });
								  
								}else{
									$("#OffpricingRuleDayModal").modal("hide");
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
									$("#offMemoDay").text("");
								}
							},
							beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
								if (form.find("textarea[name='availableMemo']").val()=="") {
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入原因！");
									return false;
								}
							}
							});
						},
			//方法加载
	        operateColumEvent: function(){
	        	$(".pricingRuleDay-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("日租计费规则详情",pricingRuleDay.appPath+ "/pricingRuleDay/toPricingRuleDayView.do?ruleNo="+$(this).data("id"));
					});
				});
				$(".pricingRuleDay-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("日租计费规则调整",pricingRuleDay.appPath+ "/pricingRuleDay/toUpdateViewDay.do?ruleNo="+$(this).data("id"));			
					});
				});
				$(".pricingRuleDay-operate-op").each(function(id,obj){
					$(this).on("click",function(){
						addTab("日租计费规则审核",pricingRuleDay.appPath+ "/pricingRuleDay/toPricingRuleDayCencorView.do?ruleNo="+$(this).data("id"));			
					});
				});
				$(".pricingRuleDay-operate-customizedDate").each(function(){
					$(this).on("click",function(){
						addTab("自定义日期计费列表",pricingRuleDay.appPath+ "/pricingRuleDay/toPricingRuleDayCustomizedDate.do?ruleNo="+$(this).data("id"));
					});
				});
				//启用弹出层
				$(".pricingRuleDay-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var pricingRuleDayNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleDay.appPath+"/pricingRuleDay/toPricingRuleDay.do",
				             data: {ruleNo:pricingRuleDayNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data!=null){
				            	    $("#onpricingRuleDayModal").modal("show");
									$("#ruleNo1Day").val(data.ruleNo);
									$("#onMemoPrDay").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将计费规则"+"“"+data.ruleName+"”"+"启用吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "不可启用！");
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".pricingRuleDay-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var pricingRuleDayNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleDay.appPath+"/pricingRuleDay/toPricingRuleDay.do",
				             data: {ruleNo:pricingRuleDayNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data!=null){
				            	    $("#OffpricingRuleDayModal").modal("show");
									$("#ruleNoDay2").val(data.ruleNo);
									$("#stoppricingRuleDayCarModelName").val(data.carModelName);
									$("#offMemoDay").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将计费规则"+"“"+data.ruleName+"”"+"停用吗？停用后的计费规则将无法使用？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "不可停用！");
				                 }
				             }
						});
					});
				});
				//自定义日期弹出层
//				$(".pricingRuleDay-operate-customizedDate").each(function(){
//					$(this).on("click",function(){
//						var ruleNo=$(this).data("id");
//						$.ajax({
//							 type: "post",
//				             url: pricingRuleDay.appPath+"/pricingRuleDay/getPricingRuleDayCustomizedDate.do",
//				             data: {ruleNo:ruleNo},
//				             dataType: "json",
//				             success: function(res){
//				            	 var customizedDateObj = res.data;
//				            	 if(customizedDateObj!=null){
//									$("#customizedDateDayModal").modal("show");
//									var form = $("form[name=customizedDateDayForm]");
//									
//									form.find("input[name=customizedId]").val(customizedDateObj.customizedId);
//									form.find("input[name=ruleNo]").val(customizedDateObj.ruleNo);
//									form.find("input[name=cityId]").val(customizedDateObj.cityId);
//									form.find("input[name=cityName]").val(customizedDateObj.cityName);
//									form.find("input[name=carModelName]").val(customizedDateObj.carModelName);
//									form.find("input[name=companyId]").val(customizedDateObj.companyId);
//									form.find("input[name=priceOfDayCustomized]").val(customizedDateObj.priceOfDayCustomized);
//									form.find("input[name=customizedDateStr]").val(customizedDateObj.customizedDateStr);
//				                 }
//				             }
//						});
//					});
//				});
	        },
			pageList:function () {	
				var pricingRuleDayTpl = $("#pricingRuleDayTpl").html();
				// 预编译模板
				var template = Handlebars.compile(pricingRuleDayTpl);
				$('#pricingRuleDayList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": pricingRuleDay.appPath+'/pricingRuleDay/pageListPricingRuleDay.do',
						"data": function ( d ) {
							var form = $("form[name=pricingRuleDaySearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"ruleName":form.find("input[name=ruleName]").val(),
								"availableTime1Start":form.find("input[name=availableTime1Start]").val()+" 00:00:00",
								"availableTime2Start":form.find("input[name=availableTime2Start]").val()+" 23:59:59",
	        					"cityId":form.find("select[name=cityId]").val(),
	        					"cencorStatus":form.find("select[name=cencorStatus]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
	        					"isStandardBilling":form.find("select[name=isStandardBilling]").val()
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
			            { "title":"城市","data": "cityName","defaultContent":"" },
						{ "title":"名称","data": "ruleName","defaultContent":"" },
						{ "title":"车辆品牌","data": "carBrandName","defaultContent":"" },
						{ "title":"车辆型号","data": "carModelName","defaultContent":"" },
						{ "title":"标准规则","data": "isStandardBilling","defaultContent":"" },
						{ "title":"工作日计费（元/天）","data": "workingDay","defaultContent":"" },
						{ "title":"周末计费（元/天）","data": "weekend","defaultContent":"" },
						{ "title":"集团名称","data": "companyName","defaultContent":"" },
						{ "title":"审核状态","data": "cencorStatus" ,"defaultContent":""},
						{ "title":"状态","data": "isAvailable" ,"defaultContent":""},
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#pricingRuleDaytool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#pricingRuleDayTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#pricingRuleDayTools").html("");
		       			$("#pricingRuleDayTools").append('<button type="button" class="btn btn-default btn-sm pricingRuleDayTools-operate-add">新增</button>');
		       			$(".pricingRuleDayTools-operate-add").on("click",function(){
		       				addTab("新增日租计费规则", pricingRuleDay.appPath+ "/pricingRuleDay/toAddPricingRuleDay.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						pricingRuleDay.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						        if(c.isStandardBilling==1){
						        	return "是";
						        }else{
						        	return "否";
						        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						
						//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
						{
						    "targets": [8],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(c.cencorStatus=="0"){
							        	return "未审核";
							        }else if(c.cencorStatus=="1"){
							        	return "已审核";
							        }else if(c.cencorStatus=="2"){
							        	return "待审核";
							        }else if(c.cencorStatus=="3"){
							        	return "未通过";
							        }
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [9],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(c.isAvailable=="1"){
							        	return "启用";
							        }else{
							        	return "停用";
							        }
						        }else{
						        	return "";
						        }
						    }
						},
						
						{
							"targets": [10],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  btn btn-default  pricingRuleDay-operate-find" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="查看">查看</span>';
								var edit = "";
								var onList="";
	        					var offList="";
	        					var op="";
	        					var customizedDate = '<span class="glyphicon  btn btn-default  pricingRuleDay-operate-customizedDate" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="自定义日期">自定义日期</span>';
	        					//定义返回结果集  审核通过时，启用状态 显示 停用/查看按钮， 停用状态 显示 启用/调整/查看 按钮，点击调整进行修改后审核状态变为未审核；
	        					// 未审核状态时，显示 查看/调整/审核 按钮；   停用时需判断当前计费规则是否是某车型的唯一一条计费规则，若是则停用失败
								if(c.isAvailable==0&& c.cencorStatus ==1){
									onList='<span class="glyphicon  btn btn-default  pricingRuleDay-operate-onList" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="启用">启用</span>';	
									edit ='<span class="glyphicon  btn btn-default  pricingRuleDay-operate-edit" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
								}
								if(c.isAvailable==1&&c.isStandardBilling==0&& c.cencorStatus ==1){//非标准资费可以停用
									offList='<span class="glyphicon  btn btn-default  pricingRuleDay-operate-offList" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="停用">停用</span>';
								}
								if(c.cencorStatus==0||c.cencorStatus==3){
									op='<span class="glyphicon  btn btn-default  pricingRuleDay-operate-op" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="审核">审核</span>';
									edit ='<span class="glyphicon  btn btn-default  pricingRuleDay-operate-edit" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
								}
								
	        						return find+edit+onList+offList+op+customizedDate;
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
	return pricingRuleDay;
});


