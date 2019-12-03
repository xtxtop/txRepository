<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carModelEditForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车型详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							 <label class=" control-label key"><span></span>&nbsp;&nbsp;车型名称：</label>
						<td>
							<label class="control-label val">${model.carModelName}</label>
						</td>
						<td>
							 <label class=" control-label key">车辆品牌：</label>
						</td>
						<td>
							<label class="control-label val">${model.carBrandName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">车系名称：</label>
						</td>
						<td>
							<label class="control-label val">${model.carSeriesName}</label>
						</td>
						<td>
							<label class=" control-label key">车辆年代：</label>
						</td>
						<td>
							 <label class="control-label val">${model.carPeriodName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">配置款型：</label>
						</td>
						<td>
							<label class="control-label val">${model.configModel}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;适用类型：</label>
						</td>
						<td>
							 <label class="control-label val">
                        <#if model.carType==1>经济型</#if>
                        <#if model.carType==2>商务型</#if>
                        <#if model.carType==3>豪华型</#if>
                        <#if model.carType==4>6至15座商务</#if>
                        <#if model.carType==5>SUV</#if>
                        </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;车身：</label>
						</td>
						<td>
							<label class="control-label val">
                            <#if model.boxType==1>
                        		两箱
                        	</#if> 
	                        <#if model.boxType==2>
	                        	三箱
	                        </#if>
                         </label>
						</td>
						<td>
							 <label class=" control-label key"><span></span>&nbsp;&nbsp;座位数：</label>
						</td>
						<td>
							<label class="control-label val">
                        		<#if model.seatNumber==1>2座</#if>
                            	<#if model.seatNumber==2>4座</#if>
                            	<#if model.seatNumber==3>5座</#if>
                            	<#if model.seatNumber==4>7座</#if>
                        </label>
						</td>
					</tr>
					<tr>
						<td>
							 <label class=" control-label key"><span></span>&nbsp;&nbsp;变速箱：</label>
						</td>
						<td>
							<label class="control-label val">
                        <#if model.gearBox==1>
                        	手动 
                        <#elseif model.gearBox==2>
                        	自动
                        <#else>
                        	手自一体
                        </#if>
                        </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;排量：</label>
						</td>
						<td>
							<label class="control-label val">${model.displacement}</label>
						</td>
					</tr>
					<tr>
						<td>
							 <label class=" control-label key"><span></span>&nbsp;&nbsp;吨位：</label>
						</td>
						<td>
							<label class="control-label val">${model.tons}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;款型描述：</label>
						</td>
						<td>
							<label class="control-label val">${model.carModelInfo}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;车辆照片：</label>
						</td>
						<td>
							<img src="${imagePath!''}/${model.carModelUrl}" width="320" height="200" name="carPicUrlImg"/>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeEditCarModelVo" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
$("#closeEditCarModelVo").click(function(){
	closeTab("车型详情");
});
</script>