define([],function() {	
	var pricingPackage={
			appPath:getPath("app"),	
			init: function () {	
				//上架状态提交
				$("#pricingPackageOnFormBtn").click(function(){
					pricingPackage.onFormSub();
	            });
				//上架取消
				$("#pricingPackageOnCancelBtn").click(function(){
					$("#onpricingPackageModal").modal("hide");
	            });
				//下架状态提交
				$("#pricingPackageOffBtn").click(function(){
					pricingPackage.offFormSub();
	            });
				//下架取消
				$("#pricingPackageOffCancel").click(function(){
					$("#OffpricingPackageModal").modal("hide");
	            });
				
	            //数据列表
				pricingPackage.pageList();
				//查询
				$("#pricingPackageSearchafter").click(function(){
					pricingPackage.pageList();
	            });
				$("#onpricingPackageModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onpricingPackageForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#onMemoPF").text("");
	            	form.find("textarea[name='availableReason']").html("")
	            	form.find("input[name=packageNo]").val("");
	            });
				$("#OffpricingPackageModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offpricingPackageForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	form.find("textarea[name='availableReason']").html("")
	            	$("#offMemo").text("");
	            	form.find("input[name=packageNo]").val("");
	            });
				
			},
				   //启用操作
		        onFormSub: function () {
		        	var form = $("form[name=onpricingPackageForm]");
					form.ajaxSubmit({
		    			url:pricingPackage.appPath+"/pricingPackage/toUpdatePricingPackage.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									$("#onpricingPackageModal").modal("hide");
									$(".bootbox").modal("hide");
									pricingPackage.pageList();
								});
								 
							}else{
								
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！",function(){
									$("#onpricingPackageModal").modal("hide");
									$(".bootbox").modal("hide");
									pricingPackage.pageList();
								});
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='availableReason']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入原因！");
								return false;
							}
						}
						});
					},
					   //下线操作
					offFormSub: function () {
			        	var form = $("form[name=offpricingPackageForm]");
						form.ajaxSubmit({
							url:pricingPackage.appPath+"/pricingPackage/toUpdatePricingPackage.do",
							type : "post",
							dataType:"json", //数据类型  
							success:function(res){
								var msg = res.msg;
								var code = res.code;
								if(code == "1"){ 
									
								  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
									  $("#OffpricingPackageModal").modal("hide");
									  $(".bootbox").modal("hide");
										pricingPackage.pageList();
								  });
								}else{
									
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！",function(){
										$("#OffpricingPackageModal").modal("hide");
										$(".bootbox").modal("hide");
										
										pricingPackage.pageList();	
									});
								}
							},
							beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
								if (form.find("textarea[name='availableReason']").val()=="") {
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入原因！");
									return false;
								}
							}
							});
						},
			//方法加载
	        operateColumEvent: function(){
	        	$(".pricingPackage-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("套餐产品详情",pricingPackage.appPath+ "/pricingPackage/toPricingPackageView.do?packageNo="+$(this).data("id"));
					});
				});
				$(".pricingPackage-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("套餐产品编辑",pricingPackage.appPath+ "/pricingPackage/toPricingPackageEditView.do?packageNo="+$(this).data("id"));			
					});
				});
				$(".pricingPackage-operate-op").each(function(id,obj){
					$(this).on("click",function(){
						addTab("套餐产品审核",pricingPackage.appPath+ "/pricingPackage/toPricingPackageCencorView.do?packageNo="+$(this).data("id"));			
					});
				});
				
				//启用弹出层
				$(".pricingPackage-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var packageNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingPackage.appPath+"/pricingPackage/toPricingPackage.do",
				             data: {packageNo:packageNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data!=null){
				            	    $("#onpricingPackageModal").modal("show");
				            	    var form = $("form[name=onpricingPackageForm]");
				            	    form.find("input[name=packageNo]").val(data.packageNo);
									$("#onMemoPF").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将套餐"+"“"+data.packageName+"”"+"上架吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "不可上架！");
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".pricingPackage-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var packageNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingPackage.appPath+"/pricingPackage/toPricingPackage.do",
				             data: {packageNo:packageNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data!=null){
				            	    $("#OffpricingPackageModal").modal("show");
				            	    var form = $("form[name=offnForm]");
				            	    document.getElementById("packageNo2").value = data.packageNo;
//				            	    form.find("input[name=packageNo]").val(data.packageNo);
									$("#offMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将套餐"+"“"+data.packageName+"”"+"下架吗？下架后的套餐将不能被购买。");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "不可下架！");
				                 }
				             }
						});
					});
				});
				//删除
				$(".pricingPackage-operate-delete").each(function(id,obj){
					$(this).on("click",function(){
						var packageNo=$(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.ajax({
									 type: "post",
						             url: pricingPackage.appPath+"/pricingPackage/deletePricingPackage.do",
						             data: {packageNo:packageNo},
						             dataType: "json",
						             success: function(res){
						            	 var code = res.code;
						            	 var msg = res.msg;
						            	 if(code=="1"){
						            		 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！");
						            		 $("#pricingPackageList").DataTable().ajax.reload(function(){});
						                 }else{
						                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！");
						                 }
						             }
								});
							}
						})
					});
				})
	        },
			pageList:function () {	
				var pricingPackageTpl = $("#pricingPackageTpl").html();
				// 预编译模板
				var template = Handlebars.compile(pricingPackageTpl);
				$('#pricingPackageList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": pricingPackage.appPath+'/pricingPackage/pageListPricingPackage.do',
						"data": function ( d ) {
							var form = $("form[name=pricingPackageSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"packageName":form.find("input[name=packageName]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
	        					"cencorStatus":form.find("select[name=cencorStatus]").val(),
	        					"packageType":form.find("select[name=packageType]").val()
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
//						{ "title":"编号","data": "packageNo" },
						{ "title":"套餐名称","data": "packageName" },
						{ "title":"销售价(元)","data": "price" },
//						{ "title":"时长(分钟)","data": "minutes" },
						{ "title":"套餐面值金额(元)","data": "packAmount" },
//						{ "title":"有效天数（天）","data": "availableDays" },
						{ "title":"上下架状态","data": "isAvailable" },
						{ "title":"上下架时间","data": "availabelUpdateTime" },
						{ "title":"套餐类型","data": "packageType" },
						{ "title":"审核状态","data": "cencorStatus" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#pricingPackagetool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#pricingPackageTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#pricingPackageTools").html("");
					   $("#pricingPackageTools").removeClass("col-xs-6");
					   $("#pricingPackageTools").append('<button type="button" class="btn-new-type pricingPackageTools-operate-add">新增</button>');
		       			$(".pricingPackageTools-operate-add").on("click",function(){
		       				addTab("套餐产品新增", pricingPackage.appPath+ "/pricingPackage/toAddPricingPackage.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						pricingPackage.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						           if(c.isAvailable==0){
						        	   return "下架";
						           }else if(c.isAvailable==1){
						        	   return "上架"; 
						           }
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [5],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
							    }else{
							    	return "";
							    }
						    }
						},
						//套餐类型(1.赠送类套餐，2.销售套餐)
						{
						    "targets": [6],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						           if(c.packageType==1){
						        	   return "赠送类套餐";
						           }else{
						        	   return "销售套餐"; 
						           }
						    	}else{
						    		return "";
						    	}
						    }
						},
						//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
						{
						    "targets": [7],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(c.cencorStatus=="0"){
							        	return "未审核";
							        }else if(c.cencorStatus=="1"){
							        	return "已审核";
							        }else if(c.cencorStatus=="2"){
							        	return "不通过";
							        }
						        }else{
						        	return "";
						        }
						    }
						},
						{
							"targets": [8],
							"render": function (a, b, c, d) {
								var find="";
								var edit="";
								var onList="";
								var offList="";
								var op="";
								var edit="";
								var isDelete="";
								find='<span class="glyphicon pricingPackage-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.packageNo+'" data-toggle="tooltip" data-placement="top" title="查看">查看</span>';
								if(c.isAvailable==0 && c.cencorStatu !=1){
									edit='<span class="glyphicon pricingPackage-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.packageNo+'" data-toggle="tooltip" data-placement="top" title="调整">编辑</span>';
								}
								
	        					if(c.isAvailable==0 && c.cencorStatus==1){
	        						onList='<span class="glyphicon pricingPackage-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.packageNo+'" data-toggle="tooltip" data-placement="top" title="上架">上架</span>';
	        					}
								if(c.isAvailable==1){
									offList='<span class="glyphicon pricingPackage-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.packageNo+'" data-toggle="tooltip" data-placement="top" title="下架">下架</span>';
								}
	        					if(c.cencorStatus==0){
	        						op='<span class="glyphicon pricingPackage-operate-op" style="text-decoration: underline; cursor: pointer;"data-id="'+c.packageNo+'" data-toggle="tooltip" data-placement="top" title="审核">审核</span>';
	        					}
	        					if(c.isAvailable==0){
	        						isDelete='<span class="glyphicon pricingPackage-operate-delete" style="text-decoration: underline; cursor: pointer;" data-id="'+c.packageNo+'" data-toggle="tooltip" data-placement="top" title="删除">删除</span>';
	        					}
	        					return find+edit+onList+offList+op+isDelete;
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
	return pricingPackage;
});


