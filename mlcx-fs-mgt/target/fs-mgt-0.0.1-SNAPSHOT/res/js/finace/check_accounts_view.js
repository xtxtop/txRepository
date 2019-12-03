define([],function() {	
	var checkAccountsView={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				checkAccountsView.pageList();
				checkAccountsView.handleClickMore();
				//查询
				$("#checkAccountsViewSearch").click(function(){
					var form = $("form[name=checkAccountsViewSearchForm]");
					var createTime =  form.find("input[name=createTime]").val();
					var finishTime = form.find("input[name=finishTime]").val();
					if(createTime!=""&&finishTime!=""){
						if(new Date(createTime)>new Date(finishTime)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对账周期的开始时间不能大于结束时间！");
						}else{
							checkAccountsView.pageList();
						}
					}else{
						checkAccountsView.pageList();
					}
	            });
				$("#closecheckAccountsViewView").click(function(){
					closeTab("发票开票详情");
					checkAccountsView.pageList();
	            });
				$("#checkAccountsViewBrowseModalClose").click(function(){
					$("#checkAccountsViewBrowseModal").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".checkAccountsView-operate-view").each(function(){
					$(this).on("click",function(){
						var memberNo=$(this).data("id");
						var checkDateRange1=$(this).data("checkRange1")+" 00:00:00";
						var checkDateRange2=$(this).data("checkRange2")+" 23:59:59";
						addTab("财务对账明细",checkAccountsView.appPath+ "/checkAccountsView/tocheckAccountsViewView.do?memberNo="+memberNo+"&checkDateRange1="+checkDateRange1+"&checkDateRange2="+checkDateRange2);
					});
				});
	        },
			pageList:function () {
				var checkAccountsViewBtnTpl = $("#checkAccountsViewBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(checkAccountsViewBtnTpl);
				$('#checkAccountsViewList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": checkAccountsView.appPath+'/checkAccounts/pageListCheckAccountsDetail.do',
						"data": function ( d ) {
							var form = $("form[name=checkAccountsViewSearchForm]");
							var createTime=form.find("input[name=createTime]").val();
							var finishTime=form.find("input[name=finishTime]").val();
							var memberName=form.find("input[name=memberName]").val();
							var orderStatus=form.find("select[name=orderStatus]").val();
							var payStatus=form.find("select[name=payStatus]").val();
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"checkDateRange1":form.find("input[name=createTime]").val()+ " 00:00:00",
								"checkDateRange2":form.find("input[name=finishTime]").val()+ " 23:59:59",
								"orderStatus":orderStatus,
								"payStatus":payStatus,
								"memberName":memberName,
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
			            { "title":"订单号","data": "orderNo" },
						{ "title":"客户姓名","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"城市","data": "cityName"},
						{ "title":"车型","data": "carModelName"},
						{ "title":"车牌号","data": "carPlateNo"},
						{ "title":"开始时间","data": "startTime"},
						{ "title":"结束时间","data": "finishTime"},
						{ "title":"订单状态","data": "orderStatus" },
						{ "title":"订单金额(元)","data": "orderAmount"},
						{ "title":"应付金额(元)","data": "payableAmount"},
						{ "title":"支付状态","data": "payStatus"},
						{ "title":"发票号","data": "invoiceNo"},
						{ "title":"开票金额","data": "invoiceAmount"}
					],
				   "dom": "<'row'<'#checkAccountsViewTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#checkAccountsViewTools").html("");
		       			$("#checkAccountsViewTools").append('<button type="button" class="btn-new-type checkAccountsViewTools-operate-export">导出</button>');
		       			$(".checkAccountsViewTools-operate-export").on("click",function(){
		       				var form = $("form[name=checkAccountsViewSearchForm]");
							var createTime=form.find("input[name=createTime]").val();
							var finishTime=form.find("input[name=finishTime]").val();
							var memberName=form.find("input[name=memberName]").val();
							var orderStatus=form.find("select[name=orderStatus]").val();
							var payStatus=form.find("select[name=payStatus]").val();
		       				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
								if(result){
									window.location.href = checkAccountsView.appPath+ "/checkAccounts/toCheckAccountsViewExport.do?memberName="+memberName+"&orderStatus="+orderStatus+"&payStatus="+payStatus+"&checkDateRange1="+createTime+" 00:00:00"+"&checkDateRange2="+finishTime+" 23:59:59";
								}						
		       				});
		       			});
					},
					"drawCallback": function( settings ) {
						checkAccountsView.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [6,7],
						    "render": function(a,b,c,d) {
						    	if(a){
						    		var date = moment(a);
						        	return date.format('YYYY-MM-DD HH:mm:ss');
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [8],
						    "render": function(a,b,c,d) {//订单状态0、已提交，1、已预约，2、已计费，3、已结束，4、已取消
						    	if(a){
						    		if(a==1){
						    			return "已预约";
						    		}else if(a==2){
						    			return "已计费";
						    		}else if(a==3){
						    			return "已结束";
						    		}else if(a==4){
						    			return "已取消";
						    		}else{
						    			return "";
						    		}
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [9,10,13],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return checkAccountsView.formatCurrency(a); 
		    	            	}else{
		    	            		return "0.00";
		    	            	}
						    }
						},{
						    "targets": [11],
						    "render": function(a,b,c,d) {//0、未支付，1、已支付
						    	if(a){
						    		if(a==0){
						    			return "未支付";
						    		}else if(a==1){
						    			return "已支付";
						    		}else{
						    			return "";
						    		}
		    	            	}else{
		    	            		return "";
		    	            	}
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
		        return t.split("").reverse().join("") + "." + r;  },
		      //点击更多
				handleClickMore:function(){
					$("#moreCheckAccount").click(function(){
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
	return checkAccountsView;
});


