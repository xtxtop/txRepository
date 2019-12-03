define([],
	function() {
		var merchantUser = {
			appPath : getPath("app"),
			init : function() {
				// 列表页面搜索调用
				$("#merchantUserSearch").click(function() {
					merchantUser.pageList();
				});
				//列表页面分页方法调用
				merchantUser.pageList();
				//审核提交
				$("#merchantUserCencorFormBtn").click(function(){
					merchantUser.cencorFormSub();
	            });
				//审核取消
				$("#merchantUserCencorCancelBtn").click(function(){
					$("#cencorMerchantUserModal").modal("hide");
	            });
				
				$("#cencorMerchantUserModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=cencorMerchantUserForm]");
	            	form.resetForm();
	            	$("#cencorMerchantUserMemo").text("");
	            	form.find("input[name=userNo]").val("");
	            });
				//移入黑名单提交
				$("#merchantUserInBlackConfirmBtn").click(function(){
					merchantUser.onFormSub();
				});
				//移入黑名单取消
				$("#merchantUserInBlackCancel").click(function(){
					$("#merchantUserInBlacklistModal").modal("hide");
				});
				//移出黑名单提交
				$("#merhcantUserOutBlackConfirmBtn").click(function(){
					merchantUser.offFormSub();
				});
				//移出黑名单取消
				$("#merhcantUserOutBlackCancelBtn").click(function(){
					$("#merhcantUserOutBlacklistModel").modal("hide");
				});
				$("#merchantUserInBlacklistModal").on("hidden.bs.modal", function() {  
					var form = $("form[name=merchantUserInBlacklistForm]");
					form.resetForm();
					$("#merchantUserInBlacklistMemo").text("");
					form.find("input[name=userNo]").val("");
				});
				$("#merhcantUserOutBlacklistModel").on("hidden.bs.modal", function() {  
					var form = $("form[name=merhcantUserOutBlackConfirmBtn]");
					form.resetForm();
					$("#merhcantUserOutBlackListMemo").text("");
					form.find("input[name=userNo]").val("");
				});
			},
			//审核操作
			cencorFormSub: function () {
	        	var form = $("form[name=cencorMerchantUserForm]");
				form.ajaxSubmit({
	    			url:merchantUser.appPath+"/merchantUser/editMerchantUser.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#cencorMerchantUserModal").modal('hide')
							});
							$("#merchantUserList").DataTable().ajax.reload(function(){
    						}); 
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						var cencorMemo = form.find("textarea[name='cencorMemo']").val();
						if(cencorMemo==""){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入备注！");
							return false;
						}
					}
				});
			},
			//移入黑名单操作
	        onFormSub: function () {
	        	var form = $("form[name=merchantUserInBlacklistForm]");
				form.ajaxSubmit({
	    			url:merchantUser.appPath+"/merchantUser/editMerchantUser.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#merchantUserInBlacklistModal").modal('hide')
							});
							$("#merchantUserList").DataTable().ajax.reload(function(){
    						}); 
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					}
				});
			},
			 //移出黑名单操作
			offFormSub: function () {
	        	var form = $("form[name=merhcantUserOutBlacklistForm]");
				form.ajaxSubmit({
					url:merchantUser.appPath+"/merchantUser/editMerchantUser.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
						  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#merhcantUserOutBlacklistModel").modal('hide')
							});
						  $("#merchantUserList").DataTable().ajax.reload(function(){
    						}); 
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					}
				});
			},
			operateColumEvent : function() {
				$(".merchantUser-operate-edit").each(function() {
					$(this).on("click", function() {
						addTab("编辑商家用户",merchantUser.appPath+ "/merchantUser/toMerchantUserEdit.do?userNo="+$(this).data("id"));
					});
				});
				$(".merchantUser-operate-updatePwd").each(function() {
					$(this).on("click", function() {
						addTab("编辑商家用户",merchantUser.appPath+ "/merchantUser/toMerchantUserEdit.do?userNo="+$(this).data("id"));
					});
				});
				//审核弹出层
				$(".merchantUser-operate-cencor").each(function(id,obj){
					$(this).on("click",function(){
						var userNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: merchantUser.appPath+"/merchantUser/getMerchantUser.do",
				             data: {userNo:userNo},
				             dataType: "json",
				             success: function(result){
				            	 var data = result.data;
				            	 var code = result.code;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#cencorMerchantUserModal").modal("show");
				            	    var form = $("form[name=cencorMerchantUserForm]");
									form.find("input[name='userNo']").val(data.userNo);
									$("#cencorMerchantUserMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将租赁商："+"“"+data.userName+"”"+" 审核吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
				//移入黑名单弹出层
				$(".merchantUser-operate-inBlacklist").each(function(id,obj){
					$(this).on("click",function(){
						var userNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: merchantUser.appPath+"/merchantUser/getMerchantUser.do",
				             data: {userNo:userNo},
				             dataType: "json",
				             success: function(result){
				            	 var data = result.data;
				            	 var code = result.code;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#merchantUserInBlacklistModal").modal("show");
				            	    var form = $("form[name=merchantUserInBlacklistForm]");
									form.find("input[name='userNo']").val(data.userNo);
									$("#merchantUserInBlacklistMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将商家用户："+"“"+data.userName+"”"+"移入黑名单吗？移入黑名单后，用户将不可用");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
				//移出黑名单弹出层
				$(".merchantUser-operate-outBlacklist").each(function(id,obj){
					$(this).on("click",function(){
						var userNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: merchantUser.appPath+"/merchantUser/getMerchantUser.do",
				             data: {userNo:userNo},
				             dataType: "json",
				             success: function(result){
				            	 var data = result.data;
				            	 var code = result.code;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#merhcantUserOutBlacklistModel").modal("show");
				            	    var form = $("form[name=merhcantUserOutBlacklistForm]");
				            	    form.find("input[name='userNo']").val(data.userNo);
									$("#merhcantUserOutBlackListMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将商家用户:"+"“"+data.userName+"”"+" 移出黑名单吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
			},
			pageList : function() {
				var form = $("form[name=merchantUserSerachForm]");
				var merchantId = form.find("select[name='merchantId']").val();
				var userName = $.trim(form.find("input[name='userName']").val());
				var mobilePhone = $.trim(form.find("input[name='mobilePhone']").val());
				var censorStatus = form.find("select[name='censorStatus']").val();
				var isBlacklist = form.find("select[name='isBlacklist']").val();
				var merchantUserListTpl = $("#merchantUserListTpl").html();
				
				// 预编译模板
				var template = Handlebars.compile(merchantUserListTpl);
				var table = $('#merchantUserList').dataTable({
					searching : false,
					destroy : true,
					"ajax" : {
						"type" : "POST",
						"url" : merchantUser.appPath + "/merchantUser/pageListMerchantUser.do",
						"data" : function(d) {
							return $.extend({},d,
									{"pageNo" : (d.start / d.length) + 1,
									 "pageSize" : d.length,
									 "merchantId" :merchantId,
									 "userName":userName,
									 "mobilePhone" : mobilePhone,
									 "censorStatus":censorStatus,
									 "isBlacklist":isBlacklist
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
							"title" : "用户名",
							"data" : "userName"
						},{
							"title" : "联系电话",
							"data" : "mobilePhone"
						},{
							"title" : "性别",
							"data" : "sex"
						},{
							"title" : "城市",
							"data" : "cityName"
						},{
							"title" : "租赁商",
							"data" : "merchantName"
						},{
							"title" : "审核状态",
							"data" : "censorStatus"
						},{
							"title" : "审核备注",
							"data" : "cencorMemo"
						},{
							"title" : "是否黑名单 ",
							"data" : "isBlacklist"
						},{
							"title" : "黑名单备注 ",
							"data" : "blacklistMemo"
						},{
							"title" : "更新时间",
							"data" : "updateTime"
						},{
							"title" : "操作",
							"orderable" : false
						} 
					],
					"dom" : "<'row'<'#merchantUsertool'><'col-xs-6'f>r>"
							+ "t"
							+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					initComplete : function() {
						$("#merchantUsertool").html("");
			       		$("#merchantUsertool").append('<button type="button" class="btn-new-type merchantUsertool-operate-add">新增</button>');
			       		$(".merchantUsertool-operate-add").on("click",function(){
			       			addTab("新增商家用户", merchantUser.appPath+ "/merchantUser/toMerchantUserAdd.do");
		       			});
					},
					"drawCallback" : function(settings) {
						merchantUser.operateColumEvent();
					},
					"order" : [ [ 1, "desc" ] ],
					"columnDefs" : [
						{
							"targets" : [ 2 ],
							"render" : function(a,b, c, d) {
								var sex1;
								if(c.sex==1){
									sex1="男";
								}else if(c.sex==0){
									sex1="女";
								}else{
									sex1="未知";
								}
								return sex1;
							}
						},
						{
							"targets" : [ 6 ],
							"render" : function(a,b, c, d) {
								var cencorStatus;
								//（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
								if(a==0){
									cencorStatus="未审核"
								}else if(a==1){
									cencorStatus="已审核"
								}else if(a==2){
									cencorStatus="待审核"
								}else if(a==3){
									cencorStatus="审核未通过"
								}else{
									return "";
								}
								return cencorStatus;
							}
						},
						{
							"targets" : [ 7 ],
							"orderable" : false,
							"render" : function(a,b, c, d) {
								var isBlacklist1 ;
								//是否黑名单（0、非黑名单，1、黑名单，默认0）
								if(c.isBlacklist==0){
									isBlacklist1="正常"
								}
								if(c.isBlacklist==1){
									isBlacklist1="黑名单"
								}
								return isBlacklist1;
							}
						},
						{
							"targets" : [ 9 ],
							"orderable" : false,
							"render" : function(a,b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD HH:mm:ss');
								}else{
									return "";
								}
							}
						},
						{
							targets : [ 10 ],
							render : function(a, b, c,d) {
								var cencor = '<span class="glyphicon merchantUser-operate-cencor" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.userNo+ '" >审核</span>';;
								var edit = '<span class="glyphicon merchantUser-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.userNo+ '" >编辑</span>';
								var inBlacklist = '<span class="glyphicon merchantUser-operate-inBlacklist" style="text-decoration: underline; cursor: pointer;"data-id="'+c.userNo+'" >移入黑名单</span>';
	        					var outBlacklist = '<span class="glyphicon merchantUser-operate-outBlacklist" style="text-decoration: underline; cursor: pointer;"data-id="'+c.userNo+'" >移出黑名单</span>';
	        					var updatePwd = '<span class="glyphicon merchantUser-operate-updatePwd" style="text-decoration: underline; cursor: pointer;"data-id="'+c.userNo+'" >修改密码</span>';
								if(c.censorStatus==2||c.censorStatus==0){
									return edit + cencor;
								}else if(c.censorStatus==1&&c.isBlacklist==0){
									return inBlacklist+updatePwd;
								}else if(c.censorStatus==1&&c.isBlacklist==1){
									return outBlacklist+updatePwd;
								}else{
									return edit;
								}
							}
						}]
				});
			},
		};
		return merchantUser;
	});
