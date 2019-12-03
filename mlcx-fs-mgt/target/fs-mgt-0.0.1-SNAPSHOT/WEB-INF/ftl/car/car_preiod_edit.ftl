<meta charset="utf-8">

<div class="container-fluid backgroundColor">
	<form name="carPreiodEditForm" class="form-horizontal">
		<input type="hidden" name="carPeriodId" value="${carPreiod.carPeriodId}"/>
		<div class="row hzlist">
			
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑车辆年代</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label"><span>*</span>车型年代：</label></td>
						<td><input class="form-control" name="carPeriodName" value="${carPreiod.carPeriodName}"/><span name="carPeriodNameEdit"></span></td>
						<td><label class=" control-label"><span>*</span>品牌：</label></td>
						<td>
							<select class="form-control" name="carBrandId" onchange="selectBrandValueEdit('carPeriodEditBrandId')" name="carPeriodEditBrandId">
									<option value="">全部</option>
									<#if carBrandList?exists>
                                        <#list carBrandList as carBrand>
                                            <option value="${carBrand.carBrandId}" <#if carBrand.carBrandId==carPreiod.carBrandId>selected=selected</#if>>${carBrand.carBrandName}</option>
                                        </#list>
                                    </#if>
								</select>
								<span name="carBrandIdEdit"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label"><span>*</span>车型系列：</label></td>
						<td><select class="form-control" name="carSeriesId">
									<option value="">全部</option>
									<#if carSeries?exists>
                                        <#list carSeries as carSeries>
                                            <option value="${carSeries.carSeriesId}" <#if carSeries.carSeriesId==carPreiod.carSeriesId>selected=selected</#if>>${carSeries.carSeriesName}</option>
                                        </#list>
                                    </#if>
								</select>
								<span id="carSeriesIdEdit"></span></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="editCarPreiod" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarPreiodEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"carPreiodEdit":"res/js/car/car_preiod_edit"}});
		require(["carPreiodEdit"], function (carPreiodEdit){
			carPreiodEdit.init();
		});  
	});
</script>