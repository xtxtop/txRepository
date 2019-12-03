$(document).ready(function(){
	function imagePreview(file,previewDom) {
		if (file.files && file.files[0]) {
			var reader = new FileReader();
			reader.onload = function(evt) {
				previewDom.html('<img src="' + evt.target.result + '" style="width:50px;heigth:50px"/>');
			}
			reader.readAsDataURL(file.files[0]);
		} else {
			previewDom.html('<div class="img" style="width:50px;heigth:50px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\''
					+ file.value + '\'"></div>');
		}
	}
	$.ajaxSetup({ 
	    contentType : "application/x-www-form-urlencoded;charset=utf-8",  
	    complete : function(xhr, textStatus) {  
	        //session timeout  
	    	if(xhr.getResponseHeader("session_is_timeout") == "1") {
	    		alert('您未登录或会话已超时，请重新登录。');
	    		window.location = getPath("app") + "/login.do";//"../console/toSessionTimeout.do";//去到提示登陆页
	    		return;
	    	}
	    	 
			 if(xhr.getResponseHeader("session_is_jurisdiction") == "1") {
	    		alert('您没有操作权限！');
	//    		window.location = getPath("app") + "/login.do";//"../console/toSessionTimeout.do";//去到提示登陆页
	    		return;
	    	}
			 
			 
	    }  
	});
})











