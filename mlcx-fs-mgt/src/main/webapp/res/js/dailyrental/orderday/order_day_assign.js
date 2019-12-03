define([],function() {	
	var orderAssign={
			appPath:getPath("app"),	
			init: function () {
				//关闭车辆详情页
				$("#closeOrderDayEdit").click(function(){
					closeTab("指派订单");
	            });
				//指派提交
				$("#orderAssignOnFormBtn").click(function(){
					orderAssign.onFormSub();
	            });
				
	            //数据列表
				orderAssign.pageList();
				
				$("#onorderAssignModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onorderAssignForm]");
	            	form.resetForm();
	            	$("#onorderAssignMemo").text("");
	            	form.find("input[name=parkId]").val("");
	            });
				
				
			},
			
			//方法加载
	        operateColumEvent: function(){
	        	//指派定案
				$(".orderAssign-operate-assign").each(function(id,obj){
					$(this).on("click",function(){
						var merchant=$(this).data("id");
						var merchantId = merchant.split(",")[0];
						var merchantName = merchant.split(",")[1];
						var form = $("form[name=orderAssignForm]");
						var orderNo = form.find("input[name='orderNo']").val();
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要把订单指派给"+merchantName+"吗？", function(result) {
							if(result){
								$.ajax({
									 type: "post",
						             url: orderAssign.appPath+"/orderDay/saveMerchantOrder.do",
						             data: {orderNo:orderNo,merchantId:merchantId},
						             dataType: "json",
						             success: function(data){
						            	 if(data.code=="1"){
						            		 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "指派成功！",function(){
												  $(".bootbox").modal("hide");
												  closeTab("指派订单");
												  $("#merchantInventoryList").DataTable().ajax.reload(function(){}); 
											 });
						                 }else{
						                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
						                 }
						             }
								});
							}
						})
					});
				});
	        },
			pageList:function () {	
				var orderAssignTpl = $("#merchantInventoryTpl").html();
				// 预编译模板
				var template = Handlebars.compile(orderAssignTpl);
				var form = $("form[name=orderAssignForm]");
				var carModelId = form.find("input[name='carModelId']").val();
				var cityId = form.find("input[name='cityId']").val();
				$('#merchantInventoryList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": orderAssign.appPath+'/orderDay/pageListAssignMerchantInventory.do',
						"data": function ( d ) {
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carModelId":carModelId,
								"cityId":cityId
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
						{ "title":"租赁商","data": "merchantName" },
						{ "title":"车辆型号","data": "carModelName" },
						{ "title":"车辆数量","data": "inventoryDay" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#orderAssignTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
	        			
					},
					"drawCallback": function( settings ) {
						orderAssign.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
							"targets": [3],
							"render": function (a, b, c, d) {
								var assign='<span class="glyphicon orderAssign-operate-assign" style="text-decoration: underline; cursor: pointer;"data-id="'+c.merchantId+","+c.merchantName+'" data-toggle="tooltip" data-placement="top">指派</span>';
	        					return assign;
							}
						}
					]
				});
			}
	    };
	return orderAssign;
});


