
define([],function() {
	
    var smsTemplate= {
        appPath:getPath("app"),	
        init: function () {
			$("#smsTemplateSearch").click(function(){
				smsTemplate.pageList();
            });
            smsTemplate.pageList();
            /*smsTemplate.getsmsTemplateLevelList();*/
            $("#smsTemplateModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=smsTemplateForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
        },
        del: function (smsId) {
        	$.ajax({
    			url:smsTemplate.appPath+"/smsTemplate/deleteSmsTemplate.do",
    			type:"post",
    			data:{"emplateId":smsId},
    			dataType:"json",
//    			async:false,
    			success:function(res){ 
    					var msg = res.msg;
    					var code = res.code;
    					var data = res.data;
    					if(code == "1"){ 
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
								$("#smsTemplateModal").modal("hide");
	    						$("#smsTemplate").DataTable().ajax.reload(function(){

	    						}); 
    						});
    					}else{
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！"); 
    					} 
    			}
    		});
    		return false;
    		 
        },
        operateColumEvent: function(){
			$(".smsTemplate-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("编辑短信模板",smsTemplate.appPath+ "/smsTemplate/toSmsTemplateEdit.do?templateId="+$(this).data("id"));
				});
			});
			$(".smsTemplate-operate-del").each(function(id,obj){
				$(obj).on("click",function(){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							smsTemplate.del($(obj).data("id"));
						}						
					}); 					
				});
			});	        	
        },
        pageList:function () {
        	var smsTemplateBtnTpl = $("#smsTemplateBtnTpl").html();
			//预编译模板
			var template = Handlebars.compile(smsTemplateBtnTpl);
			var table = $('#smsTemplate').dataTable( {
				destroy : true,
				searching:false,
        		"ajax": {
        			"type": "POST",
        			"url": smsTemplate.appPath+"/smsTemplate/querySmsTemplate.do",
        			"data": function ( d ) { 
        				var form = $("form[name=smsTemplateSearchForm]");
        			    var templateType=form.find("select[name=templateType]").val();
        				return $.extend( {}, d, {
        					"pageNo": (d.start/d.length)+1,
        					"pageSize":d.length,
        					"templateType":templateType,
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
    				{ "title":"短信模板编号","data": "templateId"},
					{ "title":"短信模板类型","data": "templateType" },
					{ "title":"短信模板内容","data": "templateContent" },
					{ "title":"创建时间","data": "createTime" },
					{ "title":"更新时间","data": "updateTime" }, 
					{ "title":"操作","orderable":false}
        		],
        	   "dom": "<'row'<'#smsTemplatetool'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
        	   initComplete: function () {
        		   $("#smsTemplatetool").html("");
				   $("#smsTemplatetool").removeClass("col-xs-6");
	       			$("#smsTemplatetool").append('<button type="button" class="btn-new-type smsTemplatetool-operate-add">新增</button>');
//	       			$("#sysUserTools button").addClass('btnDefault');
	       			$(".smsTemplatetool-operate-add").on("click",function(){
	       				addTab("新增短信模板", smsTemplate.appPath+ "/smsTemplate/toSmsTemplateAdd.do");
	       			});	
        	   },
				"drawCallback": function( settings ) {
					smsTemplate.operateColumEvent();
        	    },
        		"columnDefs": [{
					"render": function(data, type, row) {
		            	 if(data == 1){
		            		data = "注册";
		            	}else if(data == 2){
		            		data = "修改密码";
		            	}else if(data == 3){
		            		data = "车辆异常信息";
		            	}else if(data == 4){
		            		data = "会员审核";
		            	}else if(data == 5){
		            		data = "运行日报";
		            	}
		                return data ;
		            },
		            "targets": 1
					
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
    	        },
        		    {
						targets: 5,
						render: function (a, b, c, d) {		
							var edit='<span class="glyphicon  smsTemplate-operate-edit" data-id="'+c.templateId+'" style="text-decoration: underline; cursor: pointer;">修改</span>';
				        	var del='<span class="glyphicon  smsTemplate-operate-del" data-id="'+c.templateId+'" style="text-decoration: underline; cursor: pointer;">删除</span>';
							return edit+del;
						}
					}
					
			
        		]
        	} );
			$("#myModal").on("shown.bs.modal", function () {
				//$('#myInput').focus()；
				});
			$('#onOFFDate').daterangepicker();
        }
    };
    return  smsTemplate;
});

