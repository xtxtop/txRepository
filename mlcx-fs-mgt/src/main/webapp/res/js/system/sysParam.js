define([],function() {	
	var sysParam={
			appPath:getPath("app"),	
			init: function () {				
				$("#sysParamEditBtn").click(function(){
					sysParam.addOrEdit();
	            });	
				$("#sysParamSearchafter").click(function(){
					sysParam.pageList();
	            });
				//清除（重置）
				$("#sysParamResetBtn").click(function(){
					sysParam.reset();
		        });				
	            //数据列表
				sysParam.pageList();
				sysParam.initSearchStyle();
				$("#mySysParamModal").on("hidden.bs.modal", function() {  
	                //$(this).removeData("bs.modal");  
	            	var form = $("form[name=sysParamForm]");
	            	form.clearForm();
	            	form.resetForm();
	            	form.find("input[type=hidden]").val("");
		         });
			},		
	        addOrEdit: function (sysParamId) {	        	
	        	var form = $("form[name=sysParamForm]");
	        	var paramName = form.find("input[name=paramName]").val();
	        	var paramKey = form.find("input[name=paramKey]").val();
	        	var paramValue = form.find("input[name=paramValue]").val();
	        	var memo = form.find("input[name=memo]").val();
	        	
	        	if(paramName==""){
	        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数名称！");
			        return false;
	        	}
	        	
	        	if(paramKey==""){
	        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数键！");
			        return false;
	        	}
	        	
	        	if(paramValue==""){
	        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数值！");
			        return false;
	        	}
	        	if(memo==""){
	        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数描述！");
			        return false;
	        	}
	        	
				form.ajaxSubmit({
					url : sysParam.appPath+"/sysParam/addOrEditSysParam.do",
					type : "post",
					success : function(data) {
						if (data.code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#mySysParamModal").modal("hide");
	    						$("#sysParamList").DataTable().ajax.reload(function(){
	    							
	    						});
							});							
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
						}
					},
					beforeSubmit: function(formData, jqForm, options) {/*
						var form = jqForm[0];
						var mm=0;
						if(!form.paramName.value){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数名称！");
					        return false;
					    }else{
					    	 $.post(sysParam.appPath+"/sysParam/getByParamName.do",{paramName:form.paramName.value},function(res)
						{
					    		if(res.code=="1"){
					    			bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "参数名称已存在，请重新输入！");
					    			mm=1;
					    			return false;
					    		}
					    		
					    		
						});
					    	 
					    	 if(mm==1){
					    			return false;
					    		}
					    }

						
			    			
						if(!form.paramKey.value){							
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数键！");
						        return false;
						    }
							if(!form.paramValue.value){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数值！");
						        return false;
						    }						
						    if(!form.isConfigurable.value){
						    	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择是否可配置！");
						        return false;
						    }
			    		
					   
						
						
					*/}
				}); 
	        },
	        del: function (sysParamId) {

	    		$.ajax({
	    			url:sysParam.appPath+"/sysParam/deleteSysParam.do",
	    			type:"post",
	    			data:{sysParamId:sysParamId},
	    			dataType:"json",
//	    			async:false,
	    			success:function(result){ 
	    					var msg = result.msg;
	    					var code = result.code;
	    					var data = result.data;
	    					if(code == "1"){
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
									$("#mySysParamModal").modal("hide");
		    						$("#sysParamList").DataTable().ajax.reload(function(){
		    							
		    						}); 
	    						});
	    					}else{
	    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！");	    						
	    					} 
	    			}
	    		});
	    		return false;
	        },
	        reset: function(){
	        	var form = $("form[name=sysParamForm]");
	        	//form.clearForm();
	        	
	        	if(form.find("input[name=paramId]").val()){
	        		form.resetForm();
	        		//sysParam.detail(form.find("input[name=paramId]").val());
	        	}else{
	        		form.resetForm();
	            	form.find("input[type=hidden]").val("");        		
	        	}
	        },
	        operateColumEvent: function(){
				$(".sysParam-operate-edit").each(function(){
					$(this).on("click",function(){
						$("#mySysParamModal").modal("show");
						sysParam.detail($(this).data("id"));
					});
				});
				$(".sysParam-operate-del").each(function(id,obj){
					$(obj).on("click",function(){
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统参数吗？", function(result) {
							if(result){								
								sysParam.del($(obj).data("id"));
							}						
						}); 					
					});
				});	    
				$("#sysParamList").find('[data-toggle="tooltip"]').tooltip();
				$('#sysParamList tbody input[type="checkbox"]').on("click",function(e){
					sysParam.updateDataTableSelectAll();
				});
				sysParam.updateDataTableSelectAll();
	        },
	        detail: function (id){ 
             	$.ajax({
             		url: sysParam.appPath+"/sysParam/detail.do?paramId="+id, 
             		success: function(data){
             			if(data){
             				var form = $("form[name=sysParamForm]");
             				sysParam.loadData(data);
             			}
             		}
             	});
             },
	        loadData:function(jsonStr){
	            var obj = eval(jsonStr);
	            var key,value,tagName,type,arr;
	            var form = $("form[name=sysParamForm]");
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
	            }
	        },
	        batchRemove: function (ids) {
	        	$.ajax({
	        		url: sysParam.appPath+"/sysParam/batchDelete.do?sysParamIds="+ids, 
	        		success: function(data){
	        			if(data.code=1){
	        				$("#sysParamList").DataTable().ajax.reload(function(){
	        					sysParam.updateDataTableSelectAll();
	        				});
	        			}
	        		}
	        	});        	
	        },
	        updateDataTableSelectAll:function(){
	        	   var $chkbox_all        = $('#sysParamList tbody input[type="checkbox"]');
	        	   var $chkbox_checked    = $('#sysParamList tbody input[type="checkbox"]:checked');
	        	   var chkbox_select_all  = $('#sysParamList thead input[type="checkbox"]');
	        	   if($chkbox_checked.length === 0){
				         $('#sysParamList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   } else if ($chkbox_checked.length === $chkbox_all.length){
				         $('#sysParamList thead input[type="checkbox"]:not(:checked)').prop("checked",true);   	     
	        	   } else {
				         $('#sysParamList thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   }
	        	},
			pageList:function () {	
				var tpl = $("#sysParamTpl").html();
				//预编译模板
				var template = Handlebars.compile(tpl);
				var table = $('#sysParamList').dataTable({
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": sysParam.appPath+'/sysParam/getSysParamList.do',
						"data": function ( d ) {
							var form = $("form[name=sysParamSearchForm]");
							debugger
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"paramName":form.find("input[name=paramName]").val(),
								"paramKey":form.find("input[name=paramKey]").val(),
	        					"isConfigurable":form.find("select[name=isConfigurable]").val(),
	        					"isDeleted":0								
							} );
						},
						"dataSrc": function ( json ) {
							debugger
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},					
					"columns": [
					    { "title":"paramId","data": "paramId"},
			            { "title":"参数名称","data": "paramName" },
						{ "title":"参数键","data": "paramKey" },
						{ "title":"参数值","data": "paramValue" },
						{ "title":"备注","data": "memo" },
						{ "title":"是否可配置","data": "isConfigurable" },
						{ "title":"操作","orderable":false}
					],
				   //"dom": "<'row'<'col-xs-2'l><'#sysParamtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
					 "dom": "<'row'<'#sysParamTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					initComplete: function () {
						$("#sysParamTools").removeClass("col-xs-6");
						$(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
						
						$("#sysParamTools").append('<button type="button" class="btn-new-type" data-toggle="modal" data-target="#mySysParamModal">添加</button>');
						$("#sysParamTools button").addClass('btnDefault');
						$("#sysParamTools").append('<button type="button" class="btn-new-type sysParam-batch-del">批量删除</button>');
	        			$(".sysParam-batch-del").on("click",function(){
	    					var ids=[];
	        				$("#sysParamList tbody").find("input:checked").each(function(){
	        					ids.push($(this).val());
	        				});
	        				if(ids.length>0){
	        					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除系统参数吗？", function(result) {
	        						if(result){
	        							sysParam.batchRemove(ids);
	        						}
	        					});	
	        				}else{
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要删除的系统参数！");
	        				}
	        			});	        			
	        			$('#sysParamList thead input[type="checkbox"]').on("click",function(e){
	        				if(this.checked){
	        			         $('#sysParamList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
	        			      } else {
	        			         $('#sysParamList tbody input[type="checkbox"]:checked').prop("checked",false);
	        			      }
	        			});
					},
					"drawCallback": function( settings ) {
						sysParam.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
		                { "targets":[0],
		        		      //"visible": true,
		        		      "orderable":false,
		        		      "render":function (data, type, full, meta){
		        		               return '<input type="checkbox" name="paramId[]" value="' + $('<div/>').text(data).html() + '">';
		        		      }
		        		},
						{
							targets: 6,
							render: function (a, b, c, d) {								
								var edit='<span class="glyphicon sysParam-operate-edit" data-id="'+c.paramId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;" >修改</span>';
	        					var del='<span class="glyphicon sysParam-operate-del" data-id="'+c.paramId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;" >删除</span>';
	        					return edit+del;
							}
						},
						{
	        	            "targets": [5],
	        	            "render": function(data, type, row, meta) {
	        	                if(data==1){
	        	                	return "是";
	        	                }else{
	        	                	return "否";
	        	                }
	        	            }
	        	        } 
					]
				});
				$("#myModal").on("shown.bs.modal", function () {
					//$('#myInput').focus()；
				});
			},
			initSearchStyle:function(){
				var itemLength = $(".seach-input-details>.seach-input-item").length;
				if(itemLength<=3){
					$(".seach-input-details").css({
						"margin-top":"24px",
						"height":"auto",
						"position":"relative",
						"z-index":"1",
						"margin-right":"4.5% !important",
						"margin-bottom":"10px"
							
					});
					$(".seach-input-details>.seach-input-item").css({
						"width":"25%"
					});
					$(".seacher-button-content").css({
						"z-index":"0",
					    "float": "right",
					    "margin-top": "-15px"
					})
				}
			}
	    };
	return sysParam;
});



