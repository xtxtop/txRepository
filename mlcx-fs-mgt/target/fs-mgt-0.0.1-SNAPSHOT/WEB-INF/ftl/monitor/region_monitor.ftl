 <div id="fillmapToscreen">
   	<div id="ln-map"></div>
   	
   	<div id="info"></div>
   	
   	<div id="overlay">
   		
   	</div>
   	<input name="regionName" type="hidden"/>
</div>
 <style type="text/css">
	body{
			font-size: 10px;
	}
	
 	#ln-map{
			width:calc(100% - 180px);
			height:550px;
			float: left;
		}
	
 	#info{
			width:180px;
			height:550px;
			float: right;
		}
	
</style>
<script type="text/javascript">
	$(function(){
	var docHeight = $(document).height(); //获取窗口高度  
    
    
	  require.config({paths: {"regionMonitor":"res/js/monitor/region_monitor"}});
		require(["regionMonitor"], function (regionMonitor){
			regionMonitor.init();
		});  
	}); 
	function myrefresh(){ 
      require.config({paths: {"regionMonitor":"res/js/monitor/region_monitor"}});
		require(["regionMonitor"], function (regionMonitor){
			regionMonitor.init();
		});
	} 
</script>
