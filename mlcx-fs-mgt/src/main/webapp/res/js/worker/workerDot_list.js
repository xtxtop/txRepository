define([],function() {	
	var workerDot={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				workerDot.pageList();
				//查询
				$("#workerDotSearchafter").click(function(){
					workerDot.pageList();
	            });
	            //批量添加
	            $("#addWorkerDots").click(function(){
					workerDot.addaddWorkerDot();
	            });
				
			},
				 
					  
			//方法加载
	        operateColumEvent: function(){
	        	
	        },
	        
	        addaddWorkerDot:function(){
	        	debugger
	        	var parkIds=[];
				var len=$('#parkInfoMs input[type="checkbox"]:checked');
				if(len.length==0){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择场站！")
				}else{
					$("#parkInfoMs").find("input:checked").each(function(){
						parkIds.push($(this).val());
    					
    				});
					var form = $("form[name=workerDotSearchForm]");
		        	var workerId = form.find("input[name=workerId]").val();
					$.post("workerDot/editWorkerDot.do",{parkIds:parkIds.toString(),workerId:workerId},function(res){
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
								$("#workerDotModalAdd").modal("hide");
								$("#workerDotList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！"+msg);
						}
						
					});
					
				}
	        },
	        
	        pagePark:function(){
	        	debugger;
	        	var form = $("form[name=workerDotSearchForm]");
	        	var workerId = form.find("input[name=workerId]").val();
	        	$.post("workerDot/parkList.do",{workerId:workerId},function(res){	
					var html = "";
					if(res!=""){
						
						for (var i = 0; i < res.length; i++) {
							var isDot = "";
							if(res[i].isDot==1){
								isDot = "<td><input type='checkbox' value='"+res[i].parkNo+"' checked='checked'> "+res[i].parkNo+" </td>";
							}else{
								isDot = "<td><input type='checkbox' value='"+res[i].parkNo+"'>"+res[i].parkNo+"</td>";
							}
							html += "<tr>"+isDot+" <td>"+res[i].parkName+"</td> </tr>";	
						}
						
						$("#parkInfoMs").html(html);
						
					}
					
				});
	        	
	        },
	        
			pageList:function () {	
				var workerDotTpl = $("#workerDotTpl").html();
				// 预编译模板
				var template = Handlebars.compile(workerDotTpl);
				$('#workerDotList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": workerDot.appPath+'/workerDot/pageListWorkerDot.do',
						"data": function ( d ) {
							var form = $("form[name=workerDotSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"workerId":form.find("input[name=workerId]").val(),
								"parkName":$.trim(form.find("input[name=parkName]").val())
								
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
						{ "title":"场站编号","data": "parkNo" },
						{ "title":"场站名称","data": "parkName" },
						//{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#workerDottool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#workerDotTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#workerDotTools").html("");
					   $("#workerDotTools").css("float", "right");
					   $("#workerDotTools").removeClass("col-xs-6");
		       		   $("#workerDotTools").append('<button type="button" class="btn btn-default btn-sm workerDotTools-operate-add" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">编辑网点</button>');
		       			
		       			$(".workerDotTools-operate-add").on("click",function(){
		       				$("#workerDotModalAdd").modal("show");
		       				workerDot.pagePark();
		       			});	 
		       			
		       			
		       			
					},
					"drawCallback": function( settings ) {
						workerDot.operateColumEvent();
	        	    },
	        	    "order": [[ 1, "desc" ]],
					"columnDefs": [
					               
					               
					               
					               
					               ]
				});
			}
	    };
	return workerDot;
});


