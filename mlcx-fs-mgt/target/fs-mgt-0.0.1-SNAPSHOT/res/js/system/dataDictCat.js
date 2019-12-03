
define([],function() {
    var dataDict= {
        appPath:getPath("app"),	
        init: function () {
        	//查询
			$("#dataDictSearchButton").click(function(){
				dataDict.pageList();
            });
			//详情页关闭
			$("#closeViewDataDictCat").click(function(){
				closeTab("数据字典分类详情");
				$("#dataDictList").DataTable().ajax.reload(function(){});
            });
			dataDict.pageList();
        },
        operateColumEvent: function(){
			$(".dataDict-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("修改数据字典分类",dataDict.appPath+ "/dataDictCat/toEditDataDictCat.do?dataDictCatCode="+$(this).data("id"));
				});
			});
	        $(".dataDict-operate-view").each(function(){
				$(this).on("click",function(){
					addTab("查看数据字典分类",dataDict.appPath+ "/dataDictCat/toViewDataDictCat.do?dataDictCatCode="+$(this).data("id"));
				});
			});
			$(".dataDict-operate-del").each(function(){
				var dataDictCatCode = $(this).data("id");
				$(this).on("click",function(){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							dataDict.deleteDataDictCat(dataDictCatCode);
						}						
					}); 					
				});
			});	        	
        },
        //删除
        deleteDataDictCat: function (id) {
    		$.ajax({
    			url:dataDict.appPath+"/dataDictCat/delDataDict.do",
    			data:{dataDictCatCode:id},
    			dataType:"json",
    			success:function(res){ 
    					var code = res.code;
    					var data = res.data;
    					if(code == "1"){ 
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
	    						$("#dataDictList").DataTable().ajax.reload(function(){
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
        	var tpl=$("#dataDictTpl").html();
        	//预编译模板
			var template = Handlebars.compile(tpl);
			$("#dataDictList").dataTable({
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": dataDict.appPath+'/dataDictCat/dataDictPageList.do',
					"data":function(d){
						var form = $("form[name=dataDictSearchForm]");
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"dataDictCatName":$.trim(form.find("input[name=dataDictCatName]").val()),
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
							{ "title":"数据字典分类名称","data": "dataDictCatName" },
							{ "title":"父分类代码","data": "parentCatCode" },
							{ "title":"创建时间","data": "createTime" },
							{ "title":"更新时间","data": "updateTime" },
							{ "title":"是否删除","data": "isDeleted" },
							{ "title":"是否可用","data": "isAvailable" },						
							{ "title":"操作","orderable":false},
						],
					   "dom": "<'row'<'#dataDictTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   $("#dataDictTools").html("");
						   $("#dataDictTools").removeClass("col-xs-6");
						   $("#dataDictTools").append('<button type="button" class="btn-new-type dataDictTools-operate-addDataDictCat">添加</button>');
			       		   $(".dataDictTools-operate-addDataDictCat").on("click",function(){
			       			   addTab("数据字典分类新增", dataDict.appPath+ "/dataDictCat/toAddDataDictCat.do");
			       		   });
					   },
						"drawCallback": function( settings ) {
							dataDict.operateColumEvent();
		        	    },
		        	    "order": [[ 1, "desc" ]],
						"columnDefs": [
							{
								targets: 7,
								render: function (a, b, c, d) {							
									var view='<span class="glyphicon   dataDict-operate-view" data-id="'+c.dataDictCatCode+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">详情</span>';
									var edit='<span class="glyphicon  dataDict-operate-edit" data-id="'+c.dataDictCatCode+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">修改</span>';
		        					var del='<span class="glyphicon  dataDict-operate-del" data-id="'+c.dataDictCatCode+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">删除</span>';
		        					return view+edit+del;
								}
							},
							{
		        	            "targets": [5],
		        	            "render": function(data, type, row, meta) {
		        	                if(data==1){
		        	                	return "删除";
		        	                }else{
		        	                	return "未删除";
		        	                }
		        	            }
		        	        },
							{
		        	            "targets": [6],
		        	            "render": function(data, type, row, meta) {
		        	                if(data==1){
		        	                	return "可用";
		        	                }else{
		        	                	return "不可用";
		        	                }
		        	            }
		        	        },
							{
		        	            "targets": [3,4],
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
    return  dataDict;
});

