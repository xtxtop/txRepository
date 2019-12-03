define([],function() {	
	var pricingRule={
			appPath:getPath("app"),	
			init: function () {	
				//启用状态提交
				$("#pricingRuleOnFormBtn").click(function(){
					pricingRule.onFormSub();
	            });
				//启用取消
				$("#pricingRuleOnCancelBtn").click(function(){
					$("#onpricingRuleModal").modal("hide");
	            });
				//停用状态提交
				$("#pricingRuleOffBtn").click(function(){
					pricingRule.offFormSub();
	            });
				//停用取消
				$("#pricingRuleOffCancel").click(function(){
					$("#OffpricingRuleModal").modal("hide");
	            });
				
	            //数据列表
				pricingRule.pageList();
				pricingRule.handleClickMore();
				//查询
				$("#pricingRuleSearchafter").click(function(){
					pricingRule.pageList();
	            });
				$("#onpricingRuleModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=pricingRuleOnForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#onMemoPr").text("");
	            	form.find("input[name=ruleNo]").val("");
	            });
				$("#OffpricingRuleModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=pricingRuleOnForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#offMemo").text("");
	            	form.find("input[name=ruleNo]").val("");
	            });
				$("#closeViewpricingRule").click(function(){
					closeTab("计费规则详情");
				});
				
				$("#customizedDateModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=customizedDateForm]");
	            	form.resetForm();
	            });
//				$("#customizedDateBtn").click(function(){
//					pricingRule.saveCustomizedDate();
//				});
//				$("#customizedDateCancel").click(function() {  
//					$("#customizedDateModal").modal("hide");
//	            });
				
				
			},
	
				   //启用操作
		        onFormSub: function () {
		        	var form = $("form[name=pricingRuleOnForm]");
					form.ajaxSubmit({
		    			url:pricingRule.appPath+"/pricingRule/pricingRuleStartup.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									$("#onpricingRuleModal").modal("hide");
									$(".bootbox").modal("hide");
									$("#onMemoPr").text("");
									form.find("textarea[name='availableMemo']").html("");
									$("#pricingRuleList").DataTable().ajax.reload(function(){
		    						}); 
								});
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg+"！",function(){
									$("#onMemoPr").text("");
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
			        	var form = $("form[name=pricingRuleOffForm]");
						form.ajaxSubmit({
							url:pricingRule.appPath+"/pricingRule/updatePricingRule.do",
							type : "post",
							dataType:"json", //数据类型  
							success:function(res){
								var msg = res.msg;
								var code = res.code;
								if(code == "1"){ 
								  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									  $("#OffpricingRuleModal").modal("hide");
									  $(".bootbox").modal("hide");
										 form.find("textarea[name='availableMemo']").html("")
										  $("#offMemo").text("");
										 $("#pricingRuleList").DataTable().ajax.reload(function(){
				    						}); 
								  });
								  
								}else{
									$("#OffpricingRuleModal").modal("hide");
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
									$("#offMemo").text("");
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
	        	$(".pricingRule-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("计费规则详情",pricingRule.appPath+ "/pricingRule/toPricingRuleView.do?ruleNo="+$(this).data("id"));
					});
				});
				$(".pricingRule-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("计费规则调整",pricingRule.appPath+ "/pricingRule/toUpdateView.do?ruleNo="+$(this).data("id"));			
					});
				});
				$(".pricingRule-operate-op").each(function(id,obj){
					$(this).on("click",function(){
						addTab("计费规则审核",pricingRule.appPath+ "/pricingRule/toPricingRuleCencorView.do?ruleNo="+$(this).data("id"));			
					});
				});
				//启用弹出层
				$(".pricingRule-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var pricingRuleNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRule.appPath+"/pricingRule/toPricingRule.do",
				             data: {ruleNo:pricingRuleNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data!=null){
				            	    $("#onpricingRuleModal").modal("show");
									$("#ruleNo1").val(data.ruleNo);
									$("#onMemoPr").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将计费规则"+"“"+data.ruleName+"”"+"启用吗？启用之后原计费规则将被停用！");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "不可启用！");
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".pricingRule-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var pricingRuleNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRule.appPath+"/pricingRule/toPricingRule.do",
				             data: {ruleNo:pricingRuleNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data!=null){
				            	    $("#OffpricingRuleModal").modal("show");
									$("#ruleNo2").val(data.ruleNo);
									$("#availableMemo").val("");
									$("#stopPricingRuleCarModelName").val(data.carModelName);
									$("#offMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将计费规则"+"“"+data.ruleName+"”"+"停用吗？停用后的计费规则将无法使用？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "不可停用！");
				                 }
				             }
						});
					});
				});
		
	        },
			pageList:function () {	
				var pricingRuleTpl = $("#pricingRuleTpl").html();
				// 预编译模板
				var template = Handlebars.compile(pricingRuleTpl);
				$('#pricingRuleList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": pricingRule.appPath+'/pricingRule/pageListPricingRules.do',
						"data": function ( d ) {
							var form = $("form[name=pricingRuleSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"ruleName":form.find("input[name=ruleName]").val(),
								"availableTime1Start":form.find("input[name=availableTime1Start]").val()+" 00:00:00",
								"availableTime2Start":form.find("input[name=availableTime2Start]").val()+" 23:59:59",
	        					"cityId":form.find("select[name=cityId]").val(),
	        					"cencorStatus":form.find("select[name=cencorStatus]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
	        					"carModelName":form.find("select[name=carModelName]").val(),
	        					"companyId":form.find("select[name=companyId]").val(),
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
			            { "title":"城市","data": "cityName" },
						{ "title":"名称","data": "ruleName" },
						{ "title":"标准规则","data": "isStandardBilling" },
						{ "title":"按时计费（元/分钟）","data": "priceOfMinute" },
						{ "title":"起步价（元）","data": "baseFee" },
//						{ "title":"按里程计费（元/公里）","data": "priceOfKm" },	
//						{ "title":"人员服务按时计费（元/分钟）","data": "servicePriceOfMinute" },
//						{ "title":"人员服务按里程计费（元/公里）","data": "servicePriceOfKm" },
						{ "title":"集团名称","data": "companyName" },
						//{ "title":"有效日期起","data": "availableTime1" },
						//{ "title":"有效日期止","data": "availableTime2" },
						//{ "title":"优先级","data": "priority" },	
						{ "title":"审核状态","data": "cencorStatus" },
						{ "title":"状态","data": "isAvailable" },
						{ "title":"工作日计费","data": "priceDay" },
						{ "title":"工作日计费封顶(元)","data": "billingCapPerDay" },
//						{ "title":"周末计费","data": "priceDayOrdinary" },
//						{ "title":"周末计费封顶（元）","data": "billingCapPerDayOrdinary" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#pricingRuletool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#pricingRuleTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#pricingRuleTools").html("");
					   $("#pricingRuleTools").removeClass("col-xs-6");
					   $("#pricingRuleTools").append('<button type="button" class="btn-new-type pricingRuleTools-operate-add">新增</button>');
		       			$(".pricingRuleTools-operate-add").on("click",function(){
		       				addTab("新增计费规则", pricingRule.appPath+ "/pricingRule/toAddPricingRule.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						pricingRule.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [2],
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
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    	if(a!=null&&a!=''){
						       return a;
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [5],
						    "render": function(a,b, c, d) {
						    	if(a!=null&&a!=''){
						       return a;
						    	}else{
						    		return "";
						    	}
						    }
						},
//						{
//						    "targets": [6,7],
//						    "render": function(data, type, row, meta) {
//						    	if(data){
//						    		var now = moment(data); 
//		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
//							    	}else{
//							    		return "";
//							    	}
//						    }
//						},
						//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
						{
						    "targets": [6],
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
						//终端状态（1、在线，2、节能，3、待机，4、离线，默认离线）
						{
						    "targets": [7],
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
								var find='<span class="glyphicon pricingRule-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="查看">查看</span>';
								var edit = "";
								var onList="";
	        					var offList="";
	        					var op="";
	        					//var customizedDate = '<span class="glyphicon  btn btn-default  pricingRule-operate-customizedDate" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="自定义日期">自定义日期</span>';
	        					//定义返回结果集  审核通过时，启用状态 显示/查看按钮/调整， 停用状态 显示 启用/调整/查看 按钮，点击调整进行修改后审核状态变为未审核；
	        					// 未审核状态时，显示 查看/调整/审核 按钮；   停用时需判断当前计费规则是否是某车型的唯一一条计费规则，若是则停用失败
								if(c.cencorStatus ==1){
									if(c.isAvailable==1&&c.isStandardBilling==0){
										offList='<span class="glyphicon pricingRule-operate-offList" data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="停用">停用</span>';
									}else if(c.isAvailable==0){
										edit ='<span class="glyphicon pricingRule-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
										onList='<span class="glyphicon pricingRule-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="启用">启用</span>';
									}else if(c.isAvailable==-1){
										edit ='<span class="glyphicon pricingRule-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
										onList='<span class="glyphicon pricingRule-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="启用">启用</span>';
									}
								}else if(c.cencorStatus==0||c.cencorStatus==3){
									op='<span class="glyphicon pricingRule-operate-op" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="审核">审核</span>';
									edit ='<span class="glyphicon pricingRule-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
								}
								
	        					return find+edit+onList+offList+op;
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
			},
			//点击更多
			handleClickMore:function(){
				$("#morePricingRuleSearchafter").click(function(){
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
	return pricingRule;
});


