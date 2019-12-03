define([],function() {	
var redeemCodeCensor = {
		appPath : getPath("app"),
		init : function() {
			//审核兑换码提交
			$("#editRedeemCodeCensor").click(function(){
				redeemCodeCensor.censorRedeemCode();
			});
			//兑换码审核页面关闭
			$("#closeRedeemCodeCensor").click(function(){
				closeTab("兑换码审核");
			});
			//兑换码查看详情关闭
			$("#closeRedeemCodeView").click(function(){
				closeTab("兑换码详情");
			});
			redeemCodeCensor.pageListPlan();
		},pageListPlan:function () {
			/*debugger
			var tpl = $("#couponPlanBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(tpl);*/
			var form = $("form[name=redeemCodeCensorForm]");
			var redCode = form.find("input[name='redCode']").val();
			if(redCode == null){
				form = $("form[name=redeemCodeViewForm]");
				redCode = form.find("input[name='redCode']").val();
			}
			$('#couPonPlanLists_censor').dataTable( {
				searching:false,
				destroy: true,
				paging : false,
				"ajax": {
					"type": "POST",
					"url": redeemCodeCensor.appPath+'/couponPlan/getPageForRedemmCodePlan.do?redCode='+redCode,
					"data": function ( d ) {
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":1000//d.length
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
				   			   { "title":"方案标题","data": "title"},
				   			   { "title":"优惠方式","data": "couponMethod" },
				   			   { "title":"折扣率/直减金额","data": "couponMethod" },
				   			   { "title":"有效起始日期","data": "vailableTime1" },
				   			   { "title":"有效结束日期","data": "vailableTime2" },
				   			   { "title":"有效天数（天）","data": "availableDays" },
				   			   { "title":"兑换数量","data": "redQuantity" }
				   			
				 ],
			   ///"dom": "<'row'<'col-xs-2'l><'#parktool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
			   "dom": "<'row'<'#couponPlanToolss.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				},
				"drawCallback": function( settings ) {
			    },
				"columnDefs": [
					  {
					    "targets": [1],
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
					    "targets": [2],
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
					    "targets": [3,4],
					    "render": function(data, type, row, meta) {
					    	if(data){
					    		var now = moment(data); 
	        	            	return now.format('YYYY-MM-DD'); 
						    	}else{
						    		return "";
						    	}
					    }
					}
				]
			});
		},censorRedeemCode:function() {
			var form = $("form[name=redeemCodeCensorForm]");
			form.ajaxSubmit({
				url : redeemCodeCensor.appPath + "/redeemCode/censorRedeemCode.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码审核操作成功", function() {
							closeTab("兑换码审核");
							$("#redeemCodeList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码审核操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					
				}
			});
		}
}
return redeemCodeCensor
})
