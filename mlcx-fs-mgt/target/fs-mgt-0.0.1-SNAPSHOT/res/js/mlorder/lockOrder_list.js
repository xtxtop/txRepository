define([],function() {	
	var lockOrder={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				lockOrder.pageList();				
				//查询
				$("#lockOrderSearch").click(function(){
					lockOrder.pageList();
	            });
				$("#closelockOrderView").click(function(){
					closeTab("地锁订单详情");
	            });
				//更多
				lockOrder.handleClickMore();
				//结束地锁提交
				$("#lockOrderBtn").click(function(){
					lockOrder.offlockOrder();
	            });
				//结束地锁取消
				$("#lockOrderCancel").click(function(){
					$("#lockOrderModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".lockOrder-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("地锁订单详情",lockOrder.appPath+ "/lockOrder/toLockOrderView.do?orderNo="+$(this).data("id"));
					});
				});
	        	$(".lockOrder-operate-edit").each(function(){
	        		$(this).on("click",function(){
	        			var form = $("form[name=lockOrderForm]");
						var orderNo=$(this).data("id");
						var StartTime=$(this).data("start");
						var scheme=$(this).data("scheme");
						var nows = moment(StartTime).format('YYYY-MM-DD HH:mm:ss');
						 $("#lockOrderModal").modal("show");
		            	    form.find("input[name='orderNo']").val(orderNo);
		            	    form.find("input[name='orderStartTime']").val(nows);
		            	    form.find("input[name='lockBillingSchemeNo']").val(scheme);
							$("#offlockOrderMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将订单【"+orderNo+"】结束地锁吗？");
	        		});
	        	});
	        },
	 	   //结束操作
			offlockOrder: function () {
	        	var form = $("form[name=lockOrderForm]");
				form.ajaxSubmit({
					url:lockOrder.appPath+"/lockOrder/disableLockOrder.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
						  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
							  form.find("textarea[name='finishReason']").html("")
							  $("#offlockOrderMemo").text("");
							  $("#lockOrderModal").modal("hide");
							  $(".bootbox").modal("hide");
							  $("#lockOrderList").DataTable().ajax.reload(function(){}); 
						  });
						  
						}else{
							$("#lockOrderModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
							$("#offlockOrderMemo").text("");
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
				var lockOrderBtnTpl = $("#lockOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(lockOrderBtnTpl);
				$('#lockOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": lockOrder.appPath+'/lockOrder/pageListlockOrder.do',
						"data": function ( d ) {
							var form = $("form[name=lockOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":$.trim(form.find("input[name=orderNo]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"orderStatus":$.trim(form.find("select[name=orderStatus]").val()),
								"stationNo":$.trim(form.find("select[name=stationNo]").val()),
								"payStatus":$.trim(form.find("select[name=payStatus]").val()),
								"payType":$.trim(form.find("select[name=payType]").val()),
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
			            { "title":"订单编号","data": "orderNo" ,"defaultContent":"--"},
						{ "title":"充电站名称","data": "stationName" ,"defaultContent":"--"},
						{ "title":"地锁名称","data": "parkingLockName" ,"defaultContent":"--"},
						{ "title":"会员名称","data": "memberName" ,"defaultContent":"--"},
						{ "title":"手机号","data": "mobilePhone","defaultContent":"--"},
						{ "title":"开始时间","data": "orderStartTime","defaultContent":"--"},
						{ "title":"结束时间","data": "orderEndTime","defaultContent":"--"},
						{ "title":"总时长(分钟)","data": "orderTimeLength","defaultContent":"--"},
						{ "title":"总金额(元)","data": "orderAmount","defaultContent":"--"},
						{ "title":"订单状态","data": "orderStatus","defaultContent":"--"},
						{ "title":"支付状态","data": "payStatus","defaultContent":"--"},
						{ "title":"支付方式","data": "payType","defaultContent":"--"},
						{ "title":"结束类型","data": "finishType","defaultContent":"--"},
						{ "title":"计费类型","data": "lockType","defaultContent":"--"},
						{ "title":"充电订单编号","data": "chargeOrderNo","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#lockOrderTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					},
					"drawCallback": function( settings ) {
						lockOrder.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets" : [5,6],
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
							"targets" : [9],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									if(c.orderStatus==0){
										return "进行中";
									}else if(c.orderStatus==1){
										return "待支付";
										
									}else if(c.orderStatus==2){
										return "待评价";
										
									}else if(c.orderStatus==3){
										return "已完成";
									}else if(c.orderStatus==4){
										return "待结算";
									}
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
							"targets" : [11],
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
							"targets" : [12],
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
								if(a!=null){
									if(a==0){
										return "充电";
									}else{
										return "停车";
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
								var view='<span class="glyphicon lockOrder-operate-view" data-id="'+c.orderNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                               if(c.orderStatus==0){
                            	   edit='<span class="glyphicon lockOrder-operate-edit" data-id="'+c.orderNo+'" data-start="'+c.orderStartTime+'" data-scheme="'+c.lockBillingSchemeNo+'" data-toggle="tooltip" data-placement="top">结束地锁计费</span>';
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
						$(this).html("<div class='pull-right moreButtonNew' id='lockOrderStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
						$(".seach-input-details").removeClass("close-content");
					}else{
						$(this).html("<div class='pull-right moreButtonNew' id='lockOrderStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
						$(".seach-input-details").addClass("close-content");
					}
				})
			}
	    };
	return lockOrder;
});


