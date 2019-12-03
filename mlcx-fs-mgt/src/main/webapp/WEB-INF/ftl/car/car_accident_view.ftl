<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carAccidentForm" class="form-horizontal">
		<div class="row hzlist">
			<input type="hidden" name="accidentId" value="${carAccident.accidentId}"/>
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">事故详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							 <label class=" control-label key"><span>*</span>车牌号：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.carPlateNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>型号：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.carModelName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.cityName}</label>
						</td>
						<td>
							 <label class=" control-label key"><span>*</span>事故时间：</label>
						</td>
						<td>
							 <label class="control-label val">${carAccident.recordAccidentTime?string('yyyy-MM-dd hh:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>事故地点：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.accidentLocation}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>事故等级：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if carAccident.accidentLevel==1>
								一般事故
                				<#elseif carAccident.accidentLevel==2>
                				轻微事故
                				<#elseif carAccident.accidentLevel==3>
                				重大事故
                				<#elseif carAccident.accidentLevel==4>
                				特大事故
                				</#if>
                				</select>
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>保险公司：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.insuranceCompany}</label>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>保险进度：</label>
						</td>
						<td>
							<label class="control-label val">
                					<#if carAccident.accidentStatus==0>
                					未处理
                					<#elseif carAccident.accidentStatus==1>
                					处理中
                					<#elseif carAccident.accidentStatus==2>
                					已处理
                					</#if>
                				</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>用车类型：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if carAccident.useCarType==1>
                					订单用车
                				<#elseif carAccident.useCarType==2>
                					调度用车
                				</#if>
                				</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>单据号：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.documentNo}</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>用车人：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.driverName}</label>
						</td>
						<td>
							 <label class=" control-label reason key"><span>*</span>备注：</label>
						</td>
						<td>
							<label class="control-label val">${carAccident.memo}</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
                			<label class="control-label val">${carAccident.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
                			<label class="control-label val">${carAccident.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
				
						<td colspan="4"><button type="button" id="closeCarAccidentVeiw" class="btn-new-type-edit" >
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
</script>