define([],function() {	
	var sysRegion={
			appPath:getPath("app"),	
			init: function () {				
				$("#sysRegionEditBtn").click(function(){
					sysRegion.addOrEdit();
	            });
				//搜索
				$("#sysRegionSearchafter").click(function(){
					sysRegion.pageList();
	            });
	            //数据列表
				sysRegion.pageList();
				//清除（重置）
				$("#sysRegionResetBtn").click(function(){
					sysRegion.reset();
		        });				

				$("#mySysRegionModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=sysRegionForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	form.find("input[type=hidden]").val("");
	            });
				//初始化树	(在这加一个是修复第一次加载时保留上次选择的),type=1是需要添加一级地区是用到				
				sysRegion.viewTree("sysRegionTreeSel",null,false,true);
				//弹出层show
				$("#mySysRegionModal").on("show.bs.modal", function() {
					//$.jstree.destroy();
					//初始化树					
					sysRegion.viewTree("sysRegionTreeSel",null,false,true);
		            //$("#sysRegionTreeSel").jstree().refresh();
					$("#sysRegionTreeSel").jstree().uncheck_all();
		            $("#sysRegionTreeSel").jstree().deselect_all(true);
		        });				
						
			},	
			 viewTree:function(domId,condition,multiple,visible){
		        	$("#"+domId).jstree({
		        		"core":{
		        			"animation": 0,
		                    "check_callback": true,
		                    "themes": { "stripes": true },
		                    "multiple" : multiple ,
		                    "expand_selected_onload":true,
		                    "data" : {
		   		        				"url" : function(node){
		   			        					if(condition){
		   			        					    node.regionId=condition.regionId;
		   			        					}
		   		        						return node.regionId ? sysRegion.appPath+"/sysRegion/childTree.do" :
		   		        							sysRegion.appPath+"/sysRegion/tree.do";
		   		        				},	
		   		        				"cache" : false,
		   		        				"data" : function (node){
		   		        					if(condition){
		   		        					    node.regionId=condition.regionId;
		   		        					}
		   		        					return node;
		   	        				     }
		        			 }
		        		},
			   			 "checkbox" : {
			                 "keep_selected_style" : false,
			                 "three_state" : false,
			                 "cascade" :"undetermined",
			                 "visible":visible
			              },
			             "plugins": [
			                 "contextmenu", "dnd", "search",
			                 "state", "types", "wholerow","checkbox"
			             ]
		        	});
		        	$("#"+domId).bind("ready.jstree", function(e){
		             	$("#"+domId).jstree("uncheck_all");
		             });
		             $("#"+domId).bind("refresh.jstree", function(e){
		             	$("#"+domId).jstree("uncheck_all");
		             });
		        },
		        
	        addOrEdit: function () {	        	
	        	var form = $("form[name=sysRegionForm]");
				form.ajaxSubmit({
					url : sysRegion.appPath+"/sysRegion/addOrEditSysRegion.do",
					type : "post",
					success : function(data) {
						if (data.code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function(){
								$("#mySysRegionModal").modal("hide");
	    						$("#sysRegionList").DataTable().ajax.reload(function(){
	    							$("#sysRegionTreeSel").jstree().refresh();
	    						});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
						}							
					},
					beforeSubmit: function(formData, jqForm, options) {
						var form = jqForm[0];
						if(!form.formRegionName.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入地区名称！");
					        return false;
					    }
						/*if(!form.formPostCode.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入邮政编码！");
					        return false;
					    }*/
						if(!form.formLevel.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入级别！");
					        return false;
					    }
						
						var a = formData[4].value;
						if(!$("#sysRegionTreeSel").jstree().get_selected().length && formData[3].value != 1){
							//4是父节点表单索引
							///formData[4].value=0;
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择父级地区，如果是无父级地区则选择一级地区！");
				            return false;
						}else{							
							formData[4].value=$("#sysRegionTreeSel").jstree().get_selected()[$("#sysRegionTreeSel").jstree().get_selected().length-1];
						}
						if(!form.sysRegionformIsAvailable1.checked && !form.sysRegionformIsAvailable2.checked){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择地区是否可用！");
				            return false;
						}
					}
				}); 
	        },
	        //删除
	        del: function (regionId) {

	    		$.ajax({
	    			url:sysRegion.appPath+"/sysRegion/deleteSysRegion.do",
	    			type:"post",
	    			data:{regionId:regionId},
	    			dataType:"json",
//	    			async:false,
	    			success:function(result){ 
	    					var msg = result.msg;
	    					var code = result.code;
	    					var data = result.data;
	    					if(code == "1"){
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function(){
									$("#mySysRegionModal").modal("hide");
		    						$("#sysRegionList").DataTable().ajax.reload(function(){
		    							
		    						});
								});
	    					}else{
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);	    						
	    					} 
	    			}
	    		});
	    		return false;
	        },
	        //初始化编辑，删除事件
	        operateColumEvent: function(){
				$(".sysRegion-operate-edit").each(function(){
					$(this).on("click",function(){
						$("#mySysRegionModal").modal("show");
						sysRegion.detail($(this).data("id"));					
					});
				});
				$(".sysRegion-operate-del").each(function(id,obj){
					$(obj).on("click",function(){
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统用户吗？", function(result) {
							if(result){
								sysRegion.del($(obj).data("id"));
							}						
						}); 					
					});
				});	   
				$("#sysRegionList").find('[data-toggle="tooltip"]').tooltip();	
				$('#sysRegionList tbody input[type="checkbox"]').on("click",function(e){
					sysRegion.updateDataTableSelectAll();
				});
				sysRegion.updateDataTableSelectAll();
	        },
	        reset: function(){
	        	var form = $("form[name=sysRegionForm]");
	        	//form.clearForm();
	        	
	        	if(form.find("input[name=regionId]").val()){
	        		sysRegion.detail(form.find("input[name=regionId]").val());
	        	}else{
	        		form.resetForm();
	            	form.find("input[type=hidden]").val("");        		
	        	}
	        },
	        //加载详细信息
	        detail: function (id){	        	
	        	$.ajax({
	        		url: sysRegion.appPath+"/sysRegion/detail.do?regionId="+id, 
	        		success: function(data){
	        			if(data){
	        				var form = $("form[name=sysRegionForm]");
	        				sysRegion.loadData(data);
	        			}
	        		}
	        	})
	        },
	        //加载数据
	        loadData:function(jsonStr){
	            var obj = eval(jsonStr);
	            var key,value,tagName,type,arr;
	            var form = $("form[name=sysRegionForm]");
	            for(x in obj){
	                key = x;
	                value = obj[x];                
	                form.find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
	                    tagName = $(this)[0].tagName;
	                    type = $(this).attr("type");
	                    if(tagName=="INPUT"){
	                        if(type=="radio"){
	                            $(this).attr("checked",$(this).val()==value);
	                        }else if(type=="checkbox"){
	                            arr = value.split(",");
	                            for(var i =0;i<arr.length;i++){
	                                if($(this).val()==arr[i]){
	                                    $(this).attr("checked",true);
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
	                	$("#sysRegionTreeSel").jstree().select_node([value]);
	                }
	            }
	        },
	        //批量删除
	        batchRemove: function (ids) {
	        	$.ajax({
	        		url: sysRegion.appPath+"/sysRegion/batchDelete.do?sysRegionIds="+ids, 
	        		success: function(data){
	        			if(data.code == "1"){
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function(){
								$("#mySysRegionModal").modal("hide");
	    						$("#sysRegionList").DataTable().ajax.reload(function(){
	    							
	    						});
							});
    					}else{
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);	    						
    					} 
	        		}
	        	});        	
	        },
	        updateDataTableSelectAll:function(){
	        	   var $chkbox_all        = $('#sysRegionList tbody input[type="checkbox"]');
	        	   var $chkbox_checked    = $('#sysRegionList tbody input[type="checkbox"]:checked');
	        	   var chkbox_select_all  = $('#sysRegionList thead input[type="checkbox"]');
	        	   if($chkbox_checked.length === 0){
				         $('#sysRegionList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   } else if ($chkbox_checked.length === $chkbox_all.length){
				         $('#sysRegionList thead input[type="checkbox"]:not(:checked)').prop("checked",true);   	     
	        	   } else {
				         $('#sysRegionList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   }
	        },
			pageList:function () {				
				var tpl = $("#sysRegionTpl").html();
				//预编译模板
				var template = Handlebars.compile(tpl);
				$('#sysRegionList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": sysRegion.appPath+'/sysRegion/getSysRegionList.do',
						"data": function ( d ) {
							var form = $("form[name=sysRegionSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize": d.length,
								"regionName":form.find("input[name=regionName]").val(),
								"postCode":form.find("input[name=postCode]").val(),
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
					"order": [[ 1, "desc" ]],
					"columns": [
					    { "title":"","data": "regionId","width":"5px"},
			            { "title":"地区名称","data": "regionName" },
						{ "title":"邮政编码","data": "postCode" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"更新时间","data": "updateTime" },
						{ "title":"是否可用","data": "isAvailable" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#sysRegionTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					initComplete: function () {
						$("#sysRegionTools").removeClass("col-xs-6");
						$(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
						$("#sysRegionTools").append('<button type="button" class="btn-new-type" data-toggle="modal" data-target="#mySysRegionModal">添加</button>');
//						$("#sysUserTools button").addClass('btnDefault');
						$("#sysRegionTools").append('<button type="button" class="btn-new-type sysRegion-batch-del">批量删除</button>');
						$(".sysRegion-batch-del").on("click",function(){
	    					var ids=[];
	        				$("#sysRegionList tbody").find("input:checked").each(function(){
	        					ids.push($(this).val());
	        				});
	        				if(ids.length>0){
	        					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除地区吗？", function(result) {
	        						if(result){
	        							sysRegion.batchRemove(ids);
	        						}
	        					});	
	        				}else{
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要删除的地区！");
	        				}
	        			});	        			
	        			$('#sysRegionList thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#sysRegionList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#sysRegionList tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					},
					"drawCallback": function( settings ) {
						sysRegion.operateColumEvent();
	        	    },
					"columnDefs": [
						{ "targets":[0],
						    //"visible": true,
						    "orderable":false,
						    "render":function (data, type, full, meta){
						             return '<input type="checkbox" name="userId[]" value="' + full.regionId + '">';
						    }
						},
						{
							targets: 6,
							render: function (a, b, c, d) {
								var edit='<span class="glyphicon  sysRegion-operate-edit" data-id="'+c.regionId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;" >修改</span>';
	        					var del='<span class="glyphicon  sysRegion-operate-del" data-id="'+c.regionId+'" data-toggle="tooltip" data-placement="top"  style="text-decoration: underline; cursor: pointer;">删除</span>';
	        					return edit+del;
							}
						},
						{
	        	            "targets": [3,4],
	        	            "render": function(data, type, row, meta) {
	        	            	if(data != null){
	        	            		var now = moment(data); 
	            	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
	        	            	}	
	        	            	return "";
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
        	            }
					]
				});
				$("#myModal").on("shown.bs.modal", function () {
					//$('#myInput').focus()；
				});
			}
	    };
	return sysRegion;
});


