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
	
	table.dataTable.nowrap td {
    	white-space: normal;
	}
	
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<!--<div class="pull-right moreButtonNew" id="moreCarList">
			<div class="up-triangle"></div>
		<div class="up-box">
		
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>-->
		<div class="search-input-content">
			<form class="form-horizontal" name="carDetailSearchForm">
				<!--修改 -->
				<div class="seach-input-border-box">

					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>车牌号</span>
							<input type="text" placeholder="请输入车牌号" class="form-control" id="carPlateNo" name="carPlateNo" placeholder="" value="${carPlateNo}">
						</div>
						<div class="seach-input-item pull-left">
							<span>终端序列</span>
							<input type="text" placeholder="请输入终端序列" class="form-control" id="deviceSn" name="deviceSn" placeholder="">
						</div>
						
					</div>
										<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carDetailSearchafter">确定</button>
					</div>
				</div>
				<!-- /.box-header -->
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->

		<!-- /.col -->
	</div>
	<!-- /.row -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="carDetailTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="carDetailList" class="table table-hover display nowrap" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>


<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"carDetail": "res/js/car/car_detail_list"
			}
		});
		require(["carDetail"], function(carDetail) {
			carDetail.init();
		});
	});
</script>