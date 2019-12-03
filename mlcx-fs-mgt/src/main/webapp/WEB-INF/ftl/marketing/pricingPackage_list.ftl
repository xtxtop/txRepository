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
				<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
			</div>
			<div class="search-input-content">
				<!-- /.box-header -->
				<form class="form-horizontal" name="pricingPackageSearchForm">
					<div class="seach-input-border-box">
						<!-- /.box-tools -->
						<div class="seach-input-details close-content clearfix">
							<div class="seach-input-item pull-left">
								<span>套餐名称</span>
								<input type="text" class="form-control" id="packageName" name="packageName" placeholder="请输入套餐名称">
							</div>
							<div class="seach-input-item pull-left">
								<span>上下架状态</span>
								<select class="form-control" name="isAvailable">
									<option value="">全部</option>
									<option value="0">下架</option>
									<option value="1">上架</option>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>套餐类型</span>
								<select class="form-control" name="packageType">
									<option value="">全部</option>
									<option value="1">赠送类套餐</option>
									<option value="2">销售套餐</option>
									<option value="4">订单分享类套餐</option>
								</select>
							</div>
							<div class="seach-input-item pull-left">
								<span>审核状态</span>
								<select class="form-control" name="cencorStatus">
									<option value="">全部</option>
									<option value="1">已审核</option>
									<option value="0" <#if censorStatus!=null && censorStatus==0>selected</#if> >待审核</option>
									<option value="2">未通过</option>
								</select>
							</div>
						</div>
						<!-- /.row -->
						<div class="seacher-button-content">
							<button type="reset" class="btn-new-type">清空</button>
							<button type="button" class="btn-new-type" id="pricingPackageSearchafter">确定</button>
						</div>
					</div>
					<!-- /.box-body -->
				</form>
				<!-- /.box-footer -->
			</div>
			<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="pricingPackageTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="pricingPackageList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>

</div>
<!-- /.container-fluid -->

<div class="modal fade" id="onpricingPackageModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定上架</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="onpricingPackageForm">
					<input type="hidden" name="packageNo" id="packageNo1">
					<input type="hidden" name="isAvailable" value="1">
					<label for="inputEmail3" class=" control-label" id="onMemoPF"></label>
					<div>
						<label for="inputEmail3" class=" control-label">*原因:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control" name="availableReason"></textarea>
						</div>
					</div>
			</div>

			</form>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-right sure" id="pricingPackageOnFormBtn" value="确定">
				<button type="button" class="btn btn-default pull-right cancels" id="pricingPackageOnCancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="OffpricingPackageModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">确定下架</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="offpricingPackageForm">
					<input type="hidden" name="packageNo" id="packageNo2">
					<input type="hidden" name="isAvailable" value="0">
					<label for="inputEmail3" class=" control-label wen" id="offMemo"></label>
					<div>
						<label for="inputEmail3" class=" control-label reason">*原因:</label>
					</div>
					<div class="form-group">

						<div class="col-sm-8">
							<textarea class="form-control textareas" name="availableReason"></textarea>
						</div>
					</div>

				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="pricingPackageOffBtn" value="确定">
					<button type="button" class="btn btn-default pull-right cancels" id="pricingPackageOffCancel">取消</button>
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"pricingPackage": "res/js/marketing/pricingPackage_list"
			}
		});
		require(["pricingPackage"], function(pricingPackage) {
			pricingPackage.init();
		});
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>