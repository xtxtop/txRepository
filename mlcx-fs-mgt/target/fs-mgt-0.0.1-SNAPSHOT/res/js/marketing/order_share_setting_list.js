define([],function() {	
	var orderShareSetting={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				orderShareSetting.pageList();
				orderShareSetting.handleClickMore();
				//查询
				$("#orderShareSettingSearchafter").click(function(){
					var form = $("form[name=orderShareSettingSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							orderShareSetting.pageList();
						}
					}else{
						orderShareSetting.pageList();
					}
	            });
				$("#closeOrderShareSettingView").click(function(){
					closeTab("订单分享页面配置详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".orderShareSetting-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("订单分享页面配置详情",orderShareSetting.appPath+ "/orderShareSetting/toOrderShareSettingView.do?orderShareSettingNo="+$(this).data("id"));
					});
				});
				$(".orderShareSetting-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("订单分享页面配置编辑",orderShareSetting.appPath+ "/orderShareSetting/toUpdateOrderShareSetting.do?orderShareSettingNo="+$(this).data("id"));			
					});
				});
				$(".orderShareSetting-operate-available").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var isAvailable=$(this).data("available");
						var availableName = "启用";
						if(isAvailable == 1){
							isAvailable=0;
							availableName = "停用";
						}else{
							isAvailable=1;
						}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+availableName+"订单分享页配置吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:orderShareSetting.appPath+"/orderShareSetting/changeOrderShareSettingStatus.do",
	    				    			type:"post",
	    				    			data:{orderShareSettingNo:id,isAvailable:isAvailable},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code = res.code;
	    				    				var msg = res.msg;
    				    					if(code == "1"){ 
    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" +availableName+"操作成功", function() {
    				    							$("#orderShareSettingList").DataTable().ajax.reload(function(){});
    				    						});
    				    					}else{
    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" +availableName+"操作失败，"+msg);
    				    					}
	    				    			}
	    				    		});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var advertTpl = $("#orderShareSettingTpl").html();
				// 预编译模板
				var template = Handlebars.compile(advertTpl);
				$('#orderShareSettingList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderShareSetting.appPath+'/orderShareSetting/pageListOrderShareSetting.do',
						"data": function ( d ) {
							var form = $("form[name=orderShareSettingSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderShareName":form.find("input[name=orderShareName]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
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
			            { "title":"订单分享名称","data": "orderShareName" },
						{ "title":"链接地址","data": "linkUrl" },
						{ "title":"可用状态","data": "isAvailable" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#orderShareSettingTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#orderShareSettingTools").html("");
					   $("#orderShareSettingTools").removeClass("col-xs-6");
		       		   $("#orderShareSettingTools").append('<button type="button" class="btn-new-type orderShareSettingTools-operate-add">新增</button>');
		       		   $(".orderShareSettingTools-operate-add").on("click",function(){
		       				addTab("新增订单分享页配置", orderShareSetting.appPath+ "/orderShareSetting/toAddOrderShareSetting.do");
		       		   });	 
					},
					"drawCallback": function( settings ) {
						orderShareSetting.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "可用";
						    		}else if(a==0){
						    			return "不可用";
						    		}else{
						    			return "";
						    		}
						    }
						},
						{
						    "targets": [3],
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
							"targets": [4],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon orderShareSetting-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.orderShareSettingNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit='<span class="glyphicon orderShareSetting-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.orderShareSettingNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
	        					var available="";
								if(c.isAvailable==1){
									available='<span class="glyphicon orderShareSetting-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.orderShareSettingNo+'" data-available="'+c.isAvailable+'" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									available='<span class="glyphicon orderShareSetting-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.orderShareSettingNo+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
								}
	        					return find+edit+available;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreorderShareSettingSearchafter").click(function(){
					if($(".seach-input-details").hasClass("close-content")){
						$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
						$(".seach-input-details").removeClass("close-content");
					}else{
						$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
						$(".seach-input-details").addClass("close-content");
					}
				})
			}
	    };
	return orderShareSetting;
});


