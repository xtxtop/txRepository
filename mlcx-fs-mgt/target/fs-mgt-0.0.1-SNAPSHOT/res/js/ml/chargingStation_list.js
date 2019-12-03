define([],function() {	
	var chargingStation={
			appPath:getPath("app"),	
			imgPath:getPath("img"),
			init: function () {	
	            //数据列表
				chargingStation.pageList();
				 $('input').attr('autocomplete','off');//input框清楚缓存
				//查询
				$("#chargingStationSearch").click(function(){
					chargingStation.pageList();
	            });
				$("#closechargingStationView").click(function(){
					closeTab("充电站详情");
	            });
				
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".chargingStation-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("充电站详情",chargingStation.appPath+ "/chargingStation/tochargingStationView.do?stationNo="+$(this).data("id"));
					});
				});
                $(".chargingStation-operate-edit").each(function(){
                    $(this).on("click",function(){
                        addTab("充电站编辑",chargingStation.appPath+ "/chargingStation/tochargingStationEdit.do?stationNo="+$(this).data("id"));
                    });
                });
                $(".chargingStation-operate-reply").each(function(id,obj){
	        		$(obj).on("click",function(){
	        			var x=[];
	        			x=$(obj).data("id").split(',')
	                	var isAvailable='';
	                	if(x[1]==1){
	                		isAvailable='启用';
	                	}else{
	                		isAvailable='停用';
	                	}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+isAvailable+"吗？", function(result) {
							if(result){
								chargingStation.del(x[0],x[1],isAvailable);
							}						
						}); 					
					});
				});
	        },
	        del:function(id,isAvailable,isAvailables){
	        	$.ajax({
	        		url: chargingStation.appPath+ "/chargingStation/upChargingStation.do?stationNo="+id+"&isAvailable="+isAvailable, 
	        		success: function(data){
	        			if(data.code="1"){
	        				$("#chargingStationList").DataTable().ajax.reload(function(){
	        					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+isAvailables+"成功！");
	        				});
	        			}else{
	        				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+isAvailables+"失败！");
	        			}
	        		}
	        	});      
	        },
			pageList:function () {	
				var chargingStationBtnTpl = $("#chargingStationTpl").html();
				// 预编译模板
				var template = Handlebars.compile(chargingStationBtnTpl);
				$('#chargingStationList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": chargingStation.appPath+'/chargingStation/pageListchargingStation.do',
						"data": function ( d ) {
							var form = $("form[name=chargingStationSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"stationNo":$.trim(form.find("input[name=stationNo]").val()),
								"isAvailable":$.trim(form.find("select[name=isAvailable]").val()),
								"operatingCityNo":$.trim(form.find("select[name=operatingCityNo]").val()),
								"stationType":$.trim(form.find("select[name=stationType]").val()),
								
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
					    { "title":"充电站图片","data": "fileUrl" ,"defaultContent":"--"},
			            { "title":"充电站编号","data": "stationNo" ,"defaultContent":"--"},
						{ "title":"充电站名称","data": "stationName" ,"defaultContent":"--"},
						{ "title":"运营城市","data": "operatingCityName" ,"defaultContent":"--"},
						{ "title":"详细地址","data": "addrStreet" ,"defaultContent":"--"},
						{ "title":"充电桩数","data": "chargingPileNumber","defaultContent":"--"},
						{ "title":"慢充充电桩数","data": "interflowSum","defaultContent":"--"},
						{ "title":"快充充电桩数","data": "cocurrentSum","defaultContent":"--"},
						{ "title":"充电站状态","data": "isAvailable","defaultContent":"--"},
						{ "title":"类型","data": "stationType","defaultContent":"--"},
						{ "title":"创建时间","data": "createTime","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
					"dom" : "<'row'<'#chargingStationTool'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				initComplete : function() {
					   $("#chargingStationTool").html("");
						$("#chargingStationTool").removeClass("col-xs-6");
					   $("#chargingStationTool").append('<button type="button" class="btn-new-type chargingStationTools-operate-addchargingStation">新增</button>');
					   $(".chargingStationTools-operate-addchargingStation").on("click",function(){
		       				addTab("新增充电站", chargingStation.appPath+ "/chargingStation/toAddchargingStation.do");
		       			});
					},
					"drawCallback": function( settings ) {
						chargingStation.operateColumEvent();
	        	    },
//	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					     {
							"targets": [0],
							"render": function (a, b, c, d) {
							return "<img src='"+chargingStation.imgPath+"/"+a+"'width='120' height='120'>" 
							}
						 },   
						{
							"targets" : [ 10],
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
							"targets" : [ 4],
							"render" : function(a,
									b, c, d) {
								if(a!=null){
									var address='';
									if(c.provinceName!=null){
										address+=c.provinceName;
									}
									if(c.cityName!=null){
										address+=c.cityName;
									}
									if(c.districtName!=null){
										address+=c.districtName;
									}
									return address+a;
								}else{
									return "--";
								}
							}
						},  	  
						{
						    "targets": [8],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "启用";
						        	}else if(a==0){
						        		return "停用";
						        	}
						        }else{
						        	return "--";
						        }
						    }
						},
						{
							"targets": [9],
							"render": function(a,b,c,d) {
								if(a!=null){
									if(a==1){
										return "精品站";
									}else if(a==2){
										return "超级站";
									}
								}else{
									return "--";
								}
							}
						},
						{
							"targets": [11],
							"render": function (a, b, c, d) {
								var reply="";
								var view='<span class="glyphicon chargingStation-operate-view" data-id="'+c.stationNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
                                var edit='<span class="glyphicon chargingStation-operate-edit" data-id="'+c.stationNo+'" data-toggle="tooltip" data-placement="top">编辑</span>';
								if(c.isAvailable==1){
									reply='<span class="glyphicon chargingStation-operate-reply" data-id="'+c.stationNo+',0" data-toggle="tooltip" data-placement="top">停用</span>';
								}else{
									reply='<span class="glyphicon chargingStation-operate-reply" data-id="'+c.stationNo+',1" data-toggle="tooltip" data-placement="top">启用</span>';
								}
								return view+edit+reply;
							}
						}
					]
				});
			},
			
	    };
	return chargingStation;
});


