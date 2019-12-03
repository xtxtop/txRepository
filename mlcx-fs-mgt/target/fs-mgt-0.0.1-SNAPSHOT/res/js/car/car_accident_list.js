define([],function() {	
	var carAccident={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				carAccident.pageList();
				//查询
				$("#carAccidentSearch").click(function(){
					var form = $("form[name=carAccidentSearchForm]");
					var recordAccidentTimeStart =  form.find("input[name=recordAccidentTimeStart]").val();
					var recordAccidentTimeEnd = form.find("input[name=recordAccidentTimeEnd]").val();
					if(recordAccidentTimeStart!=""&&recordAccidentTimeEnd!=""){
						if(new Date(recordAccidentTimeStart)>new Date(recordAccidentTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							carAccident.pageList();
						}
					}else{
						carAccident.pageList();
					}
					
	            });
				carAccident.handleClickMore()
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".carAccident-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("事故详情",carAccident.appPath+ "/carAccident/toCarAccidentView.do?accidentId="+$(this).data("id"));
					});
				});
				$(".carAccident-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("事故编辑",carAccident.appPath+ "/carAccident/toUpdateCarAccident.do?accidentId="+$(this).data("id"));			
					});
				});	
				
				$(".car-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("车辆详情",carAccident.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
					});
				});
				
				
				$(".order-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("订单详情",carAccident.appPath+ "/order/toOrderView.do?orderNo="+$(this).data("id"));
					});
				});
				
				
	        },
			pageList:function () {	
				var carAccidentBtnTpl = $("#carAccidentBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carAccidentBtnTpl);
				$('#carAccidentList').dataTable({
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carAccident.appPath+'/carAccident/pageListCarAccidentList.do',
						"data": function ( d ) {
							var form = $("form[name=carAccidentSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"documentNo":$.trim(form.find("input[name=documentNo]").val()),
								"useCarType":form.find("select[name=useCarType]").val(),
								"accidentLevel":form.find("select[name=accidentLevel]").val(),
								"recordAccidentTimeStart":form.find("input[name=recordAccidentTimeStart]").val()+" 00:00:00",
								"recordAccidentTimeEnd":form.find("input[name=recordAccidentTimeEnd]").val()+" 23:59:59",
								"driverName":$.trim(form.find("input[name=driverName]").val())
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
						{ "title":"事故时间","data": "recordAccidentTime"},
						{ "title":"事故地点","data": "accidentLocation"},	
						{ "title":"事故等级","data": "accidentLevel"},
						{ "title":"保险公司","data": "insuranceCompany"},
						{ "title":"保险进度","data": "accidentStatus"},
						{ "title":"用车类型","data": "useCarType"},
						{ "title":"单据号","data": "documentNo"},
						{ "title":"用车人","data": "driverName"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#carAccidentTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   	$("#carAccidentTools").html("");
					   $("#carAccidentTools").removeClass("col-xs-6");
		       			$("#carAccidentTools").append('<button type="button" class="btn-new-type carAccidentTools-operate-addCarAccident">新增</button>');
		       			$(".carAccidentTools-operate-addCarAccident").on("click",function(){
		       				addTab("事故新增", carAccident.appPath+ "/carAccident/toAddCarAccident.do");
		       			});
					},
					"drawCallback": function( settings ) {
						carAccident.operateColumEvent();
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
						//事故等级（1、一般事故，2、轻微事故，3、重大事故，4、特大事故）
						{
						    "targets": [5],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	if(a==1){
						        		return "一般事故";
						        	}else if(a==2){
						        		return "轻微事故";
						        	}else if(a==3){
						        		return "重大事故";
						        	}else{
						        		return "特大事故";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						//保险进度（0、未处理，1、处理中，2、已处理，默认0）
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
						//用车类型（1、订单用车，2、调度用车）
						{
						    "targets": [8],
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
							"targets" : [ 9 ],
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
							"targets": [11],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon carAccident-operate-view" data-id="'+c.accidentId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var edit='<span class="glyphicon carAccident-operate-edit" data-id="'+c.accidentId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
	        					return find+edit;
							}
						}
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCarAccident").click(function(){
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
	return carAccident;
});


