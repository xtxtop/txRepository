<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="dayCarAreaEditForm">
		 <input  name="carAreaId" type="hidden"  value=${dayCarArea.carAreaId}>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">修改服务区域</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control val" name="areaName" maxlength="20"  value=${dayCarArea.areaName}>
							<span name="dayCarAreaNameEdit"></span>
						</td>
					<td>
						<label class=" control-label key"><span>*</span>服务区域地址：</label>
					</td>
					<td>
						<div style="display: flex;">
		                        <select class="" name="addrRegion1Id" onchange="getResultCityEdit(this.value)" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
		                       		<option value="">全部</option>
		                       		<#list provinces as province>
			                        	<option value="${province.regionId}" <#if province.regionId==dayCarArea.addrRegion1Id>selected</#if> >
			                        		${province.regionName}
			                        	</option>
		                       		</#list>
		                        </select>

		                        <span id="dayCarCitiesEdit">
		                         	<select class="" name="addrRegion2Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;" onchange="getDayResultDistrictsEdit(this.value)">
		                         		<option value="">全部</option>
		                         		<#list cities as city>
				                        	<option value="${city.regionId}" <#if city.regionId==dayCarArea.addrRegion2Id>selected</#if> >
				                        		${city.regionName}
				                        	</option>
		                       			</#list>
		                       		</select>
		                        </span>

		                       <span id="dayCarDistrictsEdit">
			                         <select class="" name="addrRegion3Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
				                       	<option value="">全部</option>
				                       	<#list districts as district>
				                        	<option value="${district.regionId}" <#if district.regionId==dayCarArea.addrRegion3Id>selected</#if> >
				                        		${district.regionName}
				                        	</option>
		                       			</#list>
			                        </select>
		                        </span>
		                       </div>
		                       <span name="addrRegionEdit"></span>
					</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editDayCarArea" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="editCloseDayCarArea" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
  $(function () {
      require.config({paths: {"dayCarAreaEdit":"res/js/dailyrental/car/dayCarArea_edit"}});
		require(["dayCarAreaEdit"], function (dayCarAreaEdit){
			dayCarAreaEdit.init();
		});
		
    });
</script>