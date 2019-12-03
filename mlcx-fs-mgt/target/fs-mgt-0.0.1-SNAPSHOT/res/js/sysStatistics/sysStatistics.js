
define([],function() {
    var sysStatistics= {
        appPath:getPath("app"),	
        beforePath:getPath("before"),
        init: function () {
           
            
            $("#sysStatisticsCancle").click(function(){
            	$("#sysStatisticsEditModal").modal("hide");
            });
         
			
			
            $("#sysStatisticsSearch").click(function(){
            	sysStatistics.pageList();
            });
            
            sysStatistics.pageList();
            $("#sysStatisticsEditModal").on("hidden.bs.modal", function() {  
            	var form = $("form[name=sysStatisticsEditForm]");
            	form.clearForm();
            	form.resetForm();
            	form.find("input[type=hidden]").val("");
            });
           
        },
    
	        reqDeliveryDate:function(now){
				now = moment(now); 
				return now.format('YYYY-MM-DD HH:mm:ss');			
			},
        
        
 	        
 	       
        
 	       
        
        
        operateColumEvent: function(){
			$(".sysStatistics-operate-edit").each(function(){
				$(this).on("click",function(){
					$("#sysStatisticsEditModal").modal("show");
					sysStatistics.detailTwo($(this).data("id"));
				});
			});
			
			
			
			
        },
        pageList:function () {
        	var sysStatisticsBtnTpl = $("#sysStatisticsBtnTpl").html();
			//预编译模板
			var template = Handlebars.compile(sysStatisticsBtnTpl);
			var table = $('#sysStatistics').dataTable( {
				searching:false,
				"retrieve": true,
        		"ajax": {
        			"type": "POST",
        			"url": sysStatistics.appPath+"/sysStatistics/querySysStatistics.do",
        			"data": function ( d ) {  
        				var form = $("form[name=sysStatisticsSearchForm]");
        			    var createTime=form.find("input[name=createTime]").val();
        			    var createTimeStart;
        				var createTimeEnd;
        				
        				
        				if(createTime){
        					var temp=createTime.split("-");
        					createTimeStart=temp[0];
        					createTimeEnd=temp[1];
        				}
        				return $.extend( {}, d, {
        					"pageNo": (d.start/d.length)+1,
        					"pageSize":d.length,
        					"createTimeStart":createTimeStart,
        					"createTimeEnd":createTimeEnd
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
    				{ "title":"浏览次数","data": "frequency"},
    				{ "title":"访客","data": "visitor" },
    				{ "title":"ip","data": "ip" }
					
        		],
        	   "dom": "<'row'<'#sysStatisticstool.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
        	   initComplete: function () {
        		   
        		 },
				"drawCallback": function( settings ) {
					sysStatistics.operateColumEvent();
        	    },
        	    
        		"columnDefs": [
        		               ]
        	} );
			$("#myModal").on("shown.bs.modal", function () {
				//$('#myInput').focus()；
				});
			$('#sysStatisticsonOFFDate').daterangepicker();
        },
        
        
        
        
	/** 
 * 将数值四舍五入(保留2位小数)后格式化成金额形式 
 * 
 * @param num 数值(Number或者String) 
 * @return 金额格式的字符串,如'1,234,567.45' 
 * @type String 
 */  
formatCurrency :function (s,n) { 
	n = n > 0 && n <= 20 ? n : 2;  
s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
t = "";  
for (i = 0; i < l.length; i++) {  
    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
}  
return t.split("").reverse().join("") + "." + r;  } 
    };
    return  sysStatistics;
});

