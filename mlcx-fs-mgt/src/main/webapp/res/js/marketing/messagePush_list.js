define([],function() {	
	var messagePush={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				messagePush.pageList();
				messagePush.handleClickMore();
				//查询
				$("#messagePushSearchafter").click(function(){
					var form = $("form[name=messagePushSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							messagePush.pageList();
						}
					}else{
						messagePush.pageList();
					}
	            });
				$("#closeMessagePushView").click(function(){
					closeTab("消息推送详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".messagePush-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("活动详情",messagePush.appPath+ "/messagePush/toMessagePushView.do?id="+$(this).data("id"));
					});
				});
				$(".messagePush-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("消息推送编辑",messagePush.appPath+ "/messagePush/toUpdateMessagePush.do?id="+$(this).data("id"));			
					});
				});
				$(".messagePush-operate-push").each(function(id,obj){
					$(this).on("click",function(){
						var id = $(this).data("id");
						var title = $(this).data("title");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要推送"+title+"消息吗？", function(result) {
							if(result){
								$.ajax({
					    			url:messagePush.appPath+"/messagePush/pushMessage.do",//消息推送
					    			type:"post",
					    			data:{id:id},
					    			dataType:"json",
					    			success:function(res){
					    				var code=res.code;
				    					if(code == "1"){ 
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" +title+"推送成功", function() {
				    							$("#messagePushList").DataTable().ajax.reload(function(){});
				    						});
				    					}else{
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + title+"推送失败");
				    					}
				    				}
				    			});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var messagePushTpl = $("#messagePushTpl").html();
				// 预编译模板
				var template = Handlebars.compile(messagePushTpl);
				$('#messagePushList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": messagePush.appPath+'/messagePush/pageListMessagePush.do',
						"data": function ( d ) {
							var form = $("form[name=messagePushSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"title":form.find("input[name=title]").val(),
								"memberName":form.find("input[name=memberName]").val(),
								"pushTimeStart":form.find("input[name=pushTimeStart]").val()+" 00:00:00",
								"pushTimeEnd":form.find("input[name=pushTimeEnd]").val()+" 23:59:59",
	        					"pushStatus":form.find("select[name=pushStatus]").val(),
	        					"pushPattern":form.find("select[name=pushPattern]").val(),
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
			            { "title":"标题","data": "title" },
						{ "title":"内容","data": "content",width:"25%"},
						{ "title":"推送方式","data": "pushPattern" },
						{ "title":"推送会员","data": "memberName" },
						{ "title":"推送状态","data": "pushStatus" },
						{ "title":"推送日期","data": "pushTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#messagePushtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#messagePushTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#messagePushTools").html("");
					   $("#messagePushTools").removeClass("col-xs-6");
		       			$("#messagePushTools").append('<button type="button" class="btn-new-type messagePushTools-operate-add">新增</button>');
		       			$(".messagePushTools-operate-add").on("click",function(){
		       				addTab("新增消息推送", messagePush.appPath+ "/messagePush/toAddMessagePush.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						messagePush.operateColumEvent();
	        	    },
					"columnDefs": [
						{
							"targets": [0],
							"render": function(a,b, c, d) {
								if(a != null){
						    		if (a.length > 10){
						    			var text = "<div style='white-space:nowrap;text-overflow:ellipsis;overflow:hidden;' title='"+ a +"'>"+ a.substring(0,10) + "......</div>" 
						    			return text;
						    		}
						    		return a;
						    	 }
						        return "";
							}
						},{
							"targets": [1],
					    	"render": function(a,b, c, d) {
					    		if(a != null){
						    		if (a.length > 20){
						    			var text = "<div style='white-space:nowrap;text-overflow:ellipsis;overflow:hidden;' title='"+ a +"'>"+ a.substring(0,20) + "......</div>" 
						    			return text;
						    		}
						    		return a;
						    	 }
						        return "";
							}
						},{
							"targets": [2],
					    	"render": function(a,b, c, d) {
				    			if(c.pushPattern==1){
				    				return "多个用户";
				    			}else if(c.pushPattern==2){
				    				return "全部用户";
				    			}
					    		return "";
							}
						},{
							"targets": [3],
					    	"render": function(a,b, c, d) {
					    		if(a != null){
					    			var array = a.split(",");
						    		if (array.length > 4){
						    			var text = array[0]+","+array[1]+","+array[2]+","+array[3];
						    			var text = "<div style='white-space:nowrap;text-overflow:ellipsis;overflow:hidden;' title='"+ a +"'>"+ text +"......</div>" 
						    			return text;
						    		}
						    		return a;
						    	 }
						        return "";
							}
						},{
						    "targets": [4],
						    "render": function(a,b, c, d) {
				    			if(c.pushStatus==0){
				    				return "未推送";
				    			}else if(c.pushStatus==1){
				    				return "已推送";
				    			}
					    		return "";
						    }
						},
						{
						    "targets": [5],
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
							"targets": [6],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon messagePush-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.id+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit = "";
	        					var push = "";
								if(c.pushStatus==0){
									edit='<span class="glyphicon messagePush-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.id+'" data-toggle="tooltip" data-placement="top" >修改</span>';
									push='<span class="glyphicon messagePush-operate-push" style="text-decoration: underline; cursor: pointer;"data-id="'+c.id+'" data-title="'+c.title+'" data-toggle="tooltip" data-placement="top">推送</span>';
								}
	        					return find + edit + push;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moremessagePushList").click(function(){
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
	return messagePush;
});


