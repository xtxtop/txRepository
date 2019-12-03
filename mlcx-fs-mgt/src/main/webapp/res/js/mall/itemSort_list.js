define([],function() {	
	var itemSort={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				itemSort.pageList();
				//查询
				$("#itemSortSearchafter").click(function(){
					var form = $("form[name=itemSortSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							itemSort.pageList();
						}
					}else{
						itemSort.pageList();
					}
	            });
			},
			//方法加载
	        operateColumEvent: function(){
				$(".itemSort-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("商品分类编辑",itemSort.appPath+ "/mall/toUpdateItemSort.do?sortNo="+$(this).data("id"));			
					});
				});
	        },
			pageList:function () {	
				var itemSortTpl = $("#itemSortTpl").html();
				// 预编译模板
				var template = Handlebars.compile(itemSortTpl);
				$('#itemSortList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": itemSort.appPath+'/mall/pageListItemSort.do',
						"data": function ( d ) {
							var form = $("form[name=itemSortSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"sortName":form.find("input[name=sortName]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59"
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
						{ "title":"分类名称","data": "sortName" },
						{ "title":"所属分类","data": "parentSortName" },
						{ "title":"级别","data": "sortLevel" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#itemSortTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#itemSortTools").html("");
					   $("#itemSortTools").removeClass("col-xs-6");
		       			$("#itemSortTools").append('<button type="button" class="btn-new-type itemSortTools-operate-add">新增</button>');
		       			$(".itemSortTools-operate-add").on("click",function(){
		       				addTab("新增商品分类", itemSort.appPath+ "/mall/toAddItemSort.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						itemSort.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [3],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
							    	}else{
							    		return "";
							    	}
						    }
						},
						{
							"targets": [4],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon itemSort-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.sortNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
	        					return edit;
							}
						}
					]
				});
			}
	    };
	return itemSort;
});


