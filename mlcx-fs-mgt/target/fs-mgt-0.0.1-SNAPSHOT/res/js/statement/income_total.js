define([],function() {	
	var money={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				money.detail();
				//查询
				$("#moneySearch").click(function(){
					var form = $("form[name=moneySearchForm]");
					var startTime =  form.find("input[name=startTime]").val();
					var endTime = form.find("input[name=endTime]").val();
					if(startTime!=""&&endTime!=""){
						if(new Date(startTime)>new Date(endTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							money.detail();
						}
					}else{
						money.detail();
					}
	            });
				$("#moneyTools-operate-export").click(function(){
					var form = $("form[name=moneySearchForm]");
					var startTime =  form.find("input[name=startTime]").val();
					var endTime = form.find("input[name=endTime]").val();
       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
						if(result){
							window.location.href = money.appPath+ "/financialStatement/toMoneyExport.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59";
						}						
       				});
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        },
	        detail:function() {
	        	var form = $("form[name=moneySearchForm]");
				var startTime =  form.find("input[name=startTime]").val();
				var endTime = form.find("input[name=endTime]").val();
				$.ajax({
    				url: money.appPath+"/financialStatement/getIncomeTotal.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59", 
    				type: 'POST',
    				success: function(res){
    					var code=res.code;
    					var data=res.data;
    					if(code=="1"){
    						var form=$("form[name=moneyDetailForm]");
							form.find("span[name=orderIncomeTotal]").html(money.formatCurrency(data.orderIncomeTotal,2));
    						//支付宝
							form.find("span[name=orderAlipayAmount]").html(money.formatCurrency(data.orderAlipayAmount,2));
							form.find("span[name=priceAlipayAmount]").html(money.formatCurrency(data.priceAlipayAmount,2));
							form.find("span[name=depositAlipayAmount]").html(money.formatCurrency(data.depositAlipayAmount,2));
							
							form.find("span[name=orderAlipayCharge]").html(money.formatCurrency(data.orderAlipayCharge,2));
							form.find("span[name=priceAlipayCharge]").html(money.formatCurrency(data.priceAlipayCharge,2));
							form.find("span[name=depositAlipayCharge]").html(money.formatCurrency(data.depositAlipayCharge,2));
							
							form.find("span[name=depositAlipayRefund]").html(money.formatCurrency(data.depositAlipayRefund,2));
							//微信
							form.find("span[name=orderWxAmount]").html(money.formatCurrency(data.orderWxAmount,2));
							form.find("span[name=priceWxAmount]").html(money.formatCurrency(data.priceWxAmount,2));
							form.find("span[name=depositWxAmount]").html(money.formatCurrency(data.depositWxAmount,2));
							
							form.find("span[name=orderWxCharge]").html(money.formatCurrency(data.orderWxCharge,2));
							form.find("span[name=priceWxCharge]").html(money.formatCurrency(data.priceWxCharge,2));
							form.find("span[name=depositWxCharge]").html(money.formatCurrency(data.depositWxCharge,2));
							
							form.find("span[name=depositWxRefund]").html(money.formatCurrency(data.depositWxRefund,2));
							
							var orderAmountTotal = money.formatCurrency((data.orderAlipayAmount+data.orderWxAmount),2);
							form.find("span[name=orderAmountTotal]").html(orderAmountTotal);
							
							var priceAmountTotal = money.formatCurrency((data.priceAlipayAmount+data.priceWxAmount),2);
							form.find("span[name=priceAmountTotal]").html(priceAmountTotal);
							
							var depositAmountTotal = money.formatCurrency((data.depositAlipayAmount+data.depositWxAmount),2);
							form.find("span[name=depositAmountTotal]").html(depositAmountTotal);
							
							var orderChargeTotal = money.formatCurrency((data.orderAlipayCharge,data.orderWxCharge),2);
							form.find("span[name=orderChargeTotal]").html(orderChargeTotal);
							
							var priceChargeTotal = money.formatCurrency((data.priceAlipayCharge+data.priceWxCharge),2);
							form.find("span[name=priceChargeTotal]").html(priceChargeTotal);
							
							var depositChargeTotal = money.formatCurrency((data.depositAlipayCharge+data.depositWxCharge),2);
							form.find("span[name=depositChargeTotal]").html(depositChargeTotal);
							
							var depositRefundTotal = money.formatCurrency((data.depositAlipayRefund+data.depositWxRefund),2);
							form.find("span[name=depositRefundTotal]").html(depositRefundTotal);
    					}
    				}
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
		        return t.split("").reverse().join("") + "." + r;  }
	    };
	return money;
});


