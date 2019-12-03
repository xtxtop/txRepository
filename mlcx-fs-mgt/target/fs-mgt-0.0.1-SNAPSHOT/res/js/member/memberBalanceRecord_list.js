define([],function() {	
	var memberBalanceRecord={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				memberBalanceRecord.pageList();
				//查询
				$("#memberBalanceRecordSearch").click(function(){
					memberBalanceRecord.pageList();
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        },
			pageList:function () {	
				var memberBalanceRecordTpl = $("#memberBalanceRecordTpl").html();
				// 预编译模板
				var template = Handlebars.compile(memberBalanceRecordTpl);
				$('#memberBalanceRecordList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": memberBalanceRecord.appPath+'/member/pageListMemberBalanceRecord.do',
						"data": function ( d ) {
							var form = $("form[name=memberBalanceRecordSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"memberNo":form.find("input[name=memberNo]").val(),
								"type":form.find("select[name=type]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59"
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
								{ "title":"交易类型","data": "type" },
								{ "title":"交易金额","data": "trnasactionAmount" },
								{ "title":"原始金额","data": "originalAmount" },
								{ "title":"交易后余额","data": "balance" },
								{ "title":"交易日期","data": "createTime" },
							],
						   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
						   "dom": "<'row'<'#memberBalanceTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   $("#memberBalanceTools").html("");
						},
						"drawCallback": function( settings ) {
							//memberBalance.operateColumEvent();
		        	    },
						"columnDefs": [
							{
							    "targets": [1],
							    "render": function(a,b, c, d) {
						    		if(a==1){
						    			return "充值";
						    		}else if(a==2){
						    			return "赠送";
						    		}else if(a==3){
						    			return "抵扣";
						    		}else  if(a==3){
						    			return "返还";
						    		}
						    		return "";
							    }
							},{
						    "targets": [5],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						    		return now.format('YYYY-MM-DD'); 
						    	}else{
						    		return "";
						    	}
						    }
						}
					]
				});
			}
	    };
	return memberBalanceRecord;
});


