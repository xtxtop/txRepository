<meta charset="utf-8">
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
			<form class="form-horizontal" name="carInventorySearchForm">
				<div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>车辆品牌</span>
							<select name="carBrandId" class="form-control">
								<option value="">请选择</option>
								<#list carBrands as carBrand>
									<option value="${carBrand.carBrandId}">
										${carBrand.carBrandName}
									</option>
								</#list>
							</select>
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>车辆型号</span>
							<select name="carModelId" class="form-control">
								<option value="">请选择</option>
								<#list carModels as carModel>
									<option value="${carModel.carModelId}">
										${carModel.carModelName}
									</option>
								</#list>
							</select>
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>城市</span>
							<select name="cityId" class="form-control">
								<option value="">请选择</option>
								<#list cities as citie>
									<option value="${citie.dataDictItemId}">
										${citie.itemValue}
									</option>
								</#list>
							</select>
						</div>
					</div>
					<!-- /.box-body -->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carInventorySearchafter">确定</button>
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
			<script id="carInventoryTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="carInventoryList" class="table table-hover table-bettween-far" width="100%" border='1'>
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
				"carInventory": "res/js/dailyrental/car/car_inventory_list"
			}
		});
		require(["carInventory"], function(carInventory) {
			carInventory.init();
		});
	});
</script>