define([],function() {
			var memberPointsRule = {
				appPath : getPath("app"),
				init : function() {
					$("#closeMemberPointsRuleView").click(function(){
						closeTab("会员积分规则详情");
						memberPointsRule.pageList();
					});
					// 列表页面搜索调用
					$("#memberPointsRuleSearch").click(function() {
							memberPointsRule.pageList();
					});
					// 列表页面分页方法调用
					memberPointsRule.pageList();

				},
				operateColumEvent : function() {
					$(".memberPointsRule-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("会员积分规则详情",memberPointsRule.appPath+ "/memberPointsRule/toMemberPointsRuleView.do?ruleId="+$(this).data("id"));
						});
					});
					$(".memberPointsRule-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("会员积分规则编辑",memberPointsRule.appPath+ "/memberPointsRule/toUpdateMemberPointsRule.do?ruleId="+$(this).data("id"));
						});
					});
					$(".memberPointsRule-operate-del").each(function() {
						$(this).on("click", function() {
							var id=$(this).data("id");
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
	       						if(result){
	       						 $.ajax({
	    				    			url:memberPointsRule.appPath+"/memberPointsRule/delMemberPointsRule.do",//删除活动
	    				    			type:"post",
	    				    			data:{ruleId:id},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){ 
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "会员积分规则删除操作成功", function() {
	    				    							$("#memberPointsRuleList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "会员积分规则删除操作失败");
	    				    					}
	    				    				}
	    				    			});
	       						}
	       					});	
						});
					});
				},
				pageList : function() {
					var form = $("form[name=memberPointsRuleSerachForm]");
					var businessType = $.trim(form.find("select[name='businessType']").val());
					var isAvailable=form.find("select[name='isAvailable']").val();
					var pointsType = $.trim(form.find("select[name='pointsType']").val());
					var isDefault = $.trim(form.find("select[name='isDefault']").val());
					var memberPointsRuleBtnTpl = $("#memberPointsRuleBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(memberPointsRuleBtnTpl);
					var table = $('#memberPointsRuleList').dataTable(
						{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : memberPointsRule.appPath
													+ "/memberPointsRule/pageListMemberPointsRule.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "businessType" :businessType,
																 "pointsType" : pointsType,
																 "isDefault":isDefault,
																 "isAvailable":isAvailable,
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
										{"title" : "积分业务类型","data" : "businessType"},
										{"title" : "积分类型","data" : "pointsType"},
										{"title" : "规则所对应的积分值","data" : "pointsValue"},
										{"title" : "是否是默认的规则","data" : "isDefault"},
										{"title" : "是否可用","data" : "isAvailable"},
										{"title" : "操作","orderable" : false} 
										],
										"dom" : "<'row'<'#memberPointsRuletool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#memberPointsRuletool").html("");
											$("#memberPointsRuletool").removeClass("col-xs-6");
											$("#memberPointsRuletool").append('<button type="button" class="btn-new-type memberPointsRuletool-operate-add">新增</button>');
								       		$(".memberPointsRuletool-operate-add").on("click",function(){
						        				addTab("会员积分规则添加", memberPointsRule.appPath+ "/memberPointsRule/toAddMemberPointsRule.do");
							       			});
										},
										"drawCallback" : function(settings) {
											memberPointsRule.operateColumEvent();
										},
										"columnDefs" : [
												{
													"targets" : [ 0 ],
													"render" : function(a,b, c, d) {
															if(a==1){
																return "订单支付";
															}else if(a==2){
																return "套餐支付";
															}else{
																return "";
															}
													}
												},{
													"targets" : [ 1 ],
													"orderable" : false,
													"render" : function(a, b, c, d) {//0，会员经验积分、1、可用于消费的积分
														if(a==0){
															return "会员经验积分";
														}else if(a==1){
															return "可用于消费的积分";
														}else{
															return "";
														}
													}
												},{
													"targets" : [ 3 ],
													"render" : function(a,b, c, d) {//是否是默认规则
															if(a==1){
																return "默认";
															}else if(a==0){
																return "非默认";
															}else{
																return "";
															}
													}
												},{
													"targets" : [ 4 ],
													"orderable" : false,
													"render" : function(a, b, c, d) {//是否可用
														if(a==0){
															return "不可用";
														}else if(a==1){
															return "可用";
														}else{
															return "";
														}
													}
												},{
													targets : 5,
													render : function(a, b, c,
															d) {
														var view = '<span class="glyphicon memberPointsRule-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'
																+ c.ruleId
																+ '" >查看</span>';
														var edit = '<span class="glyphicon memberPointsRule-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'
																+ c.ruleId
																+ '" >编辑</span>';
														var del = '<span class="glyphicon memberPointsRule-operate-del" style="text-decoration: underline; cursor: pointer;"data-id="'
															+ c.ruleId
															+ '" >删除</span>';
														return view+edit+del;
													}
												}]
									});
				},
			};
			return memberPointsRule;
		});
