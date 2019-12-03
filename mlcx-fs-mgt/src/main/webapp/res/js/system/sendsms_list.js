
define([],function() {
	
    var sendsms= {
        appPath:getPath("app"),	
        init: function () {
			$("#sendsmsSearch").click(function(){
				sendsms.pageList();
            });
            sendsms.pageList();
            /*sendsms.getsendsmsLevelList();*/
            $("#sendsmsModal").on("hidden.bs.modal", function() {
            	var form = $("form[name=sendsmsForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
        },
        operateColumEvent: function(){
        },
        pageList:function () {
        	
        	var sendsmsBtnTpl = $("#sendsmsBtnTpl").html();
			//预编译模板
			var template = Handlebars.compile(sendsmsBtnTpl);
			var table = $('#sendsms').dataTable( {
				searching:false,
				destroy : true,
        		"ajax": {
        			"type": "POST",
        			"url": sendsms.appPath+"/sendsms/querySendSms.do",
        			"data": function ( d ) { 
        				var form = $("form[name=sendsmsSearchForm]");
        				return $.extend( {}, d, {
        					"pageNo": (d.start/d.length)+1,
        					"pageSize":d.length,
        					"mobilePhone":form.find("input[name=mobilePhone]").val(),
        					"templateType":form.find("select[name=templateType]").val(),
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
					{ "title":"手机号","data": "mobilePhone" },
					{ "title":"短信模板类型","data": "templateType" },
					{ "title":"短信内容","data": "smsContent" },
					{ "title":"创建时间","data": "createTime" },
					{ "title":"更新时间","data": "updateTime" },
					{ "title":"发送时间","data": "sendTime" } 
					//{ "title":"操作","orderable":false}
        		],
        	   "dom": "<'row'<'#sendsmstool.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
        	   initComplete: function () {
        	   },
				"drawCallback": function( settings ) {
					sendsms.operateColumEvent();
        	    },
        		"columnDefs": [
					{
						"render": function(data, type, row) {
			            	 if(data == 1){
			            		data = "注册";
			            	}else if(data == 2){
			            		data = "修改密码";
			            	}else if(data == 3){
			            		data = "车辆异常信息";
			            	}
			            	
			                return data ;
			            },
			            "targets": 1
						
					}, 
					{
        	            "targets": [3,4,5],
        	            "render": function(data, type, row, meta) {
        	            	if(data){
        	            		return moment(data).format("YYYY-MM-DD HH:mm:ss"); 
        	            	}else{
        	            		return "";
        	            	}
        	            	
        	            }
        	        }
        		]
        	} );
			$("#myModal").on("shown.bs.modal", function () {
				//$('#myInput').focus()；
				});
			$('#onOFFDate').daterangepicker();
        }
    };
    return  sendsms;
});



