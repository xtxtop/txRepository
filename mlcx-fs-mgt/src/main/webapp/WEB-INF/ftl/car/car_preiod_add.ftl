<meta charset="utf-8">

<div class="container-fluid backgroundColor">
	<form name="carPreiodAddForm" class="form-horizontal">
		<div class="row hzlist">
			
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增车辆年代</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label"><span>*</span>车型年代：</label></td>
						<td><input class="form-control" name="carPeriodName"  placeholder="请输入车型年代"/><span name="carPeriodNameAdd"></span></td>
						<td><label class=" control-label"><span>*</span>品牌：</label></td>
						<td>
							<select class="form-control" name="carBrandId" onchange="selectBrandValueAdd('carPeriodBrandId')" id="carPeriodBrandId">
									<option value="">全部</option>
									<#if carBrandList?exists>
                                        <#list carBrandList as carBrand>
                                            <option value="${carBrand.carBrandId}">${carBrand.carBrandName}</option>
                                        </#list>
                                    </#if>
								</select>
								<span name="carBrandIdAdd"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label"><span>*</span>车型系列：</label></td>
						<td><select class="form-control" name="carSeriesId">
									<option value="">全部</option>
								</select>
								<span name="carSeriesIdAdd"></span></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="addCarPreiod" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarPreiodAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
      
    </tr>
  </tfoot>
  </table>
		</div>
	</form>
</div>


<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carPreiodAdd":"res/js/car/car_preiod_add"}});
		require(["carPreiodAdd"], function (carPreiodAdd){
			carPreiodAdd.init();
		});  
	});
</script>