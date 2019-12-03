define(
		[],
		function() {
			var memberAccountAmount = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#memberAccountAmountSearch").click(function() {
						memberAccountAmount.pageList();
					});
					// 列表页面分页方法调用
					memberAccountAmount.pageList();

				},
				operateColumEvent : function() {
					$(".memberAccountAmount-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("套餐订单列表",memberAccountAmount.appPath+ "/pricingPackOrder/toPricingPackOrderList.do?memberNo="+$(this).data("id"));
						});
					});
					
					$(".memberDepositAmount-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("押金缴纳列表",memberAccountAmount.appPath+ "/depositPay/toDepositPayList.do?memberNo="+$(this).data("id"));
						});
					});
					
					
					$(".member-operate-view").each(function(){
						$(this).on("click",function(){
							addTab("会员详情",memberAccountAmount.appPath+ "/member/toMemberView.do?memberNo="+$(this).data("id"));
						});
					});
					
					
					
					$(".amount_orderby_click").each(function() {
						$(this).on("click", function() {
							var form = $("form[name=memberAccountAmountSerachForm]");
							var amountOrderBy = form.find("input[name='amountOrderBy']");
							if(amountOrderBy.val() == null || amountOrderBy.val() == ""){
								amountOrderBy.val("desc")
							}else{
								if(amountOrderBy.val() == "desc"){
									amountOrderBy.val("asc")
								}else{
									amountOrderBy.val("desc")
								}
							}
							memberAccountAmount.pageList();
						});
					});
				},
				pageList : function() {
					var form = $("form[name=memberAccountAmountSerachForm]");
					
					var memberName = $.trim(form.find("input[name='memberName']").val());
					var mobilePhone = $.trim(form.find("input[name='mobilePhone']").val());
					
					var memberAccountAmountBtnTpl = $("#memberAccountAmountBtnTpl").html();
					var amountOrderBy = form.find("input[name='amountOrderBy']").val();
					// 预编译模板
					var template = Handlebars.compile(memberAccountAmountBtnTpl);
					var table = $('#memberAccountAmountList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : memberAccountAmount.appPath
													+ "/memberAccountAmount/pageListMemberAccountAmount.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "memberName" :memberName,
																 "mobilePhone" : mobilePhone,
																 "amountOrderBy" : amountOrderBy
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
											"title" : "姓名",
											"data" : "memberName"
										},{
											"title" : "手机号",
											"data" : "mobilePhone"
										}, {
											"title" : "<span class='amount_orderby_click'>余额</span>",
											"data" : "packOrderAmount"
										},{
											"title" : "押金",
											"data" : "residueDeposit"
										},{
											"title" : "积分",
											"data" : "memberPointsValues"
										},{
											"title" : "完成订单量",
											"data" : "orderFinishTotal"
										},{
											"title" : "充值总额",
											"data" : "rechargeTotal"
										},{
											"title" : "消费总额",
											"data" : "orderAccountTotal"
										}
										],
										"dom" : "<'row'<'#memberAccountAmountTool.col-xs-6'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
										},
										"drawCallback" : function(settings) {
											memberAccountAmount.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
													"targets" : [0],
													"render" : function(a,b, c, d) {
														if(a!=null&&a!=""){
															return '<span class="glyphicon member-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.memberNo+ '" >'+a+'</span>';
														}else{
															return "";
														}
														
													}
												},
												
												
												
												{
													"targets" : [2],
													"render" : function(a,b, c, d) {
														if(a>0.0){
															return '<span class="glyphicon memberAccountAmount-operate-view" style="color:#2b94fd;text-decoration: underline; cursor: pointer;" data-id="'+ c.memberNo+ '" >'+memberAccountAmount.formatCurrency(a)+'</span>';
														}else{
															return memberAccountAmount.formatCurrency(a);
														}
													}
												},
												{
													"targets" : [3],
													"render" : function(a,b, c, d) {
														if(a>0.0){
															return '<span class="glyphicon memberDepositAmount-operate-view" style="color:#2b94fd;text-decoration: underline; cursor: pointer;" data-id="'+ c.memberNo+ '" >'+memberAccountAmount.formatCurrency(a)+'</span>';
														}else{
															return memberAccountAmount.formatCurrency(a);
														}
													}
												},
											]
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
					return t.split("").reverse().join("") + "." + r;  
				} 
			};
			return memberAccountAmount;
		});
