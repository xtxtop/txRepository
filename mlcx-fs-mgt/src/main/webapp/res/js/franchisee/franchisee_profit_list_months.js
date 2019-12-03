define(
		[],
		function() {
			var franchiseeProfitByMonth = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#franchiseeProfitMonthSearch").click(function() {
						franchiseeProfitByMonth.pageList();
					});
					
					// 列表页面分页方法调用
					franchiseeProfitByMonth.pageList();
				},
				
				operateColumEvent : function() {
					$(".franchisee-operate-view").each(function() {
						$(this).on("click", function() {
							var id=$(this).data("id");
							var franchiseeNo=$(this).data("id2");
							addTab("查看明细(按月)",franchiseeProfitByMonth.appPath+ "/franchisee/toFranchiseeProfitByMonthsView.do?months="+id+"&franchiseeNo="+franchiseeNo);
						});
					});
				},
				pageList : function() {
					var form = $("form[name=franchiseeProfitMonthSerachForm]");
					var franchiseeName = $.trim(form.find("input[name='franchiseeName']").val());
					var queryTime = form.find("input[name='queryTime']").val();
					var franchiseeProfitMonthBtnTpl = $("#franchiseeProfitMonthBtnTpl").html();
					
					// 预编译模板
					var template = Handlebars.compile(franchiseeProfitMonthBtnTpl);
					var table = $('#franchiseeProfitMonthList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : franchiseeProfitByMonth.appPath
													+ "/franchisee/pageListFranchiseeProfitByMonths.do",
											"data" : function(d) {
												return $.extend({},d,
														{"pageNo" : (d.start / d.length) + 1,
														 "pageSize" : d.length,
														 "franchiseeName" :franchiseeName,
														 "queryTime" :queryTime,
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
										},
										{
											"title" : "月份",
											"data" : "months"
										}, 
										{
											"title" : "订单数",
											"data" : "orderNumber"
										}, 
										{
											"title" : "订单金额",
											"data" : "orderAmount"
										},
										{
											"title" : "分润额",
											"data" : "profitAmount"
										},
										{
											"title" : "车辆分润",
											"data" : "carProfit"
										},
										{
											"title" : "场站分润",
											"data" : "parkProfit"
										},
										{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#franchiseetool.col-xs-12'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											franchiseeProfitByMonth.operateColumEvent();
										},
										"drawCallback" : function(settings) {
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
													"targets" : [1],
													"render" : function(a,b, c, d) {
														if(a!=""){
															var year = a.substring(0,4);
															var month  = a.substring(4,6);
															return year+"年"+month+"月";
														}
													}
												},
												{
													targets : 7,
													render : function(a, b, c,
															d) {
													var cencor = "";
													var view = '<span class="glyphicon franchisee-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.months+ '" data-id2="'+ c.franchiseeNo+ '">查看明细</span>';
															return view;
														
													}
												}]
									});
				},
			};
			return franchiseeProfitByMonth;
		});
