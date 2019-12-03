define(
		[],
		function() {
			var franchiseeProfit = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#franchiseeProfitSearch").click(function() {
						franchiseeProfit.pageList();
					});
					// 列表页面分页方法调用
					franchiseeProfit.pageList();
				},
				
				operateColumEvent : function() {
					$(".franchiseeProfit-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("订单详情",franchiseeProfit.appPath+ "/order/toOrderView.do?orderNo="+$(this).data("id"));
						});
					});
					
				},
				pageList : function() {
					var form = $("form[name=franchiseeProfitSerachForm]");
					var franchiseeProfitBtnTpl = $("#franchiseeProfitBtnTpl").html();
					
					// 预编译模板
					var template = Handlebars.compile(franchiseeProfitBtnTpl);
					var table = $('#franchiseeProfitList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : franchiseeProfit.appPath
													+ "/franchisee/pageListFranchiseeProfit.do",
											"data" : function(d) {
												return $.extend({},d,
													{"pageNo" : (d.start / d.length) + 1,
													 "pageSize" : d.length,
						        					 "franchiseeName":form.find("input[name=franchiseeName]").val(),
						        					 "orderNo":form.find("input[name=orderNo]").val(),
						        					 "profitType":form.find("select[name=profitType]").val(),
						        					 "carOrParkNo":form.find("input[name=carOrParkNo]").val()
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
										},
										{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#franchiseetool.col-xs-6'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
								       
										},
										"drawCallback" : function(settings) {
											franchiseeProfit.operateColumEvent();
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
												{
													targets : 9,
													render : function(a, b, c,
															d) {
														var cencor = "";
														var view = '<span class="glyphicon franchiseeProfit-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.orderNo
																+ '" >查看订单</span>';
															return view;
														
													}
												}]
									});
				},
			};
			return franchiseeProfit;
		});
