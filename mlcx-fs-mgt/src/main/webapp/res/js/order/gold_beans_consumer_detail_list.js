define([],function() {	
	var goldBeansConsumerDetails={
			appPath:getPath("app"),	
			init: function () {	
				
	            //数据列表
				goldBeansConsumerDetails.pageList();
				//查询
				$("#goldBeansConsumerDetailsSearchafter").click(function(){
					goldBeansConsumerDetails.pageList();
	            });
			},
			pageList:function () {	
				var goldBeansConsumerDetailsTpl = $("#goldBeansConsumerDetailsTpl").html();
				// 预编译模板
				var template = Handlebars.compile(goldBeansConsumerDetailsTpl);
				$('#goldBeansConsumerDetailsList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": goldBeansConsumerDetails.appPath+'/consumerDetail/pageListConsumerDetail.do',
						"data": function ( d ) {
							var form = $("form[name=goldBeansConsumerDetailsSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"orderNo":form.find("input[name=orderNo]").val(),
								"memberPhone":form.find("input[name=memberPhone]").val(),
								"consumerTimeStart":form.find("input[name=consumerTimeStart]").val()+" 00:00:00",
								"consumerTimeEnd":form.find("input[name=consumerTimeEnd]").val()+" 23:59:59",
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
						{ "title":"订单编号","data": "orderNo" },
						{ "title":"订单时间","data": "orderTime" },
						{ "title":"订单金额","data": "orderAmount" },
						{ "title":"消费金豆","data": "consumerBeansNum" },
						{ "title":"消费金额","data": "consumerBeansAmount" },
						{ "title":"会员名","data": "memberName" },	
						{ "title":"会员手机号","data": "memberPhone" },
					],
				   "dom": "<'row'<'#goldBeansConsumerDetailsTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					},
					"drawCallback": function( settings ) {
						
	        	    },
					"columnDefs": [
					               {
					            	   "targets": [2,4],
					            	   "render": function(data, type, row, meta) {
					            		   if(data){
					    	            		return "<span style='color:#2b94fd'>" + goldBeansConsumerDetails.formatCurrency(data) + "</span>";
					    	            	}else{
					    	            		return "0.0";
					    	            	}
					            	   }
					               },
					               {
					            	   "targets": [1],
					            	   "render": function(data, type, row, meta) {
					            		   if(data){
					    	            		return "<span style='color:#2b94fd'>" +new Date(data).Format("yyyy-MM-dd HH:mm:ss") + "</span>";
					    	            	}else{
					    	            		return "0.0";
					    	            	}
					            	   }
					               },
					]
				});
			},
			formatCurrency :function (s,n) { 
	        	n = n > 0 && n <= 20 ? n : 2;  
		        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
		        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
		        t = "";  
		        for (i = 0; i < l.length; i++) {  
		            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		        }  
		        return t.split("").reverse().join("") + "." + r;  
		    } 
	    };
	return goldBeansConsumerDetails;
});
/**
 * 日期格式化  yyyy-MM-dd hh:mm:ss
 * @param   {[type]}   date [description]
 * @return  {[type]}        [description]
 */
Date.prototype.Format = function(fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

