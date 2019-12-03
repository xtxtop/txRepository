define([],function() {	
	var parkDay={
			appPath:getPath("app"),	
			init: function () {
				//关闭车辆详情页
				$("#closeParkDay").click(function(){
					closeTab("场站详情");
	            });
				//启用状态提交
				$("#parkDayOnFormBtn").click(function(){
					parkDay.onFormSub();
	            });
				//启用取消
				$("#parkDayOnCancelBtn").click(function(){
					$("#onParkDayModal").modal("hide");
	            });
				//停用状态提交
				$("#parkDayOffBtn").click(function(){
					parkDay.offFormSub();
	            });
				//停用取消
				$("#parkDayOffCancel").click(function(){
					$("#offParkDayModal").modal("hide");
	            });
	            //数据列表
				parkDay.pageList();
				//查询
				$("#parkDaySearchafter").click(function(){
					parkDay.pageList();
	            });
				$("#onParkDayModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=onParkDayForm]");
	            	form.resetForm();
	            	$("#onParkDayMemo").text("");
	            	form.find("input[name=parkId]").val("");
	            });
				$("#offParkDayModal").on("hidden.bs.modal", function() {  
	            	var form = $("form[name=offParkDayForm]");
	            	form.resetForm();
	            	$("#offParkDayMemo").text("");
	            	form.find("input[name=parkId]").val("");
	            });
				
			},
			creatMaker:function() {
				var map = new BMap.Map("parkDayViewMap"); // 创建地图实例
				var longitude = $("#parkDayLongitudeView").val();
				var latitude = $("#parkDayLatitudeView").val();
				var point = new BMap.Point(longitude,latitude);
				map.centerAndZoom(point,18);
				var marker = new BMap.Marker(point); // 创建标注
				map.addOverlay(marker);
				var fullAddr = $("#parkDayAddrStreetView").val();
				var label = new BMap.Label(fullAddr,{offset:new BMap.Size(-30,-20)});
		    	label.setStyle({
		    		color : "red",
		    		fontSize : "13px",
		    		height : "20px",
		    		lineHeight : "20px",
		    		fontFamily:"微软雅黑",
		    		backgroundColor :"0.05",
		    		border :"0"
		    	});
		    	marker.setLabel(label);
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".parkDay-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("门店详情",parkDay.appPath+ "/parkDay/toParkDayView.do?parkId="+$(this).data("id"));
					});
				});
	        	$(".parkDay-operate-edit").each(function(){
					$(this).on("click",function(){
						addTab("编辑门店",parkDay.appPath+ "/parkDay/toUpdateParkDay.do?parkId="+$(this).data("id"));
					});
				});
	        	//启用弹出层
				$(".parkDay-operate-onList").each(function(id,obj){
					$(this).on("click",function(){
						var parkId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: parkDay.appPath+"/parkDay/getParkDay.do",
				             data: {parkId:parkId},
				             dataType: "json",
				             success: function(result){
				            	 var code = result.code; 
				            	 var data = result.data;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#onParkDayModal").modal("show");
				            	    var form = $("form[name=onParkDayForm]");
									form.find("input[name='parkId']").val(data.parkId);
									$("#onParkDayMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将门店："+"“"+data.parkName+"”"+" 启用吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg);
				                 }
				             }
						});
					});
				});
				//停用弹出层
				$(".parkDay-operate-offList").each(function(id,obj){
					$(this).on("click",function(){
						var parkId=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: parkDay.appPath+"/parkDay/getParkDay.do",
				             data: {parkId:parkId},
				             dataType: "json",
				             success: function(result){
				            	 var code = result.code; 
				            	 var data = result.data;
				            	 var msg = result.msg;
				            	 if(code=="1"){
				            	    $("#offParkDayModal").modal("show");
				            	    var form = $("form[name=offParkDayForm]");
				            	    form.find("input[name='parkId']").val(data.parkId);
									$("#offParkDayMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将门店:"+"“"+data.parkName+"”"+" 停用吗？");
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg);
				                 }
				             }
						});
					});
				});
	        },
	        //启用操作
	        onFormSub: function () {
	        	var form = $("form[name=onParkDayForm]");
				form.ajaxSubmit({
	    			url:parkDay.appPath+"/parkDay/changeParkDayState.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							$("#onParkDayModal").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "启用成功！", function() {
								$("#onParkDayModal").modal('hide');
							});
							$("#parkDayList").DataTable().ajax.reload(function(){
    						}); 
						}else{
							$("#onParkDayModal").modal('hide');
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					}
					});
				},
				   //停用操作
				offFormSub: function () {
		        	var form = $("form[name=offParkDayForm]");
					form.ajaxSubmit({
						url:parkDay.appPath+"/parkDay/changeParkDayState.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + " 停用成功！", function() {
									$("#offParkDayModal").modal('hide');
								});
							  $("#parkDayList").DataTable().ajax.reload(function(){
	    						}); 
							}else{
								$("#offParkDayModal").modal('hide');
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + msg);
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						}
						});
					},
			pageList:function () {	
				var parkDayTpl = $("#parkDayTpl").html();
				// 预编译模板
				var template = Handlebars.compile(parkDayTpl);
				$('#parkDayList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkDay.appPath+'/parkDay/pageListParkDay.do',
						"data": function ( d ) {
							var form = $("form[name=parkDaySearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkName":$.trim(form.find("input[name=parkName]").val()),
	        					"isVailable":form.find("select[name=isVailable]").val(),
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
						{ "title":"门店名称","data": "parkName" },
						{ "title":"详细地址","data": "addrStreet" },
						{ "title":"租赁商","data": "merchantName" },
						{ "title":"城市","data": "cityName" },
//						{ "title":"营业时间","data": "businessHours" },
						{ "title":"状态","data": "isVailable" },	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#parkDayTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#parkDayTools").html("");
					   $("#parkDayTools").append('<button type="button" class="btn-new-type parkDayTools-operate-addParkDay">新增</button>');
		       		   $(".parkDayTools-operate-addParkDay").on("click",function(){
		       				addTab("新增门店 ", parkDay.appPath+ "/parkDay/toAddParkDay.do");
		       			});	     			
	        			
					},
					"drawCallback": function( settings ) {
						parkDay.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						//场站状态（0、停用，1、启用，默认0）
						{
						    "targets": [4],
						    "render": function(data, type, row, meta) {
						    		if(data==0){
							        	return "停用";
							        }else{
							        	return "启用";
							        }	
						    }
						},
						{
							"targets": [5],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon parkDay-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkId+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var edit='<span class="glyphicon parkDay-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkId+'" data-toggle="tooltip" data-placement="top">编辑</span>';
	        					var onList='<span class="glyphicon parkDay-operate-onList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkId+'" data-toggle="tooltip" data-placement="top">启用</span>';
	        					var offList='<span class="glyphicon parkDay-operate-offList" style="text-decoration: underline; cursor: pointer;"data-id="'+c.parkId+'" data-toggle="tooltip" data-placement="top">停用</span>';
	        					if(c.isVailable==0){
	        						return find+edit+onList;
	        					}else{
	        						return find+offList;
	        					}
							}
						}
					]
				});
			}
	    };
	return parkDay;
});


