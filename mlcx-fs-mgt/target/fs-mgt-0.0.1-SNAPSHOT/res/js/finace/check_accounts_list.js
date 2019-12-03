define([],function() {	
	var checkAccounts={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				checkAccounts.pageList();
				//查询
				$("#checkAccountsSearch").click(function(){
					var form = $("form[name=checkAccountsSearchForm]");
					var createTime =  form.find("input[name=createTime]").val();
					var finishTime = form.find("input[name=finishTime]").val();
					if(createTime!=""&&finishTime!=""){
						if(new Date(createTime)>new Date(finishTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							checkAccounts.pageList();
						}
					}else{
						checkAccounts.pageList();
					}
	            });
				$("#closecheckAccountsView").click(function(){
					closeTab("发票开票详情");
					checkAccounts.pageList();
	            });
				$("#checkAccountsBrowseModalClose").click(function(){
					$("#checkAccountsBrowseModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".checkAccounts-operate-view").each(function(){
					$(this).on("click",function(){
						var memberNo=$(this).data("id");
						var form = $("form[name=checkAccountsSearchForm]");
	       				var checkDateRange1=form.find("input[name=createTime]").val()+" 00:00:00";
						var checkDateRange2=form.find("input[name=finishTime]").val()+" 23:59:59";
						addTab("财务对账明细",checkAccounts.appPath+ "/checkAccounts/toCheckAccountsView.do?memberNo="+memberNo+"&checkDateRange1="+checkDateRange1+"&checkDateRange2="+checkDateRange2);
					});
				});
	        },
			pageList:function () {
				var checkAccountsBtnTpl = $("#checkAccountsBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(checkAccountsBtnTpl);
				$('#checkAccountsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": checkAccounts.appPath+'/checkAccounts/pageListCheckAccounts.do',
						"data": function ( d ) {
							var form = $("form[name=checkAccountsSearchForm]");
							var createTime=form.find("input[name=createTime]").val();
							var finishTime=form.find("input[name=finishTime]").val();
							var memberName=form.find("input[name=memberName]").val();
							var mobilePhone=form.find("input[name=mobilePhone]").val();
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"checkDateRange1":form.find("input[name=createTime]").val()+ " 00:00:00",
								"checkDateRange2":form.find("input[name=finishTime]").val()+ " 23:59:59",
								"memberName":memberName,
								"mobilePhone":mobilePhone,
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			            { "title":"对账周期","data": "checkDateRange1" },
						{ "title":"客户姓名","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"订单数","data": "orderNum"},
						{ "title":"订单金额(元)","data": "orderAmount"},
						{ "title":"应付金额(元)","data": "payableAmount"},
						{ "title":"已付金额(元)","data": "alreadyPayAmount"},
						{ "title":"开票金额(元)","data": "invoiceAmount"},
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#checkAccountsTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#checkAccountsTools").html("");
					   $("#checkAccountsTools").removeClass("col-xs-6");
					   $("#checkAccountsTools").append('<button type="button" class="btn-new-type checkAccountsTools-operate-export">导出</button>');
		       			$(".checkAccountsTools-operate-export").on("click",function(){
		       				var form = $("form[name=checkAccountsSearchForm]");
		       				var createTime=form.find("input[name=createTime]").val();
							var finishTime=form.find("input[name=finishTime]").val();
							var memberName=form.find("input[name=memberName]").val();
							var mobilePhone=form.find("input[name=mobilePhone]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = checkAccounts.appPath+ "/checkAccounts/toCheckAccountsExport.do?memberName="+memberName+"&mobilePhone="+mobilePhone+"&checkDateRange1="+createTime+" 00:00:00"+"&checkDateRange2="+finishTime+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						checkAccounts.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [0],
						    "render": function(a,b,c,d) {//1、增值税普通发票，2、增值税专用发票
						    	if(c.checkDateRange1!=null&&
						    			c.checkDateRange2!=null){
						    		var checkDateRange1 = moment(c.checkDateRange1);
						    		var checkDateRange2 = moment(c.checkDateRange2); 
						        	return checkDateRange1.format('YYYY-MM-DD')+"——"+checkDateRange2.format('YYYY-MM-DD');
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [4,5,6,7],
						    "render": function(a,b,c,d) {//0、未开票，1、已开票
						    	if(a){
		    	            		return "<span style='color:#2b94fd'>" + checkAccounts.formatCurrency(a) + "</span>";
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},
						{
							"targets": [8],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon checkAccounts-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'+c.memberNo+'"  data-toggle="tooltip" data-placement="top" title="查看">查看</span>';
								return view;
							}
						}
					]
				});
			},formatCurrency :function (s,n) { 
	        	n = n > 0 && n <= 20 ? n : 2;  
		        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
		        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
		        t = "";  
		        for (i = 0; i < l.length; i++) {  
		            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		        }  
		        return t.split("").reverse().join("") + "." + r;  }
	    };
	return checkAccounts;
});


