define([],function() {	
	var advertMengLong={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				advertMengLong.pageList();
				advertMengLong.handleClickMore();
				//初始类型选择
				selectOptionType_list();
				//查询
				$("#advertMengLongSearchafter").click(function(){
					var form = $("form[name=advertMengLongSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							advertMengLong.pageList();
						}
					}else{
						advertMengLong.pageList();
					}
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".advertMengLong-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("广告详情",advertMengLong.appPath+ "/advertMengLong/toAdvertView.do?advertNo="+$(this).data("id"));
					});
				});
				$(".advertMengLong-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("广告编辑",advertMengLong.appPath+ "/advertMengLong/toUpdateAdvert.do?advertNo="+$(this).data("id"));			
					});
				});
				$(".advertMengLong-operate-censor").each(function(id,obj){
					$(this).on("click",function(){
						addTab("广告审核",advertMengLong.appPath+ "/advertMengLong/toAdvertCensorView.do?advertNo="+$(this).data("id"));			
					});
				});
				$(".advertMengLong-operate-available").each(function(id,obj){
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
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+availableName+"广告吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:advertMengLong.appPath+"/advertMengLong/delAdvert.do",//删除活动
	    				    			type:"post",
	    				    			data:{advertNo:id,isAvailable:isAvailable},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){ 
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告"+availableName+"操作成功", function() {
	    				    							$("#advertMengLongList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告"+availableName+"操作失败");
	    				    					}
	    				    				}
	    				    			});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var advertMengLongTpl = $("#advertMengLongTpl").html();
				// 预编译模板
				var template = Handlebars.compile(advertMengLongTpl);
				$('#advertMengLongList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": advertMengLong.appPath+'/advertMengLong/pageListAdvert.do',
						"data": function ( d ) {
							var form = $("form[name=advertMengLongSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"advertName":form.find("input[name=advertName]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
	        					"type":form.find("select[name=type]").val(),
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
			            { "title":"广告编号","data": "advertNo","defaultContent":"--" },
			            { "title":"广告名称","data": "advertName","defaultContent":"--" },
						{ "title":"广告类型","data": "type","defaultContent":"--" },
						{ "title":"区域类型","data": "advertType","defaultContent":"--" },
						{ "title":"区域展示位置","data": "advertPosition","defaultContent":"--" },
						{ "title":"排名","data": "ranking","defaultContent":"--" },
						{ "title":"可用状态","data": "isAvailable","defaultContent":"--" },
						{ "title":"审核状态","data": "censorStatus","defaultContent":"--" },
						{ "title":"创建时间","data": "createTime","defaultContent":"--" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#advertMengLongTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#advertMengLongTools").html("");
					   $("#advertMengLongTools").removeClass("col-xs-6");
		       			$("#advertMengLongTools").append('<button type="button" class="btn-new-type advertMengLongTools-operate-add">新增</button>');
		       			$(".advertMengLongTools-operate-add").on("click",function(){
		       				addTab("新增广告", advertMengLong.appPath+ "/advertMengLong/toAddAdvert.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						advertMengLong.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    	return initSelectList(a,type_advert);
						    }
						},
						
						{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						    	if(c.type==2){
						    		return initSelectList(a,advertPosition_t);
						    	}else{
						    		return "--";
						    	}
						    }
						},
						
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
							    	if(c.type==1){
							    		return initSelectList(a,advertPosition_o);
							    	}else if(c.type==2){
							    		if(c.advertType==1){
							    			return initSelectList(a,advertPosition_t_o);	
							    		}else if(c.advertType==2){
							    			return initSelectList(a,advertPosition_t_t);	
							    		}else if(c.advertType==3){
							    			return initSelectList(a,advertPosition_t_s);	
							    		}else if(c.advertType==4){
							    			return initSelectList(a,advertPosition_t_f);	
							    		}else if(c.advertType==5){
							    			return initSelectList(a,advertPosition_t_fv);	
							    		}else if(c.advertType==6){
							    			return initSelectList(a,advertPosition_t_sx);	
							    		}
							    	}else if(c.type==3){
							    		return initSelectList(a,advertPosition_s);
							    	}else{
							    		return initSelectList(a,advertPosition_f);;
							    	}
						    	}else{
						    		return "--";
						    	}
						    }
						},
						{
						    "targets": [6],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "可用";
						    		}else if(a==0){
						    			return "不可用";
						    		}else{
						    			return "--";
						    		}
						    }
						},{
						    "targets": [7],
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
						    			return "--";
						    		}
						    }
						},
						{
						    "targets": [8],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
							    	}else{
							    		return "--";
							    	}
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon advertMengLong-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.advertNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit='<span class="glyphicon advertMengLong-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.advertNo+'" data-toggle="tooltip" data-placement="top" >编辑</span>';
	        					var censor="";
	        					var available="";
	        					//（1、可用，0、不可用，默认0）   （0、未审核，1、已审核，2、待审核，3、未通过，默认0）
							
								censor='<span class="glyphicon advertMengLong-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.advertNo+'" data-toggle="tooltip" data-placement="top">审核</span>';
							
								if(c.isAvailable==1){
									available='<span class="glyphicon advertMengLong-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.advertNo+'" data-available="'+c.isAvailable+'" data-toggle="tooltip" data-placement="top">停用</span>';
									return find+available;
								}else if(c.isAvailable==0 && c.censorStatus==1){
									available='<span class="glyphicon advertMengLong-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.advertNo+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
									return find+edit+available;
								}else if(c.isAvailable==0  && c.censorStatus == 0){
									available='<span class="glyphicon advertMengLong-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.advertNo+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
									return find+edit+censor;
								}else if(c.isAvailable==0  && c.censorStatus == 3){
									available='<span class="glyphicon advertMengLong-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.advertNo+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
									return find+edit;
								}
	        					
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreAdvertMengLongSearchafter").on('click',function(){
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
	return advertMengLong;
});

//初始选择
function initSelectList(s,x){
	 for(var i=0;i<x.length;i++){
			if(s==(i+1)){
			return x[i];
			}
		}
}
function selectOptionType_list(){
	$("#advertList_Type").html("");
	var select="<option  value=''>--全部--</option>";
	for(var i=0;i<type_advert.length;i++){
		select+="<option  value='"+(i+1)+"'> "+type_advert[i]+" </option>";
	}
	select+="</select>";
	$("#advertList_Type").append(select)
}