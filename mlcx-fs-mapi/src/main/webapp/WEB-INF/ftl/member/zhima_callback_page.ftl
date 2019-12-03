<html>  
<head>           
</head>  
<body>     
   <!--  <button id="iosbuttonClick" onclick="iosbuttonClick()"> test-ios</button>  
    <button id="androidbuttonClick" onclick="androidbuttonClick()"> test-Android</button> 
   -->  <script>
    	var u = navigator.userAgent, app = navigator.appVersion;
    	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    	if(isAndroid){
        	jsCallNative.callAndroid();
    	}else if(isiOS){
    		jsCallNative(); 
    	}
    </script>
</body>  

</html>  