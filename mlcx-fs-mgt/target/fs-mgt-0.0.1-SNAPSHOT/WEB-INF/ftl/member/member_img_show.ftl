<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- [if lt IE 9] -->
<!-- jQuery 2.1.4 -->
<!-- <script type="text/javascript" src="res/js/jQuery-2.1.4.min.js"></script> -->
<!-- [endif] -->
<script type="text/javascript" src="${basePath!""}/res/js/lib/jquery-1.11.3.js"></script>
</head>
<body>
<img id ="target" src="${imgSrc}" alt="" onclick="rorateImg()">
<script type="text/javascript">
   	var current = 0;
    function rorateImg(){
        current = (current+90)%360;
        $("#target").attr("style","transform:rotate("+current+"deg);-ms-transform:rotate("+current+"deg);-moz-transform:rotate("+current+"deg);-webkit-transform:rotate("+current+"deg);-o-transform:rotate("+current+"deg);");
    }
</script>
</body>
</html>
