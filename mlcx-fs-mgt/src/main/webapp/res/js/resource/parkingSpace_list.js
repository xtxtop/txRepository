define([],function() {	
	var parkingSpace={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				parkingSpace.pageList();
				//查询
				$("#parkingSpaceSearchafter").click(function(){
					parkingSpace.pageList();
	            });
				//启用状态提交
				$("#parkingSpaceOnFormBtn").click(function(){
					$("#onparkingSpaceModal").modal("hide");
					parkingSpace.onFormSub();
	            });
				//启用取消
				$("#parkingSpaceOnCancelBtn").click(function(){
					$("#onparkingSpaceModal").modal("hide");
	            });
				//停用状态提交
				$("#parkingSpaceOffBtn").click(function(){
					 $("#offparkingSpaceModal").modal("hide");
					parkingSpace.offFormSub();
	            });
				//停用取消
				$("#parkingSpaceOffCancel").click(function(){
					$("#offparkingSpaceModal").modal("hide");
	            });
				
				//详情页面的返回
				$("#closeParking").click(function(){
					closeTab("车位详情");
				});
				$("#onparkingSpaceModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onparkingSpaceForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	form.find("input[name=parkingSpaceNo]").val("");
	            });
				$("#offparkingSpaceModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offparkingSpaceForm]");
	            	//form.clearForm();
	            	form.resetForm();
	            	form.find("input[name=parkingSpaceNo]").val("");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".parkingSpace-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("车位详情",parkingSpace.appPath+ "/parkingSpace/toParkingSpaceView.do?parkingSpaceNo="+$(this).data("id"));
					});
				});
	        	$(".parkingSpace-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("车位编辑",parkingSpace.appPath+ "/parkingSpace/toUpdateView.do?parkingSpaceNo="+$(this).data("id"));
					});
				});
	        	//启用弹出层
				$(".parkingSpace-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var parkingSpaceNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: parkingSpace.appPath+"/parkingSpace/getParkingSpace.do",
				             data: {parkingSpaceNo:parkingSpaceNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            	    $("#onparkingSpaceModal").modal("show");
									$("#parkingSpaceNo1").val(data.parkingSpaceNo);
									$("#onparkingSpaceMemo").text("确认启用编号为："+"“"+data.parkingSpaceNo+"”"+"车位吗？");
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".parkingSpace-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var parkingSpaceNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: parkingSpace.appPath+"/parkingSpace/getParkingSpace.do",
				             data: {parkingSpaceNo:parkingSpaceNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            	    $("#offparkingSpaceModal").modal("show");
									$("#parkingSpaceNo2").val(data.parkingSpaceNo);
									$("#offparkingSpaceMemo").html("<img src='res/img/wen.png'/>&nbsp;&nbsp;确认停用编号为："+"“"+data.parkingSpaceNo+"”"+"车位吗？");
				                 }
				             }
						});
					});
				});
	        },
	        //启用操作
	        onFormSub: function () {
	        	var form = $("form[name=onparkingSpaceForm]");
				form.ajaxSubmit({
	    			url:parkingSpace.appPath + "/parkingSpace/availableParkingSpace.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
						    bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
						    	$(".bootbox").modal("hide");
								 $("#parkingSpaceList").DataTable().ajax.reload(function(){
		    						});
						    });
						}
					}
					});
				},
				   //停用操作
				offFormSub: function () {
		        	var form = $("form[name=offparkingSpaceForm]");
					form.ajaxSubmit({
						url:parkingSpace.appPath + "/parkingSpace/availableParkingSpace.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
								 $(".bootbox").modal("hide");
								 $("#parkingSpaceList").DataTable().ajax.reload(function(){
		    						}); 
							 });
							  
							}
						}
						});
					},
			pageList:function () {	
				var parkingSpaceTpl = $("#parkingSpaceTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkingSpaceTpl);
				$('#parkingSpaceList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkingSpace.appPath+'/parkingSpace/pageListPark.do',
						"data": function ( d ) {
							var form = $("form[name=parkingSpaceSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkingSpaceNo":$.trim(form.find("input[name=parkingSpaceNo]").val()),
								"cityId":form.find("select[name=cityId]").val(),
	        					"parkName":$.trim(form.find("input[name=parkName]").val()),
	        					"ownerType":form.find("select[name=ownerType]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
	        					"parkNo":$.trim(form.find("input[name=parkNo]").val())
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
						{ "title":"编号","data": "parkingSpaceNo" },
						{ "title":"城市","data": "cityName" },
						{ "title":"场站名称","data": "parkName" },
						{ "title":"所属","data": "ownerType" },	
						{ "title":"带电桩","data": "hasCharger" },
						{ "title":"启用时间","data": "availableUpdateTime" },
						{ "title":"停用时间","data": "disabledUpdateTime" },
						{ "title":"电桩编号","data": "chargerNo" },
						{ "title":"状态","data": "isAvailable" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#parkingSpacetool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#parkingSpaceTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#parkingSpaceTools").html("");
					   $("#parkingSpaceTools").removeClass("col-xs-6");
					   $("#parkingSpaceTools").append('<button type="button" class="btn-new-type parkingSpaceTools-operate-addPark">新增</button>');
		       			$(".parkingSpaceTools-operate-addPark").on("click",function(){
		       				var form = $("form[name=parkingSpaceSearchForm]");
		       				var parkNo = $.trim(form.find("input[name=parkNo]").val())
		       				var url = parkingSpace.appPath+ "/parkingSpace/addParkingSpacePage.do?parkNo=" + parkNo;
		       				addTab("车位新增",url );
		       			});	      			
	        			
					},
					"drawCallback": function( settings ) {
						parkingSpace.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
		               ///所属方类型（1、自有，2、租用）
						{
						    "targets": [3],
						    "render": function(data, type, row, meta) {
						        if(data==1){
						        	return "自有";
						        }else{
						        	return "租用";
						        }
						    }
						},
                          //是否带电桩（0，不带电桩，1、带电桩）
						{
						    "targets": [4],
						    "render": function(data, type, row, meta) {
						        if(data==0){
						        	return "否";
						        }else{
						        	return "是";
						        }
						    }
						},
						
						{
							"targets": [5,6],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    	var now = moment(data); 
	        	            	return now.format('YYYY-MM-DD HH:mm:ss');
						    	}else{
						    	  return "";
						    	}
						    }
						},
						 //是否可用（0，不可用、1，可用，默认1）
						{
						    "targets": [8],
						    "render": function(data, type, row, meta) {
						    	 if(data==0){
							        	return "停用";
							        }else{
							        	return "启用";
							       }
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  parkingSpace-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkingSpaceNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon  parkingSpace-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkingSpaceNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var onList='<span class="glyphicon  parkingSpace-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkingSpaceNo+'" data-toggle="tooltip" data-placement="top">启用</span>';
	        					var offList='<span class="glyphicon  parkingSpace-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkingSpaceNo+'" data-toggle="tooltip" data-placement="top">停用</span>';
//	        					var op='<span class="glyphicon  btn btn-danger  parkingSpace-operate-edit" data-id="'+c.parkingSpaceNo+'" data-toggle="tooltip" data-placement="top" title="位置">位置</span>';
	        					if(c.isAvailable==0){
	        					return find+edit+onList;
	        					}else{
	        					return find+edit+offList;
	        					}
							}
						}
					]
				});
			}
	    };
	return parkingSpace;
});


