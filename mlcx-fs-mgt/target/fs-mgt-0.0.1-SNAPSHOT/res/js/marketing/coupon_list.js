define([],function() {	
	var coupon={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				coupon.pageList();
				coupon.handleClickMore();
				//查询
				$("#couponSearchafter").click(function(){
					var form = $("form[name=couponSearchForm]");
					var vailableTime1 =  form.find("input[name=vailableTime1]").val();
					var vailableTime2 = form.find("input[name=vailableTime2]").val();
					if(vailableTime1!=""&&vailableTime2!=""){
						if(new Date(vailableTime1)>new Date(vailableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "发放日期的开始日期不能大于结束日期！");
						}else{
							coupon.pageList();
						}
					}else{
						coupon.pageList();
					}
	            });
				$("#closeCouponView").click(function(){
					closeTab("优惠券详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".coupon-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("优惠券详情",coupon.appPath+ "/coupon/toCouponView.do?couponNo="+$(this).data("id"));
					});
				});
				$(".coupon-operate-available").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var isAvailable = $(this).data("isavailable");
						var hint = isAvailable == 1 ? "启用" : "停用";
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"优惠券吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:coupon.appPath+"/coupon/updateCouponAvailable.do",
	    				    			type:"post",
	    				    			data:{
	    				    				couponNo:id,
	    				    				isAvailable:isAvailable
	    				    			},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    				var msg=res.msg;
    				    					if(code == "1"){ 
    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "停用优惠券操作成功", function() {
    				    							$("#couponList").DataTable().ajax.reload(function(){});
    				    						});
    				    					}else if(msg != null){
    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "停用优惠券操作失败," + msg);
    				    					}else{
    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "停用优惠券操作失败");
    				    					}
    				    				}
    				    			});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var couponTpl = $("#couponTpl").html();
				// 预编译模板
				var template = Handlebars.compile(couponTpl);
				$('#couponList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": coupon.appPath+'/coupon/pageListCoupon.do',
						"data": function ( d ) {
							var form = $("form[name=couponSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"couponNo":$.trim(form.find("input[name=couponNo]").val()),
								"title":$.trim(form.find("input[name=title]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"issueMethod":form.find("select[name=issueMethod]").val(),
								"isAvailable":form.find("select[name=isAvailable]").val(),
								"issueTimeStart":form.find("input[name=issueTimeStart]").val() !="" && form.find("input[name=issueTimeStart]").val() !=null? form.find("input[name=issueTimeStart]").val() +" 00:00:00" : null,
								"issueTimeEnd":form.find("input[name=issueTimeEnd]").val() !="" && form.find("input[name=issueTimeEnd]").val() !=null ? form.find("input[name=issueTimeEnd]").val() +" 23:59:59" : null,
	        					"usedStatus":form.find("select[name=usedStatus]").val(),
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
			            { "title":"优惠券编号","data": "couponNo" },
						{ "title":"会员名称","data": "memberName" },
						{ "title":"手机号码","data": "mobilePhone" },
						{ "title":"方案标题","data": "title" },
						{ "title":"优惠方式","data": "couponMethod" },
						{ "title":"折扣率/直减金额","data": "couponMethod" },
						{ "title":"发放日期","data": "issueTime" },
						{ "title":"发放方式","data": "issueMethod" },
						{ "title":"有效起始日期","data": "vailableTime1" },
						{ "title":"有效结束日期","data": "vailableTime2" },
						{ "title":"有效天数（天）","data": "availableDays" },
						{ "title":"启用状态","data": "isAvailable" },
						{ "title":"使用状态","data": "usedStatus" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#couponTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#couponTools").html("");
					   $("#couponTools").removeClass("col-xs-6");
/*					   $("#couponTools").append('<button type="button" class="btn btn-default btn-sm couponTools-operate-add" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">发放优惠券</button>');
		       			$(".couponTools-operate-add").on("click",function(){
		       				addTab("发放优惠券", coupon.appPath+ "/coupon/toAddCoupon.do");
		       			});	 */
					},
					"drawCallback": function( settings ) {
						coupon.operateColumEvent();
	        	    },
					"columnDefs": [
					  {
					    "targets": [4],
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
					    "targets": [5],
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
						    "targets": [6],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD'); 
							    	}else{
							    		return "";
							    	}
						    }
						},{
						    "targets": [7],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "系统注册";
						    		}else if(a==2){
						    			return "活动奖励";
						    		}else if(a==3){
						    			return "手动发放";
						    		}else{
						    			return "";
						    		}
						    }
						},
						{
						    "targets": [9,8],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD'); 
							    	}else{
							    		return "";
							    	}
						    }
						},{
						    "targets": [11],
						    "render": function(a,b, c, d) {
						    	if(a==1){
					    			return "启用";
					    		}else{
					    			return "停用";
					    		}
						    }
						},{
						    "targets": [12],
						    "render": function(a,b, c, d) {
					    		if(a==1){
					    			return "已使用";
					    		}else{
					    			return "未使用";
					    		}
						    }
						},
						
						{
							"targets": [13],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon coupon-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.couponNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
	        					var isAvailable="";
	        					//如果该行数据可以启用时则标签属性data-isAvailable的值为1（停用），反之为0（启用）。
								if (c.isAvailable==0 && c.usedStatus==0) {
									isAvailable='<span class="glyphicon coupon-operate-available" style="text-decoration: underline; cursor: pointer;"data-id="'+c.couponNo+'"  data-isAvailable=1 data-toggle="tooltip" data-placement="top">启用</span>';
								}else if(c.isAvailable==1 && c.usedStatus==0) {
									isAvailable='<span class="glyphicon coupon-operate-available" style="text-decoration: underline; cursor: pointer;"data-id="'+c.couponNo+'" data-isAvailable=0 data-toggle="tooltip" data-placement="top">停用</span>';
								}
	        					return find+isAvailable;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCouponList").click(function(){
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
	return coupon;
});


