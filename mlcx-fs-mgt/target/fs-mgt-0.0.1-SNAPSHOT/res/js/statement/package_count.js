define([],function() {	
	var packageCount={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				packageCount.pageList();
				//查询
				$("#packageCountSearch").click(function(){
					var form = $("form[name=packageCountSearchForm]");
					var createTime =  form.find("input[name=createTime]").val();
					var finishTime = form.find("input[name=finishTime]").val();
					if(createTime!=""&&finishTime!=""){
						if(new Date(createTime)>new Date(finishTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							packageCount.pageList();
						}
					}else{
						packageCount.pageList();
					}
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        },
			pageList:function () {
				$('#packageCount').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": packageCount.appPath+'/packageCount/pageListpackageCount.do',
						"data": function ( d ) {
							var form = $("form[name=packageCountSearchForm]");
							var startTime=form.find("input[name=startTime]").val();
							var endTime=form.find("input[name=endTime]").val();
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"startTime":form.find("input[name=startTime]").val()+ " 00:00:00",
								"endTime":form.find("input[name=endTime]").val()+ " 23:59:59",
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
					    { "title":"套餐编号","data": "packageNo" },
					    { "title":"套餐名称","data": "packageName" },
			            { "title":"套餐单价（元）","data": "price" },
			            { "title":"套餐充值（元）","data": "packAmount" },
//						{ "title":"套餐时长(分钟)","data": "minutes" },
						{ "title":"类型","data": "packageType" },
						{ "title":"购买数","data": "packOrderCount"},
						{ "title":"总实收金额（元）","data": "packRealAmount"},
						{ "title":"总充值金额（元）","data": "totalPackAmount"},
						{ "title":"已抵扣订单数","data": "dicountOrderNum"},
						{ "title":"已抵扣总额（元）","data": "dicountOrderCount"},
	/*					{ "title":"微信收入","data": "wxAmount"},
						{ "title":"微信手续费","data": "wxAgentFee"},
						{ "title":"支付宝收入","data": "zfbAmount" },
						{ "title":"支付宝手续费","data": "zfbAgentFee" }*/
					],
				   "dom": "<'row'<'#packageCountTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#packageCountTools").html("");
					   $("#packageCountTools").removeClass("col-xs-6");
					   $("#packageCountTools").append('<button type="button" class="btn-new-type packageCountTools-operate-export">导出</button>');
		       			$(".packageCountTools-operate-export").on("click",function(){
		       				var form = $("form[name=packageCountSearchForm]");
		       				var startTime=form.find("input[name=startTime]").val();
							var endTime=form.find("input[name=endTime]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = packageCount.appPath+ "/packageCount/topackageCountExport.do?startTime="+startTime+" 00:00:00"+"&endTime="+endTime+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						packageCount.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2,3,6,7,9],
						    "render": function(a,b,c,d) {
						    	if(a){
						    		return packageCount.formatCurrency(a);
						    	}else{
						    		return "0";
						    	}
						    }
						},
					]
				});
			},formatCurrency :function (s,n) { 
	        	n = n > 0 && n <= 20 ? n : 2;  
		        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
		        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
		        t = "";  
		        for (i = 0; i < l.length; i++) {  
		            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		        }  
		        return t.split("").reverse().join("") + "." + r;  }
	    };
	return packageCount;
});