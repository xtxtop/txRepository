define([],function() {	
	var merchantOrderConfirm={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				merchantOrderConfirm.pageList();
				//查询
				$("#merchantOrderConfirmSearchBtn").click(function(){
					merchantOrderConfirm.pageList();
	            });
				
			},
				
			//方法加载
	        operateColumEvent: function(){
				//确认对账单结算
				$(".merchantOrderConfirm-operate-settled").on("click",function(){
					var id = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定此对账单已结算吗？", function(result) {
						if(result){
							$.post("merchantOrderConfirm/merchantOrderConfirmSettled.do",{id:id},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#merchantOrderConfirmList").DataTable().ajax.reload(function(){
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
				var merchantOrderConfirmTpl = $("#merchantOrderConfirmTpl").html();
				// 预编译模板
				var template = Handlebars.compile(merchantOrderConfirmTpl);
				$('#merchantOrderConfirmList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": merchantOrderConfirm.appPath+'/merchantOrderConfirm/pageListMerchantOrderConfirm.do',
						"data": function ( d ) {
							var form = $("form[name=merchantOrderConfirmSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"merchantId":form.find("select[name=merchantId]").val(),
								"date":form.find("select[name=date]").val(),
								"confirmStatus":form.find("select[name=confirmStatus]").val(),
								"isSettled":form.find("select[name=isSettled]").val()
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
			            { "title":"商家名称","data": "merchantName","defaultContent":"" },
			            { "title":"对账周期","data": "date","defaultContent":"" },
						{ "title":"订单数量","data": "num","defaultContent":"" },
						{ "title":"订单总金额","data": "orderAmount","defaultContent":"" },
						{ "title":"商家分润比","data": "profitRatio","defaultContent":"" },
						{ "title":"商家分润额","data": "profitAmount","defaultContent":"" },
						{ "title":"账单确认状态","data": "confirmStatus","defaultContent":"" },
						{ "title":"账单结算状态","data": "isSettled","defaultContent":"" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#merchantOrderConfirmTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#merchantOrderConfirmTools").html("");
					},
					"drawCallback": function( settings ) {
						merchantOrderConfirm.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						
						{
						    "targets": [6,7],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		if (data==1) {
						    			return "是"; 
									}else{
										return "否";
									}
							    }else{
							    	return "否";
							    }
						    }
						},
						{
							"targets": [8],
							"render": function (a, b, c, d) {
								var  settled = "";
								if (c.confirmStatus==1) {
									if (c.isSettled!=1) {
										settled='<span class="glyphicon  btn btn-default  merchantOrderConfirm-operate-settled" data-id="'+c.id+'" data-toggle="tooltip" data-placement="top">结算</span>';
									}
								}
	        					return settled;
							}
						}
					]
				});
			}
	    };
	return merchantOrderConfirm;
});


