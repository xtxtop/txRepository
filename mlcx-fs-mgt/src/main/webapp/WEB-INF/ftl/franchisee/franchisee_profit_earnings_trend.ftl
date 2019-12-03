<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.seach-input-item pull-left .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}

.other{
margin:0 !important;
padding:0 !important;
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
<div class="container-fluid">
     <div class="search-input-wrapper">
      <div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
       <div class="search-input-content">
           <form class="form-horizontal" name="earningsSerachForm">
        <div class="seach-input-border-box">
        	<div class="seacher-button-content seacher-button-content-change">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="earningsSearch">确定</button>
					</div>
         <!-- /.box-tools -->
            <div class="seach-input-details seach-input-details-change close-content clearfix">
                    <div class="seach-input-item seach-input-item-change pull-left">
                        <span>加盟商</span>
                        <input class="form-control" name="franchiseeName" placeholder="请输入加盟商">
                    </div>
                    <div class="seach-input-item seach-input-item-change pull-left">
                        <span>时间粒度</span>
                        <select class="form-control" name="dataType">
                        <option value="0">按月</option>
                        <option value="1">按季度</option>
                    </select>
                </div>
 				
                </div>
                
            </div>
  
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     </div>
	  <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="earningsTrend" style="width:100%;height:500px;background:#fff;"></div>
    <script type="text/javascript">
      
    </script>
    <script src="res/js/echarts/echarts-all.js"></script>
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"earningsTrendChart":"res/js/franchisee/franchisee_profit_earnings_trend"}});
		require(["earningsTrendChart"], function (earningsTrendChart){
			earningsTrendChart.init();
		});  
	});    
</script>
