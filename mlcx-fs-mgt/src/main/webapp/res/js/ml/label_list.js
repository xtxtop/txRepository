define([],function() {	
	var label={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				label.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#labelSearchafter").click(function(){
					label.pageList();
	            });
				$("#closelabelView").click(function(){
					closeTab("标签详情");
					
	            });
			},
			//方法加载
	        operateColumEvent: function(){
                $(".label-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("标签编辑",label.appPath+ "/label/tolabelEdit.do?labelId="+$(this).data("id"));
                    });
                });
                $(".label-operate-del").on("click",function(){
					var labelId = $(this).data("id");
					$.post("label/verifyLabel.do",{labelId:labelId},function(res){	
						if(res.code==1){
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
								if(result){
									$.post("label/delLabel.do",{labelId:labelId},function(res){	
										if(res.code==1){
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
												  $(".bootbox").modal("hide");
												  $("#labelList").DataTable().ajax.reload(function(){
						    						}); 
											  });
										}else{
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
										}
									});
								}						
		       				});
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
						}
					});
				});
                $(".label-operate-reply").each(function(id,obj){
	        		$(obj).on("click",function(){
	        			var x=[];
	        			x=$(obj).data("id").split(',')
	                	var status='';
	                	if(x[1]==1){
	                		status='启用';
	                	}else{
	                		status='停用';
	                	}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+status+"吗？", function(result) {
							if(result){
								label.del(x[0],x[1],status);
							}						
						}); 					
					});
				});
	        },
	        del:function(id,enableStatus,status){
	        	$.ajax({
	        		url: label.appPath+ "/label/uplabel.do?labelId="+id+"&enableStatus="+enableStatus, 
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#labelList").DataTable().ajax.reload(function(){
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"成功！");
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"失败！");
	        			}
	        		}
	        	});      
	        },
			pageList:function () {	
				var labelBtnTpl = $("#labelTpl").html();
				// 预编译模板
				var template = Handlebars.compile(labelBtnTpl);
				$('#labelList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": label.appPath+'/label/pageListLabel.do',
						"data": function ( d ) {
							var form = $("form[name=labelSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"enableStatus":$.trim(form.find("select[name=enableStatus]").val()),
								"labelType":$.trim(form.find("select[name=labelType]").val()),
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							//alert(JSON.stringify(json.data))
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			            { "title":"标签ID","data": "labelId" ,"defaultContent":"--"},
						{ "title":"标签名称","data": "labelName" ,"defaultContent":"--"},
						{ "title":"状态","data": "enableStatus" ,"defaultContent":"--"},
						{ "title":"类型","data": "labelType" ,"defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#labelTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#labelTool").html("");
						$("#labelTool").removeClass("col-xs-6");
					   $("#labelTool").append('<button type="button" class="btn-new-type labelTools-operate-addlabel">新增</button>');
					   $(".labelTools-operate-addlabel").on("click",function(){
		       				addTab("新增标签", label.appPath+ "/label/toAddlabel.do");
		       			});
					},
					"drawCallback": function( settings ) {
						label.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
						{
							"targets" : [ 4],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD');
								}else{
									return "--";
								}
							}
						},  
								  
						{
						    "targets": [2],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "停用";
						        	}else if(a==1){
						        		return "启用";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [3],
							"render": function(a,b,c,d) {
								if(a!=null){
									if(a==1){
										return "充电桩标签";
									}else if(a==2){
										return "停车场标签";
									}
								}else{
									return "--";
								}
							}
						},
						{
							"targets": [5],
							"render": function (a, b, c, d) {
								var reply="";
                                var edit='<span class="glyphicon label-operate-edit" data-id="'+c.labelId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								if(c.enableStatus==1){
									reply='<span class="glyphicon label-operate-reply" data-id="'+c.labelId+',0" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									reply='<span class="glyphicon label-operate-reply" data-id="'+c.labelId+',1" data-toggle="tooltip" data-placement="top">启用</span>';
								}
								var del='<span class="glyphicon label-operate-del" data-id="'+c.labelId+'" data-toggle="tooltip" data-placement="top">删除</span>';
								return edit+reply+del;
							}
						}
					]
				});
			},
			
	    };
	return label;
});


