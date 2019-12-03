define([],function() {	
	var operatingCity={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				operatingCity.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#operatingCitySearchafter").click(function(){
					operatingCity.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
                $(".operatingCity-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("运营城市编辑",operatingCity.appPath+ "/operatingCity/toOperatingCityEdit.do?operatingCityNo="+$(this).data("id"));
                    });
                });
                $(".operatingCity-operate-reply").each(function(id,obj){
	        		$(obj).on("click",function(){
	        			var x=[];
	        			x=$(obj).data("id").split(',')
	                	var status='';
	                	if(x[1]==1){
	                		status='启用';
	                		bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+status+"吗？", function(result) {
	                			if(result){
	                				operatingCity.del(x[0],x[1],status);
	                			}						
	                		}); 	
	                	}else{
	                		status='停用';
	                		$.post('chargingStation/getPeratingCity_Employ.do', {operatingCityNo:x[0].trim()}, function(data){
	                			if(data==1){
	                				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;当前运营城市下存在充电站不能进行停用!");
	                			}else{
	                				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+status+"吗？", function(result) {
	    	                			if(result){
	    	                				operatingCity.del(x[0],x[1],status);
	    	                			}						
	    	                		}); 	
	                			}
	                		});
	                		
	                	}
					});
				});
	        },
	        del:function(id,enableStatus,status){
	        	$.ajax({
	        		url: operatingCity.appPath+ "/operatingCity/upOperatingCity.do?operatingCityNo="+id+"&enableStatus="+enableStatus, 
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#operatingCityList").DataTable().ajax.reload(function(){
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"成功！");
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"失败！");
	        			}
	        		}
	        	});      
	        },
			pageList:function () {	
				var operatingCityBtnTpl = $("#operatingCityTpl").html();
				// 预编译模板
				var template = Handlebars.compile(operatingCityBtnTpl);
				$('#operatingCityList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": operatingCity.appPath+'/operatingCity/PageFinderOperatingCity.do',
						"data": function ( d ) {
							var form = $("form[name=operatingCitySearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"enableStatus":$.trim(form.find("select[name=enableStatus]").val()),
								"operatingCityNo":$.trim(form.find("input[name=operatingCityNo]").val()),
								
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							//alert(JSON.stringify(json.data))
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			            { "title":"运营城市编号","data": "operatingCityNo" ,"defaultContent":"--"},
						{ "title":"运营城市名称","data": "operatingCityName" ,"defaultContent":"--"},
						{ "title":"经度","data": "longitude" ,"defaultContent":"--"},
						{ "title":"纬度","data": "latitude","defaultContent":"--"},
						{ "title":"更新时间","data": "createTime","defaultContent":"--"},
						{ "title":"使用状态","data": "enableStatus","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#operatingCityTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#operatingCityTool").html("");
						$("#operatingCityTool").removeClass("col-xs-6");
					   $("#operatingCityTool").append('<button type="button" class="btn-new-type operatingCityTools-operate-addoperatingCity">新增</button>');
					   $(".operatingCityTools-operate-addoperatingCity").on("click",function(){
		       				addTab("新增运营城市", operatingCity.appPath+ "/operatingCity/toAddOperatingCity.do");
		       			});
					},
					"drawCallback": function( settings ) {
						operatingCity.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
						{
							"targets" : [ 4],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var now = moment(a);
									return now.format('YYYY-MM-DD');
								}else{
									return "--";
								}
							}
						},  
								  
						{
						    "targets": [5],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "停用";
						        	}else if(a==1){
						        		return "启用";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [6],
							"render": function (a, b, c, d) {
								var reply="";
                                var edit='<span class="glyphicon operatingCity-operate-edit" data-id="'+c.operatingCityNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								if(c.enableStatus==1){
									reply='<span class="glyphicon operatingCity-operate-reply" data-id="'+c.operatingCityNo+',0" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									reply='<span class="glyphicon operatingCity-operate-reply" data-id="'+c.operatingCityNo+',1" data-toggle="tooltip" data-placement="top">启用</span>';
								}
								return edit+reply;
							}
						}
					]
				});
			},
			
	    };
	return operatingCity;
});


