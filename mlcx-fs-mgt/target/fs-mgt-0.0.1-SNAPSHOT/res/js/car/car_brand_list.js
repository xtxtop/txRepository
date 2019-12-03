define([],function() {	
	var carBrand={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				carBrand.pageList();
				//查询
				$("#carBrandSearchafter").click(function(){
					carBrand.pageList();
	            });
				
			},
					  
			//方法加载
	        operateColumEvent: function(){
				
					$(".carBrand-operate-edit").on("click",function(){
						addTab("品牌编辑",carBrand.appPath+ "/carBrand/toCarBrandEdit.do?carBrandId="+$(this).data("id"));			
					});
					$(".carBrand-operate-del").on("click",function(){
						var carBrandId = $(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
							
								$.post("carBrand/delCarBrand.do",{carBrandId:carBrandId},function(res){	
									if(res.code==1){

										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
											  $(".bootbox").modal("hide");
											  $("#carBrandList").DataTable().ajax.reload(function(){
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
				var carBrandTpl = $("#carBrandTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carBrandTpl);
				$('#carBrandList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carBrand.appPath+'/carBrand/pageListCarBrand.do',
						"data": function ( d ) {
							var form = $("form[name=carBrandSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carBrandName":$.trim(form.find("input[name=carBrandName]").val())
								
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
						{ "title":"logo图片","data": "logoPic","defaultContent":"" },
					//	{ "title":"品牌介绍","data": "brandInfo" ,"defaultContent":""},
						{ "title":"更新时间","data": "createTime" ,"defaultContent":""},
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carBrandtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carBrandTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carBrandTools").html("");
		       			//$("#carBrandTools").append('<button type="button" class="btn-new-type btn-new-type-change carBrandTools-operate-add">新增</button>');
					   $("#carBrandTools").append('<button type="button" class="btn-new-type carBrandTools-operate-add">新增</button>');
		       			$(".carBrandTools-operate-add").on("click",function(){
		       				addTab("新增品牌", carBrand.appPath+ "/carBrand/toCarBrandAdd.do");
		       			});	 
		       			
		       			/**
		       			 * 导出
		       			 */
		       			
					},
					"drawCallback": function( settings ) {
						carBrand.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets": [1],
							"render": function (a, b, c, d) {
								return "<div class='car-brand-img' style='background-image:url("+carBrand.imgPath+'/'+a+")'></div>";
							}
						},
						{
							"targets": [2],
							"render": function (a, b, c, d) {
								var now = moment(a);
								return now.format('YYYY-MM-DD HH:mm:ss');
	        					
							}
						},
						{
							"targets": [3],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon  carBrand-operate-edit" data-id="'+c.carBrandId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  carBrand-operate-del" data-id="'+c.carBrandId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+del;
	        					
							}
						}
					]
				});
			}
	    };
	return carBrand;
});


