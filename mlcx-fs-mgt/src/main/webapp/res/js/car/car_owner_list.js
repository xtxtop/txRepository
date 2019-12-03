define([],function() {	
	var carOwner={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				carOwner.pageList();
				//查询
				$("#carOwnerSearch").click(function(){
					carOwner.pageList();
	            });
				$("#closeCarOwnerView").click(function(){
					closeTab("车主详情");
					carOwner.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".carOwner-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("车主详情",carOwner.appPath+ "/carOwner/toCarOwnerView.do?ownerId="+$(this).data("id"));
					});
				});
	        	$(".carOwner-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("车主信息编辑",carOwner.appPath+ "/carOwner/toCarOwnerEdit.do?ownerId="+$(this).data("id"));
					});
				});
	        	
	        
	        	
	        	$(".carOwner-operate-delete").each(function(id,obj){
					$(obj).on("click",function(){
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								carOwner.del($(obj).data("id"));
							}						
						}); 					
					});
				});	
	        	$(".carOwner-operate-cencor").each(function(){
					$(this).on("click",function(){
						addTab("车主信息审核",carOwner.appPath+ "/carOwner/toCarOwnerCencor.do?ownerId="+$(this).data("id"));
					});
				});
	        },
	        del: function (ownerId) {
	    		$.ajax({
	    			url:carOwner.appPath+"/carOwner/delCarOwner.do",
	    			type:"post",
	    			data:{ownerId:ownerId},
	    			dataType:"json",
	    			success:function(res){ 
	    					var code = res.code;
	    					var data = res.data;
	    					if(code == "1"){ 
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
		    						$("#carOwnerList").DataTable().ajax.reload(function(){

		    						}); 
	    						});
	    					}else{
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！"); 
	    					} 
	    			}
	    		});
	    		return false;
	        },
			pageList:function () {	
				debugger;
				var carOwnerBtnTpl = $("#carOwnerBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carOwnerBtnTpl);
				$('#carOwnerList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carOwner.appPath+'/carOwner/pageListCarOwner.do',
						"data": function ( d ) {
							var form = $("form[name=carOwnerSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"ownerName":$.trim(form.find("input[name=ownerName]").val()),
								"contactPhone":$.trim(form.find("input[name=contactPhone]").val()),
								"ownerType":form.find("select[name=ownerType]").val(),
								"cencorStatus":form.find("select[name=cencorStatus]").val(),
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
			            { "title":"名称","data": "ownerName" },
						{ "title":"类型","data": "ownerType" },
						{ "title":"联系人","data": "contactPerson"},
						{ "title":"联系手机","data": "contactPhone" },
						{ "title":"审核状态","data": "cencorStatus"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#carOwnerTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carOwnerTools").html("");
					   $("#carOwnerTools").removeClass("col-xs-6");
		       			$("#carOwnerTools").append('<button type="button" class="btn-new-type carOwnerTools-operate-add">新增</button>');
		       			$(".carOwnerTools-operate-add").on("click",function(){
		       				addTab("新增车主", carOwner.appPath+ "/carOwner/toCarOwnerAdd.do");
		       			});	
//		       			$("#carOwnerTools").append('<button type="button" class="btn btn-default btn-sm carOwnerTools-operate-import" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">批量导入</button>');
//		       			$("#carOwnerTools").append('<button type="button" class="btn btn-default btn-sm carOwnerTools-operate-export" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">导出模板</button>');
		       			/**
		       			 * 导出
		       			 */
		       			$(".carOwnerTools-operate-export").on("click",function(){
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出模板吗？", function(result) {
								if(result){
									window.location.href = carOwner.appPath+ "/carOwner/downloadExcelFile.do?filepath=car&newFileName=car_owner.xls";;
								}						
		       				});
		       			});
		       			$(".carOwnerTools-operate-import").on("click",function(){
		       				bootbox.dialog({
		       			        title: "导入",
		       			        dataType: "json",
		       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='exportForm' name='exportForm' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择要导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
		       			        buttons: {
		       			            "success": {
		       			                "label": "<i class='icon-ok'></i> 保存",
		       			                "className": "	btn-sm btn-success",
		       			                "callback": function() {
		       			                    var exportForm = $("form[name='exportForm']");
		       			                    exportForm.ajaxSubmit({
		       			                        url: "carOwner/importCarOwner.do",
		       			                        type: "post",
		       			                        success: function(res) {
		       			                        	if (res.code != 1) {
		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		       			                        	} else {
		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！"); 
		       			                        	}
		       			                            $("#carOwnerList").DataTable().ajax.reload(function(){
		       			                            	
		       			            				});
		       			                        }, 
		       			                        error: function(res) {
		       			                        	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据异常，请检查数据完整性！"); 
		       			                        }
		       			                    });
		       			                }
		       			            },
		       			            "cancel": {
		       			                "label": "<i class='icon-info'></i> 取消",
		       			                "className": "btn-sm btn-default"
		       			            }
		       			            
		       			        }
		       			    })
		       			});
					},
					"drawCallback": function( settings ) {
						carOwner.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [1],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "公司";
						        	}else if(a==2){
						        		return "个人";
						        	}
						        }else{
						        	return "";
						        }
						    }
/*						},{
						    "targets": [2,3,4],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        		return a.substring(0,a.length-4)+"****";
						        }else{
						        	return "";
						        }
						    }*/
						},
						
						
						{
							"targets" : [ 2 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return '<span class="glyphicon member-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.memberNo+ '" >'+a+'</span>';
								}else{
									return "";
								}
							}
						},
						
						
						
						{
						    "targets": [4],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "未审核";
						        	}else if(a==1){
						        		return "已审核";
						        	}else if(a==3){
						        		return "未通过";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						{
							"targets": [5],
							"render": function (a, b, c, d) {
								
								var result = "";	//定义返回结果集
								var view='<span class="glyphicon carOwner-operate-view" data-id="'+c.ownerId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var edit='<span class="glyphicon carOwner-operate-edit" data-id="'+c.ownerId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
								var remove='<span class="glyphicon carOwner-operate-delete" data-id="'+c.ownerId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">删除</span>';
								var cencor='';
								if(c.cencorStatus==0){
									cencor='<span class="glyphicon carOwner-operate-cencor" data-id="'+c.ownerId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">审核</span>';
									result = view+edit+remove+cencor;
								}
								if(c.cencorStatus==1 || c.cencorStatus==2){	//已审核 待审核  不允许编辑与删除
									result = view;
								}else if(c.cencorStatus==3){	//审核不通过  允许删除与编辑
									result = view+edit+remove;
								}
								return result;
							}
						}
					]
				});
			}
	    };
	return carOwner;
});


