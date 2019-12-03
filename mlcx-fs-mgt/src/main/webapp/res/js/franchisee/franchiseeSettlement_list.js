define(
		[],
		function() {
			var franchiseeSettlement = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#franchiseeSettlementSearch").click(function() {
							franchiseeSettlement.pageList();
					});
					// 列表页面分页方法调用
					franchiseeSettlement.pageList();
					franchisee.initSearchStyle();
				},
			
				operateColumEvent : function() {
					$(".franchiseeSettlement-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("加盟商结算单详情",franchiseeSettlement.appPath+ "/franchisee/toFranchiseeSettleView.do?franchiseeSettleNo="+$(this).data("id"));
						});
					});
				},
				pageList : function() {
					var form = $("form[name=franchiseeSettlementSerachForm]");
					var franchiseeNo = form.find("select[name='franchiseeNo']").val();
					var createTimeStart=form.find("input[name=createTimeStart]").val()+" 00:00:00";
					var createTimeEnd=form.find("input[name=createTimeEnd]").val()+" 23:59:59";
				
					var franchiseeSettlementBtnTpl = $("#franchiseeSettlementBtnTpl").html();
					
					// 预编译模板
					var template = Handlebars.compile(franchiseeSettlementBtnTpl);
					var table = $('#franchiseeSettlementList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : franchiseeSettlement.appPath
													+ "/franchisee/pageListFranchiseeSettlement.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "franchiseeNo" :franchiseeNo,
																 "createTimeStart" : createTimeStart,
																 "createTimeEnd" :createTimeEnd
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
											"title" : "加盟商编号",
											"data" : "franchiseeNo"
										}, {
											"title" : "加盟商名称",
											"data" : "franchiseeName"
										},{
											"title" : "结算周期",
											"data" : "settleDay"
										},{
											"title" : "订单数",
											"data" : "orderCount"
										}, {
											"title" : "订单金额",
											"data" : "orderAmount"
										}, {
											"title" : "分润额",
											"data" : "shareAmount"
										},{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#franchiseeSettlementtool.col-xs-6'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#franchiseeSettlementtool").html("");
											$("#franchiseeSettlementtool").css("float", "right");
											$("#franchiseeSettlementtool").removeClass("col-xs-6");
								       		
										},
										"drawCallback" : function(settings) {
											franchiseeSettlement.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
											/*{
												"targets" : [0],
												 "orderable":false,
												"render" : function(data, type, full, meta) {
													  return '<input type="checkbox" name="franchiseeSettlementNo" value="' + data + '">';
												}
											   },*/
												
												
												{
													targets : 6,
													render : function(a, b, c,
															d) {
														var view = '<span class="glyphicon franchiseeSettlement-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.franchiseeSettleNo
																+ '" >查看</span>';
														
															return view;
														
													}
												}]
									});
				},
				initSearchStyle:function(){
					var itemLength = $(".seach-input-details>.seach-input-item").length;
					if(itemLength<=3){
						$(".seach-input-details").css({
							"margin-top":"24px",
							"height":"auto",
							"position":"relative",
							"z-index":"1",
							"margin-right":"4.5% !important",
							"margin-bottom":"10px"
								
						});
						$(".seach-input-details>.seach-input-item").css({
							"width":"25%"
						});
						$(".seacher-button-content").css({
							"z-index":"0",
						    "float": "right",
						    "margin-top": "-15px"
						})
					}
				}
			};
			return franchiseeSettlement;
		});
