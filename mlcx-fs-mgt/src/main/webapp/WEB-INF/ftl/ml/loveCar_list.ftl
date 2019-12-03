<meta charset="utf-8">
<style>
	.seach-input-item pull-left .form-control {
		width: 50% !important;
	}
	
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
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
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="moreAdvertMengLongSearchafter">
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="loveCarMengLongSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
							<div class="seach-input-item pull-left">
								<span>会员编号</span>
								<input type="text" class="form-control" name="memberNo" placeholder="请输入会员编号">
							</div>

                        	<div class="seach-input-item pull-left">
                            	<span>车辆品牌</span>
                            	<input type="text" class="form-control" name="vehicleBrand" placeholder="请输入车辆品牌">
                        	</div>

                            <div class="seach-input-item pull-left">
                            	<span>车辆型号</span>
								<input type="text" class="form-control" name="vehicleModel" placeholder="请输入车辆型号">
                        	</div>
							<div class="seach-input-item pull-left">
								<span>审核状态</span>
								<select class="form-control" name="censorStatus">
									<option value="">全部</option>
									<option value="0">未审核</option>
									<option value="1">已审核</option>
									<option value="3">未通过</option>
								</select>
							</div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type" onclick="$('.datepicker').datepicker('setDate', null);">清空</button>
						<button type="button" class="btn-new-type" id="loveCarMengLongSearchafter">确定</button>
					</div>
				</div>
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="loveCarMengLongTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="loveCarMengLongList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"loveCar": "res/js/ml/loveCar_list"
			}
		});
		require(["loveCar"], function(loveCar) {
            loveCar.init();
		});
		  $(".datepicker").datetimepicker({
	            language: "zh-CN",
	            minView: "month",
	            autoclose: true, //选中之后自动隐藏日期选择框
	            clearBtn: true, //清除按钮
	            todayBtn: true, //今日按钮
	            format: "yyyy-mm-dd"  //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	        });
	});
</script>