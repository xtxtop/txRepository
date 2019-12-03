define([],function() {	
	var invoicerHistory={
			appPath:getPath("app"),
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				invoicerHistory.pageList();
				//查询
				$("#invoicerHistorySearch").click(function(){
					var form = $("form[name=invoicerHistorySearchForm]");
					var invoiceTimeStart1 =  form.find("input[name=invoiceTimeStart1]").val();
					var invoiceTimeEnd1 = form.find("input[name=invoiceTimeEnd1]").val();
					if(invoiceTimeStart1!=""&&invoiceTimeEnd1!=""){
						if(new Date(invoiceTimeStart1)>new Date(invoiceTimeEnd1)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开票开始时间不能大于结束时间！");
						}else{
							invoicerHistory.pageList();
						}
					}else{
						invoicerHistory.pageList();
					}
	            });
				$("#closeInvoiceHistoryView").click(function(){
					closeTab("历史开票详情");
					invoicerHistory.pageList();
	            });
				$("#invoicerHistoryBrowseModalClose").click(function(){
					$("#invoicerHistoryBrowseModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".invoicerHistory-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("历史开票详情",invoicerHistory.appPath+ "/invoice/toInvoiceHistoryView.do?invoiceId="+$(this).data("id"));
					});
				});
	        	//浏览
	        	$(".invoicerHistory-operate-browse").each(function(){
					$(this).on("click",function(){
						$("#invoicerHistoryBrowseModal").modal("show");
						$("#invoicerHistoryBrowseImg").attr("src",$(this).data("id"));	
					});
				});
	        },
			pageList:function () {	
				var invoicerHistoryBtnTpl = $("#invoicerHistoryBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(invoicerHistoryBtnTpl);
				$('#invoicerHistoryList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": invoicerHistory.appPath+'/invoice/pageListInvoiceHistory.do',
						"data": function ( d ) {
							var form = $("form[name=invoicerHistorySearchForm]");
							var lead=form.find("select[name=invoiceTimeLead]").val();
							var invoiceTimeLeadStart="";
							var invoiceTimeLeadEnd="";
							if(lead){
		        				var temp=lead.split("_");
		        				invoiceTimeLeadStart=temp[0];
		        				invoiceTimeLeadEnd=temp[1];
		        			}	
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"bizObjId":form.find("input[name=bizObjId]").val(),
								"phone":form.find("input[name=phone]").val(),
								"invoiceTitle":form.find("input[name=invoiceTitle]").val(),
								"invoiceTimeStart":form.find("input[name=invoiceTimeStart1]").val()+ " 00:00:00",
								"invoiceTimeEnd":form.find("input[name=invoiceTimeEnd1]").val()+ " 23:59:59",
								"invoiceTimeLeadStart":invoiceTimeLeadStart+ " 00:00:00",
								"invoiceTimeLeadEnd":invoiceTimeLeadEnd+ " 23:59:59",
								//对账周期，日期范围
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
			            { "title":"开票抬头","data": "invoiceTitle" },
						{ "title":"开票类型","data": "invoiceType" },
						{ "title":"开票金额(元)","data": "invoiceAmount" },
						{ "title":"订单号","data": "bizObjId"},
						{ "title":"手机号","data": "phone"},
//						{ "title":"订单金额","data": "orderAmount"},
//						{ "title":"订单时间","data": "orderTime"},
						{ "title":"开票时间","data": "invoiceTime"},
						{ "title":"开票状态","data": "invoiceStatus"},
						{ "title":"发票号","data": "invoiceNo"},
						{ "title":"纳税人认定通知书","data": "taxpayerNoticePic"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#invoicerHistoryTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#invoicerHistoryTools").html("");
					   $("#invoicerHistoryTools").css("float", "right");
					   $("#invoicerHistoryTools").removeClass("col-xs-6");
					   $("#invoicerHistoryTools").append('<button type="button" class="btn btn-default btn-sm invoicerHistoryTools-operate-export" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd" >导出</button>');
		       			$(".invoicerHistoryTools-operate-export").on("click",function(){
		       				var form = $("form[name=invoicerHistorySearchForm]");
		       				var lead=form.find("select[name=invoiceTimeLead]").val();
							var invoiceTimeLeadStart="";
							var invoiceTimeLeadEnd="";
							if(lead){
		        				var temp=lead.split("_");
		        				invoiceTimeLeadStart=temp[0];
		        				invoiceTimeLeadEnd=temp[1];
		        			}	
		       				var bizObjId=form.find("input[name=bizObjId]").val();
		       				var invoiceTitle=form.find("input[name=invoiceTitle]").val();
		       				var invoiceTimeStart=form.find("input[name=invoiceTimeStart1]").val()+ " 00:00:00";
		       				var invoiceTimeEnd=form.find("input[name=invoiceTimeEnd1]").val()+ " 23:59:59";
		       				var invoiceTimeLeadStart=invoiceTimeLeadStart+ " 00:00:00";
		       				var invoiceTimeLeadEnd=invoiceTimeLeadEnd+ " 23:59:59";
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = invoicerHistory.appPath+ "/invoice/toInvoiceExport.do?bizObjId="+bizObjId+"&&invoiceTitle="+invoiceTitle+
									"&&invoiceTimeStart="+invoiceTimeStart+"&&invoiceTimeEnd="+invoiceTimeEnd+"&&invoiceTimeLeadStart="+invoiceTimeLeadStart
									"&&invoiceTimeLeadEnd="+invoiceTimeLeadEnd+"&&invoiceStatus="+1;
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						invoicerHistory.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [1],
						    "render": function(a,b,c,d) {//1、增值税普通发票，2、增值税专用发票
						    	if(a!=null){
						        	if(a==1){
						        		return "增值税普通发票纸质版";
						        	}else if(a==2){
						        		return "增值税专用发票";
						        	}else if(a==3){
						        		return "增值税普通发票电子版";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [3],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						    		var bizObjIds=a.split(",");
						    		var html="";
						    		for(var i=0;i<bizObjIds.length;i++){
						    			html=html+bizObjIds[i]+"<br/>";
						    		}
						        	return html;
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [5],
						    "render": function(a,b,c,d) {
						    	 if(a!=null){
							        	var now = moment(a);
										return now.format('YYYY-MM-DD HH:mm:ss');
							        }else{
							        	return "";
							        }
						    }
						},{
						    "targets": [6],
						    "render": function(a,b,c,d) {//0、未开票，1、已开票
						    	if(a!=null){
						        	if(a==0){
						        		return "未开票";
						        	}else if(a==1){
						        		return "已开票";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [8],
						    "render": function(a,b,c,d) {//0、未开票，1、已开票
						    	if(a!=null){
						        	var linkUrl='<span class="glyphicon invoicerHistory-operate-browse" style="text-decoration: underline; cursor: pointer;"data-id="'+invoicerHistory.imgPath+"/"+a+'" data-toggle="tooltip" data-placement="top">浏览</span>';
						    		return linkUrl;//弹出大图
						        }else{
						        	return "";
						        }
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon invoicerHistory-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'+c.invoiceId+'" data-toggle="tooltip" data-placement="top">查看</span>';
								return view;
							}
						}
					]
				});
			}
	    };
	return invoicerHistory;
});


