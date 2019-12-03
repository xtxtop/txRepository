define([],function() {	
	var parkingOrder={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				parkingOrder.pageList();				
				//查询
				$("#parkingOrderSearch").click(function(){
					parkingOrder.pageList();
	            });
				$("#closeparkingOrderView").click(function(){
					closeTab("停车订单详情");
	            });
				//更多
				parkingOrder.handleClickMore();
				//结束地锁提交
				$("#parkingOrderBtn").click(function(){
					parkingOrder.offparkingOrder();
	            });
				//结束地锁取消
				$("#parkingOrderCancel").click(function(){
					$("#parkingOrderModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".parkingOrder-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("停车订单详情",parkingOrder.appPath+ "/parkingOrder/ParkOrderView.do?parkOrderNo="+$(this).data("id"));
					});
				});
	        	$(".parkingOrder-operate-edit").each(function(){
	        		$(this).on("click",function(){
	        			var form = $("form[name=parkingOrderForm]");
						var parkOrderNo=$(this).data("id");
						 $("#parkingOrderModal").modal("show");
		            	    form.find("input[name='parkOrderNo']").val(parkOrderNo);
							$("#offparkingOrderMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将订单【"+parkOrderNo+"】结束吗？");
	        		});
	        	});
	        },
	 	   //结束操作
			offparkingOrder: function () {
	        	var form = $("form[name=parkingOrderForm]");
				form.ajaxSubmit({
					url:parkingOrder.appPath+"/parkingOrder/disableparkingOrder.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
						  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
							  form.find("textarea[name='finishReason']").html("")
							  $("#offparkingOrderMemo").text("");
							  $("#parkingOrderModal").modal("hide");
							  $(".bootbox").modal("hide");
							  $("#parkingOrderList").DataTable().ajax.reload(function(){}); 
						  });
						  
						}else{
							$("#parkingOrderModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
							$("#offparkingOrderMemo").text("");
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						if (form.find("textarea[name='finishReason']").val()=="") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入原因！");
							return false;
						}
					}
					});
				},
			pageList:function () {	
				var parkingOrderBtnTpl = $("#parkingOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkingOrderBtnTpl);
				$('#parkingOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkingOrder.appPath+'/parkingOrder/toParkOrderList.do',
						"data": function ( d ) {
							var form = $("form[name=parkingOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkOrderNo":$.trim(form.find("input[name=parkOrderNo]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"orderStatus":$.trim(form.find("select[name=orderStatus]").val()),
								"parkingNo":$.trim(form.find("select[name=parkingNo]").val()),
								"payStatus":$.trim(form.find("select[name=payStatus]").val()),
								"payType":$.trim(form.find("select[name=payType]").val()),
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							//alert(JSON.stringify(json.data));
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			            { "title":"订单编号","data": "parkOrderNo" ,"defaultContent":"--"},
						{ "title":"停车场名称","data": "parkingName" ,"defaultContent":"--"},
						{ "title":"会员名称","data": "memberName" ,"defaultContent":"--"},
						{ "title":"手机号","data": "mobilePhone","defaultContent":"--"},
						{ "title":"预约时间","data": "appointmentTime","defaultContent":"--"},
						{ "title":"停车开始时间","data": "entryTime","defaultContent":"--"},
						{ "title":"停车结束时间","data": "departureTime","defaultContent":"--"},
						{ "title":"总时长(分钟)","data": "totalTime","defaultContent":"--"},
						{ "title":"总金额(元)","data": "totalMoney","defaultContent":"--"},
						{ "title":"应付金额(元)","data": "nopayAmount","defaultContent":"--"},
						{ "title":"订单状态","data": "orderStatus","defaultContent":"--"},
						{ "title":"支付状态","data": "payStatus","defaultContent":"--"},
						{ "title":"支付方式","data": "payType","defaultContent":"--"},
						{ "title":"停车场类型","data": "parkType","defaultContent":"--"},
						{ "title":"结束类型","data": "finishType","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#parkingOrderTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					},
					"drawCallback": function( settings ) {
						parkingOrder.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets" : [4,5,6],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD HH:mm:ss');
								}else{
									return "--";
								}
							}
						},  
						{
							"targets" : [10],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									if(c.orderStatus==0){
										return "进行中";
									}else if(c.orderStatus==1){
										return "待支付";
										
									}else if(c.orderStatus==2){
										return "待评价";
										
									}else{
										return "已完成";
									}
								}else{
									return "--";
								}
							}
						},  
						{
							"targets" : [11],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									if(c.payStatus==0){
										return "未支付";
									}else{
										return "已支付";
									}
								}else{
									return "--";
								}
							}
						},  
						{
							"targets" : [12],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									if(c.payType==0){
										return "支付宝";
									}else{
										return "微信";
									}
								}else{
									return "--";
								}
							}
						},  
						{
							"targets" : [14],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									if(c.finishType==0){
										return "正常结束";
									}else{
										return "后台结束";
									}
								}else{
									return "--";
								}
							}
						},  
						{
							"targets" : [13],
							"render" : function(a,
									b, c, d) {
								if(a!=null){//0.闸机 1.地锁,2无设备
									if(a==0){
										return "闸机";
									}else if(a==1){
										return "地锁";
									}else{
										return "无设备";
									}
								}else{
									return "--";
								}
							}
						},  
						{
							"targets": [15],
							"render": function (a, b, c, d) {
								var edit='';
								var view='<span class="glyphicon parkingOrder-operate-view" data-id="'+c.parkOrderNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                               if(c.orderStatus==0){
                            	   edit='<span class="glyphicon parkingOrder-operate-edit" data-id="'+c.parkOrderNo+'"  data-toggle="tooltip" data-placement="top">结束订单</span>';
                               }
								
								return view+edit;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#OrderList").click(function(){
					if($(".seach-input-details").hasClass("close-content")){
						$(this).html("<div class='pull-right moreButtonNew' id='parkingOrderStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
						$(".seach-input-details").removeClass("close-content");
					}else{
						$(this).html("<div class='pull-right moreButtonNew' id='parkingOrderStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
						$(".seach-input-details").addClass("close-content");
					}
				})
			}
	    };
	return parkingOrder;
});


