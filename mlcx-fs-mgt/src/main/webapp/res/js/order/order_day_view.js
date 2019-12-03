define([],function() {	
	var orderDayView = {
			appPath : getPath("app"),
			init : function() {
				//详情关闭
				$("#closeOrderDay").click(function(){
					closeTab("日租订单详情");
	            });
				
				//结束订单
				$("#closeOrderDayStatus").click(function(){
					addTab("日租订单信息",orderDayView.appPath+ "/orderDay/toOrderDayOver.do?orderNo="+$(this).data("id"));			
				});
				
				
			},
	detail:function(id){
		$.ajax({
			type:"post",
     		url: orderDayView.appPath+"/order/updateOrder.do",
     		data:{orderNo:id,orderStatus:"3"},
     		success: function(res){
     			if(res.code="1"){
     				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "订单结束成功", function() {
    					closeTab("订单详情");
    					$("#orderList").DataTable().ajax.reload(function(){});
    				});
     			}
     		}
     	});
	},
	
	
	}
 return	orderDayView;
});


