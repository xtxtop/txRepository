define([],function() {
			var memberLevel = {
				appPath : getPath("app"),
				init : function() {
					$("#closeMemberLevelView").click(function(){
						closeTab("会员等级详情");
						memberLevel.pageList();
					});
					// 列表页面搜索调用
					$("#memberLevelSearch").click(function() {
						var form = $("form[name=memberLevelSerachForm]");
						var createTimeStart =  form.find("input[name=createTimeStart]").val();
						var createTimeEnd = form.find("input[name=createTimeEnd]").val();
						if(createTimeStart!=""&&createTimeEnd!=""){
							if(new Date(createTimeStart)>new Date(createTimeEnd)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{
								memberLevel.pageList();
							}
						}else{
							memberLevel.pageList();
						}
					});
					// 列表页面分页方法调用
					memberLevel.pageList();

				},
				operateColumEvent : function() {
					$(".memberLevel-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("会员等级详情",memberLevel.appPath+ "/memberLevel/toMemberLevelView.do?memberLevelId="+$(this).data("id"));
						});
					});
					$(".memberLevel-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("会员等级编辑",memberLevel.appPath+ "/memberLevel/toUpdateMemberLevel.do?memberLevelId="+$(this).data("id"));
						});
					});
					$(".memberLevel-operate-del").each(function() {
						$(this).on("click", function() {
							var id=$(this).data("id");
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
	       						if(result){
	       						 $.ajax({
	    				    			url:memberLevel.appPath+"/memberLevel/delMemberLevel.do",//删除等级
	    				    			type:"post",
	    				    			data:{memberLevelId:id},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){ 
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "会员等级删除操作成功", function() {
	    				    							$("#memberLevelList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "会员等级删除操作失败");
	    				    					}
	    				    				}
	    				    			});
	       						}
	       					});	
						});
					});
				},
				pageList : function() {
					var form = $("form[name=memberLevelSerachForm]");
					var levelName = $.trim(form.find("input[name='levelName']").val());
					var isAvailable=form.find("select[name='isAvailable']").val();
					var createTimeStart = $.trim(form.find("input[name='createTimeStart']").val());
					var createTimeEnd = $.trim(form.find("input[name='createTimeEnd']").val());
					var memberLevelBtnTpl = $("#memberLevelBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(memberLevelBtnTpl);
					var table = $('#memberLevelList').dataTable(
						{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : memberLevel.appPath
													+ "/memberLevel/pageListMemberLevel.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "levelName" :levelName,
																 "isAvailable" : isAvailable,
																 "createTimeStart":createTimeStart+" 00:00:00",
																 "createTimeEnd":createTimeEnd+" 23:59:59",
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
										{"title" : "等级名称","data" : "levelName"},
										{"title" : "等级折扣率","data" : "levelDiscount"},
										{"title" : "晋升所需消费额","data" : "upgradePoint"},
										{"title" : "是否可用","data" : "isAvailable"},
										{"title" : "创建时间","data" : "createTime"},
										{"title" : "操作","orderable" : false} 
										],
										"dom" : "<'row'<'#memberLeveltool'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#memberLeveltool").html("");
											$("#memberLeveltool").removeClass("col-xs-6");
											$("#memberLeveltool").append('<button type="button" class="btn-new-type memberLeveltool-operate-add">新增</button>');
								       		$(".memberLeveltool-operate-add").on("click",function(){
						        				addTab("会员等级添加", memberLevel.appPath+ "/memberLevel/toAddMemberLevel.do");
							       			});
										},
										"drawCallback" : function(settings) {
											memberLevel.operateColumEvent();
										},
										"columnDefs" : [
												{
													"targets" : [ 3 ],
													"render" : function(a,b, c, d) {
															if(a==1){
																return "可用";
															}else if(a==0){
																return "不可用";
															}else{
																return "";
															}
													}
												},{
													"targets" : [ 4 ],
													"orderable" : false,
													"render" : function(a, b, c, d) {
														if(a!=null){
															var now = moment(a);
															return now.format('YYYY-MM-DD HH:mm:ss');
														}else{
															return "";
														}
													}
												},{
													targets : 5,
													render : function(a, b, c,
															d) {
														var view = '<span class="glyphicon memberLevel-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.memberLevelId
																+ '" >查看</span>';
														var edit = '<span class="glyphicon memberLevel-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.memberLevelId
																+ '" >编辑</span>';
														var del ='';
														if(c.isAvailable==0){//不可用可删除
															del='<span class="glyphicon memberLevel-operate-del" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.memberLevelId
																+ '" >删除</span>';
														}
														return view+edit+del;
													}
												}]
									});
				},
			};
			return memberLevel;
		});
