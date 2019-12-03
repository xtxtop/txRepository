define([],function() {	
			var carModel={
			appPath:getPath("app"),	
			init: function () {	
		        //数据列表
				carModel.pageList();
				//查询
				$("#carModelSearchafter").click(function(){
					carModel.pageList();
		        });
				//上架状态提交
				$("#carModelOnLineFormBtn").click(function(){
					carModel.carModelOnLine();
		        });
				//上架取消
				$("#carModelOnLineCancelBtn").click(function(){
					$("#carModelOnLineModal").modal("hide");
		        });
				//下架状态提交
				$("#carModelOffLineBtn").click(function(){
					carModel.carModelOffLine();
		        });
				//下架取消
				$("#carModelOffLineCancel").click(function(){
					$("#carModelOffLineModal").modal("hide");
		        });
				
			},
		 	//上架操作
			carModelOnLine: function () {
		    	var form = $("form[name=carModelOnLineForm]");
				form.ajaxSubmit({
					url:carModel.appPath+"/carModel/onOffLineCarModel.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
								$("#carModelOnLineMemo").text("");
								$(".bootbox").modal("hide");
								$("#carModelOnLineModal").modal("hide");
								$("#carModelList").DataTable().ajax.reload(function(){
								}); 
							});
						}else{
							$("#carModelOnLineModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg,function(){
								$("#carModelOnLineMemo").text("");
							});
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						
					}
				});
			},
			//下线操作
			carModelOffLine: function () {
	        	var form = $("form[name=carModelOffLineForm]");
				form.ajaxSubmit({
					url:carModel.appPath+"/carModel/onOffLineCarModel.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
						  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
							  $("#carModelOffLineMemo").text("");
							  $("#carModelOffLineModal").modal("hide");
							  $(".bootbox").modal("hide");
							  $("#carModelList").DataTable().ajax.reload(function(){}); 
						  });
						  
						}else{
							$("#carModelOffLineModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
							$("#carModelOffLineMemo").text("");
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						
					}
				});
			},		  
			//方法加载
	        operateColumEvent: function(){
	        	
				$(".carModel-operate-edit").on("click",function(){
					addTab("车型编辑",carModel.appPath+ "/carModel/toCarModelEdit.do?carModelId="+$(this).data("id"));			
				});
	        	$(".carModel-operate-find").on("click",function(){
					addTab("车型详情",carModel.appPath+ "/carModel/toCarModelView.do?carModelId="+$(this).data("id"));			
				});
				$(".carModel-operate-del").on("click",function(){
					var carModelId = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							$.post("carModel/delCarModel.do",{carModelId:carModelId},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#carModelList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
								
							});
						}						
       				});
					
				});
				//上架弹出层
				$(".carModel-operate-onLine").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carModelOnLineForm]");
						var carModelId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: carModel.appPath+"/carModel/getCarModel.do",
				             data: {carModelId:carModelId},
				             dataType: "json",
				             success: function(result){
				            	 var code = result.code;
				            	 var data = result.data;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#carModelOnLineModal").modal("show");
				            	    form.find("input[name='carModelId']").val(data.carModelId);
									$("#carModelOnLineMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将车型"+"“"+data.carModelName+"”"+"上架吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
				//下架弹出层
				$(".carModel-operate-offLine").each(function(id,obj){
					$(this).on("click",function(){
						var form = $("form[name=carModelOffLineForm]");
						var carModelId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: carModel.appPath+"/carModel/getCarModel.do",
				             data: {carModelId:carModelId},
				             dataType: "json",
				             success: function(result){
				            	 var code = result.code;
				            	 var data = result.data;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#carModelOffLineModal").modal("show");
				            	    form.find("input[name='carModelId']").val(data.carModelId);
									$("#carModelOffLineMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将车型"+"“"+data.carModelName+"”"+"下架吗？下架后的车型将无法使用！");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
	        },
			pageList:function () {	
				var carModelTpl = $("#carModelTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carModelTpl);
				$('#carModelList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carModel.appPath+'/carModel/pageListCarModel.do',
						"data": function ( d ) {
							var form = $("form[name=carModelSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"queryCarModelName":$.trim(form.find("input[name=carModelName]").val()),
								"carBrandId":form.find("select[name=carBrandId]").val(),
								"carType":form.find("select[name=carType]").val()
								
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
						{ "title":"车型名称","data": "carModelName" },
						{ "title":"品牌名称","data": "carBrandName" },
						{ "title":"车系","data": "carSeriesName" },
						{ "title":"类别","data": "carType" },
						{ "title":"座位数","data": "seatNumber" },
						{ "title":"吨位","data": "tons" },
						{ "title":"排量","data": "displacement" },
						{ "title":"变速箱","data": "gearBox" },
						{ "title":"状态","data": "onOffLineStatus" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carModeltool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carModelTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carModelTools").html("");
		       			$("#carModelTools").append('<button type="button" class="btn-new-type carModelTools-operate-add">新增</button>');
		       			
		       			$(".carModelTools-operate-add").on("click",function(){
		       				addTab("车型新增", carModel.appPath+ "/carModel/toCarModelAdd.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						carModel.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a, b, c, d) {
						        return c.carSeriesName+"-"+c.carPeriodName;
						    }
						},
						{
						    "targets": [3],//车辆类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。
						    "render": function(a, b, c, d) {
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
						    "targets": [4],//车辆类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。
						    "render": function(a, b, c, d) {
						        if(a==1){
						        	return "2座";
						        }else if(a==2){
						        	return "4座";
						        }else if(a==3){
						        	return "5座";
						        }else if(a==4){
						        	return "7座";
						        }else{
						        	return "";
						        }
						    	
						    }
						},          
						{
						    "targets": [7],
						    "render": function(a, b, c, d) {
						        if(a==1){
						        	return "手动";
						        }else if(a==2){
						        	return "自动";
						        }else{
						        	return "手自一体"
						        }
						    }
						},
						{
						    "targets": [8],
						    "render": function(a, b, c, d) {
						        if(a==1){
						        	return "上架";
						        }else{
						        	return "下架";
						        }
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon carModel-operate-find" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon carModel-operate-edit" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var online='<span class="glyphicon carModel-operate-onLine" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">上架</span>';
								var offline='<span class="glyphicon carModel-operate-offLine" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">下架</span>';
								var del='<span class="glyphicon carModel-operate-del" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					if(c.onOffLineStatus==0){
	        						return edit+del+online;
	        					}else{
	        						return find+offline;
	        					}
	        					
							}
						}
					]
				});
			}
	    };
	return carModel;
});


