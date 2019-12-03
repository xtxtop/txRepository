define([],function() {	
	var accountBalance={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				accountBalance.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#accountBalanceSearch").click(function(){
					accountBalance.pageList();
	            });
				$("#closeaccountBalanceView").click(function(){
					closeTab("详情");
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".accountBalance-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("详情",accountBalance.appPath+ "/accountBalance/toAccountBalanceView.do?memberNo="+$(this).data("id"));
					});
				});
	        	$(".accountBalance-operate-edit").each(function(){
	        		$(this).on("click",function(){
	        			addTab("充值",accountBalance.appPath+ "/accountBalance/toAccountBalanceEdit.do?memberNo="+$(this).data("id"));
	        		});
	        	});
                $(".accountBalance-operate-record").each(function(){
                    $(this).on("click",function(){
                    	var memberNo = $(this).data("id");
                    	$.post("accountBalance/checkAccountBalance.do",{memberNo:memberNo},function(res){	
							if(res.code==1){
								 addTab("充值记录",accountBalance.appPath+ "/accountBalance/toaccountRecord.do?memberNo="+memberNo);
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
							}
						});
                    });
                });
	        },
			pageList:function () {	
				var accountBalanceBtnTpl = $("#accountBalanceTpl").html();
				// 预编译模板
				var template = Handlebars.compile(accountBalanceBtnTpl);
				$('#accountBalanceList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": accountBalance.appPath+'/accountBalance/getPageFinderAccountBalanceList.do',
						"data": function ( d ) {
							var form = $("form[name=accountBalanceSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberNo":$.trim(form.find("input[name=memberNo]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							//alert(JSON.stringify(json.data))
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			             { "title":"会员编号","data": "memberNo" ,"defaultContent":"--"},
						{ "title":"姓名","data": "memberName" ,"defaultContent":"--"},
						{ "title":"手机号","data": "mobilePhone" ,"defaultContent":"--"},
						{ "title":"会员类型","data": "memberType","defaultContent":"--"},
						{ "title":"充电余额(元)","data": "chargingBalance","defaultContent":"--"},
						{ "title":"停车余额(元)","data": "stopBalance","defaultContent":"--"},
						{ "title":"注册时间","data": "registerTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#accountBalanceTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					},
					"drawCallback": function( settings ) {
						accountBalance.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
						{
							"targets" : [ 6],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD');
								}else{
									return "--";
								}
							}
						},  
								  
						{
						    "targets": [3],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "普通会员";
						        	}else if(a==1){
						        		return "集团会员";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [7],
							"render": function (a, b, c, d) {
                                var view='<span class="glyphicon accountBalance-operate-view" data-id="'+c.memberNo+'" data-toggle="tooltip" data-placement="top">详情</span>';
                                var edit='<span class="glyphicon accountBalance-operate-edit" data-id="'+c.memberNo+'" data-toggle="tooltip" data-placement="top">充值</span>';
                                var record='<span class="glyphicon accountBalance-operate-record" data-id="'+c.memberNo+'" data-toggle="tooltip" data-placement="top">充值记录</span>';
								return view+record+edit;
							}
						}
					]
				});
			},
			
	    };
	return accountBalance;
});


