define([],function() {	
	var park={
			appPath:getPath("app"),	
			init: function () {
				//关闭车辆详情页
				$("#closepark").click(function(){
					closeTab("场站详情");
	            });
				//启用状态提交
				$("#parkOnFormBtn").click(function(){
					park.onFormSub();
	            });
				//启用取消
				$("#parkOnCancelBtn").click(function(){
					$("#onParkModal").modal("hide");
	            });
				//停用状态提交
				$("#parkOffBtn").click(function(){
					park.offFormSub();
	            });
				//停用取消
				$("#parkOffCancel").click(function(){
					$("#offParkModal").modal("hide");
	            });
				//可用提交
				$("#onViewBtn").click(function(){
					park.onViewFormSub();
	            });
				//可用取消
				$("#onViewCancelBtn").click(function(){
					$("#onViewModal").modal("hide");
	            });
				//隐藏提交
				$("#noViewBtn").click(function(){
					park.noViewFormSub();
	            });
				//隐藏取消
				$("#noViewCancelBtn").click(function(){
					$("#noViewModal").modal("hide");
	            });
	            //数据列表
				park.pageList();
				park.handleClickMore();
				//查询
				$("#parkSearchafter").click(function(){
					park.pageList();
	            });
				$("#onViewModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onViewForm]");
	            	$("#viewMemo").text("");
	            	form.find("input[name=parkNo]").val("");
	            });
				$("#noViewModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=noViewForm]");
	            	$("#noViewMemo").text("");
	            	form.find("input[name=parkNo]").val("");
	            });
				$("#onParkModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onParkForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#onParkMemo").text("");
	            	form.find("input[name=parkNo]").val("");
	            });
				$("#offParkModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offParkForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#offParkMemo").text("");
	            	form.find("input[name=parkNo]").val("");
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".park-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("场站详情",park.appPath+ "/park/toParkView.do?parkNo="+$(this).data("id"));
					});
				});
	        	$(".park-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("场站编辑",park.appPath+ "/park/toUpdateView.do?parkNo="+$(this).data("id"));
					});
				});
	        	$(".park-operate-op").each(function(){
					$(this).on("click",function(){
						addTab("场站参数配置",park.appPath+ "/park/toUpdateParemView.do?parkNo="+$(this).data("id"));
					});
				});
	        	//可见弹出层
	        	$(".park-operate-view").each(function(){
					$(this).on("click",function(){
						var parkNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: park.appPath+"/park/toPark.do",
				             data: {parkNo:parkNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            		 $("#onViewModal").modal("show");
									 $("#parkOnView").val(data.parkNo);
									 $("#viewMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将编号为"+"“"+data.parkNo+"”"+"的场站可见吗？");
				                 }
				             }
						});
					});
				});
	        	//隐藏弹出层
	        	$(".park-operate-noView").each(function(){
					$(this).on("click",function(){
						var parkNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: park.appPath+"/park/toPark.do",
				             data: {parkNo:parkNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            		 $("#noViewModal").modal("show");
									 $("#parkNoView").val(data.parkNo);
									 $("#noViewMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将编号为"+"“"+data.parkNo+"”"+"的场站隐藏吗？");
				                 }
				             }
						});
					});
				});
	        	//启用弹出层
				$(".park-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var parkNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: park.appPath+"/park/toPark.do",
				             data: {parkNo:parkNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            	    $("#onParkModal").modal("show");
									$("#parkNo1").val(data.parkNo);
									$("#onParkMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将编号为"+"“"+data.parkNo+"”"+" 启用吗？");
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".park-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var parkNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: park.appPath+"/park/toPark.do",
				             data: {parkNo:parkNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            	    $("#offParkModal").modal("show");
									$("#parkNo2").val(data.parkNo);
									$("#offParkMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将编号为"+"“"+data.parkNo+"”"+" 停用吗？");
				                  }
				             }
						});
					});
				});
	        },
	        //可见
	        onViewFormSub:function () {
	        	var form = $("form[name=onViewForm]");
				form.ajaxSubmit({
	    			url:park.appPath+"/park/changeParkState.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#onViewModal").modal('hide')
							});
							$("#parkList").DataTable().ajax.reload(function(){
    						}); 
						}
					},
					});
				},
			//隐藏
			noViewFormSub:function () {
	        	var form = $("form[name=noViewForm]");
				form.ajaxSubmit({
	    			url:park.appPath+"/park/changeParkState.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#noViewModal").modal('hide')
							});
							$("#parkList").DataTable().ajax.reload(function(){
    						}); 
						}
					},
					});
				},
	        //启用操作
	        onFormSub: function () {
	        	var form = $("form[name=onParkForm]");
				form.ajaxSubmit({
	    			url:park.appPath+"/park/changeParkState.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							$("#onParkModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#onParkModal").modal('hide')
							});
							$("#parkList").DataTable().ajax.reload(function(){
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
				   //停用操作
				offFormSub: function () {
		        	var form = $("form[name=offParkForm]");
					form.ajaxSubmit({
						url:park.appPath+"/park/changeParkState.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
									$("#offParkModal").modal('hide')
								});
							  $("#parkList").DataTable().ajax.reload(function(){
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
				var parkTpl = $("#parkTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkTpl);
				$('#parkList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": park.appPath+'/park/pageListPark.do',
						"data": function ( d ) {
							var form = $("form[name=parkSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkNo":$.trim(form.find("input[name=parkNo]").val()),
								"parkName":$.trim(form.find("input[name=parkName]").val()),
	        					"supportedServices":form.find("select[name=supportedServices]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
	        					"parkType":form.find("select[name=parkType]").val(),
	        					"isLotParkingSpace":form.find("select[name=isLotParkingSpace]").val()
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
			            { "title":"编号","data": "parkNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"名称","data": "parkName" },
						{ "title":"详细地址","data": "addrStreet" },
						{ "title":"车位数","data": "parkingSpaceNumber" },	
						{ "title":"车辆数","data": "carNumber" },
						{ "title":"电桩数","data": "chargerNumber" },
						{ "title":"服务","data": "supportedServices" },
						{ "title":"所属","data": "ownerType" },
						{ "title":"类型","data": "parkType" },	
						{ "title":"状态","data": "isAvailable" },	
						{ "title":"是否可见","data": "isView" },	
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#parkTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					    $("#parkTools").html("");
					   $("#parkTools").removeClass("col-xs-6");
					   $("#parkTools").append('<button type="button" class="btn-new-type parkTools-operate-addPark">新增</button>');
		       			$(".parkTools-operate-addPark").on("click",function(){
		       				addTab("场站新增 ", park.appPath+ "/park/toAddPark.do");
		       			});	     			
	        			
					},
					"drawCallback": function( settings ) {
						park.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
		               {
						    "targets": [4],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	return a;
						        }else{
						        	return "";
						        }
						    }
						},
						 {
						    "targets": [5],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	return a;
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [8],
						    "render": function(data, type, row, meta) {
						    	if(data){
							        if(data==1){
							        	return "自有";
							        }else if (data==2){
							        	return "租用";
							        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						//场站类型（1、管理类，2、使用类，默认1）
						{
						    "targets": [9],
						    "render": function(data, type, row, meta) {
						    	if(data){
							        if(data==1){
							        	return "管理类";
							        }else{
							        	return "使用类";
							        }
						    	}else{
						    		return "";
						    	}
						    }
						},
						//场站状态（0、停用，1、启用，默认0）
						{
						    "targets": [10],
						    "render": function(data, type, row, meta) {
						    		if(data==0){
							        	return "停用";
							        }else{
							        	return "启用";
							        }	
						    }
						},
						//是否可见（0、隐藏，1、可见，默认0）
						{
						    "targets": [11],
						    "render": function(data, type, row, meta) {
						    		if(data==0){
							        	return "隐藏";
							        }else{
							        	return "可见";
							        }	
						    }
						},
						{
							"targets": [12],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon park-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon park-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var onList='<span class="glyphicon park-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkNo+'" data-toggle="tooltip" data-placement="top">启用</span>';
	        					var offList='<span class="glyphicon park-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkNo+'" data-toggle="tooltip" data-placement="top">停用</span>';
	        					var view='<span class="glyphicon park-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkNo+'" data-toggle="tooltip" data-placement="top">可见</span>';
	        					var noView='<span class="glyphicon park-operate-noView" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkNo+'" data-toggle="tooltip" data-placement="top">隐藏</span>';
	        					var op='<span class="glyphicon park-operate-op" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkNo+'" data-toggle="tooltip" data-placement="top">参数</span>';
	        					if(c.isAvailable==0){
	        					return find+edit+onList;
	        					}else{
	        						if(c.isView==0){
	        							return find+edit+offList+view;
	        						}else{
	        							return find+edit+offList+noView;
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
			},
			//点击更多
			handleClickMore:function(){
				$("#moreParkList").click(function(){
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
	return park;
});


