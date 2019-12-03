define([],
	function() {
		var merchant = {
			appPath : getPath("app"),
			init : function() {
				// 列表页面搜索调用
				$("#merchantSearch").click(function() {
					merchant.pageList();
				});
				//列表页面分页方法调用
				merchant.pageList();
				//审核提交
				$("#merchantCencorFormBtn").click(function(){
					merchant.cencorFormSub();
	            });
				//审核取消
				$("#merchantCencorCancelBtn").click(function(){
					$("#cencorMerchantModal").modal("hide");
	            });
				//启用状态提交
				$("#merchantOnFormBtn").click(function(){
					merchant.onFormSub();
	            });
				//启用取消
				$("#merchantOnCancelBtn").click(function(){
					$("#onMerchantModal").modal("hide");
	            });
				//停用状态提交
				$("#merchantOffBtn").click(function(){
					merchant.offFormSub();
	            });
				//停用取消
				$("#merchantOffCancel").click(function(){
					$("#offMerchantModal").modal("hide");
	            });
				$("#cencorMerchantModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=cencorMerchantForm]");
	            	form.resetForm();
	            	$("#cencorMerchantMemo").text("");
	            	form.find("input[name=merchantId]").val("");
	            });
				$("#onMerchantModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onMerchantForm]");
	            	form.resetForm();
	            	$("#onMerchantMemo").text("");
	            	form.find("input[name=merchantId]").val("");
	            });
				$("#offMerchantModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offMerchantForm]");
	            	form.resetForm();
	            	$("#offMerchantMemo").text("");
	            	form.find("input[name=merchantId]").val("");
	            });
			},
			//审核操作
			cencorFormSub: function () {
	        	var form = $("form[name=cencorMerchantForm]");
				form.ajaxSubmit({
	    			url:merchant.appPath+"/merchant/editMerchants.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
								$("#cencorMerchantModal").modal('hide')
							});
							$("#merchantList").DataTable().ajax.reload(function(){
    						}); 
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
			//启用操作
	        onFormSub: function () {
	        	var form = $("form[name=onMerchantForm]");
				form.ajaxSubmit({
	    			url:merchant.appPath+"/merchant/editMerchants.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "启用成功！", function() {
								$("#onMerchantModal").modal('hide')
							});
							$("#merchantList").DataTable().ajax.reload(function(){
    						}); 
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					}
				});
			},
			 //停用操作
			offFormSub: function () {
	        	var form = $("form[name=offMerchantForm]");
				form.ajaxSubmit({
					url:merchant.appPath+"/merchant/editMerchants.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
						  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + " 停用成功！", function() {
								$("#offMerchantModal").modal('hide')
							});
						  $("#merchantList").DataTable().ajax.reload(function(){
    						}); 
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					}
				});
			},
			operateColumEvent : function() {
				$(".merchant-operate-edit").each(function() {
					$(this).on("click", function() {
						addTab("编辑租赁商",merchant.appPath+ "/merchant/toMerchantEdit.do?merchantId="+$(this).data("id"));
					});
				});
				
				//审核弹出层
				$(".merchant-operate-cencor").each(function(id,obj){
					$(this).on("click",function(){
						var merchantId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: merchant.appPath+"/merchant/getMerchant.do",
				             data: {merchantId:merchantId},
				             dataType: "json",
				             success: function(result){
				            	 var data = result.data;
				            	 var code = result.code;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#cencorMerchantModal").modal("show");
				            	    var form = $("form[name=cencorMerchantForm]");
									form.find("input[name='merchantId']").val(data.merchantId);
									$("#cencorMerchantMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将租赁商："+"“"+data.merchantName+"”"+" 审核吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
				
				//启用弹出层
				$(".merchant-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var merchantId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: merchant.appPath+"/merchant/getMerchant.do",
				             data: {merchantId:merchantId},
				             dataType: "json",
				             success: function(result){
				            	 var data = result.data;
				            	 var code = result.code;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#onMerchantModal").modal("show");
				            	    var form = $("form[name=onMerchantForm]");
									form.find("input[name='merchantId']").val(data.merchantId);
									$("#onMerchantMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将租赁商："+"“"+data.merchantName+"”"+" 启用吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".merchant-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var merchantId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: merchant.appPath+"/merchant/getMerchant.do",
				             data: {merchantId:merchantId},
				             dataType: "json",
				             success: function(result){
				            	 var data = result.data;
				            	 var code = result.code;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#offMerchantModal").modal("show");
				            	    var form = $("form[name=offMerchantForm]");
				            	    form.find("input[name='merchantId']").val(data.merchantId);
									$("#offMerchantMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;租赁商停用后，其下的门店也将停用，库存也将修改，您确定将租赁商:"+"“"+data.merchantName+"”"+" 停用吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
				                 }
				             }
						});
					});
				});
				//删除
				$(".merchant-operate-del").each(function(id,obj){
					$(this).on("click",function(){
						var merchantId=$(this).data("id");
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
							if(result){
								$.ajax({
									 type: "post",
						             url: merchant.appPath+"/merchant/delMerchant.do",
						             data: {merchantId:merchantId},
						             dataType: "json",
						             success: function(data){
						            	 if(data.code=="1"){
						            		 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
												  $(".bootbox").modal("hide");
												  $("#merchantList").DataTable().ajax.reload(function(){}); 
											  }); 
						                 }else{
						                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！");
						                 }
						             }
								});
							}
						})
					});
				});
			},
			pageList : function() {
				var form = $("form[name=merchantSerachForm]");
				var merchantName = $.trim(form.find("input[name='merchantName']").val());
				var cantactPerson = $.trim(form.find("input[name='cantactPerson']").val());
				var mobilePhone = $.trim(form.find("input[name='mobilePhone']").val());
				var merchantListTpl = $("#merchantListTpl").html();
				
				// 预编译模板
				var template = Handlebars.compile(merchantListTpl);
				var table = $('#merchantList').dataTable({
					searching : false,
					destroy : true,
					"ajax" : {
						"type" : "POST",
						"url" : merchant.appPath
								+ "/merchant/pageListMerchant.do",
						"data" : function(d) {
							return $.extend({},d,
									{"pageNo" : (d.start / d.length) + 1,
									 "pageSize" : d.length,
									 "merchantName" :merchantName,
									 "cantactPerson":cantactPerson,
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
							"title" : "租赁商",
							"data" : "merchantName"
						},{
							"title" : "联系人",
							"data" : "cantactPerson"
						},{
							"title" : "联系电话",
							"data" : "mobilePhone"
						},{
							"title" : "地址",
							"data" : "addrStreet"
						},{
							"title" : "联系邮箱",
							"data" : "eMail"
						},{
							"title" : "状态",
							"data" : "isAvailable"
						},{
							"title" : "异地还车 ",
							"data" : "isOffsiteReturncar"
						},{
							"title" : "审核时间",
							"data" : "cencorTime"
						},{
							"title" : "审核备注 ",
							"data" : "cencorMemo"
						},{
							"title" : "创建时间",
							"data" : "createTime"
						},{
							"title" : "分润比",
							"data" : "profitRatio"
						},{
							"title" : "操作",
							"orderable" : false
						} 
					],
					"dom" : "<'row'<'#merchanttool'><'col-xs-6'f>r>"
							+ "t"
							+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					initComplete : function() {
						$("#merchanttool").html("");
			       		$("#merchanttool").append('<button type="button" class="btn-new-type merchanttool-operate-add">新增</button>');
			       		$(".merchanttool-operate-add").on("click",function(){
			       			addTab("新增租赁商", merchant.appPath+ "/merchant/toMerchantAdd.do");
		       			});
					},
					"drawCallback" : function(settings) {
						merchant.operateColumEvent();
					},
					"order" : [ [ 1, "desc" ] ],
					"columnDefs" : [
							{
								"targets" : [ 5 ],
								"orderable" : false,
								"render" : function(a,b, c, d) {
									//是否启用（0、否，1、启用，默认0）
									if(a==1){
										return "启用";
									}else{
										return "停用";
									}
								}
							},
							{
								"targets" : [ 6 ],
								"orderable" : false,
								"render" : function(a,b, c, d) {
									//是否支持异地还车(0、不支持1、支持，默认0)
									if(a==1){
										return "支持异地还车";
									}else{
										return "不支持异地还车";
									}
								}
							},
							{
								"targets" : [ 7,9 ],
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
								targets : [ 11 ],
								render : function(a, b, c,d) {
									var cencor = cencor = '<span class="glyphicon merchant-operate-cencor" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.merchantId+ '" >审核</span>';;
									var edit = '<span class="glyphicon merchant-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
											+ c.merchantId
											+ '" >编辑</span>';
									var onList='<span class="glyphicon merchant-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.merchantId+'" >启用</span>';
		        					var offList='<span class="glyphicon merchant-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.merchantId+'" >停用</span>';
		        					var del='<span class="glyphicon merchant-operate-del" style="text-decoration: underline; cursor: pointer;"data-id="'+c.merchantId+'" >删除</span>';
									if(c.cencorStatus==2||c.cencorStatus==0){
										return edit + cencor + del;
									}else if(c.cencorStatus==1&&c.isAvailable==0){
										return edit + onList + del;
									}else if(c.cencorStatus==1&&c.isAvailable==1){
										return offList;
									}else{
										return edit;
									}
								}
							}]
				});
			},
		};
		return merchant;
	});
