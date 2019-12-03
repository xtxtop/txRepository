define([],function() {	
	var pricingRuleCustomDate={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				pricingRuleCustomDate.pageList();
				//查询
				$("#pricingRuleCustomDateSearchafter").click(function(){
					pricingRuleCustomDate.pageList();
	            });
				
			},
				
			//方法加载
	        operateColumEvent: function(){
	        	//自定义计费规则编辑
				$(".pricingRuleCustomDate-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("自定义计费调整",pricingRuleCustomDate.appPath+ "/pricingRuleDay/toEditPricingRuleCustomDate.do?customizedId="+$(this).data("id"));			
					});
				});
				//自定义计费规则删除
				$(".pricingRuleCustomDate-operate-del").on("click",function(){
					var customizedId = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							$.post("pricingRuleDay/delPricingRuleCustomDate.do",{customizedId:customizedId},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#pricingRuleCustomDateList").DataTable().ajax.reload(function(){
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
			pageList:function () {	
				var pricingRuleCustomDateTpl = $("#pricingRuleCustomDateTpl").html();
				// 预编译模板
				var template = Handlebars.compile(pricingRuleCustomDateTpl);
				$('#pricingRuleCustomDateList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": pricingRuleCustomDate.appPath+'/pricingRuleDay/pageListPricingRuleCustomDate.do',
						"data": function ( d ) {
							var form = $("form[name=pricingRuleCustomDateSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"ruleId":form.find("input[name=ruleId]").val(),
								"customizedDateStart":form.find("input[name=customizedDateStart]").val(),
								"customizedDateEnd":form.find("input[name=customizedDateEnd]").val()
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
			            { "title":"计费规则名称","data": "ruleName","defaultContent":"" },
			            //{ "title":"城市","data": "cityName","defaultContent":"" },
						{ "title":"车辆型号","data": "carModelName","defaultContent":"" },
						{ "title":"自定义日期","data": "customizedDate","defaultContent":"" },
						{ "title":"自定义日期按天计费","data": "priceOfDayCustomized","defaultContent":"" },
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#pricingRuleCustomDateTools'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#pricingRuleCustomDateTools").html("");
		       			$("#pricingRuleCustomDateTools").append('<button type="button" class="btn-new-type pricingRuleCustomDateTools-operate-add">新增</button>');
		       			$(".pricingRuleCustomDateTools-operate-add").on("click",function(){
		       				var form = $("form[name=pricingRuleCustomDateSearchForm]");
		       				var ruleId=form.find("input[name=ruleId]").val()
		       				addTab("新增自定义计费", pricingRuleCustomDate.appPath+ "/pricingRuleDay/toAddPricingRuleCustomDate.do?ruleId="+ruleId);
		       			});	 
					},
					"drawCallback": function( settings ) {
						pricingRuleCustomDate.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						
						{
						    "targets": [2],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						        	return now.format('YYYY-MM-DD'); 
							    }else{
							    	return "";
							    }
						    }
						},
						{
						    "targets": [3],
						    "render": function(data, type, row, meta) {
						    	if(data!=null){
						        	return data+"元/天"; 
							    }else{
							    	return "";
							    }
						    }
						},
						{
							"targets": [4],
							"render": function (a, b, c, d) {
								var  edit ='<span class="glyphicon pricingRuleCustomDate-operate-edit" data-id="'+c.customizedId+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
								var del='<span class="glyphicon pricingRuleCustomDate-operate-del" data-id="'+c.customizedId+'" data-toggle="tooltip" data-placement="top">删除</span>';
								
	        						return edit+del;
							}
						}
					]
				});
			}
	    };
	return pricingRuleCustomDate;
});


