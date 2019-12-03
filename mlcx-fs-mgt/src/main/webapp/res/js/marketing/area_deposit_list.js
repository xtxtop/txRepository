define([],function() {	
	var areaDeposit={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				areaDeposit.pageList();
				//查询
				$("#areaDepositSearchafter").click(function(){
					var form = $("form[name=areaDepositSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							areaDeposit.pageList();
						}
					}else{
						areaDeposit.pageList();
					}
	            });
				$("#closeAreaDepositView").click(function(){
					closeTab("地区应缴押金详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".areaDeposit-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("地区应缴押金详情",areaDeposit.appPath+ "/areaDeposit/toAreaDepositView.do?id="+$(this).data("id"));
					});
				});
				$(".areaDeposit-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("地区应缴押金编辑",areaDeposit.appPath+ "/areaDeposit/toUpdateAreaDeposit.do?id="+$(this).data("id"));			
					});
				});
				$(".areaDeposit-operate-censor").each(function(id,obj){
					$(this).on("click",function(){
						addTab("地区应缴押金审核",areaDeposit.appPath+ "/areaDeposit/toAreaDepositCensorView.do?id="+$(this).data("id"));			
					});
				});
				$(".areaDeposit-operate-available").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var isAvailable=$(this).data("available");
						var availableName = "启用";
						if(isAvailable == 1){
							isAvailable=0;
							availableName = "停用";
						}else{
							isAvailable=1;
						}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+availableName+"地区应缴押金吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:areaDeposit.appPath+"/areaDeposit/updateAreaDeposit.do",//启动地区应缴押金
	    				    			type:"post",
	    				    			data:{id:id,isAvailable:isAvailable},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){ 
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "地区应缴押金"+availableName+"操作成功", function() {
	    				    							$("#areaDepositList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "地区应缴押金"+availableName+"操作失败");
	    				    					}
	    				    				}
	    				    			});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var areaDepositTpl = $("#areaDepositTpl").html();
				// 预编译模板
				var template = Handlebars.compile(areaDepositTpl);
				$('#areaDepositList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": areaDeposit.appPath+'/areaDeposit/pageListAreaDeposit.do',
						"data": function ( d ) {
							var form = $("form[name=areaDepositSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"addrRegion":form.find("input[name=addrRegion]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
	        					"censorStatus":form.find("select[name=censorStatus]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
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
			            { "title":"地区名称","data": "addrRegion" },
						{ "title":"应缴押金","data": "depositAmount" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"审核状态","data": "censorStatus" },
						{ "title":"启用状态","data": "isAvailable" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#areaDeposittool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#areaDepositTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#areaDepositTools").html("");
					   $("#areaDepositTools").removeClass("col-xs-6");
		       			$("#areaDepositTools").append('<button type="button" class="btn-new-type areaDepositTools-operate-add">新增</button>');
		       			$(".areaDepositTools-operate-add").on("click",function(){
		       				addTab("新增地区应缴押金", areaDeposit.appPath+ "/areaDeposit/toAddAreaDeposit.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						areaDeposit.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [0],
						    "render": function(data, type, row, meta) {
					    		var addrRegion = "";
					    		if(row.addrRegion1Name!=null){
					    			addrRegion += row.addrRegion1Name;
					    		}
					    		if(row.addrRegion2Name!=null){
					    			addrRegion += row.addrRegion2Name;
					    		}
					    		if(row.addrRegion3Name!=null){
					    			addrRegion += row.addrRegion3Name;
					    		}
					    		return addrRegion;
						    }
						},
						{
						    "targets": [2],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						        	return now.format('YYYY-MM-DD HH:mm:ss'); 
							    	}else{
							    		return "";
							    	}
						    }
						},{
						    "targets": [3],
						    "render": function(a,b, c, d) {//0、未审核，1、已审核，2、待审核，3、未通过
						    		if(a==0){
						    			return "未审核";
						    		}else if(a==1){
						    			return "已审核";
						    		}else if(a==2){
						    			return "待审核";
						    		}else if(a==3){
						    			return "未通过";
						    		}else{
						    			return "";
						    		}
						    }
						},{
						    "targets": [4],
						    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "可用";
						    		}else if(a==0){
						    			return "不可用";
						    		}else{
						    			return "";
						    		}
						    }
						},{
							"targets": [5],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon areaDeposit-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.id+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit='<span class="glyphicon areaDeposit-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.id+'" data-toggle="tooltip" data-placement="top" >修改</span>';
	        					var censor="";
	        					var available="";
								if(c.censorStatus==0){
									censor='<span class="glyphicon areaDeposit-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.id+'" data-toggle="tooltip" data-placement="top">审核</span>';
								}else if(c.censorStatus==1){
									if(c.isAvailable==1){
										edit="";
										available='<span class="glyphicon areaDeposit-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.id+'" data-available="'+c.isAvailable+'" data-toggle="tooltip" data-placement="top">停用</span>';
									}else{
										available='<span class="glyphicon areaDeposit-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.id+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
									}
								}
								
	        					return find+edit+censor+available;
							}
						}
					]
				});
			}
	    };
	return areaDeposit;
});


