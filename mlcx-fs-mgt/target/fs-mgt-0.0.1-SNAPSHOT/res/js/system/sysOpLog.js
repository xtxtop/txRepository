define([],function() {	
	var sysOpLog={
			appPath:getPath("app"),	
			init: function () {				
				$("#sysOpLogEditBtn").click(function(){
					sysOpLog.addOrEdit();
	            });	         
	            //数据列表
				sysOpLog.pageList();
				//搜索
				$("#sysOpLogSearchafter").click(function(){
					sysOpLog.pageList();
	            });	
				//时间控件(放最下面防止form冲突)				
				var form = $("form[name=sysOpLogSearchForm]");
	            form.find("input[name=createTime]").daterangepicker({
	            	timePicker : true, 
	            	timePickerIncrement:1,
	            	format : "YYYY/MM/DD HH:mm:ss"
	            });				
			},	
	        pageList:function () {	
			var tpl = $("#sysOpLogTpl").html();
			//预编译模板
			var template = Handlebars.compile(tpl);
			$('#sysOpLogList').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": sysOpLog.appPath+'/sysOpLog/getSysOpLogList.do',
					"data": function ( d ) {
						var form = $("form[name=sysOpLogSearchForm]");
						var createTime=form.find("input[name=createTime]").val();
        				var startCreatTime;
        				var endCreatTime;
        				if(createTime){
        					var temp=createTime.split("-");
        					startCreatTime=temp[0];
        					endCreatTime=temp[1];
        				}
						return $.extend( {}, d, {							
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"operatorUserName":form.find("input[name=operatorUserName]").val(),
							"moduleName":form.find("input[name=moduleName]").val(),
							"startCreateTime":startCreatTime,
        					"endCreateTime":endCreatTime
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
		            { "title":"操作人","data": "operatorUserName" },
					{ "title":"操作模块","data": "moduleName" },
					{ "title":"操作动作","data": "opType" },
					{ "title":"操作内容","data": "logMsg" },
					{ "title":"操作时间","data": "createTime" },
					//{ "title":"操作"}
				],
			   //"dom": "<'row'<'col-xs-2'l><'#sysOpLogtool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
			   "dom": "<'row'<'#sysOpLogTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
					//$("#sysOpLogTools").append('<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#mySysOpLogModal">添加</button>');
				},
				"columnDefs": [
					{
					    "targets": [2],
					    "render": function(data, type, row, meta) {
					    
					    	if(data == "C"){   //操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
					    		data="新建";
					    	}else if(data=="U"){
					    		data ="更新";
					    	}else if(data=="D"){
					    		data ="删除";
					    	}
					    	else if(data=="O"){
					    		data ="其他操作/组合操作";
					    	}
					        return data ;
					    }
					},
					{
	        	            "targets": [4],
	        	            "render": function(data, type, row, meta) {
	        	            	var now = moment(data); 
	        	            	return now.format('YYYY-MM-DD HH:mm:ss'); 
	        	            }
	        	    }
	        	    
				]
			});
			$("#myModal").on("shown.bs.modal", function () {
				//$('#myInput').focus()；
			});
			//$('#sysOpLogDate').daterangepicker();
		}
    };
	return sysOpLog;
});


