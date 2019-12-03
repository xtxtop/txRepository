define([],function() {	
	var carModelAll={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				carModelAll.pageList();
				//查询
				$("#carModelAllSearchafter").click(function(){
					carModelAll.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
				
					$(".carModelAll-operate-edit").on("click",function(){
						addTab("车系编辑",carModelAll.appPath+ "/carModelAll/toCarModelAllEdit.do?carSeriesId="+$(this).data("id"));			
					});
					$(".carModelAll-operate-del").on("click",function(){
						var carSeriesId = $(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.post("carModelAll/delCarModelAll.do",{carSeriesId:carSeriesId},function(res){	
									if(res.code==1){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
											  $(".bootbox").modal("hide");
											  $("#carModelAllList").DataTable().ajax.reload(function(){
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
				var carModelAllTpl = $("#carModelAllTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carModelAllTpl);
				$('#carModelAllList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carModelAll.appPath+'/carModelAll/pageListCarModelAll.do',
						"data": function ( d ) {
							var form = $("form[name=carModelAllSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carBrandId":form.find("select[name=carBrandId]").val(),
								"carSeriesNameQuery":$.trim(form.find("input[name=carSeriesName]").val())
								
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
						{ "title":"车型名称","data": "carSeriesName" ,"defaultContent":""},
						{ "title":"车型图标","data": "carSeriresPic","defaultContent":"" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carModelAlltool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carModelAllTpls.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carModelAllTpls").html("");
		       			$("#carModelAllTpls").append('<button type="button" class="btn btn-default btn-sm carModelAllTools-operate-add btnDefault">新增</button>');
		       			
		       			$(".carModelAllTools-operate-add").on("click",function(){
		       				addTab("新增车型", carModelAll.appPath+ "/carModelAll/toCarModelAllAdd.do");
		       			});	 
		       			
		       			/**
		       			 * 导出
		       			 */
		       			
					},
					"drawCallback": function( settings ) {
						carModelAll.operateColumEvent();
	        	    },
	        	    "order": [[ 2, "desc" ]],
					"columnDefs": [
						{
							"targets": [2],
							"render": function (a, b, c, d) {
								return "<img src="+carModelAll.imgPath+"/"+a+" width='280px' height='50px'>";
							}
						},
						{
							"targets": [3],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon  carModelAll-operate-edit" data-id="'+c.carSeriesId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  carModelAll-operate-del" data-id="'+c.carSeriesId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+del;
	        					
							}
						}
					]
				});
			}
	    };
	return carModelAll;
});


