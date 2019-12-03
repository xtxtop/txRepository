define(
		[],
		function() {
			var companyPerson = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#companyPersonSearch").click(function() {
						companyPerson.pageList();
					});
					//关闭详情页
					$("#closeCompanyPerson").click(function(){
						closeTab("集团人员详情");
						companyPerson.pageList();
		            });
					
					// 列表页面分页方法调用
					companyPerson.pageList();
				},
				
				operateColumEvent : function() {
					$(".companyPerson-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("集团人员详情",companyPerson.appPath+ "/companyPerson/toCompanyPersonView.do?companyPersonNo="+$(this).data("id"));
						});
					});
					
					
					$(".companyPerson-operate-detel").each(function() {
						$(this).on("click", function() {
							var id=$(this).data("id");
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
								if(result){
										 $.ajax({
								    			url:companyPerson.appPath+"/companyPerson/deleteCompanyPerson.do",//删除集团人员
								    			type:"post",
								    			data:{companyPersonNo:id},
								    			dataType:"json",
								    			success:function(res){
								    				var code=res.code;
								    				if (code == "1") {
														bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除集团人员操作成功！", function() {
															$("#companyPersonList").DataTable().ajax.reload(function(){});
														});
													} else {
														bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除集团人员操作失败！");
													}
								    			}
									}); 
								}
		       				});
						});
					});
					
				},
				pageList : function() {
					var form = $("form[name=companyPersonSearchForm]");
					var companyName = form.find("input[name='companyName']").val();
					var name = form.find("input[name='name']").val();
					var mobilePhone = form.find("input[name='mobilePhone']").val();
					var registerStatus = form.find("select[name='registerStatus']").val();
					
					var companyPersonBtnTpl = $("#companyPersonBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(companyPersonBtnTpl);
					var table = $('#companyPersonList').dataTable({
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : companyPerson.appPath
													+ "/companyPerson/pageListCompanyPerson.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "companyName" :companyName,
																 "name" :name,
																 "mobilePhone" :mobilePhone,
																 "registerStatus" :registerStatus
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
										},             
										{
											"title" : "集团名称",
											"data" : "companyName"
										},{
											"title" : "姓名",
											"data" : "name"
										}, {
											"title" : "类型",
											"data" : "personType"
										}, {
											"title" : "手机号",
											"data" : "mobilePhone"
										}, {
											"title" : "导入时间",
											"data" : "importTime"
										}, {
											"title" : "注册情况",
											"data" : "registerStatus"
										},{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom" : "<'row'<'#companyPersonTool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#companyPersonTool").html("");
											$("#companyPersonTool").removeClass("col-xs-6");
											$("#companyPersonTool").append('<button type="button" class="btn-new-type companyPersonTool-operate-import">导入</button>');
							       			$("#companyPersonTool").append('<button type="button" class="btn-new-type companyPersonTool-operate-export">导出模板</button>');
							       			/**
							       			 * 导出
							       			 */
							       			$(".companyPersonTool-operate-export").on("click",function(){
							       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出模板吗？", function(result) {
													if(result){
														window.location.href = companyPerson.appPath+ "/companyPerson/downloadExcelFile.do?filepath=company&newFileName=companyPerson.xls";
													}						
							       				});
							       			});
							       			/**
							       			 * 导入
							       			 */
							       			$(".companyPersonTool-operate-import").on("click",function(){
							       				bootbox.dialog({
							       			        title: "导入",
							       			        dataType: "json",
							       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='importForm' name='importForm' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
							       			        buttons: {
							       			            "success": {
							       			                "label": "<i class='icon-ok'></i> 保存",
							       			                "className": "	btn-sm btn-success",
							       			                "callback": function() {
							       			                    var importForm = $("form[name='importForm']");
							       			                    importForm.ajaxSubmit({
							       			                        url: companyPerson.appPath+ "/companyPerson/importCompanyPerson.do",
							       			                        type: "post",
							       			                        success: function(res) {
							       			                        	if (res.code != 1) {
							       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
							       			                        	} else {
							       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！"); 
							       			                        		$("#companyPersonList").DataTable().ajax.reload(function(){
							       			                        		});
							       			                        	}
							       			                        }, 
							       			                        error: function(res) {
							       			                        	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据异常，请检查数据完整性！"); 
							       			                        }
							       			                    });
							       			                }
							       			            },
							       			            "cancel": {
							       			                "label": "<i class='icon-info'></i> 取消",
							       			                "className": "btn-sm btn-default"
							       			            }
							       			            
							       			        }
							       			    })
							       			});
										},
										"drawCallback" : function(settings){
											companyPerson.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
													"targets" : [ 3 ],
													"render" : function(a,
															b, c, d) {
														var ptype = "";//类型（1、员工2、非员工）
														if(a==1){
															ptype="员工";
														}else if(a==0){
															ptype="非员工";
														}
														return ptype;
													}
												},
												{
													"targets" : [5],
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
													"targets" : [ 6 ],
													"render" : function(a,b, c, d) {
														var status;//是否启用状态（1、未注册，2、已注册)
														if(a!=null){
															if(a==0){
																status="未注册";
															}else if(a==1){
																status="已注册";
															}
															return status;
														}else{
															return "";
														}
														
													}
												},
												{
													targets : 7,
													render : function(a, b, c,d) {
														
														var view = '<span class="glyphicon companyPerson-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.id
																+ '" >查看</span>';

														var detele = '<span class="glyphicon companyPerson-operate-detel" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.id
																+ '" >删除</span>';
														return view + detele;
													}
												}]
									});
				},
			};
			return companyPerson;
		});
