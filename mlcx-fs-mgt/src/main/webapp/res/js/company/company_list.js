define(
		[],
		function() {
			var company = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#companySearch").click(function() {
						company.pageList();
					});
					//集团审核取消
					$("#cencorCompanyCancel").click(function(){
						$("#cencorCompanyModel").modal("hide");
		            });
					//集团启用提交
					$("#enableCompanyBtn").click(function(){
						company.enableCompanyFormSub();
		            });
					//集团停用取消
					$("#enableCompanyCancel").click(function(){
						$("#enableCompanyModel").modal("hide");
		            });
					
					//集团停用提交
					$("#disableCompanyBtn").click(function(){
						company.disableCompanyFormSub();
		            });
					//集团停用取消
					$("#disableCompanyCancel").click(function(){
						$("#disableCompanyModel").modal("hide");
		            });
					
					$("#enableCompanyModel").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=enableCompanyForm]");
		            	form.resetForm();
		            	$("#enableCompanyMemo").text("");
		            });
					$("#disableCompanyModel").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=disableCompanyForm]");
		            	form.resetForm();
		            	$("#disableCompanyMemo").text("");
		            });
					// 列表页面分页方法调用
					company.pageList();
				},
				//启用操作
				enableCompanyFormSub: function () {
		        	var form = $("form[name=enableCompanyForm]");
					form.ajaxSubmit({
		    			url:company.appPath+"/company/enableCompany.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								$("#enableCompanyModel").modal("hide");
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
									$("#companyList").DataTable().ajax.reload(function(){});
								});
							}
						}
					});
				},
				//停用操作
				disableCompanyFormSub: function () {
		        	var form = $("form[name=disableCompanyForm]");
					form.ajaxSubmit({
		    			url:company.appPath+"/company/enableCompany.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								$("#disableCompanyModel").modal("hide");
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
									$("#companyList").DataTable().ajax.reload(function(){});
								}); 
							}
						}
					});
				},
				operateColumEvent : function() {
					$(".company-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("集团详情",company.appPath+ "/company/getCompanyView.do?companyId="+$(this).data("id"));
						});
					});
					$(".company-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("集团编辑",company.appPath+ "/company/toUpdateCompany.do?companyId="+$(this).data("id"));
						});
					});
					//审核
					$(".company-operate-cencor").each(function(id){
						$(this).on("click",function(){
							addTab("集团审核",company.appPath+ "/company/toCencorCompany.do?companyId="+$(this).data("id"));
						});
					});
					//启用弹出层
					$(".company-operate-enable").each(function(id){
						$(this).on("click",function(){
							var companyId=$(this).data("id");
							$.ajax({
								 url : company.appPath+ "/company/getCompanyByCompanyId.do?companyId="+companyId,
					             success: function(res){
					            	 if(res.code=="1"){
					            		 $("#enableCompanyModel").modal("show");
										 $("#enableCompanyNo").val(companyId);
										 $("#enableCompanyMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将集团“"+res.data.companyName+"”启用吗？");
					                 }
					             }
							});
						});
					});
					//停用弹出层
					$(".company-operate-disable").each(function(id){
						$(this).on("click",function(){
							var companyId=$(this).data("id");
							$.ajax({
								 url : company.appPath+ "/company/getCompanyByCompanyId.do?companyId="+companyId,
					             success: function(res){
					            	 if(res.code=="1"){
					            		$("#disableCompanyModel").modal("show");
										$("#disableCompanyNo").val(companyId);
										$("#disableCompanyMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将集团“"+res.data.companyName+"”停用吗？停用将不再有以该集团用车的记录！");
					                 }
					             }
							});
						});
					});
				},
				pageList : function() {
					var form = $("form[name=companySearchForm]");
					var companyName = form.find("input[name='companyName']").val();
					var cencorStatus = form.find("select[name='cencorStatus']").val();
					var companyStatus = form.find("select[name='companyStatus']").val();
					
					var companyBtnTpl = $("#companyBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(companyBtnTpl);
					var table = $('#companyList').dataTable({
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : company.appPath
													+ "/company/pageListCompany.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "companyName" :companyName,
																 "cencorStatus":cencorStatus,
																 "companyStatus":companyStatus
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
										{ "title":"","data": "companyId","width":"5px"},
										{
											"title" : "集团名称",
											"data" : "companyName"
										}, {
											"title" : "城市",
											"data" : "cityName"
										}, {
											"title" : "联系人",
											"data" : "contactPerson"
										}, {
											"title" : "联系电话",
											"data" : "contactTel"
										}, {
											"title" : "联系邮箱",
											"data" : "email"
										}, {
											"title" : "审核状态",
											"data" : "cencorStatus"
										}, {
											"title" : "审核时间",
											"data" : "cencorTime"
										}, {
											"title" : "状态",
											"data" : "companyStatus"
										}, {
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#companyTool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											 $("#companyTool").html("");
											$("#companyTool").removeClass("col-xs-6");
											$("#companyTool").append('<button type="button" class="btn-new-type companyTool-operate-addCompany">新增</button>');
									       		$("#companyTool").append('<button type="button" class="btn-new-type companyTool-operate-addPricePackOrder">添加套餐</button>');
								       			$(".companyTool-operate-addCompany").on("click",function(){
								       				addTab("新增集团", company.appPath+ "/company/toAddCompany.do");
								       			});
								       			$(".companyTool-operate-addPricePackOrder").on("click",function(){
									       			var ids=[];
													var len=$('#companyList tbody input[type="checkbox"]:checked');
													if(len.length==0){
														bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择集团！")
													}else{
														$("#companyList tbody").find("input:checked").each(function(){
															if($(this).data("status")==1){
																	$('#companyList tbody input[type="checkbox"]:checked').prop("checked",false);
															}else{
																if($(this).data("availabel")==2){
																	$('#companyList tbody input[type="checkbox"]:checked').prop("checked",false);
																}else{
																	ids.push($(this).val());
																}
															}
								        				});
														var len=$('#companyList tbody input[type="checkbox"]:checked');
														if(len.length==0){
															bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请重新选择集团，您之前所选择的存在未审核或停用的集团！")
														}else{
															addTab("集团新增套餐", company.appPath+ "/company/toPricingPackOrderAdd.do?companyIds="+ids);
														}
													}
													$('#companyList thead input[type="checkbox"]').on("click",function(e){
																if(this.checked){
										        			         $('#companyList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
										        			      } else {
										        			         $('#companyList tbody input[type="checkbox"]:checked').prop("checked",false);
										        			      }	
								        			});
													
								       			});
										},
										"drawCallback" : function(settings){
											company.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
												"targets" : [0],
												 "orderable":false,
												"render" : function(data, type, full, meta) {
													  return '<input type="checkbox" name="memberNo" value="' + data + '" data-status="'+full.cencorStatus+'" data-availabel="'+full.companyStatus+'">';
												}
											   },{
													"targets" : [ 6 ],
													"render" : function(a,
															b, c, d) {
														var dStatus;//审核状态（1、未审核2、待审核3、已审核4、不通过）
														if(a==1){
															dStatus="未审核";
														}else if(a==2){
															dStatus="待审核";
														}else if(a==3){
															dStatus="已审核";
														}else{
															dStatus="不通过";
														}
														return dStatus;
													}
												},
												{
													"targets" : [ 7 ],
													"render" : function(a,
															b, c, d) {
														if(a!=null){
															var now = moment(a);
															return now.format('YYYY-MM-DD');
														}else{
															return "";
														}
													}
												},
												{
													"targets" : [ 8 ],
													"render" : function(a,b, c, d) {
														var available;//是否启用状态（1、启用，2、停用)
														if(c.companyStatus==2){
															available="停用";
														}else if(c.companyStatus==1){
															available="启用";
														}
														return available;
													}
												},
												{
													targets : 9,
													render : function(a, b, c,
															d) {
														var result = "";	//定义返回返回结果集
														var start = "";
														var stop = "";
														var cencor = "";
														var view = '<span class="glyphicon company-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.companyId
																+ '" >查看</span>';
														var edit = '<span class="glyphicon company-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.companyId
																+ '" >编辑</span>';
														if(c.cencorStatus==1){
															cencor = '<span class="glyphicon company-operate-cencor" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.companyId
																+ '" >审核</span>';
														}
														if(c.cencorStatus==3){
															if(c.companyStatus==2){
																start = '<span class="glyphicon company-operate-enable" style="text-decoration: underline; cursor: pointer;" data-id="'
																	+ c.companyId
																	+ '" >启用</span>';
															}
															if(c.companyStatus==1){
																start = '<span class="glyphicon company-operate-disable" style="text-decoration: underline; cursor: pointer;" data-id="'
																	+ c.companyId
																	+ '" >停用</span>';
															}
														}
														if(c.cencorStatus==1){
															result = view + edit + cencor + start + stop;
														}else if(c.cencorStatus==3){
															result = view + start + stop;
														}
														return result;
													}
												}]
									});
				},
			};
			return company;
		});
