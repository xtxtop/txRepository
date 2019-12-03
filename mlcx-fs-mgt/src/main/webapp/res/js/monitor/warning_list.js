define([],function() {	
	var warning={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				warning.pageList();
				warning.handleClickMore();
				//查询
				$("#warningSearchafter").click(function(){
					var form = $("form[name=warningSearchForm]");
					var warningTimeStart =  form.find("input[name=warningTimeStart]").val();
					var warningTimeEnd = form.find("input[name=warningTimeEnd]").val();
					if(warningTimeStart!=""&&warningTimeEnd!=""){
						if(new Date(warningTimeStart)>new Date(warningTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "告警开始时间不能大于结束时间！");
						}else{
							warning.pageList();
						}
					}else{
						warning.pageList();
					}
	            });
				$("#OffWarningModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	form.find("input[name=warningNo]").val("");
	            });
				//取消
				$("#warningOffCancel").click(function(){
					 $("#OffWarningModal").modal("hide");
	            });
				//关闭详情
				$("#closeViewwarning").click(function(){
					closeTab("告警详情");
	            });
				//关闭提交
				$("#warningOffBtn").click(function(){
					$("#warningOffBtn").prop("disabled",true);
					warning.offFormSub();
	            });
				
			},
			   //下线操作
			offFormSub: function () {
	        	var form = $("form[name=offForm]");
				form.ajaxSubmit({
					url:warning.appPath+"/warning/updateWarning.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
						 
						  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭成功！",function(){
							  $("#OffWarningModal").modal("hide");
							  $(".bootbox").modal("hide");
							  form.find("textarea[name='closedMemo']").html("");
							  $("#warningOffBtn").prop("disabled",false);
							  $("#warningList").DataTable().ajax.reload(function(){
	    						});
						  });
						   
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "关闭失败！",function(){
								 $("#OffWarningModal").modal("hide");
								 $(".bootbox").modal("hide");
								 $("#warningOffBtn").prop("disabled",false);
							});
							
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						if (form.find("textarea[name='closedMemo']").val()=="") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入备注！");
							$("#warningOffBtn").prop("disabled",false);
							return false;
						}
					}
					});
				},
			  operateColumEvent: function(){
				  $(".warning-operate-find").each(function(){
						$(this).on("click",function(){
							addTab("告警详情",warning.appPath+ "/warning/toWarningView.do?warningNo="+$(this).data("id"));
						});
					});
				//停用弹出层
					$(".warning-operate-edit").each(function(id,obj){
						$(this).on("click",function(){
							var pricingRuleNo=$(this).data("id");
							$.ajax({
								 type: "post",
					             url: warning.appPath+"/warning/toWarning.do",
					             data: {warningNo:pricingRuleNo},
					             dataType: "json",
					             success: function(res){
					            	 if(null != res && res.code ==1){
					            	    $("#OffWarningModal").modal("show");
					            	    var form = $("form[name=offForm]");
					            	    form.find("input[name='warningNo']").val(res.data.warningNo);
					                 }else if(res.code == "2"){
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg+"，暂时不可关闭",function(){
												 $("#OffWarningModal").modal("hide");
												 $(".bootbox").modal("hide");
												 $("#warningOffBtn").prop("disabled",false);
											});
									 }else{
					                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前告警记录暂时不可关闭！");
					                 }
					             }
							});
						});
					});
			  },
			pageList:function () {	
				var warningTpl = $("#warningTpl").html();
				// 预编译模板
				var template = Handlebars.compile(warningTpl);
				$('#warningList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": warning.appPath+'/warning/pageListWarning.do',
						"data": function ( d ) {
							var form = $("form[name=warningSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"warningLevel":form.find("select[name=warningLevel]").val(),
								"warningType":form.find("select[name=warningType]").val(),
								"type":form.find("input[name=type]").val(),
								"isNeedManualClosed":form.find("select[name=isNeedManualClosed]").val(),
								"isClosed":form.find("select[name=isClosed]").val(),
	        					"warningTimeStart":form.find("input[name=warningTimeStart]").val()+" 00:00:00",
	        					"warningTimeEnd":form.find("input[name=warningTimeEnd]").val()+" 23:59:59",
								"cityId":form.find("select[name=cityId]").val()
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
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"告警级别","data": "warningLevel" },
						{ "title":"告警对象","data": "warningType" },	
						{ "title":"告警类型","data": "warningSubType" },
						{ "title":"告警时间","data": "warningTime" },
						{ "title":"告警内容","data": "warningContent" },
						{ "title":"相关场站","data": "parkName" },
						{ "title":"相关车辆","data": "carPlateNo" },
						{ "title":"相关会员","data": "memberName" },	
						{ "title":"相关订单","data": "orderNo" },	
						{ "title":"需要人工关闭","data": "isNeedManualClosed" },
						{ "title":"是否关闭","data": "isClosed" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#warningtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#warningTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#warningTools").html("");
//		       			$("#warningTools").append('<button type="button" class="btn btn-default btn-sm warningTools-operate-export">导出</button>');
		       			/**
		       			 * 导出
		       			 */
		       			$(".warningTools-operate-export").on("click",function(){
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出告警吗？", function(result) {
								if(result){
									window.location.href = warning.appPath+ "/warning/exportWarning.do?filepath=warning&newFileName=warning.xls";
								}						
		       				});
		       			}); 	     			
	        			
					},
					"drawCallback": function( settings ) {
						warning.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						        if(c.warningLevel==1){
						        	return "一级";
						        }else if(c.warningLevel==2){
						        	return "二级";
						        }else{
						        	return "三级";
						        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [5],
						    "render": function(a,b, c, d) {
						    	if(a!=""&&a!=null){
						    		var now = moment(c.warningTime); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    }
						},
						//是否需要人工关闭（0、不需人工关闭，1、需人工关闭，默认0）
						{
						    "targets": [11],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						        if(c.isNeedManualClosed==0){
						        	return "否";
						        }else{
						        	return "是";
						        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						//是否关闭（0、未关闭，1、已关闭，默认0）
						{
						    "targets": [12],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						        if(c.isClosed==0){
						        	return "未关闭";
						        }else{
						        	return "已关闭";
						        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
							"targets": [13],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  warning-operate-find" data-id="'+c.warningNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var edit='<span class="glyphicon  warning-operate-edit" data-id="'+c.warningNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">关闭</span>';
	        					if(c.isClosed==0){
	        					return find+edit;
	        					}else{
	        					return find;
	        					}
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreWarningList").click(function(){
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
	return warning;
});


