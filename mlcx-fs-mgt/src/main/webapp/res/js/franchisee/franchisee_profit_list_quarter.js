define(
		[],
		function() {
			var franchiseeProfitByQuarter = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#franchiseeProfitQuarterSearch").click(function() {
						franchiseeProfitByQuarter.pageList();
					});
					
					// 列表页面分页方法调用
					franchiseeProfitByQuarter.pageList();
				},
				
				operateColumEvent : function() {
					$(".franchisee-operate-view").each(function() {
						$(this).on("click", function() {
							var id=$(this).data("id");
							var franchiseeNo=$(this).data("id3");
							var year=$(this).data("id4");
							addTab("查看明细(按季度)",franchiseeProfitByQuarter.appPath+ "/franchisee/toFranchiseeProfitByQuarterView.do?quarter="+id+"&franchiseeNo="+franchiseeNo+"&year="+year);
						});
					});
				},
				pageList : function() {
					var form = $("form[name=franchiseeProfitQuarterSerachForm]");
					var franchiseeName = $.trim(form.find("input[name='franchiseeName']").val());
					var queryTime = form.find("input[name='queryTime']").val();
					var franchiseeProfitQuarterBtnTpl = $("#franchiseeProfitQuarterBtnTpl").html();
					
					// 预编译模板
					var template = Handlebars.compile(franchiseeProfitQuarterBtnTpl);
					var table = $('#franchiseeProfitQuarterList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : franchiseeProfitByQuarter.appPath
													+ "/franchisee/pageListFranchiseeProfitByQuarter.do",
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
											"title" : "季度",
											"data" : "year"
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
										"dom" : "<'row'<'#franchiseetool.col-xs-6'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											franchiseeProfitByQuarter.operateColumEvent();
										},
										"drawCallback" : function(settings) {
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
													"targets" : [1],
													"render" : function(a,b, c, d) {
														if(a!=""){
															return a+"年"+Math.floor(c.quarter)+"季度";
														}
													}
												},
												{
													targets : 7,
													render : function(a, b, c,
															d) {
														var cencor = "";
														var view = '<span class="glyphicon franchisee-operate-view"" style="text-decoration: underline; cursor: pointer;"   data-id4="'+ c.year+ '" data-id3="'+ c.franchiseeNo+ '" data-id="'+ Math.floor(c.quarter)+ '" >查看明细</span>';
															return view;
														
													}
												}]
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
			return franchiseeProfitByQuarter;
		});
