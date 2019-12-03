define([],function() {	
	var cParkLock={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				cParkLock.pageList();
				cParkLock.handleClickMore();
				//查询
				$("#cParkLockSearchafter").click(function(){
					cParkLock.pageList();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
					$(".cParkLock-operate-edit").on("click",function(){		
						var a=$(this).parent().parent().children().eq(1).text();
						addTab("地锁编辑",cParkLock.appPath+ "/cParkLock/tocParkLockEdit.do?parkLockNo="+$(this).data("id")+"&parkingName="+a);			
					});
					$(".cParkLock-operate-del").on("click",function(){
						var parkLockNo = $(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.post("cParkLock/delcParkLock.do",{parkLockNo:parkLockNo},function(res){	
									if(res.code==1){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
											  $(".bootbox").modal("hide");
											  $("#cParkLockList").DataTable().ajax.reload(function(){
					    						}); 
										  });
										
									}else{
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
									}
									
								});
							}						
	       				});
						
					});
	        },
			pageList:function () {	
				var cParkLockTpl = $("#cParkLockTpl").html();
				// 预编译模板
				var template = Handlebars.compile(cParkLockTpl);
				$('#cParkLockList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": cParkLock.appPath+'/cParkLock/pageListcParkLock.do',
						"data": function ( d ) {
							var form = $("form[name=cParkLockSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkingName":$.trim(form.find("input[name=parkingName]").val()),
								"parkLockNo":$.trim(form.find("input[name=parkLockNo]").val()),
								"parkingLockCode":$.trim(form.find("input[name=parkingLockCode]").val()),
								"parkingLockName":$.trim(form.find("input[name=parkingLockName]").val()),
								"parkingLockType":$.trim(form.find("select[name=parkingLockType]").val()),
	        					"parkingLockStatus":form.find("select[name=parkingLockStatus]").val(),
	        					"onlineStatus":form.find("select[name=onlineStatus]").val(),
	        					"pliesNumber":form.find("input[name=pliesNumber]").val(),
	        					"activeCondition":form.find("select[name=activeCondition]").val()
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
						{ "title":"停车场地锁编号","data": "parkLockNo" },
						{ "title":"停车场名称","data": "parkingName" },
						{ "title":"地锁编码","data": "parkingLockCode" },
						{ "title":"地锁名称","data": "parkingLockName"},
						{ "title":"地锁类型","data": "parkingLockType"},
						{ "title":"地锁序列号","data": "parkingLockSerialNumber"},
						{ "title":"升降状态","data": "parkingLockStatus"},
						{ "title":"地锁状态","data": "activeCondition"},
						{ "title":"在线状态","data": "onlineStatus"},
						{ "title":"车位号","data": "spaceNo"},
						{ "title":"楼层数","data": "pliesNumber"},
						{ "title":"创建时间","data": "createTime"},
						{ "title":"修改时间","data": "updateTime"},
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#cParkLocktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#cParkLockTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#cParkLockTools").html("");
		       			$("#cParkLockTools").append('<button type="button" class="btn-new-type cParkLockTools-operate-add">新增</button>');
		       			
		       			$(".cParkLockTools-operate-add").on("click",function(){
		       				addTab("新增地锁", cParkLock.appPath+ "/cParkLock/tocParkLockAdd.do");
		       			});	 
		       			
		       			/**
		       			 * 导出
		       			 */
		       			
					},
					"drawCallback": function( settings ) {
						cParkLock.operateColumEvent();
	        	    },
	        	    "order": [[ 13, "desc" ]],
					"columnDefs": [
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(c.parkingLockType==1){
						        		return "UDP版";
						        	}else if(c.parkingLockType==0){
						        		return "网关版";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [6],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(c.parkingLockStatus==1){
						        		return "降";
						        	}else if(c.parkingLockStatus==0){
						        		return "升";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						{
							"targets": [7],
							"render": function(a,b, c, d) {
								if(a!=null){
									if(c.activeCondition==1){
										return "不可用";
									}else if(c.activeCondition==0){
										return "可用";
									}
								}else{
									return "";
								}
							}
						},
						{
							"targets": [8],
							"render": function(a,b, c, d) {
								if(a!=null){
									if(c.onlineStatus==1){
										return "离线";
									}else if(c.onlineStatus==0){
										return "在线";
									}
								}else{
									return "";
								}
							}
						},
						{
						    "targets": [11],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    	var now = moment(a); 
						    	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
						    "targets": [12],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    	var now = moment(a); 
						    	return now.format('YYYY-MM-DD HH:mm:ss'); 
						    	}else{
						    		return "";
						    	}
						    }
						},
						{
							"targets": [13],
							"render": function (a, b, c, d) {
								var edit='<span class="glyphicon  cParkLock-operate-edit" data-id="'+c.parkLockNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								var del='<span class="glyphicon  cParkLock-operate-del" data-id="'+c.parkLockNo+'" data-toggle="tooltip" data-placement="top">删除</span>';
	        					
	        					return edit+del;
	        					
							}
						}
					]
				});
			},
			formatCurrency :function (s,n) { 
	        	n = n > 0 && n <= 20 ? n : 2;  
	        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	        t = "";  
	        for (i = 0; i < l.length; i++) {  
	            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	        }  
	        return t.split("").reverse().join("") + "." + r;  
	        },
	      //点击更多
			handleClickMore:function(){
				$("#parkLock_moreCparkList").click(function(){
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
	return cParkLock;
});


