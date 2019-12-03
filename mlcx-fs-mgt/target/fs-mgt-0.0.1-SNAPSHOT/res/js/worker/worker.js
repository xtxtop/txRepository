define([],function() {	
			var worker = {
				appPath : getPath("app"),
				init : function() {
					//关闭详情页
					$("#closeWorker").click(function(){
						closeTab("调度员详情");
		            });
					
					// 列表页面搜索调用
					$("#workerSearch").click(function() {
							worker.pageList();
					});
					//详情页面查看工单
					$("#workerOrderDetail").click(function() {
						var workerNo = $("#workerNo").val();
						addTab("调度工单列表",worker.appPath+ "/workerOrder/toWorkerOrderList.do?workerNo="+workerNo);
					});
					// 列表页面分页方法调用
					worker.pageList();
//					worker.handleClickMore();
				},
				
				operateColumEvent : function() {
					$(".worker-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("调度员详情",worker.appPath+ "/worker/toWorkerView.do?workerNo="+$(this).data("id"));
						});
					});
					$(".worker-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("调度员修改",worker.appPath+ "/worker/toWorkerEdit.do?workerNo="+$(this).data("id"));
						});
					});
					$(".worker-operate-dot").each(function() {
						$(this).on("click", function() {
							addTab("调度员网点",worker.appPath+ "/workerDot/toWorkerDotList.do?workerId="+$(this).data("id"));
						});
					});
					
				},
				pageList : function() {
					var form = $("form[name=workerSerachForm]");
					var empNo = form.find("input[name='empNo']").val();
					var workerName = form.find("input[name='workerName']").val();
					var mobilePhone = form.find("input[name='mobilePhone']").val();
					
					var workerBtnTpl = $("#workerBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(workerBtnTpl);
					var table = $('#workerList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : worker.appPath
													+ "/worker/pageListWorker.do",
											"data" : function(d) {
												return $
														.extend(
																{},
																d,
																{
																	"pageNo" : (d.start / d.length) + 1,
																	"pageSize" : d.length,
																	"empNo" : empNo,
																	"workerName" : workerName,
																	"mobilePhone" : mobilePhone
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
											"title" : "城市",
											"data" : "cityName"
										}, {
											"title" : "工号",
											"data" : "empNo"
										}, {
											"title" : "姓名",
											"data" : "workerName"
										}, {
											"title" : "手机号",
											"data" : "mobilePhone"
										}, {
											"title" : "调度端APP状态",
											"data" : "appIsOnline"
										}, {
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#workertool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#workertool").html("");
											$("#workertool").removeClass("col-xs-6");
											$("#workertool").append('<button type="button" class="btn-new-type workertool-operate-add">新增</button>');
											$("#workertool").append('<button type="button" class="btn-new-type workertool-operate-get">调度员位置</button>');
											$(".workertool-operate-add").on("click",function(){
							       				addTab("调度员新增", worker.appPath+ "/worker/toWorkerAdd.do");
							       			});	 
											$(".workertool-operate-get").on("click",function(){
							       				addTab("调度员位置", worker.appPath+ "/worker/getPositionReporting.do");
							       			});	
										},
										"drawCallback" : function(settings) {
											worker.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
													"targets" : [ 4 ],
													"orderable" : false,
													"render" : function(a,
															b, c, d) {
														if(c.appIsOnline==1){
															return "在线";
														}else{
															return "离线";
														}
													}
												},
												{
													targets : 5,
													render : function(a, b, c,
															d) {
														var view = '<span class="glyphicon worker-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.workerNo + '">查看</span>';
														var edit='<span class="glyphicon  worker-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
															+ c.workerNo + '">修改</span>';
														/*var dot = '<span class="glyphicon worker-operate-dot" style="text-decoration: underline; cursor: pointer;" data-id="'
															+ c.workerNo + '">网点</span>';*/
														return view+edit;
													}
												}]
									});
				},
				//点击更多
				handleClickMore:function(){
					$("#moreWokerList").click(function(){
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
			return worker;
		});
