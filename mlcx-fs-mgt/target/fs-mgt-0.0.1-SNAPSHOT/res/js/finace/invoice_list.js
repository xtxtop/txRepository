define([],function() {	
	var invoice={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				invoice.pageList();
				invoice.handleClickMore();
				//查询
				$("#invoiceSearch").click(function(){
					var form = $("form[name=invoiceSearchForm]");
					var invoiceTimeStart1 =  form.find("input[name=invoiceTimeStart1]").val();
					var invoiceTimeEnd1 = form.find("input[name=invoiceTimeEnd1]").val();
					if(invoiceTimeStart1!=""&&invoiceTimeEnd1!=""){
						if(new Date(invoiceTimeStart1)>new Date(invoiceTimeEnd1)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开票开始时间不能大于结束时间！");
						}else{
							invoice.pageList();
						}
					}else{
						invoice.pageList();
					}
	            });
				$("#closeInvoiceView").click(function(){
					closeTab("发票开票详情");
					invoice.pageList();
	            });
				$("#invoiceBrowseModalClose").click(function(){
					$("#invoiceBrowseModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".invoice-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("发票开票详情",invoice.appPath+ "/invoice/toInvoiceView.do?invoiceId="+$(this).data("id"));
					});
				});
	        	//浏览
	        	$(".invoice-operate-browse").each(function(){
					$(this).on("click",function(){
						$("#invoiceBrowseModal").modal("show");
						$("#invoiceBrowseImg").attr("src",$(this).data("id"));	
					});
				});
	        },
			pageList:function () {
				debugger;
				var invoiceBtnTpl = $("#invoiceBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(invoiceBtnTpl);
				$('#invoiceList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": invoice.appPath+'/invoice/pageListInvoice.do',
						"data": function ( d ) {
							var form = $("form[name=invoiceSearchForm]");
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
								"bizObjId":$.trim(form.find("input[name=bizObjId]").val()),
								"invoiceTimeStart":form.find("input[name=invoiceTimeStart1]").val()+ " 00:00:00",
								"invoiceTimeEnd":form.find("input[name=invoiceTimeEnd1]").val()+ " 23:59:59",
								"invoiceStatus":form.find("select[name=invoiceStatus]").val(),
								"phone":form.find("input[name=phone]").val(),
								"invoiceTimeLeadStart":invoiceTimeLeadStart+ " 00:00:00",
								"invoiceTimeLeadEnd":invoiceTimeLeadEnd+ " 23:59:59",
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
	                    { "title":"会员手机号","data": "phone" },
			            { "title":"开票抬头","data": "invoiceTitle" },
						{ "title":"开票类型","data": "invoiceType" },
						{ "title":"开票金额(元)","data": "invoiceAmount" },
						{ "title":"订单号","data": "bizObjId"},
//						{ "title":"订单时间","data": "orderTime"},
						{ "title":"开票时间","data": "invoiceTime"},
						{ "title":"开票状态","data": "invoiceStatus"},
						{ "title":"发票号","data": "invoiceNo"},
						{ "title":"纳税人认定通知书","data": "taxpayerNoticePic"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#invoiceTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#invoiceTools").html("");
					   $("#invoiceTools").removeClass("col-xs-6");
					   $("#invoiceTools").append('<button type="button" class="btn-new-type invoiceTools-operate-import">导入</button>');
		       			//$("#invoiceTools").append('<button type="button" class="btn btn-default btn-sm invoiceTools-operate-recordImport">导入表记录</button>');
		       			$("#invoiceTools").append('<button type="button" class="btn-new-type invoiceTools-operate-export">导出</button>');
		       			$(".invoiceTools-operate-import").on("click",function(){
		       				bootbox.dialog({
		       			        title: "导入",
		       			        dataType: "json",
		       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='exportForm' name='exportForm' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择要导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
		       			        buttons: {
		       			            "success": {
		       			                "label": "<i class='icon-ok'></i> 保存",
		       			                "className": "	btn-sm btn-success",
		       			                "callback": function() {
		       			                    var exportForm = $("form[name='exportForm']");
		       			                    exportForm.ajaxSubmit({
		       			                        url: "invoice/importinvoice.do",
		       			                        type: "post",
		       			                        success: function(res) {
		       			                        	if (res.code != 1) {
		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		       			                        	} else {
		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！",function(){
		       			                        			invoice.pageList();	
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
		       			                "className": "btn-sm btn-danger"
		       			            }
		       			            
		       			        }
		       			    })
		       			});
//		       			$(".invoiceTools-operate-recordImport").on("click",function(){
//		       				bootbox.dialog({
//		       			        title: "导入",
//		       			        dataType: "json",
//		       			        message: "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form' id='exportForm' name='exportForm' method='post' enctype='multipart/form-data'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>文件</label><div class='col-sm-9'><input type='file' id='txtOldPwd' placeholder='选择要导入的文件' name='file' class='col-xs-10 col-sm-5' /></div></div></div></form></div>",
//		       			        buttons: {
//		       			            "success": {
//		       			                "label": "<i class='icon-ok'></i> 保存",
//		       			                "className": "	btn-sm btn-success",
//		       			                "callback": function() {
//		       			                    var exportForm = $("form[name='exportForm']");
//		       			                    exportForm.ajaxSubmit({
//		       			                        url: "invoice/importFinaceRecord.do",
//		       			                        type: "post",
//		       			                        success: function(res) {
//		       			                        	if (res.code != 1) {
//		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
//		       			                        	} else {
//		       			                        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据导入成功！",function(){
//		       			                        			invoice.pageList();	
//		       			                        		}); 
//		       			                        	}
//		       			                            
//		       			                        }, 
//		       			                        error: function(res) {
//		       			                        	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "数据异常，请检查数据完整性！"); 
//		       			                        }
//		       			                    });
//		       			                }
//		       			            },
//		       			            "cancel": {
//		       			                "label": "<i class='icon-info'></i> 取消",
//		       			                "className": "btn-sm btn-danger"
//		       			            }
//		       			            
//		       			        }
//		       			    })
//		       			});
		       			$(".invoiceTools-operate-export").on("click",function(){
		       				var form = $("form[name=invoiceSearchForm]");
		       				var lead=form.find("select[name=invoiceTimeLead]").val();
		       				var invoiceStatus=form.find("select[name=invoiceStatus]").val();
							var invoiceTimeLeadStart="";
							var invoiceTimeLeadEnd="";
							if(lead){
		        				var temp=lead.split("_");
		        				invoiceTimeLeadStart=temp[0];
		        				invoiceTimeLeadEnd=temp[1];
		        			}	
		       				var bizObjId=$.trim(form.find("input[name=bizObjId]").val());
		       				var invoiceTimeStart=form.find("input[name=invoiceTimeStart1]").val()+ " 00:00:00";
		       				var invoiceTimeEnd=form.find("input[name=invoiceTimeEnd1]").val()+ " 23:59:59";
		       				var invoiceTimeLeadStart=invoiceTimeLeadStart+ " 00:00:00";
		       				var invoiceTimeLeadEnd=invoiceTimeLeadEnd+ " 23:59:59";
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = invoice.appPath+ "/invoice/toInvoiceExport.do?bizObjId="+bizObjId+"&&invoiceTimeStart="+invoiceTimeStart+"&&invoiceTimeEnd="+invoiceTimeEnd+"&&invoiceTimeLeadStart="+invoiceTimeLeadStart+
									"&&invoiceTimeLeadEnd="+invoiceTimeLeadEnd+"&&invoiceStatus="+invoiceStatus;
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						invoice.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b,c,d) {//1、增值税普通发票纸质版，2、增值税专用发票3.增值税普通发票电子版
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
						    "targets": [4],
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
						        		return "未开";
						        	}else if(a==1){
						        		return "已开";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [7],
						    "render": function(a,b,c,d) {//0、未开票，1、已开票
						    	if(c.invoiceStatus==0){
						        		return "";
						        }else if(c.invoiceStatus==1){
						        		return a;
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [8],
						    "render": function(a,b,c,d) {//0、未开票，1、已开票
						    	if(a!=null){
						        	var linkUrl='<span class="glyphicon  btn btn-default invoice-operate-browse" data-id="'+invoice.imgPath+"/"+a+'" data-toggle="tooltip" data-placement="top" title="浏览">浏览</span>';	
						    		return linkUrl;//弹出大图
						        }else{
						        	return "";
						        }
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon invoice-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'+c.invoiceId+'" data-toggle="tooltip" data-placement="top" title="查看">查看</span>';
								var invoiceStatus="";
								if(c.invoiceStatus==1){
									var invoiceStatus='<span class="glyphicon  btn btn-default invoice-operate-view" data-id="'+c.invoiceId+'" data-toggle="tooltip" data-placement="top" title="未开">未开</span>';
								}
								return view;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreInvoiceSearch").click(function(){
					if($(".seach-input-details").hasClass("close-content")){
						$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>收起<i class='fa fa-angle-up click-name'></i></div></div>");
						$(".seach-input-details").removeClass("close-content");
					}else{
						$(this).html("<div class='pull-right moreButtonNew' id='moreCarStatus'><div class='up-triangle'></div><div class='up-box'>更多<i class='fa fa-angle-down click-name'></i></div></div>");
						$(".seach-input-details").addClass("close-content");
					}
				})
			}
	    };
	return invoice;
});


