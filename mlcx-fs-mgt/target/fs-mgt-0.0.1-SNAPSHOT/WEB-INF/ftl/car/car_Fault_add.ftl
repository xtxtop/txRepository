<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carFaultAddForm" class="form-horizontal">
		
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">故障新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车牌号：</label>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo"  placeholder="请输入车牌号"/>
							<span id="carPlateNoAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>型号：</label>
						</td>
						<td>
							<select name="carModelId" class="form-control val">
								<#list carmodels as cmodel>
									<option value="${cmodel.carSeriesId}">
										${cmodel.carSeriesName}
									</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								<#list cities as citie>
									<option value="${citie.dataDictItemId}">
										${citie.itemValue}
									</option>
								</#list>
							</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>故障时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control val"  placeholder="请选择故障时间" name="recordFaultTime" id="recordFaultTime" />
							<span id="recordFaultTimeAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>故障地点：</label>
						</td>
						<td>
							<input class="form-control val" name="faultLocation"  placeholder="请输入故障地点"/>
							<span id="faultLocationAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>故障等级：</label>
						</td>
						<td>
							<select name="faultLevel" class="form-control val">
								<option value="">请选择</option>
								<option value="0">一级</option>
								<option value="1">二级</option>
								<option value="2">三级</option>
							</select>
							<span id="faultLevelAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>处理状态：</label>
						</td>
						<td>
							<select name="processingStatus" class="form-control val">
								<option value="">请选择</option>
								<option value="0">未处理</option>
								<option value="1">处理中</option>
								<option value="2">已处理</option>
							</select>
							<span id="processingStatusAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>用车类型：</label>
						</td>
						<td>
							<select name="useCarType" class="form-control val">
								<option value="">请选择</option>
								<option value="1">订单</option>
								<option value="2">调度</option>
							</select>
							<span id="useCarTypeAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>单据号：</label></td>
						<td>
							<input class="form-control val" name="documentNo"  placeholder="请输入单据号"/>
							<span id="documentNoAdd"></span>
						</td>
						<td><label class=" control-label key"><span>*</span>用车人：</label></td>
						<td>
							<select name="driverId" class="form-control val">
								<option value="">请选择</option>
								<#list members as member>
									<option value="${member.memberNo}">
										${member.memberName}
									</option>
								</#list>
							</select>
							<span id="driverIdAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label reason key">备注：</label>
						</td>
						<td>
							<input class="form-control val" name="memo"  placeholder="请填写备注"/>
							<span id="memoAdd"></span>
						</td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCarFault" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCarFault" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
				"carFaultAdd": "res/js/car/car_Fault_add"
			}
		});
		require(["carFaultAdd"], function(carFaultAdd) {
			carFaultAdd.init();
		});
	});
	$(function() {
		$(".datetimepicker").datetimepicker({
			language: "zh-CN",
			autoclose: true,
			todayBtn: true,
			minuteStep: 5,
			clearBtn: true, //清除按钮
			format: "yyyy-mm-dd hh:ii:ss" //日期格式
		});
	});
</script>