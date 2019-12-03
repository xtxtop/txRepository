define([],function() {	
	var pricingRuleDayCustomizedDate={
			appPath:getPath("app"),	
			init: function () {	
				
				
				
				
	            //数据列表
				pricingRuleDayCustomizedDate.pageList();
				//查询
				$("#pricingRuleDayCustomizedDateSearchafter").click(function(){
					pricingRuleDayCustomizedDate.pageList();
	            });
				
				
				
			},
				
			//方法加载
	        operateColumEvent: function(){
	        	
				$(".pricingRuleDayCustomizedDate-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("自定义计费调整",pricingRuleDayCustomizedDate.appPath+ "/pricingRuleDay/toEditPricingRuleDayCustomizedDate.do?customizedId="+$(this).data("id"));			
					});
				});
				
				$(".pricingRuleDayCustomizedDate-operate-del").on("click",function(){
					var customizedId = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
						
							$.post("pricingRuleDay/delPricingRuleDayCustomizedDate.do",{customizedId:customizedId},function(res){	
								if(res.code==1){

									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#pricingRuleDayCustomizedDateList").DataTable().ajax.reload(function(){
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
				var pricingRuleDayCustomizedDateTpl = $("#pricingRuleDayCustomizedDateTpl").html();
				// 预编译模板
				var template = Handlebars.compile(pricingRuleDayCustomizedDateTpl);
				$('#pricingRuleDayCustomizedDateList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": pricingRuleDayCustomizedDate.appPath+'/pricingRuleDay/pageListPricingRuleDayCustomizedDate.do',
						"data": function ( d ) {
							var form = $("form[name=pricingRuleDayCustomizedDateSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"ruleNo":form.find("input[name=ruleNo]").val()
								
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
						{ "title":"城市","data": "cityName","defaultContent":"" },
						{ "title":"车辆品牌","data": "carBrandName","defaultContent":"" },
						{ "title":"车辆型号","data": "carModelName","defaultContent":"" },
						{ "title":"自定义日期","data": "customizedDate","defaultContent":"" },
						{ "title":"自定义日期按天计费(元/天)","data": "priceOfDayCustomized","defaultContent":"" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#pricingRuleDayCustomizedDatetool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#pricingRuleDayCustomizedDateTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#pricingRuleDayCustomizedDateTools").html("");
		       			$("#pricingRuleDayCustomizedDateTools").append('<button type="button" class="btn btn-default btn-sm pricingRuleDayCustomizedDateTools-operate-add">新增</button>');
		       			$(".pricingRuleDayCustomizedDateTools-operate-add").on("click",function(){
		       				var form = $("form[name=pricingRuleDayCustomizedDateSearchForm]");
		       				var ruleNo=form.find("input[name=ruleNo]").val()
		       				addTab("新增自定义计费", pricingRuleDayCustomizedDate.appPath+ "/pricingRuleDay/toAddPricingRuleDayCustomizedDate.do?ruleNo="+ruleNo);
		       			});	 
					},
					"drawCallback": function( settings ) {
						pricingRuleDayCustomizedDate.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
						
						{
						    "targets": [4],
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
							"targets": [6],
							"render": function (a, b, c, d) {
								var  edit ='<span class="glyphicon  btn btn-default  pricingRuleDayCustomizedDate-operate-edit" data-id="'+c.customizedId+'" data-toggle="tooltip" data-placement="top" title="调整">调整</span>';
								var del='<span class="glyphicon  btn btn-default  pricingRuleDayCustomizedDate-operate-del" data-id="'+c.customizedId+'" data-toggle="tooltip" data-placement="top">删除</span>';
								
	        						return edit+del;
							}
						}
//						
					]
				});
			}
	    };
	return pricingRuleDayCustomizedDate;
});


