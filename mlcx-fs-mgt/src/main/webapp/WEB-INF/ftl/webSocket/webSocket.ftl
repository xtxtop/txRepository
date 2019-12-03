<meta charset="utf-8">

  <div class="container-fluid">
   
   </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    	$(function(){
	  require.config({paths: {"webSocket":"res/js/webSocket/webSocket.js"}});
		require(["webSocket"], function (webSocket){
			webSocket.init();
		});  
	}); 
    </script>
   
