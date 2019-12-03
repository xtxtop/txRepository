define([],function() {	
	var carOneLine={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				carOneLine.pageList();
				//查询
				$("#carOnlineSearchafter").click(function(){
					var form = $("form[name=carOnlineSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "提交开始时间不能大于结束时间！");
						}else{
							carOneLine.pageList();
						}
					}else{
						carOneLine.pageList();
					}
					carOneLine.pageList();
	            });
			},
				  
					 
			//方法加载
	        operateColumEvent: function(){
//	        	$(".car-operate-find").each(function(){
//					$(this).on("click",function(){
//						addTab("车辆详情",car.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
//					});
//				});
				
	        },
			pageList:function () {	
				var carOnlineTpl = $("#carOnlineTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carOnlineTpl);
				$('#carOneLineList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carOneLine.appPath+'/car/pageListCarOnline.do',
						"data": function ( d ) {
							var form = $("form[name=carOnlineSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carNo":form.find("input[name=carNo]").val(),
	        					"opType":form.find("select[name=opType]").val(),
	        					"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
	        					"userName":form.find("input[name=userName]").val(),
	        					"createTimeStart":form.find("input[name=createTimeStart]").val(),
	        					"createTimeEnd":form.find("input[name=createTimeEnd]").val() ,
	        					"updownWhy":form.find("#updownWhy").val() 
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
			            //{ "title":"车辆编号","data": "carNo" },
						{ "title":"车牌号","data": "carPlateNo" },
						{ "title":"操作类型","data": "opType" },
						{ "title":"操作时间","data": "createTime" },
						//{ "title":"理由","data": "memo" },
						{ "title":"备注","data": "memo" },
						{ "title":"上下线理由","data": "updownWhy" },
						{ "title":"操作人","data": "userName" },
						
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#cartool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
//					   $("#carTools").html("");
//		       			$("#carTools").append('<button type="button" class="btn btn-default btn-sm carTools-operate-add">新增</button>');
//		       			$("#carTools").append('<button type="button" class="btn btn-default btn-sm carTools-operate-import">导入</button>');
//		       			$(".carTools-operate-add").on("click",function(){
//		       				addTab("新增车辆", car.appPath+ "/car/toCarAdd.do");
//		       			});	 
//		       			$("#carTools").append('<button type="button" class="btn btn-default btn-sm carTools-operate-export">导出模板</button>');
		       			/**
		       			 * 导出
		       			 */
//		       			$(".carTools-operate-export").on("click",function(){
//		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出模板吗？", function(result) {
//								if(result){
//									window.location.href = car.appPath+ "/downloadExcelFile.do?filepath=car&newFileName=car.xls";;
//								}						
//		       				});
//		       			});
//		       			$(".carTools-operate-import").on("click",function(){
//		       				bootbox.dialog({
//		       			        title: "导入",
//		       			        dataType: "json",
//		       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='exportForm' name='exportForms' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择要导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
//		       			        buttons: {
//		       			            "success": {
//		       			                "label": "<i class='icon-ok'></i> 保存",
//		       			                "className": "	btn-sm btn-success",
//		       			                "callback": function() {
//		       			                    var exportForm = $("form[name='exportForms']");
//		       			                    exportForm.ajaxSubmit({
//		       			                        url: car.appPath+ "/car/expotCarAdd.do",
//		       			                        type: "post",
//		       			                        success: function(res) {
//		       			                        	if (res.code != 1) {
//		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
//		       			                        	} else {
//		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！"); 
//		       			                        	}
//		       			                            $("#carList").DataTable().ajax.reload(function(){
//		       			                            	
//		       			            				});
//		       			                        }, 
//		       			                        error: function(res) {
//		       			                        	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据异常，请检查数据完整性！"); 
//		       			                        }
//		       			                    });
//		       			                }
//		       			            },
//		       			            "cancel": {
//		       			                "label": "<i class='icon-info'></i> 取消",
//		       			                "className": "btn-sm btn-danger"
//		       			            }
//		       			            
//		       			        }
//		       			    })
//		       			});
					},
					"drawCallback": function( settings ) {
						carOneLine.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               {
				        	            "targets": [0],
				        	            "render": function(data, type, row, meta) {
				        	            	if(data){
					        	            	return data;
				        	            	}else{
				        	            		return "";
				        	            	}
				        	            	 
				        	            }
				        	        },
				        	        
						{
	        	            "targets": [2],
	        	            "render": function(data, type, row, meta) {
	        	            	if(data){
	        	            		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss');
	        	            	}else{
	        	            		return "";
	        	            	}
	        	            	 
	        	            }
	        	        },
						{
						    "targets": [1],
						    "render": function(data, type, row, meta) {
						        if(data==0){
						        	return "下线";
						        }else{
						        	return "上线";
						        }
						    }
						},
						{
			            	   "targets": [3],
			            	   "render": function(data,type,row,meta) {
			            		   if(data!=null){
			            			   if(data.length>20){
			            				   return data.substr(0,10)+"..."; 
			            			   }else{
			            				   return data;
			            			   }
			            			   
			            		   }else{
			            			   return "";
			            		   }
			            	   }
			               }
					]
				});
			}
	    };
	return carOneLine;
});


