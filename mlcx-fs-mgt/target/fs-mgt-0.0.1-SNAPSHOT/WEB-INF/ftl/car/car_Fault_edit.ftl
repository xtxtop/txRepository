<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carFaultEditForm" class="form-horizontal">
		<input type="hidden" name="faultId" value="${carFault.faultId}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">故障编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td> <label class=" control-label key"><span>*</span>车牌号：</label></td>
						<td><input class="form-control val" name="carPlateNo" value="${carFault.carPlateNo}" /><span id="carPlateNoEdit"></span></td>
						<td>
							<label class=" control-label key"><span>*</span>型号：</label>
						</td>
						<td>
							<select name="carModelId" class="form-control val">
								<#list carmodels as cmodel>
									<option <#if carFault.carModelId==cmodel.dataDictItemId>selected</#if> value="${cmodel.dataDictItemId}" > ${cmodel.itemValue}
									</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>城市：</label></td>
						<td>
							<select name="cityId" class="form-control val">
								<#list cities as citie>
									<option <#if carFault.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" > ${citie.itemValue}
									</option>
								</#list>
							</select>
						</td>
						<td><label class=" control-label key"><span>*</span>故障时间：</label></td>
						<td><input class="datetimepicker form-control val" name="recordFaultTime" value="${carFault.recordFaultTime?string('yyyy-MM-dd HH:mm:ss')}" /><span id="recordFaultTimeEdit"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>故障地点：</label></td>
						<td><input class="form-control val" name="faultLocation" value="${carFault.faultLocation}" /><span id="faultLocationEdit"></span></td>
						<td><label class=" control-label key"><span>*</span>故障等级：</label></td>
						<td>
							<select name="faultLevel" class="form-control val">
								<option value="">请选择</option>
								<option <#if carFault.faultLevel==0>selected</#if> value="0">一级</option>
								<option <#if carFault.faultLevel==1>selected</#if> value="1" >二级</option>
								<option <#if carFault.faultLevel==2>selected</#if> value="2" >三级</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>处理状态：</label></td>
						<td>
							<select name="processingStatus" class="form-control val">
								<option value="">请选择</option>
								<option <#if carFault.processingStatus==0>selected</#if> value="0" >未处理</option>
								<!--<option <#if carFault.processingStatus==1>selected</#if> value="1"  >处理中</option>-->
								<option <#if carFault.processingStatus==2>selected</#if> value="2" >已处理</option>
							</select>
							<span id="processingStatusEdit"></span>
						</td>
						<td><label class=" control-label key"><span>*</span>用车类型：</label></td>
						<td>
							<select name="useCarType" class="form-control val">
								<option value="">请选择</option>
								<option <#if carFault.useCarType==1>selected</#if> value="1">订单</option>
								<option <#if carFault.useCarType==2>selected</#if> value="2">调度</option>
							</select>
							<span id="useCarTypeEdit"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>单据号：</label></td>
						<td><input class="form-control val" name="documentNo" value="${carFault.documentNo}" /><span id="documentNoEdit"></span></td>
						<td><label class=" control-label key"><span>*</span>用车人：</label></td>
						<td>
							<select name="driverId" class="form-control val">
								<#list members as member>
									<option <#if carFault.driverId==member.memberNo>selected</#if> value="${member.memberNo}" > ${member.memberName}
									</option>
								</#list>
							</select>
							<span id="driverIdEdit"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label reason key">备注：</label></td>
						<td> <input class="form-control val" name="memo" value="${carFault.memo}" /><span id="memoEdit"></span></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editCarFault" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditCarFault" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"carFaultEdit": "res/js/car/car_Fault_edit"
			}
		});
		require(["carFaultEdit"], function(carFaultEdit) {
			carFaultEdit.init();
		});
	});
	$(function() {
		$(".datetimepicker").datetimepicker({
			language: "zh-CN",
			autoclose: true,
			todayBtn: true,
			minuteStep: 5,
			format: "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>