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
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
	        	
					$(".carModel-operate-edit").on("click",function(){
						addTab("车型编辑",carModel.appPath+ "/carModel/toCarModelEdit.do?carModelId="+$(this).data("id"));			
					});
	        	$(".carModel-operate-get").on("click",function(){
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
								"carModelNameck":$.trim(form.find("input[name=carModelNameck]").val()),
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
						{ "title":"型号","data": "carModelName" },
						{ "title":"品牌","data": "carBrandName" },
						{ "title":"类别","data": "carType" },
						{ "title":"座位","data": "seatNumber" },
						{ "title":"档位","data": "stall" },
						{ "title":"箱型","data": "boxType" },
						{ "title":"排量","data": "displacement" },
						//{ "title":"保证金","data": "bond" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carModeltool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carModelTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carModelTools").html("");
		       			$("#carModelTools").append('<button type="button" class="btn btn-default btn-sm carModelTools-operate-add btnDefault">新增</button>');
		       			
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
						    "targets": [2],//车辆类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。
						    "render": function(data, type, row, meta) {
						        if(data==1){
						        	return "经济型";
						        }else if(data==2){
						        	return "商务型";
						        }else if(data==3){
						        	return "豪华型";
						        }else if(data==4){
						        	return "6至15座商务";
						        }else if(data==5){
						        	return "SUV";
						        }
						        
						        else{
						        	return "";
						        }
						    	
						    }
						},          
						{
						    "targets": [4],
						    "render": function(data, type, row, meta) {
						        if(data==1){
						        	return "手动";
						        }else{
						        	return "自动";
						        }
						    }
						},
						{
							"targets": [7],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon carModel-operate-edit" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var get='<span class="glyphicon carModel-operate-get" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var del='<span class="glyphicon carModel-operate-del" data-id="'+c.carModelId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+get+del;
	        					
							}
						}
					]
				});
			}
	    };
	return carModel;
});


