define([],function() {	
	var item={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				item.pageList();
				//查询
				$("#itemSearchafter").click(function(){
					var form = $("form[name=itemSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							item.pageList();
						}
					}else{
						item.pageList();
					}
	            });
				$("#closeItemView").click(function(){
					closeTab("商品详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".item-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("商品详情",item.appPath+ "/mall/toItemView.do?itemNo="+$(this).data("id"));
					});
				});
				$(".item-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("商品编辑",item.appPath+ "/mall/toUpdateItem.do?itemNo="+$(this).data("id"));			
					});
				});
				$(".item-operate-status").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var status=$(this).data("status");
						var statusName = "下架";
						if(status == 1){
							status=0;
							statusName = "上架";
						}else{
							status=1;
						}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+statusName+"商品吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:item.appPath+"/mall/updateItem.do",
	    				    			type:"post",
	    				    			data:{itemNo:id,status:status},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){ 
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品"+statusName+"操作成功", function() {
	    				    							$("#itemList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品"+statusName+"操作失败");
	    				    					}
	    				    				}
	    				    			});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var itemTpl = $("#itemTpl").html();
				// 预编译模板
				var template = Handlebars.compile(itemTpl);
				$('#itemList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": item.appPath+'/mall/pageListItem.do',
						"data": function ( d ) {
							var form = $("form[name=itemSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"itemName":form.find("input[name=itemName]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
								"sortName":form.find("input[name=sortName]").val()
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
			            { "title":"商品编号","data": "itemNo" },
						{ "title":"名称名称","data": "itemName" },
						{ "title":"所属分类","data": "sortName" },
						{ "title":"积分","data": "points" },
						{ "title":"数量","data": "num" },
						{ "title":"状态","data": "status" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#itemTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#itemTools").html("");
					   $("#itemTools").removeClass("col-xs-6");
		       			$("#itemTools").append('<button type="button" class="btn-new-type itemTools-operate-add">新增</button>');
		       			$(".itemTools-operate-add").on("click",function(){
		       				addTab("新增商品", item.appPath+ "/mall/toAddItem.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						item.operateColumEvent();
	        	    },
					"columnDefs": [{
					    "targets": [5],
					    "render": function(a, b, c, d) {
					    		if(a==0){
					    			return "上架";
					    		}else{
					    			return "下架";
					    		}
					    	}
						},
						{
						    "targets": [6],
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
							"targets": [7],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon item-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.itemNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit='<span class="glyphicon item-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.itemNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
								var updateStatus;
								if(c.status==0){
									updateStatus='<span class="glyphicon item-operate-status" style="text-decoration: underline; cursor: pointer;" data-id="'+c.itemNo+'" data-status="'+c.status+'" data-toggle="tooltip" data-placement="top">下架</span>';
								}else{
									updateStatus='<span class="glyphicon item-operate-status" style="text-decoration: underline; cursor: pointer;" data-id="'+c.itemNo+'" data-status="'+c.status+'"  data-toggle="tooltip" data-placement="top">上架</span>';
								}
	        					return find+edit+updateStatus;
							}
						}
					]
				});
			}
	    };
	return item;
});


