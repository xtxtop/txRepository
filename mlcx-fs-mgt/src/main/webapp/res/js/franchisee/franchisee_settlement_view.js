define(
		[],
		function() {
			var franchiseeSettleView = {
				appPath : getPath("app"),
				init : function() {
					
					$("#closeView").click(function(){
						closeTab("加盟商结算单详情");
					});
					
					$("#queryThisFranchiseeRecord").click(function(){
						$("#earningsModal").modal("show");
						franchiseeSettleView.pageRecordList();
					});
					
				},
				
				
				pageRecordList : function() {
					var form = $("form[name=FranchiseeSettleViewForm]");
					var orderNo = form.find("input[name='orderNo']").val();
					var franchiseeNo = form.find("input[name='franchiseeNo']").val();
					var settleDay = form.find("input[name='settleDay']").val();
					var earningsModalTpl = $("#earningsModalTpl").html();
					// 预编译模板
					var template = Handlebars.compile(earningsModalTpl);
					var table = $('#earningsList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : franchiseeSettleView.appPath
													+ "/franchisee/pageListEarningsRecord.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "orderNo" : orderNo,
																 "settleDay" : settleDay,
																 "franchiseeNo" : franchiseeNo
																});
											},
											"dataSrc" : function(json) {
												json.recordsTotal = json.rowCount;
												json.recordsFiltered = json.rowCount;
												json.data = json.data;
												return json.data;
											},
											error : function(xhr, error, thrown) {
											}
										},
										"columns" : [ 
										{
											"title" : "加盟商名称",
											"data" : "franchiseeName"
										}, {
											"title" : "订单号",
											"data" : "orderNo"
										}, {
											"title" : "分润类型",
											"data" : "profitType"
										}, {
											"title" : "车辆/场站",
											"data" : "carOrParkNo"
										}, {
											"title" : "订单金额",
											"data" : "orderAmount"
										},
										{
											"title" : "按车辆分润(%)",
											"data" : "carProportion"
										},
										{
											"title" : "车辆分润",
											"data" : "carProfit"
										},
										{
											"title" : "按场站分润(%)",
											"data" : "parkProportion"
										},
										{
											"title" : "场站分润",
											"data" : "parkProfit"
										} ],
										"dom" : "<'row'<''><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#franchiseeSettle").empty().append('<button type="button"  class="btn-new-type sfranch-batch-close">关闭</button>');
										
											$(".sfranch-batch-close").on("click",function(){
												$("#earningsModal").modal("hide");
											});
										},
										"drawCallback" : function(settings) {
											
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
											{
											    "targets": [2],
											    "render": function(a,b,c,d) {//0、车辆，1、场站，2、车辆和场站
											    	if(a!=null){
											        	if(a==0){
											        		return "车辆";
											        	}else if(a==1){
											        		return "场站";
											        	}else if(a==2){
											        		return "车辆和场站";
											        	}
											        }else{
											        	return "";
											        }
											    }
											},   
									]
									});
				},
			};
			return franchiseeSettleView;
		});
