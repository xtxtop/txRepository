define([],function() {
			var memberPointsRecord = {
				appPath : getPath("app"),
				init : function() {
					$("#closeMemberPointsRecordView").click(function(){
						closeTab("会员积分记录详情");
						memberPointsRecord.pageList();
					});
					// 列表页面搜索调用
					$("#memberPointsRecordSearch").click(function() {
						var form=$("form[name=memberPointsRecordSerachForm]");
						var createTimeStart=form.find("input[name=createTimeStart]").val();
						var createTimeEnd=form.find("input[name=createTimeEnd]").val();
						if(createTimeStart!=""&&createTimeEnd!=""){
							if(new Date(createTimeStart)>new Date(createTimeEnd)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{
								memberPointsRecord.pageList();
							}
						}else{
							memberPointsRecord.pageList();
						}
					});
					// 列表页面分页方法调用
					memberPointsRecord.pageList();
					memberPointsRecord.handleClickMore();

				},
				operateColumEvent : function() {
					$(".memberPointsRecord-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("会员积分记录详情",memberPointsRecord.appPath+ "/memberPointsRecord/toMemberPointsRecordView.do?recordId="+$(this).data("id"));
						});
					});
				},
				pageList : function() {
					var form = $("form[name=memberPointsRecordSerachForm]");
					var businessType = $.trim(form.find("select[name='businessType']").val());
					var pointsType = $.trim(form.find("select[name='pointsType']").val());
					var createTimeStart=form.find("input[name='createTimeStart']").val();
					var createTimeEnd=form.find("input[name='createTimeEnd']").val();
					var opType = $.trim(form.find("select[name='opType']").val());
					var memberName = $.trim(form.find("input[name='memberName']").val());
					var memberPointsRecordBtnTpl = $("#memberPointsRecordBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(memberPointsRecordBtnTpl);
					var table = $('#memberPointsRecordList').dataTable(
						{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : memberPointsRecord.appPath
													+ "/memberPointsRecord/pageListMemberPointsRecord.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "businessType" :businessType,
																 "pointsType" : pointsType,
																 "opType":opType,
																 "createTimeStart":createTimeStart+" 00:00:00",
																 "createTimeEnd":createTimeEnd+" 23:59:59",
																 "memberName":memberName
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
										{"title" : "操作类型","data" : "opType"},
										{"title" : "积分值","data" : "pointsValue"},
										{"title" : "业务数据","data" : "businessData"},
										{"title" : "会员姓名","data" : "memberName"},
										{"title" : "创建时间","data" : "createTime"},
										{"title" : "操作","orderable" : false} 
										],
										"dom" : "<'row'<'#memberPointsRecordtool.col-xs-6'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#memberPointsRecordtool").html("");
										},
										"drawCallback" : function(settings) {
											memberPointsRecord.operateColumEvent();
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
													"targets" : [ 2 ],
													"orderable" : false,
													"render" : function(a, b, c, d) {//操作类型
														if(a==0){
															return "扣除/使用积分";
														}else if(a==1){
															return "增加/获得积分";
														}else{
															return "";
														}
													}
												},{
													targets : 6,
													render : function(a, b, c,
															d) {
														if(a!=null){
															var now = moment(a);
															return now.format('YYYY-MM-DD HH:mm:ss');
														}else{
															return "";
														}
													}
												},{
													targets : 7,
													render : function(a, b, c,
															d) {
														var view = '<span class="glyphicon memberPointsRecord-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'
																+ c.recordId
																+ '" >查看</span>';
														return view;
													}
												}]
									});
				},
				//点击更多
				handleClickMore:function(){
					$("#moreMemberPointsRecord").click(function(){
						if($(".seach-input-details").hasClass("close-content")){
							$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
							$(".seach-input-details").removeClass("close-content");
						}else{
							$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
							$(".seach-input-details").addClass("close-content");
						}
					})
				}
			};
			return memberPointsRecord;
		});
