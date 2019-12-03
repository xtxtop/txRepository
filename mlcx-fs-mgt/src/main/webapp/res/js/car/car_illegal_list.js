define([],function() {	
	var carIllegal={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				carIllegal.pageList();
				//查询
				$("#carIllegalSearch").click(function(){
					var form = $("form[name=carIllegalSearchForm]");
					var illegalTimeStart = form.find("input[name=illegalTimeStart]").val();
					var illegalTimeEnd = form.find("input[name=illegalTimeEnd]").val();
					if(illegalTimeStart!=""&&illegalTimeEnd!=""){
						if(new Date(illegalTimeStart)>new Date(illegalTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							carIllegal.pageList();
						}
					}else{
						carIllegal.pageList();
					}
	            });
				carIllegal.handleClickMore();
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".carIllegal-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("违章详情",carIllegal.appPath+ "/carIllegal/toCarIllegalView.do?illegalId="+$(this).data("id"));
					});
				});
	        	
	        	
	        	$(".car-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("车辆详情",carIllegal.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        	
	        	
	        	$(".order-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("订单详情",carIllegal.appPath+ "/order/toOrderView.do?orderNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        	
	        	
	        	
				$(".carIllegal-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("违章编辑",carIllegal.appPath+ "/carIllegal/toUpdateCarIllegal.do?illegalId="+$(this).data("id"));			
					});
				});	
	        },
			pageList:function () {	
				var carIllegalBtnTpl = $("#carIllegalBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carIllegalBtnTpl);
				$('#carIllegalList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carIllegal.appPath+'/carIllegal/pageListCarIllegal.do',
						"data": function ( d ) {
							var form = $("form[name=carIllegalSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"documentNo":$.trim(form.find("input[name=documentNo]").val()),
								"useCarType":form.find("select[name=useCarType]").val(),
								"processingStatus":form.find("select[name=processingStatus]").val(),
								"illegalTimeStart":form.find("input[name=illegalTimeStart]").val()+" 00:00:00",
								"illegalTimeEnd":form.find("input[name=illegalTimeEnd]").val()+" 23:59:59",
								"driverName":$.trim(form.find("input[name=driverName]").val()),
								"driverId":$.trim(form.find("input[name=driverId]").val()),
	        					"paymentStatus":form.find("select[name=paymentStatus]").val()
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
			            { "title":"车牌号","data": "carPlateNo" },
						{ "title":"型号","data": "carModelName" },
						{ "title":"城市","data": "cityName" },
						{ "title":"违章时间","data": "illegalTime" },
						{ "title":"违章地点","data": "illegalLocation" },	
						{ "title":"违章类型","data": "illegalType" },
						{ "title":"罚款金额(元)","data": "illegalFines" },
						{ "title":"处理状态","data": "processingStatus" },
						{ "title":"缴费状态","data": "paymentStatus" },
						{ "title":"用车类型","data": "useCarType" },
						{ "title":"单据号","data": "documentNo" },
						{ "title":"用车人","data": "driverName" },	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#carIllegalTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   	$("#carIllegalTools").html("");
					   $("#carIllegalTools").removeClass("col-xs-6");
		       			$("#carIllegalTools").append('<button type="button" class="btn-new-type carIllegalTools-operate-addCarIllegal">新增</button>');
		       			$(".carIllegalTools-operate-addCarIllegal").on("click",function(){
		       				addTab("违章新增", carIllegal.appPath+ "/carIllegal/toAddCarIllegal.do");
		       			});
					},
					"drawCallback": function( settings ) {
						carIllegal.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
							{
								"targets" : [ 0 ],
								"render" : function(a,
										b, c, d) {
									if(a){
										return '<span class="glyphicon car-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.carNo+ '" >'+a+'</span>';
									}else{
										return "";
									}
								}
							},               
					               
					               
						{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	var now = moment(a);
									return now.format('YYYY-MM-DD HH:mm:ss');
						        }else{
						        	return "";
						        }
						    }
						},
						//违章类型（1、未系安全带2、压禁止标线 3、违停4、闯红灯5、不服从指挥6、超速行驶7、未设警告标志8、未停车让行9、未保持车距10、未按道行驶）
						{
						    "targets": [5],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	if(a==1){
						        		return "未系安全带";
						        	}else if(a==2){
						        		return "压禁止标线";
						        	}else if(a==3){
						        		return "违停";
						        	}else if(a==4){
						        		return "闯红灯";
						        	}else if(a==5){
						        		return "不服从指挥";
						        	}else if(a==6){
						        		return "超速行驶";
						        	}else if(a==7){
						        		return "未设警告标志";
						        	}else if(a==8){
						        		return "未停车让行";
						        	}else if(a==9){
						        		return "未保持车距";
						        	}else if(a==10){
						        		return "未按道行驶";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						//处理状态（0、未处理，1、处理中，2、已处理，默认0）
						{
						    "targets": [7],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	if(a==0){
						        		return "未处理";
						        	}else if(a==1){
						        		return "处理中";
						        	}else if(a==2){
						        		return "已处理";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						//缴纳状态（0、未缴款，1、已缴款，默认0）
						{
						    "targets": [8],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	if(a==0){
						        		return "未缴费";
						        	}else if(a==1){
						        		return "已缴费";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						//用车类型（1、订单用车，2、调度用车）
						{
						    "targets": [9],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	if(c.useCarType==1){
						        		return "订单用车";
						        	}else{
						        		return "调度用车";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						
						
						{
							"targets" : [ 10 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return '<span class="glyphicon order-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.documentNo+ '" >'+a+'</span>';
								}else{
									return "";
								}
							}
						},      
						
						
						
						{
							"targets": [12],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon carIllegal-operate-view" data-id="'+c.illegalId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var edit='<span class="glyphicon carIllegal-operate-edit" data-id="'+c.illegalId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
	        					return find+edit;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCarIllegal").click(function(){
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
	return carIllegal;
});


