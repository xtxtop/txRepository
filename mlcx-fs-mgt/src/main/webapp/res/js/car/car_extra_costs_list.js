define([],function() {	
	var carExtraCosts={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				carExtraCosts.pageList();
				//查询
				$("#carExtraCostsSearchafter").click(function(){
					carExtraCosts.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
				
					$(".carExtraCosts-operate-edit").on("click",function(){
						addTab("车辆附加费编辑",carExtraCosts.appPath+ "/carExtraCosts/toCarExtraCostsEdit.do?extraCostsNo="+$(this).data("id"));			
					});
					$(".carExtraCosts-operate-del").on("click",function(){
						var extraCostsNo = $(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
							
								$.post("carExtraCosts/delCarExtraCosts.do",{extraCostsNo:extraCostsNo},function(res){	
									if(res.code==1){

										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
											  $(".bootbox").modal("hide");
											  $("#carExtraCostsList").DataTable().ajax.reload(function(){
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
				var carExtraCostsTpl = $("#carExtraCostsTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carExtraCostsTpl);
				$('#carExtraCostsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carExtraCosts.appPath+'/carExtraCosts/pageListCarExtraCosts.do',
						"data": function ( d ) {
							var form = $("form[name=carExtraCostsSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carExtraCostsNameck":$.trim(form.find("input[name=carExtraCostsNameck]").val()),
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
						{ "title":"品牌","data": "carBrandName" },
						{ "title":"型号","data": "carModelName" },
						{ "title":"城市","data": "cityName" },
						{ "title":"保险费","data": "insurance" },
						{ "title":"预授权（押金）","data": "preLicensing" },
						{ "title":"超时服务费","data": "overtimeCharge" },
						{ "title":"服务费","data": "coverCharge" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carExtraCoststool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carExtraCostsTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carExtraCostsTools").html("");
		       			$("#carExtraCostsTools").append('<button type="button" class="btn btn-default btn-sm carExtraCostsTools-operate-add btnDefault">新增</button>');
		       			
		       			$(".carExtraCostsTools-operate-add").on("click",function(){
		       				addTab("车辆附加费新增", carExtraCosts.appPath+ "/carExtraCosts/toCarExtraCostsAdd.do");
		       			});	 
		       			
		       			
		       			
					},
					"drawCallback": function( settings ) {
						carExtraCosts.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						
						{
							"targets": [7],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon  carExtraCosts-operate-edit" data-id="'+c.extraCostsNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  carExtraCosts-operate-del" data-id="'+c.extraCostsNo+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+del;
	        					
							}
						}
					]
				});
			}
	    };
	return carExtraCosts;
});


