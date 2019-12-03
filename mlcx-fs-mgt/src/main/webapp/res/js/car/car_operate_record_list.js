define([],function() {	
	var carOperateRecord={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				carOperateRecord.pageList();
				//查询
				$("#carOperateRecordSearch").click(function(){
					var form = $("form[name=carOperateRecordSearchForm]");
					var startTimeStart =  form.find("input[name=startTimeStart]").val();
					var finishTimeEnd = form.find("input[name=startTimeEnd]").val();
					if(startTimeStart!=""&&finishTimeEnd!=""){
						if(new Date(startTimeStart)>new Date(finishTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
						}else{
							carOperateRecord.pageList();
						}
					}else{
						carOperateRecord.pageList();
					}
	            });
				$("#closeCarOperateRecordVeiw").click(function(){
					closeTab("用车指令记录详情");
					carOperateRecord.pageList();
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".carOperateRecord-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("用车指令记录详情",carOperateRecord.appPath+ "/carOperateRecord/toCarOperateRecordView.do?recordId="+$(this).data("id"));
					});
				});
	        },
			pageList:function () {	
				var carOperateRecordBtnTpl = $("#carOperateRecordBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(carOperateRecordBtnTpl);
				$('#carOperateRecordList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": carOperateRecord.appPath+'/carOperateRecord/pageListCarOperateRecordList.do',
						"data": function ( d ) {
							var form = $("form[name=carOperateRecordSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"carPlateNo":$.trim(form.find("input[name=carPlateNo]").val()),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
								"operateType":form.find("select[name=operateType]").val(),
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
						{ "title":"指令类型","data": "operateType"},
						{ "title":"备注","data": "memo",width:"30%"},
						{ "title":"发送时间","data": "createTime"},
						{ "title":"操作人","data": "operatorName"}
						/*,	{ "title":"操作","orderable":false}*/
					],
				   "dom": "<'row'<'#carOperateRecordTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						carOperateRecord.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						{
						    "targets": [1],
						    "render": function(a,b, c, d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "关门";
						        	}else if(a==2){
						        		return "开门";
						        	}else if(a==3){
						        		return "关闭动力";
						        	}else if(a==4){
						        		return "开启动力";
						        	}else if(a==5){
						        		return "寻车";
						        	}else if(a==6){
						        		return "设为租赁";
						        	}else if(a==7){
						        		return "设为非租赁";
						        	}else if(a==8){
						        		return "物理钥匙使能";
						        	}else if(a==9){
						        		return "物理钥匙禁用";
						        	}
						        }else{
						        	return "";
						        }
						    }
						},
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    	if(a != null){
						    		if (a.length > 20){
						    			var text = "<div style='white-space:nowrap;text-overflow:ellipsis;overflow:hidden;' title='"+ a +"'>"+ a.substring(0,20) + "......</div>" 
						    			return text;
						    		}
						    		return a;
						    	 }
						        return "";
						    }
						},
						{
						    "targets": [3],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						        	var now = moment(a);
						        	debugger
									var result =  now.format('YYYY-MM-DD HH:mm:ss');
									 return result;
						        }else{
						        	return "";
						        }
						    }
						}/*,
						{
							"targets": [13],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon carOperateRecord-operate-view" data-id="'+c.recordId+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">查看</span>';
	        					return find;
							}
						}*/
					]
				});
			}
	    };
	return carOperateRecord;
});


