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
	.table#gateMachineList>tbody>tr>td{
		padding:10px;
	}
	#gateMachineList .car-brand-img{
		background-size:100%;
		background-position:center;
		background-repeat:no-repeat;
		border:solid 1px #ddd;
		width:80px;
		height:80px;
		border-radius:50%;
		margin:0 auto;
	}
	
	.table#gateMachineList>thead>tr>th:nth-child(1){
		width:20%;
	}
	.table#gateMachineList>thead>tr>th:nth-child(2){
		width:20%;
	}
	.table#gateMachineList>thead>tr>th:nth-child(3){
		width:30%;
	}
	.table#gateMachineList>thead>tr>th:nth-child(4){
		width:30%;
	}
</style>
   <div class="container-fluid">
     <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="moreCparkList">
		<!-- <div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div> -->
		</div>
         <!-- /.box-tools -->
		<div class="search-input-content">
         <form class="form-horizontal" name="gateMachineSearchForm">
       <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
				<div class="seach-input-item pull-left">
					<span>闸机名称</span>
					<input type="text" placeholder="请输入闸机名称" class="form-control" id="gateName" name="gateName">
				</div>
				<div class="seach-input-item pull-left">
					<span>停车场</span>
					<select name="parkingNo" class="form-control">
					   <option value="">--全部--</option>
					   <#list park as p>
					       <#if p.parkingStatus==0&&p.parkingType==0>
					           <option value="${p.parkingNo }">${p.parkingName }</option>
					       </#if>
					   </#list>
					</select>
				</div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>升降状态</span>
                    <select class="form-control"  name="gateStatus" placeholder="">
                    	<option value="">全部</option>
                    	<option value="0">升</option>
                    	<option value="1">降</option>
                    </select>
                </div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>闸机状态</span>
                    <select class="form-control"  name="activeCondition" >
                    	<option value="">全部</option>
                    	<option value="0">启用</option>
                    	<option value="1">停用</option>
                    </select>
                </div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>在线状态</span>
                    <select class="form-control"  name="onlineStatus" >
                    	<option value="">全部</option>
                    	<option value="0">在线</option>
                    	<option value="1">离线</option>	
                    </select>
                </div>
                
         </div><!-- /.box-body -->
         <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="gateMachineSearchafter">确定</button>
					</div>
         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="gateMachineTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="gateMachineList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"gateMachine":"res/js/mlpark/gateMachine_list"}});
		require(["gateMachine"], function (gateMachine){
			gateMachine.init();
		});  
	   }); 
    </script>
