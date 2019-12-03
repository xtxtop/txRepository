define([],function() {	
	var returnArea={
			appPath:getPath("app"),	
			init: function () {
				//关闭车辆详情页
				$("#closereturnArea").click(function(){
					closeTab("场站详情");
	            });
				//启用状态提交
				$("#returnAreaOnFormBtn").click(function(){
					returnArea.onFormSubArea();
	            });
				//启用取消
				$("#returnAreaOnCancelBtn").click(function(){
					$("#onreturnAreaModal").modal("hide");
	            });
				//停用状态提交
				$("#returnAreaOffBtn").click(function(){
					returnArea.offFormSub();
	            });
				//停用取消
				$("#returnAreaOffCancel").click(function(){
					$("#offreturnAreaModal").modal("hide");
	            });
				
	            //数据列表
				returnArea.pageList();
				//查询
				$("#returnAreaSearchafter").click(function(){
					returnArea.pageList();
	            });
				$("#onViewModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onViewForm]");
	            	$("#viewMemo").text("");
	            	form.find("input[name=returnAreaNo]").val("");
	            });
				$("#noViewModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=noViewForm]");
	            	$("#noViewMemo").text("");
	            	form.find("input[name=returnAreaNo]").val("");
	            });
				$("#onreturnAreaModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onreturnAreaForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#onreturnAreaMemo").text("");
	            	form.find("input[name=returnAreaNo]").val("");
	            });
				$("#offreturnAreaModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offreturnAreaForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#offreturnAreaMemo").text("");
	            	form.find("input[name=returnAreaNo]").val("");
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".returnArea-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("还车区域详情",returnArea.appPath+ "/returnArea/toAreaView.do?returnAreaId="+$(this).data("id"));
					});
				});
	        	$(".returnArea-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("还车区域编辑",returnArea.appPath+ "/returnArea/toUpdateView.do?returnAreaId="+$(this).data("id"));
					});
				});
//	        	$(".returnArea-operate-op").each(function(){
//					$(this).on("click",function(){
//						addTab("场站参数配置",returnArea.appPath+ "/returnArea/toUpdateParemView.do?returnAreaNo="+$(this).data("id"));
//					});
//				});
	        	
	        	//启用弹出层
				$(".returnArea-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var returnAreaId=$(this).data("id");
						$("#returnAreaId1").val(returnAreaId);
				          $("#onreturnAreaModal").modal("show");
						
									
					});
				});
				//停用弹出层
				$(".returnArea-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var returnAreaId=$(this).data("id");
						$("#returnAreaId2").val(returnAreaId);
				         $("#offreturnAreaModal").modal("show");
					});
				});
	        },
	        
	        //启用操作
	        onFormSubArea: function () {
	        	var form = $("form[name=onreturnAreaForm]");
				form.ajaxSubmit({
	    			url:returnArea.appPath+"/returnArea/changeAreaState.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							$("#onreturnAreaModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#onreturnAreaModal").modal('hide')
							});
							$("#returnAreaList").DataTable().ajax.reload(function(){
    						}); 
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败，"+msg+"！");
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						if (form.find("textarea[name='memo']").val()=="") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入理由！");
							return false;
						}
					}
					});
				},
				   //停用操作
				offFormSub: function () {
		        	var form = $("form[name=offreturnAreaForm]");
					form.ajaxSubmit({
						url:returnArea.appPath+"/returnArea/changeAreaState.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
									$("#offreturnAreaModal").modal('hide')
								});
							  $("#returnAreaList").DataTable().ajax.reload(function(){
	    						}); 
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='memo']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入理由！");
								return false;
							}
						}
						});
					},
			pageList:function () {	
				var returnAreaTpl = $("#returnAreaTpl").html();
				// 预编译模板
				var template = Handlebars.compile(returnAreaTpl);
				$('#returnAreaList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": returnArea.appPath+'/returnArea/pageListReturnArea.do',
						"data": function ( d ) {
							var form = $("form[name=returnAreaSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
//								"returnAreaNo":$.trim(form.find("input[name=returnAreaNo]").val()),
								"areaName":$.trim(form.find("input[name=areaName]").val()),
//	        					"supportedServices":form.find("select[name=supportedServices]").val(),
        					    "isAvailable":form.find("select[name=isAvailable]").val(),
//	        					"returnAreaType":form.find("select[name=returnAreaType]").val()
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
						{ "title":"名称","data": "areaName" },
						{ "title":"状态","data": "isAvailable" },	
						{ "title":"创建时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#returnAreatool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#returnAreaTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					    $("#returnAreaTools").html("");
					   $("#returnAreaTools").removeClass("col-xs-6");
					   $("#returnAreaTools").append('<button type="button" class="btn-new-type returnAreaTools-operate-addreturnArea">新增</button>');
		       			$(".returnAreaTools-operate-addreturnArea").on("click",function(){
		       				addTab("还车区域新增 ", returnArea.appPath+ "/returnArea/toAddreturnArea.do");
		       			});	     			
	        			
					},
					"drawCallback": function( settings ) {
						returnArea.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
		               {
						    "targets": [0],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	return a;
						        }else{
						        	return "";
						        }
						    }
						},
						 {
						    "targets": [1],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	return a;
						        }else{
						        	return "";
						        }
						    }
						},
						
						
						//场站状态（0、停用，1、启用，默认0）
						{
						    "targets": [2],
						    "render": function(data, type, row, meta) {
						    		if(data==0){
							        	return "停用";
							        }else{
							        	return "启用";
							        }	
						    }
						},
						{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    	var now = moment(a); 
	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
							"targets": [4],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  returnArea-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.returnAreaId+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon  returnArea-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.returnAreaId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var onList='<span class="glyphicon  returnArea-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.returnAreaId+'" data-toggle="tooltip" data-placement="top">启用</span>';
	        					var offList='<span class="glyphicon  returnArea-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.returnAreaId+'" data-toggle="tooltip" data-placement="top">停用</span>';
//	        					var view='<span class="glyphicon  btn btn-default  returnArea-operate-view" data-id="'+c.returnAreaNo+'" data-toggle="tooltip" data-placement="top">可见</span>';	
//	        					var noView='<span class="glyphicon  btn btn-default  returnArea-operate-noView" data-id="'+c.returnAreaNo+'" data-toggle="tooltip" data-placement="top">隐藏</span>';
//	        					var op='<span class="glyphicon  btn btn-default  returnArea-operate-op" data-id="'+c.returnAreaNo+'" data-toggle="tooltip" data-placement="top">参数</span>';
	        					if(c.isAvailable==0){
	        					return find+edit+onList;
	        					}else{
	        						if(c.isView==0){
	        							return find+edit+offList;
	        						}else{
	        							return find+edit+offList;
	        						}
	        						
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
	return returnArea;
});


