define([],function() {	
			var applyCarModel={
			appPath:getPath("app"),	
			init: function () {	
		        //数据列表
				applyCarModel.pageList();
				//查询
				$("#applyCarModelSearchafter").click(function(){
					applyCarModel.pageList();
		        });
				
			},
			operateColumEvent: function(){
				$(".applyCarModel-operate-agree").on("click",function(){
					var applyCarModelId = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定同意操作吗？", function(result) {
						if(result){
							$.post("applyCarModel/agreeApplyCarModel.do",{applyCarModelId:applyCarModelId},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#applyCarModelList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
								
							});
						}						
       				});
					
				});
				$(".applyCarModel-operate-isDelete").on("click",function(){
					var applyCarModelId = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							$.post("applyCarModel/deleteApplyCarModel.do",{applyCarModelId:applyCarModelId},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#applyCarModelList").DataTable().ajax.reload(function(){
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
				var applyCarModelTpl = $("#applyCarModelTpl").html();
				// 预编译模板
				var template = Handlebars.compile(applyCarModelTpl);
				$('#applyCarModelList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": applyCarModel.appPath+'/applyCarModel/pageListApplyCarModel.do',
						"data": function ( d ) {
							var form = $("form[name=applyCarModelSearchForm]");
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
						{ "title":"品牌名称","data": "carBrandName" },
						{ "title":"车系名称","data": "carSeriesName" },
						{ "title":"车辆年代","data": "carPeriodName" },
						{ "title":"变速箱","data": "gearBox" },
						{ "title":"排量","data": "displacement" },
						{ "title":"适用类型","data": "carType" },
						{ "title":"申请结果","data": "isAgree" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#applyCarModelTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						applyCarModel.operateColumEvent();
	        	    },
	        	    "order": [[ 0, "desc" ]],
					"columnDefs": [
						{
						    "targets": [3],
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
						    "targets": [5],//车辆类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。
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
						    "targets": [6],
						    "render": function(a, b, c, d) {
						        if(a==1){
						        	return "同意";
						        }else {
						        	return "未处理"
						        }
						    }
						},
						{
							"targets": [7],
							"render": function (a, b, c, d) {
								var agree='<span class="glyphicon applyCarModel-operate-agree" data-id="'+c.applyCarModelId+'" data-toggle="tooltip" data-placement="top">同意</span>';
								var isDelete='<span class="glyphicon applyCarModel-operate-isDelete" data-id="'+c.applyCarModelId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					if(c.isAgree!=1){
	        						if (c.isDelete==0) {
	        							return agree+isDelete;
									}else{
										return agree;
									}
	        					}else {
	        						return "";
	        					}
							}
						}
					]
				});
			}
	    };
	return applyCarModel;
});


