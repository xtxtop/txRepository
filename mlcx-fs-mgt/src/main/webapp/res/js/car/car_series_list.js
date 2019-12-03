define([],function() {	
	var carSeries={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				carSeries.pageList();
				//查询
				$("#carSeriesSearchafter").click(function(){
					carSeries.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
				
					$(".carSeries-operate-edit").on("click",function(){
						addTab("车系编辑",carSeries.appPath+ "/carSeries/toCarSeriesEdit.do?carSeriesId="+$(this).data("id"));			
					});
					$(".carSeries-operate-del").on("click",function(){
						var carSeriesId = $(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.post("carSeries/delCarSeries.do",{carSeriesId:carSeriesId},function(res){	
									if(res.code==1){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
											  $(".bootbox").modal("hide");
											  $("#carSeriesList").DataTable().ajax.reload(function(){
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
				var carSeriesTpl = $("#carSeriesTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carSeriesTpl);
				$('#carSeriesList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carSeries.appPath+'/carSeries/pageListCarSeries.do',
						"data": function ( d ) {
							var form = $("form[name=carSeriesSearchForm]");
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
						{ "title":"品牌","data": "carBrandName" },
						{ "title":"车型系列","data": "carSeriesName" ,"defaultContent":""},
						{ "title":"默认图片","data": "carSeriresPic","defaultContent":"" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carSeriestool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carSeriesTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carSeriesTools").html("");
		       			$("#carSeriesTools").append('<button type="button" class="btn-new-type carSeriesTools-operate-add">新增</button>');
		       			
		       			$(".carSeriesTools-operate-add").on("click",function(){
		       				addTab("新增车系", carSeries.appPath+ "/carSeries/toCarSeriesAdd.do");
		       			});	 
		       			
		       			/**
		       			 * 导出
		       			 */
		       			
					},
					"drawCallback": function( settings ) {
						carSeries.operateColumEvent();
	        	    },
	        	    "order": [[ 2, "desc" ]],
					"columnDefs": [
						{
							"targets": [2],
							"render": function (a, b, c, d) {
//								return "<img src="+imgPath+"/"+a+" width='280px' height='50px'>";
								return "<div class='car-brand-img' style='background-image:url("+carSeries.imgPath+'/'+a+")'></div>";
							}
						},
						{
							"targets": [3],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon  carSeries-operate-edit" data-id="'+c.carSeriesId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  carSeries-operate-del" data-id="'+c.carSeriesId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+del;
	        					
							}
						}
					]
				});
			}
	    };
	return carSeries;
});


