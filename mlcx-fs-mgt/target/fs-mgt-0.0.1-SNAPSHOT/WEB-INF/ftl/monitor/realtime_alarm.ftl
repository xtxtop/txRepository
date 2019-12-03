 <div id="fillmapToscreen">
    <div class="textZln">蓝色代表闲置超过24小时,绿色代表闲置超过48小时，黄色代表闲置超过72小时</div>
   	<div id="realTime-map"></div>
   	<input name="carDetailDivRealTimeAlarm" type="hidden"/>
</div><!-- /.container-fluid -->

 <style type="text/css">
 	#realTime-map{
			width:100%; 
			height:550px;
			overflow: hidden;
		}
	.carInfro{
		width:100%;
		margin:0 auto;
		list-style:none;
		overflow:hidden;
		padding-left:5px;
	}
	
	.carInfro li{
		margin-right:10px;
		height:25px;
		line-height:25px;
		font-size:14px;
	}
	.carInfroFull{
		width:100%;
		margin:0 auto;
		list-style:none;
		overflow:hidden;
		padding-left:10px;
	}
	.carInfroFull h3{
		margin-right:10px;
		height:25px;
		line-height:35px;
		font-size:30px;
	}
	.carInfroFull li{
		margin-right:10px;
		height:25px;
		line-height:30px;
		font-size:25px;
		margin-bottom: 40px;
	}
	.buttonFull{
		font-size:18px;
		height:"50px";
		width:"100px";
	}
	/*全屏样式*/
	.fillScree{
		position:absolute!important;
		top:0;
		left:0;
		z-index:10000!important;
	}
</style>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"realtimeAlarm":"res/js/monitor/realtime_alarm"}});
		require(["realtimeAlarm"], function (realtimeAlarm){
			realtimeAlarm.init();
		});  
	}); 
	function myrefresh(){ 
      require.config({paths: {"realtimeAlarm":"res/js/monitor/realtime_alarm"}});
		require(["realtimeAlarm"], function (realtimeAlarm){
			realtimeAlarm.init();
		});
	} 
	//setTimeout('myrefresh()',30000); //指定30秒刷新一次 
</script>
