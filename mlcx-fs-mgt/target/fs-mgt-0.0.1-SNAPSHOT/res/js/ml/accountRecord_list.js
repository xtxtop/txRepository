define([],function() {	
	var accountRecord={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				accountRecord.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#accountRecordSearch").click(function(){
					accountRecord.pageList();
	            });
			},
			pageList:function () {	
				var accountRecordBtnTpl = $("#accountRecordTpl").html();
				// 预编译模板
				var template = Handlebars.compile(accountRecordBtnTpl);
				$('#accountRecordList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": accountRecord.appPath+'/accountBalance/toaccountRecordList.do',
						"data": function ( d ) {
							var form = $("form[name=accountRecordSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberNo":$.trim(form.find("input[name=memberNo]").val()),
								"id":$.trim(form.find("input[name=id]").val()),
								"dealType":$.trim(form.find("select[name=dealType]").val()),
								"type":$.trim(form.find("select[name=type]").val()),
								
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
			             { "title":"交易记录编号","data": "id" ,"defaultContent":"--"},
						{ "title":"姓名","data": "menberName" ,"defaultContent":"--"},
						{ "title":"手机号","data": "mobilePhone" ,"defaultContent":"--"},
						{ "title":"交易类型","data": "dealType","defaultContent":"--"},
						{ "title":"类型","data": "type","defaultContent":"--"},
						{ "title":"交易金额(元)","data": "trnasactionAmount","defaultContent":"--"},
						{ "title":"账户结余(元)","data": "balance","defaultContent":"--"},
						{ "title":"订单号","data": "orderNo","defaultContent":"--"},
						{ "title":"交易流水号","data": "payflowNo","defaultContent":"--"},
						{ "title":"支付类型","data": "payType","defaultContent":"--"},
						{ "title":"支付状态","data": "payStatus","defaultContent":"--"},
						{ "title":"交易时间","data": "createTime","defaultContent":"--"}
					],
					"dom" : "<'row'<'#accountRecordTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					},
					"drawCallback": function( settings ) {
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
						{
							"targets" : [ 11],
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
						        		return "充电";
						        	}else if(a==2){
						        		return "停车";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
						    "targets": [9],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "微信";
						        	}else if(a==1){
						        		return "支付宝";
						        	}else{
						        		return "--";	
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
						    "targets": [10],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "未支付";
						        	}else if(a==1){
						        		return "已支付";
						        	}else{
							        	return "--";
							        }
						        }else{
						        	return "--";
						        }
						    }
						},
						{
						    "targets": [4],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "充值";
						        	}else if(a==2){
						        		return "赠送";
						        	}else if(a==3){
						        		return "抵扣";
						        	}else if(a==4){
						        		return "返还";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						}
					]
				});
			},
			
	    };
	return accountRecord;
});


