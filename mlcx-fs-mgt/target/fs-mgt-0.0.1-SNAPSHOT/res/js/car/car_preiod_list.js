define([],function() {	
	var carPreiod={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				carPreiod.pageList();
				//查询
				$("#carPreiodSearchafter").click(function(){
					carPreiod.pageList();
	            });
				$("#carPreiodSearchafterclose").click(function(){
					var form = $("form[name=carPreiodSearchForm]");
					form.find("select[name=carSeriesId]").html("");
					var option="<option value=''>全部</option>";
					  form.find("select[name=carSeriesId]").html(option);
	            });
			},
					  
			//方法加载
	        operateColumEvent: function(){
				
					$(".carPreiod-operate-edit").on("click",function(){
						addTab("车辆年代编辑",carPreiod.appPath+ "/carPreiod/toCarPreiodEdit.do?carPreiodId="+$(this).data("id"));			
					});
					$(".carPreiod-operate-del").on("click",function(){
						var carPreiodId = $(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.post("carPreiod/delCarPreiod.do",{carPeriodId:carPreiodId},function(res){	
									if(res.code==1){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
											  $(".bootbox").modal("hide");
											  $("#carPreiodList").DataTable().ajax.reload(function(){
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
				var carPreiodTpl = $("#carPreiodTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carPreiodTpl);
				$('#carPreiodList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carPreiod.appPath+'/carPreiod/pageListCarPreiod.do',
						"data": function ( d ) {
							var form = $("form[name=carPreiodSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carBrandId":form.find("select[name=carBrandId]").val(),
								"carPeriodName":form.find("input[name=carPeriodName]").val(),
								"carSeriesId":form.find("select[name=carSeriesId]").val(),
								"carSeriesNameQuery":$.trim(form.find("input[name=carPeriodName]").val())
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
						{ "title":"车型年代","data": "carPeriodName","defaultContent":"" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carPreiodtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carPreiodTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carPreiodTools").html("");
		       			$("#carPreiodTools").append('<button type="button" class="btn-new-type carPreiodTools-operate-add">新增</button>');
		       			
		       			$(".carPreiodTools-operate-add").on("click",function(){
		       				addTab("新增车辆年代", carPreiod.appPath+ "/carPreiod/toCarPreiodAdd.do");
		       			});	 
		       			
		       			/**
		       			 * 导出
		       			 */
		       			
					},
					"drawCallback": function( settings ) {
						carPreiod.operateColumEvent();
	        	    },
	        	    "order": [[ 2, "desc" ]],
					"columnDefs": [
						{
							"targets": [3],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon  carPreiod-operate-edit" data-id="'+c.carPeriodId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  carPreiod-operate-del" data-id="'+c.carPeriodId+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+del;
	        					
							}
						}
					]
				});
			}
	    };
	return carPreiod;
});
//获取指定的select选中的标签的内部值赋给指定的input
function selectBrandValue1 (selectId){
	var carBrandId = $("#"+selectId+"").find("option:selected").val();
	if(carBrandId!=""){
		$.post("carPreiod/getCarSeriesByBrandId.do", {carBrandId:carBrandId}, 
			function(res){ 
				var msg = res.msg;
				var code = res.code;
				var carSeriesList = res.data;
				var form = $("form[name=carPreiodSearchForm]");
				if(code == "1"){ 
					form.find("select[name=carSeriesId]").html("");
					var option="<option value=''>全部</option>";
                    for(var i=0;i<carSeriesList.length;i++){
                    	var carSeries = carSeriesList[i];
                    	if(i==0){
                    		option+="<option selected=selected value='"+carSeries.carSeriesId+"'> "+carSeries.carSeriesName+" </option>";
                    	}else{
                    		option+="<option value='"+carSeries.carSeriesId+"'> "+carSeries.carSeriesName+" </option>";
                    	}
          			}
                    form.find("select[name=carSeriesId]").html(option);
				}else{
					form.find("select[name=carSeriesId]").html("");
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该品牌下暂无车系！");
				}
		},"json")
	}
}

