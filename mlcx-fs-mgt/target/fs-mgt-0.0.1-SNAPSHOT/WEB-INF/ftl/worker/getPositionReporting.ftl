<meta charset="utf-8">
<style>
.frombg .form-control{
	width:50% !important;
}
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.frombg .form-control{
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
white-space: nowrap;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
white-space: nowrap;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}
table{
border:1px solid rgba(127,127,127,0.05);
}


.mapInfo {
		width: 340px;
		/*height: 250px;*/
		background: #fff;
		border: 1px solid #dedede;
		border-radius: 10px;
		box-shadow: 0px 3px 4px 0px #eee;
		z-index: 1000;
	}
	
	.mapInfo .devicePerson {
		background: #222A36;
		color: #FFFFFF;
		overflow: hidden;
		box-sizing: border-box;
		padding: 16px 28px;
		font-size: 18px;
		font-weight: normal;
		margin: 0;
		border-top-left-radius: 10px;
		border-top-right-radius: 10px;
	}	
	
	.mapInfo .devicePerson i{
		float: right;
		font-size:22px;
		cursor:pointer;
	}
	
	.m2map_t2 {
		color: #999999;
		font-size: 16px;
		text-indent: 1.5rem;
		padding: 10px 0;
	}
	
	.info {
		background: #F1F1F1;
		text-align: center;
		color: #313131;
		padding: 10px 0;
	}
	
	.task-info {
		line-height:40px;
		text-align: center;
		color: #2A64FB;
		cursor: pointer;
		font-size:14px;
	}
	.infro-content ul{
		margin: 0;
		padding: 0;
	}
	.infro-content ul li{
		list-style: none;
	}
	.arrow-down{
		width: 16px;
		height: 16px;
		transform: rotate(-45deg);
		background: #fff;
		position: absolute;
		bottom: -9px;
		left: 156px;
		border: 1px solid #dedede;
		border-right: none;
		border-top: none;
	}
</style>
<div id="reporting-map"></div>

<div class="modal fade" id="showWorkerModel">
	
	<form name="queryWorkerSearchForm">
		<input type="hidden" name="workerId" id="workerNo" value="">
	</form>
	<div class="modal-body small-model-content" style="width:750px;">
		<span class="close-model-btn" id="closeModelBtn">&times;</span>
		<div class="table-scroll-wrapper">
		<div class="with-border">
					<div class="title-new-details">任务详情</div>
				</div>
			<div class="table-data-wrapper">
				<script id="queryWorkerTpl" type="text/x-handlebars-template">
					{{#each func}}
					<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
				</script>
				<div>
					<table id="queryWorkerList" class="table table-hover table-bettween-far" width="100%" border="1"></table>
				</div>
					<div class="carRedPacketAddParkTool-bullet" id="customerFeedbackWorkerTools">
						</div>
			</div>
		</div>
	</div>
</div>


 <style type="text/css">
 	.small-model-content{
 		width:800px;
 		height:400px;
 		position:absolute;
 		top:50%;
 		left:50%;
 		margin-left:-400px;
 		margin-top:-200px;
 		background:#fff;
 		border-radius:4px;
 		overflow:initial;
 	}
 	.close-model-btn{
 		font-size:30px;
 		color:#fff;
 		font-weight:700;
 		position:absolute;
 		top: -26px;
    	right: -20px;
    	cursor: pointer;
 	}
 	.table-scroll-wrapper{
 		position:absolute;
 		height:100%;
 		width:100%;
 		overflow-y:auto;
 		overflow-x:hidden;
 		top:0;
 		right:0;
 		box-sizing:border-box;
 		padding:10px;
 	}
 	#reporting-map {
			width:100%;
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
	  require.config({paths: {"getPositionReporting":"res/js/worker/getPositionReporting"}});
		require(["getPositionReporting"], function (getPositionReporting){
			getPositionReporting.init();
		});  
	});  
</script>
