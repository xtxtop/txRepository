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
							bootbox.alert("对账周期的开始时间不能大于结束时间！");
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
       				bootbox.confirm("确定要导出吗？", function(result) {
						if(result){
							window.location.href = money.appPath+ "/financialStatement/toMoneyExport.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59";
						}						
       				});
				});
       			/**
       			 * 导入
       			 */
       			$("#moneyTools-operate-import").click(function(){
       				bootbox.dialog({
       			        title: "导入",
       			        dataType: "json",
       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='importForm' name='importForm' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
       			        buttons: {
       			            "success": {
       			                "label": "<i class='icon-ok'></i> 保存",
       			                "className": "	btn-sm btn-success",
       			                "callback": function() {
       			                    var importForm = $("form[name='importForm']");
       			                    importForm.ajaxSubmit({
       			                        url: money.appPath+ "/financialStatement/importTransferOfAccount.do",
       			                        type: "post",
       			                        success: function(res) {
       			                        	if (res.code != 1) {
       			                        		bootbox.alert(res.msg); 
       			                        	} else {
       			                        		bootbox.alert("数据导入成功！"); 
       			                        	}
       										money.detail();
       			                        }, 
       			                        error: function(res) {
       			                        	bootbox.alert("数据异常，请检查数据完整性！"); 
       			                        }
       			                    });
       			                }
       			            },
       			            "cancel": {
       			                "label": "<i class='icon-info'></i> 取消",
       			                "className": "btn-sm btn-danger"
       			            }
       			            
       			        }
       			    })
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
    				url: money.appPath+"/financialStatement/moneyDetails.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59", 
    				type: 'POST',
    				success: function(res){
    					var code=res.code;
    					var data=res.data;
    					if(code=="1"){
    						var form=$("form[name=moneyDetailForm]");
    						//支付宝 正式环境
    						form.find("span[name=certification]").html(money.formatCurrency(data.certification,2));	
							form.find("span[name=orderAlipayAmount]").html(money.formatCurrency(data.orderAlipayAmount,2));	
							form.find("span[name=priceAlipayAmount]").html(money.formatCurrency(data.priceAlipayAmount,2));	
							form.find("span[name=depositAlipayAmount]").html(money.formatCurrency(data.depositAlipayAmount,2));	
							form.find("span[name=depositAlipayRefund]").html(money.formatCurrency(data.depositAlipayRefund,2));	
							form.find("span[name=orderAlipayCharge]").html(money.formatCurrency(data.orderAlipayCharge,2));	
							form.find("span[name=priceAlipayCharge]").html(money.formatCurrency(data.priceAlipayCharge,2));
							form.find("span[name=depositAlipayCharge]").html(money.formatCurrency(data.depositAlipayCharge,2));
							form.find("span[name=alipayAmount]").html(money.formatCurrency(data.alipayAmount,2));
							
							
							//支付宝 测试环境
							form.find("span[name=orderAlipayAmountTest]").html(money.formatCurrency(data.orderAlipayAmountTest,2));	
							form.find("span[name=priceAlipayAmountTest]").html(money.formatCurrency(data.priceAlipayAmountTest,2));	
							form.find("span[name=depositAlipayAmountTest]").html(money.formatCurrency(data.depositAlipayAmountTest,2));	
							form.find("span[name=depositAlipayRefundTest]").html(money.formatCurrency(data.depositAlipayRefundTest,2));	
							form.find("span[name=orderAlipayChargeTest]").html(money.formatCurrency(data.orderAlipayChargeTest,2));	
							form.find("span[name=priceAlipayChargeTest]").html(money.formatCurrency(data.priceAlipayChargeTest,2));
							form.find("span[name=depositAlipayChargeTest]").html(money.formatCurrency(data.depositAlipayChargeTest,2));
							form.find("span[name=asw]").html(money.formatCurrency(data.asw,2));
							
							//支付宝 合计
							form.find("span[name=certification]").html(money.formatCurrency(data.certification,2));	
							form.find("span[name=orderAlipayAmountWhole]").html(money.formatCurrency(data.orderAlipayAmountWhole,2));	
							form.find("span[name=priceAlipayAmountWhole]").html(money.formatCurrency(data.priceAlipayAmountWhole,2));	
							form.find("span[name=depositAlipayAmountWhole]").html(money.formatCurrency(data.depositAlipayAmountWhole,2));	
							form.find("span[name=depositAlipayRefundWhole]").html(money.formatCurrency(data.depositAlipayRefundWhole,2));		
							form.find("span[name=orderAlipayChargeWhole]").html(money.formatCurrency(data.orderAlipayChargeWhole,2));	
							form.find("span[name=priceAlipayChargeWhole]").html(money.formatCurrency(data.priceAlipayChargeWhole,2));
							form.find("span[name=depositAlipayChargeWhole]").html(money.formatCurrency(data.depositAlipayChargeWhole,2));
							form.find("span[name=awhole]").html(money.formatCurrency(data.awhole,2));
							
							
							//微信
							form.find("span[name=orderWxAmount]").html(money.formatCurrency(data.orderWxAmount,2));	
							form.find("span[name=priceWxAmount]").html(money.formatCurrency(data.priceWxAmount,2));	
							form.find("span[name=depositWxAmount]").html(money.formatCurrency(data.depositWxAmount,2));	
							form.find("span[name=depositWxRefund]").html(money.formatCurrency(data.depositWxRefund,2));	
							form.find("span[name=orderWxCharge]").html(money.formatCurrency(data.orderWxCharge,2));
							form.find("span[name=priceWxCharge]").html(money.formatCurrency(data.priceWxCharge,2));
							form.find("span[name=depositWxCharge]").html(money.formatCurrency(data.depositWxCharge,2));
							form.find("span[name=chargeRefund]").html(money.formatCurrency(data.chargeRefund,2));
							form.find("span[name=wxAmount]").html(money.formatCurrency(data.wxAmount,2));
							
							
							//微信 测试环境
							form.find("span[name=orderWxAmountTest]").html(money.formatCurrency(data.orderWxAmountTest,2));	
							form.find("span[name=priceWxAmountTest]").html(money.formatCurrency(data.priceWxAmountTest,2));	
							form.find("span[name=depositWxAmountTest]").html(money.formatCurrency(data.depositWxAmountTest,2));	
							form.find("span[name=depositWxRefundTest]").html(money.formatCurrency(data.depositWxRefundTest,2));	
							form.find("span[name=orderWxChargeTest]").html(money.formatCurrency(data.orderWxChargeTest,2));	
							form.find("span[name=priceWxChargeTest]").html(money.formatCurrency(data.priceWxChargeTest,2));
							form.find("span[name=depositWxChargeTest]").html(money.formatCurrency(data.depositWxChargeTest,2));
							form.find("span[name=chargeRefundTest]").html(money.formatCurrency(data.chargeRefundTest,2));
							form.find("span[name=aswWx]").html(money.formatCurrency(data.aswWx,2));
							
							
							//微信 合计
							form.find("span[name=orderWxAmountWhole]").html(money.formatCurrency(data.orderWxAmountWhole,2));	
							form.find("span[name=priceWxAmountWhole]").html(money.formatCurrency(data.priceWxAmountWhole,2));	
							form.find("span[name=depositWxAmountWhole]").html(money.formatCurrency(data.depositWxAmountWhole,2));	
							form.find("span[name=depositWxRefundWhole]").html(money.formatCurrency(data.depositWxRefundWhole,2));		
							form.find("span[name=orderWxChargeWhole]").html(money.formatCurrency(data.orderWxChargeWhole,2));	
							form.find("span[name=priceWxChargeWhole]").html(money.formatCurrency(data.priceWxChargeWhole,2));
							form.find("span[name=depositWxChargeWhole]").html(money.formatCurrency(data.depositWxChargeWhole,2));
							form.find("span[name=chargeRefundWhole]").html(money.formatCurrency(data.chargeRefundWhole,2));
							form.find("span[name=awholeWx]").html(money.formatCurrency(data.awholeWx,2));
							
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
		        var result = t.split("").reverse().join("") + "." + r;
		        if(result.indexOf("-,") == 0){
		        	result = result.replace("-,","-");
		        }
		        return  result }
	    };
	return money;
});


