define([],function() {	
	var parkBilling={
			appPath:getPath("app"),	
			init: function () {
				 $('input').attr('autocomplete','off');//input框清楚缓存
	            //数据列表
                parkBilling.pageList();
				
				//查询
				$("#parkBillingSearchafter").click(function(){
                    parkBilling.pageList();
	            });
				$("#closematchingView").click(function(){
					closeTab("计费规则");
					
	            });
			},
			//方法加载
	        operateColumEvent: function(){
                $(".matching-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("计费规则编辑",parkBilling.appPath+ "/parkBilling/editParkBilling.do?parkBillingNo="+$(this).data("id"));
                    });
                });
                $(".parkBilling-operate-reply").each(function(id,obj){
	        		$(obj).on("click",function(){
	        			var x=[];
	        			x=$(obj).data("id").split(',')
	                	var status='';
	                	if(x[1]==1){
	                		status='启用';
	                	}else{
	                		status='停用';
	                	}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+status+"吗？", function(result) {
							if(result){
                                parkBilling.del(x[0],x[1],status);
							}						
						}); 					
					});
				});
	        },
	        del:function(id,enableStatus,status){
	        	$.ajax({
	        		url: parkBilling.appPath+ "/parkBilling/toupdateStatus.do?parkBillingNo="+id+"&status="+enableStatus,
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#parkBillingList").DataTable().ajax.reload(function(){
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"成功！");
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"失败！");
	        			}
	        		}
	        	});      
	        },
			pageList:function () {	
				var matchingBtnTpl = $("#parkBillingTpl").html();
				// 预编译模板
				var template = Handlebars.compile(matchingBtnTpl);
				$('#parkBillingList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parkBilling.appPath+'/parkBilling/toParkBillingList.do',
						"data": function ( d ) {
							var form = $("form[name=parkBillingSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"status":$.trim(form.find("select[name=status]").val()),
                                "parkBillingNo":$.trim(form.find("input[name=parkBillingNo]").val()),
                                "parkBillingName":$.trim(form.find("input[name=parkBillingName]").val()),
                                "billingVersion":$.trim(form.find("input[name=billingVersion]").val()),
                                "parkType":$.trim(form.find("select[name=parkType]").val()),
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
			            { "title":"停车计费方案编号","data": "parkBillingNo" ,"defaultContent":"--"},
						{ "title":"计费方案名称","data": "parkBillingName" ,"defaultContent":"--"},
						{ "title":"计费方案版本","data": "billingVersion" ,"defaultContent":"--"},
						{ "title":"停车类型","data": "parkType" ,"defaultContent":"--"},
                        { "title":"预约免费时长（分钟）","data": "freeTime" ,"defaultContent":"--"},
                        { "title":"金额","data": "overtimePrice" ,"defaultContent":"--"},
                        { "title":"计费时间单位（分钟）","data": "unitTime" ,"defaultContent":"--"},
                        { "title":"封顶金额","data": "cappingPrice" ,"defaultContent":"--"},
                        { "title":"状态","data": "status" ,"defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#parkBillingTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#parkBillingTool").html("");
						$("#parkBillingTool").removeClass("col-xs-6");
					   $("#parkBillingTool").append('<button type="button" class="btn-new-type parkBillingTools-operate-addparkBilling">新增</button>');
					   $(".parkBillingTools-operate-addparkBilling").on("click",function(){
		       				addTab("新增计费规则", parkBilling.appPath+ "/parkBilling/addParkBilling.do");
		       			});
					},
					"drawCallback": function( settings ) {
                        parkBilling.operateColumEvent();
	        	    },
					"columnDefs": [
                        {
                            "targets" : [3],
                            "render" : function(a,
                                                b, c, d) {
                                if(a!=null){
                                    if(a==0){
                                        return "闸";
                                    }else if(a==1){
                                        return "地锁";
                                    }
                                }else{
                                    return "--";
                                }
                            }
                        },
                        {
							"targets" : [ 9 ],
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
						    "targets": [8],
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
							"targets": [10],
							"render": function (a, b, c, d) {
								var reply="";
                                var edit='<span class="glyphicon matching-operate-edit" data-id="'+c.parkBillingNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								if(c.status==1){
									reply='<span class="glyphicon parkBilling-operate-reply" data-id="'+c.parkBillingNo+',0" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									reply='<span class="glyphicon parkBilling-operate-reply" data-id="'+c.parkBillingNo+',1" data-toggle="tooltip" data-placement="top">启用</span>';
								}
								return edit+reply;
							}
						}
					]
				});
			},
			
	    };
	return parkBilling;
});


