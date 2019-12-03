define([],function() {	
	var redeemCode={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				redeemCode.pageList();
				//查询
				$("#redeemCodeSearchafter").click(function(){
					var form = $("form[name=redeemCodeSearchForm]");
					var availableTime1 =  form.find("input[name=vailableTime1]").val();
					var availableTime2 = form.find("input[name=vailableTime2]").val();
					if(availableTime1!=""&&availableTime2!=""){
						if(new Date(availableTime1)>new Date(availableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "有效日期的开始日期不能大于结束日期！");
						}else{
							redeemCode.pageList();
						}
					}else{
						redeemCode.pageList();
					}
	            });
				$("#closeRedeemCodeView").click(function(){
					closeTab("兑换码详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".redeemCode-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("兑换码详情",redeemCode.appPath+ "/redeemCode/toRedeemCodeView.do?redCode="+$(this).data("id"));
					});
				});
				$(".redeemCode-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("兑换码编辑",redeemCode.appPath+ "/redeemCode/toEditRedeemCode.do?redCode="+$(this).data("id"));
					});
				});
	        	$(".redeemCode-operate-censor").each(function(){
					$(this).on("click",function(){
						addTab("兑换码审核",redeemCode.appPath+ "/redeemCode/toCensorRedeemCode.do?redCode="+$(this).data("id"));
					});
				});
				$(".redeemCode-operate-available").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var isAvailable = $(this).data("isavailable");
						var hint = isAvailable == 1 ? "启用" : "停用";
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"兑换码吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:redeemCode.appPath+"/redeemCode/updateRedeemCode.do",
	    				    			type:"post",
	    				    			data:{
	    				    				redCode:id,
	    				    				isAvailable:isAvailable
	    				    			},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    				var msg=res.msg;
    				    					if(code == "1"){ 
    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
    				    							$("#redeemCodeList").DataTable().ajax.reload(function(){});
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
				$(".redeemCode-operate-delete").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除兑换码吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:redeemCode.appPath+"/redeemCode/updateRedeemCode.do",
	    				    			type:"post",
	    				    			data:{
	    				    				redCode:id,
	    				    				isDeleted:1//删除标识
	    				    			},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    				var msg=res.msg;
					    					if(code == "1"){ 
					    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
					    							$("#redeemCodeList").DataTable().ajax.reload(function(){});
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
			pageList:function () {	
				var redeemCodeTpl = $("#redeemCodeTpl").html();
				// 预编译模板
				var template = Handlebars.compile(redeemCodeTpl);
				$('#redeemCodeList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": redeemCode.appPath+'/redeemCode/pageListRedeemCode.do',
						"data": function ( d ) {
							var form = $("form[name=redeemCodeSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"redName":form.find("input[name=redName]").val(),
								"isAvailable":form.find("select[name=isAvailable]").val(),
								"availableTime1Start":form.find("input[name=availableTime1]").val()+" 00:00:00",
								"availableTime2End":form.find("input[name=availableTime2]").val()+" 23:59:59",
	        					"censorStatus":form.find("select[name=censorStatus]").val(),
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
						{ "title":"名称","data": "redName" },
						{ "title":"兑换码","data": "redCode" },
						{ "title":"有效起始日期","data": "availableTime1" },
						{ "title":"有效结束日期","data": "availableTime2" },
						{ "title":"审核状态","data": "censorStatus" },
						{ "title":"启用状态","data": "isAvailable" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#redeemCodeTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   	$("#redeemCodeTools").html("");
					   $("#redeemCodeTools").removeClass("col-xs-6");
					   $("#redeemCodeTools").append('<button type="button" class="btn-new-type redeemCodeTools-operate-add">新增</button>');
		       			$(".redeemCodeTools-operate-add").on("click",function(){
		       				addTab("新增兑换码", redeemCode.appPath+ "/redeemCode/toAddRedeemCode.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						redeemCode.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2,3],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD'); 
							    	}else{
							    		return "";
							    	}
						    }
						},{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    		if(a==0){
						    			return "未审核";
						    		}else if(a==1){
						    			return "已审核";
						    		}else if(a==2){
						    			return "未通过";
						    		}else{
						    			return "";
						    		}
						    }
						},{
						    "targets": [5],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "启用";
						    		}else{
						    			return "停用";
						    		}
						    }
						},{
							"targets": [6],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon redeemCode-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit=""
								var del=""
	        					var censorStatus="";
								var isAvailable="";
								
								if(c.censorStatus==0){
									edit='<span class="glyphicon redeemCode-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'" data-toggle="tooltip" data-placement="top" >修改</span>';
									del='<span class="glyphicon redeemCode-operate-delete" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'"  data-toggle="tooltip" data-placement="top">删除</span>';
									censorStatus='<span class="glyphicon redeemCode-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'" data-toggle="tooltip" data-placement="top" >审核</span>';
								}
								if(c.censorStatus==1){
									
		        					//如果该行数据可以启用时则标签属性data-isAvailable的值为1（停用），反之为0（启用）。
									if (c.isAvailable==0) {
										isAvailable='<span class="glyphicon redeemCode-operate-available" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'"  data-isAvailable=1 data-toggle="tooltip" data-placement="top">启用</span>';
									}else if(c.isAvailable==1) {
										isAvailable='<span class="glyphicon redeemCode-operate-available" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'" data-isAvailable=0 data-toggle="tooltip" data-placement="top">停用</span>';
									}
								}
								if(c.censorStatus == 2){
									edit='<span class="glyphicon redeemCode-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'" data-toggle="tooltip" data-placement="top" >修改</span>';
									del='<span class="glyphicon redeemCode-operate-delete" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'"  data-toggle="tooltip" data-placement="top">删除</span>';
									censorStatus='<span class="glyphicon redeemCode-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.redCode+'" data-toggle="tooltip" data-placement="top" >审核</span>';
								}
	        					return find+edit+del+censorStatus+isAvailable;
							}
						}
					]
				});
			}
	    };
	return redeemCode;
});


