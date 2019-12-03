<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30% !important;
		}
		.seach-input-item pull-left .form-control {
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
			white-space: nowrap !important;
		}
	}
	
	@media only screen and (min-width:768px) and (max-width:1024px) {
		.row .sorting_disabled {
			font-size: 1.2rem !important;
			white-space: nowrap !important;
		}
	}
	
	@media only screen and (min-width:1024px) and (max-width:1366px) {
		.row .sorting_disabled {
			font-size: 1.3rem !important;
		}
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
			<!--<span class="pull-right moreButton" id="moreWokerList">更多</span>-->
		</div>
		<div class="search-input-content">
			<form name="workerSerachForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>工号</span>
							<input class="form-control" name="empNo" placeholder="请输入工号">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>姓名</span>
							<input class="form-control" name="workerName" placeholder="请输入姓名">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>手机</span>
							<input class="form-control" name="mobilePhone" placeholder="请输入手机">
						</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="workerSearch">确定</button>
					</div>
				</div>
				<!-- /.box-body -->
			</form>
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="workerBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}} btn-sm btnDefault" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="workerList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.container-fluid -->

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"worker": "res/js/worker/worker"
			}
		});
		require(["worker"], function(worker) {
			worker.init();
		});
	});
</script>