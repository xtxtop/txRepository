define([],function() {	
	var loveCar={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
                loveCar.pageList();
				//查询
				$("#loveCarMengLongSearchafter").click(function(){
                    loveCar.pageList();
	            });

                $("#closechargingStationView").click(function(){
                    closeTab("爱车详情");
                });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".loveCar-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("爱车详情",loveCar.appPath+ "/cLoveCar/toLoveCarView.do?loveCarNo="+$(this).data("id"));
					});
				});
				$(".loveCar-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("爱车编辑",loveCar.appPath+ "/cLoveCar/toLoveCarEdit.do?loveCarNo="+$(this).data("id"));
					});
				});
				$(".loveCar-operate-censor").each(function(id,obj){
					$(this).on("click",function(){
						addTab("爱车审核",loveCar.appPath+ "/cLoveCar/toLoveCarCensorView.do?loveCarNo="+$(this).data("id"));
					});
				});
				$(".loveCar-operate-available").each(function(id,obj){
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
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+availableName+"广告吗？", function(result) {
							if(result){
								 $.ajax({
	    				    			url:loveCar.appPath+"/advertMengLong/delAdvert.do",//删除活动
	    				    			type:"post",
	    				    			data:{advertNo:id,isAvailable:isAvailable},
	    				    			dataType:"json",
	    				    			success:function(res){
	    				    				var code=res.code;
	    				    					if(code == "1"){
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告"+availableName+"操作成功", function() {
	    				    							$("#loveCarMengLongList").DataTable().ajax.reload(function(){});
	    				    						});
	    				    					}else{
	    				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "广告"+availableName+"操作失败");
	    				    					}
	    				    				}
	    				    			});
							}
	       				});
					});
				});
	        },
			pageList:function () {	
				var loveCarMengLongTpl = $("#loveCarMengLongTpl").html();
				// 预编译模板
				var template = Handlebars.compile(loveCarMengLongTpl);
				$('#loveCarMengLongList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": loveCar.appPath+'/cLoveCar/pageListLoveCar.do',
						"data": function ( d ) {
							var form = $("form[name=loveCarMengLongSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
                                "loveCarNo":form.find("input[name=loveCarNo]").val(),
                                "memberNo":form.find("input[name=memberNo]").val(),
                                "vehicleBrand":form.find("input[name=vehicleBrand]").val(),
                                "vehicleModel":form.find("input[name=vehicleModel]").val(),
	        					"censorStatus":form.find("select[name=censorStatus]").val(),
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
			            { "title":"爱车编号","data": "loveCarNo","defaultContent":"--" },
			            { "title":"会员编号","data": "memberNo","defaultContent":"--" },
						{ "title":"车辆品牌","data": "vehicleBrand","defaultContent":"--" },
						{ "title":"车辆型号","data": "vehicleModel","defaultContent":"--" },
                        { "title":"审核状态","data": "censorStatus","defaultContent":"--" },
                        { "title":"记录时间","data": "createTime","defaultContent":"--" },
                        { "title":"修改时间","data": "updateTime","defaultContent":"--" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#advertMengLongTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#advertMengLongTools").html("");
					   $("#advertMengLongTools").removeClass("col-xs-6");
					},
					"drawCallback": function( settings ) {
                        loveCar.operateColumEvent();
	        	    },
					"columnDefs": [
{
						    "targets": [4],
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
						    			return "--";
						    		}
						    }
						},
						{
						    "targets": [5],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
							    	}else{
							    		return "--";
							    	}
						    }
						},
                        {
                            "targets": [6],
                            "render": function(data, type, row, meta) {
                                if(data){
                                    var now = moment(data);
                                    return now.format('YYYY-MM-DD HH:mm:ss');
                                }else{
                                    return "--";
                                }
                            }
                        },
						{
							"targets": [7],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon loveCar-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.loveCarNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit='<span class="glyphicon loveCar-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.loveCarNo+'" data-toggle="tooltip" data-placement="top" >编辑</span>';
	        					var censor="";
	        					//（1、可用，0、不可用，默认0）   （0、未审核，1、已审核，2、待审核，3、未通过，默认0）
								censor='<span class="glyphicon loveCar-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.loveCarNo+'" data-toggle="tooltip" data-placement="top">审核</span>';
								return find + edit + censor
	        					
							}
						}
					]
				});
			},
	    };
	return loveCar;
});

//初始选择
function initSelectList(s,x){
	 for(var i=0;i<x.length;i++){
			if(s==(i+1)){
			return x[i];
			}
		}
}
