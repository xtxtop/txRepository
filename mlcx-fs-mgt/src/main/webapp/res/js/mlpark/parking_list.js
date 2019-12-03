define([],function() {	
	var parking={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {
				 $('input').attr('autocomplete','off');//input框清楚缓存
	            //数据列表
                parking.pageList();
				
				//查询
				$("#parkingSearchafter").click(function(){
                    parking.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".matching-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("停车场详情",parking.appPath+ "/parking/toparkingView.do?parkingNo="+$(this).data("id"));
					});
				});
                $(".matching-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("停车场编辑",parking.appPath+ "/parking/editparking.do?parkingNo="+$(this).data("id"));
                    });
                });
                $(".matching-operate-reply").each(function(id,obj){
	        		$(obj).on("click",function(){
	        			var x=[];
	        			x=$(obj).data("id").split(',')
	                	var parkingStatus='';
	                	if(x[1]==0){
	                		parkingStatus='启用';
	                	}else{
	                		parkingStatus='停用';
	                	}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+parkingStatus+"吗？", function(result) {
							if(result){
								parking.reply(x[0],x[1],parkingStatus);
							}						
						}); 					
					});
				});
                $(".matching-operate-del").on("click",function(){
					var parkingNo = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							$.post("parking/delParking.do",{parkingNo:parkingNo},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#parkingList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
								}
								
							});
						}						
       				});
					
				});
	        },
	        reply:function(id,parkingStatus,parkingStatuss){
	        	$.ajax({
	        		url: parking.appPath+ "/parking/upParking.do?parkingNo="+id+"&parkingStatus="+parkingStatus, 
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#parkingList").DataTable().ajax.reload(function(){
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+parkingStatuss+"成功！");
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+parkingStatuss+"失败！");
	        			}
	        		}
	        	});      
	        },
	        del:function(id,enableStatus,status){
	        	$.ajax({
	        		url: parking.appPath+ "/parking/toupdateStatus.do?parkingNo="+id+"&status="+enableStatus,
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#parkingList").DataTable().ajax.reload(function(){
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"成功！");
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+status+"失败！");
	        			}
	        		}
	        	});      
	        },
			pageList:function () {	
				var matchingBtnTpl = $("#parkingTpl").html();
				// 预编译模板
				var template = Handlebars.compile(matchingBtnTpl);
				$('#parkingList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": parking.appPath+'/parking/toparkingList.do',
						"data": function ( d ) {
							var form = $("form[name=parkingSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"parkingStatus":$.trim(form.find("select[name=parkingStatus]").val()),
                                "operatingCityNo":$.trim(form.find("select[name=operatingCityNo]").val()),
                                "parkingName":$.trim(form.find("input[name=parkingName]").val()),
                                "parkingType":$.trim(form.find("select[name=parkingType]").val()),
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
					    { "title":"停车场图片","data": "fileUrl" ,"defaultContent":"--"},
			            { "title":"停车场编号","data": "parkingNo" ,"defaultContent":"--"},
						{ "title":"停车场名称","data": "parkingName" ,"defaultContent":"--"},
						{ "title":"运营城市","data": "operatingCityName" ,"defaultContent":"--"},
						{ "title":"所在区域","data": "addrStreet" ,"defaultContent":"--"},
						{ "title":"总车位数","data": "parkingSpaceNumber" ,"defaultContent":"--"},
						{ "title":"剩余车位数","data": "surplusSpaceNumber" ,"defaultContent":"--"},
                        { "title":"停车场类型","data": "parkingType" ,"defaultContent":"--"},
                        { "title":"停车场状态","data": "parkingStatus" ,"defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#parkingTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#parkingTool").html("");
						$("#parkingTool").removeClass("col-xs-6");
					   $("#parkingTool").append('<button type="button" class="btn-new-type parkingTools-operate-addparking">新增</button>');
					   $(".parkingTools-operate-addparking").on("click",function(){
		       				addTab("新增停车场", parking.appPath+ "/parking/addparking.do");
		       			});
					},
					"drawCallback": function( settings ) {
                        parking.operateColumEvent();
	        	    },
					"columnDefs": [
					     {
							"targets": [0],
							"render": function (a, b, c, d) {
								return "<img src='"+parking.imgPath+"/"+a+"'width='120' height='120'>" 
							}
						 },   
                        {
                            "targets" : [7],
                            "render" : function(a,
                                                b, c, d) {
                                if(a!=null){
                                    if(a==0){
                                        return "闸机";
                                    }else if(a==1){
                                        return "地锁";
                                    }else{
                                    	return "无设备";
                                    }
                                }else{
                                    return "--";
                                }
                            }
                        },
                        {
                            "targets" : [4],
                            "render" : function(a,
                                                b, c, d) {
                                if(a!=null){
                                	if(c.districtName!=null){
                                		return c.provinceName+c.cityname+c.districtName+a;
                                	}else{
                                		return c.provinceName+c.cityname+a;
                                	}
                                }else{
                                    return "--";
                                }
                            }
                        },
                        {
                            "targets" : [5],
                            "render" : function(a,
                                                b, c, d) {
                                	return c.undergroundParkingSpaceNumber+c.groundParkingSpaceNumber+c.parkingSpaceNumber;
                            }
                        },
                        {
                        	"targets" : [6],
                        	"render" : function(a,
                        			b, c, d) {
                        			return c.undergroundSurplusSpaceNumber+c.groundSurplusSpaceNumber+c.surplusSpaceNumber;
                        	}
                        },
                        {
                            "targets" : [8],
                            "render" : function(a,
                                                b, c, d) {
                                if(a!=null){
                                    if(a==0){
                                        return "启用";
                                    }else if(a==1){
                                        return "停用";
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
							"targets": [10],
							"render": function (a, b, c, d) {
								var reply="";
								var view='<span class="glyphicon matching-operate-view" data-id="'+c.parkingNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                                var edit='<span class="glyphicon matching-operate-edit" data-id="'+c.parkingNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
                                var del='<span class="glyphicon  matching-operate-del" data-id="'+c.parkingNo+'" data-toggle="tooltip" data-placement="top">删除</span>';
                                if(c.parkingStatus==0){
									reply='<span class="glyphicon matching-operate-reply" data-id="'+c.parkingNo+',1" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									reply='<span class="glyphicon matching-operate-reply" data-id="'+c.parkingNo+',0" data-toggle="tooltip" data-placement="top">启用</span>';
								}
								return view+edit+reply;
							}
						}
					]
				});
			},
			
	    };
	return parking;
});


