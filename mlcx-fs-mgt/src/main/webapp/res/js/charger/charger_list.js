define([],function() {	
	var charger={
			appPath:getPath("app"),	
			init: function () {
				//关闭充电桩详情页
				$("#closeCharger").click(function(){
					closeTab("充电桩详情");
	            });
				//启用状态提交
				$("#chargerOnFormBtn").click(function(){
					charger.onFormSub();
	            });
				//启用取消
				$("#chargerOnCancelBtn").click(function(){
					$("#onChargerModal").modal("hide");
	            });
				//停用状态提交
				$("#chargerOffBtn").click(function(){
					charger.offFormSub();
	            });
				//停用取消
				$("#chargerOffCancel").click(function(){
					$("#offChargerModal").modal("hide");
	            });
	            //数据列表
				charger.pageList();
				//查询
				$("#chargerSearchBt").click(function(){
					charger.pageList();
	            });
				$("#onChargerModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onChargerForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#onChargerMemo").text("");
	            	form.find("input[type=hidden]").val("");
	            });
				$("#offChargerModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offChargerForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	$("#offChargerMemo").text("");
	            	form.find("input[type=hidden]").val("");
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".charger-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("充电桩详情",charger.appPath+ "/charger/toChargerView.do?chargerNo="+$(this).data("id"));
					});
				});
	        	$(".charger-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("充电桩编辑",charger.appPath+ "/charger/toChargerEdit.do?chargerNo="+$(this).data("id"));
					});
				});
	        	//启用弹出层
				$(".charger-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var chargerNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: charger.appPath+"/charger/toCharger.do",
				             data: {chargerNo:chargerNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            	    $("#onChargerModal").modal("show");
									$("#chargerNo1").val(chargerNo);
									$("#onChargerMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将电桩"+"“"+chargerNo+"”"+"启用吗？");
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".charger-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var chargerNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: charger.appPath+"/charger/toCharger.do",
				             data: {chargerNo:chargerNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            	    $("#offChargerModal").modal("show");
									$("#chargerNo2").val(chargerNo);
									$("#offChargerMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将电桩"+"“"+chargerNo+"”"+" 停用吗？停用将不再有该电桩信息！");
				                      }
				             }
						});
					});
				});
	        },
	        //启用操作
	        onFormSub: function () {
	        	var form = $("form[name=onChargerForm]");
				form.ajaxSubmit({
	    			url:charger.appPath+"/charger/updateCharger.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							$("#onChargerModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！");
							$("#chargerList").DataTable().ajax.reload(function(){
    						}); 
						}
					}
					});
				},
				   //停用操作
				offFormSub: function () {
		        	var form = $("form[name=offChargerForm]");
					form.ajaxSubmit({
						url:charger.appPath+"/charger/updateCharger.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							 $("#offChargerModal").modal("hide");
							  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！");
							  $("#chargerList").DataTable().ajax.reload(function(){
	    						}); 
							}
						}
						});
					},
			pageList:function () {	
				var chargerTpl = $("#chargerTpl").html();
				// 预编译模板
				var template = Handlebars.compile(chargerTpl);
				$('#chargerList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": charger.appPath+'/charger/pageListCharger.do',
						"data": function ( d ) {
							var form = $("form[name=chargerSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"chargerType":form.find("select[name=chargerType]").val(),
								"parkName":$.trim(form.find("input[name=parkName]").val()),
								"parkNo":form.find("input[name=parkNo]").val(),
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
			            { "title":"编号","data": "chargerNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"场站名称","data": "parkName" },
						{ "title":"品牌","data": "chargerBrandName" },
						{ "title":"型号","data": "chargerModelName" },	
						{ "title":"功率","data": "chargerPower" },
						{ "title":"设备串号","data": "chargerSn" },
						{ "title":"电桩类型","data": "chargerType" },
						{ "title":"状态","data": "isAvailable" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#chargerTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#chargerTools").html("");
					   $("#chargerTools").removeClass("col-xs-6");
					   $("#chargerTools").append('<button type="button" class="btn-new-type chargerTools-operate-addCharger">新增</button>');
		       			$(".chargerTools-operate-addCharger").on("click",function(){
		       				addTab("新增充电桩 ", charger.appPath+ "/charger/toAddCharger.do");
		       			});	 	     			
	        			
					},
					"drawCallback": function( settings ) {
						charger.operateColumEvent();
	        	    },
	        	    "order": [[ 0, "desc" ]],
					"columnDefs": [
						{
						    "targets": [5],
						   "render": function(data, type, row, meta) {
						    	return data+"W";
						    }
						}, 
						 {
						    "targets": [7],
						   "render": function(data, type, row, meta) {
							   var aa = "";
						    	if(row.chargerType==2){
						    		aa="快充"
						    	}
						    	if(row.chargerType==1){
						    		aa="慢充"
						    	}
						    	return aa;
						    }
						}, 
						{
							"targets": [8],
							"render": function(data, type, row, meta) {
								var aa = "";
						    	if(row.isAvailable==0){
						    		aa="停用"
						    	}
						    	if(row.isAvailable==1){
						    		aa="启用"
						    	}
						    	return aa;
							}
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon charger-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargerNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon  charger-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargerNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var onList='<span class="glyphicon  charger-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargerNo+'" data-toggle="tooltip" data-placement="top">启用</span>';
	        					var offList='<span class="glyphicon  charger-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.chargerNo+'" data-toggle="tooltip" data-placement="top">停用</span>';
	        					if(c.isAvailable==0){
	        					   //return view;
	        					  return view+edit+onList;
	        					}else{
	        					   //return view;
	        					   return view+edit+offList;
	        					}
							}
						}
					]
				});
			}
	    };
	return charger;
});


