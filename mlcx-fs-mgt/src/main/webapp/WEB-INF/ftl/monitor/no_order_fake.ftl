 <div id="fillmapToscreen">
    <div class="textZln">红色代表非订单</div>
   	<div id="ln-map"></div>
   	<div id="overlay">
   		
   	</div>
   	<input name="carDetailDivOrMarker" type="hidden"/>
</div><!-- /.container-fluid -->
 <div class="modal fade" id="OffWarningModalclo">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定关闭</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="offForm"> 
				      <input type="hidden"  name="warningNo" id="warningNo">
				      <input type="hidden"  name="carPlateNo" id="carPlateNo">
				      <input type="hidden"  name="isClosed" value="1">
				        <label for="inputEmail3" class=" control-label  wen"><img src="res/img/wen.png" style="width:29px;hegiht:29px;margin-top:-4px">&nbsp;&nbsp;您确定关闭该警告吗？</label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason">*备注:</label>
				         </div> 
				          <div class="form-group">
	                            
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas"   name="closedMemo"></textarea>
	                            </div>
	                      </div>
	                             
				   </form> 
				      <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-right cancels"  id="warningOffCancelclo">取消</button>
                    <input type="button" class="btn btn-default pull-right sure" id="warningOffBtnclo" value="确定" >
                </div>    
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
 <style type="text/css">
 /*列表样式*/
	#overlay{
		opacity:.9;
		position:absolute;
		top:25%;
		right:4%;
		width:20%;
		height:auto;
		max-height:500px;
		z-index:5000;
		background-color:white;
		border:1px solid gray;
		overflow:auto;
		display:none;
		text-align:center;
	}
	
	#overlay div{
		display:block;
		text-align:center;
		border-top:1px solid #000000;
		font-size:18px;
		line-height:20px;
		height:20px;
	}
	#overlay p{
		font-size:18px;
		font-weight:bold;
	}
	
 	#ln-map{
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
<script type="text/javascript" src="res/dep/LuShu/monitor_Lushu.js"></script>
<script type="text/javascript">
	$(function(){
	var docHeight = $(document).height(); //获取窗口高度  
	 // $('#overlay').height(docHeight-100);
       
  //  setTimeout(function(){$('#overlay').fadeOut('slow')}, 3000); //设置3秒后覆盖层自动淡出  
    
    
	$('body').append('<div id="overlay"></div>');  
	  require.config({paths: {"noOrder":"res/js/monitor/no_order_fake"}});
		require(["noOrder"], function (noOrder){
			noOrder.init();
		});  
	}); 
	function myrefresh(){ 
      require.config({paths: {"noOrder":"res/js/monitor/no_order_fake"}});
		require(["noOrder"], function (noOrder){
			noOrder.init();
		});
	} 
	//setTimeout('myrefresh()',30000); //指定30秒刷新一次 
</script>
