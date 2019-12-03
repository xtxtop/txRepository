
define([], function() {
	var upload = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		resImgPath:getPath("resImg"),
		init : function(config) {
			//storePath为业务路径，member_icon （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）item_photo  （商品图片）
			if(config.storePath=='member_icon'||config.storePath=='member_doc'||config.storePath=='park_photo'||config.storePath=='car_photo'||config.storePath=='car_doc'||config.storePath=='advert_photo'||config.storePath=='couponPlan_photo'||config.storePath=='item_photo'||config.storePath=='orderShare_photo'){
				$("#"+config.uploadId).empty();
				upload.uploadFile(config);
			}else{
				var form = $("form[name='"+config.formName+"']");
				form.find("span[name='"+config.spanName+"']").append("<font color='red' class='formtips onError emaill'>参数storePath不符合规范！</font>");
			}
		},
		uploadFile:function(config){
        	var resImgPath=upload.resImgPath;
        	var imgPath=upload.imgPath;
        	var storePath=resImgPath+"/"+config.storePath;
        	$("#appUpload").load("res/tpl/uploadFile.html",function(){
                var manualUploader = new qq.FineUploader({
                    element: document.getElementById(config.uploadId),
                    template: "qq-template-manual-trigger",//"brandUploadFile",
                    request: {
                        endpoint: upload.appPath+"/upload/uploadFileNew.do",
                        method: "post",
                        params:{
                        	storePath:storePath,
                        	resPath:config.storePath
                        }
                    },
                    validation: {
                    	//itemLimit:config.itemLimit,
                        allowedExtensions: config.allowedExtensions,//["jpeg", "jpg", "gif", "png"],"xls","doc","xlsx","docx","pdf',"txt"],
                        sizeLimit: config.sizeLimit, // 50 kB = 50 * 1024 bytes16.   
                        minSizeLimit:config.minSizeLimit
                    },
                    thumbnails: {
                        placeholders: {
                            waitingPath: "res/dep/fine-uploader/placeholders/waiting-generic.png",
                            notAvailablePath: "res/dep/fine-uploader/placeholders/not_available-generic.png"
                        }
                    },
                    autoUpload: false,
                    multiple: false,
                    callbacks: {
                        onUpload: function (id, fileName) {
                        },
                        onSubmitted: function (id, fileName) {
                         	var li=$("#"+config.uploadId+" .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
                        	upload.picsBindEvent(li,config);
                        	li.find(".qq-upload-remove").hide();
                        },
                        onComplete: function(id, fileName, responseJSON) {
                        	var form = $("form[name='"+config.formName+"']");
                        	form.find("input[name='"+config.inputName+"']").val(responseJSON.data[0]);                        	
                        	var li=$("#"+config.uploadId+" .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
                        	li.attr("data-filepath",responseJSON.data[0]);
                        	if(responseJSON.success){
                        		li.find(".qq-upload-remove").show();                  		
                        	}
                        },
                        onProgress: function(id,  fileName,  loaded,  total) {
                        },
                        onCancel: function(id,  fileName) {                       	
                        }, 
                    }
                });
                $("#"+config.uploadId+" .trigger-upload").on("click", function() {
                    manualUploader.uploadStoredFiles();
                }); 
            });	        	
        },
        deleteFile:function(filePaths,config,fn){
        	var resPath="";
    		var regexp = /res\/img/g;	    		
			if(filePaths.match(regexp)){
				resPath="";
			}else{
				resPath=upload.resImgPath
			}
        	$.ajax({
        		url: upload.appPath+"/upload/deleteFile.do?filePaths="+filePaths,
        		data:{resPath:resPath},
        		success: function(data){
        			if(data.code=1){
        				var form = $("form[name='"+config.formName+"']");
                    	form.find("input[name='"+config.inputName+"']").val(""); 
        				if(fn){
        					fn();
        				}
        			}
        		}
        	});        	
        },
        picsBindEvent:function(obj,config){
        	obj.find(".qq-upload-up").on("click",function(){
        		if(obj.prev().length){
            		obj.insertBefore(obj.prev());
        			//obj.find(".qq-upload-up").show();
        		}/*else{
        			obj.find(".qq-upload-up").hide();
        		}*/
        	});
        	obj.find(".qq-upload-down").on("click",function(){
        		if(obj.next().length){
            		obj.insertAfter(obj.next());
            		//obj.find(".qq-upload-down").show();
        		}/*else{
        			obj.find(".qq-upload-down").hide();
        		}*/
        	});	
        	obj.find(".qq-upload-remove").on("click",function(){
        		var filePath=obj.data("filepath");
        		var temp=$(this);
    			upload.deleteFile(filePath,config,function(){
    				obj.remove();
    			});
        	});
        },
        getUploadFilePath:function(config){
        	var filePath=[];	        	
        	$("#"+config.uploadId+" .qq-upload-success").each(function(){
        		filePath.push($(this).data("filepath"));                		
        	});
        	return filePath;
        },

        picsLayout:function(url){
        	var uploadTpl = $("#upload-item-template").html();
        	var to=$("#brandFineUploader .qq-uploader .qq-upload-list");
			var li=$(uploadTpl).appendTo(to);
			var regexp = /res\/img/g;
			var imgsrc="";
			if(url.match(regexp)){
				imgsrc=upload.appPath+"/"+url;
			}else{
				imgsrc=upload.imgPath+"/"+url;
			}
			li.attr("data-filepath",url).find("img").attr("src",imgsrc);//.addClass("qq-file-id-"+x).attr("qq-file-id",x);
			var length=url.split("/").length;
			li.find(".qq-upload-file").text(url.split("/")[length-1]);
			upload.picsBindEvent(li);
        },
	}
	return upload;
})
