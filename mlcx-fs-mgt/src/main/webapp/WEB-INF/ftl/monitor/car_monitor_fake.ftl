 <meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12">
       <div class="box box-default">
        <div class="box-header with-border">
         <h3 class="box-title">查询</h3>
         <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="carMonitorSerachForm">
        <input type="hidden" id="nowTime" value="${nowTime?string('yyyy-MM-dd HH:mm:ss')}"/>
        <div class="box-body">
         <div class="row col-md-12">
         <input name="flag" type="hidden" value="${flag}">
         <div class="col-md-4">
         <div class="frombg"><span class="col-md-4">车牌号</span><input class="form-control col-md-7" name="carPlateNo" placeholder="" value="${carPlateNo}"></div>
         </div>
         <div class="col-md-4">
         <div class="frombg">
         <span class="col-md-4">状态</span><select class="form-control col-md-7" name="userageStatus">
	                                  <option value="">全部</option>
	                                  <option value="2">订单中</option>
	                                  <option value="3">调度中</option>
									</select> 
			</div>
         </div>
		<div class="col-md-4">
			<div class="frombg"><span class="col-md-4">城市</span><select class="form-control col-md-7" name="cityId">
									  <option value="">全部</option>
	                                  <#if cities?exists>
							    		<#list cities as city>
							    			<option value="${city.dataDictItemId}">${city.itemValue}</option>
							    		</#list>
							    	</#if>
									</select> 
			</div>
		</div>
			</div>
			  <div class='col-md-12' style='float:right'>

         <div class="box-footer">
	              <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="carMonitorSearch" style="background:#2b94fd;margin-right:-2px !important">确定</button>
        	       <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
         </div>
          </div>
          </div><!-- /.box-body -->
          <!--修改-->
        
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
     	 <div class="col-md-3">红色代表订单，蓝色代表调度,绿色代表空闲</div>
   		 <div id="l-map" class="col-md-2"></div>
   	</div>
    </div><!-- /.container-fluid -->
 <style type="text/css">
 	#l-map {
			width:100%; 
			height:750px;
			overflow: hidden;
		}
	.carInfro{
		width:97%;
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
<!--<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>-->
<script type="text/javascript" src="res/dep/LuShu/LuShu_min.js"></script>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carMonitor":"res/js/monitor/car_monitor_fake"}});
		require(["carMonitor"], function (carMonitor){
			carMonitor.init();
		});  
	});  
</script>
  
