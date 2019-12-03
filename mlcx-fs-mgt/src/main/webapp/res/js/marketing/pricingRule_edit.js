define([],function() {
var pricingRuleEdit = {
		appPath : getPath("app"),
		init : function() {
			
			//关闭计费规则详情编辑页
			$("#closeEditPricingRule").click(function(){
				closeTab("计费规则调整");
            });
			
			//费规则详情编辑提交
			$("#EditPricingRule").click(function(){
				pricingRuleEdit.updatecheckPlan();
			});
			$("#customizedDateBtn").click(function(){
				pricingRuleEdit.saveCustomizedDate();
			});
			$("#customizedDateCancel").click(function() {  
				$("#customizedDateModal").modal("hide");
            });
			//添加自定义计费 弹出框
			$("#customizedDateModalDay").click(function() {
				var form = $("form[name=customizedDateForm]");
				form.find(".pdd").val(""); 
				$("#customizedDateModal").modal("show");
            });
			 $("input[name=isOverdue]").click(function(){
				 pricingRuleEdit.pageListDay();
				  });
			var form = $("form[name=pricingRuleEditForm]");
			var orderCaculateType = form.find("input[name='orderCaculateType']").val();
			if(orderCaculateType=="1"){
				//自定义计费规则列表
				pricingRuleEdit.pageListDay();
			}
			//选择车辆品牌后匹配相应的车型
			form.find("select[name='carBrand']").change(function(){
				var brandId = form.find("select[name='carBrand']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: pricingRuleEdit.appPath+"/pricingRule/getCarModleByBrand.do",
		             data: {brandId:brandId},
		             success: function(res){
		            	 var carSeries = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='carModel']").html("");
		            		 var option = "";
		            		 for(var i=0;i<carSeries.length;i++){
		            			 option+="<option  value='"+carSeries[i].carSeriesId+"'> "+carSeries[i].carSeriesName+" </option>";
		              		 }
		            		 form.find("select[name='carModel']").html(option);
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		                 }
		             }
				});
			});
			//高峰时段数据列表
			pricingRuleEdit.peakHousrList();
			//高峰时段弹出框
			$("#addPeakhousrDay").click(function() {
				var form = $("form[name=peakHousrModalForm]");
				form.find(".pdd").val(""); 
				form.find("#peakHourId").val(""); 
				$("#peakHousrModalDiv").modal("show");
            });
			//点击保存高峰时段信息
			$("#peakHousrEditBtn").click(function(){
				if(pricingRuleEdit.checkPeakForm() == true){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要保存吗？", function(result) {
						if(result){
							pricingRuleEdit.savePeakHousr();
						}else{
							$("#peakHousrModalDiv").modal("hide");
						}
						
					});
				}
			});
			//点击高峰时段取消
			$("#peakHousrEditCancel").click(function() {  
				$("#peakHousrModalDiv").modal("hide");
            });
			
		},
		//验证表单
		checkPeakForm:function(){
			var form = $("form[name=peakHousrModalForm]");
			$("span[name='peakStartHoursAdd']").empty();
			$("span[name='peakEndHoursAdd']").empty();
			$("span[name='priceOfMinuteAdd']").empty();
			$("span[name='priceOfKmAdd']").empty();
			
			var peakStartHours = form.find("input[name='peakStartHours']").val();
			var peakEndHours = form.find("input[name='peakEndHours']").val();
			var priceOfMinute = form.find("input[name='priceOfMinute']").val();
			var priceOfKm = form.find("input[name='priceOfKm']").val();
			
			var minTime = "0";
			var maxTime = "23";
			
			if (peakStartHours == "") {
				$("span[name='peakStartHoursAdd']").append("<font color='red'>请输入高峰时段开始时间！</font>");
				return false;
			}
			if (peakStartHours!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(peakStartHours)) {
				$("span[name='peakStartHoursAdd']").append("<font color='red'>开始时间输入有误(正数或0)！</font>");
				return false;
			}
			
			if (peakEndHours == "") {
				$("span[name='peakEndHoursAdd']").append("<font color='red'>请输入高峰时段结束时间！</font>");
				return false;
			}
			if (peakEndHours!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(peakEndHours)) {
				$("span[name='peakEndHoursAdd']").append("<font color='red'>结束时间输入有误(正数)！</font>");
				return false;
			}
			
			if(parseInt(peakStartHours) > parseInt(peakEndHours)){
				$("span[name='peakEndHoursAdd']").append("<font color='red'>开始时间不能大于结束时间！</font>");
				return false;
			}
			
			if(parseInt(peakStartHours) == parseInt(peakEndHours)){
				$("span[name='peakEndHoursAdd']").append("<font color='red'>开始时间不能等于结束时间！</font>");
				return false;
			}
			
			if(parseInt(peakStartHours) > parseInt(maxTime)){
				$("span[name='peakStartHoursAdd']").append("<font color='red'>开始时间不在合法时间段内！</font>");
				return false;
			}
			if(parseInt(peakEndHours) > parseInt(maxTime)){
				$("span[name='peakEndHoursAdd']").append("<font color='red'>结束时间不在合法时间段内！</font>");
				return false;
			}
			
			if (priceOfMinute == "") {
				$("span[name='priceOfMinuteAdd']").append("<font color='red'>请输入时间计费！</font>");
				return false;
			}
			if (priceOfMinute!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(priceOfMinute)) {
				$("span[name='priceOfMinuteAdd']").append("<font color='red'>时间计费输入有误(正数或0)！</font>");
				return false;
			}
			
			if (priceOfKm == "") {
				$("span[name='priceOfKmAdd']").append("<font color='red'>请输入里程计费！</font>");
				return false;
			}
			if (priceOfKm!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(priceOfKm)) {
				$("span[name='priceOfKmAdd']").append("<font color='red'>里程计费输入有误(正数或0)！</font>");
				return false;
			}
			if(priceOfMinute == "0" && priceOfKm == "0"){
				$("span[name='priceOfKmAdd']").append("<font color='red'>时间和里程计费不能同时为0！</font>");
				return false;
			}
			return true;
		},
		

		//保存高峰时段
		savePeakHousr:function(){
			var form = $("form[name=peakHousrModalForm]");
			form.ajaxSubmit({
    			url:pricingRuleEdit.appPath+"/peakHours/savePeakHours.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg,function(){
							$("#peakHousrModalDiv").modal("hide");
							form.resetForm();
							$("#peakHousrModalListTable").DataTable().ajax.reload(function(){
    						}); 
						});
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" +msg);
					}
				},
				});
		},
		peakHousrList:function () {	
			var parkTpl = $("#peakHousrModalList").html();
			// 预编译模板
			var template = Handlebars.compile(parkTpl);
			$('#peakHousrModalListTable').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": pricingRuleEdit.appPath+"/peakHours/pageListPeakHours.do",
					"data": function ( d ) {
						var form = $("form[name=pricingRuleEditForm]");
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"ruleNo":$.trim(form.find("input[name=ruleNo]").val()),
						} );
					},
					"dataSrc": function ( json ) {
						json.recordsTotal=json.rowCount;
						json.recordsFiltered=json.rowCount;
						json.data=json.data;
						if(json.data.length<=0){
							$(".pcds").hide();
						}else{
							$(".pcds").show();
						}
						return json.data;
					},
					error: function (xhr, error, thrown) {  
		            }
				},
				
				"columns": [
		            { "title":"高峰时段开始时间","data": "peakStartHours" },
					{ "title":"高峰时段结束时间","data": "peakEndHours" },
					{ "title":"时间计费(元/分钟)","data": "priceOfMinute" },
					{ "title":"公里计费(元/公里)","data": "priceOfKm" },
					{ "title":"操作","orderable":false}
				],
			   initComplete: function () {
				    $("#parkTools").html("");
				},
				"drawCallback": function( settings ) {
					pricingRuleEdit.operatePeakHoursColumEvent();
        	    },
        	    "order": [[ 1, "desc" ]],
				"columnDefs": [
					{
						"targets": [4],
						"render": function (a, b, c, d) {
							var del='<span class="glyphicon operate-pk-del" data-id="'+c.peakHourId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">删除</span>';
							var form = $("form[name=pricingRuleEditForm]");
							var edit='<span class="glyphicon operate-pk-edit" data-id="'+c.peakHourId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
        					return del+edit;
						}
					}
				]
			});
		},
		 //编辑、删除高峰时段
		 operatePeakHoursColumEvent: function(){
			 $(".operate-pk-del").each(function(id,obj){
					$(this).on("click",function(){
						var peakHourId=$(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.ajax({
									 type: "post",
						             url: pricingRuleEdit.appPath+"/peakHours/delPeakHours.do",
						             data: {peakHourId:peakHourId},
						             dataType: "json",
						             success: function(data){
						            	 if(data.code="1"){
						            		 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
						            			 pricingRuleEdit.peakHousrList();
												});
						                 }
						             }
								});
							}
						});

					});
				});
			 
			 $(".operate-pk-edit").each(function(id,obj){
					$(this).on("click",function(){
						var peakHourId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleEdit.appPath+"/peakHours/updatePeakHours.do",
				             data: {peakHourId:peakHourId},
				             dataType: "json",
				             success: function(res){
				            	 var peakHoursDataObj = res.data;
				            	 if(peakHoursDataObj!=null){
									$("#peakHousrModalDiv").modal("show");
									var form = $("form[name=peakHousrModalForm]");
									form.find("input[name=peakHourId]").val(peakHourId);
									form.find("input[name=ruleNo]").val(peakHoursDataObj.ruleNo);
									form.find("input[name=peakStartHours]").val(peakHoursDataObj.peakStartHours);
									form.find("input[name=peakEndHours]").val(peakHoursDataObj.peakEndHours);
									form.find("input[name=priceOfMinute]").val(peakHoursDataObj.priceOfMinute);
									form.find("input[name=priceOfKm]").val(peakHoursDataObj.priceOfKm);
				                 }
				             }
						});
					});
				});

		 },
		 operateColumEvent: function(){
			 $(".operate-del").each(function(id,obj){
					$(this).on("click",function(){
						var customizedId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleEdit.appPath+"/pricingRule/delPricingRuleCustomizedDate.do",
				             data: {customizedId:customizedId},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            		 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
											$("#pricingRuleCustomizedDateListAdd").DataTable().ajax.reload(function(){
				    						}); 
										});
				                 }
				             }
						});
					});
				});
			 
			 $(".operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						var customizedId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleEdit.appPath+"/pricingRule/getPricingRuleCustomizedDateUpdate.do",
				             data: {customizedId:customizedId},
				             dataType: "json",
				             success: function(res){
				            	 var customizedDateObj = res.data;
				            	 if(customizedDateObj!=null){
									$("#customizedDateModal").modal("show");
									var form = $("form[name=customizedDateForm]");
									form.find("input[name=customizedId]").val(customizedDateObj.customizedId);
									form.find("input[name=ruleNo]").val(customizedDateObj.ruleNo);
									form.find("input[name=cityId]").val(customizedDateObj.cityId);
									form.find("input[name=cityName]").val(customizedDateObj.cityName);
									form.find("input[name=carModelName]").val(customizedDateObj.carModelName);
									form.find("input[name=companyId]").val(customizedDateObj.companyId);
									form.find("input[name=priceOfMinuteCustomized]").val(customizedDateObj.priceOfMinuteCustomized);
									form.find("input[name=priceOfKmCustomized]").val(customizedDateObj.priceOfKmCustomized);
									form.find("input[name=billingCapPerDayCustomized]").val(customizedDateObj.billingCapPerDayCustomized);
									form.find("input[name=customizedDateStr]").val(customizedDateObj.customizedDateStr);
				                 }
				             }
						});
					});
				});

		 },
		pageListDay:function () {	
			var parkTpl = $("#pricingRuleCustomizedDateTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(parkTpl);
			$('#pricingRuleCustomizedDateListAdd').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": pricingRuleEdit.appPath+"/pricingRule/pageListPricingRuleCustomizedDate.do",
					"data": function ( d ) {
						var form = $("form[name=pricingRuleEditForm]");
						var isOverdue=$('#oid input[name="isOverdue"]:checked ').val();
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"ruleNo":$.trim(form.find("input[name=ruleNo]").val()),
							"isOverdue":isOverdue,
							
						} );
					},
					"dataSrc": function ( json ) {
						json.recordsTotal=json.rowCount;
						json.recordsFiltered=json.rowCount;
						json.data=json.data;
						if(json.data.length<=0){
							$(".pcds").hide();
						}else{
							$(".pcds").show();
						}
						return json.data;
					},
					error: function (xhr, error, thrown) {  
		            }
				},
				
				"columns": [
		            { "title":"自定义日期","data": "customizedDate" },
					{ "title":"时间计费(元/分钟)","data": "priceOfMinuteCustomized" },
					{ "title":"公里计费(元/公里)","data": "priceOfKmCustomized" },
					{ "title":"计费封顶（元/天）","data": "billingCapPerDayCustomized" },	
					{ "title":"操作","orderable":false}
				],
			   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
			   //"dom": "<'row'<'#parkTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				    $("#parkTools").html("");
				},
				"drawCallback": function( settings ) {
					pricingRuleEdit.operateColumEvent();
        	    },
        	    "order": [[ 1, "desc" ]],
				"columnDefs": [
	               {
						    "targets": [0],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	var now = moment(a);
									return now.format('YYYY-MM-DD');
						        }else{
						        	return "";
						        }
						    }
						},
					 {
					    "targets": [1,2,3],
					    "render": function(a,b,c,d) {
					    	if(a!=null){
					        	return a;
					        }else{
					        	return "";
					        }
					    }
					},
					{
						"targets": [4],
						"render": function (a, b, c, d) {
							var edit="";
							var del='<span class="glyphicon operate-del" data-id="'+c.customizedId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">删除</span>';
							var form = $("form[name=pricingRuleEditForm]");
							var isOverdue=$('#oid input[name="isOverdue"]:checked ').val();
							if(isOverdue==0){
									   edit='<span class="glyphicon operate-edit" data-id="'+c.customizedId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
							}
							
        					return del+edit;
						}
					}
//					
//        	        
				]
			});
		},
		
		saveCustomizedDate:function(){
			var form = $("form[name=customizedDateForm]");
			form.ajaxSubmit({
    			url:pricingRuleEdit.appPath+"/pricingRule/saveOrEditCustomizdDate.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
							$("#customizedDateModal").modal("hide");
							form.resetForm();
							$("#pricingRuleCustomizedDateListAdd").DataTable().ajax.reload(function(){
    						}); 
						});
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！ "+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='priceOfMinuteCustomizedError']").empty();
					$("span[name='priceOfKmCustomizedError']").empty();
					$("span[name='billingCapPerDayCustomizedError']").empty();
					$("span[name='customizedDateError']").empty();
					if (form.find("input[name='priceOfMinuteCustomized']").val()=="") {
						$("span[name='priceOfMinuteCustomizedError']").append("<font color='red'>请输入时间计费！</font>");
						return false;
					}
					if (form.find("input[name='priceOfMinuteCustomized']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfMinuteCustomized']").val())) {
						$("span[name='priceOfMinuteCustomizedError']").append("<font color='red'>时间计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='priceOfKmCustomized']").val()=="") {
						$("span[name='priceOfKmCustomizedError']").append("<font color='red'>请输入里程计费！</font>");
						return false;
					}
					if (form.find("input[name='priceOfKmCustomized']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfKmCustomized']").val())) {
						$("span[name='priceOfKmCustomizedError']").append("<font color='red'>里程计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='billingCapPerDayCustomized']").val()=="") {
						$("span[name='billingCapPerDayCustomizedError']").append("<font color='red'>请输入封顶金额！</font>");
						return false;
					}
					if (form.find("input[name='billingCapPerDayCustomized']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='billingCapPerDayCustomized']").val())) {
						$("span[name='billingCapPerDayCustomizedError']").append("<font color='red'>封顶金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='customizedDateStr']").val()=="") {
						$("span[name='customizedDateError']").append("<font color='red'>请选择日期！</font>");
						return false;
					}
				}
				});
		},
		
		updatePricingRule:function(){
			var form = $("form[name=pricingRuleEditForm]");
			var orderCaculateType = form.find("input[name='orderCaculateType']").val();
			form.ajaxSubmit({
				url : pricingRuleEdit.appPath + "/pricingRule/updatePricingRule.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
							closeTab("计费规则调整");
							$("#pricingRuleList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='ruleNameEdit']").empty();
					$("span[name='cityIdEdit']").empty();
					$("span[name='carBrandEdit']").empty();
					$("span[name='carModelEdit']").empty();
					$("span[name='priceOfMinuteEdit']").empty();
					$("span[name='priceOfKmEdit']").empty();
					$("span[name='regardlessFranchiseEdit']").empty();
					$("span[name='insuranceAmountEdit']").empty();
					if(orderCaculateType=="1"){
						$("span[name='priceOfMinuteOrdinaryEdit']").empty();
						$("span[name='priceOfKmOrdinaryEdit']").empty();
						$("span[name='billingCapPerDayOrdinaryEdit']").empty();
					}
					$("span[name='companyIdEdit']").empty();
//					$("span[name='discountEdit']").empty();
					$("span[name='baseFeeEdit']").empty();
//					$("span[name='priceOfKmEdit']").empty();
//					$("span[name='servicePriceOfMinuteEdit']").empty();
//					$("span[name='servicePriceOfKmEdit']").empty();
					$("span[name='billingCapPerDayEdit']").empty();
				//	$("span[name='priorityEdit']").empty();
//					$("span[name='availableTime1Edit']").empty();
//					$("span[name='availableTime2Edit']").empty();
//					$("span[name='freeMinutesEdit']").empty();
					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameEdit']").append("<font color='red'>请输入名称！</font>");
						return false;
					}
					if (form.find("select[name='cityId']").val()=="") {
						$("span[name='cityIdEdit']").append("<font color='red'>请选择城市！</font>");
						return false;
					}
					if (form.find("select[name='carBrand']").val()=="") {
						$("span[name='carBrandEdit']").append("<font color='red'>请选择车辆品牌！</font>");
						return false;
					}
					if (form.find("select[name='carModel']").val()==""||form.find("select[name='carModel']").val()==null) {
						$("span[name='carModelEdit']").append("<font color='red'>请选择车辆型号！</font>");
						return false;
					}
					if (form.find("input[name='priceOfMinute']").val()=="") {
						$("span[name='priceOfMinuteEdit']").append("<font color='red'>请输入工作日按时间计费金额！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='priceOfMinuteOrdinary']").val()=="") {
							$("span[name='priceOfMinuteOrdinaryEdit']").append("<font color='red'>请输入周末按时间计费金额！</font>");
							return false;
						}
					}
					if (form.find("input[name='priceOfMinute']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfMinute']").val())) {
						$("span[name='priceOfMinuteEdit']").append("<font color='red'>工作日时间计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='priceOfMinuteOrdinary']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfMinuteOrdinary']").val())) {
							$("span[name='priceOfMinuteOrdinaryEdit']").append("<font color='red'>周末时间计费金额输入有误(正数或0)！</font>");
							return false;
						}
					}
					if (form.find("input[name='priceOfKm']").val()=="") {
						$("span[name='priceOfKmEdit']").append("<font color='red'>请输入工作日里程计费金额！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='priceOfKmOrdinary']").val()=="") {
							$("span[name='priceOfKmOrdinaryEdit']").append("<font color='red'>请输入周末里程计费金额！</font>");
							return false;
						}
					}
					if (form.find("input[name='priceOfKm']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfKm']").val())) {
						$("span[name='priceOfKmEdit']").append("<font color='red'>工作日里程计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='priceOfKmOrdinary']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='priceOfKmOrdinary']").val())) {
							$("span[name='priceOfKmOrdinaryEdit']").append("<font color='red'>周末里程计费金额输入有误(正数或0)！</font>");
							return false;
						}
					}
					if (form.find("input[name='baseFee']").val()=="") {
						$("span[name='baseFeeEdit']").append("<font color='red'>请输入起步价金额！</font>");
						return false;
					}
					
					if (form.find("input[name='baseFee']").val()!=""&&!/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/.test(form.find("input[name='baseFee']").val())) {
						$("span[name='baseFeeEdit']").append("<font color='red'>起步价输入有误(正数或小数后两位)！</font>");
						return false;
					}
//					if (form.find("select[name='companyId']").val()!="") {
//						if (form.find("input[name='discount']").val()=="") {
//							$("span[name='discountEdit']").append("<font color='red'>请输入集团会员享受的折扣！</font>");
//							return false;
//						}else if(form.find("input[name='discount']").val()!=""){
//							if(!/^([01](\.0+)?|0\.[0-9]{0,2})$/.test(form.find("input[name='discount']").val())){
//								$("span[name='discountEdit']").append("<font color='red'>折扣需输入0~1之间且最多小数位为2的数！</font>");
//								return false;
//							}
//						}
////						if (form.find("input[name='freeMinutes']").val()=="") {
////							$("span[name='freeMinutesEdit']").append("<font color='red'>请输入集团会员享受的免费时长！</font>");
////							return false;
////						}
//					}
					if (form.find("input[name='priceOfKm']").val()=="") {
						$("span[name='priceOfKmEdit']").append("<font color='red'>请输入按里程计费！</font>");
						return false;
					}
//					if (form.find("input[name='servicePriceOfMinute']").val()=="") {
//						$("span[name='servicePriceOfMinuteEdit']").append("<font color='red'>请输入人员服务按时计费！</font>");
//						return false;
//					}
//					if (form.find("input[name='servicePriceOfKm']").val()=="") {
//						$("span[name='servicePriceOfKmEdit']").append("<font color='red'>请输入人员服务按里程计费！</font>");
//						return false;
//					}
//					if (form.find("input[name='billingCapPerDay']").val()=="") {
//						$("span[name='billingCapPerDayEdit']").append("<font color='red'>请输入计费封顶！</font>");
//						return false;
//					}
					if (form.find("input[name='billingCapPerDay']").val()=="") {
						$("span[name='billingCapPerDayEdit']").append("<font color='red'>请输入工作日计费封顶金额！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='billingCapPerDayOrdinary']").val()=="") {
							$("span[name='billingCapPerDayOrdinaryEdit']").append("<font color='red'>请输入周末计费封顶金额！</font>");
							return false;
						}
					}
					if (form.find("input[name='billingCapPerDay']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='billingCapPerDay']").val())) {
						$("span[name='billingCapPerDayEdit']").append("<font color='red'>工作日计费封顶金额输入有误(正数或0)！</font>");
						return false;
					}
					if(orderCaculateType=="1"){
						if (form.find("input[name='billingCapPerDayOrdinary']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='billingCapPerDayOrdinary']").val())) {
							$("span[name='billingCapPerDayOrdinaryEdit']").append("<font color='red'>周末计费封顶金额输入有误(正数或0)！</font>");
							return false;
						}
					}
//					if (form.find("input[name='priority']").val()=="") {
//						$("span[name='priorityEdit']").append("<font color='red'>请输入优先级！</font>");
//						return false;
//					}
//					if (form.find("input[name='priority']").val()!=""&&!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='priority']").val())) {
//						$("span[name='priorityEdit']").append("<font color='red'>优先级请输入正整数！</font>");
//						return false;
//					}
//					if (form.find("input[name='availableTime1']").val()=="") {
//						$("span[name='availableTime1Edit']").append("<font color='red'>有效期至不为空！</font>");
//						return false;
//					}
//					if (form.find("input[name='availableTime2']").val()=="") {
//						$("span[name='availableTime2Edit']").append("<font color='red'>有效期至不为空！</font>");
//						return false;
//					}
//					if (form.find("input[name='availableTime1']").val()!=""&&form.find("input[name='availableTime2']").val()!="") {
//						if(new Date(form.find("input[name='availableTime1']").val())>new Date(form.find("input[name='availableTime2']").val())){
//							$("span[name='availableTime1Edit']").append("<font color='red'>有效期开始时间不能大于结束时间！</font>");
//							return false;
//						}
//					}
					if (form.find("input[name='regardlessFranchise']").val()=="") {
						$("span[name='regardlessFranchiseEdit']").append("<font color='red'>请输入不计免赔费！</font>");
						return false;
					}
					if (form.find("input[name='regardlessFranchise']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='regardlessFranchise']").val())) {
						$("span[name='regardlessFranchiseEdit']").append("<font color='red'>不计免赔费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='insuranceAmount']").val()=="") {
						$("span[name='insuranceAmountEdit']").append("<font color='red'>请输入保险费！</font>");
						return false;
					}
					if (form.find("input[name='insuranceAmount']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='insuranceAmount']").val())) {
						$("span[name='insuranceAmountEdit']").append("<font color='red'>保险费金额输入有误(正数或0)！</font>");
						return false;
					}
				}
			});
		},
 updatecheckPlan:function() {
	 	var form= $("form[name=pricingRuleEditForm]");
		var carBrand=form.find("select[name=carBrand]").val();
		var carModel=form.find("select[name=carModel]").val();
		var companyId=form.find("select[name=companyId]").val();
		var ruleNo=form.find("input[name=ruleNo]").val();
		if (form.find("input[name='ruleName']").val()=="") {
			$("span[name='ruleNameEdit']").html("<font color='red'>请输入名称！</font>");
			return false;
		}else{
			$("span[name='ruleNameEdit']").html("");
		}
		if (form.find("select[name='cityId']").val()=="") {
			$("span[name='cityIdEdit']").html("<font color='red'>请选择城市！</font>");
			return false;
		}else{
			$("span[name='cityIdEdit']").html("");
		}
		if (carBrand=="") {
				$("span[name='carBrandEdit']").html("<font color='red'>请选择车辆品牌！</font>");
				return false;
		}else{
			$("span[name='carBrandEdit']").html("");
		}
		if (carModel=="") {
				$("span[name='carModelEdit']").html("<font color='red'>请选择车辆型号！</font>");
				return false;
		}else{
			$("span[name='carModelEdit']").html("");
		}
			 $.ajax({
					url:pricingRuleEdit.appPath+"/pricingRule/uniquePricingRule.do",//验证计费规则的唯一性
					type:"post",
					data:{carBrand:carBrand,carModel:carModel,companyId:companyId,ruleNo:ruleNo},
					dataType:"json",
					success:function(res){ 
						if(res == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该车型的计费规则已经存在，请修改车辆品牌和车型！");
						}else{
							pricingRuleEdit.updatePricingRule();
						}
					}
			 });
}
}
return pricingRuleEdit;
})
