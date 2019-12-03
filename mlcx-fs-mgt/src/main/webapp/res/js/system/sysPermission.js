define([],function() {	
	var sysPermission={
			appPath:getPath("app"),	
			init: function () {	
				//增加，修改提交按钮
				$("#sysPermissionEditBtn").click(function(){
					sysPermission.addOrEdit();					
	            });	   
				//查询
				$("#sysPermissionSearchafter").click(function(){
					sysPermission.pageList();
	            });
				//清除（重置）
				$("#sysPermissionResetBtn").click(function(){
					sysPermission.reset();
		        });				
	            //数据
	            //数据列表
				sysPermission.pageList();
				//弹出层hidden
				$("#mySysPermissionModal").on("hidden.bs.modal", function() {  
	                //$(this).removeData("bs.modal");  
	            	var form = $("form[name=sysPermissionForm]");
	            	form.clearForm();
	            	form.resetForm();
	            	form.find("input[type=hidden]").val("");
		        });
				//初始化树	(在这加一个是修复第一次加载时保留上次选择的)				
				sysPermission.viewTree("sysPermissionTreeSel",null,false,true);
				//弹出层show
				$("#mySysPermissionModal").on("show.bs.modal", function() {
					//初始化树
					sysPermission.viewTree("sysPermissionTreeSel",null,false,true);					
		            //$("#sysPermissionTreeSel").jstree().refresh();
					$("#sysPermissionTreeSel").jstree().uncheck_all();
		            $("#sysPermissionTreeSel").jstree().deselect_all(true);			            
		        });	
				//是否填写菜单信息
				$("#sysPermissionDivShow").hide();
				var form = $("form[name=sysPermissionForm]");
				form.find("input[name=isMenu]").change(function(){
				       if($(this).is(':checked')){
					         if($(this).val()=="1"){
					        	 $("#sysPermissionDivShow").show();
					       	}else{
					       		$("#sysPermissionDivShow").hide();
					       	}
				       }
				       
				 });
			},	
			//权限树
			viewTree: function (domId,condition,multiple,visible) {
	            $("#"+domId).jstree({
	                "core": {
	                    "animation": 0,
	                    "check_callback": true,
	                    "themes": { "stripes": true },
	                    "multiple" : multiple ,
	                    "data" : {
			        				"url" : sysPermission.appPath+"/sysPermission/list.do",
			        				"cache" : false,
			        				"data" : function (node) {
			        					return node;
		        				 }
	        			 }                   
	                },
	                /*"types": {
	                    "#": {
	                        "max_children": 1,
	                        "max_depth": 4,
	                        "valid_children": ["root"]
	                    },
	                    "root": {
	                        "icon": "/static/3.2.1/assets/images/tree_icon.png",
	                        "valid_children": ["default"]
	                    },
	                    "default": {
	                        "valid_children": ["default", "file"]
	                    },
	                    "file": {
	                        "icon": "glyphicon glyphicon-file",
	                        "valid_children": []
	                    }
	                },*/
	                "checkbox" : {
	                    "keep_selected_style" : false,
	                    "three_state" : false,
	                    "cascade" :"undetermined",
	                    /*"tie_selection":false,*/
	                    "visible":visible
	                 },
	                "plugins": [
	                    "contextmenu", "dnd", "search",
	                    "state", "types", "wholerow","checkbox"
	                ]
	            });        	
	        },
	        addOrEdit: function (sysParamId) {		        	
	        	var form = $("form[name=sysPermissionForm]");
				form.ajaxSubmit({
					url : sysPermission.appPath+"/sysPermission/addOrEditSysPermission.do",
					type : "post",
					success : function(data) {
						if (data.code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#mySysPermissionModal").modal("hide");
	    						$("#sysPermissionList").DataTable().ajax.reload(function(){
	    							$("#sysPermissionTreeSel").jstree().refresh();
	    						}); 
    						});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
						}
					},
					beforeSubmit: function(formData, jqForm, options) {
						var form = jqForm[0];
						if(!form.formModuleName.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入模快名称！");
					        return false;
					    }
						if(!form.formPermName.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入权限名称！");
					        return false;
					    }
						if(!form.formPermType1.checked && !form.formPermType2.checked){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择权限类型！");
				            return false;
						}
						if(!form.formPermResource.value){
					        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入权限资源！");
					        return false;
					    }
						if(!form.sysPermissionformIsMenu1.checked && !form.sysPermissionformIsMenu2.checked){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择是否是菜单！");
				            return false;
						}
						if(form.sysPermissionformIsMenu1.checked){
							if(!form.sysPermissionformIsMenuShow1.checked && !form.sysPermissionformIsMenuShow2.checked){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择菜单是否显示！");
					            return false;
							}
							if(!form.formMenuName.value){
						        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入菜单名称！");
						        return false;
						    }							
						}
						if(!form.formLevel.value){
					        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入层级！");
					        return false;
					    }
						if(form.formLevel.value < 1){
					        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "层级不能小于1！");
					        return false;
					    }
						
						if(!form.formRanking.value){
					        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入排序！");
					        return false;
					    }
						if(form.formRanking.value < 1){
					        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "排序不能小于1！");
					        return false;
					    }
						var parentId;
						for(var i =0;i<formData.length;i++){
							if(formData[i].name=="parentId"){
								parentId = formData[i];	
							}
						}
						if(!$("#sysPermissionTreeSel").jstree().get_selected().length){
							//9是父节点表单索引
							parentId.value=0;
						}else{
							//form.parentId.value=$("#sysPermissionTreeSel").jstree().get_selected()[$("#sysPermissionTreeSel").jstree().get_selected().length-1];
							parentId.value=$("#sysPermissionTreeSel").jstree().get_selected()[$("#sysPermissionTreeSel").jstree().get_selected().length-1];
						}
						if(!form.sysPermissionformMemo.value){
					        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入备注！");
					        return false;
					    }
						if(!form.sysPermissionformIsAvailable1.checked && !form.sysPermissionformIsAvailable2.checked){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择权限是否可用！");
				            return false;
						}
					}
				}); 
	        },
	        del: function (permissionId) {

	    		$.ajax({
	    			url:sysPermission.appPath+"/sysPermission/deleteSysPermission.do",
	    			type:"post",
	    			data:{permissionId:permissionId},
	    			dataType:"json",
//	    			async:false,
	    			success:function(result){ 
	    					var msg = result.msg;
	    					var code = result.code;
	    					var data = result.data;
	    					if(code == "1"){
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
									$("#mySysPermissionModal").modal("hide");
		    						$("#sysPermissionList").DataTable().ajax.reload(function(){
		    							
		    						}); 
	    						});
	    					}else{
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！");	    						
	    					} 
	    			}
	    		});
	    		return false;
	        },	
	        //方法加载
	        operateColumEvent: function(){
				$(".sysPermission-operate-edit").each(function(){
					$(this).on("click",function(){
						$("#mySysPermissionModal").modal("show");
						sysPermission.detail($(this).data("id"));
					});
				});
				$(".sysPermission-operate-del").each(function(id,obj){
					$(obj).on("click",function(){
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统权限吗？", function(result) {
							if(result){
								sysPermission.del($(obj).data("id"));
							}						
						}); 					
					});
				});	
				$("#sysPermissionList").find('[data-toggle="tooltip"]').tooltip();	
				
				$('#sysPermissionList tbody input[type="checkbox"]').on("click",function(e){
					sysPermission.updateDataTableSelectAll();
				});
				sysPermission.updateDataTableSelectAll();
	        },
	        reset: function(){
	        	var form = $("form[name=sysPermissionForm]");
	        	//form.clearForm();
	        	
	        	if(form.find("input[name=permId]").val()){
	        		sysPermission.detail(form.find("input[name=permId]").val());
	        	}else{
	        		form.resetForm();
	            	form.find("input[type=hidden]").val("");        		
	        	}
	        },
	        detail: function (id){ 
             	$.ajax({
             		url: sysPermission.appPath+"/sysPermission/detail.do?permId="+id, 
             		success: function(data){
             			if(data){
             				var form = $("form[name=sysPermissionForm]");
             				sysPermission.loadData(data);
             			}
             		}
             	});
             },
	        loadData:function(jsonStr){
	            var obj = eval(jsonStr);
	            var key,value,tagName,type,arr;
	            var form = $("form[name=sysPermissionForm]");
	            for(x in obj){
	                key = x;
	                value = obj[x];                 
	                form.find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
	                    tagName = $(this)[0].tagName;
	                    type = $(this).attr("type");
	                    if(tagName=="INPUT"){
	                        if(type=="radio"){
	                            $(this).prop("checked",$(this).val()==value);
	                        }else if(type=="checkbox"){
	                            arr = value.split(",");
	                            for(var i =0;i<arr.length;i++){
	                                if($(this).val()==arr[i]){
	                                    $(this).prop("checked",true);
	                                    break;
	                                }
	                            }
	                        }else{
	                            $(this).val(value);
	                        }
	                    }else if(tagName=="SELECT" || tagName=="TEXTAREA"){
	                        $(this).val(value);
	                    }
	                     
	                });
	                if(key=="parentId"){
	                	$("#sysPermissionTreeSel").jstree().select_node([value]);
	                }
	            }
	           
	        },
	        //批量删除
	        batchRemove: function (ids) {
	        	$.ajax({
	        		url: sysPermission.appPath+"/sysPermission/batchDelete.do?sysPermissionIds="+ids, 
	        		success: function(data){
	        			if(data.code=1){
	        				$("#sysPermissionList").DataTable().ajax.reload(function(){
	        					sysPermission.updateDataTableSelectAll();
	        				});
	        			}
	        		}
	        	});        	
	        },
	        updateDataTableSelectAll:function(){
	        	   var $chkbox_all        = $('#sysPermissionList tbody input[type="checkbox"]');
	        	   var $chkbox_checked    = $('#sysPermissionList tbody input[type="checkbox"]:checked');
	        	   var chkbox_select_all  = $('#sysPermissionList thead input[type="checkbox"]');
	        	   if($chkbox_checked.length === 0){
				         $('#sysPermissionList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   } else if ($chkbox_checked.length === $chkbox_all.length){
				         $('#sysPermissionList thead input[type="checkbox"]:not(:checked)').prop("checked",true);   	     
	        	   } else {
				         $('#sysPermissionList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   }
	        	},
			pageList:function () {	
				var tpl = $("#sysPermissionTpl").html();
				//预编译模板
				var template = Handlebars.compile(tpl);
				$('#sysPermissionList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": sysPermission.appPath+'/sysPermission/getSysPermissionList.do',
						"data": function ( d ) {
							var form = $("form[name=sysPermissionSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"permName":form.find("input[name=permName]").val(),
								"moduleName":form.find("input[name=moduleName]").val(),
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
					    { "title":"","data": "permId","width":"5px"},
			            { "title":"权限名称","data": "permName" },
						{ "title":"模块名称","data": "moduleName" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"更新时间","data": "updateTime" },
						{ "title":"是否可用","data": "isAvailable" },						
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#sysPermissiontool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#sysPermissionTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#sysPermissionTools").removeClass("col-xs-6");
					   	$(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
						$("#sysPermissionTools").append('<button type="button" class="btn-new-type" data-toggle="modal" data-target="#mySysPermissionModal" id="mySysPermission">添加</button>');
//						$("#sysPermissionTools button").addClass('btnDefault');
						$("#sysPermissionTools").append('<button type="button" class="btn-new-type sysPermission-batch-del">批量删除</button>');
						$(".sysPermission-batch-del").on("click",function(){
	    					var ids=[];
	        				$("#sysPermissionList tbody").find("input:checked").each(function(){
	        					ids.push($(this).val());
	        				});
	        				if(ids.length>0){
	        					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统权限吗？", function(result) {
	        						if(result){
	        							sysPermission.batchRemove(ids);
	        						}
	        					});	
	        				}else{
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要删除的系统权限！");
	        				}
	        			});	        			
	        			$('#sysPermissionList thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#sysPermissionList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#sysPermissionList tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					},
					"drawCallback": function( settings ) {
						sysPermission.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{ "targets":[0],
						    //"visible": true,
						    "orderable":false,
						    "render":function (data, type, full, meta){
						             return '<input type="checkbox" name="permId[]" value="' + full.permId + '">';
						    }
						},
						{
							targets: 6,
							render: function (a, b, c, d) {								
								var edit='<span class="glyphicon  sysPermission-operate-edit" data-id="'+c.permId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">修改</span>';
	        					var del='<span class="glyphicon  sysPermission-operate-del" data-id="'+c.permId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">删除</span>';
	        					return edit+del;
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
	        	            "targets": [3,4],
	        	            "render": function(data, type, row, meta) {
	        	            	var now = moment(data); 
	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
	        	            }
	        	        }
					]
				});
				$("#myModal").on("shown.bs.modal", function () {
					//$('#myInput').focus()；
				});
			}
	    };
	return sysPermission;
});


