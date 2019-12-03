define([],function() {	
	var deviceUploadpkgLogDetail={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				deviceUploadpkgLogDetail.pageList(1);
				//查询
				$("#deviceUploadpkgLogViewSearchForm").click(function(){
					deviceUploadpkgLogDetail.pageList(1);
	            });
				//查询
				$("#reportLogSearchForm").click(function(){
					deviceUploadpkgLogDetail.reportLog();
	            });
				//查询下一页
				$("#nextBtn").click(function(){
	            	var form = $("form[name=deviceUploadpkgLogDetailSearchForm]");
					var pageNo = form.find("input[name='pageNo']").val();
					pageNo = parseInt(pageNo)+1;
	            	deviceUploadpkgLogDetail.pageList(pageNo);  
	            });
				//导出
				$("#reportBtn").click(function(){
   					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
   						if(result){
   							deviceUploadpkgLogDetail.dataToTxt();
   						}
   					});	
	            });
//				deviceUploadpkgLogDetail.scrollF();
			},
			pageList:function (pageNo) {	
				var form = $("form[name=deviceUploadpkgLogDetailSearchForm]");
				var carPlateNo = form.find("input[name='carPlateNo']").val();
				var deviceSn = form.find("input[name='deviceSn']").val();
				var createTimeStart = form.find("input[name='createTimeStart']").val();
				var createTimeEnd = form.find("input[name='createTimeEnd']").val();
				
				var pageSize = form.find("input[name='pageSize']").val();
				$.ajax({
		             url: deviceUploadpkgLogDetail.appPath+"/deviceUploadpkgLog/getDeviceUploadpkgLogDetailList.do",
		             data: {deviceSn:deviceSn,createTimeStart:createTimeStart,createTimeEnd:createTimeEnd,pageNo:pageNo,pageSize:pageSize},
		             success: function(res){
		            	 var deviceUploagpkgLogList = res.data;
		            	 var pageNo = res.pageNo;
		            	 form.find("input[name='pageNo']").val(pageNo);
		            	 if(deviceUploagpkgLogList.length>0){
		            		 var tr = "";
		            		 var text = "";
		            		 for(var i=0;i<deviceUploagpkgLogList.length;i++){
		            			 var deviceLog = deviceUploagpkgLogList[i];
		            			 var flag = "odd";
		            			 if(i%2==0){
		            				 flag = "even";
		            			 }
		            			 var cmdType = deviceLog.cmdType;
		            			 if(cmdType=="00"){
		            				 cmdType="同步时间请求";
						         }else if(cmdType=="02"){
						        	 cmdType="车辆启动提醒";
						         }else if(cmdType=="03"){
						        	 cmdType="车辆熄火提醒";
						         }else if(cmdType=="04"){
						        	 cmdType="终端准备进入休眠提醒";
						         }else if(cmdType=="13"){
						        	 cmdType="节能 模式消息包";
						         }else if(cmdType=="14"){
						        	 cmdType="待机 模式消息包";
						         }else if(cmdType=="17"){
						        	 cmdType="休眠心跳";
						         }else if(cmdType=="24"){
						        	 cmdType="设备实时状态数据包";
						         }else if(cmdType=="36"){
						        	 cmdType="上报操作执行返回结果";
						         }else if(cmdType=="33"){
						        	 cmdType="上报车辆控制指令的执行结果";
							     }else if(cmdType=="3E"){
							    	 cmdType="开关动力控制";
							     }else if(cmdType=="B1"){
							    	 cmdType="远程升级指令响应";
							     }
		            			 var uploadTime = moment(deviceLog.createTime); 
		            			 tr += "<tr role='row' class="+flag+"><td>"+deviceLog.carPlateNo+"</td><td>"+deviceLog.deviceSn+"</td><td>"+cmdType+"</td><td>"+deviceLog.logContent+"</td><td>"+deviceLog.chiniseContent+"</td><td>"+uploadTime.format('YYYY-MM-DD HH:mm:ss')+"</td></tr>";
		            			 text += uploadTime.format('YYYY-MM-DD HH:mm:ss')+" ";
		            			 text += deviceLog.logContent+"\r\n";
		            		 }
		            		 if(pageNo==1){
		            			 $("#deviceUploadpkgLogViewTable tbody").html(tr);
			            		 $("#deviceLogTxtData").text(text);
		            		 }else{
		            			 $("#deviceUploadpkgLogViewTable tbody").append(tr);
		            			 text = $("#deviceLogTxtData").text() + text;
			            		 $("#deviceLogTxtData").text(text);
		            		 }
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "暂无数据"); 
		                 }
		             }
				});	
			},
			reportLog:function () {	
				var form = $("form[name=deviceUploadpkgLogDetailSearchForm]");
				var deviceSn = form.find("input[name=deviceSn]").val();
				if(deviceSn == null || deviceSn == ""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入设备号！");
					return;
				}
				var createTimeStart = form.find("input[name=createTimeStart]").val();
				if(createTimeStart == null || createTimeStart == ""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入上报日期！");
					return;
				}
				window.location.href = deviceUploadpkgLogDetail.appPath+"/deviceUploadpkgLog/exportOldLogData.do?deviceSn="+deviceSn+"&date="+createTimeStart;
			},
			scrollF:function(){
				var pos = 0;  
			    var LIST_ITEM_SIZE = 100;  
			    //滚动条距底部的距离  
			    var BOTTOM_OFFSET = 0;  
			    $(document).ready(function () {  
			        $(window).scroll(function () {  
			            var $currentWindow = $(window);  
			            //当前窗口的高度  
			            var windowHeight = $currentWindow.height();  
			            console.log("current widow height is " + windowHeight);  
			            //当前滚动条从上往下滚动的距离  
			            var scrollTop = $currentWindow.scrollTop();  
			            console.log("current scrollOffset is " + scrollTop);  
			            //当前文档的高度  
			            var docHeight = $(document).height();  
			            console.log("current docHeight is " + docHeight);  
			  
			            //当 滚动条距底部的距离 + 滚动条滚动的距离 >= 文档的高度 - 窗口的高度  
			            //换句话说：（滚动条滚动的距离 + 窗口的高度 = 文档的高度）  这个是基本的公式  
			            //alert(BOTTOM_OFFSET+","+scrollTop+","+docHeight +","+ windowHeight);
			            if ((BOTTOM_OFFSET + scrollTop) >= docHeight - windowHeight) {  
			            	var form = $("form[name=deviceUploadpkgLogDetailSearchForm]");
							var pageNo = form.find("input[name='pageNo']").val();
							pageNo = parseInt(pageNo)+1;
			            	deviceUploadpkgLogDetail.pageList(pageNo);  
			            }  
			        });  
			    });  
			},
			dataToTxt:function() {
				var form = $("form[name=deviceUploadpkgLogDetailSearchForm]");
				var carPlateNo = form.find("input[name='carPlateNo']").val();
				var deviceSn = form.find("input[name='deviceSn']").val();
				var createTimeStart = form.find("input[name='createTimeStart']").val();
				var createTimeEnd = form.find("input[name='createTimeEnd']").val();
				var pageSize = form.find("input[name='pageSize']").val();
				var pageNo = form.find("input[name='pageNo']").val();
				window.location.href = deviceUploadpkgLogDetail.appPath+"/deviceUploadpkgLog/exportDeviceLogData.do?carPlateNo="+carPlateNo+"&deviceSn="+deviceSn+"&createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd+"&pageSize="+pageSize+"&pageNo="+pageNo;
			}		
	    };
	return deviceUploadpkgLogDetail;
});


(function ($) {  
    var pos = 0;  
    var LIST_ITEM_SIZE = 100;  
    //滚动条距底部的距离  
    var BOTTOM_OFFSET = 0;  
    createListItems();  
    $(document).ready(function () {  
        $(window).scroll(function () {  
            var $currentWindow = $(window);  
            //当前窗口的高度  
            var windowHeight = $currentWindow.height();  
            console.log("current widow height is " + windowHeight);  
            //当前滚动条从上往下滚动的距离  
            var scrollTop = $currentWindow.scrollTop();  
            console.log("current scrollOffset is " + scrollTop);  
            //当前文档的高度  
            var docHeight = $(document).height();  
            console.log("current docHeight is " + docHeight);  
  
            //当 滚动条距底部的距离 + 滚动条滚动的距离 >= 文档的高度 - 窗口的高度  
            //换句话说：（滚动条滚动的距离 + 窗口的高度 = 文档的高度）  这个是基本的公式  
            if ((BOTTOM_OFFSET + scrollTop) >= docHeight - windowHeight) {  
            	deviceUploadpkgLogDetail();  
            }  
        });  
    });  
  
    function createListItems() {  
        var mydocument = document;  
        var mylist = mydocument.getElementById("my_list");  
        var docFragments = mydocument.createDocumentFragment();  
        for (var i = pos; i < pos + LIST_ITEM_SIZE; ++i) {  
            var liItem = mydocument.createElement("li");  
            liItem.innerHTML = "This is item " + i;  
            docFragments.appendChild(liItem);  
        }  
        pos += LIST_ITEM_SIZE;  
        mylist.appendChild(docFragments);  
    }  
});  


