define([],function() {	
	var carRedPacketList={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				carRedPacketList.pageList();
				//查询
				$("#carRedPacketListSearchafter").click(function(){
					var form = $("form[name=carRedPacketListSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							carRedPacketList.pageList();
						}
					}else{
						carRedPacketList.pageList();
					}
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
//	        	$(".carRedPacketList-operate-find").each(function(){
//					$(this).on("click",function(){
//						addTab("红包详情",carRedPacketList.appPath+ "/carRedPacketList/tocarRedPacketListView.do?carRedPacketListNo="+$(this).data("id"));
//					});
//				});
				$(".carRedPacket-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("红包车编辑",carRedPacketList.appPath+ "/carRedPacket/toCarRedPacketEdit.do?carRedPacketId="+$(this).data("id"));			
					});
				});
				//启用停用
				$(".carRedPacket-operate-available").each(function(id,obj){
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
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+availableName+"红包吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:carRedPacketList.appPath+"/carRedPacket/carRedPacketIsAvailable.do",//启用或停用 红包
	    				    			type:"post",
	    				    			data:{carRedPacketId:id,isAvailable:isAvailable},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){ 
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "红包"+availableName+"操作成功", function() {
	    				    							$("#carRedPacketList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
	    				    					}
	    				    				}
	    				    			});
							}						
	       				});
					});
				});
				
			

	        },
			pageList:function () {	
				var carRedPacketListTpl = $("#carRedPacketListTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carRedPacketListTpl);
				$('#carRedPacketList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carRedPacketList.appPath+'/carRedPacket/pageListCarIdleStatus.do',
						"data": function ( d ) {
							var form = $("form[name=carRedPacketListSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":form.find("input[name=carPlateNo]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
//	        					"carRedPacketListType":form.find("select[name=carRedPacketListType]").val(),
//	        					"censorStatus":form.find("select[name=censorStatus]").val(),
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
			            { "title":"车牌号","data": "carPlateNo" },
						{ "title":"红包金额","data": "redPacketAmount" },
						{ "title":"是否限制订单金额满","data": "isOrderAmountLimit" },
						{ "title":"红包下发状态","data": "redPacketSendStatus" },
						{ "title":"红包车状态","data": "carRedPacketStatus" },
						{ "title":"红包设定人姓名","data": "redPacketOperatorName" },
						{ "title":"是否需要充电","data": "isCharge" },
						{ "title":"订单金额最小值","data": "orderAmountLimit" },
						{ "title":"启用状态状态","data": "isAvailable" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"修改时间","data": "updateTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#carRedPacketListtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carRedPacketListTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carRedPacketListTools").html("");
					   $("#carRedPacketListTools").css("float", "right");
					   $("#carRedPacketListTools").removeClass("col-xs-6");
		       			
		       				 
					},
					"drawCallback": function( settings ) {
						carRedPacketList.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						        if(a==1){
						        	return "是";
						        }else if(a==0){
						        	return "否";
						        }else{
						    		return "";
						    	}
						    }  	
						    
						},
						{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "已下发";
						    		}else if(a==0){
						    			return "未下发";
						    		}else{
						    			return "";
						    		}
						    }
						},
						//红包车状态(1.已生效2.进行中3.已完结)
						{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "已生效";
						    		}else if(a==2){
						    			return "进行中";
						    		}else if(a==3){
						    			return "已完结";
						    		}else if(a==0){
						    			return "未生效";
						    		}	
						    }
						},
						{
						    "targets": [6],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "是";
						    		}else if(a==0){
						    			return "否";
						    		}else{
						    			return "";
						    		}
						    }
						},
						{
						    "targets": [8],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "启用";
						    		}else if(a==0){
						    			return "停用";
						    		}else{
						    			return "";
						    		}
						    }
						},
					
						{
						    "targets": [9,10],
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
							"targets": [11],
							"render": function (a, b, c, d) {
								var available="";
								var edit="";
								if(c.isAvailable==1){
									available='<span class="glyphicon carRedPacket-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.carRedPacketId+'" data-available="'+c.isAvailable+'" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									available='<span class="glyphicon carRedPacket-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.carRedPacketId+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
									edit='<span class="glyphicon carRedPacket-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.carRedPacketId+'" data-toggle="tooltip" data-placement="top" >修改</span>';
								}
								
								 if(c.carRedPacketStatus==3||c.carRedPacketStatus==2){
									return "无";
								 }else{
									 return available+edit;
								 }
								
	        					
							}
						}
					]
				});
			},
			
	    };
	return carRedPacketList;
});


