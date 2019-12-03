define([],function() {
	var depositZhimaReduction = {
		appPath : getPath("app"),
		init : function() {
			$("#closeDepositZhimaReductionView").click(function(){
				closeTab("芝麻分减免押金详情");
			});
			// 列表页面搜索调用
			$("#depositZhimaReductionSearch").click(function() {
				depositZhimaReduction.pageList();
			});
			$("#depositZhimaReductionCensorModel").on("hidden.bs.modal", function() {  
            	var form = $("form[name=depositZhimaReductionCensorForm]");
            	form.resetForm();
            	$("#censorMemo").text("");
            });
			// 列表页面分页方法调用
			depositZhimaReduction.pageList();

		},
		operateColumEvent : function() {
			$(".depositZhimaReduction-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("芝麻分减免押金详情",depositZhimaReduction.appPath+ "/depositZhimaReduction/toDepositZhimaReductionView.do?id="+$(this).data("id"));
				});
			});
			$(".depositZhimaReduction-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("芝麻分减免押金修改",depositZhimaReduction.appPath+ "/depositZhimaReduction/toEditDepositZhimaReduction.do?id="+$(this).data("id"));
				});
			});
			
			//启停用
			$(".depositZhimaReduction-operate-isAvailable").each(function(id,obj){
				$(this).on("click",function(){
					var id=$(this).data("id");
					var isAvailable = $(this).data("isavailable");
					var name = $(this).data("name");
					var reduction = $(this).data("reduction");
					var hint = isAvailable == 1 ? "启用" : "停用";
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"会员达到芝麻分["+name+"]减免["+reduction+"]元押金吗？", function(result) {
						if(result){
							 $.ajax({
				    			url:depositZhimaReduction.appPath+"/depositZhimaReduction/updateDepositZhimaReduction.do",
				    			type:"post",
				    			data:{
				    				id:id,
				    				isAvailable:isAvailable
				    			},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var msg=res.msg;
			    					if(code == "1"){ 
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
			    							$("#depositZhimaReductionList").DataTable().ajax.reload(function(){});
			    						});
			    					}else if(msg != null){
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败," + msg);
			    					}else{
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
			    					}
			    				}
			    			});
						}						
       				});
				});
			});
			
			
			//删除
			$(".depositZhimaReduction-operate-del").each(function(id,obj){
				$(this).on("click",function(){
					var id=$(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除该数据吗？", function(result) {
						if(result){
							 $.ajax({
				    			url:depositZhimaReduction.appPath+"/depositZhimaReduction/deleteDepositZhimaReduction.do",
				    			type:"post",
				    			data:{
				    				id:id
				    			},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var msg=res.msg;
			    					if(code == "1"){ 
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
			    							$("#depositZhimaReductionList").DataTable().ajax.reload(function(){});
			    						});
			    					}else if(msg != null){
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败," + msg);
			    					}else{
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
			    					}
			    				}
			    			});
						}						
       				});
				});
			});
		},
		
		
		pageList : function() {
			var form = $("form[name=depositZhimaReductionSerachForm]");
			var depositZhimaReductionTpl = $("#depositZhimaReductionTpl").html();
			// 预编译模板
			var template = Handlebars.compile(depositZhimaReductionTpl);
			var table = $('#depositZhimaReductionList').dataTable({
				searching : false,
				destroy : true,
				"ajax" : {
					"type" : "POST",
					"url" : depositZhimaReduction.appPath
							+ "/depositZhimaReduction/pageListDepositZhimaReduction.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "censorStatus":form.find("select[name='censorStatus']").val(),
										 "isAvailable":form.find("select[name='isAvailable']").val()
										});
					},
					"dataSrc" : function(json) {
						json.recordsTotal = json.rowCount;
						json.recordsFiltered = json.rowCount;
						json.data = json.data;
						return json.data;
					},
					error : function(xhr, error, thrown) {
					}
				},
				"columns" : [ 
					{"title" : "芝麻分","data": "zhimaScore"},
					{"title" : "减免金额","data" : "reductionAmount"}, 
					{"title" : "启用状态","data" : "isAvailable"},
					{"title" : "操作","orderable" : false} 
				],
				"dom": "<'row'<'#depositZhimaReductionTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				"initComplete" : function() {
					$("#depositZhimaReductionTools").html("");
				   	$("#depositZhimaReductionTools").removeClass("col-xs-6");
				   	$("#depositZhimaReductionTools").append('<button type="button" class="btn-new-type depositZhimaReductionTools-operate-add">新增</button>');
					$(".depositZhimaReductionTools-operate-add").on("click",function(){
	       				addTab("新增芝麻分减免押金", depositZhimaReduction.appPath+ "/depositZhimaReduction/toAddDepositZhimaReduction.do");
	       			});	
				},
				"drawCallback" : function(settings) {
					depositZhimaReduction.operateColumEvent();
				},
				"columnDefs" : [
					{
						"targets" : [ 2 ],
						"render" : function(a,
								b, c, d) {
							var text ="";
							if(a==0){
								text="不可用";
							}else if(a==1){
								text="可用";
							}
							return text;
						}
					},
					{
						targets : [ 3 ],
						render : function(a, b, c,d) {
							var view = '<span class="glyphicon depositZhimaReduction-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
									+ c.id
									+ '" >查看</span>';
							var edit='<span class="glyphicon depositZhimaReduction-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.id+'" data-toggle="tooltip" data-placement="top" >修改</span>';
							var del ='<span class="glyphicon depositZhimaReduction-operate-del" data-name="'+c.zhimaScore+'" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.id	+ '" >删除</span>';
							var censorStatus = "";
							var isAvailable = "";
							if (c.isAvailable==0) {
								isAvailable='<span class="glyphicon depositZhimaReduction-operate-isAvailable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.zhimaScore+'" data-id="'+c.id + '" data-reduction="'+c.reductionAmount+'"  data-isAvailable=1 data-toggle="tooltip" data-placement="top">启用</span>';
							}else if(c.isAvailable==1) {
								edit = "";
								del = "";
								isAvailable='<span class="glyphicon depositZhimaReduction-operate-isAvailable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.zhimaScore+'" data-id="'+c.id +'" data-reduction="'+c.reductionAmount+'" data-isAvailable=0 data-toggle="tooltip" data-placement="top">停用</span>';
							}
							return edit + del + censorStatus + isAvailable;//+  view;
						}
					}]
			});
		}
	};
	return depositZhimaReduction;
});
