<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
          <h4 class="box-title">查询</h4>
       <div class="box box-default">
        <div class="with-border">
         <div class="box-tools pull-right">
          <#--<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>-->
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="carMonitorSerachForm">
        <input type="hidden" id="nowTime" value="${nowTime?string('yyyy-MM-dd HH:mm:ss')}"/>
        <div class="box-body">
         <div class="row ">
         <input name="flag" type="hidden" value="${flag}">
         <div class="col-md-2 pull-down-menu">
                <div class="frombg">
                    <span style="width: 50%">车牌号</span><input class="form-control" name="carPlateNo" placeholder="" value="${carPlateNo}">
                </div>
         </div>
         <div class="col-md-2">
                <div class="frombg">
                    <span style="width: 50%">状态</span><select class="form-control" name="userageStatus">
                                          <option value="">全部</option>
                                          <option value="2">订单中</option>
                                          <option value="3">调度中</option>
                                        </select>
                </div>
			</div>
			<div class="col-md-2">
			        <div class="frombg">
                        <span style="width: 50%">城市</span><select class="form-control" name="cityId">
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
          </div><!-- /.box-body -->
          <!--修改-->

          			<div class="col-md-12" style='float:right'>
                              <div class="box-footer">
                                      <button type="button" class="btn btn-default pull-right btn-flat" id="carMonitorSearch" style="background:#2b94fd;margin-right:-2px !important">确定</button>
                                      <button type="reset" class="btn btn-default pull-right btn-flat" style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
                               </div>
                      </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row" style="margin:15px 0;">
     	 <div class="col-md-3">红色代表订单，蓝色代表调度,绿色代表空闲</div>
   		 <div id="car-map" class="col-md-2"></div>
   	</div>
    </div><!-- /.container-fluid -->
 <style type="text/css">
 	#car-map {
			width:80%;
			height:550px;
			overflow: hidden;
			margin:16px;
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
<script type="text/javascript" src="res/dep/LuShu/LuShu_min.js"></script>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carMonitor":"res/js/monitor/car_monitor"}});
		require(["carMonitor"], function (carMonitor){
			carMonitor.init();
		});  
	});  
</script>
