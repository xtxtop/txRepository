define([],function() {	
	var couponPlan={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				couponPlan.pageList();
				couponPlan.handleClickMore();
				
				//查询
				$("#couponPlanSearchafter").click(function(){
					var form = $("form[name=couponPlanSearchForm]");
					var vailableTime1 =  form.find("input[name=vailableTime1]").val();
					var vailableTime2 = form.find("input[name=vailableTime2]").val();
					if(vailableTime1!=""&&vailableTime2!=""){
						if(new Date(vailableTime1)>new Date(vailableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "有效日期的开始日期不能大于结束日期！");
						}else{
							couponPlan.pageList();
						}
					}else{
						couponPlan.pageList();
					}
	            });
				$("#closeCouponPlanView").click(function(){
					closeTab("优惠券方案详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".couponPlan-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("优惠券方案详情",couponPlan.appPath+ "/couponPlan/toCouponPlanView.do?planNo="+$(this).data("id"));
					});
				});
				$(".couponPlan-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("优惠券方案编辑",couponPlan.appPath+ "/couponPlan/toEditCouponPlan.do?planNo="+$(this).data("id"));
					});
				});
	        	$(".couponPlan-operate-censor").each(function(){
					$(this).on("click",function(){
						addTab("优惠券方案审核",couponPlan.appPath+ "/couponPlan/toCensorCouponPlan.do?planNo="+$(this).data("id"));
					});
				});
				$(".couponPlan-operate-available").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var isAvailable = $(this).data("isavailable");
						var hint = isAvailable == 1 ? "启用" : "停用";
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"优惠券吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:couponPlan.appPath+"/couponPlan/updateCouponPlanAvailable.do",
	    				    			type:"post",
	    				    			data:{
	    				    				planNo:id,
	    				    				isAvailable:isAvailable
	    				    			},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    				var msg=res.msg;
    				    					if(code == "1"){ 
    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
    				    							$("#couponPlanList").DataTable().ajax.reload(function(){});
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
				$(".couponPlan-operate-delete").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除优惠券吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:couponPlan.appPath+"/couponPlan/updateOrDelCouponPlan.do",
	    				    			type:"post",
	    				    			data:{
	    				    				planNo:id,
	    				    				isDeleted:1//删除标识
	    				    			},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    				var msg=res.msg;
					    					if(code == "1"){ 
					    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
					    							$("#couponPlanList").DataTable().ajax.reload(function(){});
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
				var couponPlanTpl = $("#couponPlanTpl").html();
				// 预编译模板
				var template = Handlebars.compile(couponPlanTpl);
				$('#couponPlanList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": couponPlan.appPath+'/couponPlan/pageListCouponPlan.do',
						"data": function ( d ) {
							var form = $("form[name=couponPlanSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"planNo":form.find("input[name=planNo]").val(),
								"title":form.find("input[name=title]").val(),
								"isAvailable":form.find("select[name=isAvailable]").val(),
								"availableDays":form.find("input[name=availableDays]").val(),
								"availableTime1Start":form.find("input[name=vailableTime1]").val()+" 00:00:00",
								"availableTime2End":form.find("input[name=vailableTime2]").val()+" 23:59:59",
	        					"couponType":form.find("select[name=couponType]").val(),
	        					"couponType":form.find("select[name=couponType]").val(),
	        					"couponUseType":form.find("select[name=couponUseType]").val(),
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
						//{ "title":"方案编号","data": "planNo" },
						{ "title":"标题","data": "title" },
						{ "title":"优惠类型","data": "couponType" },
						{ "title":"优惠方式","data": "couponMethod" },
						{ "title":"优惠使用类型","data": "couponUseType" },
						{ "title":"折扣率/直减金额","data": "couponMethod" },
						{ "title":"有效起始日期","data": "vailableTime1" },
						{ "title":"有效结束日期","data": "vailableTime2" },
						{ "title":"有效天数（天）","data": "availableDays" },
						{ "title":"审核状态","data": "censorStatus" },
						{ "title":"启用状态","data": "isAvailable" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#couponPlanTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   	$("#couponPlanTools").html("");
					   $("#couponPlanTools").removeClass("col-xs-6");
					   $("#couponPlanTools").append('<button type="button" class="btn-new-type couponPlanTools-operate-add">新增</button>');
		       			$(".couponPlanTools-operate-add").on("click",function(){
		       				addTab("新增优惠券方案", couponPlan.appPath+ "/couponPlan/toAddCouponPlan.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						couponPlan.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [1],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "优惠券";
						    		}else if(a==2){
						    			return "订单分享类优惠券";
						    		}else{
						    			return "";
						    		}
						    }
						},{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "折扣";
						    		}else if(a==2){
						    			return "直减";
						    		}else{
						    			return "";
						    		}
						    }
						},{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "分时";
						    		}else if(a==2){
						    			return "日租";
						    		}else{
						    			return "分时";
						    		}
						    }
						},{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			var discount = c.discount;
						    			if(discount == 0 || discount == 1){
						    				return c.discount+".00"
						    			}
						    			return ""+c.discount;
						    		}else if(a==2){
						    			return ""+c.discountAmount;
						    		}else{
						    			return "";
						    		}
						    }
						},{
						    "targets": [5,6],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD'); 
							    	}else{
							    		return "";
							    	}
						    }
						},{
						    "targets": [8],
						    "render": function(a,b, c, d) {
						    		if(a==2){
						    			return "未通过";
						    		}else if(a==1){
						    			return "已审核";
						    		}else if(a==0){
						    			return "未审核";
						    		}else{
						    			return "";
						    		}
						    }
						},{
						    "targets": [9],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "启用";
						    		}else{
						    			return "停用";
						    		}
						    }
						},{
							"targets": [10],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon couponPlan-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit=""
								var del=""
	        					var censorStatus="";
								var isAvailable="";
								
								if(c.censorStatus==0){
									edit='<span class="glyphicon couponPlan-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
									del='<span class="glyphicon couponPlan-operate-delete" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'"  data-toggle="tooltip" data-placement="top">删除</span>';
									censorStatus='<span class="glyphicon couponPlan-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'" data-toggle="tooltip" data-placement="top" >审核</span>';
								}else if(c.censorStatus==1){
									
		        					//如果该行数据可以启用时则标签属性data-isAvailable的值为1（停用），反之为0（启用）。
									if (c.isAvailable==0) {
										isAvailable='<span class="glyphicon couponPlan-operate-available" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'"  data-isAvailable=1 data-toggle="tooltip" data-placement="top">启用</span>';
									}else if(c.isAvailable==1) {
										isAvailable='<span class="glyphicon couponPlan-operate-available" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'" data-isAvailable=0 data-toggle="tooltip" data-placement="top">停用</span>';
									}
								} else if(c.censorStatus==2){
									edit='<span class="glyphicon couponPlan-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
									del='<span class="glyphicon couponPlan-operate-delete" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'"  data-toggle="tooltip" data-placement="top">删除</span>';
									censorStatus='<span class="glyphicon couponPlan-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.planNo+'" data-toggle="tooltip" data-placement="top" >审核</span>';
								}
	        					return find+edit+del+censorStatus+isAvailable;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCouponPlan").click(function(){
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
	return couponPlan;
});


