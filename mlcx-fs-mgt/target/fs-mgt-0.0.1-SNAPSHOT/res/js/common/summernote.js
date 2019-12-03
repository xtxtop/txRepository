/**
 * create 2016/4/25 
 */

$.extend($.summernote.options, {
		 lang: 'zh-CN',
		 toolbar: [
		           ['style', ['style']],
		           ['font', ['bold', 'underline', 'clear']],
		           ['fontname', ['fontname']],
		           ['color', ['color']],
		           ['para', ['ul', 'ol', 'paragraph']],
		           ['table', ['table']],
		           ['insert', ['link', 'picture', 'video']],
		           ['view', ['fullscreen', 'codeview', 'help']]
		         ],
         callbacks: {
             onInit: null,
             onFocus: null,
             onBlur: null,
             onEnter: null,
             onKeyup: null,
             onKeydown: null,
             onSubmit: null,
             onImageUpload: function(files, editor, welEditable) {
                 var $editor = $(this);
                 var data = new FormData();
                 for(x in files){
                     data.append("qqfile", files[x]);
                 }
 	        	 var relativePath="item";
	        	 var resImgPath=getPath("resImg");
	        	 var imgPath=getPath("img");
	        	 var storePath=resImgPath+"/"+relativePath;	                  
                 data.append("storePath",storePath);
                 data.append("resPath",relativePath);                
                 $.ajax({
                   url: getPath("app")+"/upload/uploadFile.do",
                   method: "POST",
                   data: data,
                   processData: false,
                   contentType: false,
                   success: function(result) {
                	   if(result && result.data.length){
                		   var filePaths=result.data;
                		   for(x in filePaths){
                               var imgURL = filePaths[x];
                               $editor.summernote("insertImage", imgPath+"/"+imgURL);              			   
                		   }
                	   }

                   }
                 });
             },
             onImageUploadError: null
           }         
});




