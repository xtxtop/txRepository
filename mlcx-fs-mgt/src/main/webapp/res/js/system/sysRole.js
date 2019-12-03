define([],function() {	
	var sysRole={
			appPath:getPath("app"),	
			init: function () {				
				$("#sysRoleEditBtn").click(function(){
					sysRole.addOrEdit();
	            });	    
				$("#sysRoleSearchafter").click(function(){
					sysRole.pageList();
	            });
				//清除（重置）
				$("#sysRoleResetBtn").click(function(){
					sysRole.reset();
		        });	
	            //数据列表
				sysRole.pageList();
				$("#mySysRoleModal").on("hidden.bs.modal", function() {  
	                //$(this).removeData("bs.modal");  
	            	var form = $("form[name=sysRoleForm]");
	            	form.clearForm();
	            	form.resetForm();
	            	form.find("input[type=hidden]").val("");
		         });
				//初始化树	(在这加一个是修复第一次加载时保留上次选择的)				
				sysRole.viewTree("sysRoleTreeSel",null,false,true);
				//弹出层show
				$("#mySysRoleModal").on("show.bs.modal", function() {
					//初始化树
					sysRole.viewTree("sysRoleTreeSel",null,false,true);					
		            //$("#sysRoleTreeSel").jstree().refresh();
		            $("#sysRoleTreeSel").jstree().deselect_all(true);
		            $("#sysRoleTreeSel").jstree().uncheck_all();
		            //$("#sysRoleTreeSel").jstree().delete_node(0);
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
			        				"url" : sysRole.appPath+"/sysPermission/list.do?type=1",
			        				"cache" : false,
			        				"data" : function (node) {
			        					return node;
		        				 }
	        			 }                   
	                },
	                "checkbox" : {
	                    "keep_selected_style" : false,
	                    "cascade" :"undetermined",
	                    "tie_selection":false,
	                    "visible":visible
	                 },
	                "plugins": [
	                    "contextmenu", "dnd", "search",
	                    "state", "types", "wholerow","checkbox"
	                ]
	            }); 
	            
	        },
	        getPermissions : function (roleId){
	        	var permids = [];
	        	$.ajax({
//	        		async:false,
	    			url:sysRole.appPath+"/sysPermission/getSysPermissionByRoleId.do",
	    			type:"post",
	    			data:{roleId:roleId},
	    			dataType:"json",	    			
	    			success:function(result){
	    				for(var i=0;i<result.length;i++) {
	    					permids.push(result[i].permId);	    						    					
	    				}
                        $("#sysRoleTreeSel").jstree().check_node(permids);
                        //console.debug(permids);
                        $('li[aria-level=1]').each(function (i, v) {
                            console.debug($(this).find('li').length);
                           $(this).find('li').each(function () {
                               var id = $(this).attr("id");
                               var is_check = false;
                                for (key in permids){
                                    //console.debug(permids[key] + ' ==  ' + id)
                                    if(id==permids[key]){
                                        //console.debug('------>>'+id);
                                        $("#sysRoleTreeSel").jstree().check_node(id);
                                        is_check = true;
                                    }
                                }
                               if(!is_check){
                                   $("#sysRoleTreeSel").jstree().uncheck_node(id);
                               }
                           });
                        });
                    }
	    		});
	        },
	        addOrEdit: function () {
        	   	var form = $("form[name=sysRoleForm]");
				form.ajaxSubmit({
					url : sysRole.appPath+"/sysRole/addOrEditSysRole.do",
					type : "post",
//					timeout:5000,
					success : function(data) {
						if (data.code == "1") {							
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#mySysRoleModal").modal("hide");
	    						$("#sysRoleList").DataTable().ajax.reload(function(){
	    							
	    						}); 
    						});
						} else {
                            //console.debug(data.msg);
                            if(data==''||data.msg==''){
                                alert('操作失败！');
                                return;
                            }else{
                            	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" +data.msg);
                            }
						}
						
					},
					error: function(xml,error) {  
                        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "出现异常，请刷新重试", function() {
									$("#mySysRoleModal").modal("hide");
		    						$("#sysRoleList").DataTable().ajax.reload(function(){
		    							
		    						}); 
	    						});
					},
					beforeSubmit: function(formData, jqForm, options) {
						var form = jqForm[0];
						if(!form.formRoleName.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入角色名称！");
					        return false;
					    }
						if(!form.sysRoleformMemo.value){
					        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入备注！");
					        return false;
					    }
						if(!form.sysRoleformIsAvailable1.checked && !form.sysRoleformIsAvailable2.checked){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择账户是否可用！");
				            return false;
						}
						var ids=[];
						var checkeds = $('#sysRoleTreeSel').jstree().get_checked();
						if(checkeds.length>0){							
							for(var i=0;i < checkeds.length;i++){
								ids.push(checkeds[i]);
							}							
							//权限ids
							formData[4].value = ids;
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要要分配的权限！");
							return false;
						}	
						
					}
				});
	        },
	        del: function (roleId) {
	    		$.ajax({
	    			url:sysRole.appPath+"/sysRole/deleteSysRole.do",
	    			type:"post",
	    			data:{roleId:roleId},
	    			dataType:"json",
//	    			async:false,
	    			success:function(result){ 
	    					var msg = result.msg;
	    					var code = result.code;
	    					var data = result.data;
	    					if(code == "1"){
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
									$("#mySysRoleModal").modal("hide");
		    						$("#sysRoleList").DataTable().ajax.reload(function(){
		    							
		    						}); 
	    						});
	    					}else{
	    						alert("删除失败！");
	    					} 
	    			}
	    		});
	    		return false;
	        },
	        operateColumEvent: function(){
				$(".sysRole-operate-edit").each(function(){
					$(this).on("click",function(){						
						$("#mySysRoleModal").modal("show");
						sysRole.detail($(this).data("id"));
					});
				});
				$(".sysRole-operate-del").each(function(id,obj){
					$(obj).on("click",function(){
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统角色吗？", function(result) {
							if(result){
								sysRole.del($(obj).data("id"));
							}						
						}); 					
					});
				});	  
				$("#sysRoleList").find('[data-toggle="tooltip"]').tooltip();
				$('#sysRoleList tbody input[type="checkbox"]').on("click",function(e){
					sysRole.updateDataTableSelectAll();
				});
				sysRole.updateDataTableSelectAll();
	        },
	        reset: function(){
	        	var form = $("form[name=sysRoleForm]");
	        	//form.clearForm();
	        	
	        	if(form.find("input[name=roleId]").val()){
	        		sysRole.detail(form.find("input[name=roleId]").val());
	        	}else{
	        		form.resetForm();
	            	form.find("input[type=hidden]").val("");        		
	        	}
	        },
	        detail: function (id){ 
             	$.ajax({
             		url: sysRole.appPath+"/sysRole/detail.do?roleId="+id, 
             		success: function(data){
             			if(data){
             				var form = $("form[name=sysRoleForm]");
             				sysRole.loadData(data);
             			}
             		}
             	});
             },
	        loadData:function(jsonStr){
	            var obj = eval(jsonStr);
	            var key,value,tagName,type,arr;
	            var form = $("form[name=sysRoleForm]");
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
	                if(key=="roleId"){
	                	 sysRole.getPermissions(value);
	                }
	            }
	           
	        },
	        //批量删除
	        batchRemove: function (ids) {
	        	$.ajax({
	        		url: sysRole.appPath+"/sysRole/batchDelete.do?sysRoleIds="+ids, 
	        		success: function(data){
	        			if(data.code=1){
	        				$("#sysRoleList").DataTable().ajax.reload(function(){
	        					sysRole.updateDataTableSelectAll();
	        				});
	        			}
	        		}
	        	});        	
	        },
	        updateDataTableSelectAll:function(){
	        	   var $chkbox_all        = $('#sysRoleList tbody input[type="checkbox"]');
	        	   var $chkbox_checked    = $('#sysRoleList tbody input[type="checkbox"]:checked');
	        	   var chkbox_select_all  = $('#sysRoleList thead input[type="checkbox"]');
	        	   if($chkbox_checked.length === 0){
				         $('#sysRoleList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   } else if ($chkbox_checked.length === $chkbox_all.length){
				         $('#sysRoleList thead input[type="checkbox"]:not(:checked)').prop("checked",true);   	     
	        	   } else {
				         $('#sysRoleList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   }
	        },
			pageList:function () {	
				var tpl = $("#sysRoleTpl").html();
				//预编译模板
				var template = Handlebars.compile(tpl);
				$('#sysRoleList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": sysRole.appPath+'/sysRole/getSysRoleList.do',
						"data": function ( d ) {
							var form = $("form[name=sysRoleSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"roleName":form.find("input[name=roleName]").val(),
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
					    { "title":"","data": "roleId","width":"5px"},
			            { "title":"角色名称","data": "roleName" },
			            { "title":"创建时间","data": "createTime" },
						{ "title":"更新时间","data": "updateTime" },
						{ "title":"角色状态","data": "isAvailable" },						
						{ "title":"操作","orderable":false}
					],
				   //"dom": "<'row'<'col-xs-2'l><'#sysRoletool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#sysRoleTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#sysRoleTools").removeClass("col-xs-6");
					   $(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
						$("#sysRoleTools").append('<button type="button" class="btn-new-type" data-toggle="modal" data-target="#mySysRoleModal">添加</button>');
//						$("#sysUserTools button").addClass('btnDefault');
//						$("#sysRoleTools").append('<button type="button" class="btn btn-default btn-sm sysRole-batch-del">批量删除</button>');  
//						$(".sysRole-batch-del").on("click",function(){
//	    					var ids=[];
//	        				$("#sysRoleList tbody").find("input:checked").each(function(){
//	        					ids.push($(this).val());
//	        				});
//	        				if(ids.length>0){
//	        					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统角色吗？", function(result) {
//	        						if(result){
//	        							sysRole.batchRemove(ids);
//	        						}
//	        					});	
//	        				}else{
//	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要删除的角色参数！");
//	        				}
//	        			});	        			
	        			$('#sysRoleList thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#sysRoleList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#sysRoleList tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					},
					"drawCallback": function( settings ) {
						sysRole.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{ "targets":[0],
						    //"visible": true,
						    "orderable":false,
						    "render":function (data, type, full, meta){
						             return '<input type="checkbox" name="roleId[]" value="' + full.roleId + '">';
						    }
						},
						{
							targets: 5,
							render: function (a, b, c, d) {								
								var edit='<span class="glyphicon  sysRole-operate-edit" data-id="'+c.roleId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">修改</span>';
//	        					var del='<span class="glyphicon   btn btn-danger sysRole-operate-del" data-id="'+c.roleId+'" data-toggle="tooltip" data-placement="top" title="删除">删除</span>';
	        					return edit;
							}
						},
						{
	        	            "targets": [4],
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
	return sysRole;
});

