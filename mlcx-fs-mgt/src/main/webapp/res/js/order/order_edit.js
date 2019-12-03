define([],function() {	
	var orderView = {
			appPath : getPath("app"),
			init : function() {
				//详情关闭
				$("#closeOrder").click(function(){
					closeTab("订单详情");
	            });
				//故障提交
				$("#editCarFault").click(function(){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "您确定要记录故障吗？", function(result) {
						if(result){
							orderView.saveCarFault();
						}						
					}); 
				});
				//结束订单
				$("#closeOrderStatus").click(function(){
					addTab("订单信息",order.appPath+ "/order/toOrderOver.do?orderNo="+$(this).data("id"));
				});
				//事故提交
				$("#saveCarFault").click(function(){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "您确定要记录事故吗？", function(result) {
						if(result){
							orderView.editCarIllegal();
						}						
					}); 
				});
				$("#onOrderModal").on("hidden.bs.modal", function() {  
					
	            });
				$("#offOrderModal").on("hidden.bs.modal", function() {  
	            	
	            });
			},
	detail:function(id){
		$.ajax({
			type:"post",
     		url: orderView.appPath+"/order/updateOrder.do",
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
			//故障
	 saveCarFault:function() {
		var form = $("form[name=onForm]");
		form.ajaxSubmit({
			url : orderView.appPath + "/carFault/editCarFault.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				var data=res.data;
				if (code == "1") {
					addTab("故障编辑",order.appPath+ "/carFault/toCarFaultEdit.do?faultId="+data.faultId);			
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "记录故障失败");
				}
			}
		});
	},
	//事故
	editCarIllegal:function() {
		var form = $("form[name=offForm]");
		form.ajaxSubmit({
			url : orderView.appPath + "/carAccident/addCarAccident.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				var data=res.data;
				if (code == "1") {
					addTab("事故编辑",carAccident.appPath+ "/carAccident/toUpdateCarAccident.do?accidentId="+data.accidentId);				
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "记录事故失败！");
				}
			}
		});
	}
	}
 return	orderView;
});


