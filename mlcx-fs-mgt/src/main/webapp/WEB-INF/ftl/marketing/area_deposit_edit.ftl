<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="areaDepositEditFrom">
		<input type="hidden" name="id" value="${areaDeposit.id}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">地区应缴押金编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>地区：</label>
						</td>
						<td>
							<div class="">
							  <select class="col-sm-4" id="addrRegion1Id"  name="addrRegion1Id" value="${areaDeposit.addrRegion1Id}" onchange="getResultCity(this.value)" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
		                        <#list plist as province>
		                        <option <#if areaDeposit.addrRegion1Id==province.regionId>selected</#if> value="${province.regionId}" >
		                        ${province.regionName}
		                        </option>
		                        </#list>
		                        </select>
								<span id="itemcity">
	                              <#if areaDeposit.addrRegion2Id!=0&&areaDeposit.addrRegion2Id!=null>
	                               <select class="col-sm-4" name="addrRegion2Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;" onchange="getResultCountry(this.value)">
	                              <#list plists2 as pr>
	                              <option <#if areaDeposit.addrRegion2Id==pr.regionId>selected</#if> value="${pr.regionId}" >
	                              ${pr.regionName}
	                              </option>
	                              </#list>
	                              </select>
	                              </#if>
	                             </span>
	                             <span id="itemarea">
	                              <#if areaDeposit.addrRegion3Id!=0&&areaDeposit.addrRegion3Id!=null>
	                              <select class="col-sm-4" name="addrRegion3Id" style="height:34px;border: 1px solid #ccc;">
	                              <#list plists3 as p3>
	                              <option
	                              <#if areaDeposit.addrRegion3Id==p3.regionId>selected</#if> value="${p3.regionId}" >
	                              ${p3.regionName}
	                              </option>
	                             </#list>
	                              </select>
	                              </#if>
                              </span>
							</div>
							<span name="areaDepositNameEdit"></span>
						</td>
						<td>
							<label class="control-label key"><span>*</span>应缴押金：</label>
						</td>
						<td>
							 <input class="form-control val" name="depositAmount"value="${areaDeposit.depositAmount}"/>
							 <span name="depositAmountEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editAreaDeposit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditAreaDeposit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"areaDepositEdit":"res/js/marketing/area_deposit_edit"}});
		require(["areaDepositEdit"], function (areaDepositEdit){
			areaDepositEdit.init();
		});  
    });
</script>
