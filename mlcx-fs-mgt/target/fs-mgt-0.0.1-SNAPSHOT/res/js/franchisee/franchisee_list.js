define(
		[],
		function() {
			var franchisee = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#franchiseeSearch").click(function() {
						
							franchisee.pageList();
						
					});
					
					
					// 列表页面分页方法调用
					franchisee.pageList();
					franchisee.initSearchStyle();
				},
				
				operateColumEvent : function() {
					$(".franchisee-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("加盟商详情",franchisee.appPath+ "/franchisee/toFranchiseeView.do?franchiseeNo="+$(this).data("id"));
						});
					});
					$(".franchisee-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("加盟商编辑",franchisee.appPath+ "/franchisee/toFranchiseeEdit.do?franchiseeNo="+$(this).data("id"));
						});
					});
					$(".franchisee-operate-cencor").each(function() {
						$(this).on("click", function() {
							addTab("加盟商审核",franchisee.appPath+ "/franchisee/toCencorfranchisee.do?franchiseeNo="+$(this).data("id"));
						});
					});
					$(".franchisee-operate-del").each(function() {
						$(this).on("click", function() {
							var franchiseeNo = $(this).data("id");
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
								if(result){
									
									$.post("franchisee/delFranchisee.do",{franchiseeNo:franchiseeNo},function(res){	
										if(res.code==1){

											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
												  $(".bootbox").modal("hide");
												  $("#franchiseeList").DataTable().ajax.reload(function(){
						    						}); 
											  });
											
										}else{
											bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
										}
										
									});
								}						
		       				});
							
						});
					});
					
					
				},
				pageList : function() {
					var form = $("form[name=franchiseeSerachForm]");
					var franchiseeName = $.trim(form.find("input[name='franchiseeName']").val());
					var franchiseeNo = $.trim(form.find("input[name='franchiseeNo']").val());
					var censorStatus = form.find("select[name='censorStatus']").val();
				
					var franchiseeBtnTpl = $("#franchiseeBtnTpl").html();
					
					// 预编译模板
					var template = Handlebars.compile(franchiseeBtnTpl);
					var table = $('#franchiseeList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : franchisee.appPath
													+ "/franchisee/pageListFranchisee.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "franchiseeName" :franchiseeName,
																 "franchiseeNo" : franchiseeNo,
																 "censorStatus" :censorStatus
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
										}, {
											"title" : "分润比例（按车）",
											"data" : "carProportion"
										}, {
											"title" : "分润比例（按场站）",
											"data" : "parkProportion"
										}, {
											"title" : "审核状态",
											"data" : "censorStatus"
										},{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#franchiseetool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#franchiseetool").html("");
											$("#franchiseetool").removeClass("col-xs-6");
								       		$("#franchiseetool").append('<button type="button" class="btn-new-type  franchiseetool franchiseetool-operate-add">新增</button>');
								       		$(".franchiseetool-operate-add").on("click",function(){
								       			
								       			addTab("加盟商新增", franchisee.appPath+ "/franchisee/toFranchiseeAdd.do");
								       			
								       		});
								       
										},
										"drawCallback" : function(settings) {
											franchisee.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
											/*{
												"targets" : [0],
												 "orderable":false,
												"render" : function(data, type, full, meta) {
													  return '<input type="checkbox" name="franchiseeNo" value="' + data + '">';
												}
											   },*/
											{
													"targets" : [ 2,3 ],
													"render" : function(a,b, c, d) {
														if(a!=""){
															return a+"%";
														}else{
															return "0";
														}
													}
												},
												{
													"targets" : [ 4 ],
													"render" : function(a,b, c, d) {
														var sex1;
														if(c.censorStatus==0){
															sex1="未审核";
														}else if(c.censorStatus==1){
															sex1="审核已通过";
														}else if(c.censorStatus==2){
															sex1="审核未通过";
														}else{
															sex1="-";
														}
														return sex1;
													}
												},
												
												{
													targets : 5,
													render : function(a, b, c,
															d) {
														var cencor = "";
														var view = '<span class="glyphicon franchisee-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.franchiseeNo
																+ '" >查看</span>';
														var edit = '<span class="glyphicon franchisee-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.franchiseeNo
																+ '" >编辑</span>';
//														var del = '<span class="glyphicon franchisee-operate-del" style="text-decoration: underline; cursor: pointer;" data-id="'
//															+ c.franchiseeNo
//															+ '" >删除</span>';
														if(c.censorStatus==0){
															cencor = '<span class="glyphicon franchisee-operate-cencor" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.franchiseeNo
																+ '" >审核</span>';
														}
														
															return view + edit+cencor;
														
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
			return franchisee;
		});
