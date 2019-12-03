define([],function() {	
	var carFault={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				carFault.pageList();
				//查询
				$("#carFaultSearchafter").click(function(){
					var form=$("form[name=carFaultSearchForm]");
					var recordFaultTimeStart=form.find("input[name=recordFaultTimeStart]").val();
					var recordFaultTimeEnd=form.find("input[name=recordFaultTimeEnd]").val();
					if(recordFaultTimeStart!=null&&recordFaultTimeStart!=""){
						if(recordFaultTimeEnd!=null&&recordFaultTimeEnd!=""){
							if(recordFaultTimeStart>recordFaultTimeEnd){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{
								carFault.pageList();
							}
						}
					}else{
						carFault.pageList();
					}
					
	            });
				carFault.handleClickMore();
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".carFault-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("故障详情",carFault.appPath+ "/carFault/toCarFaultView.do?faultId="+$(this).data("id"));
					});
				});
	        	
	        	
	        	$(".car-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("车辆详情",carFault.appPath+ "/car/toCarView.do?carNo="+$(this).data("id"));
					});
				});
	        	
	        	
	        	
				$(".carFault-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("故障编辑",carFault.appPath+ "/carFault/toCarFaultEdit.do?faultId="+$(this).data("id"));			
					});
				});	
	        },
			pageList:function () {	
				var carFaultTpl = $("#carFaultTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carFaultTpl);
				$('#carFaultList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carFault.appPath+'/carFault/pageListCarFault.do',
						"data": function ( d ) {
							var form = $("form[name=carFaultSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":form.find("input[name=carPlateNo]").val(),
								"documentNo":form.find("input[name=documentNo]").val(),
								"useCarType":form.find("select[name=useCarType]").val(),
								"faultLevel":form.find("select[name=faultLevel]").val(),
								"recordFaultTimeStart":form.find("input[name=recordFaultTimeStart]").val()+" 00:00:00",
								"recordFaultTimeEnd":form.find("input[name=recordFaultTimeEnd]").val()+" 23:59:59",
	        					"driverName":form.find("input[name=driverName]").val(),
	        					"processingStatus":form.find("select[name=processingStatus]").val(),
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
						{ "title":"故障时间","data": "recordFaultTime" },
						{ "title":"故障地点","data": "faultLocation" },	
						{ "title":"故障等级","data": "faultLevel" },
						{ "title":"处理状态","data": "processingStatus" },
						{ "title":"用车类型","data": "useCarType" },
						{ "title":"单据号","data": "documentNo" },
						{ "title":"用车人","data": "driverName" },	
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#caeMalfunctiontool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#carFaultTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#carFaultTools").html("");
					   $("#carFaultTools").removeClass("col-xs-6");
		       			$("#carFaultTools").append('<button type="button" class="btn-new-type carFaultTools-operate-add">新增</button>');
		       			$(".carFaultTools-operate-add").on("click",function(){
		       				addTab("新增故障", carFault.appPath+ "/carFault/addCarFaultPage.do");
		       			});	          			
					},
					"drawCallback": function( settings ) {
						carFault.operateColumEvent();
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
						//故障等级 0一级 1二级 2三级
						{
						    "targets": [5],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	if(c.faultLevel==0){
						        		return "一级";
						        	}else if(c.faultLevel==1){
						        		return "二级";
						        	}else if(c.faultLevel==2){
						        		return "三级";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						//处理状态（0、未处理，1、处理中，2、已处理，默认0）
						{
						    "targets": [6],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	if(c.processingStatus==0){
						        		return "未处理";
						        	}else if(c.processingStatus==1){
						        		return "处理中";
						        	}else if(c.processingStatus==2){
						        		return "已处理";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						//用车类型（1、订单用车，2、调度用车）
						{
						    "targets": [7],
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
							"targets": [10],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  carFault-operate-find" data-id="'+c.faultId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
								var edit='<span class="glyphicon  carFault-operate-edit" data-id="'+c.faultId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
	        					return find+edit;
							}
						}
//						{
//	        	            "targets": [3,4],
//	        	            "render": function(data, type, row, meta) {
//	        	            	var now = moment(data); 
//	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
//	        	            }
//	        	        }
					]
				});
			},
			//点击更多
			handleClickMore:function(){
				$("#moreCarFault").click(function(){
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
	return carFault;
});

function timeStart(){
	var form = $("form[name=carFaultSearchForm]");
	var createTimeStart = form.find("input[name=createTimeStart]").val();
	var createTimeEnd = form.find("input[name=createTimeEnd]").val();
	if(createTimeStart!=""&&createTimeEnd!=""){
		var ti = createTimeStart.replace(/-/gm,'');
		var time = Number(ti);
		
		var ti2 = createTimeEnd.replace(/-/gm,'');
		var time2 = Number(ti2);
		
		if(time>time2){
			bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
		}
	}
	
	
	
}


