define([],function() {	
	var carRecord={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				carRecord.pageList();
				//查询
				$("#carRecordSearch").click(function(){
					var form = $("form[name=carRecordSearchForm]");
					var startTimeStart =  form.find("input[name=startTimeStart]").val();
					var finishTimeEnd = form.find("input[name=finishTimeEnd]").val();
					if(startTimeStart!=""&&finishTimeEnd!=""){
						if(new Date(startTimeStart)>new Date(finishTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							carRecord.pageList();
						}
					}else{
						carRecord.pageList();
					}
	            });
				$("#closeCarRecordVeiw").click(function(){
					closeTab("用车记录详情");
					carRecord.pageList();
	            });
//				carRecord.handleClickMore();
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".carRecord-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("用车记录详情",carRecord.appPath+ "/carRecord/toCarRecordView.do?recordId="+$(this).data("id"));
					});
				});
	        	
	        	$(".car-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("车辆详情",carRecord.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        	$(".order-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("订单详情",carRecord.appPath+ "/order/toOrderView.do?orderNo="+$(this).data("id"));
					});
				});
	        	
	        	$(".member-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("会员详情",carRecord.appPath+ "/member/toMemberView.do?memberNo="+$(this).data("id"));
					});
				});
	        	
	        	$(".park-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("场站详情",carRecord.appPath+ "/park/toParkView.do?parkNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        	
	        	
	        },
			pageList:function () {	
				var carRecordBtnTpl = $("#carRecordBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carRecordBtnTpl);
				$('#carRecordList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carRecord.appPath+'/carRecord/pageListCarRecordList.do',
						"data": function ( d ) {
							var form = $("form[name=carRecordSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"documentNo":$.trim(form.find("input[name=documentNo]").val()),
								"startTimeStart":form.find("input[name=startTimeStart]").val()+" 00:00:00",
								"finishTimeEnd":form.find("input[name=finishTimeEnd]").val()+" 23:59:59",
								"useCarType":form.find("select[name=useCarType]").val(),
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
						{ "title":"型号","data": "carModel" },
						{ "title":"城市","data": "city" },
						{ "title":"用车类型","data": "useCarType"},
						{ "title":"单据号","data": "documentNo"},
						{ "title":"用车人","data": "driverName"},	
						{ "title":"起点","data": "startParkName"},
						{ "title":"终点","data": "terminalParkName"},
						{ "title":"用车时间","data": "startTime"},
						{ "title":"还车时间","data": "finishTime"},	
						{ "title":"开始电量","data": "startPower"},
						{ "title":"结束电量","data": "finishPower"},
						{ "title":"总里程（Km）","data": "totalMileage"},
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#carRecordTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						carRecord.operateColumEvent();
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
							"targets" : [ 4 ],
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
							"targets" : [ 5 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return '<span class="glyphicon member-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.driverId+ '" >'+a+'</span>';
								}else{
									return "";
								}
							}
						}, 
						
						
						
						
						{
							"targets" : [ 6 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return '<span class="glyphicon park-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.startParkNo+ '" >'+a+'</span>';
								}else{
									return "";
								}
							}
						}, 
						
						
						
						{
							"targets" : [ 7 ],
							"render" : function(a,
									b, c, d) {
								if(a){
									return '<span class="glyphicon park-operate-view" style="color:#2b94fd; underline; cursor: pointer;" data-id="'+ c.terminalParkNo+ '" >'+a+'</span>';
								}else{
									return "";
								}
							}
						}, 
						
						
						
						{
						    "targets": [8,9],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	var now = moment(a);
									return now.format('YYYY-MM-DD HH:mm:ss');
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [10,11],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	return a+"%";
						        }else{
						        	return "";
						        }
						    }
						},{
						    "targets": [12],
						    "render": function(a,b,c,d) {
						    	if(a){
		    	            		return carRecord.formatCurrency(a,3); 
		    	            	}else{
		    	            		return "";
		    	            	}
						    }
						},
						{
							"targets": [13],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon carRecord-operate-view" data-id="'+c.recordId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
	        					return find;
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
		        return t.split("").reverse().join("") + "." + r;  
		        },
		    	//点击更多
				handleClickMore:function(){
					$("#moreCarRecord").click(function(){
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
	return carRecord;
});


