<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="dayCarAreaAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增服务区域</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>名称：</label>
						</td>
						<td>
							 <input class="form-control val"  placeholder="请输入名称" name="areaName" maxlength="20"/>
							 <span name="dayCarAreaNameAdd"></span>
						</td>
					<td>
						<label class="control-label key"><span>*</span>服务区域地址：</label>
					</td>
					<td>
						<div style="display: flex;">
		                        <select class="" name="addrRegion1Id" onchange="getDayResultCity(this.value)" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
		                       		<option value="">全部</option>
		                       		<#list provinces as province>
			                        	<option value="${province.regionId}" >
			                        		${province.regionName}
			                        	</option>
		                       		</#list>
		                        </select>

		                        <span id="dayCarCities">
		                         	<select class="" name="addrRegion2Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;" onchange="getResultDistricts(this.value)">
		                         		<option value="">全部</option>
		                       		</select>
		                        </span>

		                       <span id="dayCarDistricts">
			                         <select class="" name="addrRegion3Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
				                       	<option value="">全部</option>
			                        </select>
		                        </span>
		                       </div>
		                       <span name="addrRegionAdd"></span>
					</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addDayCarArea" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="addCloseDayCarArea" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
      require.config({paths: {"dayCarAreaAdd":"res/js/dailyrental/car/dayCarArea_add"}});
		require(["dayCarAreaAdd"], function (dayCarAreaAdd){
			dayCarAreaAdd.init();
		});
		
    });
</script>