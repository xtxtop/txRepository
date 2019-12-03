
define([],function() {
    var dataDictItem= {
        appPath:getPath("app"),	
        init: function () {
        	//查询
			$("#dataDictItemSearchButton").click(function(){
				dataDictItem.pageList();
            });
			//详情页关闭
			$("#closeViewDataDictItem").click(function(){
				closeTab("数据字典项详情");
				$("#dataDictItemList").DataTable().ajax.reload(function(){});
            });
			dataDictItem.pageList();
        },
        operateColumEvent: function(){
			$(".dataDictItem-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("修改数据字典项",dataDictItem.appPath+ "/dataDictItem/toEditDataDictItem.do?dataDictItemId="+$(this).data("id"));
				});
			});
	        $(".dataDictItem-operate-view").each(function(){
				$(this).on("click",function(){
					addTab("查看数据字典项",dataDictItem.appPath+ "/dataDictItem/toViewDataDictItem.do?dataDictItemId="+$(this).data("id"));
				});
			});
			$(".dataDictItem-operate-del").each(function(){
				var dataDictItemId = $(this).data("id");
				$(this).on("click",function(){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							dataDictItem.deleteDataDictItem(dataDictItemId);
						}						
					}); 					
				});
			});	        	
        },
        //删除
        deleteDataDictItem: function (id) {
    		$.ajax({
    			url:dataDictItem.appPath+"/dataDictItem/delDataDictItem.do",
    			data:{dataDictItemId:id},
    			dataType:"json",
    			success:function(res){ 
    					var code = res.code;
    					var data = res.data;
    					if(code == "1"){ 
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
	    						$("#dataDictItemList").DataTable().ajax.reload(function(){
	    						}); 
    						});
    					}else{
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！"); 
    					} 
    			}
    		});
    		return false;
        },
        pageList:function(){
        	var dataDictItemtpl=$("#dataDictItemTpl").html();
        	//预编译模板
			var template = Handlebars.compile(dataDictItemtpl);
			$("#dataDictItemList").dataTable({
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": dataDictItem.appPath+'/dataDictItem/dataDictItemPageList.do',
					"data":function(d){
						var form = $("form[name=dataDictItemSearchForm]");
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"dataDictCatCode":form.find("select[name=dataDictCatCode]").val(),
        					"isAvailable":form.find("select[name=isAvailable]").val(),
        					"isDeleted":0
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
							{ "title":"数据字典分类代码","data": "dataDictCatCode" },
							{ "title":"数据字典项值","data": "itemValue" },
							{ "title":"创建时间","data": "createTime" },
							{ "title":"更新时间","data": "updateTime" },
							{ "title":"是否删除","data": "isDeleted" },
							{ "title":"是否可用","data": "isAvailable" },						
							{ "title":"操作","orderable":false},
						],
					   "dom": "<'row'<'#dataDictItemTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   $("#dataDictItemTools").html("");
						   $("#dataDictItemTools").removeClass("col-xs-6");
						   $("#dataDictItemTools").append('<button type="button" class="btn-new-type dataDictItemTools-operate-addDataDictItem">添加</button>');
//						   $("#sysUserTools button").addClass('btnDefault');
						   $(".dataDictItemTools-operate-addDataDictItem").on("click",function(){
			       			   addTab("数据字典项新增", dataDictItem.appPath+ "/dataDictItem/toAddDataDictItem.do");
			       		   });
					   },
						"drawCallback": function( settings ) {
							dataDictItem.operateColumEvent();
		        	    },
		        	    "order": [[ 1, "desc" ]],
						"columnDefs": [
							{
								targets: 6,
								render: function (a, b, c, d) {

									var view='<span class="glyphicon  dataDictItem-operate-view" data-id="'+c.dataDictItemId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">详情</span>';
									var edit='<span class="glyphicon dataDictItem-operate-edit" data-id="'+c.dataDictItemId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">修改</span>';
		        					var del='<span class="glyphicon dataDictItem-operate-del" data-id="'+c.dataDictItemId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">删除</span>';
		        					return view+edit+del;
								}
							},
							{
		        	            "targets": [4],
		        	            "render": function(data, type, row, meta) {
		        	                if(data==1){
		        	                	return "删除";
		        	                }else{
		        	                	return "未删除";
		        	                }
		        	            }
		        	        },
							{
		        	            "targets": [5],
		        	            "render": function(data, type, row, meta) {
		        	                if(data==1){
		        	                	return "可用";
		        	                }else{
		        	                	return "不可用";
		        	                }
		        	            }
		        	        },
							{
		        	            "targets": [2,3],
		        	            "render": function(data, type, row, meta) {
		        	            	if(data){
		        	            		return moment(data).format("YYYY-MM-DD HH:mm:ss"); 
		        	            	}else{
		        	            		return "";
		        	            	} 
		        	            }
		        	        }
						]
			});
        }
    };
    return  dataDictItem;
});

