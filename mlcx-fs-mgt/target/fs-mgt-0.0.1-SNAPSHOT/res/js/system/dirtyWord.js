
define([],function() {
    var dirtyWord= {
        appPath:getPath("app"),	
        init: function () {
            $("#dirtyWordEditBtn").click(function(){
            	dirtyWord.edit();
            });
			$("#dirtyWordSearch").click(function(){
				dirtyWord.pageList();
            });
			
            dirtyWord.pageList();

            $("#dirtyWordModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=dirtyWordForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
        },
        edit: function () {
    	var form = $("form[name=dirtyWordForm]"); 

		form.ajaxSubmit({
			url:dirtyWord.appPath+"/dirtyWord/editDirtyWord.do",
			type : "post",
			success:function(res){
				var msg = res.msg;
				var code = res.code;
				var data = res.data;
				if(code == "1"){ 	
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg, function() {
					$("#dirtyWordModal").modal("hide");
					$("#dirtyWord").DataTable().ajax.reload(function(){

					}); 
				});
				}else{
					
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商城黑词信息编辑失败！"); 
				}
		},
		beforeSubmit: function(formData, jqForm, options) {//提交表单前数据验证
			var form = jqForm[0];
			/*if(!form.reatSolution.value){
		        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入处理方案（结果）！");
		        return false;
		    }*/
			
		}
	}); 
}, 
        del: function (wordId) {

    		$.ajax({
    			url:dirtyWord.appPath+"/dirtyWord/delDirtyWord.do",
    			type:"post",
    			data:{wordId:wordId},
    			dataType:"json",
//    			async:false,
    			success:function(res){ 
    					var msg = res.msg;
    					var code = res.code;
    					var data = res.data;
    					if(code == "1"){ 
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
								$("#dirtyWordModal").modal("hide");
	    						$("#dirtyWord").DataTable().ajax.reload(function(){

	    						}); 
    						});
    					}else{
    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！"); 
    					} 
    			}
    		});
    		return false;
    		 
        },
        detail: function (id){
        	$.ajax({
        		url: dirtyWord.appPath+"/dirtyWord/getDirtyWord.do?wordId="+id, 
        		success: function(data){
        			if(data){
        				var form = $("form[name=dirtyWordForm]");
        				dirtyWord.loadData(data);
        			}
        		}
        	})
        },
        loadData :function(jsonStr){
        	var obj = eval(jsonStr);
            var key,value,tagName,type,arr;
            var form = $("form[name=dirtyWordForm]");
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
        operateColumEvent: function(){
			$(".dirtyWord-operate-edit").each(function(){
				$(this).on("click",function(){
					$("#dirtyWordModal").modal("show");
					dirtyWord.detail($(this).data("id"));
				});
			});
			$(".dirtyWord-operate-del").each(function(id,obj){
				$(obj).on("click",function(){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							dirtyWord.del($(obj).data("id"));
						}						
					}); 					
				});
			});	        	
        },
        pageList:function () {
        	var dirtyWordBtnTpl = $("#dirtyWordBtnTpl").html();
			//预编译模板
			var template = Handlebars.compile(dirtyWordBtnTpl);
			var table = $('#dirtyWord').dataTable( {
				searching:false,
				destroy: true,
        		"ajax": {
        			"type": "POST",
        			"url": dirtyWord.appPath+"/dirtyWord/queryDirtyWord.do",
        			"data": function ( d ) {     				
        				return $.extend( {}, d, {
        					"pageNo": (d.start/d.length)+1,
        					"pageSize":d.length,
							//"compType":$('#dirtyWordType').val(),
							//"reatStatus":$('#treatStatus').val(),
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
    				{ "title":"","data": "wordId","width":"5px"},
					{ "title":"黑词内容","data": "content" },
					{ "title":"创建时间","data": "createTime" },
					{ "title":"更新时间","data": "updateTime" }, 
					{ "title":"操作","orderable":false}
        		],
        	   "dom": "<'row'<'#dirtyWordtool.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
        	   initComplete: function () {
        		   $(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
				   $("#dirtyWordtool").append('<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#dirtyWordModal">添加黑词记录</button> ');
					$(".dirtyWord-batch-del").on("click",function(){
   					var ids=[];
       				$("#dirtyWord tbody").find("input:checked").each(function(){
       					ids.push($(this).val());
       				});
       				if(ids.length>0){
       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
       						if(result){
       							dirtyWord.batchRemove(ids);
       						}
       					});	
       				}else{
       					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择要删除的信息！");
       				}
       			});   			
       			$('#dirtyWord thead input[type="checkbox"]').on("click",function(e){
       				if(this.checked){
       			         $('#dirtyWord tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
       			      } else {
       			         $('#dirtyWord tbody input[type="checkbox"]:checked').prop("checked",false);
       			      }
       			});
					
        	   },
				"drawCallback": function( settings ) {
					dirtyWord.operateColumEvent();
        	    },
        	    "order": [[ 1, "desc" ]],
        		"columnDefs": [
       						{ "targets":[0], 
    						    "orderable":false,
    						    "render":function (data, type, full, meta){
    						             return '<input type="checkbox" name="sellerId[]" value="' + full.wordId + '">';
    						    }
    						},
        		    {
						targets: 4,
						render: function (a, b, c, d) {		
							var edit='<span class="glyphicon  btn btn-danger  dirtyWord-operate-edit" data-id="'+c.wordId+'" data-toggle="tooltip" data-placement="top" title="添加黑词">添加黑词</span>';
				        	//var del='<span class="glyphicon btn btn-danger dirtyWord-operate-del" data-id="'+c.wordId+'">删除</span>';
				        	
							return edit;
						}
					},{
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
        	} );
			$("#myModal").on("shown.bs.modal", function () {
				//$('#myInput').focus()；
				});
        }
    };
    return  dirtyWord;
});

