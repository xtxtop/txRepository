<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		. .form-control {
			width: 45% !important;
			margin-right: 20%;
		}
		.pull-down-menu span {
			width: 20%;
		}
	}
	
	@media only screen and (max-width:768px) {
		.row .sorting_disabled {
			font-size: 1.1rem !important;
		}
	}
	
	@media only screen and (min-width:768px) and (max-width:1024px) {
		.row .sorting_disabled {
			font-size: 1.2rem !important;
		}
	}
	
	@media only screen and (min-width:1024px) and (max-width:1366px) {
		.row .sorting_disabled {
			font-size: 1.3rem !important;
		}
	}
	
	.other {
		margin: 0 !important;
		padding: 0 !important;
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
	.table-bettween-far tr td{
	height:72px;
	}
	.table#cParkLockList>tbody>tr>td{
		padding:10px;
	}
	#cParkLockList .car-brand-img{
		background-size:100%;
		background-position:center;
		background-repeat:no-repeat;
		border:solid 1px #ddd;
		width:80px;
		height:80px;
		border-radius:50%;
		margin:0 auto;
	}
	
	.table#cParkLockList>thead>tr>th:nth-child(1){
		width:20%;
	}
	.table#cParkLockList>thead>tr>th:nth-child(2){
		width:20%;
	}
	.table#cParkLockList>thead>tr>th:nth-child(3){
		width:30%;
	}
	.table#cParkLockList>thead>tr>th:nth-child(4){
		width:30%;
	}
</style>
   <div class="container-fluid">
     <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="parkLock_moreCparkList">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
         <!-- /.box-tools -->
		<div class="search-input-content">
         <form class="form-horizontal" name="cParkLockSearchForm">
       <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
				<div class="seach-input-item pull-left">
					<span>地锁编号</span>
					<input type="text" placeholder="请输入停车场地锁编号" class="form-control" id="parkLockNo" name="parkLockNo" placeholder="">
				</div>
				<div class="seach-input-item pull-left">
					<span>地锁编码</span>
					<input type="text" placeholder="请输入地锁编码" class="form-control" id="parkingLockCode" name="parkingLockCode" placeholder="">
				</div>

				<div class="seach-input-item pull-left">
					<span>楼层数</span>
					<input type="text" placeholder="请输入楼层数" class="form-control" id="pliesNumber" name="pliesNumber" placeholder="">
				</div>
				<div class="seach-input-item pull-left">
					<span>地锁名称</span>
					<input type="text" placeholder="请输入地锁名称" class="form-control" id="parkingLockName" name="parkingLockName" placeholder="">
				</div>
				<div class="seach-input-item pull-left">
					<span>停车场</span>
					<input type="text" placeholder="请输入停车场名称" class="form-control" id="parkingName" name="parkingName" placeholder="">
				</div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>地锁类型</span>
                    <select class="form-control"  name="parkingLockType" placeholder="">
                    	<option value="">全部</option>
                    	<option value="0">网关版</option>
                    	<option value="1">UDP版</option>
                    </select>
                </div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>升降状态</span>
                    <select class="form-control"  name="parkingLockStatus" placeholder="">
                    	<option value="">全部</option>
                    	<option value="0">升</option>
                    	<option value="1">降</option>
                    </select>
                </div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>地锁状态</span>
                    <select class="form-control"  name="activeCondition" placeholder="">
                    	<option value="">全部</option>
                    	<option value="0">可用</option>
                    	<option value="1">不可用</option>
                    </select>
                </div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>在线状态</span>
                    <select class="form-control"  name="onlineStatus" placeholder="">
                    	<option value="">全部</option>
                    	<option value="0">在线</option>
                    	<option value="1">离线</option>	
                    </select>
                </div>
                
         </div><!-- /.box-body -->
         <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="cParkLockSearchafter">确定</button>
					</div>
         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="cParkLockTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="cParkLockList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"cParkLock":"res/js/mlpark/cpark_lock_list"}});
		require(["cParkLock"], function (cParkLock){
			cParkLock.init();
		});  
	   }); 
    </script>
