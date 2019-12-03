define([],function() {	
	var pricingPackOrder={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				pricingPackOrder.pageList();
				pricingPackOrder.handleClickMore();
				//查询
				$("#pricePackOrderSearch").click(function(){
					var form=$("form[name=orderSearchForm]");
					var startTime=form.find("input[name=startTime]").val();
					var endTime=form.find("input[name=endTime]").val();
					if(startTime!=null&&startTime!=""){
						if(endTime!=null&&endTime!=""){
							if(startTime>endTime){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{ 
								pricingPackOrder.pageList();
							}
						}
					}else{
						pricingPackOrder.pageList();
					}
					pricingPackOrder.pageList();
	            });
				//关闭详情
				$("#closePricingPackOrderView").click(function(){
					closeTab("套餐订单详情");
					pricingPackOrder.pageList();
	            });
				
			},
			operateColumEvent : function() {
				$(".pricePacdkOrder-operate-view").each(function() {
					$(this).on("click", function() {
						addTab("套餐订单详情",pricingPackOrder.appPath+ "/pricingPackOrder/toPricingPackOrderView.do?packOrderNo="+$(this).data("id"));
					});
				});

				$(".pricePacdkOrder-operate-over").each(function() {
					
					$(this).on("click", function() {
						var id=$(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要取消吗？", function(result) {
							if(result){
									 $.ajax({
							    			url:pricingPackOrder.appPath+"/pricingPackOrder/pricingPackOrderOver.do",//取消套餐
							    			type:"post",
							    			data:{packOrderNo:id},
							    			dataType:"json",
							    			success:function(res){
							    				var code=res.code;
							    				if (code == "1") {
													bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "取消套餐操作成功！", function() {
														$("#pricePackOrderList").DataTable().ajax.reload(function(){});
													});
												} else {
													bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "取消套餐操作失败！");
												}
							    			}
								}); 
							}
	       				});
					});
				});
				 
			},
			pageList:function () {	
				var pricePackOrderTpl = $("#pricePackOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(pricePackOrderTpl);
				$('#pricePackOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": pricingPackOrder.appPath+'/pricingPackOrder/pageListPricingPackOrder.do',
						"data": function ( d ) {
							var form = $("form[name=pricePackOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberNo":$.trim(form.find("input[name=memberNo]").val()),
								"packOrderNo":$.trim(form.find("input[name=packOrderNo]").val()),
								"packageName":$.trim(form.find("select[name=packageName]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"packageType":form.find("select[name=packageType]").val(),
								"isRecharge":form.find("select[name=isRecharge]").val(),
								"startTime":form.find("input[name=startTime]").val()+" 00:00:00",
								"endTime":form.find("input[name=endTime]").val()+" 23:59:59"
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
			            { "title":"交易号","data": "packOrderNo" },
			            { "title":"客户姓名","data": "memberName" },	
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"套餐编号","data": "packageId" },
						{ "title":"套餐名称","data": "packageName"},
						{ "title":"售价(元)","data": "packAmount" },
//						{ "title":"套餐时长(分钟)","data": "packMinute" },
//						{ "title":"剩余时长(分钟)","data": "residueMinute" },
						{ "title":"套餐金额(元)","data": "packOrderAmount" },
						{ "title":"剩余金额(元)","data": "remainOrderAmount" },
						{ "title":"购买时间","data": "createTime" },
						{ "title":"应付金额(元)","data": "payableAmount" },
						{ "title":"支付状态","data": "payStatus" },	
						{ "title":"支付方式","data": "paymentMethod" },	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#pricePackOrderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						pricingPackOrder.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               {
					            	   "targets": [5,6,7,9],
					            	   "render": function(data, type, row, meta) {
					            		   if(data){
					    	            		return "<span style='color:#2b94fd'>" + pricingPackOrder.formatCurrency(data) + "</span>";
					    	            	}else{
					    	            		return "0";
					    	            	}
					            	   }
					               },
					               
						{
	        	            "targets": [8],
	        	            "render": function(data, type, row, meta) {
	        	            	if(data!=null){
	        	            		var now = moment(data); 
	        	            		return now.format('YYYY-MM-DD HH:mm:ss'); 
	        	            	}else{
	        	            		return "";
	        	            	}
	        	            }
	        	        },
	        	     // 支付状态（0、未支付，1、已支付，2、部分支付-预留）
						{
						    "targets": [10],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						    		if(a==0){
			        					return "未支付";
			        				}else if(a==1){
			        					return "已支付";
			        				}else if(a==2){
			        					return "部分支付";
			        				}
						        }else{
						        	return "";
						        }
						    }
						},
						// 支付方式(1、微信支付2、支付宝3、银行卡转账4、其他)
						{
						    "targets": [11],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						    		if(a==1){
			        					return "支付宝支付";
			        				}else if(a==2){
			        					return "微信支付";
			        				}else if(a==3){
			        					return "银行卡转账";
			        				}else{
			        					return "其他";
			        				}
						        }else{
						        	return "";
						        }
						    }
						},
						{
							"targets": [12],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon pricePacdkOrder-operate-view" data-id="'+c.packOrderNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var over = "";
								if(c.packageType==1&&c.isAvailable==1){//赠送类套餐可以操作套餐无效
									over='<span class="glyphicon pricePacdkOrder-operate-over" data-id="'+c.packOrderNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">取消</span>';
								}
	        					return view+over;
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
					$("#morePricingParkOrderList").click(function(){
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
	return pricingPackOrder;
});


