define([],function() {	
	var notice={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				notice.pageList();
				//查询
				$("#noticeSearchafter").click(function(){
					var form = $("form[name=noticeSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							notice.pageList();
						}
					}else{
						notice.pageList();
					}
	            });
				$("#closenoticeView").click(function(){
					closeTab("公告详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".notice-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("公告详情",notice.appPath+ "/notice/toNoticeView.do?noticeNo="+$(this).data("id"));
					});
				});
				$(".notice-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("公告编辑",notice.appPath+ "/notice/toUpdateNotice.do?noticeNo="+$(this).data("id"));			
					});
				});
				$(".notice-operate-censor").each(function(id,obj){
					$(this).on("click",function(){
						addTab("公告审核",notice.appPath+ "/notice/toNoticeCensorView.do?noticeNo="+$(this).data("id"));			
					});
				});
				$(".notice-operate-available").each(function(id,obj){
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
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+availableName+"活动吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:notice.appPath+"/notice/delNotice.do",//删除公告
	    				    			type:"post",
	    				    			data:{noticeNo:id,isAvailable:isAvailable},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){ 
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动"+availableName+"操作成功", function() {
	    				    							$("#noticeList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "活动"+availableName+"操作失败");
	    				    					}
	    				    				}
	    				    			});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var noticeTpl = $("#noticeTpl").html();
				// 预编译模板
				var template = Handlebars.compile(noticeTpl);
				$('#noticeList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": notice.appPath+'/notice/pageListNotice.do',
						"data": function ( d ) {
							var form = $("form[name=noticeSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"noticeName":form.find("input[name=noticeName]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
	        					"noticeType":form.find("select[name=noticeType]").val(),
	        					"censorStatus":form.find("select[name=censorStatus]").val(),
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
			            { "title":"公告编号","data": "noticeNo" },
						{ "title":"公告类型","data": "noticeType" },
						{ "title":"公告名称","data": "noticeName" },
						{ "title":"排名","data": "ranking" },
						{ "title":"可用状态","data": "isAvailable" },
						{ "title":"审核状态","data": "censorStatus" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#noticetool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#noticeTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#noticeTools").html("");
					   $("#noticeTools").removeClass("col-xs-6");
		       			$("#noticeTools").append('<button type="button" class="btn-new-type noticeTools-operate-add">新增</button>');
		       			$(".noticeTools-operate-add").on("click",function(){
		       				addTab("新增公告", notice.appPath+ "/notice/toAddNotice.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						notice.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [1],
						    "render": function(a,b, c, d) {
						    	if(a){
						        if(a==1){
						        	return "活动公告";
						        }else{
						        	return "";
						        }
						    	}else{
						    		return "";
						    	}
						    }
						},{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "可用";
						    		}else if(a==0){
						    			return "不可用";
						    		}else{
						    			return "";
						    		}
						    }
						},{
						    "targets": [5],
						    "render": function(a,b, c, d) {//0、未审核，1、已审核，2、待审核，3、未通过
						    		if(a==0){
						    			return "未审核";
						    		}else if(a==1){
						    			return "已审核";
						    		}else if(a==2){
						    			return "待审核";
						    		}else if(a==3){
						    			return "未通过";
						    		}else{
						    			return "";
						    		}
						    }
						},
						{
						    "targets": [6],
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
							"targets": [7],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon notice-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.noticeNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit='<span class="glyphicon notice-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.noticeNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
	        					var censor="";
	        					var available="";
								if(c.censorStatus==0){
									censor='<span class="glyphicon notice-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.noticeNo+'" data-toggle="tooltip" data-placement="top">审核</span>';
								}
								if(c.isAvailable==1){
									available='<span class="glyphicon notice-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.noticeNo+'" data-available="'+c.isAvailable+'" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									available='<span class="glyphicon notice-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.noticeNo+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
								}
	        					return find+edit+censor+available;
							}
						}
					]
				});
			}
	    };
	return notice;
});


