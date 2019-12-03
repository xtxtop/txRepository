define([],function() {	
	var pricingRuleDay={
			appPath:getPath("app"),	
			init: function () {	
				//启用状态提交
				$("#pricingRuleDayOnFormBtn").click(function(){
					pricingRuleDay.onPricingDayRule();
	            });
				//启用取消
				$("#pricingRuleDayOnCancelBtn").click(function(){
					$("#pricingRuleDayOnModal").modal("hide");
	            });
				//停用状态提交
				$("#pricingRuleDayOffBtn").click(function(){
					pricingRuleDay.offPricingDayRule();
	            });
				//停用取消
				$("#pricingRuleDayOffCancel").click(function(){
					$("#pricingRuleDayOffModal").modal("hide");
	            });
				
	            //数据列表
				pricingRuleDay.pageList();
				//查询
				$("#pricingRuleDaySearchafter").click(function(){
					pricingRuleDay.pageList();
	            });
				$("#pricingRuleDayOnModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=pricingRuleDayOnForm]");
	            	form.resetForm();
	            	$("#onPricingDayRuleMemo").text("");
	            	form.find("input[name=ruleId]").val("");
	            });
				$("#pricingRuleDayOffModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=pricingRuleDayOffForm]");
	            	form.resetForm();
	            	$("#offPricingDayRuleMemo").text("");
	            	form.find("input[name=ruleId]").val("");
	            });
				
				$("#customizedDateModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=customizedDateForm]");
	            	form.resetForm();
	            });
				$("#customizedDateDayCancel").click(function() {  
					$("#customizedDateDayModal").modal("hide");
	            });
				
				
			},
				   //启用操作
				onPricingDayRule: function () {
		        	var form = $("form[name=pricingRuleDayOnForm]");
					form.ajaxSubmit({
		    			url:pricingRuleDay.appPath+"/pricingRuleDay/enablePricingRuleDay.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									$("#onPricingDayRuleMemo").text("");
									form.find("textarea[name='availableMemo']").html("");
									$(".bootbox").modal("hide");
									$("#pricingRuleDayOnModal").modal("hide");
									$("#pricingRuleDayList").DataTable().ajax.reload(function(){
		    						}); 
								});
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！",function(){
									$("#onPricingDayRuleMemo").text("");
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
					offPricingDayRule: function () {
			        	var form = $("form[name=pricingRuleDayOffForm]");
						form.ajaxSubmit({
							url:pricingRuleDay.appPath+"/pricingRuleDay/disablePricingRuleDay.do",
							type : "post",
							dataType:"json", //数据类型  
							success:function(res){
								var msg = res.msg;
								var code = res.code;
								if(code == "1"){ 
								  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									  form.find("textarea[name='availableMemo']").html("")
									  $("#offPricingDayRuleMemo").text("");
									  $("#pricingRuleDayOffModal").modal("hide");
									  $(".bootbox").modal("hide");
									  $("#pricingRuleDayList").DataTable().ajax.reload(function(){}); 
								  });
								  
								}else{
									$("#pricingRuleDayOffModal").modal("hide");
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
									$("#offPricingDayRuleMemo").text("");
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
						addTab("日租计费规则详情",pricingRuleDay.appPath+ "/pricingRuleDay/toPricingRuleDayView.do?ruleId="+$(this).data("id"));
					});
				});
				$(".pricingRuleDay-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("日租计费规则调整",pricingRuleDay.appPath+ "/pricingRuleDay/toUpdateViewDay.do?ruleId="+$(this).data("id"));			
					});
				});
				$(".pricingRuleDay-operate-cencor").each(function(id,obj){
					$(this).on("click",function(){
						addTab("日租计费规则审核",pricingRuleDay.appPath+ "/pricingRuleDay/toPricingRuleDayCencorView.do?ruleId="+$(this).data("id"));			
					});
				});
				$(".pricingRuleDay-operate-customizedDate").each(function(){
					$(this).on("click",function(){
						addTab("自定义日期计费列表",pricingRuleDay.appPath+ "/pricingRuleDay/toPricingRuleCustomDate.do?ruleId="+$(this).data("id"));
					});
				});
				//启用弹出层
				$(".pricingRuleDay-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=pricingRuleDayOnForm]");
						var pricingRuleDayRuleId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleDay.appPath+"/pricingRuleDay/toPricingRuleDay.do",
				             data: {ruleId:pricingRuleDayRuleId},
				             dataType: "json",
				             success: function(result){
				            	 var code = result.code;
				            	 var data = result.data;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#pricingRuleDayOnModal").modal("show");
				            	    form.find("input[name='ruleId']").val(data.ruleId);
									$("#onPricingDayRuleMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将计费规则"+"“"+data.ruleName+"”"+"启用吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".pricingRuleDay-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=pricingRuleDayOffForm]");
						var pricingRuleDayNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleDay.appPath+"/pricingRuleDay/toPricingRuleDay.do",
				             data: {ruleId:pricingRuleDayNo},
				             dataType: "json",
				             success: function(result){
				            	 var code = result.code;
				            	 var data = result.data;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#pricingRuleDayOffModal").modal("show");
				            	    form.find("input[name='ruleId']").val(data.ruleId);
									$("#offPricingDayRuleMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将计费规则"+"“"+data.ruleName+"”"+"停用吗？停用后的计费规则将无法使用？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
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
	        					//"cityId":form.find("select[name=cityId]").val(),
	        					"carModelId":form.find("select[name=carModelId]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
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
						{ "title":"计费规则名称","data": "ruleName","defaultContent":"" },
						{ "title":"城市","data": "cityName","defaultContent":"" },
						{ "title":"车辆型号","data": "carModelName","defaultContent":"" },
						{ "title":"使用类型","data": "carType","defaultContent":"" },
						{ "title":"工作日计费","data": "priceOfDay","defaultContent":"" },
						{ "title":"周末计费","data": "priceOfDayOrdinary","defaultContent":"" },
						{ "title":"不计免赔(元/天)","data": "regardlessFranchise"},
						{ "title":"保险费(元/天)","data": "insuranceAmount"},
						{ "title":"状态","data": "isAvailable"},
						{ "title":"审核状态","data": "cencorStatus" ,"defaultContent":""},
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#pricingRuleDaytool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#pricingRuleDayTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#pricingRuleDayTools").html("");
		       			$("#pricingRuleDayTools").append('<button type="button" class="btn-new-type pricingRuleDayTools-operate-add">新增</button>');
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
						    "targets": [3],//车辆类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。
						    "render": function(a,b, c, d) {
						        if(a==1){
						        	return "经济型";
						        }else if(a==2){
						        	return "商务型";
						        }else if(a==3){
						        	return "豪华型";
						        }else if(a==4){
						        	return "6至15座商务";
						        }else if(a==5){
						        	return "SUV";
						        }else{
						        	return "";
						        }
						    	
						    }
						},          
						{
						    "targets": [4,5],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
							        return a+"元/天";
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [8],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(a==1){
								        return "启用";
							        }else if(a==0){
							        	return "停用";
							        }
						    	}else{
						        	return "停用";
						        }
						    }
						},
						//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
						{
						    "targets": [9],
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
							"targets": [10],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  pricingRuleDay-operate-find" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="查看">查看</span>';
								var edit = "";
								var onList="";
	        					var offList="";
	        					var cencor="";
	        					var customizedDate = "";
	        					
	        					if(c.isAvailable==0&& c.cencorStatus ==1){
									onList='<span class="glyphicon  pricingRuleDay-operate-onList" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="启用">启用</span>';	
									edit ='<span class="glyphicon  pricingRuleDay-operate-edit" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
									customizedDate = '<span class="glyphicon  pricingRuleDay-operate-customizedDate" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="查看自定义日期">查看自定义日期</span>';
								}
								if(c.isAvailable==1&& c.cencorStatus ==1){
									offList='<span class="glyphicon  pricingRuleDay-operate-offList" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="停用">停用</span>';
									customizedDate = '<span class="glyphicon  pricingRuleDay-operate-customizedDate" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="查看自定义日期">查看自定义日期</span>';
								}
								if(c.cencorStatus==0||c.cencorStatus==3){
									cencor='<span class="glyphicon  pricingRuleDay-operate-cencor" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="审核">审核</span>';
									edit ='<span class="glyphicon  pricingRuleDay-operate-edit" data-id="'+c.ruleId+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
								}
								
	        					return find+edit+onList+offList+cencor+customizedDate;
							}
						}
					]
				});
			}
	    };
	return pricingRuleDay;
});


