<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carAccidentEditForm" class="form-horizontal">
		<div class="row hzlist">
			<input type="hidden" name="accidentId" value="${carAccident.accidentId}"/>
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">事故编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;车牌号：</label>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo" value="${carAccident.carPlateNo}" placeholder="请输入车牌号"/>
							<span name="carPlateNoEditErr"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;型号：</label>
                            <input type="hidden" id="carModelId" name="carModelId" value="${carAccident.carModelId}">
						</td>
						<td>
							<input class="form-control val" name="carModelName" value="${carAccident.carModelName}" readonly placeholder="请输入型号"/>
						    <span id="carModelIdEditErr"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
							<input type="hidden" id="cityId" name="cityId" value="${carAccident.cityId}">
						</td>
						<td>
							 <input class="form-control val" name="cityName" value="${carAccident.cityName}" readonly>
							<span name="cityIdEditErr"></span>
						</td>
						<td>
							 <label class=" control-label key"><span>*</span>&nbsp;&nbsp;事故时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control val" name="recordAccidentTime" value="${carAccident.recordAccidentTime?string('yyyy-MM-dd hh:mm:ss')}" id="recordAccidentTimeForEdit"/>
							<span name="recordAccidentTimeEditErr"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;事故地点：</label>
						</td>
						<td>
							<input class="form-control val" name="accidentLocation" value="${carAccident.accidentLocation}"/>
							<span name="accidentLocationEditErr"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;事故等级：</label>
						</td>
						<td>
							<select name="accidentLevel" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="1" <#if carAccident.accidentLevel==1>selected=selected</#if> >一般事故</option>
                                    <option value="2" <#if carAccident.accidentLevel==2>selected=selected</#if> >轻微事故</option>
                                    <option value="3" <#if carAccident.accidentLevel==3>selected=selected</#if> >重大事故</option>
                                    <option value="4" <#if carAccident.accidentLevel==4>selected=selected</#if> >特大事故</option>
                                </select>
							<span name="accidentLevelEditErr"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;保险公司：</label>
						</td>
						<td>
							<select name="insuranceCompany" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="平安保险" <#if carAccident.insuranceCompany=="平安保险">selected=selected</#if>>平安保险</option>
                                    <option value="太平洋保险" <#if carAccident.accidentStatus=="太平洋保险">selected=selected</#if>>太平洋保险</option>
                                </select>
							<span name="insuranceCompanyEditErr"></span>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;保险进度：</label>
						</td>
						<td>
							<select name="accidentStatus" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="0" <#if carAccident.accidentStatus==0>selected=selected</#if>>未处理</option>
                                    <option value="1" <#if carAccident.accidentStatus==1>selected=selected</#if>>处理中</option>
                                    <option value="2" <#if carAccident.accidentStatus==2>selected=selected</#if>>已处理</option>
                                </select>
							<span name="accidentStatusEditErr"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;用车类型：</label>
						</td>
						<td>
							<select name="useCarType" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="1" <#if carAccident.useCarType==1>selected=selected</#if>>订单用车</option>
                                    <option value="2" <#if carAccident.useCarType==2>selected=selected</#if>>调度用车</option>
                                </select>
							<span name="useCarTypeEditErr"></span>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;单据号：</label>
						</td>
						<td>
							<input class="form-control val" name="documentNo" value="${carAccident.documentNo}"/>
							<span name="documentNoEditErr"></span>
						</td>

					</tr>
					<tr>
						<td>
							<input type="hidden" name="driverId" />
							<label class=" control-label key"><span></span>&nbsp;&nbsp;用车人：</label>
						</td>
						<td>
							<input class="form-control val" name="driverName" value="${carAccident.driverName}"/>
                                <input type="hidden" name="driverId" value="${carAccident.driverId}"/>
							<span name="driverNameEditErr"></span>
						</td>
						<td>
							 <label class=" control-label reason key"><span>*</span>&nbsp;&nbsp;备注：</label>
						</td>
						<td>
							<input class="form-control val" name="memo" value="${carAccident.memo}"/>
						</td>

					</tr>
					<tr>
						<td>
							 <label class=" control-label key"><span>*</span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							 <label class=" control-label key"><span>*</span>&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							 <label class="control-label val"${carAccident.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>

					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCarAccidentEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCarAccidentEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"carAccidentEdit":"res/js/car/car_accident_edit"}});
		require(["carAccidentEdit"], function (carAccidentEdit){
			carAccidentEdit.init();
		});
	});
   $(function () {
        $(".datetimepicker").datetimepicker({
	        language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            kind:"dtkTime",
            clearBtn: true,//清除按钮
            format: "yyyy-mm-dd hh:ii:ss"//日期格式
        });
    });
</script>