<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carAccidentAddForm" class="form-horizontal">
		<div class="row hzlist">
			
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">事故新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							 <label class=" control-label key"><span>*</span>车牌号：</label>
                             <input type="hidden" name="carNo"/>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo" placeholder="请输入车牌号"/>
							<span id="carPlateNoErr"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>型号：</label>
                            <input type="hidden" id="carModelName" name="carModelName">
						</td>
						<td>
							<select name="carModelId" class="form-control val" onchange="selectSetValue('carModelId','carModelName')" id="carModelId">
                                <option value="">请选择</option>
                                <#if carModels?exists>
                                    <#list carModels as model>
                                        <option value="${model.carSeriesId}">${model.carSeriesName}</option>
                                    </#list>
                                </#if>
                            </select>
						    <span id="carModelIdErr"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
                        <input type="hidden" id="cityName" name="cityName">
						</td>
						<td>
							<select name="cityId" class="form-control val" onchange="selectSetValue('cityId','cityName')" id="cityId">
                                <option value="">请选择</option>
                                <#if cities?exists>
                                    <#list cities as city>
                                        <option value="${city.dataDictItemId}">${city.itemValue}</option>
                                    </#list>
                                </#if>
                            </select>
							<span id="cityIdErr"></span>
						</td>
						<td>
							 <label class=" control-label key"><span>*</span>事故时间：</label>
						</td>
						<td>
							 <input class="datetimepicker form-control val" name="recordAccidentTime" id="recordAccidentTime" placeholder="请选择事故时间"/>
							<span id="recordAccidentTimeErr"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>事故地点：</label>
						</td>
						<td>
							<input class="form-control val" name="accidentLocation" placeholder="请输入事故地点"/>
							<span id="accidentLocationErr"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>事故等级：</label>
						</td>
						<td>
							<select name="accidentLevel" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="1">一般事故</option>
                                    <option value="2">轻微事故</option>
                                    <option value="3">重大事故</option>
                                    <option value="4">特大事故</option>
                                </select>
							<span id="accidentLevelErr"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>保险公司：</label>
						</td>
						<td>
							 <select name="insuranceCompany" class="form-control val">
                                                <option value="">请选择</option>
                                                <option value="平安保险">平安保险</option>
                                                <option value="太平洋保险">太平洋保险</option>
                                            </select>
							<span id="insuranceCompanyErr"></span>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>保险进度：</label>
						</td>
						<td>
							<select name="accidentStatus" class="form-control val">
                                                <option value="">请选择</option>
                                                <option value="0">未处理</option>
                                                <option value="1">处理中</option>
                                                <option value="2">已处理</option>
                                            </select>
							<span id="accidentStatusErr"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>用车类型：</label>
						</td>
						<td>
							<select name="useCarType" class="form-control val">
                                                <option value="">请选择</option>
                                                <option value="1">订单用车</option>
                                                <option value="2">调度用车</option>
                                            </select>
							<span id="useCarTypeErr"></span>
						</td>
						<td>
							<label class=" control-label key"><span></span>单据号：</label>
						</td>
						<td>
							<input class="form-control val" name="documentNo" placeholder="请输入单据号"/>
							<span id="documentNoErr"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>用车人：</label>
						</td>
						<td>
							<input class="form-control val" name="driverName" placeholder="请输入用车人"/>
                                            <input type="hidden" name="driverId"/>
							<span id="driverNameErr"></span>
						</td>
						<td>
							 <label class=" control-label reason key"><span>*</span>备注：</label>
						</td>
						<td>
							<input class="form-control val" name="memo" placeholder="请填写备注"/>
						</td>

					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveAddCarAccidentAdd" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCarAccidentAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	 	require.config({paths: {"carAccidentAdd":"res/js/car/car_accident_add"}});
		require(["carAccidentAdd"], function (carAccidentAdd){
			carAccidentAdd.init();
		});
	});
    $(function () {
        $(".datetimepicker").datetimepicker({
	        language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            clearBtn: true,//清除按钮
            format: "yyyy-mm-dd hh:ii:ss"//日期格式
        });
    });
</script>