define([],function() {	
	var memberBalance={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				memberBalance.pageList();
				//查询
				$("#memberBalanceSearch").click(function(){
					memberBalance.pageList();
	            });
				$("#closeCouponView").click(function(){
					closeTab("会员余额详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".memberBalance-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("会员余额详情",memberBalance.appPath+ "/member/toMemberBalanceView.do?memberNo="+$(this).data("id"));
					});
				});
	        	$(".memberBalance-operate-record").each(function(){
					$(this).on("click",function(){
						addTab("会员余额记录列表",memberBalance.appPath+ "/member/toMemberBalanceRecordList.do?memberNo="+$(this).data("id"));
					});
				});
				$(".memberBalance-operate-freeze").each(function(id,obj){
					$(this).on("click",function(){
						debugger
						var id=$(this).data("id");
						var isFreeze = $(this).data("isfreeze");
						var hint = isFreeze == 1 ? "冻结" : "解冻";
						addTab("冻结/解冻余额",memberBalance.appPath+ "/member/toMemberBalanceFreezeView.do?memberNo="+$(this).data("id")+"&isFreeze="+isFreeze);
						/*bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"该会员余额吗？", function(result) {
							if(result){
							}						
	       				});*/
					});
				});
	        },
			pageList:function () {	
				var memberBalanceTpl = $("#memberBalanceTpl").html();
				// 预编译模板
				var template = Handlebars.compile(memberBalanceTpl);
				$('#memberBalanceList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": memberBalance.appPath+'/member/pageListMemberBalance.do',
						"data": function ( d ) {
							var form = $("form[name=memberBalanceSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberNo":form.find("input[name=memberNo]").val(),
								"isFreeze":form.find("select[name=isFreeze]").val()
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
			            { "title":"会员编号","data": "memberNo" },
						{ "title":"账户余额","data": "balance" },
						{ "title":"冻结状态","data": "isFreeze" },
						{ "title":"更新时间","data": "updateTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#memberBalanceTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#memberBalanceTools").html("");
					},
					"drawCallback": function( settings ) {
						memberBalance.operateColumEvent();
	        	    },
					"columnDefs": [
						{
						    "targets": [2],
						    "render": function(a,b, c, d) {
					    		if(a==1){
					    			return "已冻结";
					    		}
					    		return "未冻结";
						    }
						},{
						    "targets": [3],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
		        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
							    	}else{
							    		return "";
							    	}
						    }
						},
						{
							"targets": [4],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon  btn btn-default  memberBalance-operate-find" data-id="'+c.memberNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var record='<span class="glyphicon  btn btn-default  memberBalance-operate-record" data-id="'+c.memberNo+'" data-toggle="tooltip" data-placement="top" >余额记录</span>';
	        					var isFreeze="";
								if (c.isFreeze==0) {
									isFreeze='<span class="glyphicon  btn btn-default  memberBalance-operate-freeze" data-id="'+c.memberNo+'"  data-isFreeze=1 data-toggle="tooltip" data-placement="top">冻结</span>';	
								}else if(c.isFreeze==1) {
									isFreeze='<span class="glyphicon  btn btn-default  memberBalance-operate-freeze" data-id="'+c.memberNo+'" data-isFreeze=0 data-toggle="tooltip" data-placement="top">解冻</span>';	
								}
	        					return find+isFreeze+record;
							}
						}
					]
				});
			}
	    };
	return memberBalance;
});


