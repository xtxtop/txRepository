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
</style>
<div class="container-fluid">
			<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="morePricingRuleSearchafter">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
				<div class="search-input-content">
					<form class="form-horizontal" name="pricingRuleSearchForm">
						<div class="seach-input-border-box">
							<!-- /.box-tools -->
							<div class="seach-input-details close-content clearfix">
								<div class="seach-input-item pull-left">
									<span>名称</span>
									<input type="text" class="form-control" id="ruleName" name="ruleName" placeholder="请输入名称">
								</div>
								<div class="seach-input-item pull-left">
									<span>车型</span>
									<select class="form-control" name="carModelName">
										<option value="">全部</option>
										<#list carModelName as carModelName>
											<option value="${carModelName}">
												${carModelName}
											</option>
										</#list>
									</select>
								</div>
								<div class="seach-input-item pull-left">
									<span>城市</span>
									<select class="form-control" name="cityId">
										<option value="">全部</option>
										<#list cities as citie>
											<option value="${citie.dataDictItemId}">
												${citie.itemValue}
											</option>
										</#list>
									</select>
								</div>
								<div class="seach-input-item pull-left">
									<span>审核状态</span>
									<select class="form-control" name="cencorStatus">
										<option value="">全部</option>
										<option value="0">未审核</option>
										<option value="1">已审核</option>
										<option value="2">待审核</option>
										<option value="3">未通过</option>
									</select>
								</div>
								<div class="seach-input-item pull-left">
									<span>状态</span>
									<select class="form-control" name="isAvailable">
										<option value="">全部</option>
										<option value="1">启用</option>
										<option value="0">停用</option>
									</select>
								</div>
								<div class="seach-input-item pull-left">
									<span>集团名称</span>
									<select class="form-control" name="companyId">
										<option value=null>全部</option>
										<option value="">平台</option>
										<#list companys as companys>
											<option value="${companys.companyId}">
												${companys.companyName}
											</option>
										</#list>
									</select>
								</div>
							</div>
                            <div class="seacher-button-content">
								<button type="reset" class="btn-new-type">清空</button>
								<button type="button" class="btn-new-type" id="pricingRuleSearchafter">确定</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="row show-table-content">
				<div class="col-xs-12">
					<!--定义操作列按钮模板-->
					<script id="pricingRuleTpl" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
					<div class="box">
						<div class="box-body">
							<table id="pricingRuleList" class="table table-hover" width="100%" border="1">
							</table>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
			</div>

		</div>
		<!-- /.container-fluid -->

		<div class="modal fade" id="onpricingRuleModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">确定启用</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" name="pricingRuleOnForm">
							<input type="hidden" name="ruleNo" id="ruleNo1">
							<input type="hidden" name="isAvailable" value="1">
							<label for="inputEmail3" class=" control-label wen" id="onMemoPr"></label>
							<div>
								<label for="inputEmail3" class=" control-label reason">*原因:</label>
							</div>
							<div class="form-group">

								<div class="col-sm-8">
									<textarea class="form-control textareas" name="availableMemo"></textarea>
								</div>
							</div>
					</div>

					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="pricingRuleOnFormBtn" value="确定">
						<button type="button" class="btn btn-default pull-right cancels" id="pricingRuleOnCancelBtn">取消</button>
					</div>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<div class="modal fade" id="OffpricingRuleModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">确定停用</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="pricingRuleOffForm">
						<input type="hidden" name="ruleNo" id="ruleNo2">
						<input type="hidden" name="isAvailable" value="0">
						<input type="hidden" name="carModelName" id="stopPricingRuleCarModelName">
						<label for="inputEmail3" class=" control-label wen" id="offMemo"></label>
						<div>
							<label for="inputEmail3" class=" control-label reason">*原因:</label>
						</div>
						<div class="form-group">

							<div class="col-sm-8">
								<textarea class="form-control textareas" name="availableMemo" id="availableMemo"></textarea>
							</div>
						</div>

					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="pricingRuleOffBtn" value="确定">
						<button type="button" class="btn btn-default pull-right cancels" id="pricingRuleOffCancel">取消</button>
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
					"pricingRule": "res/js/marketing/pricingRule_list"
				}
			});
			require(["pricingRule"], function(pricingRule) {
				pricingRule.init();
			});

		});
	</script>