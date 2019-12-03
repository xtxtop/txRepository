<meta charset="utf-8">
<style>
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
		.seach-input-item pull-left .form-control {
			margin-right: 10%；
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
	.table-bettween-far tr td{
	height:72px;
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
			<!-- /.box-header -->
			<form name="carOwnerSearchForm">
				<div class="seach-input-border-box">
					<!-- /.box-footer -->
					<div class="seach-input-details close-content clearfix">
							<div class="seach-input-item pull-left">
								<span>名称</span>
								<input class="form-control" name="ownerName" placeholder="请输入名称">
							</div>
							<div class="seach-input-item pull-left">
								<span>联系手机</span>
								<input class="form-control" name="contactPhone" placeholder="请输入联系手机">
							</div>
							<div class="seach-input-item pull-left">
								<span>类型</span>
								<select class="form-control" name="ownerType">
									<option value="">全部</option>
									<option value="2">个人</option>
									<option value="1">公司</option>
								</select>
							</div>

							<div class="seach-input-item pull-left">
								<span>审核状态</span>
								<select class="form-control" name="cencorStatus">
									<option value="">全部</option>
									<option value="0">未审核</option>
									<option value="3">未通过</option>
									<option value="1">已审核</option>
								</select>
							</div>

					</div>
					<!-- /.row -->
							<!--修改-->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carOwnerSearch">确定</button>
					</div>
				</div>
				
			</form>
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
<!-- /.row -->
<div class="row show-table-content">
	<div class="col-xs-12">
		<!--定义操作列按钮模板-->
		<script id="carOwnerBtnTpl" type="text/x-handlebars-template">
			{{#each func}}
			<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
		</script>
		<div class="box">
			<div class="box-body">
				<table id="carOwnerList" class="table table-hover table-bettween-far" width="100%" border="1">
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
				"carOwner": "res/js/car/car_owner_list"
			}
		});
		require(["carOwner"], function(carOwner) {
			carOwner.init();
		});
	});
	$(function() {
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>